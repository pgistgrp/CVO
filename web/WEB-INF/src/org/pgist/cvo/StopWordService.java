package org.pgist.cvo;

import java.util.Collection;
import java.util.List;

import org.pgist.util.PageSetting;

public interface StopWordService {
    

    StopWord createStopWord(String name) throws Exception;

    List getStopWords(PageSetting setting) throws Exception;

    Collection searchStopWord(String stopWord) throws Exception;

    boolean deleteStopWord(Long id) throws Exception;

    List getTags(PageSetting setting) throws Exception;

    Tag createTag(String name) throws Exception;

    void deleteTag(String name) throws Exception;

    Collection searchTag(String name) throws Exception;
    

}//interface StopWordService
