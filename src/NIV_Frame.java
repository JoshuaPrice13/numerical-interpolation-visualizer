import javax.swing.JFrame;
import javax.swing.JPanel;

public class NIV_Frame extends JFrame{

    private NIV_Model model;
    private GraphPanel graphPanel;

    //Contrutor
    public NIV_Frame (){
        setTitle("Root Finding Methods Visualizer");
        setSize(800,700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        model = new NIV_Model();

        graphPanel = new GraphPanel();
        add(graphPanel);

        performInitialInterpolation();
        
        setVisible(true);
    }

    /**
     * Performs initial interpolation and displays results with multiple polynomial orders.
     */
    private void performInitialInterpolation() {
        ChebyshevInterpolation.ChebyshevResult result = model.performRandomInterpolation();
        
        int numOriginalPoints = result.dataPoints.length;
        double[][] mergedPoints = result.dataPoints;
        int numMergedPoints = mergedPoints.length;
        
        int maxOrder = numMergedPoints - 1;
        int minOrder = 1;
        
        ChebyshevInterpolation.ChebyshevResult[] multipleResults = 
            model.testMultipleOrders(mergedPoints, minOrder, maxOrder, -1.0, 1.0);
        
       // model.printInterpolationExplanation(multipleResults, numOriginalPoints, numMergedPoints);
        
        graphPanel.displayMultipleOrders(multipleResults, mergedPoints);
    }
}