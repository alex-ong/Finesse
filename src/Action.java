public class Action {

    int key;
    boolean down;
    long time;

    public Action(int k, boolean d, long t) {
        key = k;
        down = d;
        time = t;
    }

    @Override
    public String toString() {
        return Integer.toString(key) + " "
                + Boolean.toString(down) + " "
                + Long.toString(time);
    }
}
