package invaders;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

import game_states.GameStates;

public class Segments {
	private Point start;
	private Point end;
	private Point currentLoc;
		
	private int startTime;
	private int endTime;
	private int totalAnimationTime;
	private int deltaX;
	private int deltaY;
	
	private int currentTime;
	
	private Image destroyedSpaceShip;
	
	public Segments(Point start, Point end, int startTime, int endTime) {
		super();
		currentLoc = new Point(0.0f, 0.0f);
		this.start = start;
		this.end = end;
		this.startTime = startTime;
		this.endTime = endTime;
		this.totalAnimationTime = this.endTime - this.startTime;
		this.deltaX = (int) this.end.getX() - (int) this.start.getX();
		this.deltaY = (int) this.end.getY() - (int) this.start.getY();
		
		try {
			destroyedSpaceShip = new Image(GameStates.RESOURCE_PATH + "images/player-dead.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}
	
	public void renderInvader(Graphics g) {
		if(currentTime > startTime && currentTime < endTime) {
			g.drawImage(destroyedSpaceShip, currentLoc.getX(), currentLoc.getY());
		}
	}
	
	public void startAnimation() {
		currentTime = 0;
	}
	
	public void update(int delta) {
		currentTime += delta;
		if(currentTime > startTime && currentTime < endTime) {
			int deltaTime = currentTime - startTime;
			float percentComplete = (float) deltaTime / (float) totalAnimationTime;
			currentLoc.setLocation(start.getX() + percentComplete * deltaX, 
					start.getY() + percentComplete * deltaY);
			
		}
	}
	
	public boolean isDone() {
		if(currentTime > endTime) {
			return true;
		}
		return false;
	}
}
