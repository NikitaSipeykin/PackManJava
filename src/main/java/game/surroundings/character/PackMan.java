package game.surroundings.character;

import game.main.GamePanel;
import game.main.KeyHandler;
import game.surroundings.Block;
import game.surroundings.Entity;

import javax.swing.*;

public class PackMan extends Entity {
  KeyHandler keyHandler;

  public PackMan(GamePanel gamePanel, int x, int y, KeyHandler keyHandler) {
    super(gamePanel, x, y);
    this.keyHandler = keyHandler;

    this.x = x;
    this.y = y;
    this.startX = x;
    this.startY = y;

    setImage();
    currentImage = imageRight;
  }

  @Override
  protected void setImage(){
    imageUp = new ImageIcon(getClass().getResource("/character/pacmanUp.png")).getImage();
    imageDown = new ImageIcon(getClass().getResource("/character/pacmanDown.png")).getImage();
    imageLeft = new ImageIcon(getClass().getResource("/character/pacmanLeft.png")).getImage();
    imageRight = new ImageIcon(getClass().getResource("/character/pacmanRight.png")).getImage();
  }

  @Override
  public void update(){
    if(keyHandler.upPressed) {
      direction = 'U';
      currentImage = imageUp;
      updateDirection(direction);
    } else if (keyHandler.downPressed) {
      direction = 'D';
      currentImage = imageDown;
      updateDirection(direction);
    } else if (keyHandler.leftPressed) {
      direction = 'L';
      currentImage = imageLeft;
      updateDirection(direction);
    } else if (keyHandler.rightPressed) {
      direction = 'R';
      currentImage = imageRight;
      updateDirection(direction);
    }
  }

  @Override
  public void move(){
    x += velocityX;
    y += velocityY;

    for (Block wall : gamePanel.walls) {
      if (collision(this, wall) || this.getX() <= 0 || this.getX() + this.getWidth() >= gamePanel.borderWidth) {
        x -= velocityX;
        y -= velocityY;
        break;
      }
    }

    for (Entity ghost : gamePanel.ghosts) {
      if (collision(ghost, this)){
        gamePanel.lives --;
        if (gamePanel.lives == 0){
          gamePanel.gameOver = true;
          return;
        }
        gamePanel.resetPosition();
      }
    }

    Block foodEaten = null;
    for (Block food : gamePanel.foods) {
      if (collision(this, food)) {
        foodEaten = food;
        gamePanel.score += 10;
      }
    }
    gamePanel.foods.remove(foodEaten);

    if (gamePanel.foods.isEmpty()){
      gamePanel.loadMap();
      gamePanel.resetPosition();
    }
  }

  public int randomDirection(){
    return random.nextInt(4);
  }
}
