
import java.util.List;
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

    private final TetrisGame fGame;
    private final TextRenderer fTextRenderer;
    private ReplayLoader fReplayLoader;
    private int currentAction = 0;

    public ReplayInputParser(TetrisGame game, TextRenderer renderer) {
        fGame = game;
        fTextRenderer = renderer;
    }

    public void playReplay(ReplayLoader rl) {
        currentAction = 0;
        fReplayLoader = rl;
        fGame.countDownGame();
        fGame.printReplay = false;
        fGame.setPieces(fReplayLoader.getPieces());
        fGame.setPieceAlignment(TetrisGame.stringToAlignment(fReplayLoader.getAlignment()));        
        fGame.setCanUndo(fReplayLoader.getUndo());
        fGame.GameLogic();
    }

    private void endReplay() {
        List<Action> acts = fReplayLoader.getActions();
        fGame.stopGame();
        fGame.setGameTimer(fReplayLoader.getTime());
    }

    public void parseActions() {
        if (currentAction >= fReplayLoader.getActions().size()) {
            return;
        }
        Action a = fReplayLoader.getActions().get(currentAction);

        while (fGame.getGameTimer() > a.time) {
            parseAction(a);
            currentAction++;
            if (currentAction >= fReplayLoader.getActions().size()) {
                endReplay();
                break;
            }
            a = fReplayLoader.getActions().get(currentAction);
        }
    }

    public void parseAction(Action a) {

        if (a.down) {
            InputParser.keyDown(fGame, fTextRenderer, a.key);

        } else { //key released!

            InputParser.keyUp(fGame, fTextRenderer, a.key);

        }
        SoundStore.get().poll(0);
    }

}
