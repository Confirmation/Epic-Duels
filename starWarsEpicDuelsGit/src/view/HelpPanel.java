package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class HelpPanel extends JPanel {
	private JTextArea description;
	private JLabel header, askMore;
	private JButton objective, movement, actions, attacking, defending,
			special, host, join, cardDetails, reimagined, characters, turn,
			chat, unitinfo, yoda, emperor, obi, luke, vader, jango, hansolo,
			maul;
	private JPanel startpanel, characterList;
	public static String baseDir = System.getProperty("user.dir")
			+ File.separator + "Images" + File.separator;
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

	private static final long serialVersionUID = 1L;

	public HelpPanel() {

		setLayout(null);
		setSize(900, 800);
		setLocation(100, -100);
		setBorder(null);
		setBackground(Color.BLACK);

		header = new JLabel();
		header.setSize(500, 40);
		header.setLocation(0, 110);
		header.setFont(new Font("", Font.BOLD, 30));
		header.setForeground(Color.YELLOW);
		header.setBackground(Color.BLACK);
		header.setText("WELCOME");
		add(header);

		askMore = new JLabel();
		askMore.setSize(500, 30);
		askMore.setLocation(625, 110);
		askMore.setFont(new Font("", Font.BOLD, 30));
		askMore.setForeground(Color.YELLOW);
		askMore.setBackground(Color.BLACK);
		askMore.setText("HELP DIRECTORY");
		add(askMore);

		description = new JTextArea();
		description.setLayout(null);
		description.setSize(600, 425);
		description.setLocation(0, 160);
		description.setWrapStyleWord(true);
		description.setLineWrap(true);
		description.setFont(new Font("Courier", Font.BOLD, 13));
		description.setForeground(Color.WHITE);
		description.setBackground(Color.BLACK);
		description
				.setText("Welcome to Star Wars Epic Duels, what would you like to learn more about?\n\nClick the button in the 'Help Directory' relating to the question you have");
		add(description);

		host = new JButton("START A GAME");
		host.setSize(120, 35);
		host.setLocation(10, 55);
		host.setBackground(Color.BLACK);
		host.setForeground(Color.RED);

		host.addActionListener(new HostListener());

		join = new JButton("JOIN A GAME");
		join.setSize(120, 35);
		join.setLocation(140, 55);
		join.setBackground(Color.BLACK);
		join.setForeground(Color.RED);
		join.addActionListener(new JoinListener());

		characters = new JButton("CHARACTERS");
		characters.setSize(120, 35);
		characters.setLocation(140, 10);
		characters.setBackground(Color.BLACK);
		characters.setForeground(Color.RED);
		characters.addActionListener(new CharactersListener());

		unitinfo = new JButton("UNIT INFO");
		unitinfo.setSize(120, 35);
		unitinfo.setLocation(140, 235);
		unitinfo.setBackground(Color.BLACK);
		unitinfo.setForeground(Color.RED);
		unitinfo.addActionListener(new UnitListener());

		turn = new JButton("YOUR TURN");
		turn.setSize(120, 35);
		turn.setLocation(10, 100);
		turn.setBackground(Color.BLACK);
		turn.setForeground(Color.RED);
		turn.addActionListener(new TurnListener());

		cardDetails = new JButton("CARD DETAILS");
		cardDetails.setSize(120, 35);
		cardDetails.setLocation(140, 145);
		cardDetails.setBackground(Color.BLACK);
		cardDetails.setForeground(Color.RED);
		cardDetails.addActionListener(new CardDetailsListener());

		objective = new JButton("OBJECTIVE");
		objective.setSize(120, 35);
		objective.setLocation(10, 10);
		objective.setBackground(Color.BLACK);
		objective.setForeground(Color.RED);
		objective.addActionListener(new ObjectiveListener());

		movement = new JButton("MOVEMENT");
		movement.setSize(120, 35);
		movement.setLocation(140, 100);
		movement.setBackground(Color.BLACK);
		movement.setForeground(Color.RED);
		movement.addActionListener(new MovementListener());

		actions = new JButton("ACTIONS");
		actions.setSize(120, 35);
		actions.setLocation(10, 145);
		actions.setBackground(Color.BLACK);
		actions.setForeground(Color.RED);
		actions.addActionListener(new ActionsListener());

		attacking = new JButton("ATTACKING");
		attacking.setSize(120, 35);
		attacking.setLocation(10, 190);
		attacking.setBackground(Color.BLACK);
		attacking.setForeground(Color.RED);
		attacking.addActionListener(new CombatListener());

		defending = new JButton("DEFENDING");
		defending.setSize(120, 35);
		defending.setLocation(140, 190);
		defending.setBackground(Color.BLACK);
		defending.setForeground(Color.RED);
		defending.addActionListener(new DefendingListener());

		special = new JButton("SPECIAL");
		special.setSize(120, 35);
		special.setLocation(10, 235);
		special.setBackground(Color.BLACK);
		special.setForeground(Color.RED);
		special.addActionListener(new SpecialListener());

		chat = new JButton("CHAT");
		chat.setSize(120, 35);
		chat.setLocation(10, 280);
		chat.setBackground(Color.BLACK);
		chat.setForeground(Color.RED);
		chat.addActionListener(new ChatListener());

		reimagined = new JButton("REIMAGINED");
		reimagined.setSize(120, 35);
		reimagined.setLocation(140, 280);
		reimagined.setBackground(Color.BLACK);
		reimagined.setForeground(Color.RED);
		reimagined.addActionListener(new ReimaginedListener());

		startpanel = new JPanel();
		startpanel.setLayout(null);
		startpanel.setSize(330, 400);
		startpanel.setLocation(620, 155);
		startpanel.setBorder(null);
		startpanel.setBackground(Color.BLACK);
		startpanel.setBorder(new CompoundBorder(BorderFactory
				.createEmptyBorder(), new EmptyBorder(10, 10, 10, 10)));

		startpanel.add(host);
		startpanel.add(join);
		startpanel.add(characters);
		startpanel.add(objective);
		startpanel.add(movement);
		startpanel.add(attacking);
		startpanel.add(cardDetails);
		startpanel.add(actions);
		startpanel.add(turn);
		startpanel.add(special);
		startpanel.add(chat);
		startpanel.add(unitinfo);
		startpanel.add(defending);
		startpanel.add(reimagined);
		startpanel.setVisible(true);

		add(startpanel);

		// CHARACTER BUTTONS
		yoda = new JButton(yodaIDMINI);
		yoda.setSize(75, 75);
		yoda.setLocation(0, 0);
		yoda.setBackground(Color.BLACK);
		yoda.setForeground(Color.RED);
		yoda.addActionListener(new YodaListener());

		emperor = new JButton(emperorIDMINI);
		emperor.setSize(75, 75);
		emperor.setLocation(75, 0);
		emperor.setBackground(Color.BLACK);
		emperor.setForeground(Color.RED);
		emperor.addActionListener(new EmperorListener());

		obi = new JButton(obiwanIDMINI);
		obi.setSize(75, 75);
		obi.setLocation(300, 0);
		obi.setBackground(Color.BLACK);
		obi.setForeground(Color.RED);
		obi.addActionListener(new ObiListener());

		hansolo = new JButton(hanIDMINI);
		hansolo.setSize(75, 75);
		hansolo.setLocation(150, 0);
		hansolo.setBackground(Color.BLACK);
		hansolo.setForeground(Color.RED);
		hansolo.addActionListener(new HanSoloListener());

		vader = new JButton(vaderIDMINI);
		vader.setSize(75, 75);
		vader.setLocation(225, 0);
		vader.setBackground(Color.BLACK);
		vader.setForeground(Color.RED);
		vader.addActionListener(new VaderListener());

		maul = new JButton(maulIDMINI);
		maul.setSize(75, 75);
		maul.setLocation(375, 0);
		maul.setBackground(Color.BLACK);
		maul.setForeground(Color.RED);
		maul.addActionListener(new MaulListener());

		jango = new JButton(jangoIDMINI);
		jango.setSize(75, 75);
		jango.setLocation(525, 0);
		jango.setBackground(Color.BLACK);
		jango.setForeground(Color.RED);
		jango.addActionListener(new JangoListener());

		luke = new JButton(lukeIDMINI);
		luke.setSize(75, 75);
		luke.setLocation(450, 0);
		luke.setBackground(Color.BLACK);
		luke.setForeground(Color.RED);
		luke.addActionListener(new LukeListener());

		characterList = new JPanel();
		characterList.setLayout(null);
		characterList.setSize(620, 95);
		characterList.setLocation(0, 370);
		characterList.setBorder(null);
		characterList.setBackground(Color.BLACK);
		characterList.setBorder(new CompoundBorder(BorderFactory
				.createEmptyBorder(), new EmptyBorder(10, 10, 10, 10)));

		add(characterList);
		characterList.setVisible(false);
		characterList.add(yoda);
		characterList.add(obi);
		characterList.add(maul);
		characterList.add(emperor);
		characterList.add(jango);
		characterList.add(hansolo);
		characterList.add(luke);
		characterList.add(vader);
	}

	private class ObjectiveListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			header.setText("OBJECTIVE");
			description.setSize(600, 425);
			description
					.setText("--The objective of Star Wars Epic Duels is to control your main character and minor character(s) to defeat your opponent(s) main character\n\n--A character is defeated when their health hits 0\n\n--If your major character died, the game continues until your partner(s) major character dies as well\n\n--The game ends when all major characters of one team are defeated\n\n");

			characterList.setVisible(false);
		}

	}

	private class ChatListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			header.setText("CHAT");
			description.setSize(600, 425);
			description
					.setText("--The Chat Box is a white field on the screen that appears after you successfully connect to a game and have selected a character\n\n--The Chat Box will be important in communicating with team members as well as telling you what has happened (which player's turn it is, what actions have been taken, when a person joins a game)\n\n--Send a message by entering text in the field next to the 'Send' button, and click the 'Send' button, when you are ready to send your message to other players");

			characterList.setVisible(false);
		}

	}

	private class ReimaginedListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			header.setText("REIMAGINED MODE");
			description.setSize(600, 425);
			description
					.setText("--When playing Reimagined Mode, players level up whenever they kill a unit\n\n--When a player levels up, they get a better chance to roll higher movement rolls per level\n\nFog of war is in place, and only squares adjacent to your characters will be visible\n\n--It is recommended that you are familiar with Legacy Mode before you attempt to play Reimagined Mode");

			characterList.setVisible(false);
		}

	}

	private class MovementListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			header.setText("MOVEMENT");
			description.setSize(600, 425);
			description
					.setText("--In order to move a player clicks the 'roll' button, how many characters can move and how far they can move is based upon the roll\n\n--To move, click the character you wish to move and then click the highlighted space you wish to move that character to\n\n--If a 'Move All' result is rolled, you may move all of your characters the way you would move a single character\n\n--Characters cannnot move through walls or through pits\n\n--A character may not stop their movement on the same square as another character\n\n--Characters may pass through friendly units but may not pass through enemies\n\n--Moving diagnolly takes 2 points of movement");
			characterList.setVisible(false);
		}

	}

	private class ActionsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			header.setText("ACTIONS");
			description.setSize(600, 425);
			description
					.setText("Each turn a player may perform up to two actions. The actions are as follows:\n\n--Draw a card (If hand size is greater than 10 cards at the end of the turn, discard down to 10 cards)\n\n--Playing cards");
			characterList.setVisible(false);
		}

	}

	private class CombatListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			header.setText("ATTACKING AND DAMAGE DEALT");
			description.setSize(600, 425);
			description
					.setText("--Characters with lightsabers may only attack enemiess adjacent to them.  Characters with blasters can attack in a straight "
							+ "line in any direction so long as there is an unblocked path between themselves and the target (no units, walls, ect.).\n\n--Characters with blasters may shoot over pits but nobody may move through them\n\n"
							+ "--The strength of the card is surrounded in red on the top left hand corner of the card.\n\n--A character may attack by clicking "
							+ "a card with an attack value and their picture on it (Han Solo cannot attack with a Chewbacca card or a Han Solo card with no attack value) "
							+ " Targets not in range will be greyed out while acceptable targets will remainin color\n\n--Click on the character you "
							+ "wish to attack after clicking the card you wish to play\n\n--Attacking counts as an action\n\n"
							+ "--A player may choose to defend against an attack (see 'DEFENDING'), damage dealt is equal to your attack value-their defense value.  "
							+ "If your attack is weaker than their defense, they take no damage");

			characterList.setVisible(false);
		}
	}

	private class DefendingListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			header.setText("DEFENDING AND DAMAGE RECIEVED");
			description.setSize(600, 425);
			description
					.setText("--A card's defense value is the number surrounded by blue in the top left hand corner of the card\n\n"
							+ "--When a player chooses to attack, the chat box will display what unit the player is attacking with and who their"
							+ " target is.  At that time, you may choose to defend by clicking a card in your hand that matches the targeted player"
							+ " and has a defense value (you cannot defend Jango Fett with a Zam Wessel card, or a Jango card with no defense value)"
							+ "\n\n--If you wish to save your defense cards for future attacks or do not have a defense card to play, hit 'Skip'\n\n"
							+ "--The damage you recieve will be equal to the attacker's attack value- your defense value "
							+ "(if you 'SKIP', the damage dealt will just be the attacker's attack value (see 'ATTACKING').  If your defense is greater than the attack,"
							+ " you take no damage\n\n--If your character's health ever hits '0', they are defeated and removed from the board\n\n--Remember, "
							+ "the game is over when all major characters of one team are defeated");

			characterList.setVisible(false);

		}
	}

	private class SpecialListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			header.setText("SPECIAL");
			description.setSize(600, 425);
			description
					.setText("--Special cards have abilities on them that activate when they are played, some deal unblockabe damage, some force players to discard cards, some are powered up based on various conditions\n\n--While these are just a few examples, there are many more special cards out there, play more charaters to see more specials");

			characterList.setVisible(false);
		}
	}

	private class HostListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			header.setText("STARTING A GAME");
			description.setSize(600, 425);
			description
					.setText("--Click on 'Host a Game'\n\n--Click on which game mode you wish to play ('Legacy' or 'Reimagined')\n\n--Determine how many players will play(AI and Player, max of 8, needs at least one Player)\n\n--Enter a name in the 'Name' field, this will be your name in the 'Chat Box'\n\n--Click 'Continue'\n\n--Select a Map to play on by clicking the image\n\n--Select your character, characters already chosen will be greyed out and unavailable for selection\n\n--Pick a team to play on\n\n--The game will begin when the needed number of players are connected and ready to play");
			characterList.setVisible(false);

		}

	}

	private class JoinListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			header.setText("JOINING A GAME");
			description.setSize(600, 425);
			description
					.setText("--Click on 'Join Networked Game'\n\n--Enter a name in the 'Name' field, this will be your name in the 'Chat Box'\n\n--Enter the Host's IP address in the IP field\n\n--Click 'Join'\n\n--Select your character, characters already chosen will be greyed out and unavailable for selection\n\n--Pick a team to play on\n\n--The game will begin when the needed number of players are connected and ready to play");
			characterList.setVisible(false);
		}

	}

	private class CharactersListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			header.setText("CHARACTERS");
			description.setSize(600, 225);
			description
					.setText("--Every character in Star Wars Epic Duels is different and has their own unique style of play\n\n--Every major character has a set of minor characters, however the characters shown below in split images have the minor shown in the picture\n\n--Click on the images below to read more about each character in depth");
			characterList.setVisible(true);
			characterList.setLocation(0, 395);
		}

	}

	private class TurnListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			header.setText("THINGS TO DO ON YOUR TURN");
			description.setSize(600, 425);
			description
					.setText("--Hit 'Roll' to simulate dice roll\n\n--Move character(s) up to rolled result (See 'MOVEMENT') \n\n--Spend Action(s) (See 'ACTIONS')\n\n--You may skip any steps by clicking 'SKIP' (Example: you do not want to move your character) \n\n--End Turn");
			characterList.setVisible(false);
		}
	}

	private class CardDetailsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			header.setText("CARD DETAILS");
			description.setSize(600, 425);
			description
					.setText("Cards have different attack values, defense values, and some even have special abilities when played.  To view a bigger image of a card, hover over it with your mouse and it will appear above the chat box during the game\n\n--The number surrounded in red on the card is the attack value\n\n--The number surrounded in blue is the defense value\n\n--The abilities are written on the card and activae when played\n\n--A '*' value means it depends on other factors, those factors are written on the card");
			characterList.setVisible(false);
		}
	}

	private class UnitListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			header.setText("UNIT INFO");
			description.setSize(600, 425);
			description
					.setText("--Your major character's health is displayed in their big picture at the top left corner of the screen in yellow\n\n--Your minor character(s) health is displayed in yellow next to their picture(s)\n\n--You can view other units health by scrolling over their character, their information will be displayed below your minor character(s)\n\n--The number in the picture of red cards beneath the selected character represents how many cards the player controlling that character has in ther hand\n\n--Use opponenets hand size to help predict when they will play aggressively or on defense");
			characterList.setVisible(false);
		}
	}

	private class YodaListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			header.setText("Yoda-- Strong is the Force");
			description.setSize(600, 425);
			description
					.setText("MAJOR CHARACTER: Yoda: 15 HP, Uses Lightsaber \nSTRENGTHS: Strong Defense, 'Force Lift' Card\nWEAKNESSES: Low Attack, Low Health\nMINOR CHARACTERS: Clone Troopers (x2): 4 HP, Uses Blaster \nSTRENGTHS: Ranged\nWEAKNESSES: Minor Characters, Low Health, Low Defense\n\n--Plan to stockpile cards and react to your enemy rather than constantly going on the offensive. Your game may be longer and drawn-out than you would usually prefer, but at least you will win\n\n--'Force Lift' is a useful card to play if your opponent doesn't have many cards, but don't use it on a player who is stockpiling; this almost always gives that player a chance to get rid of dead weight\n\n--Use the clone troopers to draw out defense cards and block off enemy troops");
			characterList.setLocation(0, 585);
		}

	}

	private class LukeListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			header.setText("Luke Skywalker-- The Determined");
			description.setSize(600, 425);
			description
					.setText("MAJOR CHARACTER: Luke Skywalker: 17 HP, Uses Lightsaber \nSTRENGTHS: Strong Attacks, 'I Will Not Fight You' Card\nWEAKNESSES: Average Defense\nMINOR CHARACTER: Princess Leia: 10 HP, Uses Blaster \nSTRENGTHS: Ranged, healing ability, 'Latent Force Abilities' card.\nWEAKNESSES: Minor Characters, Low Health, Low Defense\n\n--Luke and Leia are interesting, they play well as a team, and they play well with Leia dead. Using Leia in a kamikaze fashion is generally a smart way to play; her 'Latent Force Abilities' can really hurt the enemy, and Luke's 'Justice' can be a nice crippling blow after she's died\n\n--Leia's role should be drawing out defense cards from a distance; she's also useful for destroying minor characters quickly. Luke's role should be to hang back until he can adequately attack; use 'Children of the Force' to keep him away from enemies\n\n--'I Will Not Fight You' forces all players to discard attack cards with an attack value greater than '1', meaning players stockpiling damage cards will have to discard those cards they would have attacked with");
			characterList.setLocation(0, 585);

		}

	}

	private class ObiListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			header.setText("Obi-Wan Kenobi-- Jedi Master");
			description.setSize(600, 425);
			description
					.setText("MAJOR CHARACTER: Obi-Wan Kenobi: 18 HP, Uses Lightsaber \nSTRENGTHS: Strong Attacks, Strong Defense, Hit-and-Run Tactics\nWEAKNESSES: None\nMINOR CHARACTERS: Clone Troopers (x2): 4 HP, Uses Blaster \nSTRENGTHS: Ranged\nWEAKNESSES: Minor Characters, Low Health, Low Defense\n\n--Obi-Wan's strength is his ability to control the battle by the way in which he plays his cards. If the enemy is stockpiling, he can play 'Force Balance' and force them to discard everything. H can also play 'Force Control' and move everybody randomly up to 3 spaces away, 'Jedi Attack' can be used to retreat.\n\n--If a card in your discard pile is needed, you can play 'Jedi Mind Trick' to retrieve your best card in your discard pile.  By coupling 'Jedi Attack' with a standard attack card, Obi-Wan can strike fast and run away\n\n--Do not hoard Jedi Block, waiting for the right moment to play it; besides blocking any attack, it allows Obi-Wan to draw a card. Plus, there are three of them\n\n--Use Obi-Wan's clone troopers to draw out defense cards and block off enemy troops");
			characterList.setLocation(0, 585);

		}

	}

	private class VaderListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			header.setText("Darth Vader-- Dark Lord of the Sith");
			description.setSize(600, 425);
			description
					.setText("MAJOR CHARACTER: Darth Vader: 20 HP, Uses Lightsaber \nSTRENGTHS: Strong Attacks, Direct Damage Specials, Highest Health\nWEAKNESSES:Low Defense\nMINOR CHARACTERS: Stormtroopers (x2): 4 HP, Uses Blaster \nSTRENGTHS: Ranged\nWEAKNESSES: Minor Characters, Low Health, Low Defense\n\n--With 20 HP and 9 global attack cards, Darth Vader is a force to be reckoned with. He can be beaten by overly aggressive opponents, but when played well, he is nearly unstoppable. The key to winning is distance, annoy the enemy with 'Wrath,' 'Choke,' and 'Throw Debris,' whittling down HP and killing off minor characters, and then let them come to you\n\n--The stormtroopers are cannon fodder, but they are very good at blocking off enemies and drawing out defense cards. Use them to annoy your enemies while Vader stockpiles his powerful cards\n\n--If an enemy is stockpiling, use 'Your Skills Are Not Complete' to see how many defense cards he or she has (and to force him or her to discard special cards) before launching into an attack. Use 'All Too Easy' if you know for certain that a player has no defense cards; vary its usage from game to game so that your enemy can't predict when you're going to play it");
			characterList.setLocation(0, 585);
		}
	}

	private class MaulListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			header.setText("Darth Maul-- Death Sentance");
			description.setSize(600, 425);
			description
					.setText("MAJOR CHARACTER: Darth Maul: 18 HP, Uses Lightsaber \nSTRENGTHS: Strong Attacks, Can make multiple attacks without using actions\nWEAKNESSES:Low Defense\nMINOR CHARACTERS: Battle Droids (x2): 3 HP, Uses Blaster \nSTRENGTHS: Ranged\nWEAKNESSES: Minor Characters, Low Health, Low Defense\n\n--Stockpile. Attack. Retreat. Repeat until enemy is dead. Due to the fact that Darth Maul can often attack 4-5 times in one turn 'Sith Speed' and 'Super Sith Speed'), most opponents will be hard-pressed to defend against him. Finish your set with 'Athletic Surge' and retreat, waiting until you have several more attacks before you approach the enemy again\n\n--The Battle Droids are fairly useless. Use them to shield Darth Maul from blasters and to form blockades\n\n-- If you can keep your opponent guessing, they will never play the appropriate defense cards consistently correctly, resulting in Darth Maul inflicting more damage");
			characterList.setLocation(0, 585);

		}

	}

	private class JangoListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			header.setText("Jango Fett-- Bounty Hunter");
			description.setSize(600, 425);
			description
					.setText("MAJOR CHARACTER: Jango Fett: 15 HP, Uses Blaster \nSTRENGTHS: Ranged, Mobility\nWEAKNESSES:Low Defense, Low Health\nMINOR CHARACTER: Zam Wessell: 10 HP, Uses Blaster \nSTRENGTHS: Ranged, Buffed Range\nWEAKNESSES: Minor Character, Low Health, Low Defense\n\n--Jango's strength is his speed and his ability to make use of Zam Wessell\n\n--Use Jango's ranged attack abilities to attack opponents fiercely while maintaining a safe distance. Use weaker attacks to draw out defense cards\n\n--Zam should play defensively and choose her battles, always hitting hard from across the map. Jango should defend her by blocking enemy line of sight and generally being a nuisance\n\n--Jango's 'Wrist Cable' attack takes away one action from a player on their next turn, this can be used to slow down the attacks of that player\n\n--Jango has plenty of cards to move him out of tight situations, use them to evade your enemies and continue to attack from a distance");
			characterList.setLocation(0, 585);
		}

	}

	private class HanSoloListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			header.setText("Han Solo-- For Hire");

			description.setSize(600, 425);
			description
					.setText("MAJOR CHARACTER: Han Solo: 13 HP, Uses Blaster \nSTRENGTHS: Ranged, Many Attack Cards\nWEAKNESSES:Low Defense, Low Health, Minor is Stronger\nMINOR CHARACTER: Chewbacca: 15 HP, Uses Blaster \nSTRENGTHS: Ranged, Many Attack Cards, Healing Ability\nWEAKNESSES: Minor Character, Low Health, Low Defense\n\n--The most important thing to remember about Han and Chewie is that neither character is expendable. If you don't play them as a team, you will probably lose\n\n--Keep as many cards in your hand as possible so that you can soften the blow of your enemies' attacks. Without defense cards, Han and Chewbacca will die quickly.\n\n--Both Han and Chewbacca are ranged characters, meaning that neither gains any advantage by being close to an enemy. Try to keep as much distance between yourself and your enemies as possible, never backing into a corner or walking into a blockade");
			characterList.setLocation(0, 585);

		}

	}

	private class EmperorListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			header.setText("Emperor Palpatine-- Darth Sidious");
			description.setSize(600, 425);
			description
					.setText("MAJOR CHARACTER: Emperor Palpatine: 13 HP, Uses Lightsaber \nSTRENGTHS: High Defense, Healing, Direct damage, Force opponents to discard\nWEAKNESSES:Low Offense, Low Health \nMINOR CHARACTERS: Crimson Guard (x2): 5 HP, Uses Blaster \nSTRENGTHS: Ranged, High Defense, High Offense\nWEAKNESSES: Minor Character, Low Health\n\n--Sit back, stockpile, and block attackers with your Crimson Guard. Palpatine is a defensive character, and while he can be killed easily due to his low hit points, he can disarm his foes if he simply waits for the right cards\n\n--While the Emperor is rather weak offensively, his Crimson Guards have more than enough power to block off aggressive players and inflict some damage. At the very least, they can use their range and durability to draw out some enemy defense and attack cards\n\n--Since 7 of the Emperor's cards have something to do with forcing the other player to discard at least one card, try to force an opponent to discard one card per turn while always being sure to play a card and then draw a card on your turn. By continually forcing your opponent to discard cards, you limit his or her options; by constantly drawing, you increase your own");
			characterList.setLocation(0, 585);

		}
	}

}
