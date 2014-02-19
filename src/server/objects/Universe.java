package server.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class Universe implements Serializable {
	private String name;
	private ArrayList<Channel> channels;

	//Creates empty array of channels
	public Universe(String name) {
		this.name = name;
		channels = new ArrayList<Channel>();
	}

	//Creates based upon existing array of channels (copy maybe?)
	public Universe(String name, ArrayList<Channel> channels) {
		this.name = name;
		this.channels.addAll(channels);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Channel> getChannels() {
		return this.channels;
	}

	public void setChannels(ArrayList<Channel> channels) {
		this.channels = channels;
	}

	public String toString() {
		return "Universe: {" + channels.toString() + "}";
	}
}
