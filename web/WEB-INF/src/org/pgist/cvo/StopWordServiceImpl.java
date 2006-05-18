package org.pgist.cvo;

import java.util.Collection;
import java.util.List;

import org.pgist.util.PageSetting;


/**
 *
 * @author Jie Wu
 *
 */
public class StopWordServiceImpl implements StopWordService {


    private StopWordDAO stopWordDAO = null;
    
    private TagAnalyzer analyzer = null;


    public void setStopWordDAO(StopWordDAO stopWordDAO) {
        this.stopWordDAO = stopWordDAO;
    }


    public void setAnalyzer(TagAnalyzer analyzer) {
        this.analyzer = analyzer;
    }


    /*
     * ------------------------------------------------------------------------
     */


    public StopWord createStopWord(String name) throws Exception {
        if (stopWordDAO.searchStopWord(name).size() != 0) return null;
        
        StopWord stopWord = new StopWord();
        stopWord.setName(name);
        stopWordDAO.save(stopWord);
        
        return stopWord;
    }//createStopWord()


    public List getStopWords(PageSetting setting) throws Exception{
        return stopWordDAO.getStopWords(setting);
    }//getStopWords()


    public Collection searchStopWord(String stopWord) throws Exception {
        return stopWordDAO.searchStopWord(stopWord);
    } //searchStopWord()


    public boolean deleteStopWord(Long id) throws Exception {
        return stopWordDAO.deleteStopWord(id);
    }//deleteStopWord()


    public List getTags(PageSetting setting) throws Exception {
        return stopWordDAO.getTags(setting);
    }//getTags()


    public Tag createTag(String name) throws Exception {
        Tag tag = stopWordDAO.getTagByName(name);
        if (tag!=null) throw new Exception("Tag "+name+" already exists.");
        
        tag = new Tag();
        tag.setName(name);
        tag.setDescription(name);
        stopWordDAO.save(tag);
        
        analyzer.addTag(tag);
        
        return tag;
    }//createTag()


    public void deleteTag(String name) throws Exception {
        Tag tag = stopWordDAO.getTagByName(name);
        if (tag==null) throw new Exception("Tag "+name+" not exists.");
        
        tag.setStatus(Tag.STATUS_REJECTED);
        stopWordDAO.save(tag);
        
        analyzer.removeTag(tag);
    }//deleteTag()
    

} //class CCTServiceImpl
