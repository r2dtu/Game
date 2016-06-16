/*
 * File name: Game.java
 * Date Created: June 13, 2016
 * Description:
 */
package game;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import game.animation.Animation;

/**
 * TODO:
 * 1) Go backwards, but don't go past the start of the map
 * 2) Play again / Reset button after death
 * 3) Personal health goes down if you touch enemy
 * 4) Personal health goes down when shot by the enemy
 * 5) Spikes?
 * 6) Set number of enemies (array?)
 * 7) Smoother movement (up + right, etc.)
 * 
 * @author David
 */
public class Game extends Applet implements Runnable, KeyListener {

	// Game Overall Score
	private int score;
	
	// The game's robot
	private Robot robot;

	// Enemy characters
	private Heliboy enemy1, enemy2;
	
	// Character images
	private URL base;
	private Image character, character2, character3, characterDown, characterJumped, currentSprite;
	private Image heliboy, heliboy2, heliboy3, heliboy4, heliboy5;

	// Game backgrounds
	private Image image;
	private Graphics second;
	private Image background;
	private static Background bg1, bg2;

	// Animations
	private Animation animation, heliboyAnimation;

	// Tiles
	private static Image dirtTile, oceanTile;
	private static Image grassTopTile, grassBottomTile, grassLeftTile, grassRightTile;
	private ArrayList<Tile> tiles = new ArrayList<>();

	// DEAD font
	private Font font = new Font(null, Font.BOLD, GameConstants.TEXT_SIZE);

	// Game Over - there's only one instance
	private static boolean dead = false;

	/**
	 * Initialize the game window and game setup.
	 */
	@Override
	public void init() {

		// Set the size of the window and background
		setSize(GameConstants.GAME_WINDOW_WIDTH, GameConstants.GAME_WINDOW_HEIGHT);
		setBackground(Color.BLACK);

		// Set focus in window and add key listener
		setFocusable(true);
		addKeyListener(this);

		// Create the window frame
		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Q-Bot ALPHA");

		base = getDocumentBase();

		// Grab the image files for our robot character
		character = getImage(base, "game/images/character.png");
		character2 = getImage(base, "game/images/character2.png");
		character3 = getImage(base, "game/images/character3.png");
		characterDown = getImage(base, "game/images/down.png");
		characterJumped = getImage(base, "game/images/jumped.png");

		// Grab image files for our enemy characters
		heliboy = getImage(base, "game/images/heliboy.png");
		heliboy2 = getImage(base, "game/images/heliboy2.png");
		heliboy3 = getImage(base, "game/images/heliboy3.png");
		heliboy4 = getImage(base, "game/images/heliboy4.png");
		heliboy5 = getImage(base, "game/images/heliboy5.png");

		// Grab the image files
		background = getImage(base, "game/images/background.png");

		// Grab the tile images
		dirtTile = getImage(base, "game/images/tiledirt.png");
		oceanTile = getImage(base, "game/images/tileocean.png");
		grassTopTile = getImage(base, "game/images/tilegrasstop.png");
		grassBottomTile = getImage(base, "game/images/tilegrassbot.png");
		grassLeftTile = getImage(base, "game/images/tilegrassleft.png");
		grassRightTile = getImage(base, "game/images/tilegrassright.png");

		// Start the animations
		animation = new Animation();
		animation.addFrame(character, GameConstants.CHARACTER_ANIMATION_DURATION);
		animation.addFrame(character2, GameConstants.CHARACTER_ANIMATION_DURATION2);
		animation.addFrame(character3, GameConstants.CHARACTER_ANIMATION_DURATION2);
		animation.addFrame(character2, GameConstants.CHARACTER_ANIMATION_DURATION2);

		// Heliboy animations
		heliboyAnimation = new Animation();
		heliboyAnimation.addFrame(heliboy, GameConstants.HELIBOY_ANIMATION_DURATION);
		heliboyAnimation.addFrame(heliboy2, GameConstants.HELIBOY_ANIMATION_DURATION);
		heliboyAnimation.addFrame(heliboy3, GameConstants.HELIBOY_ANIMATION_DURATION);
		heliboyAnimation.addFrame(heliboy4, GameConstants.HELIBOY_ANIMATION_DURATION);
		heliboyAnimation.addFrame(heliboy5, GameConstants.HELIBOY_ANIMATION_DURATION);
		heliboyAnimation.addFrame(heliboy4, GameConstants.HELIBOY_ANIMATION_DURATION);
		heliboyAnimation.addFrame(heliboy3, GameConstants.HELIBOY_ANIMATION_DURATION);
		heliboyAnimation.addFrame(heliboy2, GameConstants.HELIBOY_ANIMATION_DURATION);

		// Set up the character animation
		currentSprite = animation.getImage();

	}

	@Override
	public void start() {

		// Create the background
		bg1 = new Background(0, 0);
		bg2 = new Background(GameConstants.BG_IMG_WIDTH, 0);

		// Create the robot
		robot = new Robot(this);

		try {
			loadMap("game/maps/map2.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Create the enemies
		enemy1 = new Heliboy(robot, GameConstants.HB_START_X, GameConstants.HB_START_Y);
		enemy2 = new Heliboy(robot, GameConstants.HB_START_X + GameConstants.HB_SPACING, GameConstants.HB_START_Y);

		// Create and start a new thread that runs this program
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void stop() {

	}

	@Override
	public void destroy() {

	}

	@Override
	public void run() {

		if (!isDead()) {

			while (true) {

				// Update the robot's information
				robot.update();

				// If we're jumping, change the image
				if (robot.isJumped()) {
					currentSprite = characterJumped;
				}

				// Otherwise, just change it back to the character
				else if (!robot.isJumped() && !robot.isDucked()) {
					currentSprite = animation.getImage();
				}

				// Update the bullet positions / information
				ArrayList<Bullet> bullets = robot.getBullets();
				for (int i = 0; i < bullets.size(); i++) {
					Bullet p = bullets.get(i);
					if (p.isVisible() == true) {
						p.update();
					} else {
						bullets.remove(i);
					}
				}
				/*
				 * ArrayList<Bullet> bullets = robot.getBullets(); for (Bullet
				 * bullet : bullets) { if (bullet.isVisible()) {
				 * bullet.update(); } else { bullets.remove(bullet); } }
				 */

				// Update the tiles
				for (Tile t : tiles) {
					t.update();
				}

				// Update the enemy's information
				enemy1.update();
				enemy2.update();

				// Update the backgrounds
				bg1.update();
				bg2.update();

				// Animate the characters
				animate();

				// Repaint the game window
				repaint();

				// Delay so we don't take up all of the CPU :)
				try {
					Thread.sleep(GameConstants.SLEEP_TIME);
				} catch (InterruptedException ex) {
					Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
				}

				// If the robot fell off the map, or the HP is zero, game over!
				if (robot.getCenterY() > GameConstants.DEAD_Y || robot.getCurrentHP() < 1) {
					setDead(true);
				}
			}
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			robot.jump();
			break;

		case KeyEvent.VK_DOWN:
			currentSprite = characterDown;
			if (!robot.isJumped()) {
				robot.setDucked(true);
				robot.setSpeedX(0);
			}
			break;

		case KeyEvent.VK_LEFT:
			robot.moveLeft();
			robot.setMovingLeft(true);
			break;

		case KeyEvent.VK_RIGHT:
			robot.moveRight();
			robot.setMovingRight(true);
			break;

		case KeyEvent.VK_SPACE:
			if (!robot.isDucked() && !robot.isJumped() && robot.isReadyToFire()) {
				robot.shoot();
				robot.setReadyToFire(false);
			}
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			break;

		case KeyEvent.VK_DOWN:
			currentSprite = animation.getImage();
			robot.setDucked(false);
			break;

		case KeyEvent.VK_LEFT:
			robot.setMovingLeft(false);
			robot.stop();
			break;

		case KeyEvent.VK_RIGHT:
			robot.setMovingRight(false);
			robot.stop();
			break;

		case KeyEvent.VK_SPACE:
			robot.setReadyToFire(true);
			break;
		}
	}

	@Override
	public void update(Graphics g) {
		if (image == null) {
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}

		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());
		paint(second);

		g.drawImage(image, 0, 0, this);
	}

	@Override
	public void paint(Graphics g) {

		if (!isDead()) {
			// Draw backgrounds first, so they're in the back
			g.drawImage(background, bg1.getBgX(), bg1.getBgY(), this);
			g.drawImage(background, bg2.getBgX(), bg2.getBgY(), this);

			// Draw the bullets
			ArrayList<Bullet> bullets = robot.getBullets();
			for (Bullet bullet : bullets) {
				g.setColor(Color.YELLOW);
				g.fillRect(bullet.getX(), bullet.getY(), GameConstants.BULLET_WIDTH, GameConstants.BULLET_HEIGHT);
			}

			// Paint the tiles
			for (Tile t : tiles) {
				g.drawImage(t.getTileImage(), t.getX(), t.getY(), this);
			}

			// Draw the enemies again
			g.drawImage(heliboyAnimation.getImage(), enemy1.getX() - GameConstants.HELIBOY_IMG_CENTER,
					enemy1.getY() - GameConstants.HELIBOY_IMG_CENTER, this);
			g.drawImage(heliboyAnimation.getImage(), enemy2.getX() - GameConstants.HELIBOY_IMG_CENTER,
					enemy2.getY() - GameConstants.HELIBOY_IMG_CENTER, this);

			// Draw character last, so it's in the front
			g.drawImage(currentSprite, robot.getCenterX() - 61, robot.getCenterY() - 63, this);

			// Draw the score on the top right corner
			g.setFont(font);
			g.setColor(Color.WHITE);
			g.drawString("Score: " + Integer.toString(getScore()), GameConstants.SCORE_TEXT_X, GameConstants.SCORE_TEXT_Y);
			
			// Draw the player's health on the top left
			g.setFont(font);
			g.setColor(Color.WHITE);
			g.drawString("HP: " + Integer.toString(robot.getCurrentHP()), GameConstants.HEALTH_TEXT_X, GameConstants.HEALTH_TEXT_Y);
			
		}
		else {
			
			// Draw the DEAD / Game over screen
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, GameConstants.GAME_WINDOW_WIDTH, GameConstants.GAME_WINDOW_HEIGHT);
			g.setColor(Color.WHITE);
			g.drawString("DEAD", GameConstants.DEAD_TEXT_X, GameConstants.DEAD_TEXT_Y);
		}

	}

	public void animate() {
		animation.update(GameConstants.CHARACTER_ANIMATION_DELAY);
		heliboyAnimation.update(GameConstants.HELIBOY_ANIMATION_DELAY);
	}

	/**
	 * Loads the level map from a map text file.
	 * 
	 * @param filename
	 * @throws IOException
	 */
	public void loadMap(String filename) throws IOException {
		ArrayList<String> lines = new ArrayList<>();
		int width = 0, height = 0;

		BufferedReader reader = new BufferedReader(new FileReader(filename));

		while (true) {

			// Read each line
			String line = reader.readLine();

			// No more lines to read
			if (line == null) {
				reader.close();
				break;
			}

			// ! means comments in the map file
			if (!line.startsWith("!")) {
				lines.add(line);
				width = Math.max(width, line.length());
			}
		}

		height = lines.size();

		for (int y = 0; y < GameConstants.TILES_PER_COLUMN; y++) {
			String line = lines.get(y);

			for (int x = 0; x < width; x++) {
				if (x < line.length()) {
					char ch = line.charAt(x);
					Tile t = new Tile(robot, x, y, Tile.getTileType(Character.getNumericValue(ch)));
					tiles.add(t);
				}
			}
		}
	}

	public Robot getRobot() {
		return robot;
	}

	public Heliboy getEnemy1() {
		return enemy1;
	}

	public void setEnemy1(Heliboy enemy1) {
		this.enemy1 = enemy1;
	}

	public Heliboy getEnemy2() {
		return enemy2;
	}

	public void setEnemy2(Heliboy enemy2) {
		this.enemy2 = enemy2;
	}

	
	public static Background getBg1() {
		return bg1;
	}

	public static Background getBg2() {
		return bg2;
	}

	public static Image getDirtTile() {
		return dirtTile;
	}

	public static void setDirtTile(Image dirtTile) {
		Game.dirtTile = dirtTile;
	}

	public static Image getOceanTile() {
		return oceanTile;
	}

	public static void setOceanTile(Image oceanTile) {
		Game.oceanTile = oceanTile;
	}

	public static Image getGrassTopTile() {
		return grassTopTile;
	}

	public static void setGrassTopTile(Image grassTopTile) {
		Game.grassTopTile = grassTopTile;
	}

	public static Image getGrassBottomTile() {
		return grassBottomTile;
	}

	public static void setGrassBottomTile(Image grassBottomTile) {
		Game.grassBottomTile = grassBottomTile;
	}

	public static Image getGrassLeftTile() {
		return grassLeftTile;
	}

	public static void setGrassLeftTile(Image grassLeftTile) {
		Game.grassLeftTile = grassLeftTile;
	}

	public static Image getGrassRightTile() {
		return grassRightTile;
	}

	public static void setGrassRightTile(Image grassRightTile) {
		Game.grassRightTile = grassRightTile;
	}

	public static boolean isDead() {
		return dead;
	}

	public static void setDead(boolean dead) {
		Game.dead = dead;
	}

	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
}
