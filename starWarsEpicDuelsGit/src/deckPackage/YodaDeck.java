package deckPackage;

public class YodaDeck extends Deck {

	public YodaDeck(String name) {
		super(name);
	}

	protected void addNormalCards() {
		deck.push(new Cards(0, 4, 2, null, "major", 12, "Yoda"));
		deck.push(new Cards(0, 4, 2, null, "major", 12, "Yoda"));
		deck.push(new Cards(0, 4, 2, null, "major", 12, "Yoda"));
		deck.push(new Cards(0, 4, 2, null, "major", 12, "Yoda"));

		deck.push(new Cards(0, 3, 3, null, "major", 6, "Yoda"));
		deck.push(new Cards(0, 3, 3, null, "major", 6, "Yoda"));

		deck.push(new Cards(0, 2, 4, null, "major", 2, "Yoda"));
		deck.push(new Cards(0, 2, 4, null, "major", 2, "Yoda"));

		deck.push(new Cards(0, 1, 4, null, "major", 0, "Yoda"));

		deck.push(new Cards(0, 1, 5, null, "major", 0, "Yoda"));

		deck.push(new Cards(0, 3, 1, null, "minor", 0, "Yoda"));
		deck.push(new Cards(0, 3, 1, null, "minor", 0, "Yoda"));

		deck.push(new Cards(0, 2, 1, null, "minor", 0, "Yoda"));
		deck.push(new Cards(0, 2, 1, null, "minor", 0, "Yoda"));
		deck.push(new Cards(0, 2, 1, null, "minor", 0, "Yoda"));

		deck.push(new Cards(0, 1, 2, null, "minor", 0, "Yoda"));
		deck.push(new Cards(0, 1, 2, null, "minor", 0, "Yoda"));
		deck.push(new Cards(0, 1, 2, null, "minor", 0, "Yoda"));
		deck.push(new Cards(0, 1, 2, null, "minor", 0, "Yoda"));
	}

	protected void addSpecialCards() {
		// Attack. Draw a card
		deck.push(new Cards(4, 6, 0, "Force Strike : Draw 1", "major", 20, "Yoda"));
		deck.push(new Cards(4, 6, 0, "Force Strike : Draw 1", "major", 20, "Yoda"));
		// Defend. Draw a card.
		deck.push(new Cards(6, 0, 15, "Serenity : Draw 1", "major", 0, "Yoda"));
		deck.push(new Cards(6, 0, 15, "Serenity : Draw 1", "major", 0, "Yoda"));
		// Attack takes the damage instead of Yoda
		deck.push(new Cards(3, 0, -2, "Force Rebound : Reflect", "major", 0, "Yoda"));
		// Move an adjacent enemy anywhere and give then 3 damage
		deck.push(new Cards(2, -3, 0,
				"Force Push : Move enemy * : Damage direct 3", "major", 4, "Yoda"));
		deck.push(new Cards(2, -3, 0,
				"Force Push : Move enemy * : Damage direct 3", "major", 4, "Yoda"));
		// Immobilize any character until any player discards three cards.
		// An immobilized opponent cannot use cards.
		deck.push(new Cards(1, -3, 0, "Force Lift : Immobilize", "major", 24, "Yoda"));
		deck.push(new Cards(1, -3, 0, "Force Lift : Immobolize", "major", 24, "Yoda"));
		// Look at a player's hand and have that person discard a chosen card
		deck.push(new Cards(5, -1, 0, "Insight : Show : Discard best 1",
				"major", 10, "Yoda"));
		deck.push(new Cards(5, -1, 0, "Insight : Show : Discard best 1",
				"major", 10, "Yoda"));
		deck.push(new Cards(5, -1, 0, "Insight : Show : Discard best 1",
				"major", 10, "Yoda"));
	}
}
