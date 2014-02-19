package server.graphics;

import server.MainServer;
import server.enums.ConsoleState;
import server.enums.EffectType;
import server.logic.*;
import server.networking.MulticastServer;
import server.objects.Channel;
import server.objects.Console;
import server.objects.PreviewMonitor;
import server.objects.Scene;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class ServerGraphics {
	private JPanel mainServerPanel;
	private JList sceneList;
	private JPanel scenesJPanel;
	private JPanel channelControlJPanel;
	private JPanel intensityControlJPanel;
	private JSlider rSlider;
	private JSlider gSlider;
	private JSlider bSlider;
	private JButton btnEffectStrobe;
	private JPanel effectsControlJPanel;
	private JButton btnEffectSnake;
	private JButton btnEffectDisco;
	private JButton btnEffectFade;
	private JScrollPane sceneScrollPane;
	private JPanel sceneControlPanel;
	private JSpinner spinnerEffectSpeed;
	private JButton btnFullRed;
	private JButton btnFullGreen;
	private JButton btnFullBlue;
	private JButton btnStopShow;
	private JButton btnSelectMusic;
	private JButton btnPlayShow;
	private JButton btnPlayShowWithMusic;
	private JLabel lblRValue;
	private JLabel lblGValue;
	private JLabel lblBValue;
	private JPanel visualJPanel;
	private JPanel monitorsJPanel;
	private JPanel visualControlJPanel;
	private JButton btnSelectAll;
	private JButton btnDeselectAll;
	private JPanel toolBarJPane;
	private JButton btnLoadUniverse;
	private JButton btnSaveUniverse;
	private JButton btnToggleNetwork;
	private JButton btnSaveScenes;
	private JButton btnLoadScenes;
	private JTextField textConsole;
	private JButton btnAddPreviewMonitor;
	private JButton btnRecordScene;
	private JButton btnCloneScene;
	private JButton btnUpdateScene;
	private JButton btnDeleteScene;
	private JSlider volumeSlider;
	private ArrayList<JButton> effectButtons;

	public ServerGraphics() {
		setupLookAndFeel();

		sceneList.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//Set Keyboard focus to a new instance of KeyDispatcher for console input.
		KeyboardFocusManager focusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		focusManager.addKeyEventDispatcher(new KeyDispatcher());

		//Add Button Listener to effects
		ButtonEffectListener buttonEffectListener = new ButtonEffectListener();
		btnEffectStrobe.addActionListener(buttonEffectListener);
		btnEffectSnake.addActionListener(buttonEffectListener);
		btnEffectDisco.addActionListener(buttonEffectListener);
		btnEffectFade.addActionListener(buttonEffectListener);

		//Add button listener to @Full parameters
		ButtonAtFullListener buttonAtFullListener = new ButtonAtFullListener();
		btnFullRed.addActionListener(buttonAtFullListener);
		btnFullGreen.addActionListener(buttonAtFullListener);
		btnFullBlue.addActionListener(buttonAtFullListener);

		//Add button listener to toolbar
		ButtonToolBarListener buttonToolBarListener = new ButtonToolBarListener();
		btnLoadUniverse.addActionListener(buttonToolBarListener);
		btnSaveUniverse.addActionListener(buttonToolBarListener);
		btnLoadScenes.addActionListener(buttonToolBarListener);
		btnSaveScenes.addActionListener(buttonToolBarListener);
		btnToggleNetwork.addActionListener(buttonToolBarListener);

		//Add button listener to add preview monitor button
		ButtonAddPreviewMonitorListener buttonAddPreviewMonitorListener = new ButtonAddPreviewMonitorListener();
		btnAddPreviewMonitor.addActionListener(buttonAddPreviewMonitorListener);

		//Add selection handler listener to buttons
		SelectToggleListener selectToggleListener = new SelectToggleListener();
		btnSelectAll.addActionListener(selectToggleListener);
		btnDeselectAll.addActionListener(selectToggleListener);

		//Add scene control button listener
		SceneControlListener sceneControlListener = new SceneControlListener();
		btnRecordScene.addActionListener(sceneControlListener);
		btnCloneScene.addActionListener(sceneControlListener);
		btnUpdateScene.addActionListener(sceneControlListener);
		btnDeleteScene.addActionListener(sceneControlListener);
		btnPlayShow.addActionListener(sceneControlListener);
		btnPlayShowWithMusic.addActionListener(sceneControlListener);
		btnStopShow.addActionListener(sceneControlListener);
		btnSelectMusic.addActionListener(sceneControlListener);

		//Add color slider listener
		ColorSliderListener colorSliderListener = new ColorSliderListener();
		rSlider.addChangeListener(colorSliderListener);
		gSlider.addChangeListener(colorSliderListener);
		bSlider.addChangeListener(colorSliderListener);

		//Add volume slider listener
		VolumeSliderListener volumeSliderListener = new VolumeSliderListener();
		volumeSlider.addChangeListener(volumeSliderListener);

		effectButtons = new ArrayList<JButton>();
		effectButtons.add(btnEffectStrobe);
		effectButtons.add(btnEffectSnake);
		effectButtons.add(btnEffectDisco);
		effectButtons.add(btnEffectFade);
		sceneList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				NetworkHandler.updateStatic(SceneHandler.getScenes().get(sceneList.getSelectedIndex()).getChannelState());
			}
		});
	}

	private class VolumeSliderListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			SoundHandler.setVolume(volumeSlider.getValue());
		}
	}

	private class ColorSliderListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			int r = rSlider.getValue();
			int g = gSlider.getValue();
			int b = bSlider.getValue();

			lblRValue.setText("" + r);
			lblGValue.setText("" + g);
			lblBValue.setText("" + b);

			Color sliderColor = new Color(r, g, b);
			lblRValue.setForeground(sliderColor);
			lblGValue.setForeground(sliderColor);
			lblBValue.setForeground(sliderColor);

			for (Channel channel : UniverseHandler.getSelectedChannels()) {
				channel.setColor(sliderColor);
			}

			for (PreviewMonitor previewMonitor : PreviewMonitorHandler.getPreviewMonitors()) {
				previewMonitor.update();
			}

			NetworkHandler.updateStatic();
		}
	}

	public class SceneControlListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton) e.getSource();

			if (source.equals(btnRecordScene)) {
				SceneHandler.saveSceneFromCurrentState();
			} else if (source.equals(btnCloneScene)) {
				SceneHandler.cloneScene(sceneList.getSelectedIndex());
			} else if (source.equals(btnUpdateScene)) {
				SceneHandler.updateScene(sceneList.getSelectedIndex());
			} else if (source.equals(btnDeleteScene)) {
				SceneHandler.deleteScene(sceneList.getSelectedIndex());
			} else if (source.equals(btnPlayShow)) {
				ShowHandler.playShow();
			} else if (source.equals(btnPlayShowWithMusic)) {
				ShowHandler.playShowWithMusic();
			} else if (source.equals(btnStopShow)) {
				ShowHandler.stopShow();
			} else if (source.equals(btnSelectMusic)) {
				SoundHandler.selectShowMusic();
			}

			updateScenes();
		}
	}

	private class SelectToggleListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton) e.getSource();

			//TODO: IMPLEMENT
			if (source.equals(btnSelectAll)) {
				for (PreviewMonitor previewMonitor : PreviewMonitorHandler.getPreviewMonitors()) {
					previewMonitor.setSelected(true);
				}
			} else if (source.equals(btnDeselectAll)) {
				for (PreviewMonitor previewMonitor : PreviewMonitorHandler.getPreviewMonitors()) {
					previewMonitor.setSelected(false);
				}
			}
		}
	}

	private class ButtonAddPreviewMonitorListener implements  ActionListener {
		public void actionPerformed(ActionEvent e) {
			PreviewMonitorHandler.createNewMonitor();
		}
	}

	private class ButtonToolBarListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton) e.getSource();

			if (source.equals(btnLoadUniverse)) {
				UniverseHandler.loadUniverseFromFile();
			} else if (source.equals(btnSaveUniverse)) {
				UniverseHandler.saveActiveUniverseToFile();
			} else if (source.equals(btnLoadScenes)) {
				SceneHandler.loadScenesFromFile();
				updateScenes();
			} else if (source.equals(btnSaveScenes)) {
				SceneHandler.saveScenesToFile();
			} else if (source.equals(btnToggleNetwork)) {
				if (MulticastServer.isEnabled()) {
					MulticastServer.setEnabled(false);
					btnToggleNetwork.setText("Toggle Network: Off");
				} else {
					MulticastServer.setEnabled(true);
					btnToggleNetwork.setText("Toggle Network: On");
				}
			}
		}
	}

	private class ButtonAtFullListener implements  ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton) e.getSource();

			//If the sliders are at any value other than 255, set them to 255 (full), if at 255 then set it to 0 (none)
			if (source.equals(btnFullRed)) {
				if (rSlider.getValue() == 255) {
					rSlider.setValue(0);
				} else {
					rSlider.setValue(255);
				}
			} else if (source.equals(btnFullGreen)) {
				if (gSlider.getValue() == 255) {
					gSlider.setValue(0);
				} else {
					gSlider.setValue(255);
				}
			} else if (source.equals(btnFullBlue)) {
				if (bSlider.getValue() == 255) {
					bSlider.setValue(0);
				} else {
					bSlider.setValue(255);
				}
			}
		}
	}

	private class ButtonEffectListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton) e.getSource();
			EffectType hitEffect = EffectType.NONE;

			if (source.equals(btnEffectStrobe)) {
				hitEffect = EffectType.STROBE;
			} else if (source.equals(btnEffectSnake)) {
				hitEffect = EffectType.SNAKE;
			} else if (source.equals(btnEffectDisco)) {
				hitEffect = EffectType.DISCO;
			} else if (source.equals(btnEffectFade)) {
				hitEffect = EffectType.FADE;
			}

			boolean isRunning = EffectHandler.isEffectRunningInChannels(UniverseHandler.getSelectedChannels(), hitEffect);
			System.out.println(isRunning);
			if (isRunning) {
				source.setText(hitEffect.toString() + ": Off");
				EffectHandler.stopEffectsOnChannels(UniverseHandler.getSelectedChannels());
			} else {
				source.setText(hitEffect.toString() + ": On");
				EffectHandler.startEffectOnChannels(UniverseHandler.getSelectedChannels(), hitEffect);
			}
		}
	}

	//Create Key Dispatcher for console input.
	private class KeyDispatcher implements KeyEventDispatcher {
		@Override
		public boolean dispatchKeyEvent(KeyEvent e) {
			if (e.getID() == KeyEvent.KEY_TYPED) {
				boolean effectSpeedSpinnerHasFocus = ((JSpinner.NumberEditor) spinnerEffectSpeed.getEditor()).getTextField().isFocusOwner();

				if (!effectSpeedSpinnerHasFocus) {
					Console console = MainServer.getConsole();
					console.handleInput(e.getKeyChar());

					textConsole.setText(console.getCurrentOutput());

					if (console.getState() == ConsoleState.SYNTAXERROR) {
						textConsole.setForeground(new Color(255, 50, 50));
					} else {
						textConsole.setForeground(new Color(0, 0, 0));
					}
				}
			}

			return false;
		}
	}

	private void createUIComponents() {
		//Custom spinner, formats as number (0.05 increments) and does not allow non-integer typing.
		SpinnerNumberModel model = new SpinnerNumberModel(1, 0.05, 5, 0.05);
		spinnerEffectSpeed = new JSpinner(model);
		JFormattedTextField txt = ((JSpinner.NumberEditor) spinnerEffectSpeed.getEditor()).getTextField();
		((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);

		monitorsJPanel = new JPanel();

		for (int i = 1; i < 20; i++) {
			PreviewMonitor testMonitor = new PreviewMonitor(new Channel(i));
			monitorsJPanel.add(testMonitor.getGraphic());
			PreviewMonitorHandler.getPreviewMonitors().add(testMonitor);
		}

	}

	private void updateScenes() {
		sceneList.clearSelection();

		DefaultListModel listModel = (DefaultListModel) sceneList.getModel();
		listModel.removeAllElements();

		for (Scene scene : SceneHandler.getScenes()) {
			listModel.addElement(scene.toString());
		}
	}

	private void setupLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Error loading look and feel!");
			e.printStackTrace();
		}
	}

	public JPanel getMainServerPanel() {
		return this.mainServerPanel;
	}
}