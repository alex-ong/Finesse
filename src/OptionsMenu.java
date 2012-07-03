
import java.awt.Font;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
class OptionsMenu {
    private boolean exit = false;
    private int selection = 0;
    private static final String[] captions = {"Randomizer", "Alignment", "Auto Save Replays", 
                                              "Save Replay Min Time", "Save full replays only", 
                                              "Rotate SpriteSheet - Board",
                                              "Rotate SpriteSheet - Preview",
                                              "Rotate SpriteSheet - Active",
                                              "Return to Main Menu"};
    private String[][] values = { {"Pure", "Bag", "History 6"},
                                         {"Left", "Right", "Center"},
                                         {"No", "Yes"},
                                         {"ALL"},
                                         {"No", "Yes"},
                                         {"No", "Yes"},
                                         {"No", "Yes"},
                                         {"No", "Yes"},                                         
                                         {""}};
    private static boolean MouseDown = false;
    private static int selections[] = {1,2,1,0,1,0,0,0,0};
    private static final int startX = 50;
    private static final int startY = 100;
    private static final int lineHeight = 30;
    private static final int startValueX = 550;
    private static final int NUM_SELECTIONS = captions.length;
    
    TrueTypeFont font;
    public static final int GET_RANDOMIZER = 0;
    public static final int GET_ALIGNMENT = 1;
    public static final int GET_SAVE = 2;
    public static final int GET_MAXTIME = 3;
    public static final int GET_FULLSAVE = 4;
    public static final int GET_TEXTURE_ROTATE_BOARD = 5;
    public static final int GET_TEXTURE_ROTATE_PREVIEW = 6;
    public static final int GET_TEXTURE_ROTATE_ACTIVE = 7;
    public int getSetting(int setting) {
        return selections[setting];    
    }
        
    public boolean exit(){
        return exit;
    }
    public void reset(){
        exit = false;
        selection = 0;
    }
    TextureHolder fTextures = null;
    public void setTextures(TextureHolder th){
        fTextures = th;                
    }
    
    public void render(){
        renderBackdrop();
        renderText();
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
       font.drawString(startX, startY + i * lineHeight, 
                captions[i], getColor(i)); 
       }
       for (int i = 0; i < captions.length; i++){
       font.drawString(startValueX, startY + i * 30, 
                values[i][selections[i]], getColor(i)); 
       }
       
    }
    public void init() {
	// load font from a .ttf file
    
	try {
		InputStream inputStream	= 
                        ResourceLoader.getResourceAsStream("dejavumonosansbold.ttf");
		Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
		awtFont2 = awtFont2.deriveFont(20f); // set font size  
                
		font = new TrueTypeFont(awtFont2, true);                
                
	} catch (Exception e) {
		e.printStackTrace();
	}	
        String[] b = new String[120];
        b[0] = "ALL";
        for (int i = 1; i < b.length; i++){
            b[i] = Integer.toString(i);
        }
        values[3] = b;
        
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
        
                switch(key){        
                    case Keyboard.KEY_ESCAPE:
                        exit = true;
                        break;
                    case Keyboard.KEY_RETURN: 
                        enterSelection();
                        break;
                    case Keyboard.KEY_RIGHT: 
                        leftRightSelection(1);
                        break;                        
                    case Keyboard.KEY_LEFT: 
                        leftRightSelection(-1);
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
            
            
            }    
    
        }
        while (Mouse.next()){
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
        invokeSelection(1);
    }
    private void leftRightSelection(int i){
        if (selection == (captions.length - 1)) { //if exit            
            return;
        }
        invokeSelection(i);
    }
    
    private void invokeSelection(int i) {        
        
        selections[selection]+= i;
        if (selections[selection] >= values[selection].length){
            selections[selection] = 0;    
        } else if (selections[selection] < 0){
            selections[selection] = values[selection].length - 1; 
        }
    }
}