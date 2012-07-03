
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.openal.SoundStore;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alex
 */
public class GameInputParser {
    TetrisGame fGame;
    TextRenderer fTextRenderer;
    ReplayMaker fReplayMaker = null;
    public GameInputParser(TetrisGame game, TextRenderer renderer){
        fGame = game;
        fTextRenderer = renderer;
        fReplayMaker = fGame.getReplayMaker();
    }
    
    public void pollInput(){
        
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) 
            {
                int key = Keyboard.getEventKey();
                if (fGame.getGameState() == TetrisGame.GAME_PLAYING)
                fReplayMaker.addAction(key, true, fGame.getCurrentFrame() - fGame.getFirstFrame());                                
                InputParser.keyDown(fGame, fTextRenderer, key);
            } else { //key released!
                
                int key = Keyboard.getEventKey();
                if (fGame.getGameState() == TetrisGame.GAME_PLAYING)
                fReplayMaker.addAction(key, false, fGame.getCurrentFrame() - fGame.getFirstFrame());                    
                InputParser.keyUp(fGame, fTextRenderer, key);
            }
        }
        SoundStore.get().poll(0);
    }

}
