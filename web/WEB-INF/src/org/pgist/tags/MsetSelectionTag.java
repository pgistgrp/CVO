package org.pgist.tags;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.pgist.sarp.vtt.MUnitSet;

public class MsetSelectionTag extends SimpleTagSupport {
    
    
    private MUnitSet mset;
    
    private String unit;
    
    
    public void setMset(MUnitSet mset) {
        this.mset = mset;
    }


    public void setUnit(String unit) {
        this.unit = unit;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void doTag() throws JspException, IOException {
        Map<Long, String> selections = mset.getUserSelections();
        
        int count = 0;
        for (Map.Entry<Long, String> entry : selections.entrySet()) {
            if (unit.equals(entry.getValue())) {
                count++;
            }
        }
        
        JspWriter writer = getJspContext().getOut();
        writer.write("(" + count + "/" + selections.size() + ")");
    }//doTag()
    
    
}
