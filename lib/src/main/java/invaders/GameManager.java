package invaders;

public class GameManager {
	
	private static GameManager gameManager = new GameManager();
	public static GameManager getGameManager() {
		return gameManager;
	}
	
	private static final int SCORE_FOR_NEW_LIFE = 4000;
	
	private int lives;
	private int score;
	private int deaths;
	private boolean superMode;
	private int level = 0;
	private int maxLevels = 5;
	private boolean sound = false;

	private boolean lost;
	
	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public GameManager() {
		lives = 3;
		score = 0;
		deaths = 0;
		superMode = false;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public void addLife() {
		this.lives++;
	}
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		int temp = (int)((float) this.score / (float) SCORE_FOR_NEW_LIFE);
		int temp1 = (int)((float) score / (float) SCORE_FOR_NEW_LIFE);
		
		//if they don't equal each other then a score threshold is crossed
		if(temp != temp1) {
			if(this.deaths > 0) {
				this.deaths--;
				this.lives++;
				SoundManager.getSoundManager().playSound(SoundManager.NEWLIFE, false);
			}
		}
		this.score = score;
	
	}
	
	public void incrementScore(int increment) {
		setScore(this.score + increment);
	}

	public void decreaseLives() {
		--this.lives;
		++this.deaths;
	}

	public boolean isSuperMode() {
		return superMode;
	}

	public void setSuperMode(boolean superMode) {
		this.superMode = superMode;
	}
	
	public void reset() {
		lives = 3;
		score = 0;
		deaths = 0;		
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean didWin() {
		return level > maxLevels;
	}

	public void setLost() {
		lost = true;
		
	}

	public boolean isSound() {
		return sound;
	}

	public void setSound(boolean sound) {
		this.sound = sound;
	}
	
	public boolean getIsLost() {
		return lost;
	}
	
}
