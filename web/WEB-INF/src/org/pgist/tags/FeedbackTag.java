package org.pgist.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;


/**
 * 
 * @author kenny
 *
 */
public class FeedbackTag extends SimpleTagSupport {
    
    
    private static final long serialVersionUID = 1L;
    
    
    private String id = "feedback";
    
    private String style = "";
    
    private String styleClass = "";
    
    private String action = "";
    
    
    public void setId(String id) {
        this.id = id;
    }


    public void setStyle(String style) {
        this.style = style;
    }


    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }


    public void setAction(String action) {
        this.action = action;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void doTag() throws JspException, IOException {
        JspWriter writer = getJspContext().getOut();
        
        writer.write("<div id=\"feedbackForm\" class=\"box2\" style=\"display:none;width:690px;padding:10px;text-align:left;\">");
        writer.write("<div id=\"feedbackCloseBox\">");
        writer.write("<a href=\"javascript:Effect.toggle('feedbackForm','blind');\"><img src=\"/images/closeinactivesm.gif\" alt=\"close this form\" onmouseover=\"javascript:this.src='/images/closeactivesm.gif'; this.style.cursor='pointer'; \" onmouseout=\"javascript:this.src='/images/closeinactivesm.gif'; this.style.cursor='auto';\" border=\"0\" class=\"floatRight\"></a></div>");
        writer.write("<h3 class=\"headerColor clearBoth\">Feedback/Bug Report Form</h3>");
        
        writer.write("<script src=\"/dwr/interface/SystemAgent.js\"></script>");
        writer.write("<script>var detect = '<p>***USER SYSTEM INFO*** </p>';detect += navigator.userAgent;function createFeedback() {  s = $('feedback_input').value;  if (s.length==0) { alert('Please input your feedback.'); return; }  s += detect;   action = 'cctView.do';  SystemAgent.createFeedback(    {feedback:s, action:action}, function(data) {      if (data.successful) { alert('Your feedback is submitted. Thank you.'); $('feedback_input').value=''; }      else { alert(data.reason); }    }  );}</script>");
        writer.write("<div id=\"feedbackDiv\"><fieldset>");
        
        writer.write("<textarea id=\"feedback_input\" style=\"width:100%;height:100px;border:1px solid #B5D77B;\" onclick=\"if(this.value==this.defaultValue){this.value = ''}\">If you came across a bug, please help us by reporting it to our development team. Please describe bugs and issues in as much detail as possible.</textarea><br>");
        
        writer.write("<input value=\"Submit\" onclick=\"createFeedback();new Effect.Fade('feedbackForm');\" type=\"button\" class=\"floatRight\"></div></fieldset></div>");

        writer.write("<p>Got a question? Having trouble using this website? <a href=\"#feedbackForm\" onclick=\"javascript:Effect.toggle('feedbackForm','blind'); setTimeout('location.hash=\\\'#feedbackDiv\\\';',900);\">Send us feedback.</a></p>"); 
        
        //writer.write("<script src=\"http://www.google-analytics.com/urchin.js\" type=\"text/javascript\"></script>");
        //writer.write("<script type=\"text/javascript\">_uacct = \"UA-797433-1\";urchinTracker();</script>");
    }//doTag()
    
    
}//class FeedbackTag
