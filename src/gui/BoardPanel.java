package gui;

import game.Card;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.List;

/**
 * @author Mshnik
 */
public class BoardPanel extends JPanel {

  private final List<Card> cards = Card.getAllCards();

  private GuiCardRow row;
  private JButton button;

  BoardPanel() {
    row = new GuiCardRow(4, GuiCardRow.GuiCardRowDirection.RIGHT_TO_LEFT);
    add(row, BorderLayout.CENTER);

    button = new JButton("add card");
    add(button, BorderLayout.SOUTH);
  }


  public static void main(String[] args) {
    JFrame frame = new JFrame();
    BoardPanel b = new BoardPanel();
    frame.getContentPane().add(b, BorderLayout.CENTER);
    frame.pack();
    frame.setVisible(true);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    b.button.addActionListener((e) -> {
      Card c = b.cards.get(new Random().nextInt(b.cards.size()));
      b.row.addCard(c);
      frame.pack();
    });
  }
}
