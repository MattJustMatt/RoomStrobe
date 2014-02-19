package client.networking;

import client.ClientConfig;
import client.ClientStarter;

import java.awt.*;

public class MulticastColorClient extends MulticastClient {
	@Override
	public void receivedPacket(byte[] packet) {
		String receivedPacket = new String(packet).trim();
		System.out.println("RCVD: " + receivedPacket);
		interperateColorPacket(receivedPacket);
	}

	public void interperateColorPacket(String packet) {
		String[] parts = packet.split("-");

		int intendedChannel = Integer.parseInt(parts[0]);
		if (intendedChannel == 0 || intendedChannel == ClientConfig.channel) {
			int r = Integer.parseInt(parts[1]);
			int g = Integer.parseInt(parts[2]);
			int b = Integer.parseInt(parts[3]);

			ClientStarter.getGraphicsWindow().changeColor(new Color(r, g, b));
		}
	}
}
