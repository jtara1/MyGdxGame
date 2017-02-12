package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame.GAME_STATE;

public class BattleTest extends ApplicationAdapter {
	SpriteBatch batch;
	BattleAgent monster1,player;

	ActionController ac;
	ActionReceiver ar;
	
	public World world;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		monster1=new Monster(new Vector2(100,100));
		player=new BattlePlayer(new Vector2(425,250));
		ac=new ActionController();
		ar=new ActionReceiver(monster1, player, "basicAttack", 0);
		ac.loadNewAction(ar);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		
		ac.update(batch);
		if(Gdx.input.isKeyPressed(Keys.A))
		{
			
			ac.loadNewAction(ar);
		}
		

		batch.draw(monster1.curSprite, monster1.getLocation().x,monster1.getLocation().y);
		batch.draw(player.curSprite, player.getLocation().x,player.getLocation().y);
		batch.end();
	
	}
}
