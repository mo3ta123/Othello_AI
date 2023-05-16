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
    
        public int getBoardValue(int i, int j) {
        return board[i][j];
    }

    public void setBoardValue(int i, int j, int value) {
        board[i][j] = value;
    }

    public GamePanel() {
        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());

        JPanel OthelloBoard = new JPanel();
        OthelloBoard.setLayout(new GridLayout(8, 8));
        OthelloBoard.setPreferredSize(new Dimension(650, 650));
        OthelloBoard.setBackground(new Color(0, 51, 0));

        // init board
        BoardInitialization();

        cells = new BoardCell[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                cells[i][j] = new BoardCell(this, OthelloBoard, i, j);
                OthelloBoard.add(cells[i][j]);
            }
        }

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.PAGE_AXIS));
        sidebar.setPreferredSize(new Dimension(200, 0));

        FirstScore = new JLabel("Score 1");
        SecondScore = new JLabel("Score 2");

        TurnToPlay = new JLabel("your turn");

        TotalWinsForFirstPlayer = new JLabel("Total Score 1");
        TotalWinsForSecondPlayer = new JLabel("Total Score 2");

        sidebar.add(FirstScore);
        sidebar.add(TotalWinsForFirstPlayer);
        sidebar.add(new JLabel("------------------------------------------------"));
        sidebar.add(SecondScore);
        sidebar.add(TotalWinsForSecondPlayer);
        sidebar.add(new JLabel("------------------------------------------------"));
        sidebar.add(TurnToPlay);

        // To playagain and reset board
        JButton PlayAgainButton = new JButton("Play Again");
        PlayAgainButton.setLayout(new BoxLayout(PlayAgainButton, BoxLayout.LINE_AXIS));
        PlayAgainButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        PlayAgainButton.add(Box.createRigidArea(new Dimension(200, 50)));
        PlayAgainButton.addActionListener((ActionEvent e) -> {
            FirstPlayerHandlerTimer.stop();
            SecondPlayerHandlerTimer.stop();
            BoardInitialization();
            repaint();
            Turn = 1;
            TurnManager();
        });

        // to start a new game from start and choose which mode to play
        JButton EndButton = new JButton("End");
        EndButton.setLayout(new BoxLayout(EndButton, BoxLayout.LINE_AXIS));
        EndButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        EndButton.add(Box.createRigidArea(new Dimension(200, 50)));
        EndButton.addActionListener((ActionEvent e) -> {
            FirstPlayerHandlerTimer.stop();
            SecondPlayerHandlerTimer.stop();
            GameWindow.EndWindow();
        });

        // it closes application
        JButton ExitButton = new JButton("Exit");
        ExitButton.setLayout(new BoxLayout(ExitButton, BoxLayout.LINE_AXIS));
        ExitButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        ExitButton.add(Box.createRigidArea(new Dimension(200, 50)));
        ExitButton.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });

        // to make buttons in GUI Place where i want
        sidebar.add(new JLabel("\n"));
        sidebar.add(new JLabel("\n"));
        sidebar.add(new JLabel("\n"));
        sidebar.add(new JLabel("\n"));
        sidebar.add(new JLabel("\n"));
        sidebar.add(new JLabel("\n"));
        sidebar.add(new JLabel("\n"));
        sidebar.add(new JLabel("\n"));
        sidebar.add(new JLabel("\n"));
        sidebar.add(new JLabel("\n"));
        sidebar.add(new JLabel("\n"));
        sidebar.add(new JLabel("\n"));
        sidebar.add(new JLabel("\n"));
        sidebar.add(new JLabel("\n"));
        sidebar.add(new JLabel("\n"));
        sidebar.add(new JLabel("\n"));
        sidebar.add(new JLabel("\n"));
        sidebar.add(new JLabel("\n"));
        sidebar.add(new JLabel("\n"));
        sidebar.add(new JLabel("\n"));
        sidebar.add(new JLabel("\n"));

        sidebar.add(PlayAgainButton);
        sidebar.add(EndButton);
        sidebar.add(ExitButton);

        this.add(sidebar, BorderLayout.EAST);
        this.add(OthelloBoard);

        chooseGameType();

        UpdateScore();
        UpdateTotalWins();

        // To unfreeze UI
        FirstPlayerHandlerTimer = new Timer(1000, (ActionEvent e) -> {
            AIHandler(FirstPlayer);
            FirstPlayerHandlerTimer.stop();
            TurnManager();
        });

        SecondPlayerHandlerTimer = new Timer(1000, (ActionEvent e) -> {
            AIHandler(SecondPlayer);
            SecondPlayerHandlerTimer.stop();
            TurnManager();
        });
        TurnManager();
    }
  
}
