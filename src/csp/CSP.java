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

/**
 *
 * @author Shakiba
 */
public class CSP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //number of rows, columns, positive and negative poles for each row and ech column
        int row, col;
        int[] rowPos, rowNeg, colPos, colNeg;
        // read input file
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("input.txt")));
            String line = br.readLine();
            String[] split = line.split(" ");
            row = Integer.valueOf(split[0]);
            col = Integer.valueOf(split[1]);
            for (int i = 1; i <= 4; i++) {
                line = br.readLine();
                switch (i) {
                    case 1: {
                        rowPos = new int[row];
                        String[] sp = line.split(" ");
                        for (int j = 0; j < row; j++) {
                            rowPos[j] = Integer.valueOf(sp[j]);
                        }
                    }
                    case 2: {
                        rowNeg = new int[row];
                        String[] sp = line.split(" ");
                        for (int j = 0; j < row; j++) {
                            rowNeg[j] = Integer.valueOf(sp[j]);
                        }
                    }

                    case 3: {
                        colPos = new int[row];
                        String[] sp = line.split(" ");
                        for (int j = 0; j < row; j++) {
                            colPos[j] = Integer.valueOf(sp[j]);
                        }
                    }

                    case 4: {
                        colNeg = new int[row];
                        String[] sp = line.split(" ");
                        for (int j = 0; j < row; j++) {
                            colNeg[j] = Integer.valueOf(sp[j]);
                        }
                    }
                }
            }

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
