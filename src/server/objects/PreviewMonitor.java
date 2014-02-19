package server.objects;

import server.logic.UniverseHandler;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PreviewMonitor {
	private PreviewListener previewListener = new PreviewListener();

	private Channel channel;
	private JButton graphic;
	private boolean selected;
	private static int incrementX = 0;
	private static int incrementY = 0;

	public PreviewMonitor(Channel channel) {
		this.channel = channel;

		graphic = new JButton();
		graphic.setForeground(Color.WHITE);
		graphic.setBackground(Color.BLACK);
		graphic.setFont(new Font(graphic.getFont().getName(), Font.BOLD, 26));
		graphic.setText("" + channel.getNumber());
		graphic.addActionListener(previewListener);
		graphic.setBorder(new LineBorder(Color.WHITE, 1));
		graphic.setBounds(incrementX*90+5, incrementY*90+5, 75, 75);

		if (incrementX < 8) {
			incrementX++;
		} else {
			incrementX = 0;
			incrementY++;
		}

		selected = false;
	}

	public void setSelected(boolean select) {
		this.selected = select;

		if (select) {
			this.graphic.setBorder(new LineBorder(Color.GREEN, 1));
			UniverseHandler.getSelectedChannels().add(this.channel);
		} else {
			this.graphic.setBorder(new LineBorder(Color.WHITE, 1));
			UniverseHandler.getSelectedChannels().remove(this.channel);
		}
	}

	public void update() {
		this.graphic.setBackground(this.channel.getColor());
	}

	public boolean isSelected() {
		return this.selected;
	}

	public JButton getGraphic() {
		return this.graphic;
	}

	public Channel getChannel() {
		return this.channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	private class PreviewListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setSelected(!isSelected());
		}
	}

	public String toString() {
		return "PreviewMonitor: Channel - " + this.channel + " color: " + this.getChannel().getColor();
	}
}
