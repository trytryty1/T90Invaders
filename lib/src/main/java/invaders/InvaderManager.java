package invaders;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

public class InvaderManager {

	private static final int INVADERS_ON_X_AXIS = 11;
	private Point offSet;
	private int gameWidth = 280,gameHeight = 210;
	private Point startingOffset = new Point(0.0f,40.0f);
	
	Invader[][] invaders;
	private int level = 0;
	private int speed = 0;
	
	private static int[] speedPerLevel = {200, 175, 150, 125, 110, 90};
	private static int movementPerUpdate;
	private static int verticalMovementPerUpdate =  (int)(Invader.INVADER_SIZE * 1.7f);
	
	private EnemyBullet[] enemyBullets = new EnemyBullet[INVADERS_ON_X_AXIS];

	private int updateCounter = 0;
	
	private static int[][] creaturesAtLevels =
		{
				{3,2,2,1,1},
				{3,3,2,2,1},
				{4,3,3,2,2}, 
				{4,4,3,3,2},
				{5,4,4,3,3},
				{5,5,4,4,3}
		};
	
	public InvaderManager(SpaceShip spaceShip, int width, int height) {
		this.gameWidth = width;
		this.gameHeight = height;
	}
	
	public void setLevel(int level) {
		this.level = level;
		speed = getSpeedForLevel(level);
		offSet = new Point(startingOffset.getX(), startingOffset.getY());
		invaders = getInvadersForLevel(this.level);
		movementPerUpdate = 2;
		//clear out any existing bullets
		for(int i = 0; i < enemyBullets.length; ++i) {
			enemyBullets[i] = null;
		}

	}

	private int getSpeedForLevel(int level) {
		if(level < speedPerLevel.length) {
			return speedPerLevel[level];
		}
		return 0;
	}

	public Invader[][] getInvadersForLevel(int level) {
		int[] levelData = creaturesAtLevels[level];
		Invader[][] invaders = new Invader[INVADERS_ON_X_AXIS][creaturesAtLevels[0].length];
		
		for(int y = 0 ; y < invaders[0].length; ++y) {
			for(int x = 0; x < invaders.length; ++x) {
				
				invaders[x][y] = new Invader(levelData[y], (6 - y) * 5);
				invaders[x][y].setLocInGang(new Point(x,y));
				invaders[x][y].setOffset(this.offSet);
				if(y == invaders[0].length - 1) {
					invaders[x][y].setShooter(true);
				}
			}
		}
		return invaders;
	}
	
//	public void nextLevel() {
//		++level;
//		if(level < speedPerLevel.length ) {
//			setLevel(level);
//		} else {
//			//change game state
//		}
//	
//	}
	
	public void allInvadersDead() {
		
	}
	
	public void render(Graphics g) {
		
		for(int y = 0 ; y < invaders[0].length; y++) {
			for(int x = 0; x < invaders.length; ++x) {
				Point loc = invaders[x][y].getActualLoc();
				invaders[x][y].renderInvader(g, loc.getX(), loc.getY());
			}
		}
		
		//draw bullets
		for(int i = 0; i < enemyBullets.length; ++i) {
			if(enemyBullets[i] != null) {
				g.setColor(Color.red);
				g.fillRect(enemyBullets[i].getX(), enemyBullets[i].getY(), 2, 5);
			}
		}
	}

	public void changeOffset(Point offset) {
		for(int y = 0 ; y < invaders[0].length; y++) {
			for(int x = 0; x < invaders.length; ++x) {
				invaders[x][y].setOffset(offset);
			}
		}
	}
	
	public boolean checkForOffScreen() {
		for(int y = 0 ; y < invaders[0].length; y++) {
			for(int x = 0; x < invaders.length; ++x) {
				if(invaders[x][y].isAlive()) {
					Point loc = invaders[x][y].getActualLoc();
					if(loc.getX() + Invader.INVADER_SIZE > gameWidth || loc.getX() < 0) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public void update(int delta, StateBasedGame stateBasedGame) {
		
		//check to see how many invaders are left to determine if speedboost should be applied
		int numEnemies = 0;
		for(int y = invaders[0].length - 1 ; y >= 0 ; y--) {
			for(int x = 0; x < invaders.length; ++x) {
				if(invaders[x][y].isAlive()) {
					numEnemies++;
				}
			}
		}
		updateCounter += delta;
		int speedBoost = 0;
		if(numEnemies < 10) {
			speedBoost = (int)(100.0f * ((10.0f - (float)numEnemies) / 10.0f));
		}
		if(updateCounter > speed - speedBoost) {
			updateCounter -= Math.abs(speed - speedBoost);
			offSet.setX(offSet.getX() + movementPerUpdate );

			changeOffset(offSet);
			
			if(checkForOffScreen()) {
				movementPerUpdate = -1 * movementPerUpdate;
				offSet.setY(offSet.getY() + verticalMovementPerUpdate);
				//reverse the movement and then go the other way
				offSet.setX(offSet.getX() + movementPerUpdate);
				changeOffset(offSet);
			}
			
			if(checkForInvadersGettingUser()) {
				GameManager.getGameManager().setLost();
			}
			 
		} 
		
		//update bullets
		for(int i = 0; i < enemyBullets.length; ++i) {
			if(enemyBullets[i] != null && enemyBullets[i].move(delta) == false) {
				//went off the screen

				enemyBullets[i] = null;
			}
			if(enemyBullets[i] != null) {
				if(!enemyBullets[i].isAlive()) {
					enemyBullets[i] = null;
					
				}
			}
		}
		
		//check if any invaders are going to shoot
		for(int y = invaders[0].length - 1 ; y >= 0 ; y--) {
			for(int x = 0; x < invaders.length; ++x) {
				if(invaders[x][y].isAlive() && invaders[x][y].isShooter() && enemyBullets[x] == null) {
					enemyBullets[x] = invaders[x][y].checkForShot(delta, level);
				}
			}
		}
		
	}

	private boolean checkForInvadersGettingUser() {
		for(int y = invaders[0].length - 1 ; y >= 0 ; y--) {
			for(int x = 0; x < invaders.length; ++x) {
				if(invaders[x][y].isAlive()) {
					Point loc = invaders[x][y].getActualLoc();
					if(loc.getY() + Invader.INVADER_SIZE > 170) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public EnemyBullet[] getEnemyBullets() {
		return this.enemyBullets;
	}
	
	//is enemy hit with user bullet
	public Invader checkAndProcessHit(Rectangle bulletBounds) {
		for(int y = invaders[0].length - 1 ; y >= 0 ; y--) {
			for(int x = 0; x < invaders.length; ++x) {
				if(!invaders[x][y].isAlive()) {
					continue;
				}
				if(invaders[x][y].getBoundingRectangle().intersects(bulletBounds)) {
					invaders[x][y].kill();
					//check for invaders above it to turn them into shooters
					for(int i = y - 1; i >= 0; --i) {
						if(invaders[x][i].isAlive()) {
							boolean shouldBeShooter = true;
							//make sure i didn't hit one in the middle
							for(int t = y + 1; t < invaders[0].length; ++t ) {
								if(invaders[x][t].isAlive()) {
									shouldBeShooter = false;
								}
							}
							if(shouldBeShooter) {
								invaders[x][i].setShooter(true);
							}
							break;
						}
					}
						
					return invaders[x][y];
				}
			}
		}
		return null;
	}

	public boolean allAliensDead() {
		for(int y = invaders[0].length - 1 ; y >= 0 ; y--) {
			for(int x = 0; x < invaders.length; ++x) {
				if(invaders[x][y].isAlive()) {
					return false;
				}
			}
		}
		return true;
	}

	public void destroyBullet(int i) {
		enemyBullets[i] = null;
		
	}
}