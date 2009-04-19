package org.pgist.sarp.vtt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
        
        for (CategoryPath path : chtDAO.getPathsByChtId(chtId, null)) {
            if (path.getNumAgree()>path.getNumVote()/2.0) {
                vtt.getPaths().add(path);
            }
        }
        
        vttDAO.save(vtt);
        
        return vtt;
    } //createVTT()


    public VTT getVTTById(Long vttId) throws Exception {
        return (VTT) vttDAO.load(VTT.class, vttId);
    } //getVTTById()


    public Collection<VTTComment> getComments(Long userId, Long vttId, PageSetting setting) throws Exception {
        return vttDAO.getComments(userId, vttId, setting);
    } //getComments()


    public VTTComment createComment(Long workflowId, Long userId, Long vttId, String title, String content, boolean emailNotify) throws Exception {
        VTT vtt = vttDAO.getVTTById(vttId);
        
        VTTComment comment = new VTTComment();
        comment.setWorkflowId(workflowId);
        comment.setAuthor(cstDAO.getUserById(WebUtils.currentUserId()));
        User user = systemDAO.getUserById(userId);
        comment.setOwner(user);
        comment.setVtt(vtt);
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


    public void saveCategoryPathValue(Long userId, Long pathId, String name, String unit, boolean isTag) throws Exception {
        CategoryPathValue catValue = vttDAO.getCategoryPathValueByPathId(userId, pathId);
        
        if (catValue==null) {
            User user = systemDAO.getUserById(userId);
            CategoryPath path = (CategoryPath) vttDAO.load(CategoryPath.class, pathId);
            catValue = new CategoryPathValue(path, user, isTag);
        }
        
        catValue.setTag(isTag);
        catValue.setName(name);
        catValue.setCriterion(unit);
        if (name==null || name.trim().length()==0) {
            catValue.setTag(false);
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


    public VTTSpecialistComment createSpecialistComment(Long workflowId, Long vttId, Long targetUserId, String title, String content, boolean emailNotify) throws Exception {
        VTT vtt = vttDAO.getVTTById(vttId);
        
        if (vtt==null) throw new Exception("can't find the specified VTT with id "+vttId);
        
        User owner = vttDAO.getUserById(targetUserId);
        if (owner==null) throw new Exception("Can't find the targeted user.");
        
        VTTSpecialistComment comment = new VTTSpecialistComment();
        comment.setWorkflowId(workflowId);
        comment.setAuthor(cstDAO.getUserById(WebUtils.currentUserId()));
        comment.setOwner(owner);
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
        
        TreeMap<String, MUnitSet> sets = new TreeMap<String, MUnitSet>();
        
        //cluster paths
        for (CategoryPath path : vtt.getPaths()) {
            sets.clear();
            
            for (CategoryPathValue value : vttDAO.getCategoryPathValuesByPathId(path.getId())) {
                String name = value.getName();
                String unit = value.getCriterion();
                
                MUnitSet mUnitSet = sets.get(name);
                if (mUnitSet==null) {
                    mUnitSet = new MUnitSet();
                    mUnitSet.setName(name);
                    mUnitSet.setPath(path);
                    sets.put(name, mUnitSet);
                }
                
                Map<String, Integer> freqs = mUnitSet.getFreqs();
                
                Integer freq = freqs.get(unit);
                if (freq==null) {
                    freqs.put(unit, 1);
                } else {
                    freqs.put(unit, freq + 1);
                }
            }
            
            for (MUnitSet mUnitSet : sets.values()) {
                vttDAO.save(mUnitSet);
            }
        }
    } //setClusteredPaths()
    
    


    @Override
    public CategoryPath getCategoryPathById(Long pathId) throws Exception {
        return (CategoryPath) vttDAO.load(CategoryPath.class, pathId);
    } //getCategoryPathById()
    
    


    @Override
    public List<MUnitSet> getMUnitSetsByPathId(Long pathId) throws Exception {
        return vttDAO.getMUnitSetsByPathId(pathId);
    } //getMUnitSetByPathId()


    
    
    @Override
    public MUnitSet getMUnitSetById(Long id) throws Exception {
        return (MUnitSet) vttDAO.load(MUnitSet.class, id);
    } //getMUnitSetByPathId()
    
    


    public Collection<VTTSpecialistComment> getSpecialistComments(Long targetUserId, Long vttId, PageSetting setting) throws Exception {
        return vttDAO.getSpecialistComments(targetUserId, vttId, setting);
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
    
    


    @Override
    public void setToggleSelection(Long pathId, String name, String type, String criterion, boolean checked) throws Exception {
        List<MUnitSet> musets = vttDAO.getMUnitSetsByPathId(pathId);
        
        Long userId = WebUtils.currentUserId();
        
        for (MUnitSet muset : musets) {
            EUnitSet euset = muset.getExpUnits().get(userId);
            if (euset==null) {
                euset = new EUnitSet();
                muset.getExpUnits().put(userId, euset);
            }
            
            if (!muset.getName().equals(name)) {
                if ("rec".equalsIgnoreCase(type)) {
                    Map<String, Boolean> recs = euset.getRecs();
                    recs.clear();
                }
            } else {
                if ("appr".equalsIgnoreCase(type)) {
                    Map<String, Boolean> apprs = euset.getApprs();
                    apprs.put(criterion, checked);
                } else if ("avail".equalsIgnoreCase(type)) {
                    Map<String, Boolean> avails = euset.getAvails();
                    avails.put(criterion, checked);
                } else if ("dup".equalsIgnoreCase(type)) {
                    Map<String, Boolean> dups = euset.getDups();
                    dups.put(criterion, checked);
                } else if ("rec".equalsIgnoreCase(type)) {
                    Map<String, Boolean> recs = euset.getRecs();
                    recs.clear();
                    if (criterion!=null && criterion.length()>0) {
                        recs.put(criterion, checked);
                    }
                }
            }
            
            vttDAO.save(euset);
            vttDAO.save(muset);
        }
    } //setToggleSelection()
    
    


    @Override
    public void publishExpertUnits(Long vttId) throws Exception {
        VTT vtt = vttDAO.getVTTById(vttId);
        User user = vttDAO.getUserById(WebUtils.currentUserId());
        vtt.getExperts().add(user);
        vttDAO.save(user);
    } //publishExpertUnits()


    @Override
    
    
    public void setUnitComment(Long pathId, String content, String source) throws Exception {
        ExpertPathComment comment = vttDAO.getExpertPathComment(pathId, WebUtils.currentUserId());
        if (comment==null) {
            comment = new ExpertPathComment();
            comment.setOwner(vttDAO.getUserById(WebUtils.currentUserId()));
            comment.setPath(vttDAO.getCategoryPathById(pathId));
        }
        comment.setContent(content);
        comment.setSource(source);
        
        vttDAO.save(comment);
    } //setUnitComment()


    
    
    @Override
    public void setClusteredExpertsSelections(Long vttId) throws Exception {
        VTT vtt = vttDAO.getVTTById(vttId);
        
        for (CategoryPath path : vtt.getPaths()) {
            List<MUnitSet> msets = vttDAO.getMUnitSetsByPathId(path.getId());
            
            // the default selection
            MUnitSet def = new MUnitSet();
            def.setName("");
            def.setPath(path);
            vttDAO.save(def);
            
            msets.add(def);
            
            for (MUnitSet mset : msets) {
                Map<String, Integer> apprFreqs = mset.getApprFreqs();
                Map<String, Integer> availFreqs = mset.getAvailFreqs();
                Map<String, Integer> dupFreqs = mset.getDupFreqs();
                Map<String, Integer> recFreqs = mset.getRecoFreqs();
                
                for (String unit : mset.getFreqs().keySet()) {
                    apprFreqs.put(unit, 0);
                    availFreqs.put(unit, 0);
                    dupFreqs.put(unit, 0);
                    recFreqs.put(unit, 0);
                }
                
                for (Map.Entry<Long, EUnitSet> entry : mset.getExpUnits().entrySet()) {
                    for (Map.Entry<String, Boolean> apprEntry : entry.getValue().getApprs().entrySet()) {
                        String unit = apprEntry.getKey();
                        Integer count = apprFreqs.get(unit);
                        if (count==null) {
                            apprFreqs.put(unit, 1);
                        } else {
                            apprFreqs.put(unit, count+1);
                        }
                    }
                    
                    for (Map.Entry<String, Boolean> availEntry : entry.getValue().getAvails().entrySet()) {
                        String unit = availEntry.getKey();
                        Integer count = availFreqs.get(unit);
                        if (count==null) {
                            availFreqs.put(unit, 1);
                        } else {
                            availFreqs.put(unit, count+1);
                        }
                    }
                    
                    for (Map.Entry<String, Boolean> dupEntry : entry.getValue().getDups().entrySet()) {
                        String unit = dupEntry.getKey();
                        Integer count = dupFreqs.get(unit);
                        if (count==null) {
                            dupFreqs.put(unit, 1);
                        } else {
                            dupFreqs.put(unit, count+1);
                        }
                    }
                    
                    for (Map.Entry<String, Boolean> recoEntry : entry.getValue().getRecs().entrySet()) {
                        String unit = recoEntry.getKey();
                        Integer count = recFreqs.get(unit);
                        if (count==null) {
                            recFreqs.put(unit, 1);
                        } else {
                            recFreqs.put(unit, count+1);
                        }
                    }
                }
                
                vttDAO.save(mset);
            }
        }
    } //setClusteredExpertsSelections()
    
    


    @Override
    public void saveSelection(Long pathId, Long musetId, Long userId, String unit) throws Exception {
        List<MUnitSet> msets = vttDAO.getMUnitSetsByPathId(pathId);
        for (MUnitSet mset : msets) {
            if (!mset.getId().equals(musetId)) {
                mset.getUserSelections().remove(userId);
            } else {
                mset.getUserSelections().put(userId, unit);
            }
            vttDAO.save(mset);
        }
    } //saveSelection()
    
    


    @Override
    public CategoryPath createPath(Long vttId, String pathIds) throws Exception {
        VTT vtt = vttDAO.getVTTById(vttId);
        CategoryPath path = new CategoryPath();
        path.setCht(vtt.getCht());
        path.setFrequency(0);
        path.setNumAgree(0);
        path.setNumVote(0);
        
        String[] ids = pathIds.split(",");
        
        for (String id : ids) {
            if (id==null || id.trim().length()==0) continue;
            
            CategoryReference catRef = chtDAO.getCategoryReferenceById(new Long(id));
            path.getCategories().add(catRef);
        }
        path.genTitle();
        
        if (!vttDAO.checkPath(vttId, path.getTitle())) {
            vttDAO.save(path);
        } else {
            throw new Exception("path already exists!");
        }
        
        vtt.getPaths().add(path);
        
        return path;
    } //createPath()
    
    


    @Override
    public void deletePathById(Long vttId, Long pathId) throws Exception {
        VTT vtt = vttDAO.getVTTById(vttId);
        CategoryPath path = vttDAO.getCategoryPathById(pathId);
        vtt.getPaths().remove(path);
        vttDAO.save(vtt);
    }
    
    
    @Override
    public Set<User> getThreadUsers(Long ownerId, Long vttId) throws Exception {
        return vttDAO.getThreadUsers(ownerId, vttId);
    }


    @Override
    public ExpertPathComment getExpertPathComment(Long pathId, Long userId) throws Exception {
        return vttDAO.getExpertPathComment(pathId, userId);
    } //getExpertPathComment()


    @Override
    public void addIndicator(Long pathId, String indicator) throws Exception {
        List<MUnitSet> musets = vttDAO.getMUnitSetsByPathId(pathId);
        for (MUnitSet muset : musets) {
            if (indicator.equalsIgnoreCase(muset.getName())) {
                throw new Exception("this indicator already exists");
            }
        }
        
        MUnitSet muset = new MUnitSet();
        muset.setName(indicator);
        muset.setPath(vttDAO.getCategoryPathById(pathId));
        muset.setUserSelections(new HashMap<Long, String>());
        muset.setApprFreqs(new HashMap<String, Integer>());
        muset.setAvailFreqs(new HashMap<String, Integer>());
        muset.setDupFreqs(new HashMap<String, Integer>());
        muset.setRecoFreqs(new HashMap<String, Integer>());
        muset.setExpUnits(new HashMap<Long, EUnitSet>());
        muset.setFreqs(new HashMap<String, Integer>());
        vttDAO.save(muset);
    } //addIndicator()


    @Override
    public void deleteIndicator(Long musetId) throws Exception {
        MUnitSet muset = vttDAO.getMUnitSetById(musetId);
        vttDAO.delete(muset);
    } //deleteIndicator()


    @Override
    public void addUnit(Long musetId, String unit) throws Exception {
        MUnitSet muset = vttDAO.getMUnitSetById(musetId);
        
        muset.getApprFreqs().put(unit, 0);
        muset.getAvailFreqs().put(unit, 0);
        muset.getDupFreqs().put(unit, 0);
        muset.getRecoFreqs().put(unit, 0);
        muset.getFreqs().put(unit, 0);
        
        vttDAO.save(muset);
    } //addUnit()


    @Override
    public void deleteUnit(Long musetId, String unit) throws Exception {
        MUnitSet muset = vttDAO.getMUnitSetById(musetId);
        
        muset.getApprFreqs().remove(unit);
        muset.getAvailFreqs().remove(unit);
        muset.getDupFreqs().remove(unit);
        muset.getRecoFreqs().remove(unit);
        muset.getFreqs().remove(unit);
        
        vttDAO.save(muset);
    } //deleteUnit()


} //class VTTServiceImpl
