package server.logic;

import common.Utilities;
import common.io.FileSaving;
import common.io.FileSelector;
import common.io.Serialization;
import server.objects.PreviewMonitor;
import server.objects.Scene;

import javax.swing.*;
import java.util.ArrayList;

public class SceneHandler {
	private static ArrayList<Scene> scenes;

	static {
		scenes = new ArrayList<Scene>();
	}

	public static void saveSceneFromCurrentState() {
		Scene scene = new Scene(scenes.size()+1, 0.25, deepCopyChannelState());
		scenes.add(scene);
		System.out.println("Added new scene " + scene.getChannelState());
	}

	public static void cloneScene(int index) {
		Scene sceneToClone = scenes.get(index);
		scenes.add(new Scene(sceneToClone.getNumber(), sceneToClone.getTime(), deepCopyChannelState()));
	}

	public static void updateScene(int index) {
		scenes.get(index).setChannelState(deepCopyChannelState());
	}

	public static void deleteScene(int index) {
		scenes.remove(index);
	}

	public static void loadScenesFromFile() {
		if (Utilities.requestUserConfirmation("Are you sure you want to load new scenes? Will not save old scenes!")) {
			String pathToLoad = FileSelector.getPathToReadFile();

			Object objectFromPath = Serialization.readObject(pathToLoad);

			if (objectFromPath instanceof ArrayList) {
				ArrayList<Scene> newScenes = (ArrayList<Scene>) objectFromPath;

				scenes.clear();
				scenes = newScenes;

				JOptionPane.showMessageDialog(null, "Successfully loaded new scenes from file!", "LOADED", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Selected file was not a valid scene database! (or generic failure)", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public static void saveScenesToFile() {
		String pathToSave = FileSelector.getPathToSaveFile();
		boolean success = FileSaving.saveObjectToFile(pathToSave, "scenes", scenes);

		if (success) {
			JOptionPane.showMessageDialog(null, "Scenes successfully saved!", "SAVED", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "Scenes failed to save! (Check console)", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}

	private static ArrayList<PreviewMonitor> deepCopyChannelState() {
		ArrayList<PreviewMonitor> newChannelState = new ArrayList<PreviewMonitor>();

        for (PreviewMonitor monitor : PreviewMonitorHandler.getPreviewMonitors()) {
            newChannelState.add(new PreviewMonitor(monitor));
        }

		return newChannelState;
	}

	public static ArrayList<Scene> getScenes() {
		return scenes;
	}
}
