import skulls.Skulls.GameState;
import skulls.Skulls.ActionRequest;
import skulls.Skulls.PlayerData;
import skulls.Skulls.Card;
import skulls.Skulls.KnownDiscardedCard;

import java.util.ArrayList;

public class Logic {
    private GameState gameState;

    public Logic(ArrayList<String> playerIds) {
        GameState.Builder gameStateBuilder = GameState.newBuilder()
            .setTurnIndex(0);

        for (String playerId : playerIds) {
            PlayerData.Builder playerDataBuilder = PlayerData.newBuilder()
                .setPlayerId(playerId)
                .setPoints(0);

            // Add 3 roses and 1 skull to the player's hand
            playerDataBuilder.addHand(Card.ROSE);
            playerDataBuilder.addHand(Card.ROSE);
            playerDataBuilder.addHand(Card.ROSE);
            playerDataBuilder.addHand(Card.SKULL);

            gameStateBuilder.addPlayerData(playerDataBuilder);
        }

        gameState = gameStateBuilder.build();
    }

    public void processActionRequest(ActionRequest request) {
        // process the request
        // update the game state
    }

    public GameState getGameState(String playerId) {
        // Create a copy of the game state
        GameState.Builder perspectiveGameStateBuilder = gameState.toBuilder();

        // Modify the copied game state from the perspective of the player
        for (PlayerData.Builder playerDataBuilder : perspectiveGameStateBuilder.getPlayerDataBuilderList()) {
            if (!playerDataBuilder.getPlayerId().equals(playerId)) {
                for (int i = 0; i < playerDataBuilder.getHandCount(); i++) {
                    playerDataBuilder.setHand(i, Card.HIDDEN);
                }
                for (int i = 0; i < playerDataBuilder.getPlayedCount(); i++) {
                    playerDataBuilder.setPlayed(i, Card.HIDDEN);
                }
            }
        }

        for (KnownDiscardedCard.Builder knownDiscardedCardBuilder : perspectiveGameStateBuilder.getKnownDiscardedCardsBuilderList()) {
            if (!knownDiscardedCardBuilder.getKnowingPlayerId().equals(playerId)) {
                knownDiscardedCardBuilder.setCard(Card.HIDDEN);
            }
        }

        return perspectiveGameStateBuilder.build();
    }

}