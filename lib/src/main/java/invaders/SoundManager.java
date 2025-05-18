package invaders;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import game_states.GameStates;

public class SoundManager {

	private static SoundManager soundManager;
	Sound[] sounds = new Sound[9];
	Music[] music = new Music[2];
	
	public static final int MAIN_STATE = 0;
	public static final int UFO_STATE = 1;
	
	public static final int EXPLOSION = 0;
	public static final int INVADERMOVE = 1;
	public static final int INVADERKILLED = 2;
	public static final int SHOOT = 3;
	public static final int UFO = 4;
	public static final int UFOEXPLOSION = 5;
	public static final int UFOBONUSHIT = 6;
	public static final int BLIPSELECT = 7;
	public static final int NEWLIFE = 8;
	
	
	public static SoundManager getSoundManager() {
		if(soundManager == null) {
			soundManager = new SoundManager();
		}
		return soundManager;
	}
	
	public SoundManager() {
		try {
			sounds[EXPLOSION] = new Sound(GameStates.RESOURCE_PATH + "sounds/explosion.ogg");
			//sounds[INVADERMOVE] = new Sound(GameStates.RESOURCE_PATH + "sounds/invader.ogg");
			sounds[INVADERKILLED] = new Sound(GameStates.RESOURCE_PATH + "sounds/invaderkilled.ogg");
			sounds[SHOOT] = new Sound(GameStates.RESOURCE_PATH + "sounds/shoot.ogg");
			sounds[UFO] = new Sound(GameStates.RESOURCE_PATH + "sounds/ufo_lowpitch.ogg");
			sounds[UFOEXPLOSION] = new Sound(GameStates.RESOURCE_PATH + "sounds/ufoexplosion.ogg");
			sounds[UFOBONUSHIT] = new Sound(GameStates.RESOURCE_PATH + "sounds/ufobonushit.ogg");
			sounds[BLIPSELECT] = new Sound(GameStates.RESOURCE_PATH + "sounds/Blip_Select15.ogg");
			sounds[NEWLIFE] = new Sound(GameStates.RESOURCE_PATH + "sounds/newlife.ogg");
		
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			music[MAIN_STATE] =  new Music(GameStates.RESOURCE_PATH + "sounds/main.ogg");
			music[UFO_STATE] =  new Music(GameStates.RESOURCE_PATH + "sounds/ufo.ogg");

		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	public void playMusic(int musicType) {
		if(musicType < music.length) {
			music[musicType].fade(1000, 0.50f, false);
			music[musicType].loop();//.play();
		}
	}
	
	public boolean isPlayingMusic (int musicType) {
		if(musicType < music.length) {
			return music[musicType].playing();
		}
		return false;
	}
	
	public void fadeMusicOff(int musicType, int duration) {
		if(musicType < music.length) {
			music[musicType].fade(duration, 0, false);
		}
	}
	
	public void playSound(int soundType, boolean shouldLoop) {
		if(soundType < sounds.length) {
			if(shouldLoop) {
				sounds[soundType].loop();
			} else {
				sounds[soundType].play();
			}
		}
	}
	
	public boolean isPlaying(int soundType) {
		return sounds[soundType].playing();
	}
	
	public void stopPlayingSound(int soundType) {
		if(sounds[soundType].playing()) {
			sounds[soundType].stop();
		}
 	}
	public void stopPlayingAllSound()
	{
		for(int i = 0; i < sounds.length; ++i) {
			if(sounds[i] != null) {
				sounds[i].stop();
			}
		}
		} 
 }
