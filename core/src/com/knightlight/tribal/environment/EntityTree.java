package com.knightlight.tribal.environment;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.knightlight.tribal.Entity;
import com.knightlight.tribal.Resources;
import com.knightlight.tribal.TribalCore;

public class EntityTree extends Entity {

	public EntityTree() {super();}

	public EntityTree(float x, float y) {
		super(x, y);
		size = 8f;
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
		//Builds a triangle
		PolygonShape triangle = new PolygonShape();
		Vector2[] vertices = new Vector2[3];
		vertices[0] = new Vector2(0, size/2);
		vertices[1] = new Vector2(-size/2, -size/2);
		vertices[2] = new Vector2(size/2, -size/2);
		
		triangle.set(vertices);

		Fixture fix = body.createFixture(triangle, 0.0f);
		
		triangle.dispose();

		fixture = fix;
	}

	@Override
	protected void addSprite()
	{
		Sprite s = new Sprite(Resources.triangle);
		s.setColor(0.04f, 0.43f, 0f, 1f);
		s.setSize(getSize(), getSize());
		s.setOriginCenter();
		s.setRotation(angle * TribalCore.RAD_TO_DEG);
		s.setCenter(position.x, position.y);
		sprite = s;
	}
}
