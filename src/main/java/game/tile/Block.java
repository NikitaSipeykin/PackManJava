package game.tile;

import game.character.PacMan;

import java.awt.*;

public class Block {
  private final PacMan pacMan;

  private int x;
  private int y;
  private final int width;
  private final int height;

  private Image image;

  int startX;
  int startY;
  char direction = 'U';
  private int velocityX = 0;
  private int velocityY = 0;

  public Block( Image image, int x, int y, int width, int height, PacMan pacMan) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.image = image;
    this.pacMan = pacMan;
    this.startX = x;
    this.startY = y;
  }

  public void updateDirection(char direction){
    char previousDirection = this.direction;
    this.direction = direction;
    updateVelocity();
    this.x += this.velocityX;
    this.y += this.velocityY;
    for (Block wall :
        pacMan.walls) {
      if (pacMan.collision(this, wall)){
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
      this.velocityY = - pacMan.tileSize / 4;
    } else if (this.direction == 'D') {
      this.velocityX = 0;
      this.velocityY = pacMan.tileSize / 4;
    } else if (this.direction == 'L') {
      this.velocityX = - pacMan.tileSize / 4;
      this.velocityY = 0;
    } else if (this.direction == 'R') {
      this.velocityX = pacMan.tileSize / 4;
      this.velocityY = 0;
    }
  }

  public void reset(){
    this.x = this.startX;
    this.y = this.startY;
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

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public Image getImage() {
    return image;
  }

  public void setImage(Image image) {
    this.image = image;
  }

  public char getDirection() {
    return direction;
  }
}
