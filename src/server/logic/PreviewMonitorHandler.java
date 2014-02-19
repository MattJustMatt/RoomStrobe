package server.logic;

import server.objects.Channel;
import server.objects.PreviewMonitor;

import javax.swing.*;
import java.util.ArrayList;

public class PreviewMonitorHandler {
	private static ArrayList<PreviewMonitor> previewMonitors;

	static {
		previewMonitors = new ArrayList<PreviewMonitor>();
	}

	public static void createNewMonitor() {
		String input = JOptionPane.showInputDialog(null, "Please enter a channel number for a new visualizer", "Add Visualizer", JOptionPane.PLAIN_MESSAGE);

		int channelNumber;
		try {
			channelNumber = Integer.parseInt(input);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "You must type a number to add a new channel!", "ERROR", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (channelNumber <= 0) {
			JOptionPane.showMessageDialog(null, "Channel number must be great than 0!", "ERROR", JOptionPane.ERROR_MESSAGE);
			return;
		} else {
			previewMonitors.add(new PreviewMonitor(new Channel(channelNumber)));
		}
	}

	public static ArrayList<PreviewMonitor> getPreviewMonitors() {
		return previewMonitors;
	}
}
