package gui;

import game.Card;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Mshnik
 */
class GuiCardRow extends JPanel {

  private static final int MARGIN = 8;

  private List<GuiCard> cards;
  private final int length;
  private final GuiCardRowDirection direction;

  GuiCardRow(int length, GuiCardRowDirection direction) {
    this.length = length;
    this.direction = direction;
    cards = new LinkedList<>();

    setLayout(null);
    if (direction.isHorizontal()) {
      setBounds(0, 0, (GuiCard.WIDTH + MARGIN) * length, GuiCard.HEIGHT + MARGIN);
    } else {
      setBounds(0, 0, GuiCard.WIDTH + MARGIN, (GuiCard.HEIGHT + MARGIN) * length);
    }

    setBackground(Color.DARK_GRAY);
  }

  @Override
  public Dimension getMinimumSize() {
    return getBounds().getSize();
  }

  @Override
  public Dimension getPreferredSize() {
    return getBounds().getSize();
  }

  void addCard(Card c) {
    GuiCard g = new GuiCard(c);
    cards.add(g);
    add(g);
    if (cards.size() > length) {
      remove(cards.remove(0));
    }

    int x = MARGIN/2;
    int y = MARGIN/2;
    int xInc = 0;
    int yInc = 0;
    switch (direction) {
      case LEFT_TO_RIGHT:
        xInc = GuiCard.WIDTH + MARGIN;
        break;
      case TOP_TO_BOTTOM:
        yInc = GuiCard.HEIGHT + MARGIN;
        break;
      case RIGHT_TO_LEFT:
        x = GuiCard.WIDTH * (length - 1) + MARGIN/2;
        xInc = -(GuiCard.WIDTH + MARGIN);
        break;
      case BOTTOM_TO_TOP:
        y = GuiCard.HEIGHT * (length - 1) + MARGIN/2;
        yInc = -(GuiCard.HEIGHT + MARGIN);
        break;
    }

    for (GuiCard guiCard : cards) {
      guiCard.setLocation(x, y);
      x += xInc;
      y += yInc;
    }
  }

  enum GuiCardRowDirection {
    LEFT_TO_RIGHT,
    TOP_TO_BOTTOM,
    RIGHT_TO_LEFT,
    BOTTOM_TO_TOP;

    boolean isHorizontal() {
      switch (this) {
        case LEFT_TO_RIGHT:
        case RIGHT_TO_LEFT:
          return true;
        case TOP_TO_BOTTOM:
        case BOTTOM_TO_TOP:
          return false;
        default:
          throw new UnsupportedOperationException();
      }
    }
  }
}
