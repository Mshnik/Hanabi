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

  protected Player() {
    hand = new ArrayList<>();
  }

  public List<Card> getHand() {
    return Collections.unmodifiableList(hand);
  }

  void drawCard(Deck d) {
    try {
      hand.add(d.drawTopCard());
    } catch (EmptyDeckException e) {}
  }

  void drawCards(Deck d, int cards) {
    for(int i = 0; i < cards; i++) {
      drawCard(d);
    }
  }

  abstract void getInformation(Player source, Player target, Information information);

  protected final void giveInformation(Game g, Player targetPlayer, Information information, Player... otherPlayers) {
    g.checkGiveInformation();
    targetPlayer.getInformation(this, targetPlayer, information);
    for(Player p : otherPlayers) {
      p.getInformation(this, targetPlayer, information);
    }
  }

  protected final void playCard(Game g, Card c) {
    if (hand.contains(c)) {
      hand.remove(c);
      g.playCard(c);
      drawCard(g.getDeck());
    } else {
      throw new RuntimeException("Can't play " + c + " don't have it.");
    }
  }

  protected final void discardCard(Game g, Card c) {
    if (hand.contains(c)) {
      hand.remove(c);
      g.discardCard(c);
      drawCard(g.getDeck());
    } else {
      throw new RuntimeException("Can't play " + c + " don't have it.");
    }
  }

  abstract protected void doTurn(Game g);
}
