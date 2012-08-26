package mapPackage;

import java.awt.Point;
import java.io.Serializable;

import Model.Character;

public abstract class MainMap implements Serializable {

	protected int map[][];
	protected String name;
	
	// Removes the character from the map and replaces with a zero
	public void removeCharacter(Point currLocation) {
		map[currLocation.x][currLocation.y] = 0;
	}

	public void removeCharacter(int character) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] == character) {
					map[i][j] = 0;
					return;
				}
			}
		}
	}

	public void allPossibleLocations() {
		for (int i = 0; i < map.length; i++)
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] == 0)
					map[i][j] = 6;
			}
	}

	// Adds the character to a specific location on the map
	// Notifies the character of this position.
	public void addCharacter(Character character, Point newLocation) {
		map[newLocation.x][newLocation.y] = character.getCharacterNumber();
	}

	public void addCharacter(int character, Point newLocation) {
		map[newLocation.x][newLocation.y] = character;
	}

	// Moving the character. Sets current location to zero, modifies
	// the character of his new location, and sets the new location
	// to his number
	public void moveCharacter(Point oldLocation, Point newLocation) {
		if (map[oldLocation.x][oldLocation.y] > 9) {
			int character = map[oldLocation.x][oldLocation.y];
			map[oldLocation.x][oldLocation.y] = 0;
			map[newLocation.x][newLocation.y] = character;
		}
	}

	// For clearing debris
	public void clearPoint(Point location) {
		map[location.x][location.y] = 0;
	}

	// TESTING PURPOSES
	public int[][] getMap() {
		return map;
	}

	// Changes the map to one that shows the possible movement for that
	// character
	public void displayPossiblePaths(Point location, int move) {
		PossiblePath.removePaths(map);
		int character = map[location.x][location.y];
		map = PossiblePath.GetPossiblePath(map, character, move);
	}

	// Changes the map to one that shows the possible movement for that
	// character
	public void displayAISafeFinder(Point location, boolean ranged) {
		int character = map[location.x][location.y];
		map = AISafeFinder.GetAttackSquares(map, character, ranged);
	}

	// Removes the markers PosiblePath leaves
	public void removePossiblePaths() {
		PossiblePath.removePaths(map);
	}

	// TESTING PURPSES
	public int getNumAt(Point numAt) {
		if ((numAt.x >= map.length || numAt.y >= map[0].length)
				|| (numAt.x < 0 || numAt.y < 0))
			return -1;
		return map[numAt.x][numAt.y];
	}

	public synchronized void print() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				System.out.print(map[i][j]);
			}
			System.out.println();
		}
	}

	public String getName() {
		return name;
	}

	public void removeStarts() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] == 4 || map[i][j] == 5) {
					map[i][j] = 0;
				} else if (map[i][j] == 5) {
					map[i][j] = 0;
				}
			}
		}
	}

	public Point getLocationOf(int number) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] == number) {
					return new Point(i, j);
				}
			}
		}
		return null;
	}
}
