package com.knightlight.tribal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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

	/** The graphical representation of the Entity */
	public transient Sprite sprite;

	/** The location of the Entity */
	protected float posX, posY;

	/** The rotational angle of the Entity, in radians */
	protected float angle;

	/** The size of the Entity, in units */
	protected float size;

	/**
	 * Internal use only
	 */
	protected Entity() {	}

	/**
	 * Standard non-graphical constructor for an Entity
	 * Only assigns entity-specific data
	 * @param w - The World to add the Entity into
	 * @param x - The x position
	 * @param y - The y position
	 */
	public Entity(float x, float y)
	{
		posX = x; posY = y;
		angle = 0;
		size = 2f;
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
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(posX, posY);
		bodyDef.angle = angle;
		
		body = w.createBody(bodyDef);
	}

	/** 
	 * Creates the Fixture for the Entity and attaches it to the Body
	 * Extend this to change the Fixture for different Entities
	 */
	protected void addFixture()
	{
		// Create a circle shape and set its radius to 6
		CircleShape circle = new CircleShape();
		circle.setRadius(getSize()/2);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 0.5f; 
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.1f;

		Fixture fix = body.createFixture(fixtureDef);

		body.setLinearDamping(1f);

		circle.dispose();

		fixture = fix;
	}

	/**
	 * Creates the Sprite for the Entity
	 */
	protected void addSprite()
	{
		Sprite s = new Sprite(Resources.dude);
		s.setSize(getSize(), getSize());
		s.setOriginCenter();
		s.setCenter(posX, posY);
		sprite = s;
	}

	/**
	 * Creates the Light object associated with this Entity
	 * Only call this after the initial construction of the Setting and Renderer to avoid exceptions
	 * Does nothing if the Entity does not have a light
	 */
	public Entity addLight() 
	{
		/* NO LIGHT */return this;
	}

	/** Updates the position and logic of the Entity, called every World update */
	public void update()
	{
		// Updates the sprite position to the body
		Vector2 bodyPos = body.getPosition();
		posX = bodyPos.x;
		posY = bodyPos.y;
		angle = body.getAngle();
		
		sprite.setRotation(body.getAngle() * TribalCore.RAD_TO_DEG);
		sprite.setCenter(bodyPos.x, bodyPos.y);


		if(Gdx.input.isKeyPressed(Keys.A))
			body.applyLinearImpulse(-0.8f, 0, getX(), getY(), true);
		if(Gdx.input.isKeyPressed(Keys.W))
			body.applyLinearImpulse(0, 0.8f, getX(), getY(), true);
		if(Gdx.input.isKeyPressed(Keys.D))
			body.applyLinearImpulse(0.8f, 0, getX(), getY(), true);
		if(Gdx.input.isKeyPressed(Keys.S))
			body.applyLinearImpulse(0, -0.8f, getX(), getY(), true);
		if(Gdx.input.isKeyPressed(Keys.SPACE))
			setPosition(10f, 10f);
	}

	/**
	 * Sets the position of the Entity, both the body and the sprite
	 * @param x - the x position to set it to
	 * @param y - the y position to set it to
	 */
	public void setPosition(float x, float y)
	{

		posX = x; posY = y;
		angle = body.getAngle();
		
		sprite.setRotation(angle * TribalCore.RAD_TO_DEG);
		sprite.setCenter(x, y);
		
		if(body != null)
		{
			body.setTransform(new Vector2(x, y), angle);
		}
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

	public float getSize()
	{
		return size;
	}
}
