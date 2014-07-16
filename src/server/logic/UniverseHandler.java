package server.logic;

import common.Utilities;
import common.io.FileSaving;
import common.io.FileSelector;
import common.io.Serialization;
import server.objects.Channel;
import server.objects.Universe;

import javax.swing.*;
import java.util.ArrayList;

public class UniverseHandler {
	private static Universe activeUniverse;
	private static ArrayList<Channel> selectedChannels;

	static {
		activeUniverse = new Universe("Untitled Universe");
		selectedChannels = new ArrayList<Channel>();
	}

	public static ArrayList<Channel> getSelectedChannels() {
		return selectedChannels;
	}

	public static void selectAllChannels() {
		selectedChannels.clear();
		selectedChannels.addAll(activeUniverse.getChannels());
	}

	public static void loadUniverseFromFile() {
		if (Utilities.requestUserConfirmation("Are you sure you want to load a new universe? Will not save old universe!")) {
			String pathToLoad = FileSelector.getPathToReadFile();

			Object objectFromPath = Serialization.readObject(pathToLoad);

			if (objectFromPath instanceof Universe) {
				Universe newUniverse = (Universe) objectFromPath;
				selectedChannels.clear();

				activeUniverse = newUniverse;

				JOptionPane.showMessageDialog(null, "Successfully loaded new universe: " + newUniverse.getName(), "LOADED", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Selected file was not a valid universe! (or generic failure)", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public static void saveActiveUniverseToFile() {
		String pathToSave = FileSelector.getPathToSaveFile();
		boolean success = FileSaving.saveObjectToFile(pathToSave, "universe", activeUniverse);

		if (success) {
			JOptionPane.showMessageDialog(null, "Universe " + activeUniverse.getName() + " successfully saved!", "SAVED", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "Universe " + activeUniverse.getName() + " failed to save! (Check console)", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static Universe getActiveUniverse() {
		return activeUniverse;
	}

    public static String report() {
        String channelString = "";
        for (Channel channel : getActiveUniverse().getChannels()) {
            channelString += channel + "\n";
        }

        return "UNIVERSE:\n" + channelString;
    }
}