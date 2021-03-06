
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import org.jasypt.util.text.BasicTextEncryptor;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Alex
 */
public class ReplayMaker {

    private volatile int[] pieces = new int[400];
    private volatile int numPieces = 0;
    private volatile String alignment;
    private volatile boolean undoAllowed;
    private volatile ArrayList<Action> actions = new ArrayList<Action>();
    private BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
    
    
    public String getAlignment() {
        return alignment;
    }

    public ReplayMaker() {
        textEncryptor.setPassword("TTCSucks");
    }

    public void setUndoAllowed(boolean result) {
        this.undoAllowed = result;
    }
    
    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    @SuppressWarnings("unchecked")
    public ReplayMaker clone() {
        ReplayMaker result = new ReplayMaker();
        result.numPieces = this.numPieces;
        //copy array
        for (int i = 0; i < numPieces; i++) {
            result.pieces[i] = this.pieces[i];
        }

        result.actions = (ArrayList<Action>) this.actions.clone();
        result.undoAllowed = this.undoAllowed;
        result.alignment = this.alignment;
        
        return result;
    }

    public void reset() {
        numPieces = 0;
        actions.clear();
    }

    public void outputData(BufferedWriter f) throws IOException {
        for (int i : InputLoader.keyCodes) {
            f.append(Integer.toString(i));
            f.newLine();
        }
        f.append(textEncryptor.encrypt("game start"));
        f.newLine();
        f.append(textEncryptor.encrypt("Alignment " + alignment));
        f.newLine();
        f.append(textEncryptor.encrypt("AllowUndo " + Boolean.toString(undoAllowed)));
        f.newLine();
        String s = "";
        for (int i = 0; i < numPieces; i++) {
            s += PieceStringParser.parsePiece(pieces[i]);
        }
        //f.append(s);
        f.append(textEncryptor.encrypt(s));
        f.newLine();
        outputActions(f);
    }

    public class ReplayWritingThread extends Thread {

        ReplayMaker rm;
        String win;

        @Override
        public void run() {
            rm.printReplay2(win);
        }

        public ReplayWritingThread(ReplayMaker rm, String win) {
            this.rm = rm;
            this.win = win;
        }
    }

    private void printReplay2(String win) {

        if (actions.isEmpty()) {
            return;
        }
        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat formatter
                = new SimpleDateFormat("dd_MM_yyyy-HH_mm_SS");
        String dateNow = formatter.format(currentDate.getTime());

        String filename = System.getProperty("user.dir") + "/rep/"
                + win + "_" + actions.get(actions.size() - 1).time + "_"
                + dateNow + ".rep";
        try {
            BufferedWriter bw = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(filename), "UTF-8"));
            outputData(bw);
            bw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void printReplay(String win) {
        new ReplayWritingThread(this.clone(), win).start();
    }

    public void printActions() {
        for (Action a : actions) {
            System.out.println(a);
        }

    }

    public void addPiece(int piece) {
        pieces[numPieces] = piece;
        numPieces++;
    }

    public void addAction(int key, boolean down, long time) {
        actions.add(new Action(key, down, time));
    }

    private void outputActions(BufferedWriter f) throws IOException {

        for (Action a : actions) {
            //f.append(a.toString());
            f.append(textEncryptor.encrypt(a.toString()));
            f.newLine();
        }
    }

}
