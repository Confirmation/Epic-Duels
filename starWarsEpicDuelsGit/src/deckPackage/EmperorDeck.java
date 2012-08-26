package deckPackage;

public class EmperorDeck extends Deck {

	public EmperorDeck(String name) {
		super(name);
	}

	protected void addNormalCards() {
		 deck.push(new Cards(0, 4, 2, null, "major", 12,
		 "Emperor Palpatine"));
		 deck.push(new Cards(0, 4, 2, null, "major", 12,
		 "Emperor Palpatine"));
		 deck.push(new Cards(0, 4, 2, null, "major", 12,
		 "Emperor Palpatine"));
		 deck.push(new Cards(0, 4, 2, null, "major", 12,
		 "Emperor Palpatine"));
		
		 deck.push(new Cards(0, 3, 3, null, "major", 6, "Emperor Palpatine"));
		 deck.push(new Cards(0, 3, 3, null, "major", 6, "Emperor Palpatine"));
		
		 deck.push(new Cards(0, 2, 4, null, "major", 2, "Emperor Palpatine"));
		 deck.push(new Cards(0, 2, 4, null, "major", 2, "Emperor Palpatine"));
		
		 deck.push(new Cards(0, 1, 4, null, "major", 0, "Emperor Palpatine"));
		
		 deck.push(new Cards(0, 1, 5, null, "major", 0, "Emperor Palpatine"));
		
		 deck.push(new Cards(0, 5, 1, null, "minor", 0, "Emperor Palpatine"));
		
		 deck.push(new Cards(0, 4, 1, null, "minor", 0, "Emperor Palpatine"));
		
		 deck.push(new Cards(0, 3, 2, null, "minor", 0, "Emperor Palpatine"));
		
		 deck.push(new Cards(0, 3, 1, null, "minor", 0, "Emperor Palpatine"));
		 deck.push(new Cards(0, 3, 1, null, "minor", 0, "Emperor Palpatine"));
		
		 deck.push(new Cards(0, 2, 4, null, "minor", 0, "Emperor Palpatine"));
		
		 deck.push(new Cards(0, 2, 3, null, "minor", 0, "Emperor Palpatine"));
		 deck.push(new Cards(0, 2, 3, null, "minor", 0, "Emperor Palpatine"));
		
		 deck.push(new Cards(0, 1, 4, null, "minor", 0, "Emperor Palpatine"));
	}

	protected void addSpecialCards() {
		// Inflict 3 damage to anyone on board; Victim must discard a card
		deck.push(new Cards(1, -1, 0,
				"Force Lightning : Damage direct 3 : Discard 1", "major", 20, "Emperor Palpatine"));
		deck.push(new Cards(1, -1, 0,
				"Force Lightning : Damage direct 3 : Discard 1", "major", 20, "Emperor Palpatine"));
		deck.push(new Cards(1, -1, 0,
				"Force Lightning : Damage direct 3 : Discard 1", "major", 20, "Emperor Palpatine"));
		deck.push(new Cards(1, -1, 0,
				"Force Lightning : Damage direct 3 : Discard 1", "major", 20, "Emperor Palpatine"));
		// Heal 4 HP; select one opponent to not draw on his or her next turn
		deck.push(new Cards(4, -1, 0, "Meditation : Heal major 4 : Remove draw",
				"major", 10, "Emperor Palpatine"));
		deck.push(new Cards(4, -1, 0, "Meditation : Heal major 4 : Remove draw",
				"major", 10, "Emperor Palpatine"));
		// Look at the top four cards of deck, pick one to place into hand
		// Place other three back on deck in any order
		deck.push(new Cards(2, 0, 0, "Future Foreseen : Foreseen", "major", 5, "Emperor Palpatine"));
		deck.push(new Cards(2, 0, 0, "Future Foreseen : Foreseen", "major", 5, "Emperor Palpatine"));
		// Target opponent discards two cards
		deck.push(new Cards(3, -1, 0,
				"Let Go of Your Hatred : Discard choice 2", "major", 40, "Emperor Palpatine"));
		deck.push(new Cards(3, -1, 0,
				"Let Go of Your Hatred : Discard choice 2", "major", 40, "Emperor Palpatine"));
		// Target opponent discards entire hand
		deck.push(new Cards(6, -1, 0, "You Will Die : Discard *", "major", 60, "Emperor Palpatine"));
		// Emperor switches place with a Crimson Guard
		deck.push(new Cards(5, -2, 0, "Royal Command : Switch", "major", 2, "Emperor Palpatine"));
	}
}
