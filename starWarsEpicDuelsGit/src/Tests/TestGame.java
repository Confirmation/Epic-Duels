/**
 * This class tests the game class which controls
 * the game and acts as the server.
 */

package Tests;

import static org.junit.Assert.*;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import mapPackage.CarbonFreezeMap;
import mapPackage.MainMap;
import mapPackage.PossiblePath;

import org.junit.Test;

import deckPackage.ObiWanDeck;

import Model.Character;
import Model.Game;
import Model.Player;

import Model.*;

public class TestGame {

	@Test
	public void testMovementNoNetwork() {
		// Start a game
		Game g = new Game(2, true, new CarbonFreezeMap());
		// create a player
		Player player = new Player(Game.getCharacterList().get(0)[0], null, 0,
				new ObiWanDeck("test"));
		// add the player to the game
		g.addPlayer(player, 0, null);

		// get the games map grid
		MainMap map = g.getMap();
		int[][] mapGrid = map.getMap();

		// a copy grid
		int[][] grid = new int[mapGrid.length][mapGrid[0].length];
		boolean contains10 = false;
		Point location = null;

		for (int i = 0; i < mapGrid.length; i++) {
			for (int j = 0; j < mapGrid[0].length; j++) {
				grid[i][j] = mapGrid[i][j];// copy the game grid
				if (mapGrid[i][j] == 10) {// find the player
					contains10 = true;
					location = new Point(i, j);
				}
			}
		}
		assertTrue(contains10);// make sure the game contains the player
		assertEquals(10, grid[location.x][location.y]);// check the identifier

		// get the grid of possible motion
		int[][] possible = PossiblePath.GetPossiblePath(grid, player
				.getMajorCharacter().getCharacterNumber(), 2);

		Point destination = null;
		for (int i = 0; i < possible.length; i++) {
			for (int j = 0; j < possible[0].length; j++) {
				// choose a destination
				if (possible[i][j] == 6) {
					destination = new Point(i, j);
					// make sure the move s more than 0 squares
					if (!destination.equals(location)) {
						break;
					}
				}
			}
		}

		// move the player
		g.move(null, location, destination, true);

		// get the changed map
		map = g.getMap();
		int[][] grid2 = map.getMap();

		// check the previous grid
		assertEquals(10, grid[location.x][location.y]);
		assertEquals(0, grid[destination.x][destination.y]);

		// check the new grid
		assertEquals(0, grid2[location.x][location.y]);
		assertEquals(10, grid2[destination.x][destination.y]);
		for (int i = 0; i < grid2.length; i++) {
			for (int j = 0; j < grid2[0].length; j++) {
				if (!((i == location.x && j == location.y) || (i == destination.x && j == destination.y))) {
					assertEquals(grid[i][j], grid2[i][j]);
				}
			}
		}
		g.kill();
	}
}
