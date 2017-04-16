package game;

import game.Card.Color;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public final class Information {

  public final Color color;
  public final Card.Number number;

  private List<Integer> handIndices;

  public Information(Color color) {
    this.color = color;
    this.number = null;
  }

  public Information(Card.Number number) {
    this.number = number;
    this.color = null;
  }

  void setHandIndices(List<Integer> handIndices) {
    this.handIndices = handIndices;
  }

  public List<Integer> getHandIndices() {
    return Collections.unmodifiableList(handIndices);
  }
}
