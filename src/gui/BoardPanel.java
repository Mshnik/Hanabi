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
public class BoardPanel extends JPanel {

  private final List<Card> cards = Card.getAllCards();

  private GuiCardRow[] playerHands;

  private Map<Card.Color, GuiCard> board;
  private JPanel boardPanel;

  private JButton button;

  BoardPanel(int playerCount) {
    JPanel handPanel = new JPanel();
    handPanel.setLayout(new BoxLayout(handPanel, BoxLayout.Y_AXIS));
    playerHands = new GuiCardRow[playerCount];
    for(int i = 0; i < playerCount; i++) {
      playerHands[i] = new GuiCardRow(1, Game.getStartingHandSizeForPlayerCount(playerCount));
      handPanel.add(playerHands[i]);
    }
    add(handPanel, BorderLayout.WEST);

    boardPanel = new JPanel();
    board = new HashMap<>();
    for (Card.Color color : Card.Color.values()) {
      board.put(color, null);
    }
    add(boardPanel, BorderLayout.CENTER);

    button = new JButton("add card");
    add(button, BorderLayout.SOUTH);
  }

  void draw(int playerIndex, Card c) {
    playerHands[playerIndex].addCard(c);
  }

  void remove(int playerIndex, Card c) {
    playerHands[playerIndex].removeCard(c);
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

  public static void main(String[] args) {
    JFrame frame = new JFrame();
    BoardPanel b = new BoardPanel(2);
    frame.getContentPane().add(b, BorderLayout.CENTER);
    frame.pack();
    frame.setVisible(true);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    b.button.addActionListener((e) -> {
      Card c = b.cards.get(new Random().nextInt(b.cards.size()));
      b.play(c);
      frame.pack();
    });
  }
}
