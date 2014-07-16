package server.logic;

import server.MainServer;
import server.objects.Scene;

import java.io.File;

public class ShowHandler {
	private static boolean isShowPlaying = false;
	private static File music;

	public static void playShow() {
		//TODO: IMPLEMENT
		Thread scenePlayThread = new Thread(new PlayShowThread());
		scenePlayThread.start();

		isShowPlaying = true;
	}

	public static void playShowWithMusic() {
		//TODO: IMPLEMENT

		if (!isShowPlaying) {
			playShow();
			SoundHandler.playShowMusic();
		}
	}

	public static void stopShow() {
		if (SoundHandler.isPlaying()) {
			SoundHandler.stopShowMusic();
		}

		isShowPlaying = false;
	}

	public static boolean hasValidMusic() {
		if (music.exists() && music.getAbsolutePath().endsWith(".wav")) {
			return true;
		} else {
			return false;
		}
	}

	public static class PlayShowThread implements Runnable {
		public void run() {
			for (Scene scene : SceneHandler.getScenes()) {
				if (isShowPlaying) {
                    MainServer.getMulticastServer().broadcastState(scene.getChannelState());

					try {
						Thread.sleep((long) scene.getTime());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
