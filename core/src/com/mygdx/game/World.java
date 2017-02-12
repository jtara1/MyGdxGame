package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class World implements ApplicationListener {
	private OrthographicCamera cam;
	private float rotationSpeed = 0.5f;
	
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
        cam = new OrthographicCamera(30, 30* (h/w));
        cam.position.set(cam.viewportWidth/2f, cam.viewportHeight/2f,0);
        cam.zoom += 15;
        cam.update();
	}
	
	public World(String fileName, float width, float height) {
		this(fileName);
		boundaries = new Rectangle(0, 0, width, height);
	}
	
	public void draw() {
		handleInput();
	    cam.update();
	    batch.setProjectionMatrix(cam.combined);
	        
		batch.begin();
		batch.draw(background, 0, 0);
		batch.draw(player.sprite, player.position.x, player.position.y);
		input.move(player, cam);
		batch.end();
	}
	
	public void dispose() {
		background.dispose();
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		
	}

	@Override
    public void resize(int width, int height) {
        cam.viewportWidth = 30f;
        cam.viewportHeight = 30f * height/width;
        cam.update();
    }

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            cam.zoom += 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            cam.zoom -= 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            cam.translate(-3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            cam.translate(3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            cam.translate(0, -3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            cam.translate(0, 3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            cam.rotate(-rotationSpeed, 0, 0, 1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.F)) {
            cam.rotate(rotationSpeed, 0, 0, 1);
        }

        //cam.zoom = MathUtils.clamp(cam.zoom, 0.1f, 100/cam.viewportWidth);

        //float effectiveViewportWidth = cam.viewportWidth * cam.zoom;
        //float effectiveViewportHeight = cam.viewportHeight * cam.zoom;

        //cam.position.x = MathUtils.clamp(cam.position.x, effectiveViewportWidth / 2f, 100 - effectiveViewportWidth / 2f);
        //cam.position.y = MathUtils.clamp(cam.position.y, effectiveViewportHeight / 2f, 100 - effectiveViewportHeight / 2f);
    }
	
}
