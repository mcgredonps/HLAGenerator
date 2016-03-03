
package hlagenerator;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.*;

/**
 *
 * @author DMcG
 */
public class OmdParser extends DefaultHandler
{
    Stack<HLAObject> classStack = new Stack();
    Stack<HLAInteraction> interactionStack = new Stack();
    
    HLAObject currentHLAObject = null;
    HLAInteraction currentHLAInteraction = null;
    HLAFixedRecordData currentFixedRecord = null;
    HLAEnumeratedData currentEnumeratedData = null;
    
    List<HLAObject> hlaClasses = new ArrayList();
    List<HLAInteraction> hlaInteractions = new ArrayList();
    List<HLAFixedRecordData> hlaFixedRecords = new ArrayList();
    List<HLAEnumeratedData> hlaEnumeratedData = new ArrayList();
    
     @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException 
    {
        //System.out.println("Start " + qName);
        
        if(qName.equalsIgnoreCase("objectClass"))
        {
            if(currentHLAObject != null)
                classStack.push(currentHLAObject);
            
            HLAObject newObject = new HLAObject();
            newObject.parent = currentHLAObject;
            currentHLAObject = newObject;
            
            String objectName = attributes.getValue("name");
            currentHLAObject.name = objectName;
            
            String semantics = attributes.getValue("semantics");
            currentHLAObject.semantics = semantics;
            
            String sharing = attributes.getValue("sharing");
            currentHLAObject.sharing = sharing;
            
            String nameNotes = attributes.getValue("nameNotes");
            currentHLAObject.nameNotes = nameNotes;
            
            System.out.println("Created class " + currentHLAObject);
        }
        
        if(qName.equalsIgnoreCase("attribute"))
        {
            HLAAttribute attribute = new HLAAttribute();
            attribute.name = attributes.getValue("name");
            
            attribute.dataType = attributes.getValue("dataType");
            attribute.semantics = attributes.getValue("semantics");
            
            attribute.dimensions = attributes.getValue("dimensions");
            attribute.nameNotes = attributes.getValue("nameNotes");
            attribute.order = attributes.getValue("order");
            attribute.ownership = attributes.getValue("ownership");
            attribute.sharing = attributes.getValue("sharing");
            attribute.transportation = attributes.getValue("transportation");
            attribute.updateCondition = attributes.getValue("updateCondition");
            attribute.updateType = attributes.getValue("updateType");
            
            currentHLAObject.addAttribute(attribute);
        }
        
        if(qName.equalsIgnoreCase("interactionClass"))
        {
            if(currentHLAInteraction != null)
                interactionStack.push(currentHLAInteraction);

            HLAInteraction newInteraction = new HLAInteraction();
            newInteraction.parent = currentHLAInteraction;
            currentHLAInteraction = newInteraction;

            currentHLAInteraction.name = attributes.getValue("name");
            currentHLAInteraction.dimensions = attributes.getValue("dimensions");
            currentHLAInteraction.nameNotes = attributes.getValue("nameNotes");
            currentHLAInteraction.transportation = attributes.getValue("transportation");
            currentHLAInteraction.order = attributes.getValue("order");
            currentHLAInteraction.semantics = attributes.getValue("semantics");
            currentHLAInteraction.sharing = attributes.getValue("sharing");
        }
        
        if(qName.equalsIgnoreCase("parameter"))
        {
            HLAParameter parameter = new HLAParameter();
            
            parameter.name = attributes.getValue("name");
            parameter.dataType = attributes.getValue("dataType");
            parameter.semantics = attributes.getValue("semantics");
            
            currentHLAInteraction.addParameter(parameter);
        }
        
        if(qName.equalsIgnoreCase("fixedRecordData"))
        {
            HLAFixedRecordData newRecord = new HLAFixedRecordData();
            currentFixedRecord = newRecord;
            
            newRecord.name = attributes.getValue("name");
            newRecord.semantics = attributes.getValue("semantics");
            newRecord.encoding = attributes.getValue("encoding");
            
            this.hlaFixedRecords.add(newRecord);
        }
        
        if(qName.equalsIgnoreCase("field"))
        {
            HLAField field = new HLAField();
            
            field.name = attributes.getValue("name");
            field.dataType = attributes.getValue("dataType");
            field.semantics = attributes.getValue("semantics");
            
            currentFixedRecord.fields.add(field);
        }
        
        if(qName.equalsIgnoreCase("enumeratedData"))
        {
            HLAEnumeratedData newEnumeration = new HLAEnumeratedData();
            this.currentEnumeratedData = newEnumeration;
            
            newEnumeration.name = attributes.getValue("name");
            newEnumeration.semantics = attributes.getValue("semantics");
            newEnumeration.representation = attributes.getValue("encoding");
            
            this.hlaEnumeratedData.add(newEnumeration);
        }
        
        if(qName.equalsIgnoreCase("enumerator"))
        {
            HLAEnumerator enumerator = new HLAEnumerator();
            
            enumerator.name = attributes.getValue("name");
            enumerator.values = attributes.getValue("values");
            
            this.currentEnumeratedData.enumValues.add(enumerator);
        }
        
        
    }

     @Override
    public void endElement(String uri, String localName, String qName) 
            throws SAXException 
    {
        if(qName.equals("objectClass"))
        {
            hlaClasses.add(currentHLAObject);
            System.out.println(currentHLAObject.name + " has " + currentHLAObject.attributes.size() + " attributes");
            if(!classStack.empty())
                currentHLAObject = classStack.pop();
            
        }
        
        if(qName.equalsIgnoreCase("InteractionClass"))
        {
            hlaInteractions.add(currentHLAInteraction);
            System.out.println(currentHLAInteraction.name + " has " + currentHLAInteraction.parameters.size() + " parameters");
            if(!interactionStack.empty())
                currentHLAInteraction = interactionStack.pop();
            
        }
        
    }

}
