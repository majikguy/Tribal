package com.knightlight.tribal.tribe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.knightlight.tribal.Entity;
import com.knightlight.tribal.Resources;

/**
 * EntityTribesman objects are the people that make up the tribe
 * Currently only controlled by keyboard input for testing
 * @author majikguy
 *
 */
public class EntityTribesman extends Entity {

	protected EntityTribesman() {super();}
	
	public EntityTribesman(float x, float y) {
		super(x, y);
		size = 2f;
	}
	
	/** 
	 * Updates the position and logic of the Entity, called every World update 
	 */
	@Override
	public void update()
	{
		super.update();

		handleInput();
	}
	
	/**
	 * Updates the Entity from input
	 */
	private void handleInput()
	{
		if(Gdx.input.isKeyPressed(Keys.A))
			body.applyLinearImpulse(-0.8f, 0, position.x, position.y, true);
		if(Gdx.input.isKeyPressed(Keys.W))
			body.applyLinearImpulse(0, 0.8f, position.x, position.y, true);
		if(Gdx.input.isKeyPressed(Keys.D))
			body.applyLinearImpulse(0.8f, 0, position.x, position.y, true);
		if(Gdx.input.isKeyPressed(Keys.S))
			body.applyLinearImpulse(0, -0.8f, position.x, position.y, true);
		if(Gdx.input.isKeyPressed(Keys.SPACE))
			setPosition(new Vector2(10f, 10f));
//		if(Gdx.input.isTouched())
//		{
//			setPosition(new Vector2( Gdx.input.getX() * TribalCore.PIX_TO_UNIT, (Gdx.graphics.getHeight() - Gdx.input.getY()) * TribalCore.PIX_TO_UNIT ) );
//		}
	}
	
	/**
	 * Creates the Body for the entity, and adds it to the provided World
	 * Extend this to change the Body type of different Entities
	 * @param w - the World to add the Body to
	 */
	@Override
	protected void addBody(World w)
	{
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(position);
		bodyDef.angle = angle;
		
		body = w.createBody(bodyDef);
	}

	/** 
	 * Creates the Fixture for the Entity and attaches it to the Body
	 * Extend this to change the Fixture for different Entities
	 */
	@Override
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
		body.setAngularDamping(1f);

		circle.dispose();

		fixture = fix;
	}

	/**
	 * Creates the Sprite for the Entity
	 */
	@Override
	protected void addSprite()
	{
		Sprite s = new Sprite(Resources.circle);
		s.setColor(1f, 0.68f, 0.35f, 1f);
		s.setSize(getSize(), getSize());
		s.setOriginCenter();
		s.setCenter(position.x, position.y);
		sprite = s;
	}
}
