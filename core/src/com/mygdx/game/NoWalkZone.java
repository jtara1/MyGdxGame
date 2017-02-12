package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;

public class NoWalkZone {
	public Rectangle boundaries;
	
	public NoWalkZone(float x, float y, float width, float height) {
		boundaries = new Rectangle(x, y, width, height);
		
	}
}
