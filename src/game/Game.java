package game;

import game.Card.Color;
import game.Card.Number;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public final class Game {

  private Map<Color, Number> board;
  private Deck deck;
  private List<Card> discard;

  private List<Player> players;
  private int timeCounters = 8;
  private int fuseCounters = 3;


  public Game(Player... players) {
    board = new HashMap<>();
    for(Color c: Color.values()) {
      board.put(c, null);
    }
    deck = new Deck();
    discard = new ArrayList<>();

    this.players = new LinkedList<>();
    for(Player p : players) {
      this.players.add(p);
      if (this.players.size() < 4) {
        p.drawCards(deck, 5);
      } else {
        p.drawCards(deck, 4);
      }
    }
  }

  public int getTimeCounters() {
    return timeCounters;
  }

  public int getFuseCounters() {
    return fuseCounters;
  }

  Deck getDeck() {
    return deck;
  }

  void checkGiveInformation() {
    if (timeCounters == 0) {
      throw new RuntimeException("Can't give info without time counter");
    }
    timeCounters--;
  }

  void discardCard(Card c) {
    discard.add(c);
    timeCounters++;
  }

  void playCard(Card c) {
    if (board.get(c.color).inc() == c.number) {
      board.put(c.color, c.number);
    } else {
      fuseCounters--;
      if (fuseCounters == 0) {
        throw new RuntimeException("YOU LOSE HAHAHAHA");
      }
    }
  }

  public boolean isVictory() {
    for (Color c : Color.values()) {
      if (board.get(c) != Number.FIVE) {
        return false;
      }
    }
    return true;
  }


  public void runTurn() {
    Player currentPlayer = players.remove(0);
    currentPlayer.doTurn(this);
    players.add(currentPlayer);
  }
}
