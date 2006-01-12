package org.pgist.cvo;

import java.util.Collection;

import org.apache.struts.action.ActionForm;


/**
 * 
 * @author kenny
 *
 */
public class CVOForm extends ActionForm {

    
    private static final long serialVersionUID = 5443268954560626922L;
    
    private Collection cvoList;
    

    public Collection getCvoList() {
        return cvoList;
    }

    public void setCvoList(Collection cvoList) {
        this.cvoList = cvoList;
    }
    
    
}
