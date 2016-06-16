/*
 * File name: Enemy.java
 * Date Created: June 13, 2016
 * Description:
 */
package game;

import java.awt.Rectangle;

/**
 * 
 * @author David
 */
public abstract class Enemy {

	// Private instance of the player
	private Robot robot;
	
	// Enemy data (position, speed, health points)
	private int maxHp = GameConstants.MAX_ENEMY_HP, currentHp;
	private int power;
	private int speed;
	private int x, y;

	// Enemy collision detection
	private Rectangle r = new Rectangle();
	private Background bg = Game.getBg1();
	private int hitDelayCounter = GameConstants.HIT_DELAY;

	/**
	 * Create an enemy for the game
	 * @param robot
	 */
	public Enemy(Robot robot) {
		this.robot = robot;
	}
	
	public void update() {
		x += speed;
		speed = bg.getSpeedX() * GameConstants.BG_PROPORTIONAL_SLOW_CHANGE;

		r.setBounds(getX() - 25, getY() - 25, 50, 60);

		if (r.intersects(robot.getYellowRed())) {
			checkCollision();
		}
	}

	// Each enemy will attack and die differently
	public abstract void attack();
	public abstract void die();

	private void checkCollision() {
		
		// If we've collided, the player will take hits
		if (r.intersects(robot.getRect()) || r.intersects(robot.getRect2()) || r.intersects(robot.getRect3())
				|| r.intersects(robot.getRect4())) {
			
			// Taking hits every 17ms is terrifying
			if (hitDelayCounter++ > GameConstants.HIT_DELAY) {
				robot.setCurrentHP(robot.getCurrentHP() - 1);
				setHitDelayCounter(0);
			}

		}
		
		// Otherwise, reset it for the next time we hit an enemy
		else {
			setHitDelayCounter(GameConstants.HIT_DELAY);
		}
	}

	public int getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}

	public int getCurrentHp() {
		return currentHp;
	}

	public void setCurrentHp(int currentHp) {
		this.currentHp = currentHp;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
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

	public Background getBg() {
		return bg;
	}

	public void setBg(Background bg) {
		this.bg = bg;
	}

	public Rectangle getRect() {
		return r;
	}
	
	public int getHitDelayCounter() {
		return hitDelayCounter;
	}
	
	public void setHitDelayCounter(int hitDelayCounter) {
		this.hitDelayCounter = hitDelayCounter;
	}
}
