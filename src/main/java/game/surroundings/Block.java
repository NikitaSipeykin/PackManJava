package game.surroundings;

import game.main.GamePanel;

import java.awt.*;

public class Block {
  public GamePanel gamePanel;
  public final int tileSize = 32;

  public int x;
  public int y;
  public int width = tileSize;
  public int height = tileSize;

  public Image currentImage;

  public Block(GamePanel gamePanel, int x, int y) {
    this.gamePanel = gamePanel;
    this.x = x;
    this.y = y;
  }

  public Block(GamePanel gamePanel, int x, int y, int width, int height) {
    this.gamePanel = gamePanel;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  protected void setImage() {}
}
