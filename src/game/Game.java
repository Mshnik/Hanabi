package game;

import game.Card.Color;
import game.Card.Number;

import java.util.*;

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

  private boolean deckEmpty = false;
  private int bonusTurns = 0;


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

  public List<Card> getDiscard() {
    return Collections.unmodifiableList(discard);
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
        if (board.get(c.color).inc() == c.number) {
          board.put(c.color, c.number);
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

  public boolean runTurn() {
    Player currentPlayer = players.remove(0);
    Turn turn = currentPlayer.doTurn(this);
    switch (turn.turnEnum) {
      case PLAY_CARD:
      case DISCARD_CARD:
        currentPlayer.removeCardFromHand(turn.card);
        playOrDiscardCard(turn.card, turn.turnEnum);
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
        turn.targetPlayer.receiveInformation(currentPlayer, turn.targetPlayer, turn.information);
        for(Player p : players) {
          if (p != currentPlayer && p != turn.targetPlayer) {
            p.receiveInformation(currentPlayer, turn.targetPlayer, turn.information);
          }
        }
        break;
    }
    if (isVictory() || fuseCounters == 0 || (deckEmpty && bonusTurns == 0)) {
      return true;
    }
    players.add(currentPlayer);
    return false;
  }
}
