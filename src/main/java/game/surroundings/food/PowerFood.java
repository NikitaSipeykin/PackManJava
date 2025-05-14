package game.surroundings.food;

import game.main.GamePanel;
import game.surroundings.Block;

import javax.swing.*;

public class PowerFood extends Block {
  public PowerFood(GamePanel gamePanel, int x, int y) {
    super(gamePanel, x, y);
    this.isPowerFood = true;
    this.score = 0;

    setImage();
  }

  @Override
  protected void setImage() {
    currentImage = new ImageIcon(getClass().getResource("/food/powerFood.png")).getImage();
  }
}
