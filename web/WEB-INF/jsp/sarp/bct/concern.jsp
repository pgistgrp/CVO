<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Step 1a: Brainstorm</title>
<style type="text/css" media="screen">
@import "styles/lit.css";
</style>
<style type="text/css">
.discussionRow{margin-top:10px;}
</style>
<pg:show condition="${!bct.closed}">
<script language="javascript" type="text/javascript" src="scripts/tinymce/jscripts/tiny_mce/tiny_mce.js"></script>
</pg:show>
<script language="JavaScript" src="scripts/qtip.js" type="text/JavaScript"></script>
<script src="scripts/tags.js" type="text/javascript"></script>
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<script src="scripts/search.js" type="text/javascript"></script>

<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>

<!--BCT Specific  Libraries-->
<script type='text/javascript' src='/dwr/interface/BCTAgent.js'></script>
<!--End BCT Specific  Libraries-->

<script type="text/javascript">
  <pg:show condition="${!bct.closed}">
	tinyMCE.init({
		mode : "exact",
		theme : "advanced",
		theme_advanced_buttons1 : "bold, italic, bullist, numlist,undo, redo,link",
		theme_advanced_buttons2 : "",
		theme_advanced_buttons3 : "",
		content_css : "/scripts/tinymce/jscripts/tiny_mce/themes/simple/css/bigmce.css",
		extended_valid_elements : "blockquote[style='']"
	});
  </pg:show>
  //START Global Variables
	var bct = new Object;
	bct.divDiscussionCont = 'discussionBody';
	var concernId = ${concern.id}
	var currentPage = 1;
	var commentCount = 15;
	var divDiscussion = "container-include";
	var filterAnchor = "#filterAnchor";
	var commentAnchor = "#commentAnchor";
	bct.bctId = "${concern.bct.id}";	
	//Inputs
	var txtNewCommentTitle = 'txtNewCommentTitle';
	var txtNewComment = 'txtNewComment';
	var txtNewCommentTags = 'txtNewCommentTags'
  //END Global variables

	function getConcernComments(page, jump){
		currentPage = page;

		BCTAgent.getConcernComments({concernId: concernId, page: currentPage, count: commentCount},<pg:wfinfo/>, {
			callback:function(data){
				if (data.successful){
					$(divDiscussion).innerHTML = data.html; //uses commentsMain.jsp
          <pg:show condition="${!bct.closed}">
					tinyMCE.idCounter=0;
					tinyMCE.execCommand('mceAddControl',false,'txtNewComment');
          </pg:show>
					var anchor = location.hash;
          if (anchor){
              Element.scrollTo(anchor.substr(1,anchor.length));
          }
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("BCTAgent.getConcernComments( error:" + errorString + exception);
			}
		});

	}

	function createNewComment(){
		var title = $(txtNewCommentTitle).value;
		var content = tinyMCE.getContent();
		var tags = '';
						
		//alert("concernId: " + concernId + " title: " + title + " content: " + content + " tags: " + tags); 

		BCTAgent.createConcernComment({concernId: concernId, title: title, content: content, tags: tags},<pg:wfinfo/>,{
			callback:function(data){
				if (data.successful){
					getConcernComments(1, true);
					//setCommentVoting(data.concern.id,'true')
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("BCTAgent.createComment( error:" + errorString + exception);
			}
		});
	}
	
	function goToPage(page){
		getConcernComments(page);
	}
	
	
	function setVote(id, agree){
		BCTAgent.setVoting({id: id, agree:agree}, {
		callback:function(data){
				if (data.successful){ 
					if($('concernVote'+id) != undefined){
           				 new Effect.Fade('concernVote'+id, {afterFinish: function(){getConcernComments(currentPage, false); new Effect.Appear('concernVote'+id);}});
           			}else{ //newly created concern
           				getConcernComments(currentPage, false);
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
	
	function setCommentVoting(id, agree){
		//alert("id: " + id + " agree: " + agree); 
		BCTAgent.setConcernCommentVoting({id:id,agree:agree}, {
			callback:function(data){
				if (data.successful){
					if($('voting-post'+id) != undefined){
           				 new Effect.Fade('voting-post'+id, {afterFinish: function(){getConcernComments(currentPage, false); new Effect.Appear('voting-post'+id);}});
           			}else{ //newly created concern
           				getConcernComments(currentPage, false);
           			}
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("BCTAgent.setCommentVoting( error:" + errorString + exception);
			}
		});
	}

	function resetNewCommentForm(){
		$(txtNewCommentTitle).value = '';
		tinyMCE.setContent('');
		$(txtNewCommentTags).value = '';	
	}
	
	function setQuote(commentId){
		var currentReply = tinyMCE.getContent();
		var currentReplyContent = $('commentContent'+commentId).innerHTML;
		var currentReplyOwner = $('commentOwner'+commentId).innerHTML;
		tinyMCE.setContent(currentReply + '<blockquote style="color:#3B4429;background: #EEF7DD;border: 2px solid #B4D579;margin-left: 10px;padding:0px 5px 5px 5px;line-height: 10px;">' + currentReplyContent + currentReplyOwner +'</blockquote><p>');
		location.href="#commentAnchor";
		new Effect.Highlight('newcomment');
	};
	/*
	function editComment(params){
		//alert("isid: " + sd.isid + " ioid: " + sd.ioid + " tags: " + tags + " page: " + page + " count: " + sd.concernCount); 
		BCTAgent.editComment({dwrParams}, {
			callback:function(data){
				if (data.successful){
					alert(data.html)
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("BCTAgent.editComment( error:" + errorString + exception);
			}
		});
	}
	*/
	function deleteComment(id){
		//alert("isid: " + sd.isid + " ioid: " + sd.ioid + " tags: " + tags + " page: " + page + " count: " + sd.concernCount); 
		var destroy = confirm ("Are you sure you want to delete this comment? Note: there is no undo.")
		if(destroy){
		    BCTAgent.deleteConcernComment({commentId: id}, {
    		callback:function(data){
    				if (data.successful){
    					new Effect.Puff("comment" + id, {afterFinish: function(){getConcernComments(currentPage, false);}});
    				}else{
    					alert(data.reason);
    				}
    			},
    			errorHandler:function(errorString, exception){ 
    			alert("BCTAgent.deleteComment( error:" + errorString + exception);
    			}
    		});
	    }
	}

function toggleEditing(component, concernId){
	var concernBody = 'discussionText' + concernId;
	var concernTags = 'tagsUL' + concernId;
	var editConcern = 'editingArea' +concernId;
	var editTags = 'tagEditingArea' +concernId;	
	
	if(component == "concern"){
		($(editTags).style.display != "none") ? Element.toggle(editTags) : '';
		Element.toggle(concernTags);
		Element.toggle(concernBody);
		Element.toggle(editConcern);
	}
	if(component == "tags"){
		($(editConcern).style.display != "none") ? Element.toggle(editConcern) : '';
		($(concernBody).style.display == "none") ? Element.toggle(concernBody) : '';
		($(concernTags).style.display != "none") ? Element.toggle(concernTags) : '';
		Element.toggle(editTags);
	}	
}

function editConcern(concernId){
	var newConcern = $('editConcern').value;
	var concernBody = 'discussionText' + concernId;
	BCTAgent.editConcern({concernId:concernId, concern:newConcern}, <pg:wfinfo/>,{
		callback:function(data){
				if (data.successful){
					getConcernComments(currentPage, false);
				
				}  
		},
		errorHandler:function(errorString, exception){ 
			alert("editConcern: "+errorString+" "+exception);
			//showTheError();
		}
	});
}

function editConcernPopup(concernId){
var currentConcern = '';
BCTAgent.getConcernById(concernId, {

	callback:function(data){
		if (data.successful){
				currentConcern = data.concern.content;
				toggleEditing('concern', concernId);
				var editConcern = 'editingArea' +concernId;
					os = "";
					os += '<textarea style="" name="editConcern" id="editConcern" cols="50" rows="5">' +currentConcern+ '</textarea>';
					os += '<br/><input type="button" id="modifyConcern" value="Submit Edits!" onclick="javascript:editConcern('+concernId+');"/>';
					os += '<input type="button" value="Cancel" onClick="javascript:toggleEditing(\'concern\', '+concernId+');">';
				$(editConcern).innerHTML = os;		
		}
	},
	errorHandler:function(errorString, exception){ 
			alert("editConcernPopup: "+errorString+" "+exception);
			//showTheError();
	}

});

}

function editTagsPopup(concernId){
	tagHolderId = 1;
	concernTags = "";

	BCTAgent.getConcernById(concernId, {
	callback:function(data) {
			if (data.successful){
			var tagDiv = 'tagEditingArea' +concernId;
			toggleEditing('tags', concernId);
					
			//Find Current Tags
					newConcernTagsArray = [];
					editingTags = data.concern.tags;
					for(i=0; i < editingTags.length; i++){
						addToConcernTagsArray(editingTags[i].tag.name, "checked");
					}
					$(tagDiv).innerHTML = renderEditingTags(concernId);  
				}
		},
		errorHandler:function(errorString, exception){ 
				alert("editTagsPopup: "+errorString+" "+exception);
				//showTheError();
		}
	});
}

function addToConcernTagsArray(tagName, status){
	var newConcernTagInstance = new NewConcernTag(tagName, status);
	newConcernTagsArray.push(newConcernTagInstance);
}

function NewConcernTag(tagName, status){
	this.tagName = tagName;
	this.status = status;
}

function renderEditingTags(concernId){
	os = '<ul  class="tagsList">' + renderTags(); + '</ul>';
	os += '<form action="javascript: addManualEditTag('+concernId+');"><input id="manualEditTag" type="text" />';
      os += '<input type="button" value="Add Tag" onClick="addManualEditTag('+concernId+');" /></form>';
      os += '<p><small>You must use at least 2 or more keywords/phrases to continue.</small></p>';
     	os += '<div><hr><input type="button" id="subeditTags" value="Submit Edits" onClick="editTags('+concernId+')">';
	os += '<input type="button" value="Cancel" onClick="javascript:toggleEditing(\'tags\', '+concernId+');"></div>';
     return os;
}

function renderTags(){
	var str= "";
	for(i=0; i < newConcernTagsArray.length; i++){
		if(newConcernTagsArray[i] != ""){
			str += '<li><label><input type="checkbox" '+newConcernTagsArray[i].status+' onclick="checkNewConcernTag('+ i +');" />'+ newConcernTagsArray[i].tagName +'</label></li>';
		}
	}	
	return str;
}

function addManualEditTag(concernId){
	var tagDiv = 'tagEditingArea' + concernId;
	var manualTag = $('manualEditTag').value;
	if(manualTag != ""){
		addToConcernTagsArray(manualTag, "checked");	
		$('manualEditTag').value = ""; //clear textbox
		$(tagDiv).innerHTML = renderEditingTags(concernId);  
	}else{
		alert("Keyword/phase cannot be blank.");
	}
}
		
function renderEditingTags(concernId){
	os = '<ul  class="tagsList">' + renderTags(); + '</ul>';
	os += '<form action="javascript: addManualEditTag('+concernId+');"><input id="manualEditTag" type="text" />';
      os += '<input type="button" value="Add Tag" onClick="addManualEditTag('+concernId+');" /></form>';
      os += '<p><small>You must use at least 2 or more keywords/phrases to continue.</small></p>';
     	os += '<div><hr><input type="button" id="subeditTags" value="Submit Edits" onClick="editTags('+concernId+')">';
	os += '<input type="button" value="Cancel" onClick="javascript:toggleEditing(\'tags\', '+concernId+');"></div>';
     return os;
}
	

function editTags(concernId){
	getSelectedTags();
	if(newConcernSelectedTagsArray.length<bct.numTagsInNewConcern){
		alert("You must use at least "+ bct.numTagsInNewConcern +" keywords/phrases");
	}else{
	var newConcernSelectedTagsString = newConcernSelectedTagsArray.toString();
	//for (i=0; i<newConcernSelectedTagsArray.length; i++){
		//newConcernSelectedTagsString += newConcernSelectedTagsArray[i] + ',';	
	//}
	BCTAgent.editTags({concernId:concernId, tags:newConcernSelectedTagsString}, <pg:wfinfo/>,{
		callback:function(data){
			if (data.successful){ 
				getConcernComments(currentPage, false);
			}else{
				alert(data.reason);	
			}
			
		},
		errorHandler:function(errorString, exception){ 
				alert("editTags: "+errorString+" "+exception);
				//showTheError();
		}
	});
	}
}

function getSelectedTags(){
	newConcernSelectedTagsArray = [];
	for(i=0; i<newConcernTagsArray.length; i++){
		if(newConcernTagsArray[i].status == 'checked'){
			newConcernSelectedTagsArray.push(newConcernTagsArray[i].tagName);
		}
	}	
	//alert(newConcernSelectedTagsArray);
}
	
function checkNewConcernTag(index){
	if(newConcernTagsArray[index].status == "unchecked"){
		newConcernTagsArray[index].status = "checked";
	}else{
		newConcernTagsArray[index].status = "unchecked";
	}
}

function changeCurrentFilter(tagRefId){
	$(bct.chbxMyConcerns).checked = false; //a user's concerns can not be filtered.
	checkMyConcerns();
	if (tagRefId != ''){
			BCTAgent.getTagByTagRefId(tagRefId, {
			callback:function(data){
			if (data.successful){
						var tagName = data.tag.name;
						getConcernComments(currentPage, false);
						bct.currentFilter = tagName;
	          			
						$(bct.divFilteredBy).innerHTML = '<h3 class="contrast1">Filtered By: ' + tagName + ' <a href="javascript: changeCurrentFilter(\'\');"><img src="images/close.gif" alt="clear filter" /></a>';
					}else{
						alert(data.reason);
					}
			},
			errorHandler:function(errorString, exception){ 
					alert("get tagbytagref error:" + errorString + exception);
			}
			});
	}else{	
		bct.currentFilter='';
		getConcernComments(currentPage, false);
		$(bct.divFilteredBy).innerHTML = '';
		
	}
}

function onPageLoaded() {
  window.setTimeout('tooltip.init()',1000);
  getConcernComments(currentPage, false);
}

</script>
<wf:pageunload />
</head>
<body onload="onPageLoaded();">
    <!-- Start Global Headers  -->
    <wf:nav />
    <wf:subNav />
    <!-- End Global Headers -->
    <div style="display: none;" id="loading-indicator">Loading... <img src="/images/indicator_arrows.gif"></div>
    <div id="container">
      <div id="container-include">
        <!-- load commentsMain.jsp via AJAX-->
      </div>
      <!-- start feedback form -->
      <pg:feedback id="feedbackDiv" action="sdRoom.do" />
      <!-- end feedback form -->
    </div><!-- end container -->
    <!-- Start Global Headers  -->
    <wf:subNav />
    <!-- End Global Headers -->
    <!-- Begin footer -->
    <div id="footer">
      <jsp:include page="/footer.jsp" />
    </div>
  <!-- End footer -->
</body>

</html:html>
