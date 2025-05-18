package invaders;

import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;

public class StarMap {
	Point[] stars;
	private int width, height;
	
	public StarMap(int amountOfStars) {
		Random rand = new Random();
		stars = new Point[amountOfStars];
		for(int i = 0; i < amountOfStars; ++i) {
			stars[i] = new Point(rand.nextInt(width), rand.nextInt(height));
		}
	}
	
	public void render(Graphics g, int frame) {
		for(int i = 0; i < stars.length; ++i) {
			//g.draw(new Shape());
		}
	}
}
