package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class World {
	public Texture background;
	public Rectangle boundaries;
	
	public SpriteBatch batch;
	
	public World(String fileName) {
		background = new Texture(fileName);
//		boundaries = new Rectangle(0, 0, width, height);
		batch = new SpriteBatch();
	}
	
	public World(String fileName, float width, float height) {
		this(fileName);
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
