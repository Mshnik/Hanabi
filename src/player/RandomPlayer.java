package player;

import game.Game;
import game.Information;
import game.Player;
import game.Turn;

/**
 * @author Mshnik
 */
public final class RandomPlayer extends Player{

  @Override
  protected void receiveInformation(int source, int target, Information information) {
  }

  @Override
  protected Turn doTurn(Game g) {
    return Turn.playCard(0);
  }
}
