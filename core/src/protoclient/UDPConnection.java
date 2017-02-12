package protoclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;

import com.google.protobuf.ByteString;

public class UDPConnection implements Runnable
{
	public static final int MAX_DATA_SIZE = 1024;
	private int localPort;
	private Client owner;
	private DatagramSocket socket;
	public UDPConnection(Client owner) {
		this.owner = owner;
		this.socket = null;
	}
	
	void setLocalPort(int port) {
		this.localPort = port;
	}
	
	@Override
	public void run() {
		try {
			socket = new DatagramSocket(localPort);
			socket.setReuseAddress(true);
		} catch (IOException e) {
			ConnectionEventHandler conFailHandler = owner.getConnectionFailHandler();
			if (conFailHandler != null) {
				conFailHandler.run(e.getMessage());
			}
			System.err.println("UDP: " + e.getMessage());
			return;
		}
		while (true) {
			byte[] data = new byte[MAX_DATA_SIZE];
			try {
				DatagramPacket packet = new DatagramPacket(data, MAX_DATA_SIZE);
				socket.receive(packet);
				if (packet.getAddress() != owner.getServerAddress())
				{
					continue;
				}
				int headerSize = owner.getHeaderManager().parseHeaderSize(data);
				byte[] headerData = new byte[headerSize];
				for (int i = 0; i < headerSize; i++) {
					headerData[i] = data[i + Client.HEADER_PACK_SIZE_BYTES];
				}
				IPacket iPack = owner.getHeaderManager().parseHeader(headerData);
				byte[] mainData = new byte[iPack.getDataSize()];
				for (int i = 0; i < iPack.getDataSize(); i++) {
					mainData[i] = data[i + Client.HEADER_PACK_SIZE_BYTES + headerSize];
				}
				iPack.setData(ByteString.copyFrom(mainData));
				if (!owner.getPacketManager().callHandler(iPack)){
					System.err.println("Unrecognized key: " + iPack.getPKey());
					socket.close();
					return;
				}
			} catch (IOException e) {
				ConnectionEventHandler conErrorHandler = owner.getConnectionErrorHandler();
				System.err.println("UDP Recv error: " + e.getMessage());
				if (conErrorHandler != null) {
					conErrorHandler.run("UDP Recv error: " + e.getMessage());
				}
				socket.close();
				return;
			}
		}
	}
	
	public boolean send(OPacket oPack) {
		System.out.println("Sent called");
		try {
			byte[] data = owner.getHeaderManager().serialize(oPack).toByteArray();
			DatagramPacket packet = new DatagramPacket(data, data.length, owner.getServerAddress(), owner.getPort());
			socket.send(packet);
		} catch (IOException e) {
			ConnectionEventHandler conErrorHandler = owner.getConnectionErrorHandler();
			if (conErrorHandler != null) {
				conErrorHandler.run("Send err: " + e.getMessage());
			}
		}
		return true;
	}
	
	public void stop() {
		if (socket != null) {
			socket.close();
		}
	}
}
