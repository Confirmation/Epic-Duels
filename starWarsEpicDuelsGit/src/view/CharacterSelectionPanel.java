package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import Model.Character;
import Model.Client;
import Model.Game;
import Model.Player;

public class CharacterSelectionPanel extends JPanel implements Observer {

	private static final long serialVersionUID = -2581468606159891332L;

	private JPanel selectCharacter, selectTeam, waiting;

	private JButton teamBack, waitBack, char1, char2, char3, char4, char5,
			char6, char7, char8, team1, team2, pos1, pos2, pos3, pos4,
			waitpos1, waitpos2, waitpos3, waitpos4, waitpos5, waitpos6, pos5,
			pos6;

	private JLabel chooseChar, waitingForPlayers, wait1Label, wait2Label,
			waitMap;

	private Client client;

	int numPlayers;

	Character[] group;

	private Font mylargeFont = new Font("Tahoma", Font.ITALIC, 35);

	public static String baseDir = System.getProperty("user.dir")
			+ File.separator + "Images" + File.separator;

	private final static ImageIcon emperorID = new ImageIcon(baseDir
			+ "emperorID.jpg");
	private final static ImageIcon vaderID = new ImageIcon(baseDir
			+ "vaderID.jpg");
	private final static ImageIcon maulID = new ImageIcon(baseDir
			+ "maulID.jpg");
	private final static ImageIcon jangoID = new ImageIcon(baseDir
			+ "jangoID.jpg");
	private final static ImageIcon yodaID = new ImageIcon(baseDir
			+ "yodaID.jpg");
	private final static ImageIcon lukeID = new ImageIcon(baseDir
			+ "lukeID.jpg");
	private final static ImageIcon obiwanID = new ImageIcon(baseDir
			+ "obiwanID.jpg");
	private final static ImageIcon hanID = new ImageIcon(baseDir + "hanID.jpg");

	private final static ImageIcon emperorIDMINI = new ImageIcon(baseDir
			+ "EmperorIDMINI.jpg");
	private final static ImageIcon vaderIDMINI = new ImageIcon(baseDir
			+ "VaderIDMINI.jpg");
	private final static ImageIcon maulIDMINI = new ImageIcon(baseDir
			+ "maulIDMINI.jpg");
	private final static ImageIcon jangoIDMINI = new ImageIcon(baseDir
			+ "jangoIDMINI.jpg");
	private final static ImageIcon yodaIDMINI = new ImageIcon(baseDir
			+ "yodaIDMINI.jpg");
	private final static ImageIcon lukeIDMINI = new ImageIcon(baseDir
			+ "lukeIDMINI.jpg");
	private final static ImageIcon obiwanIDMINI = new ImageIcon(baseDir
			+ "obiwanIDMINI.jpg");
	private final static ImageIcon hanIDMINI = new ImageIcon(baseDir
			+ "hanIDMINI.jpg");

	private ImageIcon carbonImage = new ImageIcon(baseDir + "carbonSmall.gif");
	private ImageIcon geonosisImage = new ImageIcon(baseDir + "genosisSmall.gif");
	private ImageIcon kaminoImage = new ImageIcon(baseDir + "kaminoSmall.gif");
	private ImageIcon throneImage = new ImageIcon(baseDir + "throneSmall.gif");

	private JPanel chat;
	private JTextArea chatLog;
	private JTextField inputBox;
	private JScrollPane logScroll;
	private JButton sendButton;

	public CharacterSelectionPanel() {

		setSize(1024, 600);
		setLocation(0, 0);
		setLayout(null);
		setBackground(Color.BLACK);

		selectCharacter = new JPanel();
		selectCharacter.setSize(1024, 500);
		selectCharacter.setLocation(0, 0);
		selectCharacter.setLayout(null);
		selectCharacter.setBackground(Color.BLACK);
		add(selectCharacter);

		chooseChar = new JLabel("Choose Your Character");
		chooseChar.setSize(600, 60);
		chooseChar.setLocation(10, 10);
		chooseChar.setBorder(null);
		chooseChar.setForeground(Color.YELLOW);
		chooseChar.setFont(mylargeFont);
		selectCharacter.add(chooseChar);

		char1 = new JButton(yodaID);
		char1.setSize(150, 150);
		char1.setLocation(50, 80);
		char1.setBorder(null);
		char1.addActionListener(new ForwardListener());
		char1.setBorder(new CompoundBorder(BorderFactory
				.createRaisedBevelBorder(), new EmptyBorder(10, 10, 10, 10)));
		// activate enter key for button
		char1.registerKeyboardAction(char1.getActionForKeyStroke(KeyStroke
				.getKeyStroke(KeyEvent.VK_SPACE, 0, false)), KeyStroke
				.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
				JComponent.WHEN_FOCUSED);
		char1.registerKeyboardAction(char1.getActionForKeyStroke(KeyStroke
				.getKeyStroke(KeyEvent.VK_SPACE, 0, true)), KeyStroke
				.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
				JComponent.WHEN_FOCUSED);
		selectCharacter.add(char1);

		char2 = new JButton(lukeID);
		char2.setSize(150, 150);
		char2.setLocation(260, 80);
		char2.setBorder(null);
		char2.addActionListener(new ForwardListener());
		char2.setBorder(new CompoundBorder(BorderFactory
				.createRaisedBevelBorder(), new EmptyBorder(10, 10, 10, 10)));
		// activate enter key for button
		char2.registerKeyboardAction(char2.getActionForKeyStroke(KeyStroke
				.getKeyStroke(KeyEvent.VK_SPACE, 0, false)), KeyStroke
				.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
				JComponent.WHEN_FOCUSED);
		char2.registerKeyboardAction(char2.getActionForKeyStroke(KeyStroke
				.getKeyStroke(KeyEvent.VK_SPACE, 0, true)), KeyStroke
				.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
				JComponent.WHEN_FOCUSED);
		selectCharacter.add(char2);

		char3 = new JButton(obiwanID);
		char3.setSize(150, 150);
		char3.setLocation(470, 80);
		char3.setBorder(null);
		char3.addActionListener(new ForwardListener());
		char3.setBorder(new CompoundBorder(BorderFactory
				.createRaisedBevelBorder(), new EmptyBorder(10, 10, 10, 10)));
		// activate enter key for button
		char3.registerKeyboardAction(char3.getActionForKeyStroke(KeyStroke
				.getKeyStroke(KeyEvent.VK_SPACE, 0, false)), KeyStroke
				.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
				JComponent.WHEN_FOCUSED);
		char3.registerKeyboardAction(char3.getActionForKeyStroke(KeyStroke
				.getKeyStroke(KeyEvent.VK_SPACE, 0, true)), KeyStroke
				.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
				JComponent.WHEN_FOCUSED);
		selectCharacter.add(char3);

		char4 = new JButton(hanID);
		char4.setSize(150, 150);
		char4.setLocation(680, 80);
		char4.setBorder(null);
		char4.addActionListener(new ForwardListener());
		char4.setBorder(new CompoundBorder(BorderFactory
				.createRaisedBevelBorder(), new EmptyBorder(10, 10, 10, 10)));
		// activate enter key for button
		char4.registerKeyboardAction(char4.getActionForKeyStroke(KeyStroke
				.getKeyStroke(KeyEvent.VK_SPACE, 0, false)), KeyStroke
				.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
				JComponent.WHEN_FOCUSED);
		char4.registerKeyboardAction(char4.getActionForKeyStroke(KeyStroke
				.getKeyStroke(KeyEvent.VK_SPACE, 0, true)), KeyStroke
				.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
				JComponent.WHEN_FOCUSED);
		selectCharacter.add(char4);

		char5 = new JButton(emperorID);
		char5.setSize(150, 150);
		char5.setLocation(50, 280);
		char5.setBorder(null);
		char5.addActionListener(new ForwardListener());
		char5.setBorder(new CompoundBorder(BorderFactory
				.createRaisedBevelBorder(), new EmptyBorder(10, 10, 10, 10)));
		// activate enter key for button
		char5.registerKeyboardAction(char5.getActionForKeyStroke(KeyStroke
				.getKeyStroke(KeyEvent.VK_SPACE, 0, false)), KeyStroke
				.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
				JComponent.WHEN_FOCUSED);
		char5.registerKeyboardAction(char5.getActionForKeyStroke(KeyStroke
				.getKeyStroke(KeyEvent.VK_SPACE, 0, true)), KeyStroke
				.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
				JComponent.WHEN_FOCUSED);
		selectCharacter.add(char5);

		char6 = new JButton(vaderID);
		char6.setSize(150, 150);
		char6.setLocation(260, 280);
		char6.setBorder(null);
		char6.addActionListener(new ForwardListener());
		char6.setBorder(new CompoundBorder(BorderFactory
				.createRaisedBevelBorder(), new EmptyBorder(10, 10, 10, 10)));
		// activate enter key for button
		char6.registerKeyboardAction(char6.getActionForKeyStroke(KeyStroke
				.getKeyStroke(KeyEvent.VK_SPACE, 0, false)), KeyStroke
				.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
				JComponent.WHEN_FOCUSED);
		char6.registerKeyboardAction(char6.getActionForKeyStroke(KeyStroke
				.getKeyStroke(KeyEvent.VK_SPACE, 0, true)), KeyStroke
				.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
				JComponent.WHEN_FOCUSED);
		selectCharacter.add(char6);

		char7 = new JButton(maulID);
		char7.setSize(150, 150);
		char7.setLocation(470, 280);
		char7.setBorder(null);
		char7.addActionListener(new ForwardListener());
		char7.setBorder(new CompoundBorder(BorderFactory
				.createRaisedBevelBorder(), new EmptyBorder(10, 10, 10, 10)));
		// activate enter key for button
		char7.registerKeyboardAction(char7.getActionForKeyStroke(KeyStroke
				.getKeyStroke(KeyEvent.VK_SPACE, 0, false)), KeyStroke
				.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
				JComponent.WHEN_FOCUSED);
		char7.registerKeyboardAction(char7.getActionForKeyStroke(KeyStroke
				.getKeyStroke(KeyEvent.VK_SPACE, 0, true)), KeyStroke
				.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
				JComponent.WHEN_FOCUSED);
		selectCharacter.add(char7);

		char8 = new JButton(jangoID);
		char8.setSize(150, 150);
		char8.setLocation(680, 280);
		char8.setBorder(null);
		char8.addActionListener(new ForwardListener());
		char8.setBorder(new CompoundBorder(BorderFactory
				.createRaisedBevelBorder(), new EmptyBorder(10, 10, 10, 10)));
		// activate enter key for button
		char8.registerKeyboardAction(char8.getActionForKeyStroke(KeyStroke
				.getKeyStroke(KeyEvent.VK_SPACE, 0, false)), KeyStroke
				.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
				JComponent.WHEN_FOCUSED);
		char8.registerKeyboardAction(char8.getActionForKeyStroke(KeyStroke
				.getKeyStroke(KeyEvent.VK_SPACE, 0, true)), KeyStroke
				.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
				JComponent.WHEN_FOCUSED);
		selectCharacter.add(char8);

		selectTeam = new JPanel();
		selectTeam.setSize(1024, 500);
		selectTeam.setLocation(0, 0);
		selectTeam.setLayout(null);
		selectTeam.setBackground(Color.BLACK);
		selectTeam.setVisible(false);
		add(selectTeam);

		teamBack = new JButton("Back");
		teamBack.setSize(200, 35);
		teamBack.setLocation(775, 420);
		teamBack.setBorder(null);
		teamBack.addActionListener(new BackListener());
		// activate enter key for button
		teamBack.registerKeyboardAction(teamBack
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, false)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
		teamBack.registerKeyboardAction(teamBack
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, true)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);
		selectTeam.add(teamBack);

		waiting = new JPanel();
		waiting.setSize(1024, 600);
		waiting.setLocation(0, 0);
		waiting.setLayout(null);
		waiting.setBackground(Color.BLACK);
		waiting.setVisible(false);
		add(waiting);

		pos1 = new JButton();
		pos1.setSize(150, 150);
		pos1.setLocation(50, 80);
		pos1.setBorder(null);
		pos1.setBorder(new CompoundBorder(BorderFactory
				.createRaisedBevelBorder(), new EmptyBorder(10, 10, 10, 10)));
		// activate enter key for button
		pos1.registerKeyboardAction(pos1.getActionForKeyStroke(KeyStroke
				.getKeyStroke(KeyEvent.VK_SPACE, 0, false)), KeyStroke
				.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
				JComponent.WHEN_FOCUSED);
		pos1.registerKeyboardAction(pos1.getActionForKeyStroke(KeyStroke
				.getKeyStroke(KeyEvent.VK_SPACE, 0, true)), KeyStroke
				.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
				JComponent.WHEN_FOCUSED);
		selectTeam.add(pos1);

		pos2 = new JButton();
		pos2.setSize(150, 150);
		pos2.setLocation(260, 80);
		pos2.setBorder(null);
		pos2.setBorder(new CompoundBorder(BorderFactory
				.createRaisedBevelBorder(), new EmptyBorder(10, 10, 10, 10)));
		// activate enter key for button
		pos2.registerKeyboardAction(pos2.getActionForKeyStroke(KeyStroke
				.getKeyStroke(KeyEvent.VK_SPACE, 0, false)), KeyStroke
				.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
				JComponent.WHEN_FOCUSED);
		pos2.registerKeyboardAction(pos2.getActionForKeyStroke(KeyStroke
				.getKeyStroke(KeyEvent.VK_SPACE, 0, true)), KeyStroke
				.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
				JComponent.WHEN_FOCUSED);
		selectTeam.add(pos2);

		pos3 = new JButton();
		pos3.setSize(150, 150);
		pos3.setLocation(470, 80);
		pos3.setBorder(null);
		pos3.setBorder(new CompoundBorder(BorderFactory
				.createRaisedBevelBorder(), new EmptyBorder(10, 10, 10, 10)));
		// activate enter key for button
		pos3.registerKeyboardAction(pos3.getActionForKeyStroke(KeyStroke
				.getKeyStroke(KeyEvent.VK_SPACE, 0, false)), KeyStroke
				.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
				JComponent.WHEN_FOCUSED);
		pos3.registerKeyboardAction(pos3.getActionForKeyStroke(KeyStroke
				.getKeyStroke(KeyEvent.VK_SPACE, 0, true)), KeyStroke
				.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
				JComponent.WHEN_FOCUSED);
		selectTeam.add(pos3);

		pos4 = new JButton();
		pos4.setSize(150, 150);
		pos4.setLocation(50, 280);
		pos4.setBorder(null);
		pos4.setBorder(new CompoundBorder(BorderFactory
				.createRaisedBevelBorder(), new EmptyBorder(10, 10, 10, 10)));
		// activate enter key for button
		pos4.registerKeyboardAction(pos4.getActionForKeyStroke(KeyStroke
				.getKeyStroke(KeyEvent.VK_SPACE, 0, false)), KeyStroke
				.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
				JComponent.WHEN_FOCUSED);
		pos4.registerKeyboardAction(pos4.getActionForKeyStroke(KeyStroke
				.getKeyStroke(KeyEvent.VK_SPACE, 0, true)), KeyStroke
				.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
				JComponent.WHEN_FOCUSED);
		selectTeam.add(pos4);

		pos5 = new JButton();
		pos5.setSize(150, 150);
		pos5.setLocation(260, 280);
		pos5.setBorder(null);
		pos5.setBorder(new CompoundBorder(BorderFactory
				.createRaisedBevelBorder(), new EmptyBorder(10, 10, 10, 10)));
		// activate enter key for button
		pos5.registerKeyboardAction(pos5.getActionForKeyStroke(KeyStroke
				.getKeyStroke(KeyEvent.VK_SPACE, 0, false)), KeyStroke
				.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
				JComponent.WHEN_FOCUSED);
		pos5.registerKeyboardAction(pos5.getActionForKeyStroke(KeyStroke
				.getKeyStroke(KeyEvent.VK_SPACE, 0, true)), KeyStroke
				.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
				JComponent.WHEN_FOCUSED);
		selectTeam.add(pos5);

		pos6 = new JButton();
		pos6.setSize(150, 150);
		pos6.setLocation(470, 280);
		pos6.setBorder(null);
		pos6.setBorder(new CompoundBorder(BorderFactory
				.createRaisedBevelBorder(), new EmptyBorder(10, 10, 10, 10)));
		// activate enter key for button
		pos6.registerKeyboardAction(char1.getActionForKeyStroke(KeyStroke
				.getKeyStroke(KeyEvent.VK_SPACE, 0, false)), KeyStroke
				.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
				JComponent.WHEN_FOCUSED);
		pos6.registerKeyboardAction(pos6.getActionForKeyStroke(KeyStroke
				.getKeyStroke(KeyEvent.VK_SPACE, 0, true)), KeyStroke
				.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
				JComponent.WHEN_FOCUSED);
		selectTeam.add(pos6);

		team1 = new JButton("Join Team 1");
		team1.setSize(150, 20);
		team1.setLocation(50, 145);
		team1.setBorder(null);
		team1.addActionListener(new ForwardListener());
		team1.setBorder(new CompoundBorder(BorderFactory
				.createRaisedBevelBorder(), new EmptyBorder(10, 10, 10, 10)));
		// activate enter key for button
		team1.registerKeyboardAction(team1.getActionForKeyStroke(KeyStroke
				.getKeyStroke(KeyEvent.VK_SPACE, 0, false)), KeyStroke
				.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
				JComponent.WHEN_FOCUSED);
		team1.registerKeyboardAction(team1.getActionForKeyStroke(KeyStroke
				.getKeyStroke(KeyEvent.VK_SPACE, 0, true)), KeyStroke
				.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
				JComponent.WHEN_FOCUSED);
		selectTeam.add(team1);

		team2 = new JButton("Join Team 2");
		team2.setSize(150, 20);
		team2.setLocation(50, 345);
		team2.setBorder(null);
		team2.addActionListener(new ForwardListener());
		team2.setBorder(new CompoundBorder(BorderFactory
				.createRaisedBevelBorder(), new EmptyBorder(10, 10, 10, 10)));
		// activate enter key for button
		team2.registerKeyboardAction(team2.getActionForKeyStroke(KeyStroke
				.getKeyStroke(KeyEvent.VK_SPACE, 0, false)), KeyStroke
				.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
				JComponent.WHEN_FOCUSED);
		team2.registerKeyboardAction(team2.getActionForKeyStroke(KeyStroke
				.getKeyStroke(KeyEvent.VK_SPACE, 0, true)), KeyStroke
				.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
				JComponent.WHEN_FOCUSED);
		selectTeam.add(team2);

		pos1.setVisible(false);
		pos2.setVisible(false);
		pos3.setVisible(false);
		pos4.setVisible(false);
		pos5.setVisible(false);
		pos6.setVisible(false);

		waitBack = new JButton("Back To Team Selection");
		waitBack.setSize(200, 35);
		waitBack.setLocation(775, 500);
		waitBack.setBorder(null);
		waitBack.addActionListener(new BackListener());
		// activate enter key for button
		waitBack.registerKeyboardAction(waitBack
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, false)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
		waitBack.registerKeyboardAction(waitBack
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, true)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);
		waiting.add(waitBack, null, 0);

		waitpos1 = new JButton();
		waitpos1.setSize(50, 50);
		waitpos1.setLocation(90, 60);
		waitpos1.setBackground(Color.DARK_GRAY);
		waitpos1.setBorder(null);
		waitpos1.setBorder(new CompoundBorder(BorderFactory
				.createRaisedBevelBorder(), new EmptyBorder(10, 10, 10, 10)));
		// activate enter key for button
		waitpos1.registerKeyboardAction(waitpos1
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, false)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
		waitpos1.registerKeyboardAction(waitpos1
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, true)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);
		waiting.add(waitpos1);

		waitpos2 = new JButton();
		waitpos2.setSize(50, 50);
		waitpos2.setLocation(160, 60);
		waitpos2.setBorder(null);
		waitpos2.setBackground(Color.DARK_GRAY);
		waitpos2.setBorder(new CompoundBorder(BorderFactory
				.createRaisedBevelBorder(), new EmptyBorder(10, 10, 10, 10)));
		// activate enter key for button
		waitpos2.registerKeyboardAction(waitpos2
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, false)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
		waitpos2.registerKeyboardAction(waitpos2
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, true)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);
		waiting.add(waitpos2);

		waitpos3 = new JButton();
		waitpos3.setSize(50, 50);
		waitpos3.setLocation(230, 60);
		waitpos3.setBorder(null);
		waitpos3.setBackground(Color.DARK_GRAY);
		waitpos3.setBorder(new CompoundBorder(BorderFactory
				.createRaisedBevelBorder(), new EmptyBorder(10, 10, 10, 10)));
		// activate enter key for button
		waitpos3.registerKeyboardAction(waitpos3
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, false)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
		waitpos3.registerKeyboardAction(waitpos3
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, true)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);
		waiting.add(waitpos3);

		waitpos4 = new JButton();
		waitpos4.setSize(50, 50);
		waitpos4.setLocation(90, 120);
		waitpos4.setBorder(null);
		waitpos4.setBackground(Color.DARK_GRAY);
		waitpos4.setBorder(new CompoundBorder(BorderFactory
				.createRaisedBevelBorder(), new EmptyBorder(10, 10, 10, 10)));
		// activate enter key for button
		waitpos4.registerKeyboardAction(waitpos4
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, false)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
		waitpos4.registerKeyboardAction(waitpos4
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, true)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);
		waiting.add(waitpos4);

		waitpos5 = new JButton();
		waitpos5.setSize(50, 50);
		waitpos5.setLocation(160, 120);
		waitpos5.setBorder(null);
		waitpos5.setBackground(Color.DARK_GRAY);
		waitpos5.setBorder(new CompoundBorder(BorderFactory
				.createRaisedBevelBorder(), new EmptyBorder(10, 10, 10, 10)));
		// activate enter key for button
		waitpos5.registerKeyboardAction(waitpos5
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, false)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
		waitpos5.registerKeyboardAction(waitpos5
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, true)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);
		waiting.add(waitpos5);

		waitpos6 = new JButton();
		waitpos6.setSize(50, 50);
		waitpos6.setLocation(230, 120);
		waitpos6.setBorder(null);
		waitpos6.setBackground(Color.DARK_GRAY);
		waitpos6.setBorder(new CompoundBorder(BorderFactory
				.createRaisedBevelBorder(), new EmptyBorder(10, 10, 10, 10)));
		// activate enter key for button
		waitpos6.registerKeyboardAction(waitpos6
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, false)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
		waitpos6.registerKeyboardAction(waitpos6
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, true)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);
		waiting.add(waitpos6);

		waitingForPlayers = new JLabel("Waiting For Other Players");
		waitingForPlayers.setSize(500, 45);
		waitingForPlayers.setLocation(10, 10);
		waitingForPlayers.setBorder(null);
		waitingForPlayers.setForeground(Color.YELLOW);
		waitingForPlayers.setFont(mylargeFont);
		waiting.add(waitingForPlayers);

		wait1Label = new JLabel("Team 1 :");
		wait1Label.setSize(500, 45);
		wait1Label.setLocation(20, 60);
		wait1Label.setBorder(null);
		wait1Label.setForeground(Color.YELLOW);
		waiting.add(wait1Label);

		wait2Label = new JLabel("Team 2 :");
		wait2Label.setSize(500, 45);
		wait2Label.setLocation(20, 120);
		wait2Label.setBorder(null);
		wait2Label.setForeground(Color.YELLOW);
		waiting.add(wait2Label);

		waitMap = new JLabel();
		waitMap.setSize(285, 200);
		waitMap.setLocation(715, 10);
		waitMap.setBorder(null);
		waiting.add(waitMap);

		// TODO: chat size and location...
		chat = new JPanel();
		chat.setSize(980, 380);
		chat.setLocation(20, 220);
		chat.setBackground(Color.BLACK);
		chat.setLayout(null);
		waiting.add(chat);

		chatLog = new JTextArea();
		chatLog.setSize(980, 260);
		chatLog.setLocation(0, 0);
		chatLog.setEditable(false);
		chatLog.setVisible(true);
		chat.add(chatLog);

		logScroll = new JScrollPane(chatLog);
		logScroll.setSize(980, 260);
		logScroll.setLocation(0, 0);
		logScroll.setVisible(true);
		chat.add(logScroll);

		inputBox = new JTextField();
		inputBox.setSize(490, 35);
		inputBox.setLocation(0, 265);
		inputBox.addActionListener(new SendListener());
		inputBox.setVisible(true);
		chat.add(inputBox);

		sendButton = new JButton();
		sendButton.setText("Send");
		sendButton.setSize(75, 35);
		sendButton.setLocation(505, 265);
		sendButton.addActionListener(new SendListener());
		sendButton.setVisible(true);
		// activate enter key for button
		sendButton.registerKeyboardAction(sendButton
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, false)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
		sendButton.registerKeyboardAction(sendButton
				.getActionForKeyStroke(KeyStroke.getKeyStroke(
						KeyEvent.VK_SPACE, 0, true)), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);
		chat.add(sendButton);

		waitpos2.setVisible(false);
		waitpos3.setVisible(false);
		waitpos5.setVisible(false);
		waitpos6.setVisible(false);

		char1.grabFocus();
	}

	private class SendListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			client.chat(inputBox.getText());
			inputBox.setText("");
		}

	}

	private class BackListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Object source = arg0.getSource();
			if (source.equals(teamBack)) {
				selectTeam.setVisible(false);
				selectCharacter.setVisible(true);
				client.releaseCharacter(group);
				char1.grabFocus();
			} else if (source.equals(waitBack)) {
				selectTeam.setVisible(true);
				waiting.setVisible(false);
				client.releaseTeam();
				team1.grabFocus();
			}
			while (client.isReleasingCharacters() || client.isReleasingTeam()) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (client.releasingCharacterFailed()) {
					// TODO: undo release character
					break;
				}
				if (client.teamReleaseFailed()) {
					// TODO: undo team release
					break;
				}
			}
		}

	}

	private class ForwardListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Object source = e.getSource();
			if (source.equals(team1)) {
				client.chooseTeam(0);
				selectTeam.setVisible(false);
				waiting.setVisible(true);
				inputBox.grabFocus();
			} else if (source.equals(team2)) {
				client.chooseTeam(1);
				selectTeam.setVisible(false);
				waiting.setVisible(true);
				inputBox.grabFocus();
			} else {
				selectTeam.setVisible(true);
				selectCharacter.setVisible(false);

				ArrayList<Character[]> character = Game.getCharacterList();
				// Yoda
				if (source.equals(char1)) {
					group = character.get(2);
					client.chooseCharacter(group);
				}
				// Luke
				else if (source.equals(char2)) {
					group = character.get(1);
					client.chooseCharacter(group);
				}
				// Obi-Wan
				else if (source.equals(char3)) {
					group = character.get(0);
					client.chooseCharacter(group);
				}
				// Han
				else if (source.equals(char4)) {
					group = character.get(3);
					client.chooseCharacter(group);
				}
				// Emperor
				else if (source.equals(char5)) {
					group = character.get(7);
					client.chooseCharacter(group);
				}
				// Vader
				else if (source.equals(char6)) {
					group = character.get(4);
					client.chooseCharacter(group);
				}
				// Maul
				else if (source.equals(char7)) {
					group = character.get(5);
					client.chooseCharacter(group);
				}
				// Jango
				else if (source.equals(char8)) {
					group = character.get(6);
					client.chooseCharacter(group);
				}
				team1.grabFocus();
			}
			while (client.isChoosingCharacters() || client.isChoosingTeam()) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException eo) {
					eo.printStackTrace();
				}
				if (client.choosingCharacterFailed()) {
					// TODO: undo character choice
					break;
				}
				if (client.teamSelectionFailed()) {
					// TODO: undo team selection
					break;
				}
			}
		}

	}

	public void setMapImage(String theMap) {
		if (theMap.equals("carbon"))
			waitMap.setIcon(carbonImage);
		else if (theMap.equals("geonosis"))
			waitMap.setIcon(geonosisImage);
		else if (theMap.equals("kamino"))
			waitMap.setIcon(kaminoImage);
		else if (theMap.equals("throne"))
			waitMap.setIcon(throneImage);
	}

	@Override
	public void update(Observable notifier, Object argument) {
		if (argument != null) {
			if (argument instanceof String) {
				chatLog.append((String) argument);
				chatLog.setCaretPosition(chatLog.getDocument().getLength());
			}
		}
		client = (Client) notifier;
		setMapImage(client.getMap().getName());
		int waitingPlayers = 0;
		ArrayList<Character[]> list = client.getUnavailableCharacters();
		// System.out.println(list);
		int charSize = list.size();
		for (int i = 0; i < charSize; i++) {
			// System.out.println(list.get(i)[0].getCharacterName());
			if (list.get(i)[0].getCharacterName().equals("Emperor Palpatine")) {
				char5.setEnabled(false);
				char5.setOpaque(true);
			} else if (list.get(i)[0].getCharacterName().equals("Darth Vader")) {
				char6.setEnabled(false);
				char6.setOpaque(true);
			} else if (list.get(i)[0].getCharacterName().equals("Darth Maul")) {
				char7.setEnabled(false);
				char7.setOpaque(true);
			} else if (list.get(i)[0].getCharacterName().equals("Jango Fett")) {
				char8.setEnabled(false);
				char8.setOpaque(true);
			} else if (list.get(i)[0].getCharacterName().equals("Yoda")) {
				char1.setEnabled(false);
				char1.setOpaque(true);
			} else if (list.get(i)[0].getCharacterName().equals(
					"Luke Skywalker")) {
				char2.setEnabled(false);
				char2.setOpaque(true);
			} else if (list.get(i)[0].getCharacterName()
					.equals("ObiWan Kenobi")) {
				char3.setEnabled(false);
				char3.setOpaque(true);
			} else if (list.get(i)[0].getCharacterName().equals("Han Solo")) {
				char4.setEnabled(false);
				char4.setOpaque(true);
			}

		}
		list = client.getAvailableCharacters();
		charSize = list.size();
		for (int i = 0; i < charSize; i++) {
			// System.out.println(list.get(i)[0].getCharacterName());
			if (list.get(i)[0].getCharacterName().equals("Emperor Palpatine")) {
				char5.setEnabled(true);
				char5.setOpaque(false);
			} else if (list.get(i)[0].getCharacterName().equals("Darth Vader")) {
				char6.setEnabled(true);
				char6.setOpaque(false);
			} else if (list.get(i)[0].getCharacterName().equals("Darth Maul")) {
				char7.setEnabled(true);
				char7.setOpaque(false);
			} else if (list.get(i)[0].getCharacterName().equals("Jango Fett")) {
				char8.setEnabled(true);
				char8.setOpaque(false);
			} else if (list.get(i)[0].getCharacterName().equals("Yoda")) {
				char1.setEnabled(true);
				char1.setOpaque(false);
			} else if (list.get(i)[0].getCharacterName().equals(
					"Luke Skywalker")) {
				char2.setEnabled(true);
				char2.setOpaque(false);
			} else if (list.get(i)[0].getCharacterName()
					.equals("ObiWan Kenobi")) {
				char3.setEnabled(true);
				char3.setOpaque(false);
			} else if (list.get(i)[0].getCharacterName().equals("Han Solo")) {
				char4.setEnabled(true);
				char4.setOpaque(false);
			}
		}
		ArrayList<ArrayList<Player>> biggestList = client.getTeams();
		ArrayList<Player> teamList = biggestList.get(0);
		charSize = teamList.size();
		waitingPlayers = charSize;
		pos1.setVisible(false);
		team1.setVisible(true);
		numPlayers = client.getNumPlayers();

		if (numPlayers >= 4) {
			waitpos2.setVisible(true);
			waitpos5.setVisible(true);
		}
		if (numPlayers == 6) {
			waitpos3.setVisible(true);
			waitpos6.setVisible(true);
		}

		team1.setLocation(50 + (charSize) * 210, team1.getY());
		waitpos1.setIcon(null);
		waitpos2.setIcon(null);
		waitpos3.setIcon(null);
		if (charSize > 0) {
			pos1.setVisible(true);
			if (teamList.get(0).getMajorCharacterName().equals("Yoda")) {
				pos1.setIcon(yodaID);
				waitpos1.setIcon(yodaIDMINI);
			} else if (teamList.get(0).getMajorCharacterName()
					.equals("Luke Skywalker")) {
				pos1.setIcon(lukeID);
				waitpos1.setIcon(lukeIDMINI);
			} else if (teamList.get(0).getMajorCharacterName()
					.equals("ObiWan Kenobi")) {
				pos1.setIcon(obiwanID);
				waitpos1.setIcon(obiwanIDMINI);
			} else if (teamList.get(0).getMajorCharacterName()
					.equals("Han Solo")) {
				pos1.setIcon(hanID);
				waitpos1.setIcon(hanIDMINI);
			} else if (teamList.get(0).getMajorCharacterName()
					.equals("Emperor Palpatine")) {
				pos1.setIcon(emperorID);
				waitpos1.setIcon(emperorIDMINI);
			} else if (teamList.get(0).getMajorCharacterName()
					.equals("Darth Vader")) {
				pos1.setIcon(vaderID);
				waitpos1.setIcon(vaderIDMINI);
			} else if (teamList.get(0).getMajorCharacterName()
					.equals("Darth Maul")) {
				pos1.setIcon(maulID);
				waitpos1.setIcon(maulIDMINI);
			} else if (teamList.get(0).getMajorCharacterName()
					.equals("Jango Fett")) {
				pos1.setIcon(jangoID);
				waitpos1.setIcon(jangoIDMINI);
			}
			pos2.setVisible(false);
			pos3.setVisible(false);
			// System.out.println(numPlayers);
			if (numPlayers > 2) {
				if (charSize > 1) {
					if (teamList.get(1).getMajorCharacterName().equals("Yoda")) {
						pos2.setIcon(yodaID);
						waitpos2.setIcon(yodaIDMINI);
					} else if (teamList.get(1).getMajorCharacterName()
							.equals("Luke Skywalker")) {
						pos2.setIcon(lukeID);
						waitpos2.setIcon(lukeIDMINI);
					} else if (teamList.get(1).getMajorCharacterName()
							.equals("ObiWan Kenobi")) {
						pos2.setIcon(obiwanID);
						waitpos2.setIcon(obiwanIDMINI);
					} else if (teamList.get(1).getMajorCharacterName()
							.equals("Han Solo")) {
						pos2.setIcon(hanID);
						waitpos2.setIcon(hanIDMINI);
					} else if (teamList.get(1).getMajorCharacterName()
							.equals("Emperor Palpatine")) {
						pos2.setIcon(emperorID);
						waitpos2.setIcon(emperorIDMINI);
					} else if (teamList.get(1).getMajorCharacterName()
							.equals("Darth Vader")) {
						pos2.setIcon(vaderID);
						waitpos2.setIcon(vaderIDMINI);
					} else if (teamList.get(1).getMajorCharacterName()
							.equals("Darth Maul")) {
						pos2.setIcon(maulID);
						waitpos2.setIcon(maulIDMINI);
					} else if (teamList.get(1).getMajorCharacterName()
							.equals("Jango Fett")) {
						pos2.setIcon(jangoID);
						waitpos2.setIcon(jangoIDMINI);
					}
					pos2.setVisible(true);
					pos3.setVisible(false);
					if (numPlayers > 4) {
						if (charSize > 2) {
							if (teamList.get(2).getMajorCharacterName()
									.equals("Yoda")) {
								pos3.setIcon(yodaID);
								waitpos3.setIcon(yodaIDMINI);
							} else if (teamList.get(2).getMajorCharacterName()
									.equals("Luke Skywalker")) {
								pos3.setIcon(lukeID);
								waitpos3.setIcon(lukeIDMINI);
							} else if (teamList.get(2).getMajorCharacterName()
									.equals("ObiWan Kenobi")) {
								pos3.setIcon(obiwanID);
								waitpos3.setIcon(obiwanIDMINI);
							} else if (teamList.get(2).getMajorCharacterName()
									.equals("Han Solo")) {
								pos3.setIcon(hanID);
								waitpos3.setIcon(hanIDMINI);
							} else if (teamList.get(2).getMajorCharacterName()
									.equals("Emperor Palpatine")) {
								pos3.setIcon(emperorID);
								waitpos3.setIcon(emperorIDMINI);
							} else if (teamList.get(2).getMajorCharacterName()
									.equals("Darth Vader")) {
								pos3.setIcon(vaderID);
								waitpos3.setIcon(vaderIDMINI);
							} else if (teamList.get(2).getMajorCharacterName()
									.equals("Darth Maul")) {
								pos3.setIcon(maulID);
								waitpos3.setIcon(maulIDMINI);
							} else if (teamList.get(2).getMajorCharacterName()
									.equals("Jango Fett")) {
								pos3.setIcon(jangoID);
								waitpos3.setIcon(jangoIDMINI);
							}
							pos3.setVisible(true);
							team1.setVisible(false);
							// System.out.println("hELLO WORLD");
						}
					} else
						team1.setVisible(false);
				}
			} else
				team1.setVisible(false);
		}
		teamList = biggestList.get(1);
		charSize = teamList.size();
		team2.setLocation(50 + charSize * 210, team2.getY());
		pos4.setVisible(false);
		team2.setVisible(true);
		waitpos4.setIcon(null);
		waitpos5.setIcon(null);
		waitpos6.setIcon(null);
		waitingPlayers += charSize;
		if (charSize > 0) {
			if (teamList.get(0).getMajorCharacterName().equals("Yoda")) {
				pos4.setIcon(yodaID);
				waitpos4.setIcon(yodaIDMINI);
			} else if (teamList.get(0).getMajorCharacterName()
					.equals("Luke Skywalker")) {
				pos4.setIcon(lukeID);
				waitpos4.setIcon(lukeIDMINI);
			} else if (teamList.get(0).getMajorCharacterName()
					.equals("ObiWan Kenobi")) {
				pos4.setIcon(obiwanID);
				waitpos4.setIcon(obiwanIDMINI);
			} else if (teamList.get(0).getMajorCharacterName()
					.equals("Han Solo")) {
				pos4.setIcon(hanID);
				waitpos4.setIcon(hanIDMINI);
			} else if (teamList.get(0).getMajorCharacterName()
					.equals("Emperor Palpatine")) {
				pos4.setIcon(emperorID);
				waitpos4.setIcon(emperorIDMINI);
			} else if (teamList.get(0).getMajorCharacterName()
					.equals("Darth Vader")) {
				pos4.setIcon(vaderID);
				waitpos4.setIcon(vaderIDMINI);
			} else if (teamList.get(0).getMajorCharacterName()
					.equals("Darth Maul")) {
				pos4.setIcon(maulID);
				waitpos4.setIcon(maulIDMINI);
			} else if (teamList.get(0).getMajorCharacterName()
					.equals("Jango Fett")) {
				pos4.setIcon(jangoID);
				waitpos4.setIcon(jangoIDMINI);
			}
			pos4.setVisible(true);
			pos5.setVisible(false);
			pos6.setVisible(false);
			if (numPlayers > 2) {
				if (charSize > 1) {
					if (teamList.get(1).getMajorCharacterName().equals("Yoda")) {
						pos5.setIcon(yodaID);
						waitpos5.setIcon(yodaIDMINI);
					} else if (teamList.get(1).getMajorCharacterName()
							.equals("Luke Skywalker")) {
						pos5.setIcon(lukeID);
						waitpos5.setIcon(lukeIDMINI);
					} else if (teamList.get(1).getMajorCharacterName()
							.equals("ObiWan Kenobi")) {
						pos5.setIcon(obiwanID);
						waitpos5.setIcon(obiwanIDMINI);
					} else if (teamList.get(1).getMajorCharacterName()
							.equals("Han Solo")) {
						pos5.setIcon(hanID);
						waitpos5.setIcon(hanIDMINI);
					} else if (teamList.get(1).getMajorCharacterName()
							.equals("Emperor Palpatine")) {
						pos5.setIcon(emperorID);
						waitpos5.setIcon(emperorIDMINI);
					} else if (teamList.get(1).getMajorCharacterName()
							.equals("Darth Vader")) {
						pos5.setIcon(vaderID);
						waitpos5.setIcon(vaderIDMINI);
					} else if (teamList.get(1).getMajorCharacterName()
							.equals("Darth Maul")) {
						pos5.setIcon(maulID);
						waitpos5.setIcon(maulIDMINI);
					} else if (teamList.get(1).getMajorCharacterName()
							.equals("Jango Fett")) {
						pos5.setIcon(jangoID);
						waitpos5.setIcon(jangoIDMINI);
					}
					pos5.setVisible(true);
					pos6.setVisible(false);
					if (numPlayers > 4) {
						if (charSize > 2) {
							if (teamList.get(2).getMajorCharacterName()
									.equals("Yoda")) {
								pos6.setIcon(yodaID);
								waitpos6.setIcon(yodaIDMINI);
							} else if (teamList.get(2).getMajorCharacterName()
									.equals("Luke Skywalker")) {
								pos6.setIcon(lukeID);
								waitpos6.setIcon(lukeIDMINI);
							} else if (teamList.get(2).getMajorCharacterName()
									.equals("ObiWan Kenobi")) {
								pos6.setIcon(obiwanID);
								waitpos6.setIcon(obiwanIDMINI);
							} else if (teamList.get(2).getMajorCharacterName()
									.equals("Han Solo")) {
								pos6.setIcon(hanID);
								waitpos6.setIcon(hanIDMINI);
							} else if (teamList.get(2).getMajorCharacterName()
									.equals("Emperor Palpatine")) {
								pos6.setIcon(emperorID);
								waitpos6.setIcon(emperorIDMINI);
							} else if (teamList.get(2).getMajorCharacterName()
									.equals("Darth Vader")) {
								pos6.setIcon(vaderID);
								waitpos6.setIcon(vaderIDMINI);
							} else if (teamList.get(2).getMajorCharacterName()
									.equals("Darth Maul")) {
								pos6.setIcon(maulID);
								waitpos6.setIcon(maulIDMINI);
							} else if (teamList.get(2).getMajorCharacterName()
									.equals("Jango Fett")) {
								pos6.setIcon(jangoID);
								waitpos6.setIcon(jangoIDMINI);
							}
							pos6.setVisible(true);
							team2.setVisible(false);
						}
					} else
						team2.setVisible(false);
				}
			} else
				team2.setVisible(false);
		}

		if (waitingPlayers == numPlayers && this.isVisible()) {
			waitBack.setEnabled(false);
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					CharacterSelectionPanel.this.setVisible(false);
				}
			}, 3000);
		}

	}

	public void initFocus() {
		char1.grabFocus();
	}

}
