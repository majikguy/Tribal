package com.knightlight.tribal;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.knightlight.tribal.setting.Setting;

public class TribalCore extends InputAdapter implements ApplicationListener {

	// Conversion units for world units
	// Needed for Box2D
	public static final float PIX_TO_UNIT = 1/8f;
	public static final float UNIT_TO_PIX = 8f;
	public static final float RAD_TO_DEG = 57.2957795f;

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
			setting = Setting.getNewSetting();
			setting.environment.addTestEntities();
		}
		
		renderer = new SettingRenderer(setting);
		renderer.buildLoadedLights();
		
		setting.environment.testInt = (int)(Math.random() * 1000);
		System.out.println("TEST INT!!: " + setting.environment.testInt);

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
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		saveState();

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