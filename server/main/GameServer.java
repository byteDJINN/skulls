import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.UUID;

import skulls.GameServiceGrpc;
import skulls.Skulls.GameState;
import skulls.Skulls.ActionRequest;

public class GameServer extends GameServiceGrpc.GameServiceImplBase {
    private class ClientManager {
        private final LinkedHashMap<StreamObserver<GameState>, String> clientPlayerMap = new LinkedHashMap<>();
        private int currentIndex = 0;

        public void addClient(StreamObserver<GameState> client) {
            System.out.println("Adding client");
            String playerId = assignPlayerId();
            clientPlayerMap.put(client, playerId);

            if (clientPlayerMap.size() == 2) { // TODO arbitrary value start game at 2 players
                logic = new Logic(new ArrayList<>(clientPlayerMap.values()));
                client.onNext(logic.getGameState(playerId));
            }
        }

        public void removeClient(StreamObserver<GameState> client) {
            System.out.println("Removing client");
            clientPlayerMap.remove(client);
        }

        public StreamObserver<GameState> nextClient() {
            if (clientPlayerMap.isEmpty()) {
                return null;
            }
            currentIndex = (currentIndex + 1) % clientPlayerMap.size();
            return (StreamObserver<GameState>) clientPlayerMap.keySet().toArray()[currentIndex];
        }

        public String getPlayerId(StreamObserver<GameState> client) {
            return clientPlayerMap.get(client);
        }

        private String assignPlayerId() {
            return UUID.randomUUID().toString();
        }
    }
    
    private Server server;
    private final ClientManager clientManager = new ClientManager();
    private Logic logic;
    
    public void run() {
        try {
            int port = 50051;
            server = ServerBuilder.forPort(port)
                .addService(this)
                .build()
                .start();
            System.out.println("Server started, listening on " + port);
            // block until server shuts down
            server.awaitTermination(); 
        } catch (IOException | InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public StreamObserver<ActionRequest> getAction(StreamObserver<GameState> responseObserver) {
        clientManager.addClient(responseObserver);
        return new StreamObserver<ActionRequest>() {
            @Override
            public void onNext(ActionRequest request) {
                System.out.println("Received action: " + request.getActionCase());

                // process ActionRequest and send new GameState
                logic.processActionRequest(request);

                StreamObserver<GameState> client = clientManager.nextClient();
                if (client != null) {
                    client.onNext(logic.getGameState(clientManager.getPlayerId(client))); 
                }
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("Error: " + t.getMessage());
                clientManager.removeClient(responseObserver);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
                clientManager.removeClient(responseObserver);
            }
        };
    }
}