/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sawmill.theater.ticketing.service;

import java.io.File;
import java.util.List;
import org.sawmill.theater.ticketing.model.ShowSeating;
import org.sawmill.theater.ticketing.model.Showtime;

/**
 *
 * @author jeremy
 */
public interface TheatreService {
    
    public boolean isDatabaseConnected();
    public boolean checkSelectedDatabase(String filePath);
    public void createDatabase(String filePath);
    
    public List<Showtime> getShowtimes();
    public Showtime addShowtime(Showtime showtime);
    public void updateShowtime(Showtime showtime);
    public void deleteShowtime(int showtimeId);
    
    public List<ShowSeating> getShowtimeSeats(int showId);
    public ShowSeating addShowSeat(ShowSeating seat);
    public void updateShowSeat(ShowSeating seat);
    public void deleteShowSeat(int seatId);
    public void deleteAllShowSeats(int showId);
}
