<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Step 1a: Brainstorm Concerns</title>
<!-- Site Wide CSS -->
<style type="text/css" media="screen">
@import "styles/lit.css";
</style>
<!-- End Site Wide CSS -->
<!-- Site Wide JavaScript -->
<script src="scripts/tags.js" type="text/javascript"></script>
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<script src="scripts/search.js" type="text/javascript"></script>
<!-- End Site Wide JavaScript -->
<!-- DWR JavaScript Libraries -->
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<!-- End DWR JavaScript Libraries -->
<!--CCT Specific  Libraries-->
<script type='text/javascript' src='/dwr/interface/SDAgent.js'></script>
<script type='text/javascript' src='/dwr/interface/CCTAgent.js'></script>
<!--End CCT Specific  Libraries-->
<script type="text/javascript">

//GLOBAL Vars
	var sd = new Object;
	sd.isid = "${isid}";
	sd.ioid = "${ioid}";
	sd.concernCount = 8;
	sd.filterAnchor = "#filterJump";
	sd.currentFilter = '';
	sd.divFilteredBy = 'filteredBy';
	sd.divSearchResults = 'searchResults';
	sd.divDiscussionTitle = "discussionTitle";
		sd.discussionTitle = "All Participants' Concerns";
		sd.discussionTitleOnChk = "Your Concerns";


	function getConcerns(tags, page, jump){
		if(jump){
			location.href = sd.filterAnchor;
		}
		//alert("isid: " + sd.isid + " ioid: " + sd.ioid + " tags: " + tags + " page: " + page + " count: " + sd.concernCount); 
		SDAgent.getConcerns({isid:sd.isid, ioid: sd.ioid, tags: tags, page: page, count: sd.concernCount}, {
			callback:function(data){
					if (data.successful){
					   	$('discussion-cont').innerHTML = (data.source.html);
					}else{
						alert(data.reason);
					}
				},
			errorHandler:function(errorString, exception){ 
					alert("delete post error:" + errorString + exception);
			}
			});
	}
	
	function goToPage(page){
		getConcerns(sd.currentFilter, page, true)
	}
	
	function setVote(id, agree){
			CCTAgent.setVoting({id: id, agree:agree}, {
			callback:function(data){
					if (data.successful){ 
						if($('concernVote'+id) != undefined){
            				 new Effect.Fade('concernVote'+id, {afterFinish: function(){getConcerns(sd.currentFilter, sd.currentPage, false);new Effect.Appear('concernVote'+id);}});
            			}else{ //newly created concern
            				getConcerns(sd.currentFilter, sd.currentPage, false);	
            			}
					}else{
						alert(data.reason);
					}
				},
			errorHandler:function(errorString, exception){ 
					alert("setVote error:" + errorString + exception);
			}
			});
	};
	
	function changeCurrentFilter(tagId, type){
		getConcerns(tagId, 0, true)
		sd.currentFilter = tagId;
		if (tagId != ''){
				if(type == 'tagRef'){
						CCTAgent.getTagByTagRefId(sd.currentFilter, {
						callback:function(data){
						if (data.successful){
				          			var tagName = data.tag.name;
									$(sd.divFilteredBy).innerHTML = '<h3 class="contrast1">Filtered By: ' + tagName + ' <a href="javascript: changeCurrentFilter(\'\');"><img src="images/close.gif" alt="clear filter" /></a>';
								}else{
									alert(data.reason);
								}
						},
						errorHandler:function(errorString, exception){ 
								alert("get getTagByTagRefId error:" + errorString + exception);
						}
						});
				}else{ //tagId
						SDAgent.getTagById(sd.currentFilter, {
						callback:function(data){
						if (data.successful){
				          			var tagName = data.tag.name;
									$(sd.divFilteredBy).innerHTML = '<h3 class="contrast1">Filtered By: ' + tagName + ' <a href="javascript: changeCurrentFilter(\'\');"><img src="images/close.gif" alt="clear filter" /></a>';
								}else{
									alert(data.reason);
								}
						},
						errorHandler:function(errorString, exception){ 
								alert("get getTagByTagRefId error:" + errorString + exception);
						}
						});
				}
		}else{
			sd.currentFilter = '';	
			$(sd.divFilteredBy).innerHTML = '';
		}
	}
		
/*
	function customFilter(query, key){
		if (key.keyCode == 8 && query.length < 1){
			return false;	
		}
		if(query.length > 3){
			customFilterAction(query);	
		}
	}
	
	function customFilterAction(query){
			CCTAgent.searchTags({cctId:cct.cctId,tag:query},{
				callback:function(data){
						if (data.successful){
							if($(sd.divSearchResults).style.display == 'none'){
								new Effect.Appear(sd.divSearchResults, {duration: 0.5});		
							}		
							
							$(cct.divSearchResults).innerHTML = $(sd.divSearchResults).innerHTML = data.html;
							if (data.count == 0){
								$(sd.divSearchResults).innerHTML = '<a href="javascript:Effect.Fade(\''+sd.divSearchResults+'\', {duration: 0.5}); void(0);"><img src="images/close1.gif" border=0 class="floatRight"></a><p>No tag matches found! Please try a different search.</p> ';
							}
						}
				},
				errorHandler:function(errorString, exception){ 
							alert("sidebarSearchTagsAction: "+errorString+" "+exception);
							//showTheError();
				}		
			});	
	};
*/
</script>

  </head><body>

  <!-- #container is the container that wraps around all the main page content -->
  <div id="containerPopUp">

    <a name="filterJump"></a>
    <div id="discussion" style="width: 700px;">
      <div id="discussionHeader">
        <div class="sectionTitle">
          <h2 id="discussionTitle">Concerns within Theme: ${infoObject.object}</h2>
          <div id="filteredBy"></div>
		</div>

		<!-- Begin sorting menu 
        <div id="sortingMenu" class="box4"> sort concerns by:
          <select>
            <option>Option</option>
            <option>Option Option Option Option Option</option>
            <option>Option</option>
            <option>Option</option>
            <option>Option</option>
          </select>
          <br />
          <div class="floatLeft">filter discussion by:</div>
          <form action="javascript: customFilterAction($('txtCustomFilter').value);" class="floatLeft">
            <input type="text" id="txtCustomFilter" value="Add a filter" onKeyUp="customFilter(this.value, event);"  onKeyUp="customFilter(this.value, event);" onClick="javascript:if(this.value==this.defaultValue){this.value = ''}"/>
            or <a href="#">Browse All Tags</a>
          </form>
          <div id="searchResults" style="display: none;"></div>
        </div>
		 End sorting menu -->
		
		
      </div>
      <div id="discussion-cont" class="floatLeft">
        <!-- left col -->
      </div>
      <!-- end left col -->
 
      <div class="clearBoth"></div>
    </div>
    <!-- end discussion -->
  </div>
  <!-- end container -->
  

  
   
  <script type="text/javascript">

		var defaultTagId = '<%= request.getParameter("tag") %>';
		changeCurrentFilter(defaultTagId, 'tagId');
</script>
  </body>

