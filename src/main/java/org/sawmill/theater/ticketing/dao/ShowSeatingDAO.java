/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sawmill.theater.ticketing.dao;

import java.util.List;
import org.sawmill.theater.ticketing.model.ShowSeating;

/**
 *
 * @author jeremy
 */
public interface ShowSeatingDAO {
    
    public List<ShowSeating> getShowtimeSeats(int showId);
    public void addShowSeat(ShowSeating seat);
    public void updateShowSeat(ShowSeating seat);
    public void deleteShowSeat(int seatId);
    public void deleteAllShowSeats(int showId);
}
