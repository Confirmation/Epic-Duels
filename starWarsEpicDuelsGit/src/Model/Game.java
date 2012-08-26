/**
/ * This class controls the game, contains a list of client managers
 * that handle communications with the various clients and acts as
 * the server.
 */
package Model;

import java.awt.Point;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Observable;
import java.util.concurrent.LinkedBlockingQueue;

import mapPackage.MainMap;

import Model.Character;
import deckPackage.*;

public class Game extends Observable {

	// use Game.port in other classes so that changing the port will only
	// require us to change this number
	public static final int port = 4000;

	private int numPlayersDesired;
	private int numPlayers;
	private int numClients;
	private boolean original; // original or to spec
	private ArrayList<Character[]> availableCharacters;
	private ArrayList<Character[]> unavailableCharacters;
	private ArrayList<Team> teams;
	private MainMap map;
	private boolean waitingForEndTurn;
	private boolean waitingForPlayers;
	private int turn;
	private ClientManager attacker;
	private int attackValue;
	private Player attacking;
	private Cards attackerCard;
	private Cards playedCard;
	private int attackingCharacter;
	private int attackedCharacter;
	private ClientManager attacked;
	private Player defending;
	private boolean didDamage;
	private int damageAmount;
	private boolean unblocked;

	Deque<String[]> actions = null;

	private boolean isMoving = false;

	private boolean majorCard = true;

	private ServerSocket server;
	private LinkedBlockingQueue<ClientManager> removal;
	private LinkedList<ClientManager> clientManagers;
	private Thread gameThread;

	public Game() {
		// only exists for testing without creating the server or thread.
	}

	public Game(int numPlayers, boolean original, MainMap map) {
		this.numPlayersDesired = numPlayers;
		this.original = original;
		this.map = map;
		newGame();
	}

	// set the available characters
	public static ArrayList<Character[]> getCharacterList() {
		ArrayList<Character[]> availableCharacters = new ArrayList<Character[]>();
		Character[] group = new Character[2];
		group[0] = new Character(12, 18, "ObiWan Kenobi", 65, 14, false); // 65
																			// 14
		group[1] = new Character(12, 4, "Clone Trooper", 30, 30, true);
		availableCharacters.add(group);
		group = new Character[2];
		group[0] = new Character(12, 17, "Luke Skywalker", 42, 20, false);
		group[1] = new Character(12, 10, "Leia Skywalker", 35, 35, true);
		availableCharacters.add(group);
		group = new Character[2];
		group[0] = new Character(12, 15, "Yoda", 45, 11, false);
		group[1] = new Character(12, 4, "Clone Trooper", 30, 30, true);
		availableCharacters.add(group);
		group = new Character[2];
		group[0] = new Character(12, 13, "Han Solo", 50, 30, true);
		group[1] = new Character(12, 15, "Chewbacca", 65, 65, true);
		availableCharacters.add(group);
		group = new Character[2];
		group[0] = new Character(12, 20, "Darth Vader", 46, 18, false);
		group[1] = new Character(12, 4, "Stormtrooper", 30, 30, true);
		availableCharacters.add(group);
		group = new Character[2];
		group[0] = new Character(12, 18, "Darth Maul", 70, 20, false);
		group[1] = new Character(12, 3, "Battle Droid", 20, 20, true);
		availableCharacters.add(group);
		group = new Character[2];
		group[0] = new Character(12, 15, "Jango Fett", 55, 30, true);
		group[1] = new Character(12, 10, "Zam Wesell", 45, 45, true);
		availableCharacters.add(group);
		group = new Character[2];
		group[0] = new Character(12, 13, "Emperor Palpatine", 50, 30, false);
		group[1] = new Character(12, 5, "Royal Guard", 35, 35, true);
		availableCharacters.add(group);
		// System.out.println(availableCharacters);
		return availableCharacters;
	}

	public void newGame() {
		numPlayers = 0;
		numClients = 0;
		waitingForPlayers = true;

		unavailableCharacters = new ArrayList<Character[]>();
		availableCharacters = getCharacterList();

		teams = new ArrayList<Team>();
		teams.add(new Team());
		teams.add(new Team());

		clientManagers = new LinkedList<ClientManager>();
		removal = new LinkedBlockingQueue<ClientManager>();
		gameThread = new Thread(new GameThread());
		gameThread.start();
	}

	private static final String separator = " : ";
	private static final String iff = "}";
	private static final String otherwise = ";";

	public static String getSpecialName(String special) {
		String[] actions = special.split(separator);
		return actions[0];
	}

	public synchronized void interpreter(String special, ClientManager client) {
		if (special != null) {
			chat(client.getName() + " Played " + getSpecialName(special));
		}
		// System.out.println("interpretting:");
		Team team;
		Player player = null;
		for (int i = 0; i < teams.size(); i++) {
			team = teams.get(i);
			if (team.contains(client)) {
				player = team.getPlayer(client);
			}
		}
		if (special == null || player == null) { // if no special action do
													// nothing
			// System.out.println("   Null"); // debug statement
			client.wakeUp();
			return;

			// if special action contains an else identifier then it must be
			// Luke's weird special
		} else if (special.contains(otherwise)) {
			// System.out
			// .println("   If major adjacent, then heal major 3, else if major dead, heal minor 3");
			// // debug
			// statement
			if (player.majorIsAlive()
					&& adjacent(player.getMajorCharacter(),
							player.getMinorCharacter(0))) {
				player.majorHeal(3);
			} else if (!player.majorIsAlive()) {
				player.minorHeal(0, 3);
			}
			client.wakeUp();

		} else { // interpret the special attribute

			// separate the commands via : and remove the special's name
			ArrayList<String> commands = parse(special);

			// a stack of actions to be executed in order
			actions = new ArrayDeque<String[]>();
			for (String s : commands) { // loop through all of the commands
				if (s.contains(iff)) { // if an if statement (}) is present
					String[] conditional = s.split(iff); // split about the if
															// character
					// System.out.println("   if " + conditional[0] +
					// ", then "); // debug
					// statement
					if (check(conditional[0].trim(), player)) { // if the
																// condition in
						// conditional[0] is true

						// put the action on the stack
						actions.addLast(conditional[1].trim().split(" "));
					}
				} else { // if no if statement then the action will always
							// happen
					actions.addLast(s.split(" ")); // put the action on the
													// stack
				}
			}
			nextInstruction(client);
		}
	}

	public synchronized void nextInstruction(ClientManager client) {
		if (actions != null && actions.size() > 0) {
			String[] s = actions.getFirst();
			actions.removeFirst();
			for (int i = 0; i < s.length; i++) {// loop through keywords in
				// action
				// System.out.print(s[i] + " ");// debug statement
			}
			// System.out.println(); // debug statement
			try {
				if (!s[0].trim().isEmpty()) {
					Method method = this.getClass().getMethod(s[0],
							String[].class, ClientManager.class);
					method.invoke(this, (Object[]) s, client);
				}
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
		} else {
			System.out.println("Client is waking up now");
			actions = null;
			client.wakeUp();
		}
	}

	// part of the interpreter. It returns a list of commands without the
	// specials name
	private synchronized ArrayList<String> parse(String special) {
		// separate the commands
		String[] actions = special.split(separator);

		// put the commands into a list
		ArrayList<String> result = new ArrayList<String>();
		for (int i = 1; i < actions.length; i++) {
			result.add(actions[i]);
		}
		return result;
	}

	// part of the interpreter
	private synchronized boolean check(String condition, Player player) {
		// System.out.println("|" + condition + "|");
		if (condition.equalsIgnoreCase("Dead minor")) {
			if (getNumMinors(player.getMajorCharacter()) == 1)
				return !player.minorIsAlive(0);
			else
				return !(player.minorIsAlive(0) || player.minorIsAlive(1));
		} else if (condition.equalsIgnoreCase("Damage")) {
			// System.out.println("|" + condition + "| " + didDamage + " : "
			// + damageAmount);
			return didDamage;
		} else if (condition.equalsIgnoreCase("Map Kamino")) {
			return map.getName().equalsIgnoreCase("Kamino");
		}
		return true;
	}

	// returns the number of minors for a given character
	public synchronized int getNumMinors(Character character) {
		if ((character.getCharacterName()).equalsIgnoreCase("Luke Skywalker")
				|| (character.getCharacterName()).equalsIgnoreCase("Han Solo")
				|| (character.getCharacterName())
						.equalsIgnoreCase("Jango Fett")) {
			return 1;
		} else {
			return 2;
		}
	}

	// returns a given characters deck
	public synchronized Deck getDeck(Character character) {
		if ((character.getCharacterName()).equalsIgnoreCase("Luke Skywalker")) {
			return new LukeSkyWalkerDeck("Luke");
		} else if ((character.getCharacterName())
				.equalsIgnoreCase("Jango Fett")) {
			return new JangoDeck("Jango");
		} else if ((character.getCharacterName())
				.equalsIgnoreCase("ObiWan Kenobi")) {
			return new ObiWanDeck("ObiWan");
		} else if ((character.getCharacterName()).equalsIgnoreCase("Han Solo")) {
			return new HanSoloDeck("Han");
		} else if ((character.getCharacterName()).equalsIgnoreCase("Yoda")) {
			return new YodaDeck("Yoda");
		} else if ((character.getCharacterName())
				.equalsIgnoreCase("Darth Vader")) {
			return new DarthVaderDeck("Darth Vader");
		} else if ((character.getCharacterName())
				.equalsIgnoreCase("Darth Maul")) {
			return new DarthMaulDeck("Darth Maul");
		} else if ((character.getCharacterName())
				.equalsIgnoreCase("Emperor Palpatine")) {
			return new EmperorDeck("Emperor");
		}
		return null;
	}

	public synchronized ArrayList<Character[]> getAvailableCharacters() {
		return availableCharacters;
	}

	public synchronized ArrayList<Character[]> getUnavailableCharacters() {
		return unavailableCharacters;
	}

	// removes a character from the available list and adds it to the
	// unavailable list
	public synchronized boolean makeUnavailable(Character[] character) {
		boolean result = false;
		for (int i = 0; i < availableCharacters.size(); i++) {
			Character[] c = availableCharacters.get(i);
			if (c[0].compareTo(character[0]) == 0) {
				availableCharacters.remove(i);
				unavailableCharacters.add(character);
				result = true;
				break;
			}
			// System.out.println(i);
		}
		if (result) {
			alertAvailabilityChange();
		}
		return result;
	}

	public synchronized boolean makeAvailable(Character[] character) {
		boolean result = false;
		for (int i = 0; i < unavailableCharacters.size(); i++) {
			Character[] c = unavailableCharacters.get(i);
			if (c[0].compareTo(character[0]) == 0) {
				unavailableCharacters.remove(i);
				availableCharacters.add(character);
				result = true;
				break;
			}
		}
		if (result) {
			alertAvailabilityChange();
		}
		return result;
	}

	public synchronized ArrayList<ArrayList<Player>> getTeams() {
		ArrayList<ArrayList<Player>> result = new ArrayList<ArrayList<Player>>();
		for (int i = 0; i < teams.size(); i++) {
			result.add((teams.get(i)).getPlayerList());
		}
		return result;
	}

	// removes a player from the game
	public synchronized boolean removePlayer(ClientManager client) {
		boolean result = false;
		Player player;
		if ((teams.get(0)).contains(client)) {
			player = (teams.get(0)).removePlayer(client);
			result = true;
		} else {
			player = (teams.get(1)).removePlayer(client);
			result = true;
		}
		if (result) {
			numPlayers--;
			alertTeamChange();
			removePlayerFromMap(player);
		}
		return result;
	}

	// adds a player to the game on a given team
	public synchronized boolean addPlayer(Player player, Integer team,
			ClientManager client) {
		boolean result = false;
		int numTeams = teams.size();
		int playersPerTeam = numPlayersDesired / numTeams;
		int teamMates = (teams.get(team)).size();
		if (teamMates < playersPerTeam) {
			result = true;
			(teams.get(team)).add(player, client);
			player.setCharacterNumbers(team, teamMates);
			client.setPlayerNumber(player.getPlayerNumber());
			ArrayList<Object> list = new ArrayList<Object>();
			list.add("setPlayerNumber");
			list.add(player.getPlayerNumber());
			if (client != null)
				client.send(list);
			alertTeamChange();
			list = new ArrayList<Object>();
			list.add("chat");
			list.add(client.getName() + " has joined team " + (team + 1)
					+ " as " + player.getMajorCharacterName() + ".");
			for (ClientManager manager : clientManagers) {
				manager.send(list);
			}
			addPlayerToMap(player);
			numPlayers++;
		}
		return result;
	}

	public int startX(int i) {
		if (i == 5 || i == 6 || i == 7) {
			return -1;
		} else if (i == 0 || i == 4) {
			return 0;
		} else if (i == 1 || i == 2 || i == 3) {
			return 1;
		} else {
			System.exit(0);
			return -Integer.MAX_VALUE;
		}
	}

	public int startY(int i) {
		if (i == 1 || i == 0 || i == 7) {
			return 1;
		} else if (i == 6 || i == 2) {
			return 0;
		} else if (i == 5 || i == 4 || i == 3) {
			return -1;
		} else {
			System.exit(0);
			return -Integer.MAX_VALUE;
		}
	}

	public synchronized void addPlayerToMap(Player player) {
		int x = (int) Math.round(Math.random() * 6);
		int y = (int) Math.round(Math.random() * 9);
		int team = (player.getPlayerNumber() / 10) % 2;
		while (map.getNumAt(new Point(x, y)) != team + 4) {
			x = (int) Math.round(Math.random() * 6);
			y = (int) Math.round(Math.random() * 9);
		}
		Point location = new Point(x, y);
		Character character = player.getMajorCharacter();
		character.setCharacterLocation(location);
		map.addCharacter(character, location);
		int minorNum = 0;
		for (int i = 0; minorNum < player.getNumMinors(); i++) {
			Point minorLocation = new Point(location.x + startX(i), location.y
					+ ((-2 * team + 1) * startY(i)));
			if (map.getNumAt(minorLocation) != 0) {
				continue;
			}
			character = player.getMinorCharacter(minorNum);
			character.setCharacterLocation(minorLocation);
			map.addCharacter(character, minorLocation);
			minorNum++;
		}
		alertMapChange();
	}

	public synchronized void removePlayerFromMap(Player player) {
		Point location = player.getMajorLocation();
		map.removeCharacter(location);
		for (int i = 0; i < player.getNumMinors(); i++) {
			location = player.getMinorLocation(i);
			map.removeCharacter(location);
		}
		alertMapChange();
	}

	public synchronized MainMap getMap() {
		return map;
	}

	// remove specified clients
	public synchronized void removeClients() {
		while (!removal.isEmpty()) {
			clientManagers.remove(removal.poll());
			numClients--;
		}
	}

	public synchronized void addClientForRemoval(ClientManager client) {
		removal.add(client);
	}

	public synchronized int getFogOfWar() {
		if (original) {
			return 0;
		} else {
			return 1;
		}
	}

	private class GameThread implements Runnable {

		@Override
		public void run() {
			try {
				server = new ServerSocket(port);
				// always wait for more connections.
				// When a new client new connects,
				// add its thread to the list called clients, and start a new
				// thread
				// for it.
				for (numClients = 0; numClients != numPlayersDesired; numClients++) {
					Socket socket = server.accept();
					ClientManager newManager = new ClientManager(socket,
							Game.this);
					clientManagers.add(newManager);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			while (numPlayers != numPlayersDesired) {
				// something must be in this while loop or it never ends.
				// wait for numPlayers to be d by the clients choosing a
				// character and a team
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			map.removeStarts();
			alertMapChange();
			// ArrayList<Object> list = new ArrayList<Object>();
			// list.add("chat");
			chat("The game will begin shortly.");
			// for (ClientManager manager : clientManagers) {
			// manager.send(list);
			// }
			waitingForPlayers = false;
			turn = 10;
			while (Game.this.isGoing()) {
				if (Game.this.playerIsAlive(turn)) {
					ClientManager client = getClient(turn);
					// list = new ArrayList<Object>();
					// list.add("chat");

					chat(client.getCharacterName() + "'s (" + client.getName()
							+ "'s) turn.");
					// for (ClientManager manager : clientManagers) {
					// manager.send(list);
					// }
					ArrayList<Object> list = new ArrayList<Object>();
					list.add("setTurn");
					list.add(turn);
					for (ClientManager manager : clientManagers) {
						manager.send(list);
					}
					waitingForEndTurn = true;
					while (waitingForEndTurn) {
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				turn += 10;
				if (turn > numPlayersDesired * 10) {
					turn = 10;
				}
			}
			chat("GAME OVER");
			chat(winner() + " has won.");
			if (server != null) {
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public synchronized void kill() {
		if (server != null) {
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void move(ClientManager clientManager, Point location,
			Point destination, boolean alert) {
		if (location != null && destination != null) {
			if ((teams.get(0)).contains(clientManager)) {
				(teams.get(0)).move(clientManager, location, destination);
				for (int i = 0; i < teams.get(0).size(); i++) {
					Player p = teams.get(0).getPlayerList().get(i);
					if (p.getPlayerNumber() == clientManager.getPlayerNumber()) {
						chat(clientManager.getName()
								+ "'s "
								+ p.getCharacter(map.getNumAt(location))
										.getCharacterName()
								+ "\n\t has moved "
								+ (Math.abs(location.x - destination.x) + Math
										.abs(location.y - destination.y))
								+ " spaces");
					}
				}

			} else {
				(teams.get(1)).move(clientManager, location, destination);
				for (int i = 0; i < teams.get(1).size(); i++) {
					Player p = teams.get(1).getPlayerList().get(i);
					if (p.getPlayerNumber() == clientManager.getPlayerNumber()) {
						chat(clientManager.getName()
								+ "'s "
								+ p.getCharacter(map.getNumAt(location))
										.getCharacterName()
								+ "\n\t has moved "
								+ (Math.abs(location.x - destination.x) + Math
										.abs(location.y - destination.y))
								+ " spaces");
					}
				}
			}
			map.moveCharacter(location, destination);
		}
		if (alert) {
			alertMapChange();
		}
		// System.out.println(playedCard);
		if (actions != null)
			this.nextInstruction(clientManager);
	}

	public synchronized void alertMapChange() {
		this.setChanged();
		this.notifyObservers();
		ArrayList<Object> list = new ArrayList<Object>();
		list.add("setMap");
		list.add(getMap());
		for (ClientManager manager : clientManagers) {
			manager.send(list);
		}
	}

	public synchronized void alertAvailabilityChange() {
		ArrayList<Object> list = new ArrayList<Object>();
		list.add("setCharacterAvailability");
		list.add(availableCharacters);
		list.add(unavailableCharacters);
		for (ClientManager manager : clientManagers) {
			manager.send(list);
		}
	}

	public synchronized void alertTeamChange() {
		ArrayList<Object> list = new ArrayList<Object>();
		list.add("setTeams");
		list.add(getTeams());
		for (ClientManager manager : clientManagers) {
			manager.send(list);
		}
	}

	public synchronized boolean isGoing() {
		int numTeamsAlive = 0;
		for (Team t : teams) {
			if (t.isAlive()) {
				numTeamsAlive++;
			}
		}
		return numTeamsAlive > 1;
	}

	public synchronized String winner() {
		Team t;
		for (int i = 0; i < teams.size(); i++) {
			t = teams.get(i);
			if (t.isAlive()) {
				return "Team " + (i + 1) + " ";
			}
		}
		return null;
	}

	public synchronized void endTurn(ClientManager client) {
		if ((turn / 10) % 2 == 0) {
			waitingForEndTurn = !teams.get(1).contains(client);
		} else {
			waitingForEndTurn = !teams.get(0).contains(client);
		}
	}

	public synchronized boolean playerIsAlive(int turn) {
		if ((turn / 10) % 2 == 0) {
			return teams.get(1).playerIsAlive(turn);
		} else {
			return teams.get(0).playerIsAlive(turn);
		}
	}

	public synchronized ClientManager getClient(int turn) {
		Team t = null;
		if ((turn / 10) % 2 == 0) {
			t = teams.get(1);
		} else {
			t = teams.get(0);
		}
		return t.getClient(turn);
	}

	public synchronized int getNumClients() {
		return numClients;
	}

	public synchronized int getNumPlayers() {
		return numPlayersDesired;
	}

	public synchronized int getNumConnectedPlayers() {
		return numPlayers;
	}

	public synchronized boolean isWaitingForPlayers() {
		return waitingForPlayers;
	}

	public synchronized void draw(int num, ClientManager client) {
		if (!client.isAI() || this.getClient(this.turn).equals(client)) {
			if ((teams.get(0)).contains(client)) {
				(teams.get(0)).draw(num, client);
				chat(client.getName() + " drew " + num + " card(s)");
			} else {
				(teams.get(1)).draw(num, client);
				chat(client.getName() + " drew " + num + " card(s)");
			}
		}
		alertTeamChange();
	}

	public synchronized void discardCard(int index, ClientManager client) {
		if ((teams.get(0)).contains(client)) {
			if ((teams.get(0)).getPlayer(client).getCardInHand(index)
					.getSpecialAttribute() != null) {
				chat(client.getName()
						+ " discarded "
						+ getSpecialName((teams.get(0)).getPlayer(client)
								.getCardInHand(index).getSpecialAttribute()));
			} else
				chat(client.getName() + " discarded.");
			(teams.get(0)).discardCard(index, client);

		} else {
			if ((teams.get(1)).getPlayer(client).getCardInHand(index)
					.getSpecialAttribute() != null) {
				chat(client.getName()
						+ " discarded "
						+ getSpecialName((teams.get(1)).getPlayer(client)
								.getCardInHand(index).getSpecialAttribute()));
			} else
				chat(client.getName() + " discarded.");
			(teams.get(1)).discardCard(index, client);
		}
		alertTeamChange();
	}

	public synchronized Cards getCard(int index, ClientManager client) {
		Cards card = null;
		if ((teams.get(0)).contains(client)) {
			card = (teams.get(0)).playCard(index, client);
		} else {
			card = (teams.get(1)).playCard(index, client);
		}
		return card;
	}

	public synchronized void playCard(int index, ClientManager client) {
		Cards card = getCard(index, client);
		if (card != null) {
			majorCard = card.getCharacter().equals("major");
			playCard(card, client);
		}
		alertTeamChange();
	}

	public synchronized void playCard(Cards card, ClientManager client) {
		majorCard = card.getCharacter().equals("major");
		playedCard = card;
		interpreter(card.getSpecialAttribute(), client);
		alertTeamChange();
		alertMapChange();
		// System.out.println(client.getName() + ": playing card: " + card);
	}

	public synchronized void playAttackCard(int index, int character,
			int attacker, ClientManager client) {
		if (this.getClient(turn).equals(client)) {
			Cards card = getCard(index, client);
			majorCard = card.getCharacter().equals("major");
			if (card != null) {
				playAttackCard(card, character, attacker, client);
			}
		}
		alertTeamChange();
	}

	public synchronized void playAttackCard(Cards card, int character,
			int attacker, ClientManager client) {
		if (this.getClient(turn).equals(client)) {
			majorCard = card.getCharacter().equals("major");
			this.attacker = client;
			attacking = getPlayer(this.attacker);
			attackingCharacter = attacker;
			attackerCard = card;
			attackValue = card.getAttackValue();
			attacked = getOwningClient(character);
			defending = getPlayer(attacked);
			attackedCharacter = character;
			if (attackValue > 0) {
				chat(client.getName() + "'s "
						+ attacking.getCharacterName(card.getCharacter())
						+ " is attacking " + "\n\t" + attacked.getName()
						+ "'s " + defending.getCharacterName(character));
				ArrayList<Object> list = new ArrayList<Object>();
				list.add("mountDefense");
				list.add(character);
				attacked.send(list);
			} else {
				interpreter(card.getSpecialAttribute(), client);
				alertTeamChange();
				alertMapChange();
			}
		}
	}

	public synchronized void playDefendCard(int index, ClientManager client) {
		int defend = 0;
		if (index == -1) {
			unblocked = true;
			defend = 0;
		} else {
			unblocked = false;
			Cards card = getCard(index, client);
			defend = card.getDefenseValue();
			if (defend == 0 || defend == -1) {
				defend = 0;
			} else if (defend == -2) {
				attacked = attacker;
				attackedCharacter = attackingCharacter;
				defend = 0;
			}
			interpreter(card.getSpecialAttribute(), client);
		}
		damageAmount = attackValue - defend;
		didDamage = damageAmount > 0;
		interpreter(attackerCard.getSpecialAttribute(), attacker);
		attack(attackValue - defend);
		damageAmount = 0;
		didDamage = false;
		attacked = null;
		attacker = null;
		attackerCard = null;
		attackValue = -1;
		attackedCharacter = -1;
		alertTeamChange();
		alertMapChange();
	}

	public synchronized void attack(int amount) {
		if (amount < 0) {
			amount = 0;
		}
		Player p = getPlayer(attacked);
		boolean killed = false;
		didDamage = false;
		// System.out.println("DAMAGE: " + amount);
		if (p.characterIsAlive(attackedCharacter)) {
			damageAmount = amount;
			didDamage = damageAmount > 0;
			// System.out.println(didDamage + "| |" + damageAmount);
			killed = p.takeDamage(amount, attackedCharacter);
		}
		if (killed) {
			map.removeCharacter(attackedCharacter);
			chat(p.getName() + "'s "
					+ p.getCharacter(attackedCharacter).getCharacterName()
					+ " has died");
			if (!original) {
				getPlayer(attacker).levelUp();
				chat(attacker.getName() + " has leveled up to "
						+ getPlayer(attacker).getLevel());
			}
		} else {
			if (p.characterIsAlive(attackedCharacter)) {
				ClientManager client = getOwningClient(attackedCharacter);
				chat(client.getName() + "'s "
						+ p.getCharacterName(attackedCharacter) + " took "
						+ amount + " damage.");
			}
		}
	}

	public synchronized ClientManager getOwningClient(int character) {
		for (ClientManager client : clientManagers) {
			if (client.getPlayerNumber() / 10 == character / 10) {
				return client;
			}
		}
		return null;
	}

	public synchronized Player getPlayer(ClientManager client) {
		if (teams.get(0).contains(client)) {
			return teams.get(0).getPlayer(client);
		} else {
			return teams.get(1).getPlayer(client);
		}
	}

	public synchronized void chat(String message) {
		ArrayList<Object> list = new ArrayList<Object>();
		list.add("chat");
		list.add(message);
		for (ClientManager manager : clientManagers) {
			manager.send(list);
		}
	}

	public synchronized void rolled(String name, String roll) {
		ArrayList<Object> list = new ArrayList<Object>();
		list.add("chat");
		list.add(name + " rolled a " + roll);
		for (ClientManager manager : clientManagers) {
			manager.send(list);
		}
	}

	public static String rollValue(int roll) {
		if (roll == 1) {
			return "3";
		} else if (roll == 2) {
			return "4";
		} else if (roll == 3) {
			return "5";
		} else if (roll == 4) {
			return "2 All";
		} else if (roll == 5) {
			return "3 All";
		} else if (roll == 6) {
			return "4 All";
		} else {
			return "!!!ROLLING ERROR!!!";
		}
	}

	// In progress
	public synchronized void skipped(String name, ClientManager client) {
		ArrayList<Object> list = new ArrayList<Object>();
		list.add("chat");
		list.add(client.getName() + " has joined team ");
		for (ClientManager manager : clientManagers) {
			manager.send(list);
		}
	}

	public boolean adjacent(Character one, Character two) {
		Point oneLocation = map.getLocationOf(one.getCharacterNumber());
		Point twoLocation = map.getLocationOf(two.getCharacterNumber());
		return oneLocation.distance(twoLocation) < 2;
	}

	//
	// The methods for the special attribute interpreter:
	//

	public void Draw(String[] list, ClientManager client) {
		if (list.length > 1 && list[1].equalsIgnoreCase("all")) {
			int num = Integer.parseInt(list[2]);
			// System.out.println("ALL Draw " + num);
			for (ClientManager manager : clientManagers) {
				draw(num, manager);
			}
			this.nextInstruction(client);
		} else {
			int num = Integer.parseInt(list[1]);
			// System.out.println("Draw " + num);
			draw(num, client);
			this.nextInstruction(client);
		}
	}

	public void Attack(String[] list, ClientManager client) {
		int increase = Integer.parseInt(list[1]);
		attackValue += increase;
		this.nextInstruction(client);
	}

	public void Show(String[] list, ClientManager client) {
		if (list.length > 1) {
			if (list[1].equalsIgnoreCase("Self")) {
				for (ClientManager clients : clientManagers) {
					ArrayList<Object> lists = new ArrayList<Object>();
					lists.add("show");
					lists.add(getPlayer(client).getHand());
					clients.send(lists);
				}
			} else if (list[1].equalsIgnoreCase("Enemy")) {
				for (ClientManager clients : clientManagers) {
					ArrayList<Object> lists = new ArrayList<Object>();
					lists.add("show");
					lists.add(getPlayer(attacked).getHand());
					clients.send(lists);
				}
			} else if (list[1].equalsIgnoreCase("graveyard")) {
				// not a show, changed from original and I'm too lazy to put it
				// elsewhere
				Player p = getPlayer(client);
				int index = 0;
				int previous = -1;
				for (int i = 0; i < p.getGraveSize(); i++) {
					if (p.getCardInGrave(i).getAIValue() > previous) {
						index = i;
					}
					previous = p.getCardInGrave(i).getAIValue();
				}
				p.ungraveCard(index);
			}
		} else {
			for (ClientManager clients : clientManagers) {
				ArrayList<Object> lists = new ArrayList<Object>();
				lists.add("show");
				lists.add(getPlayer(attacked).getHand());
				clients.send(lists);
			}
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (ClientManager clients : clientManagers) {
			ArrayList<Object> lists = new ArrayList<Object>();
			lists.add("nothing");
			lists.add(13);
			clients.send(lists);
		}
		this.nextInstruction(client);
	}

	public void discardReveal(int index) {
		getPlayer(attacked).discardCard(index);
		for (ClientManager clients : clientManagers) {
			ArrayList<Object> lists = new ArrayList<Object>();
			lists.add("nothing");
			lists.add(13);
			clients.send(lists);
		}
		alertTeamChange();
	}

	public void Discard(String[] list, ClientManager client) {
		if (list[1].equalsIgnoreCase("Attack>1")) {
			Player p = getPlayer(client);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (int i = p.getHandSize() - 1; i >= 0; i--) {
				if (p.getCardInHand(i).getAttackValue() > 1) {
					p.discardCard(i);
				}
			}
			p = getPlayer(attacked);
			for (int i = p.getHandSize() - 1; i >= 0; i--) {
				if (p.getCardInHand(i).getAttackValue() > 1) {
					p.discardCard(i);
				}
			}
		} else if (list[1].equalsIgnoreCase("1")) {
			Player p = getPlayer(attacked);
			p.discardCard((int) Math.round(Math.random()
					* (p.getHandSize() - 1)));
		} else if (list[1].equalsIgnoreCase("best")) {
			int amount = Integer.parseInt(list[2]);
			Player p = getPlayer(attacked);
			int index = 0;
			int previous = -1;
			for (int i = 0; i < p.getHandSize(); i++) {
				if (p.getCardInHand(i).getAIValue() > previous) {
					index = i;
				}
				previous = p.getCardInHand(i).getAIValue();
			}
			p.discardCard(index);
			// System.out.println("chose and discard " + amount);
		} else if (list[1].equalsIgnoreCase("all")) {
			for (ClientManager clients : clientManagers) {
				Player p = getPlayer(clients);
				while (!p.handIsEmpty()) {
					p.discardCard(0);
				}
			}
		} else if (list[1].equalsIgnoreCase("Special")) {
			Player p = getPlayer(attacked);
			for (int i = p.getHandSize() - 1; i >= 0; i--) {
				if (p.getCardInHand(i).getAttackValue() <= 0
						&& p.getCardInHand(i).getDefenseValue() <= 0) {
					p.discardCard(i);
				}
			}
		} else if (list[1].equalsIgnoreCase("Choice")) {
			int amount = Integer.parseInt(list[2]);
			Player p = getPlayer(attacked);
			for (int i = 0; i < amount; i++) {
				p.discardCard((int) Math.round(Math.random()
						* (p.getHandSize() - 1)));
			}
		} else if (list[1].equalsIgnoreCase("*")) {
			Player p = getPlayer(attacked);
			while (!p.handIsEmpty()) {
				p.discardCard(0);
			}
		}
		this.nextInstruction(client);
	}

	public void Move(String[] list, ClientManager client) {
		isMoving = true;
		if (list[1].equalsIgnoreCase("major")) {
			int amount = Integer.parseInt(list[2]);
			ArrayList<Object> lists = new ArrayList<Object>();
			lists.add("moveAmount");
			lists.add(true);
			lists.add(amount);
			client.send(lists);
			// System.out.println("Move major" + amount);
		} else if (list[1].equalsIgnoreCase("minor")) {
			int amount = Integer.parseInt(list[2]);
			ArrayList<Object> lists = new ArrayList<Object>();
			lists.add("moveAmount");
			lists.add(false);
			lists.add(amount);
			client.send(lists);
			// System.out.println("Move minor" + amount);
		} else if (list[1].equalsIgnoreCase("Minor/Major")) {
			int amount = Integer.parseInt(list[2]);
			client.isMoving();
			ArrayList<Object> lists = new ArrayList<Object>();
			lists.add("moveAmount");
			lists.add(false);
			lists.add(amount);
			client.send(lists);
			// System.out.println("Move minor/major" + amount);
		} else if (list[1].equalsIgnoreCase("Enemy")) {
			int amount = 0;
			if (list[2].contains("*")) {
				amount = 15;
			} else {
				amount = Integer.parseInt(list[2]);
			}
			Point location = map.getLocationOf(attackedCharacter);
			map.displayPossiblePaths(location,
					(int) Math.round(Math.random() * amount));
			Point destination = map.getLocationOf(6);
			for (int i = 0; i < Math.random(); i++) {
				map.addCharacter(0, destination);
				destination = map.getLocationOf(6);
			}
			move(attacked, location, destination, true);
			map.removePossiblePaths();
			this.nextInstruction(client);
			// System.out.println("Move Enemy" + amount);
		} else if (list[1].equalsIgnoreCase("*")) {
			ArrayList<Object> lists = new ArrayList<Object>();
			lists.add("moveAnywhere");
			lists.add(majorCard);
			client.send(lists);
			// System.out.println("Move to anywhere");
		} else if (list[1].equalsIgnoreCase("All")) {
			int amount = Integer.parseInt(list[2]);
			for (int i = 10; i <= numPlayersDesired * 10; i += 10) {
				for (int j = 0; j < 10; j++) {
					Point location = map.getLocationOf(i + j);
					ClientManager manager = getOwningClient(i + j);
					if (location != null) {
						map.displayPossiblePaths(location,
								(int) Math.round(Math.random() * amount));
						Point destination = map.getLocationOf(6);
						for (int k = 0; k < Math.random(); k++) {
							Point temp = destination;
							if (temp != null) {
								map.addCharacter(0, temp);
							}
							destination = map.getLocationOf(6);
							if (destination == null) {
								destination = temp;
								break;
							}
						}
						if (location != null && destination != null
								&& manager != null)
							move(manager, location, destination, false);
						map.removePossiblePaths();
					}
				}
			}
			this.nextInstruction(client);
			// System.out.println("Move all " + amount);
		}
	}

	public void Heal(String[] list, ClientManager client) {
		Player p = getPlayer(client);
		if (list[1].equalsIgnoreCase("major")) {
			int amount = Integer.parseInt(list[2]);
			p.majorHeal(amount);
		} else if (list[1].equalsIgnoreCase("minor")) {
			int amount = Integer.parseInt(list[2]);
			// System.out.println("heal minor" + amount);
			p.minorHeal(0, amount);
		} else if (list[1].equalsIgnoreCase("*")) {
			// System.out.println("heal vader by damage");
			p.majorHeal(damageAmount);
		}
		this.nextInstruction(client);
	}

	public void Search(String[] list, ClientManager client) {
		if (list[1].equalsIgnoreCase("Deck")) {
			Player p = getPlayer(client);
			Deck d = p.getDeck();
			Deque<Cards> cards = new ArrayDeque<Cards>();
			while (!d.isEmpty()) {
				Cards c = d.draw();
				if (d.getName().equalsIgnoreCase("Han") && c.getCardNum() == 1) {
					p.addCardToHand(c);
					break;
				} else {
					cards.addFirst(c);
				}
			}
			for (Cards c : cards) {
				d.push(c);
			}
		}
		this.nextInstruction(client);
	}

	public void Damage(String[] list, ClientManager client) {
		if (list[1].equalsIgnoreCase("Direct")) {
			if (list[2].equalsIgnoreCase("View")) {
				Player p = getPlayer(client);
				ArrayList<Point> toDamage = getRangedAttackableLocations(p);
				int amount = Integer.parseInt(list[4]);
				for (int i = 0; i < toDamage.size(); i++) {
					int character = map.getNumAt(toDamage.get(i));
					ClientManager enemy = getOwningClient(character);
					Player attacked = null;
					if ((character / 10) % 2 == 1) {
						attacked = teams.get(0).getPlayer(enemy);
					} else {
						attacked = teams.get(1).getPlayer(enemy);
					}
					attack(amount, character, attacked, attacker);
				}
				// System.out.println("Damage all attackable " + amount);
			} else if (list[2].equalsIgnoreCase("Splash")) {
				Player p = getPlayer(client);
				ArrayList<Point> toDamage = getLocalAttackableLocations(p);
				int amount = Integer.parseInt(list[3]);
				for (int i = 0; i < toDamage.size(); i++) {
					int character = map.getNumAt(toDamage.get(i));
					ClientManager enemy = getOwningClient(character);
					Player attacked = null;
					if ((character / 10) % 2 == 1) {
						attacked = teams.get(0).getPlayer(enemy);
					} else {
						attacked = teams.get(1).getPlayer(enemy);
					}
					attack(amount, character, attacked, attacker);
				}
				// System.out.println("Damage all adjacent " + amount);
			} else if (list[2].equalsIgnoreCase("major")) {
				int amount = Integer.parseInt(list[3]);
				attack(amount, defending.getPlayerNumber(), defending, attacker);
			} else if (list[2].equalsIgnoreCase("minors")) {
				int amount = Integer.parseInt(list[3]);
				for (int i = 0; i < defending.getNumMinors(); i++) {
					attack(amount, defending.getPlayerNumber() + i + 1,
							defending, attacker);
				}
			} else if (list[2].equalsIgnoreCase("minor")) {
				int amount = Integer.parseInt(list[3]);
				attack(amount, attackedCharacter, defending, attacker);
			} else if (list[2].equalsIgnoreCase("Back")) {
				int amount = Integer.parseInt(list[3]);
				attack(amount, attackingCharacter, attacking, attacker);
			} else {
				int amount = Integer.parseInt(list[2]);
				attack(amount);
			}
		} else if (list[1].equalsIgnoreCase("Unblocked")) {
			int amount = Integer.parseInt(list[2]);
			// System.out.println("If unblocked should do " + amount +
			// "damage");
			if (unblocked) {
				attack(amount);
			}
		}
		this.nextInstruction(client);
	}

	public void Repopulate(String[] list, ClientManager client) {
		Player p = getPlayer(client);
		p.repopulateDeck();
		this.nextInstruction(client);
	}

	public void Remove(String[] list, ClientManager client) {
		if (list[1].equalsIgnoreCase("Action")) {
			if (list[2].equalsIgnoreCase("Self")) {
				ArrayList<Object> lists = new ArrayList<Object>();
				lists.add("addAction");
				client.send(lists);
				// System.out.println("This shouldn't count as an action");
			} else {
				int amount = Integer.parseInt(list[2]);
				ArrayList<Object> lists = new ArrayList<Object>();
				lists.add("removeAction");
				lists.add(amount);
				attacked.send(lists);
				// System.out.println("Opponent cannot do " + amount +
				// "actions");
			}
		} else if (list[1].equalsIgnoreCase("Draw")) {
			ArrayList<Object> lists = new ArrayList<Object>();
			lists.add("removeDraws");
			attacked.send(lists);
			// System.out.println("Opponent cannot draw on next turn");
		}
		this.nextInstruction(client);
	}

	public void Reflect(String[] list, ClientManager client) {
		// This is taken care off in the playDefendCard method, It was much
		// easier that way
		// System.out.println("Reflect damage");
		this.nextInstruction(client);
	}

	public void Immobilize(String[] list, ClientManager client) {
		ArrayList<Object> lists = new ArrayList<Object>();
		lists.add("removeMove");
		attacked.send(lists);
		Player p = getPlayer(attacked);
		for (int i = 0; i < 3; i++) {
			p.discardCard((int) Math.round(Math.random()
					* (p.getHandSize() - 1)));
		}
		this.nextInstruction(client);
		// System.out.println("Immobilize opponent");
	}

	public void Foreseen(String[] list, ClientManager client) {
		ArrayList<Cards> cards = new ArrayList<Cards>();
		Player p = getPlayer(client);
		cards = p.peek(4);
		ArrayList<Object> lists = new ArrayList<Object>();
		lists.add("show");
		lists.add(cards);
		client.send(lists);
		ArrayList<Cards> sorted = new ArrayList<Cards>();
		while (!cards.isEmpty()) {
			Cards c = cards.get(0);
			int last = 0;
			for (int i = 1; i < cards.size(); i++) {
				if (cards.get(i).getAIValue() > c.getAIValue()) {
					c = cards.get(i);
					last = i;
				}
			}
			sorted.add(c);
			cards.remove(last);
		}
		p.addCardToHand(sorted.get(0));
		for (int i = sorted.size() - 1; i > 0; i--) {
			p.pushOnDeck(sorted.get(i));
		}
		this.nextInstruction(client);
		// System.out.println("Look at four...");
	}

	public void Switch(String[] list, ClientManager client) {
		Point destination = map.getLocationOf(attackedCharacter);
		Point source = map.getLocationOf(attacker.getPlayerNumber());
		map.addCharacter(attackedCharacter, source);
		map.addCharacter(attacker.getPlayerNumber(), destination);
		// System.out.println("Switch with guard");
		this.nextInstruction(client);
	}

	public void attack(int amount, int character, Player p,
			ClientManager attacker) {
		boolean killed = false;
		didDamage = false;
		if (p.characterIsAlive(character)) {
			didDamage = amount > 0;
			damageAmount = amount;
			// System.out.println(didDamage + "| |" + damageAmount);
			killed = p.takeDamage(amount, character);
		}
		if (killed) {
			map.removeCharacter(character);
			chat(p.getName() + "'s "
					+ p.getCharacter(character).getCharacterName()
					+ " has died");
			if (!original) {
				getPlayer(attacker).levelUp();
				chat(attacker.getName() + " has leveled up to "
						+ getPlayer(attacker).getLevel());
			}
		} else {
			if (p.characterIsAlive(character)) {
				ClientManager client = getOwningClient(character);
				chat(client.getName() + "'s " + p.getCharacterName(character)
						+ " took " + amount + " damage.");
			}
		}
	}

	private ArrayList<Point> getRangedAttackableLocations(Player player) {
		int playerNumber = player.getPlayerNumber();
		Point origin = map.getLocationOf(playerNumber);
		Point p = null;
		ArrayList<Point> attackableLocations = new ArrayList<Point>();
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

		return attackableLocations;
	}

	private ArrayList<Point> getLocalAttackableLocations(Player player) {
		int playerNumber = player.getPlayerNumber();
		Point origin = map.getLocationOf(playerNumber);
		Point p = null;
		ArrayList<Point> attackableLocations = new ArrayList<Point>();
		p = new Point(origin.x + 1, origin.y);
		if (p != null && (map.getNumAt(p) > 9 || map.getNumAt(p) == 3)) {
			attackableLocations.add(p);
		}
		p = new Point(origin.x + 1, origin.y + 1);
		if (p != null && (map.getNumAt(p) > 9 || map.getNumAt(p) == 3)) {
			attackableLocations.add(p);
		}
		p = new Point(origin.x, origin.y + 1);
		if (p != null && ((map.getNumAt(p) > 9 || map.getNumAt(p) == 3))) {
			attackableLocations.add(p);
		}
		p = new Point(origin.x - 1, origin.y + 1);
		if (p != null && ((map.getNumAt(p) > 9 || map.getNumAt(p) == 3))) {
			attackableLocations.add(p);
		}
		p = new Point(origin.x - 1, origin.y);
		if (p != null && ((map.getNumAt(p) > 9 || map.getNumAt(p) == 3))) {
			attackableLocations.add(p);
		}
		p = new Point(origin.x - 1, origin.y - 1);
		if (p != null && ((map.getNumAt(p) > 9 || map.getNumAt(p) == 3))) {
			attackableLocations.add(p);
		}
		p = new Point(origin.x, origin.y - 1);
		if (p != null && ((map.getNumAt(p) > 9 || map.getNumAt(p) == 3))) {
			attackableLocations.add(p);
		}
		p = new Point(origin.x + 1, origin.y - 1);
		if (p != null && ((map.getNumAt(p) > 9 || map.getNumAt(p) == 3))) {
			attackableLocations.add(p);
		}
		return attackableLocations;
	}
}
