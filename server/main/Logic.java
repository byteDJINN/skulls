

import skulls.Skulls.GameState;
import skulls.Skulls.ActionRequest;
import skulls.Skulls.PlayerData;
import skulls.Skulls.Card;
import skulls.Skulls.KnownDiscardedCard;


public class Logic {
  private GameState gameState;

  public void processActionRequest(ActionRequest request) {
    // process the request
    // update the game state
  }

    public GameState getGameState(String playerId) {
        // Create a new GameState object
        GameState.Builder newGameStateBuilder = GameState.newBuilder();

        // Copy and modify player data
        for (PlayerData playerData : gameState.getPlayerDataList()) {
            PlayerData.Builder newPlayerDataBuilder = PlayerData.newBuilder()
                .setPlayerId(playerData.getPlayerId())
                .setPoints(playerData.getPoints());

            if (!playerData.getPlayerId().equals(playerId)) {
                for (Card card : playerData.getHandList()) {
                    newPlayerDataBuilder.addHand(Card.HIDDEN);
                }
                for (Card card : playerData.getPlayedList()) {
                    newPlayerDataBuilder.addPlayed(Card.HIDDEN);
                }
            } else {
                newPlayerDataBuilder.addAllHand(playerData.getHandList());
                newPlayerDataBuilder.addAllPlayed(playerData.getPlayedList());
            }

            newGameStateBuilder.addPlayerData(newPlayerDataBuilder);
        }

        // Copy and modify known discarded cards
        for (KnownDiscardedCard knownDiscardedCard : gameState.getKnownDiscardedCardsList()) {
            KnownDiscardedCard.Builder newKnownDiscardedCardBuilder = KnownDiscardedCard.newBuilder()
                .setKnowingPlayerId(knownDiscardedCard.getKnowingPlayerId())
                .setPlayerId(knownDiscardedCard.getPlayerId());

            if (!knownDiscardedCard.getKnowingPlayerId().equals(playerId)) {
                newKnownDiscardedCardBuilder.setCard(Card.HIDDEN);
            } else {
                newKnownDiscardedCardBuilder.setCard(knownDiscardedCard.getCard());
            }

            newGameStateBuilder.addKnownDiscardedCards(newKnownDiscardedCardBuilder);
        }

        // Set the turn index
        newGameStateBuilder.setTurnIndex(gameState.getTurnIndex());

        return newGameStateBuilder.build();
    }


}