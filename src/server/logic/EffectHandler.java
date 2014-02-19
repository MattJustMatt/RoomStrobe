package server.logic;

import server.enums.EffectType;
import server.objects.Channel;

import java.util.ArrayList;

public class EffectHandler {
	public static void startEffectOnChannels(ArrayList<Channel> channels, EffectType effectType) {
		for (Channel channel : channels) {
			channel.setEffect(EffectType.NONE);
			channel.setEffect(effectType);
		}
	}

	public static void stopEffectsOnChannels(ArrayList<Channel> channels) {
		for (Channel channel : channels) {
				channel.setEffect(EffectType.NONE);
		}
	}

	//Checks if any of ArrayList<Channel> are running EffectType
	public static boolean isEffectRunningInChannels(ArrayList<Channel> channels, EffectType effect) {
		for (Channel channel : channels) {
			if (channel.getEffect().equals(effect)) {
				return true;
			}
		}

		return false;
	}
}