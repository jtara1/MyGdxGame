package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class ActionController {
	
	private SpriteBatch batch;
	
	private ActionReceiver thisAction;
	
	private boolean isActionEnd=true;
	
	private Vector2 initialLoc;
	
	private Vector2 destination;
	
	private Vector2 velocity;
		
	private int attackStage;	
	
	private Texture fireball;
	
	public ActionController(SpriteBatch batch)
	{
		this.batch=batch;
		fireball=new Texture(Gdx.files.internal("Magic/Fireball1_GD_BlueKPL.png"));
	}
	
	/**
	 * 
	 * @param action
	 * "basicAttack" for basic attack
	 * @return true if the action is still processing
	 */
	public boolean update()
	{
		if(isActionEnd)
			return false;
		else
		{
			if(thisAction.getAction().equals("basicAttack"))
				isActionEnd=this.BasicAttack();
			return true;
		}
	}
	
	/**
	 * call this when a new action is receiving
	 * @param action
	 */
	public void loadNewAction(ActionReceiver action)
	{
		thisAction=action;
		isActionEnd=false;
		
		initialLoc=new Vector2(thisAction.getIssuer().getLocation());
		
		destination=new Vector2(thisAction.getReceiver().getLocation());
		
		velocity=new Vector2((destination.x-initialLoc.x)/100f,(destination.y-initialLoc.y)/100f);
		
		attackStage=0;
		
		
	}
	
	/**
	 * 
	 * @return true if actionEnd
	 */
	public boolean BasicAttack()
	{
		boolean attackDone=false;
		switch(attackStage) 
		{
		case 0: //the action has not started yet
			attackStage=1;
			break;
		case 1: //the issuer is moving
			if(thisAction.getIssuer().getLocation().x==destination.x &&
			thisAction.getIssuer().getLocation().y==destination.y)
				attackStage=2;
			else
			{
				thisAction.getIssuer().changeLocation(thisAction.getIssuer().getLocation().add(velocity));
				thisAction.getIssuer().move();
			}
			break;
		case 2: //get to the destination and start to attack
			if(thisAction.getIssuer().basicAttack())
				attackStage=3;
			break;
		case 3: //the attack is done and moving back
			if(thisAction.getIssuer().getLocation().x==initialLoc.x &&
					thisAction.getIssuer().getLocation().y==initialLoc.y)
				attackStage=4;
			else
			{
				thisAction.getIssuer().changeLocation(thisAction.getIssuer().getLocation().mulAdd(velocity, -1f));
				thisAction.getIssuer().move();
			}
			break;
		case 4:
			thisAction.getIssuer().stand();
			thisAction.done();
			attackDone=true;
			break;
		}	
		return attackDone;
	}
	


	
}
