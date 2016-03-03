
package hlagenerator;

import java.io.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Reads XML file, generates Java classes.
 * 
 * Uses SAX because the documents involved can get pretty big.
 * 
 * @author DMcG
 */
public class HLAGenerator 
{
    public static void main(String[] args) 
    {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        
        try
        {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            OmdParser handler = new OmdParser();
            saxParser.parse(new File("rpr2.0/Annex_B_Files_Informative/RPR_FOM_v2.0_1516-2000.xml"), handler);
            System.out.println("Discovered " + handler.hlaClasses.size() + " classes in file");
            System.out.println("Discovered " + handler.hlaInteractions.size() + " interactions in file");
            SourceCodeWriter writer = new SourceCodeWriter(handler);
            writer.writeAll();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println(e);
        }
        
    }
    
}
