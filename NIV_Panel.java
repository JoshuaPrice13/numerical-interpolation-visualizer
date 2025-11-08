
/*
This panel sits on the frame and acts as a controller for the 
model that utilizes the binary operations.
 */

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.BorderLayout;

public class NIV_Panel extends JPanel{

    public static NIV_Model model = new NIV_Model();

    public static JTextArea resultArea;
    public static JTextArea analyticsArea; 

    public static JTextField CO1;
    public static JTextField CO2;
    public static JTextField CO3;

    public static JTextField initGuess1;
    public static JTextField initGuess2;
    
    // Modern color scheme
    private static final Color BACKGROUND_COLOR = new Color(45, 45, 50);
    private static final Color PANEL_COLOR = new Color(55, 55, 60);
    private static final Color BUTTON_COLOR = new Color(70, 130, 180);
    private static final Color BUTTON_HOVER = new Color(100, 149, 237);
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color FIELD_COLOR = new Color(65, 65, 70);
    
    public NIV_Panel(){
        setBackground(BACKGROUND_COLOR);
        placeComponents();
    }
        
    public void placeComponents(){
        //System.out.println("Placing components now");
    
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create main input panel
        JPanel mainInputPanel = createInputPanel();
        
        // Create result panel
        JPanel resultPanel = createResultPanel();
        
        // Add panels to main layout
        add(mainInputPanel, BorderLayout.NORTH);
        add(resultPanel, BorderLayout.CENTER);
    }
    
    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
        inputPanel.setBackground(PANEL_COLOR);
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 85), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        // Fields panel
        JPanel fieldsPanel = new JPanel(new GridLayout(6, 4, 10, 15));
        fieldsPanel.setBackground(PANEL_COLOR);
        
        // Create styled labels and fields
        JLabel CO1_Text = createStyledLabel("Coefficient A:");
        CO1 = createStyledTextField();
        
        JLabel CO2_Text = createStyledLabel("Coefficient B:");
        CO2 = createStyledTextField();

        JLabel CO3_Text = createStyledLabel("Coefficient C:");
        CO3 = createStyledTextField();

        JLabel initGuess1_text = createStyledLabel("Intial Guess 1");
        initGuess1 = createStyledTextField();

        JLabel initGuess2_text = createStyledLabel("Initial Guess 2");
        initGuess2 = createStyledTextField();
        
        fieldsPanel.add(CO1_Text);
        fieldsPanel.add(CO1);
        fieldsPanel.add(CO2_Text);
        fieldsPanel.add(CO2);
        fieldsPanel.add(CO3_Text);
        fieldsPanel.add(CO3);
        fieldsPanel.add(initGuess1_text);
        fieldsPanel.add(initGuess1);
        fieldsPanel.add(initGuess2_text);
        fieldsPanel.add(initGuess2);

        JButton RandomCoefficientButton = createInputButton("Random");
        JButton RunTestsButton = createInputButton("Run Tests");

        RandomCoefficientButton.addActionListener(inputButton("RandomCoefficientButton"));
        RunTestsButton.addActionListener(inputButton("RunTestsButton"));

        fieldsPanel.add(RandomCoefficientButton);
        fieldsPanel.add(RunTestsButton);

        
        // Operator buttons panel - grouped together and smaller
        JPanel operatorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 10));
        operatorPanel.setBackground(PANEL_COLOR);

        JButton BisectionButton = createOperatorButton("Bisection");
        JButton InverseQuadraticButton = createOperatorButton("Inverse Quadratic");
        JButton RegulaFalsiButton = createOperatorButton("Regula Falsi");
        JButton BruteForceButton = createOperatorButton("Brute Force");
        
        operatorPanel.add(BisectionButton);
        operatorPanel.add(InverseQuadraticButton);
        operatorPanel.add(RegulaFalsiButton);
        operatorPanel.add(BruteForceButton);
        
        // Add action listeners
        BisectionButton.addActionListener(operationButton("Bisection"));
        InverseQuadraticButton.addActionListener(operationButton("Inverse Quadratic"));
        RegulaFalsiButton.addActionListener(operationButton("Regula Falsi"));
        BruteForceButton.addActionListener(operationButton("Brute Force"));
        
        inputPanel.add(fieldsPanel, BorderLayout.CENTER);
        inputPanel.add(operatorPanel, BorderLayout.SOUTH);
        
        return inputPanel;
    }
    
    private JPanel createResultPanel() {
        JPanel resultPanel = new JPanel(new BorderLayout(10, 10));
        resultPanel.setBackground(BACKGROUND_COLOR);
        
        JLabel resultLabel = createStyledLabel("Result:");
        resultLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        resultArea = new JTextArea(12, 50);
        resultArea.setEditable(false);
        resultArea.setBackground(FIELD_COLOR);
        resultArea.setForeground(TEXT_COLOR);
        resultArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        resultArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 85), 1));
        scrollPane.getViewport().setBackground(FIELD_COLOR);
        
        resultPanel.add(resultLabel, BorderLayout.NORTH);
        resultPanel.add(scrollPane, BorderLayout.CENTER);
        
        return resultPanel;
    }
    
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT_COLOR);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        return label;
    }
    
    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setBackground(FIELD_COLOR);
        field.setForeground(TEXT_COLOR);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 85), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        field.setCaretColor(TEXT_COLOR);
        return field;
    }
    
    private JButton createOperatorButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(BUTTON_COLOR);
        button.setForeground(TEXT_COLOR);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(150, 40));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 85), 1));
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(BUTTON_HOVER);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(BUTTON_COLOR);
            }
        });
        
        return button;
    }

    private ActionListener operationButton(String buttonName){
        ActionListener tmp = new ActionListener() {
            double co1 = 0;
            double co2 = 0;
            double co3 = 0;
            float ig1 = 0f;
            float ig2 = 0f;
        
            public void actionPerformed(ActionEvent e) {

                

                boolean validInput = true;
                  
                if (initGuess1 != null && initGuess2 != null){
                    if (CO1.getText() != null && CO2.getText() != null && CO3.getText() != null){
                        
                        try{
                            co1 = Double.parseDouble(CO1.getText());
                            co2 = Double.parseDouble(CO2.getText());
                            co3 = Double.parseDouble(CO3.getText());
                            ig1 = Float.parseFloat(initGuess1.getText());
                            ig2 = Float.parseFloat(initGuess2.getText());
                        } catch (Exception err){
                            System.err.println(err);
                            resultArea.setText("Input error");
                            validInput = false;
                        }
                        
                        if (validInput){
                            final double[] result = {0};
                            long executionTime = 0;
                            
                            if(buttonName.equals("Bisection")){
                                executionTime = measureTime(() -> {
                                    result[0] = model.Bisection(co1, co2, co3, ig1, ig2);
                                });
                            }
                            
                            if(buttonName.equals("Inverse Quadratic")){
                                executionTime = measureTime(() -> {
                                    result[0] = model.InverseQuadratic(co1, co2, co3, ig1, ig2);
                                });
                            }
                            
                            if(buttonName.equals("Regula Falsi")){
                                executionTime = measureTime(() -> {
                                    result[0] = model.RegulaFalsi(co1, co2, co3, ig1, ig2);
                                });
                            }
                            
                            if(buttonName.equals("Brute Force")){
                                executionTime = measureTime(() -> {
                                    result[0] = model.BruteForce(co1, co2, co3, ig1, ig2);
                                });
                            }
                            
                            double timeInMs = executionTime / 1000000.0;
                            System.out.println(buttonName + " took " + String.format("%.4f", timeInMs) + " ms");
                            
                            resultArea.setText(buttonName + " Method Result:\n" + 
                                            "Root: " + result[0] + "\n" +
                                            "Equation: " + co1 + "x² + " + co2 + "x + " + co3 + " = 0\n" +
                                            "Execution Time: " + String.format("%.4f", timeInMs) + " ms");
                        }
                        
                    }
                    else{
                        System.err.println("Must input coefficients");
                    }
                    
                }
                else {
                    System.err.println("Must input initial guesses/range");
                }
            }
                
        };
        return tmp;
    }

    private JButton createInputButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(BUTTON_COLOR);
        button.setForeground(TEXT_COLOR);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(50, 40));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 85), 1));
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_HOVER);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_COLOR);
            }
        });
        
        return button;
    }

    private ActionListener inputButton(String buttonName){
        ActionListener tmp = new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (buttonName.equals("RandomCoefficientButton")){
                    /*
                    System.out.println("random button clicked!");
                    int randomIndex = testCoefficients.getLastRandomIndex();
                    int[] coefficients = testCoefficients.getCoefficientsAtIndex(randomIndex);
                    float[] guesses = testCoefficients.getInitialGuesses();
                    setCoefficients(coefficients);
                    setInitialGuesses(guesses);
                    System.out.println("Set coefficients: [" + coefficients[0] + ", " + coefficients[1] + ", " + coefficients[2] + "]");
                    System.out.println("Set initial guesses: [" + guesses[0] + ", " + guesses[1] + "]");
                    */
                }

                if (buttonName.equals("RunTestsButton")){
                    resultArea.setText("");
                    long executionTime = 0;

                    executionTime = measureTime(() -> {
                            model.AllTestsBisection();
                        });
                    double timeInMs = executionTime / 1000000.0;
                    System.out.println("Bisection 100 Tests Execution Time:" + String.format("%.4f", timeInMs) + " ms");

                    String currText = resultArea.getText();
                    String newText = currText + "\n" + "Bisection 100 Tests Execution Time: \n" + String.format("%.4f", timeInMs) + " ms";
                    resultArea.setText(newText);

                    executionTime = 0;
                    executionTime = measureTime(() -> {
                            model.AllTestsInverseQuadratic();
                        });
                    timeInMs = executionTime / 1000000.0;
                    System.out.println("Inverse Quadratic 100 Tests Execution Time: " + String.format("%.4f", timeInMs) + " ms");

                    currText = resultArea.getText();
                    newText = currText + "\n" + "Inverse Quadratic 100 Tests Execution Time: \n" + String.format("%.4f", timeInMs) + " ms";
                    resultArea.setText(newText);
                    
                    executionTime = 0;
                    executionTime = measureTime(() -> {
                            model.AllTestsRegulaFalsi();
                        });
                    timeInMs = executionTime / 1000000.0;
                    System.out.println("Regula Falsi 100 Tests Execution Time: " + String.format("%.4f", timeInMs) + " ms");

                    executionTime = 0;
                    currText = resultArea.getText();
                    newText = currText + "\n" + "Regula Falsi 100 Tests Execution Time: \n" + String.format("%.4f", timeInMs) + " ms";
                    resultArea.setText(newText);

                    executionTime = 0;
                    executionTime = measureTime(() -> {
                            model.AllTestsBruteForce();
                        });
                    timeInMs = executionTime / 1000000.0;
                    System.out.println("Brute Force 100 Tests Execution Time: " + String.format("%.4f", timeInMs) + " ms");
                    
                    currText = resultArea.getText();
                    newText = currText + "\n" + "Brute Force 100 Tests Execution Time: \n" + String.format("%.4f", timeInMs) + " ms";
                    resultArea.setText(newText);

                    
                }
            }
        };
        return tmp;
    }

    private void setCoefficients(int[] newCOs){
        CO1.setText( Integer.toString(newCOs[0]) );
        CO2.setText( Integer.toString(newCOs[1]) );
        CO3.setText( Integer.toString(newCOs[2]) );
    }

    private void setInitialGuesses(float[] guesses){
        initGuess1.setText( Float.toString(guesses[0]) );
        initGuess2.setText( Float.toString(guesses[1]) );
    }

    public static long measureTime(Runnable method) {
        long startTime = System.nanoTime();
        method.run();
        return System.nanoTime() - startTime;
    }
}