package core.evaluation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import core.data.Config;
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
	 * @param id 				the unique id for this thread
	 * @param absoluteFilePath 	the absolute (image) file path (e.g. "C:/test_folder/test_image.jpg"
	 * @param set 				the parent set, which will contain the created EvaluationDataSetEntry
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

			// fetch set configuration stuff
			int sliceX = set.getSliceX();
			int sliceY = set.getSliceY();
			int histogramSize = set.getHistogramSize();
			
			BufferedImage bufferedImage = new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_GRAY);
			bufferedImage = ImageIO.read(new File(absoluteFilePath));

			// calculate data for this entry
			CropData cropData = null;
			if (set.getEdgeDetection()) {
				EdgeDetectionConfig edgeDetectionConfig = null;
				if (Config.sensorsEdgeDetectionConfig.containsKey(sensorType)) {
					// fetch config
					edgeDetectionConfig = Config.sensorsEdgeDetectionConfig.get(sensorType);
				} else {
					// default config
					edgeDetectionConfig = new EdgeDetectionConfig();
				}
				Debug.log("thread id: " + this.id + " path: " + this.absoluteFilePath + " config: " + edgeDetectionConfig);
				cropData = EdgeDetection.getCropData(bufferedImage, edgeDetectionConfig);
			} else {
				Debug.log("thread id: " + this.id + " path: " + this.absoluteFilePath);
			}
			
			
			
			int[][] greyScaleData = GreyScale.get(bufferedImage, cropData);
			int[][][][] greyScaleSlicedData = Utils.getChunksFromIntArray2D(greyScaleData, sliceX, sliceY);
			int[] histogramData = Histogram.get(greyScaleData, histogramSize, true);

			double variance = Variance.get(greyScaleData, histogramData);
			double entropy = Entropy.get(greyScaleData, histogramData);

			double[][] slicedEntropies = new double[sliceY][sliceX];
			double[][] slicedVariances = new double[sliceY][sliceX];
			for (int i = 0; i < sliceY; ++i) {
				for (int j = 0; j < sliceX; ++j) {
					int[] localHistogramData = Histogram.get(greyScaleSlicedData[i][j], histogramSize, true);
					
					double localEntropy = Entropy.get(greyScaleSlicedData[i][j], localHistogramData);
					double localVariance = Variance.get(greyScaleSlicedData[i][j], localHistogramData);

					slicedEntropies[i][j] = localEntropy;
					slicedVariances[i][j] = localVariance;
				}
			}

			// TODO: Use one of these instead of (local (?)) histogramData
			int[] entropyHistogramData = Histogram.getIntHistogramForDoubleValues(slicedEntropies,
					set.histogramSizeForEntropy);
			int[] varianceHistogramData = Histogram.getIntHistogramForDoubleValues(slicedVariances,
					set.histogramSizeForVariance);

			// create and add entry to set
			this.set.addEntry(
					new EvaluationDataSetEntry(
							this.id,
							fileFolderPath,
							fileName,
							fileExtension,
							sensorType,
							greyScaleData,
							greyScaleSlicedData,
							histogramData,
							variance,
							slicedVariances,
							entropy,
							slicedEntropies,
							null, // kNearestIDs argument has to be null, as we can not yet calculate metric related stuff
							null  // kNearestSensorTypes argument has to be null, as we can not yet calculate metric related stuff
							)
					);
		} catch (IOException e) {
			Debug.log("IOException: : " + e);
		}
	}
}