package game.surroundings;

import game.main.GamePanel;

import java.awt.*;
import java.util.Random;

public class Entity extends Block{
  protected int startX;
  protected int startY;
  protected char direction = 'U';
  protected int velocityX = 0;
  protected int velocityY = 0;

  public Random random = new Random();

  public Image imageUp, imageDown, imageLeft, imageRight;
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
    updateVelocity();
    this.x += this.velocityX;
    this.y += this.velocityY;
    for (Block wall : gamePanel.walls) {
      if (collision(this, wall)){
        this.x -= this.velocityX;
        this.y -= this.velocityY;

        this.direction = previousDirection;
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

  public int getVelocityX() {
    return velocityX;
  }

  public void setVelocityX(int velocityX) {
    this.velocityX = velocityX;
  }

  public int getVelocityY() {
    return velocityY;
  }

  public void setVelocityY(int velocityY) {
    this.velocityY = velocityY;
  }

  public char getDirection() {
    return direction;
  }

  public void update(){}

  public void move(){}

  public int randomDirection(){
    return random.nextInt(4);
  }
}
