package server.logic;

import common.io.FileSelector;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.io.File;

public class SoundHandler {
	private static File showMusic;

	private static float reservedVolume = -60;
	private static AudioInputStream audioInputStream;
	private static Clip clip;

	public static void playShowMusic() {
		System.out.println("Starting show music.");
		if (showMusic != null && showMusic.exists()) {
			try {
				audioInputStream = AudioSystem.getAudioInputStream(showMusic.getAbsoluteFile());
				clip = AudioSystem.getClip();

				clip.open(audioInputStream);

				FloatControl volControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

				volControl.setValue(reservedVolume);

				clip.start();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error playing music from file!", "ERROR", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(null, "Could not find music in scene database!", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void stopShowMusic() {
		System.out.println("Stopping show music.");
		if (clip != null) {
			clip.stop();
		} else {
			System.out.println("Couldn't stop song because clip was null");
		}
	}

	public static void setVolume(int vol) {
		if (vol < 0 || vol > 100) {
			vol = 100;
		}

		double val = vol / 100.0;

		float dB = (float) (Math.log(val == 0.0 ? 0.0001 : val) / Math.log(10.0) * 20.0);

		if (clip != null) {
			FloatControl volControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

			try {
				volControl.setValue(dB);
			} catch (Exception ex) {
				System.err.println("Error: could not set volume");
			}
		} else {
			reservedVolume = dB;
		}
	}

	public static boolean isPlaying() {
		if (clip == null) {
			return false;
		}

		return clip.isActive();
	}

	public static void selectShowMusic() {
		String path = FileSelector.getPathToReadFile();

		if (path.endsWith(".wav")) {
			showMusic = new File(path);
		} else {
			JOptionPane.showMessageDialog(null, "You must select a .wav audio file!", "ERROR", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
}
