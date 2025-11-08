

public class NIV_Model {
    
    public static final int MAX_ITERATIONS = 100000;
    public static final double TOLERANCE = 1e-12;
    
    public static double f(double co1, double co2, double co3, double x){
        double tmp = co1 * Math.exp(co2 * x) - (co3 * Math.cos(x));
        return tmp;
    }

    // Derivative for y=a*ebx−(c*cos(x))
    public static double fprime(double co1, double co2, double co3, double x){
        double tmp = co1 * co2 * Math.exp(co2 * x) + (co3 * Math.sin(x));
        return tmp;
    }
    
    public double Bisection(double co1, double co2, double co3, float ig1, float ig2){
        
        double min = Math.min(ig1, ig2);
        double max = Math.max(ig1, ig2);
        
        double fMin = f(co1, co2, co3, min);
        double fMax = f(co1, co2, co3, max);
        
        if (fMin * fMax >= 0) {
            System.out.println("Initial guesses do not bracket a root");
            return 0;
        }
        
        int iterations = 0;
        double mid = 0;
        
        while (iterations < MAX_ITERATIONS) {
            iterations++;
            
            // 2. Solve the equation at the midpoint between max and min.
            mid = (min + max) / 2.0;
            double fMid = f(co1, co2, co3, mid);
            
            // 3. Is the solution 0, or close to 0?  That's probably the root.
            if (Math.abs(fMid) <= TOLERANCE) {
                //System.out.println("Root found at: " + mid + " after " + iterations + " iterations");
                return mid;
            }
            
            // 5. are max and min arbitrarily close?  Then the root might be between them!
            if (Math.abs(max - min) <= TOLERANCE) {
                //System.out.println("Root approximated at: " + mid + " after " + iterations + " iterations");
                return mid;
            }
            
            // 4. Replace the max or min that is the same sign as f(mid).
            if (fMid * fMin < 0) {
                max = mid;
                fMax = fMid;
            } else {
                min = mid;
                fMin = fMid;
            }
        }
        
        //System.out.println("Max iterations reached. Best approximation: " + mid);
        return mid;
    }

    public double InverseQuadratic(double co1, double co2, double co3, float ig1, float ig2){
        
        // 1. Make three guesses where might be close to the root -> x.
        double x0 = ig1;
        double x1 = ig2;
        double x2 = (ig1 + ig2) / 2.0;
        
        int iterations = 0;
        
        while (iterations < MAX_ITERATIONS) {
            iterations++;
            
            // 2. Solve the equation at all x.
            double f0 = f(co1, co2, co3, x0);
            double f1 = f(co1, co2, co3, x1);
            double f2 = f(co1, co2, co3, x2);
            
            // 3. Is the solution 0, or close to 0?  That's probably the root.
            if (Math.abs(f2) < TOLERANCE) {
                //System.out.println("Root found at: " + x2 + " after " + iterations + " iterations");
                return x2;
            }
            
            // 4. Find a parabola that goes through the three points (f(x),x)
            // 5. Find the root of the parabola and set the x value to nextX/
            // 6. This is just the quadratic equation.
            double L0 = (x0 * f1 * f2) / ((f0 - f1) * (f0 - f2));
            double L1 = (x1 * f0 * f2) / ((f1 - f0) * (f1 - f2));
            double L2 = (x2 * f0 * f1) / ((f2 - f0) * (f2 - f1));
            
            double nextX = L0 + L1 + L2;
            
            // 7. Is nextX close to x?  Then the root might be between them!  If not, set one of the current x (I assume x, but I'm not getting any guaranty) to nextX.
            if (Math.abs(nextX - x2) < TOLERANCE) {
                System.out.println("Root approximated at: " + nextX + " after " + iterations + " iterations");
                return nextX;
            }
            
            x0 = x1;
            x1 = x2;
            x2 = nextX;
        }
        
        //System.out.println("Max iterations reached. Best approximation: " + x2);
        return x2;
    }

    public double RegulaFalsi(double co1, double co2, double co3, float ig1, float ig2){
        
        // Step 1: Choose two initial points a and b such that function at those points have opposite sign i.e., f(a)⋅f(b) < 0.
        double a = ig1;
        double b = ig2;
        
        double fa = f(co1, co2, co3, a);
        double fb = f(co1, co2, co3, b);
        
        if (fa * fb >= 0) {
            System.out.println("Initial guesses do not bracket a root");
            return 0;
        }
        
        int iterations = 0;
        double c = 0;
        
        while (iterations < MAX_ITERATIONS) {
            iterations++;
            
            // Step 2: Calculate the point c where the linear approximation intersects the x-axis using the formula.
            c = a - fa * (b - a) / (fb - fa);
            
            // Step 3: Determine f(c).
            double fc = f(co1, co2, co3, c);
            
            // Step 4: Repeat the steps until ∣f(c)∣ is less than a predefined tolerance level or the interval [a, b] is sufficiently small.
            if (Math.abs(fc) <= TOLERANCE || Math.abs(b - a) <= TOLERANCE) {
                //System.out.println("Root found at: " + c + " after " + iterations + " iterations");
                return c;
            }
            
            // If f(c) ⋅ f(a) < 0, then the root lies between a and c. Set b = c.
            if (fc * fa < 0) {
                b = c;
                fb = fc;
            }
            // If f(c) ⋅ f(b) < 0, then the root lies between b and c. Set a = c.
            else {
                a = c;
                fa = fc;
            }
        }
        
        //System.out.println("Max iterations reached. Best approximation: " + c);
        return c;
    }

    public double BruteForce(double co1, double co2, double co3, float ig1, float ig2) {
        float step = 0.00001f;
        for (double x = ig1; x <= ig2; x += step) {
            double fx = f(co1, co2, co3, x);
            double fxNext = f(co1, co2, co3, x + step);
            if (fx * fxNext <= 0) {
                return x;
            }
        }
        throw new ArithmeticException("Root not found in the interval");
    }
}
