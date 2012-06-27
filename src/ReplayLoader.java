
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
    public BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
    public ReplayLoader(){
        textEncryptor.setPassword("TTCSucks");        
    }
    
    public String getNumPieces() {
        return String.valueOf(pieceString.length());
    }
    public String getAlignment(){
        return alignment;
    }
    public String getTime() {
        DecimalFormat df = new DecimalFormat("0.000");
        return df.format((double)(actions.get(actions.size()-1).time)/1000);
    }
    public String getPieces(){
        return pieceString;
    }
    
    public List<Action> getActions(){
        return actions;        
    }
    public boolean load(String filename){
        File file = new File(filename);
        try{
        Scanner input = new Scanner(file);

        while(input.hasNext()) {
            String nextLine = input.nextLine();
            nextLine = textEncryptor.decrypt(nextLine);
            if (nextLine.equals("game start")) continue;            
            String[] splits = nextLine.split("\\s+");
            if (splits.length == 1) {
                //list of chars
                pieceString = nextLine;
            } else if (splits.length == 2){
                //alignment
                alignment = splits[1];
            } else if (splits.length == 3){
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
