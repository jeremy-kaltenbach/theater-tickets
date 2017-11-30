/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sawmill.theater.ticketing.dao;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import org.sawmill.theater.ticketing.model.Showtime;

/**
 *
 * @author jeremy
 */
public interface ShowtimeDAO {
    
    
    public List<Showtime> getShowtimes();
    public Showtime getShowtimeByName(String name);
    
}
