package protoclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.google.protobuf.ByteString;

public class TCPConnection implements Runnable
{
	private Client owner;
	private Socket socket;
	private InputStream inStream;
	private OutputStream outStream;
	public TCPConnection(Client owner) {
		this.owner = owner;
		this.socket = null;
		this.inStream = null;
		this.outStream = null;
	}
	
	@Override
	public void run() {
		try {
			socket = new Socket(owner.getServerAddress(), owner.getPort());
			inStream = socket.getInputStream();
			outStream = socket.getOutputStream();
		} catch (IOException e) {
			ConnectionEventHandler conFailHandler = owner.getConnectionFailHandler();
			if (conFailHandler != null) {
				conFailHandler.run(e.getMessage());
			}
		}
		while (true) {
			byte[] data = new byte[Client.HEADER_PACK_SIZE_BYTES];
			try {
				inStream.read(data, 0, Client.HEADER_PACK_SIZE_BYTES);
				int headerSize = owner.getHeaderManager().parseHeaderSize(data);
				data = new byte[headerSize];
				inStream.read(data, 0, headerSize);
				IPacket iPack = owner.getHeaderManager().parseHeader(data);
				data = new byte[iPack.getDataSize()];
				inStream.read(data, 0, iPack.getDataSize());
				iPack.setData(ByteString.copyFrom(data));
				if (!owner.getPacketManager().callHandler(iPack)){
					System.err.println("Unrecognized key: " + iPack.getPKey());
				}
			} catch (IOException e) {
				if (!socket.isClosed() && socket.isConnected()) {
					ConnectionEventHandler conErrorHandler = owner.getConnectionErrorHandler();
					if (conErrorHandler != null) {
						conErrorHandler.run("Recv error: " + e.getMessage());
					}
				}
				else {
					if (!socket.isClosed()) {
						try {
							socket.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					ConnectionEventHandler conCloseHandler = owner.getConnectionCloseHandler();
					conCloseHandler.run(e.getMessage());
					return;
				}
			}
		}
	}
	
	public boolean send(ByteString data) {
		try {
			outStream.write(data.toByteArray());
		} catch (IOException e) {
			ConnectionEventHandler conErrorHandler = owner.getConnectionErrorHandler();
			if (conErrorHandler != null) {
				conErrorHandler.run("Send err: " + e.getMessage());
			}
		}
		return true;
	}
	
	public void stop() {
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
