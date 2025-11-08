/*
Joshua Thomas Price
Oklahoma State University
CS-3513 (Numerical Methods for Digital Computing)
11-07-2025
-----------
Project 3, NIV_Model
What is this program for?

This model handles all the computational logic for polynomial interpolation
using Chebyshev nodes. It acts as the middle-man between the GUI and the
ChebyshevInterpolation class.
*/

public class NIV_Model {
    
    private static final double DEFAULT_X_MIN = -1.0;
    private static final double DEFAULT_X_MAX = 1.0;
    private static final double DEFAULT_Y_MIN = -5.0;
    private static final double DEFAULT_Y_MAX = 5.0;
    private static final int DEFAULT_NUM_POINTS = 5;
    
    private double mergeThreshold;
    private ChebyshevInterpolation.ChebyshevResult currentResult;
    
    public NIV_Model() {
        this.mergeThreshold = 0.1;
    }
    
    /**
     * @param numPoints Number of data points to generate
     * @param xMin
     * @param xMax
     * @param yMin
     * @param yMax
     * @return ChebyshevResult containing data points and interpolation curve
     */
    public ChebyshevInterpolation.ChebyshevResult performInterpolation(
        int numPoints, double xMin, double xMax, double yMin, double yMax) {
    
        if (numPoints < 3 || numPoints > 9) {
            throw new IllegalArgumentException("Number of points must be between 3 and 9");
        }
        if (xMin >= xMax || yMin >= yMax) {
            throw new IllegalArgumentException("Invalid range: min must be less than max");
        }
        
        currentResult = ChebyshevInterpolation.performInterpolation(
            numPoints, xMin, xMax, yMin, yMax);
        
        double[][] mergedPoints = mergeClosePoints(currentResult.dataPoints);
        
        if (mergedPoints.length != currentResult.dataPoints.length) {
            currentResult = ChebyshevInterpolation.performInterpolationWithPoints(
                mergedPoints, xMin, xMax);
        }
        
        return currentResult;
    }
    
    /**
     * Does interpolation with default parameters.
     * 
     * @return ChebyshevResult with default parameters
     */
    public ChebyshevInterpolation.ChebyshevResult performDefaultInterpolation() {
        return performInterpolation(
            DEFAULT_NUM_POINTS, 
            DEFAULT_X_MIN, 
            DEFAULT_X_MAX, 
            DEFAULT_Y_MIN, 
            DEFAULT_Y_MAX
        );
    }
    
    /**
     * Does interpolation with a random number of points between 6 and 8.
     * 
     * @return ChebyshevResult with random number of points
     */
    public ChebyshevInterpolation.ChebyshevResult performRandomInterpolation() {
        java.util.Random random = new java.util.Random();
        int numPoints = 6 + random.nextInt(3);
        
        return performInterpolation(
            numPoints,
            DEFAULT_X_MIN,
            DEFAULT_X_MAX,
            DEFAULT_Y_MIN,
            DEFAULT_Y_MAX
        );
    }

    /**
     * Merges points that are closer than the threshold distance using Euclidean distance.
     * When two points are close, they are replaced with their average.
     * 
     * @param points Original data points
     * @return Merged data points with close points averaged
     */
    public double[][] mergeClosePoints(double[][] points) {
        if (points == null || points.length == 0) {
            return points;
        }
        
        java.util.ArrayList<double[]> mergedList = new java.util.ArrayList<>();
        boolean[] merged = new boolean[points.length];
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Point Merging Analysis");
        System.out.println("=".repeat(50));
        System.out.println("Merge threshold: " + mergeThreshold);
        System.out.println("Original number of points: " + points.length);
        
        for (int i = 0; i < points.length; i++) {
            if (merged[i]) {
                continue;
            }
            
            double sumX = points[i][0];
            double sumY = points[i][1];
            int count = 1;
            
            for (int j = i + 1; j < points.length; j++) {
                if (merged[j]) {
                    continue;
                }
                
                double dx = points[i][0] - points[j][0];
                double dy = points[i][1] - points[j][1];
                double distance = Math.sqrt(dx * dx + dy * dy);
                
                if (distance < mergeThreshold) {
                    System.out.printf("Merging points: (%.4f, %.4f) and (%.4f, %.4f) - Distance: %.4f\n",
                        points[i][0], points[i][1], points[j][0], points[j][1], distance);
                    
                    sumX += points[j][0];
                    sumY += points[j][1];
                    count++;
                    merged[j] = true;
                }
            }
            
            double avgX = sumX / count;
            double avgY = sumY / count;
            mergedList.add(new double[]{avgX, avgY});
            
            if (count > 1) {
                System.out.printf("Created averaged point: (%.4f, %.4f) from %d points\n", avgX, avgY, count);
            }
        }
        
        System.out.println("Final number of points after merging: " + mergedList.size());
        System.out.println("=".repeat(50) + "\n");
        
        return mergedList.toArray(new double[0][]);
    }

    /**
     * Tests multiple polynomial orders to find the best fit.
     * Fits lower-order polynomials as approximations to the full dataset.
     * 
     * @param dataPoints Data points to interpolate
     * @param minOrder Minimum polynomial order to test
     * @param maxOrder Maximum polynomial order to test
     * @param xMin
     * @param xMax
     * @return Array of ChebyshevResults for each order tested
     */
    public ChebyshevInterpolation.ChebyshevResult[] testMultipleOrders(
            double[][] dataPoints, int minOrder, int maxOrder, double xMin, double xMax) {
        
        if (dataPoints == null || dataPoints.length < 2) {
            throw new IllegalArgumentException("Need at least 2 points for interpolation");
        }
        if (minOrder < 1) {
            minOrder = 1;
        }
        if (maxOrder >= dataPoints.length) {
            maxOrder = dataPoints.length - 1;
        }
        if (minOrder > maxOrder) {
            throw new IllegalArgumentException("Min order must be <= max order");
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Testing Multiple Polynomial Orders");
        System.out.println("=".repeat(50));
        System.out.println("Testing orders from " + minOrder + " to " + maxOrder);
        System.out.println("Number of data points: " + dataPoints.length);
        
        int numOrders = maxOrder - minOrder + 1;
        ChebyshevInterpolation.ChebyshevResult[] results = new ChebyshevInterpolation.ChebyshevResult[numOrders];
        
        for (int order = minOrder; order <= maxOrder; order++) {
            int numPointsForOrder = order + 1;
            double[][] selectedPoints = selectBestPointsForOrder(dataPoints, numPointsForOrder);
            
            results[order - minOrder] = ChebyshevInterpolation.performInterpolationWithPoints(
                selectedPoints, xMin, xMax);
            
            double error = calculateApproximationError(dataPoints, results[order - minOrder]);
            
            System.out.printf("Order %d: Using %d points, Approximation error: %.6f\n", 
                            order, numPointsForOrder, error);
        }
        
        System.out.println("=".repeat(50) + "\n");
        
        return results;
    }

    /**
     * Selects the best subset of points for a given polynomial order.
     * Uses evenly distributed points from the dataset.
     * 
     * @param allPoints All available data points
     * @param numPointsNeeded Number of points needed for the order
     * @return Selected subset of points
     */
    private double[][] selectBestPointsForOrder(double[][] allPoints, int numPointsNeeded) {
        if (numPointsNeeded >= allPoints.length) {
            return allPoints;
        }
        
        double[][] selected = new double[numPointsNeeded][2];
        
        for (int i = 0; i < numPointsNeeded; i++) {
            int index = (int) Math.round(i * (allPoints.length - 1.0) / (numPointsNeeded - 1.0));
            selected[i] = allPoints[index];
        }
        
        return selected;
    }

    /**
     * Calculates the approximation error of a polynomial fit.
     * Sees how well the polynomial approximates all original data points.
     * 
     * @param originalPoints All original data points
     * @param result The interpolation result to evaluate
     * @return Root mean square error across all points
     */
    private double calculateApproximationError(double[][] originalPoints, 
                                            ChebyshevInterpolation.ChebyshevResult result) {
        double sumSquaredError = 0.0;
        
        for (int i = 0; i < originalPoints.length; i++) {
            double x = originalPoints[i][0];
            double yActual = originalPoints[i][1];
            double yPredicted = ChebyshevInterpolation.lagrangeInterpolation(result.dataPoints, x);
            double error = yActual - yPredicted;
            sumSquaredError += error * error;
        }
        
        return Math.sqrt(sumSquaredError / originalPoints.length);
    }
    
    //--------------- Getters and Setters ----------------

    /**
     * Sets the threshold distance for merging close points.
     * 
     * @param threshold Distance threshold for point merging
     */
    public void setMergeThreshold(double threshold) {
        if (threshold < 0) {
            throw new IllegalArgumentException("Threshold must be non-negative");
        }
        this.mergeThreshold = threshold;
    }
    
    /**
     * Gets the current merge threshold.
     * 
     * @return Current threshold value
     */
    public double getMergeThreshold() {
        return mergeThreshold;
    }
    
    /**
     * Gets the most recent interpolation result.
     * 
     * @return Current ChebyshevResult or null if no interpolation performed yet
     */
    public ChebyshevInterpolation.ChebyshevResult getCurrentResult() {
        return currentResult;
    }
}