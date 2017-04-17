package game;

import common.types.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 */
public final class Card extends Tuple2<Card.Color, Card.Number> {

  public enum Color {
    BLUE,
    RED,
    WHITE,
    GREEN,
    YELLOW;
  }

  public enum Number {
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE;

    public Number inc() {
      return Number.values()[ordinal() + 1];
    }

    public int score() {
      return ordinal() + 1;
    }
  }

  public static List<Card> getAllCards() {
    List<Card> cards = new ArrayList<>();
    for (Color c : Color.values()) {
      for (Number n : Number.values()) {
        cards.add(new Card(c, n));
      }
    }
    return cards;
  }

  public Card(Color color, Number number) {
    super(color, number);
  }

  public Card copy() {
    return new Card(_1, _2);
  }
}
