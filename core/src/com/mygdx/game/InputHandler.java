package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class InputHandler {

	public InputHandler() {
		// TODO Auto-generated constructor stub
	}
	
	public void move(Player player, OrthographicCamera camera) {
		if(Gdx.input.isKeyPressed(Keys.DPAD_LEFT)) {
		      player.position.x -= Gdx.graphics.getDeltaTime() * player.speed;
		      player.sprite = player.sprites[1];
		      camera.translate(-Gdx.graphics.getDeltaTime() * player.speed, 0, 0);
		   }
		   if(Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)) { 
			   player.position.x += Gdx.graphics.getDeltaTime() * player.speed;
			   player.sprite = player.sprites[3];
			   camera.translate(Gdx.graphics.getDeltaTime() * player.speed, 0, 0);
		   }
		   if(Gdx.input.isKeyPressed(Keys.DPAD_UP)) { 
		      player.position.y += Gdx.graphics.getDeltaTime() * player.speed;
		      player.sprite = player.sprites[0];
		      camera.translate(0, Gdx.graphics.getDeltaTime() * player.speed, 0);
		   }
		   if(Gdx.input.isKeyPressed(Keys.DPAD_DOWN)) { 
			   player.position.y -= Gdx.graphics.getDeltaTime() * player.speed;
			   player.sprite = player.sprites[2];
			   camera.translate(0, -Gdx.graphics.getDeltaTime() * player.speed, 0);
		   }
	}
}
