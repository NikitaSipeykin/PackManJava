package game.main;

import game.surroundings.Block;
import game.surroundings.Entity;
import game.surroundings.character.Ghost;
import game.surroundings.character.PackMan;
import game.surroundings.food.Cherry;
import game.surroundings.food.PowerFood;
import game.surroundings.tile.Wall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

public class GamePanel extends JPanel implements ActionListener {
  KeyHandler keyHandler = new KeyHandler(this);
  Timer gameLoop;

  private final int rowCount = 21;
  private final int colCount = 19;
  public final int tileSize = 32;
  public final int borderWidth = colCount * tileSize;
  public final int borderHeight = rowCount * tileSize;

  public int score = 0;
  public int lives = 3;

  public boolean gameOver = false;
  public boolean isPowerTime = false;

  private int powerTimeCounter = 0;

  public HashSet<Block> walls;
  public HashSet<Block> foods;
  public HashSet<Block> cherrys;
  public HashSet<Block> powerFoods;
  public HashSet<Entity> ghosts;

  public PackMan packMan;
  //X = wall, O = skip, P = pac man, ' ' = food
  //Ghosts: b = blue, o = orange, p = pink, r = red
  private final String[] tileMap = {
      "XXXXXXXXXXXXXXXXXXX",
      "X        X        X",
      "X XX XXX X XXX XX X",
      "X   C         C   X",
      "X XX X XXXXX X XX X",
      "X    X       X    X",
      "XXXX XXXX XXXX XXXX",
      "OOOX X   S   X XOOO",
      "XXXX X XXrXX X XXXX",
      "T       bpo       T",
      "XXXX X XXXXX X XXXX",
      "OOOX X       X XOOO",
      "XXXX X XXXXX X XXXX",
      "X   C    X    C   X",
      "X XX XXX X XXX XX X",
      "X  X     P     X  X",
      "XX X X XXXXX X X XX",
      "X    X   X   X    X",
      "X XXXXXX X XXXXXX X",
      "X                 X",
      "XXXXXXXXXXXXXXXXXXX"
  };

  public GamePanel(){
    setPreferredSize(new Dimension(borderWidth, borderHeight));
    setBackground(Color.black);
    addKeyListener(keyHandler);
    setFocusable(true);

    loadMap();
    for (Entity ghost : ghosts) {
      char newDirection = ghost.directions[ghost.randomDirection()];
      ghost.updateDirection(newDirection);
    }
    gameLoop = new Timer(50, this);
    gameLoop.start();
  }

  public void loadMap(){
    walls = new HashSet<Block>();
    foods = new HashSet<Block>();
    cherrys = new HashSet<Block>();
    powerFoods = new HashSet<Block>();
    ghosts = new HashSet<Entity>();


    for (int r = 0; r < rowCount; r++) {
      for (int c = 0; c < colCount; c++) {
        String row = tileMap[r];
        char tileMapChar = row.charAt(c);

        int x = c * tileSize;
        int y = r * tileSize;

        if (tileMapChar == 'X'){
          Block wall = new Wall(this, x, y);
          walls.add(wall);
        } else if (tileMapChar == 'b') {
          Entity ghost = new Ghost(this, x, y, "blue");
          ghosts.add(ghost);
        }
        else if (tileMapChar == 'o') {
          Entity ghost = new Ghost(this, x, y, "orange");
          ghosts.add(ghost);
        }
        else if (tileMapChar == 'p') {
          Entity ghost = new Ghost(this, x, y, "pink");
          ghosts.add(ghost);
        }
        else if (tileMapChar == 'r') {
          Entity ghost = new Ghost(this, x, y, "red");
          ghosts.add(ghost);
        }
        else if (tileMapChar == 'P') {
          packMan = new PackMan(this, x, y, keyHandler);
        }
        else if (tileMapChar == 'C') {
          Block cherry = new Cherry(this, x, y);
          cherrys.add(cherry);
        }
        else if (tileMapChar == 'S') {
          Block powerFood = new PowerFood(this, x, y);
          powerFoods.add(powerFood);
        }
        else if (tileMapChar == ' ') {
          Block food = new Block(this, x + 14, y + 14, 4, 4 );
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
    g.drawImage(packMan.getCurrentImage(), packMan.getX(), packMan.getY(), packMan.getWidth(), packMan.getHeight(), null);

    for (Entity ghost : ghosts) {
      g.drawImage(ghost.getCurrentImage(), ghost.getX(), ghost.getY(), ghost.getWidth(), ghost.getHeight(), null);
    }
    for (Block wall : walls) {
      g.drawImage(wall.getCurrentImage(), wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight(), null);
    }

    g.setColor(Color.white);
    for (Block food : foods) {
      g.fillRect(food.getX(), food.getY(), food.getWidth(), food.getHeight());
    }
    for (Block cherry : cherrys) {
      g.drawImage(cherry.getCurrentImage(), cherry.getX(), cherry.getY(), cherry.getWidth(), cherry.getHeight(), null);
    }
    for (Block powerFood : powerFoods) {
      g.drawImage(powerFood.getCurrentImage(), powerFood.getX(), powerFood.getY(), powerFood.getWidth(), powerFood.getHeight(), null);
    }

    g.setFont(new Font("Arial", Font.PLAIN, 18));
    if (gameOver){
      g.drawString("Game Over" + String.valueOf(score), tileSize / 2, tileSize / 2);
    }
    else {
      g.drawString("x" + String.valueOf(lives) + " Score: " + String.valueOf(score), tileSize / 2, tileSize / 2);
    }
  }

  public void resetPosition() {
    packMan.reset();
    packMan.setVelocityX(0);
    packMan.setVelocityY(0);

    for (Entity ghost : ghosts) {
      ghost.reset();
      char newDirection = ghost.directions[ghost.randomDirection()];
      ghost.updateDirection(newDirection);
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    packMan.move();
    if (isPowerTime){
      powerTimeCounter++;
    }

    for (Entity ghost : ghosts) {
      ghost.move();
    }

    if (powerTimeCounter == 600){
      isPowerTime = false;
      powerTimeCounter = 0;

      for (Entity ghost : ghosts) {
        ghost.setDefaultImage();
      }
    }

    repaint();

    if (gameOver){
      gameLoop.stop();
    }
  }
}
