/**
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class BattleField {
	public Texture background;
	private Texture menu, masterHpMpBar, playerHpMpBar;
	
	private OrthographicCamera camera;
		

	public Rectangle boundaries;
	
	public SpriteBatch batch;
	
	public InputHandler input;
	
	private Monster monster;
	
	public BattleField() {
		
		// image of World background loaded as a Texture
		background = new Texture("forest_preview.png");
		
		// used to draw each Texture
		batch = new SpriteBatch();
		
		// back default boundaries the size of the background image
		boundaries = new Rectangle(0, 0, background.getWidth(), background.getHeight());
		
		input = new InputHandler();
		
		float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        
		background = new Texture("forest_preview.png");
		menu = new Texture("botton_default.png");
		masterHpMpBar = new Texture("bar_hp_mp.png");
		playerHpMpBar = new Texture("bar_hp_mp.png");
		
        camera = new OrthographicCamera(30, 30 * (h / w));
        camera.zoom += 20;
        camera.update();
	}
	
	public BattleField(String fileName, float width, float height) {
		this();
		boundaries = new Rectangle(0, 0, width, height);
	}
	
	public void draw() {
	        
		batch.begin();
		batch.draw(background, 0, 0);
		batch.draw(menu, 0, 0, 400, 400);
		batch.draw(masterHpMpBar, 0, 700, 400, 50);
		batch.draw(playerHpMpBar, 800, 700, 400, 50);
		batch.end();
	}
	
	public void dispose() {
		background.dispose();
		menu.dispose();
		masterHpMpBar.dispose();
		playerHpMpBar.dispose();
	}
	
}

*/
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class BattleField {
	
	private OrthographicCamera camera;
	
	public Texture background;
	public Texture menu, masterHpMpBar, playerHpMpBar;
	
	public Rectangle boundaries;
	
	public SpriteBatch batch;
	
	public Player player;
	
	public InputHandler input;
	
	public BattleField() {
		
		// image of World background loaded as a Texture
		background = new Texture("BattleField/Sky.jpg");
		menu = new Texture("BattleField/menu/button_default.png");
		masterHpMpBar = new Texture("BattleField/menu/bar_hp_mp.png");
		playerHpMpBar = new Texture("BattleField/menu/bar_hp_mp.png");
		
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
	
	public BattleField(String fileName, float width, float height) {
		this();
		boundaries = new Rectangle(0, 0, width, height);
	}
	
	public void draw() {
	    batch.setProjectionMatrix(camera.combined);
	        
		batch.begin();
		batch.draw(background, 0, 0);
		batch.draw(menu, 0, 0, 400, 400);
		batch.draw(masterHpMpBar, 0, 700, 400, 50);
		batch.draw(playerHpMpBar, 0, 0, 400, 50);
		batch.draw(player.sprite, player.position.x, player.position.y);
		input.move(player, camera);
		batch.end();
	}
	
	public void dispose() {
		background.dispose();
	}
	
}