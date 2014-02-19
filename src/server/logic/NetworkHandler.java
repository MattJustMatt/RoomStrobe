package server.logic;

import server.MainServer;
import server.objects.PreviewMonitor;

import java.util.ArrayList;

public class NetworkHandler {
	public static void updateStatic() {
		for (PreviewMonitor previewMonitor : PreviewMonitorHandler.getPreviewMonitors()) {
			if (previewMonitor.isSelected()) {
				MainServer.getMulticastServer().sendColorToClient(previewMonitor.getChannel().getNumber(), previewMonitor.getChannel().getColor());
			}
		}
	}

	public static void updateStatic(ArrayList<PreviewMonitor> channelState) {
		for (PreviewMonitor previewMonitor : channelState) {
			if (previewMonitor.isSelected()) {
				MainServer.getMulticastServer().sendColorToClient(previewMonitor.getChannel().getNumber(), previewMonitor.getChannel().getColor());
			}
		}
	}
}
