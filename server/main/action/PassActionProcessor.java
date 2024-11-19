import skulls.Skulls.GameState;
import skulls.Skulls.ActionRequest;

public class PassActionProcessor implements ActionProcessor {
    @Override
    public void process(String playerId, ActionRequest request, GameState.Builder gameStateBuilder) {
        gameStateBuilder.setTurnIndex((gameStateBuilder.getTurnIndex() + 1) % gameStateBuilder.getPlayerDataCount());
    }
}