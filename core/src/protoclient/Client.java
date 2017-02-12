package protoclient;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Client {
	public static int HEADER_PACK_SIZE_BYTES = 2;
	private TCPConnection tcpConnection;
	private UDPConnection udpConnection;
	private InetAddress serverAddress;
	private int port;
	private ConnectionEventHandler conFailHandler;
	private ConnectionEventHandler conErrorHandler;
	private ConnectionEventHandler conCloseHandler;
	private ConnectionEventHandler conOpenHandler;
	private HeaderManager headerManager;
	private PacketManager packManager;
	
	public Client() {
		this.tcpConnection = new TCPConnection(this);
		this.udpConnection = new UDPConnection();
		this.headerManager = new HeaderManager();
		this.packManager = new PacketManager();
	}
	
	HeaderManager getHeaderManager() {
		return headerManager;
	}
	
	public PacketManager getPacketManager() {
		return packManager;
	}
	
	void addConnectionFailHandler(ConnectionEventHandler handler) {
		this.conFailHandler = handler;
	}
	
	ConnectionEventHandler getConnectionFailHandler() {
		return conFailHandler;
	}
	
	void addConnectionErrorHandler(ConnectionEventHandler handler) {
		this.conErrorHandler = handler;
	}
	
	ConnectionEventHandler getConnectionErrorHandler() {
		return conErrorHandler;
	}
	
	void addConnectionCloseHandler(ConnectionEventHandler handler) {
		this.conCloseHandler = handler;
	}
	
	ConnectionEventHandler getConnectionCloseHandler() {
		return conCloseHandler;
	}
	
	void addConnectionOpenHandler(ConnectionEventHandler handler) {
		this.conOpenHandler = handler;
	}
	
	ConnectionEventHandler getConnectionOpenHandler() {
		return conOpenHandler;
	}
	
	InetAddress getServerAddress() {
		return serverAddress;
	}
	
	int getPort() {
		return port;
	}
	
	boolean createManagers() {
		
		return true;
	}
	
	boolean run(String host, int port) {
		try {
			this.serverAddress = InetAddress.getByName(host);
			this.port = port;
		} catch (UnknownHostException e) {
			//WOULD LIKE TO DISPLAY
			System.err.println("Could not resolve the host " + host);
			e.printStackTrace();
		}
		return true;
	}
	
	void stop() {
		
	}
}
