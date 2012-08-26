/**
 * To be run in conjunction with TestServer
 */
package Tests;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import mapPackage.PossiblePath;

import Model.*;
import Model.Character;

public class TestClient extends JFrame {

	JButton exit;
	JButton move;
	JButton update;
	JPanel playerPanel;
	JPanel gamePanel;
	Client player;

	public static void main(String[] args) {
		new TestClient().setVisible(true);
	}

	public TestClient() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(512, 600);
		setLocation(100, 100);
		setLayout(null);
		setTitle("Test Networking (Client Side)");

		exit = new JButton("Exit");
		exit.setSize(100, 25);
		exit.setLocation(300, 450);
		exit.addActionListener(new ButtonListener());
		add(exit);

		move = new JButton("Move");
		move.setSize(100, 25);
		move.setLocation(190, 450);
		move.addActionListener(new ButtonListener());
		add(move);

		update = new JButton("Update");
		update.setSize(100, 25);
		update.setLocation(80, 450);
		update.addActionListener(new ButtonListener());
		add(update);

		playerPanel = new ClientPanel();
		playerPanel.setSize(480, 400);
		playerPanel.setLocation(10, 10);
		add(playerPanel);

		String ip = JOptionPane.showInputDialog("IP???");

		player = new Client("player", ip);
		player.addObserver((Observer) playerPanel);

		ArrayList<Character[]> hostAvailable = player.getAvailableCharacters();
		player.chooseCharacter(hostAvailable.get(0));
		while (player.isChoosingCharacters()) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		player.chooseTeam(1);
		while (player.isChoosingTeam()) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			Object source = event.getSource();
			if (source.equals(exit)) {
				System.exit(0);
			} else if (source.equals(update)) {
				player.update();
				while (player.isUpdating()) {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} else if (source.equals(move)) {
				int[][] grid = (player.getMap()).getMap();
				Point hostCurrent = null;
				for (int i = 0; i < grid.length; i++) {
					for (int j = 0; j < grid[0].length; j++) {
						if (grid[i][j] == 20) {
							hostCurrent = new Point(i, j);
						}
					}
				}
				int[][] possible = PossiblePath.GetPossiblePath(grid, 10, 2);

				Point dest = null;
				for (int i = 0; i < possible.length; i++) {
					for (int j = 0; j < possible[0].length; j++) {
						if (possible[i][j] == 6) {
							dest = new Point(i, j);
						}
					}
				}
				player.move(hostCurrent, dest);
			}
		}
	}

}
