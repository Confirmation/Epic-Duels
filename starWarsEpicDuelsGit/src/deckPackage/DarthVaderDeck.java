package deckPackage;

public class DarthVaderDeck extends Deck {

	public DarthVaderDeck(String name) {
		super(name);
	}

	protected void addNormalCards() {

		// MAJOR CARDS
    	deck.push(new Cards(0, 5, 1, null, "major", 20, "Darth Vader"));
		deck.push(new Cards(0, 5, 1, null, "major", 20, "Darth Vader"));
		deck.push(new Cards(0, 5, 1, null, "major", 20, "Darth Vader"));
		deck.push(new Cards(0, 5, 1, null, "major", 20, "Darth Vader"));

		deck.push(new Cards(0, 4, 2, null, "major", 12, "Darth Vader"));
		deck.push(new Cards(0, 4, 2, null, "major", 12, "Darth Vader"));

		deck.push(new Cards(0, 4, 1, null, "major", 13, "Darth Vader"));

		deck.push(new Cards(0, 3, 2, null, "major", 6, "Darth Vader"));

		deck.push(new Cards(0, 2, 3, null, "major", 2, "Darth Vader"));

		deck.push(new Cards(0, 1, 4, null, "major", 0, "Darth Vader"));

		// MINOR CARDS
		deck.push(new Cards(0, 1, 2, null, "minor", 0, "Darth Vader"));
		deck.push(new Cards(0, 1, 2, null, "minor", 0, "Darth Vader"));
		deck.push(new Cards(0, 1, 2, null, "minor", 0, "Darth Vader"));
		deck.push(new Cards(0, 1, 2, null, "minor", 0, "Darth Vader"));

		deck.push(new Cards(0, 2, 1, null, "minor", 0, "Darth Vader"));
		deck.push(new Cards(0, 2, 1, null, "minor", 0, "Darth Vader"));
		deck.push(new Cards(0, 2, 1, null, "minor", 0, "Darth Vader"));

		deck.push(new Cards(0, 3, 1, null, "minor", 0, "Darth Vader"));
		deck.push(new Cards(0, 3, 1, null, "minor", 0, "Darth Vader"));
	}

	protected void addSpecialCards() {

		// Wratch: Inflict 2 damage to all of one opponent's characters
		deck.push(new Cards(5, -1, 0,
				"Wrath : Damage direct major 2 : Damage direct minors 2",
				"major", 20, "Darth Vader"));
		deck.push(new Cards(5, -1, 0,
				"Wrath : Damage direct major 2 : Damage direct minors 2",
				"major", 20, "Darth Vader"));
		deck.push(new Cards(5, -1, 0,
				"Wrath : Damage direct major 2 : Damage direct minors 2",
				"major", 20, "Darth Vader"));

		// Choke: Inflict 6 damage pints on any one minor character
		deck.push(new Cards(2, -5, 0, "Choke : Damage direct minor 6", "major",
				19, "Darth Vader"));
		deck.push(new Cards(2, -5, 0, "Choke : Damage direct minor 6", "major",
				19, "Darth Vader"));
		deck.push(new Cards(2, -5, 0, "Choke : Damage direct minor 6", "major",
				19, "Darth Vader"));

		// Throw Debris: Inflict 4 damage pints on any one character
		deck.push(new Cards(4, -1, 0, "Throw Debris : Damage direct 4",
				"major", 20, "Darth Vader"));
		deck.push(new Cards(4, -1, 0, "Throw Debris : Damage direct 4",
				"major", 20, "Darth Vader"));

		// Dark Side Drain: 3 attack, Vader heals one HP for each point
		// of damge taken by victim
		deck.push(new Cards(3, 3, 0, "Dark Side Drain : Damage } Heal *",
				"major", 18, "Darth Vader"));
		deck.push(new Cards(3, 3, 0, "Dark Side Drain : Damage } Heal *",
				"major", 18, "Darth Vader"));

		// All Too Easy: 3 attack, 20 attack if undefended
		deck.push(new Cards(1, 3, 0, "All Too Easy : Damage unblocked 17",
				"major", 25, "Darth Vader"));

		// Your Skills Are Not Complete: Force opponent to reveal entire hand
		// and discard all special cards
		deck.push(new Cards(6, -1, 0,
				"Your Skills Are Not Complete : Show : Discard special",
				"major", 15, "Darth Vader"));
	}
}
