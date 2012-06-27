
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
    private Texture minos;
    private Texture flash;
    private Texture mainMenuBackdrop;
    
    public Texture getMinos() {
        return minos;
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
            minos = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("minos.png"));    
            flash = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("flash.png"));
            mainMenuBackdrop = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("mainmenu.png"));
        } catch (IOException e) {
                e.printStackTrace();
        }
    }
}
