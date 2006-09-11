package org.pgist.tagging;

import java.util.Collection;

import org.pgist.util.PageSetting;


/**
 *
 * @author kenny
 *
 */
public class TaggingServiceImpl implements TaggingService {


    private TagDAO tagDAO = null;

    private TagAnalyzer analyzer = null;


    public void setTagDAO(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }


    public void setAnalyzer(TagAnalyzer analyzer) {
        this.analyzer = analyzer;
    }


    /*
     * ------------------------------------------------------------------------
     */


    public Collection getTags(PageSetting setting, boolean included) throws Exception {
        return tagDAO.getTags(setting, included);
    }//getTags()


    public Tag createTag(String name, boolean included) throws Exception {
        Tag tag = (Tag) analyzer.getTag(name);
        
        if (tag==null) {
            tag = tagDAO.addTag(name, Tag.STATUS_OFFICIAL, included);
            analyzer.addTag(tag);
        } else {
            throw new Exception("tag/stopword has already existed in the database.");
        }

        return tag;
    }//createTag()


    public void deleteTag(Long id, boolean included) throws Exception {
        int type = included ? Tag.TYPE_INCLUDED : Tag.TYPE_EXCLUDED;
        
        Tag tag = tagDAO.getTagById(id);
        
        if (tag!=null) {
            if (tag.getType()==type) {
                analyzer.deleteTag(tag.getName());
                tagDAO.deleteTag(id);
            } else {
                throw new Exception("Incorrect tag type!");
            }
        } else {
            throw new Exception("Can't find the tag/stopword to delete.");
        }
    }//deleteTag()


    public Collection searchTags(String name, boolean included) throws Exception {
        return tagDAO.searchTags(name, included);
    }//searchTags()


} //class CCTServiceImpl
