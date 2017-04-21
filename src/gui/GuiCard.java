package gui;

import game.Card;
import sun.awt.image.FileImageSource;
import sun.awt.image.PNGImageDecoder;

import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ImageConsumer;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * @author Mshnik
 */
class GuiCard extends JPanel {
//  private static final Map<Card, BufferedImage> imageMap;
//  private static final String IMAGE_DIRECTORY = "img/";
//  private static final int IMAGE_HEIGHT = 100;
//  private static final int IMAGE_WIDTH = 100;
//
//  static {
//    imageMap = new HashMap<>();
//    try {
//      for (Card c : Card.getAllCards()) {
//        FileImageSource imgSource = new FileImageSource(IMAGE_DIRECTORY + c._1.toString().toLowerCase() + "-" + c._2.score() + ".png");
//        imgSource.startProduction();
//      }
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//  }

  static final int WIDTH = 50;
  static final int HEIGHT= 50;

  private JLabel numberLabel;

  GuiCard(Card card) {
    setBounds(0, 0, WIDTH, HEIGHT);
    setBackground(GuiUtil.getColor(card._1));

    numberLabel = new JLabel("" + card._2.score());
    numberLabel.setForeground(GuiUtil.getTextColor(card._1));
    numberLabel.setPreferredSize(getBounds().getSize());

    add(numberLabel, BorderLayout.CENTER);
  }

  @Override
  public Dimension getMinimumSize() {
    return getBounds().getSize();
  }

  @Override
  public Dimension getPreferredSize() {
    return getBounds().getSize();
  }
}
