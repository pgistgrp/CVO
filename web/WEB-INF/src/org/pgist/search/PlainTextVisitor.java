package org.pgist.search;

import java.util.HashSet;
import java.util.Set;

import org.htmlparser.Tag;
import org.htmlparser.Text;
import org.htmlparser.visitors.NodeVisitor;


/**
 * @author kenny
 *
 */
public class PlainTextVisitor extends NodeVisitor {
    
    private StringBuilder sb = new StringBuilder();
    
    private boolean output = true;
    
    private static Set<String> tags = new HashSet<String>();
    
    static {
        tags.add("SCRIPT");
        tags.add("STYLE");
    }
    
    public PlainTextVisitor() {
    }
    
    public void visitStringNode(Text string) {
        if (output) {
            sb.append(string.toPlainTextString());
        }
    }
    
    public void visitTag(Tag tag) {
        if (tags.contains(tag.getTagName().toUpperCase())) {
            output = false;
        }
    }
    
    public void visitEndTag(Tag tag) {
        if (tags.contains(tag.getTagName().toUpperCase())) {
            output = true;
        }
    }
    
    public String getText() {
        return sb.toString();
    }
}
