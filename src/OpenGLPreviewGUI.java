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
public class OpenGLPreviewGUI 
{
    private int piece = 0;
    private boolean[][] bitarray = new boolean[2][4];
    private int startX;
    private int startY;
    private TextureAtlas minos = null;
    private int minoWidth = 20;
    private boolean monochrome = false;
    public OpenGLPreviewGUI(int startX, int startY){
        this.startX = startX;
        this.startY = startY;
    }
    public void setTextureAtlas(TextureAtlas at){
        minos = at;
    }
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
      if (piece == TetrisPiece.NO_PIECE) {
          boolean[][] tmp = {{false, false, false, false},{false,false,false,false}};
          copyGrid(tmp);
      } else if (piece == TetrisPiece.I_PIECE) {
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
    
    
    public void render()
    { 

        final int numCols = 4;
        final int numRows = 2;

        for (int rows = 0; rows < numRows; rows++) 
        {
            for (int cols = 0; cols < numCols; cols++)
            {
                final boolean bit = bitarray[rows][cols];
                if (bit != false) {  
                    if (monochrome) {
                        renderBlock(cols * minoWidth + startX, rows*minoWidth + startY, 7);
                    } else {
                        renderBlock(cols * minoWidth + startX, rows*minoWidth + startY, piece);
                   }
                }
            }

        }

    }    

    
    private void renderBlock(int x, int y, int piece) {
        minos.drawSprite(x,y,piece);

    }    
     
    public Dimension getPreferredSize()
    {
        return new Dimension(96, 48);
    }
    
    
    public void setMonochrome(boolean b){
        this.monochrome = b;
    }

    public boolean getMonochrome() {
        return monochrome;
    }
    
}
