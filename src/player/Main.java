package player;

import game.Game;
import game.Player;

/**
 * @author Mshnik
 */
public class Main {

  public static void main(String[] args) {
    // Set players here.
    Player p1 = new RandomPlayer();
    Player p2 = new RandomPlayer();
    Player p3 = new RandomPlayer();
    Player p4 = new RandomPlayer();

    Game g = new Game(p1, p2, p3, p4).withGui();
  }
}
