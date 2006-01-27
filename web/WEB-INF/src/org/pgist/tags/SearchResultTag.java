package org.pgist.tags;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.pgist.cvo.CVO;
import org.pgist.model.Post;


/**
 * 
 * @author kenny
 *
 */
public class SearchResultTag extends TagSupport {

    
    private static final long serialVersionUID = -4358153734927746923L;

    private Map object;
    
    
    public void setObject(Map object) {
        this.object = object;
    }


    public int doStartTag() throws JspException {
        return SKIP_BODY;
    }//doStartTag()


    public int doEndTag() throws JspException {
        HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
        String ctxPath = req.getContextPath();
        JspWriter writer = pageContext.getOut();
        try {
            String type = (String) object.get("type");
            if ("concern".equals(type) || "comment".equals(type)) {
                CVO cvo = (CVO) object.get("cvo");
                writer.write("<a href=\""+ctxPath+"/cvoview.do?id="+cvo.getId()+"\">[CVO]</a><br>");
                Post post = (Post) object.get("post");
                String content = post.getContent();
                if (content!=null && content.length()>150) content = content.substring(0, 150) + " ...";
                writer.write(content);
                writer.write("<br><br>");
            }
        } catch (IOException e) {
            throw new JspException(e);
        }
        
        return EVAL_PAGE;
    }//doEndTag()


}//class SearchResultTag
