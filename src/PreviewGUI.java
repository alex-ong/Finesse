/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

/**
 *
 * @author Alex
 */
public class PreviewGUI extends JPanel
{
    private int piece = 0;
    private boolean[][] bitarray = new boolean[2][4];
    
    
    public void setPiece(int pieceNo) {
        piece = pieceNo;
        setGrid();
    }
    public int getPiece() {
        return piece;
    }
    
    public void copyGrid(boolean[][] toCopy){
        for (int i = 0; i < 2; i++) {
            System.arraycopy(toCopy[i], 0, bitarray[i], 0, 4);
        }
    }
    
    private void setGrid() {
      if (piece == TetrisPiece.I_PIECE) {
          boolean[][] tmp = {{false, false, false, false},{true, true, true, true}};
          copyGrid(tmp);
      } else if (piece == TetrisPiece.J_PIECE) {
          boolean[][] tmp = {{true, false, false, false},{true, true, true, false}};
          copyGrid(tmp);          
      } else if (piece == TetrisPiece.L_PIECE) {
          boolean[][] tmp = {{false, false, true, false},{true, true, true, false}};
          copyGrid(tmp);          
      } else if (piece == TetrisPiece.T_PIECE) {
          boolean[][] tmp = {{false, true, false, false},{true, true, true, false}};
          copyGrid(tmp);          
      } else if (piece == TetrisPiece.S_PIECE) {
          boolean[][] tmp = {{false, true, true, false},{true, true, false, false}};
          copyGrid(tmp);          
      } else if (piece == TetrisPiece.Z_PIECE) {
          boolean[][] tmp = {{true, true, false, false},{false, true, true, false}};
          copyGrid(tmp);          
      } else if (piece == TetrisPiece.O_PIECE) {
          boolean[][] tmp = {{false, true, true, false},{false, true, true, false}};
          copyGrid(tmp);          
      }
      
        
    }
    
    @Override
    public void paintComponent(Graphics graphics)
    {
            final int width   = getBounds().width;
            final int height  = getBounds().height;	

            // Set up double buffering.
            final Image    image = createImage(width, height);
        final Graphics g     = image.getGraphics();           

        final int numCols = 4;
        final int numRows = 2;

        for (int rows = 0; rows < numRows; rows++) 
        {
            for (int cols = 0; cols < numCols; cols++)
            {
                final boolean bit = bitarray[rows][cols];
                if (bit == false) {
                    g.setColor(Color.BLACK);   
                }
                else g.setColor(getPieceColor(piece));                
                drawBlock(g, (cols * width / numCols), 
                            (rows * height / numRows), 
                            (width / numCols) - 1, 
                            (height / numRows) - 1);
            }

        }
       g.setColor(Color.blue);
       g.drawRect(0, 0, width - 1, height - 1);
       graphics.drawImage(image, 0, 0, width, height, null);
    }    

    
    private void drawBlock(Graphics g, int x, int y, int width, int height)
    {
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
    }       
     
    public Dimension getPreferredSize()
    {
        return new Dimension(96, 48);
    }

    private Color getPieceColor(int color)
    {
            Color result = null;

            switch (color) 
            {
            case TetrisPiece.L_PIECE : result = Color.ORANGE;
                                        break;
            case TetrisPiece.J_PIECE : result = Color.BLUE;
                                        break;
            case TetrisPiece.I_PIECE : result = Color.CYAN;
                                        break;
            case TetrisPiece.Z_PIECE : result = Color.RED;
                                        break;
            case TetrisPiece.S_PIECE : result = Color.GREEN;
                                        break;
            case TetrisPiece.O_PIECE : result = Color.YELLOW;
                                        break;
            case TetrisPiece.T_PIECE : result = Color.MAGENTA;
                                        break;
            }

            return result;
    }
}
