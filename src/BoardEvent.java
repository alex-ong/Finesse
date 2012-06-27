

import java.awt.*;

/**
 * The BoardEvent class.
 * 
 * @author Scott Clee
 */
public class BoardEvent extends Event
{
    private Object fSource;

    /**
     * Create a BoardEvent.
     * 
     * @param source The source of the event.
     */
    public BoardEvent(Object source)
    {
	    super(null, 0, source);
	    fSource = source;
    }

    /**
     * Returns the source of the event.
     * 
     * @return The source of the event.
     */
    public Object getSource()
    {
	    return fSource;
    }
}
