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
  public final int index;
  public final Player targetPlayer;
  public final Information information;

  private Turn(TurnEnum t, int index, Player targetPlayer, Information i) {
    turnEnum = t;
    this.index = index;
    this.targetPlayer = targetPlayer;
    information = i;
  }

  public static Turn playCard(int index) {
    return new Turn(TurnEnum.PLAY_CARD, index, null,null);
  }

  public static Turn discardCard(int index) {
    return new Turn(TurnEnum.DISCARD_CARD, index, null,null);
  }

  public static Turn tellInformation(Player target, Information i) {
    return new Turn(TurnEnum.TELL_INFORMATION, -1, target, i);
  }
}
