/*
Joshua T. Price
Oklahoma State University
CS-3513 Numerical Methods for Digital Computers
10-10-2025

A Java Swing application for finding roots of quadratic equations using four numerical methods:
Bisection Method
Inverse Quadratic Interpolation
Halley's Method
Brute Force

How to compile using javac from the command line
>javac NIV.java NIV_Frame.java NIV_Panel.java NIV_Model.java testCoefficients.java
>java NIV

You must compiler all 4 files before running the main function of NIV that 
acts as the controller by calling the init of the JFrame I have created. 

For Proffesor/TA: 
I appligize for how hard this must be to compile when all pasted into a
single text box. However for projects where I utilize the swing library, I feel strongly
that multiple files add the needed encapsulation to the code. Thank you for your time! 
*/

public class NIV {
    public static void main(String args[]){
        new NIV_Frame();
    }
} 