package org.pgist.util;


/**
 * This class aggregate some addtional Database Meta Data for PGIST.<br>
 * 
 * Normally the Hibernate layer makes database details transparent to developers. But sometimes we have
 * to directly use JDBC instead of Hibernate.<br>
 * 
 * @author kenny
 *
 */
public class DBMetaData {
    
    
    public static final String SEQ_FOOTPRINT_ID = "gis_proj_footprint_id";
    
    
    public static final String TABLE_CAT_TAG_IN_CST = "pgist_cst_cat_tag_link";
    
    
    public static final String VIEW_CONCERN_TAG_IN_STRUCTURE = "view_concern_tags";
    
    
    public static final String VIEW_POST_REPLY_TAG_IN_DISCUSSION = "view_post_reply_tags";
    
    
    public static final String VIEW_DPOST_TAG_IN_TARGET = "view_dpost_tag_link";
    
    
}//class DBMetaData
