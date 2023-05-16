package Players;

import java.awt.*;

public abstract class Player {

    protected int myPlayer;

    public Player(int player) {
        myPlayer = player;
    }

    abstract public boolean isHumanPlayer();

    abstract public String getName();

    abstract public Point play(int[][] board);

}
