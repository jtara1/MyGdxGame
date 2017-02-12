package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.ExternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.google.protobuf.*;
import com.google.protobuf.AbstractMessage.Builder;

import protoclient.Client;

public class MyGdxGame extends ApplicationAdapter {
	public enum GAME_STATE {
		MAIN_MENU,
		WORLD,
		COMBAT,
		MULTIPLAYER
	}
	
	SpriteBatch batch;
	Texture img;
	Texture playerSprite;
	TextureRegion playerStance;
	
	TiledMap map;
	
	BattleField battlefield;
	
	public World world;
	public Lobby lobby;
	public MainMenu menu;
	
	public static GAME_STATE GameState = GAME_STATE.WORLD;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		playerSprite = new Texture("BODY_animation.png");
		playerStance = new TextureRegion(playerSprite, 0, 0, 64, 64);
		world = new World("forest_preview.png");
//		map = new TmxMapLoader(new ExternalFileHandleResolver()).load("map.tmx");
		if (GameState == GAME_STATE.MULTIPLAYER) {
			//lobby = new Lobby("127.0.0.1", 5000, new LobbyMember("what up"));
			menu = new MainMenu();
		}
		battlefield = new BattleField();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		switch(GameState) {
		case MAIN_MENU:
			break;
		case WORLD:
			world.draw();
			break;
		case COMBAT:
			battlefield.draw();
			break;
		case MULTIPLAYER:
			if (menu == null) {
				lobby.draw();
				if (lobby.hasFailed()) {
					System.out.println("Lobby fail");
					lobby = null;
					menu = new MainMenu();
				}
				else if (lobby.hasFinished()) {
					
				}
			}
			else
			{
				menu.draw();
				if (menu.isFinished())
				{
					try {
						int port = Integer.decode(menu.getPort());
						lobby = new Lobby(menu.getIP(), port, new LobbyMember(menu.getName()));
						menu = null;
					} catch(NumberFormatException ex) {
						menu = new MainMenu();
					}
				}
			}
			break;
		}
		
		
//		batch.begin();
//		batch.draw(img, 0, 0);
//		batch.draw(playerStance, 0, 0);
//		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		playerSprite.dispose();
		world.dispose();
	}
}
