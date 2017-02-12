package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class World {
	public Texture background;
	
	public Rectangle boundaries;
	
	public SpriteBatch batch;
	
	public Player player;
	
	public World(String fileName) {
		
		// image of World background loaded as a Texture
		background = new Texture(fileName);
		
		// used to draw each Texture
		batch = new SpriteBatch();
		
		// back default boundaries the size of the background image
		boundaries = new Rectangle(0, 0, background.getWidth(), background.getHeight());
		
		player = new Player();
	}
	
	public World(String fileName, float width, float height) {
		this(fileName);
		boundaries = new Rectangle(0, 0, width, height);
	}
	
	public void draw() {
		batch.begin();
		batch.draw(background, 0, 0);
		batch.draw(player.sprite, player.position.x, player.position.y);
		batch.end();
	}
	
	public void dispose() {
		background.dispose();
	}
	
	public void render() {
		   if(Gdx.input.isKeyPressed(Keys.DPAD_LEFT)) {
		      player.position.x -= Gdx.graphics.getDeltaTime() * player.speed;
		      player.sprite = player.sprites[1];
		   }
		   if(Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)) { 
			   player.position.x += Gdx.graphics.getDeltaTime() * player.speed;
			   player.sprite = player.sprites[3];
		   }
		   if(Gdx.input.isKeyPressed(Keys.DPAD_UP)) { 
		      player.position.y += Gdx.graphics.getDeltaTime() * player.speed;
		      player.sprite = player.sprites[0];
		   }
		   if(Gdx.input.isKeyPressed(Keys.DPAD_DOWN)) { 
			   player.position.y -= Gdx.graphics.getDeltaTime() * player.speed;
			   player.sprite = player.sprites[2];
		   }

		   //Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		   batch.begin();
		   batch.draw(player.sprite, player.position.x, player.position.y);
		   batch.end();
		} 
}
