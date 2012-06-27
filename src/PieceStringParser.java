
import java.util.LinkedList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alex
 */
public class PieceStringParser {
    static char parsePiece(int i){
        switch (i){
            case (TetrisPiece.I_PIECE): return 'I';
            case (TetrisPiece.O_PIECE): return 'O';       
            case (TetrisPiece.S_PIECE): return 'S';
            case (TetrisPiece.Z_PIECE): return 'Z'; 
            case (TetrisPiece.T_PIECE): return 'T';
            case (TetrisPiece.J_PIECE): return 'J';
            case (TetrisPiece.L_PIECE): return 'L';
            default: return ' ';                    
        }
    }
    static LinkedList<Integer> parsePieces(String s){
        LinkedList<Integer> result = new LinkedList<Integer>();
        for (int i = 0; i < s.length(); i++){
            char c = s.charAt(i);        
            switch (c) {
                case 'I': 
                    result.add(TetrisPiece.I_PIECE); break;
                case 'O': 
                    result.add(TetrisPiece.O_PIECE); break;             
                case 'S': 
                    result.add(TetrisPiece.S_PIECE);  break;
                case 'Z': 
                    result.add(TetrisPiece.Z_PIECE); break; 
                case 'T': 
                    result.add(TetrisPiece.T_PIECE);  break;
                case 'J': 
                    result.add(TetrisPiece.J_PIECE);  break;
                case 'L': 
                    result.add(TetrisPiece.L_PIECE);  break;

                default:
                    break;
                    
            }
        }
        
        return result;
    }
}
