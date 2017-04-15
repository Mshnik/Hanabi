package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 */
public final class Card {

  enum Color {
    BLUE,
    RED,
    WHITE,
    GREEN,
    YELLOW;
  }

  enum Number {
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE;

    public Number inc() {
      return Number.values()[ordinal() + 1];
    }
  }

  public final Color color;
  public final Number number;

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
    this.color = color;
    this.number = number;
  }

  public Card copy() {
    return new Card(color, number);
  }

  public String toString() {
    return color.toString() + ", " + number.toString();
  }

  public boolean equals(Object o) {
    if (! (o instanceof Card)) {
      return false;
    } else {
      Card c = (Card)o;
      return color == c.color && number == c.number;
    }
  }

  public int hashCode() {
    return Objects.hash(color, number);
  }
}
