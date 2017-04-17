package gui;

import game.Card;

import java.awt.*;

/**
 * @author Mshnik
 */
class GuiUtil {
  private GuiUtil() {}

  public static Color getColor(Card.Color color) {
    switch (color) {
      case BLUE:
        return Color.BLUE;
      case RED:
        return Color.RED;
      case WHITE:
        return Color.WHITE;
      case GREEN:
        return Color.GREEN;
      case YELLOW:
        return Color.YELLOW;
    }
    return Color.BLACK;
  }
}
