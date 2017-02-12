package protoclient;

import java.util.concurrent.ConcurrentHashMap;

public class PacketManager {
	private ConcurrentHashMap<String, PacketHandler> keyHandlerMap;

	public PacketManager() {
		keyHandlerMap = new ConcurrentHashMap<String, PacketHandler>();
	}
	
	public void addPacketHandler(String key, PacketHandler packHandler) {
		keyHandlerMap.put(key, packHandler);
	}
	
	public void removePacketHandler(String key) {
		keyHandlerMap.remove(key);
	}
	
	public boolean callHandler(IPacket iPack) {
		PacketHandler handler = keyHandlerMap.get(iPack.getPKey());
		if (handler == null) {
			return false;
		}
		handler.run(iPack);
		return true;
	}
}
