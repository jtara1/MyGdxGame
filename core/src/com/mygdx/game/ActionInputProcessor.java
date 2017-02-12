package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.InputProcessor;

public class ActionInputProcessor implements InputProcessor {

	private BattleAgent issuer;
	
	private BattleAgent receiver;
	
	private ActionReceiver ar;
	
	public ActionInputProcessor(BattleAgent issuer,BattleAgent receiver,
			ActionReceiver ar)
	{
		this.issuer=issuer;
		this.receiver=receiver;
		this.ar=ar;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		String input=actionCheck(screenX, screenY);
		if (input!=null)
		{
			ar.receiving(issuer, receiver, input, 1);
			return true;
		}
		else
			return false;
			
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private String actionCheck(int x,int last_y)
	{
		int y= Gdx.graphics.getHeight()-last_y;
		System.out.println(x);
		System.out.println(y);
		if(x>=500&&x<=600&&y>=140&&y<=190)
			return "basicAttack";
		else 
			return null;
	}

}
