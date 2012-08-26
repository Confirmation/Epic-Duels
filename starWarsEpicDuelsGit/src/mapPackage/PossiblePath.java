package mapPackage;

public class PossiblePath {

	static int possibleMap[][];
	static int charact;

	// Returns a map where the only thing that has changed was placing
	// a 6 on squares that can be moved to

	// Finds the player and then calls RecursionTime to replace the
	// spaces with a 6
	public static int[][] GetPossiblePath(int[][] map, int character, int move) {
		possibleMap = copy(map);
		charact = (character / 10) % 2;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (possibleMap[i][j] == character) {
					RecursionTime(i, j, move);
					return possibleMap;
				}
			}
		}
		return possibleMap;
	}

	public static int[][] copy(int[][] map) {
		int[][] copy = new int[map.length][map[0].length];
		for (int i = 0; i < copy.length; i++) {
			for (int j = 0; j < copy[0].length; j++) {
				copy[i][j] = map[i][j];
			}
		}
		return copy;
	}

	// Calls on itself, reducing the amount of time it moves each time
	// Until all squares that are moveable are found
	private static void RecursionTime(int X, int Y, int move) {
		if (X >= 0 && X < possibleMap.length && Y >= 0
				&& Y < possibleMap[0].length) {
			if (possibleMap[X][Y] == 0)
				possibleMap[X][Y] = 6;
			if (((possibleMap[X][Y] == 6) || (possibleMap[X][Y] == 0) || ((possibleMap[X][Y] / 10) % 2) == charact)
					&& move > 0 && possibleMap[X][Y] != 1) {
				RecursionTime(X - 1, Y, move - 1);
				RecursionTime(X + 1, Y, move - 1);
				RecursionTime(X, Y - 1, move - 1);
				RecursionTime(X, Y + 1, move - 1);
			}
		}
	}

	public static void removePaths(int[][] map) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] == 6) {
					map[i][j] = 0;
				}
			}
		}
	}
}
