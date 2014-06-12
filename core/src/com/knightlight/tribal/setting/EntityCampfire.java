package com.knightlight.tribal.setting;

import box2dLight.Light;
import box2dLight.PointLight;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.knightlight.tribal.Entity;
import com.knightlight.tribal.Resources;
import com.knightlight.tribal.TribalCore;

public class EntityCampfire extends Entity {
	
	/** The Light object attached to the campfire */
	protected transient Light light;
	
	/** The distance illuminated by the campfire */
	protected float lightDistance = 16f;
	
	/**
	 * Internal use only
	 */
	protected EntityCampfire() {}

	/**
	 * Standard constructor for an EntityCampfire
	 * Nothing needed here, as the work is done in the add methods
	 * @param w - The World to add the Entity into
	 * @param x - The x position
	 * @param y - The y position
	 */
	public EntityCampfire(float x, float y) {
		super(x, y);
		size = 3f;
		
	}
	
	@Override
	protected void addBody(World w)
	{
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(posX, posY);
		bodyDef.angle = angle;

		body = w.createBody(bodyDef);
	}
	
	@Override
	protected void addFixture()
	{
		PolygonShape square = new PolygonShape();
		square.setAsBox(this.size/2, this.size/2);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = square;
		fixtureDef.density = 0.5f; 
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.6f;

		Fixture fix = body.createFixture(square, 0.0f);
		
		square.dispose();

		fixture = fix;
	}
	
	@Override
	protected void addSprite()
	{
		Sprite s = new Sprite(Resources.campfire);
		s.setSize(getSize(), getSize());
		s.setOriginCenter();
		s.setCenter(posX, posY);
		sprite = s;
	}
	
	@Override
	public Entity addLight()
	{
		light = new PointLight(TribalCore.renderer.lightRenderer, 256);
		light.setDistance(lightDistance);
		light.attachToBody(body, 0, 0);
		light.setColor(1f,.6f,0f, 0.1f);
		
		return this;
	}
	
	public void update()
	{
		// Updates the sprite position to the body
		Vector2 bodyPos = body.getPosition();
		posX = bodyPos.x;
		posY = bodyPos.y;
		angle = body.getAngle();
		sprite.setRotation(body.getAngle() * TribalCore.RAD_TO_DEG);
		sprite.setCenter(bodyPos.x, bodyPos.y);
		lightDistance = 15 + (float)(Math.random()*1);
		light.setDistance(lightDistance);
	}
}
