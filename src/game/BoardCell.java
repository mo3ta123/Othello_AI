package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;

public class BoardCell extends JLabel implements MouseListener {

    int i;
    int j;

    GamePanel panel;
    JPanel parent;

    public int HighLightLegalMoves = 0;

    public String text = "";
  
      @Override
    public void paint(Graphics g) {

        int margin_left = this.getWidth() / 10;
        int margin_top = this.getHeight() / 10;

        // draw HighLightLegalMoves
        switch (HighLightLegalMoves) {
            case 1:
                g.setColor(new Color(138, 177, 62));
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
                g.setColor(parent.getBackground());
                g.fillRect(4, 4, this.getWidth() - 8, this.getHeight() - 8);
                break;
            case 2:
                g.setColor(new Color(177, 158, 70));
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
                g.setColor(parent.getBackground());
                g.fillRect(4, 4, this.getWidth() - 8, this.getHeight() - 8);
                break;
            case 10:
                g.setColor(new Color(177, 43, 71));
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
                break;
            default:
                break;
        }

        // border
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, this.getWidth(), this.getHeight());

        // piece
        int value = panel.getBoardValue(i, j);
        if (value == 1) 
        {
            g.setColor(Color.BLACK);
            g.fillOval(margin_left, margin_top, this.getWidth() - 2 * margin_left, this.getHeight() - 2 * margin_top);
        }
        else if (value == 2)
        {
            g.setColor(Color.WHITE);
            g.fillOval(margin_left, margin_top, this.getWidth() - 2 * margin_left, this.getHeight() - 2 * margin_top);
        }

        if (!text.isEmpty()) 
        {
            g.setColor(new Color(255, 255, 0));
            Font font = g.getFont();
            Font nfont = new Font(font.getName(), Font.PLAIN, 30);
            g.setFont(nfont);

            drawStringInCenterOfRectangle(g, 0, 0, this.getWidth(), this.getHeight(), text);
        }
        super.paint(g);
    }
}
