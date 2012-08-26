package Model;

import java.awt.Point;
import java.io.Serializable;

public class Character implements Serializable {

	private int health;
	private Point location;
	private String characterName;
	private boolean isAlive;
	private int characterNum;
	private int attackThreshold;
	private int defendThreshold;
	private boolean ranged;

	public Character(int characterNum, int health, String characterName,
			int attackThreshold, int defendThreshold, boolean ranged) {
		this.health = health;
		this.location = new Point(0, 0);
		this.characterName = characterName;
		this.characterNum = characterNum;
		this.attackThreshold = attackThreshold;
		this.defendThreshold = defendThreshold;
		this.ranged = ranged;
	}

	public Character clone() {
		return new Character(characterNum, health, characterName,
				attackThreshold, defendThreshold, ranged);
	}

	public void changeHealth(int amount) {
		if (health > 0)
			health += amount;
	}

	public int getCharacterHealth() {
		return health;
	}

	public int getAttackThreshold() {
		return attackThreshold;
	}

	public int getDefendThreshold() {
		return defendThreshold;
	}

	public Point getCharacterLocation() {
		return location;
	}

	public String getCharacterName() {
		return characterName;
	}

	public int getCharacterNumber() {
		return characterNum;
	}

	public void setCharacterLocation(Point newLocation) {
		location = newLocation;
	}

	public boolean isAlive() {
		return health > 0;
	}

	public int compareTo(Character other) {
		if (this.getCharacterName().equalsIgnoreCase(other.getCharacterName())) {
			return 0;
		} else {
			return -1;
		}
		// return (this.getCharacterName()).compareTo(other.getCharacterName());
	}

	public void setCharacterNumber(int num) {
		characterNum = num;
	}

	public boolean isRanged() {
		return ranged;
	}

	public void takeDamage(int amount) {
		health -= amount;
		if (health < 0)
			health = 0;
	}

	public void heal(int amount) {
		if (health > 0)
			health += amount;
	}
}
