package Players;

import java.awt.*;

public class HumanPlayer extends Player {

    public HumanPlayer(int player) {
        super(player);
    }

    @Override
    public boolean isHumanPlayer() {
        return true;
    }

    @Override
    public String getName() {
        return "User-" + ((this.myPlayer == 1) ? "Black" : "White");
    }

    @Override
    public Point play(int[][] board) {
        return null;
    }

}
