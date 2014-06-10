package com.knightlight.tribal.environment;

import com.badlogic.gdx.physics.box2d.World;

public class Environment {
	
	// Box2D world object from the Setting
	// 	transient to prevent serialization and crashing due to it
	transient private World world;
	
	public int testInt;

	private Environment()
	{}
	
	public Environment(World w)
	{
		this();
		world = w;
	}
}
