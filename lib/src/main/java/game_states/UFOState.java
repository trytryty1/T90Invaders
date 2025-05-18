package game_states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import invaders.Base;
import invaders.GameManager;
import invaders.SoundManager;
import invaders.SpaceShip;
import invaders.UFO;

public class UFOState extends BasicGameState{

	Base base;
	SpaceShip spaceShip;
	UFO ufo;
	boolean paused = false;
	int score;
	int hits = 0;
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		setup();
		
	}

	public void setup() {
		base = new Base(10, 190, 200, 10);
		spaceShip = new SpaceShip(100f, 170f);	
		ufo = new UFO(260, 20 , 12, 6);
		score = 50;
		hits = 0;
		
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		
		base.render(g);
		if(paused ) {
			g.setColor(Color.red);
			g.drawString("Game Paused", 100, 100);
		}
		
		spaceShip.render(g);
		
		if(ufo != null) {
			ufo.render(g);
		}		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame stateBasedGame, int delta) throws SlickException {
		if (gc.getInput().isKeyDown(Input.KEY_BACK)) {
			paused = !paused;
		}
		
		if (!paused) {

			ufo.update(delta);
			if(ufo.isAlive()) {			
			
				spaceShip.update(gc.getInput(), delta);
				if (spaceShip.isBulletAlive()) {
					
					Rectangle bulletBounds = spaceShip.getBulletBounds();
					
					if(ufo != null) {
						//check to see if it hits a UFO
						Rectangle ufoBounds = ufo.GetBounds();
						if(ufoBounds.intersects(bulletBounds)) {
							SoundManager.getSoundManager().playSound(SoundManager.UFOBONUSHIT, false);
							++hits;
							//ufo is hit
							GameManager.getGameManager().incrementScore(hits * 40);
							int ufoWidth = (int)((float)ufo.getWidth() * 0.9f);
							if(ufoWidth < 1) {
								ufoWidth = 1;
							}
 							ufo.setWidth(ufoWidth);
							ufo.reverseDirection();
							spaceShip.destroyBullet();
						}
					}
				}
	
			} else {
				//ufo went out of bounds
				stateBasedGame.enterState(GameStates.GAME_ENGINE_STATE);
			}
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return GameStates.UFO_STATE;
	}

	@Override
	public void enter(GameContainer gc, StateBasedGame game) throws SlickException {
		super.enter(gc, game);
		SoundManager.getSoundManager().playMusic(SoundManager.UFO_STATE);
		setup();
	}
	
	@Override
	public void leave(GameContainer gc, StateBasedGame game) throws SlickException {
		super.leave(gc, game);
		SoundManager.getSoundManager().fadeMusicOff(SoundManager.UFO_STATE, 1000);
	}


}
