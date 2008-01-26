package org.pgist.sarp.bct;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.pgist.sarp.drt.InfoObject;
import org.pgist.system.SystemDAO;
import org.pgist.system.UserDAO;
import org.pgist.system.YesNoVoting;
import org.pgist.tagging.Tag;
import org.pgist.tagging.TagAnalyzer;
import org.pgist.tagging.TagDAO;
import org.pgist.users.User;
import org.pgist.util.PageSetting;
import org.pgist.util.WebUtils;


/**
 *
 * @author kenny
 *
 */
public class BCTServiceImpl implements BCTService {


    private UserDAO userDAO = null;

    private BCTDAO bctDAO = null;

    private TagDAO tagDAO = null;

    private TagAnalyzer analyzer = null;

    private SystemDAO systemDAO;
    
    
	public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    public void setBctDAO(BCTDAO bctDAO) {
        this.bctDAO = bctDAO;
    }


    public void setTagDAO(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }


    public void setAnalyzer(TagAnalyzer analyzer) {
        this.analyzer = analyzer;
    }


    public void setSystemDAO(SystemDAO systemDAO) {
        this.systemDAO = systemDAO;
    }

    
    /*
     * ------------------------------------------------------------------------
     */


    public BCT createBCT(Long workflowId, String name, String purpose, String instruction) throws Exception {
        Date time = new Date();
        BCT bct = new BCT();

        bct.setWorkflowId(workflowId);
        bct.setName(name);
        bct.setPurpose(purpose);
        bct.setInstruction(instruction);
        bct.setCreateTime(time);
        
        bctDAO.save(bct);
        
        return bct;
    } //createBCT()


    public void save(BCT bct) throws Exception {
        bctDAO.save(bct);
    } //save()


    public void save(Concern concern) throws Exception {
        bctDAO.save(concern);
    } //save()


    public BCT getBCTById(Long bctId) throws Exception {
        return bctDAO.getBCTById(bctId);
    } //getBCTById()


    public Concern createConcern(Long bctId, String concern, String[] tagStrs) throws Exception {
        BCT bct = bctDAO.getBCTById(bctId);
        if (bct == null) throw new Exception("bct not found");

        Concern c = new Concern();
        c.setBct(bct);
        c.setCreateTime(new Date());
        c.setContent(concern);
        c.setReplyTime(c.getCreateTime());

        Long id = WebUtils.currentUserId();
        User user = userDAO.getUserById(id, true, false);
        c.setAuthor(user);

        Tag tag = null;
        TagReference ref = null;
        for (String tagName : tagStrs) {
            if (tagName == null || "".equals(tagName.trim())) continue;

            tag = analyzer.getTag(tagName.trim());
            if (tag != null) {
                ref = bctDAO.getTagReferenceByTagId(bct.getId(), tag.getId());
                if (ref == null) {
                    ref = new TagReference();
                    ref.setBctId(bct.getId());
                    ref.setTag(tag);
                    ref.setTimes(1);
                } else {
                    bctDAO.increaseRefTimes(ref);
                }
            } else {
                tag = new Tag();
                tag.setName(tagName);
                tag.setStatus(Tag.STATUS_CANDIDATE);
                tagDAO.save(tag);
                
                analyzer.addTag(tag);
                
                ref = new TagReference();
                ref.setTag(tag);
                ref.setTimes(1);
                ref.setBctId(bct.getId());
                bctDAO.save(ref);
            }
            
            c.getTags().add(ref);
        } //for

        bctDAO.save(c);

        /*
         * the author of the concern always agrees.
         */
        systemDAO.setVoting(YesNoVoting.TYPE_CONCERN, c.getId(), true);
        bctDAO.increaseVoting(c, true);
        
        return c;
    } //createConcern()


    public Collection getMyConcerns(BCT bct) throws Exception {
        return bctDAO.getMyConcerns(bct.getId(), WebUtils.currentUserId());
    } //getMyConcerns()


    public Collection getOthersConcerns(BCT bct, int count) throws Exception {
        return bctDAO.getOthersConcerns(bct.getId(), WebUtils.currentUserId(),
                                        count);
    } //getOthersConcerns()


    public Collection getRandomConcerns(BCT bct, PageSetting setting) throws Exception {
        return bctDAO.getRandomConcerns(bct.getId(), WebUtils.currentUserId(), setting);
    } //getRandomConcerns()


    public Collection getTagsByRank(BCT bct, int count) throws Exception {
        return bctDAO.getTagsByRank(bct, count);
    } //getTagsByRank()


    public Collection getTagsByThreshold(BCT bct, int threshold) throws Exception {
        return bctDAO.getTagsByThreshold(bct, threshold);
    } //getTagsByThreshold()


    public Collection getConcernsByTag(Long tagRefId, int count) throws Exception {
        TagReference tagRef = bctDAO.getTagReferenceById(tagRefId);
        if (tagRef == null)throw new Exception(
                "Requested TagReference doesn't exist.");
        return bctDAO.getConcernsByTag(tagRef, count);
    } //getConcernsByTag()
    
    
    public String[][] getSuggestedTags(String statement) throws Exception {
        return analyzer.suggest(statement);
    } //getSuggestedTags


    public Concern getConcernById(Long concernId) throws Exception {
        return bctDAO.getConcernById(concernId);
    } //getConcernById()


    public ConcernComment getConcernCommentById(Long commentId) throws Exception {
        return bctDAO.getConcernCommentById(commentId);
    }//getConcernCommentById()


    public void deleteConcern(Long concernId) throws Exception {
        Concern concern = bctDAO.getConcernById(concernId);
        
        if (concern==null) {
            throw new Exception("can't find the specified concern");
        }
        
        if (concern.isDeleted()) {
            throw new Exception("This concern is already deleted.");
        }
        
        if (!WebUtils.checkUser(concern.getAuthor().getId())) {
            throw new Exception("You have no right to edit this concern.");
        }
        
        BCT bct = concern.getBct();
        
        Set oldTags = new HashSet(concern.getTags());
        concern.getTags().clear();
        
        for (Object object : oldTags) {
            TagReference ref = (TagReference) object;
            if (ref.getTimes() > 0) {
                bctDAO.decreaseRefTimes(ref);
                bctDAO.save(ref);
            }
        } //for
        
        concern.setDeleted(true);
        concern.setCreateTime(new Date());
        
        bctDAO.deleteConcernComments(concern);
        bct.getConcerns().remove(concern);
        
        bctDAO.save(concern);
        
        bctDAO.save(bct);
    } //deleteConcern()


    public void editConcernTags(Concern concern, String[] tagStrs) throws Exception {
        BCT bct = concern.getBct();
        
        Set tags = new HashSet();
        for (String one : tagStrs) {
            tags.add(one);
        }//for
        
        Set set = new HashSet();
        
        for (TagReference one : (Set<TagReference>) concern.getTags()) {
            if (!tags.contains(one.getTag().getName())) {
                set.add(one);
                bctDAO.decreaseRefTimes(one);
            }
        }//for
        
        concern.getTags().removeAll(set);
        
        tags.clear();
        
        for (TagReference one : (Set<TagReference>) concern.getTags()) {
            tags.add(one.getTag().getName());
        }//for
        
        Tag tag;
        TagReference ref;
        
        for (String one : tagStrs) {
            if (!tags.contains(one)) {
                if (one == null || "".equals(one.trim()))continue;
                
                tag = analyzer.getTag(one);
                if (tag != null) {
                    tag = tagDAO.getTagById(tag.getId());
                    ref = bctDAO.getTagReferenceByTagId(bct.getId(), tag.getId());
                    if (ref == null) {
                        ref = new TagReference();
                        ref.setBctId(bct.getId());
                        ref.setTag(tag);
                        ref.setTimes(1);
                    } else {
                        bctDAO.increaseRefTimes(ref);
                    }
                } else {
                    tag = tagDAO.addTag(one, Tag.STATUS_CANDIDATE, true);
                    analyzer.addTag(tag);
                    ref = new TagReference();
                    ref.setTag(tag);
                    ref.setTimes(1);
                    ref.setBctId(bct.getId());
                }
                bctDAO.save(ref);
                concern.getTags().add(ref);
            }
        }//for
        
        concern.setCreateTime(new Date());
        bctDAO.save(concern);
    } //editConcernTags()


    public int getConcernsTotal(BCT bct, int whose) throws Exception {
        return bctDAO.getConcernsTotal(bct, whose, WebUtils.currentUserId());
    } //getConcernsTotal()


    public TagReference getTagReferenceById(Long tagRefId) throws Exception {
        return bctDAO.getTagReferenceById(tagRefId);
    } //getTagReferenceById()


    public Collection searchTags(BCT bct, String tag) throws Exception {
        return bctDAO.searchTags(bct.getId(), tag);
    } //searchTags()


    public Collection getTagCloud(BCT bct, PageSetting setting) throws Exception {
        return bctDAO.getTagCloud(bct, setting);
    }//getTagCloud()


    public Collection getContextConcerns(BCT bct, PageSetting setting, String filter, String type, int sorting) throws Exception {
        Collection concerns = null;
        
        if (filter==null || "".equals(filter.trim())) {
            concerns = bctDAO.getContextConcerns(bct, setting, type, sorting);
        } else {
            concerns = bctDAO.getContextConcerns(bct, setting, filter, type, sorting);
        }
        
        /**
         * put the voting object for current user to concern
         */
        for (Concern one : (Collection<Concern>) concerns) {
            YesNoVoting voting = systemDAO.getVoting(YesNoVoting.TYPE_CONCERN, one.getId());
            one.setObject(voting);
        }
        
        return concerns;
    }//getContextConcerns()


    public boolean setVotingOnConcern(Long id, boolean agree) throws Exception {
        if (!systemDAO.setVoting(YesNoVoting.TYPE_CONCERN, id, agree)) return false;
        
        Concern concern = bctDAO.getConcernById(id);
        
        bctDAO.increaseVoting(concern, agree);
        
        return true;
    }//setVotingOnConcern()


    public ConcernComment createConcernComment(Long concernId, String title, String content, String[] tags) throws Exception {
        Concern concern = bctDAO.getConcernById(concernId);
        
        if (concern==null) throw new Exception("can't find the specified concern");
            
        ConcernComment comment = new ConcernComment();
        comment.setConcern(concern);
        comment.setTitle(title);
        comment.setContent(content);
        comment.setDeleted(false);
        comment.setCreateTime(WebUtils.getDate());
        
        User owner = userDAO.getUserById(WebUtils.currentUserId(), true, false);
        comment.setOwner(owner);
        
        for (String tagStr : tags) {
            Tag tag = analyzer.getTag(tagStr.trim());
            
            if (tag==null) {
                tag = tagDAO.addTag(tagStr.trim(), Tag.STATUS_CANDIDATE, true);
            } else {
                tag = tagDAO.getTagByName(tag.getName());
            }
            
            comment.getTags().add(tag);
        }
        
        bctDAO.save(comment);
        
        bctDAO.increaseReplies(concern);
        
        /*
         * the author of the comment always agrees.
         */
        systemDAO.setVoting(YesNoVoting.TYPE_COMMENT, comment.getId(), true);
        bctDAO.increaseVoting(comment, true);
        
        return comment;
    }//createConcernComment()


    public ConcernComment editConcernComment(Long commentId, String title, String content, String[] tags) throws Exception {
        ConcernComment comment = bctDAO.getConcernCommentById(commentId);
        
        if (comment==null) throw new Exception("can't find the specified comment");
            
        User owner = userDAO.getUserById(WebUtils.currentUserId(), true, false);
        
        if (owner.getId().equals(comment.getOwner().getId())) {
            throw new Exception("You are not the owner of this comment");
        }
        
        comment.setTitle(title);
        comment.getTags().clear();
        
        for (String tagStr : tags) {
            Tag tag = analyzer.getTag(tagStr.trim());
            
            if (tag==null) {
                tag = tagDAO.addTag(tagStr.trim(), Tag.STATUS_CANDIDATE, true);
            } else {
                tag = tagDAO.getTagByName(tag.getName());
            }
            
            comment.getTags().add(tag);
        }
        
        bctDAO.save(comment);
        
        return comment;
    }//editConcernComment()


    public void deleteConcernComment(Long commentId) throws Exception {
        ConcernComment comment = bctDAO.getConcernCommentById(commentId);
        
        if (comment==null) throw new Exception("can't find the specified comment");
        
        User owner = userDAO.getUserById(WebUtils.currentUserId(), true, false);
        
        if (!owner.getId().equals(comment.getOwner().getId())) {
            throw new Exception("You are not the owner of this comment");
        }
        
        comment.setDeleted(true);
        
        bctDAO.decreaseReplies(comment.getConcern());
        
        bctDAO.save(comment);
    }//deleteConcernComment()


    public Collection getConcernComments(Long concernId, PageSetting setting) throws Exception {
        return bctDAO.getConcernComments(concernId, setting);
    }//getConcernComments()


    public boolean setVotingOnConcernComment(Long id, boolean agree) throws Exception {
        if (!systemDAO.setVoting(YesNoVoting.TYPE_COMMENT, id, agree)) return false;
        
        ConcernComment comment = bctDAO.getConcernCommentById(id);
        
        bctDAO.increaseVoting(comment, agree);
        
        return true;
    }//setVotingOnConcernComment()


    public void increaseViews(Long concernId) throws Exception {
        bctDAO.increaseViews(concernId);
    }//increaseViews()


    public InfoObject publish(Long bctId, String title) throws Exception {
    	BCT bct = bctDAO.getBCTById(bctId);
    	bct.getId();
    	bct.getConcerns();
    	bct.getCreateTime();
    	bct.getInstruction();
    	bct.getMaxConcernPerPerson();
    	bct.getName();
    	bct.getPurpose();
    	bct.getTagRefs();
    	bct.getWorkflowId();
    	
        InfoObject infoObject = new InfoObject();
        infoObject.setTitle(title);
        infoObject.setTarget(bct);
        bctDAO.save(infoObject);
        
        return infoObject;
    }//publish()


	@Override
	public void toggleBCT(Long bctId, boolean closed) throws Exception {
		BCT bct = bctDAO.getBCTById(bctId);
		bct.setClosed(closed);
		bctDAO.save(bct);
	}//toggleBCT()


}//class BCTServiceImpl
