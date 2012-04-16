package edu.yale.mssa.xml;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import javax.xml.transform.TransformerFactory;

public class FOGen {
    
    private BufferedOutputStream outp;
   
    
    FOGen(String xml, String xslt) throws FileNotFoundException, MalformedURLException {

        outp = new BufferedOutputStream(new FileOutputStream(new File("testout.xml")));
        
        TransformerFactory tf = TransformerFactory.newInstance();
        System.out.println(tf.getClass());


    }
    
    public static void main(String[] args) throws FileNotFoundException, MalformedURLException{
        FOGen f = new FOGen("barcodes.xml", "barcodes2xsl-fo.xsl");
    }
    
}
