package org.pgist.sarp.vtt;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.pgist.sarp.bct.BCTDAO;
import org.pgist.sarp.cht.CHT;
import org.pgist.sarp.cht.CHTDAO;
import org.pgist.sarp.cht.CategoryPath;
import org.pgist.sarp.cst.CSTDAO;
import org.pgist.sarp.cst.CategoryReference;
import org.pgist.sarp.drt.InfoObject;
import org.pgist.system.SystemDAO;
import org.pgist.system.YesNoVoting;
import org.pgist.tagging.TagDAO;
import org.pgist.users.User;
import org.pgist.util.PageSetting;
import org.pgist.util.WebUtils;


/**
 * 
 * @author kenny
 *
 */
public class VTTServiceImpl implements VTTService {
    
    
    private BCTDAO bctDAO = null;

    private CSTDAO cstDAO = null;
    
    private CHTDAO chtDAO = null;
    
    private VTTDAO vttDAO = null;
    
    private TagDAO tagDAO = null;
    
    private SystemDAO systemDAO = null;

    
    public void setCstDAO(CSTDAO cstDAO) {
        this.cstDAO = cstDAO;
    }


    public void setBctDAO(BCTDAO bctDAO) {
        this.bctDAO = bctDAO;
    }


    public void setChtDAO(CHTDAO chtDAO) {
        this.chtDAO = chtDAO;
    }


    public VTTDAO getVttDAO() {
        return vttDAO;
    }


    public void setVttDAO(VTTDAO vttDAO) {
        this.vttDAO = vttDAO;
    }


    public void setTagDAO(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }


    public void setSystemDAO(SystemDAO systemDAO) {
        this.systemDAO = systemDAO;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void toggleVTT(Long vttId, boolean closed) throws Exception {
        VTT vtt = vttDAO.getVTTById(vttId);
        vtt.setClosed(closed);
        vttDAO.save(vtt);
    } //toggleVTT()
    
    
    public InfoObject publish(Long vttId, String title) throws Exception {
        VTT vtt = vttDAO.getVTTById(vttId);
        vtt.getId();
        vtt.getPaths();
        vtt.getInstruction();
        vtt.getName();
        vtt.getPurpose();
        
        InfoObject infoObject = new InfoObject();
        infoObject.setTitle(title);
        infoObject.setTarget(vtt);
        
        cstDAO.save(infoObject);
        
        return infoObject;
    } //publish()
    
    
    public VTT createVTT(Long id, Long chtId, String name, String purpose, String instruction) throws Exception {
        CHT cht = chtDAO.getCHTById(chtId);
        if (cht==null) throw new Exception("can not find the specified object");
        
        VTT vtt = new VTT();
        vtt.setCht(cht);
        vtt.setClosed(false);
        vtt.setName(name);
        vtt.setInstruction(instruction);
        vtt.setPurpose(purpose);
        
        vtt.getPaths().addAll(chtDAO.getPathsByChtId(chtId, null));
        
        vttDAO.save(vtt);
        
        return vtt;
    } //createVTT()


    public VTT getVTTById(Long vttId) throws Exception {
        return (VTT) vttDAO.load(VTT.class, vttId);
    } //getVTTById()


    public Collection<VTTComment> getComments(Long userId, Long vttId, PageSetting setting) throws Exception {
        return vttDAO.getComments(userId, vttId, setting);
    } //getComments()


    public VTTComment createComment(Long userId, Long vttId, String title, String content, boolean emailNotify) throws Exception {
        User user = systemDAO.getUserById(userId);
        VTT vtt = vttDAO.getVTTById(vttId);
        
        VTTComment comment = new VTTComment();
        comment.setAuthor(cstDAO.getUserById(WebUtils.currentUserId()));
        comment.setVtt(vtt);
        comment.setOwner(user);
        comment.setTitle(title);
        comment.setContent(content);
        comment.setCreateTime(new Date());
        comment.setNumAgree(1);
        comment.setNumVote(1);
        comment.setEmailNotify(emailNotify);
        
        cstDAO.save(comment);
        
        systemDAO.setVoting(YesNoVoting.TYPE_SARP_VTT_COMMENT, comment.getId(), true);
        
        return comment;
    } //createComment()


    public void deleteComment(VTTComment comment) throws Exception {
        comment.setDeleted(true);
        cstDAO.save(comment);
    } //deleteComment()


    public VTTComment getCommentById(Long cid) throws Exception {
        return (VTTComment) vttDAO.load(VTTComment.class, cid);
    } //getCommentById()


    public VTTComment setVotingOnComment(Long cid, boolean agree) throws Exception {
        VTTComment comment = (VTTComment) cstDAO.load(VTTComment.class, cid);
        if (comment==null) throw new Exception("can't find the specified Comment with id "+cid);
        
        systemDAO.setVoting(YesNoVoting.TYPE_SARP_VTT_COMMENT, cid, agree);
        
        vttDAO.increaseVoting(comment, agree);
        
        return comment;
    } //setVotingOnComment()


    public CategoryPathValue getCategoryPathValueByPathId(Long userId, Long pathId) throws Exception {
        return vttDAO.getCategoryPathValueByPathId(userId, pathId);
    } //getCategoryValueById()


    public void saveCategoryPathValue(Long userId, Long pathId, String name, String unit) throws Exception {
        CategoryPathValue catValue = vttDAO.getCategoryPathValueByPathId(userId, pathId);
        
        if (catValue==null) {
            User user = systemDAO.getUserById(userId);
            CategoryPath path = (CategoryPath) vttDAO.load(CategoryPath.class, pathId);
            catValue = new CategoryPathValue(path, user);
        }
        
        catValue.setName(name);
        catValue.setCriterion(unit);
        if (name==null || name.trim().length()==0) {
            catValue.setTag(false);
        } else {
            catValue.setTag(true);
        }
        
        vttDAO.save(catValue);
    } //saveCategoryValue()


    public void publish(Long vttId, Long userId) throws Exception {
        VTT vtt = vttDAO.getVTTById(vttId);
        User user = systemDAO.getUserById(userId);
        
        vtt.getUsers().add(user);
        
        vttDAO.save(vtt);
    } //publish()


    public CategoryReference getCategoryReferenceById(Long id) throws Exception {
        return chtDAO.getCategoryReferenceById(id);
    } //getCategoryReferenceById()


    public VTTSpecialistComment createSpecialistComment(Long vttId, String title, String content, boolean emailNotify) throws Exception {
        VTT vtt = vttDAO.getVTTById(vttId);
        
        if (vtt==null) throw new Exception("can't find the specified VTT with id "+vttId);
        
        VTTSpecialistComment comment = new VTTSpecialistComment();
        comment.setAuthor(cstDAO.getUserById(WebUtils.currentUserId()));
        comment.setOwner(comment.getAuthor());
        comment.setVtt(vtt);
        comment.setTitle(title);
        comment.setContent(content);
        comment.setCreateTime(new Date());
        comment.setNumAgree(1);
        comment.setNumVote(1);
        comment.setEmailNotify(emailNotify);
        
        cstDAO.save(comment);
        
        systemDAO.setVoting(YesNoVoting.TYPE_SARP_VTT_SPEC_COMMENT, comment.getId(), true);
        
        return comment;
    } //createSpecialistComment()


    @Override
    public void setClusteredPaths(Long vttId) throws Exception {
        VTT vtt = vttDAO.getVTTById(vttId);
        
        if (vtt==null) throw new Exception("can't find the specified VTT with id "+vttId);
        
        //cluster paths
        for (CategoryPath path : vtt.getPaths()) {
            MUnitSet mUnitSet = new MUnitSet();
            mUnitSet.setPath(path);
            Map<String, Integer> freqs = mUnitSet.getFreqs();
            for (CategoryPathValue value : vttDAO.getCategoryPathValuesByPathId(path.getId())) {
                String name = value.getCriterion();
                Integer freq = freqs.get(name);
                if (freq==null) {
                    freqs.put(name, 1);
                } else {
                    freqs.put(name, freq + 1);
                }
            }
            
            vttDAO.save(mUnitSet);
        }
    } //setClusteredPaths()


    @Override
    public CategoryPath getCategoryPathById(Long pathId) throws Exception {
        return (CategoryPath) vttDAO.load(CategoryPath.class, pathId);
    } //getCategoryPathById()


    @Override
    public MUnitSet getMUnitSetByPathId(Long pathId) throws Exception {
        return vttDAO.getMUnitSetByPathId(pathId);
    } //getMUnitSetByPathId()


    public Collection<VTTSpecialistComment> getSpecialistComments(Long userId, Long vttId, PageSetting setting) throws Exception {
        return vttDAO.getSpecialistComments(userId, vttId, setting);
    } //getComments()


    public VTTSpecialistComment getSpecialistCommentById(Long cid) throws Exception {
        return (VTTSpecialistComment) vttDAO.load(VTTSpecialistComment.class, cid);
    } //getSpecialistCommentById()


    public void deleteSpecialistComment(VTTSpecialistComment comment) throws Exception {
        comment.setDeleted(true);
        cstDAO.save(comment);
    } //deleteSpecialistComment()


    public VTTSpecialistComment setVotingOnSpecialistComment(Long cid, boolean agree) throws Exception {
        VTTSpecialistComment comment = (VTTSpecialistComment) cstDAO.load(VTTSpecialistComment.class, cid);
        if (comment==null) throw new Exception("can't find the specified SpecialistComment with id "+cid);
        
        systemDAO.setVoting(YesNoVoting.TYPE_SARP_VTT_SPEC_COMMENT, cid, agree);
        
        vttDAO.increaseSpecialistVoting(comment, agree);
        
        return comment;
    } //setVotingOnComment()


} //class VTTServiceImpl
