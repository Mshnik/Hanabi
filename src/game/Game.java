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
  private static final int MAX_TIME_COUNTERS = 8;
  private int timeCounters = MAX_TIME_COUNTERS;
  private int fuseCounters = 3;

  private boolean deckEmpty = false;
  private int bonusTurns = 0;

  public static int getStartingHandSizeForPlayerCount(int playerCount) {
    if (playerCount < 4) {
      return 5;
    } else {
      return 4;
    }
  }

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
        p.drawCards(deck, getStartingHandSizeForPlayerCount(players.length));
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

  private Player getPlayer(int id) {
    for (Player p : players) {
      if (p.getId() == id) {
        return p;
      }
    }
    throw new RuntimeException("Illegal Player ID " + id + ", no such player.");
  }

  List<Card> getPlayerHand(Player requestingPlayer, int targetId) {
    if (requestingPlayer.getId() == targetId) {
      throw new RuntimeException("Illegal play - Can't get your own hand");
    }
    return getPlayer(targetId).getHand();
  }

  private void checkGiveInformationAndSetHandIndices(Information information, Player targetPlayer) {
    if (timeCounters == 0) {
      throw new RuntimeException("Illegal play - Can't give info without time counter");
    }
    timeCounters--;

    List<Integer> handIndices = new ArrayList<>();
    for (int i = 0; i < targetPlayer.getHand().size(); i++) {
      Card c = targetPlayer.getHand().get(i);
      if (information.color == c._1 || information.number == c._2) {
        handIndices.add(i);
      }
    }
    if (handIndices.size() == 0) {
      throw new RuntimeException("Illegal play - can't give info with no applicable cards");
    }
    information.setHandIndices(Collections.unmodifiableList(handIndices));
  }

  private boolean playOrDiscardCard(Card c, Turn.TurnEnum turnEnum) {
    switch (turnEnum) {
      case PLAY_CARD:
        Number currentNumberForColor = board.get(c._1);
        if (currentNumberForColor == null && c._2 == Number.ONE ||
            currentNumberForColor != null && currentNumberForColor.inc() == c._2) {
          board.put(c._1, c._2);
          return true;
        } else {
          discard.add(c);
          fuseCounters--;
          return false;
        }
      case DISCARD_CARD:
        discard.add(c);
        timeCounters = Math.min(timeCounters + 1, MAX_TIME_COUNTERS);
        return false;
      case TELL_INFORMATION:
        throw new RuntimeException();
    }
    return false;
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
    } while(! result._7);
  }

  TurnResult runTurn() {
    Player currentPlayer = players.remove(0);
    Turn turn = currentPlayer.doTurn(this);


    Card c = null;
    boolean hit = false;

    switch (turn.turnEnum) {
      case PLAY_CARD:
      case DISCARD_CARD:
        c = currentPlayer.removeCardFromHand(turn.index);
        hit = playOrDiscardCard(c, turn.turnEnum);
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
        checkGiveInformationAndSetHandIndices(turn.information, turn.targetPlayer);
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
    int targetPlayerId = turn.targetPlayer != null ? turn.targetPlayer.getId() : -1;
    TurnResult t = new TurnResult(this, turn.turnEnum, turn.index, c, hit, turn.information, targetPlayerId, gameOver);
    playHistory.add(t);
    return t;
  }
}
