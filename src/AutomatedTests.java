import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;

import core.data.Config;
import core.evaluation.Evaluation;
import core.evaluation.EvaluationDataSet;
import core.evaluation.EvaluationDataSetEntry;
import core.evaluation.Histogram;
import core.evaluation.Metric;
import utils.Debug;
import utils.Translation;

public class AutomatedTests {

	public static void main(String[] args) {
		Debug.enabled = true;
		// initialize config
		try {
			Config.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// initialize translation singleton
		try {
			Translation.getInstance();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("C:\\Users\\Michi\\Desktop\\grundlagen_bildv_results\\ed_bildverarbeitung_results.txt", "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
		String[] metrics = Metric.getNames();
		int[] kFactors = {1, 8, 16};
		int[] slices = {10, 50};
		int[] histogramSizes = {256, 128, 64, 32};
		String[] histogramTypes = Histogram.getTypes();
		
		int resultSetIndex = 1;
		int numSetsToTest = metrics.length * kFactors.length * slices.length * histogramSizes.length * histogramTypes.length;
		
		for (int metricIndex = 0; metricIndex < metrics.length; ++metricIndex) {
			for (int kFactorIndex = 0; kFactorIndex < kFactors.length; ++kFactorIndex) {
				for (int sliceIndex = 0; sliceIndex < slices.length; ++sliceIndex) {
					for (int histogramSizeIndex = 0; histogramSizeIndex < histogramSizes.length; ++histogramSizeIndex) {
						for (int histogramTypeIndex = 0; histogramTypeIndex < histogramTypes.length; ++histogramTypeIndex) {
							Debug.enabled = false;
							EvaluationDataSet result = null;
							EvaluationDataSet input = new EvaluationDataSet(
									new Timestamp(System.currentTimeMillis()),
									String.valueOf(resultSetIndex) + " / " + String.valueOf(numSetsToTest), // name
									"C:/Users/Michi/Desktop/testb_bildv_full", // source
									true, // edge detection
									kFactors[kFactorIndex], // k factor
									metrics[metricIndex], // metric name
									slices[sliceIndex], // slices x
									slices[sliceIndex], // slices y
									histogramTypes[histogramTypeIndex], // histogram type
									histogramSizes[histogramSizeIndex] // histogram size
									);
							
							Evaluation evaluation = new Evaluation(input);
							try {
								result = evaluation.process(input);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							if (result != null) {
								Debug.enabled = true;
								
								int sameSensors = 0;
								for (EvaluationDataSetEntry entry : result.getEntries()) {
									if (entry != null && entry.getIsSensorTypeEqualToNearestSensorType()) {
										sameSensors++;
									}
								}
								
								String dataText = result.getName() + " | " + result.getMetricName() + " | " + result.getKFactor() + " | " + result.getSliceX() + " | " + result.getHistogramType()
										+ " | " + result.getHistogramSize() + " | " + String.format("%.4f", sameSensors * 100f / result.getEntriesSize());
								
								
								Debug.log(dataText);
								writer.println(dataText);
								writer.flush();
								++resultSetIndex;
							}
						}
						Debug.log("");
						writer.println("");
					}
					Debug.log("");
					writer.println("");
				}
				Debug.log("");
				writer.println("");
			}
			Debug.log("");
			writer.println("");
		}
		
		writer.close();
	}
	
}
