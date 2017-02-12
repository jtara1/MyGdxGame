package protoclient;

import com.google.protobuf.ByteString;

import protopacks.PackFW.PackHeaderOut;

public class IPacket {
	private PackHeaderOut packOut;
	private ByteString data;
	public IPacket(PackHeaderOut packOut) {
		this.packOut = packOut;
	}
	
	public void setData(ByteString data) {
		this.data = data;
	}
	
	public final String getPKey() {
		return packOut.getLocKey();
	}
	
	public final int GetSenderID() {
		return packOut.getSentFromID();
	}
	
	public final ByteString getData() {
		return data;
	}
	
	public final int getDataSize() {
		return packOut.getDataSize();
	}
}
