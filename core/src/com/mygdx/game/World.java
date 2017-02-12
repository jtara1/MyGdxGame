package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGdxGame.GAME_STATE;

public class World {
	public final int DIRECTION_UP = 0;
	public final int DIRECTION_RIGHT = 1;
	public final int DIRECTION_DOWN = 2;
	public final int DIRECTION_LEFT = 3;
	public final int DIRECTION_NONE = -1;
	
	public Texture background;
	
	public Rectangle boundaries;
	
	public SpriteBatch batch;
	
	public Player player;
	
	public ArrayList<NoWalkZone> noWalkZones;
	
	public ArrayList<Monster> monsters;

	public InputHandler input;
	
	public OrthographicCamera camera;
	
	public World(String fileName) {
		
		// image of World background loaded as a Texture
		background = new Texture(fileName);
		
		// used to draw each Texture
		batch = new SpriteBatch();
		
		// back default boundaries the size of the background image
		boundaries = new Rectangle(0, 0, background.getWidth(), background.getHeight());
		
		player = new Player();
		
		noWalkZones = new ArrayList<NoWalkZone>();
		monsters = new ArrayList<Monster>();
		
		createNoWalkZones();
		setMonsters();

		input = new InputHandler();
		
		float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        
        //Creates a new camera, sets its position focused on the player, and zooms out
        camera = new OrthographicCamera(30, 30 * (h / w));
        camera.position.set(player.position.x,player.position.y,0);
        camera.zoom += 20;
        camera.update();
	}
	
	public World(String fileName, float width, float height) {
		this(fileName);
		boundaries = new Rectangle(0, 0, width, height);
	}
	public void setMonsters() {
		Vector2 pos1 = new Vector2(200,200);
		Monster mons1 = new Monster(pos1);
		monsters.add(mons1);
		Vector2 pos2 = new Vector2(150,500);
		Monster mons2 = new Monster(pos2);
		monsters.add(mons2);
		Vector2 pos3 = new Vector2(800,400);
		Monster mons3 = new Monster(pos3);
		monsters.add(mons3);
		Vector2 pos4 = new Vector2(500,100);
		Monster mons4 = new Monster(pos4);
		monsters.add(mons4);
		Vector2 pos5 = new Vector2(600,600);
		Monster mons5 = new Monster(pos5);
		monsters.add(mons5);
	}
	public void drawMonsters() {
		for(int i = 0; i < monsters.size(); i++)
			batch.draw(monsters.get(i).curSprite, monsters.get(i).getLocation().x, monsters.get(i).getLocation().y);
	}
	public void checkMonsterCollision(){
		for(int i = 0; i < monsters.size(); i++){
			if(Math.abs(monsters.get(i).getLocation().x - player.position.x) <= 15 &&
					Math.abs(monsters.get(i).getLocation().y - player.position.y) <= 15)
				MyGdxGame.GameState = GAME_STATE.COMBAT;
		}
	}
	public void createNoWalkZones() {
		NoWalkZone zone1 = new NoWalkZone(335, 149, 130, 140);
		noWalkZones.add(zone1);
	}
	
	public void draw() {
	    batch.setProjectionMatrix(camera.combined);
	        
		batch.begin();
		batch.draw(background, 0, 0);
		
		int offset = 10;
		int[] sidesBlocked = playerCollidedWithNoWalkZone();
		// debug
//		for (int i = 0; i < sidesBlocked.length; i++) {
//			System.out.print(sidesBlocked[i] + " ");
//		} System.out.println();
		
		if(Gdx.input.isKeyPressed(Keys.DPAD_LEFT)) {
			if (sidesBlocked[3] == DIRECTION_NONE) {
				player.position.x -= Gdx.graphics.getDeltaTime() * player.speed;
				camera.translate(-Gdx.graphics.getDeltaTime() * player.speed, 0, 0);
			} else {
				player.position.x += Gdx.graphics.getDeltaTime() * player.speed * 2;
				camera.translate(Gdx.graphics.getDeltaTime() * player.speed * 2, 0, 0);
			}
			player.sprite = player.sprites[1];
		}
		if(Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)) {
			if (sidesBlocked[1] == DIRECTION_NONE) {
				player.position.x += Gdx.graphics.getDeltaTime() * player.speed;
				camera.translate(Gdx.graphics.getDeltaTime() * player.speed, 0, 0);
			} else {
				player.position.x -= Gdx.graphics.getDeltaTime() * player.speed * 2;
				camera.translate(-Gdx.graphics.getDeltaTime() * player.speed * 2, 0, 0);
			}
			player.sprite = player.sprites[3];
		}
		if(Gdx.input.isKeyPressed(Keys.DPAD_UP)) {
			if (sidesBlocked[0] == DIRECTION_NONE) {
				player.position.y += Gdx.graphics.getDeltaTime() * player.speed;
				camera.translate(0, Gdx.graphics.getDeltaTime() * player.speed, 0);
			} else {
				player.position.y -= Gdx.graphics.getDeltaTime() * player.speed * 2;
				camera.translate(0, -Gdx.graphics.getDeltaTime() * player.speed * 2, 0);
			}
			player.sprite = player.sprites[0];
		}
		if(Gdx.input.isKeyPressed(Keys.DPAD_DOWN)) {
			if (sidesBlocked[2] == DIRECTION_NONE) {
				player.position.y -= Gdx.graphics.getDeltaTime() * player.speed;
				camera.translate(0, -Gdx.graphics.getDeltaTime() * player.speed, 0);
			} else {
				player.position.y += Gdx.graphics.getDeltaTime() * player.speed * 2;
				camera.translate(0, Gdx.graphics.getDeltaTime() * player.speed * 2, 0);
			}
			player.sprite = player.sprites[2];
		}
		camera.update();
			   
//		System.out.println("Player position: " + player.position);
//		playerCollidedWithNoWalkZone();
		
		batch.draw(player.sprite, player.position.x, player.position.y);
		drawMonsters();
		checkMonsterCollision();
		
//		input.move(player, camera);
		batch.end();
	}
	
	/**
	 * 
	 * @return array containing 4 ints, each representing a side blocked or not0 if blockade above player,
	 * 1 if blockade right of player
	 * 2 if blockade below player
	 * 3 if blockade left of player
	 */
	public int[] playerCollidedWithNoWalkZone() {
		int[] allSidesClear = {DIRECTION_NONE, DIRECTION_NONE, DIRECTION_NONE, DIRECTION_NONE};
		
		for (NoWalkZone zone : noWalkZones) {
			System.out.println("working");
			int[] sidesBlocked = allSidesClear;
			
			if (player.blockadeAbove(zone.boundaries)) {
				sidesBlocked[0] = 1;
			} 
			if (player.blockadeRight(zone.boundaries)) {
				sidesBlocked[1] = 1;
			} 
			if (player.blockadeBelow(zone.boundaries)) {
				sidesBlocked[2] = 1;
			} 
			if (player.blockadeLeft(zone.boundaries)){
				sidesBlocked[3] = 1;
			}

			if (sidesBlocked != allSidesClear) {
				return sidesBlocked;
			}
		}
		return allSidesClear;
	}
	
	public boolean isPlayerOutOfBounds() {
		return !boundaries.contains(player.getBoundaries());
	}
	
	public void dispose() {
		background.dispose();
	}
}
