package mapPackage;

public class BlankMap extends MainMap {

	public BlankMap(int[][] newMap) {
		map = new int[newMap.length][newMap[0].length];
		for(int i = 0; i < newMap.length; i++)
			for(int j = 0; j < newMap[0].length; j++){
				map[i][j] = newMap[i][j];
			}
		name = "temp";
	}
}