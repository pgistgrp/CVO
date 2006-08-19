package org.pgist.cvo;

import java.util.Collection;
import java.util.List;
import org.apache.struts.action.ActionForm;
import org.pgist.tag.Category;
import org.pgist.util.PageSetting;

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

    private PageSetting setting;

    private List stopWords;

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


    public void setStopWords(List stopWords) {
        this.stopWords = stopWords;
    }


    public List getStopWords() {
        return this.stopWords;
    }


    public void setPageSetting(PageSetting setting) {
        this.setting = setting;
    }


    public PageSetting getPageSetting() {
        return this.setting;
    }


}
