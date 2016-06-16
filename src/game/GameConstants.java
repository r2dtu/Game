/*
 * File name: GameConstants.java
 * Date Created: June 13, 2016
 * Description:
 */
package game;

/**
 *
 * @author David
 */
public class GameConstants {
    
	// Program delay time to free processor
    public static final int SLEEP_TIME = 17; // millseconds
    
    // Window limits
    public static final int GAME_WINDOW_WIDTH = 800;
    public static final int GAME_WINDOW_HEIGHT = 480;
    public static final int BG_IMG_WIDTH = 2160;
    public static final int START_X = 100;
    public static final int GROUND_Y = 382;
    public static final int LEFT_X_BOUND = 61;
    public static final int DEAD_Y = 500;
    
    // Tile constants
    public static final int COLUMNS_IN_GAME_WINDOW = 200;
    public static final int TILES_PER_COLUMN = 12;
    public static final int DIRT_TILE_POS = 10;
    public static final int OCEAN_TILE_POS = 11;
    
    // Text Objects
    public static final int TEXT_SIZE = 30;
    public static final int HEALTH_TEXT_X = 10;
    public static final int HEALTH_TEXT_Y = 30;
    public static final int SCORE_TEXT_X = 650;
    public static final int SCORE_TEXT_Y = 30;
    public static final int DEAD_TEXT_X = 360;
    public static final int DEAD_TEXT_Y = 240;
    
    // Character constants
    public static final int MAX_PLAYER_HP = 20;
    public static final int MOVE_SPEED = 6;
    public static final int JUMP_SPEED = -15;    
    
    // Enemy constants
    public static final int HB_START_X = 340;
    public static final int HB_START_Y = 360;
    public static final int HB_SPACING = 360;
    public static final int HELIBOY_IMG_CENTER = 48;
    public static final int MAX_ENEMY_HP = 5;
    public static final int HIT_DELAY = 20;
    
    // Bullet constants
    public static final int BULLET_SPEED = 7;
    public static final int BULLET_WIDTH = 10;
    public static final int BULLET_HEIGHT = 5;
    public static final int BULLET_START_X_OFFSET = 50;
    public static final int BULLET_START_Y_OFFSET = 25;
    
    // Animation constants
    public static final int CHARACTER_ANIMATION_DURATION = 1250;
    public static final int CHARACTER_ANIMATION_DURATION2 = 50;
    public static final int HELIBOY_ANIMATION_DURATION = 100;
    public static final int CHARACTER_ANIMATION_DELAY = 10;
    public static final int HELIBOY_ANIMATION_DELAY = 50;
    
    // Background constants
    public static final int BG_PROPORTIONAL_SLOW_CHANGE = 3;
}
