package com.knightlight.tribal;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

public class Entity {

	// Kept as transient to avoid serialization problems
	//	Can only be accessed after the Entity is fully created/recreated
	public transient Body body;
	public transient Fixture fixture;
	
	// The graphical representation of the Entity
	public Sprite sprite;
	
	// The location of the Entity
	private float posX, posY;

	/**
	 * Internal use only
	 */
	private Entity() 
	{
		sprite = new Sprite();
	}

	/**
	 * Standard non-graphical constructor for an Entity
	 * @param w - The World to add the Entity into
	 * @param x - The x position
	 * @param y - The y position
	 */
	public Entity(World w, float x, float y) 
	{
		this();
		setPosition(x, y);
		addToWorld(w);
	}

	/**
	 * Adds the entity to the specified world, creating the body object and adding the fixtures
	 * @param w - The world to add the Entity into
	 */
	public void addToWorld(World w)
	{
		body = getBody(w);
		fixture = getFixture();
	}

	/**
	 * Creates the Body for the entity, and adds it to the provided World
	 * @param w - the World to add the Body to
	 * @return The Body added
	 */
	protected Body getBody(World w)
	{
		// First we create a body definition
		BodyDef bodyDef = new BodyDef();
		// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
		bodyDef.type = BodyType.DynamicBody;
		// Set our body's starting position in the world
		bodyDef.position.set(sprite.getX(), sprite.getY());

		return w.createBody(bodyDef);
	}

	protected Fixture getFixture()
	{
		// Create a circle shape and set its radius to 6
		CircleShape circle = new CircleShape();
		circle.setRadius(1f);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 0.5f; 
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.6f;

		Fixture fix = body.createFixture(fixtureDef);

		circle.dispose();

		return fix;
	}
	
	public void update()
	{
		// Updates the sprite position to the body
		Vector2 bodyPos = body.getPosition();
		posX = bodyPos.x;
		posY = bodyPos.y;
		sprite.setPosition(bodyPos.x, bodyPos.y);
		sprite.rotate(body.getAngle() * TribalCore.RAD_TO_DEG);
	}

	/**
	 * Sets the position of the Entity, both the body and the sprite
	 * @param x - the x position to set it to
	 * @param y - the y position to set it to
	 */
	public void setPosition(float x, float y)
	{
		posX = x; posY = y;
		sprite.setPosition(x * TribalCore.UNIT_TO_PIX, y * TribalCore.UNIT_TO_PIX);
		if(body != null)
			body.getPosition().set(x, y);
	}
	
	/**
	 * @return the x position of the entity
	 */
	public float getX()
	{
		return posX;
	}
	
	/**
	 * @return the y position of the entity
	 */
	public float getY()
	{
		return posY;
	}
}
