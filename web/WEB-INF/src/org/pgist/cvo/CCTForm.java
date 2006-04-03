package org.pgist.cvo;

import java.util.Collection;

import org.apache.struts.action.ActionForm;


/**
 * 
 * @author kenny
 *
 */
public class CCTForm extends ActionForm {

    
    private static final long serialVersionUID = 1L;

    private Collection ccts;
    
    private Long cctId;
    
    private CCT cct;
    
    private Category category;
    
    private Collection tags;
    
    
    public Collection getCcts() {
        return ccts;
    }
    
    
    public void setCcts(Collection cvoList) {
        this.ccts = cvoList;
    }
    
    
    public Long getCctId() {
        return cctId;
    }
    
    
    public void setCctId(Long id) {
        this.cctId = id;
    }
    
    
    public CCT getCct() {
        return cct;
    }


    public void setCct(CCT cct) {
        this.cct = cct;
    }


    public Category getCategory() {
        return category;
    }


    public void setCategory(Category category) {
        this.category = category;
    }


    public Collection getTags() {
        return tags;
    }


    public void setTags(Collection tags) {
        this.tags = tags;
    }
    
    
}
