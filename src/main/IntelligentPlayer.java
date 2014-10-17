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

    public IntelligentPlayer(String name, char symbol) {
        super(name, symbol);
        firstMoveover = false;
    }

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
