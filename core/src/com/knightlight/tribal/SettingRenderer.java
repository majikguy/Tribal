package com.knightlight.tribal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
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
	
	/** pixel perfect projection for font rendering */
	private Matrix4 normalProjection = new Matrix4();
	
	/** Map scaled projection for game rendering */
	private Matrix4 gameProjection = new Matrix4();
	
	public SettingRenderer(Setting s)
	{
		setting = s;
		debugRenderer = new Box2DDebugRenderer();
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Gdx.graphics.getWidth()*TribalCore.PIX_TO_UNIT, Gdx.graphics.getHeight()*TribalCore.PIX_TO_UNIT);
		normalProjection.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		gameProjection.setToOrtho2D(0, 0, Gdx.graphics.getWidth()*TribalCore.PIX_TO_UNIT, Gdx.graphics.getHeight()*TribalCore.PIX_TO_UNIT);
	}
	
	public void render()
	{
		camera.update();
		
		Gdx.gl.glClearColor(0f,0f,0f,1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(gameProjection);
		
		renderBG();
		
		debugRenderer.render(setting.getWorld(), gameProjection);
	}
	
	private void renderBG()
	{
		//batch.setProjectionMatrix(normalProjection);
		batch.begin();
		batch.draw(ResourceHandler.background, 0, 0);
		batch.end();
		//batch.setProjectionMatrix(gameProjection);
	}
	
	public void resize(int width, int height)
	{
		camera = new OrthographicCamera(Gdx.graphics.getWidth()*TribalCore.PIX_TO_UNIT, Gdx.graphics.getHeight()*TribalCore.PIX_TO_UNIT);
		normalProjection.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		gameProjection.setToOrtho2D(0, 0, Gdx.graphics.getWidth()*TribalCore.PIX_TO_UNIT, Gdx.graphics.getHeight()*TribalCore.PIX_TO_UNIT);
	}
	
}