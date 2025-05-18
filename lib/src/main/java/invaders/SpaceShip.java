package invaders;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

import game_states.GameStates;

public class SpaceShip extends GameObject {

	Image img;
	private float speed;
	private Bullet bullet;
	
	private static final int SPACESHIP_SIZE = 9;
	
	public SpaceShip(float x, float y) {
		super(x, y, SPACESHIP_SIZE, SPACESHIP_SIZE);
		try {
			img = new Image(GameStates.RESOURCE_PATH + "images/player.png").getScaledCopy(width, height);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		speed = 0.1f;
		bullet = new Bullet(x,y);
	}
	
	@Override
	public void render(Graphics g) {
		super.render(g);
		g.drawImage(img, x, y);
		bullet.render(g);
	}
	
	public void update(Input in, int delta) {
		if(x > 45) {
			if(in.isKeyDown(Input.KEY_LEFT) || in.isControllerLeft(Input.ANY_CONTROLLER)) {
				x -= speed * delta;
			}
		}
		if(x + width < 235) {
			if(in.isKeyDown(Input.KEY_RIGHT) || in.isControllerRight(Input.ANY_CONTROLLER)) {
				x += speed * delta;	
			}
		}
		if(in.isKeyDown(Input.KEY_SPACE) || in.isButton1Pressed(Input.ANY_CONTROLLER)) {
			if(!bullet.isAlive())
				fireBullet();
		}
		if(bullet.isAlive()) {
			bullet.update(delta);
		}
	}
	
	private void fireBullet() {
		SoundManager.getSoundManager().playSound(SoundManager.SHOOT, false);
		bullet.setAlive(true);
		bullet.x = x + width/2 - 1;
		bullet.y = y;
	}
	
	public  Rectangle getBulletBounds() {
		return bullet.getBoundedRectangle();
	}

	public void destroyBullet() {
		bullet.setAlive(false);
		
	}

	public boolean isBulletAlive() {
		return bullet.isAlive();
	}
	
	public Point getLocation() {
		return new Point(x,y);
	}

	public Bullet getBullet() {
		// TODO Auto-generated method stub
		return bullet;
	}
}
