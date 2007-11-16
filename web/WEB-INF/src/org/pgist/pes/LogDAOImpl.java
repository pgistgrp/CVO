package org.pgist.pes;

import java.util.ArrayList;

import org.hibernate.Query;
import org.pgist.system.BaseDAOImpl;
import org.pgist.system.SystemLog;

public class LogDAOImpl extends BaseDAOImpl implements LogDAO{
    private static final String hql_getLogs_desc = "from SystemLog where time>? and time<=? order by time desc";
    private static final String hql_getLogs_asc = "from SystemLog where time>? and time<=? order by time";
    
    public ArrayList<SystemLog> getLogs(String start, String end, String order){
        if (start == null || "".equals(start))
            start = "1970-01-01 0:0:0";
        if (end == null || "".equals(end))
            end = "2100-01-01 0:0:0";
       
        Query query = null;
        String qstr = (order.equalsIgnoreCase("DESC"))?hql_getLogs_desc:hql_getLogs_asc;
        query = getSession().createQuery(qstr);
        query.setString(0, start);
        query.setString(1, end);
        
        ArrayList<SystemLog> logs = new ArrayList<SystemLog>(query.list());
        System.out.println(">>" + query.getQueryString());
        System.out.println("Total logs returned: " + logs.size());
        return logs;
    }
    
    public ArrayList<SystemLog> getUserActivities(String start, String end){
       
        
        return null;
    }

}
