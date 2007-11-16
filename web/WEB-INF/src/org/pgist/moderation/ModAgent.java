/**
 * Moderate support tool for AJAX access
 */
package org.pgist.moderation;

import java.util.Map;
import java.util.HashMap;

public class ModAgent {
    public Map getUserStats(){
        Map results = new HashMap();
        results.put("successful", false);
        
        
        results.put("users", null);
        
        return results;
    }
}
