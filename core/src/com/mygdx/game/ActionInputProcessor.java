package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.InputProcessor;

public class ActionInputProcessor implements InputProcessor {

	private BattleAgent issuer;
	
	private BattleAgent receiver;
	
	private ActionPackage pack;
	
	public ActionInputProcessor(BattleAgent issuer,BattleAgent receiver,
			ActionPackage pack)
	{
		this.issuer=issuer;
		this.receiver=receiver;
		this.pack=pack;
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
			pack.addAction(new ActionReceiver(issuer, receiver, input, 99));
			pack.addAction(new ActionReceiver(receiver, issuer, "basicAttack", 15));
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
		if(x>=500&&x<=600&&y>=320&&y<=370)
			return "basicAttack";
		else if(x>=500&&x<=600&&y>=200&&y<=250)
			return "defense";
		else if(x>=500&&x<=600&&y>=260&&y<=310)
			return "fireBall";
		else 
			return null;
	}

}
