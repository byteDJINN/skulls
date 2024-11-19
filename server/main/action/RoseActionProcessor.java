import skulls.Skulls.ActionRequest;
import skulls.Skulls.GameState;
import skulls.Skulls.PlayerData;
import skulls.Skulls.Card;

public class RoseActionProcessor implements ActionProcessor {
  @Override
  public void process(String playerId, ActionRequest request, GameState.Builder gameStateBuilder) {
    PlayerData currentPlayerData = gameStateBuilder.getPlayerData(gameStateBuilder.getTurnIndex());
    if (!currentPlayerData.getHandList().contains(Card.ROSE)) {
      throw new IllegalArgumentException("Player does not have a rose to play");
    }

    // Create a new hand without the rose
    PlayerData.Builder newPlayerDataBuilder = currentPlayerData.toBuilder();
    newPlayerDataBuilder.clearHand();
    boolean roseRemoved = false;
    for (Card card : currentPlayerData.getHandList()) {
      if (card == Card.ROSE && !roseRemoved) {
        roseRemoved = true;
      } else {
        newPlayerDataBuilder.addHand(card);
      }
    }
    newPlayerDataBuilder.addPlayed(Card.ROSE);
    gameStateBuilder.setPlayerData(gameStateBuilder.getTurnIndex(), newPlayerDataBuilder);
  }
}