/*
 * File name: Background.java
 * Date Created: June 13, 2016
 * Description: 
 */
package game;

/**
 *
 * @author David
 */
public class Background {
    
    private int bgX, bgY, speedX;
    
    /**
     * Creates a background object for the window so we can control it.
     * @param bgX
     * @param bgY
     */
    public Background(int bgX, int bgY) {
        this.bgX = bgX;
        this.bgY = bgY;
        this.speedX = 0;
    }

    /**
     * Updates the background object (moves it, etc.)
     */
    public void update() {
        bgX += speedX;
        
        if (bgX < -(GameConstants.BG_IMG_WIDTH + 1)) {
            bgX += 4320;
        }
    }
    
    /**
     * @return bgX
     */
    public int getBgX() {
        return bgX;
    }

    /**
     * @param bgX
     */
    public void setBgX(int bgX) {
        this.bgX = bgX;
    }

    /**
     * @return the bgY
     */
    public int getBgY() {
        return bgY;
    }

    /**
     * @param bgY
     */
    public void setBgY(int bgY) {
        this.bgY = bgY;
    }

    /**
     * @return speedX
     */
    public int getSpeedX() {
        return speedX;
    }

    /**
     * @param speedX
     */
    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }
}
