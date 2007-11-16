package org.pgist.util;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author kenny
 *
 */
public class StringUtil {
    
    
    /**
     * Split the given comma separated line to String[].
     * 
     * @param csl comma separated line, can't be null
     * @return
     */
    public static String[] splitCSL(String csl) {
        List list = new ArrayList();
        
        String[] strs = csl.split(",");
        for (int i=0; i<strs.length; i++) {
            if (strs[i]!=null && strs[i].trim().length()>0) list.add(strs[i].trim());
        }
        
        return (String[]) list.toArray(new String[list.size()]);
    }//parseCSL()
    
    
    /**
     * parse the given comma separated line, and return a long[].
     * 
     * @param csl comma separated line, can't be null
     * @return
     */
    public static long[] parseCSL(String csl) throws Exception {
        List<Long> list = new ArrayList<Long>();
        
        String[] strs = csl.split(",");
        for (int i=0; i<strs.length; i++) {
            if (strs[i]!=null && strs[i].trim().length()>0) list.add(new Long(strs[i]));
        }
        
        long[] ids = new long[list.size()];
        for (int i=0, n=list.size(); i<n; i++) {
            ids[i] = list.get(i);
        }
        
        return ids;
    }//parseCSL()
    
    
    public static String join(long[] ids) {
        StringBuilder sb = new StringBuilder();
        
        for (int i=0; i<ids.length; i++) {
            if (i>0) sb.append(',');
            sb.append(ids[i]);
        }
        
        return sb.toString();
    }//join()
    
    
}//class StringUtil
