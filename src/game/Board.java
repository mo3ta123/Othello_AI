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
    
    public static ArrayList<Point> getAllPossibleMoves(int[][] board, int player) {
        ArrayList<Point> result = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (canPlay(board, player, i, j)) {
                    result.add(new Point(i, j));
                }
            }
        }
        return result;
    }
        public static ArrayList<Point> getOppositePoints(int[][] board, int player, int i, int j) {

        ArrayList<Point> allOppositePoints = new ArrayList<>();
        ArrayList<Point> temp_points = new ArrayList<>();
        int idx, jdx;
        int opposite_player = ((player == 1) ? 2 : 1);

        // up

        idx = i - 1;
        jdx = j;
        while (idx > 0 && board[idx][jdx] == opposite_player) {
            temp_points.add(new Point(idx, jdx));
            idx--;
        }
        if (idx >= 0 && board[idx][jdx] == player && temp_points.size() > 0) {
            allOppositePoints.addAll(temp_points);
        }

        // down
        temp_points.clear();
        idx = i + 1;
        jdx = j;
        while (idx < 7 && board[idx][jdx] == opposite_player) {
            temp_points.add(new Point(idx, jdx));
            idx++;
        }
        if (idx <= 7 && board[idx][jdx] == player && temp_points.size() > 0) {
            allOppositePoints.addAll(temp_points);
        }

        // left
        temp_points.clear();
        idx = i;
        jdx = j - 1;
        while (jdx > 0 && board[idx][jdx] == opposite_player) {
            temp_points.add(new Point(idx, jdx));
            jdx--;
        }
        if (jdx >= 0 && board[idx][jdx] == player && temp_points.size() > 0) {
            allOppositePoints.addAll(temp_points);
        }

        // right
        temp_points.clear();
        idx = i;
        jdx = j + 1;
        while (jdx < 7 && board[idx][jdx] == opposite_player) {
            temp_points.add(new Point(idx, jdx));
            jdx++;
        }
        if (jdx <= 7 && board[idx][jdx] == player && temp_points.size() > 0) {
            allOppositePoints.addAll(temp_points);
        }

        // up left
        temp_points.clear();
        idx = i - 1;
        jdx = j - 1;
        while (idx > 0 && jdx > 0 && board[idx][jdx] == opposite_player) {
            temp_points.add(new Point(idx, jdx));
            idx--;
            jdx--;
        }
        if (idx >= 0 && jdx >= 0 && board[idx][jdx] == player && temp_points.size() > 0) {
            allOppositePoints.addAll(temp_points);
        }
        // up right
        temp_points.clear();
        idx = i - 1;
        jdx = j + 1;
        while (idx > 0 && jdx < 7 && board[idx][jdx] == opposite_player) {
            temp_points.add(new Point(idx, jdx));
            idx--;
            jdx++;
        }
        if (idx >= 0 && jdx <= 7 && board[idx][jdx] == player && temp_points.size() > 0) {
            allOppositePoints.addAll(temp_points);
        }

        // down left
        temp_points.clear();
        idx = i + 1;
        jdx = j - 1;
        while (idx < 7 && jdx > 0 && board[idx][jdx] == opposite_player) {
            temp_points.add(new Point(idx, jdx));
            idx++;
            jdx--;
        }
        if (idx <= 7 && jdx >= 0 && board[idx][jdx] == player && temp_points.size() > 0) {
            allOppositePoints.addAll(temp_points);
        }

        // down right
        temp_points.clear();
        idx = i + 1;
        jdx = j + 1;
        while (idx < 7 && jdx < 7 && board[idx][jdx] == opposite_player) {
            temp_points.add(new Point(idx, jdx));
            idx++;
            jdx++;
        }
        if (idx <= 7 && jdx <= 7 && board[idx][jdx] == player && temp_points.size() > 0) {
            allOppositePoints.addAll(temp_points);
        }

        return allOppositePoints;
    }
}
