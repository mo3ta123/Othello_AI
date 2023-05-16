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
    int Turn = 1;

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
  private boolean HoldUntilClick = false;

    public void TurnManager() {
        if (Board.hasAnyMoves(board, 1) || Board.hasAnyMoves(board, 2)) {
            UpdateScore();
            if (Turn == 1) {
                if (Board.hasAnyMoves(board, 1)) {
                    if (FirstPlayer.isHumanPlayer()) {
                        HoldUntilClick = true;
                    } else {
                        FirstPlayerHandlerTimer.start();
                    }
                } else {
                    // pass the Turn
                    Turn = 2;
                    TurnManager();
                }
            } else {
                if (Board.hasAnyMoves(board, 2)) {
                    if (SecondPlayer.isHumanPlayer()) {
                        HoldUntilClick = true;
                    } else {
                        SecondPlayerHandlerTimer.start();
                    }
                } else {
                    // pass the Turn
                    Turn = 1;
                    TurnManager();
                }
            }
        } else {
            // game end
            int winner = Board.getWinner(board);
            if (winner == 1) {
                FirstPlayerTotalWins++;
            } else if (winner == 2) {
                SecondPlayerTotalWins++;
            }
            UpdateTotalWins();
            if (FirstPlayerScore > SecondPlayerScore) {
                PopUpMessage.showMessageDialog(null,
                        "WINNER IS : " + FirstPlayer.getName(),
                        "WINNER",
                        JOptionPane.PLAIN_MESSAGE);
            } else {
                PopUpMessage.showMessageDialog(null,
                        "WINNER IS : " + SecondPlayer.getName(),
                        "WINNER",
                        JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

    public void BoardInitialization() {
        board = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = 0;
            }
        }
        // initial states for the board
        setBoardValue(3, 3, 2);
        setBoardValue(3, 4, 1);
        setBoardValue(4, 3, 1);
        setBoardValue(4, 4, 2);
    }

    // update score for each round
    public void UpdateScore() {
        FirstPlayerScore = 0;
        SecondPlayerScore = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == 1) {
                    FirstPlayerScore++;
                }
                if (board[i][j] == 2) {
                    SecondPlayerScore++;
                }
                if (Board.canPlay(board, Turn, i, j)) {
                    cells[i][j].HighLightLegalMoves = 1;
                } else {
                    cells[i][j].HighLightLegalMoves = 0;
                }
            }
        }
        FirstScore.setText(FirstPlayer.getName() + " : " + FirstPlayerScore);
        SecondScore.setText(SecondPlayer.getName() + " : " + SecondPlayerScore);
        TurnToPlay.setText(((Turn == 1) ? FirstPlayer.getName() + "'s turn" : SecondPlayer.getName() + "'s turn"));
    }

    public void UpdateTotalWins() {
        TotalWinsForFirstPlayer.setText("Total Wins For " + FirstPlayer.getName() + " : " + FirstPlayerTotalWins);
        TotalWinsForSecondPlayer.setText("Total Wins For " + SecondPlayer.getName() + " : " + SecondPlayerTotalWins);
    }

    public void ClickHandler(int i, int j) {
        if (HoldUntilClick && Board.canPlay(board, Turn, i, j)) {
            // update board
            board = Board.getNewBoardAfterMove(board, new Point(i, j), Turn);

            // next Turn
            Turn = (Turn == 1) ? 2 : 1;

            repaint();

            HoldUntilClick = false;

            TurnManager();
        }
    }
    
    // Choose Game mode between those Three options
    public void chooseGameType() {
        String[] options = new String[] { "Human VS Human", "Human vs AI", "AI vs AI" };
        String message = "Select the game mode you would like to use.";
        AIType = JOptionPane.showOptionDialog(null, message,
                "Choose how to play",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        switch (AIType) {
            case 0:
                FirstPlayer = new HumanPlayer(1);
                SecondPlayer = new HumanPlayer(2);
                break;
            case 1:
                color = ChooseColorToPlay();
                if (color == 1) {
                    FirstPlayer = new HumanPlayer(color);
                } else {
                    SecondPlayer = new HumanPlayer(color);
                }
                chooseDifficultyMode();
                break;
            case 2:
                chooseDifficultyMode();
                break;
        }
    }
        // Choose Game difficulty mode between those Three options
    public void chooseDifficultyMode() {
        String[] options = new String[] { "Easy", "Medium", "Hard" };
        String message = "Select the game difficulty mode For AI.";
        int difficultyChoice = JOptionPane.showOptionDialog(null, message,
                "Choose difficulty Mode",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        switch (difficultyChoice) {
            case 0:
                if (AIType == 2) {
                    FirstPlayer = new AiPlayer("EASY", 1, 1000);
                    chooseDifficultyModeForOtherAI();
                } else {
                    if (color == 1) {
                        SecondPlayer = new AiPlayer("EASY", ((color == 1) ? 2 : 1), 1000);
                    } else {
                        FirstPlayer = new AiPlayer("EASY", ((color == 1) ? 2 : 1), 1000);
                    }
                }
                break;
            case 1:
                if (AIType == 2) {
                    FirstPlayer = new AiPlayer("MED", 1, 1000);
                    chooseDifficultyModeForOtherAI();
                } else {
                    if (color == 1) {
                        SecondPlayer = new AiPlayer("MED", ((color == 1) ? 2 : 1), 1000);
                    } else {
                        FirstPlayer = new AiPlayer("MED", ((color == 1) ? 2 : 1), 1000);
                    }
                }
                break;
            case 2:
                if (AIType == 2) {
                    FirstPlayer = new AiPlayer("HIGH", 1, 1000);
                    chooseDifficultyModeForOtherAI();
                } else {
                    if (color == 1) {
                        SecondPlayer = new AiPlayer("HIGH", ((color == 1) ? 2 : 1), 1000);
                    } else {
                        FirstPlayer = new AiPlayer("HIGH", ((color == 1) ? 2 : 1), 1000);
                    }
                }
                break;
        }
    }

    // Choose Game difficulty mode between those Three options for the Second AI if
    // it was Choosen AI vs AI
    public void chooseDifficultyModeForOtherAI() {
        String[] options = new String[] { "Easy", "Medium", "Hard" };
        String message = "Select the game difficulty mode for the other AI.";
        int difficultyChoice = JOptionPane.showOptionDialog(null, message,
                "Choose difficulty Mode",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        switch (difficultyChoice) {
            case 0:
                SecondPlayer = new AiPlayer("EASY", 2, 1000);
                break;
            case 1:
                SecondPlayer = new AiPlayer("MED", 2, 1000);
                break;
            case 2:
                SecondPlayer = new AiPlayer("HIGH", 2, 1000);
                break;
        }
    }
        public int ChooseColorToPlay() {
        String[] options = new String[] { "Black", "White" };
        String message = "Select the Color you want to play with.";
        int ColorChoice = JOptionPane.showOptionDialog(null, message,
                "Choose Color",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        if (ColorChoice == 0) {
            color = 1;
        } else {
            color = 2;
        }
        return color;
    }

    public void AIHandler(Player ai) {
        Point aiPlayPoint = ai.play(board);
        // update board
        board = Board.getNewBoardAfterMove(board, aiPlayPoint, Turn);
        // switch Turn
        if (Turn == 1) {
            Turn = 2;
        } else {
            Turn = 1;
        }
        repaint();
    }
}
