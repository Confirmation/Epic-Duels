package deckPackage;

public class LukeSkyWalkerDeck extends Deck {

	public LukeSkyWalkerDeck(String name) {
		super(name);
	}

	protected void addSpecialCards() {
		// 4 attack if Leia is alive otherwise 10 attack
		deck.add(new Cards(3, 4, 0, "Justice : Dead minor } Attack 6", "major",
				12, "Luke Skywalker"));
		deck.add(new Cards(3, 4, 0, "Justice : Dead minor } Attack 6", "major",
				12, "Luke Skywalker"));
		// Luke and opponent both show hands and discard any card with an attack
		// value greater than 1
		deck.add(new Cards(
				2,
				-1,
				0,
				"I will not Fight you : Show self : Show enemy : Discard Attack>1",
				"major", 0, "Luke Skywalker"));
		deck.add(new Cards(
				2,
				-1,
				0,
				"I will not Fight you : Show self : Show enemy : Discard Attack>1",
				"major", 0, "Luke Skywalker"));
		deck.add(new Cards(
				2,
				-1,
				0,
				"I will not Fight you : Show self : Show enemy : Discard Attack>1",
				"major", 0, "Luke Skywalker"));
		// Move Luke and Leia 6 spaces each, draw 2 cards
		deck.add(new Cards(1, 0, 0,
				"Children of the Force : Move Major 6 : Move Minor 6 : Draw 2", "major",
				15, "Luke Skywalker"));
		deck.add(new Cards(1, 0, 0,
				"Children of the Force : Move Major 6 : Move Minor 6 : Draw 2", "major",
				15, "Luke Skywalker"));
		deck.add(new Cards(1, 0, 0,
				"Children of the Force : Move Major 6 : Move Minor 6 : Draw 2", "major",
				15, "Luke Skywalker"));

		// draw a card
		deck.add(new Cards(4, 7, 7, "Latent Force Abilities : Draw 1", "minor",
				20, "Luke Skywalker"));
		deck.add(new Cards(4, 7, 7, "Latent Force Abilities : Draw 1", "minor",
				20, "Luke Skywalker"));

		// Luke recovers 3HP if adjacent; if dead Leia recovers 3HP
		deck.add(new Cards(
				5,
				0,
				0,
				"Luke's in Trouble : Adjacent Major } Heal Major 3 ; Dead Major } Heal Minor 3",
				"minor", 0, "Luke Skywalker"));
		deck.add(new Cards(
				5,
				0,
				0,
				"Luke's in Trouble : Adjacent Major } Heal Major 3 ; Dead Major } Heal Minor 3",
				"minor", 0, "Luke Skywalker"));
	}

	protected void addNormalCards() {
		deck.add(new Cards(0, 5, 1, null, "major", 20, "Luke Skywalker"));
		deck.add(new Cards(0, 5, 1, null, "major", 20, "Luke Skywalker"));
		deck.add(new Cards(0, 5, 1, null, "major", 20, "Luke Skywalker"));
		deck.add(new Cards(0, 5, 1, null, "major", 20, "Luke Skywalker"));
		deck.add(new Cards(0, 4, 2, null, "major", 12, "Luke Skywalker"));
		deck.add(new Cards(0, 4, 2, null, "major", 12, "Luke Skywalker"));
		deck.add(new Cards(0, 4, 1, null, "major", 13, "Luke Skywalker"));
		deck.add(new Cards(0, 3, 2, null, "major", 6, "Luke Skywalker"));
		deck.add(new Cards(0, 2, 3, null, "major", 2, "Luke Skywalker"));
		deck.add(new Cards(0, 1, 4, null, "major", 0, "Luke Skywalker"));

		deck.add(new Cards(0, 4, 1, null, "minor", 0, "Luke Skywalker"));
		deck.add(new Cards(0, 4, 1, null, "minor", 0, "Luke Skywalker"));
		deck.add(new Cards(0, 3, 2, null, "minor", 0, "Luke Skywalker"));
		deck.add(new Cards(0, 3, 1, null, "minor", 0, "Luke Skywalker"));
		deck.add(new Cards(0, 3, 1, null, "minor", 0, "Luke Skywalker"));
		deck.add(new Cards(0, 2, 3, null, "minor", 0, "Luke Skywalker"));
		deck.add(new Cards(0, 2, 3, null, "minor", 0, "Luke Skywalker"));
		deck.add(new Cards(0, 1, 4, null, "minor", 0, "Luke Skywalker"));
		deck.add(new Cards(0, 1, 4, null, "minor", 0, "Luke Skywalker"));
		
		
		
		// Move Luke and Leia 6 spaces each, draw 2 cards
		deck.add(new Cards(1, 0, 0,
				"Children of the Force : Move Major 6 : Move Minor 6 : Draw 2", "major",
				15, "Luke Skywalker"));
		deck.add(new Cards(1, 0, 0,
				"Children of the Force : Move Major 6 : Move Minor 6 : Draw 2", "major",
				15, "Luke Skywalker"));
		deck.add(new Cards(1, 0, 0,
				"Children of the Force : Move Major 6 : Move Minor 6 : Draw 2", "major",
				15, "Luke Skywalker"));
		// Move Luke and Leia 6 spaces each, draw 2 cards
		deck.add(new Cards(1, 0, 0,
				"Children of the Force : Move Major 6 : Move Minor 6 : Draw 2", "major",
				15, "Luke Skywalker"));
		deck.add(new Cards(1, 0, 0,
				"Children of the Force : Move Major 6 : Move Minor 6 : Draw 2", "major",
				15, "Luke Skywalker"));
		deck.add(new Cards(1, 0, 0,
				"Children of the Force : Move Major 6 : Move Minor 6 : Draw 2", "major",
				15, "Luke Skywalker"));
		// Move Luke and Leia 6 spaces each, draw 2 cards
		deck.add(new Cards(1, 0, 0,
				"Children of the Force : Move Major 6 : Move Minor 6 : Draw 2", "major",
				15, "Luke Skywalker"));
		deck.add(new Cards(1, 0, 0,
				"Children of the Force : Move Major 6 : Move Minor 6 : Draw 2", "major",
				15, "Luke Skywalker"));
		deck.add(new Cards(1, 0, 0,
				"Children of the Force : Move Major 6 : Move Minor 6 : Draw 2", "major",
				15, "Luke Skywalker"));
		// Move Luke and Leia 6 spaces each, draw 2 cards
		deck.add(new Cards(1, 0, 0,
				"Children of the Force : Move Major 6 : Move Minor 6 : Draw 2", "major",
				15, "Luke Skywalker"));
		deck.add(new Cards(1, 0, 0,
				"Children of the Force : Move Major 6 : Move Minor 6 : Draw 2", "major",
				15, "Luke Skywalker"));
		deck.add(new Cards(1, 0, 0,
				"Children of the Force : Move Major 6 : Move Minor 6 : Draw 2", "major",
				15, "Luke Skywalker"));
		// Move Luke and Leia 6 spaces each, draw 2 cards
		deck.add(new Cards(1, 0, 0,
				"Children of the Force : Move Major 6 : Move Minor 6 : Draw 2", "major",
				15, "Luke Skywalker"));
		deck.add(new Cards(1, 0, 0,
				"Children of the Force : Move Major 6 : Move Minor 6 : Draw 2", "major",
				15, "Luke Skywalker"));
		deck.add(new Cards(1, 0, 0,
				"Children of the Force : Move Major 6 : Move Minor 6 : Draw 2", "major",
				15, "Luke Skywalker"));
		
	}
}
