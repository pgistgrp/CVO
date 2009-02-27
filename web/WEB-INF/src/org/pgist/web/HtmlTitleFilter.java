package org.pgist.web;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.BodyTag;
import org.htmlparser.tags.HeadTag;
import org.htmlparser.tags.ScriptTag;
import org.htmlparser.tags.StyleTag;
import org.htmlparser.tags.TitleTag;


/**
 * 
 * @author kenny
 *
 */
public class HtmlTitleFilter implements NodeFilter {
    
    
    private String title = "";
    
    private StringBuilder body = new StringBuilder();
    
    private int defaultDepth = 0;
    
    private int depth = 0;
    
    
    public HtmlTitleFilter(boolean completeHtml) {
        if (completeHtml) {
            defaultDepth = 0;
        } else {
            defaultDepth = -1;
        }
        
        depth = defaultDepth;
    }
    
    
    public boolean accept(Node node) {
        System.out.println(node.getClass().getName()+" ----> "+node.getText());
        
        if (node instanceof HeadTag) {
            depth--;
        } else if (node instanceof TagNode && "/head".equalsIgnoreCase(node.getText())) {
            depth++;
        } else if (node instanceof BodyTag) {
            depth--;
        } else if (node instanceof TagNode && "/body".equalsIgnoreCase(node.getText())) {
            depth++;
        } else if (node instanceof TitleTag) {
            TitleTag titleTag = (TitleTag) node;
            title = titleTag.getTitle();
        } else if (node instanceof ScriptTag) {
            depth++;
        } else if (node instanceof TagNode && "/script".equalsIgnoreCase(node.getText())) {
            depth--;
        } else if (node instanceof StyleTag) {
            depth++;
        } else if (node instanceof TagNode && "/style".equalsIgnoreCase(node.getText())) {
            depth--;
        } else if (depth<0 && node instanceof TextNode) {
            body.append(node.getText()).append(' ');
        }
        
        return true;
    }//accept()
    
    
    public String getTitle() {
        return title;
    }
    
    
    public String getBody() {
        return body.toString();
    }
    
    
    public void reset() {
        title = "";
        body.delete(0, body.length());
        depth = defaultDepth;
    }
    
    
}//class HtmlTitleFilter
