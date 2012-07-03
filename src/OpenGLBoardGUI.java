
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alex
 */
public class OpenGLBoardGUI {
        private TetrisGame fGame = null;
        private TetrisBoard fBoard = null;
        private static final int startX = 121;
        private static final int startY = 36;
        private static final int blockWidth = 20;
        private static final int blockHeight = 20;
        private static final int width = 200;
        private static final int height = 400;
        private TextureHolder textures = null;
        private TextureAtlas minos = null;
        private TextRenderer fTextRenderer = null;
    	/**
	 * draw a quad with the image on it
	 */
        public OpenGLBoardGUI(TetrisGame tg, TextureHolder th, TetrisBoard tb, TextRenderer tr){
            fGame = tg;
            textures = th;
            fBoard = tb;
            fTextRenderer = tr;
            
        }
        
        
        public void setTextureAtlas(TextureAtlas minos)
        {
           
            this.minos = minos;
        }

	public void render() {
               setStats(fGame.getCurrentFrame()-fGame.getFirstFrame(),
                       fGame.getPieces(),
                       fGame.getTotalLines(),
                       fGame.getKeys(),
                       fGame.getGameState(),
                       fGame.getPieceStats());               
               renderBackdrop();
               fTextRenderer.render();             
               renderPieces();
               renderOutline();

	}
        
        private void renderLine(int x, int y, int side) {
            minos.drawLine(x,y,side);            
        }   
        
        private void renderOutline(){
            // Draw the board pieces if board not null.
            if (fBoard != null) 
            {
            final int numCols = fBoard.getColumns();
            final int numRows = fBoard.getRows();                        
            final int startRow = fBoard.getInvisRows();            
            Color.white.bind();
            
                for (int cols = 0; cols < numCols; cols++) 
                {
                    for (int rows = startRow; rows < numRows; rows++) 
                    {
                        int aRows = rows - startRow;
                        final TetrisCell piece = fBoard.getPieceAt(cols, rows);

                        if (piece.pieceType != TetrisBoard.EMPTY_BLOCK) 
                        {   
                            int ColsArg = cols*blockWidth + startX;
                            int RowsArg = aRows*blockHeight+startY;
                            TetrisCell above = fBoard.getPieceAt(cols,rows-1);
                            if (above != null && above.pieceType == TetrisBoard.EMPTY_BLOCK)
                            renderLine(ColsArg,RowsArg,0);
                            TetrisCell right = fBoard.getPieceAt(cols+1,rows);
                            if (right != null && right.pieceType == TetrisBoard.EMPTY_BLOCK)
                            renderLine(ColsArg,RowsArg,1);
                            TetrisCell down = fBoard.getPieceAt(cols,rows+1);
                            if (down != null && down.pieceType == TetrisBoard.EMPTY_BLOCK)
                            renderLine(ColsArg,RowsArg,2);
                            TetrisCell left = fBoard.getPieceAt(cols-1,rows);
                            if (left != null && left.pieceType == TetrisBoard.EMPTY_BLOCK)
                            renderLine(ColsArg,RowsArg,3);
                            
                        }
                    }
                } 
            }	            
            
        }
        private void renderPieces() {
            
            // Draw the board pieces if board not null.
            if (fBoard != null) 
            {
            final int numCols = fBoard.getColumns();
            final int numRows = fBoard.getRows();                        
            final int startRow = fBoard.getInvisRows();            
            minos.stack.bind();
            Color.white.bind();
                for (int cols = 0; cols < numCols; cols++) 
                {
                    for (int rows = startRow; rows < numRows; rows++) 
                    {
                        int aRows = rows - startRow;
                        final TetrisCell piece = fBoard.getPieceAt(cols, rows);

                        if (piece.pieceType != TetrisBoard.EMPTY_BLOCK) 
                        {                            
                            renderBlock(cols*blockWidth + startX, aRows*blockHeight+startY, piece);
                        }
                    }
                } 
            }	
        }

        private void renderBlock(int x, int y, TetrisCell piece) {
            minos.drawSprite(x,y,piece);
            
        }            
            

        
        private void renderBackdrop(){
            Color.white.bind();
                Texture texture = textures.getBackdrop();
		texture.bind(); // or GL11.glBind(texture.getTextureID());
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0,0);
			GL11.glVertex2f(0,0);
			GL11.glTexCoord2f(1,0);
			GL11.glVertex2f(texture.getTextureWidth(),0);
			GL11.glTexCoord2f(1,1);
			GL11.glVertex2f(texture.getTextureWidth(),texture.getTextureHeight());
			GL11.glTexCoord2f(0,1);
			GL11.glVertex2f(0,texture.getTextureHeight());
		GL11.glEnd();
        }
    
    private void setStats(long time, int pieces, int lines, int keys, int gameState, int[] pieceStats) {
        fTextRenderer.setTime(time);
        fTextRenderer.setPieces(pieces);
        fTextRenderer.setLines(lines);
        fTextRenderer.setKeys(keys);
        fTextRenderer.setGameState(gameState);
        fTextRenderer.setPieceStats(pieceStats);
    }

    public void win() {
        fTextRenderer.setGameState(TetrisGame.GAME_WIN);
    }

    void start() {
        fTextRenderer.start();
    }

    TextureAtlas getTextureAtlas() {
        return minos;
    }



        
}
