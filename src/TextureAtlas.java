

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
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
    public Texture t;
    private IntBuffer vertexBuffer = BufferUtils.createIntBuffer(8);
    private DoubleBuffer textureBuffer = BufferUtils.createDoubleBuffer(64);
    
    public void init(Texture t, int tW, int tH, int sW, int sH)    
    {
        this.t = t;
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
        for (int frameIndex = 0; frameIndex < 8; frameIndex++){
            double tx = (frameIndex % numPerRow) * tw;
            double ty = (frameIndex / numPerRow + 1) * th;
            double texVerts[] = {
                tx, ty,
                tx + tw, ty,
                tx + tw, ty + th,
                tx, ty + th
            };
            textureBuffer.put(texVerts);
        }
        
    }
    void drawSprite(int posX, int posY, int frameIndex) {

        final int verts[] = {
            posX, posY,
            posX + minoWidth, posY,
            posX + minoWidth, posY + minoWidth,
            posX, posY + minoWidth
        };

        vertexBuffer.clear();
        vertexBuffer.put(verts);        
        
              
        
        
        vertexBuffer.rewind();         
        textureBuffer.position(frameIndex*8);

        
        // ... Bind the texture, enable the proper arrays
        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
        
        GL11.glVertexPointer(2, 0, vertexBuffer);        
        GL11.glTexCoordPointer(2, 0, textureBuffer);             
        
        GL11.glDrawArrays(GL11.GL_QUADS, 0, 4);        
        
        GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);

    }        
             
}
