/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sawmill.theater.ticketing.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sawmill.theater.ticketing.model.Showtime;

/**
 *
 * @author jeremy
 */
public class ShowtimeDAOImpl implements ShowtimeDAO {
    
    private static final String GET_ALL_SHOWTIMES = "SELECT SHOW_ID, SHOW_NAME, THEATRE_GROUP, SHOW_DATE, LAST_UPDATED FROM SHOWTIME";
    private static final String ADD_SHOWTIME = "INSERT INTO SHOWTIME (SHOW_NAME, THEATRE_GROUP, SHOW_DATE, LAST_UPDATED) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_SHOWTIME = "UPDATE SHOWTIME SET SHOW_NAME=?, THEATRE_GROUP=?, SHOW_DATE=?, LAST_UPDATED=? WHERE SHOW_ID=?";
    private static final String REMOVE_SHOWTIME = "DELETE FROM SHOWTIME WHERE SHOW_ID=?";
    
    public ShowtimeDAOImpl() { 
    }

    @Override
    public List<Showtime> getShowtimes() {
        List<Showtime> showtimes = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        String connectionUrl = getConnectionUrl();
        
        try {
            Connection conn = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt = conn.prepareStatement(GET_ALL_SHOWTIMES);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                
                Showtime showtime = new Showtime();
                showtime.setShowId(rs.getInt("SHOW_ID"));
                showtime.setShowName(rs.getString("SHOW_NAME"));
                showtime.setTheatreGroup(rs.getString("THEATRE_GROUP"));
                
                calendar.setTimeInMillis(Long.parseLong(rs.getString("SHOW_DATE")));
                showtime.setShowDate(calendar.getTime());
                calendar.setTimeInMillis(Long.parseLong(rs.getString("LAST_UPDATED")));
                showtime.setLastUpdated(calendar.getTime());
                
                showtimes.add(showtime);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ShowtimeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return showtimes;
    }

    @Override
    public Showtime addShowtime(Showtime showtime) {
        String connectionUrl = getConnectionUrl();
        
        try {
            Connection conn = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt = conn.prepareStatement(ADD_SHOWTIME);
            stmt.setString(1, showtime.getShowName());
            stmt.setString(2, showtime.getTheatreGroup());
            stmt.setDate(3, new java.sql.Date(showtime.getShowDate().getTime()));
            stmt.setDate(4, new java.sql.Date(showtime.getLastUpdated().getTime()));
            stmt.execute();
            
            Statement stmt2 = conn.createStatement();
            ResultSet generatedKeys = stmt2.executeQuery("SELECT last_insert_rowid()");
            if (generatedKeys.next()) {
                int generatedKey = generatedKeys.getInt(1);
                showtime.setShowId(generatedKey);
            }
            
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ShowtimeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return showtime;
    }

    @Override
    public void updateShowtime(Showtime showtime) {
        String connectionUrl = getConnectionUrl();
        
        try {
            Connection conn = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt = conn.prepareStatement(UPDATE_SHOWTIME);
            stmt.setString(1, showtime.getShowName());
            stmt.setString(2, showtime.getTheatreGroup());
            stmt.setDate(3, new java.sql.Date(showtime.getShowDate().getTime()));
            stmt.setDate(4, new java.sql.Date(showtime.getLastUpdated().getTime()));
            stmt.setInt(5, showtime.getShowId());
            stmt.execute();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ShowtimeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteShowtime(int showtimeId) {
        String connectionUrl = getConnectionUrl();
        
        try {
            Connection conn = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt = conn.prepareStatement(REMOVE_SHOWTIME);
            stmt.setInt(1, showtimeId);
            stmt.execute();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ShowtimeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String getConnectionUrl() {
        // Get connection URL from database location stored in the properties file
        // This was moved from the constructor in order to get the latest settings
        String url = "";
        InputStream input;
        Properties props = new Properties();
        try {
            input = new FileInputStream(System.getProperty("user.home") + "/SawmillTheatreTickets/config/application.properties");
            props.load(input);
            url =  "jdbc:sqlite:" + props.getProperty("database.location");
            input.close();
        } catch (IOException ex) {
            Logger.getLogger(ShowtimeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return url;
    }
    
}
