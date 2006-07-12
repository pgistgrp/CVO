package org.pgist.ddl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.Element;
import org.pgist.glossary.Term;
import org.pgist.glossary.TermCategory;
import org.pgist.glossary.TermLink;
import org.pgist.glossary.TermSource;
import org.pgist.users.User;


/**
 * 
 * @author kenny
 *
 */
public class GlossaryHandler extends Handler {
    
    
    public void doImports(Element root) throws Exception {
        List termElements = root.elements("term");
        for (int i=0,n=termElements.size(); i<n; i++) {
            Element element = (Element) termElements.get(i);
            
            String name = element.attributeValue("name");
            if (name==null || "".equals(name)) throw new Exception("name is required for term");
            
            String status = element.attributeValue("status");
            if (status==null || "".equals(status)) status = "official";
            
            Term term = getTermByName(name);
            if (term==null) {
                term = new Term();
            }
            
            name = Character.toUpperCase(name.charAt(0)) + name.substring(1).toLowerCase();
            term.setName(name);
            
            String abbreviation = element.elementTextTrim("shortDefinition");
            if (abbreviation==null) abbreviation = "";
            term.setAbbreviation(abbreviation);
            
            if ("pending".equalsIgnoreCase(status)) {
                term.setStatus(Term.STATUS_PENDING);
            } else if ("official".equalsIgnoreCase(status)) {
                term.setStatus(Term.STATUS_OFFICIAL);
            } else {
                throw new Exception("Unknown status for term: "+name);
            }
            
            term.setInitial(name.charAt(0));
            term.setDeleted(false);
            
            String shortDefinition = element.elementTextTrim("shortDefinition");
            if (shortDefinition==null) shortDefinition = "";
            term.setShortDefinition(shortDefinition);
            
            String extDefinition = element.elementTextTrim("extDefinition");
            if (extDefinition==null) extDefinition = "";
            term.setExtDefinition(extDefinition);
            
            String loginname = element.elementTextTrim("creator");
            User creator = getUserByLoginName(loginname);
            if (creator==null) throw new Exception("can't find user with loginname "+loginname);
            term.setCreator(creator);
            
            String createTimeStr = element.elementTextTrim("createTime");
            if (createTimeStr==null || "".equals(createTimeStr)) {
                term.setCreateTime(new Date());
            } else {
                Date createTime = format.parse(createTimeStr);
                term.setCreateTime(createTime);
            }
            
            Element relatedTermsElement = element.element("relatedTerms");
            if (relatedTermsElement!=null) {
                List terms = relatedTermsElement.elements("term");
                for (int j=0,m=terms.size(); j<m; j++) {
                    Element relatedTermElement = (Element) terms.get(j);
                    String relatedTermName = relatedTermElement.getTextTrim();
                    if (relatedTermName==null || "".equals(relatedTermName)) throw new Exception("related term can't be empty");
                    
                    Term relatedTerm = getTermByName(relatedTermName);
                    if (relatedTerm==null) {
                        relatedTerm = new Term();
                        relatedTerm.setName(relatedTermName);
                        relatedTerm.setCreateTime(new Date());
                        termMap.put(relatedTermName.toLowerCase(), relatedTerm);
                    }
                    
                    term.getRelatedTerms().add(relatedTerm);
                }//for j
            }
            
            Element linksElement = element.element("links");
            if (linksElement!=null) {
                List links = linksElement.elements("link");
                for (int j=0,m=links.size(); j<m; j++) {
                    Element linkElement = (Element) links.get(j);
                    String link = linkElement.getTextTrim();
                    if (link==null || "".equals(link)) throw new Exception("link can't be empty");
                    
                    TermLink termLink = new TermLink();
                    termLink.setLink(link);
                    term.getLinks().add(termLink);
                }//for j
            }
            
            Element sourcesElement = element.element("sources");
            if (sourcesElement!=null) {
                List sources = sourcesElement.elements("link");
                for (int j=0,m=sources.size(); j<m; j++) {
                    Element sourceElement = (Element) sources.get(j);
                    String source = sourceElement.getTextTrim();
                    if (source==null || "".equals(source)) throw new Exception("source can't be empty");
                    
                    TermSource termSource = new TermSource();
                    termSource.setSource(source);
                    term.getSources().add(termSource);
                }//for j
            }
            
            Element catsElement = element.element("categories");
            if (catsElement!=null) {
                List cats = catsElement.elements("category");
                for (int j=0,m=cats.size(); j<m; j++) {
                    Element catElement = (Element) cats.get(j);
                    String category = catElement.getTextTrim();
                    if (category==null || "".equals(category)) throw new Exception("category can't be empty");
                    
                    TermCategory termCat = getTermCatByName(category);
                    if (termCat==null) {
                        termCat = new TermCategory();
                        termCat.setName(category);
                        saveTermCat(termCat);
                    }
                    term.getCategories().add(termCat);
                }//for j
            }
            
            String refCount = element.elementTextTrim("refCount");
            if (refCount==null || "".equals(refCount)) term.setViewCount(0);
            else term.setViewCount(Integer.parseInt(refCount));
            
            String hitCount = element.elementTextTrim("hitCount");
            if (hitCount==null || "".equals(hitCount)) term.setHighlightCount(0);
            else term.setHighlightCount(Integer.parseInt(hitCount));
            
            String commentCount = element.elementTextTrim("commentCount");
            if (commentCount==null || "".equals(commentCount)) term.setCommentCount(0);
            else term.setCommentCount(Integer.parseInt(commentCount));
            
            termMap.put(name.toLowerCase(), term);
        }//for i
        
        for (Term term : termMap.values()) {
            saveTerm(term);
        }//for term

    }//imports()
    
    
    public void doExports(Document document) throws Exception {
        Element root = document.addElement("glossary");
        
        List<Term> terms = getTerms();
        
        for (Term term : terms) {
            Element one = root.addElement("term");
            one.addAttribute("name", term.getName());
            
            switch(term.getStatus()) {
                case Term.STATUS_PENDING:
                    one.addAttribute("status", "pending");
                    break;
                case Term.STATUS_OFFICIAL:
                    one.addAttribute("status", "official");
                    break;
                default:
                    throw new Exception("Unknown status for term: "+term.getName());
            }//switch
            
            one.addElement("shortDefinition").setText(term.getShortDefinition());
            one.addElement("extDefinition").setText(term.getExtDefinition());
            
            Element abbreviation = one.addElement("abbreviation");
            abbreviation.setText(term.getAbbreviation());
            
            Element creator = one.addElement("creator");
            creator.addAttribute("type", "loginname");
            creator.addText(term.getCreator().getLoginname());
            
            Element createTime = one.addElement("createTime");
            createTime.setText(format.format(term.getCreateTime()));
            
            Element relatedTerms = one.addElement("relatedTerms");
            for (Term relatedTerm : (Set<Term>)term.getRelatedTerms()) {
                Element oneTerm = relatedTerms.addElement("term");
                oneTerm.setText(relatedTerm.getName());
            }
            
            Element links = one.addElement("links");
            for (TermLink link : (Set<TermLink>)term.getLinks()) {
                Element oneLink = links.addElement("link");
                oneLink.setText(link.getLink());
            }
            
            Element sources = one.addElement("sources");
            for (TermSource source : (Set<TermSource>)term.getSources()) {
                Element oneSource = sources.addElement("source");
                oneSource.setText(source.getSource());
            }
            
            Element categories = one.addElement("categories");
            for (TermCategory category : (Set<TermCategory>)term.getCategories()) {
                Element oneCategory = categories.addElement("category");
                oneCategory.setText(category.getName());
            }
            
            one.addElement("refCount").setText(""+term.getViewCount());
            one.addElement("hitCount").setText(""+term.getHighlightCount());
            one.addElement("commentCount").setText(""+term.getCommentCount());
        }//for
    }//doExports()


}//class GlossaryHandler
