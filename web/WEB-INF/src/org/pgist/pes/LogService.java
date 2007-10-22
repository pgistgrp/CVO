package org.pgist.pes;

import java.util.ArrayList;

import org.pgist.system.SystemLog;

public interface LogService {
    public ArrayList<SystemLog> getLogs(String start, String end, String order);
    public ArrayList<SystemLog> getUserActivities(String start, String end);

}
