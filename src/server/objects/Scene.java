package server.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class Scene implements Serializable {
	private int number;
	private double time;
	private ArrayList<Channel> channelState;

	public Scene(int number, double time, ArrayList<Channel> channels) {
		this.number = number;
		this.time = time;

		//Freeze a snapshot of the channel state taken in.
		for (Channel channel : channels) {
            this.channelState.add(new Channel(channel));
        }
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

	public ArrayList<Channel> getChannelState() {
		return this.channelState;
	}

	public void setChannelState(ArrayList<Channel> channelState) {
		this.channelState = channelState;
	}

	public String toString() {
		return "Scene " + this.number + ": " + time + "s";
	}
}
