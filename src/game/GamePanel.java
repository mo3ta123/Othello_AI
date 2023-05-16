package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import Players.*;

public class GamePanel extends JPanel {

    // To Select between Black and White
    int color;

    // Score for each player
    int FirstPlayerScore = 0;
    int SecondPlayerScore = 0;

    // it determines the mode that user wants to play and if it is AI vs AI it will
    // call another Function just to determine diffculty of each AAI
    int AIType;

    // board
    int[][] board;

    // black plays first = 1
    int turn = 1;

    BoardCell[][] cells;

    JLabel TurnToPlay;

    JLabel FirstScore;
    JLabel SecondScore;

    int FirstPlayerTotalWins = 0;
    int SecondPlayerTotalWins = 0;

    JLabel TotalWinsForFirstPlayer;
    JLabel TotalWinsForSecondPlayer;

    Player FirstPlayer;
    Player SecondPlayer;

    Timer FirstPlayerHandlerTimer;
    Timer SecondPlayerHandlerTimer;

    JOptionPane PopUpMessage = new JOptionPane();
  
}
