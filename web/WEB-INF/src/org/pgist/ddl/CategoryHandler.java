package org.pgist.ddl;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.pgist.cvo.Category;
import org.pgist.cvo.Tag;


/**
 * 
 * @author kenny
 *
 */
public class CategoryHandler extends Handler {
    
    
    public void doImports(Element root) throws Exception {
        Category rootCategory = new Category();
        rootCategory.setDeleted(false);
        rootCategory.setName("");
        rootCategory.setParent(null);
        saveCategory(rootCategory);
        
        List categories = root.elements("category");
        for (int i=0,n=categories.size(); i<n; i++) {
            Element element = (Element) categories.get(i);
            
            Category category = new Category();
            
            String name = element.attributeValue("name");
            if (name==null || "".equals(name)) throw new Exception("name is required for category");
            category.setName(name);
            category.setDeleted(false);
            
            Category parent = rootCategory;
            String parentName = element.attributeValue("parent");
            if (parentName!=null && !"".equals(parentName)) {
                parent = getCategoryByName(parentName);
            }
            category.setParent(parent);
            
            List tags = element.elements("tag");
            for (int j=0,m=tags.size(); j<m; j++) {
                Element tagElement = (Element) tags.get(j);
                String tagName = tagElement.getTextTrim();
                Tag tag = getTagByName(tagName);
                if (tag==null) {
                    tag = new Tag();
                    tag.setName(tagName);
                    tag.setDescription(tagName);
                    tag.setCount(0);
                    tag.setStatus(Tag.STATUS_OFFICIAL);
                    saveTag(tag);
                }
                category.getTags().add(tag);
            }//for j
            
            saveCategory(category);
            parent.getChildren().add(category);
        }//for i
        saveCategory(rootCategory);
    }//imports()
    
    
    public void doExports(Document document) throws Exception {
        Element root = document.addElement("categories");
        
        List<Category> categories = getCategories();
        
        for (Category category : categories) {
            Category parent = category.getParent();
            if (parent==null) continue;
            
            Element one = root.addElement("category");
            one.addAttribute("name", category.getName());
            if (!"".equals(parent.getName())) one.addAttribute("parent", parent.getName());
            
            Element tags = one.addElement("tags");
            for (Iterator iter=category.getTags().iterator(); iter.hasNext(); ) {
                Tag tag = (Tag) iter.next();
                Element two = tags.addElement("tag");
                two.setText(tag.getName());
            }//for iter
        }//for user
    }//doExports()
    
    
}//class CategoryHandler
