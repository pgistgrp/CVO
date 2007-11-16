package org.pgist.ddl;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.pgist.cvo.CCT;
import org.pgist.users.User;


/**
 * 
 * @author kenny
 *
 */
public class CCTHandler extends Handler {
    
    
    public void doImports(Element root) throws Exception {
        List ccts = root.elements("cct");
        
        DateFormat format = DateFormat.getInstance();
        
        for (int i=0,n=ccts.size(); i<n; i++) {
            Element element = (Element) ccts.get(i);
            
            CCT cct = new CCT();
            cct.setDeleted(false);
            
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
            
            String name = element.elementTextTrim("name");
            if (name==null || "".equals(name)) throw new Exception("name is required for cct");
            
            String purpose = element.elementTextTrim("purpose");
            if (purpose==null || "".equals(purpose)) throw new Exception("purpose is required for cct");
            
            String instruction = element.elementTextTrim("instruction");
            if (instruction==null || "".equals(instruction)) throw new Exception("instruction is required for cct");
            
            cct.setName(name);
            cct.setPurpose(purpose);
            cct.setInstruction(instruction);
            
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
            createTime.setText(cct.getCreateTime().toString());
        }//for
    }//doExports()
    
    
}//class RoleHandler
