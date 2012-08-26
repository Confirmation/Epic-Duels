package mapPackage;

public class AISafeFinder {

	static int attackMap[][];
	static int charact;

	// Returns a map where the only thing that has changed was placing
	// a 6 on squares that can be moved to

	// Finds the player and then calls RecursionTime to replace the
	// spaces with a 6
	public static int[][] GetAttackSquares(int[][] map, int character,
			boolean ranged) {
		attackMap = copy(map);
		charact = (character / 10) % 2;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (attackMap[i][j] == character) {
					RecursionHub(i, j, ranged);
					return attackMap;
				}
			}
		}
		return attackMap;
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
	private static void RecursionHub(int X, int Y, boolean ranged) {
		if (X >= 0 && X < attackMap.length && Y >= 0 && Y < attackMap[0].length) {
			if (!ranged) {
				if (X - 1 >= 0 && Y - 1 >= 0 && attackMap[X - 1][Y - 1] == 0)
					attackMap[X - 1][Y - 1] = 9;
				if (X - 1 >= 0 && attackMap[X - 1][Y] == 0)
					attackMap[X - 1][Y] = 9;
				if (X - 1 >= 0 && Y + 1 < attackMap[0].length
						&& attackMap[X - 1][Y + 1] == 0)
					attackMap[X - 1][Y + 1] = 9;

				if (Y - 1 >= 0 && attackMap[X][Y - 1] == 0)
					attackMap[X][Y - 1] = 9;
				if (Y + 1 < attackMap[0].length && attackMap[X][Y + 1] == 0)
					attackMap[X][Y + 1] = 9;

				if (X + 1 < attackMap.length && Y - 1 >= 0
						&& attackMap[X + 1][Y - 1] == 0)
					attackMap[X + 1][Y - 1] = 9;
				if (X + 1 < attackMap.length && attackMap[X + 1][Y] == 0)
					attackMap[X + 1][Y] = 9;
				if (X + 1 < attackMap.length && Y + 1 < attackMap[0].length
						&& attackMap[X + 1][Y + 1] == 0)
					attackMap[X + 1][Y + 1] = 9;
			} else {
				ranged(X, Y, 0);
				ranged(X, Y, 1);
				ranged(X, Y, 2);
				ranged(X, Y, 3);
				ranged(X, Y, 4);
				ranged(X, Y, 5);
				ranged(X, Y, 6);
				ranged(X, Y, 7);
			}
		}
	}

	// clockwise: 0 = north, 1 = north-east, 2 = east,
	// 3 = south-east, 4 = south, 5 = south-west, 6 = west, 7 = north-west
	public static void ranged(int X, int Y, int direction) {
		if (X >= 0 && X < attackMap.length && Y >= 0 && Y < attackMap[0].length) {

			if (attackMap[X][Y] == 0)
				attackMap[X][Y] = 9;

			if (((attackMap[X][Y] == 9) || (attackMap[X][Y] == 0) || ((attackMap[X][Y] / 10) % 2) == charact)
					&& attackMap[X][Y] != 1) {
				if (direction == 0)
					ranged(X - 1, Y, direction);
				else if (direction == 1)
					ranged(X - 1, Y + 1, direction);
				else if (direction == 2)
					ranged(X, Y + 1, direction);
				else if (direction == 3)
					ranged(X + 1, Y + 1, direction);
				else if (direction == 4)
					ranged(X + 1, Y, direction);
				else if (direction == 5)
					ranged(X + 1, Y - 1, direction);
				else if (direction == 6)
					ranged(X, Y - 1, direction);
				else if (direction == 7)
					ranged(X - 1, Y - 1, direction);
			}
		}
	}

	public static void removePaths(int[][] map) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] == 9) {
					map[i][j] = 0;
				}
			}
		}
	}
}
