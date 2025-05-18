package invaders;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public abstract class GameObject {
	
	private boolean alive;
	protected float x, y;
	protected int width, height;
	
	public GameObject(float x, float y, int width, int height) {
		this.alive = true;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void render(Graphics g) {
		
	}
	
	public void update(int delta) {
		
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public Rectangle GetBounds() {
		return new Rectangle(x, y, width, height);
	}
}
