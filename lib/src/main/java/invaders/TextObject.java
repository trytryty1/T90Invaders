package invaders;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;

public class TextObject extends GameObject {

	Font font = new TrueTypeFont(new java.awt.Font(java.awt.Font.DIALOG, java.awt.Font.PLAIN, 8), true);

	String text;
	int counter;
	int duration;
	
	public TextObject(float x, float y, String text, int duration) {
		super(x, y, 0, 0);
		this.text = text;
		this.duration = duration;
	}
	
	@Override
	public void render(Graphics g) {
		super.render(g);
		if(isAlive()) {
			g.setFont(font);
			g.setColor(Color.white);
			g.drawString(text, x, y);
		}
		
	}
	
	@Override
	public void update(int delta) {
		super.update(delta);
		counter += delta;
		if(counter > duration) {
			this.setAlive(false);
		}
		
	}
	

}
