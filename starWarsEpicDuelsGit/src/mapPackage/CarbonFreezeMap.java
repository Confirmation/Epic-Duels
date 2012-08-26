package mapPackage;

import java.io.Serializable;

public class CarbonFreezeMap extends MainMap {

	public CarbonFreezeMap() {
		map = new int[][] {
				{ 1, 1, 1, 5, 0, 0, 0, 0, 1, 1 },
				{ 1, 1, 0, 0, 0, 0, 0, 4, 0, 4 },
				{ 0, 0, 5, 0, 0, 0, 0, 0, 0, 0 },
				{ 5, 0, 0, 0, 0, 4, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 5, 0, 0, 4, 0, 0 },
				{ 1, 1, 0, 0, 0, 0, 0, 0, 0, 4 },
				{ 1, 1, 1, 5, 0, 0, 0, 0, 1, 1 } };
		name = "carbon";
	}
}