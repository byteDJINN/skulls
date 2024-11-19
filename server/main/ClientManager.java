import io.grpc.stub.StreamObserver;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.ArrayList;

public class ClientManager<T> {
  private final Map<StreamObserver<T>, String> clientPlayerMap = new LinkedHashMap<>();

  public ClientManager() {
}

  public void addClient(StreamObserver<T> client) {
    System.out.println("Adding client");
    String playerId = UUID.randomUUID().toString();
    clientPlayerMap.put(client, playerId);
  }

  public void removeClient(StreamObserver<T> client) {
    System.out.println("Removing client");
    clientPlayerMap.remove(client);
  }

  public StreamObserver<T> getClientFromId(String playerId) {
    for (Map.Entry<StreamObserver<T>, String> entry : clientPlayerMap.entrySet()) {
      if (entry.getValue().equals(playerId)) {
        return entry.getKey();
      }
    }
    return null;
  }

  public String getIdFromClient(StreamObserver<T> client) {
    return clientPlayerMap.get(client);
  }

  public int getClientCount() {
    return clientPlayerMap.size();
  }

  public ArrayList<String> getAllIds() {
    return new ArrayList<>(clientPlayerMap.values());
  }

}