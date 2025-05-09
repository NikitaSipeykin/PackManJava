package game.character;

import game.tile.Block;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Random;

public class PacMan extends JPanel implements ActionListener, KeyListener {
  private final int rowCount = 21;
  private final int colCount = 19;
  public final int tileSize = 32;
  private final int borderWidth = colCount * tileSize;
  private final int borderHeight = rowCount * tileSize;

  private final Image wallImage;
  private final Image blueGhostImage;
  private final Image orangeGhostImage;
  private final Image pinkGhostImage;
  private final Image redGhostImage;

  private final Image pacManUpImage;
  private final Image pacManDownImage;
  private final Image pacManLeftImage;
  private final Image pacManRightImage;

  //X = wall, O = skip, P = pac man, ' ' = food
  //Ghosts: b = blue, o = orange, p = pink, r = red
  private final String[] tileMap = {
      "XXXXXXXXXXXXXXXXXXX",
      "X        X        X",
      "X XX XXX X XXX XX X",
      "X                 X",
      "X XX X XXXXX X XX X",
      "X    X       X    X",
      "XXXX XXXX XXXX XXXX",
      "OOOX X       X XOOO",
      "XXXX X XXrXX X XXXX",
      "O       bpo       O",
      "XXXX X XXXXX X XXXX",
      "OOOX X       X XOOO",
      "XXXX X XXXXX X XXXX",
      "X        X        X",
      "X XX XXX X XXX XX X",
      "X  X     P     X  X",
      "XX X X XXXXX X X XX",
      "X    X   X   X    X",
      "X XXXXXX X XXXXXX X",
      "X                 X",
      "XXXXXXXXXXXXXXXXXXX"
  };

  public HashSet<Block> walls;
  public HashSet<Block> foods;
  public HashSet<Block> ghosts;
  public Block pacMan;

  Timer gameLoop;
  char[] directions = {'U', 'D', 'L', 'R'};
  Random random = new Random();

  int score = 0;
  int lives = 3;
  boolean gameOver = false;

  public PacMan(){
    setPreferredSize(new Dimension(borderWidth, borderHeight));
    setBackground(Color.black);
    addKeyListener(this);
    setFocusable(true);

    //load images
    wallImage = new ImageIcon(getClass().getResource("/tile/wall.png")).getImage();
    blueGhostImage = new ImageIcon(getClass().getResource("/enemy/blueGhost.png")).getImage();
    orangeGhostImage = new ImageIcon(getClass().getResource("/enemy/orangeGhost.png")).getImage();
    pinkGhostImage = new ImageIcon(getClass().getResource("/enemy/pinkGhost.png")).getImage();
    redGhostImage = new ImageIcon(getClass().getResource("/enemy/redGhost.png")).getImage();
    pacManUpImage = new ImageIcon(getClass().getResource("/character/pacmanUp.png")).getImage();
    pacManDownImage = new ImageIcon(getClass().getResource("/character/pacmanDown.png")).getImage();
    pacManLeftImage = new ImageIcon(getClass().getResource("/character/pacmanLeft.png")).getImage();
    pacManRightImage = new ImageIcon(getClass().getResource("/character/pacmanRight.png")).getImage();

    loadMap();
    for (Block ghost : ghosts) {
      char newDirection = directions[random.nextInt(4)];
      ghost.updateDirection(newDirection);
    }
    gameLoop = new Timer(50, this);
    gameLoop.start();
  }

  public void loadMap(){
    walls = new HashSet<Block>();
    foods = new HashSet<Block>();
    ghosts = new HashSet<Block>();

    for (int r = 0; r < rowCount; r++) {
      for (int c = 0; c < colCount; c++) {
        String row = tileMap[r];
        char tileMapChar = row.charAt(c);

        int x = c * tileSize;
        int y = r * tileSize;

        if (tileMapChar == 'X'){
          Block wall = new Block(wallImage, x, y, tileSize, tileSize, this);
          walls.add(wall);
        } else if (tileMapChar == 'b') {
          Block ghost = new Block(blueGhostImage, x, y, tileSize, tileSize, this);
          ghosts.add(ghost);
        }
        else if (tileMapChar == 'o') {
          Block ghost = new Block(orangeGhostImage, x, y, tileSize, tileSize, this);
          ghosts.add(ghost);
        }
        else if (tileMapChar == 'p') {
          Block ghost = new Block(pinkGhostImage, x, y, tileSize, tileSize, this);
          ghosts.add(ghost);
        }
        else if (tileMapChar == 'r') {
          Block ghost = new Block(redGhostImage, x, y, tileSize, tileSize, this);
          ghosts.add(ghost);
        }
        else if (tileMapChar == 'P') {
          pacMan = new Block(pacManRightImage, x, y, tileSize, tileSize, this);
        } else if (tileMapChar == ' ') {
          Block food = new Block(null, x + 14, y + 14, 4, 4, this);
          foods.add(food);
        }
      }
    }
  }

  public void paintComponent(Graphics g){
    super.paintComponent(g);
    draw(g);
  }

  private void draw(Graphics g) {
    g.drawImage(pacMan.getImage(), pacMan.getX(), pacMan.getY(), pacMan.getWidth(), pacMan.getHeight(), null);

    for (Block ghost : ghosts) {
      g.drawImage(ghost.getImage(), ghost.getX(), ghost.getY(), ghost.getWidth(), ghost.getHeight(), null);
    }
    for (Block wall : walls) {
      g.drawImage(wall.getImage(), wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight(), null);
    }
    g.setColor(Color.white);
    for (Block food : foods) {
      g.fillRect(food.getX(), food.getY(), food.getWidth(), food.getHeight());
    }

    g.setFont(new Font("Arial", Font.PLAIN, 18));
    if (gameOver){
      g.drawString("Game Over" + String.valueOf(score), tileSize / 2, tileSize / 2);
    }
    else {
      g.drawString("x" + String.valueOf(lives) + " Score: " + String.valueOf(score), tileSize / 2, tileSize / 2);
    }
  }

  private void move(){
    pacMan.setX(pacMan.getX() + pacMan.getVelocityX());
    pacMan.setY(pacMan.getY() + pacMan.getVelocityY());

    for (Block wall : walls) {
      if (collision(pacMan, wall)) {
        pacMan.setX(pacMan.getX() - pacMan.getVelocityX());
        pacMan.setY(pacMan.getY() - pacMan.getVelocityY());
        break;
      }
    }

    for (Block ghost : ghosts) {
      if (collision(ghost, pacMan)){
        lives --;
        if (lives == 0){
          gameOver = true;
          return;
        }
        resetPosition();
      }
      if (ghost.getY() == tileSize * 9 && ghost.getDirection() != 'U' && ghost.getDirection() != 'D'){
        ghost.updateDirection('U');
      }
      ghost.setX(ghost.getX() + ghost.getVelocityX());
      ghost.setY(ghost.getY() + ghost.getVelocityY());
      for (Block wall : walls) {
        if (collision(ghost, wall) || ghost.getX() <= 0 || ghost.getX() + ghost.getWidth() >= borderWidth) {
          ghost.setX(ghost.getX() - ghost.getVelocityX());
          ghost.setY(ghost.getY() - ghost.getVelocityY());
          char newDirection = directions[random.nextInt(4)];
          ghost.updateDirection(newDirection);
        }
      }
    }

    Block foodEaten = null;
    for (Block food : foods) {
      if (collision(pacMan, food)) {
        foodEaten = food;
        score += 10;
      }
    }
    foods.remove(foodEaten);

    if (foods.isEmpty()){
      loadMap();
      resetPosition();
    }
  }

  private void resetPosition() {
    pacMan.reset();
    pacMan.setVelocityX(0);
    pacMan.setVelocityY(0);

    for (Block ghost :
        ghosts) {
      ghost.reset();
      char newDirection = directions[random.nextInt(4)];
      ghost.updateDirection(newDirection);
    }
  }

  public boolean collision(Block a, Block b){
    return a.getX() < b.getX() + b.getWidth() &&
        a.getX() + a.getWidth() > b.getX() &&
        a.getY() < b.getY() + b.getHeight() &&
        a.getY() + a.getHeight() > b.getY();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    move();
    repaint();
    if (gameOver){
      gameLoop.stop();
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {}

  @Override
  public void keyPressed(KeyEvent e) {}

  @Override
  public void keyReleased(KeyEvent e) {
    if (gameOver){
      loadMap();
      resetPosition();
      lives = 3;
      score = 0;
      gameOver = false;
      gameLoop.start();
    }
    if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W){
      pacMan.updateDirection('U');
    } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
      pacMan.updateDirection('D');
    } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
      pacMan.updateDirection('L');
    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
      pacMan.updateDirection('R');
    }

    if (pacMan.getDirection() == 'U'){
      pacMan.setImage(pacManUpImage);
    } else if (pacMan.getDirection() == 'D') {
      pacMan.setImage(pacManDownImage);
    } else if (pacMan.getDirection() == 'L') {
      pacMan.setImage(pacManLeftImage);
    } else if (pacMan.getDirection() == 'R') {
      pacMan.setImage(pacManRightImage);
    }
  }
}
