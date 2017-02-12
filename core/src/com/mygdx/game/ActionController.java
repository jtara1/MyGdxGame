package com.mygdx.game;

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
	}
	
	/**
	 * 
	 * @param action
	 * @return false if this action is done
	 */
	public boolean update()
	{
		if(isActionEnd)
			return false;
		else
		{
			if(thisAction.getAction().equals("basicAttack"))
				isActionEnd=this.BasicAttack();
			return isActionEnd;
		}
	}
	
	/**
	 * call this when a new action is receiving
	 * @param action
	 */
	public void loadNewAction(ActionReceiver action,SpriteBatch batch)
	{
		thisAction=action;
		isActionEnd=false;
		
		initialLoc=new Vector2(thisAction.getIssuer().getLocation());
		
		destination=new Vector2(thisAction.getReceiver().getLocation());
		
		attackStage=0;
		
		velocity=new Vector2((destination.x-initialLoc.x)/100f,(destination.y-initialLoc.y)/100f);
		
		
	}
	
	/**
	 * 
	 * @return true if actionEnd
	 */
	public boolean BasicAttack()
	{
		boolean attackDone;
		switch(attackStage) 
		{
		case 0: //the action has not started yet
			attackStage=1;
			attackDone=false;
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
			attackDone=false;
			break;
		case 2: //get to the destination and start to attack
			if(thisAction.getIssuer().basicAttack())
				attackStage=3;
			attackDone=false;
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
			attackDone=false;
			break;
		case 4:
			thisAction.getIssuer().stand();
			attackDone=true;
			break;
		default:
			attackDone=false;
		}	
		return attackDone;
	}
	
	/**
	 * 
	 * @return true if actionEnd
	 */
	public boolean FireBall()
	{
		boolean attackDone;
		switch(attackStage) 
		{
		case 0: //the action has not started yet
			attackStage=1;
			attackDone=false;
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
			attackDone=false;
			break;
		case 2: //get to the destination and start to attack
			if(thisAction.getIssuer().basicAttack())
				attackStage=3;
			attackDone=false;
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
			attackDone=false;
			break;
		case 4:
			thisAction.getIssuer().stand();
			attackDone=true;
			break;
		default:
			attackDone=false;
		}	
		return attackDone;
	}
	

	
}
