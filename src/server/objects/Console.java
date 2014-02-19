package server.objects;

import server.enums.ConsoleState;
import server.logic.SceneHandler;
import server.logic.UniverseHandler;

import java.util.ArrayList;

public class Console {
	private String currentOutput;
	private ConsoleState state;

	private String piece;

	private ArrayList<Integer> selectedChannels;
	private ArrayList<Integer> selectedScenes;

	private boolean isScene;

	public Console() {
		currentOutput = "";
		state = ConsoleState.EMPTY;

		piece = "";

		selectedChannels = new ArrayList<Integer>();
		selectedScenes = new ArrayList<Integer>();

		isScene = false;
	}

	public void handleInput(char input) {
		System.out.println("Processing input: " + input + " current console state: " + state);

		if (isValidInput(input)) {
			boolean numeric = Character.isDigit(input);

			//Update console state based on approved input.
			if (state.equals(ConsoleState.EMPTY)) {
				if (numeric) {
					state = ConsoleState.TYPINGCHANNEL;

					piece += input;
					currentOutput += input;
				} else if (input == 's') {
					state = ConsoleState.TYPINGSCENE;

					currentOutput = "Scene: ";
				}
			} else if (state.equals(ConsoleState.TYPINGCHANNEL)) {
				if (input == ' ') {
					boolean isValid = false;

					for (Scene scene : SceneHandler.getScenes()) {
						if (scene.getNumber() == Integer.parseInt(piece)) {
							isValid = true;
						}
					}

					if (isValid) {
						state = ConsoleState.CHANNElSELECTED;
						piece = "";
					} else {
						state = ConsoleState.SYNTAXERROR;
						currentOutput = "SYNTAX ERROR: Invalid Channel";
					}
				} else {
					piece += input;
					currentOutput += input;
				}
			} else if (state.equals(ConsoleState.TYPINGSCENE)) {
				if (input == ' ') {
					boolean isValid = false;

					for (Channel channel : UniverseHandler.getActiveUniverse().getChannels()) {
						if (channel.getNumber() == Integer.parseInt(piece)) {
							isValid = true;
						}
					}

					if (isValid) {
						state = ConsoleState.SCENESELECTED;
						piece = "";
					} else {
						state = ConsoleState.SYNTAXERROR;
						currentOutput = "SYNTAX ERROR: Invalid Scene";
					}
				} else {
					piece += input;
					currentOutput += input;
				}
			} else if (state.equals(ConsoleState.SYNTAXERROR)) {
				state = ConsoleState.EMPTY;
				piece = "";
				currentOutput = "";
			}
		} else {
			System.out.println("INVALID INPUT DETECTED: " + input);
		}
	}

	private boolean isValidInput(char input) {
		boolean numeric = Character.isDigit(input);

		if (state.equals(ConsoleState.EMPTY)) {
			//The first thing users should type is a channel number, so if the console is empty we must have a number
			if (numeric) {
				return true;
			} else if (input == 's') {
				return true;
			}
		} else if (state.equals(ConsoleState.CHANNElSELECTED)) {

		} else if (state.equals(ConsoleState.TYPINGCHANNEL)) {
			if (numeric) {
				return true;
			} else if (input == ' ') {
				return true;
 			}
		} else if (state.equals(ConsoleState.TYPINGSCENE)) {
			if (numeric) {
				return true;
			} else if (input == ' ') {
				return true;
			}
		} else if (state.equals(ConsoleState.SYNTAXERROR)) {
			return true;
		}

		return false;
	}

	public String getCurrentOutput() {
		return this.currentOutput;

	}

	public ConsoleState getState() {
		return this.state;
	}
}
