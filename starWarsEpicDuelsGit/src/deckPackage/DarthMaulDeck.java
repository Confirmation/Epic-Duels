package deckPackage;

public class DarthMaulDeck extends Deck {

	public DarthMaulDeck(String name) {
		super(name);
	}

	@Override
	protected void addSpecialCards() {
		// Doesn't count as an action
		deck.add(new Cards(4, 3, 0, "Sith Speed : Remove Action Self", "major",
				27, "Darth Maul"));
		deck.add(new Cards(4, 3, 0, "Sith Speed : Remove Action Self", "major",
				27, "Darth Maul"));
		deck.add(new Cards(4, 3, 0, "Sith Speed : Remove Action Self", "major",
				27, "Darth Maul"));
		// Doesn't count as an action
		deck.add(new Cards(5, 4, 0, "Super Sith speed : Remove Action Self",
				"major", 30, "Darth Maul"));
		deck.add(new Cards(5, 4, 0, "Super Sith speed : Remove Action Self",
				"major", 30, "Darth Maul"));
		deck.add(new Cards(5, 4, 0, "Super Sith speed : Remove Action Self",
				"major", 30, "Darth Maul"));
		// Move 6 spaces
		deck.add(new Cards(1, 8, 0, "Athletic Surge : Move Major 6", "major",
				18, "Darth Maul"));
		deck.add(new Cards(1, 8, 0, "Athletic Surge : Move Major 6", "major",
				18, "Darth Maul"));
		deck.add(new Cards(1, 8, 0, "Athletic Surge : Move Major 6", "major",
				18, "Darth Maul"));
		// Attacker takes 3 damage points
		deck.add(new Cards(2, 0, -1, "Blinding Surge : Damage Direct Back 3",
				"major", 0, "Darth Maul"));
		deck.add(new Cards(2, 0, -1, "Blinding Surge : Damage Direct Back 3",
				"major", 0, "Darth Maul"));
		// Draw 1 card
		deck.add(new Cards(3, 0, 10, "Martial Defense : Draw 1", "major", 0, "Darth Maul"));
		
	}

	@Override
	protected void addNormalCards() {
		deck.add(new Cards(0, 5, 1, null, "major", 20, "Darth Maul"));
		deck.add(new Cards(0, 5, 1, null, "major", 20, "Darth Maul"));
		deck.add(new Cards(0, 5, 1, null, "major", 20, "Darth Maul"));
		deck.add(new Cards(0, 5, 1, null, "major", 20, "Darth Maul"));
		deck.add(new Cards(0, 4, 2, null, "major", 12, "Darth Maul"));
		deck.add(new Cards(0, 4, 2, null, "major", 12, "Darth Maul"));
		deck.add(new Cards(0, 4, 1, null, "major", 13, "Darth Maul"));
		deck.add(new Cards(0, 3, 2, null, "major", 6, "Darth Maul"));
		deck.add(new Cards(0, 2, 3, null, "major", 2, "Darth Maul"));
		deck.add(new Cards(0, 1, 4, null, "major", 0, "Darth Maul"));

		deck.add(new Cards(0, 3, 1, null, "minor", 0, "Darth Maul"));
		deck.add(new Cards(0, 3, 1, null, "minor", 0, "Darth Maul"));
		deck.add(new Cards(0, 2, 1, null, "minor", 0, "Darth Maul"));
		deck.add(new Cards(0, 2, 1, null, "minor", 0, "Darth Maul"));
		deck.add(new Cards(0, 2, 1, null, "minor", 0, "Darth Maul"));
		deck.add(new Cards(0, 1, 2, null, "minor", 0, "Darth Maul"));
		deck.add(new Cards(0, 1, 2, null, "minor", 0, "Darth Maul"));
		deck.add(new Cards(0, 1, 2, null, "minor", 0, "Darth Maul"));
     	deck.add(new Cards(0, 1, 2, null, "minor", 0, "Darth Maul"));
	}

}
