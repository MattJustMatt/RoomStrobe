package client;

import client.graphics.ClientGraphics;
import client.networking.MulticastClient;
import client.networking.MulticastColorClient;

import javax.swing.*;

public class ClientStarter {
	private static ClientGraphics clientGraphics;
	private static MulticastClient multicastClient;

	public static void start(int channel) {
		//Setup configuration
		ClientConfig.channel = channel;

		//Setup Graphics
		clientGraphics = new ClientGraphics();
		clientGraphics.setSize(1920, 1080);
		clientGraphics.setUndecorated(true);
		clientGraphics.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		clientGraphics.setVisible(true);
		clientGraphics.setAlwaysOnTop(true);

		//Add shutdown hook to shutdown networking
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.println("Shutting down.");
				multicastClient.shutdown();
			}
		});

		//Setup networking
		multicastClient = new MulticastColorClient();
		multicastClient.start();
		try {
			System.in.read();
		} catch (Exception e) {

		}

		//After completion shutdown
		multicastClient.shutdown();
	}

	public static ClientGraphics getGraphicsWindow() {
		return clientGraphics;
	}
}