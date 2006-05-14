package org.pgist.cvo;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 *
 * @author kenny
 *
 */
public class TagDAOImpl extends CVODAOImpl implements TagDAO {


    private TagReferenceComparator comparator = new TagReferenceComparator(false);


    /*
     * ------------------------------------------------------------------------
     */


    public List addTags(String[] tags) throws Exception {
        return null;
    } //addTags()


    private static String hql_getTagsByRank = "from TagReference tr where tr.cctId=? order by tr.times desc, tr.tag.name";


    public Collection getTagsByRank(CCT cct, int count) throws Exception {
        getHibernateTemplate().setMaxResults(count);
        List list = getHibernateTemplate().find(hql_getTagsByRank, cct.getId());
        Collections.sort(list, comparator);
        return list;
    } //getTagsByRank()


    private static String getTagsByThreshold = "from TagReference tr where tr.cctId=? and tr.times>? order by tr.times desc, tr.tag.name";


    public Collection getTagsByThreshold(CCT cct, int threshold) throws Exception {
        List list = getHibernateTemplate().find(
                getTagsByThreshold,
                new Object[] {
                cct.getId(),
                new Integer(threshold),
        });
        Collections.sort(list, comparator);
        return list;
    } //getTagsByThreshold()


    private static String hql_getAllTags = "from Tag t where t.status!=?";


    public Collection getAllTags() throws Exception {
        getHibernateTemplate().setMaxResults( -1);
        return getHibernateTemplate().find(hql_getAllTags, new Integer(Tag.STATUS_REJECTED));
    } //getAllTags


}//class TagDAOImpl
