package game;

import java.awt.*;
import java.util.ArrayList;

public class Board {

    public static boolean isGameFinished(int[][] board) {
        // if both players have no moves then the game is end
        return !(hasAnyMoves(board, 1) || hasAnyMoves(board, 2));
    }

    public static int getWinner(int[][] board) {
        if (!isGameFinished(board))
            // game not finished
            return -1;
        else {
            // count disks
            int p1s = getPlayerDiskCount(board, 1);
            int p2s = getPlayerDiskCount(board, 2);

            if (p1s == p2s) {
                // draw
                return 0;
            } else if (p1s > p2s) {
                // player 1 wins
                return 1;
            } else {
                // player 2 wins
                return 2;
            }
        }
    }

    public static int getPlayerDiskCount(int[][] board, int player) {
        // get total disks of the player on board
        int count = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == player)
                    count++;
            }
        }
        return count;
    }
    
    public static boolean hasAnyMoves(int[][] board, int player) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (canPlay(board, player, i, j)) {
                    return true;
                }
            }
        }
        return false;
    }
