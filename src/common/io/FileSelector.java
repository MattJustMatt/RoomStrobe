package common.io;

import javax.swing.*;

public class FileSelector {
	public static String getPathToReadFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.showOpenDialog(null);

		return fileChooser.getSelectedFile().getAbsolutePath();
	}

	public static String getPathToSaveFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.showSaveDialog(null);

		return fileChooser.getSelectedFile().getAbsolutePath();
	}
}
