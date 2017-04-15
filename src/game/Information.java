package game;

import game.Card.Color;
import java.util.Arrays;

/**
 *
 */
public final class Information {

  private int[] handIndices;
  public final Color color;
  public final Number number;

  public Information(Color color, int... handIndices) {
    this.color = color;
    this.number = null;
    this.handIndices = Arrays.copyOf(handIndices, handIndices.length);
  }

  public Information(Number number, int... handIndices) {
    this.number = number;
    this.color = null;
    this.handIndices = Arrays.copyOf(handIndices, handIndices.length);
  }

  public int[] getHandIndices() {
    return Arrays.copyOf(handIndices, handIndices.length);
  }
}
