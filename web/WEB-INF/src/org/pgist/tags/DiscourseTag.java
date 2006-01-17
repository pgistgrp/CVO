package org.pgist.tags;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.pgist.model.Post;


/**
 * 
 * @author kenny
 *
 */
public class DiscourseTag extends SimpleTagSupport {

    
    private static final long serialVersionUID = -4358153734927746923L;

    private String id;
    
    private String post;
    
    private String callback;
    
    private int depth = 0;
    
    
    public void setId(String condition) {
        this.id = condition;
    }
    
    
    public void setPost(String post) {
        this.post = post;
    }
    
    
    public void setCallback(String callback) {
        this.callback = callback;
    }


    public void setDepth(int depth) {
        if (depth<0) depth = -1;
        else this.depth = depth;
    }


    public void doTag() throws JspException, IOException {
        Post thePost = (Post) getJspContext().getAttribute(post);
        JspWriter writer = getJspContext().getOut();
        writer.write("<div id=\"");
        writer.write(id);
        writer.write("\" class=\"discourse\" width=\"100%\">");
        
        writer.write("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">");
        
        int n = thePost.getChildren().size();
        writer.write("<tr><td width=\"100%\" valign=\"top\" class=\"outtop\"");
        if (n>1) {
            writer.write(" colspan=\""+n+"\"");
        }
        writer.write(">");
        String content = thePost.getContent();
        content = content.replaceAll("\n", "<br>");
        writer.write(content);
        writer.write("<span style=\"margin-left:30px;\">--- ");
        writer.write(thePost.getOwner().getLoginname());
        writer.write("</span><span style=\"margin-left:30px;\" class=\"link\" onclick=\"");
        writer.write("if ("+callback+") {"+callback+"(");
        writer.write(""+thePost.getId());
        writer.write(");}\">[Comment]</span>");
        writer.write("</td></tr>");
        
        writer.write("<tr>");
        boolean first = true;
        for (Iterator iter=thePost.getChildren().iterator(); iter.hasNext(); ) {
            writer.write("<td valign=\"top\"");
            writer.write(">");
            renderNode((Post) (iter.next()), first, 1);
            first = false;
            writer.write("</td>");
        }//for i
        writer.write("</tr>");
        
        writer.write("</table>");
        writer.write("</div>");
    }//doTag()
    
    
    public void renderNode(Post thePost, boolean first, int depth) throws IOException {
        //HttpServletRequest req = (HttpServletRequest) this.getPageContext.getRequest();
        String ctxPath = "/cvotest"; //req.getContextPath();

        int n = thePost.getChildren().size();
        
        JspWriter writer = getJspContext().getOut();
        writer.write("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">");
        
        String styleClass = "innertop";
        if (this.depth!=-1 && depth>this.depth) {
            styleClass = "hidetop";
        }
        writer.write("<tr><td width=\"100%\" valign=\"top\" class=\"bar\"");  ////"+styleClass+"  ////gz
        
        if (n>1) {
            writer.write(" colspan=\"");
            writer.write(n);
            writer.write("\"");
        }
        writer.write(">");
        
        if (this.depth==-1 || depth<=this.depth) {
            String content = thePost.getContent();
            content = content.replaceAll("\n", "<br>");
            writer.write(content);
        }
        writer.write("<img src=\"" + ctxPath + "/images/dot.gif\" width=\"100%\" height=\"6\">");	/////gz
        writer.write("</td></tr>");
        
        writer.write("<tr>");
        first = true;
        for (Iterator iter=thePost.getChildren().iterator(); iter.hasNext(); ) {
            if (this.depth!=-1 && depth>this.depth) styleClass = "hide";
            else styleClass = "inner";
            if (first) styleClass += "left";
            else styleClass = "right";
            writer.write("<td valign=\"top\" class=\""+styleClass+"\">");
            renderNode((Post) (iter.next()), first, depth+1);
            first = false;
            writer.write("</td>");
        }//for i
        writer.write("</tr>");
        
        writer.write("</table>");
    }//renderNode()
    
    
}//class DialogTag
