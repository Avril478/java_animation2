/*
 *	==========================================================================================
 *	RunAssignment2.java : Started  A2 program via a class implementing Runnable & using
 *  invokeLater()
 *  upi: ydia530
 *  Name: Diao Yuan
 *	==========================================================================================
 */

import javax.swing.SwingUtilities;

public class RunAssignment2 implements Runnable{

    public void run(){
        Assignment2 myApp= new Assignment2("A2"); //Run the application

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new RunAssignment2()); //main
    }
}