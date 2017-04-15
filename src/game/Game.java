package game;

import game.Card.Color;
import game.Card.Number;

import java.util.*;

/**
 *
 */
public final class Game {

  private Map<Color, Number> board;
  private List<TurnResult> playHistory;
  private Deck deck;
  private List<Card> discard;

  private List<Player> players;
  private int timeCounters = 8;
  private int fuseCounters = 3;

  private boolean deckEmpty = false;
  private int bonusTurns = 0;


  public Game(Player... players) {
    playHistory = new ArrayList<>();
    board = new HashMap<>();
    for(Color c: Color.values()) {
      board.put(c, null);
    }
    deck = new Deck();
    discard = new ArrayList<>();

    this.players = new LinkedList<>();
    int id = 0;
    for(Player p : players) {
      this.players.add(p);
      p.setId(id++);
      p.setGame(this);
      try {
        if (this.players.size() < 4) {
          p.drawCards(deck, 5);
        } else {
          p.drawCards(deck, 4);
        }
      } catch(Deck.EmptyDeckException e) {}
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

  public int getDeckSize() {
    return deck.size();
  }

  public List<Card> getDiscard() {
    return Collections.unmodifiableList(discard);
  }

  public List<TurnResult> getPlayHistory() {
    return Collections.unmodifiableList(playHistory);
  }

  List<Card> getPlayerHand(Player requestingPlayer, int targetId) {
    if (requestingPlayer.getId() == targetId) {
      throw new RuntimeException("Can't get your own hand");
    }
    for (Player p : players) {
      if (p.getId() == targetId) {
        return p.getHand();
      }
    }
    throw new RuntimeException("Illegal Player ID " + targetId + ", no such player.");
  }

  private void checkGiveInformation() {
    if (timeCounters == 0) {
      throw new RuntimeException("Can't give info without time counter");
    }
    timeCounters--;
  }

  private void playOrDiscardCard(Card c, Turn.TurnEnum turnEnum) {
    switch (turnEnum) {
      case PLAY_CARD:
        Number currentNumberForColor = board.get(c._1);
        if (currentNumberForColor == null && c._2 == Number.ONE ||
            currentNumberForColor != null && currentNumberForColor.inc() == c._2) {
          board.put(c._1, c._2);
        } else {
          discard.add(c);
          fuseCounters--;
        }
        break;
      case DISCARD_CARD:
        discard.add(c);
        timeCounters++;
        break;
      case TELL_INFORMATION:
        throw new RuntimeException();
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

  public int score() {
    int score = 0;
    for (Number n : board.values()) {
      if (n != null) {
        score += n.score();
      }
    }
    return score;
  }

  public void runToCompletion(boolean print) {
    TurnResult result;
    do {
      result = runTurn();
      if (print) {
        System.out.println(result);
      }
    } while(! result._5);
  }

  TurnResult runTurn() {
    Player currentPlayer = players.remove(0);
    Turn turn = currentPlayer.doTurn(this);
    Card c = null;
    switch (turn.turnEnum) {
      case PLAY_CARD:
      case DISCARD_CARD:
        c = currentPlayer.removeCardFromHand(turn.index);
        playOrDiscardCard(c, turn.turnEnum);
        try {
          currentPlayer.drawCard(deck);
        } catch (Deck.EmptyDeckException e) {
          if (! deckEmpty) {
            deckEmpty = true;
            bonusTurns = players.size() + 1;
          }
          bonusTurns--;
        }
        break;
      case TELL_INFORMATION:
        checkGiveInformation();
        turn.targetPlayer.receiveInformation(currentPlayer.getId(), turn.targetPlayer.getId(), turn.information);
        for(Player p : players) {
          if (p != currentPlayer && p != turn.targetPlayer) {
            p.receiveInformation(currentPlayer.getId(), turn.targetPlayer.getId(), turn.information);
          }
        }
        break;
    }
    boolean gameOver = isVictory() || fuseCounters == 0 || (deckEmpty && bonusTurns == 0);
    players.add(currentPlayer);
    TurnResult t = new TurnResult(turn.turnEnum, turn.index, c, turn.information, gameOver);
    playHistory.add(t);
    return t;
  }
}
