package com.knightlight.tribal;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

public class Entity {

	// Kept as transient to avoid serialization problems
	//	Can only be accessed after the Entity is fully created/recreated
	public transient Body body;
	public transient Fixture fixture;

	/** The graphical representation of the Entity */
	public transient Sprite sprite;

	/** The location of the Entity */
	protected Vector2 position;

	/** The rotational angle of the Entity, in radians */
	protected float angle;

	/** The size of the Entity, in units */
	protected float size;

	/**
	 * Internal use only
	 */
	protected Entity() {/*Nothing yet!*/}

	/**
	 * Standard non-graphical constructor for an Entity
	 * Only assigns entity-specific data
	 * @param w - The World to add the Entity into
	 * @param x - The x position
	 * @param y - The y position
	 */
	public Entity(float x, float y)
	{
		this();
		position = new Vector2(x, y);
		angle = 0;
		size = 1f;
	}

	/**
	 * Builds the entity
	 * Creates the sprite, body, fixture, and adds it to the world
	 * Done after the Entity has been constructed to ensure the fields are properly set
	 * @param w - The world to add the Entity into
	 * @param e - The Entity to build
	 * @return The built Entity
	 */
	public Entity build(World w)
	{
		addToWorld(w);
		addSprite();
		return this;
	}

	/** 
	 * Updates the position and logic of the Entity, called every World update 
	 * Base Entity simply updates the positions of the different component objects of the Entity
	 */
	public void update()
	{
		// Check if the Entity moved, only update sprite if it did.
		Vector2 bodyPos = body.getPosition();
		float bodyAngle = body.getAngle();
		boolean moved = !position.equals(bodyPos) || angle != bodyAngle;

		if(moved)
		{
			// Updates the sprite position to the body
			position.set(body.getPosition());
			angle = bodyAngle;

			sprite.setRotation(angle * TribalCore.RAD_TO_DEG);
			sprite.setCenter(position.x, position.y);
		}
	}

	/**
	 * Builds the Entity's Box2D objects then adds it to the specified world, 
	 * 	creating the body object and adding the fixtures
	 * @param w - The world to add the Entity into
	 */
	protected void addToWorld(World w)
	{
		addBody(w);
		addFixture();
		body.setUserData(this);
	}

	/**
	 * Creates the Body for the entity, and adds it to the provided World
	 * Extend this to change the Body type of different Entities
	 * @param w - the World to add the Body to
	 */
	protected void addBody(World w)
	{
		//To be overridden!
	}

	/** 
	 * Creates the Fixture for the Entity and attaches it to the Body
	 * Extend this to change the Fixture for different Entities
	 */
	protected void addFixture()
	{
		//To be overridden!
	}

	/**
	 * Creates the Sprite for the Entity
	 */
	protected void addSprite()
	{
		//To be overridden!
	}

	/**
	 * Creates the Light object associated with this Entity
	 * Only call this after the initial construction of the Setting and Renderer to avoid exceptions
	 * Does nothing if the Entity does not have a light
	 */
	public Entity addLight() 
	{
		//To be overridden!
		return this;
	}

	/**
	 * Sets the position of the Entity, both the body and the sprite
	 * @param x - the x position to set it to
	 * @param y - the y position to set it to
	 */
	public void setPosition(Vector2 pos)
	{

		position.set(pos);
		angle = body.getAngle();

		sprite.setRotation(angle * TribalCore.RAD_TO_DEG);
		sprite.setCenter(position.x, position.y);
		
		body.setTransform(position, angle);
		body.setLinearVelocity(0f, 0f);
	}

	public Vector2 getPosition()
	{
		return position;
	}

	public float getSize()
	{
		return size;
	}
}
