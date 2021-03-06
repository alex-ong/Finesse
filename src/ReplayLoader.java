
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.jasypt.util.text.BasicTextEncryptor;

/**
 *
 * @author Alex
 */
public class ReplayLoader {

    private String pieceString;

    ArrayList<Action> actions = new ArrayList<Action>();
    private String alignment = "NO IDEA";
    private boolean canUndo = false;
    
    public BasicTextEncryptor textEncryptor = new BasicTextEncryptor();

    public ReplayLoader() {
        textEncryptor.setPassword("TTCSucks");
    }

    public boolean getUndo() {
        return this.canUndo;
    }
    
    public String getNumPieces() {
        return String.valueOf(pieceString.length());
    }

    public String getAlignment() {
        return alignment;
    }

    public long getTime() {        
        return actions.get(actions.size() - 1).time;
    }

    public String getPieces() {
        return pieceString;
    }

    public List<Action> getActions() {
        return actions;
    }

    public boolean load(String filename) {
        File file = new File(filename);
        try {
            Scanner input = new Scanner(file);

            int i = 0;
            int j = InputLoader.keyCodes.length;

            while (input.hasNext()) {
                String nextLine = input.nextLine();
                //keybinds
                if (i < j) {
                    InputLoader.keyCodes[i] = Integer.parseInt(nextLine);
                    i++;
                    continue;
                }

                nextLine = textEncryptor.decrypt(nextLine);

                if (nextLine.equals("game start")) {
                    continue;
                }
                String[] splits = nextLine.split("\\s+");
                if (splits.length == 1) {
                    //list of chars
                    pieceString = nextLine;
                } else if (splits.length == 2) {
                    if ("Alignment".equals(splits[0])) {                        
                        alignment = splits[1];
                    } else if ("AllowUndo".equals(splits[0])) {                        
                        canUndo = Boolean.parseBoolean(splits[1]);
                    }
                } else if (splits.length == 3) {
                    //action
                    Action a = new Action(Integer.parseInt(splits[0]),
                            Boolean.parseBoolean(splits[1]),
                            Long.parseLong(splits[2]));
                    actions.add(a);
                }

            }
            input.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
