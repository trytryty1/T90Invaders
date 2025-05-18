package invaders;


import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;

import game_states.GameStates;

public class Base extends GameObject {
	
	int deadSpaceShips;
	int aliveSpaceShips;
	SpaceShip spaceShip;
	Rectangle location;
	Image inactiveSpaceShip;
	Image destroyedSpaceShip;
	
	
	public Base(float x, float y, int width, int height) {
		//TODO move width height init
		super(45,180,190,20);
		
		try {
			inactiveSpaceShip = new Image(GameStates.RESOURCE_PATH + "images/player-inactive.png");
			destroyedSpaceShip = new Image(GameStates.RESOURCE_PATH + "images/player-dead.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	Font font = new TrueTypeFont(new java.awt.Font(java.awt.Font.DIALOG, java.awt.Font.PLAIN, 10), true);
	
	
	@Override
	public void render(Graphics g) {
		super.render(g);
		g.setFont(font);
		g.setColor(Color.blue);
		g.drawRect(x, y, width, height);
		g.drawString("Score: " + GameManager.getGameManager().getScore(), 0, 0);
		g.drawString("Level: " + (GameManager.getGameManager().getLevel() + 1), 150, 0);
		switch(GameManager.getGameManager().getLives()) {
		case 3:
			g.drawImage(inactiveSpaceShip, 84, 186);
		case 2:
			g.drawImage(inactiveSpaceShip, 60, 186);			
		case 1:
		}
		
		switch(GameManager.getGameManager().getDeaths()) {
		case 2:
			g.drawImage(destroyedSpaceShip, 198, 186);			
		case 1:
			g.drawImage(destroyedSpaceShip, 212, 186);
		}
	}
	
	@Override
	public void update(int delta) {
		super.update(delta);
		
	}
	
}
