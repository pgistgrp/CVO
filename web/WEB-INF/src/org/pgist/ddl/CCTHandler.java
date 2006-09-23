package org.pgist.ddl;

import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.pgist.cvo.CCT;
import org.pgist.cvo.CategoryReference;
import org.pgist.users.User;


/**
 * 
 * @author kenny
 *
 */
public class CCTHandler extends Handler {
    
    
    public void doImports(Element root) throws Exception {
        List ccts = root.elements("cct");
        
        for (int i=0,n=ccts.size(); i<n; i++) {
            Element element = (Element) ccts.get(i);
            
            
            String name = element.elementTextTrim("name");
            if (name==null || "".equals(name)) throw new Exception("name is required for cct");
            
            CCT cct = getCCTByName(name);
            if (cct==null) {
                cct = new CCT();
                cct.setName(name);
                cct.setDeleted(false);
            }
            
            String loginname = element.elementTextTrim("creator");
            User creator = getUserByLoginName(loginname);
            if (creator==null) throw new Exception("creator is not found: "+loginname);
            cct.setCreator(creator);
            
            String createTimeStr = element.elementTextTrim("createTime");
            if (createTimeStr==null || "".equals(createTimeStr)) {
                cct.setCreateTime(new Date());
            } else {
                Date createTime = format.parse(createTimeStr);
                cct.setCreateTime(createTime);
            }
            
            String purpose = element.elementTextTrim("purpose");
            if (purpose==null || "".equals(purpose)) throw new Exception("purpose is required for cct");
            
            String instruction = element.elementTextTrim("instruction");
            if (instruction==null || "".equals(instruction)) throw new Exception("instruction is required for cct");
            
            cct.setPurpose(purpose);
            cct.setInstruction(instruction);
            
            CategoryReference catRef = new CategoryReference();
            catRef.setCct(cct);
            
            cct.setRootCategory(catRef);
            
            saveCCT(cct);
        }//for i
    }//imports()
    
    
    public void doExports(Document document) throws Exception {
        Element root = document.addElement("ccts");
        
        List<CCT> ccts = getCCTs();
        
        for (CCT cct : ccts) {
            Element one = root.addElement("cct");
            
            Element creator = one.addElement("creator");
            creator.addAttribute("type", "loginname");
            creator.setText(cct.getCreator().getLoginname());
            
            Element name = one.addElement("name");
            name.setText(cct.getName());
            
            Element purpose = one.addElement("purpose");
            purpose.setText(cct.getPurpose());
            
            Element instruction = one.addElement("instruction");
            instruction.setText(cct.getInstruction());
            
            Element createTime = one.addElement("createTime");
            createTime.setText(format.format(cct.getCreateTime()));
        }//for
    }//doExports()
    
    
}//class RoleHandler
