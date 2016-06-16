/*
 * File name: Animation.java
 * Date Created: June 13, 2016
 * Description:
 */
package game.animation;

import java.awt.Image;
import java.util.ArrayList;

/**
 * 
 * @author David
 */
public class Animation {
	
	private ArrayList<AnimationFrame> frames;
	private int currentFrame;
	private long animationTime, totalDuration;

	public Animation() {
		frames = new ArrayList<>();
		setTotalDuration(0);
		
		// Need to synchronize this?
		synchronized (this) {
			setCurrentFrame(0);			
			setAnimationTime(0);
		}
	}
	
	public synchronized void addFrame(Image image, long duration) {
		setTotalDuration(getTotalDuration() + duration);
		getFrames().add(new AnimationFrame(image, getTotalDuration()));
	}
	
	public synchronized void update(long elapsedTime) {
		if (!getFrames().isEmpty()) {
			setAnimationTime(getAnimationTime() + elapsedTime);
			
			if (getAnimationTime() >= getTotalDuration()) {
				setAnimationTime(getAnimationTime() % getTotalDuration());
				setCurrentFrame(0);
			}
			
			while (getAnimationTime() > getFrame(getCurrentFrame()).endTime) {
				setCurrentFrame(getCurrentFrame() + 1);
			}
		}
	}
	
	public synchronized Image getImage() {
		if (getFrames().isEmpty()) {
			return null;
		}
		return getFrame(getCurrentFrame()).image;
	}
	
	private AnimationFrame getFrame(int index) {
		return (AnimationFrame) getFrames().get(index);
	}
	
	
	private class AnimationFrame {
		
		Image image;
		long endTime;
		
		public AnimationFrame(Image image, long endTime) {
			this.image = image;
			this.endTime = endTime;
		}
	}


	public synchronized ArrayList<AnimationFrame> getFrames() {
		return frames;
	}
	
	public synchronized void setFrames(ArrayList<AnimationFrame> frames) {
		this.frames = frames;
	}
	
	public synchronized int getCurrentFrame() {
		return currentFrame;
	}
	
	public synchronized void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}
	
	public synchronized long getAnimationTime() {
		return animationTime;
	}
	
	public synchronized void setAnimationTime(long animationTime) {
		this.animationTime = animationTime;
	}
	
	public synchronized long getTotalDuration() {
		return totalDuration;
	}
	
	public synchronized void setTotalDuration(long totalDuration) {
		this.totalDuration = totalDuration;
	}
	
}
