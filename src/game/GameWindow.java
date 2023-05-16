package game;

import javax.swing.*;

public class GameWindow extends JFrame 
{
    GamePanel GP;
    public GameWindow() 
    {
        GP = new GamePanel();
        this.add(GP);
        this.setTitle("Othello Game");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);  
    }
    
   public static GameWindow GameWindow;
 
    public static void EndWindow() 
    {
        GameWindow.dispose();
        GameWindow = new GameWindow();
    }
    
    public static void main(String[] args) 
    {
        GameWindow = new GameWindow();
    }
}
