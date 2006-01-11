package org.pgist.cvo;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;


/**
 * 
 * @author kenny
 *
 */
public class CVOForm extends ActionForm {

    
    private static final long serialVersionUID = 5443268954560626922L;
    
    private List cvoList = new ArrayList();
    

    public List getCvoList() {
        return cvoList;
    }

    public void setCvoList(List cvoList) {
        this.cvoList = cvoList;
    }
    
    
}
