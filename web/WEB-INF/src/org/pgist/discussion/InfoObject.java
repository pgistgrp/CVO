package org.pgist.discussion;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_info_object" lazy="true"
 */
public class InfoObject extends GenericInfo {
    
    
    private InfoStructure structure;
    
    private Object object;
    
    
    /**
     * @return
     * @hibernate.many-to-one column="structure_id" lazy="true"
     */
    public InfoStructure getStructure() {
        return structure;
    }


    public void setStructure(InfoStructure structure) {
        this.structure = structure;
    }


    /**
     * @return object
     * 
     * @hibernate.any id-type="long" meta-type="string"
     * @hibernate.any-column name="class_name"
     * @hibernate.any-column name="class_id"
     */
    public Object getObject() {
        return object;
    }
    
    
    public void setObject(Object object) {
        this.object = object;
    }


}//class InfoObject
