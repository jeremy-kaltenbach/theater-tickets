/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sawmill.theater.ticketing.model;

import java.util.Date;

/**
 *
 * @author jeremy
 */
public class Showtime {
    
    private int showId;
    private String showName;
    private String theatreGroup;
    private Date showDate;
    private Date lastUpdated;
    
    public Showtime() {
    }

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getTheatreGroup() {
        return theatreGroup;
    }

    public void setTheatreGroup(String theatreGroup) {
        this.theatreGroup = theatreGroup;
    }

    public Date getShowDate() {
        return showDate;
    }

    public void setShowDate(Date showDate) {
        this.showDate = showDate;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    
    
    
}
