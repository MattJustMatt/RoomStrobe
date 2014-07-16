package server.networking;

import common.networking.MulticastConfig;
import server.logic.UniverseHandler;
import server.objects.Channel;

import java.awt.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

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

    public void broadcastState() {
        String packet = "";

        for (Channel channel : UniverseHandler.getActiveUniverse().getChannels()) {
            Color color = channel.getColor();

            packet += channel.getNumber() + "-" + color.getRed() + "-" + color.getGreen() + "-" + color.getBlue() + ":";
        }

        sendPacketToClient(packet);
    }

    public void broadcastState(ArrayList<Channel> channelState) {
        String packet = "";

        for (Channel channel : channelState) {
            Color color = channel.getColor();

            packet += channel.getNumber() + "-" + color.getRed() + "-" + color.getGreen() + "-" + color.getBlue() + ":";
        }

        sendPacketToClient(packet);
    }

	public static boolean isEnabled() {
		return enabled;
	}

	public static void setEnabled(boolean newEnabled) {
		enabled = newEnabled;
	}
}
