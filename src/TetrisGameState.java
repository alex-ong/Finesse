
import java.util.LinkedList;

/*
 * 
 * This class enables TIME REVERSAL (lol).
 * 
 * 
 */
public class TetrisGameState {
    
    public TetrisBoard  fBoard;
    public TetrisPiece  fCurrPiece;
    public TetrisPiece  fHoldPiece = null;      
    public int          fTotalLines;    
    public int fPieces;
    public int[] pieceStats = new int[7];    
    
    public TetrisGameState(TetrisGame old) {
        fBoard = new TetrisBoard(old.getTetrisBoard());
        fCurrPiece = new TetrisPiece(old.getCurrentPiece());
        fHoldPiece = (old.getHoldPiece() == null) ? null : 
                     new TetrisPiece(old.getHoldPiece());        
        fTotalLines = old.getTotalLines();
        fPieces = old.getPieces();
        for (int i = 0; i < 7; i++){
            pieceStats[i] = old.getPieceStats()[i];
        }
        
    }
    
}
