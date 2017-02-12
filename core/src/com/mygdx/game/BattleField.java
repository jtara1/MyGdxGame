package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public class BattleField {
	private Texture background;
	private Texture menu, masterHpMpBar, playerHpMpBar;
	private Texture player, master;
	
	private Monster monster;
	
	public BattleField(String filename) {
		// image of background, menu, hpMpbars
		background = new Texture(filename);
		menu = new Texture("botton_default.png");
		masterHpMpBar = new Texture("bar_hp_mp.png");
		playerHpMpBar = new Texture("bar_hp_mp.png");
		monster = new Monster(new Vector2(0, 600));
	}
	
	public BattleField(String fileName, float width, float height) {
		this(fileName);
		boundaries = new Rectangle(0, 0, width, height);
	}
	
	public void handleInput() {
		if (Gdx.input.justTouched()) {
			monster.basicAttack();
		}
	}
	
	public void update(float dt) {
		handleInput();
		monster.updata(dt);
		player.updata()
	}
	

	public void render(SpriteBatch sb) {
		sb.draw(background, 0, 0);
		sb.draw(menu, 0, 0, 400, 400);
		sb.draw(masterHpMpBar, 0, 700, 400, 50);
		sb.draw(playerHpMpBar, 800, 700, 400, 50);
		sb.draw(monster.getTexture(), monster.getPosition().x, monster.getPosition().y);
		sb.end();
	}
	
	public void dispose() {
		background.dispose();
		menu.dispose();
		masterHpMpBar.dispose();
		playerHpMpBar.dispose();
	}
}
