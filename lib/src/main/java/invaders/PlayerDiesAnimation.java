package invaders;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;

public class PlayerDiesAnimation {
	Animation animation;
	//45,180,190,20
	boolean isActive;
	
	
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public PlayerDiesAnimation() {
	}
	
	public void setupAnimationFromPoint(Point currentLoc) {
		//build the animation
		animation = new Animation();
		
		//max distance if 198
		float percentToTravel =  (140 - currentLoc.getX()) / 140;
		int initialTime = (int) (200 + (3200 * percentToTravel));
		
		animation.addSegment(new Segments(currentLoc, new Point(140, currentLoc.getY()), 200,  (int) initialTime));
		animation.addSegment(new Segments(new Point(140, currentLoc.getY()), 
				new Point (140, 186), initialTime, initialTime + 500));

		int numDeaths = GameManager.getGameManager().getDeaths();
		int[] xCoords = {212, 198, 184};
		if(numDeaths < xCoords.length) {
			animation.addSegment(new Segments(new Point(140, 186), new Point (xCoords[numDeaths], 186), initialTime + 500, initialTime + 1500));
		}
		
	}
	
	public void stopAnimation() {
		this.isActive = false;
	}
	
	public void startAnimation() {
		this.isActive = true;
	}
	
	public void update(int delta) {
		animation.update(delta);
		
	}
	
	public void render(Graphics g) {
		animation.render(g);
	}
	
	public boolean isDone() {
		return animation.isDone();
	}
	
	public boolean isActive() {
		return this.isActive;
	}
}
