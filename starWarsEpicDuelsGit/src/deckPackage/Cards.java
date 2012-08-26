package deckPackage;

import java.io.Serializable;

public class Cards implements Serializable {

	private int attack;
	private int defense;
	private String special;
	private String character;
	private int cardNum;
	private int aiValue;
	private String deck;

	public Cards(int cardNum, int attack, int defense, String special,
			String character, int aiValue, String deck) {
		this.attack = attack;
		this.defense = defense;
		this.special = special;
		this.character = character;
		this.cardNum = cardNum;
		this.aiValue = aiValue;
		this.deck = deck;
	}
	
	public String getDeck(){
		return deck;
	}

	public int getAIValue() {
		return aiValue;
	}

	public int getAttackValue() {
		return attack;
	}

	public int getDefenseValue() {
		return defense;
	}

	public String getSpecialAttribute() {
		return special;
	}

	public String getCharacter() {
		return character;
	}

	public int getCardNum() {
		return cardNum;
	}

	public String toString() {
		return "Attack: " + attack + ", Defense: " + defense + ", Special: "
				+ special + ", Character: " + character;
	}

	public int compareTo(Cards other) {
		int result = 0;
		result += other.getAttackValue() - attack;
		result += other.getDefenseValue() - defense;
		result += (other.getCharacter()).compareTo(character);
		result += (other.getSpecialAttribute()).compareTo(special);
		return result;
	}

	public boolean isAttackCard() {
		return attack != 0;
	}

	public boolean isDefenseCard() {
		return defense != 0;
	}
}
