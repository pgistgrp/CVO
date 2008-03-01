package org.pgist.tags;

import java.util.List;
import java.util.Stack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.pgist.sarp.cst.CategoryReference;


/**
 * Depth first iterator tag for {@link CategoryReference}.
 * 
 * @author kenny
 *
 */
public class DFIteratorTag extends TagSupport {
    
    
    private static final long serialVersionUID = 1L;
    
    
    private String var = "";
    
    private String level = "";
    
    private CategoryReference root = null;
    

    public void setVar(String var) {
        this.var = var;
    }


    public void setLevel(String level) {
        this.level = level;
    }


    public void setRoot(CategoryReference root) {
        this.root = root;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    private Stack<CategoryReference> stack = new Stack<CategoryReference>();
    private Stack<Integer> stack1 = new Stack<Integer>();
    
    private CategoryReference catRef = null;
    private int iLevel = 0;
    
    public int doStartTag() throws JspException {
        HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
        
        if (root==null) throw new JspException("root is null");
        
        List<CategoryReference> list = root.getChildren();
        for (int i=list.size()-1; i>-1; i--) {
            stack.push(list.get(i));
            stack1.push(0);
        }
        
        if (stack.isEmpty()) {
            return SKIP_BODY;
        } else {
            catRef = stack.pop();
            iLevel = stack1.pop();
            req.setAttribute(var, catRef);
            req.setAttribute(level, iLevel);
            return EVAL_BODY_INCLUDE;
        }
    }//doStartTag()
    
    
    public int doAfterBody() throws JspException {
        HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
        
        List<CategoryReference> list = catRef.getChildren();
        for (int i=list.size()-1; i>-1; i--) {
            stack.push(list.get(i));
            stack1.push(iLevel+1);
        }
        
        if (stack.isEmpty()) {
            return SKIP_BODY;
        } else {
            catRef = stack.pop();
            iLevel = stack1.pop();
            req.setAttribute(var, catRef);
            req.setAttribute(level, iLevel);
            return EVAL_BODY_AGAIN;
        }
    }//doAfterBody()


}//class DFIteratorTag
