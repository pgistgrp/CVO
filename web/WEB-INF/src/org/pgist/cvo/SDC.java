package org.pgist.cvo;

import java.util.SortedSet;
import java.util.TreeSet;

import org.pgist.model.PGame;


/**
 * 
 * @author kenny
 *
 * @hibernate.joined-subclass name="SDC" table="pgist_cvo_sdc"
 * @hibernate.joined-subclass-key column="id"
 */
public class SDC extends PGame {
    
    
    private CCT cct;
    
    private CST cst;
    
    private SortedSet themes = new TreeSet();
    
    
    public CCT getCct() {
        return cct;
    }
    
    
    public void setCct(CCT cct) {
        this.cct = cct;
    }
    
    
    public CST getCst() {
        return cst;
    }
    
    
    public void setCst(CST cst) {
        this.cst = cst;
    }


    public SortedSet getThemes() {
        return themes;
    }


    public void setThemes(SortedSet themes) {
        this.themes = themes;
    }
    
    
}//class SDC
