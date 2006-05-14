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


    public void setStopWordDAO(StopWordDAO stopWordDAO) {
        this.stopWordDAO = stopWordDAO;
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
    

} //class CCTServiceImpl
