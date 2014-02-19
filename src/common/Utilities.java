package common;

import javax.swing.*;

public class Utilities {
	public static boolean requestUserConfirmation(String message) {
		int confirmed = JOptionPane.showConfirmDialog(null, message, "WARNING", JOptionPane.WARNING_MESSAGE);

		if (confirmed == JOptionPane.YES_OPTION) {
			return true;
		} else {
			return false;
		}
	}
}
