
import java.io.IOException;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alex
 */
public class SoundCache {
    private static Audio[] waves = new Audio[10]; 
    private static Audio hold = null;
    private static Audio countdown1 = null;
    private static Audio countdown3 = null;
    private static Audio gameClear = null;
    private static Audio undo = null;


    /**
    * Initialise resources
    */
    public static Audio getUndo() {
        return undo;
    }
    public static Audio getColumn(int i) {
        return waves[i];
    }
    public static Audio getHold(){
        return hold;
    }
    public static Audio getCountDown1(){
        return countdown1;
    }
    public static Audio getCountDown3(){
        return countdown3;
    }
    public static Audio getGameClear(){
        return gameClear;
    }
    
    public static void init() {

        try {
	    for (int i = 0; i < 10; i++){
                waves[i] = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("col" + i + ".wav"));
            }
            hold = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("hold.wav"));
            countdown1 = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("countdown3.wav"));
            countdown3 = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("countdown1.wav"));
            gameClear = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("gameclear.wav"));
            undo = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("undo.wav"));
        } catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
}
