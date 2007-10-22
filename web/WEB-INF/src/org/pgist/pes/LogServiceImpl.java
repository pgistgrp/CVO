package org.pgist.pes;

import java.util.ArrayList;

import org.pgist.system.SystemLog;

public class LogServiceImpl implements LogService{
    private LogDAO logDAO;
    
    public void setLogDAO(LogDAO logDAO){
        this.logDAO = logDAO;
    }
    
    public ArrayList<SystemLog> getLogs(String start, String end, String order){
        return this.logDAO.getLogs(start, end, order);
    }
    public ArrayList<SystemLog> getUserActivities(String start, String end){
        return this.logDAO.getUserActivities(start, end);
    }

}
