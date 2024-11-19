import skulls.Skulls.GameState;
import skulls.Skulls.ActionRequest;

public interface ActionProcessor {
    void process(String playerId, ActionRequest request, GameState.Builder gameStateBuilder);
}