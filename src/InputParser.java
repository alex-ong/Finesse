
import org.lwjgl.input.Keyboard;

/**
 *
 * @author Alex
 */
public class InputParser {

    public static void keyDown(TetrisGame fGame, TextRenderer fTextRenderer, int key) {
        char c = Keyboard.getKeyName(key).toUpperCase().charAt(0);

        InputLoader.columnOrientation convert = InputLoader.keyToCode(key);
        if (convert.isNormal()) {
            fGame.typer(convert.column, convert.orientation, key);
            fTextRenderer.keyDown(convert.column, convert.orientation, c);
        } else if (convert.column == InputLoader.HOLD) {
            fGame.hold();
            fTextRenderer.spaceDown();
        } else if (convert.column == InputLoader.UNDO) {
            fGame.undo();
            fTextRenderer.backSpaceDown();
        } else if (convert.column == InputLoader.REVERSE_ORIENT ||
                    convert.column == InputLoader.REVERSE_ORIENT_ALT) {
            //nothing for now.
            fTextRenderer.shiftDown();
        } else {
            switch (key) {
                case Keyboard.KEY_RETURN:
                    fGame.stopGame();
                    fGame.countDownGame();
                    fGame.GameLogic();
                    break;
                case Keyboard.KEY_ESCAPE:
                    fGame.setMenu(true);
                    break;
            }
        }
    }

    public static void keyUp(TetrisGame fGame, TextRenderer fTextRenderer, int key) {
        boolean dontRelease = false;
        char c = Keyboard.getKeyName(key).toUpperCase().charAt(0);
        InputLoader.columnOrientation convert = InputLoader.keyToCode(key);
        if (convert.isNormal()) {
            fTextRenderer.keyUp(convert.column, convert.orientation, c);
        } else if (convert.column == InputLoader.HOLD) {
            fTextRenderer.spaceUp();
        } else if (convert.column == InputLoader.UNDO) {
            fTextRenderer.backSpaceUp();
        } else if (convert.column == InputLoader.REVERSE_ORIENT ||
            convert.column == InputLoader.REVERSE_ORIENT_ALT) {
            fTextRenderer.shiftUp();
        } else {
            dontRelease = true;
        }
        if (!dontRelease) {
            fGame.releaseKey(key);
        }
    }
}
