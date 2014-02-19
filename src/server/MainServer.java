package server;

import server.graphics.ServerGraphics;
import server.networking.MulticastServer;
import server.objects.Console;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class MainServer {
	private static MulticastServer multicastServer;
	private static Console console;
	private static JFrame graphics;

	public static void startServer() {
		multicastServer = new MulticastServer();
		console = new Console();

		setupGraphics();
	}

	private static void setupGraphics() {
		graphics = new JFrame("ServerGraphics");
		graphics.setContentPane(new ServerGraphics().getMainServerPanel());
		graphics.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		graphics.setVisible(true);
		graphics.setSize(1400, 805);
		graphics.setTitle("RoomStrobe - Matthew Salsamendi 2014");
		graphics.setResizable(false);
		try {
			graphics.setIconImage(ImageIO.read(new File("res/logo.png")));
		} catch (IOException e) {
			System.out.println("Error reading logo for server graphics.");
		}
	}

	public static MulticastServer getMulticastServer() {
		return multicastServer;
	}

	public static Console getConsole() {
		return console;
	}

	public static JFrame getGraphics() {
		return graphics;
	}
}