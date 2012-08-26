package view;

import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import mapPackage.MainMap;
import mapPackage.PossiblePath;

import deckPackage.*;

import Model.*;
import Model.Character;

public class GamePanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1471963444400253613L;

	int currRoll = 0;
	Point charToMove;
	ArrayList<Point> moveToList = new ArrayList<Point>();

	private Client client;

	public static String baseDir = System.getProperty("user.dir")
			+ File.separator + "Images" + File.separator;

	private SpriteSheet sprite;
	private BigSpriteSheet bigSprite;

	private Deck deck;
	private int numDraws;
	private boolean discarding;
	private boolean defending;
	private boolean attacking;
	private int revealSize;

	private JPanel[] handCard = new JPanel[15];

	private JPanel[] revealCard = new JPanel[15];

	private JButton[][] characterPosition = new JButton[7][10];
	private JLabel[][] fogSquares = new JLabel[7][10];

	private JPanel playerStatus, hoverStatus, defendCard, attackCard, chat,
			specialCard, revealHand;

	private JTextArea chatLog;
	private JTextField inputBox;
	private JScrollPane logScroll;
	private JButton sendButton, rollButton;

	private JLabel gameMap, gameStatus, playerPic, hoverPic, playerName,
			hoverName, playerNameBack, hoverNameBack, playerHealth,
			hoverHealth, playerHealthBack, hoverHealthBack, playerMinor1Pic,
			playerMinor2Pic, playerMinor1Health, playerMinor1HealthBack,
			playerMinor2Health, playerMinor2HealthBack, hoverMinorHealth,
			hoverMinorHealthBack, handImagePic, handCount;

	private JButton skip, draw;

	private JLabel hoverTeam;

	private ImageIcon unactivatedAction = new ImageIcon(baseDir
			+ "statusBarUnactivated.jpg");
	private ImageIcon rollAction = new ImageIcon(baseDir + "statusBarRoll.jpg");
	private ImageIcon moveAction = new ImageIcon(baseDir + "statusBarMove.jpg");
	private ImageIcon action1Action = new ImageIcon(baseDir
			+ "statusBarAction1.jpg");
	private ImageIcon action2Action = new ImageIcon(baseDir
			+ "statusBarAction2.jpg");

	private ImageIcon darthMaulBack = new ImageIcon(baseDir + "maulBack.png");
	private ImageIcon darthVaderBack = new ImageIcon(baseDir + "vaderBack.png");
	private ImageIcon emperorBack = new ImageIcon(baseDir + "emperorBack.png");
	private ImageIcon hanBack = new ImageIcon(baseDir + "hanBack.png");
	private ImageIcon jangoBack = new ImageIcon(baseDir + "jangoBack.png");
	private ImageIcon lukeBack = new ImageIcon(baseDir + "lukeBack.png");
	private ImageIcon obiBack = new ImageIcon(baseDir + "obiBack.png");
	private ImageIcon yodaBack = new ImageIcon(baseDir + "yodaBack.png");

	private ImageIcon fog = new ImageIcon(baseDir + "fog.gif");

	private ImageIcon carbonImage = new ImageIcon(baseDir + "carbon.gif");
	private ImageIcon geonosisImage = new ImageIcon(baseDir + "genosis.gif");
	private ImageIcon kaminoImage = new ImageIcon(baseDir + "kamino.gif");
	private ImageIcon throneImage = new ImageIcon(baseDir + "throne.gif");

	private ImageIcon emperorIDMID = new ImageIcon(baseDir + "EmperorIDMID.jpg");
	private ImageIcon vaderIDMID = new ImageIcon(baseDir + "VaderIDMID.jpg");
	private ImageIcon maulIDMID = new ImageIcon(baseDir + "maulIDMID.jpg");
	private ImageIcon jangoIDMID = new ImageIcon(baseDir + "jangoIDMID.jpg");
	private ImageIcon yodaIDMID = new ImageIcon(baseDir + "yodaIDMID.jpg");
	private ImageIcon lukeIDMID = new ImageIcon(baseDir + "lukeIDMID.jpg");
	private ImageIcon obiwanIDMID = new ImageIcon(baseDir + "obiwanIDMID.jpg");
	private ImageIcon hanIDMID = new ImageIcon(baseDir + "hanIDMID.jpg");

	private ImageIcon emperorMINOR = new ImageIcon(baseDir + "EmperorMINOR.jpg");
	private ImageIcon vaderMINOR = new ImageIcon(baseDir + "VaderMINOR.jpg");
	private ImageIcon maulMINOR = new ImageIcon(baseDir + "maulMINOR.jpg");
	private ImageIcon jangoMINOR = new ImageIcon(baseDir + "jangoMINOR.jpg");
	private ImageIcon yodaMINOR = new ImageIcon(baseDir + "yodaMINOR.jpg");
	private ImageIcon lukeMINOR = new ImageIcon(baseDir + "lukeMINOR.jpg");
	private ImageIcon obiwanMINOR = new ImageIcon(baseDir + "obiwanMINOR.jpg");
	private ImageIcon hanMINOR = new ImageIcon(baseDir + "hanMINOR.jpg");

	private ImageIcon emperorIcon = new ImageIcon(baseDir + "emperorIcon.png");
	private ImageIcon vaderIcon = new ImageIcon(baseDir + "darthvaderIcon.png");
	private ImageIcon maulIcon = new ImageIcon(baseDir + "maulIcon.png");
	private ImageIcon jangoIcon = new ImageIcon(baseDir + "jangoIcon.png");
	private ImageIcon yodaIcon = new ImageIcon(baseDir + "yodaIcon.png");
	private ImageIcon lukeIcon = new ImageIcon(baseDir + "lukeIcon.png");
	private ImageIcon obiwanIcon = new ImageIcon(baseDir + "obiwanIcon.png");
	private ImageIcon hanIcon = new ImageIcon(baseDir + "hanIcon.png");

	private ImageIcon emperorMinorIcon = new ImageIcon(baseDir
			+ "emperorMinorIcon.png");
	private ImageIcon vaderMinorIcon = new ImageIcon(baseDir
			+ "vaderMinorIcon.png");
	private ImageIcon maulMinorIcon = new ImageIcon(baseDir
			+ "maulMinorIcon.gif");
	private ImageIcon jangoMinorIcon = new ImageIcon(baseDir
			+ "jangoMinorIcon.png");
	private ImageIcon yodaMinorIcon = new ImageIcon(baseDir
			+ "yodaMinorIcon.png");
	private ImageIcon lukeMinorIcon = new ImageIcon(baseDir
			+ "lukeMinorIcon.png");
	private ImageIcon obiwanMinorIcon = new ImageIcon(baseDir
			+ "yodaMinorIcon.png");
	private ImageIcon hanMinorIcon = new ImageIcon(baseDir + "hanMinorIcon.png");

	private ImageIcon team1 = new ImageIcon(baseDir + "team1.png");
	private ImageIcon team2 = new ImageIcon(baseDir + "team2.png");

	private ImageIcon handImage = new ImageIcon(baseDir + "handImage.png");

	private ImageIcon targetImage = new ImageIcon(baseDir + "Target.png");

	private Font characterFont = new Font("Arial", Font.BOLD, 15);
	private Font healthFont = new Font("Arial", Font.BOLD, 30);
	private Font handFont = new Font("Arial", Font.BOLD, 20);

	private int amountToMove;
	private int cardIndex;
	private boolean freeStart = true;

	private boolean moveMajor;
	private boolean specialMove = false;

	public void setMapImage(String theMap) {
		if (theMap.equals("carbon"))
			gameMap.setIcon(carbonImage);
		else if (theMap.equals("geonosis"))
			gameMap.setIcon(geonosisImage);
		else if (theMap.equals("kamino"))
			gameMap.setIcon(kaminoImage);
		else if (theMap.equals("throne"))
			gameMap.setIcon(throneImage);
	}

	public GamePanel() {

		numDraws = 0;
		setSize(1024, 600);
		setLocation(0, 0);
		setLayout(null);
		setBackground(Color.BLACK);

		draw = new JButton();
		draw.setSize(91, 130);
		draw.setLocation(5, 430);
		draw.setSelected(false);
		draw.setFocusable(false);
		draw.addActionListener(new DrawListener());
		add(draw);
		repaint();

		gameMap = new JLabel();
		gameMap.setSize(600, 420);
		gameMap.setLocation(100, 0);
		gameMap.setBackground(Color.RED);
		add(gameMap);

		playerStatus = new JPanel();
		playerStatus.setSize(90, 205);
		playerStatus.setLocation(5, 5);
		playerStatus.setLayout(null);
		playerStatus.setBackground(Color.BLACK);
		add(playerStatus);

		playerName = new JLabel();
		playerName.setSize(200, 20);
		playerName.setLocation(0, -2);
		playerName.setForeground(Color.YELLOW);
		playerName.setFont(characterFont);
		playerStatus.add(playerName);

		playerNameBack = new JLabel();
		playerNameBack.setSize(200, 20);
		playerNameBack.setLocation(2, -1);
		playerNameBack.setForeground(Color.BLACK);
		playerNameBack.setFont(characterFont);
		playerStatus.add(playerNameBack);

		playerHealth = new JLabel("20");
		playerHealth.setSize(200, 30);
		playerHealth.setLocation(55, 63);
		playerHealth.setForeground(Color.YELLOW);
		playerHealth.setFont(healthFont);
		playerStatus.add(playerHealth);

		playerMinor1Health = new JLabel("20");
		playerMinor1Health.setSize(200, 30);
		playerMinor1Health.setLocation(56, 123);
		playerMinor1Health.setForeground(Color.YELLOW);
		playerMinor1Health.setFont(healthFont);
		playerStatus.add(playerMinor1Health);

		playerMinor2Health = new JLabel();
		playerMinor2Health.setSize(200, 30);
		playerMinor2Health.setLocation(56, 178);
		playerMinor2Health.setForeground(Color.YELLOW);
		playerMinor2Health.setFont(healthFont);
		playerStatus.add(playerMinor2Health);

		playerHealthBack = new JLabel("20");
		playerHealthBack.setSize(200, 30);
		playerHealthBack.setLocation(57, 64);
		playerHealthBack.setForeground(Color.BLACK);
		playerHealthBack.setFont(healthFont);
		playerStatus.add(playerHealthBack);

		playerMinor1HealthBack = new JLabel("5");
		playerMinor1HealthBack.setSize(200, 30);
		playerMinor1HealthBack.setLocation(58, 124);
		playerMinor1HealthBack.setForeground(Color.BLACK);
		playerMinor1HealthBack.setFont(healthFont);
		playerStatus.add(playerMinor1HealthBack);

		playerMinor2HealthBack = new JLabel("5");
		playerMinor2HealthBack.setSize(200, 30);
		playerMinor2HealthBack.setLocation(58, 179);
		playerMinor2HealthBack.setForeground(Color.BLACK);
		playerMinor2HealthBack.setFont(healthFont);
		playerStatus.add(playerMinor2HealthBack);

		playerPic = new JLabel();
		playerPic.setSize(90, 90);
		playerPic.setLocation(0, 0);
		playerStatus.add(playerPic);

		playerMinor1Pic = new JLabel();
		playerMinor1Pic.setSize(60, 60);
		playerMinor1Pic.setLocation(40, 95);
		playerStatus.add(playerMinor1Pic);

		playerMinor2Pic = new JLabel();
		playerMinor2Pic.setSize(60, 60);
		playerMinor2Pic.setLocation(40, 150);
		playerStatus.add(playerMinor2Pic);

		hoverStatus = new JPanel();
		hoverStatus.setSize(90, 205);
		hoverStatus.setLocation(5, 215);
		hoverStatus.setLayout(null);
		hoverStatus.setBackground(Color.BLACK);
		add(hoverStatus);

		hoverName = new JLabel();
		hoverName.setSize(200, 20);
		hoverName.setLocation(0, 17);
		hoverName.setForeground(Color.YELLOW);
		hoverName.setFont(characterFont);
		hoverStatus.add(hoverName);

		hoverNameBack = new JLabel();
		hoverNameBack.setSize(200, 20);
		hoverNameBack.setLocation(2, 18);
		hoverNameBack.setForeground(Color.BLACK);
		hoverNameBack.setFont(characterFont);
		hoverStatus.add(hoverNameBack);

		hoverHealth = new JLabel();
		hoverHealth.setSize(200, 30);
		hoverHealth.setLocation(55, 83);
		hoverHealth.setForeground(Color.YELLOW);
		hoverHealth.setFont(healthFont);
		hoverStatus.add(hoverHealth);

		hoverMinorHealth = new JLabel();
		hoverMinorHealth.setSize(200, 30);
		hoverMinorHealth.setLocation(56, 58);
		hoverMinorHealth.setForeground(Color.YELLOW);
		hoverMinorHealth.setFont(healthFont);
		hoverStatus.add(hoverMinorHealth);

		hoverMinorHealthBack = new JLabel();
		hoverMinorHealthBack.setSize(200, 30);
		hoverMinorHealthBack.setLocation(58, 59);
		hoverMinorHealthBack.setForeground(Color.BLACK);
		hoverMinorHealthBack.setFont(healthFont);
		hoverStatus.add(hoverMinorHealthBack);

		hoverHealthBack = new JLabel();
		hoverHealthBack.setSize(200, 30);
		hoverHealthBack.setLocation(57, 84);
		hoverHealthBack.setForeground(Color.BLACK);
		hoverHealthBack.setFont(healthFont);
		hoverStatus.add(hoverHealthBack);

		handCount = new JLabel();
		handCount.setSize(60, 60);
		handCount.setForeground(Color.BLACK);
		handCount.setLocation(37, 103);
		handCount.setFont(handFont);
		hoverStatus.add(handCount);

		handImagePic = new JLabel(handImage);
		handImagePic.setSize(60, 60);
		handImagePic.setLocation(10, 100);
		handImagePic.setVisible(false);
		hoverStatus.add(handImagePic);

		hoverTeam = new JLabel();
		hoverTeam.setSize(90, 5);
		hoverTeam.setLocation(0, 165);
		hoverStatus.add(hoverTeam);
		hoverTeam.setVisible(false);

		hoverPic = new JLabel();
		hoverPic.setSize(90, 90);
		hoverPic.setLocation(0, 20);
		hoverStatus.add(hoverPic);

		defendCard = new JPanel();
		defendCard.setSize(210, 300);
		defendCard.setLocation(780, 10);
		defendCard.setBackground(Color.WHITE);
		add(defendCard);

		attackCard = new JPanel();
		attackCard.setSize(210, 300);
		attackCard.setLocation(720, 10);
		attackCard.setBackground(Color.GRAY);
		add(attackCard);

		gameStatus = new JLabel(unactivatedAction);
		gameStatus.setSize(280, 30);
		gameStatus.setLocation(720, 330);
		gameStatus.setBackground(Color.CYAN);
		add(gameStatus);

		skip = new JButton("SKIP");
		skip.setSize(100, 15);
		skip.setLocation(810, 365);
		skip.setBackground(Color.BLACK);
		skip.setSelected(false);
		skip.setFocusable(false);
		skip.setEnabled(true);
		skip.addActionListener(new SkipListener());
		add(skip);

		chat = new JPanel();
		chat.setSize(280, 180);
		chat.setLocation(720, 390);
		chat.setBackground(Color.DARK_GRAY);
		chat.setLayout(null);
		add(chat);

		chatLog = new JTextArea();
		chatLog.setSize(280, 150);
		chatLog.setLocation(0, 0);
		chatLog.setEditable(false);
		chatLog.setVisible(true);
		chat.add(chatLog);

		logScroll = new JScrollPane(chatLog);
		logScroll.setSize(280, 150);
		logScroll.setLocation(0, 0);
		logScroll.setVisible(true);
		chat.add(logScroll);

		inputBox = new JTextField();
		inputBox.setSize(190, 25);
		inputBox.setLocation(0, 155);
		inputBox.addActionListener(new SendListener());
		inputBox.setVisible(true);
		chat.add(inputBox);

		sendButton = new JButton();
		sendButton.setText("Send");
		sendButton.setSize(75, 25);
		sendButton.setLocation(205, 155);
		sendButton.addActionListener(new SendListener());
		sendButton.setVisible(true);
		chat.add(sendButton);

		rollButton = new JButton("Roll");
		rollButton.setBackground(Color.WHITE);
		rollButton.setSize(120, 60);
		rollButton.setLocation(580, 360);
		rollButton.addActionListener(new RollListener());
		rollButton.setVisible(true);
		rollButton.setEnabled(false);
		rollButton.setBackground(Color.BLACK);
		add(rollButton, null, 0);

		specialCard = new ImagePanel();
		specialCard.setSize(210, 300);
		specialCard.setLocation(755, 10);
		specialCard.setBackground(Color.GREEN);
		add(specialCard);

		attackCard.setVisible(false);
		defendCard.setVisible(false);
		specialCard.setVisible(false);
	}

	private class SendListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (inputBox.getText().length() > 0) {
				client.chat(inputBox.getText());
				inputBox.setText("");
			}
		}

	}

	private class DrawListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			client.draw(1);
			removeTarget();
			enableAllPositions();
			draw.setEnabled(false);
			attacking = false;
		}

	}

	private class RollListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			currRoll = client.roll();
			while (currRoll < client.getLevel()) {
				currRoll = client.roll();
				if (currRoll == 6) {
					break;
				}
			}
			enableAllPositions();
			if (currRoll <= 3) {
				amountToMove = currRoll + 2;
			} else {
				amountToMove = currRoll - 2;
			}
			rollButton.setEnabled(false);
			rollButton.setBackground(Color.BLACK);
		}

	}

	private class CardImage extends JPanel {

		private static final long serialVersionUID = -2472609992483940050L;
		private BufferedImage currentCardImage;
		private BufferedImage bigCardImage;

		public CardImage(Cards card) {
			sprite = new SpriteSheet(deck);
			bigSprite = new BigSpriteSheet(deck);
			currentCardImage = sprite.getCardImage(card);
			bigCardImage = bigSprite.getCardImage(card);
		}

		public CardImage(Cards card, Deck deck2) {
			sprite = new SpriteSheet(deck2);
			bigSprite = new BigSpriteSheet(deck2);
			currentCardImage = sprite.getCardImage(card);
			bigCardImage = bigSprite.getCardImage(card);
		}

		public void paintComponent(Graphics g) {

			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;

			// If there is no current card, then don't attempt to draw it
			if (currentCardImage != null) {
				g2.drawImage(currentCardImage, 0, 0, null);
				setVisible(true);
			} else {
				setVisible(false);
			}
		}

		public Image getCardImage() {
			return bigCardImage;
		}

		public void setImage(Cards card) {
			if (card != null) {
				sprite = new SpriteSheet(deck);
				bigSprite = new BigSpriteSheet(deck);
				currentCardImage = sprite.getCardImage(card);
				bigCardImage = bigSprite.getCardImage(card);
			} else {
				currentCardImage = null;
				bigCardImage = null;
			}
		}

	}

	private class ImagePanel extends JPanel {

		private static final long serialVersionUID = -7237735988750234440L;
		private Image currentCardImage;

		public void setIcon(Image image) {
			currentCardImage = image;
		}

		public void paintComponent(Graphics g) {

			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;

			// If there is no current card, then don't attempt to draw it
			if (currentCardImage != null)
				g2.drawImage(currentCardImage, 0, 0, null);

		}

	}

	private void removeTarget() {
		for (int i = 0; i < characterPosition.length; i++)
			for (int j = 0; j < characterPosition[0].length; j++) {
				if (characterPosition[i][j].getIcon() == targetImage) {
					characterPosition[i][j].setIcon(null);
					characterPosition[i][j].setContentAreaFilled(false);
				}
			}
	}

	private void addTarget() {
		int[][] theMap = client.getMap().getMap();
		for (int i = 0; i < characterPosition.length; i++)
			for (int j = 0; j < characterPosition[0].length; j++) {
				if (theMap[i][j] == 6) {
					characterPosition[i][j].setContentAreaFilled(false);
					characterPosition[i][j].setIcon(targetImage);
					if (fogSquares[i][j] != null)
						fogSquares[i][j].setVisible(false);
				}
			}
	}

	private void enableAllPositions() {
		for (int i = 0; i < characterPosition.length; i++)
			for (int j = 0; j < characterPosition[0].length; j++) {
				characterPosition[i][j].setEnabled(true);
			}
	}

	private class CharacterListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			Object source = arg0.getSource();
			if (((JButton) source).isEnabled()) {
				if (client.getActionNum() == 1 || specialMove) {
					int j = (((JButton) source).getX() - 100) / 60;
					int i = ((JButton) source).getY() / 60;
					Point location = new Point(i, j);
					MainMap map = client.getMap();
					if ((client.ownsCharacter(map.getNumAt(location)))) {
						if (!moveToList.contains(location)) {
							charToMove = new Point(i, j);
							removeTarget();
							map.displayPossiblePaths(location, amountToMove);
							addTarget();
							charToMove = location;
						}
					} else if (((JButton) source).getIcon() == targetImage
							&& (client.getNumToMove() > 0 || specialMove)) {
						if (moveMajor && specialMove) {
							charToMove = client.getMajorCharacter()
									.getCharacterLocation();
							// System.out.println("Major is moving");
						} else if (specialMove) {
							charToMove = client.getMinorCharacter(0)
									.getCharacterLocation();
						}

						if (!specialMove) {
							moveToList.add(location);
						}

						specialMove = false;
						Point moveTo = location;
						client.move(charToMove, moveTo, 1);
						removeTarget();
					}
				} else if (client.getActionNum() > 1 && attacking) {
					if (client.canAttack(((JButton) source).getY() / 60,
							(((JButton) source).getX() - 100) / 60)) {
						int j = (((JButton) source).getX() - 100) / 60;
						int i = ((JButton) source).getY() / 60;
						Point location = new Point(i, j);
						MainMap map = client.getMap();
						int character = map.getNumAt(location);
						ArrayList<Point> attackable = client
								.getAttackableLocations();
						int index = attackable.indexOf(location);
						int attacker = -1;
						for (int k = index - 1; k >= 0; k--) {
							if (client.ownsCharacter(attackable.get(k))) {
								attacker = map.getNumAt(attackable.get(k));
								break;
							}
						}
						if (client.playAttackCard(cardIndex, character,
								attacker)) {
							enableAllPositions();
							if (client.isAttacking()) {
								skip.setEnabled(true);
								draw.setEnabled(false);
							}
							attacking = false;
						}
					}
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			Object source = arg0.getSource();
			int y = (((JButton) source).getX() - 100) / 60;
			int x = ((JButton) source).getY() / 60;
			if ((((JButton) source).getIcon() != null || (((JButton) source)
					.getIcon() != targetImage))
					&& (fogSquares[x][y] != null && !fogSquares[x][y]
							.isVisible()) || fogSquares[x][y] == null) {
				String hoverCharacterName = "";
				int majorCharacter = 0;
				Icon targetIcon = ((JButton) source).getIcon();

				if (targetIcon == hanIcon) {
					hoverPic.setIcon(hanIDMID);
					hoverCharacterName = "Han Solo";
					majorCharacter = 1;
				} else if (targetIcon == hanMinorIcon) {
					hoverPic.setIcon(hanMINOR);
					hoverCharacterName = "Han Solo";
				} else if (targetIcon == lukeIcon) {
					hoverPic.setIcon(lukeIDMID);
					hoverCharacterName = "Luke Skywalker";
					majorCharacter = 1;
				} else if (targetIcon == lukeMinorIcon) {
					hoverPic.setIcon(lukeMINOR);
					hoverCharacterName = "Luke Skywalker";
				} else if (targetIcon == vaderIcon) {
					hoverPic.setIcon(vaderIDMID);
					hoverCharacterName = "Darth Vader";
					majorCharacter = 1;
				} else if (targetIcon == vaderMinorIcon) {
					hoverPic.setIcon(vaderMINOR);
					hoverCharacterName = "Darth Vader";
				} else if (targetIcon == yodaIcon) {
					hoverPic.setIcon(yodaIDMID);
					hoverCharacterName = "Yoda";
					majorCharacter = 1;
				} else if (targetIcon == yodaMinorIcon) {
					hoverPic.setIcon(yodaMINOR);
					hoverCharacterName = "Yoda";
				} else if (targetIcon == obiwanIcon) {
					hoverPic.setIcon(obiwanIDMID);
					hoverCharacterName = "ObiWan Kenobi";
					majorCharacter = 1;
				} else if (targetIcon == obiwanMinorIcon) {
					hoverPic.setIcon(obiwanMINOR);
					hoverCharacterName = "ObiWan Kenobi";
				} else if (targetIcon == emperorIcon) {
					hoverPic.setIcon(emperorIDMID);
					hoverCharacterName = "Emperor Palpatine";
					majorCharacter = 1;
				} else if (targetIcon == emperorMinorIcon) {
					hoverPic.setIcon(emperorMINOR);
					hoverCharacterName = "Emperor Palpatine";
				} else if (targetIcon == maulIcon) {
					hoverPic.setIcon(maulIDMID);
					hoverCharacterName = "Darth Maul";
					majorCharacter = 1;
				} else if (targetIcon == maulMinorIcon) {
					hoverPic.setIcon(maulMINOR);
					hoverCharacterName = "Darth Maul";
				} else if (targetIcon == jangoIcon) {
					hoverPic.setIcon(jangoIDMID);
					hoverCharacterName = "Jango Fett";
					majorCharacter = 1;
				} else if (targetIcon == jangoMinorIcon) {
					hoverPic.setIcon(jangoMINOR);
					hoverCharacterName = "Jango Fett";
				}

				ArrayList<ArrayList<Player>> teams = client.getTeams();
				ArrayList<Player> teamOne = teams.get(0);
				ArrayList<Player> teamTwo = teams.get(1);

				for (int i = 0; i < teamOne.size(); i++) {
					Player hoverPlayer = teamOne.get(i);
					if (hoverPlayer.getMajorCharacterName().equals(
							hoverCharacterName)) {

						hoverTeam.setIcon(team1);
						hoverTeam.setVisible(true);

						handCount.setText(hoverPlayer.getHandSize() + "");
						handImagePic.setVisible(true);

						hoverName.setText(hoverPlayer.getName());
						hoverNameBack.setText(hoverPlayer.getName());

						if (majorCharacter == 1) {
							hoverHealth.setText(hoverPlayer.getMajorHealth()
									+ "");
							hoverHealthBack.setText(hoverPlayer
									.getMajorHealth() + "");
						} else {
							int minorChar = 0;
							if (hoverPlayer.getMinorCharacter(0)
									.getCharacterLocation().x == x
									&& hoverPlayer.getMinorCharacter(0)
											.getCharacterLocation().y == y)
								minorChar = 0;
							else
								minorChar = 1;
							if (hoverPlayer.getNumMinors() == 1)
								minorChar = 0;
							if (hoverPlayer.getMinorCharacter(minorChar)
									.getCharacterHealth() <= 9) {
								hoverMinorHealth.setLocation(72,
										hoverMinorHealth.getY());
								hoverMinorHealthBack.setLocation(74,
										hoverMinorHealthBack.getY());
							} else {
								hoverMinorHealth.setLocation(55,
										hoverMinorHealth.getY());
								hoverMinorHealthBack.setLocation(57,
										hoverMinorHealthBack.getY());
							}

							if (hoverPlayer.getMinorHealth(minorChar) > 0) {
								hoverMinorHealth.setText(hoverPlayer
										.getMinorHealth(minorChar) + "");
								hoverMinorHealthBack.setText(hoverPlayer
										.getMinorHealth(minorChar) + "");
							} else {
								hoverMinorHealth.setText(0 + "");
								hoverMinorHealthBack.setText(0 + "");
							}
						}
					}
				}

				for (int i = 0; i < teamTwo.size(); i++) {
					Player hoverPlayer = teamTwo.get(i);
					if (hoverPlayer.getMajorCharacterName().equals(
							hoverCharacterName)) {

						hoverTeam.setIcon(team2);
						hoverTeam.setVisible(true);

						handCount.setText(hoverPlayer.getHandSize() + "");
						handImagePic.setVisible(true);

						hoverName.setText(hoverPlayer.getName());
						hoverNameBack.setText(hoverPlayer.getName());

						if (majorCharacter == 1) {
							hoverHealth.setText(hoverPlayer.getMajorHealth()
									+ "");
							hoverHealthBack.setText(hoverPlayer
									.getMajorHealth() + "");
						} else {
							int minorChar = 0;
							if (hoverPlayer.getMinorCharacter(0)
									.getCharacterLocation().x == x
									&& hoverPlayer.getMinorCharacter(0)
											.getCharacterLocation().y == y)
								minorChar = 0;
							else
								minorChar = 1;
							if (hoverPlayer.getNumMinors() == 1)
								minorChar = 0;
							if (hoverPlayer.getMinorCharacter(minorChar)
									.getCharacterHealth() <= 9) {
								hoverMinorHealth.setLocation(72,
										hoverMinorHealth.getY());
								hoverMinorHealthBack.setLocation(74,
										hoverMinorHealthBack.getY());
							} else {
								hoverMinorHealth.setLocation(55,
										hoverMinorHealth.getY());
								hoverMinorHealthBack.setLocation(57,
										hoverMinorHealthBack.getY());
							}
							if (hoverPlayer.getMinorHealth(minorChar) > 0) {
								hoverMinorHealth.setText(hoverPlayer
										.getMinorHealth(minorChar) + "");
								hoverMinorHealthBack.setText(hoverPlayer
										.getMinorHealth(minorChar) + "");
							} else {
								hoverMinorHealth.setText(0 + "");
								hoverMinorHealthBack.setText(0 + "");
							}
						}
					}
				}

			}
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			hoverName.setText("");
			hoverNameBack.setText("");
			hoverHealth.setText("");
			hoverHealthBack.setText("");
			hoverMinorHealth.setText("");
			hoverMinorHealthBack.setText("");
			handCount.setText("");
			handImagePic.setVisible(false);
			hoverPic.setIcon(null);
			hoverTeam.setVisible(false);
		}

		@Override
		public void mousePressed(MouseEvent arg0) {

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}

	}

	private class HoverListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			if (client.getActionNum() > 1 || discarding || defending) {
				CardImage source = (CardImage) arg0.getSource();
				int index = -1;
				for (int i = 0; i < client.getHandSize(); i++) {
					if (source.getX() == (((i - (i % 5)) / 5) * 20 + 105) + 120
							* (i % 5)) {
						if (source.getY() == ((i - (i % 5)) / 5) * 15 + 425) {
							index = i;
							break;
						}
					}
				}
				if (index >= 0) {
					cardIndex = index;
					if (discarding) {
						client.discardCard(cardIndex);
						while (client.isDiscarding()) {
							try {
								Thread.sleep(1);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						client.endTurn();
					} else if (defending) {
						defending = !client.playDefendCard(cardIndex);
					} else {
						client.playCard(cardIndex);
					}
				} else {
					for (int i = 0; i < revealSize; i++) {
						if (source.getX() == (((i - (i % 5)) / 5) * 20 + 105)
								+ 120 * (i % 5)) {
							if (source.getY() == ((i - (i % 5)) / 5) * 15 + 110) {
								index = i;
								break;
							}
						}
					}
					client.discardReveal(index);
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			CardImage source = (CardImage) arg0.getSource();
			if (source.getCardImage() != null)
				((ImagePanel) specialCard).setIcon(source.getCardImage());
			specialCard.setVisible(true);

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			specialCard.setVisible(false);
		}

		@Override
		public void mousePressed(MouseEvent arg0) {

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {

		}

	}

	private class SkipListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			if (source.equals(skip)) {

				if (defending) {
					defending = !client.playDefendCard(-1);
				} else if (specialMove) {
					if (moveMajor) {
						charToMove = client.getMajorCharacter()
								.getCharacterLocation();
						// System.out.println("Major is moving");
					} else {
						charToMove = client.getMinorCharacter(0)
								.getCharacterLocation();
					}
					specialMove = false;
					client.move(charToMove, charToMove, 1);
					removeTarget();
				} else if (client.isTurn() && !attacking) {
					client.skipAction();
					enableAllPositions();
					skip.setEnabled(true);
				}

			}
		}

	}

	private void getBackCardName(String deckName) {

		if (deckName.equals("Darth Maul")) {
			draw.setIcon(darthMaulBack);
		} else if (deckName.equals("Darth Vader")) {
			draw.setIcon(darthVaderBack);
		} else if (deckName.equals("Emperor")) {
			draw.setIcon(emperorBack);
		} else if (deckName.equals("Han")) {
			draw.setIcon(hanBack);
		} else if (deckName.equals("Jango")) {
			draw.setIcon(jangoBack);
		} else if (deckName.equals("Luke")) {
			draw.setIcon(lukeBack);
		} else if (deckName.equals("ObiWan")) {
			draw.setIcon(obiBack);
		} else if (deckName.equals("Yoda")) {
			draw.setIcon(yodaBack);
		}
	}

	private void freeMemory() {
		if (freeStart && this.isVisible()) {
			freeStart = false;
			Icon keep = this.gameMap.getIcon();
			if (keep != this.kaminoImage)
				kaminoImage = null;
			if (keep != this.geonosisImage)
				geonosisImage = null;
			if (keep != this.carbonImage)
				carbonImage = null;
			if (keep != this.throneImage)
				throneImage = null;

			ArrayList<Character[]> teams = client.getAvailableCharacters();
			for (int i = 0; i < teams.size(); i++) {
				if ((teams.get(i))[0].getCharacterName().equals(
						"Luke Skywalker")) {
					lukeMinorIcon = null;
					lukeIcon = null;
					lukeMINOR = null;
					lukeIDMID = null;
					lukeBack = null;
				} else if ((teams.get(i))[0].getCharacterName().equals("Yoda")) {
					yodaMinorIcon = null;
					yodaIcon = null;
					yodaMINOR = null;
					yodaIDMID = null;
					yodaBack = null;
				} else if ((teams.get(i))[0].getCharacterName().equals(
						"ObiWan Kenobi")) {
					obiwanMinorIcon = null;
					obiwanIcon = null;
					obiwanMINOR = null;
					obiwanIDMID = null;
					obiBack = null;
				} else if ((teams.get(i))[0].getCharacterName().equals(
						"Han Solo")) {
					hanMinorIcon = null;
					hanIcon = null;
					hanMINOR = null;
					hanIDMID = null;
					hanBack = null;
				} else if ((teams.get(i))[0].getCharacterName().equals(
						"Darth Vader")) {
					vaderMinorIcon = null;
					vaderIcon = null;
					vaderMINOR = null;
					vaderIDMID = null;
					darthVaderBack = null;
				} else if ((teams.get(i))[0].getCharacterName().equals(
						"Darth Maul")) {
					maulMinorIcon = null;
					maulIcon = null;
					maulMINOR = null;
					maulIDMID = null;
					darthMaulBack = null;
				} else if ((teams.get(i))[0].getCharacterName().equals(
						"Jango Fett")) {
					jangoMinorIcon = null;
					jangoIcon = null;
					jangoMINOR = null;
					jangoIDMID = null;
					jangoBack = null;
				} else if ((teams.get(i))[0].getCharacterName().equals(
						"Emperor Palpatine")) {
					emperorMinorIcon = null;
					emperorIcon = null;
					emperorMINOR = null;
					emperorIDMID = null;
					emperorBack = null;
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable notifier, Object argument) {

		freeMemory();

		if (argument != null) {
			for (int i = 0; i < revealCard.length; i++)
				if (revealCard[i] != null)
					revealCard[i].setVisible(false);
			if (revealHand != null)
				revealHand.setVisible(false);
			if (argument instanceof String) {
				String message = (String) argument;
				chatLog.append(message);
				chatLog.setCaretPosition(chatLog.getDocument().getLength());
			} else if (argument instanceof ArrayList<?>) {
				revealHand.setVisible(true);
				setShownCards((ArrayList<Cards>) argument);
				revealSize = ((ArrayList<Cards>) argument).size();
				System.out.println(argument.toString());
			} else if (argument instanceof Boolean) {
				specialMove = true;
				if ((Boolean) argument) {
					System.out.println("boolean for major is true");
					moveMajor = true;
				} else {
					System.out.println("boolean for major is false");
					moveMajor = false;
				}
				addTarget();
				skip.setEnabled(true);
			} else if (argument.equals(Action.ATTACK)) {
				attacking = true;
				ArrayList<Point> attackable = client.getAttackableLocations();
				if (client.attackingSelf()) {
					for (int i = 0; i < characterPosition.length; i++)
						for (int j = 0; j < characterPosition[0].length; j++) {
							if (!attackable.contains(new Point(i, j))
									|| client.isOwnedMajor(new Point(i, j))) {
								characterPosition[i][j].setEnabled(false);
							} else {
								characterPosition[i][j].setEnabled(true);
							}
						}
				} else {
					for (int i = 0; i < characterPosition.length; i++)
						for (int j = 0; j < characterPosition[0].length; j++) {
							if (!attackable.contains(new Point(i, j))
									|| client.ownsCharacter(new Point(i, j))) {
								characterPosition[i][j].setEnabled(false);
							} else {
								characterPosition[i][j].setEnabled(true);
							}
						}
				}
			} else if (argument.equals(Action.DEFEND)) {
				defending = true;
				skip.setEnabled(true);
			}
		}
		client = (Client) notifier;
		if (!attacking) {
			setMapImage(client.getMap().getName());
			deck = client.getDeck();
			if (deck != null) {
				getBackCardName(deck.getName());
			}
			numDraws = client.getHandSize();
			setDrawnCards();
			setCharacterPosition();
			if (revealHand == null) {
				revealHand = new JPanel();
				revealHand.setSize(600, 210);
				revealHand.setLocation(100, 100);
				revealHand.setBorder(BorderFactory.createLineBorder(Color.blue,
						2));
				revealHand.setBackground(Color.BLACK);
				add(revealHand, null, 2);
				revealHand.setVisible(false);
			}
			if (client.isTurn()) {
				// gameStatus.setIcon(rollAction);
				skip.setEnabled(true);
				rollButton.setEnabled(true);
				rollButton.setBackground(Color.WHITE);
			} else {
				gameStatus.setIcon(unactivatedAction);
				skip.setEnabled(true);
				draw.setEnabled(false);
				rollButton.setEnabled(false);
				rollButton.setBackground(Color.BLACK);
			}
			placeCharacters();
			if (client.isFogOfWar()) {
				placeFog();
			}
			playerStatusDisplay();
			setGameStatusIcon(client.getActionNum());
			discarding = client.needsToDiscard();
			hideEmptySpaces();
		}
	}

	private void placeFog() {
		MainMap theMap = client.getMap();
		int[][] realMap = theMap.getMap();
		int team = client.getTeamChoice();
		for (int j = 0; j < realMap[0].length; j++)
			for (int i = 0; i < realMap.length; i++) {
				if ((realMap[i][j] / 10) % 2 == team || realMap[i][j] < 10) {
					if (fogSquares[i][j] == null) {
						fogSquares[i][j] = new JLabel(fog);
						fogSquares[i][j].setSize(60, 60);
						fogSquares[i][j].setOpaque(false);
						int y = i * 60;
						int x = j * 60 + 100;
						fogSquares[i][j].setLocation(x, y);
						fogSquares[i][j].setVisible(true);
						add(fogSquares[i][j], null, 1);
					} else {
						fogSquares[i][j].setVisible(true);
					}
				}
			}
		for (int j = 0; j < realMap[0].length; j++)
			for (int i = 0; i < realMap.length; i++) {
				if ((realMap[i][j] / 10) % 2 != team && realMap[i][j] >= 10) {
					removeAdjacentFog(i, j);
				}
			}
	}

	public void removeAdjacentFog(int i, int j) {
		for (int k = i - 1; k <= i + 1; k++) {
			for (int l = j - 1; l <= j + 1; l++) {
				if (k >= 0 && k < 7 && l >= 0 && l < 10) {
					if (fogSquares[k][l] != null) {
						fogSquares[k][l].setVisible(false);
					}
				}
			}
		}
	}

	private void placeCharacters() {
		MainMap theMap = client.getMap();
		int[][] realMap = theMap.getMap();
		ArrayList<ArrayList<Player>> teams = client.getTeams();
		ArrayList<Player> teamOne = teams.get(0);
		ArrayList<Player> teamTwo = teams.get(1);
		Player thePlayer;
		int currPlayer;
		for (int j = 0; j < realMap[0].length; j++)
			for (int i = 0; i < realMap.length; i++) {
				characterPosition[i][j].setContentAreaFilled(false);
				if (realMap[i][j] > 9) {

					for (int k = 0; k < teamOne.size(); k++) {
						thePlayer = teamOne.get(k);
						currPlayer = thePlayer.getPlayerNumber();
						if ((currPlayer / 10) == (realMap[i][j] / 10)) {
							// characterPosition[i][j].setContentAreaFilled(true);
							if ((realMap[i][j] % 10) == 0) {
								if (thePlayer.getMajorCharacterName().equals(
										"Yoda")) {
									characterPosition[i][j].setIcon(yodaIcon);
								} else if (thePlayer.getMajorCharacterName()
										.equals("Luke Skywalker")) {
									characterPosition[i][j].setIcon(lukeIcon);
								} else if (thePlayer.getMajorCharacterName()
										.equals("ObiWan Kenobi")) {
									characterPosition[i][j].setIcon(obiwanIcon);
								} else if (thePlayer.getMajorCharacterName()
										.equals("Han Solo")) {
									characterPosition[i][j].setIcon(hanIcon);
								} else if (thePlayer.getMajorCharacterName()
										.equals("Emperor Palpatine")) {
									characterPosition[i][j]
											.setIcon(emperorIcon);
								} else if (thePlayer.getMajorCharacterName()
										.equals("Darth Vader")) {
									characterPosition[i][j].setIcon(vaderIcon);
								} else if (thePlayer.getMajorCharacterName()
										.equals("Darth Maul")) {
									characterPosition[i][j].setIcon(maulIcon);
								} else if (thePlayer.getMajorCharacterName()
										.equals("Jango Fett")) {
									characterPosition[i][j].setIcon(jangoIcon);
								}
							} else {
								if (thePlayer.getMajorCharacterName().equals(
										"Yoda")) {
									characterPosition[i][j]
											.setIcon(yodaMinorIcon);
								} else if (thePlayer.getMajorCharacterName()
										.equals("Luke Skywalker")) {
									characterPosition[i][j]
											.setIcon(lukeMinorIcon);
								} else if (thePlayer.getMajorCharacterName()
										.equals("ObiWan Kenobi")) {
									characterPosition[i][j]
											.setIcon(obiwanMinorIcon);
								} else if (thePlayer.getMajorCharacterName()
										.equals("Han Solo")) {
									characterPosition[i][j]
											.setIcon(hanMinorIcon);
								} else if (thePlayer.getMajorCharacterName()
										.equals("Emperor Palpatine")) {
									characterPosition[i][j]
											.setIcon(emperorMinorIcon);
								} else if (thePlayer.getMajorCharacterName()
										.equals("Darth Vader")) {
									characterPosition[i][j]
											.setIcon(vaderMinorIcon);
								} else if (thePlayer.getMajorCharacterName()
										.equals("Darth Maul")) {
									characterPosition[i][j]
											.setIcon(maulMinorIcon);
								} else if (thePlayer.getMajorCharacterName()
										.equals("Jango Fett")) {
									characterPosition[i][j]
											.setIcon(jangoMinorIcon);
								}
							}

						}
					}
					for (int k = 0; k < teamTwo.size(); k++) {
						thePlayer = teamTwo.get(k);
						currPlayer = thePlayer.getPlayerNumber();
						if ((currPlayer / 10) == (realMap[i][j] / 10)) {
							// characterPosition[i][j].setContentAreaFilled(true);
							if ((realMap[i][j] % 10) == 0) {
								if (thePlayer.getMajorCharacterName().equals(
										"Yoda")) {
									characterPosition[i][j].setIcon(yodaIcon);
								} else if (thePlayer.getMajorCharacterName()
										.equals("Luke Skywalker")) {
									characterPosition[i][j].setIcon(lukeIcon);
								} else if (thePlayer.getMajorCharacterName()
										.equals("ObiWan Kenobi")) {
									characterPosition[i][j].setIcon(obiwanIcon);
								} else if (thePlayer.getMajorCharacterName()
										.equals("Han Solo")) {
									characterPosition[i][j].setIcon(hanIcon);
								} else if (thePlayer.getMajorCharacterName()
										.equals("Emperor Palpatine")) {
									characterPosition[i][j]
											.setIcon(emperorIcon);
								} else if (thePlayer.getMajorCharacterName()
										.equals("Darth Vader")) {
									characterPosition[i][j].setIcon(vaderIcon);
								} else if (thePlayer.getMajorCharacterName()
										.equals("Darth Maul")) {
									characterPosition[i][j].setIcon(maulIcon);
								} else if (thePlayer.getMajorCharacterName()
										.equals("Jango Fett")) {
									characterPosition[i][j].setIcon(jangoIcon);
								}
							} else {
								if (thePlayer.getMajorCharacterName().equals(
										"Yoda")) {
									characterPosition[i][j]
											.setIcon(yodaMinorIcon);
								} else if (thePlayer.getMajorCharacterName()
										.equals("Luke Skywalker")) {
									characterPosition[i][j]
											.setIcon(lukeMinorIcon);
								} else if (thePlayer.getMajorCharacterName()
										.equals("ObiWan Kenobi")) {
									characterPosition[i][j]
											.setIcon(obiwanMinorIcon);
								} else if (thePlayer.getMajorCharacterName()
										.equals("Han Solo")) {
									characterPosition[i][j]
											.setIcon(hanMinorIcon);
								} else if (thePlayer.getMajorCharacterName()
										.equals("Emperor Palpatine")) {
									characterPosition[i][j]
											.setIcon(emperorMinorIcon);
								} else if (thePlayer.getMajorCharacterName()
										.equals("Darth Vader")) {
									characterPosition[i][j]
											.setIcon(vaderMinorIcon);
								} else if (thePlayer.getMajorCharacterName()
										.equals("Darth Maul")) {
									characterPosition[i][j]
											.setIcon(maulMinorIcon);
								} else if (thePlayer.getMajorCharacterName()
										.equals("Jango Fett")) {
									characterPosition[i][j]
											.setIcon(jangoMinorIcon);
								}
							}
						}
					}

				}
			}
	}

	private void playerStatusDisplay() {
		if (this.isVisible()) {
			int twoMinors = 0;
			if (client.getMajorCharacter().getCharacterName().equals("Yoda")) {
				playerPic.setIcon(yodaIDMID);
				playerMinor1Pic.setIcon(yodaMINOR);
				playerMinor2Pic.setIcon(yodaMINOR);
				twoMinors = 1;
			} else if (client.getMajorCharacter().getCharacterName()
					.equals("Han Solo")) {
				playerPic.setIcon(hanIDMID);
				playerMinor1Pic.setIcon(hanMINOR);
			} else if (client.getMajorCharacter().getCharacterName()
					.equals("Darth Vader")) {
				playerPic.setIcon(vaderIDMID);
				playerMinor1Pic.setIcon(vaderMINOR);
				playerMinor2Pic.setIcon(vaderMINOR);
				twoMinors = 1;
			} else if (client.getMajorCharacter().getCharacterName()
					.equals("Darth Maul")) {
				playerPic.setIcon(maulIDMID);
				playerMinor1Pic.setIcon(maulMINOR);
				playerMinor2Pic.setIcon(maulMINOR);
				twoMinors = 1;
			} else if (client.getMajorCharacter().getCharacterName()
					.equals("Luke Skywalker")) {
				playerPic.setIcon(lukeIDMID);
				playerMinor1Pic.setIcon(lukeMINOR);
			} else if (client.getMajorCharacter().getCharacterName()
					.equals("Emperor Palpatine")) {
				playerPic.setIcon(emperorIDMID);
				playerMinor1Pic.setIcon(emperorMINOR);
				playerMinor2Pic.setIcon(emperorMINOR);
				twoMinors = 1;
			} else if (client.getMajorCharacter().getCharacterName()
					.equals("Jango Fett")) {
				playerPic.setIcon(jangoIDMID);
				playerMinor1Pic.setIcon(jangoMINOR);
			} else if (client.getMajorCharacter().getCharacterName()
					.equals("ObiWan Kenobi")) {
				playerPic.setIcon(obiwanIDMID);
				playerMinor1Pic.setIcon(obiwanMINOR);
				playerMinor2Pic.setIcon(obiwanMINOR);
				twoMinors = 1;
			}
			playerName.setText(client.getName());
			playerNameBack.setText(client.getName());
			if (client.getMajorCharacter().getCharacterHealth() > 0) {
				playerHealth.setText(client.getMajorCharacter()
						.getCharacterHealth() + "");
				playerHealthBack.setText(client.getMajorCharacter()
						.getCharacterHealth() + "");
			} else {
				playerHealth.setText(0 + "");
				playerHealthBack.setText(0 + "");
			}
			if (client.getMinorCharacter(0).getCharacterHealth() > 0) {
				playerMinor1Health.setText(client.getMinorCharacter(0)
						.getCharacterHealth() + "");
				playerMinor1HealthBack.setText(client.getMinorCharacter(0)
						.getCharacterHealth() + "");
			} else {
				playerMinor1Health.setText(0 + "");
				playerMinor1HealthBack.setText(0 + "");
			}

			if (twoMinors == 1) {
				playerMinor2Health.setText(client.getMinorCharacter(1)
						.getCharacterHealth() + "");
				playerMinor2HealthBack.setText(client.getMinorCharacter(1)
						.getCharacterHealth() + "");
				if (client.getMinorCharacter(1).getCharacterHealth() <= 9) {
					playerMinor2Health.setLocation(72, 178); // 55, 178
					playerMinor2HealthBack.setLocation(74, 179);
				} else {
					playerMinor2Health.setLocation(52, 178); // 55, 178
					playerMinor2HealthBack.setLocation(54, 179);
				}
			}

			if (client.getMinorCharacter(0).getCharacterHealth() <= 9) {
				playerMinor1Health.setLocation(72, 123); // 55, 178
				playerMinor1HealthBack.setLocation(74, 124);
			} else {
				playerMinor1Health.setLocation(52, 123); // 55, 178
				playerMinor1HealthBack.setLocation(54, 124);
			}
			if (client.getMajorCharacter().getCharacterHealth() <= 9) {
				playerHealth.setLocation(72, 63);
				playerHealthBack.setLocation(74, 64);
			} else {
				playerHealth.setLocation(55, 63);
				playerHealthBack.setLocation(57, 64);
			}
		}
	}

	private void setGameStatusIcon(int actionNum) {
		if (attacking) {
			actionNum = -1;
		}
		if (actionNum == -1) {
			gameStatus.setIcon(unactivatedAction);
			if (!defending) {
				skip.setEnabled(true);
			} else {
				skip.setEnabled(true);
			}
			draw.setEnabled(false);
			rollButton.setEnabled(false);
			rollButton.setBackground(Color.BLACK);
			enableAllPositions();
		} else if (actionNum == 0) {
			gameStatus.setIcon(rollAction);
			rollButton.setEnabled(true);
			rollButton.setBackground(Color.WHITE);
			currRoll = -1;
			amountToMove = -1;
		} else if (actionNum == 1) {
			gameStatus.setIcon(moveAction);
			rollButton.setEnabled(false);
			rollButton.setBackground(Color.BLACK);
		} else if (actionNum == 2) {
			gameStatus.setIcon(action1Action);
			if (client.canDraw()) {
				draw.setEnabled(true);
			}
			moveToList = new ArrayList<Point>();
			rollButton.setEnabled(false);
			rollButton.setBackground(Color.BLACK);
		} else if (actionNum == 3) {
			gameStatus.setIcon(action2Action);
			if (client.canDraw()) {
				draw.setEnabled(true);
			}
			rollButton.setEnabled(false);
			rollButton.setBackground(Color.BLACK);
		}
		gameStatus.revalidate();
		gameStatus.repaint();
	}

	private void setCharacterPosition() {
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 10; j++) {
				if (characterPosition[i][j] == null) {
					characterPosition[i][j] = new JButton();
					characterPosition[i][j].setSize(60, 60);
					characterPosition[i][j].setOpaque(false);
					characterPosition[i][j].setContentAreaFilled(false);
					characterPosition[i][j].setBorderPainted(false);
					int y = i * 60;
					int x = j * 60 + 100;
					characterPosition[i][j].setLocation(x, y);
					characterPosition[i][j]
							.addMouseListener(new CharacterListener());
					characterPosition[i][j].setVisible(true);
					add(characterPosition[i][j], null, 1);
				} else {
					// (characterPosition[i][j]).setImage(client.getCardInHand(i));
					characterPosition[i][j].setVisible(true);
				}
			}
		}
	}

	private void setDrawnCards() {
		for (int i = 0; i < handCard.length; i++) {
			if (numDraws >= i + 1) {
				if (handCard[i] == null) {
					handCard[i] = new CardImage(client.getCardInHand(i));
					handCard[i].setSize(91, 130);
					int x = (((i - (i % 5)) / 5) * 20 + 105) + 120 * (i % 5);
					int y = ((i - (i % 5)) / 5) * 15 + 425;
					handCard[i].setLocation(x, y);
					handCard[i].addMouseListener(new HoverListener());
					handCard[i].setVisible(true);
					add(handCard[i], null, Math.abs((i - (i % 5)) / 5 - 2));
				} else {
					((CardImage) handCard[i]).setImage(client.getCardInHand(i));
					handCard[i].setVisible(true);
				}
			} else if (handCard[i] != null) {
				((CardImage) handCard[i]).setImage(null);
			}
		}
		repaint();
	}

	private void setShownCards(ArrayList<Cards> display) {

		Deck theDeck = null;
		if (((Cards) display.get(0)).getDeck().equals("ObiWan Kenobi"))
			theDeck = new ObiWanDeck("ObiWan");
		else if (((Cards) display.get(0)).getDeck().equals("Luke Skywalker"))
			theDeck = new LukeSkyWalkerDeck("Luke");
		else if (((Cards) display.get(0)).getDeck().equals("Han Solo"))
			theDeck = new HanSoloDeck("Han");
		else if (((Cards) display.get(0)).getDeck().equals("Yoda"))
			theDeck = new YodaDeck("Yoda");
		else if (((Cards) display.get(0)).getDeck().equals("Jango Fett"))
			theDeck = new JangoDeck("Jango");
		else if (((Cards) display.get(0)).getDeck().equals("Darth Vader"))
			theDeck = new DarthVaderDeck("Darth Vader");
		else if (((Cards) display.get(0)).getDeck().equals("Darth Maul"))
			theDeck = new DarthMaulDeck("Darth Maul");
		else if (((Cards) display.get(0)).getDeck().equals("Emperor Palpatine"))
			theDeck = new EmperorDeck("Emperor");

		for (int i = 0; i < revealCard.length; i++) {
			if (display.size() >= i + 1) {
				revealCard[i] = new CardImage((Cards) display.get(i), theDeck);
				revealCard[i].setSize(91, 130);
				int x = (((i - (i % 5)) / 5) * 20 + 105) + 120 * (i % 5);
				int y = ((i - (i % 5)) / 5) * 15 + 110;
				revealCard[i].setLocation(x, y);
				revealCard[i].addMouseListener(new HoverListener());
				revealCard[i].setVisible(true);
				add(revealCard[i], null, Math.abs((i - (i % 5)) / 5 - 2));
			} else if (revealCard[i] != null) {
				((CardImage) revealCard[i]).setImage(null);
			}
		}
		repaint();
	}

	private void hideEmptySpaces() {
		int theMap[][] = client.getMap().getMap();
		for (int i = 0; i < theMap.length; i++)
			for (int j = 0; j < theMap[0].length; j++)
				if (theMap[i][j] == 0)
					characterPosition[i][j].setIcon(null);
	}

}
