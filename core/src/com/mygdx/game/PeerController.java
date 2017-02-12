package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import gdxpacks.GdxPacks.PackB1;

public class PeerController {
	private Player player;
	private float dX;
	private float dY;
	private String name;
	private int peerID;
	
	public PeerController(int id, String name, int heroID) {
		player = new Player();
		this.peerID = id;
		this.name = name;
	}
	
	public void update(PackB1 packB1) {
		player.position.x = packB1.getX();
		player.position.y = packB1.getY();
		dX = packB1.getDX();
		dY = packB1.getDY();
	}
	
	public void draw(SpriteBatch batch) {
		//player.move(dX, dY);
		batch.draw(player.sprite, player.position.x, player.position.y);
	}
	
	public int getPeerID() {
		return peerID;
	}
}
