
package hlagenerator;

import java.util.*;

/**
 *
 * @author DMcG
 */
public class HLAInteraction 
{
    String name;
    String dimensions;
    String nameNotes;
    String transportation;
    String order;
    String semantics;
    String sharing;
    
    HLAInteraction parent = null;
    
    ArrayList parameters = new ArrayList();

    public void addParameter(HLAParameter parameter)
    {
        parameters.add(parameter);
    }
}
