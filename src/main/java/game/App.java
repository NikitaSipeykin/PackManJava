package game;

import game.character.PacMan;

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

    PacMan pacMan = new PacMan();
    frame.add(pacMan);
    frame.pack();
    pacMan.requestFocus();
    frame.setVisible(true);
  }
}
