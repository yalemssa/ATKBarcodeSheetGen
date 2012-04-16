/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.yale.mssa.xml;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XmlGen {
    private Map<String, String> barcodes;
    private BufferedOutputStream outp;
    public XmlGen(Map<String, String> barcodes) throws IOException {
        this.barcodes = new HashMap<String, String>(barcodes);
        Document doc = createDocument();
        writeFile(doc);
    }
    
    private Document createDocument() {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement( "barcodes" );
        Element section = root.addElement("section");
        section.addAttribute("title", "ATK barcodes");
        TreeSet<String> keys = new TreeSet<String>(barcodes.keySet());
        
        for (String key : keys) { 
            
            String bnum = barcodes.get(key).toString();
            Element box = section.addElement( "barcode" );
            Element boxNum = box.addElement("description");
            boxNum.addText(bnum);
            Element barcode = box.addElement("msg");
            barcode.addText(key.toString());
            Element code = box.addElement("code39");
            
        }
        return document;
    }

    private void writeFile(Document doc) throws IOException {
        outp = new BufferedOutputStream(new FileOutputStream(new File("atkbarcodes.xml")));
        outp.write(doc.asXML().getBytes());
    }
}
