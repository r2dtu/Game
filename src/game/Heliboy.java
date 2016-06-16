/*
 * File name: Heliboy.java
 * Date Created: June 13, 2016
 * Description:
 */
package game;

public class Heliboy extends Enemy {

	/**
	 * Creates a heliboy enemy.
	 * 
	 * @param xPos
	 * @param yPos
	 */
	public Heliboy(Robot robot, int xPos, int yPos) {
		super(robot);
		setX(xPos);
		setY(yPos);
		setCurrentHp(GameConstants.MAX_ENEMY_HP);
	}
	
	@Override
	public void attack() {
		// TODO Auto-generated method stub
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
	}

}
