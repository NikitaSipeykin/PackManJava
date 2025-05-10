package game.surroundings.tile;

import game.main.GamePanel;
import game.surroundings.Block;

import javax.swing.*;
import java.awt.*;

public class Wall extends Block {
  public Wall(GamePanel gamePanel, int x, int y) {
    super(gamePanel, x, y);

    setImage();
  }

  @Override
  public void setImage() {
    currentImage = new ImageIcon(getClass().getResource("/tile/wall.png")).getImage();
  }
}
