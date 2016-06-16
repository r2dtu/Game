/*
 * File name: Tile.java
 * Date Created: June 13, 2016
 * Description:
 */
package game;

import java.awt.Image;
import java.awt.Rectangle;

public class Tile {

	// Different tiles in the game graphics
	public enum TileType {
		DIRT_TILE, GRASS_TOP_TILE, GRASS_BOTTOM_TILE, GRASS_RIGHT_TILE, GRASS_LEFT_TILE, OCEAN_TILE, UNK
	}

	// Instance of the player's robot
	private Robot robot;

	// Tile data - position, movement speed, and what kind of tile it is
	private int x, y, speed;
	private Image tileImage;
	private TileType type;

	// We need to move the tiles with relation to the background
	private Background bg = Game.getBg1();

	// The bounds of each tile
	private Rectangle r;

	/**
	 * Creates a tile object to be put on the Graphics.
	 * @param x
	 * @param y
	 * @param type
	 */
	public Tile(Robot robot, int x, int y, TileType type) {
		
		this.robot = robot;
		
		setX(x * 40);
		setY(y * 40);

		// Create the bounds of collision
		r = new Rectangle();

		// Define the tile with its corresponding image
		setType(type);
		switch (getType()) {
		case DIRT_TILE:
			setTileImage(Game.getDirtTile());
			break;
		case GRASS_TOP_TILE:
			setTileImage(Game.getGrassTopTile());
			break;
		case GRASS_BOTTOM_TILE:
			setTileImage(Game.getGrassBottomTile());
			break;
		case GRASS_LEFT_TILE:
			setTileImage(Game.getGrassLeftTile());
			break;
		case GRASS_RIGHT_TILE:
			setTileImage(Game.getGrassRightTile());
			break;
		case OCEAN_TILE:
			setTileImage(Game.getOceanTile());
			break;
		case UNK:
			break;
		default:
			break;
		}
	}

	public void update() {

		setSpeed(bg.getSpeedX() * GameConstants.BG_PROPORTIONAL_SLOW_CHANGE);
		setX(getX() + getSpeed());
		r.setBounds(getX(), getY(), 40, 40);

		if (r.intersects(robot.getYellowRed()) && getType() != TileType.UNK) {
			checkVerticalCollision(robot.getRect(), robot.getRect2());
			checkSideCollision(robot.getRect3(), robot.getRect4(), robot.getFootLeft(), robot.getFootRight());
		}
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

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public TileType getType() {
		return type;
	}

	public void setType(TileType type) {
		this.type = type;
	}

	public Image getTileImage() {
		return tileImage;
	}

	public void setTileImage(Image tileImage) {
		this.tileImage = tileImage;
	}

	public void checkVerticalCollision(Rectangle rtop, Rectangle rbottom) {
		if (rtop.intersects(r)) {

		}

		if (rbottom.intersects(r) && getType() == TileType.GRASS_TOP_TILE) {
			robot.setJumped(false);
			robot.setSpeedY(0);
			robot.setCenterY(getY() - 63);
		}
	}

	public void checkSideCollision(Rectangle rleft, Rectangle rright, Rectangle leftFoot, Rectangle rightFoot) {
		if (getType() != TileType.DIRT_TILE && getType() != TileType.GRASS_BOTTOM_TILE && getType() != TileType.UNK) {
			if (rleft.intersects(r)) {
				robot.setCenterX(getX() + 102);
				robot.setSpeedX(0);
			}
			
			else if (leftFoot.intersects(r)) {
				robot.setCenterX(getX() + 85);
				robot.setSpeedX(0);
			}

			if (rright.intersects(r)) {
				robot.setCenterX(getX() - 62);
				robot.setSpeedX(0);
			}

			else if (rightFoot.intersects(r)) {
				robot.setCenterX(getX() - 45);
				robot.setSpeedX(0);
			}
		}
	}

	public static TileType getTileType(int tileType) {
		switch (tileType) {
		case 5:
			return TileType.DIRT_TILE;
		case 8:
			return TileType.GRASS_TOP_TILE;
		case 4:
			return TileType.GRASS_LEFT_TILE;
		case 2:
			return TileType.GRASS_BOTTOM_TILE;
		case 6:
			return TileType.GRASS_RIGHT_TILE;
		}
		return TileType.UNK;
	}

}
