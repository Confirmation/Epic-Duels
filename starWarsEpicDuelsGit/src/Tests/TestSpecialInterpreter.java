//package Tests;
//
//import org.junit.Test;
//
//import deckPackage.Cards;
//import deckPackage.DarthMaulDeck;
//import deckPackage.DarthVaderDeck;
//import deckPackage.Deck;
//import deckPackage.EmperorDeck;
//import deckPackage.HanSoloDeck;
//import deckPackage.JangoDeck;
//import deckPackage.LukeSkyWalkerDeck;
//import deckPackage.ObiWanDeck;
//import deckPackage.YodaDeck;
//
//import Model.*;
//
//public class TestSpecialInterpreter {
//
//	@Test
//	public void testInterpeterMarkOne() {
//		Game g = new Game();
//		Deck deck = new LukeSkyWalkerDeck("test");
//		while (!deck.isEmpty()) {
//			System.out.println("New Card");
//			Cards c = deck.draw();
//			System.out.println(c.getSpecialAttribute());
//			g.interpreter(c.getSpecialAttribute());
//		}
//		deck = new YodaDeck("test");
//		while (!deck.isEmpty()) {
//			System.out.println("New Card");
//			Cards c = deck.draw();
//			System.out.println(c.getSpecialAttribute());
//			g.interpreter(c.getSpecialAttribute());
//		}
//		deck = new DarthMaulDeck("test");
//		while (!deck.isEmpty()) {
//			System.out.println("New Card");
//			Cards c = deck.draw();
//			System.out.println(c.getSpecialAttribute());
//			g.interpreter(c.getSpecialAttribute());
//		}
//		deck = new HanSoloDeck("test");
//		while (!deck.isEmpty()) {
//			System.out.println("New Card");
//			Cards c = deck.draw();
//			System.out.println(c.getSpecialAttribute());
//			g.interpreter(c.getSpecialAttribute());
//		}
//		deck = new DarthVaderDeck("test");
//		while (!deck.isEmpty()) {
//			System.out.println("New Card");
//			Cards c = deck.draw();
//			System.out.println(c.getSpecialAttribute());
//			g.interpreter(c.getSpecialAttribute());
//		}
//		deck = new EmperorDeck("test");
//		while (!deck.isEmpty()) {
//			System.out.println("New Card");
//			Cards c = deck.draw();
//			System.out.println(c.getSpecialAttribute());
//			g.interpreter(c.getSpecialAttribute());
//		}
//		deck = new JangoDeck("test");
//		while (!deck.isEmpty()) {
//			System.out.println("New Card");
//			Cards c = deck.draw();
//			System.out.println(c.getSpecialAttribute());
//			g.interpreter(c.getSpecialAttribute());
//		}
//		deck = new ObiWanDeck("test");
//		while (!deck.isEmpty()) {
//			System.out.println("New Card");
//			Cards c = deck.draw();
//			System.out.println(c.getSpecialAttribute());
//			g.interpreter(c.getSpecialAttribute());
//		}
//	}
//
//}
