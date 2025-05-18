package game_states;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class GameStates extends StateBasedGame {
	
	public static final int GAME_ENGINE_STATE = 0;
	public static final int START_GAME_STATE = 1;
	public static final int UFO_STATE = 2;
	public static final int OPTION_STATE = 3;
	public static final int WINNING_STATE = 4;
	public static final int LOSING_STATE = 5;
	
	public static final String RESOURCE_PATH = "invaders2018/res/";
	
	public static void main(String args[]) throws SlickException {
		AppGameContainer gc = new AppGameContainer(new ScalableGame(new GameStates("T90Invaders"), 280, 210, true));
		System.out.println(args.toString());
		if(args.length != 0) {

			gc.setFullscreen(true);
		} else {
			gc.setDisplayMode(280*4, 210*4, false);
		}
		gc.setTargetFrameRate(60);
		gc.setShowFPS(true);
		gc.setAlwaysRender(true);
		gc.start();
		
	}

	public GameStates(String name) {
		super(name);
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		addState(new StartGameState());
		addState(new GameRunningState());
		addState(new UFOState());
		addState(new OptionsState());
		addState(new LoseGameState());
		addState(new WinGameState());
	}
	
}