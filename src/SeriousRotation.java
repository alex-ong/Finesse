
import java.awt.Point;

/*
 * Rotation tables for Serious rotation. (SRSly? :P)
 */

/**
 *
 * @author Alex
 */
public class SeriousRotation {
    //some notes.. block 1 = "centre"
    //block 0 = left
    //block 1 = centre
    //block 3 = right
    public static void RightRotate(TetrisPiece p){
        switch (p.getType()){
            case TetrisPiece.I_PIECE: RightRotateI(p); break;            
            case TetrisPiece.J_PIECE: RightRotateJ(p); break;                
            case TetrisPiece.T_PIECE: RightRotateT(p); break;
            case TetrisPiece.L_PIECE: RightRotateL(p); break;
            case TetrisPiece.S_PIECE: RightRotateS(p); break;
            case TetrisPiece.Z_PIECE: RightRotateZ(p); break;
            default: break;
        }
    }
       
    
    public static void LeftRotate(TetrisPiece p){
        switch (p.getType()){
            case TetrisPiece.I_PIECE: RightRotateI(p); break;            
            case TetrisPiece.J_PIECE: LeftRotateJ(p); break;                
            case TetrisPiece.T_PIECE: LeftRotateT(p); break;
            case TetrisPiece.L_PIECE: LeftRotateL(p); break;
            case TetrisPiece.S_PIECE: LeftRotateS(p); break;
            case TetrisPiece.Z_PIECE: LeftRotateZ(p); break;
            default: break;
        }
    }
    
    public static void FlipRotate(TetrisPiece p){
        switch (p.getType()){
            case TetrisPiece.J_PIECE: FlipRotateJ(p); break;                
            case TetrisPiece.T_PIECE: FlipRotateT(p); break;
            case TetrisPiece.L_PIECE: FlipRotateL(p); break;
        }
    }
    
    /*
     *  XXXX X0XX
     *  XXXX X1XX
     *  0123 X2XX
     *  XXXX X3XX
     */
    
    private static void RightRotateI(TetrisPiece p) {
       Point[] points =  p.getRelativePoints();
       points[0] = new Point(points[0].x+1, points[0].y-2);
       points[1].y -= 1;
       points[2].x -= 1;
       points[3] = new Point(points[2].x,points[2].y+1);
       int[] sprites = p.getfBlockSprite();
       sprites[0] = 0;
       sprites[1] = 3;          
       sprites[2] = 3;
       sprites[3] = 0;
       int[] rotates = p.getfBlockRotate();
       rotates[0] = 0;
       rotates[1] = 0;
       rotates[2] = 0;
       rotates[3] = 2;
    }
    
    /*
     * 01X XX3
     * X23 X12 
     * XXX X0X
     * 
     */
    private static void RightRotateZ(TetrisPiece p) {
       Point[] points =  p.getRelativePoints();
       points[0] = new Point(points[0].x+1, points[0].y+2);
       points[1].y+=1;
       points[2].x+=1;
       points[3].y-=1;
       int[] sprites = p.getfBlockSprite();
       sprites[0] = 0;
       sprites[1] = 1;          
       sprites[2] = 1;
       sprites[3] = 0;
       int[] rotates = p.getfBlockRotate();
       rotates[0] = 2;
       rotates[1] = 0;
       rotates[2] = 2;
       rotates[3] = 0;
    }
    
    /*
     * X23 X0X
     * 01X X12
     * XXX XX3
     * 
     */
    private static void RightRotateS(TetrisPiece p) {
       Point[] points = p.getRelativePoints();
       points[0] = new Point(points[0].x+1, points[0].y-1);
       points[2] = new Point(points[2].x+1, points[2].y+1);
       points[3].y+=2;  
       int[] sprites = p.getfBlockSprite();
       sprites[0] = 0;
       sprites[1] = 1;          
       sprites[2] = 1;
       sprites[3] = 0;
       int[] rotates = p.getfBlockRotate();
       rotates[0] = 0;
       rotates[1] = 3;
       rotates[2] = 1;
       rotates[3] = 2;        
    }

    /*
     * 0XX X03
     * 213 X1X
     * XXX X2X
     * 
     */
    private static void RightRotateJ(TetrisPiece p) {
       Point[] points =  p.getRelativePoints();
       points[0].x += 1;
       points[2] = new Point(points[2].x + 1, points[2].y+1);
       points[3].y -=1;
       int[] sprites = p.getfBlockSprite();
       sprites[0] = 1;
       sprites[1] = 3;          
       sprites[2] = 0;
       sprites[3] = 0;
       int[] rotates = p.getfBlockRotate();
       rotates[0] = 0;
       rotates[1] = 0;
       rotates[2] = 2;
       rotates[3] = 1;
       
    }

    /*
     * X2X X0X
     * 013 X13
     * XXX X2X   
     */
    private static void RightRotateT(TetrisPiece p) {
       Point[] points =  p.getRelativePoints();
       points[0] = new Point(points[0].x+1, points[0].y-1);
       points[2].y+=2;   
       int[] sprites = p.getfBlockSprite();
       sprites[0] = 0;
       sprites[1] = 2;          
       sprites[2] = 0;
       sprites[3] = 0;
       int[] rotates = p.getfBlockRotate();
       rotates[0] = 0;
       rotates[1] = 3;
       rotates[2] = 2;
       rotates[3] = 1;
    }
    
    /*
     * XX2 X0X
     * 013 X1X
     * XXX X23
     */
    private static void RightRotateL(TetrisPiece p) {
       Point[] points =  p.getRelativePoints();
       points[0] = new Point(points[0].x+1, points[0].y-1);
       points[2] = new Point(points[1].x,points[1].y+1);
       points[3].y += 1;
       int[] sprites = p.getfBlockSprite();
       sprites[0] = 0;
       sprites[1] = 3;          
       sprites[2] = 1;
       sprites[3] = 0;
       int[] rotates = p.getfBlockRotate();
       rotates[0] = 0;
       rotates[1] = 0;
       rotates[2] = 3;
       rotates[3] = 1;
    }

     /*
     * 0XX X3X
     * 213 X1X
     * XXX 02X     
     */
    private static void LeftRotateJ(TetrisPiece p) {
       Point[] points =  p.getRelativePoints();
       points[0].y+=2;
       points[2] = new Point(points[1].x,points[1].y+1);
       points[3] = new Point(points[1].x,points[1].y-1);
       int[] sprites = p.getfBlockSprite();
       sprites[0] = 0;
       sprites[1] = 3;          
       sprites[2] = 1;
       sprites[3] = 0;
       int[] rotates = p.getfBlockRotate();
       rotates[0] = 3;
       rotates[1] = 0;
       rotates[2] = 2;
       rotates[3] = 0;
    }
    
    /*
     * X2X X2X
     * 013 01X
     * XXX X3X   
     */
    private static void LeftRotateT(TetrisPiece p) {
       Point[] points =  p.getRelativePoints();              
       points[3] = new Point(points[1].x,points[1].y+1); 
       int[] sprites = p.getfBlockSprite();
       sprites[0] = 0;
       sprites[1] = 2;          
       sprites[2] = 0;
       sprites[3] = 0;
       int[] rotates = p.getfBlockRotate();
       rotates[0] = 3;
       rotates[1] = 1;
       rotates[2] = 0;
       rotates[3] = 2;
    }

    
    /*
     * XX2 03X
     * 013 X1X
     * XXX X2X
     */
    private static void LeftRotateL(TetrisPiece p) {
       Point[] points =  p.getRelativePoints();              
       points[0].y -= 1;
       points[2] = new Point(points[1].x, points[1].y+1);
       points[3] = new Point(points[1].x, points[1].y-1);
       int[] sprites = p.getfBlockSprite();
       sprites[0] = 0;
       sprites[1] = 3;          
       sprites[2] = 0;
       sprites[3] = 1;
       int[] rotates = p.getfBlockRotate();
       rotates[0] = 3;
       rotates[1] = 0;
       rotates[2] = 2;
       rotates[3] = 1;
    }

    /*
     * X23 0XX
     * 01X 21X
     * XXX X3X
     * 
     */
    private static void LeftRotateS(TetrisPiece p) {
       Point[] points =  p.getRelativePoints();              
       points[0].y -= 1;
       points[2] = new Point(points[1].x-1, points[1].y);
       points[3] = new Point(points[1].x, points[1].y+1);
       int[] sprites = p.getfBlockSprite();
       sprites[0] = 0;
       sprites[1] = 1;          
       sprites[2] = 1;
       sprites[3] = 0;
       int[] rotates = p.getfBlockRotate();
       rotates[0] = 0;
       rotates[1] = 1;
       rotates[2] = 3;
       rotates[3] = 2;
    }
    
    /*
     * 01X XX3
     * X23 X21 
     * XXX X0X
     */
    private static void LeftRotateZ(TetrisPiece p) {
       Point[] points =  p.getRelativePoints();              
       points[0] = new Point(points[0].x+1, points[0].y+2);
       points[1].y+=1;
       points[1].x+=1;
       points[3].y-=1;
       int[] sprites = p.getfBlockSprite();
       sprites[0] = 0;
       sprites[1] = 1;          
       sprites[2] = 1;
       sprites[3] = 0;
       int[] rotates = p.getfBlockRotate();
       rotates[0] = 2;
       rotates[1] = 2;
       rotates[2] = 0;
       rotates[3] = 0;
    }
    /*
     * 0XX XXX
     * 213 013
     * XXX XX2
     * 
     */
    private static void FlipRotateJ(TetrisPiece p) {
       Point[] points =  p.getRelativePoints();              
       points[0].y+=1;       
       points[2] = new Point(points[3].x, points[3].y+1);
       int[] sprites = p.getfBlockSprite();
       sprites[0] = 0;
       sprites[1] = 3;          
       sprites[2] = 0;
       sprites[3] = 1;
       int[] rotates = p.getfBlockRotate();
       rotates[0] = 3;
       rotates[1] = 1;
       rotates[2] = 2;
       rotates[3] = 1;
       
    }
 /*
     * X2X XXX
     * 013 013
     * XXX X2X   
     */
    private static void FlipRotateT(TetrisPiece p) {
        p.getRelativePoints()[2].y+=2;
    }
    /*
     * XX2 XXX
     * 013 013
     * XXX 2XX
     */
    private static void FlipRotateL(TetrisPiece p) {
       Point[] points =  p.getRelativePoints();                     
       points[2] = new Point(points[0].x, points[0].y+1);
       int[] sprites = p.getfBlockSprite();
       sprites[0] = 0;
       sprites[1] = 3;          
       sprites[2] = 0;
       sprites[3] = 1;
       int[] rotates = p.getfBlockRotate();
       rotates[0] = 3;
       rotates[1] = 1;
       rotates[2] = 2;
       rotates[3] = 1;
       
       
       
    }

    
    
    
}
