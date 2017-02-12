package com.mygdx.game;

import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.google.protobuf.InvalidProtocolBufferException;

import gdxpacks.GdxPacks.PackA0;
import gdxpacks.GdxPacks.PackA1;
import gdxpacks.GdxPacks.PackA2;
import gdxpacks.GdxPacks.PackZ9;
import protoclient.Client;
import protoclient.ConnectionEventHandler;
import protoclient.IPacket;
import protoclient.OPacket;
import protoclient.PacketHandler;
import protoclient.PacketHandlerOwner;


class LobbyConnectionOpenHandler implements ConnectionEventHandler {
	private Lobby lobby;
	
	public LobbyConnectionOpenHandler(Lobby lobby) {
		this.lobby = lobby;
	}
	
	@Override
	public void run(String err) {
		
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
			lobby.setNumPeersRequired(lobby.getNumPeersRequired() - 1);
			
			PackA1.Builder builderA1 = PackA1.newBuilder();
			builderA1.setName(lobby.user.name);
			builderA1.setHeroID(0);
			OPacket oPack = new OPacket("A1", builderA1.build().toByteString());
			oPack.addSendToID(pack.GetSenderID());
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
			lobby.setNumPeersRequired(lobby.getNumPeersRequired() - 1);
			System.out.println("A1 received");
		} catch (InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}

class PackA2Handler implements PacketHandler
{
	private Lobby lobby;
	
	PackA2Handler(Lobby lobby)
	{
		this.lobby = lobby;
	}
	@Override
	public boolean run(IPacket pack) {
		PackA2 packA2 = null;
		try {
			packA2 = PackA2.parseFrom(pack.getData());
			lobby.setNumPeersRequired(packA2.getNumClients());
			PackA0.Builder builderA0 = PackA0.newBuilder();
			builderA0.setName(lobby.user.name);
			OPacket oPack = new OPacket("A0", builderA0.build().toByteString());
			oPack.addSendToID(OPacket.BROADCAST_ID);
			lobby.client.send(oPack);
			System.out.println("A2 received");
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
		return true;
	}
}

class PackZ9Handler implements PacketHandler {
	private Lobby lobby;
	
	public PackZ9Handler(Lobby lobby) {
		this.lobby = lobby;
	}

	@Override
	public boolean run(IPacket pack) {
		PackZ9 packZ9;
		try {
			packZ9 = PackZ9.parseFrom(pack.getData());
			lobby.removeLobbyMember(packZ9.getId());
			lobby.setNumPeersRequired(lobby.getNumPeersRequired() - 1);
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
	private int numPeersRequired;
	private ShapeRenderer renderer;
	private boolean displayLobby;
	public Client client;
	public LobbyMember user;
	public LinkedList<LobbyMember> lobbyMembers;
	private BitmapFont font;
	private SpriteBatch batch;
	private boolean failed;
	private boolean finished;
	private Stage stage;

	public Lobby(String address, int port, LobbyMember user)
	{
		failed = false;
		finished = false;
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
	
	public boolean hasFailed() {
		return failed || !client.isRunning();
	}
	
	public boolean hasFinished() {
		return finished;
	}

	@Override
	public void createPacketHandlers() {
		client.getPacketManager().addPacketHandler("A0", new PackA0Handler(this));
		client.getPacketManager().addPacketHandler("A1", new PackA1Handler(this));
		client.getPacketManager().addPacketHandler("A2", new PackA2Handler(this));
		client.getPacketManager().addPacketHandler("Z9", new PackZ9Handler(this));
	}

	@Override
	public void removePacketHandlers() {
		client.getPacketManager().removePacketHandler("A0");
		client.getPacketManager().removePacketHandler("A1");
		client.getPacketManager().removePacketHandler("A2");
		client.getPacketManager().removePacketHandler("Z9");
	}
	
	public void setDisplayLobby(boolean mode) {
		this.displayLobby = mode;
	}
	
	void removeLobbyMember(int id) {
		Iterator <LobbyMember> iter = lobbyMembers.iterator();
		while (iter.hasNext()) {
			if (iter.next().peerID == id) {
				iter.remove();
				break;
			}
		}
	}
	
	int getNumPeersRequired() {
		return numPeersRequired;
	}
	
	void setNumPeersRequired(int size) {
		this.numPeersRequired = size;
		if (numPeersRequired <= 0) {
			displayLobby = true;
		}
	}
	
	void removeHandlers() {
		removePacketHandlers();
		client.addConnectionCloseHandler(null);
		client.addConnectionOpenHandler(null);
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
		initStage();
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
		
		stage.draw();
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
	
	public void initStage() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		Skin skin = new Skin();
		Pixmap pixmap = new Pixmap(100, 100, Format.RGBA8888);
		pixmap.setColor(Color.GREEN);
		pixmap.fill();
 
		skin.add("white", new Texture(pixmap));
		
		TextButtonStyle readyButtonStyle = new TextButtonStyle();
		readyButtonStyle.up = skin.newDrawable("white", Color.GREEN);
		readyButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		readyButtonStyle.checked = skin.newDrawable("white", Color.DARK_GRAY);
		readyButtonStyle.over = skin.newDrawable("white", Color.CYAN);
		readyButtonStyle.font = font;
		skin.add("ready", readyButtonStyle);
		
		TextButton readyButton = new TextButton("Ready", skin, "ready");
		readyButton.setPosition(540, 430);
		readyButton.setSize(100, 50);
		
		stage.addActor(readyButton);
		
		TextButtonStyle exitButtonStyle = new TextButtonStyle();
		exitButtonStyle.up = skin.newDrawable("white", Color.RED);
		exitButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		exitButtonStyle.checked = skin.newDrawable("white", Color.DARK_GRAY);
		exitButtonStyle.over = skin.newDrawable("white", Color.FIREBRICK);
 
		exitButtonStyle.font = font;
		skin.add("exit", exitButtonStyle);
		
		TextButton exitButton = new TextButton("Exit", skin, "exit");
		exitButton.setPosition(0, 430);
		exitButton.setSize(100, 50);
		exitButton.addListener( new ClickListener() {              
		    @Override
		    public void clicked(InputEvent event, float x, float y) {
		    	System.out.println("CLICKED");
		        client.stop();
		    };
		});
		readyButton.addListener( new ClickListener() {              
		    @Override
		    public void clicked(InputEvent event, float x, float y) {
		    	finished = true;
		    };
		});
		
		stage.addActor(exitButton);
	}
}
