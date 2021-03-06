package game;

import com.sun.tools.classfile.InnerClasses_attribute.Info;
import game.Deck.EmptyDeckException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public abstract class Player {

  private final List<Card> hand;
  private int id;
  private Game game;

  protected Player() {
    hand = new ArrayList<>();
  }

  protected final int getId() {
    return id;
  }

  void setId(int id) {
    this.id = id;
  }

  protected final Game getGame() {
    return game;
  }

  void setGame(Game g) {
    game = g;
  }

  List<Card> getHand() {
    return Collections.unmodifiableList(hand);
  }

  protected final List<Card> getHand(int playerId) {
    return game.getPlayerHand(this, playerId);
  }

  Card drawCard(Deck d) throws EmptyDeckException {
    Card c = d.drawTopCard();
    hand.add(c);
    return c;
  }

  void drawCards(Deck d, int cards) throws EmptyDeckException {
    for(int i = 0; i < cards; i++) {
      drawCard(d);
    }
  }

  abstract protected void receiveInformation(int sourcePlayerId, int targetPlayerId, Information information);

  final Card removeCardFromHand(int index) {
    return hand.remove(index);
  }

  abstract protected Turn doTurn(Game g);
}
