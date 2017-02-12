package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;


public class BattleField {
	private Texture background, menu, masterHpMpBar, playerHpMpBar;
	private TextureRegion menuPo, masterHpMpBarPo, playerHpMpBarPo;
	private Master master;
	private Player player;
	
	public BattleField(String filename) {
		// image of background, menu, hpMpbars
		background = new Texture(filename);
		menu = new Texture("botton_default.png");
		menuPo = new TextureRegion(menu, 0, 0, 400, 400);
		masterHpMpBar = new Texture("bar_hp_mp.png");
		masterHpMpBarPo = new TextureRegion(masterHpMpBar, 0, 700, 400, 50);
		playerHpMpBar = new Texture("bar_hp_mp.png");
		playerHpMpBarPo = new TextureRegion(playerHpMpBar, 800, 700, 400, 50);
		master = new Master();
		player = new Player();
	}
	
	public BattleField(String fileName, float width, float height) {
		this(fileName);
		boundaries = new Rectangle(0, 0, width, height);
	}
	
	public void draw() {
		batch.begin();
		batch.draw(background, 0, 0);
		batch.draw(player.playerSprite, player.position.x, player.position.y);
		batch.end();
	}
}
