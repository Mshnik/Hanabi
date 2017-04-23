package gui;

import game.Card;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Mshnik
 */
class GuiCardRow extends JPanel {

  private static final int MARGIN = 10;

  private List<GuiCard> cards;
  private int rows;
  private int cols;
  private final Dimension preferredSize;

  GuiCardRow(int rows, int cols) {
    this.rows = rows;
    this.cols = cols;
    cards = new LinkedList<>();

    //setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
    setLayout(new GridLayout(rows, cols));
    preferredSize = new Dimension( (GuiCard.WIDTH + MARGIN) * cols, (GuiCard.HEIGHT + MARGIN) * rows);

    setBorder(new BevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, Color.LIGHT_GRAY));
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

  @Override
  public Dimension getMaximumSize() {
    return new Dimension(preferredSize);
  }

  int getRows() {
    return rows;
  }

  int getCols() {
    return cols;
  }

  void addCard(Card c) {
    GuiCard g = new GuiCard(c);
    add(g);
    cards.add(g);
  }

  void addCard(Card c, int index) {
    for(GuiCard guiCard : cards) {
      remove(guiCard);
    }
    GuiCard g = new GuiCard(c);
    cards.add(index, g);
    for(GuiCard guiCard : cards) {
      add(guiCard);
    }
  }

  void removeCardAtIndex(int cardIndex) {
    GuiCard toRemove = cards.get(cardIndex);
    remove(toRemove);
    cards.remove(toRemove);
  }
}
