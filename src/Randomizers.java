
import java.util.LinkedList;
import java.util.Vector;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alex
 */
public class Randomizers {
    public static LinkedList<Integer> fillPureRandomizer() {
        LinkedList<Integer> result = new LinkedList<Integer>();
        for (int i = 0; i < 100; i++) {
            result.add((int)(Math.random() * 7));   
        }
        return result;
    }
    
    public static LinkedList<Integer> fillBagRandomizer() {
        LinkedList<Integer> result = new LinkedList<Integer>();
        Vector<Integer> newBag = new Vector<Integer>();
        //fill bag
        for (int i = 0; i < 7; i++){
            newBag.add(i);
        }
        //grab contents out of bag and onto queue.
        while (newBag.size() > 0) {
            int tmp = (int)(Math.random() * newBag.size());
            
            result.add(newBag.get(tmp));
            newBag.remove(tmp);
        }
        return result;
    }
    
    private static LinkedList<Integer> setLoadRandomizer(String s) {
        return PieceStringParser.parsePieces(s);
    }
    public static LinkedList<Integer> fillLoadRandomizer(String loadPieces) {
        return setLoadRandomizer(loadPieces);
    }
    
    private static int generateNextHistory(int[] array, int rerolls){
        int result = -1;
        boolean inHistory = false;
        for (int i = 0; i <= rerolls; i++) {
            result = (int)(Math.random()*7);
            for (int j = 0; j < 4; j++) {
                if (result == array[i]) {
                    inHistory = true;
                    break;
                }
            }
            if (!inHistory) return result;
        }
        return result;
    }
    
    private static int[] shuffleArray(int[] array, int addition){
        for (int i = 1; i < 4; i++) {
            array[i] = array[i-1];
        }
        array[0] = addition;
        return array;
    }
    
    
    public static LinkedList<Integer> fillHistoryRandomizer() {
        int[] history = new int[]
        {TetrisPiece.Z_PIECE,TetrisPiece.Z_PIECE,TetrisPiece.Z_PIECE,TetrisPiece.Z_PIECE,};
        LinkedList<Integer> result = new LinkedList<Integer>();
        for (int i = 0; i < 200; i++) {//generate 200
            int next = generateNextHistory(history, 2);
            history = shuffleArray(history,next);
            result.add(next);
        }
        return result;
    }
    
    public static LinkedList<Integer> fillBagPlusRandomizer() {
        Integer i = (int)(Math.random()*7);
        LinkedList<Integer> result = fillBagRandomizer();
        result.add(i);
        return result;
    }
}
