package com.knightlight.tribal.setting;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.knightlight.tribal.Entity;
import com.knightlight.tribal.environment.Environment;

public class Setting {
	// Environment handler
	public Environment environment;

	/** Box2D World object
	 * 	transient to prevent it from being serialized and crashing
	 */
	transient public World gameWorld;

	/** boolean representing whether or not the World just stepped */
	transient public boolean stepped = true;
	// Box2D fields end

	private Setting() {/*DOES NOTHING*/}

	private Setting(World world)
	{
		gameWorld = world;
		environment = new Environment(gameWorld);
	}

	/** Factory method for creating a new Setting --
	 * Required to prevent issues with serialization and the 0 argument constructor */
	public static Setting getNewSetting()
	{
		World world = new World(new Vector2(0, 0), true);
		Setting newSetting = new Setting(world);

		return newSetting;
	}

	/** Updates the world objects and simulation logic */
	public void update()
	{
		stepped = fixedStep(Gdx.graphics.getDeltaTime());
		//gameWorld.step(1/30f, 6, 2);

		if(stepped)
		{
			Array<Body> bodiesArray = new Array<Body>();
			gameWorld.getBodies(bodiesArray);
			Iterator<Body> bi = bodiesArray.iterator();
			while(bi.hasNext()) {
				Body body = bi.next();
				Entity entity = (Entity) body.getUserData();
				if(entity != null) {
					entity.update();
				}
			}
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

	/** Loads a save file and builds a new Setting from the data */
	public static Setting buildFromJson(String saveState)
	{
		Json save = new Json();
		Setting loadedSetting = save.fromJson(Setting.class, saveState);
		loadedSetting.gameWorld = new World(new Vector2(0, 0), true);

		loadedSetting.environment.rebuild(loadedSetting.gameWorld);

		return loadedSetting;
	}

	/** Builds a Json file representing the state of the Setting */
	public String toJson()
	{
		Json json = new Json();
		return json.prettyPrint(this);
	}
}