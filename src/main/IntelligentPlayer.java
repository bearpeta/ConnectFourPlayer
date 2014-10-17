
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
    private char otherPlayer = '-';

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

        List<BoardState> boards = GetPossibleMovesFirst(cloneBoard(board), getSymbol());
        int maxColumn = 0;
        int maxVal = 0;
        for (BoardState bs : boards) {
            int sumOfAll = 0;
            for (char[][] subBoard : bs.Boards) {
                if (hasWon(subBoard, getOtherSymbol(subBoard, getSymbol()))) {
                    sumOfAll -= 15;
                }
                if (hasWon(subBoard, getSymbol())) {
                    sumOfAll += 50;
                }
                sumOfAll += hasHowManyInColumn(subBoard, getSymbol());
                sumOfAll -= hasHowManyInColumn(subBoard, getOtherSymbol(board, getSymbol()));
            }
            if (sumOfAll > maxVal) {
                maxColumn = bs.Column;
                maxVal = sumOfAll;
            }
        }
        return maxColumn;
        /*
         do {
         column = (int) (Math.random() * board.length);
         } while (board[column][0] != '-');

         return column;*/
    }

    public boolean checkIfFirstMove(char[][] board) {
        for (int i = 0; i < board.length - 1; i++) {
            if (board[i][board[0].length - 1] != EMPTY) {
                return false;
            }
        }
        return true;
    }

    public boolean hasWon(char[][] board, char playerSymbol) {
        return hasFourInColumn(board, playerSymbol) || hasFourInRow(board, playerSymbol) || hasFourInDiagonal(board, playerSymbol);
    }

    private boolean hasFourInColumn(char[][] board, char playerSymbol) {
        for (int i = 0; i < board.length; i++) {
            int counter = 0;
            for (int j = 0; j < board[i].length && counter < 4; j++) {
                if (board[i][j] == playerSymbol) {
                    counter++;
                } else {
                    counter = 0;
                }
            }
            if (counter == 4) {
                return true;
            }
        }
        return false;
    }

    private boolean hasFourInRow(char[][] board, char playerSymbol) {
        for (int i = 0; i < board[0].length; i++) {
            int counter = 0;
            for (int j = 0; j < board.length && counter < 4; j++) {
                if (board[j][i] == playerSymbol) {
                    counter++;
                } else {
                    counter = 0;
                }
            }
            if (counter == 4) {
                return true;
            }
        }
        return false;
    }

    private boolean hasFourInDiagonal(char[][] board, char playerSymbol) {

        // Left-to-right diagonal:
        for (int i = 0; i <= board.length - 4; i++) {
            for (int j = 0; j <= board[i].length - 4; j++) {
                char[] cells = new char[]{board[i][j], board[i + 1][j + 1],
                    board[i + 2][j + 2], board[i + 3][j + 3]};
                if (equal(cells, playerSymbol)) {
                    return true;
                }
            }
        }

        // Right-to-left diagonal:
        for (int i = board.length - 1; i >= 3; i--) {
            for (int j = 0; j <= board[i].length - 4; j++) {
                char[] cells = new char[]{board[i][j], board[i - 1][j + 1],
                    board[i - 2][j + 2], board[i - 3][j + 3]};
                if (equal(cells, playerSymbol)) {
                    return true;
                }
            }
        }

        return false;
    }

    private int hasHowManyInColumn(char[][] board, char playerSymbol) {
        int maxFound = 0;
        for (int i = 0; i < board.length; i++) {
            int counter = 0;
            int counterPossibles = 0;
            for (int j = board[0].length - 1; j >= 0; j--) {
                if (counter == 0) {

                    for (int k = j - 1; k >= 0; k--) {
                        if (board[i][k] == EMPTY || board[i][k] == playerSymbol) {
                            counterPossibles++;
                        } else {
                            break;
                        }
                    }
                }

                if (board[i][j] == playerSymbol) {
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
                    }
                    if (maxFound < counter + counterPossibles) {
                        if (counter + counterPossibles >= 4) {
                            maxFound = counter + counterPossibles;
                        }
                    }
                    counterPossibles = 0;
                    counter = 0;
                }
            }

        }
        return maxFound;
    }

    public List<BoardState> GetPossibleMovesFirst(char[][] board, char symbol) {
        List<BoardState> possibleBoards = new ArrayList<>();

        for (int i = 0; i < board.length; i++) {
            char[] column = board[i];
            for (int r = column.length - 1; r >= 0; r--) {
                if (column[r] == EMPTY) {
                    column[r] = symbol;

                    BoardState bs = new BoardState();
                    bs.Column = i;
                    bs.Boards.addAll(this.GetPossibleMoves(cloneBoard(board), SEARCH_DEPTH, getOtherSymbol(board, symbol)));
                    possibleBoards.add(bs);
                    break;
                }
            }
        }
        return possibleBoards;
    }

    private List<char[][]> GetPossibleMoves(char[][] board, int depth, char symbol) {
        List<char[][]> possibleBoards = new ArrayList<>();

        if (depth >= 0) {
            for (int i = 0; i < board.length; i++) {
                char[] column = board[i];
                for (int r = column.length - 1; r >= 0; r--) {
                    if (column[r] == EMPTY) {
                        column[r] = symbol;
                        List<char[][]> res = this.GetPossibleMoves(cloneBoard(board), depth - 1, getOtherSymbol(board, symbol));
                        possibleBoards.addAll(res);
                        break;
                    }

                }
            }
        } else {
            possibleBoards.add(board);
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
        if (otherPlayer == '-') {
            for (char[] column : board) {
                for (int r = column.length - 1; r >= 0; r--) {
                    if (column[r] != firstSymbol
                            && column[r] != EMPTY) {
                        otherPlayer = column[r];
                        return otherPlayer;
                    }
                }
            }
            // Fallback
            return firstSymbol == 'x' ? 'o' : 'x';
        } else {
            return otherPlayer == firstSymbol ? getSymbol() : otherPlayer;
        }
    }

    private boolean equal(char[] array, char symbol) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] != symbol) {
                return false;
            }
        }
        return true;
    }

}
