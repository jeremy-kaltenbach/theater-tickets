/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sawmill.theater.ticketing.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    
    private static final String CREATE_SHOWTIME_TABLE = "CREATE TABLE SHOWTIME (SHOW_ID INTEGER PRIMARY KEY, "
            + "SHOW_NAME VARCHAR (255) NOT NULL, THEATRE_GROUP VARCHAR (255) NOT NULL, SHOW_DATE DATETIME NOT NULL, "
            + "LAST_UPDATED DATETIME);";
    private static final String CREATE_SHOW_SEATING_TABLE = "CREATE TABLE SHOW_SEATING (SEAT_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + "SHOW_ID INTEGER NOT NULL, SECTION INT NOT NULL, \"ROW\" VARCHAR (1) NOT NULL, SEAT_NUMBER INTEGER NOT NULL, "
            + "LAST_NAME VARCHAR (255) NOT NULL, FIRST_NAME VARCHAR (255));";
    
    public TheatreDAOImpl() {
        prop = new Properties();
    }

    @Override
    public boolean isDatabaseConnected() {
        boolean isConnected = false;
        
        // Check the properties file to see if the database is created yet
        // If so, then verify that the file path saved points to a valid database
        try {
            input = new FileInputStream(System.getProperty("user.home") + "/SawmillTheatreTickets/config/application.properties");
            if (input != null) {
                prop.load(input);
                isConnected = checkDatabaseAndTables(prop.getProperty("database.location"));
            }
        } catch (FileNotFoundException ex) {
            isConnected = false;
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
            updatePropertiesFile(filePath);
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
    
    private void createPropertiesFile(String dbFilePath) {
        try {
            // Create a new file called application.propertes and store the path of the
            // database's location
            new File(System.getProperty("user.home") + "/SawmillTheatreTickets/config").mkdirs();
            Properties props = new Properties();
            props.setProperty("database.location", dbFilePath);
            File propFile = new File(System.getProperty("user.home") + "/SawmillTheatreTickets/config/application.properties");
            FileOutputStream fileOut = new FileOutputStream(propFile);
            props.store(fileOut, "Sawmill Theatre Database Properties");
            
        } catch (IOException ex) {
            Logger.getLogger(TheatreDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void updatePropertiesFile(String dbFilePath) {
        try {
            // If the connection to the database is successful, then update the properties
            // file with the path
            InputStream input = new FileInputStream(System.getProperty("user.home") + "/SawmillTheatreTickets/config/application.properties");
            Properties props = new Properties();
            props.load(input);
            input.close();
            
            OutputStream output = new FileOutputStream(System.getProperty("user.home") + "/SawmillTheatreTickets/config/application.properties");
            props.setProperty("database.location", dbFilePath);
            props.store(output, "Sawmill Theatre Database Properties");
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(TheatreDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void createDatabase(String filePath) {
        
        // Create the new database file at the specified file path, then add
        // the SHOWTIME and SHOW_SEATING tables
        String connectionUrl = "jdbc:sqlite:" + filePath; 
        try (Connection conn = DriverManager.getConnection(connectionUrl)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("New database has been created.");
                
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute(CREATE_SHOWTIME_TABLE);
                    stmt.execute(CREATE_SHOW_SEATING_TABLE);
                    
                }
                
                System.out.println("Tables added");
                
                // Create the properties file with the database path
                createPropertiesFile(filePath);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TheatreDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
