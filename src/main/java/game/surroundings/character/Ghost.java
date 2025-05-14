package game.surroundings.character;

import game.main.GamePanel;
import game.surroundings.Block;
import game.surroundings.Entity;

import javax.swing.*;

public class Ghost extends Entity {

  public Ghost(GamePanel gamePanel, int x, int y, String color) {
    super(gamePanel, x, y);

    setColor(color);
  }

  private void setColor(String color){
    switch (color) {
      case "red" -> currentImage = new ImageIcon(getClass().getResource("/enemy/redGhost.png")).getImage();
      case "orange" -> currentImage = new ImageIcon(getClass().getResource("/enemy/orangeGhost.png")).getImage();
      case "pink" -> currentImage = new ImageIcon(getClass().getResource("/enemy/pinkGhost.png")).getImage();
      case "blue" -> currentImage = new ImageIcon(getClass().getResource("/enemy/blueGhost.png")).getImage();
    }
  }

  @Override
  public void move(){
    x += velocityX;
    y += velocityY;

    if (y == gamePanel.tileSize * 9 && direction != 'U' && direction != 'D'){
      this.updateDirection('U');
    }

    for (Block wall : gamePanel.walls) {
      if (collision(this, wall) || this.getX() <= 0 || this.getX() + this.getWidth() >= gamePanel.borderWidth) {
        x -= velocityX;
        y -= velocityY;
        char newDirection = this.directions[random.nextInt(4)];
        this.updateDirection(newDirection);
        break;
      }
    }
  }
}
