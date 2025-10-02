# Pacman 2D (Java)

## 🎯 Project goal
The project was created as a Java training exercise and, at the same time, as a full-fledged 2D **Pacman** game.  
Main goals:  
- Learn how to develop desktop applications in Java.  
- To study the principles of building a game cycle, rendering, and event handling.  
- To obtain a ready-to-play implementation of a classic game.  

---

## 🛠️ Technology stack
- **Java 17**
- **Swing** — for the graphical interface and rendering game objects.  
- **JavaFX** — for animation elements and additional visual effects.
- Third-party libraries: **not used** (pure Java code).  

---

## 🚀 How to deploy a project

### Requirements
- **Java 17** or newer installed
- (optional) Maven / any IDE (IntelliJ IDEA, Eclipse, NetBeans)  

### Launching via IDE
1. Clone the repository:
   ```bash
   git clone https://github.com/NikitaSipeykin/PackManJava.git
   ```
2. Open the project in the IDE.
3. Run the class with the `main` method.  

### Launching via console
1. Compile the project:
   ```bash
   javac -d out src/main/java/game/main/App.java
   ```
2. Start the game:
   ```bash
   java -cp out App
   ``` 

---

## 📂 Project structure
Main code components:
- **App** — application entry point.
- **GamePanel** — game loop, rendering, and status updates.
- **KeyHandler** — character control (Pacman).  
- **PackMan** — character behavior logic (Pacman).  
- **Ghost** — enemy behavior logic.  
- **Cherry** — bonus food objects.  
- **PowerFood** — power-up objects.  
- **Wall** — wall object.
- **Block** — parent object from which all other objects on the map inherit.
- **Entity** — parent object for movable objects.

---

## 🎮 Controls
- **Arrow keys/WASD** — move your character.
