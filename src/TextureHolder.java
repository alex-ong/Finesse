
import java.io.IOException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alex
 */
public class TextureHolder {
        
    private Texture backdrop;      
    private Texture stackMinos;
    private Texture previewMinos;
    private Texture activeMinos;

    public Texture getActiveMinos() {
        return activeMinos;
    }
    public Texture getPreviewMinos() {
        return previewMinos;
    }
    
    private Texture flash;
    private Texture mainMenuBackdrop;
    
    public Texture getStackMinos() {
        return stackMinos;
    }
    public Texture getBackdrop(){
        return backdrop;
    }
    
    public Texture getFlash(){
        return flash;
    }
    public Texture getMainMenu(){
        return mainMenuBackdrop;
    }
    public void init() {

        try {
                // load texture from PNG file
            backdrop = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("backdrop.png"));         
            stackMinos = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("minos_stack.png"));    
            previewMinos = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("minos_preview.png"));    
            activeMinos = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("minos_active.png")); 
            flash = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("flash.png"));
            mainMenuBackdrop = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("mainmenu.png"));
        } catch (IOException e) {
                e.printStackTrace();
        }
    }
}
