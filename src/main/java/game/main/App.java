package game.main;

import game.main.GamePanel;

import javax.swing.*;

public class App {
  public static void main(String[] args) {
    int rowCount = 21;
    int colCount = 19;
    int tileSize = 32;
    int borderWidth = colCount * tileSize;
    int borderHeight = rowCount * tileSize;

    JFrame frame = new JFrame("Pack Man");
    frame.setSize(borderWidth, borderHeight);
    frame.setLocationRelativeTo(null);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    GamePanel gamePanel = new GamePanel();
    frame.add(gamePanel);
    frame.pack();
    gamePanel.requestFocus();
    frame.setVisible(true);
  }
}
