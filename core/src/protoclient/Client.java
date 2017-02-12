package protoclient;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Client {
	public static int HEADER_PACK_SIZE_BYTES = 2;
	private boolean running;
	private TCPConnection tcpConnection;
	private UDPConnection udpConnection;
	private Thread tcpThread;
	private Thread udpThread;
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
		this.udpConnection = new UDPConnection(this);
		this.headerManager = new HeaderManager();
		this.packManager = new PacketManager();
		running = false;
	}
	
	public void send(OPacket oPack) {
		if (oPack.getReliable()) {
			tcpConnection.send(oPack);
		}
		else
		{
			udpConnection.send(oPack);
		}
	}
	
	HeaderManager getHeaderManager() {
		return headerManager;
	}
	
	public PacketManager getPacketManager() {
		return packManager;
	}
	
	public void addConnectionFailHandler(ConnectionEventHandler handler) {
		this.conFailHandler = handler;
	}
	
	ConnectionEventHandler getConnectionFailHandler() {
		return conFailHandler;
	}
	
	public void addConnectionErrorHandler(ConnectionEventHandler handler) {
		this.conErrorHandler = handler;
	}
	
	ConnectionEventHandler getConnectionErrorHandler() {
		return conErrorHandler;
	}
	
	public void addConnectionCloseHandler(ConnectionEventHandler handler) {
		this.conCloseHandler = handler;
	}
	
	ConnectionEventHandler getConnectionCloseHandler() {
		return conCloseHandler;
	}
	
	public void addConnectionOpenHandler(ConnectionEventHandler handler) {
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
	
	boolean startUDP(int port) {
		udpThread = new Thread(udpConnection);
		udpConnection.setLocalPort(port);
		udpThread.start();
		return true;
	}
	
	public boolean run(String host, int port) {
		try {
			this.serverAddress = InetAddress.getByName(host);
			this.port = port;
		} catch (UnknownHostException e) {
			//WOULD LIKE TO DISPLAY
			System.err.println("Could not resolve the host " + host);
			e.printStackTrace();
		}
		tcpThread = new Thread(tcpConnection);
		tcpThread.start();
		return true;
	}
	
	public void stop() {
		tcpConnection.stop();
		running = false;
	}
	
	public boolean isRunning() {
		return running;
	}
}
