package invaders;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import game_states.GameStates;

public class UFO extends GameObject {

	private float speed = 0.02f;
	private int gameWidth = 280, gameHeight = 210;
	

	private boolean isAlive;
	Image img;
	Image scaledImg;
	
	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public UFO(float x, float y, int width, int height) {
		super(x, y, width, height);
		try {
			img = new Image(GameStates.RESOURCE_PATH + "images/ufo.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}

		this.x = x;
		this.y = y;
		this.setWidth(width);
		this.height = height;
		this.isAlive = true;
	
	}

	public void update(int delta) {
		this.x -= delta * speed;
		if(this.x +  width > gameWidth || this.x < 0) {
			this.isAlive = false;
		}
	}
	
	public void render(Graphics g) {
		if(isAlive) {
			if(scaledImg != null) {
				g.drawImage(scaledImg.getScaledCopy(width, height), x, y);
			} else {
				g.drawImage(img.getScaledCopy(width, height), x, y);				
			}
		}
	}
	
	public void reverseDirection() {
		speed = speed * -1;
	}
	
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
		scaledImg = img.getScaledCopy(width, height);
	}

}
