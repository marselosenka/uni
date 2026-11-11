package A13;

import java.util.Random;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;
import java.text.Normalizer;

public class Sudoku {


    public static void main(String[] args) {
        int[][] board = {
                {8, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 3, 6, 0, 0, 0, 0, 0},
                {0, 7, 0, 0, 9, 0, 2, 0, 0},
                {0, 5, 0, 0, 0, 7, 0, 0, 0},
                {0, 0, 0, 0, 4, 5, 7, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 3, 0},
                {0, 0, 1, 0, 0, 0, 0, 6, 8},
                {0, 0, 8, 5, 0, 0, 0, 1, 0},
                {0, 9, 0, 0, 0, 0, 4, 0, 0}
        };
        SudokuValidator SV = new SudokuValidator();
        SudokuGenerator SG = new SudokuGenerator();

        // Question (a)
        //System.out.println("Execution of itsValidBoard(): "+ SV.isValidBoard(board));

        // Question (b)
        //System.out.println("Execution of isSolvableBoard(board): "+SV.isSolvableBoard(board));

        // Question (c)
        //System.out.println("Generating a board with 75 empty cells: ");
        //int[][] temp = SG.generateBoard(75);
        //SG.printBoard(temp);

        // Question (d)
        System.out.println("Generating 10 Puzzles with 75 empty cells");
        SG.generateValidBoards(10,65);


    }
}

class SudokuValidator {

    private static final int BOARD_SIZE = 9;
    private static final int SUBSECTION_SIZE = 3;
    private static final int BOARD_START_INDEX = 0;
    private static final int NO_VALUE = 0;
    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 9;
    private static final int[][] board = {
            {8, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 3, 6, 0, 0, 0, 0, 0},
            {0, 7, 0, 0, 9, 0, 2, 0, 0},
            {0, 5, 0, 0, 0, 7, 0, 0, 0},
            {0, 0, 0, 0, 4, 5, 7, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 3, 0},
            {0, 0, 1, 0, 0, 0, 0, 6, 8},
            {0, 0, 8, 5, 0, 0, 0, 1, 0},
            {0, 9, 0, 0, 0, 0, 4, 0, 0}
    };


    // (α) Method to check if the given board is valid according to Sudoku rules


    private static boolean columnConstraint(int[][] board, int column) {
        boolean[] constraint = new boolean[BOARD_SIZE];
        for (int row = BOARD_START_INDEX; row < BOARD_SIZE; row++) {
            if (!checkConstraint(board, row, constraint, column)) {
                return false;
            }
        }
        return true;
    }

    private static boolean rowConstraint(int[][] board, int row) {
        boolean[] constraint = new boolean[BOARD_SIZE];
        for (int column = BOARD_START_INDEX; column < BOARD_SIZE; column++) {
            if (!checkConstraint(board, row, constraint, column)) {
                return false;
            }
        }
        return true;
    }

    private static boolean checkConstraint(int[][] board, int row, boolean[] constraint, int column) {
        if (board[row][column] != NO_VALUE) {
            if (!constraint[board[row][column] - 1]) {
                constraint[board[row][column] - 1] = true;
            } else {
                return false;
            }
        }
        return true;
    }
    private static boolean isValid(int[][] board, int row, int column) {
        return rowConstraint(board, row) &&
                columnConstraint(board, column) &&
                subsectionConstraint(board, row, column);
    }
    private static boolean subsectionConstraint(int[][] board, int row, int column) {
        boolean[] constraint = new boolean[BOARD_SIZE];
        int subsectionRowStart = (row / SUBSECTION_SIZE) * SUBSECTION_SIZE;
        int subsectionRowEnd = subsectionRowStart + SUBSECTION_SIZE;

        int subsectionColumnStart = (column / SUBSECTION_SIZE) * SUBSECTION_SIZE;
        int subsectionColumnEnd = subsectionColumnStart + SUBSECTION_SIZE;

        for (int r = subsectionRowStart; r < subsectionRowEnd; r++) {
            for (int c = subsectionColumnStart; c < subsectionColumnEnd; c++) {
                if (!checkConstraint(board, r, constraint, c)) return false;
            }
        }
        return true;
    }


    /**
     *
     * @param board to be checked whether is valid or not, checks row,columns and 3X3.
     * @return a boolean
     */
    public static boolean isValidBoard(int[][] board) {
        boolean cols = true,rows = true,subs=true;
        for(int i=0;i<BOARD_SIZE;i++) {
            if (columnConstraint(board,i)) {
            } else {
                cols = false;
            }

            if (rowConstraint(board,i)) {
            } else {
                rows = false;
            }
        }
        for(int i =0;i <MAX_VALUE; i++) {
            for(int j=0; j < MAX_VALUE; j++) {
                if (subsectionConstraint(board,i,j)) {

                } else {
                    subs = false;
                }
            }
        }
        if(cols && rows && subs) {
            return true;
        } else {
            return false;
        }

    }public static boolean isValidBoard(int[][] board) {
        boolean cols = true,rows = true,subs=true;
        for(int i=0;i<BOARD_SIZE;i++) {
            if (columnConstraint(board,i)) {
            } else {
                cols = false;
            }

            if (rowConstraint(board,i)) {
            } else {
                rows = false;
            }
        }
        for(int i =0;i <MAX_VALUE; i++) {
            for(int j=0; j < MAX_VALUE; j++) {
                if (subsectionConstraint(board,i,j)) {

                } else {
                    subs = false;
                }
            }
        }
        if(cols && rows && subs) {
            return true;
        } else {
            return false;
        }

    }

    /**
     *
     * @param board solves board and returns it.
     * @return
     */
    public static int[][] solve(int[][] board) {
        for (int row = BOARD_START_INDEX; row < BOARD_SIZE; row++) {
            for (int column = BOARD_START_INDEX; column < BOARD_SIZE; column++) {
                if (board[row][column] == NO_VALUE) {
                    for (int k = MIN_VALUE; k <= MAX_VALUE; k++) {
                        board[row][column] = k;
                        if (isValid(board, row, column)) {
                            if(solve(board) != null) {
                                return board;
                            }

                        }
                        board[row][column] = NO_VALUE;
                    }
                    return null;
                }
            }
        }
        return board;
    }

    // (β) Method to check if the board is solvable using the backtracking algorithm
    public static boolean isSolvableBoard(int[][] board) {
        if (isValidBoard(board) && solvebool(board) ) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param board Board to check whether can be solved.
     * @return a boolean
     */
    public static boolean solvebool(int[][] board) {
        for (int row = BOARD_START_INDEX; row < BOARD_SIZE; row++) {
            for (int column = BOARD_START_INDEX; column < BOARD_SIZE; column++) {
                if (board[row][column] == NO_VALUE) {
                    for (int k = MIN_VALUE; k <= MAX_VALUE; k++) {
                        board[row][column] = k;
                        if (isValid(board, row, column) && solvebool(board)) {
                            return true;
                        }
                        board[row][column] = NO_VALUE;
                    }
                    return false;
                }
            }
        }
        return true;
    }
}


class SudokuGenerator {
    /**
     *
     * @param emptyCells Number of empty cells on the board..
     * @return A 2D array.
     */
    public static int[][] generateBoard(int emptyCells) {
        int[][] A = new int[9][9];

        int non_empty = 81 - emptyCells;
        Random r = new Random();
        int i = 0;
        while(i < non_empty) {
            int x = r.nextInt(9);
            int y = r.nextInt(9);
            if(A[x][y] == 0) {
                A[x][y] = r.nextInt(9-1+1) + 1;
                i++;
            }
        }
        return A;
    }

    /**
     *
     * @param board board to be printed.
     */
    public static void printBoard(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                System.out.print(board[row][column] + " ");
            }
            System.out.println();
        }
    }

    /**
     *  Generates N empty boards.
     * @param N number of boards
     * @param emptyCells empty cells per board
     */
    public static void generateValidBoards(int N, int emptyCells) {
        SudokuValidator SV = new SudokuValidator();
        int i = 0;
        int invalid = 0;
        int valid = 0;
        int unsolvable = 0;
        long start = System.nanoTime();
        while (i<N) {
            int[][] A = generateBoard(emptyCells);
            if (SV.isValidBoard(A)) {
                System.out.println("Board #" + valid);
                printBoard(A);
                A = SV.solve(A);
                if(A != null) {
                    System.out.println("Solution of board #" +valid);
                    valid += 1;
                    printBoard(A);
                    System.out.println();
                    i++;
                } else {
                    System.out.println("No solution for that board!");
                    unsolvable++;
                }
            } else {
                invalid++;
            }
        }
        long end = System.nanoTime();
        System.out.println("Empty cells per board : "+emptyCells);
        System.out.println("Valid boards created : "+valid);
        System.out.println("Invalid boards created : "+invalid);
        System.out.println("Unsolvable boards created: "+unsolvable);
        System.out.println("Elapsed time in nano-seconds : "+(end-start));
    }
}
