import skulls.Skulls.ActionRequest;
import skulls.Skulls.GameState;
import skulls.Skulls.PlayerData;
import skulls.Skulls.Card;

public class SkullActionProcessor implements ActionProcessor {
  @Override
  public void process(String playerId, ActionRequest request, GameState.Builder gameStateBuilder) {
    PlayerData currentPlayerData = gameStateBuilder.getPlayerData(gameStateBuilder.getTurnIndex());
    if (!currentPlayerData.getHandList().contains(Card.SKULL)) {
      throw new IllegalArgumentException("Player does not have a skull to play");
    }

    // Create a new hand without the skull
    PlayerData.Builder newPlayerDataBuilder = currentPlayerData.toBuilder();
    newPlayerDataBuilder.clearHand();
    boolean skullRemoved = false;
    for (Card card : currentPlayerData.getHandList()) {
      if (card == Card.SKULL && !skullRemoved) {
        skullRemoved = true;
      } else {
        newPlayerDataBuilder.addHand(card);
      }
    }
    newPlayerDataBuilder.addPlayed(Card.SKULL);
    gameStateBuilder.setPlayerData(gameStateBuilder.getTurnIndex(), newPlayerDataBuilder);
  }
}