package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

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
	
	public World(String fileName) {
		
		// image of World background loaded as a Texture
		background = new Texture(fileName);
		
		// used to draw each Texture
		batch = new SpriteBatch();
		
		// back default boundaries the size of the background image
		boundaries = new Rectangle(0, 0, background.getWidth(), background.getHeight());
		
		player = new Player();
		
		noWalkZones = new ArrayList<NoWalkZone>();
		
		createNoWalkZones();
	}
	
	public World(String fileName, float width, float height) {
		this(fileName);
		boundaries = new Rectangle(0, 0, width, height);
	}
	
	public void createNoWalkZones() {
		NoWalkZone zone1 = new NoWalkZone(335, 149, 130, 140);
		noWalkZones.add(zone1);
	}
	
	public void draw() {
		batch.begin();
		batch.draw(background, 0, 0);
		
		int offset = 10;
		int[] sidesBlocked = playerCollidedWithNoWalkZone();
		// debug
		for (int i = 0; i < sidesBlocked.length; i++) {
			System.out.print(sidesBlocked[i] + " ");
		} System.out.println();
		
		if(Gdx.input.isKeyPressed(Keys.DPAD_LEFT)) {
			if (sidesBlocked[3] == DIRECTION_NONE) {
				player.position.x -= Gdx.graphics.getDeltaTime() * player.speed;
			} else {
				player.position.x += offset;
			}
			player.sprite = player.sprites[1];
		}
		if(Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)) {
			if (sidesBlocked[1] == DIRECTION_NONE) {
				player.position.x += Gdx.graphics.getDeltaTime() * player.speed;
			} else {
				player.position.x -= offset;
			}
			player.sprite = player.sprites[3];
		}
		if(Gdx.input.isKeyPressed(Keys.DPAD_UP)) {
			if (sidesBlocked[0] == DIRECTION_NONE) {
				player.position.y += Gdx.graphics.getDeltaTime() * player.speed;
			} else {
				player.position.y -= offset;
			}
			player.sprite = player.sprites[0];
		}
		if(Gdx.input.isKeyPressed(Keys.DPAD_DOWN)) {
			if (sidesBlocked[2] == DIRECTION_NONE) {
				player.position.y -= Gdx.graphics.getDeltaTime() * player.speed;
			} else {
				player.position.y += offset;
			}
			player.sprite = player.sprites[2];
		}
			   
//		System.out.println("Player position: " + player.position);
		playerCollidedWithNoWalkZone();
//		if (isPlayerOutOfBounds())
//			System.out.println("out of bounds");
		
		batch.draw(player.sprite, player.position.x, player.position.y);
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
