package Controlador;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.*;
import javax.swing.*;

public class extraerPdf {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Drag and Drop Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create a JTextArea component
        JTextArea textArea = new JTextArea();
        textArea.setDragEnabled(true);
        textArea.setDropMode(DropMode.INSERT);
        
        FileTransferHandler trans = new FileTransferHandler();
//        trans.setComponent(textArea);
        textArea.setTransferHandler(trans);
        
        // Add the JTextArea component to the JFrame
        frame.getContentPane().add(textArea, BorderLayout.CENTER);
        
        // Display the JFrame
        frame.setSize(400, 300);
        frame.setVisible(true);
    }
}


