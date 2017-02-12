package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {
	private Texture spritePage;
	private static final int spriteSize = 64;
	private Rectangle boundaries;
	
	public TextureRegion sprite;
	public TextureRegion[] sprites;
	public Vector2 position;
	public Vector2 velocity;
	public float speed;
	
	public Player() {
//		super(0, 0, (float)spriteSize, (float)spriteSize);
		spritePage = new Texture("mage walking poses sheet copy.png");

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
		
		position = new Vector2(0, 0);
		velocity = new Vector2(0, 0);
		speed = 180f;
		
//		boundaries = new Rectangle(position.x, position.y, spriteSize, spriteSize);
	}
	
	public Rectangle getBoundaries() {
		return new Rectangle(position.x, position.y, spriteSize, spriteSize);
	}
	
	public boolean inHeightRange(Rectangle rect) {
		boundaries = getBoundaries();
		if (boundaries.y <= rect.y + rect.height &&
			boundaries.y + boundaries.height >= rect.y) {
			
			return true;
		}
		return false;
	}
	
	public boolean inWidthRange(Rectangle rect) {
		boundaries = getBoundaries();
		if (boundaries.x + boundaries.width >= rect.x &&
			boundaries.x <= rect.x + rect.width) {
			
			return true;
		}
		return false;
	}
	
	public boolean blockadeRight(Rectangle rect) {
		boundaries = getBoundaries();
		System.out.println(boundaries);
		if (boundaries.overlaps(rect)) {
			if ((boundaries.x + boundaries.width) >= rect.x) {
				return true;
			}
		}
		return false;
	}
	
	public boolean blockadeLeft(Rectangle rect) {
		boundaries = getBoundaries();
		if (boundaries.overlaps(rect)) {
			if (boundaries.x <= (rect.x + rect.width)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean blockadeBelow(Rectangle rect) {
		boundaries = getBoundaries();
		if (boundaries.overlaps(rect)) {
			if (boundaries.y <= (rect.y + rect.height)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean blockadeAbove(Rectangle rect) {
		boundaries = getBoundaries();
		if (boundaries.overlaps(rect)) {
			if ((boundaries.y + boundaries.height) >= rect.y) {
				return true;
			}
		}
		return false;
	}
}
