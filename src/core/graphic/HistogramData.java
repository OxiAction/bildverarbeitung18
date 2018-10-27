package core.graphic;

public class HistogramData {
    private int[][] image;

    // 100 shades of grey
    private int[] histogramData = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

    /*
     * Constructors
     */
    public HistogramData(){ }
    public HistogramData(int[][] image){
        this.image = image;
    }

    /*
     * Getter for HistogramData
     * @return  histogramData   int array of 100 grey values
     */
    public int[] getHistogramData() {
        return histogramData;
    }

    /*
     * Generates the histogram data out of the image and stores it to histogramData
     */
    public void generate(){
        for(int i = 0; i < image.length; i++){
            for(int j = 0; j < image[i].length; j++){
                this.histogramData[image[i][j]] += 1;
            }
        }
    }
}
