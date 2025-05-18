
package game_states;

import java.util.ArrayList;

import org.lwjgl.util.Dimension;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import invaders.Barricade;
import invaders.Base;
import invaders.EnemyBullet;
import invaders.ExplosionAnimation;
import invaders.GameBackground;
import invaders.GameManager;
import invaders.Invader;
import invaders.InvaderManager;
import invaders.PlayerDiesAnimation;
import invaders.SoundManager;
import invaders.SpaceShip;
import invaders.TextObject;
import invaders.UFO;

public class GameRunningState extends BasicGameState {

	private SpaceShip spaceShip;
	InvaderManager invaderManager;
	GameBackground background;
	Base base;
	public static final Dimension GAME_SIZE = new Dimension(280, 210);
	public static final int UFO_KILL_SCORE = 500;
	
	ArrayList<Barricade> barricades;
	
	boolean paused = false;
	PlayerDiesAnimation deathAnimation = new PlayerDiesAnimation();
	ArrayList<ExplosionAnimation> explotions;
	SpriteSheet explotionSpriteSheet;
	int[] UFO_Timing = {10000, 30000, 70000, 100000};
	int currentCounter;
	private int lastCounter;
	private TextObject textToDisplay;	
	
	UFO ufo;
	
	boolean transitionToUFOState;
	
	@Override
	public void init(GameContainer gc, StateBasedGame stateBasedGame) throws SlickException {
		background = new GameBackground(GAME_SIZE.getWidth(), GAME_SIZE.getHeight(), 2);
		setupGame();
		explotionSpriteSheet = new SpriteSheet(new Image(GameStates.RESOURCE_PATH + "images/explosion.png").getScaledCopy(0.3f), (int)(64*0.3), (int)(64*0.3));
		explotions = new ArrayList<>();
		transitionToUFOState = false;
	}

	public void setupGame() {
		GameManager.getGameManager().setLevel(0);
		GameManager.getGameManager().reset();
		barricades = new ArrayList<Barricade>();
		createBarricades();
		base = new Base(10, 190, 200, 10);
		spaceShip = new SpaceShip(100f, 170f);		
		invaderManager = new InvaderManager(spaceShip, GAME_SIZE.getWidth(), GAME_SIZE.getHeight());
		invaderManager.setLevel(GameManager.getGameManager().getLevel());
	}

//	/45,180,190,20
	public void createBarricades() {
		barricades.clear();
		for(int i = 0; i < 3; ++i) {
			barricades.add(new Barricade(60 + i * 70, 150));
		}
	}
	@Override
	public void render(GameContainer gc, StateBasedGame stateBasedGame, Graphics g) throws SlickException {
		invaderManager.render(g);
		
		base.render(g);
		for(Barricade barricade: barricades) {
			barricade.render(g);
		}
		if(paused && !deathAnimation.isActive()) {
			g.setColor(Color.red);
			g.drawString("Game Paused", 100, 100);
		} 
		if(deathAnimation.isActive()) {
			deathAnimation.render(g);
		} else {
			spaceShip.render(g);

		}
		for(int i = 0; i < explotions.size(); ++i) {
			explotions.get(i).getAnimation().draw(explotions.get(i).getX(),explotions.get(i).getY());
		}
		explotions.clear();
		
		if(ufo != null) {
			ufo.render(g);
		}
		
		if(textToDisplay != null) {
 			textToDisplay.render(g);
		}
	}


	@Override
	public void update(GameContainer gc, StateBasedGame stateBasedGame, int delta) throws SlickException {
		
		if (gc.getInput().isKeyDown(Input.KEY_BACK)) {
			paused = !paused;
		}
		if (!paused) {
			
			if(textToDisplay != null) {
				textToDisplay.update(delta);
				if(!textToDisplay.isAlive()) {
					textToDisplay = null;
				}
			}
			
			lastCounter = currentCounter;
			currentCounter += delta;
			for(int i =0; i < UFO_Timing.length; ++i) {
				if(UFO_Timing[i] > lastCounter && UFO_Timing[i] < currentCounter) {
					ufo = new UFO(260, 25, 12, 6);
					SoundManager.getSoundManager().playSound(SoundManager.UFO, true);
				}
			}

			if(ufo != null) {
				ufo.update(delta);
				if(!ufo.isAlive()) {
					ufo = null;
					SoundManager.getSoundManager().stopPlayingSound(SoundManager.UFO);
				}
			}
			
			for(int i = 0; i < explotions.size(); ++i) {
				explotions.get(i).update(delta);
			}
			spaceShip.update(gc.getInput(), delta);
			invaderManager.update(delta, stateBasedGame);
			if(GameManager.getGameManager().getIsLost()) {
				stateBasedGame.enterState(GameStates.START_GAME_STATE);
			} else {
				
			
				if (spaceShip.isBulletAlive()) {
					
					Rectangle bulletBounds = spaceShip.getBulletBounds();
					
					for(Barricade barricade: barricades) {
						barricade.collide(spaceShip.getBullet(), Barricade.UP);
					}
		
					//check to see if it hits an invader
					Invader invader = invaderManager.checkAndProcessHit(bulletBounds);
					if (invader != null) {
						Point poc = invader.getActualLoc();
						explotions.add(new ExplosionAnimation(new Animation(explotionSpriteSheet, 80), poc.getX() - 4, poc.getY() - 4));
						
						GameManager.getGameManager().incrementScore(invader.getScoreForInvader());
						SoundManager.getSoundManager().playSound(SoundManager.INVADERKILLED, false);
						spaceShip.destroyBullet();
					} else if(ufo != null) {
						//check to see if it hits a UFO
						Rectangle ufoBounds = ufo.GetBounds();
						if(ufoBounds.intersects(bulletBounds)) {
							//ufo is hit
							SoundManager.getSoundManager().playSound(SoundManager.UFOEXPLOSION, false);
	
							GameManager.getGameManager().incrementScore(UFO_KILL_SCORE);
							textToDisplay = new TextObject(ufo.getX() - 4, ufo.getY(), "" + UFO_KILL_SCORE, 2000);
							ufo = null;
							SoundManager.getSoundManager().stopPlayingSound(SoundManager.UFO);
							spaceShip.destroyBullet();
						}
					}
				}
	
				EnemyBullet[] enemyBullets = invaderManager.getEnemyBullets();
				for(int i = 0; i < enemyBullets.length; ++i) {
					if(enemyBullets[i] != null) {
						if(spaceShip.GetBounds().intersects(enemyBullets[i].GetBounds())) {
							invaderManager.destroyBullet(i);
							SoundManager.getSoundManager().playSound(SoundManager.EXPLOSION, false);
							if(GameManager.getGameManager().getLives() > 0) {
								deathAnimation.setupAnimationFromPoint(spaceShip.getLocation());
								deathAnimation.startAnimation();
								this.paused = true;
							} else {
								stateBasedGame.enterState(GameStates.START_GAME_STATE);
							}
						} else {
							//check to see if it hits an invader
							for(Barricade barricade: barricades) {
								if(barricade.GetBounds().intersects(enemyBullets[i].GetBounds())) {
							
									barricade.collide(enemyBullets[i], Barricade.DOWN);
								}
							}
						}
					}
				} 
				
				//check to see if all the aliens are dead
				if (invaderManager.allAliensDead()) {
					//reset the ufo counters for the next level
					ufo = null;
					this.currentCounter = 0;
					this.lastCounter = 0;
					GameManager.getGameManager().setLevel(GameManager.getGameManager().getLevel() + 1);
					if(GameManager.getGameManager().didWin()) {
						stateBasedGame.enterState(GameStates.START_GAME_STATE);
					} else {
						invaderManager.setLevel(GameManager.getGameManager().getLevel());
						transitionToUFOState = true;
						stateBasedGame.enterState(GameStates.UFO_STATE);
					}
				}
			} 
			
			//check to see if all the aliens are dead
			if (invaderManager.allAliensDead()) {
				//reset the ufo counters for the next level
				ufo = null;
				this.currentCounter = 0;
				this.lastCounter = 0;
				GameManager.getGameManager().setLevel(GameManager.getGameManager().getLevel() + 1);
				if(GameManager.getGameManager().didWin()) {
					stateBasedGame.enterState(GameStates.WINNING_STATE);
				} else {
					invaderManager.setLevel(GameManager.getGameManager().getLevel());
					transitionToUFOState = true;
					stateBasedGame.enterState(GameStates.UFO_STATE);
				// barricade.update(delta);
	
				background.update(delta);
				}
			}
			// barricade.update(delta);

			background.update(delta);
		} else {
			//paused - check to see if it is for a death
			if(deathAnimation.isActive()) {
				deathAnimation.update(delta);
				if(deathAnimation.isDone()) {
					deathAnimation.stopAnimation();
					GameManager.getGameManager().decreaseLives();
					if(GameManager.getGameManager().getLives() == 0) {
						stateBasedGame.enterState(GameStates.LOSING_STATE);
					} 
				}
			}
		}
		
	}

	@Override
	public int getID() {
		return GameStates.GAME_ENGINE_STATE;
	}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame game) throws SlickException {
		super.enter(gc, game);
		SoundManager.getSoundManager().playMusic(SoundManager.MAIN_STATE);
		if(!transitionToUFOState) {
			//only do this if not coming back from a UFO state
			setupGame();
		} else {
			transitionToUFOState = false;
			createBarricades();
		}
		
		
	}
	
	
	@Override
	public void leave(GameContainer gc, StateBasedGame game) throws SlickException {
		super.leave(gc, game);
		SoundManager.getSoundManager().fadeMusicOff(SoundManager.MAIN_STATE, 1000);
		SoundManager.getSoundManager().stopPlayingAllSound();
	}

}
