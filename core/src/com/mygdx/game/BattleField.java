package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
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
	public Texture text1, text2, text3, text4;
	public Texture masterHpMpBar, playerHpMpBar;
	
	public Rectangle boundaries;
	
	public ActionPackage actionPack;
	
	public SpriteBatch batch;
	
	public InputHandler input;
	
	private ActionController ac;
	
	
	private BattleAgent monster1,player;
	
	public BattleField() {
		
		batch = new SpriteBatch();
		actionPack=new ActionPackage();
		monster1=new Monster(new Vector2(100,200));
		player=new BattlePlayer(new Vector2(350,250));
		ac=new ActionController(batch);
		// image of World background loaded as a Texture
		background = new Texture("BattleField/Sky.jpg");
		worldBackGround = new Texture("forest_preview.png");
		attack = new Texture("BattleField/menu/confirm_bg.png");
		text1 = new Texture("Picture1.png");
		specialAttack = new Texture("BattleField/menu/confirm_bg.png");
		text2 = new Texture("Picture2.png");
		defence = new Texture("BattleField/menu/confirm_bg.png");
		text3 = new Texture("Picture3.png");
		escape = new Texture("BattleField/menu/confirm_bg.png");
		text4 = new Texture("Picture4.png");
		masterHpMpBar = new Texture("BattleField/menu/bar_hp_mp.png");
		playerHpMpBar = new Texture("BattleField/menu/bar_hp_mp.png");
		
		// used to draw each Texture
		
	}
	
	public BattleField(String fileName, float width, float height) {
		this();
		boundaries = new Rectangle(0, 0, width, height);
	}
	
	private boolean actionMode = false;
	
	public void draw() {
		
		batch.begin();
		
		batch.draw(background, 0, 0, 750, 500);
		
		if(actionMode)
		{ 
			boolean isActioning=ac.update();
			
			if(!isActioning&&actionPack.isEmpty())
				actionMode=false;
			else if(!isActioning)
				ac.loadNewAction(actionPack.getAction());
		}
	   
		else{
			
			drawActionMenu();
			Gdx.input.setInputProcessor(new ActionInputProcessor(player, monster1, actionPack));
			if(actionPack.fullPack())
			{
				Gdx.input.setInputProcessor(new InputAdapter());
				actionMode=true;
			}
		}
		
		
		batch.draw(monster1.curSprite, monster1.getLocation().x,monster1.getLocation().y);
		batch.draw(player.curSprite, player.getLocation().x,player.getLocation().y);
		
		
		
		batch.end();
		
		
	}
	
	public void drawActionMenu() {
		
		int height=Gdx.graphics.getHeight();
		int width=Gdx.graphics.getWidth();
		
		batch.draw(attack, 500, 140, 100, 50);
		batch.draw(text4, 500, 140, 100, 50);
		batch.draw(attack, 500, 200, 100, 50);
		batch.draw(text3, 500, 200, 100, 50);
		batch.draw(attack, 500, 260, 100, 50);
		batch.draw(text2, 500, 260, 100, 50);
		batch.draw(attack, 500, 320, 100, 50);
		batch.draw(text1, 500, 320, 100, 50);
		
		batch.draw(masterHpMpBar, 40, 50, 100, 30);
		batch.draw(playerHpMpBar, 350, 400, 100, 30);
	}
	

	
	public void dispose() {
		background.dispose();
	}
	
}
