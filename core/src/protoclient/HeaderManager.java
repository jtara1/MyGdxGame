package protoclient;

import java.nio.ByteOrder;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import ProtobufPackets.PackFW.PackHeaderOut;

public class HeaderManager {
	boolean isBigEndian;
	
	public HeaderManager() {
		isBigEndian = ByteOrder.nativeOrder().equals(ByteOrder.BIG_ENDIAN);
	}
	
	public int parseHeaderSize(byte[] data) {
		int size = 0;
		if (isBigEndian) {
			for (int i = 0; i < Client.HEADER_PACK_SIZE_BYTES; i++) {
				size |= (data[i] & 0xff) << (8 * i);
			}
		} else {
			for (int i = 0; i < Client.HEADER_PACK_SIZE_BYTES; i++) {
				size |= (data[Client.HEADER_PACK_SIZE_BYTES - i - 1] & 0xff) << (8 * i);
			}
		}
		return size;
	}
	
	public byte[] serializeHeaderSize(int size) {
		byte[] data = new byte[Client.HEADER_PACK_SIZE_BYTES];
		if (isBigEndian) {
			for (int i = 0; i < Client.HEADER_PACK_SIZE_BYTES; i++) {
				data[i] = (byte) ((size << (i * 8)) & 0xff);
			}
		} else {
			for (int i = 0; i < Client.HEADER_PACK_SIZE_BYTES; i++) {
				data[Client.HEADER_PACK_SIZE_BYTES - i - 1] = (byte) ((size << (i * 8)) & 0xff);
			}
		}
		return data;
	}
	
	public IPacket parseHeader(byte[] data) {
		PackHeaderOut packIn;
		try {
			packIn = PackHeaderOut.parseFrom(data);
			IPacket iPack = new IPacket(packIn);
			return iPack;
		} catch (InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public ByteString serialize(OPacket oPack) {
		ByteString headerData = oPack.getHeaderData();
		ByteString data = ByteString.copyFrom(serializeHeaderSize(headerData.size()));
		data = data.concat(headerData);
		data = data.concat(oPack.getData());
		System.out.println(data.size());
		return data;
	}
	
	
}
