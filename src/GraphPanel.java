import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

/**
 * Custom panel that displays a 2D graph using JFreeChart.
 * This panel can display multiple data series including scatter plots
 * and line plots for polynomial interpolation visualization.
 */
public class GraphPanel extends JPanel {
    
    private XYSeriesCollection dataset;
    private JFreeChart chart;
    private ChartPanel chartPanel;
    
    /**
     * Constructor initializes the graph panel with a basic chart setup.
     */
    public GraphPanel() {
        setLayout(new BorderLayout());
        
        // Initialize the dataset
        dataset = new XYSeriesCollection();
        
        // Create the chart
        chart = ChartFactory.createXYLineChart(
            "Polynomial Interpolation",  // Chart title
            "X",                          // X-axis label
            "Y",                          // Y-axis label
            dataset                       // Dataset
        );
        
        // Customize the chart appearance
        customizeChart();
        
        // Create the chart panel and add it to this panel
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        add(chartPanel, BorderLayout.CENTER);
        
        // Add sample data for demonstration
        addSampleData();
    }
    
    /**
     * Customizes the appearance of the chart.
     */
    private void customizeChart() {
        XYPlot plot = chart.getXYPlot();
        
        // Set background colors
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        
        // Customize the renderer
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        plot.setRenderer(renderer);
    }
    
    /**
     * Adds sample data to demonstrate the graph functionality.
     * This method should be replaced with actual data from your Chebyshev program.
     */
    private void addSampleData() {
        // Create a series for original data points (scatter plot)
        XYSeries dataPoints = new XYSeries("Data Points");
        dataPoints.add(0.0, 1.0);
        dataPoints.add(1.0, 2.0);
        dataPoints.add(2.0, 1.5);
        dataPoints.add(3.0, 3.0);
        dataPoints.add(4.0, 2.5);
        
        dataset.addSeries(dataPoints);
        
        // Create a series for a sample polynomial curve
        XYSeries polynomialCurve = new XYSeries("Polynomial (Order 2)");
        for (double x = 0.0; x <= 4.0; x += 0.1) {
            // Sample polynomial: y = 0.5x^2 - 0.5x + 1.5
            double y = 0.5 * x * x - 0.5 * x + 1.5;
            polynomialCurve.add(x, y);
        }
        
        dataset.addSeries(polynomialCurve);
        
        // Configure renderer for different series styles
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        
        // Series 0 (data points): show shapes, no lines
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesPaint(0, Color.BLUE);
        
        // Series 1 (polynomial): show lines, no shapes
        renderer.setSeriesLinesVisible(1, true);
        renderer.setSeriesShapesVisible(1, false);
        renderer.setSeriesPaint(1, Color.RED);
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
     * @param xValues Array of x-coordinates
     * @param yValues Array of y-coordinates
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
        
        // Configure the renderer for this series
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesLinesVisible(seriesIndex, showLines);
        renderer.setSeriesShapesVisible(seriesIndex, showShapes);
    }
    
    /**
     * Updates the chart title.
     * 
     * @param title New title for the chart
     */
    public void setChartTitle(String title) {
        chart.setTitle(title);
    }
    
    /**
     * Main method for testing the GraphPanel independently.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Graph Panel Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new GraphPanel());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}