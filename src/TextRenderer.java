

import java.awt.Font;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
public class TextRenderer {
	
    TrueTypeFont font;
    TrueTypeFont BigFont;
    TrueTypeFont HugeFont;
    private double timeToPrint = 0;
    private int lines = 0;
    private int keys = 0;
    private int pieces = 0;
    private double TPM = 0;
    private double KPT = 0;
    private final static int StartStatsX = 98;
    private final static int StartStatsY = 217; 
   private final static int KeyGridX = 128;
    private final static int KeyGridY = 439;
    private final static int KeyGridSpace = 20;
    private static final int FlashStartX = 121;
    private static final int FlashStartY = 36;
    private static final int numrows = 20;
    private static final int blocksize = 20;
    private static final int lineMeterX = 322;
    private static final int lineMeterY = 436;
    private static final int lineMeterWidth = 3;    
    private final static char[] defaultKeyGrid = {'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L',';'};
    private final static int KeyboardX = 444;
    private final static int KeyboardY = 55;
    private final static int KeyboardSize = 22;
    private final static int KeyboardRow = 13;
    private final static int startRecordTimeX = 470;
    private final static int startRecordPiecesX = 540;
    private final static int startRecordKeysX = 613;
    private final static int startRecordTpmX = 660;
    private final static int startRecordY = 189;
    private final static int recordRowHeight = 15;
    private final static int pieceStatX = 450;
    private final static int pieceStatY = 424;
    private final static int pieceStatSize = 40;
    
    private ArrayList<Character>[] keysPressed;
    private Color darkPink = new Color(251,96,127);
    private Color turquoise = new Color(0,128,255);
    private TextureHolder th = null;
    private int gameState = TetrisGame.GAME_FINISHED;
    
    private int[][] keyFrequency = new int[4][10];
    private boolean[][] keyboardGUI = new boolean[4][10];
    
    private int spaceFrequency = 0;       
    private boolean spaceDown = false;
    
    private int backSpaceFrequency = 0;
    private boolean backSpaceDown = false;
    
    private int shiftFrequency = 0;
    private int shiftDown = 0; //number of shiftKeys held down...
    
    private ArrayList<ScoreKeeper.Score> scores = null;
    private int[] pieceStats = null;;
    
    @SuppressWarnings("unchecked")
    public void reset(){        
        keysPressed = new ArrayList[10];
        
        for (int i = 0; i < 10; i++){
            keysPressed[i] = new ArrayList<Character>();
        }
        
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 10; j++) {
                keyboardGUI[i][j] = false;
            }
        }
        spaceDown = false;
        backSpaceDown = false;
        shiftDown = 0;
    }
    
    @SuppressWarnings("unchecked")
    public void init(TextureHolder th) {
	// load font from a .ttf file
        this.th = th;
	try {
		InputStream inputStream	= ResourceLoader.getResourceAsStream("dejavumonosansbold.ttf");
		Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
		awtFont2 = awtFont2.deriveFont(12f); // set font size  
                
		font = new TrueTypeFont(awtFont2, true);                
                awtFont2 = awtFont2.deriveFont(20f);
                BigFont = new TrueTypeFont(awtFont2, true);
                awtFont2 = awtFont2.deriveFont(40f);
                HugeFont = new TrueTypeFont(awtFont2, true);
	} catch (Exception e) {
		e.printStackTrace();
	}	
        keysPressed = new ArrayList[10];
        
        for (int i = 0; i < 10; i++){
            keysPressed[i] = new ArrayList<Character>();
        }
        
    }
    
    public void renderCountdown(){   

       DecimalFormat df = new DecimalFormat("0.000");
       if ((3 - timeToPrint) > 0 && (3 - timeToPrint <= 3)){
            int advance = BigFont.getWidth(df.format(3 - timeToPrint));
            int width = KeyGridSpace * 10;
            int theX = KeyGridX + (width - advance)/2 + 3;
            BigFont.drawString(theX, 200, 
                    df.format(3 - timeToPrint), Color.white);   
       }
    }     
    
    public void setScoreList(ArrayList<ScoreKeeper.Score> list){
        scores = list;
    }    
    
    public void backSpaceDown() {
        if (gameState == TetrisGame.GAME_PLAYING) backSpaceFrequency++;
        backSpaceDown = true;
    }
    
    public void backSpaceUp() {
        backSpaceDown = false;
    }
    
    public void shiftDown() {
        if (gameState == TetrisGame.GAME_PLAYING) shiftFrequency++;
        shiftDown++;
    }
    
    public void shiftUp() {
        shiftDown--;
    }
    
    
    public void spaceDown(){
        if (gameState == TetrisGame.GAME_PLAYING) spaceFrequency++;
        spaceDown = true;
    }
    
    public void spaceUp(){
        spaceDown = false;        
    }
    
    
    
    public void keyDown(int column, int row, char key){
        keysPressed[column].add(key);
        if (gameState == TetrisGame.GAME_PLAYING) keyFrequency[row][column]++;
        keyboardGUI[row][column] = true;
    }
    
    public void keyUp(int column, int row, char key){
        keysPressed[column].remove((Object)(key));
        keyboardGUI[row][column] = false;

    }
    
    public void setTime(long time){
        timeToPrint = (double)time/1000;
    }
    
    public void render() {
            if (gameState == TetrisGame.GAME_COUNTDOWN){
                renderCountdown();
                timeToPrint = 0;
            }
                renderTime();
                renderPiecesUsed();
                renderLines();
                renderTPM();
                renderKeys();
                renderKPT();
                renderScores();
                renderFlash();
                renderKeyGrid();
                renderSoftKeyboard();
                renderPiecesStats();
                
                
    }
    
    
    private void renderSoftKeyboard(){
        if (gameState == TetrisGame.GAME_COUNTDOWN) return;
        if (gameState == TetrisGame.GAME_PLAYING) renderCurrentKeys();
        else renderKeyStats();        
        
    }
    private Color statsColor() {
        if (gameState == TetrisGame.GAME_WIN){
            return turquoise;
        }
        return Color.white;
    }
    private Color statsColor2() {
        if (gameState == TetrisGame.GAME_WIN){
            return Color.cyan;
        }
        return Color.white;
    }
    
    private void renderTime(){     

       DecimalFormat df = new DecimalFormat("0.000");
       int advance = font.getWidth(df.format(timeToPrint));
       font.drawString(StartStatsX - advance, StartStatsY, 
                df.format(timeToPrint), statsColor2());   
    }
    
    private void renderPiecesUsed() {
        int advance = font.getWidth(Integer.toString(pieces));
        font.drawString(StartStatsX - advance, StartStatsY + 40, 
                Integer.toString(pieces), statsColor());   
    }
    
    private void renderLines() {
        int advance = font.getWidth(Integer.toString(lines));
        font.drawString(StartStatsX - advance, StartStatsY + 80, 
                Integer.toString(lines), statsColor());   
        
        advance = HugeFont.getWidth(Integer.toString(40 - lines));
        HugeFont.drawString(StartStatsX - advance, StartStatsY - 80,
                Integer.toString(40 - lines), lineColor());
        

        if (lines <= 40) {
            Color.red.bind();       

            GL11.glDisable(GL11.GL_TEXTURE_2D);

          

            GL11.glRecti(lineMeterX, lineMeterY - 10*(40-lines),
                          lineMeterX+lineMeterWidth,lineMeterY);
                    

        }
    }
    
    private Color lineColor(){
        if (lines < 35) {
            return Color.white;
        } else {
            return Color.red;
        }
    }
    /*private final static int startRecordTimeX = 417;
    private final static int startRecordPiecesX = 536;
    private final static int startRecordKeysX = 610;
    private final static int startRecordTpmX = 668;
    private final static int startRecordY = 187;
    private final static int recordRowHeight = 15;    
    * 
    */
    private void renderScores(){
       for (int i = 0; i < 9 && i < scores.size(); i++){
           ScoreKeeper.Score s = scores.get(i);
           DecimalFormat df = new DecimalFormat("0.000");       
           
           font.drawString(startRecordTimeX, startRecordY + recordRowHeight * i,
                           df.format((double)s.time/1000), Color.white);
           font.drawString(startRecordPiecesX, startRecordY + recordRowHeight * i,
                           Integer.toString(s.pieces), Color.white);
           font.drawString(startRecordKeysX, startRecordY + recordRowHeight * i,
                           Integer.toString(s.keys), Color.white);
           font.drawString(startRecordTpmX, startRecordY + recordRowHeight * i,
                           df.format(s.TPM), Color.white);
       }
    }    
    
    
    
    private void renderTPM() {
        DecimalFormat df = new DecimalFormat("0.00");
        double TPM = (double)pieces/timeToPrint*60;
        if (timeToPrint == 0) TPM = 0;
       int advance = font.getWidth(df.format(TPM));
        font.drawString(StartStatsX - advance, StartStatsY + 120, 
                df.format(TPM), statsColor());     
    }
    
    //num keys pressed
    private void renderKeys() {
        int advance = font.getWidth(Integer.toString(keys));
        font.drawString(StartStatsX - advance, StartStatsY + 160, 
                Integer.toString(keys), statsColor());  
    }


    private void renderKPT() {
        DecimalFormat df = new DecimalFormat("0.00");
        double KPT = (double)keys/pieces;
        if (pieces == 0) KPT = 0;
       int advance = font.getWidth(df.format(KPT));
        font.drawString(StartStatsX - advance, StartStatsY + 200, 
                df.format(KPT), Color.white);    
    }


    public void renderFlash() {
        for (int i = 0; i < 10; i++){
            if (!keysPressed[i].isEmpty()) {
                renderFlashColumn(FlashStartX+blocksize*i,FlashStartY);  
            }
            
        }
    }
    
    private void renderFlashColumn(int x, int y) {        
        Color.white.bind();
        Texture texture = th.getFlash();
        texture.bind();         

        
        GL11.glBegin(GL11.GL_QUADS);
                GL11.glTexCoord2f(0,0);
                GL11.glVertex2f(x,y);
                GL11.glTexCoord2f(1,0);
                GL11.glVertex2f(x+blocksize,y);
                GL11.glTexCoord2f(1,1);
                GL11.glVertex2f(x+blocksize,y+numrows*blocksize);
                GL11.glTexCoord2f(0,1);
                GL11.glVertex2f(x,y+numrows*blocksize);
        GL11.glEnd();

        
        
    }
    private void renderKeyGrid() {
        for (int i = 0; i < 10; i++){
            if (!keysPressed[i].isEmpty()) {
                font.drawString(KeyGridX + i*KeyGridSpace, KeyGridY, 
                                Character.toString(keysPressed[i].get(keysPressed[i].size()-1)), darkPink);    
            } else {
                font.drawString(KeyGridX + i*KeyGridSpace, KeyGridY, 
                                Character.toString(defaultKeyGrid[i]), Color.white);    
            }
            
        }
        
    }
   
    public void resetKeyStats(){
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++){
                keyFrequency[i][j] = 0;
            }
        }
        
        spaceFrequency = 0;
        backSpaceFrequency = 0;
        shiftFrequency = 0;
        
    }
    
    public void setPieces(int pieces) {
        this.pieces = pieces;
    }

    public void setLines(int lines) {
        this.lines = lines;
    }

    public void setKeys(int keys) {
        this.keys = keys;
    }

    public void setGameState(int gs) {
        gameState = gs;
    }

    public void start() {
        gameState = TetrisGame.GAME_PLAYING;
    }

    private void renderCurrentKeys() {
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 10; j++){
                if (keyboardGUI[i][j]){
                    renderKey(j,i, Color.red);
                }
            }
        }
        if (spaceDown) renderHold(Color.red);
        if (backSpaceDown) renderUndo(Color.red);
        if (shiftDown > 0) renderReverse(Color.red);
    }
    
    private void renderUndo(Color c) {
        c.bind();       
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        int topLeftX = 664;
        int topLeftY = 55; //magic!
        int bottomRightX = 702;
        int bottomRightY = 76;
        GL11.glRecti(topLeftX,topLeftY,bottomRightX,bottomRightY);    
    }
    
    private void renderReverse(Color c) {
        c.bind();       
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        int topLeftX = 444;
        int topLeftY = 121; //magic!
        int bottomRightX = 482;
        int bottomRightY = 142;
        GL11.glRecti(topLeftX,topLeftY,bottomRightX,bottomRightY);    
    }
    
    private void renderHold(Color c){
        c.bind();       
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        int topLeftX = 514;
        int topLeftY = 143; //magic!
        int bottomRightX = 647;
        int bottomRightY = 160;
        GL11.glRecti(topLeftX,topLeftY,bottomRightX,bottomRightY);    
    }
    
    private void renderKey(int x, int y, Color c){
        c.bind();       

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        int topLeftX = KeyboardX + y*KeyboardRow + x*KeyboardSize;
        int topLeftY = KeyboardY + y*KeyboardSize;
        int bottomRightX = topLeftX + KeyboardSize - 1;
        int bottomRightY = topLeftY + KeyboardSize - 1;
        GL11.glRecti(topLeftX,topLeftY,bottomRightX,bottomRightY);
    }
    
    private void renderKeyStats() {
        int highestAmount = 0;
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 10; j++){
                if (keyFrequency[i][j] > highestAmount){
                    highestAmount = keyFrequency[i][j];
                }
            }
        }

        double shade = (double)255/highestAmount;
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 10; j++){                 
                    int amount = (int)(shade*keyFrequency[i][j]);
                    
                    renderKey(j,i,  new Color(amount,0,0));
            }
        }
        
        
        int amount = (int)(shade*spaceFrequency);        
        renderHold(new Color(amount, 0, 0));
        
        amount = (int)(shade*backSpaceFrequency);
        renderUndo(new Color(amount, 0, 0));
        
        amount = (int)(shade*shiftFrequency);
        renderReverse(new Color(amount, 0, 0));
        
    }

    void setPieceStats(int[] pieceStats) {
        this.pieceStats = pieceStats;
    }

    private void renderPiecesStats() {
            Color.white.bind();
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            for (int i = 0; i < 7; i++){
                int topLeftX = pieceStatX +pieceStatSize *i;
                int topLeftY = pieceStatY - pieceStats[i]*2;
                int bottomRightX = topLeftX + 10;
                int bottomRightY = pieceStatY; 
                GL11.glRecti(topLeftX,topLeftY,bottomRightX,bottomRightY); 
            }     

            
    }
           



}
