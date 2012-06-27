

import java.awt.*;

/**
 * The GameEvent class.
 * 
 * @author Scott Clee
 */
public class GameEvent extends Event
{
    public static final int START = 0;
    public static final int END   = 1;

    private int fType;

    /**
     * Create a GameEvent.
     * 
     * @param type   The type of event. Either START or END.
     * @param source The source of the Event.
     */
    public GameEvent(int type)
    {
	    super(null, 0, null);
        fType   = type;
    }

    /**
     * Returns the type of the event.
     * 
     * @return The type of the event.
     */
    public int getType()
    {
        return fType;
    }
}
