package org.pgist.cvo;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.pgist.system.UserDAO;
import org.pgist.users.User;
import org.pgist.util.PageSetting;
import org.pgist.util.WebUtils;
import java.util.List;


/**
 *
 * @author kenny
 *
 */
public class CCTServiceImpl implements CCTService {


    private UserDAO userDAO = null;

    private CCTDAO cctDAO = null;

    private TagDAO tagDAO = null;

    private TagAnalyzer analyzer = null;


    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    public void setCctDAO(CCTDAO cctDAO) {
        this.cctDAO = cctDAO;
    }


    public void setTagDAO(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }


    public void setAnalyzer(TagAnalyzer analyzer) {
        this.analyzer = analyzer;
    }


    /*
     * ------------------------------------------------------------------------
     */


    public Collection getCCTs() throws Exception {
        return cctDAO.getCCTs();
    }//getCCTs()


    public CCT createCCT(String name, String purpose, String instruction) throws Exception {
        CategoryReference catRef = new CategoryReference();

        CCT cct = new CCT();

        catRef.setCct(cct);
        cct.setRootCategory(catRef);

        cct.setName(name);
        cct.setPurpose(purpose);
        cct.setInstruction(instruction);
        cct.setCreateTime(new Date());

        Long id = WebUtils.currentUserId();
        User user = userDAO.getUserById(id, true, false);
        cct.setCreator(user);

        cctDAO.save(cct);

        return cct;
    }//createCCT()


    public void save(CCT cct) throws Exception {
        cctDAO.save(cct);
    }//save()


    public void save(Concern concern) throws Exception {
        cctDAO.save(concern);
    }//save()


    public CCT getCCTById(Long cctId) throws Exception {
        return cctDAO.getCCTById(cctId);
    }//getCCTById()


    public Concern createConcern(Long cctId, String concern, String[] tagStrs) throws Exception {
        CCT cct = cctDAO.getCCTById(cctId);
        if (cct==null) throw new Exception("cct not found");

        Concern c = new Concern();
        c.setCct(cct);
        c.setCreateTime(new Date());
        c.setContent(concern);

        Long id = WebUtils.currentUserId();
        User user = userDAO.getUserById(id, true, false);
        c.setAuthor(user);

        synchronized (this) {
            Tag tag = null;
            TagReference ref = null;
            for (String tagName : tagStrs) {
                if (tagName==null || "".equals(tagName.trim())) continue;

                tag = analyzer.tagExists(tagName.trim());
                if (tag!=null) {
                    ref = cctDAO.getTagReferenceByTagId(cct.getId(), tag.getId());
                    if (ref==null) {
                        ref = new TagReference();
                        ref.setCctId(cct.getId());
                        ref.setTag(tag);
                        ref.setTimes(1);
                    } else {
                        ref.setTimes(ref.getTimes()+1);
                    }
                } else {
                    tag = new Tag();
                    tag.setName(tagName);
                    tag.setDescription(tagName);
                    tag.setStatus(Tag.STATUS_CANDIDATE);
                    cctDAO.save(tag);

                    analyzer.addTag(tag);

                    ref = new TagReference();
                    ref.setTag(tag);
                    ref.setTimes(1);
                    ref.setCctId(cct.getId());
                    cctDAO.save(ref);
                }
                cctDAO.save(ref);
                c.getTags().add(ref);
            }//for
        }//synchronized

        cctDAO.save(c);
        cctDAO.save(cct);

        return c;
    }//createConcern()


    public Collection getMyConcerns(CCT cct) throws Exception {
        return cctDAO.getMyConcerns(cct.getId(), WebUtils.currentUserId());
    }//getMyConcerns()


    public Collection getOthersConcerns(CCT cct, int count) throws Exception {
        return cctDAO.getOthersConcerns(cct.getId(), WebUtils.currentUserId(), count);
    }//getOthersConcerns()


    public Collection getRandomConcerns(CCT cct, PageSetting setting) throws Exception {
        return cctDAO.getRandomConcerns(cct.getId(), WebUtils.currentUserId(), setting);
    }//getRandomConcerns()


    public Collection getTagsByRank(CCT cct, int count) throws Exception {
        return tagDAO.getTagsByRank(cct, count);
    }//getTagsByRank()


    public Collection getTagsByThreshold(CCT cct, int threshold) throws Exception {
        return tagDAO.getTagsByThreshold(cct, threshold);
    }//getTagsByThreshold()


    public Collection getConcernsByTag(Long tagRefId, int count) throws Exception {
        TagReference tagRef = tagDAO.getTagReferenceById(tagRefId);
        if (tagRef==null) throw new Exception("Requested TagReference doesn't exist.");
        return cctDAO.getConcernsByTag(tagRef, count);
    }//getConcernsByTag()

    public Collection getSuggestedTags(String statement) throws Exception{
    	return analyzer.parseTextTokenized(statement);
    }//getSuggestedTags


    public Concern getConcernById(Long concernId) throws Exception {
        return cctDAO.getConcernById(concernId);
    }//getConcernById()


    public void deleteConcern(Concern concern) throws Exception {
        CCT cct = concern.getCct();

        synchronized (this) {
            Set oldTags = new HashSet(concern.getTags());
            concern.getTags().clear();

            for (Object object : oldTags) {
                TagReference ref = (TagReference) object;
                ref.setTimes(ref.getTimes()-1);
                if (ref.getTimes()<1) {
                    cctDAO.delete(ref);
                } else {
                    cctDAO.save(ref);
                }
            }//for
        }//synchronized

        concern.setDeleted(true);
        concern.setCreateTime(new Date());

        cctDAO.save(concern);
        cctDAO.save(cct);
    }//deleteConcern()


    public void editConcernTags(Concern concern, String[] tagStrs) throws Exception {
        System.out.println("tag array: ");
        for (int i = 0; i < tagStrs.length; i++) {
            System.out.print("---> "+tagStrs[i]);
        }
        System.out.println();

        //Collection tags = analyzer.ensureTags(tagStrs);

        CCT cct = concern.getCct();

        synchronized (this) {
            Map<String, TagReference> oldTags = new HashMap<String, TagReference>();
            Set<TagReference> tags = concern.getTags();
            for (TagReference one : tags) {
                oldTags.put(one.getTag().getName(), one);
            }
            tags.clear();

            Tag tag = null;
            TagReference ref = null;
            for (String tagName : tagStrs) {
                if (tagName==null || "".equals(tagName.trim())) continue;

                ref = oldTags.get(tagName);
                if (ref!=null) {
                    oldTags.remove(tagName);
                } else {
                    tag = analyzer.tagExists(tagName);
                    if (tag!=null) {
                        tag = cctDAO.getTagById(tag.getId());
                        ref = cctDAO.getTagReferenceByTagId(cct.getId(), tag.getId());
                        if (ref==null) {
                            ref = new TagReference();
                            ref.setCctId(cct.getId());
                            ref.setTag(tag);
                            ref.setTimes(1);
                        } else {
                            ref.setTimes(ref.getTimes()+1);
                        }
                    } else {
                        tag = new Tag();
                        tag.setName(tagName);
                        tag.setDescription(tagName);
                        tag.setStatus(Tag.STATUS_CANDIDATE);
                        cctDAO.save(tag);
                        ref = new TagReference();
                        ref.setTag(tag);
                        ref.setTimes(1);
                        ref.setCctId(cct.getId());
                    }
                    cctDAO.save(ref);
                }
                tags.add(ref);
            }//for

            //Decrease the times of the old tags
            Collection<TagReference> collection = oldTags.values();
            for (TagReference one : collection) {
                one.setTimes(one.getTimes()-1);
                if (one.getTimes()<1) {
                    cctDAO.delete(one);
                } else {
                    cctDAO.save(one);
                }
            }

            concern.setCreateTime(new Date());
            cctDAO.save(concern);
            cctDAO.save(cct);
        }//synchronized
    }//editConcernTags()


    public int getConcernsTotal(CCT cct, int whose) throws Exception {
        return cctDAO.getConcernsTotal(cct, whose, WebUtils.currentUserId());
    }//getConcernsTotal()


    public TagReference getTagReferenceById(Long tagRefId) throws Exception {
        return tagDAO.getTagReferenceById(tagRefId);
    }//getTagReferenceById()


    public Collection searchTags(CCT cct, String tag) throws Exception {
        return cctDAO.searchTags(cct.getId(), tag);
    }//searchTags()


    public void editStopWord(Long id, String updatedName){
    }


    public void createStopWord(String name) {

    }


    public void deleteStopWord(Long id){

    }


    public List getStopWords(PageSetting setting){
        return null;
    }


}//class CCTServiceImpl
