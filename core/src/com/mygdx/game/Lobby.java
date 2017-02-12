package com.mygdx.game;

import java.util.LinkedList;

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
}

public class Lobby implements PacketHandlerOwner
{
	private boolean displayLobby;
	public Client client;
	public LobbyMember user;
	public LinkedList<LobbyMember> lobbyMembers;

	public Lobby(String address, int port, LobbyMember user)
	{
		this.user = user;
		lobbyMembers = new LinkedList<LobbyMember>();
		displayLobby = false;
		client = new Client();
		client.addConnectionOpenHandler(new LobbyConnectionOpenHandler(this));
		client.addConnectionCloseHandler(new LobbyConnectionCloseHandler(client));
		createPacketHandlers();
		client.run(address, port);
	}

	@Override
	public void createPacketHandlers() {
		client.getPacketManager().addPacketHandler("A0", new PackA0Handler(this));
		client.getPacketManager().addPacketHandler("A1", new PackA1Handler(this));
	}

	@Override
	public void removePacketHandlers() {
		
	}
	
	public void setDisplayLobby(boolean mode) {
		this.displayLobby = mode;
	}
}
