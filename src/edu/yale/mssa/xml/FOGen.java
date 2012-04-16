package edu.yale.mssa.xml;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import sun.java2d.SunGraphicsEnvironment.T1Filter;

public class FOGen {
    
    private BufferedOutputStream outp;
    private final StreamSource xml;
    private final StreamSource xslt;
    private final StreamResult result;
    
    FOGen(String xml, String xslt) throws FileNotFoundException, MalformedURLException, TransformerConfigurationException, TransformerException {
        outp = new BufferedOutputStream(new FileOutputStream(new File("testout.xml")));
        this.xml = new StreamSource(new File(xml));
        this.xslt = new StreamSource(new File(xslt));
        result = new StreamResult(outp);
        transform();
    }
    
    private void transform() throws TransformerConfigurationException, TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer(xslt);
        transformer.transform(xml, result);
    }
    
    public static void main(String[] args) throws FileNotFoundException, MalformedURLException, TransformerConfigurationException, TransformerException{
        FOGen f = new FOGen("barcodes.xml", "barcodes2xsl-fo.xsl");
    }


    
}
