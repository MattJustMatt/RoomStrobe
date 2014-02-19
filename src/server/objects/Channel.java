package server.objects;

import server.enums.EffectType;

import java.awt.*;

public class Channel {
	private int number;
	private Color color;
	private EffectType effect;

	public Channel(int number) {
		this.number = number;
		this.color = new Color(0, 0,0 );
		this.effect = EffectType.NONE;
	}

	public Channel(Channel channel) {
		this.number = channel.getNumber();
		this.color = channel.getColor();
		this.effect = channel.getEffect();
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public EffectType getEffect() {
		return effect;
	}

	public void setEffect(EffectType effect) {
		this.effect = effect;
	}

	public String toString() {
		return "Channel: " + number + " " + color + " " + effect;
	}
}
