package com.knightlight.tribal.environment;

import java.util.ArrayList;

import com.badlogic.gdx.physics.box2d.World;
import com.knightlight.tribal.Entity;

/**
 * Manages the resources, terrain, and non-tribal Entities in the Environment
 * @author majikguy
 *
 */
public class Environment {
	
	/** Box2D world object from the Setting
	* 	transient to prevent serialization and crashing due to it */
	transient private World gameWorld;
	
	/** ArrayLists containing the Entities in the Environment */
	public ArrayList<Entity> trees, wildlife;

	/**
	 * Creates basic setup for loaded or created Environments
	 */
	private Environment() {
		
		trees = new ArrayList<Entity>();
		wildlife = new ArrayList<Entity>();
	}
	
	/**
	 * Basic constructor
	 * @param w - The World to add the Environment to
	 */
	public Environment(World w)
	{
		this();
		gameWorld = w;
	}
	
	/**
	 * Rebuilds the Environment after being loaded
	 * @param w - The World to be built in
	 */
	public void rebuild(World w)
	{
		gameWorld = w;
		for(Entity e : trees)
			e.build(gameWorld);
		for(Entity e : wildlife)
			e.build(gameWorld);
	}
	
	/**
	 * Updates the game logic for each governed Entity
	 */
	public void update()
	{
		for(Entity e : trees)
			e.update();
		for(Entity e : wildlife)
			e.update();
	}
	
	/**
	 * Builds a tribe for testing purposes
	 * Called if a save is not loaded
	 */
	public void makeTestEnvironment() 
	{
		trees.add(new EntityTree(65, 30).build(gameWorld));
		trees.add(new EntityTree(20, 10).build(gameWorld));
		trees.add(new EntityTree(0, 40).build(gameWorld));
		trees.add(new EntityTree(10, 50).build(gameWorld));
		trees.add(new EntityTree(10, 33).build(gameWorld));
		trees.add(new EntityTree(47, 20).build(gameWorld));
		trees.add(new EntityTree(30, 20).build(gameWorld));
		trees.add(new EntityTree(38, 42).build(gameWorld));
		trees.add(new EntityTree(70, 38).build(gameWorld));
	}
}
