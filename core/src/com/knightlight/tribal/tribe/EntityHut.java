package com.knightlight.tribal.tribe;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.knightlight.tribal.Entity;
import com.knightlight.tribal.Resources;
import com.knightlight.tribal.TribalCore;

/**
 * A simple static-bodied Entity representing a hut
 * @author majikguy
 *
 */
public class EntityHut extends Entity {

	protected EntityHut() {super();}
	
	/**
	 * Creates an EntityHut
	 * @param x - The x coordinate
	 * @param y - The y coordinate
	 * @param a - The initial angle of the hut in degrees (converted to rads in the constructor)
	 * @param s - The size of the hut
	 */
	public EntityHut(float x, float y, float a, float s)
	{
		super(x, y);
		angle = a * TribalCore.DEG_TO_RAD;
		size = s;
	}
	
	@Override
	protected void addBody(World w)
	{
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(position);
		bodyDef.angle = angle;

		body = w.createBody(bodyDef);
	}
	
	@Override
	protected void addFixture()
	{
		PolygonShape square = new PolygonShape();
		square.setAsBox(this.size/2, this.size/2);

		Fixture fix = body.createFixture(square, 0.0f);
		
		square.dispose();

		fixture = fix;
	}
	
	@Override
	protected void addSprite()
	{
		Sprite s = new Sprite(Resources.square);
		s.setColor(0.665f, 0.271f, 0.075f, 1f);
		s.setSize(getSize(), getSize());
		s.setOriginCenter();
		s.setRotation(angle * TribalCore.RAD_TO_DEG);
		s.setCenter(position.x, position.y);
		sprite = s;
	}
}
