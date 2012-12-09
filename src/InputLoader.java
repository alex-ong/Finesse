
import org.lwjgl.input.Keyboard;

/**
 *
 * @author Alex
 */
public class InputLoader {

    public static final int HOLD = 40;
    public static final int UNDO = 41;
    
    public static class columnOrientation {
        int column;
        int orientation;
        public columnOrientation(int c, int o) {
            column = c;
            orientation = o;
        }
        public boolean isNormal(){
            return (column >= 0 
                    && column <= 9 
                    && orientation >= 0
                    && orientation <= 3);
        }
    }
    
    
    public static int[] keyCodes = 
    {
        Keyboard.KEY_1,
        Keyboard.KEY_2, 
        Keyboard.KEY_3, 
        Keyboard.KEY_4, 
        Keyboard.KEY_5, 
        Keyboard.KEY_6, 
        Keyboard.KEY_7, 
        Keyboard.KEY_8,           
        Keyboard.KEY_9,   
        Keyboard.KEY_0,   
        Keyboard.KEY_Q, 
        Keyboard.KEY_W, 
        Keyboard.KEY_E, 
        Keyboard.KEY_R, 
        Keyboard.KEY_T, 
        Keyboard.KEY_Y, 
        Keyboard.KEY_U, 
        Keyboard.KEY_I,           
        Keyboard.KEY_O,   
        Keyboard.KEY_P,   
        Keyboard.KEY_A, 
        Keyboard.KEY_S, 
        Keyboard.KEY_D, 
        Keyboard.KEY_F, 
        Keyboard.KEY_G, 
        Keyboard.KEY_H, 
        Keyboard.KEY_J, 
        Keyboard.KEY_K,           
        Keyboard.KEY_L,   
        Keyboard.KEY_SEMICOLON,   
        Keyboard.KEY_Z, 
        Keyboard.KEY_X, 
        Keyboard.KEY_C, 
        Keyboard.KEY_V, 
        Keyboard.KEY_B, 
        Keyboard.KEY_N, 
        Keyboard.KEY_M, 
        Keyboard.KEY_COMMA,           
        Keyboard.KEY_PERIOD,   
        Keyboard.KEY_SLASH,   
        Keyboard.KEY_SPACE,
        Keyboard.KEY_BACK,
    };
    public static columnOrientation keyToCode(int key) {        
        for (int i = 0; i < 42; i++)
            if (keyCodes[i] == key) {
                if (i < 40) {
                    return new columnOrientation(i%10,i/10);    
                } else {
                    return new columnOrientation(i,i);
                }                
            }
        return new columnOrientation(-1,0);
    }
}
