package minesweeper.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

public class UiClone
{
    public static void main( String[] args )
    {
        (new Frame(new CloneRenderer())).newGame();
    }
}

class CloneRenderer implements TileRenderer
{
    private static final ImageIcon tile_initial = new ImageIcon(CloneRenderer.class.getResource("/initial.png"));
    private static final ImageIcon tile_exposed = new ImageIcon(CloneRenderer.class.getResource("/exposed.png"));
    private static final ImageIcon tile_sealed = new ImageIcon(CloneRenderer.class.getResource("/sealed.png"));
    private static final ImageIcon tile_adjacent1 = new ImageIcon(CloneRenderer.class.getResource("/adjacent1.png"));
    private static final ImageIcon tile_adjacent2 = new ImageIcon(CloneRenderer.class.getResource("/adjacent2.png"));
    private static final ImageIcon tile_adjacent3 = new ImageIcon(CloneRenderer.class.getResource("/adjacent3.png"));
    private static final ImageIcon tile_adjacent4 = new ImageIcon(CloneRenderer.class.getResource("/adjacent4.png"));
    private static final ImageIcon tile_adjacent5 = new ImageIcon(CloneRenderer.class.getResource("/adjacent5.png"));
    private static final ImageIcon tile_adjacent6 = new ImageIcon(CloneRenderer.class.getResource("/adjacent6.png"));
    private static final ImageIcon tile_adjacent7 = new ImageIcon(CloneRenderer.class.getResource("/adjacent7.png"));
    private static final ImageIcon tile_adjacent8 = new ImageIcon(CloneRenderer.class.getResource("/adjacent8.png"));
    private static final ImageIcon tile_mine_exposed = new ImageIcon(CloneRenderer.class.getResource("/mine_exposed.png"));
    private static final ImageIcon tile_mine_hidden = new ImageIcon(CloneRenderer.class.getResource("/mine_hidden.png"));
    
    private String cellText = "";
    
    private MouseListener defaultMouseListener;
    
    
    @Override
    public void initialize( JButton cell )
    {
        defaultMouseListener = cell.getMouseListeners()[0];
        
        cell.setForeground(new Color(100, 100, 100));
        cell.setFont(new Font("Arial", Font.PLAIN, 30));
        cell.setContentAreaFilled(false);
        cell.setBorderPainted(false);
        cell.setFocusPainted(false);
        cell.setMargin(new Insets(0, 0, 0, 0));
        
        cell.setIcon(tile_initial);
        cell.setPressedIcon(tile_exposed);
    }
    
    @Override
    public void setDisabled( JButton cell )
    {
        cell.removeMouseListener(defaultMouseListener);
    }
    
    @Override
    public void showExposed( JButton cell )
    {
        cell.setIcon(tile_exposed);
        cell.repaint();
    }
    
    @Override
    public void showExposedWithAdjacentMineCount( JButton cell, int mineCount )
    {
        switch ( mineCount )
        {
            case 1:
                cell.setIcon(tile_adjacent1);
                cell.setForeground(new Color(0, 0, 255));
                break;
            case 2:
                cell.setIcon(tile_adjacent2);
                cell.setForeground(new Color(0, 128, 0));
                break;
            case 3:
                cell.setIcon(tile_adjacent3);
                cell.setForeground(new Color(255, 0, 0));
                break;
            case 4:
                cell.setIcon(tile_adjacent4);
                cell.setForeground(new Color(0, 0, 128));
                break;
            case 5:
                cell.setIcon(tile_adjacent5);
                cell.setForeground(new Color(128, 0, 0));
                break;
            case 6:
                cell.setIcon(tile_adjacent6);
                cell.setForeground(new Color(0, 128, 128));
                break;
            case 7:
                cell.setIcon(tile_adjacent7);
                cell.setForeground(new Color(0, 0, 0));
                break;
            case 8:
                cell.setIcon(tile_adjacent8);
                cell.setForeground(new Color(128, 128, 128));
                break;
        }
    }
    
    @Override
    public void showInitial( JButton cell )
    {
        cell.setIcon(tile_initial);
        cell.setPressedIcon(tile_exposed);
        cell.repaint();
    }
    
    @Override
    public void showHiddenMine( JButton cell )
    {
        cell.setIcon(tile_mine_hidden);
        cell.repaint();
    }
    
    @Override
    public void showSealed( JButton cell )
    {
        cell.setIcon(tile_sealed);
        cell.setPressedIcon(tile_sealed);
        cell.repaint();
    }
    
    @Override
    public void showExposedMine( JButton cell )
    {
        cell.setIcon(tile_mine_exposed);
        cell.repaint();
    }
    
    @Override
    public void paint( JButton cell, Graphics g )
    {
        FontMetrics metrics = g.getFontMetrics(cell.getFont());
        int x = (cell.getWidth() - metrics.stringWidth(cellText)) / 2;
        int y = ((cell.getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
        g.setFont(cell.getFont());
        g.drawString(cellText, x, y);
    }
}