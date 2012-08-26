/**
 * This class manages the server side client-server
 * interactions in its own thread. It is responsible
 * for translating client messages for the game class
 * and alerting its specific client of any game
 * changes or actions. There will be one client
 * manager for every client. A list of client managers
 * is contained in the game class and the team class
 * maps client managers to specific players. 
 */
package Model;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;

import deckPackage.Deck;

public class ClientManager {

	private String name;
	private Socket socket;
	private Game game;
	private ObjectInputStream inStream;
	private ObjectOutputStream outStream;
	private Character[] characterChoice;
	private int numMinors;
	private Deck deck;
	private boolean running;
	private int playerNumber;
	Thread getMessagesThread;
	private boolean isMoving = false;
	private boolean AI;
	private int moves = 0;
	private boolean discarding = false;

	ClientManager(Socket sock, Game game) {
		running = true;
		socket = sock;
		this.game = game;

		try {
			outStream = new ObjectOutputStream(socket.getOutputStream());
			inStream = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		// tell client what they connected to
		update(null);
		getMessagesThread = new Thread(new GetThread());
		getMessagesThread.start();

	}

	// this thread constantly waits for a new message from the client. When
	// such a message is received, it acts accordingly.
	private class GetThread implements Runnable {

		@Override
		public void run() {
			while (running) {
				try {
					@SuppressWarnings("unchecked")
					ArrayList<Object> command = (ArrayList<Object>) inStream
							.readObject();
					// System.out.println("Manager Recieved " + command);
					execute(command);
				} catch (IOException e) {
					game.removePlayer(ClientManager.this);
					running = false;
					if (socket != null) {
						try {
							ClientManager.this.socket.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				game.removeClients();
			}
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void wakeUp(){
		ArrayList<Object> list = new ArrayList<Object>();
		list.add("wakeUp");
		send(list);
	}

	@SuppressWarnings("unchecked")
	public synchronized void setCharacterChoice(Object o) {
		ArrayList<Object> list = (ArrayList<Object>) o;
		characterChoice = (Character[]) list.get(1);
		if (game.makeUnavailable(characterChoice)) {
			numMinors = game.getNumMinors(characterChoice[0]);
			deck = game.getDeck(characterChoice[0]);
		} else {
			ArrayList<Object> list2 = new ArrayList<Object>();
			list2.add("characterChoiceFailed");
			send(list);
		}
	}

	@SuppressWarnings("unchecked")
	public synchronized void removeCharacterChoice(Object o) {
		ArrayList<Object> list = (ArrayList<Object>) o;
		characterChoice = (Character[]) list.get(1);
		if (game.makeAvailable(characterChoice)) {
			numMinors = -1;
			deck = null;
		} else {
			ArrayList<Object> list2 = new ArrayList<Object>();
			list2.add("releasingFailed");
			send(list);
		}
	}

	public synchronized void execute(ArrayList<Object> list) {
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

	@SuppressWarnings("unchecked")
	public synchronized void addPlayer(Object o) {
		ArrayList<Object> list = (ArrayList<Object>) o;
		Integer choice = (Integer) list.get(1);
		if (!game.addPlayer(new Player(characterChoice[0], characterChoice[1],
				numMinors, deck, name), choice, ClientManager.this)) {
			ArrayList<Object> list2 = new ArrayList<Object>();
			list2.add("teamSelectionFailed");
		}
	}

	public synchronized void removePlayer(Object o) {
		if (!game.removePlayer(this)) {
			ArrayList<Object> list2 = new ArrayList<Object>();
			list2.add("teamReleaseFailed");
		}
	}

	@SuppressWarnings("unchecked")
	public synchronized void move(Object o) {
		ArrayList<Object> list = (ArrayList<Object>) o;
		game.move(this, (Point) list.get(1), (Point) list.get(2), true);
		moves--;
		if (moves <= 0) {
			isMoving = false;
		}
	}

	public synchronized void update(Object o) {
		ArrayList<Object> list = new ArrayList<Object>();
		list.add("update");
		list.add(game.getAvailableCharacters());
		list.add(game.getUnavailableCharacters());
		list.add(game.getTeams());
		list.add(game.getMap());
		list.add((Integer) game.getNumPlayers());
		list.add(game.getFogOfWar());
		send(list);
	}

	public synchronized void stop() {
		running = false;
	}

	public synchronized void sendMap(Object o) {
		ArrayList<Object> list = new ArrayList<Object>();
		list.add("setMap");
		list.add(game.getMap());
		send(list);
	}

	public synchronized void send(ArrayList<Object> message) {
		try {
			outStream.writeObject(message);
			outStream.reset();
		} catch (IOException e) {
			game.addClientForRemoval(ClientManager.this);
		}
	}

	public synchronized void endTurn(Object o) {
		game.endTurn(this);
	}

	@SuppressWarnings("unchecked")
	public synchronized void draw(Object o) {
		ArrayList<Object> list = (ArrayList<Object>) o;
		game.draw((Integer) list.get(1), this);
	}

	@SuppressWarnings("unchecked")
	public synchronized void discardCard(Object o) {
		ArrayList<Object> list = (ArrayList<Object>) o;
		game.discardCard((Integer) list.get(1), this);
		discarding = false;
	}

	@SuppressWarnings("unchecked")
	public synchronized void discardReveal(Object o) {
		ArrayList<Object> list = (ArrayList<Object>) o;
		game.discardReveal((Integer) list.get(1));
	}

	@SuppressWarnings("unchecked")
	public synchronized void playCard(Object o) {
		ArrayList<Object> list = (ArrayList<Object>) o;
		game.playCard((Integer) list.get(1), this);
	}

	@SuppressWarnings("unchecked")
	public synchronized void playAttackCard(Object o) {
		ArrayList<Object> list = (ArrayList<Object>) o;
		game.playAttackCard((Integer) list.get(1), (Integer) list.get(2),
				(Integer) list.get(3), this);
	}

	@SuppressWarnings("unchecked")
	public synchronized void playDefendCard(Object o) {
		ArrayList<Object> list = (ArrayList<Object>) o;
		game.playDefendCard((Integer) list.get(1), this);
	}

	@SuppressWarnings("unchecked")
	public synchronized void chat(Object o) {
		ArrayList<Object> list = (ArrayList<Object>) o;
		game.chat((String) list.get(1));
	}

	@SuppressWarnings("unchecked")
	public synchronized void setName(Object o) {
		ArrayList<Object> list = (ArrayList<Object>) o;
		this.name = (String) list.get(1);
		this.AI = (Boolean) list.get(2);
	}

	public synchronized String getName() {
		return name;
	}

	public synchronized String getCharacterName() {
		return characterChoice[0].getCharacterName();
	}

	@SuppressWarnings("unchecked")
	public synchronized void rolled(Object o) {
		ArrayList<Object> list = (ArrayList<Object>) o;
		game.rolled(getName(), (String) list.get(1));
	}

	public synchronized void setPlayerNumber(int number) {
		playerNumber = number;
	}

	public synchronized int getPlayerNumber() {
		return playerNumber;
	}

	public synchronized boolean isDiscarding() {
		return discarding;
	}

	public synchronized void setDiscarding(boolean value) {
		discarding = value;
	}

	public synchronized void setIsMoving() {
		isMoving = true;
		moves = 2;
	}

	public synchronized boolean isMoving() {
		return isMoving;
	}

	public synchronized boolean isAI() {
		return AI;
	}
}
