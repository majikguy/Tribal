package com.knightlight.tribal;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.knightlight.tribal.setting.Setting;

public class TribalCore extends InputAdapter implements ApplicationListener {
	
	/** Tag for whether debug functions should run */
	public static final boolean DEBUG = false;
	
	/** The hardcoded seed to use for generation during debugging */
	public static final long SEED = -3037696267990719665L;

	// Conversion units for Box2D world units
	// VERY helpful
	public static final float PIX_TO_UNIT = 1/32f;
	public static final float UNIT_TO_PIX = 32f;
	public static final float RAD_TO_DEG = 57.2957795f;
	public static final float DEG_TO_RAD = 0.0174532925f;

	/** Setting object, contains the actual game state objects. */
	public Setting setting;

	/** Game Rendering object */
	public static SettingRenderer renderer;

	@Override
	public void create() {
		
		// Attempts to load an existing game-state from a save file.
		if(!loadState())
		{
			// If no save is found, creates a new setting
			float aspectRatio = (float)Gdx.graphics.getWidth() / (float)Gdx.graphics.getHeight();
			System.out.println("Aspect Ratio: " + aspectRatio);
			setting = Setting.getNewSetting(aspectRatio);
		}
		
		renderer = new SettingRenderer(setting);
		renderer.buildLoadedLights();

	}

	@Override
	public void resize(int width, int height) {
		
		renderer.resize(width, height);

	}

	@Override
	public void render() {

		renderer.render();
		
		setting.update();
	}

	@Override
	public void pause() {
		// TODO Look into more uses
		saveState();

	}

	@Override
	public void resume() {
		// TODO Investigate, most likely unneeded

	}

	@Override
	public void dispose() 
	{
		// TODO Look into adding dispose methods in the Setting.
		// 	Possibly unneeded, since it runs lean and only disposes on quit.

	}

	/**
	 * Saves the game state in a Json file
	 */
	public void saveState()
	{
		FileHandle save = Gdx.files.local("save/save.json");
		save.writeString(setting.toJson(), false);
		System.out.println("Saved State!");
	}

	/** Attempts the load the state of the game from a save file
	 * 
	 * @return True if a valid save file could be read, false otherwise
	 */
	public boolean loadState()
	{
		try
		{
			FileHandle save = Gdx.files.local("save/save.json");
			
			setting = Setting.buildFromJson(save.readString());
			
			System.out.println("Setting Loaded!");
			return true;
		}
		catch(Exception e)
		{
			FileHandle errorLog = Gdx.files.local("save/error.log");
			errorLog.writeString(e.toString(), false);
			System.out.println("No valid save found!");
			return false;
		}
	}


}