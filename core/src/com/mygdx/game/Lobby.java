package com.mygdx.game;

import java.awt.Font;
import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.google.protobuf.InvalidProtocolBufferException;

import protoclient.Client;
import protoclient.ConnectionEventHandler;
import protoclient.IPacket;
import protoclient.OPacket;
import protoclient.PacketHandler;
import protoclient.PacketHandlerOwner;
import protopacks.PackFW.PackA0;
import protopacks.PackFW.PackA1;


class LobbyConnectionOpenHandler implements ConnectionEventHandler {
	private Lobby lobby;
	
	public LobbyConnectionOpenHandler(Lobby lobby) {
		this.lobby = lobby;
	}
	
	@Override
	public void run(String err) {
		PackA0.Builder builderA0 = PackA0.newBuilder();
		builderA0.setName(lobby.user.name);
		OPacket oPack = new OPacket("A0", builderA0.build().toByteString());
		oPack.setReliable(false);
		oPack.addSendToID(OPacket.BROADCAST_ID);
		lobby.client.send(oPack);
		lobby.setDisplayLobby(true);
	}
}

class LobbyConnectionCloseHandler implements ConnectionEventHandler{
	private Client client;
	
	public LobbyConnectionCloseHandler(Client client) {
		this.client = client;
	}
	
	@Override
	public void run(String err) {
		client.stop();
	}
}

class PackA0Handler implements PacketHandler {
	private Lobby lobby;
	
	public PackA0Handler(Lobby lobby) 
	{
		this.lobby = lobby;
	}

	@Override
	public boolean run(IPacket pack) {
		PackA0 packA0 = null;
		try {
			System.out.println("A0 received");
			packA0 = PackA0.parseFrom(pack.getData());
			LobbyMember peer = new LobbyMember(packA0.getName());
			peer.heroID = 0;
			peer.peerID = pack.GetSenderID();
			lobby.lobbyMembers.add(peer);
			
			PackA1.Builder builderA1 = PackA1.newBuilder();
			builderA1.setName(lobby.user.name);
			OPacket oPack = new OPacket("A1", builderA1.build().toByteString());
			oPack.setReliable(false);
			oPack.addSendToID(OPacket.BROADCAST_ID);
			lobby.client.send(oPack);
		} catch (InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}

class PackA1Handler implements PacketHandler {
	private Lobby lobby;
	
	public PackA1Handler(Lobby lobby) 
	{
		this.lobby = lobby;
	}

	@Override
	public boolean run(IPacket pack) {
		PackA1 packA1 = null;
		try {
			packA1 = PackA1.parseFrom(pack.getData());
			LobbyMember peer = new LobbyMember(packA1.getName());
			peer.heroID = packA1.getHeroID();
			peer.peerID = pack.GetSenderID();
			lobby.lobbyMembers.add(peer);
			System.out.println("A1 received");
		} catch (InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}

class LobbyMember {
	LobbyMember(String name) {
		this.name = name;
		this.heroID = 0;
		this.peerID = 0;
	}
	public String name;
	int heroID;
	public int peerID;
	
	public void drawBackground(ShapeRenderer renderer, float x, float y, float w, float h) {
		renderer.rect(x, y, w, h);
	}
	
	public void drawText(BitmapFont font, SpriteBatch batch, float x, float y) {
		font.draw(batch, name, x, y);
	}
}

public class Lobby implements PacketHandlerOwner
{
	private static final float SCREEN_W = 640;
	private static final float SCREEN_H = 480;
	private static final float TITLE_OFF = 60;
	private static final float MEMBER_BOX_OFF = 10;
	private static final float MEMBER_BOX_H = 50;
	private ShapeRenderer renderer;
	private boolean displayLobby;
	public Client client;
	public LobbyMember user;
	public LinkedList<LobbyMember> lobbyMembers;
	private BitmapFont font;
	private SpriteBatch batch;

	public Lobby(String address, int port, LobbyMember user)
	{
		displayLobby = false;
		this.user = user;
		lobbyMembers = new LinkedList<LobbyMember>();
		
		initInternet(address, port);
		initGraphics();
	}
	
	public void draw() {
		if (!displayLobby) {
			drawLoad();
		}
		else
		{
			drawLobby();
		}
	}

	@Override
	public void createPacketHandlers() {
		client.getPacketManager().addPacketHandler("A0", new PackA0Handler(this));
		client.getPacketManager().addPacketHandler("A1", new PackA1Handler(this));
	}

	@Override
	public void removePacketHandlers() {
		client.getPacketManager().removePacketHandler("A0");
		client.getPacketManager().removePacketHandler("A1");
	}
	
	public void setDisplayLobby(boolean mode) {
		this.displayLobby = mode;
	}
	
	private void initInternet(String address, int port) {
		client = new Client();
		createPacketHandlers();
		client.addConnectionOpenHandler(new LobbyConnectionOpenHandler(this));
		client.addConnectionCloseHandler(new LobbyConnectionCloseHandler(client));
		client.run(address, port);
	}
	
	private void initGraphics() {
		renderer = new ShapeRenderer();
		font = new BitmapFont();
		batch = new SpriteBatch();
	}
	
	private void drawLoad() {
		font.getData().setScale(3);
		font.setColor(0, 0, 0, 1);
		batch.begin();
		font.draw(batch, "Connecting...", SCREEN_W/3, SCREEN_H/2);
		batch.end();
	}
	
	private void drawLobby() {
		renderer.begin(ShapeType.Filled);
		
		drawLobbyBackground();
		drawMemberBoxBackground();
		
		renderer.end();
		batch.begin();
		drawMemberBoxText();
		batch.end();
	}
	
	private void drawLobbyBackground() {
		renderer.setColor(0, 0, 0, 1);
		renderer.rect(0, 0, SCREEN_W, SCREEN_H);
		renderer.setColor(.5f, .5f, .5f, .3f);
		renderer.rect(MEMBER_BOX_OFF/2, MEMBER_BOX_OFF/2, SCREEN_W/2 - MEMBER_BOX_OFF, 
				SCREEN_H - MEMBER_BOX_OFF - TITLE_OFF);
		renderer.rect(SCREEN_W/2 + MEMBER_BOX_OFF/2, MEMBER_BOX_OFF/2, 
				SCREEN_W/2 - MEMBER_BOX_OFF, 
				SCREEN_H - MEMBER_BOX_OFF - TITLE_OFF);
		renderer.setColor(1, 1, 1, 1);
		renderer.rect(MEMBER_BOX_OFF, MEMBER_BOX_OFF, SCREEN_W/2 - MEMBER_BOX_OFF * 2, 
				SCREEN_H - MEMBER_BOX_OFF * 2 - TITLE_OFF);
		renderer.rect(SCREEN_W/2 + MEMBER_BOX_OFF, MEMBER_BOX_OFF, 
				SCREEN_W/2 - MEMBER_BOX_OFF * 2, 
				SCREEN_H - MEMBER_BOX_OFF * 2 - TITLE_OFF);
	}
	
	private void drawMemberBoxBackground() {
		float y = SCREEN_H - MEMBER_BOX_OFF * 2 - TITLE_OFF - MEMBER_BOX_H;
		Iterator<LobbyMember> iter = lobbyMembers.iterator();
		renderer.setColor(.8f, .8f, .8f, 1);
		while (iter.hasNext()) {
			iter.next().drawBackground(renderer, SCREEN_W/2 + MEMBER_BOX_OFF * 2, 
					y, SCREEN_W/2 - MEMBER_BOX_OFF * 4, MEMBER_BOX_H);
			y -= MEMBER_BOX_H + MEMBER_BOX_OFF;
		}
	}
	
	private void drawMemberBoxText() {
		font.getData().setScale(1);
		font.setColor(0, 0, 0, 1);
		float y = SCREEN_H - MEMBER_BOX_OFF * 2 - TITLE_OFF - MEMBER_BOX_H;
		Iterator<LobbyMember> iter = lobbyMembers.iterator();
		while (iter.hasNext()) {
			iter.next().drawText(font, batch, SCREEN_W/2 + MEMBER_BOX_OFF * 4, y + MEMBER_BOX_H/2 + 10);
			y -= MEMBER_BOX_H + MEMBER_BOX_OFF;
		}
	}
}
