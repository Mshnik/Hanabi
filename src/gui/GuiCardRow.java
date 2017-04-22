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

  private static final int MARGIN = 10;

  private List<GuiCard> cards;
  private final int length;
  private final GuiCardRowDirection direction;
  private final Dimension preferredSize;

  GuiCardRow(int length, GuiCardRowDirection direction) {
    this.length = length;
    this.direction = direction;
    cards = new LinkedList<>();

    if (direction.isHorizontal()) {
      setComponentOrientation(direction == GuiCardRowDirection.LEFT_TO_RIGHT ?
          ComponentOrientation.LEFT_TO_RIGHT : ComponentOrientation.RIGHT_TO_LEFT);
    }
    setLayout(new BoxLayout(this, direction.isHorizontal() ? BoxLayout.LINE_AXIS : BoxLayout.PAGE_AXIS));
    if (direction.isHorizontal()) {
      preferredSize = new Dimension( (GuiCard.WIDTH + MARGIN) * length, GuiCard.HEIGHT + MARGIN);
    } else {
      preferredSize = new Dimension(GuiCard.WIDTH + MARGIN, (GuiCard.HEIGHT + MARGIN) * length);
    }

    setOpaque(false);
  }

  @Override
  public Dimension getMinimumSize() {
    return getPreferredSize();
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(preferredSize);
  }

  void addCard(Card c) {
    GuiCard g = new GuiCard(c);
    add(g);
    cards.add(g);
    if (cards.size() > length) {
      remove(cards.remove(0));
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
