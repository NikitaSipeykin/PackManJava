package game.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
  GamePanel gp;
  public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed, spacePressed;

  public KeyHandler(GamePanel gp) {
    this.gp = gp;
  }

  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (gp.gameOver){
      gp.loadMap();
      gp.resetPosition();
      gp.lives = 3;
      gp.score = 0;
      gp.gameOver = false;
      gp.gameLoop.start();
    }
    if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W){
      upPressed = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
      downPressed = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
      leftPressed = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
      rightPressed = true;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (gp.gameOver){
      gp.loadMap();
      gp.resetPosition();
      gp.lives = 3;
      gp.score = 0;
      gp.gameOver = false;
      gp.gameLoop.start();
    }
    if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W){
      upPressed = false;
    } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
      downPressed = false;
    } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
      leftPressed = false;
    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
      leftPressed = false;
    }
  }
}
