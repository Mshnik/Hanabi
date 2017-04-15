package game;

/**
 *
 */
public final class Turn {

  enum TurnEnum {
    PLAY_CARD,
    DISCARD_CARD,
    TELL_INFORMATION
  }

  public final TurnEnum turnEnum;
  public final Card card;
  public final Player targetPlayer;
  public final Information information;

  private Turn(TurnEnum t, Card c, Player targetPlayer, Information i) {
    turnEnum = t;
    card = c;
    this.targetPlayer = targetPlayer;
    information = i;
  }

  public static Turn playCard(Card c) {
    return new Turn(TurnEnum.PLAY_CARD, c, null,null);
  }

  public static Turn discardCard(Card c) {
    return new Turn(TurnEnum.DISCARD_CARD, c, null,null);
  }

  public static Turn tellInformation(Player target, Information i) {
    return new Turn(TurnEnum.TELL_INFORMATION, null, target, i);
  }
}
