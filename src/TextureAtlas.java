

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
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
class TextureAtlas {
    private int spriteWidth, spriteHeight;
    private int texWidth, texHeight;
    private int minoWidth = 20;
    private boolean noRotate = false;

    public boolean isNoRotate() {
        return noRotate;
    }

    public void setNoRotate(boolean noRotate) {
        this.noRotate = noRotate;
    }
    
    public Texture stack;
    public Texture prev;
    private IntBuffer vertexBuffer = BufferUtils.createIntBuffer(8);
    private DoubleBuffer textureBuffer = BufferUtils.createDoubleBuffer(256);
    
    private IntBuffer vertexBuffer_prev = BufferUtils.createIntBuffer(8);
    private DoubleBuffer textureBuffer_prev = BufferUtils.createDoubleBuffer(256);
    
    public void init(Texture stack, Texture prev, int tW, int tH, int sW, int sH)    
    {
        this.stack = stack;
        this.prev = prev;
        texWidth = tW;
        texHeight = tH;
        spriteWidth = sW;
        spriteHeight = sH;
        calculateSprites();
    }

    void calculateSprites(){
        double tw = (double)(spriteWidth) / texWidth;
        double th = (double)(spriteHeight) / texHeight;
        int numPerRow = texWidth / spriteWidth;
        for (int frameIndex = 0; frameIndex < 32; frameIndex++){
            double tx = (frameIndex % numPerRow) * tw;
            double ty = (frameIndex / numPerRow + 1) * th;
            double texVerts[] = {
                tx, ty,
                tx + tw, ty,
                tx + tw, ty + th,
                tx, ty + th
            };
            textureBuffer.put(texVerts);
            textureBuffer_prev.put(texVerts);
        }
        
    }
    
    private int[] getCellFlip(int posX, int posY, int rotation){
        
        if (rotation == 0 || noRotate)
        return new int[]{
            posX, posY,
            posX + minoWidth, posY,
            posX + minoWidth, posY + minoWidth,
            posX, posY + minoWidth
        };
        else if (rotation == 1)
        return new int[]{
            posX + minoWidth, posY,
            posX + minoWidth, posY + minoWidth,
            posX, posY + minoWidth,
            posX, posY
        };
        else if (rotation == 2)
        return new int[] {
            posX + minoWidth, posY + minoWidth,
            posX, posY + minoWidth,
            posX, posY,
            posX + minoWidth, posY
        };
        else
        return new int[] {            
            posX, posY + minoWidth,
            posX, posY,
            posX + minoWidth, posY,
            posX + minoWidth, posY + minoWidth
        };
    }
    
    private int[] getLineCoords(int posX, int posY, int side){
        
        if (side == 0)
        return new int[]{
            posX, posY,
            posX + minoWidth, posY
        };
        else if (side == 1)
        return new int[]{
            posX + minoWidth, posY,
            posX + minoWidth, posY + minoWidth,            
        };
        else if (side == 2)
        return new int[] {
            posX + minoWidth, posY + minoWidth,
            posX, posY + minoWidth,            
        };
        else
        return new int[] {            
            posX, posY + minoWidth,
            posX, posY            
        };
    }
    
    //used to draw outline
    public void drawLine(int posX, int posY, int side){        
        int[] verts = getLineCoords(posX,posY,side);
        
       
        
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        vertexBuffer.clear();
        vertexBuffer.put(verts);
        vertexBuffer.rewind();        
        
        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glVertexPointer(2, 0, vertexBuffer);       
        GL11.glDrawArrays(GL11.GL_LINES, 0, 2);
        GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);       
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        
        
    }
    public void drawSprite(int posX, int posY, TetrisCell cell) {

        int verts[] = getCellFlip(posX,posY,cell.orientationRotation);

        vertexBuffer.clear();
        vertexBuffer.put(verts);        

        vertexBuffer.rewind();         
        textureBuffer.position(cell.pieceType*8+cell.orientation*64);

        
        // ... Bind the texture, enable the proper arrays
        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
        
        GL11.glVertexPointer(2, 0, vertexBuffer);        
        GL11.glTexCoordPointer(2, 0, textureBuffer);             
        
        GL11.glDrawArrays(GL11.GL_QUADS, 0, 4);        
        
        GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);

    }

    void drawPreview(int posX, int posY, TetrisCell cell) {

        int verts[] = getCellFlip(posX,posY,cell.orientationRotation);

        vertexBuffer_prev.clear();
        vertexBuffer_prev.put(verts);        

        vertexBuffer_prev.rewind();         
        textureBuffer_prev.position(cell.pieceType*8+cell.orientation*64);

        
        // ... Bind the texture, enable the proper arrays
        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
        
        GL11.glVertexPointer(2, 0, vertexBuffer_prev);        
        GL11.glTexCoordPointer(2, 0, textureBuffer_prev);             
        
        GL11.glDrawArrays(GL11.GL_QUADS, 0, 4);        
        
        GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);        
    }
             
}
