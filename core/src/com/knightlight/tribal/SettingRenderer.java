package com.knightlight.tribal;

import java.util.Iterator;

import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;
import com.knightlight.tribal.setting.Setting;

public class SettingRenderer
{
	/** Game Setting to render */
	private final Setting setting;

	/** Camera for game rendering */
	private OrthographicCamera camera;

	/** SpriteBatch for general drawing */
	private SpriteBatch batch;

	/** Box2D debug renderer for testing */
	private Box2DDebugRenderer debugRenderer;

	/** Font for rendering debug messages */
	private BitmapFont font;

	/** Box2DLight light rendering object */
	public RayHandler lightRenderer;

	/** pixel perfect projection */
	private Matrix4 normalProjection = new Matrix4();

	/** Map scaled projection for game rendering */
	private Matrix4 gameProjection = new Matrix4();

	public SettingRenderer(Setting s)
	{
		setting = s;

		debugRenderer = new Box2DDebugRenderer();

		font = new BitmapFont();
		font.setColor(Color.RED);

		// Light renderer setup begin
		RayHandler.setGammaCorrection(true);
		RayHandler.useDiffuseLight(true);
		lightRenderer = new RayHandler(setting.gameWorld);
		lightRenderer.setAmbientLight(0.08f, 0.08f, 0.08f, 0.1f);
		lightRenderer.setCulling(true);		
		//lightRenderer.setBlur(false);
		lightRenderer.setBlurNum(1);
		// Light renderer setup end

		batch = new SpriteBatch();
	}

	/** Renders the game world */
	public void render()
	{
		camera.update();

		Gdx.gl.glClearColor(0f,0f,0f,1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(gameProjection);

		batch.begin();

		renderBG();

		renderObjects();

		renderLights();

		batch.end();

		if(TribalCore.DEBUG)
			debugRenderer.render(setting.gameWorld, gameProjection);
	}

	/** Handles the rendering for the background, including the grass and water */
	// TODO Make the background rendering more than grass
	private void renderBG()
	{
		batch.disableBlending();
		batch.draw(Resources.background, 0, 0, setting.height, setting.height);
		batch.enableBlending();
	}

	/** Renders the objects in the world in layers */
	private void renderObjects()
	{
		//Draws Entities starting from the lowest to the highest to get the layers correct
		for(Entity e : setting.tribe.structures)
			e.sprite.draw(batch);
		for(Entity e : setting.tribe.residents)
			e.sprite.draw(batch);

		for(Entity e : setting.environment.wildlife)
			e.sprite.draw(batch);
		for(Entity e : setting.environment.trees)
			e.sprite.draw(batch);
	}

	private void renderLights()
	{
		batch.end();

		lightRenderer.setCombinedMatrix(gameProjection);		
		long time = System.nanoTime();

		if(setting.stepped)
			lightRenderer.update();
		lightRenderer.render();

		setting.aika += System.nanoTime() - time;

		/* FONT */
		batch.setProjectionMatrix(normalProjection);
		batch.begin();

		if(TribalCore.DEBUG)
			font.draw(batch, Integer.toString(Gdx.graphics.getFramesPerSecond()) 
					+ " time used for shadow calculation:" +setting.aika / ++setting.times + "ns" , 0, 20);

	}

	/** Resizes the view and corrects the projection to the new screen size */
	public void resize(int width, int height)
	{
		camera = new OrthographicCamera(setting.width, setting.height);
		normalProjection.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		gameProjection.setToOrtho2D(0, 0, setting.width, setting.height);
		camera.update();
	}

	/** 
	 * Builds the light objects of all the Entites currently in the world
	 * Only to be used when the Setting in reloaded from a save file
	 */
	public void buildLoadedLights()
	{
		// Iterates through all of the bodies in the physics world
		Array<Body> bodiesArray = new Array<Body>();
		setting.gameWorld.getBodies(bodiesArray);
		Iterator<Body> bi = bodiesArray.iterator();
		while(bi.hasNext()) {
			Body body = bi.next();
			Entity entity = (Entity) body.getUserData();
			if(entity != null) {
				entity.addLight();
			}
		}
	}

}