package Tests;

import static org.junit.Assert.*;

import java.awt.Point;

import mapPackage.CarbonFreezeMap;
import mapPackage.EmperorThroneMap;
import mapPackage.MainMap;

import org.junit.Test;

import Model.Character;

public class TestPossiblePath {

	@Test
	public void emptyMove1() {
		Character hanSolo = new Character(10, 12, "Han Solo",0,0, true);
		MainMap currMap = new CarbonFreezeMap();
		Point start = new Point(4, 5);

		hanSolo.setCharacterLocation(start);

		currMap.addCharacter(hanSolo, start);

		assertEquals(10, currMap.getNumAt(new Point(4, 5)));
		assertEquals(0, currMap.getNumAt(new Point(5, 5)));
		assertEquals(0, currMap.getNumAt(new Point(6, 5)));
		assertEquals(0, currMap.getNumAt(new Point(3, 5)));
		assertEquals(0, currMap.getNumAt(new Point(2, 5)));
		assertEquals(0, currMap.getNumAt(new Point(4, 6)));
		assertEquals(0, currMap.getNumAt(new Point(4, 7)));
		assertEquals(0, currMap.getNumAt(new Point(4, 4)));
		assertEquals(0, currMap.getNumAt(new Point(4, 3)));
		assertEquals(0, currMap.getNumAt(new Point(5, 6)));
		assertEquals(0, currMap.getNumAt(new Point(5, 4)));
		assertEquals(0, currMap.getNumAt(new Point(3, 6)));
		assertEquals(0, currMap.getNumAt(new Point(3, 4)));

		currMap.displayPossiblePaths(hanSolo.getCharacterLocation(), 2);

		assertEquals(10, currMap.getNumAt(new Point(4, 5)));
		assertEquals(6, currMap.getNumAt(new Point(5, 5)));
		assertEquals(6, currMap.getNumAt(new Point(6, 5)));
		assertEquals(6, currMap.getNumAt(new Point(3, 5)));
		assertEquals(6, currMap.getNumAt(new Point(2, 5)));
		assertEquals(6, currMap.getNumAt(new Point(4, 6)));
		assertEquals(6, currMap.getNumAt(new Point(4, 7)));
		assertEquals(6, currMap.getNumAt(new Point(4, 4)));
		assertEquals(6, currMap.getNumAt(new Point(4, 3)));
		assertEquals(6, currMap.getNumAt(new Point(5, 6)));
		assertEquals(6, currMap.getNumAt(new Point(5, 4)));
		assertEquals(6, currMap.getNumAt(new Point(3, 6)));
		assertEquals(6, currMap.getNumAt(new Point(3, 4)));

	}

	@Test
	public void MoveWithObstacles() {
		Character hanSolo = new Character(10, 12, "Han Solo",0,0, true);
		Character chewbacca = new Character(11, 14, "Chewbacca",0,0, true);
		Character jangoFett = new Character(20, 14, "Jango",0,0, true);
		MainMap currMap = new EmperorThroneMap();
		Point start = new Point(2, 9);
		Point Chewwy = new Point(1, 9);
		Point Jango = new Point(3, 9);

		currMap.addCharacter(hanSolo, start);
		currMap.addCharacter(chewbacca, Chewwy);
		currMap.addCharacter(jangoFett, Jango);

		hanSolo.setCharacterLocation(start);
		chewbacca.setCharacterLocation(Chewwy);
		jangoFett.setCharacterLocation(Jango);

		assertEquals(10, currMap.getNumAt(new Point(2, 9)));
		assertEquals(0, currMap.getNumAt(new Point(2, 7)));
		assertEquals(2, currMap.getNumAt(new Point(2, 8)));
		assertEquals(0, currMap.getNumAt(new Point(1, 8)));
		assertEquals(11, currMap.getNumAt(new Point(1, 9)));
		assertEquals(0, currMap.getNumAt(new Point(1, 10)));
		assertEquals(2, currMap.getNumAt(new Point(2, 10)));
		assertEquals(1, currMap.getNumAt(new Point(0, 9)));
		assertEquals(20, currMap.getNumAt(new Point(3, 9)));
		assertEquals(0, currMap.getNumAt(new Point(4, 9)));
		assertEquals(0, currMap.getNumAt(new Point(4, 8)));
		assertEquals(0, currMap.getNumAt(new Point(4, 10)));
		currMap.displayPossiblePaths(hanSolo.getCharacterLocation(), 2);
		assertEquals(10, currMap.getNumAt(new Point(2, 9)));
		assertEquals(0, currMap.getNumAt(new Point(2, 7)));
		assertEquals(2, currMap.getNumAt(new Point(2, 8)));
		assertEquals(6, currMap.getNumAt(new Point(1, 8)));
		assertEquals(11, currMap.getNumAt(new Point(1, 9)));
		assertEquals(6, currMap.getNumAt(new Point(1, 10)));
		assertEquals(2, currMap.getNumAt(new Point(2, 10)));
		assertEquals(1, currMap.getNumAt(new Point(0, 9)));
		assertEquals(20, currMap.getNumAt(new Point(3, 9)));
		assertEquals(0, currMap.getNumAt(new Point(4, 9)));
		assertEquals(0, currMap.getNumAt(new Point(4, 8)));
		assertEquals(0, currMap.getNumAt(new Point(4, 10)));

	}

}
