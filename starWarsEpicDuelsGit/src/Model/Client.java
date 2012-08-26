/**
 * This is the client class that tells the GUI how
 * to respond to the server messages and tells the
 * server of any user actions made via the GUI.
 */
package Model;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;

import deckPackage.Cards;
import deckPackage.Deck;

import mapPackage.BlankMap;
import mapPackage.MainMap;

public class Client extends Observable {

	private Socket socket;
	private ObjectInputStream inStream;
	private ObjectOutputStream outStream;
	private int port = Game.port;
	private Thread receivingThread;
	private String name;
	private boolean connection;
	private ArrayList<Character[]> available;
	private ArrayList<Character[]> unavailable;
	private ArrayList<ArrayList<Player>> teams;
	private MainMap map;
	private boolean running;
	private boolean choosingCharacters;
	private boolean choosingCharacterFailed;
	private boolean releasingCharacterFailed;
	private boolean releasingCharacters;
	private boolean choosingTeam;
	private boolean teamSelectionFailed;
	private boolean releasingTeam;
	private boolean teamReleaseFailed;
	private boolean updating;
	private boolean AI;
	private boolean AIIsChoosing;
	private boolean drawing;
	private boolean discarding;
	private boolean attacking;
	private boolean removing;
	private boolean needsToDiscard;
	private int numPlayers;
	private int playerNumber;
	private int turn;
	private int teamChoice;
	private int actionNum;
	private int attackedCharacter;
	private Character target;
	private int handSize;
	private int forceDiscard = 0;
	private boolean canDraw;
	private boolean canMove = true;
	private boolean attackSelf;
	private boolean lastActionDraw;

	private int rollValue = 0;

	private int numToMove = 0;

	private boolean fogOfWar;

	private int numMinors;

	private int futureActions = 0;
	private boolean moveMain = true;
	private boolean specialMove = false;

	private boolean hibernation = false;

	// true if the ai is not in attack mode
	private boolean aiWeak = true;
	// true if the ai is safe
	private boolean aiSafe = true;
	// this will be the attack threshold that will be changed over the course of
	// the game
	private int aiMajorAttackThreshold = 0;
	private int aiMinorAttackThreshold = 0;
	// this is the combination of the values of the cards in hand
	private int aiMajorCardValues = 0;
	private int aiMinorCardValues = 0;

	int discardBUFF = 2;

	private ArrayList<Point> attackableLocations = new ArrayList<Point>();

	public Client(String name, String ip) {
		this(name, ip, false);
	}

	public Client(String name, String ip, boolean AI) {
		this.name = name;
		this.AI = AI;
		this.playerNumber = -1;
		this.running = true;
		this.canDraw = true;
		this.forceDiscard = -1;
		this.actionNum = -1;

		// connect to server using information given above
		try {
			socket = new Socket(ip, port);

			inStream = new ObjectInputStream(socket.getInputStream());
			outStream = new ObjectOutputStream(socket.getOutputStream());

			connection = true;

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			connection = false;
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
			System.exit(0);
		}

		if (connection) {
			try {
				@SuppressWarnings("unchecked")
				ArrayList<Object> message = (ArrayList<Object>) inStream
						.readObject();
				execute(message);
				Client.this.setChanged();
				Client.this.notifyObservers();
				message = new ArrayList<Object>();
				message.add("setName");
				message.add(name);
				message.add(AI);
				send(message);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			// start new thread that receives messages
			receivingThread = new Thread(new ReceiveThread());
			receivingThread.start();
			this.setChanged();
			this.notifyObservers("Started");
		}
	}

	// this thread will constantly wait for new messages that it can add to the
	// list on the View's side
	private class ReceiveThread implements Runnable {

		@SuppressWarnings("unchecked")
		@Override
		public void run() {
			while (running && connection) {
				try {
					ArrayList<Object> message = new ArrayList<Object>();
					message = (ArrayList<Object>) inStream.readObject();
					execute(message);
					Client.this.setChanged();
					Client.this.notifyObservers();
				} catch (IOException e) {
					connection = false;
					e.printStackTrace();
					System.exit(0);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private synchronized void execute(ArrayList<Object> list) {
		try {
			Method method = this.getClass().getMethod((String) list.get(0),
					Object.class);
			method.invoke(this, list);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public synchronized void wakeUp(Object o) {
		hibernation = false;
		if (!AI && !removing) {
			actionNum++;
			if (actionNum > 3) {
				actionNum = -1;
				endTurn();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public synchronized void beingAttacked(Object o) {
		ArrayList<Object> list = (ArrayList<Object>) o;
		Player defendingPlayer = (Player) list.get(1);
		Character defender = (Character) list.get(2);
		Integer attackValue = (Integer) list.get(3);
		if (this.playerNumber == defendingPlayer.getPlayerNumber()) {
			if (getMajorCharacter().getCharacterName().equals(
					defender.getCharacterName())) {
				changeCharacterHealth(defendingPlayer, getMajorCharacter(),
						attackValue);
			} else if (getMinorCharacter(0).getCharacterName().equals(
					defender.getCharacterName())) {
				changeCharacterHealth(defendingPlayer, getMinorCharacter(0),
						attackValue);
			} else {
				changeCharacterHealth(defendingPlayer, getMinorCharacter(1),
						attackValue);
			}
		}
	}

	private synchronized void changeCharacterHealth(Player defendingPlayer,
			Character defender, Integer attackValue) {
		defender.changeHealth(-(int) attackValue);
		ArrayList<Object> list = new ArrayList<Object>();
		list.add("changeHealth");
		list.add(defendingPlayer);
		send(list);
	}

	public synchronized void send(ArrayList<Object> list) {
		try {
			// System.out.println(name + " Sending " + list);
			outStream.writeObject(list);
			outStream.reset();
		} catch (IOException e) {
			connection = false;
		}
	}

	public synchronized boolean isConnected() {
		return connection;
	}

	public synchronized boolean isRunning() {
		return running;
	}

	public synchronized void kill() {
		connection = false;
		running = false;
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized ArrayList<Character[]> getAvailableCharacters() {
		return available;
	}

	public synchronized ArrayList<Character[]> getUnavailableCharacters() {
		return unavailable;
	}

	@SuppressWarnings("unchecked")
	public synchronized void setCharacterAvailability(Object o) {
		ArrayList<Object> list = (ArrayList<Object>) o;
		setAvailableCharacters((ArrayList<Character[]>) list.get(1));
		setUnavailableCharacters((ArrayList<Character[]>) list.get(2));
	}

	public synchronized void setAvailableCharacters(
			ArrayList<Character[]> available) {
		this.available = available;
		choosingCharacters = false;
		releasingCharacters = false;
	}

	public synchronized void setUnavailableCharacters(
			ArrayList<Character[]> unavailable) {
		this.unavailable = unavailable;
		choosingCharacters = false;
		releasingCharacters = false;
	}

	@SuppressWarnings("unchecked")
	public synchronized void setTeams(Object o) {
		ArrayList<Object> list = (ArrayList<Object>) o;
		setTeams((ArrayList<ArrayList<Player>>) list.get(1));
	}

	public synchronized void setTeams(ArrayList<ArrayList<Player>> teams) {
		this.teams = teams;
		drawing = false;
		discarding = false;
	}

	@SuppressWarnings("unchecked")
	public synchronized void setMap(Object o) {
		if (!specialMove) {
			ArrayList<Object> list = (ArrayList<Object>) o;
			setMap((MainMap) list.get(1));
		}
	}

	public synchronized void setMap(MainMap map) {
		this.map = map;
		choosingTeam = false;
		releasingTeam = false;
		updating = false;
		AIIsChoosing = false;
		if (attacking || removing) {
			attacking = false;
			removing = false;
			if (!AI) {
				this.setChanged();
				this.notifyObservers();
			}

		}
	}

	public synchronized void move(Point location, Point destination, int amount) {
		numToMove -= amount;
		if (numToMove <= 0 && !specialMove) {
			if (!specialMove && actionNum == 1) {
				actionNum++;
				actionNum += futureActions;
			}
			futureActions = 0;
		}
		// System.out.println("ActionNum = " + actionNum);
		move(location, destination);
	}

	public synchronized void move(Point location, Point destination) {
		ArrayList<Object> list = new ArrayList<Object>();
		specialMove = false;
		list.add("move");
		list.add(location);
		list.add(destination);
		send(list);
		// map.clearPoint(location);
	}

	public synchronized void moveAnywhere(Object o) {
		specialMove = true;
		ArrayList<Object> list = (ArrayList<Object>) o;
		moveMain = (Boolean) list.get(1);
		map.allPossibleLocations();
		this.setChanged();
		this.notifyObservers(moveMain);
	}

	public synchronized void moveAmount(Object o) {
		specialMove = true;
		ArrayList<Object> list = (ArrayList<Object>) o;
		moveMain = (Boolean) list.get(1);
		int move = (Integer) list.get(2);
		if (!AI) {
			Point location;
			if (moveMain)
				location = map.getLocationOf(playerNumber);
			else
				location = map.getLocationOf(playerNumber + 1);

			if (location != null) {
				map.displayPossiblePaths(location, move);
				this.setChanged();
				this.notifyObservers(moveMain);
			} else {
				move(null, null);
			}
		} else {
			if (true) {
				this.update();
				this.endTurn();
			} else {
				this.update();
			}
		}
	}

	public synchronized void chooseCharacter(Character[] choice) {
		choosingCharacters = true;
		choosingCharacterFailed = false;
		ArrayList<Object> list = new ArrayList<Object>();
		list.add("setCharacterChoice");
		list.add(choice);
		send(list);
		aiMajorAttackThreshold = choice[0].getAttackThreshold();
		aiMinorAttackThreshold = choice[1].getAttackThreshold();
	}

	public synchronized void releaseCharacter(Character[] choice) {
		releasingCharacters = true;
		releasingCharacterFailed = false;
		ArrayList<Object> list = new ArrayList<Object>();
		list.add("removeCharacterChoice");
		list.add(choice);
		send(list);
	}

	public synchronized void AIChooseCharacterAndTeam(Integer team) {
		AIIsChoosing = true;
		chooseCharacter(available.get(0));
		chooseTeam(team);
	}

	public synchronized boolean AIIsChoosing() {
		return AIIsChoosing;
	}

	public synchronized boolean isChoosingCharacters() {
		return choosingCharacters;
	}

	public synchronized boolean isReleasingCharacters() {
		return releasingCharacters;
	}

	public synchronized void chooseTeam(Integer choice) {
		teamSelectionFailed = false;
		choosingTeam = true;
		ArrayList<Object> list = new ArrayList<Object>();
		list.add("addPlayer");
		list.add(choice);
		send(list);
		teamChoice = choice;
	}

	public synchronized void releaseTeam() {
		teamReleaseFailed = false;
		releasingTeam = true;
		ArrayList<Object> list = new ArrayList<Object>();
		list.add("removePlayer");
		send(list);
	}

	public boolean isReleasingTeam() {
		return releasingTeam;
	}

	public synchronized boolean isChoosingTeam() {
		return choosingTeam;
	}

	public synchronized MainMap getMap() {
		return map;
	}

	public synchronized void disconnect() {
		running = false;
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public synchronized void update(Object o) {
		ArrayList<Object> list = (ArrayList<Object>) o;
		setAvailableCharacters((ArrayList<Character[]>) list.get(1));
		setUnavailableCharacters((ArrayList<Character[]>) list.get(2));
		setTeams((ArrayList<ArrayList<Player>>) list.get(3));
		setMap((MainMap) list.get(4));
		setNumPlayers((Integer) list.get(5));
		setFogOfWar((Integer) list.get(6));
	}

	public synchronized void setNumPlayers(Integer num) {
		numPlayers = num;
	}

	public synchronized int getNumPlayers() {
		return numPlayers;
	}

	public synchronized void update() {
		updating = true;
		ArrayList<Object> list = new ArrayList<Object>();
		list.add("update");
		send(list);
	}

	public synchronized void needToUpdate(Object o) {
		update();
	}

	public synchronized boolean isUpdating() {
		return updating;
	}

	public String getName() {
		return name;
	}

	@SuppressWarnings("unchecked")
	public synchronized void setPlayerNumber(Object o) {
		ArrayList<Object> list = (ArrayList<Object>) o;
		setPlayerNumber((Integer) list.get(1));
	}

	public synchronized void setPlayerNumber(Integer num) {
		playerNumber = num;
	}

	public synchronized boolean isTurn() {
		if (actionNum > 3 & !AI) {
			actionNum = -1;
			endTurn();
		}
		return turn == playerNumber;
	}

	// rolls for the ai and moves to the next step after setting the state
	private synchronized void aiRoll() {
		roll();
		aiSetState();
		aiMove();
	}

	// This calls upon the various methods required for ai movement
	private synchronized void aiMove() {
		if (aiWeak) {
			move(this.getMajorCharacter().getCharacterLocation(),
					aiSafeFinder(), 1);
		} else {

		}
		System.out.println("AI at move and weak is " + aiWeak
				+ " and aiSafe is " + aiSafe);

	}

	// Decides if the ai is weak and decides if it is safe
	// Also sets the aiCardValues
	private synchronized void aiSetState() {
		aiMajorCardValues = 0;
		aiMinorCardValues = 0;
		for (int i = 0; i < getHandSize(); i++) {
			if (getCardInHand(i).getCharacter().equals("major"))
				aiMajorCardValues += this.getCardInHand(i).getAIValue();
			else
				aiMinorCardValues += this.getCardInHand(i).getAIValue();
		}
		if (aiMajorCardValues > this.aiMajorAttackThreshold) {
			System.out.println("cardValues: " + aiMajorCardValues
					+ "   threshold: " + aiMajorAttackThreshold);
			aiWeak = false;
		} else {
			aiWeak = true;
		}

	}

	// This creates a map from the game map, removes the main character, and
	// puts in the number 9 where the enemies can attack. Then compares the two
	// maps to see if there is overlap.
	// Returns null if safe. Else returns a safe spot. If there are no safe
	// spot, it returns the safest spot.
	private synchronized Point aiSafeFinder() {
		MainMap attackView = new BlankMap(getMap().getMap());
		attackView.removeCharacter(this.getMajorCharacter()
				.getCharacterNumber());
		ArrayList<Player> enemyTeam = null;
		if (this.getTeamChoice() == 0)
			enemyTeam = this.getTeams().get(1);
		else
			enemyTeam = this.getTeams().get(0);
		for (int i = 0; i < numPlayers / 2; i++) {
			Character enemyCharacter = enemyTeam.get(i).getMajorCharacter();
			if (enemyTeam.get(i).majorIsAlive()) {
				attackView.displayAISafeFinder(
						enemyCharacter.getCharacterLocation(),
						enemyCharacter.isRanged());
			}

			enemyCharacter = enemyTeam.get(i).getMinorCharacter(0);
			if (enemyTeam.get(i).minorIsAlive(0)) {
				attackView.displayAISafeFinder(
						enemyCharacter.getCharacterLocation(),
						enemyCharacter.isRanged());
			}

			if (enemyTeam.get(i).getNumMinors() > 1) {
				enemyCharacter = enemyTeam.get(i).getMinorCharacter(1);
				if (enemyTeam.get(i).minorIsAlive(1)) {
					attackView.displayAISafeFinder(
							enemyCharacter.getCharacterLocation(),
							enemyCharacter.isRanged());
				}
			}
		}
		if (attackView
				.getNumAt(this.getMajorCharacter().getCharacterLocation()) == 0)
			aiSafe = true;
		if (aiSafe == false)
			return aiBestLocation(attackView);

		return null;
	}

	// Finds the best location to move to for both weak and strong
	private synchronized Point aiBestLocation(MainMap attackView) {
		Point goTo = null;
		double tempDist = 0;
		double distFromThreat = 0;
		Character threat;
		System.out.println("In aiBestLocation");
		if (aiWeak) {
			threat = aiLargestThreat();
			map.displayPossiblePaths(this.getMajorCharacter()
					.getCharacterLocation(), rollValue);
			for (int i = 0; i < map.getMap().length; i++) {
				for (int j = 0; j < map.getMap()[0].length; j++) {
					tempDist = Math.sqrt(Math.pow(
							threat.getCharacterLocation().x - i, 2)
							+ Math.pow(threat.getCharacterLocation().y - j, 2));

					if (map.getNumAt(new Point(i, j)) == 6) {
						if (attackView.getNumAt(new Point(i, j)) == 0) {
							if (goTo == null || tempDist > distFromThreat) {
								goTo = new Point(i, j);
								distFromThreat = tempDist;
								System.out.println("tempDist = " + tempDist);
							}
						}
					}
				}
			}
		}
		System.out.println("distFromThreat = " + distFromThreat);
		return goTo;
	}

	// Used to find the largest threat to attack. This may favor farther enemies
	private synchronized Character aiLargestThreat() {
		// (total card values)*(attack threshold) *
		// (1/ number of card in threat's hand) / (distance * .5 + (health)) =
		// offensive threat (kill him!)

		// (number of card in threat's hand) *
		// (threat's attack threshold) / (distance * .25) = defensive threat
		// (run away from)
		Character target = null;
		int threatLevel = 0;
		int enemyTeam = teamChoice;
		if (enemyTeam == 0)
			enemyTeam = 1;
		else
			enemyTeam = 0;
		ArrayList<Player> enemies = teams.get(enemyTeam);
		for (int i = 0; i < enemies.size(); i++) {
			Player p = enemies.get(i);
			Point location = this.getMajorCharacter().getCharacterLocation();
			Point threatLocation;
			int distance;
			if (p.majorIsAlive()) {

				threatLocation = p.getMajorLocation();
				distance = (Math.abs(threatLocation.x - location.x) + Math
						.abs(threatLocation.y - location.y)) + 1;
				// Offensive
				if (!aiWeak) {
					if ((aiMajorCardValues)
							* (p.getMajorCharacter().getAttackThreshold())
							* (1 / p.getHandSize()) * 1000
							/ (distance * .5 + p.getMajorHealth()) > threatLevel) {
						threatLevel = (p.getHandSize() * 1000) / distance;
						target = p.getMajorCharacter();
					}
				} else {
					// Defensive
					if ((p.getHandSize()
							* p.getMajorCharacter().getAttackThreshold() * 1000 / (distance * .25)) >= threatLevel) {
						threatLevel = (int) (p.getHandSize()
								* p.getMajorCharacter().getAttackThreshold()
								* 1000 / (distance * .25));
						target = p.getMajorCharacter();
					}
				}
			}
			if (p.minorIsAlive(0)) {
				threatLocation = p.getMinorLocation(0);
				location = p.getMinorLocation(0);
				distance = (Math.abs(threatLocation.x - location.x) + Math
						.abs(threatLocation.y - location.y)) + 1;
				if (!aiSafe) {
					if (((15 - p.getHandSize()) * 1000) / (distance) > threatLevel) {
						threatLevel = (p.getHandSize() * 1000) / distance;
						target = p.getMinorCharacter(0);
					}
				} else {
					// Defensive
					if ((p.getHandSize()
							* p.getMinorCharacter(0).getAttackThreshold()
							* 1000 / (distance * .5)) >= threatLevel) {
						threatLevel = (int) (p.getHandSize()
								* p.getMinorCharacter(0).getAttackThreshold()
								* 1000 / (distance * .5));
						target = p.getMinorCharacter(0);
					}
				}
			}
			if (p.getNumMinors() > 1 && p.minorIsAlive(1)) {
				threatLocation = p.getMinorLocation(1);
				location = p.getMinorLocation(1);
				distance = (Math.abs(threatLocation.x - location.x) + Math
						.abs(threatLocation.y - location.y)) + 1;
				if (!aiSafe) {
					if (((15 - p.getHandSize()) * 1000) / distance > threatLevel) {
						threatLevel = (p.getHandSize() * 1000) / distance;
						target = p.getMinorCharacter(1);
					}
				} else {
					// Defensive
					if ((p.getHandSize()
							* p.getMinorCharacter(1).getAttackThreshold()
							* 1000 / (distance * .5)) >= threatLevel) {
						threatLevel = (int) (p.getHandSize()
								* p.getMinorCharacter(1).getAttackThreshold()
								* 1000 / (distance * .5));
						target = p.getMinorCharacter(1);
					}
				}
			}
		}
		System.out.println(target.getCharacterName());
		return target;
	}

	private synchronized void aiAction() {

	}

	@SuppressWarnings("unchecked")
	public synchronized void setTurn(Object o) {
		ArrayList<Object> list = (ArrayList<Object>) o;
		setTurn((Integer) list.get(1));
	}

	public synchronized void setTurn(Integer turn) {
		lastActionDraw = false;
		this.turn = turn;
		numToMove = 0;
		if (AI && isTurn()) {

			if (canMove) {
				aiSafe = false;
				actionNum = 0;
				aiRoll();
			} else {
				actionNum = 2;
				canMove = true;
				aiAction();
			}

		} else if (isTurn()) {
			// System.out.println(canMove + " " + actionNum);
			if (canMove) {
				actionNum = 0;
			} else {
				actionNum = 2;
				canMove = true;
			}
			// System.out.println(canMove + " " + actionNum);
			this.setChanged();
			this.notifyObservers();
		} else {
			actionNum = -1;
			this.setChanged();
			this.notifyObservers();
		}
	}

	public synchronized boolean endTurn() {
		if (attacking) {
			// Do Nothing Please

		}
		int handSize = getHandSize();
		if (lastActionDraw) {
			handSize += 1;
		}
		if (handSize <= 10 && forceDiscard < 0) {
			actionNum = -1;
			needsToDiscard = false;
			ArrayList<Object> list = new ArrayList<Object>();
			list.add("endTurn");
			send(list);
			canDraw = true;
			return true;
		} else {
			needsToDiscard = true;
			if (forceDiscard > 0) {
				this.setChanged();
				this.notifyObservers("You need to discard " + forceDiscard
						+ " card(s)\n");
			} else if (forceDiscard == 0) {
				needsToDiscard = false;
				forceDiscard = -1;
				this.setChanged();
				this.notifyObservers();
			} else {
				forceDiscard = -1;
				this.setChanged();
				this.notifyObservers("You cannot have more than ten cards in your hand.\n Please discard down to ten.\n");
			}
			return false;
		}
	}

	public synchronized boolean needsToDiscard() {
		return needsToDiscard;
	}

	public synchronized Deck getDeck() {
		ArrayList<Player> myTeam = teams.get(teamChoice);
		Player myself = null;
		for (Player p : myTeam) {
			if (p.getPlayerNumber() == playerNumber) {
				myself = p;
			}
		}
		if (myself != null) {
			return myself.getDeck();
		} else {
			return null;
		}
	}

	public synchronized void characterReleasingFailed(Object o) {
		releasingCharacterFailed = true;
	}

	public synchronized boolean releasingCharacterFailed() {
		return releasingCharacterFailed;
	}

	public synchronized void characterChoiceFailed(Object o) {
		choosingCharacterFailed = true;
	}

	public synchronized boolean choosingCharacterFailed() {
		return choosingCharacterFailed;
	}

	public synchronized ArrayList<ArrayList<Player>> getTeams() {
		return teams;
	}

	public synchronized void teamSelectionFailed(Object o) {
		teamSelectionFailed = true;
	}

	public synchronized boolean teamSelectionFailed() {
		return teamSelectionFailed;
	}

	public synchronized void teamReleaseFailed(Object o) {
		teamReleaseFailed = true;
	}

	public synchronized boolean teamReleaseFailed() {
		return teamReleaseFailed;
	}

	public synchronized int getActionNum() {
		return actionNum;
	}

	public synchronized void skipAction() {

		if (actionNum == 0) {
			ArrayList<Object> list = new ArrayList<Object>();
			list.add("chat");
			list.add(this.name + " skipped roll and move");
			send(list);
			actionNum++;
		} else if (actionNum == 1) {
			ArrayList<Object> list = new ArrayList<Object>();
			list.add("chat");
			list.add(this.name + " skipped move");
			send(list);
		} else if (actionNum == 2 && !attacking) {
			ArrayList<Object> list = new ArrayList<Object>();
			list.add("chat");
			list.add(this.name + " skipped action 1");
			send(list);
		} else if (actionNum == 3 && !attacking) {
			ArrayList<Object> list = new ArrayList<Object>();
			list.add("chat");
			list.add(this.name + " skipped action 2");
			send(list);
		} else if (actionNum > 3) {
			if (!AI) {
				endTurn();
			}
		}
		actionNum++;
		if (actionNum == 2) {
			actionNum += futureActions;
			futureActions = 0;
		}
		if (!AI) {
			this.setChanged();
			this.notifyObservers();
		}
	}

	public synchronized void addAction(Object o) {
		// System.out.println(actionNum + " before");
		actionNum--;
		// System.out.println(actionNum + " after");
	}

	public synchronized void removeAction(Object o) {
		ArrayList<Object> list = (ArrayList<Object>) o;
		int amount = (Integer) list.get(1);
		futureActions += amount;
	}

	public synchronized void removeDraws(Object o) {
		canDraw = false;
	}

	public synchronized void removeMove(Object o) {
		canMove = false;
	}

	public synchronized boolean canDraw() {
		return canDraw;
	}

	public synchronized void draw(int num) {
		drawing = true;
		actionNum++;
		if (!AI && !hibernation) {
			if (actionNum > 3) {
				actionNum = -1;
				lastActionDraw = true;
				endTurn();
			}

		}
		ArrayList<Object> list = new ArrayList<Object>();
		list.add("draw");
		list.add(num);
		send(list);
	}

	public synchronized boolean isDrawing() {
		return drawing;
	}

	public synchronized Cards getCardInHand(int index) {
		ArrayList<Player> myTeam = teams.get(teamChoice);
		for (int i = 0; i < myTeam.size(); i++) {
			Player p = myTeam.get(i);
			if (p.getPlayerNumber() == playerNumber) {
				return p.getCardInHand(index);
			}
		}
		return null;
	}

	public synchronized int getHandValue() {
		ArrayList<Player> myTeam = teams.get(teamChoice);
		for (int i = 0; i < myTeam.size(); i++) {
			Player p = myTeam.get(i);
			if (p.getPlayerNumber() == playerNumber) {
				int handSize = p.getHandSize();
				int points = 0;
				for (int j = 0; j < handSize; j++)
					points += p.getCardInHand(j).getAIValue();

				return points;
			}
		}
		return 0;
	}

	public synchronized int getHandSize() {
		ArrayList<Player> myTeam = teams.get(teamChoice);
		for (int i = 0; i < myTeam.size(); i++) {
			Player p = myTeam.get(i);
			if (p.getPlayerNumber() == playerNumber) {
				handSize = p.getHandSize();
				return p.getHandSize();
			}
		}
		return 0;
	}

	public synchronized void discardCard(int index) {
		forceDiscard--;
		ArrayList<Object> list = new ArrayList<Object>();
		list.add("discardCard");
		list.add(index);
		send(list);
		discarding = true;
	}

	@SuppressWarnings("unchecked")
	public synchronized void discard(Object o) {
		ArrayList<Object> list = (ArrayList<Object>) o;
		int amount = (Integer) list.get(1);
		forceDiscard = amount;
		needsToDiscard = true;
		this.setChanged();
		this.notifyObservers("You need to discard " + amount + " card(s)\n");
	}

	public synchronized boolean isDiscarding() {
		return discarding;
	}

	public synchronized void discardReveal(int index) {
		ArrayList<Object> list = new ArrayList<Object>();
		list.add("discardReveal");
		list.add(index);
		send(list);
	}

	public synchronized void playCard(int index) {
		if (!hibernation) {
			hibernation = true;
			if (actionNum > 1) {
				ArrayList<Player> myTeam = teams.get(teamChoice);
				for (int i = 0; i < myTeam.size(); i++) {
					Player p = myTeam.get(i);
					if (p.getPlayerNumber() == playerNumber) {
						Cards card = p.getCardInHand(index);

						if (card.isAttackCard()) {
							setAttackableLocations(card.getCharacter(),
									card.getAttackValue());
							this.setChanged();
							this.notifyObservers(Action.ATTACK);
							return;
						} else if (card.isDefenseCard()) {
							return;
						} else {
							break;
						}
					}
				}
			}
			ArrayList<Player> myTeam = teams.get(teamChoice);
			for (int i = 0; i < myTeam.size(); i++) {
				Player p = myTeam.get(i);
				if (p.getPlayerNumber() == playerNumber) {
					Cards card = p.getCardInHand(index);
					if (Game.getSpecialName(card.getSpecialAttribute())
							.equalsIgnoreCase("Fire up the jet pack")
							|| Game.getSpecialName(card.getSpecialAttribute())
									.equalsIgnoreCase("Force Quickness")
							|| Game.getSpecialName(card.getSpecialAttribute())
									.equalsIgnoreCase("Children of the Force")) {
						removing = true;
						break;
					}
				}
			}
			ArrayList<Object> list = new ArrayList<Object>();
			list.add("playCard");
			list.add(index);
			send(list);
		}
	}

	public synchronized boolean playAttackCard(int index, int character,
			int attacker) {
		attackSelf = false;
		ArrayList<Player> myTeam = teams.get(teamChoice);
		for (int i = 0; i < myTeam.size(); i++) {
			Player p = myTeam.get(i);
			if (p.getPlayerNumber() == playerNumber) {
				if (p.getCardInHand(index).isAttackCard()) {
					attacking = true;
					ArrayList<Object> list = new ArrayList<Object>();
					list.add("playAttackCard");
					list.add(index);
					list.add(character);
					list.add(attacker);
					send(list);
					return true;
				}
			}
		}
		return false;
	}

	public synchronized boolean isAttacking() {
		return attacking;
	}

	@SuppressWarnings("unchecked")
	public synchronized void mountDefense(Object o) {
		ArrayList<Object> list = (ArrayList<Object>) o;
		attackedCharacter = (Integer) list.get(1);
		if (AI) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} else {
			this.setChanged();
			this.notifyObservers(Action.DEFEND);
		}
	}

	public synchronized boolean playDefendCard(int index) {
		if (index >= 0) {
			ArrayList<Player> myTeam = teams.get(teamChoice);
			for (int i = 0; i < myTeam.size(); i++) {
				Player p = myTeam.get(i);
				if (p.getPlayerNumber() == playerNumber) {
					Cards card = p.getCardInHand(index);
					if ((card.getCharacter().equals("minor") && attackedCharacter % 10 != 0)
							|| (card.getCharacter().equals("major") && attackedCharacter % 10 == 0)) {
						if (card.isDefenseCard()) {
							ArrayList<Object> list = new ArrayList<Object>();
							list.add("playDefendCard");
							list.add(index);
							send(list);
							return true;
						} else {
							ArrayList<Object> list = new ArrayList<Object>();
							list.add("playDefendCard");
							list.add(-1);
							send(list);
							return true;
						}
					}
				}
			}
		} else {
			ArrayList<Object> list = new ArrayList<Object>();
			list.add("playDefendCard");
			list.add(index);
			send(list);
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public synchronized void chat(Object o) {
		ArrayList<Object> list = (ArrayList<Object>) o;
		this.setChanged();
		this.notifyObservers((String) list.get(1) + "\n");
	}

	public synchronized void chat(String message) {
		ArrayList<Object> list = new ArrayList<Object>();
		list.add("chat");
		list.add(name + ": " + message);
		send(list);
	}

	public synchronized Character getMajorCharacter() {
		ArrayList<Player> myTeam = teams.get(teamChoice);
		for (int i = 0; i < myTeam.size(); i++) {
			Player p = myTeam.get(i);
			if (p.getPlayerNumber() == playerNumber) {
				return p.getMajorCharacter();
			}
		}
		return null;
	}

	public synchronized Character getMinorCharacter(int index) {
		ArrayList<Player> myTeam = teams.get(teamChoice);
		for (int i = 0; i < myTeam.size(); i++) {
			Player p = myTeam.get(i);
			if (p.getPlayerNumber() == playerNumber) {
				return p.getMinorCharacter(index);
			}
		}
		return null;
	}

	public synchronized int roll() {
		actionNum++;
		int roll = (int) (Math.round(Math.random() * 5) + 1);

		if (roll <= 3) {
			numToMove = 1;
			rollValue = roll + 2;
		} else {
			numToMove = getNumCharactersAlive();
			rollValue = roll - 2;
		}
		String rollValue = Game.rollValue(roll);
		ArrayList<Object> list = new ArrayList<Object>();
		list.add("rolled");
		list.add(rollValue);
		send(list);
		return roll;
	}

	public synchronized int getNumToMove() {
		return numToMove;
	}

	public synchronized int getLevel() {
		ArrayList<Player> myTeam = teams.get(teamChoice);
		for (int i = 0; i < myTeam.size(); i++) {
			Player p = myTeam.get(i);
			if (p.getPlayerNumber() == playerNumber) {
				return p.getLevel();
			}
		}
		return 0;
	}

	public synchronized boolean ownsCharacter(int characterNum) {
		return playerNumber / 10 == characterNum / 10;
	}

	public synchronized int getNumCharacters() {
		ArrayList<Player> myTeam = teams.get(teamChoice);
		for (int i = 0; i < myTeam.size(); i++) {
			Player p = myTeam.get(i);
			if (p.getPlayerNumber() == playerNumber) {
				return p.getNumMinors() + 1;
			}
		}
		return -1;
	}

	public synchronized int getNumCharactersAlive() {
		ArrayList<Player> myTeam = teams.get(teamChoice);
		Player p = null;
		int result = 0;
		for (int i = 0; i < myTeam.size(); i++) {
			p = myTeam.get(i);
			if (p.getPlayerNumber() == playerNumber) {
				break;
			}
		}
		if (p != null) {
			if (p.isAlive()) {
				result++;
			}
			for (int i = 0; i < p.getNumMinors(); i++) {
				if (p.minorIsAlive(i)) {
					result++;
				}
			}
		}
		return result;
	}

	public boolean majorIsRanged() {
		ArrayList<Player> myTeam = teams.get(teamChoice);
		for (int i = 0; i < myTeam.size(); i++) {
			Player p = myTeam.get(i);
			if (p.getPlayerNumber() == playerNumber) {
				return p.majorIsRanged();
			}
		}
		return false;
	}

	public boolean minorIsRanged(int index) {
		ArrayList<Player> myTeam = teams.get(teamChoice);
		for (int i = 0; i < myTeam.size(); i++) {
			Player p = myTeam.get(i);
			if (p.getPlayerNumber() == playerNumber) {
				return p.minorIsRanged(index);
			}
		}
		return false;
	}

	public boolean minorIsAlive(int index) {
		ArrayList<Player> myTeam = teams.get(teamChoice);
		for (int i = 0; i < myTeam.size(); i++) {
			Player p = myTeam.get(i);
			if (p.getPlayerNumber() == playerNumber) {
				return p.minorIsAlive(index);
			}
		}
		return false;
	}

	private void setAttackableLocations(String character, int attackValue) {
		attackableLocations = new ArrayList<Point>();
		if (attackValue > 0 || attackValue == -4) {
			if (character.equals("major")) {
				Point attacker = map.getLocationOf(playerNumber);
				attackableLocations.add(attacker);
				if (majorIsRanged()) {
					setRangedAttackableLocations(attacker);
				} else {
					setLocalAttackableLocations(attacker);
				}
			} else {
				for (int i = playerNumber + 1; i < playerNumber
						+ getNumCharacters(); i++) {
					if (minorIsAlive(i - playerNumber - 1)) {
						Point attacker = map.getLocationOf(i);
						attackableLocations.add(attacker);
						if (minorIsRanged(i - playerNumber - 1)) {
							setRangedAttackableLocations(attacker);
						} else {
							setLocalAttackableLocations(attacker);
						}
					}
				}
			}
		} else if (attackValue == -1) {
			Point attacker = map.getLocationOf(playerNumber);
			attackableLocations.add(attacker);
			setOpponentLocations();
		} else if (attackValue == -2) {
			// self minors only
			Point attacker = map.getLocationOf(playerNumber);
			attackableLocations.add(attacker);
			attackSelf = true;
			setMinorLocations();
		} else if (attackValue == -3) {
			// adjacent only
			Point attacker = map.getLocationOf(playerNumber);
			attackableLocations.add(attacker);
			setLocalAttackableLocations(attacker);
		} else if (attackValue == -5) {
			// minors only
			Point attacker = map.getLocationOf(playerNumber);
			attackableLocations.add(attacker);
			attackSelf = true;
			setAllMinorLocations();
		} else if (attackValue == -6) {
			// adjacent only
			Point attacker = map.getLocationOf(playerNumber + 1);
			attackableLocations.add(attacker);
			setLocalAttackableLocations(attacker);
		}
	}

	public synchronized boolean attackingSelf() {
		return attackSelf;
	}

	private void setOpponentLocations() {
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 10; j++) {
				Point location = new Point(i, j);
				if ((map.getNumAt(location) / 10) % 2 != (playerNumber / 10) % 2) {
					attackableLocations.add(location);
				}
			}
		}
	}

	private void setMinorLocations() {
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 10; j++) {
				Point location = new Point(i, j);
				if (map.getNumAt(location) / 10 == (playerNumber / 10)
						&& map.getNumAt(location) != playerNumber) {
					attackableLocations.add(location);
				}
			}
		}
	}

	private void setAllMinorLocations() {
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 10; j++) {
				Point location = new Point(i, j);
				if (map.getNumAt(location) % 10 != 0
						&& map.getNumAt(location) > 10) {
					attackableLocations.add(location);
				}
			}
		}
	}

	private void setRangedAttackableLocations(Point origin) {
		Point p = null;
		// on x in front of character
		for (int x = origin.x + 1; x < 7; x++) {
			p = new Point(x, origin.y);
			if (map.getNumAt(p) != 0 && map.getNumAt(p) != 2)
				break;
		}
		if (p != null
				&& ((map.getNumAt(p) > 9 && (map.getNumAt(p) / 10) % 2 != (playerNumber / 10) % 2) || map
						.getNumAt(p) == 3)) {
			attackableLocations.add(p);
		}

		// on x behind character
		for (int x = origin.x - 1; x >= 0; x--) {
			p = new Point(x, origin.y);
			if (map.getNumAt(p) != 0 && map.getNumAt(p) != 2)
				break;
		}
		if (p != null
				&& ((map.getNumAt(p) > 9 && (map.getNumAt(p) / 10) % 2 != (playerNumber / 10) % 2) || map
						.getNumAt(p) == 3)) {
			attackableLocations.add(p);
		}

		// on y above character
		for (int y = origin.y + 1; y < 10; y++) {
			p = new Point(origin.x, y);
			if (map.getNumAt(p) != 0 && map.getNumAt(p) != 2)
				break;
		}
		if (p != null
				&& ((map.getNumAt(p) > 9 && (map.getNumAt(p) / 10) % 2 != (playerNumber / 10) % 2) || map
						.getNumAt(p) == 3)) {
			attackableLocations.add(p);
		}

		// on y below character
		for (int y = origin.y - 1; y >= 0; y--) {
			p = new Point(origin.x, y);
			if (map.getNumAt(p) != 0 && map.getNumAt(p) != 2)
				break;
		}
		if (p != null
				&& ((map.getNumAt(p) > 9 && (map.getNumAt(p) / 10) % 2 != (playerNumber / 10) % 2) || map
						.getNumAt(p) == 3)) {
			attackableLocations.add(p);
		}

		// on diagonal front / below
		for (int x = origin.x + 1, y = origin.y + 1; x < 7 && y < 10; x++, y++) {
			p = new Point(x, y);
			if (map.getNumAt(p) != 0 && map.getNumAt(p) != 2)
				break;
		}
		if (p != null
				&& ((map.getNumAt(p) > 9 && (map.getNumAt(p) / 10) % 2 != (playerNumber / 10) % 2) || map
						.getNumAt(p) == 3)) {
			attackableLocations.add(p);
		}

		// on diagonal front / above
		for (int x = origin.x + 1, y = origin.y - 1; x < 7 && y >= 0; x++, y--) {
			p = new Point(x, y);
			if (map.getNumAt(p) != 0 && map.getNumAt(p) != 2)
				break;
		}
		if (p != null
				&& ((map.getNumAt(p) > 9 && (map.getNumAt(p) / 10) % 2 != (playerNumber / 10) % 2) || map
						.getNumAt(p) == 3)) {
			attackableLocations.add(p);
		}

		// on diagonal behind / above
		for (int x = origin.x - 1, y = origin.y - 1; x >= 0 && y >= 0; x--, y--) {
			p = new Point(x, y);
			if (map.getNumAt(p) != 0 && map.getNumAt(p) != 2)
				break;
		}
		if (p != null
				&& ((map.getNumAt(p) > 9 && (map.getNumAt(p) / 10) % 2 != (playerNumber / 10) % 2) || map
						.getNumAt(p) == 3)) {
			attackableLocations.add(p);
		}

		// on diagonal behind / below
		for (int x = origin.x - 1, y = origin.y + 1; x >= 0 && y < 10; x--, y++) {
			p = new Point(x, y);
			if (map.getNumAt(p) != 0 && map.getNumAt(p) != 2)
				break;
		}
		if (p != null
				&& ((map.getNumAt(p) > 9 && (map.getNumAt(p) / 10) % 2 != (playerNumber / 10) % 2) || map
						.getNumAt(p) == 3)) {
			attackableLocations.add(p);
		}
	}

	private void setLocalAttackableLocations(Point origin) {
		Point p = null;
		p = new Point(origin.x + 1, origin.y);
		if (p != null
				&& ((map.getNumAt(p) > 9 && (map.getNumAt(p) / 10) % 2 != (playerNumber / 10) % 2) || map
						.getNumAt(p) == 3)) {
			attackableLocations.add(p);
		}
		p = new Point(origin.x + 1, origin.y + 1);
		if (p != null
				&& ((map.getNumAt(p) > 9 && (map.getNumAt(p) / 10) % 2 != (playerNumber / 10) % 2) || map
						.getNumAt(p) == 3)) {
			attackableLocations.add(p);
		}
		p = new Point(origin.x, origin.y + 1);
		if (p != null
				&& ((map.getNumAt(p) > 9 && (map.getNumAt(p) / 10) % 2 != (playerNumber / 10) % 2) || map
						.getNumAt(p) == 3)) {
			attackableLocations.add(p);
		}
		p = new Point(origin.x - 1, origin.y + 1);
		if (p != null
				&& ((map.getNumAt(p) > 9 && (map.getNumAt(p) / 10) % 2 != (playerNumber / 10) % 2) || map
						.getNumAt(p) == 3)) {
			attackableLocations.add(p);
		}
		p = new Point(origin.x - 1, origin.y);
		if (p != null
				&& ((map.getNumAt(p) > 9 && (map.getNumAt(p) / 10) % 2 != (playerNumber / 10) % 2) || map
						.getNumAt(p) == 3)) {
			attackableLocations.add(p);
		}
		p = new Point(origin.x - 1, origin.y - 1);
		if (p != null
				&& ((map.getNumAt(p) > 9 && (map.getNumAt(p) / 10) % 2 != (playerNumber / 10) % 2) || map
						.getNumAt(p) == 3)) {
			attackableLocations.add(p);
		}
		p = new Point(origin.x, origin.y - 1);
		if (p != null
				&& ((map.getNumAt(p) > 9 && (map.getNumAt(p) / 10) % 2 != (playerNumber / 10) % 2) || map
						.getNumAt(p) == 3)) {
			attackableLocations.add(p);
		}
		p = new Point(origin.x + 1, origin.y - 1);
		if (p != null
				&& ((map.getNumAt(p) > 9 && (map.getNumAt(p) / 10) % 2 != (playerNumber / 10) % 2) || map
						.getNumAt(p) == 3)) {
			attackableLocations.add(p);
		}
	}

	public synchronized ArrayList<Point> getAttackableLocations() {
		return attackableLocations;
	}

	public synchronized boolean canAttack(int x, int y) {
		if (attackableLocations.contains(new Point(x, y))) {
			return true;
		}
		return false;
	}

	public synchronized boolean ownsCharacter(Point location) {
		return playerNumber / 10 == map.getNumAt(location) / 10;
	}

	public synchronized boolean isOwnedMajor(Point location) {
		return playerNumber == map.getNumAt(location);
	}

	public synchronized int getTeamChoice() {
		return teamChoice;
	}

	@SuppressWarnings("unchecked")
	public synchronized void show(Object o) {
		ArrayList<Object> list = (ArrayList<Object>) o;
		ArrayList<Cards> hand = (ArrayList<Cards>) list.get(1);
		this.setChanged();
		this.notifyObservers(hand);
	}

	public synchronized void nothing(Object o) {
		ArrayList<Object> list = (ArrayList<Object>) o;
		Integer hand = (Integer) list.get(1);
		this.setChanged();
		this.notifyObservers(hand);
	}

	public synchronized void chooseShow(Object o) {
		ArrayList<Object> list = (ArrayList<Object>) o;
		Integer amount = (Integer) list.get(1);
		// TODO: choose shown to be discarded
	}

	public synchronized boolean isFogOfWar() {
		return fogOfWar;
	}

	public synchronized void setFogOfWar(int amount) {
		fogOfWar = amount != 0;
	}

}
