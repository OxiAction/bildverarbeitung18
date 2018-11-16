package core.evaluation;

public class Metric {
	public static final String NAME_CORRELATION = "Correlation";
	public static final String NAME_CHI_SQUARE = "Chi-Square";
	public static final String NAME_INTERSECTION = "Intersection";
	public static final String NAME_BHATTACHARYY_DISTANCE = "Bhattacharyya Distance";

	protected static final String[] NAMES = { NAME_CORRELATION, NAME_CHI_SQUARE, NAME_INTERSECTION, NAME_BHATTACHARYY_DISTANCE };
	
	/**
	 * TODO [Markus] implement
	 * 
	 * @param args
	 * @return
	 */
	public static double getCorrelationData(int h1, int h2) {
		return 0.0;
	}
	
	/**
	 * TODO [Markus] implement
	 * 
	 * @param args
	 * @return
	 */
	public static double getChiSquareData(int h1, int h2) {
		return 0.0;
	}
	
	/**
	 * TODO [Markus] implement
	 * 
	 * @param args
	 * @return
	 */
	public static double getIntersectionData(int h1, int h2) {
		return 0.0;
	}
	
	/**
	 * TODO [Markus] implement
	 * 
	 * @param args
	 * @return
	 */
	public static double getBhattacharyyaDistanceData(int h1, int h2) {
		return 0.0;
	}
	
	/**
	 * get metric data by metric name
	 * 
	 * @param args
	 * @return
	 */
	public static double getDataByName(String name, int h1, int h2) {
		switch (name) {
		case NAME_CORRELATION:
			return getCorrelationData(h1, h2);
		case NAME_CHI_SQUARE:
			return getChiSquareData(h1, h2);
		case NAME_INTERSECTION:
			return getIntersectionData(h1, h2);
		case NAME_BHATTACHARYY_DISTANCE:
			return getBhattacharyyaDistanceData(h1, h2);
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
