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
    

}//interface StopWordDAO
