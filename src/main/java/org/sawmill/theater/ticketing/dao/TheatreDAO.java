/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sawmill.theater.ticketing.dao;

import java.io.File;

/**
 *
 * @author jeremy
 */
public interface TheatreDAO {
    
    public boolean isDatabaseConnected();
    public boolean checkSelectedDatabase(String filePath);
    public void createDatabase();
    
}
