<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
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
<script type='text/javascript' src='/dwr/interface/BCTAgent.js'></script>
<!--End CCT Specific  Libraries-->
<script type="text/javascript">

//GLOBAL Vars
	var sd = new Object;
	sd.isid = "${isid}";
	sd.ioid = "${ioid}";
	sd.concernCount = 8;
	sd.filterAnchor = "#filterJump";
	sd.currentFilter = '';
	sd.currentPage = 1;
	sd.divFilteredBy = 'filteredBy';
	sd.divDiscussion = 'discussion-cont';
	sd.divSearchResults = 'searchResults';


	function getConcerns(tagId, page, jump){
		sd.currentPage = page;
		if(jump){
			location.href = sd.filterAnchor;
		}
		//alert("isid: " + sd.isid + " ioid: " + sd.ioid + " tagId: " + tagId +" page: " + page + " count: " + sd.concernCount);
    var params = {isid:sd.isid, ioid: sd.ioid, tags: tagId, type:"tag", page: page, count: sd.concernCount};
		SDAgent.getConcerns(params, <pg:wfinfo/>, {
			callback:function(data){
					if (data.successful){
					   	$(sd.divDiscussion).innerHTML = (data.source.html);
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
			BCTAgent.setVoting({id: id, agree:agree}, {
			callback:function(data){
					if (data.successful){ 
						//effect not working right now...might be a timing issue.
						//if($('concernVote'+id) != undefined){
            			//	 new Effect.Fade('concernVote'+id, {afterFinish: function(){getConcerns(sd.currentFilter, sd.currentPage, false);new Effect.Appear('concernVote'+id);}});
            			//}else{ //newly created concern
            				getConcerns(sd.currentFilter, sd.currentPage, false);	
            			//}
					}else{
						alert(data.reason);
					}
				},
			errorHandler:function(errorString, exception){ 
					alert("setVote error:" + errorString + exception);
			}
			});
	};
	
	//convert tagRefId to tagId prior to getting concerns
	function changeCurrentFilter(tagId, type){
		sd.currentFilter = tagId;
		if (tagId != ''){
				if(type == 'tagRef'){
						BCTAgent.getTagByTagRefId(sd.currentFilter, {
						callback:function(data){
						if (data.successful){
							//alert(data.tag.id);
				          			var tagName = data.tag.name;
									$(sd.divFilteredBy).innerHTML = '<h3 class="contrast1">Filtered By: ' + tagName + ' <a href="javascript: changeCurrentFilter(\'\');"><img src="images/close.gif" alt="clear filter" /></a>';
									
									getConcerns(data.tag.id, 0, true)
								}else{
									alert(data.reason);
								}
						},
						errorHandler:function(errorString, exception){ 
								alert("get getTagByTagRefId error:" + errorString + exception);
						}
						});
				}else{ //tagId
						//already in tagId format
						getConcerns(tagId, 0, true);
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
								alert("get getTagById error:" + errorString + exception);
						}
						});
				}
		}else{
			sd.currentFilter = '';	
			$(sd.divFilteredBy).innerHTML = '';
			getConcerns(sd.currentFilter, 0, true);
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
			BCTAgent.searchTags({cctId:cct.cctId,tag:query},{
				callback:function(data){
						if (data.successful){
							if($(sd.divSearchResults).style.display == 'none'){
								new Effect.Appear(sd.divSearchResults, {duration: 0.5});		
							}		
							
							$(cct.divSearchResults).innerHTML = $(sd.divSearchResults).innerHTML = data.html;
							if (data.count == 0){
								$(sd.divSearchResults).innerHTML = '<a href="javascript:Effect.Fade(\''+sd.divSearchResults+'\', {duration: 0.5}); void(0);"><img src="images/close1.gif" border=0 class="floatRight"></a><p>No keyphrase matches found.</p> ';
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
  function pageLoaded() {
    var defaultTagId = "${param.tag}";
		changeCurrentFilter(defaultTagId, 'tag');
  }
</script>
<event:pageunload />
  </head><body style="margin-top:50px;" onLoad="pageLoaded();">

  <!-- #container is the container that wraps around all the main page content -->
  <div id="containerPopUp">

    <a name="filterJump"></a>
    <div id="discussion" style="width: 700px;">
      <div id="discussionHeader">
        <div class="sectionTitle">
          <h3 class="headerColor" id="discussionTitle">Concerns within Theme: ${infoObject.object}</h3>
          <div id="filteredBy"></div>
		</div>		
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
  </body>
</html>
