
import java.util.LinkedList;
import org.newdawn.slick.Color;

/**
 * A generic Tetris game with no GUI.
 *
 * @author Scott Clee
 */
public class TetrisGame {

    private final ReplayMaker fReplayMaker = new ReplayMaker();
    private final TetrisBoard fBoard;
    private TetrisPiece fCurrPiece;
    private TetrisPiece fHoldPiece = null;
    private final OpenGLPreviewGUI[] fPreviews;
    private final OpenGLPreviewGUI fHoldPreview;
    private final OpenGLPreviewGUI fNowPreview;
    private final boolean[] countdowns = new boolean[3];

    private int fTotalLines;
    private int fGameState = GAME_COUNTDOWN;
    public boolean printReplay = true;
    public static final int GAME_COUNTDOWN = 0;
    public static final int GAME_PLAYING = 1;
    public static final int GAME_WIN = 2;
    public static final int GAME_FINISHED = 3;
    private final ScoreKeeper fScores = new ScoreKeeper();
    private OpenGLBoardGUI fBoardGUI = null;
    private final TextRenderer fTextRenderer = new TextRenderer();
    private TextureHolder fTextures = null;
    private OptionsMenu options = null;

    private boolean fMenu = false;
    public final static int NUM_ROWS = 25;
    public final static int NUM_INVIS_ROWS = 5;

    private int randomizer = 1;
    public static final int PURE_RANDOMIZER = 0;
    public static final int BAG_RANDOMIZER = 1;
    public static final int HISTORY_RANDOMIZER = 2;
    public static final int BAG_PLUS_RANDOMIZER = 3;
    public static final int LOAD_RANDOMIZER = 4;
    public final static int NUM_PREVIEWS = 9;
    private int pieceAlignment = TetrisPiece.CENTER_ALIGNED;
    private LinkedList<Integer> morePieces = null;

    private final int previewX = 343;
    private final int previewY = 82;

    private final int currentPieceX = 343;
    private final int currentPieceY = 36;

    private long currentFrame = 0;
    private long gameStartFrame = 0;
    private int fPieces;
    private int fKeys;
    private final int[] pieceStats = new int[7];
    private String loadPieces;
    private TetrisGameState previousMove = null;
    private boolean canUndo;
    private int reverseDown = 0;
    

    /**
     * Create a TetrisGame.
     */
    public TetrisGame() {
        fBoard = new TetrisBoard(10, NUM_ROWS, NUM_INVIS_ROWS);
        fGameState = GAME_FINISHED;
        fPreviews = new OpenGLPreviewGUI[NUM_PREVIEWS];
        for (int i = 0; i < NUM_PREVIEWS; i++) {
            fPreviews[i] = new OpenGLPreviewGUI(previewX, previewY + i * 41);
        }
        fNowPreview = new OpenGLPreviewGUI(currentPieceX, currentPieceY);
        fHoldPreview = new OpenGLPreviewGUI(currentPieceX - 320, currentPieceY + 1);
        initRandomizer();

    }

    public void init() {
        fTextRenderer.init(fTextures);
        fTextRenderer.setScoreList(fScores.getScores());

        GameLogic();
    }

    public TextRenderer getTextRenderer() {
        return fTextRenderer;
    }

    public long getCurrentFrame() {
        return currentFrame;
    }

    public long getFirstFrame() {
        return gameStartFrame;
    }

    /**
     * Get the accurate system time
     *
     * @return The system time in milliseconds
     */
    public long getTime() {
        return System.nanoTime() / 1000000;
    }

    public long getGameTimer() {
        return currentFrame - gameStartFrame;
    }
    public int getReverseDown() {
        return reverseDown;
    }

    public void render() {
        if (fGameState == GAME_PLAYING) {
            currentFrame = getTime(); // call before loop to initialise fps time        
        }
        fBoardGUI.render();
        //bind texture once here.
        fTextures.getPreviewMinos().bind();
        Color.white.bind();
        fNowPreview.render();
        for (int i = 0; i < NUM_PREVIEWS; i++) {
            fPreviews[i].render();
        }
        fHoldPreview.render();
    }

    public void setTextures(TextureHolder th) {
        fTextures = th;
        fBoardGUI = new OpenGLBoardGUI(this, fTextures, fBoard, fTextRenderer);

    }

    public void setTextureAtlas(TextureAtlas ta) {
        fBoardGUI.setTextureAtlas(ta);
        for (int i = 0; i < NUM_PREVIEWS; i++) {
            fPreviews[i].setTextureAtlas(ta);
        }
        fNowPreview.setTextureAtlas(ta);
        fHoldPreview.setTextureAtlas(ta);
    }

    public TetrisBoard getTetrisBoard() {
        return fBoard;
    }

    public OpenGLPreviewGUI getPreviewGUI(int i) {
        return fPreviews[i];

    }

    /*
     * Replay functions - call countdownGame followed by this.
     */
    public void setPieces(String s) {
        loadPieces = s;
        randomizer = TetrisGame.LOAD_RANDOMIZER;
        initRandomizer();
    }

    public void setPieceAlignment(int alignment) {
        pieceAlignment = alignment;
    }

    /**
     * Start a Tetris game if one is not already playing.
     */
    public void countDownGame() {
        previousMove = null;
        fReplayMaker.reset();
        fTextRenderer.reset();
        fMenu = false;
        fBoard.resetBoard();        
        randomizer = options.getSetting(OptionsMenu.GET_RANDOMIZER);
        pieceAlignment = options.getSetting(OptionsMenu.GET_ALIGNMENT);
        canUndo = (options.getSetting(OptionsMenu.GET_UNDO) == 1);
        initRandomizer();
        fTotalLines = 0;
        fGameState = GAME_COUNTDOWN;
        fCurrPiece = null;
        fKeys = 0;
        fPieces = 0;
        fHoldPiece = null;
        gameStartFrame = getTime();
        fBoardGUI.start();
        fHoldPreview.setPiece(TetrisPiece.NO_PIECE);
        fHoldPreview.setMonochrome(false);
        for (int i = 0; i < 3; i++) {
            countdowns[i] = false;
        }
        for (int i = 0; i < 7; i++) {
            pieceStats[i] = 0;
        }

        printReplay = options.getSetting(OptionsMenu.GET_SAVE) == 1;

        if (options.getSetting(OptionsMenu.GET_TEXTURE_ROTATE_BOARD) == 1) {
            fBoardGUI.setFlipBoard(true);
        } else {
            fBoardGUI.setFlipBoard(false);
        }

        if (options.getSetting(OptionsMenu.GET_TEXTURE_ROTATE_ACTIVE) == 1) {
            fBoardGUI.setFlipActive(true);
        } else {
            fBoardGUI.setFlipActive(false);
        }
        boolean flipPreviews
                = (options.getSetting(OptionsMenu.GET_TEXTURE_ROTATE_PREVIEW) == 1);
        for (int i = 0; i < NUM_PREVIEWS; i++) {
            fPreviews[i].setFlipPreview(flipPreviews);
        }
        fHoldPreview.setFlipPreview(flipPreviews);
        fNowPreview.setFlipPreview(flipPreviews);

    }

    public void startGame() {
        fGameState = GAME_PLAYING;
        fTextRenderer.resetKeyStats();
        gameStartFrame = getTime();
    }

    public static String alignmentToString(int i) {
        if (i == 0) {
            return "LEFT";
        } else if (i == 1) {
            return "RIGHT";
        } else if (i == 2) {
            return "CENTER";
        } else {
            return "NO IDEA";
        }
    }

    public static int stringToAlignment(String s) {
        if (null != s) switch (s) {
            case "LEFT":
                return 0;
            case "RIGHT":
                return 1;
            case "CENTER":
                return 2;            
        }
        return 2;
    }

    /**
     * Stop the current game.
     */

    public void stopGame() {
        if (printReplay) {
            long maxTime = options.getSetting(OptionsMenu.GET_MAXTIME) * 1000;
            int fullSave = options.getSetting(OptionsMenu.GET_FULLSAVE);

            if ((maxTime == 0 || getGameTimer() < maxTime)
                    && ((fullSave == 1 && fTotalLines >= 40) || fullSave == 0)) {
                fReplayMaker.setAlignment(alignmentToString(pieceAlignment));
                fReplayMaker.printReplay((fTotalLines >= 40 ? "Win" : "Lose"));
                fReplayMaker.reset();
            }
        }
        fGameState = GAME_FINISHED;
    }

    /**
     * Returns a copy of the current piece.
     *
     * @return A copy of the current piece.
     */
    public TetrisPiece getCurrentPiece() {
        return fCurrPiece;
    }

    /**
     * Sets the current piece.
     *
     * @param currPiece The current piece.
     */
    public void setCurrentPiece(TetrisPiece currPiece) {
        fCurrPiece = currPiece;
    }

    private void shiftPreviewsBack() {
        morePieces.push(fPreviews[NUM_PREVIEWS - 1].getPiece());
        for (int i = NUM_PREVIEWS - 1; i > 0; i--) {
            fPreviews[i].setPiece(fPreviews[i - 1].getPiece());
        }
    }

    public void undo() {        
        if (!(fGameState == GAME_PLAYING)) {
            return;
        }
        fKeys++;
        if (!canUndo) {
            return;
        }
        if (previousMove == null) {
            return;
        }
        
        fBoard.copy(previousMove.fBoard);
        fTotalLines = previousMove.fTotalLines;
        for (int i = 0; i < 7; i++) {
            pieceStats[i] = previousMove.pieceStats[i];
        }
        if (!(fPieces == previousMove.fPieces)) {
            shiftPreviewsBack();
            fPreviews[0].setPiece(fCurrPiece.getType());
            fPieces = previousMove.fPieces;
        }
        fCurrPiece = previousMove.fCurrPiece;
        fHoldPiece = previousMove.fHoldPiece;
        fNowPreview.setPiece(fCurrPiece.getType());
        if (fHoldPiece == null) {
            fHoldPreview.setPiece(TetrisPiece.NO_PIECE);

        } else {
            fHoldPreview.setPiece(fHoldPiece.getType());
        }
        previousMove = null;
        SoundCache.getUndo().playAsSoundEffect((float) 1.0, (float) 1.0, false);
    }

    public int getGameState() {
        return fGameState;
    }

    public void hold() {
        if (!(fGameState == GAME_PLAYING)) {
            return;
        }
        previousMove = new TetrisGameState(this);
        if (fHoldPiece == null) {
            fHoldPiece = fCurrPiece;
            fCurrPiece = null;
            fPieces++;
        } else {
            TetrisPiece temp = fCurrPiece;
            fCurrPiece = fHoldPiece;
            fHoldPiece = temp;
            fNowPreview.setPiece(fCurrPiece.getType());
        }
        fKeys++;
        //fHoldPreview.setMonochrome(true);
        fHoldPreview.setPiece(fHoldPiece.getType());
        SoundCache.getHold().playAsSoundEffect((float) 1.0, (float) 1.0, false);
        GameLogic();

    }

    public void releaseKey(int key) {
        fBoard.releaseKey(key);
    }

    public void pressReverse() {
        this.reverseDown++;
        if (fGameState == GAME_COUNTDOWN || fGameState == GAME_PLAYING) {
            fKeys++;
        }       
    }
    public void releaseReverse() {
        this.reverseDown--;
        if (this.reverseDown < 0) {
            this.reverseDown = 0;
        }
    }
    
    public void typer(int column, int rotation, int key) {
        if (!(fGameState == GAME_PLAYING)) {
            return;
        }
        previousMove = new TetrisGameState(this);
        fHoldPreview.setMonochrome(false);
        SoundCache.getColumn(column).playAsSoundEffect((float) 1.0, (float) 1.0, false);
        //rotate then move 
        fPieces++;
        fKeys++;
        if (this.reverseDown > 0) {
            rotation += 2;
            rotation %= 4;
        }
        move(rotation);
        int currColumn = fCurrPiece.column();
        fCurrPiece.setKeyPressed(key);
        while (currColumn != column) {
            if (currColumn < column) {
                if (!move(TetrisPiece.RIGHT)) {
                    break;
                }
            } else {
                if (!move(TetrisPiece.LEFT)) {
                    break;
                }
            }
            currColumn = fCurrPiece.column();
        }
        move(TetrisPiece.FALL);

        GameLogic();
    }

    /**
     * If a move is possible then the move call is passed through to the current
     * piece.
     *
     * @param direction The direction to move the current piece. These are the
     * constants from the TetrisPiece class.
     * @return true if the piece moved else false.
     */
    public boolean move(int direction) {
        boolean result = false;
        if (fCurrPiece != null) {

            if (direction == TetrisPiece.DOWN || direction == TetrisPiece.FALL) {
                // If it won't go any further then drop it there.
                if (fCurrPiece.move(direction) == false) {
                    pieceStats[fCurrPiece.getType()]++;
                    fCurrPiece = null;
                    GameLogic();
                } else {
                    result = true;
                }
            } else {
                result = fCurrPiece.move(direction);
            }
        }

        return result;
    }

    /**
     * Returns the number of completed lines so far in the game.
     *
     * @return Total number of completed lines.
     */
    public int getTotalLines() {
        return fTotalLines;
    }

    /**
     * Sets the number of total lines.
     *
     * @param totalLines The number of total lines.
     */
    public void setTotalLines(int totalLines) {
        fTotalLines = totalLines;
    }

    public void GameLogic() {

        if (fCurrPiece == null) {

            int completeLines = 0;

            // First check for any complete lines.
            for (int rows = fBoard.getRows() - 1; rows >= 0; rows--) {
                boolean same = true;

                for (int cols = 0; cols < fBoard.getColumns(); cols++) {
                    if (fBoard.getPieceAt(cols, rows).pieceType == TetrisBoard.EMPTY_BLOCK) {
                        same = false;
                    }
                }

                if (same) {
                    // Remove the completed row.
                    fBoard.removeRow(rows);

                    // Set it so we check this row again since
                    // the rows above have been moved down.
                    rows++;

                    // Increment values for scoring.
                    completeLines++;

                }
            }

            if (completeLines > 0) {
                // The more lines completed at once the bigger
                // the score increment.

                fTotalLines += completeLines;
                if (fTotalLines >= 40) {
                    winGame();
                }
            }

            fCurrPiece = getRandomPiece(fBoard);
            if (!fBoard.canSpawn()) {
                stopGame();
            }
            if (!fBoard.willFit(fCurrPiece)) {
                stopGame();
            }
        }

    }

    /**
     * Returns a random piece to use in the given board.
     *
     * @param board The board the piece will be in.
     * @return A random piece.
     */
    private TetrisPiece getRandomPiece(TetrisBoard board) {
        if (morePieces == null || morePieces.isEmpty()) {
            fillPieces();
        }

        TetrisPiece result
                = new TetrisPiece(fPreviews[0].getPiece(),
                        board,
                        pieceAlignment);
        Color.white.bind();
        fNowPreview.setPiece(fPreviews[0].getPiece());
        fNowPreview.render();
        for (int i = 0; i < NUM_PREVIEWS - 1; ++i) {
            fPreviews[i].setPiece(fPreviews[i + 1].getPiece());
            fPreviews[i].render();
        }
        fPreviews[NUM_PREVIEWS - 1].setPiece(morePieces.poll());
        fPreviews[NUM_PREVIEWS - 1].render();
        return result;

    }

    private void fillPreviews() {
        for (int i = 0; i < NUM_PREVIEWS; i++) {
            if (morePieces.isEmpty()) {
                fillPieces();
            }
            fPreviews[i].setPiece(morePieces.poll());
        }

    }

    private void initRandomizer() {

        fillPieces();
        fillPreviews();

    }

    private void fillPieces() {
        // now fill previews.
        if (randomizer == PURE_RANDOMIZER) {
            morePieces = Randomizers.fillPureRandomizer();
        } else if (randomizer == BAG_RANDOMIZER) {
            morePieces = Randomizers.fillBagRandomizer();
        } else if (randomizer == HISTORY_RANDOMIZER) {
            morePieces = Randomizers.fillHistoryRandomizer();
        } else if (randomizer == BAG_PLUS_RANDOMIZER) {
            morePieces = Randomizers.fillBagPlusRandomizer();
        } else {
            morePieces = Randomizers.fillLoadRandomizer(loadPieces);
        }
        //now add pieces to replaymaker
        for (int i : morePieces) {
            fReplayMaker.addPiece(i);
        }

    }

    public int getPieces() {
        return fPieces;
    }

    public int getKeys() {
        return fKeys;
    }

    private void winGame() {
        stopGame();
        fGameState = GAME_WIN;
        fBoardGUI.win();
        SoundCache.getGameClear().playAsSoundEffect((float) 1.0, (float) 1.0, false);
        GameLogic();
        if (fTotalLines >= 40 && printReplay) { // this function can be called by replay
            fScores.addScore(currentFrame - gameStartFrame, fPieces, fKeys);
        }

    }

    void checkCountdown() {
        if (fGameState == GAME_COUNTDOWN) {
            currentFrame = getTime();
            int second = (int) ((double) (currentFrame - gameStartFrame) / 1000);
            if (second == 3 || countdowns[second] == false) {

                if (second == 3) {
                    startGame();
                    SoundCache.getCountDown3().playAsSoundEffect((float) 1.0, (float) 1.0, false);
                } else {
                    countdowns[second] = true;
                    SoundCache.getCountDown1().playAsSoundEffect((float) 1.0, (float) 1.0, false);
                }
            }

        }

    }

    public int[] getPieceStats() {
        return pieceStats;
    }

    public ReplayMaker getReplayMaker() {
        return fReplayMaker;
    }

    public void setMenu(boolean b) {
        fMenu = b;
    }

    public boolean mainMenu() {
        return fMenu;
    }

    void setOptions(OptionsMenu fOptions) {
        options = fOptions;
    }

    public void setGameTimer(long time) {
        currentFrame = gameStartFrame + time;
    }

    public OptionsMenu getOptions() {
        return options;
    }

    public TetrisPiece getHoldPiece() {
        return fHoldPiece;
    }
}
