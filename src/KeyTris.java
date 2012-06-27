
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

/**
 * This class is a GUI for the Tetris Bean.
 * 
 * @author Alex Ong
 */
public class KeyTris
{


    /**
     * Create in instance of TypeTris
     */

public class DisplayExample  {
    final TetrisGame     fGame        = new TetrisGame();
    final TetrisMenu     fMenu        = new TetrisMenu();
    final OptionsMenu    fOptions     = new OptionsMenu();
    final ReplayMenu     fReplay      = new ReplayMenu();    
    //final CreditsMenu    fCredits     = new CreditsMenu();
    
    final TextureHolder  fTextures = new TextureHolder();
    final TextureAtlas  fTextureAtlas = new TextureAtlas(); 
    final InputParser fInputParser = new InputParser(fGame, fGame.getTextRenderer());
    final ReplayLoader fReplayLoader = new ReplayLoader();
    private int state = GAME_MENU;    
    private final static int GAME_MENU = 0;
    private final static int GAME_PLAY = 1;
    private final static int OPTIONS = 2;
    private final static int REPLAY = 3;
    private final static int CREDITS = 4;
    private final static int QUIT = 5;
    private boolean stateChange = false;
    public DisplayExample(String args[]){
        if (args.length > 0) {
            fReplayLoader.load(args[0]);
        }
    }
    /**
	 * Start the example
	 */
    public void start() {
            Display.setTitle("KeyTris - Loading 0%");
            initGL(720,480);
            Display.setTitle("KeyTris - Loading 15%");
            fTextures.init();
            fTextureAtlas.init(fTextures.getMinos(),
                                    fTextures.getMinos().getImageWidth(),
                                    fTextures.getMinos().getImageHeight(),
                                    32, 32);
            Display.setTitle("KeyTris - Loading 30%");
            SoundCache.init();
            fGame.setTextures(fTextures);
            fMenu.setTextures(fTextures);
            fOptions.setTextures(fTextures);
            fReplay.setTextures(fTextures);
            Display.setTitle("KeyTris - Loading 45%");
            fGame.setTextureAtlas(fTextureAtlas);  
            Display.setTitle("KeyTris - Loading 60%");
            fGame.init();
            Display.setTitle("KeyTris - Loading 80%");
            fMenu.init();
            Display.setTitle("KeyTris - Loading 85%");
            fOptions.init();
            Display.setTitle("KeyTris - Loading 95%");
            fReplay.init();
            fReplay.setGame(fGame);
            Display.setTitle("KeyTris");
            
            fGame.setOptions(fOptions);
            while (true) {
                    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
                    if (stateChange){
                        stateChange = false;
                        switch (state) {
                            case GAME_PLAY:
                                fGame.stopGame();
                                fGame.printReplay = true;
                                fGame.countDownGame();
                                fGame.GameLogic();
                            break;
                            case GAME_MENU:
                                fMenu.reset();
                            break;
                            case OPTIONS:
                                fMenu.reset();
                            break;
                            case REPLAY:
                                fMenu.reset();
                            break;
                            case CREDITS:
                                fMenu.reset();
                            break;
                            case QUIT:                                   
                                AL.destroy();
                                Display.destroy();
                                System.exit(0);
                            break;
                        }
   
                    }
                    
                    if (state == GAME_PLAY){
                        fGame.render();
                        fGame.checkCountdown();
                        fInputParser.pollInput();
                        if (fGame.mainMenu()) {
                            state = GAME_MENU;
                            stateChange = true;
                        }                        
                    } else if (state == GAME_MENU) {
                        fMenu.render();
                        fMenu.pollInput();
                        if (fMenu.start()) {
                            state = fMenu.getSelection() + 1;
                            stateChange = true;
                        }
                    } else if (state == OPTIONS) {
                        fOptions.render();
                        fOptions.pollInput();
                        if(fOptions.exit()){
                            fOptions.reset();
                            state = GAME_MENU;
                            stateChange = true;
                        }
                       
                    } else if (state == REPLAY) {
                        fReplay.render();
                        fReplay.pollInput();
                        if(fReplay.exit()){
                            fReplay.reset();
                            state = GAME_MENU;
                            stateChange = true;
                        }
                    }
                    
                    Display.update();
                    Display.sync(120);
                    if (Display.isCloseRequested()) {                            
                            AL.destroy();
                            Display.destroy();
                            System.exit(0);
                    }
            }
    }
	
    
        
        
        
	/**
	 * Initialise the GL display
	 * 
	 * @param width The width of the display
	 * @param height The height of the display
	 */
	private void initGL(int width, int height) {
		try {
			Display.setDisplayMode(new DisplayMode(width,height));
			Display.create();
			//Display.setVSyncEnabled(true);

		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		GL11.glEnable(GL11.GL_TEXTURE_2D);               
        
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);          
        
        	// enable alpha blending
        	GL11.glEnable(GL11.GL_BLEND);
        	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        	GL11.glViewport(0,0,width,height);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, height, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

	}
	



}    
    



    
    
    
    
    public KeyTris(String args[])
    {
        DisplayExample displayExample = new DisplayExample(args);
        displayExample.start();       
        
    }

        
	
    
    
    
    public static void main(String args[])
    {
       // System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir") + "/lib/");
        //make sure u copy the dlls!
        new KeyTris(args);

        
    }
    

    
    
        
   
}
