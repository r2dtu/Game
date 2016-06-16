/*
 * File name: Bullet.java
 * Date Created: June 13, 2016
 * Description:
 */
package game;

import java.awt.Rectangle;

/**
 * 
 * @author David
 */
public class Bullet {

	// Private instance of the current game
	private Game game;
	
	// Bullet data
	private int x, y, speed;
	private boolean visible;

	private Rectangle r;
	
	public Bullet(Game game, int xPos, int yPos) {
		
		this.game = game;
		setX(xPos);
		setY(yPos);
		setSpeed(GameConstants.BULLET_SPEED);
		setVisible(true);
		
		r = new Rectangle();
	}

	public void update() {
		setX(getX() + speed);
		r.setBounds(x, y, 10, 5);
		if (getX() > 800) {
			setVisible(false);
			r = null;
		}
		if (x < 800) {
			checkCollision();
		}
	}

	private void checkCollision() {
		if (r.intersects(game.getEnemy1().getRect())) {
			visible = false;

			if (game.getEnemy1().getCurrentHp() > 0) {
				game.getEnemy1().setCurrentHp(game.getEnemy1().getCurrentHp() - 1);
			}
			if (game.getEnemy1().getCurrentHp() == 0) {
				game.getEnemy1().setX(-100);
				game.setScore(game.getScore() + 5);
			}

		}

		if (r.intersects(game.getEnemy2().getRect())) {
			visible = false;

			if (game.getEnemy2().getCurrentHp() > 0) {
				game.getEnemy2().setCurrentHp(game.getEnemy2().getCurrentHp() - 1);
			}
			if (game.getEnemy2().getCurrentHp() == 0) {
				game.getEnemy2().setX(-100);
				game.setScore(game.getScore() + 5);
			}

		}
	}

	public int getX() {
		return x;
	}

	public void setX(int xPos) {
		this.x = xPos;
	}

	public int getY() {
		return y;
	}

	public void setY(int yPos) {
		this.y = yPos;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
