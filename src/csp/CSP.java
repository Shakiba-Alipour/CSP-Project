/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Shakiba
 */
public class CSP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //number of rows, columns, positive and negative poles for rows and columns
        int row, col, rowPos, rowNeg, colPos, colNeg;
        // read input file
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("input.txt")));
            String line;
            int lineNumber = 1;
            while (lineNumber <= 5) {
                line = br.readLine();
                switch (lineNumber) {
                    case 1: {
                        String[] split = line.split(" ");
                        row = Integer.valueOf(split[0]);
                        col = Integer.valueOf(split[1]);
                    }
                    case 2:
                        rowPos = Integer.valueOf(line);
                    case 3:
                        rowNeg = Integer.valueOf(line);
                    case 4:
                        colPos = Integer.valueOf(line);
                    case 5:
                        colNeg = Integer.valueOf(line);
                }
                lineNumber++;
            }
            
            
            
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
