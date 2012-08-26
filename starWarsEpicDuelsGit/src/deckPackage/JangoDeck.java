package deckPackage;

public class JangoDeck extends Deck {

	public JangoDeck(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	protected void addNormalCards() {
		deck.push(new Cards(0, 4, 1, null, "major", 12, "Jango Fett"));
		deck.push(new Cards(0, 4, 1, null, "major", 12, "Jango Fett"));
		deck.push(new Cards(0, 4, 1, null, "major", 12, "Jango Fett"));
		deck.push(new Cards(0, 3, 2, null, "major", 6, "Jango Fett"));
		deck.push(new Cards(0, 3, 1, null, "major", 6, "Jango Fett"));
		deck.push(new Cards(0, 3, 1, null, "major", 6, "Jango Fett"));
		deck.push(new Cards(0, 2, 2, null, "major", 2, "Jango Fett"));
		deck.push(new Cards(0, 2, 2, null, "major", 2, "Jango Fett"));
		deck.push(new Cards(0, 1, 4, null, "major", 0, "Jango Fett"));
		deck.push(new Cards(0, 1, 4, null, "major", 0, "Jango Fett"));
		deck.push(new Cards(0, 4, 1, null, "minor", 0, "Jango Fett"));
		deck.push(new Cards(0, 4, 1, null, "minor", 0, "Jango Fett"));
		deck.push(new Cards(0, 3, 2, null, "minor", 0, "Jango Fett"));
		deck.push(new Cards(0, 3, 1, null, "minor", 0, "Jango Fett"));
		deck.push(new Cards(0, 3, 1, null, "minor", 0, "Jango Fett"));
		deck.push(new Cards(0, 2, 3, null, "minor", 0, "Jango Fett"));
		deck.push(new Cards(0, 2, 3, null, "minor", 0, "Jango Fett"));
		deck.push(new Cards(0, 1, 4, null, "minor", 0, "Jango Fett"));
		deck.push(new Cards(0, 1, 4, null, "minor", 0, "Jango Fett"));
	}

	protected void addSpecialCards() {
		// Move Jango Fett to any empty space, does not count as action
		deck.push(new Cards(2, 0, 0,
				"Fire Up the Jet Pack : Remove Action Self : Move *", "major",
				5, "Jango Fett"));

		// 2 damage to all characters adjacent to Jango Fett, move them up to 3
		// spaces away
		deck.push(new Cards(3, 0, 0,
				"Flamethrower : Damage Direct Splash 2  : Move *", "major", 15,
				"Jango Fett"));

		// Draw 3 cards
		deck.push(new Cards(4, 7, 0,
				"Missle Launch : Draw 3 : Map Kamino } Draw 1", "major", 20,
				"Jango Fett"));

		// After attacking, you may move to any empty space
		deck.push(new Cards(5, 4, 0, "Rocket Retreat : Move *", "major", 15,
				"Jango Fett"));
		deck.push(new Cards(5, 4, 0, "Rocket Retreat : Move *", "major", 15,
				"Jango Fett"));
		deck.push(new Cards(5, 4, 0, "Rocket Retreat : Move *", "major", 15,
				"Jango Fett"));

		// 2 damage to any one player Jango may attack, player loses one action
		// next turn
		deck.push(new Cards(7, -4, 0,
				"Wrist Cable : Damage Direct 2 : Remove action 1", "major", 25,
				"Jango Fett"));
		deck.push(new Cards(7, -4, 0,
				"Wrist Cable : Damage Direct 2 : Remove action 1", "major", 25,
				"Jango Fett"));

		// After attacking, move Zam to any empty space
		deck.push(new Cards(1, 7, 0, "Assasination : Move *", "minor", 10,
				"Jango Fett"));

		// If attack is not blocked, does 6 damage instead
		deck.push(new Cards(6, 3, 0, "Sniper Shot : Damage Unblocked 3",
				"minor", 10, "Jango Fett"));
		deck.push(new Cards(6, 3, 0, "Sniper Shot : Damage Unblocked 3",
				"minor", 10, "Jango Fett"));
		deck.push(new Cards(6, 3, 0, "Sniper Shot : Damage Unblocked 3",
				"minor", 10, "Jango Fett"));

	}
}
