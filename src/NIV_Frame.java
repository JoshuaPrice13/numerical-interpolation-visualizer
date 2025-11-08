import javax.swing.JFrame;
import javax.swing.JPanel;

public class NIV_Frame extends JFrame{
    //Contrutor
    public NIV_Frame (){
        setTitle("Root Finding Methods Visualizer");
        setSize(800,700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        GraphPanel graphPanel = new GraphPanel();
        add(graphPanel);
        
        setVisible(true);
    }
}