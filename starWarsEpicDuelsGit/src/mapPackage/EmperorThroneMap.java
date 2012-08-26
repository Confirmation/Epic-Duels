package mapPackage;

import java.io.Serializable;

public class EmperorThroneMap extends MainMap {

	public EmperorThroneMap() {
		map = new int[][] { 
				{ 0, 0, 0, 0, 1, 1, 1, 1, 1, 1 },
				{ 2, 5, 2, 5, 0, 0, 0, 4, 1, 1 },
				{ 2, 0, 2, 2, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 5, 0, 0, 4, 4, 4, 4 },
				{ 2, 5, 2, 2, 0, 0, 0, 0, 0, 0 },
				{ 2, 0, 2, 5, 0, 0, 0, 4, 1, 1 },
				{ 0, 0, 0, 0, 1, 1, 1, 1, 1, 1 } };
		name = "throne";
	}
}