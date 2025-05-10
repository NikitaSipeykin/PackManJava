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
  protected void update(){
    if(keyHandler.upPressed) {
      direction = 'U';
      currentImage = imageUp;
    } else if (keyHandler.downPressed) {
      direction = 'D';
      currentImage = imageDown;
    } else if (keyHandler.leftPressed) {
      direction = 'L';
      currentImage = imageLeft;
    } else if (keyHandler.rightPressed) {
      direction = 'R';
      currentImage = imageRight;
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

    for (Entity ghost : gamePanel.ghosts) {
      if (collision(ghost, this)){
        gamePanel.lives --;
        if (gamePanel.lives == 0){
          gamePanel.gameOver = true;
          return;
        }
        gamePanel.resetPosition();
      }
      if (ghost.getY() == tileSize * 9 && ghost.getDirection() != 'U' && ghost.getDirection() != 'D'){
        ghost.updateDirection('U');
      }
    }

    //todo: move it to pacman
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

  public boolean collision(Entity a, Entity b){
    return a.getX() < b.getX() + b.getWidth() &&
        a.getX() + a.getWidth() > b.getX() &&
        a.getY() < b.getY() + b.getHeight() &&
        a.getY() + a.getHeight() > b.getY();
  }

  public boolean collision(Entity a, Block b){
    return a.getX() < b.getX() + b.getWidth() &&
        a.getX() + a.getWidth() > b.getX() &&
        a.getY() < b.getY() + b.getHeight() &&
        a.getY() + a.getHeight() > b.getY();
  }

  public int randomDirection(){
    return random.nextInt(4);
  }
}
