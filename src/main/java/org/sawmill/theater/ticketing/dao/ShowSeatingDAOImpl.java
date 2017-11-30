/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sawmill.theater.ticketing.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sawmill.theater.ticketing.model.ShowSeating;

/**
 *
 * @author jeremy
 */
public class ShowSeatingDAOImpl implements ShowSeatingDAO {
    
    private static String connectionUrl = "jdbc:sqlite:SawmillTheatre";
    
    private static final String GET_SHOWSEATS = "SELECT SEAT_ID, SECTION, ROW, SEAT_NUMBER, LAST_NAME, FIRST_NAME FROM SHOW_SEATING WHERE SHOW_ID=?";
    private static final String ADD_SHOWSEAT = "INSERT INTO SHOW_SEATING (SHOW_ID, SECTION, ROW, SEAT_NUMBER, LAST_NAME, FIRST_NAME) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_SHOWSEAT = "UPDATE SHOW_SEATING SET SECTION=?, ROW=?, SEAT_NUMBER=?, LAST_NAME=?, FIRST_NAME=? WHERE SEAT_ID=?";
    private static final String REMOVE_SHOWSEAT = "DELETE FROM SHOW_SEATING WHERE SEAT_ID=?";
    private static final String REMOVE_ALL_SHOW_SHOWSEATS = "DELETE FROM SHOW_SEATING WHERE SHOW_ID=?";
    
    public ShowSeatingDAOImpl() {
    }

    @Override
    public List<ShowSeating> getShowtimeSeats(int showId) {
        List<ShowSeating> seats = new ArrayList<>();
        
        try {
            Connection conn = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt = conn.prepareStatement(GET_SHOWSEATS);
            stmt.setInt(1, showId);
            ResultSet rs = stmt.executeQuery();
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
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ShowtimeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return seats;
    }

    @Override
    public void addShowSeat(ShowSeating seat) {
        try {
            Connection conn = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt = conn.prepareStatement(ADD_SHOWSEAT);
            stmt.setInt(1, seat.getSection());
            stmt.setString(2, seat.getRow());
            stmt.setInt(3, seat.getSeatNumber());
            stmt.setString(4, seat.getLastName());
            stmt.setString(5, seat.getFirstName());
            stmt.setInt(6, seat.getSeatId());
            stmt.execute();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ShowtimeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateShowSeat(ShowSeating seat) {
        try {
            Connection conn = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt = conn.prepareStatement(UPDATE_SHOWSEAT);
            stmt.setInt(1, seat.getShowId());
            stmt.setInt(2, seat.getSection());
            stmt.setString(3, seat.getRow());
            stmt.setInt(4, seat.getSeatNumber());
            stmt.setString(5, seat.getLastName());
            stmt.setString(6, seat.getFirstName());
            stmt.execute();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ShowtimeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteShowSeat(int seatId) {
        try {
            Connection conn = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt = conn.prepareStatement(REMOVE_SHOWSEAT);
            stmt.setInt(1, seatId);
            stmt.execute();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ShowtimeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteAllShowSeats(int showId) {
        try {
            Connection conn = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt = conn.prepareStatement(REMOVE_ALL_SHOW_SHOWSEATS);
            stmt.setInt(1, showId);
            stmt.execute();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ShowtimeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
