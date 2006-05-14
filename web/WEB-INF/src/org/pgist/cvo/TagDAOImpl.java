package org.pgist.cvo;

import org.pgist.util.PageSetting;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import org.hibernate.Query;


/**
 *
 * @author kenny
 *
 */
public class TagDAOImpl extends CVODAOImpl implements TagDAO {
    public TagDAOImpl() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private TagReferenceComparator comparator = new TagReferenceComparator(false);


    /*
     * ------------------------------------------------------------------------
     */


    public List addTags(String[] tags) throws Exception {
        return null;
    } //addTags()


    public StopWord createStopWord(String s) {
        StopWord sw = new StopWord();
        sw.setName(s);
        getHibernateTemplate().saveOrUpdate(sw);
        return sw;
    }


    private static String hql_getTagsByRank =
            "from TagReference tr where tr.cctId=? order by tr.times desc, tr.tag.name";


    public Collection getTagsByRank(CCT cct, int count) throws Exception {
        getHibernateTemplate().setMaxResults(count);
        List list = getHibernateTemplate().find(hql_getTagsByRank, cct.getId());
        Collections.sort(list, comparator);
        return list;
    } //getTagsByRank()


    private static String getTagsByThreshold = "from TagReference tr where tr.cctId=? and tr.times>? order by tr.times desc, tr.tag.name";


    public Collection getTagsByThreshold(CCT cct, int threshold) throws
            Exception {
        List list = getHibernateTemplate().find(
                getTagsByThreshold,
                new Object[] {
                cct.getId(),
                new Integer(threshold),
        }
                );
        Collections.sort(list, comparator);
        return list;
    } //getTagsByThreshold()


    private static String hql_getAllTags = "from Tag t where t.status!=?";


    public Collection getAllTags() throws Exception {
        getHibernateTemplate().setMaxResults( -1);
        return getHibernateTemplate().find(hql_getAllTags,
                                           new Integer(Tag.STATUS_REJECTED));
    } //getAllTags


    private static String hql_getAllStopWords = "from StopWord sw";

    public List getAllStopWords() {
        return getHibernateTemplate().find(hql_getAllTags);
    }


    public boolean deleteStopWord(Long id) {
        StopWord sw = (StopWord) getHibernateTemplate().load(StopWord.class, id);
        if (sw == null)
            return false;
        getHibernateTemplate().delete(sw);
        return true;
    }


    private static final String hql_searchStopWord =
            "from StopWord sw where lower(sw.name) = ?";

    public Collection searchStopWord(String stopWord) throws Exception {
        return getHibernateTemplate().find(hql_searchStopWord,
                                           stopWord.toLowerCase());
    } //searchStopWord()


    private static String hql_getStopWords1 =
            "select count(sw.id) from StopWord sw";

    private static String hql_getStopWords2 =
            "from StopWord sw order by sw.name";

    public List getStopWords(PageSetting setting) {
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

    private void jbInit() throws Exception {
    }

} //class TagDAOImpl
