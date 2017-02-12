package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import gdxpacks.GdxPacks.PackB1;
import jphys.CollisionManager;

public class PeerController {
	private Player player;
	private float dX;
	private float dY;
	private String name;
	private int peerID;
	private float textXOff;
	private BitmapFont font;
	
	public PeerController(CollisionManager colManager, int id, String name, int heroID) {
		player = new Player(colManager);
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
		player.move(dX, dY);
		if (font == null) {
			font = new BitmapFont();
			font.getData().setScale(1f);
			font.setColor(1, 1, 1, .9f);
			final GlyphLayout layout = new GlyphLayout(font, name);
			// or for non final texts: layout.setText(font, text);

			textXOff = layout.width / 2;
		}
		font.draw(batch, name, player.position.x + player.getBoundaries().width / 2 - textXOff, player.position.y + player.getBoundaries().height + 30);
		player.draw(batch);
	}
	
	public int getPeerID() {
		return peerID;
	}
}
