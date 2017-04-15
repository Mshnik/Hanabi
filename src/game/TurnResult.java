package game;

import common.types.Tuple5;

/**
 * @author Mshnik
 */
public class TurnResult extends Tuple5<Turn.TurnEnum, Integer, Card, Information, Boolean> {

  TurnResult(Turn.TurnEnum turnEnum, int handIndex, Card card, Information information, boolean gameOver) {
    super(turnEnum, handIndex, card, information, gameOver);
  }
}
