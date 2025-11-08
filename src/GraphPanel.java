import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;


public class GraphPanel extends JPanel {
    
    private XYSeriesCollection dataset;
    private JFreeChart chart;
    private ChartPanel chartPanel;
    
    /**
     * Constructor initializes the graph panel with a basic chart setup.
     */
    public GraphPanel() {
        setLayout(new BorderLayout());
        
        
        dataset = new XYSeriesCollection();
        
        chart = ChartFactory.createXYLineChart(
            "Polynomial Interpolation",  // Chart title
            "X",                          // X-axis label
            "Y",                          // Y-axis label
            dataset                       // Dataset
        );
        
        XYPlot plot = chart.getXYPlot();
        
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        plot.setRenderer(renderer);
        
        // Create the chart panel and add it to this panel
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        add(chartPanel, BorderLayout.CENTER);
        
        // Add sample data
        addSampleData();
    }
    
    /**
     * Clears all data from the chart.
     */
    public void clearData() {
        dataset.removeAllSeries();
    }
    
    /**
     * Adds a series of data points to the chart.
     * 
     * @param seriesName The name of the series
     * @param xValues
     * @param yValues
     * @param showLines Whether to draw lines connecting points
     * @param showShapes Whether to show shapes at data points
     */
    public void addSeries(String seriesName, double[] xValues, double[] yValues, 
                         boolean showLines, boolean showShapes) {
        XYSeries series = new XYSeries(seriesName);
        
        for (int i = 0; i < xValues.length && i < yValues.length; i++) {
            series.add(xValues[i], yValues[i]);
        }
        
        int seriesIndex = dataset.getSeriesCount();
        dataset.addSeries(series);
        
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesLinesVisible(seriesIndex, showLines);
        renderer.setSeriesShapesVisible(seriesIndex, showShapes);
    }
    
    public void displayChebyshevResult(ChebyshevInterpolation.ChebyshevResult result) {
        clearData();
        
        double[] dataX = new double[result.dataPoints.length];
        double[] dataY = new double[result.dataPoints.length];
        for (int i = 0; i < result.dataPoints.length; i++) {
            dataX[i] = result.dataPoints[i][0];
            dataY[i] = result.dataPoints[i][1];
        }
        
        double[] curveX = new double[result.curvePoints.length];
        double[] curveY = new double[result.curvePoints.length];
        for (int i = 0; i < result.curvePoints.length; i++) {
            curveX[i] = result.curvePoints[i][0];
            curveY[i] = result.curvePoints[i][1];
        }
        
        addSeries("Data Points", dataX, dataY, false, true);
        addSeries("Polynomial (Order " + result.polynomialOrder + ")", curveX, curveY, true, false);
        
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesPaint(1, Color.RED);
    }

    private void addSampleData() {
        NIV_Model model = new NIV_Model();
        ChebyshevInterpolation.ChebyshevResult result = model.performRandomInterpolation();
        displayChebyshevResult(result);
    }

    /**
     * Displays multiple polynomial orders on the same chart.
     * I am making each polynomial a different color.
     * 
     * @param results Array of ChebyshevResults for different orders
     * @param allDataPoints All original data points to display
     */
    public void displayMultipleOrders(ChebyshevInterpolation.ChebyshevResult[] results, double[][] allDataPoints) {
        clearData();
        
        if (results == null || results.length == 0) {
            return;
        }
        
        double[] dataX = new double[allDataPoints.length];
        double[] dataY = new double[allDataPoints.length];
        for (int i = 0; i < allDataPoints.length; i++) {
            dataX[i] = allDataPoints[i][0];
            dataY[i] = allDataPoints[i][1];
        }
        
        addSeries("Data Points", dataX, dataY, false, true);
        
        Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.MAGENTA, Color.CYAN};
        
        for (int i = 0; i < results.length; i++) {
            ChebyshevInterpolation.ChebyshevResult result = results[i];
            
            double[] curveX = new double[result.curvePoints.length];
            double[] curveY = new double[result.curvePoints.length];
            for (int j = 0; j < result.curvePoints.length; j++) {
                curveX[j] = result.curvePoints[j][0];
                curveY[j] = result.curvePoints[j][1];
            }
            
            addSeries("Order " + result.polynomialOrder, curveX, curveY, true, false);
            
            XYPlot plot = chart.getXYPlot();
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
            renderer.setSeriesPaint(i + 1, colors[i % colors.length]);
        }
        
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.BLACK);
    }
}