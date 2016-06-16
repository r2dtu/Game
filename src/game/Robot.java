/*
 * File name: Robot.java
 * Date Created: June 13, 2016
 * Description:
 */
package game;

import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author David
 */
public class Robot {

	private Game game;
	
	// Player starting positions & default information
    private int centerX = GameConstants.START_X;
    private int centerY = GameConstants.GROUND_Y;
    private boolean jumped, movingLeft, movingRight, ducked;
    private boolean readyToFire = true;
    private int currentHP = GameConstants.MAX_PLAYER_HP;

    // Movement for robot
    private int speedX = 0;
    private int speedY = 0;

    // Movement with relation to background
    private static Background bg1 = Game.getBg1();
    private static Background bg2 = Game.getBg2();

	// Weaponry
	private ArrayList<Bullet> bullets = new ArrayList<>();
	
	// Collision Detection
	private Rectangle rect = new Rectangle();
	private Rectangle rect2 = new Rectangle();
	private Rectangle rect3 = new Rectangle();
	private Rectangle rect4 = new Rectangle();
	private Rectangle yellowRed = new Rectangle();
	
	private Rectangle footLeft = new Rectangle();
	private Rectangle footRight = new Rectangle();

	public Robot(Game game) {
		this.game = game;
	}
	
    public void update() {

    	// Moving left
        if (speedX < 0) {
        	
//        	if (centerX < 61) {
                centerX += speedX;
                bg1.setSpeedX(0);
                bg2.setSpeedX(0);
//        	}
        	
//        	else {
//        		bg1.setSpeedX(GameConstants.MOVE_SPEED);
//        		bg2.setSpeedX(GameConstants.MOVE_SPEED);
//        	}
        }
        
        // If we're not moving, stop the background from moving too
        else if (speedX == 0) {
            bg1.setSpeedX(0);
            bg2.setSpeedX(0);
        }
        
        // Moving right
        else {
        	
        	// Initial screen: just move the robot right
            if (centerX < 201) {
                centerX += speedX;
            }
            
            // Move the background to the left (so the character moves right)
            else {
                bg1.setSpeedX(-GameConstants.MOVE_SPEED / GameConstants.BG_PROPORTIONAL_SLOW_CHANGE);
                bg2.setSpeedX(-GameConstants.MOVE_SPEED / GameConstants.BG_PROPORTIONAL_SLOW_CHANGE);
            }
        }

        // Update the Y posiiton
        centerY += speedY;
        
        setSpeedY(getSpeedY() + 1);
        
        if (getSpeedY() > 3) {
        	setJumped(true);
        }

        // Don't ever go more left of the screen!
        if (centerX + speedX < GameConstants.LEFT_X_BOUND) {
            centerX = GameConstants.LEFT_X_BOUND;
        }
        
        // Draw the bounds for collision
        rect.setRect(getCenterX() - 34, getCenterY() - 63, 68, 63);
        rect2.setRect(rect.getX(), rect.getY() + 63, 68, 64);
        rect3.setRect(rect.getX() - 26, rect.getY() + 32, 26, 20);
        rect4.setRect(rect.getX() + 68, rect.getY() + 32, 26, 20);
        
        yellowRed.setRect(getCenterX() - 110, getCenterY() - 110, 180, 180);

		footLeft.setRect(getCenterX() - 50, getCenterY() + 20, 50, 15);
		footRight.setRect(getCenterX(), getCenterY() + 20, 50, 15);
    }

    public void moveRight() {
        if (!isDucked()) {
            setSpeedX(GameConstants.MOVE_SPEED);
        }
    }

    public void moveLeft() {
        if (!isDucked()) {
            setSpeedX(-GameConstants.MOVE_SPEED);
        }
    }

    public void stop() {
        if (!isMovingRight() && !isMovingLeft()) {
            setSpeedX(0);
        }
        if (!isMovingRight() && isMovingLeft()) {
            moveLeft();
        }
        if (isMovingRight() && !isMovingLeft()) {
            moveRight();
        }
    }

    public void jump() {
        if (!jumped) {
            setSpeedY(GameConstants.JUMP_SPEED);
            setJumped(true);
        }
    }
    
    public void shoot() {
    	if (isReadyToFire()) {
	    	Bullet b = new Bullet(game, centerX + GameConstants.BULLET_START_X_OFFSET, centerY - GameConstants.BULLET_START_Y_OFFSET);
	    	bullets.add(b);
    	}
    }
    
    /**
     * @return centerX
     */
    public int getCenterX() {
        return centerX;
    }

    /**
     * @return centerY
     */
    public int getCenterY() {
        return centerY;
    }

    /**
     * @return jumped
     */
    public boolean isJumped() {
        return jumped;
    }

    /**
     * @return speedX
     */
    public int getSpeedX() {
        return speedX;
    }

    /**
     * @return speedY
     */
    public int getSpeedY() {
        return speedY;
    }

    /**
     * @param centerX
     */
    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    /**
     * @param centerY
     */
    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    /**
     * @param jumped
     */
    public void setJumped(boolean jumped) {
        this.jumped = jumped;
    }

    /**
     * @param speedX
     */
    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    /**
     * @param speedY
     */
    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    /**
     * @return the movingLeft
     */
    public boolean isMovingLeft() {
        return movingLeft;
    }

    /**
     * @param movingLeft
     */
    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    /**
     * @return the movingRight
     */
    public boolean isMovingRight() {
        return movingRight;
    }

    /**
     * @param movingRight
     */
    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    /**
     * @return the ducked
     */
    public boolean isDucked() {
        return ducked;
    }

    /**
     * @param ducked the ducked to set
     */
    public void setDucked(boolean ducked) {
        this.ducked = ducked;
    }
    
    /**
     * @return bullets
     */
    public ArrayList<Bullet> getBullets() {
    	return bullets;
    }
    
    /**
     * @return rect
     */
    public Rectangle getRect() {
		return rect;
	}

    /**
     * @param rect
     */
	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

    /**
     * @return rect2
     */
	public Rectangle getRect2() {
		return rect2;
	}

    /**
     * @param rect2
     */
	public void setRect2(Rectangle rect2) {
		this.rect2 = rect2;
	}

    /**
     * @return rect3
     */
	public Rectangle getRect3() {
		return rect3;
	}

    /**
     * @param rect3
     */
	public void setRect3(Rectangle rect3) {
		this.rect3 = rect3;
	}
	
    /**
     * @return rect4
     */
	public Rectangle getRect4() {
		return rect4;
	}

    /**
     * @param rect4
     */
	public void setRect4(Rectangle rect4) {
		this.rect4 = rect4;
	}
	
	public boolean isReadyToFire() {
		return readyToFire;
	}

	public void setReadyToFire(boolean readyToFire) {
		this.readyToFire = readyToFire;
	}
	

	public Rectangle getYellowRed() {
		return yellowRed;
	}

	public void setYellowRed(Rectangle yellowRed) {
		this.yellowRed = yellowRed;
	}

	public Rectangle getFootLeft() {
		return footLeft;
	}

	public void setFootLeft(Rectangle footLeft) {
		this.footLeft = footLeft;
	}

	public Rectangle getFootRight() {
		return footRight;
	}

	public void setFootRight(Rectangle footRight) {
		this.footRight = footRight;
	}

	public int getCurrentHP() {
		return currentHP;
	}
	
	public void setCurrentHP(int currentHP) {
		this.currentHP = currentHP;
	}

}
