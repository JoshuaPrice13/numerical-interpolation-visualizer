/*
Joshua T. Price
Oklahoma State University
CS-3513 Numerical Methods for Digital Computers
11-07-2025

Entry point for the NIV program.

How to compile using javac from the command line
>javac -cp ".;../lib/*" *.java
>java -cp ".;../lib/*" NIV

You must compiler all 4 files before running the main function of NIV that 
acts as the controller by calling the init of the JFrame I have created. 
*/

public class NIV {
    public static void main(String args[]){
        new NIV_Frame();
    }
} 