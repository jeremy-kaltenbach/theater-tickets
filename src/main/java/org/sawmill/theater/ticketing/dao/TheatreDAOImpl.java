/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sawmill.theater.ticketing.dao;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jeremy
 */
public class TheatreDAOImpl implements TheatreDAO {
   
    private Properties prop;
    private InputStream input;
    
    public TheatreDAOImpl() {
        prop = new Properties();
    }

    @Override
    public boolean isDatabaseConnected() {
        boolean isConnected = false;
        
        // Check the properties file to see if the database is created yet
        // If so, then verify that the file path saved points to a valid database
        try {
            input = getClass().getResourceAsStream("/application.properties");
            if (input != null) {
                prop.load(input);
                if (prop.getProperty("database.created").equals("true")) {
                    isConnected = checkDatabaseAndTables(prop.getProperty("database.location"));
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(TheatreDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    Logger.getLogger(TheatreDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
	}
        
        return isConnected;
    }
    
    @Override
    public boolean checkSelectedDatabase(String filePath) {
        boolean isValid = checkDatabaseAndTables(filePath);
        
        if (isValid) {
            try {
                // If the connection to the database is successful, then update the properties
                // file with the path
                InputStream in = getClass().getResourceAsStream("/application.properties");
                Properties props = new Properties();
                props.load(in);
                in.close();

                String outfile = getClass().getResource("/application.properties").getFile();
                FileOutputStream out = new FileOutputStream(outfile);
                props.setProperty("database.created", "true");
                props.setProperty("database.location", filePath);
                props.store(out, null);
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(TheatreDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return isValid;
    }
    
    private boolean checkDatabaseAndTables(String filePath) {
        
        // Test connecting to the database and see if the tables exist
        boolean isDbValid = false;
        String connectionUrl = "jdbc:sqlite:" + filePath;
        
        try {
            Connection conn = DriverManager.getConnection(connectionUrl);
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet showtimeTable = dbm.getTables(null, null, "SHOWTIME", null);
            ResultSet seatingTable = dbm.getTables(null, null, "SHOW_SEATING", null);
            if (showtimeTable.next() && seatingTable.next()) {
                isDbValid = true;
            }
        } catch (SQLException ex) {
            isDbValid = false;
        }
        return isDbValid;
    }
    
    @Override
    public void createDatabase() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
