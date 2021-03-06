package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import jphys.Body;
import jphys.CollisionManager;

public class Player extends Body {
	private Texture spritePage;
	private static final int spriteSize = 64;
	private Rectangle boundaries;
	private String fileName;
	
	public TextureRegion sprite;
	public TextureRegion[] sprites;
	public Vector2 position;
	public Vector2 velocity;
	public float speed;
	
	public float distancePerFrame;
	
	public Player(CollisionManager collideManager)
	{
		super(collideManager, 500, 500, spriteSize, spriteSize/2);
		fileName = "mage walking poses sheet copy.png";
//		super(0, 0, (float)spriteSize, (float)spriteSize);
		
		position = new Vector2(500, 500);
		velocity = new Vector2(0, 0);
		speed = 180f;
		distancePerFrame = speed / 50f;
		
//		boundaries = new Rectangle(position.x, position.y, spriteSize, spriteSize);
	}
	
	public void draw(SpriteBatch batch) {
		if (spritePage == null) {
			spritePage = new Texture(fileName);
		}
		if (sprites == null) {
			initializeSprites();
		}
		if (sprite == null) {
			sprite = sprites[2];
		}
		batch.draw(sprite, getX(), getY());
	}

	public void initializeSprites() {
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
	}
	
	public void move(int direction) {
		float dX = 0;
		float dY = 0;
		switch(direction) {
		case(World.DIRECTION_UP):
			dY = distancePerFrame;
			sprite = sprites[0];
			break;
		case(World.DIRECTION_RIGHT):
			dX = distancePerFrame;
			sprite = sprites[3];
			break;
		case(World.DIRECTION_DOWN):
			dY = -distancePerFrame;
			sprite = sprites[2];
			break;
		case(World.DIRECTION_LEFT):
			dX = -distancePerFrame;
			sprite = sprites[1];
			break;
		}
		super.move(dX, dY);
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
