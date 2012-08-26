package mapPackage;

public class GeonosisMap extends MainMap {

	public GeonosisMap() {
		map = new int[][] {

				{ 0, 0, 0, 0, 0, 0, 5, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 1, 1, 1, 5, 0 },
				{ 4, 0, 0, 0, 0, 0, 5, 1, 0, 0 },
				{ 0, 0, 4, 0, 0, 0, 0, 0, 5, 0 },
				{ 4, 4, 0, 4, 0, 0, 0, 0, 0, 5 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 1, 1, 1, 4, 0, 1, 1, 1, 1 } };
		name = "geonosis";
	}
}