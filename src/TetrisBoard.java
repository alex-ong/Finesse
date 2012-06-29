

import java.util.*;
import java.awt.*;

/**
 * A generic Tetris board with no GUI.
 * 
 * @author Scott Clee
 */
public class TetrisBoard
{
    /**
     * The value of an empty block.
     */
    public static final int EMPTY_BLOCK = -1;

    private TetrisCell[][] fMatrix;
    private int     fColumns;
    private int     fRows;
    private int     fInvisRows;
    
    public static final int xSpawn = 4;
    public static final int ySpawn = 2;
    /**
     * Create a TetrisBoard with the desired number
     * of columns and rows.
     * 
     * @param cols   The number of columns.
     * @param rows   The number of rows.
     */
    public TetrisBoard(int cols, int rows, int invisRows)
    {
        fColumns = cols;
        fRows    = rows;
        fInvisRows = invisRows;
        resetBoard();
    }

    /**
     * Sets all entries in the board matrix to the
     * empty piece value.
     */
    public void resetBoard()
    {
        fMatrix  = new TetrisCell[fColumns][fRows];
       	
        for (int cols = 0; cols < fColumns; cols++) 
    	    for (int rows = 0; rows < fRows; rows++) 
    	    	fMatrix[cols][rows] = new TetrisCell(EMPTY_BLOCK, 0, 0);
    }

    /**
     * Returns the number of columns in the board.
     * 
     * @return The number of columns in the board.
     */
    public int getColumns()
    {
    	return fColumns;
    }

    /**
     * Set the number of columns in the board.  The board
     * will be reset after this is done.
     * 
     * @param columns The number of desired columns.
     */
    public void setColumns(int columns)
    {
        fColumns = columns;
        resetBoard();
    }

    /**
     * Returns the number of rows in the board.
     * 
     * @return The number of rows in the board.
     */
    public int getRows()
    {
    	return fRows;
    }
    public int getInvisRows()
    {
        return fInvisRows;
    }
    /**
     * Set the number of rows in the board.  The board
     * will be reset after this is done.
     * 
     * @param rowss The number of desired rows.
     */
    public void setRows(int rows)
    {
        fRows = rows;
        resetBoard();
    }   

    /**
     * Returns the value of the block at the given 
     * coordinates.
     * 
     * @param x      The x coordinate.
     * @param y      The y coordinate.
     * @return The value at the given coordinates.
     */
    public TetrisCell getPieceAt(int x, int y)
    {
    	return fMatrix[x][y];
    }

    /**
     * Sets the piece at the given coordinates to the
     * given value.
     * 
     * @param x      The x coordinate.
     * @param y      The y coordinate.
     * @param value  The value to be set.
     */
    public void setPieceAt(int x, int y, TetrisCell value)
    {
        fMatrix[x][y] = value;
    }

    /**
     * Add a piece to the board.
     * 
     * The notify parameter is there to supress events in
     * such cases as when performing a fall which will involve
     * multiple add/removes.
     * 
     * @param piece  The piece to add.
     * @param notify If true then fire a BoardEvent once the piece
     *               is added.
     */
    public void addPiece(TetrisPiece piece)
    {
        if (piece != null)
        {            
            final Point[] blocks = piece.getRelativePoints();
            final int[] sprite = piece.getfBlockSprite();
            final int[] blockRotate = piece.getfBlockRotate();
        	for (int count = 0; count < 4; count++) 
        	{
        	    int x = blocks[count].x;
        	    int y = blocks[count].y;
        
        	    fMatrix[x][y] = new TetrisCell(piece.getType(), sprite[count],
                                                    blockRotate[count]);
        	}
                    
        }
    }

    /**
     * Remove the piece from the board.
     * 
     * @param piece  The piece to remove.
     */
    public void removePiece(TetrisPiece piece)
    {
        if (piece != null)	
        {
            final Point   centre = piece.getCentrePoint();
            final Point[] blocks = piece.getRelativePoints();

            for (int count = 0; count < 4; count++) 
        	{
        	    int x = centre.x + blocks[count].x;
        	    int y = centre.y + blocks[count].y;
            
        	    fMatrix[x][y] = new TetrisCell(EMPTY_BLOCK, 0, 0);
        	}
        }
    }

    /**
     * Removes the row at the given row index.  Rows
     * which are above the given one are then moved
     * downwards one place.
     * 
     * A BoardEvent will be fired after the rows are
     * moved.
     * 
     * @param row    The index of the row to remove.
     */
    public void removeRow(int row)
    {
    	for (int tempRow = row; tempRow > 0; tempRow--) 
    	{
    	    for (int tempCol = 0; tempCol < fColumns; tempCol++) 
    	    {
    		    fMatrix[tempCol][tempRow] = fMatrix[tempCol][tempRow - 1];
    	    }
    	}
    
    	for (int tempCol = 0; tempCol < fColumns; tempCol++) 
    	{
    	    fMatrix[tempCol][0] = new TetrisCell(EMPTY_BLOCK, 0, 0);
    	}
    
    }

    /**
     * Tests to see if the given piece will fit in this
     * board.
     * 
     * @param piece  The piece to test against.
     * @return true if the piece fits else false.
     */
    public boolean willFit(TetrisPiece piece)
    {
        if (piece != null)
        {

            final Point[] blocks = piece.getRelativePoints();
    
        	for (int count = 0; count < 4; count++) 
        	{
        	    int x = blocks[count].x;
        	    int y = blocks[count].y;
        	    
                // Ensure it's within the boundaries
        	    if (x < 0 || x >= fColumns || y < 0 || y >= fRows)
        		    return false;
        	     
                if (fMatrix[x][y].pieceType != EMPTY_BLOCK) return false;
        	}
        }

    	return true;
    }

    

    public boolean canSpawn() {
        for (int x = xSpawn; x < xSpawn+4; x++) {
            for (int y = ySpawn; y < ySpawn+4; y++){
                if (fMatrix[x][y].pieceType != EMPTY_BLOCK) return false;
            }
        }
        return true;
    }
}
