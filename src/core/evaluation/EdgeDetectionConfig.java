package core.evaluation;

public class EdgeDetectionConfig {
	// black / white tresholds
	public int threshold1 = 255;
	public int threshold2 = 0;
	// position of the finger -> 1 = horizontal, 2 = vertical
	public int fingerPosition = 0;
	// fixed (cropping) size (origin -> middle)
	public int fixedSizeFromMiddle = 0;
	// search for adjacent lines
	public boolean findAdjacentLinesFromMiddle = false;
	// edge offset - used for ALL calculations
	public int offsetX = 10;
	public int offsetY = 10;
	// clear top
	public boolean clearTop = false;
	// clear bottom
	public boolean clearBottom = false;
	
	public EdgeDetectionConfig() {
	}
	
	public EdgeDetectionConfig(int threshold1, int threshold2, int fingerPosition, int fixedSizeFromMiddle, boolean findAdjacentLinesFromMiddle, int offsetX, int offsetY, boolean clearTop,
			boolean clearBottom) {
		this.threshold1 = threshold1;
		this.threshold2 = threshold2;
		this.fingerPosition = fingerPosition;
		this.findAdjacentLinesFromMiddle = findAdjacentLinesFromMiddle;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.clearTop = clearTop;
		this.clearBottom = clearBottom;
	}
	
	@Override
	public String toString() {
		return "threshold1: " + String.format("%1$-3s", this.threshold1) +
				" threshold2: " + String.format("%1$-3s", this.threshold2) +
				" fingerPosition: " + this.fingerPosition +
				" fixedSizeFromMiddle: " + this.fixedSizeFromMiddle +
				" findAdjacentLinesFromMiddle: " + String.format("%1$-5s", this.findAdjacentLinesFromMiddle) +
				" offsetX: " + this.offsetX +
				" offsetY: " + this.offsetY +
				" clearTop: " + String.format("%1$-5s", this.clearTop) +
				" clearBottom: " + String.format("%1$-5s", this.clearBottom);
	}
}
