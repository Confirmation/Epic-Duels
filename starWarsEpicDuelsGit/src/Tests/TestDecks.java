package Tests;

import org.junit.Test;

import deckPackage.Cards;
import deckPackage.Deck;
import deckPackage.JangoDeck;

import Model.*;

public class TestDecks {

	@Test
	public void testConstructionAndShuffling() {
		Deck d = new JangoDeck("Jango");
		while (!d.isEmpty()) {
			Cards c = d.draw();
			System.out.println(c.toString());
			d.graveYard(c);
		}
		System.out.println("Deck is empty, repopulating and shuffleing...");
		d.repopulate();
		d.shuffle();
		while (!d.isEmpty()) {
			Cards c = d.draw();
			System.out.println(c.toString());
			d.graveYard(c);
		}
	}
	
}
