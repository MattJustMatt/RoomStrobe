package common;

import client.ClientStarter;
import server.MainServer;

import java.util.Scanner;

public class RoomStrobe {
	public static void main(String[] args) {
		/*ConfigurationWindow dialog = new ConfigurationWindow();
		dialog.pack();
		dialog.setVisible(true);
		System.exit(0)*/

		String clientServer = new Scanner(System.in).nextLine();
		if (clientServer.startsWith("Server")) {
			MainServer.startServer();
		} else if (clientServer.startsWith("Client")) {
			ClientStarter.start(Integer.parseInt(clientServer.split(" ")[1]));
		}
	}
}