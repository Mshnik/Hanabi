package gui;

import game.Card;
import game.Game;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.List;

/**
 * @author Mshnik
 */
class BoardPanel extends JPanel {

  private Map<Card.Color, GuiCard> board;
  private JPanel boardPanel;

  BoardPanel() {
    boardPanel = new JPanel();
    board = new HashMap<>();
    for (Card.Color color : Card.Color.values()) {
      board.put(color, null);
    }
    add(boardPanel, BorderLayout.CENTER);
  }

  void play(Card c) {
    GuiCard previous = board.get(c._1);
    if (previous != null) {
      boardPanel.remove(previous);
    }
    board.put(c._1, new GuiCard(c));
    for (GuiCard guiCard : board.values()) {
      if (guiCard != null) {
        boardPanel.remove(guiCard);
      }
    }
    for (GuiCard guiCard : board.values()) {
      if (guiCard != null) {
        boardPanel.add(guiCard);
      }
    }
  }
}
