/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Dimension;

/**
 *
 * @author Alex
 */
public class OpenGLPreviewGUI {

    private int piece = 0;
    private TetrisCell[][] bitarray = new TetrisCell[2][4];
    private int startX;
    private int startY;
    private TextureAtlas minos = null;
    private int minoWidth = 20;
    private boolean monochrome = false;
    private boolean flipPreview;

    public OpenGLPreviewGUI(int startX, int startY) {
        this.startX = startX;
        this.startY = startY;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                bitarray[i][j] = new TetrisCell(0, 0, 0);
            }
        }
    }

    public void setTextureAtlas(TextureAtlas at) {
        minos = at;
    }

    public void setPiece(int pieceNo) {
        piece = pieceNo;
        setGrid();
    }

    public int getPiece() {
        return piece;
    }

    public void copyGrid(boolean[][] toCopy) {
        for (int i = 0; i < 2; i++) {
            System.arraycopy(toCopy[i], 0, bitarray[i], 0, 4);
        }
    }

    private void setNoPiece() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                bitarray[i][j].clear();
            }
        }
    }

    private void setIPiece() {
        bitarray[0][0].clear();
        bitarray[0][1].clear();
        bitarray[0][2].clear();
        bitarray[0][3].clear();
        bitarray[1][0].set(TetrisPiece.I_PIECE, 0, 3);
        bitarray[1][1].set(TetrisPiece.I_PIECE, 3, 1);
        bitarray[1][2].set(TetrisPiece.I_PIECE, 3, 1);
        bitarray[1][3].set(TetrisPiece.I_PIECE, 0, 1);
    }

    private void setJPiece() {
        bitarray[0][0].set(TetrisPiece.J_PIECE, 0, 0);
        bitarray[0][1].clear();
        bitarray[0][2].clear();
        bitarray[0][3].clear();
        bitarray[1][0].set(TetrisPiece.J_PIECE, 1, 3);
        bitarray[1][1].set(TetrisPiece.J_PIECE, 3, 1);
        bitarray[1][2].set(TetrisPiece.J_PIECE, 0, 1);
        bitarray[1][3].clear();
    }

    private void setLPiece() {
        bitarray[0][0].clear();
        bitarray[0][1].clear();
        bitarray[0][2].set(TetrisPiece.L_PIECE, 0, 0);
        bitarray[0][3].clear();
        bitarray[1][0].set(TetrisPiece.L_PIECE, 0, 3);
        bitarray[1][1].set(TetrisPiece.L_PIECE, 3, 1);
        bitarray[1][2].set(TetrisPiece.L_PIECE, 1, 2);
        bitarray[1][3].clear();
    }

    private void setTPiece() {
        bitarray[0][0].clear();
        bitarray[0][1].set(TetrisPiece.T_PIECE, 0, 0);
        bitarray[0][2].clear();
        bitarray[0][3].clear();
        bitarray[1][0].set(TetrisPiece.T_PIECE, 0, 3);
        bitarray[1][1].set(TetrisPiece.T_PIECE, 2, 2);
        bitarray[1][2].set(TetrisPiece.T_PIECE, 0, 1);
        bitarray[1][3].clear();
    }

    private void setOPiece() {
        bitarray[0][0].clear();
        bitarray[0][1].set(TetrisPiece.O_PIECE, 1, 0);
        bitarray[0][2].set(TetrisPiece.O_PIECE, 1, 1);
        bitarray[0][3].clear();
        bitarray[1][0].clear();
        bitarray[1][1].set(TetrisPiece.O_PIECE, 1, 3);
        bitarray[1][2].set(TetrisPiece.O_PIECE, 1, 2);
        bitarray[1][3].clear();
    }

    private void setSPiece() {
        bitarray[0][0].clear();
        bitarray[0][1].set(TetrisPiece.S_PIECE, 1, 0);
        bitarray[0][2].set(TetrisPiece.S_PIECE, 0, 1);
        bitarray[0][3].clear();
        bitarray[1][0].set(TetrisPiece.S_PIECE, 0, 3);
        bitarray[1][1].set(TetrisPiece.S_PIECE, 1, 2);
        bitarray[1][2].clear();
        bitarray[1][3].clear();
    }

    private void setZPiece() {
        bitarray[0][0].set(TetrisPiece.Z_PIECE, 0, 3);
        bitarray[0][1].set(TetrisPiece.Z_PIECE, 1, 1);
        bitarray[0][2].clear();
        bitarray[0][3].clear();
        bitarray[1][0].clear();
        bitarray[1][1].set(TetrisPiece.Z_PIECE, 1, 3);
        bitarray[1][2].set(TetrisPiece.Z_PIECE, 0, 1);
        bitarray[1][3].clear();
    }

    private void setGrid() {

        if (piece == TetrisPiece.NO_PIECE) {
            setNoPiece();
        } else if (piece == TetrisPiece.I_PIECE) {
            setIPiece();
        } else if (piece == TetrisPiece.J_PIECE) {
            setJPiece();
        } else if (piece == TetrisPiece.L_PIECE) {
            setLPiece();
        } else if (piece == TetrisPiece.T_PIECE) {
            setTPiece();
        } else if (piece == TetrisPiece.S_PIECE) {
            setSPiece();
        } else if (piece == TetrisPiece.Z_PIECE) {
            setZPiece();
        } else if (piece == TetrisPiece.O_PIECE) {
            setOPiece();
        }

    }

    public void render() {

        final int numCols = 4;
        final int numRows = 2;

        for (int rows = 0; rows < numRows; rows++) {
            for (int cols = 0; cols < numCols; cols++) {
                final TetrisCell bit = bitarray[rows][cols];
                renderBlock(cols * minoWidth + startX, rows * minoWidth + startY, bit);
            }

        }

    }

    private void renderBlock(int x, int y, TetrisCell piece) {
        if (piece.pieceType == TetrisPiece.NO_PIECE) {
            return;
        }
        minos.drawSprite(x, y, piece, flipPreview);

    }

    public Dimension getPreferredSize() {
        return new Dimension(96, 48);
    }

    public void setMonochrome(boolean b) {
        this.monochrome = b;
    }

    public boolean getMonochrome() {
        return monochrome;
    }

    public boolean isFlipPreview() {
        return flipPreview;
    }

    public void setFlipPreview(boolean flipPreview) {
        this.flipPreview = flipPreview;
    }
}
