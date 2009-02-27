package org.pgist.ddl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.pgist.cvo.CCT;
import org.pgist.cvo.Concern;
import org.pgist.cvo.TagReference;
import org.pgist.tagging.Tag;
import org.pgist.users.User;


/**
 * 
 * @author kenny
 *
 */
public class ConcernHandler extends XMLHandler {
    
    
    public void doImports(Element root) throws Exception {
        List concerns = root.elements("concern");
        for (int i=0,n=concerns.size(); i<n; i++) {
            Element element = (Element) concerns.get(i);
            
            Concern concern = new Concern();
            concern.setCreateTime(new Date());
            concern.setReplyTime(concern.getCreateTime());
            concern.setDeleted(false);
            
            String randomStr = element.elementTextTrim("sortOrder");
            if (randomStr!=null && !"".equals(randomStr)) {
                concern.setSortOrder(Integer.parseInt(randomStr));
            } else {
                concern.setSortOrder(random.nextInt(65535));
            }
            
            String participant = element.elementTextTrim("participant");
            if (participant==null || "".equals(participant)) throw new Exception("participant is required for concern");
            
            User author = getUserByLoginName(participant);
            if (author==null) throw new Exception("participant not found: "+participant);
            concern.setAuthor(author);
            
            String cctName = element.attributeValue("cct");
            CCT cct = getCCTByName(cctName);
            if (cct==null) throw new Exception("CCT not found: "+cctName);
            
            concern.setCct(cct);
            
            String content = element.elementTextTrim("content");
            if (content==null || "".equals(content)) throw new Exception("content is required for concern");
            
            concern.setContent(content);
            
            String createTimeStr = element.elementTextTrim("createTime");
            if (createTimeStr==null || "".equals(createTimeStr)) {
                concern.setCreateTime(new Date());
            } else {
                Date createTime = format.parse(createTimeStr);
                concern.setCreateTime(createTime);
            }
            
            Element tagsElement = element.element("tags");
            if (tagsElement!=null) {
                List tags = tagsElement.elements("tag");
                for (int j=0,m=tags.size(); j<m; j++) {
                    Element tagElement = (Element) tags.get(j);
                    String tagName = tagElement.getTextTrim();
                    if (tagName==null || "".equals(tagName)) throw new Exception("tag name is required for tag");
                    
                    Tag tag = getTagByName(tagName);
                    if (tag==null) {
                        tag = new Tag();
                        tag.setName(tagName);
                        tag.setStatus(parseTagStatus(element.attributeValue("status")));
                        tag.setType(Tag.TYPE_INCLUDED);
                        saveTag(tag);
                    }
                    
                    TagReference tagRef = ensureTagReference(cct, tag);
                    saveTagReference(tagRef);
                    
                    concern.getTags().add(tagRef);
                }//for j
            }
            
            cct.getConcerns().add(concern);
            saveConcern(concern);
            saveCCT(cct);
        }//for i
    }//imports()
    
    
    public void doExports(Document document) throws Exception {
        Element root = document.addElement("concerns");
        
        List<Concern> concerns = getConcerns();
        
        for (Concern concern : concerns) {
            Element one = root.addElement("concern");
            one.addAttribute("cct", concern.getCct().getName());
            
            Element participant = one.addElement("participant");
            participant.addAttribute("type", "loginname");
            participant.setText(concern.getAuthor().getLoginname());
            
            Element content = one.addElement("content");
            content.setText(concern.getContent());
            
            Element createTime = one.addElement("createTime");
            createTime.setText(format.format(concern.getCreateTime()));
            Element tags = one.addElement("tags");
            
            for (Iterator iter=concern.getTags().iterator(); iter.hasNext(); ) {
                TagReference tagRef = (TagReference) iter.next();
                Element two = tags.addElement("tag");
                two.setText(tagRef.getTag().getName());
            }//for iter
        }//for
    }//doExports()
    
    
}//class ConcernHandler
