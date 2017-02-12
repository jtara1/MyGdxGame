package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Monster extends BattleAgent {
	 
	
	private Texture spritePage;

	public Monster(Vector2 location)
	{
		int spriteSize=64;
		
		spritePage= new Texture(Gdx.files.internal("LPC imp/attack - pitchfork shield.png"));
		TextureRegion[] attackSprites=new TextureRegion[4];
		TextureRegion[] moveSprites=new TextureRegion[2];
		
		for(int i=0;i<4;i++)
		{
			attackSprites[i]=new TextureRegion(spritePage,i * spriteSize , 
					spriteSize * 2 , spriteSize , spriteSize);
		}
		
		moveSprites[0]=attackSprites[0];
		moveSprites[1]=attackSprites[3];
		TextureRegion standingSprite=moveSprites[0];
		
		super.setUp(location, standingSprite, attackSprites, moveSprites);
	}

	
	


}
