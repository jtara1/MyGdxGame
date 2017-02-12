package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.google.protobuf.InvalidProtocolBufferException;
import com.mygdx.game.MyGdxGame.GAME_STATE;

import gdxpacks.GdxPacks.PackA0;
import gdxpacks.GdxPacks.PackA1;
import gdxpacks.GdxPacks.PackA2;
import gdxpacks.GdxPacks.PackB0;
import gdxpacks.GdxPacks.PackB1;
import gdxpacks.GdxPacks.PackZ9;
import protoclient.Client;
import protoclient.IPacket;
import protoclient.OPacket;
import protoclient.PacketHandler;
import protoclient.PacketHandlerOwner;

class PackA0WorldHandler implements PacketHandler {
	private World world;
	
	public PackA0WorldHandler(World world) 
	{
		this.world = world;
	}

	@Override
	public boolean run(IPacket pack) {
		PackA0 packA0 = null;
		PackA1.Builder builderA1 = PackA1.newBuilder();
		builderA1.setName(world.userInfo.name);
		builderA1.setHeroID(world.userInfo.heroID);
		OPacket oPack = new OPacket("A1", builderA1.build().toByteString());
		oPack.addSendToID(OPacket.BROADCAST_ID);
		world.client.send(oPack);
		return true;
	}
}

class PackB0WorldHandler implements PacketHandler {
	private World world;
	
	PackB0WorldHandler(World world) {
		this.world = world;
	}

	@Override
	public boolean run(IPacket pack) {
		PackB0 packB0;
		try {
			packB0 = PackB0.parseFrom(pack.getData());
			world.peerControllers.add(new PeerController(pack.GetSenderID(), packB0.getName(), packB0.getHeroID()));
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
		
		return true;
	}
}

class PackB1WorldHandler implements PacketHandler {
	private World world;
	
	PackB1WorldHandler(World world) {
		this.world = world;
	}

	@Override
	public boolean run(IPacket pack) {
		PackB1 packB1;
		try {
			packB1 = PackB1.parseFrom(pack.getData());
			for (int i = 0; i < world.peerControllers.size(); i++) {
				if (world.peerControllers.get(i).getPeerID() == pack.GetSenderID())
				{
					world.peerControllers.get(i).update(packB1);
				}
			}
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
}

class PackZ9WorldHandler implements PacketHandler {
	private World world;
	
	PackZ9WorldHandler(World world) {
		this.world = world;
	}

	@Override
	public boolean run(IPacket pack) {
		PackZ9 packZ9;
		try {
			packZ9 = PackZ9.parseFrom(pack.getData());
			for (int i = 0; i < world.peerControllers.size(); i++) {
				if (world.peerControllers.get(i).getPeerID() == packZ9.getId()) {
					world.peerControllers.remove(i);
				}
			}
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
		return true;
	}
}

public class World implements PacketHandlerOwner, InputProcessor {
	public static final int DIRECTION_UP = 0;
	public static final int DIRECTION_RIGHT = 1;
	public static final int DIRECTION_DOWN = 2;
	public static final int DIRECTION_LEFT = 3;
	public static final int DIRECTION_NONE = -1;
	
	public Texture background;
	
	public Rectangle boundaries;
	
	public SpriteBatch batch;
	
	public Player player;
	
	public ArrayList<Rectangle> noWalkZones;
	
	public ArrayList<Monster> monsters;
	
	public ArrayList<PeerController> peerControllers;

	public InputHandler input;
	
	public OrthographicCamera camera;
	
	public Client client;
	
	public UserInfo userInfo;
	
	public World(Client client, UserInfo userInfo, ArrayList<UserInfo> peers, String fileName) {
		this.userInfo = userInfo;
		this.client = client;
		peerControllers = new ArrayList<PeerController>();
		
		for (int i = 0; i < peers.size(); i++) {
			peerControllers.add(new PeerController(peers.get(i).peerID, peers.get(i).name, peers.get(i).heroID));
		}
		
		createPacketHandlers();
		
		PackB0.Builder packB0Builder = PackB0.newBuilder();
		packB0Builder.setName(userInfo.name);
		packB0Builder.setHeroID(userInfo.heroID);
		OPacket oPack = new OPacket("B0", packB0Builder.build().toByteString());
		oPack.addSendToID(65535);
		client.send(oPack);
		
		// image of World background loaded as a Texture
		background = new Texture(fileName);
		
		// used to draw each Texture
		batch = new SpriteBatch();
		
		// back default boundaries the size of the background image
		boundaries = new Rectangle(0, 0, background.getWidth(), background.getHeight());
		
		player = new Player();
		
		noWalkZones = new ArrayList<Rectangle>();
		monsters = new ArrayList<Monster>();
		
		createNoWalkZones();
		setMonsters();

		input = new InputHandler();
		
		float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        
        //Creates a new camera, sets its position focused on the player, and zooms out
        camera = new OrthographicCamera(30, 30 * (h / w));
        camera.position.set(player.position.x,player.position.y,0);
        camera.zoom += 20;
        camera.update();
	}
	/*
	public World(UserInfo userInfo, ArrayList<UserInfo> peers, String fileName, float width, float height) {
		this(userInfo, peers, fileName);
		boundaries = new Rectangle(0, 0, width, height);
	}
	*/
	public void setMonsters() {
		Vector2 pos1 = new Vector2(200,200);
		Monster mons1 = new Monster(pos1);
		monsters.add(mons1);
		Vector2 pos2 = new Vector2(150,500);
		Monster mons2 = new Monster(pos2);
		monsters.add(mons2);
		Vector2 pos3 = new Vector2(800,400);
		Monster mons3 = new Monster(pos3);
		monsters.add(mons3);
		Vector2 pos4 = new Vector2(500,100);
		Monster mons4 = new Monster(pos4);
		monsters.add(mons4);
		Vector2 pos5 = new Vector2(600,600);
		Monster mons5 = new Monster(pos5);
		monsters.add(mons5);
	}
	public void drawMonsters() {
		for(int i = 0; i < monsters.size(); i++)
			batch.draw(monsters.get(i).curSprite, monsters.get(i).getLocation().x, monsters.get(i).getLocation().y);
	}
	public void checkMonsterCollision(){
		for(int i = 0; i < monsters.size(); i++){
			if(Math.abs(monsters.get(i).getLocation().x - player.position.x) <= 15 &&
					Math.abs(monsters.get(i).getLocation().y - player.position.y) <= 15)
				MyGdxGame.GameState = GAME_STATE.COMBAT;
		}
	}
	public void createNoWalkZones() {
		Rectangle zone1 = new Rectangle(335, 149, 130, 140);
		noWalkZones.add(zone1);
	}
	
	public void draw() {
	    batch.setProjectionMatrix(camera.combined);
	        
		batch.begin();
		batch.draw(background, 0, 0);
		player.draw(batch);
		
		int offset = 10;
		int[] sidesBlocked = playerCollidedWithNoWalkZone();
		// debug
//		for (int i = 0; i < sidesBlocked.length; i++) {
//			System.out.print(sidesBlocked[i] + " ");
//		} System.out.println();
		
		if(Gdx.input.isKeyPressed(Keys.DPAD_LEFT) && !player.blockadeAhead(DIRECTION_LEFT, noWalkZones.get(0))) {
			if (sidesBlocked[3] == DIRECTION_NONE) {
//				player.move(-player.speed * Gdx.graphics.getDeltaTime(), 0);
				player.move(DIRECTION_LEFT);
				camera.translate(-Gdx.graphics.getDeltaTime() * player.speed, 0, 0);
			} else {
				player.position.x += Gdx.graphics.getDeltaTime() * player.speed * 2;
				camera.translate(Gdx.graphics.getDeltaTime() * player.speed * 2, 0, 0);
			}
		}
		if(Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)) {
			if (sidesBlocked[1] == DIRECTION_NONE) {
//				player.move(player.speed * Gdx.graphics.getDeltaTime(), 0);
				player.move(DIRECTION_RIGHT);
				camera.translate(Gdx.graphics.getDeltaTime() * player.speed, 0, 0);
			} else {
				player.position.x -= Gdx.graphics.getDeltaTime() * player.speed * 2;
				camera.translate(-Gdx.graphics.getDeltaTime() * player.speed * 2, 0, 0);
			}
		}
		if(Gdx.input.isKeyPressed(Keys.DPAD_UP)) {
			if (sidesBlocked[0] == DIRECTION_NONE) {
				player.move(DIRECTION_UP);
//				player.move(0, player.speed * Gdx.graphics.getDeltaTime());
				camera.translate(0, Gdx.graphics.getDeltaTime() * player.speed, 0);
			} else {
				player.position.y -= Gdx.graphics.getDeltaTime() * player.speed * 2;
				camera.translate(0, -Gdx.graphics.getDeltaTime() * player.speed * 2, 0);
			}
		}
		if(Gdx.input.isKeyPressed(Keys.DPAD_DOWN)) {
			if (sidesBlocked[2] == DIRECTION_NONE) {
//				player.move(0, -player.speed * Gdx.graphics.getDeltaTime());
				player.move(DIRECTION_DOWN);
				camera.translate(0, -Gdx.graphics.getDeltaTime() * player.speed, 0);
			} else {
				player.position.y += Gdx.graphics.getDeltaTime() * player.speed * 2;
				camera.translate(0, Gdx.graphics.getDeltaTime() * player.speed * 2, 0);
			}
		}
		camera.position.set(player.position, 0);
		camera.update();
			   
//		System.out.println("Player position: " + player.position);
//		playerCollidedWithNoWalkZone();
		
		drawMonsters();
		checkMonsterCollision();
		for (int i = 0; i < peerControllers.size(); i++) {
			peerControllers.get(i).draw(batch);
		}
//		input.move(player, camera);
		batch.end();
		if (peerControllers.size() > 0) {
			PackB1.Builder packB1Builder = PackB1.newBuilder();
			packB1Builder.setX(player.position.x);
			packB1Builder.setY(player.position.y);
			OPacket oPack = new OPacket("B1", packB1Builder.build().toByteString());
			oPack.setReliable(false);
			for (int i = 0; i < peerControllers.size(); i++) {
				oPack.addSendToID(peerControllers.get(i).getPeerID());
			}
			client.send(oPack);
		}
	}
	
	/**
	 * 
	 * @return array containing 4 ints, each representing a side blocked or not0 if blockade above player,
	 * 1 if blockade right of player
	 * 2 if blockade below player
	 * 3 if blockade left of player
	 */
	public int[] playerCollidedWithNoWalkZone() {
		int[] allSidesClear = {DIRECTION_NONE, DIRECTION_NONE, DIRECTION_NONE, DIRECTION_NONE};
		
		for (Rectangle zone : noWalkZones) {
			//System.out.println("working");
			int[] sidesBlocked = allSidesClear;
			
			if (player.blockadeAbove(zone)) {
				sidesBlocked[0] = 1;
			} 
			if (player.blockadeRight(zone)) {
				sidesBlocked[1] = 1;
			} 
			if (player.blockadeBelow(zone)) {
				sidesBlocked[2] = 1;
			} 
			if (player.blockadeLeft(zone)){
				sidesBlocked[3] = 1;
			}

			if (sidesBlocked != allSidesClear) {
				return sidesBlocked;
			}
		}
		return allSidesClear;
	}
	
	public boolean isPlayerOutOfBounds() {
		return !boundaries.contains(player.getBoundaries());
	}
	
	public void dispose() {
		background.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		switch(keycode) {
		
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void createPacketHandlers() {
		client.getPacketManager().addPacketHandler("A0", new PackA0WorldHandler(this));
		client.getPacketManager().addPacketHandler("B0", new PackB0WorldHandler(this));
		client.getPacketManager().addPacketHandler("B1", new PackB1WorldHandler(this));
		client.getPacketManager().addPacketHandler("Z9", new PackZ9WorldHandler(this));
	}
	@Override
	public void removePacketHandlers() {
		// TODO Auto-generated method stub
		
	}
}
