package invaders;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public class Barricade extends GameObject {
	
	private boolean[][] activeBarricade = {
			{false,  false,  false,  false,  true,  true,  true,  true, true },		
			{false,  false,  false,  true,  true,  true,  true,  true, true },		
			{false,  false,  false,  true,  true,  true,  true,  true, true },		
			{false,  false,  true,  true,  true,  true,  true,  true, true },		
			{false,  false,  true,  true,  true,  true,  true,  true, true },		
			{false,  true,  true,  true,  true,  true,  true,  true, true },		
			{false,  true,  true,  true,  true,  true,  true,  true, true },		
			{true,  true,  true,  true,  true,  true,  true,  true, false },		
			{true,  true,  true,  true,  true,  true,  true,  true, false },		
			{true,  true,  true,  true,  true,  true,  true,  false, false },		
			{true,  true,  true,  true,  true,  true,  true,  true, false },		
			{true,  true,  true,  true,  true,  true,  true,  true, false },		
			{true,  true,  true,  true,  true,  true,  true,  true, true },		
			{false,  true,  true,  true,  true,  true,  true,  true, true },		
			{false,  true,  true,  true,  true,  true,  true,  true, true },		
			{false,  false,  true,  true,  true,  true,  true,  true, true },		
			{false,  false,  true,  true,  true,  true,  true,  true, true },		
			{false,  false,  false,  true,  true,  true,  true,  true, true },		
			{false,  false,  false,  true,  true,  true,  true,  true, true },		
			{false,  false,  false,  false,  true,  true,  true,  true, true }		

	};
	
	
	public Barricade(float x, float y) {
		super(x, y, 20, 9);
	}
	
	@Override
	public void render(Graphics g) {
		super.render(g);
		g.setColor(Color.white);
		for(int i = 0; i < activeBarricade.length; ++i) {
			for(int t = 0; t < activeBarricade[i].length; ++t) {
				if(activeBarricade[i][t])
					g.fillRect(x+i, y+t, 1, 1);
			}
		}
	}
	public static final int UP = 1;
	public static final int DOWN = 2;
	
	public void collide(Bullet bullet, int direction) {
		Rectangle bounds = bullet.GetBounds();
		if(this.GetBounds().intersects(bounds)) {
			System.out.println("got intersection");
			int intersectY = 0;
			int intersectX = (int)(bullet.getX() - this.getX());
			if(direction == UP) {
				intersectY = (int)( this.getY() - bullet.getY());
			} else if(direction == DOWN) {
				intersectY = (int)(bullet.getY() + bullet.height - this.getY());
			}
			
			System.out.println("x:" + intersectX + ", y: " + intersectY);
		
			if(intersectX >= 0 && intersectX < this.activeBarricade.length && intersectY >= 0 && intersectY < this.activeBarricade[0].length) {
				if(this.activeBarricade[intersectX][intersectY]) {
					if(direction == DOWN) {
						intersectY = intersectY + 2 > activeBarricade[0].length - 1 ? activeBarricade[0].length - 1 : intersectY + 2;               
						for (int i = 0; i <= intersectY; ++i) {
							for(int t = intersectX - 1; t < intersectX + 1; ++t) {
								if(t >= 0 && t <= activeBarricade.length && i >= 0 && i < activeBarricade[0].length) {
									this.activeBarricade[t][i] = false;
								}
							}
						}
						bullet.setAlive(false);
						this.activeBarricade[intersectX][intersectY] = false;
					} else if(direction == UP) {
//						intersectY = intersectY + 2 < 0 ? 0 : intersectY + 2;               
//						for (int i = intersectY; i >= 0; --i) {
//							for(int t = intersectX - 1; t < intersectX + 1; ++t) {
//								if(t >= 0 && t <= activeBarricade.length && i >= 0 && i < activeBarricade[0].length) {
//									this.activeBarricade[t][i] = false;
//								}
//							}
//						}
						bullet.setAlive(false);
//						this.activeBarricade[intersectX][intersectY] = false;						bullet.setAlive(false);
//						this.activeBarricade[intersectX][intersectY] = false;
					}
				}
				
				
				
			}
		}
		
	}
}
