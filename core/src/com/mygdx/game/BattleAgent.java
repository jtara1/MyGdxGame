package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class BattleAgent {
	
	private int attackCounter = 0;
	
	private int moveCounter = 0;
	
	public TextureRegion curSprite;
	
	private boolean isDefense;
	
	Vector2 location; 
	
	TextureRegion standingSprite;
	
	private TextureRegion[] attackSprites;
	
	private TextureRegion[] moveSprites;
	
	/**
	 * 
	 * @param location
	 * @param standingSprite
	 * @param attackSprites
	 * @param moveSprites
	 */
	public void setUp(Vector2 location,TextureRegion standingSprite, 
			TextureRegion[] attackSprites, TextureRegion[] moveSprites)
	{
		this.location=location;
		this.standingSprite=standingSprite;
		this.attackSprites=attackSprites;
		this.moveSprites=moveSprites;
		curSprite=standingSprite;
		isDefense=false;
	}
	
	/**
	 * This method is for the animation of basicAttack. If the animation done, 
	 * true will be return, else return false.
	 */
	public boolean basicAttack() {
		if(attackCounter < 21+(attackSprites.length-1)*10)
		{	
			attackCounter++;
			if(attackCounter-20>=0)
			curSprite=attackSprites[(attackCounter-20)/10];
			return false;
		}
		else
		{
			attackCounter=0;
			return true;
		}
	}
	
	/**
	 * This a an animation of move 
	 */
	public void move() {
		if(moveCounter<moveSprites.length*10-1)
			moveCounter++;
		else
			moveCounter=0;
		curSprite=moveSprites[moveCounter/10];	
	}
	
	public void stand() {
		curSprite=standingSprite;
	}
	
	
	public void changeLocation(Vector2 newLocation) {
		this.location=newLocation;
	}
	
	public Vector2 getLocation()
	{
		return this.location;
	}
	
	public void defense()
	{
		isDefense=true;
	}
	
	public void resetDefense()
	{
		isDefense=false;
	}
	
	public boolean isDefense()
	{
		return isDefense;
	}

}
