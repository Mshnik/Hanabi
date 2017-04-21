package gui;

import game.Card;

import java.awt.*;

/**
 * @author Mshnik
 */
class GuiUtil {
  private GuiUtil() {}

  static Color getColor(Card.Color color) {
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

  static Color getTextColor(Card.Color color) {
    switch (color) {
      case BLUE:
      case GREEN:
      case RED:
        return Color.WHITE;
      case WHITE:
      case YELLOW:
        return Color.BLACK;
    }
    return Color.BLACK;
  }
}
