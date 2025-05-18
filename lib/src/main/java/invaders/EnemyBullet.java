package invaders;

import org.newdawn.slick.geom.Point;

public class EnemyBullet extends Bullet {
	
	private static final int length = 7;
	private static final int width = 2;
	
	private float speed = 0.05f;
	
	public EnemyBullet(Point loc) {
		super(loc.getX(), loc.getY(), width, length);
		this.x = loc.getX();
		this.y = loc.getY();
		this.setAlive(true);
	}

	public boolean move(int delta) {
		this.y += speed * delta;
		if(this.y > 180-length) {
			return false;
		}
		return true;
		
	}
}
