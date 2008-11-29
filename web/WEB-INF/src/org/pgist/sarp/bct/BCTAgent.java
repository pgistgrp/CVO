package org.pgist.sarp.bct;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.directwebremoting.WebContextFactory;
import org.pgist.search.SearchHelper;
import org.pgist.system.EmailSender;
import org.pgist.system.SystemService;
import org.pgist.system.UserDAO;
import org.pgist.system.YesNoVoting;
import org.pgist.tagging.Tag;
import org.pgist.users.User;
import org.pgist.util.PageSetting;
import org.pgist.util.StringUtil;
import org.pgist.util.WebUtils;


/**
 * DWR AJAX Agent class.<br>
 * Provide AJAX services to client programs.<br>
 * In this document, all the NON-AJAX methods are marked out. So all methods
 * <span style="color:red;">without</span> such a description
 * <span style="color:red;">ARE</span> AJAX service methods.<br>
 *
 * @author kenny
 *
 */
public class BCTAgent {


    private BCTService bctService = null;
    
    private UserDAO userDAO = null;
    
    private SystemService systemService;
    
    private SearchHelper searchHelper;
    
    private EmailSender emailSender;
    
    
    /**
     * This is not an AJAX service method.
     *
     * @param bctService
     */
    public void setBctService(BCTService bctService) {
        this.bctService = bctService;
    }


    /**
     * This is not an AJAX service method.
     *
     * @param userDAO
     */
    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }


    /**
     * This is not an AJAX service method.
     *
     * @param userDAO
     */
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    /**
     * This is not an AJAX service method.
     *
     * @param searchHelper
     */
    public void setSearchHelper(SearchHelper searchHelper) {
        this.searchHelper = searchHelper;
    }


    public void setEmailSender(EmailSender emailSender) {
        this.emailSender = emailSender;
    }


    /*
     * ------------------------------------------------------------------------
     */


    /**
     * Analyze the given concern, extract and return the recognized tags from it.
     *
     * @param params A map contains:<br>
     *         <ul>
     *           <li>concern - A string, the new concern which user entered</li>
     *         </ul>
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>tags - a string array each element is name for an existing tag</li>
     *           <li>potentialtags - a string array each element is a possible tag name</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *         </ul>
     */
    public Map prepareConcern(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            String concern = (String) params.get("concern");
            
            String[][] tags = bctService.getSuggestedTags(concern);
            
            map.put("tags", tags[0]);
            map.put("potentialtags", tags[1]);
            
            map.put("successful", new Boolean(true));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return map;
    }//prepareConcern()


    /**
     * Save the given new concern and its tags to the system.
     *
     * @param params A map contains:<br>
     *         <ul>
     *           <li>bctId - long int, the current BCT instance id</li>
     *           <li>concern - String, the new concern which user entered</li>
     *           <li>tags - String, a comma-separated tag list provided by current user</li>
     *           <li>workflowId - long</li>
     *           <li>contextId - long</li>
     *           <li>activityId - long</li>
     *         </ul>
     * 
     * @param wfinfo A map contains:
     *   <ul>
     *     <li>workflowId - long</li>
     *     <li>contextId - long</li>
     *     <li>activityId - long</li>
     *   </ul>
     * 
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>concern - A newly created Concern object</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *         </ul>
     */
    public Map saveConcern(Map params, Map wfinfo) {
        Map map = new HashMap();
        map.put("successful", false);

        Long bctId = new Long((String) params.get("bctId"));

        String concernStr = (String) params.get("concern");
        if (concernStr == null || "".equals(concernStr.trim())) {
            map.put("reason", "concern can not be empty.");
            return map;
        }

        String tags = (String) params.get("tags");
        
        Concern concern = null;
        
        try {
            concern = bctService.createConcern(bctId, concernStr, tags.split(","));
            map.put("concern", concern);
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }

        if (concern!=null) {
            /*
             * Indexing with Lucene.
             */
            IndexWriter writer = null;
            try {
                writer = searchHelper.getIndexWriter();
                Document doc = new Document();
                doc.add( new Field("type", "concern", Field.Store.YES, Field.Index.UN_TOKENIZED) );
                doc.add( new Field("author", concern.getAuthor().getLoginname(), Field.Store.YES, Field.Index.TOKENIZED) );
                doc.add( new Field("date", concern.getCreateTime().toString(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
                doc.add( new Field("body", concern.getContent(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
                doc.add( new Field("tags", tags, Field.Store.YES, Field.Index.UN_TOKENIZED) );
                doc.add( new Field("contents", tags+" "+concern.getContent(), Field.Store.YES, Field.Index.TOKENIZED) );
                doc.add( new Field("workflowid", concern.getBct().getWorkflowId().toString(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
                doc.add( new Field("contextid", wfinfo.get("contextId").toString(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
                doc.add( new Field("activityid", wfinfo.get("activityId").toString(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
                doc.add( new Field("bctid", concern.getBct().getId().toString(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
                doc.add( new Field("concernid", concern.getId().toString(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
                writer.addDocument(doc);
            } catch(Exception e) {
                e.printStackTrace();
            } finally {
                if (writer!=null) {
                    try {
                        writer.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        
        return map;
    }//saveConcern()


    /**
     * Get concerns conform to given conditions.
     *
     * @param params A map contains:<br>
     *         <ul>
     *           <li>bctId - long int, the current BCT instance id</li>
     *           <li>type - int
     *             <ul>
     *               <li>type==0, get the current user's concerns</li>
     *               <li>type==1, get other people's concerns</li>
     *               <li>type==2, get other people's concerns (randomly ordered)</li>
     *             </ul>
     *           </li>
     *           <li>count - int, number of concerns to be extracted</li>
     *           <li>page - int, the page number of concerns to be extracted (only valid when type==2)</li>
     *         </ul>
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>html - a HTML source segment. (Generated by /WEB-INF/jsp/cvo/concerns.jsp)<br>
     *                  The following variables are available for use in the jsp:
     *                  <ul>
     *                    <li>concerns - A list of Concern objects</li>
     *                    <li>setting - A PageSetting objects</li>
     *                  </ul>
     *           </li>
     *           <li>count - int, number of concerns actually extracted (valid when successful==true)</li>
     *           <li>total - int, number of total my/other concerns in the database (valid when successful==true)</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *         </ul>
     * @throws Exception
     */
    public Map getConcerns(HttpServletRequest request, Map params) {
        Map map = new HashMap();

        Long bctId = new Long((String) params.get("bctId"));

        if (!(bctId > 0)) {
            map.put("successful", new Boolean(false));
            map.put("reason", "No BCTId is given.");
            return map;
        }

        int count = 10;
        
        try {
        	count = Integer.parseInt((String) params.get("count"));
        	if (count<=0) count = -1;
        } catch (Exception e) {
        	//do nothing
		}

        Collection concerns = null;
        Integer type = null;
        String url = "";

        try {
            BCT bct = bctService.getBCTById(bctId);

            type = new Integer((String) params.get("type"));
            switch (type.intValue()) {
	            case 0:
	                concerns = bctService.getMyConcerns(bct);
	                map.put("total", "" + bctService.getConcernsTotal(bct, 1));
	
	                request.setAttribute("showIcon", new Boolean(true));
	
	                url = "/WEB-INF/jsp/sarp/bct/concerns.jsp";
	                break;
	            case 1:
	                concerns = bctService.getOthersConcerns(bct, count);
	                map.put("total", "" + bctService.getConcernsTotal(bct, 2));
	
	                request.setAttribute("showIcon", new Boolean(false));
	
	                url = "/WEB-INF/jsp/sarp/bct/concerns.jsp";
	                break;
	            case 2:
	                PageSetting setting = new PageSetting();
	                setting.setRowOfPage(count);
	                try {
	                    setting.setPage(Integer.parseInt((String) params.get("page")));
	                } catch (Exception e) {
	                    setting.setPage(1);
	                }
	                concerns = bctService.getRandomConcerns(bct, setting);
	                map.put("total", "" + setting.getRowSize());
	
	                request.setAttribute("setting", setting);
	                request.setAttribute("showIcon", new Boolean(false));
	
	                url = "/WEB-INF/jsp/sarp/bct/concerns.jsp";
	                break;
	            default:
	                map.put("successful", new Boolean(false));
	                map.put("reason", "Not sure who's concern is wanted. Please set type to 0 (current user) or 1 (others').");
	                return map;
            }

            request.setAttribute("showTitle", new Boolean(false));

            request.setAttribute("bct", bct);
            request.setAttribute("concerns", concerns);
            request.setAttribute("type", type);

            map.put("html", WebContextFactory.get().forwardToString(url));
            map.put("successful", new Boolean(true));
        } catch (Exception e) {
            e.printStackTrace();
            map.put("successful", new Boolean(false));
            if (type == null) {
                map.put("reason", "Not sure who's concern is wanted. Please set type to 0 (current user) or 1 (others').");
            } else {
                map.put("reason", e.getMessage());
            }
            return map;
        }

        return map;
    }//getConcerns()


    /**
     * Get the tag cloud of the current BCT instance.
     *
     * @param params A map contains:<br>
     *         <ul>
     *           <li>bctId - long int, the current BCT instance id</li>
     *           <li>type - int
     *             <ul>
     *               <li>type==0, search top count tags which are the hottest</li>
     *               <li>type==1, search tags which are over a specific threshhold</li>
     *               <li>type==2, search all tags</li>
     *             </ul>
     *           </li>
     *           <li>count - valid when type==0, default is 10; or type==2, default is -1</li>
     *           <li>page - valid when type==2, default is 1</li>
     *           <li>threshhold - valid when type==1, default is 2</li>
     *         </ul>
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>html - a HTML source segment. (Generated by /WEB-INF/jsp/cvo/tagCloud.jsp)<br>
     *                  The following variables are available for use in the jsp:
     *                  <ul>
     *                    <li>tags - a list of TagReference objects</li>
     *                    <li>setting - An PageSetting object</li>
     *                  </ul>
     *           </li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *         </ul>
     */
    public Map getTagCloud(HttpServletRequest request, Map params) {
        Map map = new HashMap();
        
        int type = -1;
        BCT bct = null;
        
        try {
            type = Integer.parseInt((String) params.get("type"));
            Long bctId = new Long((String) params.get("bctId"));
            bct = bctService.getBCTById(bctId);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("successful", false);
            if (type == -1) map.put("reason", "Wrong invocation type!");
            if (bct == null) map.put("reason", "No BCTId is given.");
            return map;
        }
        
        if (type == 0) {
            int count = -1;
            try {
                count = Integer.parseInt((String) params.get("count"));
                if (count < 1) count = 2;
            } catch (Exception e) {
                e.printStackTrace();
                count = 2;
            }
            
            try {
                Collection tags = bctService.getTagsByRank(bct, count);
                request.setAttribute("tags", tags);
                map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/sarp/bct/tagCloud.jsp"));
                map.put("successful", true);
            } catch (Exception e) {
                e.printStackTrace();
                map.put("successful", false);
                map.put("reason", "Error: " + e.getMessage());
                return map;
            }
        } else if (type == 1) {
            int threshhold = -1;
            try {
                threshhold = Integer.parseInt((String) params.get("count"));
                if (threshhold < 1) threshhold = 10;
                else if (threshhold > 100) threshhold = 100;
            } catch (Exception e) {
                threshhold = 10;
            }
            
            try {
                Collection tags = bctService.getTagsByThreshold(bct, threshhold);
                request.setAttribute("tags", tags);
                map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/sarp/bct/tagCloud.jsp"));
                map.put("successful", true);
            } catch (Exception e) {
                map.put("successful", false);
                map.put("reason", "Error: " + e.getMessage());
                return map;
            }
        } else if (type == 2) {
            try {
                PageSetting setting = new PageSetting();
                setting.setRowOfPage((String) params.get("count"));
                setting.setPage((String) params.get("page"));
                
                Collection tags = bctService.getTagCloud(bct, setting);
                request.setAttribute("tags", tags);
                request.setAttribute("setting", setting);
                map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/sarp/bct/tagCloud.jsp"));
                
                map.put("successful", true);
            } catch (Exception e) {
                map.put("reason", e.getMessage());
            }
        } else {
            map.put("reason", "Wrong invocation type!");
        }
        
        return map;
    }//getTagCloud()


    /**
     * Get a Concern object with the given id.
     * @param id the id of Concern object.
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>concern - a Concern object. (valid when successful==true)</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *           <li>voting - a YesNoVoting object (may be null if the current user hasn't voted yet.)</li>
     *         </ul>
     */
    public Map getConcernById(Long id) {
        Map map = new HashMap();

        try {
            Concern concern = bctService.getConcernById(id);
            
            if (concern == null) {
                map.put("successful", false);
                map.put("reason", "concern not found with id " + id);
                return map;
            }
            
            YesNoVoting voting = systemService.getVoting(YesNoVoting.TYPE_CONCERN, id);
            if (voting!=null) {
                map.put("voting", voting);
            }
            
            map.put("successful", true);
            map.put("concern", concern);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("successful", false);
            map.put("reason", e.getMessage());
        }

        return map;
    }//getConcernById


    /**
     * Get concerns attached to a tag.
     *
     * @param params A map contains:<br>
     *         <ul>
     *           <li>tagRefId - long int, the TagReference instance id</li>
     *           <li>count - the Max concerns to get, default is 10. -1 denotes no limit.</li>
     *         </ul>
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>html - a HTML source segment. (Generated by /WEB-INF/jsp/cvo/tagConcerns.jsp)<br>
     *                  The following variables are available for use in the jsp:
     *                  <ul>
     *                    <li>tagRef - The TagReference objects</li>
     *                    <li>concerns - A list of Concern objects</li>
     *                  </ul>
     *           </li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *         </ul>
     */
    public Map getConcernsByTag(HttpServletRequest request, Map params) {
        Map map = new HashMap();

        Long tagRefId = null;
        int count = -1;

        try {
            tagRefId = new Long((String) params.get("tagRefId"));
            count = Integer.parseInt((String) params.get("count"));
        } catch (NumberFormatException e) {
            if (tagRefId == null) {
                map.put("successful", new Boolean(false));
                map.put("reason", "tagRefId is required!");
                return map;
            }
        }

        try {
            Collection concerns = bctService.getConcernsByTag(tagRefId, count);
            TagReference tagRef = bctService.getTagReferenceById(tagRefId);

            request.setAttribute("showIcon", new Boolean(false));
            request.setAttribute("showTitle", new Boolean(true));
            request.setAttribute("tagRef", tagRef);
            request.setAttribute("concerns", concerns);

            map.put("html",
                    WebContextFactory.get().forwardToString(
                            "/WEB-INF/jsp/sarp/bct/concerns.jsp"));
        } catch (Exception e) {
            e.printStackTrace();
            map.put("successful", new Boolean(false));
            map.put("reason", e.getMessage());
            return map;
        }

        map.put("successful", new Boolean(true));

        return map;
    }//getConcernsByTag()


    /**
     * Edit the given Concern object. Before edit, the current user will be check if he is the author of
     * this concern.
     *
     * @param params A map contains:<br>
     *         <ul>
     *           <li>concernId - long int, the Concern instance id</li>
     *           <li>concern - A string, the concern which user edited, can be null or "" after trimed, means don't edit the concern string.</li>
     *         </ul>
     * 
     * @param wfinfo A map contains:
     *   <ul>
     *     <li>workflowId - long</li>
     *     <li>contextId - long</li>
     *     <li>activityId - long</li>
     *   </ul>
     *   
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *         </ul>
     */
    public Map editConcern(Map params, Map wfinfo) {
        Map map = new HashMap();
        map.put("successful", false);
        
        Long concernId = null;
        Concern concern = null;
        
        String newConcern = (String) params.get("concern");
        if (newConcern == null || "".equals(newConcern.trim())) {
            map.put("successful", new Boolean(false));
            map.put("reason", "Concern string can't be empty.");
            return map;
        }
        
        try {
            concernId = new Long((String) params.get("concernId"));
            concern = bctService.getConcernById(concernId);
            if (concern.isDeleted()) {
                map.put("successful", new Boolean(false));
                map.put("reason", "This concern is already deleted.");
                return map;
            }
        } catch (Exception e) {
            map.put("successful", new Boolean(false));
            if (concernId == null) {
                map.put("reason", "concernId is required.");
            } else {
                map.put("reason",
                        "failed to extract concern object with id " + concernId);
            }
            return map;
        }
        
        //Check if the current user is the author of this concern.
        try {
            Long userId = WebUtils.currentUserId();
            User user = userDAO.getUserById(userId, true, false);
            if (user.getId().doubleValue() == concern.getAuthor().getId()) {
                concern.setContent(newConcern);
                concern.setCreateTime(new Date());
                bctService.save(concern);
                map.put("successful", true);
                
                IndexSearcher searcher = null;
                IndexWriter writer = null;
                try {
                    searcher = searchHelper.getIndexSearcher();
                    Hits hits = searcher.search(searchHelper.getParser().parse(
                        "workflowid:"+concern.getBct().getWorkflowId()
                       +" AND type:concern AND concernid:"+concernId
                    ));
                    
                    String tags = "";
                    
                    if (hits.length()>0) {
                        /*
                         * delete from lucene
                         */
                        IndexReader reader = null;
                        try {
                            Document doc = hits.doc(0);
                            
                            tags = doc.get("tags");
                            
                            reader = searchHelper.getIndexReader();
                            
                            reader.deleteDocument(hits.id(0));
                        } finally {
                            if (reader!=null) reader.close();
                        }
                    }
                    
                    writer = searchHelper.getIndexWriter();
                    
                    Document doc = new Document();
                    doc.add( new Field("type", "concern", Field.Store.YES, Field.Index.UN_TOKENIZED) );
                    doc.add( new Field("author", concern.getAuthor().getLoginname(), Field.Store.YES, Field.Index.TOKENIZED) );
                    doc.add( new Field("date", concern.getCreateTime().toString(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
                    doc.add( new Field("body", concern.getContent(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
                    doc.add( new Field("tags", tags, Field.Store.YES, Field.Index.UN_TOKENIZED) );
                    doc.add( new Field("contents", tags+" "+concern.getContent(), Field.Store.NO, Field.Index.TOKENIZED) );
                    doc.add( new Field("workflowid", concern.getBct().getWorkflowId().toString(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
                    doc.add( new Field("contextid", wfinfo.get("contextId").toString(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
                    doc.add( new Field("activityid", wfinfo.get("activityId").toString(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
                    doc.add( new Field("bctid", concern.getBct().getId().toString(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
                    doc.add( new Field("concernid", concern.getId().toString(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
                    
                    /*
                     * reindexing in lucene
                     */
                    writer.addDocument(doc);
                    
                    writer.optimize();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (searcher!=null) searcher.close();
                    if (writer!=null) writer.close();
                }
            } else {
                map.put("reason", "You have no right to edit this concern.");
                return map;
            }
        } catch (Exception e) {
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//editConcern()


    /**
     * Delete the given Concern object. Before delete, the current user will be check if he is the author of
     * this concern.
     * @param params A map contains:<br>
     *         <ul>
     *           <li>concernId - long int, the Concern instance id</li>
     *         </ul>
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *         </ul>
     */
    public Map deleteConcern(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        Long concernId = null;
        
        //Check if the current user is the author of this concern.
        try {
            concernId = new Long((String) params.get("concernId"));
            if (concernId == null) {
                map.put("reason", "concernId is required.");
                return map;
            }
            
            bctService.deleteConcern(concernId);
            
            map.put("successful", new Boolean(true));
            
            /*
             * delete from lucene
             */
            IndexSearcher searcher = null;
            IndexReader reader = null;
            try {
                searcher = searchHelper.getIndexSearcher();
                
                Hits hits = searcher.search(searchHelper.getParser().parse(
                    "concernid:" + concernId
                ));
                
                if (hits.length()>0) {
                    reader = searchHelper.getIndexReader();
                    for (int i=0; i<hits.length(); i++) {
                        reader.deleteDocument(hits.id(i));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (searcher!=null) searcher.close();
                if (reader!=null) reader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//deleteConcern()


    /**
     * Delete the given TagReference objects from the given Concern object. Before delete, the current user will be check if he is the author of
     * this concern.
     *
     * @param params A map contains:<br>
     *   <ul>
     *     <li>concernId - long int, the Concern instance id</li>
     *     <li>tags - A string, a comma-separated tag name list which is the final tags</li>
     *   </ul>
     * 
     * @param wfinfo A map contains:
     *   <ul>
     *     <li>workflowId - long</li>
     *     <li>contextId - long</li>
     *     <li>activityId - long</li>
     *   </ul>
     *   
     * @return A map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map editTags(Map params, Map wfinfo) {
        Map map = new HashMap();
        map.put("successful", false);
        
        Long concernId = null;
        Concern concern = null;

        try {
            concernId = new Long((String) params.get("concernId"));
            concern = bctService.getConcernById(concernId);
            if (concern.isDeleted()) {
                map.put("reason", "This concern is already deleted.");
                return map;
            }
        } catch (Exception e) {
            if (concernId == null) {
                map.put("reason", "concernId is required.");
            } else {
                map.put("reason",
                        "failed to extract concern object with id " + concernId);
            }
            return map;
        }

        //Check if the current user is the author of this concern.
        try {
            Long userId = WebUtils.currentUserId();
            User user = userDAO.getUserById(userId, true, false);
            String tagStr = (String) params.get("tags");
            String tags[] = null;
            if (tagStr == null) tagStr = "";
            tags = tagStr.trim().split(",");
            if (user.getId().doubleValue() == concern.getAuthor().getId()) {
                concern.setCreateTime(new Date());
                bctService.editConcernTags(concern, tags);
                map.put("successful", true);
                
                IndexSearcher searcher = null;
                IndexWriter writer = null;
                try {
                    searcher = searchHelper.getIndexSearcher();
                    Hits hits = searcher.search(searchHelper.getParser().parse(
                        "workflowid:"+concern.getBct().getWorkflowId()
                       +" AND type:concern AND concernid:"+concernId
                    ));
                    
                    if (hits.length()>0) {
                        /*
                         * delete from lucene
                         */
                        IndexReader reader = null;
                        try {
                            Document document = hits.doc(0);
                            
                            reader = searchHelper.getIndexReader();
                            
                            reader.deleteDocument(hits.id(0));
                        } finally {
                            if (reader!=null) reader.close();
                        }
                    }
                    
                    writer = searchHelper.getIndexWriter();
                    
                    /*
                     * reindexing in lucene
                     */
                    Document doc = new Document();
                    
                    doc.add( new Field("type", "concern", Field.Store.YES, Field.Index.UN_TOKENIZED) );
                    doc.add( new Field("author", concern.getAuthor().getLoginname(), Field.Store.YES, Field.Index.TOKENIZED) );
                    doc.add( new Field("date", concern.getCreateTime().toString(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
                    doc.add( new Field("body", concern.getContent(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
                    doc.add( new Field("tags", tagStr, Field.Store.YES, Field.Index.UN_TOKENIZED) );
                    doc.add( new Field("contents", tagStr+" "+concern.getContent(), Field.Store.NO, Field.Index.TOKENIZED) );
                    doc.add( new Field("concernid", concern.getId().toString(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
                    doc.add( new Field("bctid", concern.getBct().getId().toString(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
                    doc.add( new Field("workflowid", concern.getBct().getWorkflowId().toString(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
                    doc.add( new Field("contextid", wfinfo.get("contextId").toString(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
                    doc.add( new Field("activityid", wfinfo.get("activityId").toString(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
                    
                    writer.addDocument(doc);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (searcher!=null) searcher.close();
                    if (writer!=null) writer.close();
                }
            } else {
                map.put("reason", "You have no right to edit this concern.");
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }

        return map;
    }//editTags()

    
    /**
     * Search all matched tags in the given BCT by tag name. Approximate match is used for the tag string.
     *
     * @param params A map contains:<br>
     *         <ul>
     *           <li>bctId - long int, the current BCT instance id</li>
     *           <li>tag - A string, all or part of a Tag name</li>
     *         </ul>
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>count - amount of matched Tag objects (valid when successful==true)</li>
     *           <li>html - a HTML source segment. (valid when count>0) <br>
     *                  (Generated by /WEB-INF/jsp/cvo/tagSearch.jsp)<br>
     *                  The following variables are available for use in the jsp:
     *                  <ul>
     *                    <li>tags - A list of TagReference objects</li>
     *                  </ul>
     *           </li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *         </ul>
     */
    public Map searchTags(HttpServletRequest request, Map params) {
        Map map = new HashMap();

        BCT bct = null;

        try {
            Long bctId = new Long((String) params.get("bctId"));
            bct = bctService.getBCTById(bctId);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("successful", false);
            if (bct == null) map.put("reason", "No bctId is given.");
            else map.put("reason", e.getMessage());
            return map;
        }

        String tag = (String) params.get("tag");
        if (tag == null || tag.trim().equals("")) {
            map.put("successful", new Boolean(false));
            map.put("reason", "tag string to be searched is required!");
        }

        try {
            Collection tags = bctService.searchTags(bct, tag);
            map.put("count", tags.size());
            if (tags.size() > 0) {
                request.setAttribute("tags", tags);
                map.put("html",
                        WebContextFactory.get().forwardToString(
                                "/WEB-INF/jsp/sarp/bct/tagSearch.jsp"));
            }
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("successful", false);
            map.put("reason", e.getMessage());
        }

        return map;
    }//searchTags()

    /**
     * Get a specific tag based upon the tagReferenceID.
     * 
     * @param id Long<br>
     * 
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>count - amount of matched Tag objects (valid when successful==true)</li>
     *           <li>tag - The Tag</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *         </ul>
     */
    public Map getTagByTagRefId(String strTagId) {
    	Map map = new HashMap();
        map.put("successful", false);
  
        Long tagId = Long.parseLong(strTagId);
    	try {
	    	TagReference ref = bctService.getTagReferenceById(tagId);
	    	Tag myTag = ref.getTag();
	    	//request.setAttribute("tag", myTag);
	    	
	    	map.put("tag", myTag);
	    	
	    	map.put("successful", true);    	
    	} catch (Exception e) {
    		e.printStackTrace();
            map.put("reason", e.getMessage());  		
    	}
    	return map;
    } //getTagByTagRefId()
    

    /**
     * Get conerns in current BCT context.
     * 
     * @param params A map contains:
     *   <ul>
     *     <li>bctId - long int, the current BCT instance id</li>
     *     <li>filter - string, tag name as filter (Optional)</li>
     *     <li>count - number of records shown per page. Optional, default is -1, means all records.</li>
     *     <li>page - page number. Optional, default is 1.</li>
     *     <li>sorting - int, the sorting index, 1-7, referencing BCTDAOImpl.java for the meaning</li>
     *     <li>
     *       type - string, types of searching
     *       <ul>
     *         <li>empty or "all" - show all concerns</li>
     *         <li>"owner" - show only the current user's concerns</li>
     *         <li>"other" - show only the other user's concerns</li>
     *       </ul>
     *     </li>
     *   </ul>
     * 
     * @param wfinfo A map contains:
     *   <ul>
     *   <li>workflowId - long</li>
     *   <li>contextId - long</li>
     *   <li>activityId - long</li>
     * </ul>
     * 
     * @return A map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>html - a HTML source segment. (valid when count>0) <br>
     *       (Generated by /WEB-INF/jsp/sarp/bct/contextConcerns.jsp)<br>
     *       The following variables are available for use in the jsp:
     *       <ul>
     *         <li>bct - A BCT object</li>
     *         <li>concerns - A list of Concern objects, each concern's "object" field may be a YesNoVoting object, or null.</li>
     *         <li>setting - An PageSetting object</li>
     *       </ul>
     *     </li>
     *   </ul>
     * 
     */
    public Map getContextConcerns(HttpServletRequest request, Map params, Map wfinfo) {
        Map map = new HashMap();
        map.put("successful", false);
        
        BCT bct = null;
        
        try {
            request.setAttribute("wfinfo", wfinfo);
            
            Long bctId = new Long((String) params.get("bctId"));
            bct = bctService.getBCTById(bctId);
        } catch (Exception e) {
            e.printStackTrace();
            if (bct == null) map.put("reason", "No bctId is given.");
            else map.put("reason", e.getMessage());
            return map;
        }
        
        String filter = (String) params.get("filter");
        
        try {
            int sorting = 0;
            try {
                sorting = Integer.parseInt((String) params.get("sorting"));
            } catch (Exception e) {
            }
            
            String type = (String) params.get("type");
            if (type==null || "".equals(type)) type = "all";
            
            PageSetting setting = new PageSetting();
            setting.setRowOfPage((String) params.get("count"));
            setting.setPage((String) params.get("page"));
            
            Collection concerns = bctService.getContextConcerns(bct, setting, filter, type, sorting);
            
            request.setAttribute("bct", bct);
            request.setAttribute("concerns", concerns);
            request.setAttribute("setting", setting);
            request.setAttribute("filter", filter);
            request.setAttribute("type", type);
            request.setAttribute("sorting", sorting);
            
            map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/sarp/bct/contextConcerns.jsp"));
            
            map.put("successful", true);        
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());          
        }
        
        return map;
    }//getContextConcerns()
    
    
    /**
     * Get conerns in concise format in current BCT context.
     * 
     * @param params A map contains:
     *   <ul>
     *     <li>bctId - long int, the current BCT instance id</li>
     *     <li>filter - string, tag id as filter (Optional, -1)</li>
     *     <li>count - number of records shown per page. Optional, default is -1, means all records.</li>
     *     <li>page - page number. Optional, default is 1.</li>
     *     <li>sorting - int, the sorting index, 1-7, referencing BCTDAOImpl.java for the meaning</li>
     *   </ul>
     * 
     * @param wfinfo A map contains:
     *   <ul>
     *   <li>workflowId - long</li>
     *   <li>contextId - long</li>
     *   <li>activityId - long</li>
     * </ul>
     * 
     * @return A map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>filter - a Tag object</li>
     *     <li>html - a HTML source segment. (valid when count>0) <br>
     *       (Generated by /WEB-INF/jsp/sarp/bct/contextConcerns.jsp)<br>
     *       The following variables are available for use in the jsp:
     *       <ul>
     *         <li>bct - A BCT object</li>
     *         <li>concerns - A list of Concern objects, each concern's "object" field may be a YesNoVoting object, or null.</li>
     *         <li>setting - An PageSetting object</li>
     *       </ul>
     *     </li>
     *   </ul>
     * 
     */
    public Map getConciseConcerns(HttpServletRequest request, Map params, Map wfinfo) {
        Map map = new HashMap();
        map.put("successful", false);
        
        BCT bct = null;
        
        try {
            request.setAttribute("wfinfo", wfinfo);
            
            Long bctId = new Long((String) params.get("bctId"));
            bct = bctService.getBCTById(bctId);
        } catch (Exception e) {
            e.printStackTrace();
            if (bct == null) map.put("reason", "No bctId is given.");
            else map.put("reason", e.getMessage());
            return map;
        }
        
        String filterId = (String) params.get("filter");
        
        try {
            int sorting = 0;
            try {
                sorting = Integer.parseInt((String) params.get("sorting"));
            } catch (Exception e) {
            }
            
            TagReference filter = null;
            String filterName = null;
            try {
            	filter = bctService.getTagReferenceById(new Long(filterId));
            	if (filter!=null) filterName = filter.getTag().getName();
            } catch (Exception e) {
				//do nothing
			}
            
            PageSetting setting = new PageSetting();
            setting.setRowOfPage((String) params.get("count"));
            setting.setPage((String) params.get("page"));
            
            Collection concerns = bctService.getContextConcerns(bct, setting, filterName, "all", sorting);
            
            request.setAttribute("bct", bct);
            request.setAttribute("concerns", concerns);
            request.setAttribute("setting", setting);
            request.setAttribute("filter", filterName);
            request.setAttribute("type", "all");
            request.setAttribute("sorting", sorting);
            
            map.put("filter", filter);
            map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/sarp/bct/conciseConcerns.jsp"));
            
            map.put("successful", true);        
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());          
        }
        
        return map;
    }//getConciseConcerns()
    
    
    /**
     * Get tags in concise format in current BCT context.
     * 
     * @param params A map contains:
     *   <ul>
     *     <li>bctId - long int, the current BCT instance id</li>
     *     <li>page - page number. Optional, default is 1.</li>
     *     <li>sorting - int, 0:a-z, 1:z-a, 2:frequency. Optional, default is 0.</li>
     *   </ul>
     * 
     * @param wfinfo A map contains:
     *   <ul>
     *   <li>workflowId - long</li>
     *   <li>contextId - long</li>
     *   <li>activityId - long</li>
     * </ul>
     * 
     * @return A map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>sorting - int</li>
     *     <li>html - a HTML source segment. (valid when count>0) <br>
     *       (Generated by /WEB-INF/jsp/sarp/bct/contextConcerns.jsp)<br>
     *       The following variables are available for use in the jsp:
     *       <ul>
     *         <li>bct - A BCT object</li>
     *         <li>tags - A list of TagReference objects
     *         <li>setting - An PageSetting object</li>
     *       </ul>
     *     </li>
     *   </ul>
     * 
     */
    public Map getConciseTags(HttpServletRequest request, Map params, Map wfinfo) {
        Map map = new HashMap();
        map.put("successful", false);
        
        BCT bct = null;
        
        try {
            request.setAttribute("wfinfo", wfinfo);
            
            Long bctId = new Long((String) params.get("bctId"));
            bct = bctService.getBCTById(bctId);
        } catch (Exception e) {
            e.printStackTrace();
            if (bct == null) map.put("reason", "No bctId is given.");
            else map.put("reason", e.getMessage());
            return map;
        }
        
        int sorting = 0;
        try {
        	sorting = Integer.parseInt((String) params.get("sorting"));
        	if (sorting<0) sorting = 0;
        	if (sorting>2) sorting = 2;
        } catch (Exception e) {
		}
        
        try {
            PageSetting setting = new PageSetting(20);
            setting.setPage((String) params.get("page"));
            
            Collection tags = bctService.getConciseTags(bct, setting, sorting);
            
            request.setAttribute("bct", bct);
            request.setAttribute("tags", tags);
            request.setAttribute("setting", setting);
            
            map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/sarp/bct/conciseTags.jsp"));
            
            map.put("sorting", sorting);
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());          
        }
        
        return map;
    }//getConciseTags()
    
    
    /**
     * Set the voting choice on the given concern.
     * 
     * @param params A map contains:
     *   <ul>
     *     <li>id - int, id of the Concern object. Required.</li>
     *     <li>agree - string, "true" or "false". Whether or not the current user agree with the current object.</li>
     *   </ul>
     *   
     * @return A map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map setVoting(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        Long id = null;
        try {
            id = new Long((String) params.get("id"));
        } catch (Exception e) {
            map.put("reason", "id is required.");
            return map;
        }
        
        boolean agree = "true".equalsIgnoreCase((String) params.get("agree"));
        
        try {
            bctService.setVotingOnConcern(id, agree);
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//setVoting()
    
    
    /**
     * Get the concern comments on the given concern.
     * 
     * @param params A map contains:
     *   <ul>
     *     <li>concernId - int, id of the Concern object. Required.</li>
     *     <li>page - int, current page number.</li>
     *     <li>count - int, total number of pages.</li>
     *   </ul>
     *   
     * @param wfinfo A map contains:
     *   <ul>
     *     <li>workflowId - long</li>
     *     <li>contextId - long</li>
     *     <li>activityId - long</li>
     *   </ul>
     * 
     * @return A map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>html - a HTML source segment. <br>
     *       (Generated by /WEB-INF/jsp/sarp/bct/comments.jsp)<br>
     *       The following variables are available for use in the jsp:
     *       <ul>
     *         <li>concern - a Concern object, "object" field may be a YesNoVoting object, or null.</li>
     *         <li>comments - A list of ConcernComment objects, each concern's "object" field may be a YesNoVoting object, or null.</li>
     *         <li>setting - An PageSetting object</li>
     *       </ul>
     *     </li>
     *   </ul>
     */
    public Map getConcernComments(HttpServletRequest request, Map params, Map wfinfo) {
        Map map = new HashMap();
        map.put("successful", false);
        
        Long concernId = null;

        try {
            concernId = new Long((String) params.get("concernId"));
        } catch (Exception e) {
            map.put("reason", "concernId is required.");
            return map;
        }
        
        try {
            request.setAttribute("wfinfo", wfinfo);
            
            PageSetting setting = new PageSetting();
            setting.setPage((String) map.get("page"));
            setting.setRowOfPage((String) map.get("count"));
            
            Concern concern = bctService.getConcernById(concernId);
            YesNoVoting voting = systemService.getVoting(YesNoVoting.TYPE_CONCERN, concernId);
            if (voting!=null) concern.setObject(voting);
            
            Collection comments = bctService.getConcernComments(concernId, setting);
            for (ConcernComment comment : (Collection<ConcernComment>) comments) {
                voting = systemService.getVoting(YesNoVoting.TYPE_COMMENT, comment.getId());
                if (voting!=null) comment.setObject(voting);
            }
            
            request.setAttribute("bct", concern.getBct());
            request.setAttribute("concern", concern);
            request.setAttribute("comments", comments);
            request.setAttribute("setting", setting);
            
            map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/sarp/bct/comments.jsp"));
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//getConcernComments()
    
    
    /**
     * Create a concern comment on the given concern.
     * 
     * @param params A map contains:
     *   <ul>
     *     <li>concernId - int, id of the Concern object. Required.</li>
     *     <li>title - string, title of the comment. Required.</li>
     *     <li>content - string, content of the comment. Required.</li>
     *     <li>tags - string, comma separated tag name list. Required.</li>
     *   </ul>
     * 
     * @param wfinfo A map contains:
     *   <ul>
     *     <li>workflowId - long</li>
     *     <li>contextId - long</li>
     *     <li>activityId - long</li>
     *   </ul>
     *   
     * @return A map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map createConcernComment(Map params, Map wfinfo) {
        Map map = new HashMap();
        map.put("successful", false);
        
        Long concernId = null;
        
        try {
            concernId = new Long((String) params.get("concernId"));
        } catch (Exception e) {
            map.put("reason", "concernId is required.");
            return map;
        }
        
        String title = (String) params.get("title");
        String content = (String) params.get("content");
        
        if (title==null || content==null) {
            map.put("reason", "title and content can't be empty");
            return map;
        }
        
        title = title.trim();
        content = content.trim();
        
        if (title.length()==0 || content.length()==0) {
            map.put("reason", "title and content can't be empty");
            return map;
        }
        
        String tagStr = (String) params.get("tags");
        String[] tags = StringUtil.splitCSL(tagStr);
        
        ConcernComment comment = null;
        
        String workflowId = (String) wfinfo.get("workflowId");
        String contextId = (String) wfinfo.get("contextId");
        String activityId = (String) wfinfo.get("activityId");
        
        try {
            comment = bctService.createConcernComment(new Long(workflowId), concernId, title, content, tags);
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        if (comment!=null) {
            // sending email
            try {
                Set<User> recipients = bctService.getThreadUsers(concernId);
                System.out.println("recipients 000 =================> "+recipients);
                recipients.add(comment.getConcern().getAuthor());
                String url = "concern.do?workflowId="+workflowId+"&contextId="+contextId+"&activityId="+activityId+"&id="+concernId;
                
                System.out.println("recipients 111 =================> "+recipients);
                
                emailSender.enqueue(recipients, WebUtils.currentUserId(), url);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            /*
             * Indexing with Lucene.
             */
            IndexWriter writer = null;
            try {
                writer = searchHelper.getIndexWriter();
                Document doc = new Document();
                doc.add( new Field("type", "comment", Field.Store.YES, Field.Index.UN_TOKENIZED) );
                doc.add( new Field("author", comment.getAuthor().getLoginname(), Field.Store.YES, Field.Index.TOKENIZED) );
                doc.add( new Field("date", comment.getCreateTime().toString(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
                doc.add( new Field("title", comment.getTitle(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
                doc.add( new Field("body", comment.getContent(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
                doc.add( new Field("tags", tagStr, Field.Store.YES, Field.Index.UN_TOKENIZED) );
                doc.add( new Field("contents", comment.getTitle()+" "+Arrays.toString(tags)+" "+comment.getContent(), Field.Store.NO, Field.Index.TOKENIZED) );
                doc.add( new Field("commentid", comment.getId().toString(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
                doc.add( new Field("bctid", comment.getConcern().getBct().getId().toString(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
                doc.add( new Field("concernid", comment.getConcern().getId().toString(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
                doc.add( new Field("workflowid", comment.getConcern().getBct().getWorkflowId().toString(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
                doc.add( new Field("contextid", wfinfo.get("contextId").toString(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
                doc.add( new Field("activityid", wfinfo.get("activityId").toString(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
                writer.addDocument(doc);
            } catch(Exception e) {
                e.printStackTrace();
            } finally {
                if (writer!=null) {
                    try {
                        writer.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        
        return map;
    }//createComment()
    
    
    /**
     * Edit the given concern comment.
     * 
     * @param params A map contains:
     *   <ul>
     *     <li>commentId - int, id of the ConcernComment object. Required.</li>
     *     <li>title - string, title of the concern comment. Required.</li>
     *     <li>content - string, content of the concern comment. Required.</li>
     *     <li>tags - string, comma separated tag name list. Required.</li>
     *   </ul>
     *   
     * @param wfinfo A map contains:
     *   <ul>
     *     <li>workflowId - long</li>
     *     <li>contextId - long</li>
     *     <li>activityId - long</li>
     *   </ul>
     *   
     * @return A map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map editConcernComment(Map params, Map wfinfo) {
        Map map = new HashMap();
        map.put("successful", false);
        
        Long commentId = null;

        try {
            commentId = new Long((String) params.get("commentId"));
        } catch (Exception e) {
            map.put("reason", "commentId is required.");
            return map;
        }
        
        String title = (String) params.get("title");
        String content = (String) params.get("content");
        
        if (title==null || content==null) {
            map.put("reason", "title and content can't be empty");
            return map;
        }
        
        title = title.trim();
        content = content.trim();
        
        if (title.length()==0 || content.length()==0) {
            map.put("reason", "title and content can't be empty");
            return map;
        }
        
        String tagStr = (String) params.get("tags");
        String[] tags = StringUtil.splitCSL(tagStr);
        
        ConcernComment comment = null;
        
        try {
            comment = bctService.editConcernComment(commentId, title, content, tags);
            
            IndexWriter writer = null;
            try {
                writer = searchHelper.getIndexWriter();
                
                /*
                 * delete from lucene
                 */
                
                writer.deleteDocuments(new Term("commentid", commentId.toString()));
                
                Document doc = new Document();
                doc.add( new Field("type", "post", Field.Store.YES, Field.Index.UN_TOKENIZED) );
                doc.add( new Field("author", comment.getAuthor().getLoginname(), Field.Store.YES, Field.Index.TOKENIZED) );
                doc.add( new Field("date", comment.getCreateTime().toString(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
                doc.add( new Field("title", comment.getTitle(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
                doc.add( new Field("body", comment.getContent(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
                doc.add( new Field("tags", tagStr, Field.Store.YES, Field.Index.UN_TOKENIZED) );
                doc.add( new Field("contents", comment.getTitle()+" "+Arrays.toString(tags)+" "+comment.getContent(), Field.Store.NO, Field.Index.TOKENIZED) );
                doc.add( new Field("workflowid", comment.getConcern().getBct().getWorkflowId().toString(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
                doc.add( new Field("contextid", wfinfo.get("contextId").toString(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
                doc.add( new Field("activityid", wfinfo.get("activityId").toString(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
                doc.add( new Field("bctid", comment.getConcern().getBct().getId().toString(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
                doc.add( new Field("concernid", comment.getConcern().getId().toString(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
                doc.add( new Field("commentid", comment.getId().toString(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
                
                /*
                 * reindexing in lucene
                 */
                writer.addDocument(doc);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (writer!=null) writer.close();
            }
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//editConcernComment()
    
    
    /**
     * Delete the given concern comment.
     * 
     * @param params A map contains:
     *   <ul>
     *     <li>commentId - int, id of the ConcernComment object. Required.</li>
     *   </ul>
     *   
     * @return A map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map deleteConcernComment(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        Long commentId = null;

        try {
            commentId = new Long((String) params.get("commentId"));
        } catch (Exception e) {
            map.put("reason", "commentId is required.");
            return map;
        }
        
        try {
        	ConcernComment comment = bctService.getConcernCommentById(commentId);
            bctService.deleteConcernComment(commentId);
            
            /*
             * delete from lucene
             */
            IndexSearcher searcher = null;
            IndexReader reader = null;
            try {
                searcher = searchHelper.getIndexSearcher();
                
                Hits hits = searcher.search(searchHelper.getParser().parse(
                    "workflowid:" + comment.getConcern().getBct().getWorkflowId()
                    + "AND concernid:" + comment.getConcern().getId()
                    + "AND commentid:" + comment.getId()
                ));
                
                if (hits.length()>0) {
                    reader = searchHelper.getIndexReader();
                    reader.deleteDocument(hits.id(0));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (searcher!=null) searcher.close();
                if (reader!=null) reader.close();
            }
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//deleteComment()
    
    
    /**
     * Set voting to the given concern comment.
     * 
     * @param params A map contains:
     *   <ul>
     *     <li>id - int, id of the ConcernComment object. Required.</li>
     *     <li>agree - string, "true" or "false". Whether or not the current user agree with the current object.</li>
     *   </ul>
     *   
     * @return A map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map setConcernCommentVoting(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        Long id = null;
        try {
            id = new Long((String) params.get("id"));
        } catch (Exception e) {
            map.put("reason", "id is required.");
            return map;
        }
        
        boolean agree = "true".equalsIgnoreCase((String) params.get("agree"));
        
        try {
            bctService.setVotingOnConcernComment(id, agree);
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//setConcernCommentVoting()
    

}//class BCTAgent
