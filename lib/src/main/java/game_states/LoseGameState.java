package game_states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import invaders.GameManager;

public class LoseGameState  extends BasicGameState {

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		g.setColor(Color.red);
		g.drawString("YOU LOSE :(", 100, 100);
		g.drawString("Score: " + GameManager.getGameManager().getScore(), 100, 50);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// TODO Auto-generated method stub
		if(container.getInput().isKeyDown(Input.KEY_ENTER)) {
			game.enterState(GameStates.START_GAME_STATE);
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return GameStates.LOSING_STATE;
	}

}