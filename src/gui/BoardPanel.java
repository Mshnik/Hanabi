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

  BoardPanel() {

    add(new GuiCard(Card.getAllCards().get(0)), BorderLayout.NORTH);

    GuiCardRow row = new GuiCardRow(4, GuiCardRow.GuiCardRowDirection.LEFT_TO_RIGHT);
    add(row, BorderLayout.CENTER);

    JButton button = new JButton("add card");
    button.addActionListener((e) -> {
      Card c = cards.get(new Random().nextInt(cards.size()));
      row.addCard(c);
    });
    add(button, BorderLayout.SOUTH);
  }


  public static void main(String[] args) {
    JFrame frame = new JFrame();
    frame.getContentPane().add(new BoardPanel(), BorderLayout.CENTER);
    frame.pack();
    frame.setVisible(true);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }
}
