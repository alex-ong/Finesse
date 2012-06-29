
import java.util.List;
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
public class ReplayInputParser {
    private TetrisGame fGame;
    private TextRenderer fTextRenderer;
    private ReplayLoader fReplayLoader;
    private int currentAction = 0;
    public ReplayInputParser(TetrisGame game, TextRenderer renderer){
        fGame = game;
        fTextRenderer = renderer;
    }
    public void playReplay(ReplayLoader rl){
        fReplayLoader = rl;
        fGame.countDownGame();        
        fGame.printReplay = false;
        fGame.setPieces(fReplayLoader.getPieces());
        fGame.setPieceAlignment(fGame.stringToAlignment(fReplayLoader.getAlignment()));        
        fGame.GameLogic();
    }
    private void endReplay(){
        List<Action> acts = fReplayLoader.getActions();
        fGame.stopGame();
        fGame.setGameTimer(acts.get(acts.size()-1).time);
    }
    public void parseActions(){
        if (currentAction >= fReplayLoader.getActions().size()) {
            return;
        }
        Action a = fReplayLoader.getActions().get(currentAction);
        
        while (fGame.getGameTimer() > a.time){
            parseAction(a);            
            currentAction++;
            if (currentAction >= fReplayLoader.getActions().size()) {
                endReplay();
                break;
            }
            a = fReplayLoader.getActions().get(currentAction);
        }
    }
    public void parseAction(Action a){    

    if (a.down) 
    {
        int key = a.key;

        switch(key){
            case Keyboard.KEY_1: fGame.typer(0, TetrisPiece.FLIP); fTextRenderer.keyDown(0, 0, '1'); break; 
            case Keyboard.KEY_2: fGame.typer(1, TetrisPiece.FLIP); fTextRenderer.keyDown(1, 0, '2'); break; 
            case Keyboard.KEY_3: fGame.typer(2, TetrisPiece.FLIP); fTextRenderer.keyDown(2, 0, '3'); break; 
            case Keyboard.KEY_4: fGame.typer(3, TetrisPiece.FLIP); fTextRenderer.keyDown(3, 0,'4'); break; 
            case Keyboard.KEY_5: fGame.typer(4, TetrisPiece.FLIP); fTextRenderer.keyDown(4, 0,'5'); break; 
            case Keyboard.KEY_6: fGame.typer(5, TetrisPiece.FLIP); fTextRenderer.keyDown(5, 0,'6'); break; 
            case Keyboard.KEY_7: fGame.typer(6, TetrisPiece.FLIP); fTextRenderer.keyDown(6, 0,'7'); break; 
            case Keyboard.KEY_8: fGame.typer(7, TetrisPiece.FLIP); fTextRenderer.keyDown(7, 0,'8'); break;           
            case Keyboard.KEY_9: fGame.typer(8, TetrisPiece.FLIP); fTextRenderer.keyDown(8, 0,'9'); break;   
            case Keyboard.KEY_0: fGame.typer(9, TetrisPiece.FLIP); fTextRenderer.keyDown(9, 0,'0'); break;   

            case Keyboard.KEY_Q: fGame.typer(0, TetrisPiece.CCW); fTextRenderer.keyDown(0, 1,'Q'); break; 
            case Keyboard.KEY_W: fGame.typer(1, TetrisPiece.CCW); fTextRenderer.keyDown(1, 1,'W'); break; 
            case Keyboard.KEY_E: fGame.typer(2, TetrisPiece.CCW); fTextRenderer.keyDown(2, 1,'E'); break; 
            case Keyboard.KEY_R: fGame.typer(3, TetrisPiece.CCW); fTextRenderer.keyDown(3, 1,'R'); break; 
            case Keyboard.KEY_T: fGame.typer(4, TetrisPiece.CCW); fTextRenderer.keyDown(4, 1,'T'); break; 
            case Keyboard.KEY_Y: fGame.typer(5, TetrisPiece.CCW); fTextRenderer.keyDown(5, 1,'Y'); break; 
            case Keyboard.KEY_U: fGame.typer(6, TetrisPiece.CCW); fTextRenderer.keyDown(6, 1,'U'); break; 
            case Keyboard.KEY_I: fGame.typer(7, TetrisPiece.CCW); fTextRenderer.keyDown(7, 1,'I'); break;           
            case Keyboard.KEY_O: fGame.typer(8, TetrisPiece.CCW); fTextRenderer.keyDown(8, 1,'O'); break;   
            case Keyboard.KEY_P: fGame.typer(9, TetrisPiece.CCW); fTextRenderer.keyDown(9, 1,'P'); break;   

            case Keyboard.KEY_A: fGame.typer(0, TetrisPiece.NEUTRAL); fTextRenderer.keyDown(0, 2,'A'); break; 
            case Keyboard.KEY_S: fGame.typer(1, TetrisPiece.NEUTRAL); fTextRenderer.keyDown(1, 2,'S'); break; 
            case Keyboard.KEY_D: fGame.typer(2, TetrisPiece.NEUTRAL); fTextRenderer.keyDown(2, 2,'D'); break; 
            case Keyboard.KEY_F: fGame.typer(3, TetrisPiece.NEUTRAL); fTextRenderer.keyDown(3, 2,'F'); break; 
            case Keyboard.KEY_G: fGame.typer(4, TetrisPiece.NEUTRAL); fTextRenderer.keyDown(4, 2,'G'); break; 
            case Keyboard.KEY_H: fGame.typer(5, TetrisPiece.NEUTRAL); fTextRenderer.keyDown(5, 2,'H'); break; 
            case Keyboard.KEY_J: fGame.typer(6, TetrisPiece.NEUTRAL); fTextRenderer.keyDown(6, 2,'J'); break; 
            case Keyboard.KEY_K: fGame.typer(7, TetrisPiece.NEUTRAL); fTextRenderer.keyDown(7, 2,'K'); break;           
            case Keyboard.KEY_L: fGame.typer(8, TetrisPiece.NEUTRAL); fTextRenderer.keyDown(8, 2,'L'); break;   
            case Keyboard.KEY_SEMICOLON: fGame.typer(9, TetrisPiece.NEUTRAL); fTextRenderer.keyDown(9, 2,';'); break;   

            case Keyboard.KEY_Z: fGame.typer(0, TetrisPiece.CW); fTextRenderer.keyDown(0, 3,'Z'); break; 
            case Keyboard.KEY_X: fGame.typer(1, TetrisPiece.CW); fTextRenderer.keyDown(1, 3,'X'); break; 
            case Keyboard.KEY_C: fGame.typer(2, TetrisPiece.CW); fTextRenderer.keyDown(2, 3,'C'); break; 
            case Keyboard.KEY_V: fGame.typer(3, TetrisPiece.CW); fTextRenderer.keyDown(3, 3,'V'); break; 
            case Keyboard.KEY_B: fGame.typer(4, TetrisPiece.CW); fTextRenderer.keyDown(4, 3,'B'); break; 
            case Keyboard.KEY_N: fGame.typer(5, TetrisPiece.CW); fTextRenderer.keyDown(5, 3,'N'); break; 
            case Keyboard.KEY_M: fGame.typer(6, TetrisPiece.CW); fTextRenderer.keyDown(6, 3,'M'); break; 
            case Keyboard.KEY_COMMA: fGame.typer(7, TetrisPiece.CW); fTextRenderer.keyDown(7, 3,','); break;           
            case Keyboard.KEY_PERIOD: fGame.typer(8, TetrisPiece.CW); fTextRenderer.keyDown(8, 3,'.'); break;   
            case Keyboard.KEY_SLASH: fGame.typer(9, TetrisPiece.CW); fTextRenderer.keyDown(9, 3,'/'); break;   
            case Keyboard.KEY_SPACE: fGame.hold(); fTextRenderer.spaceDown();break;
        }
    } else { //key released!

        int key = a.key;

        switch(key){
            case Keyboard.KEY_1     : fTextRenderer.keyUp(0, 0,'1');break;                        
            case Keyboard.KEY_2     : fTextRenderer.keyUp(1, 0,'2');break;
            case Keyboard.KEY_3     : fTextRenderer.keyUp(2, 0,'3');break;
            case Keyboard.KEY_4     : fTextRenderer.keyUp(3, 0,'4');break;
            case Keyboard.KEY_5     : fTextRenderer.keyUp(4, 0,'5');break;                     
            case Keyboard.KEY_6     : fTextRenderer.keyUp(5, 0,'6');break;                    
            case Keyboard.KEY_7     : fTextRenderer.keyUp(6, 0,'7');break;
            case Keyboard.KEY_8     : fTextRenderer.keyUp(7, 0,'8');break;
            case Keyboard.KEY_9     : fTextRenderer.keyUp(8, 0,'9');break;
            case Keyboard.KEY_0     : fTextRenderer.keyUp(9, 0,'0');break;

            case Keyboard.KEY_Q     : fTextRenderer.keyUp(0, 1,'Q');break;                        
            case Keyboard.KEY_W     : fTextRenderer.keyUp(1, 1,'W');break;
            case Keyboard.KEY_E     : fTextRenderer.keyUp(2, 1,'E');break;
            case Keyboard.KEY_R     : fTextRenderer.keyUp(3, 1,'R');break;
            case Keyboard.KEY_T     : fTextRenderer.keyUp(4, 1,'T');break;                     
            case Keyboard.KEY_Y     : fTextRenderer.keyUp(5, 1,'Y');break;                    
            case Keyboard.KEY_U     : fTextRenderer.keyUp(6, 1,'U');break;
            case Keyboard.KEY_I     : fTextRenderer.keyUp(7, 1,'I');break;
            case Keyboard.KEY_O     : fTextRenderer.keyUp(8, 1,'O');break;
            case Keyboard.KEY_P     : fTextRenderer.keyUp(9, 1,'P');break;

            case Keyboard.KEY_A     : fTextRenderer.keyUp(0, 2,'A');break;                        
            case Keyboard.KEY_S     : fTextRenderer.keyUp(1, 2,'S');break;
            case Keyboard.KEY_D     : fTextRenderer.keyUp(2, 2,'D');break;
            case Keyboard.KEY_F     : fTextRenderer.keyUp(3, 2,'F');break;
            case Keyboard.KEY_G     : fTextRenderer.keyUp(4, 2,'G');break;                     
            case Keyboard.KEY_H     : fTextRenderer.keyUp(5, 2,'H');break;                    
            case Keyboard.KEY_J     : fTextRenderer.keyUp(6, 2,'J');break;
            case Keyboard.KEY_K     : fTextRenderer.keyUp(7, 2,'K');break;
            case Keyboard.KEY_L     : fTextRenderer.keyUp(8, 2,'L');break;
            case Keyboard.KEY_SEMICOLON : fTextRenderer.keyUp(9, 2,';');break;

            case Keyboard.KEY_Z     : fTextRenderer.keyUp(0, 3,'Z');break;                        
            case Keyboard.KEY_X     : fTextRenderer.keyUp(1, 3,'X');break;
            case Keyboard.KEY_C     : fTextRenderer.keyUp(2, 3,'C');break;
            case Keyboard.KEY_V     : fTextRenderer.keyUp(3, 3,'V');break;
            case Keyboard.KEY_B     : fTextRenderer.keyUp(4, 3,'B');break;                     
            case Keyboard.KEY_N     : fTextRenderer.keyUp(5, 3,'N');break;                    
            case Keyboard.KEY_M     : fTextRenderer.keyUp(6, 3,'M');break;
            case Keyboard.KEY_COMMA : fTextRenderer.keyUp(7, 3,',');break;
            case Keyboard.KEY_PERIOD: fTextRenderer.keyUp(8, 3,'.');break;
            case Keyboard.KEY_SLASH : fTextRenderer.keyUp(9, 3,'/');break;
            case Keyboard.KEY_SPACE : fTextRenderer.spaceUp();
        }

    }
    SoundStore.get().poll(0);
}


}