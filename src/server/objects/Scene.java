package server.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class Scene implements Serializable {
	private int number;
	private double time;
	private ArrayList<PreviewMonitor> channelState;

	public Scene(int number, double time, ArrayList<PreviewMonitor> channelState) {
		this.number = number;
		this.time = time;

		//Freeze a snapshot of the channel state taken in.
		this.channelState = new ArrayList<PreviewMonitor>(channelState.size());
		this.channelState.addAll(channelState);
	}

	public int getNumber() {
		return number;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public ArrayList<PreviewMonitor> getChannelState() {
		return this.channelState;
	}

	public void setChannelState(ArrayList<PreviewMonitor> channelState) {
		this.channelState = channelState;
	}

	public String toString() {
		return "Scene " + this.number + ": " + time + "s";
	}
}
