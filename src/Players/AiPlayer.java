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
}
