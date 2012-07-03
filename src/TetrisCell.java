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
    public int activeKey;
    public TetrisCell(int type, int orientation, int orientationRotation){
        this.pieceType = type;
        this.orientation = orientation;
        this.orientationRotation = orientationRotation;
    }
    public void clear(){
        set(TetrisPiece.NO_PIECE,0,0,-1);
    }
    public void set (int t, int o, int or) {
        set(t,o,or,-1);
    }
    public void set (int t, int o, int or, int aK){
        pieceType = t;
        orientation = o;
        orientationRotation = or;
        activeKey = aK;
    }
}
