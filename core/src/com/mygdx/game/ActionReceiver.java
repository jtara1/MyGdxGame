/**
 * 
 */
package com.mygdx.game;


public class ActionReceiver {
	
	private Agent issuer;
	
	private Agent receiver; //the index of action receiver
	
	private int priority;
	
	private String actionType;
	
	
	public ActionReceiver(Agent issuer,Agent receiver,String actionType,int priority)
	{
		this.issuer=issuer;
		this.receiver=receiver;
		this.actionType=actionType;
		this.priority=priority;
	}
	
	public Agent getIssuer()
	{
		return issuer;
	}
	
	public Agent getReceiver()
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
