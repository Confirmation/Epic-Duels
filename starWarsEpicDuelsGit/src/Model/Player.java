package Model;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

import Model.Character;
import deckPackage.Cards;
import deckPackage.Deck;

public class Player implements Serializable {

	private static final long serialVersionUID = -5600028329907897585L;
	private Character major;
	private Character[] minors;
	private Deck deck;
	private ArrayList<Cards> hand;
	private int numMinors;
	private boolean isAlive;
	private int playerNumber;
	private String name;
	private int level = 0;

	public Player(Character major, Character minor, int numMinors, Deck deck,
			String name) {
		this(major, minor, numMinors, deck);
		this.name = name;
	}

	public Player(Character major, Character minor, int numMinors, Deck deck) {
		this.major = major;
		this.numMinors = numMinors;
		this.minors = new Character[numMinors];
		for (int i = 0; i < minors.length; i++) {
			this.minors[i] = minor.clone();
		}
		this.deck = deck;
		this.hand = new ArrayList<Cards>();
		for (int i = 0; i < 4; i++) {
			hand.add(deck.draw());
		}
		this.isAlive = true;
	}

	public enum Direction {
		UP, DOWN, LEFT, RIGHT;

	}

	public String getMajorCharacterName() {
		return major.getCharacterName();
	}

	public String getMinorCharacterName(int minorCharacterNumber) {
		return minors[minorCharacterNumber].getCharacterName();
	}

	public boolean isAlive() {
		return isAlive;
	}

	public boolean majorIsAlive() {
		return major.isAlive();
	}

	public int getMajorHealth() {
		return major.getCharacterHealth();
	}

	public int getMinorHealth(int minorCharacterNumber) {
		return minors[minorCharacterNumber].getCharacterHealth();
	}

	public Point getMajorLocation() {
		return major.getCharacterLocation();
	}

	public Point getMinorLocation(int minorCharacterNumber) {
		return minors[minorCharacterNumber].getCharacterLocation();
	}

	// The direction enum can be used later for animation
	// Pre-Condition: Always send a valid condition
	public void move(Point location, Point newLocation) {
		if (major.getCharacterLocation().equals(location)) {
			major.setCharacterLocation(newLocation);
			/*
			 * switch (d) { case UP: if (currentMajor.y != 0) { currentMajor.y
			 * -= 1; } break; case DOWN: if (currentMajor.y < 6) {
			 * currentMajor.y += 1; } case LEFT: if (currentMajor.x != 0) {
			 * currentMajor.x -= 1; } case RIGHT: if (currentMajor.x < 9) {
			 * currentMajor.x += 1; } }
			 */
		} else {
			for (int i = 0; i < minors.length; i++) {
				Point currentMinor = minors[i].getCharacterLocation();
				if (currentMinor.equals(location)) {
					minors[i].setCharacterLocation(newLocation);
					/*
					 * switch (d) { case UP: if (currentMinor.y != 0) {
					 * currentMinor.y -= 1; } break; case DOWN: if
					 * (currentMinor.y < 6) { currentMinor.y += 1; } case LEFT:
					 * if (currentMinor.x != 0) { currentMinor.x -= 1; } case
					 * RIGHT: if (currentMinor.x < 9) { currentMinor.x += 1; }
					 */
				}
			}
		}

	}

	public void setCharacterNumbers(Integer team, int teamMates) {
		playerNumber = (team + 1 + 2 * teamMates) * 10;
		major.setCharacterNumber(playerNumber);
		for (int i = 0; i < minors.length; i++) {
			minors[i].setCharacterNumber(playerNumber + i + 1);
		}
	}

	public int getPlayerNumber() {
		return playerNumber;
	}

	public int getNumMinors() {
		return numMinors;
	}

	public Character getMajorCharacter() {
		return major;
	}

	public Character getMinorCharacter(int index) {
		return minors[index];
	}

	public void draw(int num) {
		for (int i = 0; i < num; i++) {
			hand.add(deck.draw());
		}
	}

	public ArrayList<Cards> getHand() {
		return hand;
	}

	public void discardFromHand(Cards card) {
		for (Cards c : hand) {
			if (c.compareTo(card) == 0) {
				hand.remove(c);
				return;
			}
		}
	}

	public Deck getDeck() {
		return deck;
	}

	public Cards getCardInHand(int index) {
		return hand.get(index);
	}

	public int getHandSize() {
		return hand.size();
	}

	public void discardCard(int index) {
		if (!hand.isEmpty()) {
			deck.graveYard(hand.remove(index));
		}
	}

	public Cards playCard(int index) {
		Cards card = hand.remove(index);
		deck.graveYard(card);
		return card;
	}

	public Character getCharacter(int num) {
		if (major.getCharacterNumber() == num) {
			return major;
		} else {
			for (int i = 0; i < minors.length; i++) {
				if (minors[i].getCharacterNumber() == num)
					return minors[i];
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public boolean majorIsRanged() {
		return major.isRanged();
	}

	public boolean minorIsRanged(int index) {
		return minors[index].isRanged();
	}

	public String getCharacterName(int character) {
		if (major.getCharacterNumber() == character) {
			return major.getCharacterName();
		} else {
			for (int i = 0; i < minors.length; i++) {
				if (minors[i].getCharacterNumber() == character) {
					return minors[i].getCharacterName();
				}
			}
		}
		return null;
	}

	public String getCharacterName(String character) {
		if (character.equals("major")) {
			return major.getCharacterName();
		} else {
			return minors[0].getCharacterName();
		}
	}

	public boolean takeDamage(int amount, int character) {
		if (major.getCharacterNumber() == character) {
			major.takeDamage(amount);
			if (!major.isAlive()) {
				return true;
			} else {
				return false;
			}
		} else {
			for (int i = 0; i < minors.length; i++) {
				if (minors[i].getCharacterNumber() == character) {
					minors[i].takeDamage(amount);
					if (!minors[i].isAlive()) {
						return true;
					} else {
						return false;
					}
				}
			}
		}
		return false;
	}

	public void majorHeal(int amount) {
		major.heal(amount);
	}

	public void minorHeal(int index, int amount) {
		minors[index].heal(amount);
	}

	public boolean minorIsAlive(int index) {
		return minors[index].isAlive();
	}

	public int getDeckSize() {
		return deck.size();
	}

	public void addCardToHand(Cards card) {
		hand.add(card);
	}

	public void repopulateDeck() {
		deck.repopulate();
	}

	public ArrayList<Cards> getGraveYard() {
		return deck.getGraveYard();
	}

	public boolean handIsEmpty() {
		return hand.isEmpty();
	}

	public void levelUp() {
		level++;
	}

	public int getLevel() {
		return level;
	}

	public int getGraveSize() {
		return deck.getGraveSize();
	}

	public Cards getCardInGrave(int index) {
		return deck.getCardInGrave(index);
	}

	public void ungraveCard(int index) {
		hand.add(deck.getCardInGrave(index));
	}

	public ArrayList<Cards> peek(int amount) {
		ArrayList<Cards> peek = new ArrayList<Cards>();
		for (int i = 0; i < amount; i++) {
			peek.add(deck.draw());
		}
		return peek;
	}

	public void pushOnDeck(Cards card) {
		deck.push(card);
	}

	public boolean characterIsAlive(int character) {
		if (character % 10 == 0) {
			return majorIsAlive();
		} else {
			return minorIsAlive(character - playerNumber - 1);
		}
	}
}
