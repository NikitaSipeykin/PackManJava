package game.surroundings.character;

import game.main.GamePanel;
import game.main.KeyHandler;
import game.surroundings.Block;
import game.surroundings.Entity;

import javax.swing.*;
import java.util.HashSet;

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
    char newDirection;
    if(keyHandler.upPressed) {
      newDirection = 'U';
      updateDirection(newDirection);
    } else if (keyHandler.downPressed) {
      newDirection = 'D';
      updateDirection(newDirection);
    } else if (keyHandler.leftPressed) {
      newDirection = 'L';
      updateDirection(newDirection);
    } else if (keyHandler.rightPressed) {
      newDirection = 'R';
      updateDirection(newDirection);
    }
  }

  @Override
  protected void changeSprite(char direction) {
    switch (direction){
      case 'U' ->       currentImage = imageUp;
      case 'D' ->       currentImage = imageDown;
      case 'L' ->       currentImage = imageLeft;
      case 'R' ->       currentImage = imageRight;
    }
  }

  @Override
  public void move(){
    x += velocityX;
    y += velocityY;

    if (!canTurn){
      updateDirection(failedDirection);
    }

    for (Block wall : gamePanel.walls) {
      if (collision(this, wall) ) {
        x -= velocityX;
        y -= velocityY;
        break;
      } else if ( this.getX() <= 0 && direction == 'L') {
        x = gamePanel.borderWidth;
      } else if (this.getX() + this.getWidth() >= gamePanel.borderWidth && direction == 'R') {
        x = 0;
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

    eatTheFood(gamePanel.foods);
    eatTheFood(gamePanel.cherrys);

    if (gamePanel.foods.isEmpty()&& gamePanel.cherrys.isEmpty()){
      gamePanel.loadMap();
      gamePanel.resetPosition();
    }
  }

  private void eatTheFood(HashSet<Block> foodToCheck) {
    Block foodEaten = null;
    for (Block food : foodToCheck) {
      if (collision(this, food)) {
        foodEaten = food;
        gamePanel.score += food.getScore();
      }
    }
    foodToCheck.remove(foodEaten);
  }
}
