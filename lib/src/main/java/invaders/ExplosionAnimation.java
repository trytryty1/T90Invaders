package invaders;
import org.newdawn.slick.Animation;

public class ExplosionAnimation extends Animation{

	Animation animation;
	float x,y;
	
	public ExplosionAnimation(Animation animation, float x, float y) {
		this.animation = animation;
		this.x  = x;
		this.y = y;
	}

	public Animation getAnimation() {
		return animation;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
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
}
