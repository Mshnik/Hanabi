package gui;

import game.Card;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mshnik
 */
class GuiCard extends JPanel {
  private static final Map<Card, ImageIcon> imageMap;
  private static final String IMAGE_DIRECTORY = "img/";
  static final int WIDTH = 100;
  static final int HEIGHT= 100;

  static {
    imageMap = new HashMap<>();
    for (Card c : Card.getAllCards()) {
      imageMap.put(c,
          new ImageIcon(IMAGE_DIRECTORY + c._1.toString().toLowerCase() + "-" + c._2.score() + ".png"));
    }
  }

  private Card card;

  GuiCard(Card card) {
    this.card = card;
    JLabel imgLabel = new JLabel();
    imgLabel.setIcon(imageMap.get(card));
    add(imgLabel);
    setOpaque(false);
  }

  Card getCard() {
    return card;
  }
}
