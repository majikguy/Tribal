package com.knightlight.tribal.environment;

import java.util.ArrayList;

import com.badlogic.gdx.physics.box2d.World;
import com.knightlight.tribal.Entity;
import com.knightlight.tribal.setting.EntityCampfire;

public class Environment {
	
	// Box2D world object from the Setting
	// 	transient to prevent serialization and crashing due to it
	transient private World world;
	
	public ArrayList<Entity> entities;
	
	public int testInt;

	private Environment() {
		
		entities = new ArrayList<Entity>();
	}
	
	public Environment(World w)
	{
		this();
		world = w;
	}
	
	public void addTestEntities() {
		entities.add(new Entity(40, 10).build(world));
		entities.add(new EntityCampfire(50, 30).build(world));
	}
	
	public void rebuild(World w)
	{
		world = w;
		for(Entity e : entities)
			e.build(world);
	}
}
