package invaders;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public class Bullet extends GameObject {
	
	private float speed;
	
	public Bullet(float x, float y) {
		super(x, y, 2, 5);
		setAlive(false);
		speed = 0.125f;
		if(GameManager.getGameManager().isSuperMode()) {
			speed = 1;
		}
	}
	
	public Bullet(float x, float y, int width, int height) {
		super(x, y, width, height);
		setAlive(false);
		speed = 0.3f;
	}
	
	@Override
	public void render(Graphics g) {
		super.render(g);
		if(isAlive()) {
			g.setColor(Color.blue);
			g.fillRect(x, y, width, height);
		}
	}

	public void update(int delta) {
		y-= speed*delta;
		if(y < 0) {
			setAlive(false);
		}
	}
	
	public Rectangle getBoundedRectangle() {
		return new Rectangle(x,y,width,height);
	}
	
}
