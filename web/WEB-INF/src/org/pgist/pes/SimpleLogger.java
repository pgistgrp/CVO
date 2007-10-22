package org.pgist.pes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

import org.pgist.system.SystemLog;
import org.pgist.system.SystemService;
import org.pgist.users.User;

public class SimpleLogger {
    private SystemService systemService;
    private LogService logService;
    
    /**
     * This is not an AJAX service method.
     *
     * @param systemService
     */
    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }
    
    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    /*
     * -----------------------------------------------------------
     */
    
    public Map saveAct(Map log){
        Map map = new HashMap();
        map.put("successful", true);
                      
        return map;
    }//UserAct()
    
    public Map getLogs(String start, String end, String order){
        Map map = new HashMap();
        map.put("successful", false);
                      
        try{
            if(this.logService == null)
                System.out.println(">>null logService");
            ArrayList<SystemLog> logs = this.logService.getLogs(start, end, order);
            map.put("events", logs);
            map.put("successful", true);
        }catch (Exception e){
            System.out.println("error in: SimpleLogger.getLogs.");
            e.printStackTrace();
        }
        return map;        
    }
    
    /**
     * 
     * @param start
     * @param end
     * @return user-activities, sorted by the user's total number of log entries
     */public Map getUsers(){
        Map map = new HashMap();
        map.put("successful", false);
                      
        try{
            //{user_id, user_name, }
            Collection users = systemService.getEnabledUsers();
            Map user_list = new HashMap();
            Iterator itr = users.iterator();
            while (itr.hasNext()){
                User user = (User)itr.next();
                user_list.put(user.getId(), user.getLoginname());
            }
            map.put("users", user_list);
            map.put("successful", true);
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;        
    }
}
