/**
 * 
 */
package com.mygdx.game;


public class ActionReceiver {
	
	private BattleAgent issuer;
	
	private BattleAgent receiver;
	
	private int priority;
	
	private String actionType;
	
	private boolean hasReceived=false;
	
	
	public void receiving(BattleAgent issuer,BattleAgent receiver,String actionType,int priority)
	{
		this.issuer=issuer;
		this.receiver=receiver;
		this.actionType=actionType;
		this.priority=priority;
		hasReceived=true;
	}
	
	public boolean hasReceived()
	{
		return hasReceived;
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
	
	public void done()
	{
		hasReceived=false;
	}

}
