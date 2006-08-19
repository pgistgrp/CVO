package org.pgist.cvo;

import java.util.Collection;
import java.util.List;

import org.pgist.tag.Tag;
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
        //check if this word is already a stop word in the database
        if (stopWordDAO.checkStopWord(name))
            throw new Exception("StopWord has existed in the database.");

        //check if this word is already a tag in the database
        if (stopWordDAO.checkTag(name))
            throw new Exception(name + " is a tag in the database.");

        StopWord stopWord = new StopWord();
        stopWord.setName(name);
        stopWordDAO.save(stopWord);
        analyzer.addStopWord(name);

        return stopWord;
    }//createStopWord()


    public List getStopWords(PageSetting setting) throws Exception{
        return stopWordDAO.getStopWords(setting);
    }//getStopWords()


    public Collection searchStopWord(String stopWord) throws Exception {
        return stopWordDAO.searchStopWord(stopWord);
    } //searchStopWord()


    public boolean deleteStopWord(Long id) throws Exception {
        analyzer.deleteStopWord(id);
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
        stopWordDAO.save(tag);

        analyzer.addTag(tag);

        return tag;
    }//createTag()


    public void deleteTag(Long id) throws Exception {
        Tag tag = stopWordDAO.getTagById(id);
        if (tag==null) throw new Exception("Tag with id "+id+" not exists.");

        tag.setStatus(Tag.STATUS_REJECTED);
        stopWordDAO.save(tag);

        analyzer.removeTag(tag);
    }//deleteTag()


    public Collection searchTag(String name) throws Exception {
        return stopWordDAO.searchTag(name);
    }//searchTag()


} //class CCTServiceImpl
