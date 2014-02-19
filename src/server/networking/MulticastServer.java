package server.networking;

import common.networking.MulticastConfig;

import java.awt.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class MulticastServer {
	private static boolean enabled = true;
	private static final DatagramSocket SOCKET;

	static {
		try {
			SOCKET = new DatagramSocket();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void sendPacketToClient(String inputPacket) {
		if (enabled) {
			byte[] packet = inputPacket.getBytes();

			try {
				final DatagramPacket datagramPacket = new DatagramPacket(packet, packet.length, MulticastConfig.GROUP, MulticastConfig.PORT);
				SOCKET.send(datagramPacket);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			System.out.println("SNT: " + inputPacket);
		} else {
			System.out.println("NOT SENDING PACKET " + inputPacket + " NETWORK DISABLED!");
		}
	}

	public void sendColorToClient(int channel, Color color) {
		sendPacketToClient(channel + "-" + color.getRed() + "-" + color.getGreen() + "-" + color.getBlue());
	}

	public void sendColorToAllClients(Color color) {
		sendPacketToClient("0-" + color.getRed() + "-" + color.getGreen() + "-" + color.getBlue());
	}

	public static boolean isEnabled() {
		return enabled;
	}

	public static void setEnabled(boolean newEnabled) {
		enabled = newEnabled;
	}
}
