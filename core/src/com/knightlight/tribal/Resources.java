package com.knightlight.tribal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Resources {

	public static Texture background;
	
	public static Texture circle;
	
	public static Texture square;
	
	public static Texture triangle;
	
	public static Texture campfire;
	
	public static void reloadResources()
	{
		background = new Texture(Gdx.files.internal("sprites/bg.png"));
		
		circle = new Texture(Gdx.files.internal("sprites/circle.png"));
		
		square = new Texture(Gdx.files.internal("sprites/square.png"));
		
		triangle = new Texture(Gdx.files.internal("sprites/triangle.png"));
		
		campfire = new Texture(Gdx.files.internal("sprites/campfire.png"));
	}
	
}
