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
    public static final int PLAYER_COUNT = 2; // constant value
    private Server server;
    private ClientManager clientManager = new ClientManager();
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

        String playerId = clientManager.getIdFromClient(responseObserver);
        System.out.println("Client connected: " + playerId);

        if (clientManager.getClientCount() == PLAYER_COUNT) {
            logic = new Logic(clientManager.getAllIds());
            clientManager.getClientFromId(logic.getTurnPlayerId()).onNext(logic.getGameState(playerId));
        }
        return new StreamObserver<ActionRequest>() {
            @Override
            public void onNext(ActionRequest request) {
                System.out.println("Received action: [" + playerId + ", " + request.getActionCase() + "]");

                // process ActionRequest and send new GameState
                logic.processActionRequest(playerId, request);

                String nextPlayerId = logic.getTurnPlayerId();
                StreamObserver<GameState> client = clientManager.getClientFromId(nextPlayerId);
                if (client != null) {
                    // send new GameState to the next player
                    client.onNext(logic.getGameState(nextPlayerId)); 
                }
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("Error: " + t.getMessage());
                server.shutdown();
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
                System.out.println("One of the clients disconnected!");
                server.shutdown();
            }
        };
    }
}