package invaders;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

public class Animation {

	ArrayList<Segments> segments = new ArrayList<Segments>();
	
	public void update(int delta) {
		for(Segments segment: segments) {
			segment.update(delta);
		}
	}
	
	public void addSegment(Segments segment) {
		segments.add(segment);
	}
	
	public void startAnimation() {
		for(Segments segment: segments) {
			segment.startAnimation();
		}		
	}
	
	public void render(Graphics g) {
		for(Segments segment: segments) {
			segment.renderInvader(g);
		}		

	}
	public boolean isDone() {
		for(Segments segment: segments) {
			if(!segment.isDone()) {
				return false;
			}
		}
		return true;
	}
}
