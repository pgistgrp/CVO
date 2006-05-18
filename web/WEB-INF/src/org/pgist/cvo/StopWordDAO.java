package org.pgist.cvo;

import java.util.Collection;
import java.util.List;

import org.pgist.util.PageSetting;


/**
 * 
 * @author Jie Wu
 *
 */
public interface StopWordDAO {
    

    StopWord createStopWord(String s) throws Exception;

    List getAllStopWords() throws Exception;

    boolean deleteStopWord(Long id) throws Exception;

    Collection searchStopWord(String stopWord) throws Exception;

    List getStopWords(PageSetting setting) throws Exception;
    
    void save(StopWord stopWord) throws Exception;

    List getTags(PageSetting setting) throws Exception;

    Tag getTagByName(String name) throws Exception;

    void save(Tag tag) throws Exception;

    Collection searchTag(String name) throws Exception;
    

}//interface StopWordDAO
