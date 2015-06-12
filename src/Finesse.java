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
public class Finesse
{


    /**
     * Create in instance of TypeTris
     */

public class DisplayExample  {
    final String         version = "2015-06-12";
    final TetrisGame     fGame        = new TetrisGame();
    final TetrisMenu     fMenu        = new TetrisMenu();
    final OptionsMenu    fOptions     = new OptionsMenu();
    final ReplayMenu     fReplay      = new ReplayMenu();    
    final KeyBindMenu    fKeyBindMenu = new KeyBindMenu();
    
    final TextureHolder  fTextures = new TextureHolder();
    final TextureAtlas  fTextureAtlas = new TextureAtlas(); 
    final GameInputParser fInputParser = new GameInputParser(fGame, fGame.getTextRenderer());
    final ReplayLoader fReplayLoader = new ReplayLoader();
    private int state = GAME_MENU;    
    public final static int GAME_MENU = 0;
    public final static int GAME_PLAY = 1;
    public final static int OPTIONS = 2;
    public final static int REPLAY = 3;    
    public final static int QUIT = 4;
    public final static int KEYBIND = 5;
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
            Display.setTitle("Finesse - Loading 0%");
            initGL(720,480);
            Display.setTitle("Finesse - Loading 15%");
            fTextures.init();
            
            fTextureAtlas.init(fTextures.getStackMinos().getImageWidth(),
                               fTextures.getStackMinos().getImageHeight(),
                               32, 32);
            Display.setTitle("Finesse - Loading 30%");
            SoundCache.init();
            fGame.setTextures(fTextures);
            fMenu.setTextures(fTextures);
            fOptions.setTextures(fTextures);
            fReplay.setTextures(fTextures);
            fKeyBindMenu.setTextures(fTextures);
            
            Display.setTitle("Finesse - Loading 45%");
            fGame.setTextureAtlas(fTextureAtlas);  
            Display.setTitle("Finesse - Loading 60%");
            fGame.init();
            Display.setTitle("Finesse - Loading 80%");
            fMenu.init();
            Display.setTitle("Finesse - Loading 85%");
            fOptions.init();
            fKeyBindMenu.init();
            Display.setTitle("Finesse - Loading 95%");
            fReplay.init();
            fReplay.setGame(fGame);
            Display.setTitle("Finesse  " + version);
            
            fGame.setOptions(fOptions);
            fOptions.loadSettings();
            fKeyBindMenu.loadSettings();
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
                            state = fOptions.whichState();
                            System.out.println(state);
                            stateChange = true;
                        }
                    } else if (state == KEYBIND) {
                        fKeyBindMenu.render();
                        fKeyBindMenu.pollInput();
                        if(fKeyBindMenu.exit()){
                            fKeyBindMenu.reset();
                            state = OPTIONS;
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
                    Display.sync(10000);
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
			Display.setVSyncEnabled(false);

		} catch (LWJGLException e) {
                        System.out.println(e);
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
    

    public Finesse(String args[])
    {
        DisplayExample displayExample = new DisplayExample(args);
        displayExample.start();       
        
    }
    
    public static void main(String args[])
    {       
        System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir") + "/lib/");
        new Finesse(args);
    }
   
}
