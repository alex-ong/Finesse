/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alex
 */
public class TetrisCell {
    public int pieceType;
    public int orientation;
    public int orientationRotation;
    public TetrisCell(int type, int orientation, int orientationRotation){
        this.pieceType = type;
        this.orientation = orientation;
        this.orientationRotation = orientationRotation;
    }
}
