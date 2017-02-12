package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class BattlePlayer extends BattleAgent {
	 
	
	private Texture spritePage;

	public BattlePlayer(Vector2 location)
	{
		int spriteSize=64;
		
		spritePage= new Texture(Gdx.files.internal("LPC imp/attack - sword shield.png"));
		TextureRegion[] attackSprites=new TextureRegion[4];
		TextureRegion[] moveSprites=new TextureRegion[2];
		
		for(int i=0;i<4;i++)
		{
			attackSprites[i]=new TextureRegion(spritePage,i * spriteSize , 
					spriteSize * 3 , spriteSize , spriteSize);
		}
		
		moveSprites[0]=attackSprites[0];
		moveSprites[1]=attackSprites[3];
		TextureRegion standingSprite=moveSprites[0];
		
		super.setUp(location, standingSprite, attackSprites, moveSprites);
	}

	
	


}
