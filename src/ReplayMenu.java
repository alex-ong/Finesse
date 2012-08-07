
import java.awt.Font;
import java.io.File;
import java.io.InputStream;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.ResourceLoader;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alex
 */
class ReplayMenu {
    private TetrisGame fGame = null;
    private ReplayInputParser rip;
    private boolean playingReplay = false;
    private boolean exit = false;
    private int selection = 0;
    private static final String[] captions = {"Open Replay...", "Return to Main Menu"};   
    private static final int NUM_SELECTIONS = captions.length;
    private static boolean MouseDown = false;
    private static int lineHeight = 30;
    private static final int startX = 100;
    private static final int startY = 100;
    private static final int startValueX = 400;
    private ReplayLoader rl;
    private JFileChooser fc;
    TrueTypeFont font;
    public static final int OPEN_REPLAY = 0;
    public static final int RETURN = 1;
    
        
    public boolean exit(){
        return exit;
    }
    public void reset(){
        exit = false;
        selection = 0;
    }
    public void setGame(TetrisGame tg){
        fGame = tg;
        rip = new ReplayInputParser(fGame, fGame.getTextRenderer());
    }
    TextureHolder fTextures = null;
    public void setTextures(TextureHolder th){
        fTextures = th;                
    }
    public void renderMenu(){
        renderBackdrop();
        renderText();
    }
    public void render() {
        if (!playingReplay) {
        renderMenu();    
        } else {
            if (fGame.getGameState() == fGame.GAME_PLAYING){
                rip.parseActions();
            }
            fGame.render();
            fGame.checkCountdown();
        }

    }   
    
    private Color getColor(int i){
        if (i == selection) {
            return Color.yellow;
        } else {
            return Color.white;
        }
    }
    private void renderText(){
       for (int i = 0; i < captions.length; i++){
       font.drawString(startX, startY + i * 30, 
                captions[i], getColor(i)); 
       }
    }
    public void init() {
	// load font from a .ttf file
    
	try {
		InputStream inputStream	= ResourceLoader.getResourceAsStream("dejavumonosansbold.ttf");
		Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
		awtFont2 = awtFont2.deriveFont(20f); // set font size  
                
		font = new TrueTypeFont(awtFont2, true);                
                
	} catch (Exception e) {
		e.printStackTrace();
	}	
        rl = new ReplayLoader();
        fc = new JFileChooser();
        String s = File.separatorChar + "rep" + File.separatorChar;        
        fc.setCurrentDirectory(new File(System.getProperty("user.dir")+s));
        
        FileFilter ff = new OnlyExt("rep");
        fc.setFileFilter(ff);
    }    
    
    private void renderBackdrop(){
            Color.white.bind();
                Texture texture = fTextures.getMainMenu();
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
    
    public void pollInput(){
        
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) 
            {
                int key = Keyboard.getEventKey();
                if (!playingReplay){
                    switch(key){        
                        case Keyboard.KEY_ESCAPE:
                            if (playingReplay){
                                stopReplay();                            
                            } else {
                                exit = true;
                            }
                            break;
                        case Keyboard.KEY_RETURN: 
                            enterSelection();
                            break;

                        case Keyboard.KEY_DOWN:
                            selection++;
                            if (selection >= NUM_SELECTIONS) {
                                selection = 0;
                            }
                            break;
                        case Keyboard.KEY_UP:
                            selection--;
                            if (selection < 0) {
                                selection = NUM_SELECTIONS - 1;
                            }
                            break;
                    }
                } else { //not playing replay
                    switch(key){        
                    case Keyboard.KEY_ESCAPE:
                        if (playingReplay){
                            stopReplay();                            
                        } else {
                            exit = true;
                        }
                        break;
                    }
                }
            
            
            }    
    
        }

        while (Mouse.next()){
            if (playingReplay) break; //only parse mouse when selecting.
            if(Mouse.getEventButton() == 0)
               if (Mouse.getEventButtonState() && MouseDown == false){            
                setSelection(Mouse.getX(), Mouse.getY());
                MouseDown = true;
               } else {
                MouseDown = false;
               }
            
        }
        
        
    }

    //mouse input, set selection
    void setSelection(int x, int y) {
        //invert that shit.
        y = 480 - y;            
            
        for (int i = 0; i < NUM_SELECTIONS; i++){
            int topY = startY + i*lineHeight;
            int bottomY = startY + (i+1)*lineHeight;
            if (y >= topY && y <= bottomY) {
                if (selection == i) enterSelection();
                else selection = i;
                break;
            }
        }
    }

    public int getSelection() {
        return selection;
    }
    private void enterSelection(){
        if (selection == (captions.length - 1)) { //if exit            
            exit = true;
            return;
        }
        invokeSelection();
    }

    private void stopReplay() {
        fGame.stopGame();
        playingReplay = false;
    }
    
    public class OnlyExt extends FileFilter { 
        String ext; 
        public OnlyExt(String ext) { 
            this.ext = "." + ext.toLowerCase(); 
        } 

        public String getDescription() {
            return "Replay files (" + ext + ")";
        }

        public boolean accept(File pathname) {
            return pathname.getName().toLowerCase().endsWith(ext);             
        }
    }
    
    
    
    
    
    private void invokeSelection() {        

    int returnVal = fc.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
           
            if (rl.load(fc.getSelectedFile().getAbsolutePath())){
                playingReplay = true;
                rip.playReplay(rl);
                
            } else {
                System.out.println ("error on opening file");
            }
        }
    }
}