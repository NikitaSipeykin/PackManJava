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
    if (color.equals("red")){
      currentImage = new ImageIcon(getClass().getResource("/enemy/redGhost.png")).getImage();
    } else if (color.equals("orange")) {
      currentImage = new ImageIcon(getClass().getResource("/enemy/orangeGhost.png")).getImage();
    } else if (color.equals("pink")) {
      currentImage = new ImageIcon(getClass().getResource("/enemy/pinkGhost.png")).getImage();
    } else if (color.equals("blue")){
      currentImage = new ImageIcon(getClass().getResource("/enemy/blueGhost.png")).getImage();
    }
  }

  @Override
  public void move(){
    x += velocityX;
    y += velocityY;

    if (y == tileSize * 9 && direction != 'U' && direction != 'D'){
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

  public int randomDirection(){
    return random.nextInt(4);
  }
}
