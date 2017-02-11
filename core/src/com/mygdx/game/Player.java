package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Player {
	private Texture playerSpritePage;
	
	public TextureRegion playerSprite;
	public TextureRegion[] playerSprites;
	public Vector2 position;
	public Vector2 velocity;
	
	public Player() {
		playerSpritePage = new Texture("mage walking poses sheet copy.png");
		
		int spriteSize = 64;
		playerSprites = new TextureRegion[4];
		for (int i = 0; i < 4; i++) {
			playerSprites[i] = new TextureRegion(
					playerSpritePage, 
					spriteSize * i, 
					spriteSize * i, 
					spriteSize,
					spriteSize);
		}
		
		playerSprite = playerSprites[2];
		
		position = new Vector2(0, 0);
		velocity = new Vector2(0, 0);
	}
	
	public void update() {
		position.add(velocity);
	}
}
