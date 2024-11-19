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

    public void processActionRequest(String playerId, ActionRequest request) {
      // Check that playerId matches turn index
      if (!gameState.getPlayerData(gameState.getTurnIndex()).getPlayerId().equals(playerId)) {
        throw new IllegalArgumentException("Not player's turn");
      }

      if (request.hasBetAction()) {
        // TODO this is hard to do
      } else if (request.hasPassAction()) {
        gameState = gameState.toBuilder()
          .setTurnIndex((gameState.getTurnIndex() + 1) % gameState.getPlayerDataCount())
          .build();
      } else if (request.hasSkullAction()) {
        PlayerData currentPlayerData = gameState.getPlayerData(gameState.getTurnIndex());
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

        gameState = gameState.toBuilder()
          .setTurnIndex((gameState.getTurnIndex() + 1) % gameState.getPlayerDataCount())
          .setPlayerData(gameState.getTurnIndex(), newPlayerDataBuilder)
          .build();
      } else if (request.hasRoseAction()) {
        PlayerData currentPlayerData = gameState.getPlayerData(gameState.getTurnIndex());
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

        gameState = gameState.toBuilder()
          .setTurnIndex((gameState.getTurnIndex() + 1) % gameState.getPlayerDataCount())
          .setPlayerData(gameState.getTurnIndex(), newPlayerDataBuilder)
          .build();
      }
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

    public String getTurnPlayerId() {
        return gameState.getPlayerData(gameState.getTurnIndex()).getPlayerId();
    }

}