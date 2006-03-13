package org.pgist.workflow.pgame.brainstorming;

import java.util.Date;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.model.DiscourseObject;
import org.pgist.model.Post;
import org.pgist.wfengine.Activity;
import org.pgist.wfengine.activity.PGameActivity;
import org.pgist.workflow.WorkflowForm;


/**
 * 
 * @author kenny
 *
 */
public class BrainStormingAction extends Action {
    
    
    private BrainStormingDAO brainStormingDAO;
    
    
    public void setBrainStormingDAO(BrainStormingDAO brainStormingDAO) {
        this.brainStormingDAO = brainStormingDAO;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    synchronized BrainStorming getBrainStorming(PGameActivity activity, boolean create) throws Exception {
        BrainStorming brainStorming = brainStormingDAO.getBrainStorming(activity.getId(), activity.getCounts());
        if (brainStorming==null && create) {
            Post post = new Post();
            post.setRoot(post);
            post.setTarget(true);
            post.setOwner(null);
            post.setParent(null);
            post.setTime(new Date());
            post.setContent("What is your concern of King County Transportation?");
            
            DiscourseObject dobj = new DiscourseObject();
            dobj.setOwner(null);
            dobj.setRoot(post);
            
            brainStorming = new BrainStorming();
            brainStorming.setActivityId(activity.getId());
            brainStorming.setCounts(activity.getCounts());
            brainStorming.setDeleted(false);
            brainStorming.setOwner(null);
            brainStorming.setDiscourseObject(dobj);
            
            brainStormingDAO.saveBrainStorming(brainStorming);
        }
        
        return brainStorming;
    }//getBrainStorming()
    
    
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response
    ) throws java.lang.Exception {
        WorkflowForm wfform = (WorkflowForm) form;
        
        PGameActivity activity = wfform.getPgame();
        if (activity==null) return mapping.findForward("error");
        
        BrainStorming brainStorming = null;
        if (activity.getStatus()==Activity.STATUS_INACTIVE) {
            request.setAttribute("mode", "readonly");
            brainStorming = getBrainStorming(activity, false);
            if (brainStorming==null) return mapping.findForward("error");
        } else {
            request.setAttribute("mode", "writable");
            brainStorming = getBrainStorming(activity, true);
        }
        
        request.setAttribute("brainStorming", brainStorming);
        
        return mapping.findForward("show");
    }//execute()
    
    
}//class ReviewAction
