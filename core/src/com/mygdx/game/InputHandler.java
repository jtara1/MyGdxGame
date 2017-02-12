package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class InputHandler {

	public InputHandler() {
		// TODO Auto-generated constructor stub
	}
	
	public void move(Player player) {
		if(Gdx.input.isKeyPressed(Keys.DPAD_LEFT)) {
		      player.position.x -= Gdx.graphics.getDeltaTime() * player.speed;
		      player.sprite = player.sprites[1];
		   }
		   if(Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)) { 
			   player.position.x += Gdx.graphics.getDeltaTime() * player.speed;
			   player.sprite = player.sprites[3];
		   }
		   if(Gdx.input.isKeyPressed(Keys.DPAD_UP)) { 
		      player.position.y += Gdx.graphics.getDeltaTime() * player.speed;
		      player.sprite = player.sprites[0];
		   }
		   if(Gdx.input.isKeyPressed(Keys.DPAD_DOWN)) { 
			   player.position.y -= Gdx.graphics.getDeltaTime() * player.speed;
			   player.sprite = player.sprites[2];
		   }
	}
}
