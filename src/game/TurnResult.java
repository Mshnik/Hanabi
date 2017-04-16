package game;


import common.types.Tuple7;

/**
 * @author Mshnik
 */
public class TurnResult extends Tuple7<Turn.TurnEnum, Integer, Card, Boolean, Information, Integer, Boolean> {

  private final Game g;

  TurnResult(Game g, Turn.TurnEnum turnEnum, int handIndex, Card card, Boolean hit, Information information, int targetPlayer, boolean gameOver) {
    super(turnEnum, handIndex, card, hit, information, targetPlayer, gameOver);
    this.g = g;
  }

  public String toString() {
    String s = "";
    switch (_1) {
      case PLAY_CARD:
        s = "Play Card " + _2 + "=" + _3 + " " + (_4 ? "HIT" : ("MISS, " + g.getFuseCounters() + " fuse counters remaining"));
        break;
      case DISCARD_CARD:
        s = "Discard Card " + _2 + "=" + _3 + ", " + g.getTimeCounters() + " time counters remaining";
        break;
      case TELL_INFORMATION:
        s = "Tell Information " + _5 + " to player " + _6 + ", " + g.getTimeCounters() + " time counters remaining";
        break;
    }
    return s + (_7 ? ", Game Over." : "");
  }
}
