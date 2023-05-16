package Players;

import java.awt.Point;
import java.util.ArrayList;

import game.Board;

public class AiPlayer extends Player {
    private int depth;
    private String diffculty;

    public static final String[] difficulty_options = new String[] { "EASY", "MED", "HIGH" };

    private long millisecLimit;

    private boolean maxDepthReached;

    public AiPlayer(String diffculty, int player, int millisecLimit) {
        super(player);
        this.millisecLimit = millisecLimit;
        switch (diffculty) {
            case "EASY":
                this.depth = 1;
                this.diffculty = "EASY";
                break;
            case "MED":
                this.depth = 4;
                this.diffculty = "MED";
                break;
            case "HIGH":
                this.depth = 6;
                this.diffculty = "HIGH";
                break;
            default:
                this.depth = 4;
                this.diffculty = "MED";
        }
    }

    @Override
    public boolean isHumanPlayer() {
        return false;
    }

    @Override
    public String getName() {
        return "AI-" + diffculty + "-" + ((this.myPlayer == 1) ? "Black" : "White");
    }
    
    @Override
    public Point play(int[][] board) {
        ArrayList<Point> moves = Board.getAllPossibleMoves(board, myPlayer);
        if (diffculty == "HIGH") {
            return MinMax_IterativeDeepening(board, myPlayer, depth, moves);
        } else {
            return MinMax_normal(board, myPlayer, depth, moves);
        }
    }
    
        private Point MinMax_IterativeDeepening(int[][] board, int Player, int depth, ArrayList<Point> moves) {
        // iterative deepening
        long remainTime = millisecLimit;
        long timeTakenPrevDepth = 0;
        // iterative deepening
        // the remaintime must be close to double the time needed for the Prev depth
        // making assumtion that each depth takes double time than it's prev one which
        // is more than that and that it reached the max depth at least in one of it's
        // branched
        Point bestMove = null;
        maxDepthReached = true;
        for (int i = 0; timeTakenPrevDepth * 2 <= remainTime + 100 && maxDepthReached; i++) {
            // System.out.println("time taken" + timeTakenPrevDepth);
            // System.out.println();
            // System.out.println("depth:" + (depth + i));
            maxDepthReached = false;
            // getting time start
            long startTime = System.currentTimeMillis();
            // calling normal minmax with alpha beta pruning
            bestMove = MinMax_normal(board, Player, depth + i, moves);
            // calculating time taken for move and remaining time
            timeTakenPrevDepth = System.currentTimeMillis() - startTime;
            remainTime -= timeTakenPrevDepth;
        }
        return bestMove;
    }
        private Point MinMax_normal(int[][] board, int Player, int depth, ArrayList<Point> moves) {
        int bestScore = Integer.MIN_VALUE;
        Point bestMove = null;
        for (Point move : moves) {
            // create new node
            int[][] newBoard = Board.getNewBoardAfterMove(board, move, myPlayer);
            // recursive call
            int MoveScore = MinMaxAlphaBeta(newBoard, myPlayer, depth - 1, false, Integer.MIN_VALUE,
                    Integer.MAX_VALUE);
            if (MoveScore >= bestScore) {
                bestScore = MoveScore;
                bestMove = move;
            }
        }
        return bestMove;
    }

    private int MinMaxAlphaBeta(int[][] board, int Player, int depth, boolean isMaximizingPlayer, int alpha, int beta) {
        if (Board.isGameFinished(board)) {
            int p = Board.getWinner(board);
            if (p == Player) {
                return Integer.MAX_VALUE;
            } else if (p == 0) {
                return 0;
            } else {
                return Integer.MIN_VALUE;
            }
        }
        // if terminal reached or depth limit reached evaluate
        if (depth == 0) {
            maxDepthReached = true;
            return evaluation(board, Player);
        }

        int otherPlayer = (Player == 1) ? 2 : 1;
        // if no moves available then forfeit turn
        if ((isMaximizingPlayer && !Board.hasAnyMoves(board, Player))
                || (!isMaximizingPlayer && !Board.hasAnyMoves(board, otherPlayer))) {

            return MinMaxAlphaBeta(board, Player, depth - 1, !isMaximizingPlayer, alpha, beta);
        }
        int score;
        if (isMaximizingPlayer) {
            // maximizing
            score = Integer.MIN_VALUE;
            for (Point move : Board.getAllPossibleMoves(board, Player)) { // my turn
                // board after move
                int[][] newBoard = Board.getNewBoardAfterMove(board, move, Player);
                int childScore = MinMaxAlphaBeta(newBoard, Player, depth - 1, false, alpha, beta);
                if (childScore > score)
                    score = childScore;
                // alpha beta pruning
                if (score > alpha)
                    alpha = score;
                if (beta <= alpha)
                    break;
            }
        } else {
            // minimizing
            score = Integer.MAX_VALUE;
            for (Point move : Board.getAllPossibleMoves(board, otherPlayer)) { // opponent turn
                // board after move
                int[][] newBoard = Board.getNewBoardAfterMove(board, move, otherPlayer);
                int childScore = MinMaxAlphaBeta(newBoard, Player, depth - 1, true, alpha, beta);
                if (childScore < score) {
                    score = childScore;
                }

                // alpha beta pruning
                if (score < beta) {
                    beta = score;
                }
                if (beta <= alpha) {
                    break;
                }
            }
        }
        return score;
    }
  private static int evaluation(int[][] board, int player) {
        /**
         * the corner
         * heuristic had a weight of 30, the mobility
         * heuristic had a weight of 5, while the stability
         * heuristic had a weight of 25 and the coin parity
         * heuristic also had a weight of 25.
         */
        return 25 * evaluate_stability(board, player) +
                30 * evaluate_CornersCaptured(board, player) +
                5 * evaluate_Mobility(board, player) +
                25 * evaluate_coinParity(board, player);
    }
    
        private static int evaluate_stability(int[][] board, int player) {
        final int[][] weights = {
                { 4, -3, 2, 2, 2, 2, -3, 4 },
                { -3, -4, -1, -1, -1, -1, -4, -3 },
                { 2, -1, 1, 0, 0, 1, -1, 2 },
                { 2, -1, 0, 1, 1, 0, -1, 2 },
                { 2, -1, 0, 1, 1, 0, -1, 2 },
                { 2, -1, 1, 0, 0, 1, -1, 2 },
                { -3, -4, -1, -1, -1, -1, -4, -3 },
                { 4, -3, 2, 2, 2, 2, -3, 4 }
        };
        /*
         * if((Max Player Stability Value+ Min Player Stability Value) !=0)
         * Stability Heuristic Value =
         * 100* (Max Player Stability Value–Min Player Stability Value)/
         * (Max Player Stability Value+ Min Player Stability Value)
         * else
         * Stability Heuristic Value = 0
         */
        int max_stability = 0;
        int min_stability = 0;
        int other_player = (player == 1) ? 2 : 1;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == player) {
                    max_stability += weights[i][j];
                } else if (board[i][j] == other_player) {
                    min_stability += weights[i][j];
                }
            }
        }
        if (min_stability + max_stability != 0) {
            return 100 * (max_stability - min_stability) / (max_stability + min_stability);
        } else {
            return 0;
        }
    }
}
