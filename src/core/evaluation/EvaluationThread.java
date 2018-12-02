package core.evaluation;

import java.io.IOException;
import java.util.HashMap;

import utils.Debug;
import utils.Utils;

/**
 * Evaluation thread class, calculating everything required for an entry.
 */
public class EvaluationThread extends Thread {
	protected int id;
	protected String absoluteFilePath;
	protected EvaluationDataSet set;

	/**
	 * Thread for evaluating an set entry.
	 * 
	 * @param id the unique id for this thread
	 * @param absoluteFilePath the absolute (image) file path (e.g. "C:/test_folder/test_image.jpg"
	 * @param set the parent set, which will contain the created EvaluationDataSetEntry
	 */
	public EvaluationThread(int id, String absoluteFilePath, EvaluationDataSet set) {
		this.id = id;
		this.absoluteFilePath = absoluteFilePath;
		this.set = set;
	}

	/**
	 * Run the thread, calculating all the necessary stuff for the entry.
	 * After everything is finished calculating, the entry will be added to the set.
	 */
	@Override
	public void run() {
		Debug.log("EvaluationThread @ running with ID: " + this.id);

		try {
			HashMap<String, String> infos = Utils.getAbsoluteFilePathInfos(this.absoluteFilePath);
			String fileName = infos.get("fileName");
			String fileFolderPath = infos.get("fileFolderPath");
			String fileExtension = infos.get("fileExtension");
			String sensorType = null;

			// get sensor type by folder structure...
			String[] partAfterSourceFolder = fileFolderPath.split(this.set.getSourceFolder());
			if (partAfterSourceFolder.length > 0) {
				partAfterSourceFolder = partAfterSourceFolder[1].split("/");
				if (partAfterSourceFolder.length > 0) {
					sensorType = partAfterSourceFolder[1];
				}
			}

			// error...
			if (sensorType == null) {
				throw new IOException("IOException: Could not determine the sensorType by folder structure!");
			}
			
			// set related config
			int sliceX = Integer.parseInt(set.getSliceX());
			int sliceY = Integer.parseInt(set.getSliceY());
			int histogramSize = Integer.parseInt(set.getHistogramSize());
			
			// create and add entry to set
			// note: kNearest argument has to be null, as we can not yet calculate metric related stuff
			int[][] greyScaleData = GreyScale.get(absoluteFilePath);
			int[][][][] greyScaleSlicedData = Utils.getChunksFromIntArray2D(greyScaleData, sliceX, sliceY);
			int[] histogramData = Histogram.get(greyScaleData, histogramSize);

			double variance = Variance.get(greyScaleData, histogramData);
			double entropy = Entropy.get(greyScaleData, histogramData);

			double[][] slicedEntropies = new double[sliceY][sliceX];
			for (int i = 0; i < sliceY; ++i) {
				for (int j = 0; j < sliceX; ++j) {
					int[] localHistogramData = Histogram.get(greyScaleSlicedData[i][j], histogramSize);
					double localEntropy = Entropy.get(greyScaleSlicedData[i][j], localHistogramData);
					
					slicedEntropies[i][j] = localEntropy;
				}
			}
			
			this.set.addEntry(
					new EvaluationDataSetEntry(this.id, fileFolderPath, fileName, fileExtension, sensorType, greyScaleData, greyScaleSlicedData, histogramData, variance, entropy, null, null, slicedEntropies));
		} catch (IOException e) {
			Debug.log("IOException: : " + e);
		}
	}
}