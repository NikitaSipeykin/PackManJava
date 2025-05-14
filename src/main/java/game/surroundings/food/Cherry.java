package game.surroundings.food;

import game.main.GamePanel;
import game.surroundings.Block;

import javax.swing.*;

public class Cherry extends Block {

  public Cherry(GamePanel gamePanel, int x, int y) {
    super(gamePanel, x, y);
    this.score = 50;

    setImage();
  }

  @Override
  protected void setImage() {
    currentImage = new ImageIcon(getClass().getResource("/food/cherry.png")).getImage();
  }
}
