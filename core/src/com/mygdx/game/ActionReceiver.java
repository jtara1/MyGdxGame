/**
 * 
 */
package com.mygdx.game;


public class ActionReceiver {
	
	private BattleAgent issuer;
	
	private BattleAgent receiver;
	
	private int priority;
	
	private String actionType;
	
	
	public ActionReceiver(BattleAgent issuer,BattleAgent receiver,String actionType,int priority)
	{
		this.issuer=issuer;
		this.receiver=receiver;
		this.actionType=actionType;
		this.priority=priority;
	}
	
	public BattleAgent getIssuer()
	{
		return issuer;
	}
	
	public BattleAgent getReceiver()
	{
		return receiver;
	}
	
	public String getAction()
	{
		return actionType;
	}
	
	public int getPriority()
	{
		return priority;
	}

}
