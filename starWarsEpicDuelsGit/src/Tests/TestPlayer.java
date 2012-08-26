package Tests;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import deckPackage.Deck;
import deckPackage.HanSoloDeck;

import Model.Player;
import Model.Character;

public class TestPlayer {
	
	@Test
	public void testPlayerConstructor() {
		
    Character hanSolo = new Character(10, 12, "Han Solo",0,0, true);
    Character lei = new Character(12, 10, "Lei Skywalker",0,0, true);
    Deck han = new HanSoloDeck("Han Solo");
    
    Player one = new Player(hanSolo, lei, 1, han);
    
    assertEquals(0, one.getMajorLocation().x);
    assertEquals(0, one.getMajorLocation().y);
    
    assertEquals(0, one.getMinorLocation(0).x);
    assertEquals(0, one.getMinorLocation(0).y);
    
    assertEquals(0, one.getMinorLocation(0).x);
    assertEquals(0, one.getMinorLocation(0).y);
    
    one.move(one.getMajorLocation(), new Point(2,2));
    assertEquals(2, one.getMajorLocation().x);
    assertEquals(2, one.getMajorLocation().y);
    
    one.move(one.getMajorLocation(), new Point(4,5));
    assertEquals(4, one.getMajorLocation().x);
    assertEquals(5, one.getMajorLocation().y);
    
    assertEquals("Han Solo", one.getMajorCharacterName());
    assertEquals("Lei Skywalker", one.getMinorCharacterName(0));
    
    assertEquals(12, one.getMajorHealth()); 
    assertEquals(10, one.getMinorHealth(0));
 
    one.move(one.getMinorLocation(0), new Point(4,5));
    assertEquals(4, one.getMinorLocation(0).x);
    assertEquals(5, one.getMinorLocation(0).y);
		
	}

}
