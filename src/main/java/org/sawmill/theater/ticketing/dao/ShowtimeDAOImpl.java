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
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sawmill.theater.ticketing.model.Showtime;
import org.springframework.stereotype.Service;

/**
 *
 * @author jeremy
 */
@Service
public class ShowtimeDAOImpl implements ShowtimeDAO {
    
    private static String connectionUrl = "jdbc:sqlite:SawmillTheatre";
    
    private static final String GET_ALL_SHOWTIMES = "SELECT SHOW_ID, SHOW_NAME, THEATRE_GROUP, SHOW_DATE, LAST_UPDATED FROM SHOWTIME";
    
    public ShowtimeDAOImpl() {
        
    }

    @Override
    public List<Showtime> getShowtimes() {
        List<Showtime> showtimes = new ArrayList<>();
        
        try {
            Connection conn = DriverManager.getConnection(connectionUrl);
            PreparedStatement stmt = conn.prepareStatement(GET_ALL_SHOWTIMES);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Showtime showtime = new Showtime();
                showtime.setShowId(rs.getInt("SHOW_ID"));
                showtime.setShowName(rs.getString("SHOW_NAME"));
                showtime.setTheatreGroup(rs.getString("THEATRE_GROUP"));
//                showtime.setShowDate(new Date());
//                showtime.setLastUpdated(new Date());
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
    public Showtime getShowtimeByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
