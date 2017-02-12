package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {
	public static final Texture spritePage = new Texture("mage walking poses sheet copy.png");
	private int spriteSize = 64;
	private Rectangle boundaries;
	
	public TextureRegion sprite;
	public TextureRegion[] sprites;
	public Vector2 position;
	public Vector2 velocity;
	public float speed;
	public float distancePerFrame;
	
	public Player() {
//		super(0, 0, (float)spriteSize, (float)spriteSize);
//		spritePage = new Texture("mage walking poses sheet copy.png");

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
		speed = 180f;
		distancePerFrame = speed / 50f;
		
//		boundaries = new Rectangle(position.x, position.y, spriteSize, spriteSize);
	}
	
	/**
	 * 
	 * @param direction {0 : up, 1 : right, 2 : down, 3 : left} as defined in World class
	 */
	public void move(int direction) {
		
		switch(direction) {
		case(World.DIRECTION_UP):
			position.y += distancePerFrame;
			sprite = sprites[0];
			break;
		case(World.DIRECTION_RIGHT):
			position.x += distancePerFrame;
			sprite = sprites[3];
			break;
		case(World.DIRECTION_DOWN):
			position.y -= distancePerFrame;
			sprite = sprites[2];
			break;
		case(World.DIRECTION_LEFT):
			position.x -= distancePerFrame;
			sprite = sprites[1];
			break;
		}
	}
	
	public boolean blockadeAhead(int direction, Rectangle blockade) {
		Vector2 aheadPosition = position;
		Rectangle rect;
		switch(direction) {
		case(World.DIRECTION_UP):
			aheadPosition.y += distancePerFrame;
			rect = new Rectangle(aheadPosition.x, aheadPosition.y, spriteSize, spriteSize);
			return blockadeAbove(rect, blockade);
		case(World.DIRECTION_RIGHT):
			aheadPosition.x += distancePerFrame;
			rect = new Rectangle(aheadPosition.x, aheadPosition.y, spriteSize, spriteSize);
			return blockadeRight(rect, blockade);
		case(World.DIRECTION_DOWN):
			aheadPosition.y -= distancePerFrame;
			rect = new Rectangle(aheadPosition.x, aheadPosition.y, spriteSize, spriteSize);
			return blockadeBelow(rect, blockade);
		case(World.DIRECTION_LEFT):
			aheadPosition.x -= distancePerFrame;
			rect = new Rectangle(aheadPosition.x, aheadPosition.y, spriteSize, spriteSize);
			return blockadeLeft(rect, blockade);
		}
		return false;
	}
	
	public void move(float deltaX, float deltaY) {
		this.position.add(new Vector2(deltaX, deltaY));
	}
	
	public Rectangle getBoundaries() {
		return new Rectangle(position.x, position.y, spriteSize, spriteSize);
	}
	
	public boolean blockadeRight(Rectangle rect) {
		boundaries = getBoundaries();
		//System.out.println(boundaries);
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
	
	public boolean blockadeLeft(Rectangle boundaries, Rectangle rect) {
		if (boundaries.overlaps(rect)) {
			if (boundaries.x <= (rect.x + rect.width)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean blockadeBelow(Rectangle boundaries, Rectangle rect) {
		if (boundaries.overlaps(rect)) {
			if (boundaries.y <= (rect.y + rect.height)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean blockadeAbove(Rectangle boundaries, Rectangle rect) {
		if (boundaries.overlaps(rect)) {
			if ((boundaries.y + boundaries.height) >= rect.y) {
				return true;
			}
		}
		return false;
	}
	
	public boolean blockadeRight(Rectangle boundaries, Rectangle rect) {
		System.out.println(boundaries);
		if (boundaries.overlaps(rect)) {
			if ((boundaries.x + boundaries.width) >= rect.x) {
				return true;
			}
		}
		return false;
	}
}
