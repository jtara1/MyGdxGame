/**
 * 
 */
package com.mygdx.game;


public class ActionReceiver implements Comparable<ActionReceiver> {
	
	private BattleAgent issuer;
	
	private BattleAgent receiver;
	
	private int priority;
	
	private String actionType;
	
	private boolean hasReceived=false;
	
	
	public ActionReceiver(BattleAgent issuer,BattleAgent receiver,String actionType,int priority)
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
	



	@Override
	public int compareTo(ActionReceiver o) {
		if(this.getPriority()<this.getPriority())
			return -1;
		else if (this.getPriority()==o.getPriority())
			return 0;
		else 
			return 1;
	}

}
