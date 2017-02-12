package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Player {
	private Texture spritePage;
	
	public TextureRegion sprite;
	public TextureRegion[] sprites;
	public Vector2 position;
	public Vector2 velocity;
	public float speed;
	
	public Player() {
		spritePage = new Texture("mage walking poses sheet copy.png");
		
		int spriteSize = 64;
		sprites = new TextureRegion[4];
		// sprites of the player facing up, left, down, right
		for (int i = 0; i < 4; i++) {
			sprites[i] = new TextureRegion(
					spritePage, 
					spriteSize * i, 
					spriteSize * i, 
					spriteSize,
					spriteSize);
		}
		
		sprite = sprites[2];
		
		position = new Vector2(500, 500);
		velocity = new Vector2(0, 0);
		speed = 60f;
	}
}
