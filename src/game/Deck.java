package game;

import game.Card.Number;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 */
final class Deck {

  private static Map<Number, Integer> START_COUNT_BY_NUMBER;

  static {
    START_COUNT_BY_NUMBER = new HashMap<>();
    START_COUNT_BY_NUMBER.put(Number.ONE, 3);
    START_COUNT_BY_NUMBER.put(Number.TWO, 2);
    START_COUNT_BY_NUMBER.put(Number.THREE, 2);
    START_COUNT_BY_NUMBER.put(Number.FOUR, 2);
    START_COUNT_BY_NUMBER.put(Number.FIVE, 1);
  }

  private List<Card> cards;

  Deck() {
    cards = new LinkedList<>();
    for (Card c : Card.getAllCards()) {
      for (int i = 0; i < START_COUNT_BY_NUMBER.get(c._2); i++) {
        cards.add(c.copy());
      }
    }

    Collections.shuffle(cards);
  }

  int size() {
    return cards.size();
  }

  Card drawTopCard() throws EmptyDeckException {
    if (cards.isEmpty()) {
      throw new EmptyDeckException();
    }
    return cards.remove(0);
  }


  static class EmptyDeckException extends Exception {}
}
