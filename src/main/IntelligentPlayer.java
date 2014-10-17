/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import ch.hslu.ai.connect4.Player;

/**
 *
 * @author Alessandro
 */
public class IntelligentPlayer extends Player {

    private boolean firstMoveover;
    int columns;
    int rows;
    private static final char EMTPY = '-';

    public IntelligentPlayer(String name, char symbol) {
        super(name, symbol);
        firstMoveover = false;
    }

    private int hasHowManyInColumn(char[][] board, char playerSymbol) {
        int maxFound = 0;
        for (int i = 0; i < board.length; i++) {
            int counter = 0;
            int counterPossibles = 0;
            for (int j = 0; j < board[0].length && counter < 4; j++) {
                if (board[i][j] == playerSymbol) {
                    if (counter == 0) {
                        for (int k = j - 1; k >= 0; k--) {
                            if (board[i][k] == EMTPY || board[i][k] == playerSymbol) {
                                counterPossibles++;
                            } else {
                                break;
                            }
                        }
                    }

                    counter++;
                } else {
                    if (counter != 0) {
                        for (int k = j + 1; k < board[0].length; k++) {
                            if (board[i][k] == EMTPY || board[i][k] == playerSymbol) {
                                counterPossibles++;
                            } else {
                                break;
                            }
                        }
                        if (maxFound < counter) {
                            if (counter + counterPossibles >= 4) {
                                maxFound = counter;
                            }
                        }
                    }
                    counter = 0;
                }
            }

        }
        return maxFound;
    }

    /**
     * @return true if the current player has 4 consecutive discs in one row,
     * false otherwise
     */
    /*private int hasHowManyInRow(char[][] board, char playerSymbol) {
     int maxFound = 0;
     for (int i = 0; i < board[0].length; i++) {
     int counter = 0;
     for (int j = 0; j < board.length && counter < 4; j++) {
     if (board[j][i] == playerSymbol) {
     counter++;
     } else {
     counter = 0;
     }
     }
     if (maxFound < counter) {
     maxFound = counter;
     }
     }
     return maxFound;
     }
     */
    /**
     * @return true if the current player has 4 consecutive discs in one
     * diagonal, false otherwise
     */
    /* private boolean hasHowManyInDiagonal(char[][] board, char playerSymbol) {

     // Left-to-right diagonal:
     for (int i = 0; i <= board.length - 4; i++) {
     for (int j = 0; j <= board[0].length - 4; j++) {
     char[] cells = new char[]{board[i][j], board[i + 1][j + 1],
     board[i + 2][j + 2], board[i + 3][j + 3]};
     if (equal(cells, playerSymbol)) {
     return true;
     }
     }
     }

     // Right-to-left diagonal:
     for (int i = board.length - 1; i >= 3; i--) {
     for (int j = 0; j <= board[0].length - 4; j++) {
     char[] cells = new char[]{board[i][j], board[i - 1][j + 1],
     board[i - 2][j + 2], board[i - 3][j + 3]};
     if (equal(cells, playerSymbol)) {
     return true;
     }
     }
     }

     return false;
     }

     private boolean equal(char[] array, char symbol) {
     for (int i = 0; i < array.length; i++) {
     if (array[i] != symbol) {
     return false;
     }
     }
     return true;
     }*/
    /**
     * The following method allows you to implement your own game intelligence.
     * The method must return the column number where the computer player puts
     * the next disc. board[i][j] = cell content at position (i,j), i = column,
     * j = row
     *
     * If board[i][j] = this.getSymbol(), the cell contains one of your discs If
     * board[i][j] = '-', the cell is empty Otherwise, the cell contains one of
     * your opponent's discs
     *
     * @param board The current game board
     * @return The columns number where you want to put your disc
     */
    @Override
    public int play(char[][] board) {
        int column = -1;
        if (!firstMoveover && checkIfFirstMove(board)) {
            firstMoveover = true;
            return ((board.length - 1) / 2);
        }

        do {
            column = (int) (Math.random() * board.length);
        } while (board[column][0] != '-');

        return column;
    }

    public boolean checkIfFirstMove(char[][] board) {
        for (int i = 0; i < board.length - 1; i++) {
            if (board[i][board[0].length - 1] != '-') {
                return false;
            }
        }
        return true;
    }

}
