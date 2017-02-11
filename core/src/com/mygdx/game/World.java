package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class World {
	public Texture background;
	
	public Rectangle boundaries;
	
	public SpriteBatch batch;
	
	public World(String fileName) {
		
		// image of World background loaded as a Texture
		background = new Texture(fileName);
		
		// used to draw each Texture
		batch = new SpriteBatch();
		
		// back default boundaries the size of the background image
		boundaries = new Rectangle(0, 0, background.getWidth(), background.getHeight());
	}
	
	public World(String fileName, float width, float height) {
		this(fileName);
		boundaries = new Rectangle(0, 0, width, height);
	}
	
	public void draw() {
		batch.begin();
		batch.draw(background, 0, 0);
		batch.end();
	}
	
	public void dispose() {
		background.dispose();
	}
}
