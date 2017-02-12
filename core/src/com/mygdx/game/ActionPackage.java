package com.mygdx.game;

import java.util.PriorityQueue;

public class ActionPackage {
	
	PriorityQueue<ActionReceiver> actionList;
	
	ActionPackage()
	{
		actionList=new PriorityQueue<ActionReceiver> ();
	}
	
	void addAction(ActionReceiver action)
	{
		actionList.add(action);

	}

	ActionReceiver getAction()
	{
		return actionList.poll();
	}
	
	boolean isEmpty()
	{
		return actionList.isEmpty();
	}
	
	boolean fullPack()
	{
		return actionList.size()==2?true:false;
	}
}
