package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class World {
	
	private OrthographicCamera camera;
	
	public Texture background;
	
	public Rectangle boundaries;
	
	public SpriteBatch batch;
	
	public Player player;
	
	public InputHandler input;
	
	public World(String fileName) {
		
		// image of World background loaded as a Texture
		background = new Texture(fileName);
		
		// used to draw each Texture
		batch = new SpriteBatch();
		
		// back default boundaries the size of the background image
		boundaries = new Rectangle(0, 0, background.getWidth(), background.getHeight());
		
		player = new Player();
		
		input = new InputHandler();
		
		float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        
        //Creates a new camera, sets its position focused on the player, and zooms out
        camera = new OrthographicCamera(30, 30 * (h / w));
        camera.position.set(player.position.x,player.position.y,0);
        camera.zoom += 20;
        camera.update();
	}
	
	public World(String fileName, float width, float height) {
		this(fileName);
		boundaries = new Rectangle(0, 0, width, height);
	}
	
	public void draw() {
	    batch.setProjectionMatrix(camera.combined);
	        
		batch.begin();
		batch.draw(background, 0, 0);
		batch.draw(player.sprite, player.position.x, player.position.y);
		input.move(player, camera);
		batch.end();
	}
	
	public void dispose() {
		background.dispose();
	}
	
}
