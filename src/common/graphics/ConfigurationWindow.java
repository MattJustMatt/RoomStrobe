package common.graphics;

import client.ClientStarter;
import server.MainServer;

import javax.swing.*;
import java.awt.event.*;

public class ConfigurationWindow extends JDialog {
	private JPanel contentPane;
	private JButton buttonOK;
	private JButton buttonCancel;

	private ButtonGroup groupClientServerSelection = new ButtonGroup();
	private JRadioButton radioClient;
	private JRadioButton radioServer;

	public ConfigurationWindow() {
		setContentPane(contentPane);
		setModal(true);
		getRootPane().setDefaultButton(buttonOK);

		buttonOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onOK();
			}
		});

		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCancel();
			}
		});

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				onCancel();
			}
		});

		contentPane.registerKeyboardAction(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCancel();
			}
		}, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

		groupClientServerSelection.add(radioClient);
		groupClientServerSelection.add(radioServer);
	}

	private void onOK() {
		if (radioClient.isSelected()) {
			dispose();

			ClientStarter.start(0);
		} else if (radioServer.isSelected()) {
			dispose();

			try {
				MainServer.startServer();
			} catch (Exception e) {

			}
		} else {
			JOptionPane.showMessageDialog(null, "You must select either \"Client\" or \"Server\"!", "ERROR!", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void onCancel() {
		dispose();
	}
}
