/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sawmill.theater.ticketing.dao;

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
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sawmill.theater.ticketing.model.ShowSeating;

/**
 *
 * @author jeremy
 */
public class ShowSeatingDAOImpl implements ShowSeatingDAO {
    
    private Logger logger;
    
    private static final String GET_SHOWSEATS = "SELECT SEAT_ID, SECTION, ROW, SEAT_NUMBER, LAST_NAME, FIRST_NAME FROM SHOW_SEATING WHERE SHOW_ID=?";
    private static final String ADD_SHOWSEAT = "INSERT INTO SHOW_SEATING (SHOW_ID, SECTION, ROW, SEAT_NUMBER, LAST_NAME, FIRST_NAME) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_SHOWSEAT = "UPDATE SHOW_SEATING SET SECTION=?, ROW=?, SEAT_NUMBER=?, LAST_NAME=?, FIRST_NAME=? WHERE SEAT_ID=?";
    private static final String REMOVE_SHOWSEAT = "DELETE FROM SHOW_SEATING WHERE SEAT_ID=?";
    private static final String REMOVE_ALL_SHOW_SHOWSEATS = "DELETE FROM SHOW_SEATING WHERE SHOW_ID=?";
    
    public ShowSeatingDAOImpl() {
        logger = Logger.getLogger("SawmillTheatreLogger");
    }

    @Override
    public List<ShowSeating> getShowtimeSeats(int showId) {
        List<ShowSeating> seats = new ArrayList<>();
        String connectionUrl = getConnectionUrl();
        
        try (Connection conn = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt = conn.prepareStatement(GET_SHOWSEATS);){
            stmt.setInt(1, showId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ShowSeating seat = new ShowSeating();
                    seat.setSeatId(rs.getInt("SEAT_ID"));
                    seat.setShowId(showId);
                    seat.setSection(rs.getInt("SECTION"));
                    seat.setRow(rs.getString("ROW"));
                    seat.setSeatNumber(rs.getInt("SEAT_NUMBER"));
                    seat.setLastName(rs.getString("LAST_NAME"));
                    seat.setFirstName(rs.getString("FIRST_NAME"));
                    seats.add(seat);
                }
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error fetching show seats from the database.", ex);
        }
        
        return seats;
    }

    @Override
    public ShowSeating addShowSeat(ShowSeating seat) {
        String connectionUrl = getConnectionUrl();
        
        try (Connection conn = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt = conn.prepareStatement(ADD_SHOWSEAT);
            Statement stmt2 = conn.createStatement();){
            
            stmt.setInt(1, seat.getShowId());
            stmt.setInt(2, seat.getSection());
            stmt.setString(3, seat.getRow());
            stmt.setInt(4, seat.getSeatNumber());
            stmt.setString(5, seat.getLastName());
            stmt.setString(6, seat.getFirstName());
            stmt.execute();
            
            try (ResultSet generatedKeys = stmt2.executeQuery("SELECT last_insert_rowid()")){
                if (generatedKeys.next()) {
                    int generatedKey = generatedKeys.getInt(1);
                    seat.setSeatId(generatedKey);
                }
            }
            
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error adding show seat to the database.", ex);
        }
        return seat;
    }

    @Override
    public void updateShowSeat(ShowSeating seat) {
        String connectionUrl = getConnectionUrl();
        
        try (Connection conn = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt = conn.prepareStatement(UPDATE_SHOWSEAT);){
            stmt.setInt(1, seat.getSection());
            stmt.setString(2, seat.getRow());
            stmt.setInt(3, seat.getSeatNumber());
            stmt.setString(4, seat.getLastName());
            stmt.setString(5, seat.getFirstName());
            stmt.setInt(6, seat.getSeatId());
            stmt.execute();
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error updating show seat in the database.", ex);
        }
    }

    @Override
    public void deleteShowSeat(int seatId) {
        String connectionUrl = getConnectionUrl();
        
        try (Connection conn = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt = conn.prepareStatement(REMOVE_SHOWSEAT);){
            stmt.setInt(1, seatId);
            stmt.execute();
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error deleting show seat from the database.", ex);
        }
    }

    @Override
    public void deleteAllShowSeats(int showId) {
        String connectionUrl = getConnectionUrl();
        try (Connection conn = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt = conn.prepareStatement(REMOVE_ALL_SHOW_SHOWSEATS);){
            stmt.setInt(1, showId);
            stmt.execute();
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error deleting all show seats from the database.", ex);
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
            logger.log(Level.SEVERE, "Error connecting to the database.", ex);
        } 
        return url;
    }
    
}
