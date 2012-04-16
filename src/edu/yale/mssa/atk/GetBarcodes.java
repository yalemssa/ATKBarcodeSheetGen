/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.yale.mssa.atk;

import edu.yale.mssa.xml.XmlGen;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class GetBarcodes {
    private long cId;
    private Connection c;
    private Map<String, String> boxes = new HashMap<String, String>();
    private Properties defaultProps;
    
    public GetBarcodes(Long l) throws ClassNotFoundException, SQLException, IOException {
        cId = l;
        loadProps();
        initdb();
        getSeries();
        createXml();
    }
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException{
        GetBarcodes g = new GetBarcodes(1222577L);
    }

    private void initdb() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        c = DriverManager.getConnection(defaultProps.getProperty("db_url"), defaultProps.getProperty("db_user"), defaultProps.getProperty("db_pass"));
    }

    private void getSeries() throws SQLException {
        PreparedStatement ps = c.prepareStatement("SELECT resourceComponentId, hasChild FROM ResourcesComponents WHERE parentResourceComponentId = ?");
        ps.setLong(1, cId);
        ResultSet r = ps.executeQuery();
        while(r.next()){
            getInstances(r.getLong(1));
            if(r.getBoolean(2) == true) getChildren(r.getLong(1));
        }
        ps.close();
    }
    
    private void getChildren(Long l) throws SQLException{
        PreparedStatement ps = c.prepareStatement("SELECT resourceComponentId, hasChild FROM ResourcesComponents WHERE parentResourceComponentId = ?");
        ps.setLong(1, l);
        ResultSet r = ps.executeQuery();
        while(r.next()){
            getInstances(r.getLong(1));
            if(r.getBoolean(2) == true) getChildren(r.getLong(1));
        }
        ps.close();
    }
    
    private void getInstances(Long l) throws SQLException{
        PreparedStatement ps = c.prepareStatement("SELECT barcode, container1NumericIndicator FROM ArchDescriptionInstances WHERE resourceComponentId = ?");
        ps.setLong(1, l);
        ResultSet r = ps.executeQuery();
        while(r.next()){
            if(r.getString(1) != null && ! r.getString(1).equals(""));
                boxes.put(r.getString(1), "Box " + r.getInt(2));
        }
    }

    private void testPrint() {
        for(Map.Entry e : boxes.entrySet()){
            System.out.println(e.getKey());
        }
    }

    private void createXml() throws IOException {
        XmlGen x = new XmlGen(boxes);
    }

    private void loadProps() throws FileNotFoundException, IOException {
        defaultProps = new Properties();
        FileInputStream fis = new FileInputStream("barcodeGen.properties");
        defaultProps.load(fis);
    }
}
