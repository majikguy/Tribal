package com.knightlight.tribal.tribe;

import java.util.ArrayList;

import com.badlogic.gdx.physics.box2d.World;
import com.knightlight.tribal.Entity;

/**
 * Manages the resources, buildings, and residents in a tribe
 * @author majikguy
 *
 */
public class Tribe {
	
	/** Box2D world object from the Setting
	* 	transient to prevent serialization and crashing due to it */
	transient private World gameWorld;

	/** ArrayLists containing the Entities that make up the Tribe */
	public ArrayList<Entity> structures, residents;
	
	/**
	 * Creates basic setup for loaded or created Tribes
	 */
	private Tribe() {
		structures = new ArrayList<Entity>();
		residents = new ArrayList<Entity>();
	}
	
	/**
	 * Basic constructor
	 * @param w - The World to add the Tribe to
	 */
	public Tribe(World w) {
		this();
		gameWorld = w;
	}
	
	/**
	 * Rebuilds the Tribe after being loaded
	 * @param w - The World to be built in
	 */
	public void rebuild(World w)
	{
		gameWorld = w;
		
		for(Entity e : structures)
			e.build(gameWorld);
		for(Entity e : residents)
			e.build(gameWorld);
	}
	
	/**
	 * Updates the game logic for each governed Entity
	 */
	public void update()
	{
		for(Entity e : structures)
			e.update();
		for(Entity e : residents)
			e.update();
	}
	
	/**
	 * Builds a tribe for testing purposes
	 * Called if a save is not loaded
	 */
	public void makeTestTribe() {
		residents.add(new EntityTribesman(40, 10).build(gameWorld));
		
		structures.add(new EntityCampfire(50, 30).build(gameWorld));
		structures.add(new EntityHut(57f, 37f, 45f, 7f).build(gameWorld));
	}
	
}
