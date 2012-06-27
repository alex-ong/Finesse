
import java.util.ArrayList;
import java.util.Collections;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alex
 */
public class ScoreKeeper {
    
    private ArrayList<Score> scores = new ArrayList<Score>();
    

    public ArrayList<Score> getScores(){
        return scores;
    }
    
    public void addScore(long time, int pieces, int keys) {
        Score s = new Score(time,pieces,keys);
        scores.add(s);
        Collections.sort(scores);
        while (scores.size() > 9){
            scores.remove(scores.size()-1);
        }
    }
    
    
    
    public class Score implements Comparable {
        long time;
        int pieces;
        int keys;
        double TPM;        
        public Score(long t, int p, int k){
            time = t;
            pieces = p;
            keys = k;
            this.TPM = (double)p/((double)t/1000)*60;
        }
        


        public int compareTo(Object o) {
            Score other = (Score)o;
            if (this.time < other.time){
                return -1;
            } else {
                return 1;
            }
        }

    }
    
    
    
    
    
    
    
    
    
}
