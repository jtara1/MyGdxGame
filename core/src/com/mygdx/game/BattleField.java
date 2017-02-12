package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;


public class BattleField {
	private Texture background;
	private Texture menu, masterHpMpBar, playerHpMpBar;
	private Master master;
	private Player player;
	
	public BattleField(String filename) {
		// image of background, menu, hpMpbars
		background = new Texture(filename);
		menu = new Texture("botton_default.png");
		masterHpMpBar = new Texture("bar_hp_mp.png");
		playerHpMpBar = new Texture("bar_hp_mp.png");
		master = new Master();
		player = new Player();
	}
	
	public BattleField(String fileName, float width, float height) {
		this(fileName);
		boundaries = new Rectangle(0, 0, width, height);
	}
	
	public void handleInput() {
		//TODO
	}
	
	public void update(float dt) {
		//TODO
	}
	

	public void render(SpriteBatch sb) {
		sb.draw(background, 0, 0);
		sb.draw(menu, 0, 0, 400, 400);
		sb.draw(masterHpMpBar, 0, 700, 400, 50);
		sb.draw(playerHpMpBar, 800, 700, 400, 50);
		sb.end();
	}
}
