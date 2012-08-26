package Model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import deckPackage.Cards;

public class Team {

	private HashMap<ClientManager, Player> players;

	Team() {
		players = new HashMap<ClientManager, Player>();
	}

	public void add(Player player, ClientManager clientManager) {
		players.put(clientManager, player);
	}

	public boolean contains(ClientManager client) {
		return players.containsKey(client);
	}

	public Player removePlayer(ClientManager client) {
		return players.remove(client);
	}

	public void move(ClientManager client, Point location, Point destination) {
		(players.get(client)).move(location, destination);
	}

	public int size() {
		return players.size();
	}

	public ArrayList<Player> getPlayerList() {
		ArrayList<Player> list = new ArrayList<Player>();
		for (ClientManager manager : players.keySet()) {
			list.add(players.get(manager));
		}
		return list;
	}

	public boolean isAlive() {
		int numMajorsAlive = 0;
		for (ClientManager manager : players.keySet()) {
			if (players.get(manager).majorIsAlive()) {
				numMajorsAlive++;
			}
		}
		return numMajorsAlive > 0;
	}

	public boolean playerIsAlive(int playerNum) {
		for (ClientManager manager : players.keySet()) {
			Player p = players.get(manager);
			if (p.isAlive() && p.getPlayerNumber() == playerNum) {
				return true;
			}
		}
		return false;
	}

	public void draw(int num, ClientManager client) {
		Player p = players.get(client);
		p.draw(num);
	}

	public void discardCard(int index, ClientManager client) {
		Player p = players.get(client);
		p.discardCard(index);
	}

	public Cards playCard(int index, ClientManager client) {
		Player p = players.get(client);
		return p.playCard(index);
	}

	public ClientManager getClient(int turn) {
		ClientManager client = null;
		for (ClientManager manager : players.keySet()) {
			if (players.get(manager).getPlayerNumber() == turn) {
				client = manager;
			}
		}
		return client;
	}
	
	public Player getPlayer(ClientManager client){
		return players.get(client);
	}
}
