package com.knightlight.tribal.setting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Json;
import com.knightlight.tribal.environment.Environment;
import com.knightlight.tribal.tribe.Tribe;

public class Setting {
	
	/** Box2D World object
	 * 	transient to prevent it from being serialized and crashing
	 */
	transient public World gameWorld;
	
	/** Environment Handler */
	public Environment environment;
	
	/** Tribe Handler */
	public Tribe tribe;

	/** boolean representing whether or not the World just stepped */
	transient public boolean stepped = true;

	/**
	 * Creates basic setup required for both creation and loading
	 */
	private Setting() {/*DOES NOTHING YET*/}

	/**
	 * Creates a new Setting object
	 * @param world - The World to attach to the Setting
	 */
	private Setting(World world)
	{
		gameWorld = world;
	}

	/** 
	 * Factory method for creating a new Setting
	 * Also attaches an Environment and a Tribe
	 * Required to prevent issues with serialization and the 0 argument constructor 
	 */
	public static Setting getNewSetting()
	{
		//Create the World and the Setting object
		World world = new World(new Vector2(0, 0), true);
		Setting newSetting = new Setting(world);
		
		//Attach a new Environment and populate it
		newSetting.environment = new Environment(world);
		newSetting.environment.makeTestEnvironment();
		
		//Attach a new Tribe and populate it
		newSetting.tribe = new Tribe(world);
		newSetting.tribe.makeTestTribe();

		return newSetting;
	}
	
	/** Loads a save file and builds a new Setting from the data */
	public static Setting buildFromJson(String saveState)
	{
		Json save = new Json();
		Setting loadedSetting = save.fromJson(Setting.class, saveState);
		loadedSetting.gameWorld = new World(new Vector2(0, 0), true);

		loadedSetting.environment.rebuild(loadedSetting.gameWorld);
		loadedSetting.tribe.rebuild(loadedSetting.gameWorld);

		return loadedSetting;
	}

	/** Updates the world objects and simulation logic */
	public void update()
	{
		stepped = fixedStep(Gdx.graphics.getDeltaTime());
		//gameWorld.step(1/30f, 6, 2);

		if(stepped)
		{
			environment.update();
			tribe.update();
		}
	}

	private final static int MAX_FPS = 30;
	private final static int MIN_FPS = 15;
	public final static float TIME_STEP = 1f / MAX_FPS;
	private final static float MAX_STEPS = 1f + MAX_FPS / MIN_FPS;
	private final static float MAX_TIME_PER_FRAME = TIME_STEP * MAX_STEPS;
	private final static int VELOCITY_ITERS = 6;
	private final static int POSITION_ITERS = 2;

	transient float physicsTimeLeft;
	public transient long aika;
	public transient int times;

	private boolean fixedStep(float delta) {
		physicsTimeLeft += delta;
		if (physicsTimeLeft > MAX_TIME_PER_FRAME)
			physicsTimeLeft = MAX_TIME_PER_FRAME;

		boolean stepped = false;
		while (physicsTimeLeft >= TIME_STEP) {
			gameWorld.step(TIME_STEP, VELOCITY_ITERS, POSITION_ITERS);
			physicsTimeLeft -= TIME_STEP;
			stepped = true;
		}
		return stepped;
	}

	/** Builds a Json file representing the state of the Setting */
	public String toJson()
	{
		Json json = new Json();
		return json.prettyPrint(this);
	}
}