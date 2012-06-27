

import java.awt.Point;

/**
 * A generic Tetris piece with no GUI.
 * 
 * @author Alex Ong
 */
public class TetrisPiece
{
    public static final int NO_PIECE = -1;
    public static final int I_PIECE   = 0;
    public static final int O_PIECE   = 1;
    public static final int S_PIECE   = 2;
    public static final int Z_PIECE   = 3;
    public static final int J_PIECE   = 4;
    public static final int L_PIECE   = 5;
    public static final int T_PIECE   = 6;

    public static final int LEFT      = 10;
    public static final int RIGHT     = 11;
    public static final int DOWN      = 13;
    public static final int FALL      = 14;
    
    public static final int FLIP = 0;
    public static final int CCW = 1;
    public static final int NEUTRAL = 2;
    public static final int CW = 3;
    
    

    public static final int LEFT_ALIGNED = 0;
    public static final int RIGHT_ALIGNED = 1;
    public static final int CENTER_ALIGNED = 2;
    
    private int     fType;    
    private int     fAlignment;
    private Point   fCentrePoint = new Point();
    private Point[] fBlocks      = new Point[4];
    private int[]   fBlockFlips = new int[4];

    private TetrisBoard fBoard;


    /**
     * Create a TetrisPiece object.
     * 
     * @param type   The type/shape of the piece.
     * @param board  The board the piece is going to move around in.
     */
    public TetrisPiece(int type, TetrisBoard board, int alignment)
    {
	fType  = type;
        fBoard = board;
        fAlignment = alignment;
    	initializeBlocks();
    }
    
    
    private void moveLeft(){
        move(-1,0);
    }
    
    private void moveRight(){
        move(1,0);
    }
    private void moveDown(){
        move(0,1);
    }
    private void moveUp() {
        move(0,-1);
    }
    private void move(int xdif, int ydif) {
        for (int i = 0 ;i < 4; i++){
            fBlocks[i].x += xdif;
            fBlocks[i].y += ydif;
        }
    }
    /**
     * Move this piece in the given direction.
     * 
     * @param direction The direction of the move.
     *                  This should be of the form:
     *                  TetrisPiece.LEFT           
     *                  TetrisPiece.RIGHT          
     *                  TetrisPiece.ROTATE         
     *                  TetrisPiece.DOWN           
     *                  TetrisPiece.FALL
     * @return true if the move was completed.
     */
    public boolean move(int direction)
    {		      
        boolean result = true;

        if (direction == FALL)
        {
            boolean loop = true;
            
            while (loop)
            {                
                moveDown();
                
                if (!fBoard.willFit(this)) 

                {
                    moveUp();
                    fBoard.addPiece(this);
                    loop   = false;
                    result = false;
                }
            }
        }
        else
        {


            switch (direction)
            {
                case LEFT   : moveLeft();  break; // Move left
                case RIGHT  : moveRight();  break; // Move right
                case DOWN   : moveDown();  break; // Drop
                case CW: SRSRotation.RightRotate(this); break;
                case CCW: SRSRotation.LeftRotate(this); break;
                case FLIP: SRSRotation.FlipRotate(this); break;
                case NEUTRAL: break;
            }
            
            if (!fBoard.willFit(this))            
            {
                switch (direction)
                {
                    case LEFT   : moveRight();      break; // Move right
                    case RIGHT  : moveLeft();      break; // Move left
                    case DOWN   : moveUp();      break; // Undrop
                }

                result = false;
            }
        }

        return result;
    }
    
    /**
     * Returns the centre coordinate of this piece.
     * 
     * @return The centre point.			
     */
    public Point getCentrePoint()
    {
        return fCentrePoint;
    }

    /**
     * Set the Centre point of this piece.
     * 
     */
    public void setCentrePoint(Point point)
    {
        fCentrePoint = point;
    }

    /**
     * Returns an array containing the relative point positions
     * around the centre piece. i.e. (0, -1) for 1 block above.
     * 
     * @return A Point array of relative points.					 
     */
    public Point[] getRelativePoints()
    {
        return fBlocks;
    }

    /**
     * Set the relative centre points.
     * 
     * @param blocks The relative centre points.
     */
    public void setRelativePoints(Point[] blocks)
    {
        if (blocks != null) fBlocks = blocks;
    }

    /**
     * Returns the type of this piece.
     * 
     * @return The type of this piece.
     */
    public int getType()
    {
    	return fType;
    }

    /**
     * Set the type of this piece.
     * 
     * @param type   The type of this piece.
     */
    public void setType(int type)
    {
        fType = type;
        initializeBlocks();
    }
    
    private Point spawnPoint(int x, int y){
        return new Point(fBoard.xSpawn + x, fBoard.ySpawn+y);
    }
    private void initializeBlocks()
    {
        for (int i = 0; i < 4; i++){
            fBlockFlips[i] = 0;
        }
        switch (fType) 
    	{
            
    	    case I_PIECE : fBlocks[0] = spawnPoint(-1, 0);
                           fBlocks[1] = spawnPoint(0, 0);
                           fBlocks[2] = spawnPoint(1, 0);
                           fBlocks[3] = spawnPoint(2, 0);
                           break;
    	    
    	    case L_PIECE : fBlocks[0] = spawnPoint(-1, 1);
                           fBlocks[1] = spawnPoint(0, 1);
                           fBlocks[2] = spawnPoint(1, 0);
                           fBlocks[3] = spawnPoint(1, 1);
         		   break;
        
    	    case J_PIECE : fBlocks[0] = spawnPoint(-1, 0);
                           fBlocks[1] = spawnPoint(0, 1);
                           fBlocks[2] = spawnPoint(-1, 1);
                           fBlocks[3] = spawnPoint(1, 1);
            		   break;
        
    	    case Z_PIECE : fBlocks[0] = spawnPoint(-1, 0);
                           fBlocks[1] = spawnPoint(0, 0);
                           fBlocks[2] = spawnPoint(0, 1);
                           fBlocks[3] = spawnPoint(1, 1);
            		   break;
    	    
    	    case S_PIECE : fBlocks[0] = spawnPoint(-1, 1);
                           fBlocks[1] = spawnPoint(0, 1);
                           fBlocks[2] = spawnPoint(0, 0);
                           fBlocks[3] = spawnPoint(1, 0);
            		   break;
    
    	    case O_PIECE : fBlocks[0] = spawnPoint(0, 0);
                           fBlocks[1] = spawnPoint(0, 1);
                           fBlocks[2] = spawnPoint(1, 0);
                           fBlocks[3] = spawnPoint(1, 1);
            		   break;
    	    
    	    case T_PIECE : fBlocks[0] = spawnPoint(-1, 1);
                           fBlocks[1] = spawnPoint(0, 1);
                           fBlocks[2] = spawnPoint(0, 0);
                           fBlocks[3] = spawnPoint(1, 1);
        		   break;
    	}
    }

    


    public int column(){
        switch(fAlignment){
            case LEFT_ALIGNED: return fBlocks[0].x;
            case CENTER_ALIGNED: return fBlocks[1].x;
            case RIGHT_ALIGNED: return fBlocks[3].x;
            default: return -1;
        }        
    }

}
