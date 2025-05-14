package game.surroundings;

import game.main.GamePanel;

import java.awt.*;
import java.util.Random;

public class Entity extends Block{
  protected int startX;
  protected int startY;
  protected char direction = 'U';
  protected char failedDirection;
  protected int velocityX = 0;
  protected int velocityY = 0;

  protected Random random = new Random();
  protected Image imageUp, imageDown, imageLeft, imageRight;
  protected boolean canTurn = false;

  public char[] directions = {'U', 'D', 'L', 'R'};

  public Entity(GamePanel gamePanel, int x, int y) {
    super(gamePanel, x, y);

    this.x = x;
    this.y = y;
    this.startX = x;
    this.startY = y;
  }

  public void updateDirection(char direction){
    char previousDirection = this.direction;
    this.direction = direction;

    canTurn = true;
    changeSprite(this.direction);
    updateVelocity();
    this.x += this.velocityX;
    this.y += this.velocityY;

    for (Block wall : gamePanel.walls) {
      if (collision(this, wall)){
        this.x -= this.velocityX;
        this.y -= this.velocityY;

        canTurn = false;
        failedDirection = this.direction;
        this.direction = previousDirection;
        changeSprite(this.direction);
        updateVelocity();
      }
    }
  }

  private void updateVelocity() {
    if (this.direction == 'U'){
      this.velocityX = 0;
      this.velocityY = - gamePanel.tileSize / 4;
    } else if (this.direction == 'D') {
      this.velocityX = 0;
      this.velocityY = gamePanel.tileSize / 4;
    } else if (this.direction == 'L') {
      this.velocityX = - gamePanel.tileSize / 4;
      this.velocityY = 0;
    } else if (this.direction == 'R') {
      this.velocityX = gamePanel.tileSize / 4;
      this.velocityY = 0;
    }
  }

  public void reset(){
    this.x = this.startX;
    this.y = this.startY;
  }

  public boolean collision(Entity a, Block b){
    return a.getX() < b.getX() + b.getWidth() &&
        a.getX() + a.getWidth() > b.getX() &&
        a.getY() < b.getY() + b.getHeight() &&
        a.getY() + a.getHeight() > b.getY();
  }

  public void setVelocityX(int velocityX) {
    this.velocityX = velocityX;
  }

  public void setVelocityY(int velocityY) {
    this.velocityY = velocityY;
  }

  public void update(){}

  public void move(){}

  protected void changeSprite(char direction) {}

  public int randomDirection(){
    return random.nextInt(4);
  }

  public void setDefaultImage(){}
}
