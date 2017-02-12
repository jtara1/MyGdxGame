package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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
	
	private ActionController ac;
	
	private ActionReceiver ar;
	
	private BattleAgent monster1,player;
	
	public BattleField() {
		
		monster1=new Monster(new Vector2(100,100));
		player=new BattlePlayer(new Vector2(425,250));
		ac=new ActionController(batch);
		ar=new ActionReceiver();
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
		monster1=new Monster(new Vector2(100,100));
		player=new BattlePlayer(new Vector2(425,250));
	}
	
	public void draw() {
	        
		batch.begin();
		batch.draw(background, 0, 0, 750, 500);
		
		boolean isActioning=ac.update();
		if(isActioning)
			System.out.println("A");
		else
		{
			drawActionMenu();
			System.out.println("C");
			Gdx.input.setInputProcessor(new ActionInputProcessor(player, monster1, ar));
			if(ar.hasReceived())
			{
				ac.loadNewAction(ar);
				System.out.println("B");
			}
		}
		

		batch.draw(monster1.curSprite, monster1.getLocation().x,monster1.getLocation().y);
		batch.draw(player.curSprite, player.getLocation().x,player.getLocation().y);
		
		
		batch.end();
	}
	
	public void drawActionMenu() {
		
		batch.draw(attack, 500, 140, 100, 50);
		batch.draw(attack, 500, 200, 100, 50);
		batch.draw(attack, 500, 260, 100, 50);
		batch.draw(attack, 500, 320, 100, 50);
	}
	

	
	public void dispose() {
		background.dispose();
	}
	
}