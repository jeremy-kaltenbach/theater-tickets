/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sawmill.theater.ticketing.service;

import java.util.List;
import org.sawmill.theater.ticketing.dao.ShowtimeDAO;
import org.sawmill.theater.ticketing.model.Showtime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jeremy
 */
@Service
public class TheatreServiceImpl implements TheatreService {
    
    @Autowired
    private ShowtimeDAO showTimeDAO;

    @Override
    public List<Showtime> getShowtimes() {
        return showTimeDAO.getShowtimes();
    }

    @Override
    public Showtime getShowtimeByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
