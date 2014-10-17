
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import ch.hslu.ai.connect4.Player;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alessandro
 */
public class IntelligentPlayer extends Player {

    private boolean firstMoveover;
    private static final int SEARCH_DEPTH = 3;
    private static final char EMPTY = '-';

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

        List<char[][]> boards = GetPossibleMoves(board, SEARCH_DEPTH, getSymbol());

        do {
            column = (int) (Math.random() * board.length);
        } while (board[column][0] != '-');

        return column;
    }

    public boolean checkIfFirstMove(char[][] board) {
        for (int i = 0; i < board.length - 1; i++) {
            if (board[i][board[0].length - 1] != EMPTY) {
                return false;
            }
        }
        return true;
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
                            if (board[i][k] == EMPTY || board[i][k] == playerSymbol) {
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
                            if (board[i][k] == EMPTY || board[i][k] == playerSymbol) {
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

    private List<char[][]> GetPossibleMoves(char[][] board, int depth, char symbol) {
        ArrayList<char[][]> possibleBoards = new ArrayList<char[][]>();

        if (depth != 0) {
            for (char[] column : board) {
                for (int r = column.length - 1; r >= 0; r++) {
                    if (column[r] == EMPTY) {
                        column[r] = symbol;
                    }
                    possibleBoards.addAll(this.GetPossibleMoves(cloneBoard(board), depth - 1, getOtherSymbol(board, symbol)));
                }
            }
        }

        return possibleBoards;
    }

    private char[][] cloneBoard(char[][] board) {
        char[][] newBoard = board.clone();
        for (int i = 0; i < board.length; i++) {
            newBoard[i] = board[i].clone();
        }
        return newBoard;
    }

    private char getOtherSymbol(char[][] board, char firstSymbol) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != firstSymbol
                        && board[i][j] != EMPTY) {
                    return board[i][j];
                }
            }
        }
        // Fallback
        return firstSymbol == 'x' ? 'o' : 'x';
    }

}

