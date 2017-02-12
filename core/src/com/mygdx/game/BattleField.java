package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class BattleField {
	
	private OrthographicCamera camera;
	private Vector3 mouse;
	
	public Texture background, worldBackGround;
	public Texture attack, specialAttack, defence, escape;
	public Texture masterHpMpBar, playerHpMpBar;
	
	public Rectangle boundaries;
	
	public SpriteBatch batch;
	
	public InputHandler input;
	
	public BattleField() {
		
		// image of World background loaded as a Texture
		background = new Texture("BattleField/Sky.jpg");
		worldBackGround = new Texture("forest_preview.png");
		attack = new Texture("BattleField/menu/confirm_bg.png");
		specialAttack = new Texture("BattleField/menu/confirm_bg.png");
		defence = new Texture("BattleField/menu/confirm_bg.png");
		escape = new Texture("BattleField/menu/confirm_bg.png");
		masterHpMpBar = new Texture("BattleField/menu/bar_hp_mp.png");
		playerHpMpBar = new Texture("BattleField/menu/bar_hp_mp.png");
		
		// used to draw each Texture
		batch = new SpriteBatch();
	}
	
	public BattleField(String fileName, float width, float height) {
		this();
		boundaries = new Rectangle(0, 0, width, height);
	}
	
	public void draw() {
	        
		batch.begin();
		
		batch.draw(background, 0, 0, 750, 500);
		batch.draw(attack, 500, 140, 100, 50);
		batch.draw(attack, 500, 200, 100, 50);
		batch.draw(attack, 500, 260, 100, 50);
		batch.draw(attack, 500, 320, 100, 50);
		batch.end();
	}
	
	public void dispose() {
		background.dispose();
	}
	
}