package deckPackage;

public class HanSoloDeck extends Deck {

	public HanSoloDeck(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	protected void addNormalCards() {
		deck.push(new Cards(0, 4, 1, null, "major", 12, "Han Solo"));
		deck.push(new Cards(0, 4, 1, null, "major", 12, "Han Solo"));
		deck.push(new Cards(0, 4, 1, null, "major", 12, "Han Solo"));
		deck.push(new Cards(0, 3, 2, null, "major", 6, "Han Solo"));
		deck.push(new Cards(0, 3, 1, null, "major", 6, "Han Solo"));
		deck.push(new Cards(0, 3, 1, null, "major", 6, "Han Solo"));
		deck.push(new Cards(0, 2, 3, null, "major", 2, "Han Solo"));
		deck.push(new Cards(0, 2, 2, null, "major", 2, "Han Solo"));
		deck.push(new Cards(0, 1, 4, null, "major", 0, "Han Solo"));
		deck.push(new Cards(0, 1, 4, null, "major", 0, "Han Solo"));
		deck.push(new Cards(0, 5, 1, null, "minor", 0, "Han Solo"));
		deck.push(new Cards(0, 4, 1, null, "minor", 0, "Han Solo"));
		deck.push(new Cards(0, 3, 2, null, "minor", 0, "Han Solo"));
		deck.push(new Cards(0, 3, 1, null, "minor", 0, "Han Solo"));
		deck.push(new Cards(0, 3, 1, null, "minor", 0, "Han Solo"));
		deck.push(new Cards(0, 2, 3, null, "minor", 0, "Han Solo"));
		deck.push(new Cards(0, 2, 3, null, "minor", 0, "Han Solo"));
		deck.push(new Cards(0, 1, 4, null, "minor", 0, "Han Solo"));
		deck.push(new Cards(0, 1, 4, null, "minor", 0, "Han Solo"));
	}

	protected void addSpecialCards() {
		// If this attack does damage opponent discards 1 card
		deck.push(new Cards(2, 4, 0, "Gambler's Luck : Damage } Discard 1",
				"major", 16, "Han Solo"));
		deck.push(new Cards(2, 4, 0, "Gambler's Luck : Damage } Discard 1",
				"major", 16, "Han Solo"));
		deck.push(new Cards(2, 4, 0, "Gambler's Luck : Damage } Discard 1",
				"major", 16, "Han Solo"));

		// Han may move up to 5 spaces after attacking
		deck.push(new Cards(3, 5, 0, "Heroic Retreat : Move Major 5", "major",
				22, "Han Solo"));
		deck.push(new Cards(3, 5, 0, "Heroic Retreat : Move Major 5", "major",
				22, "Han Solo"));

		// Draw a Card
		deck.push(new Cards(1, 11, 0, "Bowcaster Attack : Draw 1", "minor", 0,
				"Han Solo"));

		// 2 damage to any players Han can attack, shuffle discard pile into
		// deck
		deck.push(new Cards(5, 0, 0,
				"Never Tell Me the Odds : Damage Direct View * 2 : Repopulate",
				"major", 15, "Han Solo"));
		deck.push(new Cards(5, 0, 0,
				"Never Tell Me the Odds : Damage Direct View * 2 : Repopulate",
				"major", 15, "Han Solo"));

		// Move any adjacent enemies up to 3 spaces away, they each take 3
		// damage
		deck.push(new Cards(4, -6, 0,
				"It's Not Wise... : Damage Direct 3 : Move Enemy 3 ", "minor",
				2, "Han Solo"));
		deck.push(new Cards(4, -6, 0,
				"It's Not Wise... : Damage Direct 3 : Move Enemy 3 ", "minor",
				2, "Han Solo"));

		// Heal 3 up to 3 damage, move up to 5 spaces
		deck.push(new Cards(6, 0, 0,
				"Wookiee Healing : Heal Minor 3 : Move Minor 5", "minor", 3,
				"Han Solo"));

		// Find "Bowcaster Attack" from draw pile and add to hand, reshuffle
		// draw pile
		deck.push(new Cards(7, 0, 0, "Wookiee Instincts : Search Deck Wookiee",
				"minor", 0, "Han Solo"));
	}
}
