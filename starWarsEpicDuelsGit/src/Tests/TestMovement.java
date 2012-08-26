package Tests;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import mapPackage.*;

import Model.*;
import Model.Character;

public class TestMovement extends JFrame {

	public static void main(String[] args) {
		new TestMovement();
	}

	public TestMovement() {
		testMovementWithNetwork();
	}

	JButton exit;
	JButton move;
	JButton update;
	JPanel playerPanel;
	JPanel hostPanel;
	JPanel gamePanel;
	Client host;
	Client player;
	Game g;

	public void testMovementWithNetwork() {

		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1550, 600);
		setLocation(100, 100);
		setLayout(null);
		setBackground(Color.BLACK);
		setTitle("Movement Test With Networking");

		exit = new JButton("Exit");
		exit.setSize(100, 25);
		exit.setLocation(800, 450);
		exit.addActionListener(new ButtonListener());
		add(exit);

		move = new JButton("Move");
		move.setSize(100, 25);
		move.setLocation(690, 450);
		move.addActionListener(new ButtonListener());
		add(move);

		update = new JButton("Update");
		update.setSize(100, 25);
		update.setLocation(580, 450);
		update.addActionListener(new ButtonListener());
		add(update);

		hostPanel = new ClientPanel();
		hostPanel.setSize(480, 400);
		hostPanel.setLocation(10, 10);
		add(hostPanel);

		playerPanel = new ClientPanel();
		playerPanel.setSize(480, 400);
		playerPanel.setLocation(500, 10);
		add(playerPanel);

		gamePanel = new ClientPanel();
		gamePanel.setSize(480, 400);
		gamePanel.setLocation(990, 10);
		add(gamePanel);

		g = new Game(2, true, new CarbonFreezeMap());
		g.addObserver((Observer) gamePanel);

		host = new Client("host", "localhost");
		host.addObserver((Observer) hostPanel);

		ArrayList<Character[]> hostAvailable = host.getAvailableCharacters();
		host.chooseCharacter(hostAvailable.get(0));
		while (host.isChoosingCharacters()) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		host.chooseTeam(0);
		while (host.isChoosingTeam()) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		player = new Client("player", "localhost");
		player.addObserver((Observer) playerPanel);

		ArrayList<Character[]> playerAvailable = player
				.getAvailableCharacters();
		player.chooseCharacter(playerAvailable.get(0));
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

		host.update();
		while (host.isUpdating()) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		player.update();
		while (player.isUpdating()) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public class ClientPanel extends JPanel implements Observer {

		private int[][] map;
		private JTextArea view;

		public ClientPanel() {
			map = new int[7][10];
			view = new JTextArea();
			view.setSize(480, 400);
			view.setLocation(0, 0);
			Font ourFont = new Font("Courier", Font.PLAIN, 30);
			view.setFont(ourFont);
			view.setForeground(Color.GREEN);
			view.setBackground(Color.BLACK);
			this.setBackground(Color.BLACK);
			add(view);
		}

		private String viewToString() {
			String result = "";
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[0].length; j++) {
					result += map[i][j] + " ";
				}
				result += "\n";
			}
			return result;
		}

		@Override
		public void update(Observable notifier, Object argument) {
			if (notifier instanceof Client) {
				Client client = (Client) notifier;
				System.out.println(client.getName() + " Has Notified");
				map = (client.getMap()).getMap();
				view.setText(viewToString());
				this.repaint();
			} else {
				Game client = (Game) notifier;
				map = (client.getMap()).getMap();
				view.setText(viewToString());
				this.repaint();
			}
		}

	}

	public class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			Object source = event.getSource();
			if (source.equals(exit)) {
				g.kill();
				System.exit(0);
			} else if (source.equals(update)) {
				host.update();
				while (host.isUpdating()) {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

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
						if (grid[i][j] == 10) {
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
				host.move(hostCurrent, dest);
			}
		}
	}
}
