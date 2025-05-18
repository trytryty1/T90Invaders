package game_states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import invaders.GameManager;
import invaders.SoundManager;

public class StartGameState extends BasicGameState {

	int optionSelected;
	boolean isUpArrowDown = false, isDownArrowDown = false;
	String[] options;
	
	@Override
	public void init(GameContainer gc, StateBasedGame arg1) throws SlickException {
		optionSelected = 0;
		options = new String[] {
				"Start Game", "Options", "Exit Game"
		};
		//this will load all the sounds
		SoundManager.getSoundManager();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g) throws SlickException {
		g.setColor(Color.yellow);
		g.fillOval(GameRunningState.GAME_SIZE.getWidth() / 2 - 10, optionSelected*50 +30, 20, 20);
		g.setColor(Color.blue);
		for(int i = 0; i < options.length; ++i) {
			g.drawString(options[i] ,GameRunningState.GAME_SIZE.getWidth()/2 - g.getFont().getWidth(options[i])/2, i * 50 + 30);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame gameStates, int arg2) throws SlickException {
		Input in = gc.getInput();
		if(in.isKeyDown(Input.KEY_UP) || in.isControllerUp(Input.ANY_CONTROLLER)) {
			if(!isUpArrowDown) {
				SoundManager.getSoundManager().playSound(SoundManager.BLIPSELECT, false);
				optionSelected = optionSelected > 0 ? optionSelected-1 :  0;
				isUpArrowDown = true;
			}
		} else {
			isUpArrowDown = false;
		}
		if(in.isKeyDown(Input.KEY_DOWN) || in.isControllerDown(Input.ANY_CONTROLLER)) {
			if(!isDownArrowDown) {
				SoundManager.getSoundManager().playSound(SoundManager.BLIPSELECT, false);
				optionSelected = optionSelected < options.length - 1 ? optionSelected+1 :  options.length-1;
				isDownArrowDown = true;
			}
		} else {
			isDownArrowDown = false;
		}
		if(in.isKeyDown(Input.KEY_ENTER) || in.isButton1Pressed(Input.ANY_CONTROLLER)) {
			if(options[optionSelected].equals("Start Game")) {
				gameStates.enterState(GameStates.GAME_ENGINE_STATE);
			} else if(options[optionSelected].equals("Exit Game")){
				if(isDownArrowDown && isUpArrowDown) {
					GameManager.getGameManager().setSuperMode(true);
				} else {
					System.exit(0);
				}
			} else if(options[optionSelected].equals("Options")) {
				gameStates.enterState(GameStates.OPTION_STATE);
			}
		}
	}

	@Override
	public int getID() {
		return GameStates.START_GAME_STATE;
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		// TODO Auto-generated method stub
		super.enter(container, game);
		GameManager.getGameManager().setSuperMode(false);
	}

}
