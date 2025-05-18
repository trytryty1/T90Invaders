package game_states;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import invaders.GameManager;

public class OptionsState extends BasicGameState {

	private String[] options;
	private int optionSelected;
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		options = new String[] {
				"Difficulty", "Sound", "Back"
		};
		optionSelected = 0;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.setColor(Color.yellow);
		g.fillOval(GameRunningState.GAME_SIZE.getWidth() / 2 - 10, optionSelected*50 +30, 20, 20);
		
		for(int i = 0; i < options.length; ++i) {
			if(options[i].equals("Sound") && !GameManager.getGameManager().isSound()) {
				g.setColor(Color.red);
				g.drawLine(GameRunningState.GAME_SIZE.getWidth()/2 - g.getFont().getWidth(options[i])/2, i * 50 + 30, GameRunningState.GAME_SIZE.getWidth()/2 - g.getFont().getWidth(options[i])/2 + g.getFont().getWidth((options[optionSelected])), i * 50 + 30);
			}
			g.setColor(Color.blue);
			g.drawString(options[i] ,GameRunningState.GAME_SIZE.getWidth()/2 - g.getFont().getWidth(options[i])/2, i * 50 + 30);
		}
	}

	boolean isUpArrowDown = false, isDownArrowDown = false;
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		Input in = container.getInput();
		if(in.isKeyDown(Input.KEY_UP) || in.isControllerUp(Input.ANY_CONTROLLER)) {
			if(!isUpArrowDown) {
				optionSelected = optionSelected > 0 ? optionSelected-1 :  0;
				isUpArrowDown = true;
			}
		} else {
			isUpArrowDown = false;
		}
		if(in.isKeyDown(Input.KEY_DOWN) || in.isControllerDown(Input.ANY_CONTROLLER)) {
			if(!isDownArrowDown) {
				optionSelected = optionSelected < options.length - 1 ? optionSelected+1 :  options.length-1;
				isDownArrowDown = true;
			}
		} else {
			isDownArrowDown = false;
		}
		if(in.isKeyDown(Input.KEY_ENTER)) {
			if(options[optionSelected].equals("Back")) {
				game.enterState(GameStates.START_GAME_STATE);
			} else if(options[optionSelected].equals("Sound")) {
				GameManager.getGameManager().setSound(!GameManager.getGameManager().isSound());
			}
		}
	}

	@Override
	public int getID() {
		return GameStates.OPTION_STATE;
	}
	
}
