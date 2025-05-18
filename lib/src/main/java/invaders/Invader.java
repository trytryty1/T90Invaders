package invaders;

import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

import game_states.GameStates;

public class Invader extends GameObject {
	
	private Image img;
	private Point offset;
	private Point locInGang;
	public static final int INVADER_SIZE = 8;
	
	
	private int counterToShoot = 0;
	private int timeBetweenFire = 500;
	
	private int scoreForInvader;

	private boolean isShooter;
	
	
	public Invader(int type, int scoreValue) {
		super(0,0,0,0);
		try {
			img = new Image(GameStates.RESOURCE_PATH + "images/SpaceInvaders.png").getSubImage(0, 13*(type%6), 22, 13).getScaledCopy(INVADER_SIZE, INVADER_SIZE);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		this.scoreForInvader = scoreValue;
	}
	
	public void move() {
		
	}
	
	public void renderInvader(Graphics g, float x, float y) {
		if(isAlive()) {
			g.drawImage(img, x, y);
		}
	}
	
	@Override
	public void render(Graphics g) {
		
		super.render(g);
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	public Point getOffset() {
		return offset;
	}

	public void setOffset(Point offset) {
		this.offset = offset;
	}

	public Point getActualLoc() {
		//multiply by 1.5 to get a half an alien space in-between
		return new Point(this.offset.getX() + this.getLocInGang().getX() * INVADER_SIZE * 1.5f,
				this.offset.getY() + this.getLocInGang().getY() * INVADER_SIZE * 2.0f );
	}

	public Point getLocInGang() {
		return locInGang;
	}

	public void setLocInGang(Point locInGang) {
		this.locInGang = locInGang;
	}
	
	public Rectangle getBoundingRectangle() {
		Point loc = getActualLoc();
		return new Rectangle(loc.getX(), loc.getY(), INVADER_SIZE, INVADER_SIZE);
	}

	public void kill() {
		this.setAlive(false);
		
	}

	public boolean isShooter() {
		return isShooter;
	}

	public void setShooter(boolean isShooter) {
		this.isShooter = isShooter;
	}

	//this method will return an enemyBullet if it decides to fire or null otherwise
	public EnemyBullet checkForShot(int delta, int level) {
		int probabilityOfShot = (level + 1) * 15;
		if(counterToShoot + delta > timeBetweenFire) {
			counterToShoot = 0;
			Random rnd = new Random();
			
			if(rnd.nextInt(100) < probabilityOfShot) {
				return new EnemyBullet(new Point(this.getActualLoc().getX() + 4, 
						this.getActualLoc().getY() + INVADER_SIZE));	
			}
		} else {
			counterToShoot += delta;
		}
		return null;
	}

	public int getScoreForInvader() {
		return scoreForInvader;
	}

	public void setScoreForInvader(int scoreForInvader) {
		this.scoreForInvader = scoreForInvader;
	}
	
}
