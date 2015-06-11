
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Alex
 */
public class ScoreKeeper {

    private ArrayList<Score> scores = new ArrayList<>();

    public ArrayList<Score> getScores() {
        return scores;
    }

    public void addScore(long time, int pieces, int keys) {
        Score s = new Score(time, pieces, keys);
        scores.add(s);
        Collections.sort(scores);
        while (scores.size() > 9) {
            scores.remove(scores.size() - 1);
        }
        writeScores();
    }

    public ScoreKeeper() {
        loadScores();
    }

    public void loadScores() {
        File file = new File("highscore");
        try {
            Scanner input = new Scanner(file);

            while (input.hasNext()) {
                String nextLine = input.nextLine();

                String[] splits = nextLine.split("\\s+");
                Score s = new Score(Long.parseLong(splits[0]),
                        Integer.parseInt(splits[1]),
                        Integer.parseInt(splits[2]));
                scores.add(s);

            }
            input.close();
        } catch (Exception e) {
        }
    }

    public void writeScores() {
        File file = new File("highscore");
        String ls = System.getProperty("line.separator");
        try {
            FileWriter input = new FileWriter(file);

            for (Score s : scores) {
                input.append(s.toString() + ls);
            }
            input.close();
        } catch (Exception e) {
        }

    }

    public class Score implements Comparable<Score> {

        long time;
        int pieces;
        int keys;
        double TPM;

        public Score(long t, int p, int k) {
            time = t;
            pieces = p;
            keys = k;
            this.TPM = (double) p / ((double) t / 1000) * 60;
        }

        @Override
        public String toString() {
            return time + " " + pieces + " " + keys;

        }

        @Override
        public int compareTo(Score o) {
            Score other = o;
            if (this.time < other.time) {
                return -1;
            } else {
                return 1;
            }
        }

    }

}
