/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hlagenerator;

import java.util.*;

/**
 *
 * @author DMcG
 */
public class HLAObject 
{
    HLAObject parent = null;
    
    String name;
    String semantics;
    String sharing;
    String nameNotes;
    ArrayList<HLAAttribute> attributes = new ArrayList();
    
    public void addAttribute(HLAAttribute attribute)
    {
        attributes.add(attribute);
    }
    
    
    public String toString()
    {
        String result = name + ", parent=";
        
        if(parent != null)
            result = result + parent.name;
        else
            result = result + "root";
          
        return result;
    }
}
