/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sawmill.theater.ticketing.service;

import java.util.List;
import org.sawmill.theater.ticketing.dao.ShowSeatingDAO;
import org.sawmill.theater.ticketing.dao.ShowSeatingDAOImpl;
import org.sawmill.theater.ticketing.dao.ShowtimeDAO;
import org.sawmill.theater.ticketing.dao.ShowtimeDAOImpl;
import org.sawmill.theater.ticketing.model.ShowSeating;
import org.sawmill.theater.ticketing.model.Showtime;

/**
 *
 * @author jeremy
 */
public class TheatreServiceImpl implements TheatreService {

    private ShowtimeDAO showTimeDAO = new ShowtimeDAOImpl();
    private ShowSeatingDAO showSeatingDAO = new ShowSeatingDAOImpl();

    @Override
    public List<Showtime> getShowtimes() {
        return showTimeDAO.getShowtimes();
    }

    @Override
    public Showtime addShowtime(Showtime showtime) {
        return showTimeDAO.addShowtime(showtime);
    }

    @Override
    public void updateShowtime(Showtime showtime) {
        showTimeDAO.updateShowtime(showtime);
    }

    @Override
    public void deleteShowtime(int showtimeId) {
        showTimeDAO.deleteShowtime(showtimeId);
        showSeatingDAO.deleteAllShowSeats(showtimeId);
    }

    @Override
    public List<ShowSeating> getShowtimeSeats(int showId) {
        return showSeatingDAO.getShowtimeSeats(showId);
    }

    @Override
    public ShowSeating addShowSeat(ShowSeating seat) {
        return showSeatingDAO.addShowSeat(seat);
    }

    @Override
    public void updateShowSeat(ShowSeating seat) {
        showSeatingDAO.updateShowSeat(seat);
    }

    @Override
    public void deleteShowSeat(int seatId) {
        showSeatingDAO.deleteShowSeat(seatId);
    }

    @Override
    public void deleteAllShowSeats(int showId) {
        showSeatingDAO.deleteAllShowSeats(showId);
    }
}
