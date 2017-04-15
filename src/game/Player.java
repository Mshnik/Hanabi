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

  private List<Card> hand;
  private Game game;

  protected Player() {
    hand = new ArrayList<>();
  }

  protected Game getGame() {
    return game;
  }

  void setGame(Game g) {
    game = g;
  }

  public List<Card> getHand() {
    return Collections.unmodifiableList(hand);
  }

  void drawCard(Deck d) throws EmptyDeckException {
      hand.add(d.drawTopCard());
  }

  void drawCards(Deck d, int cards) throws EmptyDeckException {
    for(int i = 0; i < cards; i++) {
      drawCard(d);
    }
  }

  abstract void receiveInformation(Player source, Player target, Information information);

  final void removeCardFromHand(Card c) {
    if (hand.contains(c)) {
      hand.remove(c);
    } else {
      throw new RuntimeException("Can't remove " + c + " don't have it.");
    }
  }

  abstract protected Turn doTurn(Game g);
}
