package org.pgist.workflow;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


/**
 * 
 * @author kenny
 *
 */
public class WorkflowDAOImpl extends HibernateDaoSupport implements WorkflowDAO {
    
    
    public Collection getSituations() throws Exception {
        return new ArrayList();
    }//getSituations()
    
    
}//class WorkflowDAOImpl
