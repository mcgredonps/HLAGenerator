
package hlagenerator;

import java.io.*;
import java.util.*;

/**
 *
 * @author DMcG
 */
public class SourceCodeWriter 
{
    OmdParser parseResults;
    
    public SourceCodeWriter(OmdParser parseResults)
    {
      this.parseResults = parseResults;
    }

public void writeAll()
{
   this.writeHLAClasses();
   this.writeHLAInteractions();
   this.writeHLAFixedRecords();
   this.writeHLAEnumerations();
}

public void writeHLAClasses()
{
    Iterator<HLAObject> it = parseResults.hlaClasses.iterator();
    while(it.hasNext())
    {
        HLAObject anObject = it.next();
        this.writeClass(anObject);
    }
}

public void writeHLAInteractions()
{
    Iterator<HLAInteraction> it = parseResults.hlaInteractions.iterator();
    while(it.hasNext())
    {
        HLAInteraction anInteraction = it.next();
        this.writeInteraction(anInteraction);
    }
}

public void writeHLAFixedRecords()
{
    Iterator<HLAFixedRecordData> it = parseResults.hlaFixedRecords.iterator();
    while(it.hasNext())
    {
        HLAFixedRecordData aRecord = it.next();
        this.writeFixedRecord(aRecord);
    }   
}

public void writeHLAEnumerations()
{
   Iterator<HLAEnumeratedData> it = parseResults.hlaEnumeratedData.iterator();
    while(it.hasNext())
    {
        HLAEnumeratedData aEnum = it.next();
        this.writeEnumerated(aEnum);
    } 
}

public void writeEnumerated(HLAEnumeratedData d)
{
    try
    {
        String fullPath = "src/main/java/edu/nps/moves/rpr/" + d.name + ".java";
        File outputFile = new File(fullPath);
        outputFile.getParentFile().mkdirs();
        outputFile.createNewFile();
        PrintWriter pw = new PrintWriter(outputFile);

        pw.println("package edu.nps.moves.rpr;");
        pw.println();
        pw.println("/**");
        pw.println(" * " + d.semantics);
        pw.println(" */");
        pw.println();
        pw.println("public enum " + d.name);
        pw.println("{");

        Iterator it = d.enumValues.iterator();
        while(it.hasNext())
        {
            HLAEnumerator e = (HLAEnumerator)it.next();

            pw.print("    " + this.fixName(e.name) + "( " + e.values + " )");
            if(it.hasNext())
                pw.println(",");
            else
                pw.println(";");
        }

        pw.println();
        pw.println("  public final int val;");

        pw.println();

        pw.println("  " + d.name + "(int val)");
        pw.println("  {");
        pw.println("      this.val = val;");
        pw.println("  }");

        pw.println();

        pw.println("  public int val() { return val; }");
        pw.println();
        pw.println("}");
        pw.flush();
        pw.close();
    }
    catch(Exception e)
    {
        e.printStackTrace();
        System.out.println(e);
    }
    
}



public void writeFixedRecord(HLAFixedRecordData record)
{
    try
    {
         String fullPath = "src/main/java/edu/nps/moves/rpr/" + record.name + ".java";
         File outputFile = new File(fullPath);
         outputFile.getParentFile().mkdirs();
         outputFile.createNewFile();
         PrintWriter pw = new PrintWriter(outputFile);
         
         pw.println("package edu.nps.moves.rpr;");
         pw.println();
         pw.println("/**");
         pw.println(" * " + record.semantics);
         pw.println(" */");
         pw.println();
         pw.println("public class " + record.name);
         pw.println("{");
         
         Iterator<HLAField> it = record.fields.iterator();
         while(it.hasNext())
         {
             HLAField field = it.next();
             pw.println("    /** " + field.semantics + " */");
             pw.println("    " + field.dataType + " " + field.name + ";");
             pw.println();
             
         }
         
         pw.println("}");
         
         pw.flush();
         pw.close();
    }
    catch(Exception e)
    {
        e.printStackTrace();
        System.out.println(e);
    }
    
}
public void writeClass(HLAObject anObject)
{
    try
    {
        String fullPath = "src/main/java/edu/nps/moves/rpr/" + anObject.name + ".java";
         File outputFile = new File(fullPath);
         outputFile.getParentFile().mkdirs();
         outputFile.createNewFile();
         PrintWriter pw = new PrintWriter(outputFile);
         
         pw.println("package edu.nps.moves.rpr;");
         pw.println();
         pw.println("/**");
         pw.println(" * " + anObject.semantics);
         pw.println(" */");
         pw.println();
         
         pw.print("public class " + anObject.name);
         if(anObject.parent != null)
         {
             pw.print(" extends " + anObject.parent.name);
         }
         pw.println();
         pw.println("{");
         
         Iterator<HLAAttribute> it = anObject.attributes.iterator();
         while(it.hasNext())
         {
             HLAAttribute attr = it.next();
             pw.println("    /** " + attr.semantics + " */");
             pw.println("    " + attr.dataType + " " + attr.name + ";");
             pw.println();
             
         }
         pw.println("}");
         
         
         
         pw.close();
    }
    catch(Exception e)
    {
        e.printStackTrace();
        System.out.println(e);
    }
    
}

public void writeInteraction(HLAInteraction anObject)
{
    try
    {
        String fullPath = "src/main/java/edu/nps/moves/rpr/" + anObject.name + "Interaction.java";
         File outputFile = new File(fullPath);
         outputFile.getParentFile().mkdirs();
         outputFile.createNewFile();
         PrintWriter pw = new PrintWriter(outputFile);
         
         pw.println("package edu.nps.moves.rpr;");
         pw.println();
         pw.println("/**");
         pw.println(" * " + anObject.semantics);
         pw.println(" */");
         pw.println();
         
         pw.print("public class " + anObject.name + "Interaction");
         if(anObject.parent != null)
         {
             pw.print(" extends " + anObject.parent.name + "Interaction");
         }
         pw.println();
         pw.println("{");
         
         Iterator<HLAParameter> it = anObject.parameters.iterator();
         while(it.hasNext())
         {
             HLAParameter param = it.next();
             pw.println("    /** " + param.semantics + " */");
             pw.println("    " + param.dataType + " " + param.name + ";");
             pw.println();
             
         }
         pw.println("}");
         
         
         
         pw.close();
    }
    catch(Exception e)
    {
        e.printStackTrace();
        System.out.println(e);
    }
    
}

public String fixName(String name)
{
    return name.replace("-", "_");
}

}