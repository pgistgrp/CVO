package org.pgist.cvo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.pgist.util.PageSetting;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


/**
 *
 * @author Jie Wu
 *
 */
public class StopWordDAOImpl extends HibernateDaoSupport implements StopWordDAO {
    
    
    public StopWord createStopWord(String s) throws Exception{
        StopWord sw = new StopWord();
        sw.setName(s);
        getHibernateTemplate().saveOrUpdate(sw);
        return sw;
    }
    
    
    public void save(StopWord stopWord) throws Exception {
        getHibernateTemplate().saveOrUpdate(stopWord);
    }//save()


    private static String hql_getAllStopWords = "from StopWord sw order by sw.name";
    

    public List getAllStopWords() throws Exception{
        return getHibernateTemplate().find(hql_getAllStopWords);
    }//getAllStopWords()


    public boolean deleteStopWord(Long id) throws Exception{
        StopWord sw = (StopWord) getHibernateTemplate().load(StopWord.class, id);
        if (sw == null)
            return false;
        getHibernateTemplate().delete(sw);
        return true;
    }//deleteStopWord()


    private static final String hql_searchStopWord = "from StopWord sw where lower(sw.name) like ?";
    

    public Collection searchStopWord(String stopWord) throws Exception {
        return getHibernateTemplate().find(hql_searchStopWord, stopWord.toLowerCase()+"%");
    } //searchStopWord()


    private static String hql_getStopWords1 = "select count(sw.id) from StopWord sw";

    private static String hql_getStopWords2 = "from StopWord sw order by sw.name";
    

    public List getStopWords(PageSetting setting) throws Exception{
        List result = new ArrayList();

        List list = getHibernateTemplate().find(hql_getStopWords1);
        if (list == null || list.size() == 0)return result;

        int total = ((Integer) list.get(0)).intValue();
        if (setting.getRowOfPage() == -1) setting.setRowOfPage(total);
        setting.setRowSize(total);

        Query query = getSession().createQuery(hql_getStopWords2);
        query.setFirstResult(setting.getFirstRow());
        query.setMaxResults(setting.getRowOfPage());

        return query.list();
    } //getStopWords()


    private static String hql_getTags1 = "select count(t.id) from Tag t where t.status!=?";

    private static String hql_getTags2 = "from Tag t where t.status!=? order by t.name";
    

    public List getTags(PageSetting setting) throws Exception {
        List result = new ArrayList();

        List list = getHibernateTemplate().find(hql_getTags1, new Integer(Tag.STATUS_REJECTED));
        if (list == null || list.size() == 0) return result;

        int total = ((Integer) list.get(0)).intValue();
        if (setting.getRowOfPage() == -1) setting.setRowOfPage(total);
        setting.setRowSize(total);

        Query query = getSession().createQuery(hql_getTags2);
        query.setInteger(0, Tag.STATUS_REJECTED);
        query.setFirstResult(setting.getFirstRow());
        query.setMaxResults(setting.getRowOfPage());

        return query.list();
    }//getTags()

    
    private static final String hql_getTagByName = "from Tag t where t.status!=? and lower(t.name)=?";
    

    public Tag getTagByName(String name) throws Exception {
        List list = getHibernateTemplate().find(hql_getTagByName, new Object[] {
                new Integer(Tag.STATUS_REJECTED),
                name.toLowerCase(),
        });
        if (list.size()==0) return null;
        return (Tag) list.get(0);
    }//getTagByName()


    public void save(Tag tag) throws Exception {
        getHibernateTemplate().saveOrUpdate(tag);
    }//save()


}//class StopWordDAOImpl
