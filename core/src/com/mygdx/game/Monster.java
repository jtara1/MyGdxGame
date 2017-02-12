package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Monster implements Agent {
	
	private SpriteBatch batch;
	
	private int counter = 0;
	
	private Vector2 location; 
	
	private Texture spritePage;
	
	private TextureRegion standingSprite;
	
	private TextureRegion curSprite;
	
	private TextureRegion[] attackSprites;
	
	private TextureRegion[] moveSprites;
	
	public Monster(Vector2 location)
	{
		batch=new SpriteBatch(); 
		this.location=location;
		int spriteSize=64;
		spritePage= new Texture(Gdx.files.internal("LPC imp/attack - pitchfork shield.png"));
		attackSprites=new TextureRegion[4];
		moveSprites=new TextureRegion[2];
		for(int i=0;i<4;i++)
		{
			attackSprites[i]=new TextureRegion(spritePage,i * spriteSize , 
					spriteSize * 2 , spriteSize , spriteSize);
		}
		
		moveSprites[0]=attackSprites[0];
		moveSprites[1]=attackSprites[3];
		standingSprite=moveSprites[0];
		
		curSprite=standingSprite; //initial the monster is standing facing rightward
		
	}

	/**
	 * This method is for the animation of basicAttack. If the animation done, 
	 * false will be return, else return true.
	 */
	@Override
	public boolean basicAttack() {
		// TODO Auto-generated method stub
		if(counter<3)
		{	
			counter++;
			curSprite=attackSprites[counter];
			return true;
		}
		else
		{
			counter=0;
			return false;
		}
	}
	
	public void move() {
		counter= counter==0?1:0;
		curSprite=moveSprites[counter];	
	}

	@Override
	public void beAttacked() {
		// TODO Auto-generated method stub
		
	}

}
