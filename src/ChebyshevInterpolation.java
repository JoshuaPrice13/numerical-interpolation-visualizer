/*
Joshua Thomas Price
Oklahoma State University
CS-3513 (Numerical Methods for Digital Computing)
10-31-2025
-----------
Module 11, Chebyshev
What is this program for?

This program implements polynomial interpolation using Chebyshev nodes,
which is a technique that creates smooth curves through data points while avoiding
the wild oscillations that can happen with normal polynomial interpolation.

How to compile using javac from the command line
>javac ChebyshevInterpolation.java
>java ChebyshevInterpolation
 
Thank you for your time, have a good day!
*/

import java.util.Random;
import java.util.Arrays;


public class ChebyshevInterpolation {

    /**
     * Generates random data points within specified ranges. 
     * points[row][0] is for x values.
     * points[row][1] is for y values.
     * 
     * @param numPoints Number of random points to generate
     * @param xMin
     * @param xMax
     * @param yMin
     * @param yMax
     * @return double[][] of [x, y] coordinates
     */
    public static double[][] generateRandomPoints(int numPoints, double xMin, double xMax, double yMin, double yMax) {
        if (numPoints < 6 || numPoints > 8) {
            throw new IllegalArgumentException("Number of points must be between 6 and 8");
        }
        if (xMin >= xMax || yMin >= yMax) {
            throw new IllegalArgumentException("Minimum values must be less than maximum values");
        }
        
        Random random = new Random();
        double[][] points = new double[numPoints][2];
        
        for (int i = 0; i < numPoints; i++) {
            points[i][0] = xMin + (xMax - xMin) * random.nextDouble();
            points[i][1] = yMin + (yMax - yMin) * random.nextDouble();
        }
        
        return points;
    }
    
    /**
     * Computes Chebyshev nodes
     * 
     * The formula for Chebyshev nodes on is:
     *   xk = cos(k * Math.PI / (n - 1)) for k = 0, 1, ..., n-1
     * 
     * @param n Number of Chebyshev nodes to compute
     * @param xMin
     * @param xMax
     * @return double[] of n Chebyshev nodes
     */
    public static double[] computeChebyshevNodes(int n, double xMin, double xMax) {
        if (n < 2) {
            throw new IllegalArgumentException("Need at least 2 nodes for interpolation");
        }
        if (xMin >= xMax) {
            throw new IllegalArgumentException("Min must be less than Max");
        }
        
        double[] nodes = new double[n];
        
        for (int k = 0; k < n; k++) {
            // Chebyshev nodes of the second kind formula
            double nodeStandard = Math.cos(k * Math.PI / (n - 1));
            
            // Linear transformation from [-1, 1] to [xMin, xMax]
            nodes[k] = ((xMax - xMin) * nodeStandard + (xMax + xMin)) / 2.0;
        }
        
        return nodes;
    }
    
    /**
     * Lagrange interpolation - evaluates the polynomial at x
     * 
     * @param points 2D array of data points [x, y]
     * @param x The x-value to evaluate
     * @return The interpolated y-value
     */
    public static double lagrangeInterpolation(double[][] points, double x) {
        if (points == null || points.length == 0) {
            throw new IllegalArgumentException("Points array cannot be null or empty");
        }
        
        int n = points.length;
        double result = 0.0;
        
        for (int i = 0; i < n; i++) {
            double xi = points[i][0];
            double yi = points[i][1];
            
            double Li = 1.0;
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    double xj = points[j][0];
                    
                    if (Math.abs(xi - xj) < 1e-10) {
                        throw new ArithmeticException("Duplicate x-values detected");
                    }
                    
                    Li *= (x - xj) / (xi - xj);
                }
            }
            
            result += yi * Li;
        }
        
        return result;
    }
    
    /**
     * Prints concluding remarks about the interpolation results
     * 
     * @param numPoints Number of data points used
     * @param avgError Average interpolation error achieved
     */
    private static void printConcludingRemarks(int numPoints, double avgError) {
        System.out.println("=".repeat(50));
        System.out.println("Conclusion");
        System.out.println("=".repeat(50));
        
        System.out.println("Generated " + numPoints + " random data points at Chebyshev nodes.");
        System.out.println("Computed Chebyshev nodes for best interpolation point placement.");
        System.out.println("Fitted a polynomial of degree " + (numPoints - 1) + " using Lagrange interpolation");
        System.out.println("Evaluated the quality of fit (average error: " + String.format("%.6f", avgError) + ")");
        System.out.println("Generated a smooth interpolation curve through the data");
        System.out.println("=".repeat(50));
    }

    /**
     * Performs Chebyshev interpolation with pre-existing data points.
     * 
     * @param dataPoints Existing data points to interpolate
     * @param xMin Minimum x value for curve generation
     * @param xMax Maximum x value for curve generation
     * @return ChebyshevResult object containing interpolation data
     */
    public static ChebyshevResult performInterpolationWithPoints(double[][] dataPoints, double xMin, double xMax) {
        if (dataPoints == null || dataPoints.length < 2) {
            throw new IllegalArgumentException("Need at least 2 points for interpolation");
        }
        
        Arrays.sort(dataPoints, (a, b) -> Double.compare(a[0], b[0]));
        
        int numCurvePoints = 200;
        double[][] curvePoints = new double[numCurvePoints][2];
        for (int i = 0; i < numCurvePoints; i++) {
            double x = xMin + (xMax - xMin) * i / (numCurvePoints - 1);
            double y = lagrangeInterpolation(dataPoints, x);
            curvePoints[i][0] = x;
            curvePoints[i][1] = y;
        }
        
        return new ChebyshevResult(dataPoints, curvePoints, dataPoints.length - 1);
    }

    /**
     * Steps:
     * 1. Generates a random number of random data points (3-9 points)
     * 2. Computes Chebyshev nodes
     * 3. Uses Lagrange interpolation method
     * 4. Evaluates and displays interpolation results
     */
    public static void main(String[] args) {
        // domain for Chebyshev polynomials
        double xMin = -1.0;
        double xMax = 1.0;
        double yMin = -5.0;
        double yMax = 5.0;
        
        Random random = new Random();
        int numPoints = 3 + random.nextInt(7);
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Step 1: Chebyshev Nodes Computation");
        System.out.println("=".repeat(50));
        System.out.println("Computing Chebyshev nodes for " + numPoints + " points in range [" + xMin + ", " + xMax + "].");
        
        double[] chebyshevNodes = computeChebyshevNodes(numPoints, xMin, xMax);
        
        System.out.println("\nChebyshev nodes (x-coordinates):");
        for (int i = 0; i < chebyshevNodes.length; i++) {
            System.out.printf("\tNode %d: %.4f\n", i + 1, chebyshevNodes[i]);
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Step 2: Data Generation");
        System.out.println("=".repeat(50));
        System.out.println("Generating random y-values at Chebyshev nodes...");
        
        double[][] dataPoints = new double[numPoints][2];
        for (int i = 0; i < numPoints; i++) {
            dataPoints[i][0] = chebyshevNodes[i];
            dataPoints[i][1] = yMin + (yMax - yMin) * random.nextDouble();
        }
        
        System.out.println("\nGenerated data points (x, y):");
        for (int i = 0; i < dataPoints.length; i++) {
            System.out.printf(" \tPoint %d: (%.4f, %.4f)\n", i + 1, dataPoints[i][0], dataPoints[i][1]);
        }
        
        // Sort points by x-coordinate for clearer visualization
        Arrays.sort(dataPoints, (a, b) -> Double.compare(a[0], b[0]));
        
        System.out.println("\nChebyshev nodes (x-coordinates):");
        for (int i = 0; i < chebyshevNodes.length; i++) {
            System.out.printf("\tNode %d: %.4f\n", i + 1, chebyshevNodes[i]);
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Step 3: Polynomial Interpolation");
        System.out.println("=".repeat(50));
        System.out.println("Doing Lagrange polynomial interpolation.");
        System.out.println("Polynomial degree: " + (numPoints - 1));
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Step 4: Interpolation Evaluation");
        System.out.println("=".repeat(50));
        System.out.println("Evaluating how well the polynomial fits the original data.");
        
        double totalError = 0.0;
        System.out.println("\nFit quality at original data points:");
        for (int i = 0; i < dataPoints.length; i++) {
            double x = dataPoints[i][0];
            double yActual = dataPoints[i][1];
            double yInterpolated = lagrangeInterpolation(dataPoints, x);
            double error = Math.abs(yActual - yInterpolated);
            totalError += error;
            
           System.out.printf("\tPoint %d: x=%.10f, y_actual=%.10f, y_fit=%.10f, error=%.15f\n", i + 1, x, yActual, yInterpolated, error);
        }
        
        double averageError = totalError / dataPoints.length;
        System.out.printf("\nAverage absolute error: %.6f\n", averageError);
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Step 5: Interpolation Curve Visualization");
        System.out.println("=".repeat(50));
        System.out.println("Generating points along the interpolation curve.");
        System.out.println("(These show what y values the polynomial predicts for different x values)\n");
        
        int numTestPoints = 15;
        System.out.println("Sample points on interpolation curve:");
        for (int i = 0; i < numTestPoints; i++) {
            double x = xMin + (xMax - xMin) * i / (numTestPoints - 1);
            double y = lagrangeInterpolation(dataPoints, x);
            System.out.printf("  x = %.4f  ->  P(x) = %.4f\n", x, y);
        }
        
        printConcludingRemarks(numPoints, averageError);
    }

    //-------------- Adding stuff for project 3 below this line ----------------

    /**
     * Performs Chebyshev interpolation and returns the data points for graphing.
     * This is the main method the Model will call.
     * 
     * @param numPoints Number of points to generate
     * @param xMin 
     * @param xMax 
     * @param yMin 
     * @param yMax 
     * @return ChebyshevResult object containing all necessary data for graphing
     */
    public static ChebyshevResult performInterpolation(int numPoints, double xMin, double xMax, double yMin, double yMax) {
        if (numPoints < 6 || numPoints > 8) {
            throw new IllegalArgumentException("Number of points must be between 6 and 8");
        }
       
        double[] chebyshevNodes = computeChebyshevNodes(numPoints, xMin, xMax);
        
        // Generate data points at Chebyshev nodes
        Random random = new Random();
        double[][] dataPoints = new double[numPoints][2];
        for (int i = 0; i < numPoints; i++) {
            dataPoints[i][0] = chebyshevNodes[i];
            dataPoints[i][1] = yMin + (yMax - yMin) * random.nextDouble();
        }
        
        // Sort by x-coordinate
        Arrays.sort(dataPoints, (a, b) -> Double.compare(a[0], b[0]));
        
        // Generate interpolation curve points
        int numCurvePoints = 200;
        double[][] curvePoints = new double[numCurvePoints][2];
        for (int i = 0; i < numCurvePoints; i++) {
            double x = xMin + (xMax - xMin) * i / (numCurvePoints - 1);
            double y = lagrangeInterpolation(dataPoints, x);
            curvePoints[i][0] = x;
            curvePoints[i][1] = y;
        }
        
        return new ChebyshevResult(dataPoints, curvePoints, numPoints - 1);
    }

    /**
     * Inner class to hold the results of Chebyshev interpolation.
     * I usually don't do innner classes but this one is small and
     * makes it easy to pass all necessary data to the GUI.
     */
    public static class ChebyshevResult {
        public final double[][] dataPoints;
        public final double[][] curvePoints;
        public final int polynomialOrder; 
        
        public ChebyshevResult(double[][] dataPoints, double[][] curvePoints, int polynomialOrder) {
            this.dataPoints = dataPoints;
            this.curvePoints = curvePoints;
            this.polynomialOrder = polynomialOrder;
        }
    }
}