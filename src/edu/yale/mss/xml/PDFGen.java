package edu.yale.mss.xml;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.MimeConstants;

public class PDFGen {
    private OutputStream outp = null;

    public PDFGen(String fIn, String fOut) throws IOException{
        try{
        FopFactory fopFactory = FopFactory.newInstance();
        outp = new BufferedOutputStream(new FileOutputStream(new File(fOut)));
        Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, outp);
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        Source src = new StreamSource(new File(fIn));
        Result res = new SAXResult(fop.getDefaultHandler());
        transformer.transform(src, res);
        System.out.println("alive");
        } catch (Exception e){
            System.err.println(e);
        }finally {
            outp.close();   
        }
    }
    
    
    public static void main(String[] args) throws IOException{
        PDFGen f = new PDFGen("atk.fo", "atk.pdf");
    }
}
