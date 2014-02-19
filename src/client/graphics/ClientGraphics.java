package client.graphics;

import javax.swing.*;
import java.awt.*;

public class ClientGraphics extends JFrame {
	private JPanel contentPane;

	public ClientGraphics() {
		this.contentPane = new JPanel();
		this.add(contentPane);
		this.setContentPane(contentPane);
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		//GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

		//if (graphicsDevice.isFullScreenSupported()) {
		//	graphicsDevice.setFullScreenWindow(this);
		//}
	}

	public void changeColor(Color color) {
		this.getContentPane().setBackground(color);
	}
}
