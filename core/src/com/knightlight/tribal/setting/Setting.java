package com.knightlight.tribal.setting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Json;
import com.knightlight.tribal.Entity;
import com.knightlight.tribal.TribalCore;
import com.knightlight.tribal.environment.Environment;

public class Setting {
	// Environment handler
	public Environment environment;

	// Box2D fields start t
	//	transient to prevent it from being serialized and crashing
	transient public World gameWorld;
	// Box2D fields end

	public Entity entity;

	private Setting()
	{
		//gameWorld = new World(new Vector2(0, 0), true);
	}

	private Setting(World world)
	{
		gameWorld = world;
		environment = new Environment(gameWorld);

		entity = new Entity(gameWorld, 0, 0);
	}

	public static Setting newSetting()
	{
		World world = new World(new Vector2(0, 0), true);
		Setting newSetting = new Setting(world);

		return newSetting;
	}

	public static Setting buildFromJson(String saveState)
	{
		Json save = new Json();
		Setting loadedSetting = save.fromJson(Setting.class, saveState);
		loadedSetting.gameWorld = new World(new Vector2(0, 0), true);

		Entity tempEntity = loadedSetting.entity;
		tempEntity.addToWorld(loadedSetting.gameWorld);
		tempEntity.setPosition(tempEntity.getX(), tempEntity.getY());

		return loadedSetting;
	}

	public void update()
	{
		gameWorld.step(1/45f, 6, 2);
		entity.update();
		if(Gdx.input.isTouched())
		{
			entity.body.applyLinearImpulse(-8f, 0, entity.getX(), entity.getY(), true);
		}
	}

	public String toJson()
	{
		Json json = new Json();
		return json.prettyPrint(this);
	}

	public World getWorld()
	{
		return gameWorld;
	}
}