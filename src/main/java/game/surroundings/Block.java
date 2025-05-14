package game.surroundings;

import game.main.GamePanel;

import java.awt.*;

public class Block {
  protected GamePanel gamePanel;
  protected final int tileSize = 32;

  protected int x;
  protected int y;
  protected int width = tileSize;
  protected int height = tileSize;

  protected Image currentImage;

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

  public Image getCurrentImage() {
    return currentImage;
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
