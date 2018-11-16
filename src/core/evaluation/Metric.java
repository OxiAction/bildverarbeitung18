package core.evaluation;

public class Metric {
	public static final String NAME_CORRELATION = "Correlation";
	public static final String NAME_CHI_SQUARE = "Chi-Square";
	public static final String NAME_INTERSECTION = "Intersection";
	public static final String NAME_BHATTACHARYY_DISTANCE = "Bhattacharyya Distance";

	protected static final String[] NAMES = { NAME_CORRELATION, NAME_CHI_SQUARE, NAME_INTERSECTION, NAME_BHATTACHARYY_DISTANCE };
	
	/**
	 * TODO implement
	 * 
	 * @param args
	 * @return
	 */
	public static int[][] getCorrelationData(int... args) {
		return null;
	}
	
	/**
	 * TODO implement
	 * 
	 * @param args
	 * @return
	 */
	public static int[][] getChiSquareData(int... args) {
		return null;
	}
	
	/**
	 * TODO implement
	 * 
	 * @param args
	 * @return
	 */
	public static int[][] getIntersectionData(int... args) {
		return null;
	}
	
	/**
	 * TODO implement
	 * 
	 * @param args
	 * @return
	 */
	public static int[][] getBhattacharyyaDistanceData(int... args) {
		return null;
	}
	
	/**
	 * get metric data by metric name
	 * 
	 * @param args
	 * @return
	 */
	public static int[][] getDataByName(String name, int... args) {
		switch (name) {
		case NAME_CORRELATION:
			return getCorrelationData(args);
		case NAME_CHI_SQUARE:
			return getChiSquareData(args);
		case NAME_INTERSECTION:
			return getIntersectionData(args);
		case NAME_BHATTACHARYY_DISTANCE:
			return getBhattacharyyaDistanceData(args);
		default:
			throw new Error("Unknwon metric Name: " + name);
		}
	}
	
	/**
	 * returns all available metric names as array
	 * 
	 * @return
	 */
	public static String[] getNames() {
		return NAMES;
	}
}
