package gui;

import game.Card;
import game.Game;

import javax.swing.*;
import java.awt.*;

/**
 * @author Mshnik
 */
class HandPanel extends JPanel {

  private GuiCardRow[] playerHands;

  HandPanel(int playerCount) {
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    playerHands = new GuiCardRow[playerCount];
    for(int i = 0; i < playerCount; i++) {
      playerHands[i] = new GuiCardRow(1, Game.getStartingHandSizeForPlayerCount(playerCount));
      add(playerHands[i]);
    }
  }

  void draw(int playerIndex, Card c) {
    playerHands[playerIndex].addCard(c);
  }

  void remove(int playerIndex, int cardIndex) {
    playerHands[playerIndex].removeCardAtIndex(cardIndex);
  }
}
