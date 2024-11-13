import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.ArrayList;

import skulls.GameServiceGrpc;
import skulls.Skulls.GameState;
import skulls.Skulls.ActionRequest;

public class GameServer extends GameServiceGrpc.GameServiceImplBase {

    private class ClientManager {
        private final ArrayList<StreamObserver<GameState>> clients = new ArrayList<>();
        private int currentIndex = 0; 

        public void addClient(StreamObserver<GameState> client) {
            System.out.println("Adding client");
            clients.add(client);
        }

        public void removeClient(StreamObserver<GameState> client) {
            System.out.println("Removing client");
            clients.remove(client);
        }

        public StreamObserver<GameState> nextClient() {
            if (clients.isEmpty()) {
                return null;
            }
            currentIndex = (currentIndex + 1) % clients.size();
            return clients.get(currentIndex);
        }
    }
    
    private Server server;
    private final ClientManager clientManager = new ClientManager();
    
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
                GameState response = GameState.newBuilder().build();

                StreamObserver<GameState> client = clientManager.nextClient();
                if (client != null) {
                    client.onNext(response);
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