package gui;

import game.Card;
import game.TurnResult;

import javax.swing.*;
import java.awt.*;
import java.util.function.Supplier;

/**
 * @author Mshnik
 */
public class Gui extends JFrame {

  private BoardPanel boardPanel;
  private HandPanel handPanel;
  private JLabel deckLabel;
  private JLabel timeCountersLabel;
  private JLabel fuseCountersLabel;
  private GuiCardRow discardPanel;

  private JTextArea printArea;
  private boolean gameOver;

  public Gui(int playerCount, Supplier<TurnResult> turnAction) {
    boardPanel = new BoardPanel();
    handPanel = new HandPanel(playerCount);
    deckLabel = new JLabel();
    timeCountersLabel = new JLabel();
    fuseCountersLabel = new JLabel();
    discardPanel = new GuiCardRow(3,10);

    printArea = new JTextArea();
    printArea.setEditable(false);

    getContentPane().setLayout(new BorderLayout());

    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
    infoPanel.add(deckLabel);
    infoPanel.add(timeCountersLabel);
    infoPanel.add(fuseCountersLabel);

    JPanel bottomPanel = new JPanel();
    bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
    bottomPanel.add(discardPanel);
    bottomPanel.add(new JSeparator());
    JScrollPane printScrollPane = new JScrollPane();
    printScrollPane.add(printArea);
    bottomPanel.add(printScrollPane);

    JButton turnButton = new JButton("Take Turn");
    turnButton.addActionListener((e) -> {
      if (! gameOver) {
        TurnResult result = turnAction.get();
        if (result._7) {
          gameOver = true;
          turnButton.setEnabled(false);
          print("Game Over");
        }
      }
    });

    add(boardPanel, BorderLayout.NORTH);
    add(handPanel, BorderLayout.CENTER);
    add(infoPanel, BorderLayout.EAST);
    add(turnButton, BorderLayout.WEST);
    add(bottomPanel, BorderLayout.SOUTH);

    pack();
    setVisible(true);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }

  public void play(Card c) {
    boardPanel.play(c);
    pack();
  }

  public void addCard(int playerIndex, Card c) {
    handPanel.draw(playerIndex, c);
    pack();
  }

  public void removeCard(int playerIndex, int cardIndex) {
    handPanel.remove(playerIndex, cardIndex);
    pack();
  }

  public void setDeckSize(int deckSize) {
    deckLabel.setText("Deck: " + deckSize);
  }

  public void setTimeCounters(int timeCounters) {
    timeCountersLabel.setText("Time Ctrs: " + timeCounters);
  }

  public void setFuseCounters(int fuseCounters) {
    fuseCountersLabel.setText("Fuse Ctrs: " + fuseCounters);
  }

  public void discard(Card c) {
    discardPanel.addCard(c);
  }

  public void print(String s) {
    printArea.append(s);
    printArea.append("\n");
  }
}
