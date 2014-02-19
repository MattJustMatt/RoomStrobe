package client.networking;

import common.networking.MulticastConfig;

import java.net.DatagramPacket;
import java.net.MulticastSocket;

public abstract class MulticastClient extends Thread {
	private static final MulticastSocket SOCKET;

	private static boolean running = true;

	static {
		try {
			SOCKET = new MulticastSocket(MulticastConfig.PORT);
			SOCKET.joinGroup(MulticastConfig.GROUP);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void run() {
		while(running) {
			try {
				Thread.sleep(10);
				byte[] buf = new byte[64];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				SOCKET.receive(packet);
				receivedPacket(packet.getData());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void shutdown() {
		running = false;
		try {
			SOCKET.leaveGroup(MulticastConfig.GROUP);
		} catch (Exception e) {
			e.printStackTrace();
		}
		SOCKET.close();
	}

	public abstract void receivedPacket(byte[] packet);
}