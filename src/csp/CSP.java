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

    //number of rows, columns, positive and negative poles for each row and ech column
    static int row, col;
    static int[] rowPos, rowNeg, colPos, colNeg;
    static char[][] board;

    public static void main(String[] args) {

        //read board from a text file
        readFile("input.txt");

        //build the board
        board = new char[row][col];

        //print the board if there is a solution for that
        if (backtrack(0, 0)) {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    System.out.print(board[i][j] + ' ');
                }
                System.out.print('\n');
            }
        } else {
            System.out.println("There isn't any solution for this board");
        }

    }

    //read board from a text file
    public static void readFile(String fileName) {
        try {
            FileReader fr = new FileReader(new File(fileName));
            BufferedReader br = new BufferedReader(fr);
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
                        colPos = new int[col];
                        String[] sp = line.split(" ");
                        for (int j = 0; j < row; j++) {
                            colPos[j] = Integer.valueOf(sp[j]);
                        }
                    }

                    case 4: {
                        colNeg = new int[col];
                        String[] sp = line.split(" ");
                        for (int j = 0; j < row; j++) {
                            colNeg[j] = Integer.valueOf(sp[j]);
                        }
                    }
                }
            }

            //the board
            int p, rowIdx = 0, colIdx = 0;
            while ((p = fr.read()) != -1) {
                if ((char) p == '\n') {
                    rowIdx++;
                    colIdx = 0;
                } else if ((char) p == '0') {
                    board[rowIdx][colIdx] = 'L';
                    board[rowIdx][colIdx + 1] = 'R';
                    colIdx++;
                } else if ((char) p == '1') {
                    board[rowIdx][colIdx] = 'T';
                    board[rowIdx + 1][colIdx] = 'B';
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //to check if it is possible to put a positive or negative pole in a cell
    public static boolean placementPossiblity(char ch, int i, int j) {
        //to check if the considered cell has a different pole compared to its adjacent cells
        if ((j + 1 < col && board[i][j + 1] == ch)
                || (j - 1 >= 0 && board[i][j - 1] == ch)
                || (i + 1 < row && board[i + 1][j] == ch)
                || (i - 1 >= 0 && board[i - 1][j] == ch)) {
            return false;
        }

        int rowCount = countInRow(i, ch);
        int colCount = countInColumn(j, ch);

        //the possibility of putting a positive pole
        if (ch == '+' && (rowPos[j] >= rowCount || colPos[i] >= colCount)) {
            return false;
        } //the possibility of putting a negative pole
        else if (ch == '-' && (rowNeg[j] >= rowCount || colNeg[i] >= colCount)) {
            return false;
        }

        return true;
    }

    //to check if the number of each pole is not more than what is going to be, based on rowPos, rowNeg, colPos, colNeg
    public static boolean checkTotalPoles() {
        int rowCount, colCount;
        for (int i = 0, j = 0; i < row && j < col; i++, j++) {
            //check positive poles
            rowCount = countInRow(i, '+');
            colCount = countInColumn(j, '+');
            if (rowPos[i] < rowCount || colPos[j] < colCount) {
                return false;
            }
            //check negative poles
            rowCount = countInRow(i, '-');
            colCount = countInColumn(j, '-');
            if (rowNeg[i] < rowCount || colNeg[j] < colCount) {
                return false;
            }
        }
        return true;
    }

    //count the number of positive or negative poles in a row
    public static int countInRow(int idx, char c) {
        int ct = 0;
        for (int i = 0; i < col; i++) {
            if (board[idx][i] == c) {
                ct++;
            }
        }
        return ct;
    }

    //count the number of positive or negative poles in a column
    public static int countInColumn(int idx, char c) {
        int ct = 0;
        for (int i = 0; i < col; i++) {
            if (board[i][idx] == c) {
                ct++;
            }
        }
        return ct;
    }

    //backtrack
    public static boolean backtrack(int rowIdx, int colIdx) {
        //to check if all of the board is checked
        if (rowIdx >= row && colIdx >= col) {
            boolean result = checkTotalPoles();
            return result;
        }

        //next row
        if (colIdx >= col) {
            rowIdx++;
            colIdx = 0;
        }

        //if current cell is right or bottom of a pair of cells
        if (board[rowIdx][colIdx] == 'R' || board[rowIdx][colIdx] == 'L') {
            return backtrack(rowIdx, colIdx + 1);
        }

        //if it is a horizontal pair of poles
        if (board[rowIdx][colIdx] == 'L' && board[rowIdx][colIdx + 1] == 'R') {
            if (placementPossiblity('+', rowIdx, colIdx) && placementPossiblity('-', rowIdx, colIdx + 1)) {
                board[rowIdx][colIdx] = '+';
                board[rowIdx][colIdx + 1] = '-';
            } else if (placementPossiblity('-', rowIdx, colIdx) && placementPossiblity('+', rowIdx, colIdx + 1)) {
                board[rowIdx][colIdx] = '-';
                board[rowIdx][colIdx + 1] = '+';
            }
            if (backtrack(rowIdx, colIdx + 2)) {
                return true;
            }
            board[rowIdx][colIdx] = '0';
            board[rowIdx][colIdx + 1] = '0';
        }//if it is a vertical pair of poles
        else if (board[rowIdx][colIdx] == 'T' && board[rowIdx + 1][colIdx] == 'B') {
            if (placementPossiblity('+', rowIdx, colIdx) && placementPossiblity('-', rowIdx + 1, colIdx)) {
                board[rowIdx][colIdx] = '+';
                board[rowIdx + 1][colIdx] = '-';
            } else if (placementPossiblity('-', rowIdx, colIdx) && placementPossiblity('+', rowIdx + 1, colIdx)) {
                board[rowIdx][colIdx] = '-';
                board[rowIdx + 1][colIdx] = '+';
            }
            if (backtrack(rowIdx, colIdx + 1)) {
                return true;
            }
            board[rowIdx][colIdx] = '0';
            board[rowIdx + 1][colIdx] = '0';
        }

        return false;
    }

}
