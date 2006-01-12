package org.pgist.cvo;

import java.util.Collection;

import org.apache.struts.action.ActionForm;
import org.pgist.model.DiscourseObject;
import org.pgist.model.Post;


/**
 * 
 * @author kenny
 *
 */
public class CVOForm extends ActionForm {

    
    private static final long serialVersionUID = 5443268954560626922L;
    
    private Collection cvoList;
    
    private Long id;
    
    private CVO cvo;
    
    private DiscourseObject dobj;
    
    private Post root;
    
    
    public Collection getCvoList() {
        return cvoList;
    }
    
    
    public void setCvoList(Collection cvoList) {
        this.cvoList = cvoList;
    }
    
    
    public Long getId() {
        return id;
    }
    
    
    public void setId(Long id) {
        this.id = id;
    }
    
    
    public CVO getCvo() {
        return cvo;
    }
    
    
    public void setCvo(CVO cvo) {
        this.cvo = cvo;
    }
    
    
    public Post getRoot() {
        return root;
    }
    
    
    public void setRoot(Post root) {
        this.root = root;
    }
    
    
    public DiscourseObject getDobj() {
        return dobj;
    }
    
    
    public void setDobj(DiscourseObject dobj) {
        this.dobj = dobj;
    }
    
    
}
