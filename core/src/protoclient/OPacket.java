package protoclient;

import java.util.List;

import com.google.protobuf.ByteString;

import protopacks.PackFW;
import protopacks.PackFW.PackHeaderIn;

public class OPacket {
	public static int BROADCAST_ID = 65535;
	private PackHeaderIn.Builder builder;
	private ByteString data;
	private boolean reliable;

	public OPacket(String pKey, ByteString data) {
		this.builder = PackHeaderIn.newBuilder();
		builder.setLocKey(pKey);
		this.data = data;
		builder.setServerRead(false);
		reliable = true;
	}
	
	public void setPKey(String pKey) {
		builder.setLocKey(pKey);
	}
	
	public void setData(ByteString data) {
		this.data = data;
	}
	
	public String getPKey() {
		return builder.getLocKey();
	}
	
	public ByteString getData() {
		return data;
	}
	
	public void addSendToID(int id) {
		builder.addSendToIDs(id);
	}
	
	public List<Integer> getSendToIDs() {
		return builder.getSendToIDsList();
	}
	
	public void setServerRead(boolean mode) {
		builder.setServerRead(mode);
	}
	
	public boolean getServerRead() {
		return builder.getServerRead();
	}
	
	public void setReliable(boolean mode) {
		this.reliable = mode;
	}
	
	public boolean getReliable() {
		return reliable;
	}
	
	public ByteString getHeaderData() {
		builder.setDataSize(data.size());
		return builder.build().toByteString();
	}
}
