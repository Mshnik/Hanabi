package gui;

import game.Card;

import javax.swing.*;
import java.awt.*;

/**
 * @author Mshnik
 */
class GuiCard extends JPanel {
  private static final int WIDTH = 50;
  private static final int HEIGHT= 50;

  private final Card card;

  GuiCard(Card card) {
    this.card = card;

    setBounds(0, 0, WIDTH, HEIGHT);
    setBackground(GuiUtil.getColor(card._1));

    JLabel numberLabel = new JLabel("" + card._2.score());

    add(numberLabel, BorderLayout.CENTER);
  }
}
