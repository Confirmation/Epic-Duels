package deckPackage;

public class ObiWanDeck extends Deck {

	public ObiWanDeck(String name) {
		super(name);
	}

	protected void addNormalCards() {
		// MAJOR CARDS
		deck.push(new Cards(0, 5, 1, null, "major", 20, "ObiWan Kenobi"));
		deck.push(new Cards(0, 5, 1, null, "major", 20, "ObiWan Kenobi"));

		deck.push(new Cards(0, 4, 2, null, "major", 12, "ObiWan Kenobi"));
		deck.push(new Cards(0, 4, 2, null, "major", 12, "ObiWan Kenobi"));

		deck.push(new Cards(0, 3, 3, null, "major", 6, "ObiWan Kenobi"));
		deck.push(new Cards(0, 3, 3, null, "major", 6, "ObiWan Kenobi"));

		deck.push(new Cards(0, 1, 4, null, "major", 0, "ObiWan Kenobi"));
		deck.push(new Cards(0, 1, 4, null, "major", 0, "ObiWan Kenobi"));

		deck.push(new Cards(0, 4, 1, null, "major", 13, "ObiWan Kenobi"));

		deck.push(new Cards(0, 2, 3, null, "major", 2, "ObiWan Kenobi"));

		// MINOR CARDS
		deck.push(new Cards(0, 1, 2, null, "minor", 0, "ObiWan Kenobi"));
		deck.push(new Cards(0, 1, 2, null, "minor", 0, "ObiWan Kenobi"));
		deck.push(new Cards(0, 1, 2, null, "minor", 0, "ObiWan Kenobi"));
		deck.push(new Cards(0, 1, 2, null, "minor", 0, "ObiWan Kenobi"));

		deck.push(new Cards(0, 2, 1, null, "minor", 0, "ObiWan Kenobi"));
		deck.push(new Cards(0, 2, 1, null, "minor", 0, "ObiWan Kenobi"));
		deck.push(new Cards(0, 2, 1, null, "minor", 0, "ObiWan Kenobi"));
		deck.push(new Cards(0, 3, 1, null, "minor", 0, "ObiWan Kenobi"));
		deck.push(new Cards(0, 3, 1, null, "minor", 0, "ObiWan Kenobi"));
	}

	protected void addSpecialCards() {

		// Jedi Attack 6 attack, move 6
		deck.push(new Cards(4, 6, 0, "Jedi Attack : Move Major 6", "major", 30,
				"ObiWan Kenobi"));
		deck.push(new Cards(4, 6, 0, "Jedi Attack : Move Major 6", "major", 30,
				"ObiWan Kenobi"));
		deck.push(new Cards(4, 6, 0, "Jedi Attack : Move Major 6", "major", 30,
				"ObiWan Kenobi"));

		// Jedi Block: 12 defend, Draw 1
		deck.push(new Cards(5, 0, 12, "Jedi Block : Draw 1", "major", 0,
				"ObiWan Kenobi"));
		deck.push(new Cards(5, 0, 12, "Jedi Block : Draw 1", "major", 0,
				"ObiWan Kenobi"));
		deck.push(new Cards(5, 0, 12, "Jedi Block : Draw 1", "major", 0,
				"ObiWan Kenobi"));

		// Force Quickness: Move 8, Draw 1
		deck.push(new Cards(3, 0, 0, "Force Quickness : Move Major 8 : Draw 1",
				"major", 6, "ObiWan Kenobi"));
		deck.push(new Cards(3, 0, 0, "Force Quickness : Move Major 8 : Draw 1",
				"major", 6, "ObiWan Kenobi"));

		// Force Control: 7 attack, move anyone/everyone 3 spaces each
		deck.push(new Cards(2, 7, 0, "Force Control : Move all 3", "major", 40,
				"ObiWan Kenobi"));
		deck.push(new Cards(2, 7, 0, "Force Control : Move all 3", "major", 40,
				"ObiWan Kenobi"));

		// Force Balance: All player discard hands and draw 3 cards
		deck.push(new Cards(1, 0, 0,
				"Force Balance : Discard all * : Draw all 3", "major", 0,
				"ObiWan Kenobi"));

		// Jedi Mind Trick: Take a card from discard pile
		deck.push(new Cards(6, 0, 0, "Jedi Mind Trick : Show graveyard 1",
				"major", 0, "ObiWan Kenobi"));
	}
}
