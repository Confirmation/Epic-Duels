package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import mapPackage.*;

import Model.AISetup;
import Model.Client;
import Model.Game;

public class MainMenuGUI extends JFrame implements Serializable {

	private static final long serialVersionUID = -2764263090882536913L;

	private Game game;

	private final int maxPlayers = 6;

	private Client client;
	@SuppressWarnings("unused")
	private ArrayList<Client> AIClients = new ArrayList<Client>();

	private MainMap map;

	public static String baseDir = System.getProperty("user.dir")
			+ File.separator + "Images" + File.separator;

	// private Model game; // The game model

	private JButton hostButton, clientButton, helpButton, exitButton,
			legacyButton, reimagineButton, back2Button, main2Button,
			playerUpButton, playerDownButton, aiUpButton, aiDownButton,
			confirmButton, back3Button, main3Button, joinButton, back4Button,
			main4Button, back5Button, main5Button, map1Button, map2Button,
			map3Button, map4Button, helpBackButton, charMain;

	private JTextField joinText, joinName, hostName;

	private JLabel joinTextLabel, joinNameLabel, playerNumber, aiNumber,
			playerLabel, aiLabel, hostLabel, mapLabel;

	// phase1 = startup
	// phase2 = type of game
	// phase3 = set-up game (client does not go here)
	// phase4 = join game (client goes here)
	// phase5 = map selection for the host
	// phase6 = character selection
	// phase7 = the actual game
	private JPanel phase1Box, phase2Box, phase3Box, phase4Box, phase5Box; // Box
																			// that
																			// holds
																			// buttons

	private GamePanel gamePanel = new GamePanel();

	private CharacterSelectionPanel selectionPanel = new CharacterSelectionPanel();

	private JPanel helpPanel = new HelpPanel();
	// Variables to be passed to the game.
	private int numPlayers, numAI;
	// host = true if the player is the host
	// original = true if it is the boardgame (not required)
	private boolean original;

	ImageIcon image = new ImageIcon(baseDir + "possibleSplash2.jpg");

	ImageIcon buttonImage1 = new ImageIcon(baseDir + "buttonAttemptOne.png");
	ImageIcon buttonImage2 = new ImageIcon(baseDir + "buttonAttemptTwo.png");

	ImageIcon up = new ImageIcon(baseDir + "up.png");
	ImageIcon down = new ImageIcon(baseDir + "down.png");

	private JLabel splashPic = new JLabel(image);

	private final static ImageIcon geonosis = new ImageIcon(baseDir
			+ "geonosis_button.jpg");
	private final static ImageIcon kamino = new ImageIcon(baseDir
			+ "kamino_button.jpg");
	private final static ImageIcon throne = new ImageIcon(baseDir
			+ "throne_button.jpg");
	private final static ImageIcon carbon = new ImageIcon(baseDir
			+ "carbon_button.jpg");

	public static void main(String[] args) {
		new MainMenuGUI().setVisible(true); // Start the GUI
	}

	public MainMenuGUI() {

		Font bigFont = new Font("Tahoma", Font.PLAIN, 60);

		Font buttonFont = new Font("Helvetica", Font.ITALIC, 20);

		numPlayers = 2;
		numAI = 0;
		original = true;

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1024, 600);
		setLocation(0, 0);
		setLayout(null);
		setResizable(false);
		setTitle("Star Wars Epic Duels");
		Container con = this.getContentPane();
		con.setBackground(Color.BLACK);

		gamePanel.setVisible(false);
		add(gamePanel);

		selectionPanel.setVisible(false);
		add(selectionPanel);

		charMain = new JButton("Cancel");
		charMain.setSize(200, 35);
		charMain.setLocation(775, 500);
		charMain.setBorder(null);
		charMain.addActionListener(new CancelListener());
		selectionPanel.add(charMain);

		// a panel for buttons
		phase1Box = new JPanel();
		phase1Box.setLayout(null);
		phase1Box.setSize(250, 600);
		phase1Box.setLocation(765, 0);
		phase1Box.setBorder(null);
		phase1Box.setBackground(Color.BLACK);
		phase1Box.setBorder(new CompoundBorder(BorderFactory
				.createEmptyBorder(), new EmptyBorder(10, 10, 10, 10)));
		add(phase1Box);
		phase1Box.setVisible(true);

		hostButton = new JButton("Host Game", buttonImage1);
		hostButton.setHorizontalTextPosition(JButton.CENTER);
		hostButton.setVerticalTextPosition(JButton.CENTER);
		hostButton.setForeground(Color.WHITE);
		hostButton.setFont(buttonFont);
		hostButton.setSize(200, 35);
		hostButton.setLocation(10, 40);
		hostButton.setBorder(null);
		hostButton.setContentAreaFilled(false);
		hostButton.addActionListener(new ForwardListener());
		// activate enter key for button
		hostButton.registerKeyboardAction(hostButton
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, false)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
		hostButton.registerKeyboardAction(hostButton
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, true)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);
		phase1Box.add(hostButton);

		clientButton = new JButton("Join Game", buttonImage2);
		clientButton.setHorizontalTextPosition(JButton.CENTER);
		clientButton.setVerticalTextPosition(JButton.CENTER);
		clientButton.setForeground(Color.WHITE);
		clientButton.setFont(buttonFont);
		clientButton.setSize(200, 35);
		clientButton.setLocation(10, 140);
		clientButton.setContentAreaFilled(false);
		clientButton.setBorder(null);
		clientButton.addActionListener(new ForwardListener());
		// activate enter key for button
		clientButton.registerKeyboardAction(clientButton
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, false)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
		clientButton.registerKeyboardAction(clientButton
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, true)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);
		phase1Box.add(clientButton);

		helpButton = new JButton("Help", buttonImage2);
		helpButton.setHorizontalTextPosition(JButton.CENTER);
		helpButton.setVerticalTextPosition(JButton.CENTER);
		helpButton.setForeground(Color.WHITE);
		helpButton.setFont(buttonFont);
		helpButton.setSize(200, 35);
		helpButton.setContentAreaFilled(false);
		helpButton.setBorder(null);
		helpButton.setLocation(10, 420);
		helpButton.addActionListener(new HelpListener());
		// activate enter key for button
		helpButton.registerKeyboardAction(helpButton
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, false)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
		helpButton.registerKeyboardAction(helpButton
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, true)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);
		phase1Box.add(helpButton);

		exitButton = new JButton("Exit", buttonImage1);
		exitButton.setHorizontalTextPosition(JButton.CENTER);
		exitButton.setVerticalTextPosition(JButton.CENTER);
		exitButton.setForeground(Color.WHITE);
		exitButton.setFont(buttonFont);
		exitButton.setSize(200, 35);
		exitButton.setLocation(10, 500);
		exitButton.setContentAreaFilled(false);
		exitButton.setBorder(null);
		exitButton.addActionListener(new ExitListener());
		// activate enter key for button
		exitButton.registerKeyboardAction(exitButton
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, false)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
		exitButton.registerKeyboardAction(exitButton
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, true)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);
		phase1Box.add(exitButton);

		// a panel for buttons
		phase2Box = new JPanel();
		phase2Box.setLayout(null);
		phase2Box.setSize(250, 600);
		phase2Box.setLocation(765, 0);
		phase2Box.setBorder(null);
		phase2Box.setBackground(Color.BLACK);
		phase2Box.setBorder(new CompoundBorder(BorderFactory
				.createEmptyBorder(), new EmptyBorder(10, 10, 10, 10)));
		add(phase2Box);
		phase2Box.setVisible(false);

		legacyButton = new JButton("Legacy Mode", buttonImage2);
		legacyButton.setContentAreaFilled(false);
		legacyButton.setHorizontalTextPosition(JButton.CENTER);
		legacyButton.setVerticalTextPosition(JButton.CENTER);
		legacyButton.setForeground(Color.WHITE);
		legacyButton.setFont(buttonFont);
		legacyButton.setSize(200, 35);
		legacyButton.setLocation(10, 40);
		legacyButton.setBorder(null);
		legacyButton.addActionListener(new ForwardListener());
		// activate enter key for button
		legacyButton.registerKeyboardAction(legacyButton
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, false)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
		legacyButton.registerKeyboardAction(legacyButton
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, true)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);
		phase2Box.add(legacyButton);

		reimagineButton = new JButton("Reimagined Mode", buttonImage2);
		reimagineButton.setContentAreaFilled(false);
		reimagineButton.setHorizontalTextPosition(JButton.CENTER);
		reimagineButton.setVerticalTextPosition(JButton.CENTER);
		reimagineButton.setForeground(Color.WHITE);
		reimagineButton.setFont(buttonFont);
		reimagineButton.setSize(200, 35);
		reimagineButton.setLocation(10, 140);
		reimagineButton.setBorder(null);
		reimagineButton.addActionListener(new ForwardListener());
		phase2Box.add(reimagineButton);

		back2Button = new JButton("Back", buttonImage1);
		back2Button.setContentAreaFilled(false);
		back2Button.setHorizontalTextPosition(JButton.CENTER);
		back2Button.setVerticalTextPosition(JButton.CENTER);
		back2Button.setForeground(Color.WHITE);
		back2Button.setFont(buttonFont);
		back2Button.setSize(200, 35);
		back2Button.setLocation(10, 420);
		back2Button.setBorder(null);
		back2Button.addActionListener(new BackListener());
		// activate enter key for button
		back2Button.registerKeyboardAction(back2Button
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, false)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
		back2Button.registerKeyboardAction(back2Button
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, true)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);
		phase2Box.add(back2Button);

		main2Button = new JButton("Main Menu", buttonImage1);
		main2Button.setContentAreaFilled(false);
		main2Button.setHorizontalTextPosition(JButton.CENTER);
		main2Button.setVerticalTextPosition(JButton.CENTER);
		main2Button.setForeground(Color.WHITE);
		main2Button.setFont(buttonFont);
		main2Button.setSize(200, 35);
		main2Button.setLocation(10, 500);
		main2Button.setBorder(null);
		main2Button.addActionListener(new MainMenuListener());
		// activate enter key for button
		main2Button.registerKeyboardAction(main2Button
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, false)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
		main2Button.registerKeyboardAction(main2Button
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, true)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);
		phase2Box.add(main2Button);

		// a panel for buttons
		phase3Box = new JPanel();
		phase3Box.setLayout(null);
		phase3Box.setSize(250, 600);
		phase3Box.setLocation(765, 0);
		phase3Box.setBorder(null);
		phase3Box.setBackground(Color.BLACK);
		phase3Box.setBorder(new CompoundBorder(BorderFactory
				.createEmptyBorder(), new EmptyBorder(10, 10, 10, 10)));
		add(phase3Box);
		phase3Box.setVisible(false);

		playerLabel = new JLabel("Number of Players");
		playerLabel.setSize(120, 20);
		playerLabel.setLocation(0, 30);
		playerLabel.setForeground(Color.BLUE);
		phase3Box.add(playerLabel);

		playerNumber = new JLabel("2");
		playerNumber.setSize(500, 50);
		playerNumber.setLocation(30, 50);
		playerNumber.setForeground(Color.BLUE);
		playerNumber.setFont(bigFont);
		phase3Box.add(playerNumber);

		playerUpButton = new JButton(up);
		playerUpButton.setContentAreaFilled(false);
		playerUpButton.setSize(20, 20);
		playerUpButton.setLocation(0, 50);
		playerUpButton.setBorder(null);
		playerUpButton.addActionListener(new NumberListener());
		// activate enter key for button
		playerUpButton.registerKeyboardAction(playerUpButton
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, false)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
		playerUpButton.registerKeyboardAction(playerUpButton
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, true)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);
		phase3Box.add(playerUpButton);

		playerDownButton = new JButton(down);
		playerDownButton.setContentAreaFilled(false);
		playerDownButton.setSize(20, 20);
		playerDownButton.setLocation(0, 80);
		playerDownButton.setBorder(null);
		playerDownButton.addActionListener(new NumberListener());
		// activate enter key for button
		playerDownButton.registerKeyboardAction(playerDownButton
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, false)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
		playerDownButton.registerKeyboardAction(playerDownButton
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, true)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);
		phase3Box.add(playerDownButton);

		aiLabel = new JLabel("Number of AI");
		aiLabel.setSize(100, 20);
		aiLabel.setLocation(150, 30);
		aiLabel.setForeground(Color.RED);
		phase3Box.add(aiLabel);

		aiNumber = new JLabel("0");
		aiNumber.setSize(500, 50);
		aiNumber.setLocation(155, 50);
		aiNumber.setForeground(Color.RED);
		aiNumber.setFont(bigFont);
		phase3Box.add(aiNumber);

		aiUpButton = new JButton(up);
		aiUpButton.setContentAreaFilled(false);
		aiUpButton.setSize(20, 20);
		aiUpButton.setLocation(200, 50);
		aiUpButton.setBorder(null);
		aiUpButton.addActionListener(new NumberListener());
		// activate enter key for button
		aiUpButton.registerKeyboardAction(aiUpButton
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, false)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
		aiUpButton.registerKeyboardAction(aiUpButton
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, true)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);
		phase3Box.add(aiUpButton);

		aiDownButton = new JButton(down);
		aiDownButton.setContentAreaFilled(false);
		aiDownButton.setSize(20, 20);
		aiDownButton.setLocation(200, 80);
		aiDownButton.setBorder(null);
		aiDownButton.addActionListener(new NumberListener());
		// activate enter key for button
		aiDownButton.registerKeyboardAction(aiDownButton
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, false)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
		aiDownButton.registerKeyboardAction(aiDownButton
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, true)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);
		phase3Box.add(aiDownButton);

		hostLabel = new JLabel("Enter Name");
		hostLabel.setSize(200, 20);
		hostLabel.setLocation(10, 140);
		hostLabel.setForeground(Color.RED);
		phase3Box.add(hostLabel);

		hostName = new JTextField();
		hostName.setSize(200, 20);
		hostName.setLocation(10, 160);
		hostName.addActionListener(new ForwardListener());
		// Font joinFont = new Font("Courier", Font.PLAIN, 15);
		// joinText.setFont(joinFont);
		phase3Box.add(hostName);

		confirmButton = new JButton("Continue", buttonImage2);
		confirmButton.setContentAreaFilled(false);
		confirmButton.setHorizontalTextPosition(JButton.CENTER);
		confirmButton.setVerticalTextPosition(JButton.CENTER);
		confirmButton.setForeground(Color.WHITE);
		confirmButton.setFont(buttonFont);
		confirmButton.setSize(200, 35);
		confirmButton.setLocation(10, 240);
		confirmButton.setBorder(null);
		confirmButton.addActionListener(new ForwardListener());
		// activate enter key for button
		confirmButton.registerKeyboardAction(confirmButton
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, false)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
		confirmButton.registerKeyboardAction(confirmButton
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, true)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);
		phase3Box.add(confirmButton);

		back3Button = new JButton("Back", buttonImage2);
		back3Button.setContentAreaFilled(false);
		back3Button.setHorizontalTextPosition(JButton.CENTER);
		back3Button.setVerticalTextPosition(JButton.CENTER);
		back3Button.setForeground(Color.WHITE);
		back3Button.setFont(buttonFont);
		back3Button.setSize(200, 35);
		back3Button.setLocation(10, 420);
		back3Button.setBorder(null);
		back3Button.addActionListener(new BackListener());
		// activate enter key for button
		back3Button.registerKeyboardAction(back3Button
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, false)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
		back3Button.registerKeyboardAction(back3Button
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, true)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);
		phase3Box.add(back3Button);

		main3Button = new JButton("Main Menu", buttonImage2);
		main3Button.setContentAreaFilled(false);
		main3Button.setHorizontalTextPosition(JButton.CENTER);
		main3Button.setVerticalTextPosition(JButton.CENTER);
		main3Button.setForeground(Color.WHITE);
		main3Button.setFont(buttonFont);
		main3Button.setSize(200, 35);
		main3Button.setLocation(10, 500);
		main3Button.setBorder(null);
		main3Button.addActionListener(new MainMenuListener());
		// activate enter key for button
		main3Button.registerKeyboardAction(main3Button
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, false)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
		main3Button.registerKeyboardAction(main3Button
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, true)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);
		phase3Box.add(main3Button);

		// a panel for buttons
		phase4Box = new JPanel();
		phase4Box.setLayout(null);
		phase4Box.setSize(250, 600);
		phase4Box.setLocation(765, 0);
		phase4Box.setBorder(null);
		phase4Box.setBackground(Color.BLACK);
		phase4Box.setBorder(new CompoundBorder(BorderFactory
				.createEmptyBorder(), new EmptyBorder(10, 10, 10, 10)));
		add(phase4Box);
		phase4Box.setVisible(false);

		joinButton = new JButton("Join", buttonImage1);
		// joinButton.setIcon(down);
		joinButton.setContentAreaFilled(false);
		joinButton.setHorizontalTextPosition(JButton.CENTER);
		joinButton.setVerticalTextPosition(JButton.CENTER);
		joinButton.setForeground(Color.WHITE);
		joinButton.setFont(buttonFont);
		joinButton.setSize(200, 35);
		joinButton.setLocation(10, 200);
		joinButton.setBorder(null);
		joinButton.addActionListener(new ForwardListener());
		// activate enter key for button
		joinButton.registerKeyboardAction(joinButton
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, false)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
		joinButton.registerKeyboardAction(joinButton
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, true)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);
		phase4Box.add(joinButton);

		joinName = new JTextField();
		joinName.setSize(200, 20);
		joinName.setLocation(10, 100);
		joinName.addActionListener(new ForwardListener());
		// Font joinFont = new Font("Courier", Font.PLAIN, 15);
		// joinText.setFont(joinFont);
		phase4Box.add(joinName);

		joinNameLabel = new JLabel("Enter Name");
		joinNameLabel.setSize(200, 20);
		joinNameLabel.setLocation(10, 80);
		joinNameLabel.setForeground(Color.RED);
		// Font joinFont = new Font("Courier", Font.PLAIN, 15);
		// joinText.setFont(joinFont);
		phase4Box.add(joinNameLabel);

		joinText = new JTextField();
		joinText.setSize(200, 20);
		joinText.setLocation(10, 150);
		joinText.addActionListener(new ForwardListener());
		phase4Box.add(joinText);

		joinTextLabel = new JLabel("Enter Host IP Address");
		joinTextLabel.setSize(200, 20);
		joinTextLabel.setLocation(10, 130);
		joinTextLabel.setForeground(Color.RED);
		phase4Box.add(joinTextLabel);

		back4Button = new JButton("Back", buttonImage2);
		back4Button.setContentAreaFilled(false);
		back4Button.setHorizontalTextPosition(JButton.CENTER);
		back4Button.setVerticalTextPosition(JButton.CENTER);
		back4Button.setForeground(Color.WHITE);
		back4Button.setFont(buttonFont);
		back4Button.setSize(200, 35);
		back4Button.setLocation(10, 420);
		back4Button.setBorder(null);
		back4Button.addActionListener(new BackListener());
		// activate enter key for button
		back4Button.registerKeyboardAction(back4Button
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, false)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
		back4Button.registerKeyboardAction(back4Button
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, true)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);
		phase4Box.add(back4Button);

		main4Button = new JButton("Main Menu", buttonImage1);
		main4Button.setContentAreaFilled(false);
		main4Button.setHorizontalTextPosition(JButton.CENTER);
		main4Button.setVerticalTextPosition(JButton.CENTER);
		main4Button.setForeground(Color.WHITE);
		main4Button.setFont(buttonFont);
		main4Button.setSize(200, 35);
		main4Button.setLocation(10, 500);
		main4Button.setBorder(null);
		main4Button.addActionListener(new MainMenuListener());
		// activate enter key for button
		main4Button.registerKeyboardAction(main4Button
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, false)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
		main4Button.registerKeyboardAction(main4Button
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, true)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);
		phase4Box.add(main4Button);

		// a panel for buttons
		phase5Box = new JPanel();
		phase5Box.setLayout(null);
		phase5Box.setSize(1024, 600);
		phase5Box.setLocation(0, 0); // 765, 0
		phase5Box.setBorder(null);
		phase5Box.setBackground(Color.BLACK);
		phase5Box.setBorder(new CompoundBorder(BorderFactory
				.createEmptyBorder(), new EmptyBorder(10, 10, 10, 10)));
		add(phase5Box);
		phase5Box.setVisible(false);

		mapLabel = new JLabel("Click on a Map to Start a Game");
		mapLabel.setSize(200, 20);
		mapLabel.setLocation(30, 10);
		mapLabel.setForeground(Color.RED);
		phase5Box.add(mapLabel);

		map1Button = new JButton();
		// joinButton.setIcon(down);
		map1Button.setIcon(geonosis);
		map1Button.setSize(350, 245);
		map1Button.setLocation(30, 35);
		map1Button.setBorder(null);
		map1Button.addActionListener(new ForwardListener());
		map1Button.setBorder(new CompoundBorder(BorderFactory
				.createRaisedBevelBorder(), new EmptyBorder(10, 10, 10, 10)));
		// activate enter key for button
		map1Button.registerKeyboardAction(map1Button
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, false)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
		map1Button.registerKeyboardAction(map1Button
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, true)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);
		phase5Box.add(map1Button);

		map2Button = new JButton();
		// joinButton.setIcon(down);
		map2Button.setIcon(kamino);
		map2Button.setSize(350, 245);
		map2Button.setLocation(390, 35);
		map2Button.setBorder(null);
		map2Button.addActionListener(new ForwardListener());
		map2Button.setBorder(new CompoundBorder(BorderFactory
				.createRaisedBevelBorder(), new EmptyBorder(10, 10, 10, 10)));
		// activate enter key for button
		map2Button.registerKeyboardAction(map2Button
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, false)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
		map2Button.registerKeyboardAction(map2Button
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, true)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);
		phase5Box.add(map2Button);

		map3Button = new JButton();
		// joinButton.setIcon(down);
		map3Button.setIcon(throne);
		map3Button.setSize(350, 245);
		map3Button.setLocation(30, 290);
		map3Button.setBorder(null);
		map3Button.addActionListener(new ForwardListener());
		map3Button.setBorder(new CompoundBorder(BorderFactory
				.createRaisedBevelBorder(), new EmptyBorder(10, 10, 10, 10)));
		// activate enter key for button
		map3Button.registerKeyboardAction(map3Button
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, false)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
		map3Button.registerKeyboardAction(map3Button
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, true)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);
		phase5Box.add(map3Button);

		map4Button = new JButton();
		// joinButton.setIcon(down);
		map4Button.setIcon(carbon);
		map4Button.setSize(350, 245);
		map4Button.setLocation(390, 290);
		map4Button.setBorder(null);
		map4Button.addActionListener(new ForwardListener());
		map4Button.setBorder(new CompoundBorder(BorderFactory
				.createRaisedBevelBorder(), new EmptyBorder(10, 10, 10, 10)));
		// activate enter key for button
		map4Button.registerKeyboardAction(map4Button
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, false)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
		map4Button.registerKeyboardAction(map4Button
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, true)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);
		phase5Box.add(map4Button);

		back5Button = new JButton("Back", buttonImage2);
		back5Button.setContentAreaFilled(false);
		back5Button.setHorizontalTextPosition(JButton.CENTER);
		back5Button.setVerticalTextPosition(JButton.CENTER);
		back5Button.setForeground(Color.WHITE);
		back5Button.setFont(buttonFont);
		back5Button.setSize(200, 35);
		back5Button.setLocation(775, 420);
		back5Button.setBorder(null);
		back5Button.addActionListener(new BackListener());
		// activate enter key for button
		back5Button.registerKeyboardAction(back5Button
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, false)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
		back5Button.registerKeyboardAction(back5Button
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, true)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);
		phase5Box.add(back5Button);

		main5Button = new JButton("Main Menu", buttonImage1);
		main5Button.setContentAreaFilled(false);
		main5Button.setHorizontalTextPosition(JButton.CENTER);
		main5Button.setVerticalTextPosition(JButton.CENTER);
		main5Button.setForeground(Color.WHITE);
		main5Button.setFont(buttonFont);
		main5Button.setSize(200, 35);
		main5Button.setLocation(775, 500);
		main5Button.setBorder(null);
		main5Button.addActionListener(new MainMenuListener());
		// activate enter key for button
		main5Button.registerKeyboardAction(main5Button
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, false)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
		main5Button.registerKeyboardAction(main5Button
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, true)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);
		phase5Box.add(main5Button);

		splashPic.setSize(1024, 600);
		splashPic.setLocation(-175, -15);
		add(splashPic);

		hostButton.grabFocus();
	}

	private class StartGameListener implements ComponentListener {

		@Override
		public void componentHidden(ComponentEvent arg0) {
			if (!splashPic.isVisible()) {

				phase1Box = null;
				phase2Box = null;
				phase3Box = null;
				phase4Box = null;
				phase5Box = null;
				splashPic = null;

				helpPanel = null;
				gamePanel.setVisible(true);
				client.update();
				//System.out.println("gamePanel is now visible");
			}
		}

		@Override
		public void componentMoved(ComponentEvent arg0) {

		}

		@Override
		public void componentResized(ComponentEvent arg0) {

		}

		@Override
		public void componentShown(ComponentEvent arg0) {

		}

	}

	private class ExitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}
	}

	private class HelpBackListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			helpPanel.setVisible(false);
			splashPic.setVisible(true);
			phase1Box.setVisible(true);
			hostButton.grabFocus();
		}
	}

	private class HelpListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// Adds the help menu
			add(helpPanel);
			helpPanel.setVisible(false);
			helpBackButton = new JButton("BACK TO TITLE");
			helpBackButton.setSize(120, 35);
			helpBackButton.setLocation(760, 620);
			helpBackButton.setBackground(Color.BLACK);
			helpBackButton.setForeground(Color.WHITE);
			helpBackButton.addActionListener(new HelpBackListener());
			helpPanel.add(helpBackButton);
			phase1Box.setVisible(false);
			splashPic.setVisible(false);
			helpPanel.setVisible(true);
		}

	}

	private class NumberListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			if (source.equals(playerUpButton)) {
				if (numPlayers >= maxPlayers - numAI && numAI > 0) {
					numPlayers = maxPlayers - numAI + 1;
					numAI = 0;
				} else if (numPlayers >= maxPlayers) {
					numPlayers = maxPlayers;
				} else {
					if (numAI == 0) {
						numPlayers += 2;
						numAI = 0;
					} else {
						if (numPlayers == 1) {
							numPlayers = 2;
							numAI = 0;
						} else {
							numPlayers += 2;
							numAI = 0;
						}
					}
				}
				playerNumber.setText(numPlayers + "");
				aiNumber.setText(numAI + "");
			} else if (source.equals(playerDownButton)) {
				if (numPlayers == 2) {
					numPlayers = 1;
					numAI = 1;
				} else {
					if (numPlayers + numAI == 2 && numPlayers > 1) {
						numPlayers -= 2;
						numAI = 0;
					} else if (numPlayers <= 2) {
						numPlayers = 1;
						numAI = 1;
					} else {
						numPlayers -= 2;
						numAI = 0;
						if (numAI == -1)
							numAI = 1;
					}
				}
				playerNumber.setText(numPlayers + "");
				aiNumber.setText(numAI + "");
			} else if (source.equals(aiUpButton)) {
				if (numAI >= 1) {
					numAI = 1;
				} else {
					numAI = 1;
					numPlayers = 1;
				}

				// if (numAI >= maxPlayers - numPlayers - 1 && numPlayers > 1) {
				// numAI = maxPlayers - numPlayers + 1;
				// numPlayers--;
				// } else if (numAI >= maxPlayers - 1) {
				// numAI = maxPlayers - 1;
				// } else {
				// if (numPlayers > 1) {
				// numPlayers--;
				// numAI++;
				// } else {
				// numAI++;
				// numPlayers++;
				// }
				//
				// }
				aiNumber.setText(numAI + "");
				playerNumber.setText(numPlayers + "");
			} else if (source.equals(aiDownButton)) {
				// if (numAI + numPlayers == 2 && numAI > 0) {
				// numAI--;
				// numPlayers++;
				// } else if (numAI == 0)
				// numAI = 0;
				// else {
				// numPlayers--;
				// numAI--;
				// if (numPlayers == 0)
				// numPlayers = 2;
				// }
				if (numAI <= 0) {
					numAI = 0;
				} else {
					numAI = 0;
					numPlayers++;
				}
				aiNumber.setText(numAI + "");
				playerNumber.setText(numPlayers + "");
			}
		}

	}

	private class ForwardListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Object source = arg0.getSource();
			if (source.equals(hostButton)) {
				phase1Box.setVisible(false);
				phase2Box.setVisible(true);
				legacyButton.grabFocus();
			} else if (source.equals(clientButton)) {
				phase1Box.setVisible(false);
				phase4Box.setVisible(true);
				joinName.grabFocus();
			} else if (source.equals(reimagineButton)
					|| source.equals(legacyButton)) {
				phase2Box.setVisible(false);
				phase3Box.setVisible(true);
				playerUpButton.grabFocus();
			} else if (source.equals(confirmButton) || source.equals(hostName)) {
				if (hostName.getText().length() > 0) {
					String name = hostName.getText();
					while (name.contains("  ")) {
						name = name.replaceAll("  ", " ");
					}
					if (name.length() > 10) {
						name = name.substring(0, 7) + "...";
					}
					hostName.setText(name);
					phase2Box.setVisible(false);
					phase3Box.setVisible(false);
					phase4Box.setVisible(false);
					phase5Box.setVisible(true);
					splashPic.setVisible(false);
					map1Button.grabFocus();
				} else {
					hostName.grabFocus();
				}
			} else if (source.equals(map1Button) || source.equals(map2Button)
					|| source.equals(map3Button) || source.equals(map4Button)) {
				if (source.equals(map1Button)) {
					map = new GeonosisMap();
				} else if (source.equals(map2Button)) {
					map = new KaminoPlatformMap();
				} else if (source.equals(map3Button)) {
					map = new EmperorThroneMap();
				} else if (source.equals(map4Button)) {
					map = new CarbonFreezeMap();
				}
				phase5Box.setVisible(false);
				selectionPanel.setVisible(true);
				game = new Game(numPlayers + numAI, original, map);
				client = new Client(hostName.getText(), "localHost");
				client.addObserver((Observer) selectionPanel);
				client.addObserver((Observer) gamePanel);
				client.update();
				selectionPanel.addComponentListener(new StartGameListener());
				selectionPanel.initFocus();
				new AISetup(game, numAI, numPlayers);
			} else if (source.equals(joinButton) || source.equals(joinText)) {
				//System.out.println("JOIN");
				if (joinName.getText().length() > 0) {
					String name = joinName.getText();
					while (name.contains("  ")) {
						name = name.replaceAll("  ", " ");
					}
					if (name.length() > 10) {
						name = name.substring(0, 7) + "...";
					}
					joinName.setText(name);
					client = new Client(joinName.getText(), joinText.getText());
					client.addObserver((Observer) selectionPanel);
					client.addObserver((Observer) gamePanel);
					client.update();
					phase4Box.setVisible(false);
					selectionPanel.setVisible(true);
					selectionPanel
							.addComponentListener(new StartGameListener());
					selectionPanel.initFocus();
					splashPic.setVisible(false);
				} else {
					joinName.grabFocus();
				}
			} else if (source.equals(joinName)) {
				joinText.grabFocus();
			}
			if (source.equals(legacyButton)) {
				original = true;
			} else if (source.equals(reimagineButton)) {
				original = false;
			} else if (source.equals(clientButton)) {
			} else if (source.equals(hostButton)) {
			} else if (source.equals(confirmButton)) {
			}
		}

	}

	private class BackListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (phase2Box.isVisible()) {
				phase1Box.setVisible(true);
				phase2Box.setVisible(false);
				hostButton.grabFocus();
			} else if (phase3Box.isVisible()) {
				phase2Box.setVisible(true);
				phase3Box.setVisible(false);
				legacyButton.grabFocus();
			} else if (phase4Box.isVisible()) {
				phase1Box.setVisible(true);
				phase4Box.setVisible(false);
				joinText.grabFocus();
			} else if (phase5Box.isVisible()) {
				phase3Box.setVisible(true);
				phase5Box.setVisible(false);
				splashPic.setVisible(true);
				playerUpButton.grabFocus();
			}
		}
	}

	private class MainMenuListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			phase1Box.setVisible(true);
			phase2Box.setVisible(false);
			phase3Box.setVisible(false);
			phase4Box.setVisible(false);
			phase5Box.setVisible(false);
			splashPic.setVisible(true);
			hostButton.grabFocus();
		}

	}

	private class CancelListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (game != null) {
				game.kill();
			}
			phase1Box.setVisible(true);
			selectionPanel.setVisible(false);
			splashPic.setVisible(true);
		}

	}

}