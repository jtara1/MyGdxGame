package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Player {
	private Texture playerSpritePage;
	
	public TextureRegion playerSprite;
	public Vector2 position;
	public Vector2 velocity;
	
	public Player() {
		playerSpritePage = new Texture("mage walking poses sheet copy.png");
		playerSprite = new TextureRegion(playerSpritePage, 0, 0, 64, 64); 
		
		position = new Vector2(0, 0);
		velocity = new Vector2(0, 0);
	}
}
