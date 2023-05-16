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
}
