<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>

<html:html>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Step 1a: Brainstorm</title>
<!-- Site Wide CSS -->
<style type="text/css" media="screen">
@import "styles/lit.css";
#sortingMenu{width:680px;padding-right:0px;}
</style>
<!-- End Site Wide CSS -->
<!-- Site Wide JavaScript -->
<script language="JavaScript" src="scripts/qtip.js" type="text/JavaScript"></script>
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
<script type='text/javascript' src='/dwr/interface/CCTAgent.js'></script>
<!--End CCT Specific  Libraries-->
<script type="text/javascript">
//START Global Variables

var cct = new Object;
var newConcernTagsArray = new Array();
var newConcernSelectedTagsArray = new Array();
var allNewConcernTags = new Array;
//Element IDs - allows for easier maintanence.
	cct.divDiscussionCont = 'discussion-cont';
	cct.divTagNewConcern = 'tagNewConcern';
	cct.divAddConcernTagsList = 'addConcernTagsList';
	cct.divFilteredBy = 'filteredBy';
	cct.divSearchResults = 'searchResults';
	cct.filterAnchor = "#filterJump";
	cct.divTagCloud = 'tagCloud';
	cct.divDiscussionTitle = "discussionTitle";
		cct.discussionTitle = "All participants' concerns";
		cct.discussionTitleOnChk = "Your concerns";
		
//Input Element IDs
	cct.txtAddConcern = 'txtAddConcern';
	cct.btnContinueCont = 'btnContinueCont';
	cct.chbxMyConcerns = 'myconcerns';
	
//Settings
	cct.currentPage = 0;
	cct.currentSort = 1; //Initial sorting
	cct.currentFilter = '';  //Default tag filter
	cct.numTagsInNewConcern = 2;
	cct.concernsPerPage = 8;
	cct.showOnlyMyConcerns = false; 
	cct.concernsDesc = true; //concern order
	cct.cctId = "${cctForm.cct.id}";
	cct.tagCloudCount = 20;
	cct.currentTagCloudPage = 1;


//END Global Variables

	function getContextConcerns(filter, page, jump, showMyConcerns, sorting){
		//alert("filter" + filter + " page: " + page + " jump: " + jump + " showMyConcerns: " + showMyConcerns + " sorting: " + sorting)
		if(jump){
			location.href = cct.filterAnchor; //set anchor if need be
		}		
		cct.showOnlyMyConcerns = showMyConcerns;
		type = (cct.showOnlyMyConcerns) ? "owner" : "all"; //determine type
		cct.currentPage = page; //set current page
		cct.currentSort = sorting; 
		
	    //	alert("cct: " + cct.cctId + " filter: " + filter + " count: " + cct.concernsPerPage + " page: " + page + " sorting: " + cct.currentSort + " type: " + type);
		CCTAgent.getContextConcerns({cctId: cct.cctId,filter: filter, count: cct.concernsPerPage, page: page, sorting: cct.currentSort, type: type},<pg:wfinfo/>, {
			callback:function(data){
					if (data.successful){
						$(cct.divDiscussionCont).innerHTML = data.html;
					}else{
						alert(data.reason);
					}
				},
				async:false,
			errorHandler:function(errorString, exception){ 
					alert("getContextConcerns error:" + errorString + exception);
			}
		});	
		return true
	}
		
	function goToPage(page, section){
		if(section=="tagCloud"){
			cct.currentTagCloudPage=page;
			getNextTagCloud();
		}else{
		getContextConcerns(cct.currentFilter,page,true, cct.showOnlyMyConcerns, cct.currentSort); 
		//new Effect.Highlight('discussion-cont',{startcolor: "#D6E7EF"});
		}
	}
	
	
	function checkMyConcerns(){
		if($(cct.chbxMyConcerns).checked != true){
			$(cct.divFilteredBy).style.display = 'inline';
			getContextConcerns(cct.currentFilter, cct.currentPage, false, false, cct.currentSort);
			location.href= cct.filterAnchor;
			$(cct.divDiscussionTitle).innerHTML = cct.discussionTitle;
		}else{ //when it is checked
			$(cct.divFilteredBy).style.display = 'none';
			getContextConcerns('', cct.currentPage, false, true, cct.currentSort);
			location.href= cct.filterAnchor;
			$(cct.divDiscussionTitle).innerHTML = cct.discussionTitleOnChk;
		}
	}

	
	//ADD Concern Functions
	function validateForm(){
		if($(cct.txtAddConcern).value == "" ||$(cct.txtAddConcern).value  == $(cct.txtAddConcern).defaultValue ){ 
				alert('Please fill in your concern above.');
				return false;
		}else{
				new Effect.BlindDown(cct.divTagNewConcern, {duration: 0.2});
				
				return true;
		}
	}
	
	function resetForm(){
		$(cct.txtAddConcern).value = '';
	}

	function cancelSubmit(){
		Effect.BlindUp(cct.divTagNewConcern, {duration: 0.2});
	}
	
	function swapContinue(showContinue){
		if(!showContinue){
			$(cct.btnContinueCont).innerHTML = '<input id="btnContinue" type="button" value="Continue" onClick="prepareConcern(); swapContinue(true);" />';
			$(cct.txtAddConcern).disabled = false;
		}else{
			$(cct.btnContinueCont).innerHTML = '<input id="btnCancelNewConcern" type="button" value="Cancel" onClick="cancelSubmit(); swapContinue();" /><input id="btnSubmitNewConcern" type="button" value="Submit" onClick="saveConcern();" />';
			$(cct.txtAddConcern).disabled = true;
		}
	}

	function prepareConcern(){  //Find tags and potential tags to render
		var concernTags = new Array;
		var potentialTags = new Array;
		var concern = $(cct.txtAddConcern).value;
		
		if (validateForm()){	
			CCTAgent.prepareConcern({cctId:cct.cctId,concern:concern}, function(data) {
				if (data.successful){
					swapContinue(true);
					newConcernTagsArray = [];
					for(i=0; i< data.tags.length; i++){
						concernTags.push(data.tags[i]);
					}
					for(i=0; i < data.potentialtags.length; i++){
						potentialTags.push(data.potentialtags[i]);
					}
					allNewConcernTags = concernTags.concat(potentialTags);
					for(i=0; i < allNewConcernTags.length; i++){
						addToConcernTagsArray(allNewConcernTags[i], "unchecked");
					}
				
					$(cct.divAddConcernTagsList).innerHTML = renderTags();  
				}else{
					alert(data.reason);	
				}
			} );
		}
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
	
	function checkNewConcernTag(index){
		if(newConcernTagsArray[index].status == "unchecked"){
			newConcernTagsArray[index].status = "checked";
		}else{
			newConcernTagsArray[index].status = "unchecked";
		}
	}
	
	function NewConcernTag(tagName, status){
		this.tagName = tagName;
		this.status = status;
	}
	
	function addToConcernTagsArray(tagName, status){
		var newConcernTagInstance = new NewConcernTag(tagName, status);
		newConcernTagsArray.push(newConcernTagInstance);
	}
	
	function getTagCloud(){
		//alert('cctId: ' + cct.cctId + ' count: ' + cct.tagCloudCount + ' page: ' + cct.currentTagCloudPage);
			CCTAgent.getTagCloud({cctId:cct.cctId,type:2,count:cct.tagCloudCount, page: cct.currentTagCloudPage},{
				callback:function(data){
						if (data.successful){			
							$(cct.divTagCloud).innerHTML = data.html;
							if (data.count == 0){
								$(cct.divTagCloud).innerHTML = '<a href="javascript:Effect.Fade(\''+cct.divTagCloud+'\', {duration: 0.5}); void(0);"><img src="images/close1.gif" border=0 class="floatRight"></a><p>No tag matches found! Please try a different search.</p> ';
							}
							if($(cct.divTagCloud).style.display == 'none'){
								new Effect.BlindDown(cct.divTagCloud, {duration: 0.5});		
							}else{
								new Effect.BlindUp(cct.divTagCloud, {duration: 0.5});	
							}		
						}else{
							alert(data.reason);
						}
				},
				errorHandler:function(errorString, exception){ 
							alert("getTagCloud: "+errorString+" "+exception);
				}		
			});	
	};
	
	function getNextTagCloud(){
		
			CCTAgent.getTagCloud({cctId:cct.cctId,type:2,count:cct.tagCloudCount, page: cct.currentTagCloudPage},{
				callback:function(data){
						if (data.successful){			
							$(cct.divTagCloud).innerHTML = data.html;
							if (data.count == 0){
								$(cct.divTagCloud).innerHTML = '<a href="javascript:Effect.Fade(\''+cct.divTagCloud+'\', {duration: 0.5}); void(0);"><img src="images/close1.gif" border=0 class="floatRight"></a><p>No tag matches found! Please try a different search.</p> ';
							}
							
						}else{
							alert(data.reason);
						}
				},
				errorHandler:function(errorString, exception){ 
							alert("getNextTagCloud: "+errorString+" "+exception);
				}		
			});	
	};
	
	
	
	function addManualTag(){
		var manualTag = $('manualTag').value;
		if(manualTag != $('manualTag').defaultValue){  //couldn't get the or operator working for some reason?
			if(manualTag != ""){
				addToConcernTagsArray(manualTag, "checked");	
				$(cct.divAddConcernTagsList).innerHTML = renderTags();
				$('manualTag').value = ""; //clear textbox
			}else{
				alert("Tag can not be blank!");	
			}
		}else{
			alert("Tag can not be blank!");	
		}
		//for(i=0; i< newConcernTagsArray.length; i++){
		//	alert(newConcernTagsArray[i].tagName + " status:" + newConcernTagsArray[i].status);	
		//}
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
				
	function saveConcern(){
		getSelectedTags();
		if(newConcernSelectedTagsArray.length<cct.numTagsInNewConcern){
		alert("You must use at least "+ cct.numTagsInNewConcern +" tags");
		}else{
		var concern = $(cct.txtAddConcern).value;
		var newConcernSelectedTagsString = '';
		for (i=0; i<newConcernSelectedTagsArray.length; i++){
			newConcernSelectedTagsString += newConcernSelectedTagsArray[i] + ',';	
		}
		//alert(newConcernSelectedTagsString);
		//alert('cctId:' + cctId + ', concern: ' + concern + ', tags: ' + newConcernSelectedTagsString);
		//alert(newConcernSelectedTagsString);
		
		CCTAgent.saveConcern({cctId:cct.cctId,concern:concern,tags:newConcernSelectedTagsString},<pg:wfinfo/>, {
			callback:function(data){
				if (data.successful){
					getContextConcerns(cct.currentFilter, 0, true, cct.showOnlyMyConcerns, cct.currentSort); 	
					//setVote(data.concern.id, "true")
					swapContinue(false);
					$(cct.txtAddConcern).value = '';
					new Effect.BlindUp(cct.divTagNewConcern);
					window.setTimeout('new Effect.Highlight("concern'+ data.concern.id +'", {duration: 4.0});',1000);
					//new Effect.Highlight("concern"+data.concern.id, {duration:4.0});
				}else{
					alert(data.reason)
				}
			},
			errorHandler:function(errorString, exception){ 
				alert("saveTheConcern: "+errorString+" "+exception);
			}
	});

	}
	}
	
	function setVote(id, agree){
		CCTAgent.setVoting({id: id, agree:agree}, {
		callback:function(data){
				if (data.successful){ 
					if($('concernVote'+id) != undefined){
           				 new Effect.Fade('concernVote'+id, {afterFinish: function(){getContextConcerns(cct.currentFilter,cct.currentPage, false, cct.showOnlyMyConcerns, cct.currentSort); new Effect.Appear('concernVote'+id);}});
           			}else{ //newly created concern
           				getContextConcerns(cct.currentFilter, cct.currentPage, false, cct.showOnlyMyConcerns, cct.currentSort); 	
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
	//END ADD Concern Functions
	
	function deleteConcern(concernId){
		var destroy = confirm ("Are you sure you want to delete this concern? Note: there is no undo.")
		if (destroy){
				CCTAgent.deleteConcern({concernId:concernId}, {
				callback:function(data){	
						if (data.successful){
							new Effect.Puff('concern'+concernId, {afterFinish:function(){getContextConcerns(cct.currentFilter,cct.currentPage,false, cct.showOnlyMyConcerns, cct.currentSort);}});
						}else{
							alert(data.reason);	
						}
				},
				errorHandler:function(errorString, exception){ 
					alert("delConcern: "+errorString+" "+exception);
					//showTheError();
				}
				});
		}
	}
	
	function changeCurrentFilter(tagRefId){
		$(cct.chbxMyConcerns).checked = false; //a user's concerns can not be filtered.
		checkMyConcerns();
		if (tagRefId != ''){
				CCTAgent.getTagByTagRefId(tagRefId, {
				callback:function(data){
				if (data.successful){
							var tagName = data.tag.name;
							getContextConcerns(tagName, 0, true, cct.showOnlyMyConcerns, cct.currentSort);
							cct.currentFilter = tagName;
		          			
							$(cct.divFilteredBy).innerHTML = '<h3 class="contrast1">Filtered By: ' + tagName + ' <a href="javascript: changeCurrentFilter(\'\');"><img src="images/close.gif" alt="clear filter" /></a>';
						}else{
							alert(data.reason);
						}
				},
				errorHandler:function(errorString, exception){ 
						alert("get tagbytagref error:" + errorString + exception);
				}
				});
		}else{	
			cct.currentFilter='';
			getContextConcerns('', 0, true, cct.showOnlyMyConcerns, cct.currentSort);
			$(cct.divFilteredBy).innerHTML = '';
			
		}
	}
	
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
							if($(cct.divSearchResults).style.display == 'none'){
								new Effect.Appear(cct.divSearchResults, {duration: 0.5});		
							}		
							
							$(cct.divSearchResults).innerHTML = $(cct.divSearchResults).innerHTML = data.html;
							if (data.count == 0){
								$(cct.divSearchResults).innerHTML = '<a href="javascript:Effect.Fade(\''+cct.divSearchResults+'\', {duration: 0.5}); void(0);"><img src="images/close1.gif" border=0 class="floatRight"></a><p>No tag matches found! Please try a different search.</p> ';
							}
						}
				},
				errorHandler:function(errorString, exception){ 
							alert("sidebarSearchTagsAction: "+errorString+" "+exception);
							//showTheError();
				}		
			});	
	};


	//END CCT Cleanup


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


function editConcernPopup(concernId){
var currentConcern = '';
CCTAgent.getConcernById(concernId, {

	callback:function(data){
		if (data.successful){
				currentConcern = data.concern.content;
				toggleEditing('concern', concernId);
				var editConcern = 'editingArea' +concernId;
					os = "";
					os += '<textarea style="" name="editConcern" id="editConcern" cols="50" rows="5">' +currentConcern+ '</textarea>';
					os += '<input type="button" id="modifyConcern" value="Submit Edits!" onclick="javascript:editConcern('+concernId+');"/>';
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


function editConcern(concernId){
	var newConcern = $('editConcern').value;
	var concernBody = 'discussionText' + concernId;
	CCTAgent.editConcern({concernId:concernId, concern:newConcern}, <pg:wfinfo/>,{
		callback:function(data){
				if (data.successful){
					getContextConcerns(cct.currentFilter, cct.currentPage, false, cct.showOnlyMyConcerns, cct.currentSort);
				
				}  
		},
		errorHandler:function(errorString, exception){ 
			alert("editConcern: "+errorString+" "+exception);
			//showTheError();
		}
	});
}
	

function editTagsPopup(concernId){
	tagHolderId = 1;
	concernTags = "";

	CCTAgent.getConcernById(concernId, {
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

	function renderEditingTags(concernId){
		os = '<ul  class="tagsList">' + renderTags(); + '</ul>';
		os += '<form action="javascript: addManualEditTag('+concernId+');"><input id="manualEditTag" type="text" />';
        os += '<input type="button" value="Add Tag" onClick="addManualEditTag('+concernId+');" /></form>';
        os += '<p><small>You must use at least 2 or more tags to continue.</small></p>';
       	os += '<div><hr><input type="button" id="subeditTags" value="Submit Edits" onClick="editTags('+concernId+')">';
		os += '<input type="button" value="Cancel" onClick="javascript:toggleEditing(\'tags\', '+concernId+');"></div>';
       return os;
	}

	function addManualEditTag(concernId){
		var tagDiv = 'tagEditingArea' + concernId;
		var manualTag = $('manualEditTag').value;
		if(manualTag != ""){
			addToConcernTagsArray(manualTag, "checked");	
			$('manualEditTag').value = ""; //clear textbox
			$(tagDiv).innerHTML = renderEditingTags(concernId);  
		}else{
			alert("Tag can not be blank.");
		}
		
		//alert(tagDiv);
		//alert(manualTag);
		//for(i=0; i< newConcernTagsArray.length; i++){
		//	alert(newConcernTagsArray[i].tagName + " status:" + newConcernTagsArray[i].status);	
		//}
		
	}

function removeLastComma(str){
str = str.replace(/[\,]$/,'');
concernTags = str;
}

function editTags(concernId){
	getSelectedTags();
	if(newConcernSelectedTagsArray.length<cct.numTagsInNewConcern){
		alert("You must use at least "+ cct.numTagsInNewConcern +" tags");
	}else{
	var newConcernSelectedTagsString = newConcernSelectedTagsArray.toString();
	//for (i=0; i<newConcernSelectedTagsArray.length; i++){
		//newConcernSelectedTagsString += newConcernSelectedTagsArray[i] + ',';	
	//}
	CCTAgent.editTags({concernId:concernId, tags:newConcernSelectedTagsString}, <pg:wfinfo/>,{
		callback:function(data){
			if (data.successful){ 
				getContextConcerns(cct.currentFilter, cct.currentPage, false, false, cct.currentSort);
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

/*

function ifEnter(field,event) {

var theCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
if (theCode == 13){
prepareConcern();
$('theTag').focus();
return false;
} 
else
return true;
}   


var winH;
function getWinH(){
if (parseInt(navigator.appVersion)>3) {
 if (navigator.appName=="Netscape") {
  winH = window.innerHeight;
 }
 if (navigator.appName.indexOf("Microsoft")!=-1) {
  winH = document.body.offsetHeight;
 }
}

alert(winH);
$('slate').style.Height = winH;
}


*/

</script>


  <event:pageunload />
  </head><body>
  <!-- Start Global Headers  -->
  <wf:nav />
  <wf:subNav />
  <!-- End Global Headers -->
  <!-- #container is the container that wraps around all the main page content -->
  <div id="container">
    <!-- begin "overview and instructions" area -->
    <div id="overview" class="box2">
    <!--
    		<p>***** Started on: <fmt:formatDate value="${current.beginTime}" dateStyle="long" /> *****</p>
    		<p>***** Ends on: <fmt:formatDate value="${current.endTime}" dateStyle="long" /> *****</p>    
    -->
			<h3>Overview and instructions</h3>
    		
    		<c:set var="current" value="${requestScope['org.pgist.wfengine.CURRENT']}" />
			
			<pg:termHighlight styleClass="glossHighlight" url="glossaryView.do?id=">
			Before we can determine how to best improve the transportation system, we need to know what the problems are. In this step you can:
			<ul>
				<li>Read concerns posted by other participants.</li>
				<li>Share your own concerns.</li>
			</ul>
			<p>Brainstorming will conclude at midnight on <strong><fmt:formatDate value="${current.endTime}" dateStyle="long" /></strong>.</p>
			<a href="#" onclick="Effect.toggle('hiddenRM','blind'); return false">Read more about this step</a>
				<p id="hiddenRM" style="display:none">When step 1b concludes on October 3 the moderators will review and summarize them according to themes. The final version of these summaries will be included in the final report of the <em>LIT Challenge</em>. The summaries will also be used in Step 2 when we assess different "factors" used to evaluate proposed transportation improvement projects.</p>
			</pg:termHighlight>
    </div>
    <!-- end overview -->

    <a name="filterJump"></a>
    <div id="discussion" style="background-image: url('images/addConcern.gif'); background-repeat: no-repeat; background-position: 730px 0;">
      <div id="discussionHeader">
        <div class="sectionTitle">
          <h3 id="discussionTitle">All participants' concerns</h3>
          <div id="filteredBy"></div>
          <input type="checkbox" id="myconcerns" onClick="checkMyConcerns();"/>
          Show only my concerns </div>
        
		
				<!-- Begin sorting menu -->
				<div id="sortingMenu" class="box4"> 
					<form style="display:inline" 
				action="javascript: customFilterAction($('txtCustomFilter').value);">
						<span id="sm-left">Search concerns:
						<input type="text" id="txtCustomFilter" value="Add a filter" 
					onKeyUp="customFilter(this.value, event);"  onKeyUp="customFilter(this.value, event);" 
					onClick="javascript:if(this.value==this.defaultValue){this.value = ''}"/>
						</span> <span id="sm-middle"> <a href="javascript:getTagCloud();">Browse
						keywords</a> <a href="javascript:getTagCloud();"><img src="images/keyword-cloud.gif" alt="Click here for the Keyword Cloud" /></a></span>
					</form>
					<span id="sm-right"> Sort concerns by:
					<select name="selectsort" id="selectsort" 
				onChange="javascript:getContextConcerns(cct.currentFilter, 1, true, cct.showOnlyMyConcerns, this.value);	">
						<option value="1">Newest to oldest</option>
						<option value="2">Oldest to newest</option>
						<option value="3">Most agreement</option>
						<option value="4">Least agreement</option>
						<option value="5">Most comments</option>
						<option value="6">Most views</option>
						<option value="7">Most votes</option>
					</select>
					</span>
					<div id="searchResults" style="display: none;"></div>
					<div class="clearBoth"></div>
				</div>
				<!-- End sorting menu -->
</div>
	  
	  <!-- start tag cloud -->
  	<div id="tagCloud" class="discussion-left box2" style="display: none;">
  		<!-- load "browse all tags" tag cloud -->
  	</div>	
<!-- end tag cloud -->

      <div id="discussion-cont" class="floatLeft">
        <!-- left col -->
      </div>
      <!-- end left col -->
      <div id="colRight" class="floatLeft box6 colRight">
        <!-- right col -->
        <h3>Add your own concern</h3>
		<p>Consider these questions:</p>
		  <ul>
			  <li>What problems do you encounter in your daily trips to work, shopping, errands, or entertainment? </li>
			  <li>In what ways do you feel our current transportation system 
			  fails to meet the needs of our growing and changing region?</li>
		  </ul></p>
        <fieldset>
        <textarea id="txtAddConcern" style="width:100%; border: 1px solid #FFC978; height: 100px;" onClick="if(this.value==this.defaultValue){this.value = ''}">Type in one concern about the transportation system. You can enter more later.</textarea>
        </fieldset>
        <div id="tagNewConcern" class="box6 padding5" style="display:none;">
          <h3>Keyword your concern</h3>
		<p><a href="litfaq.jsp#whattag" target="_blank" class="glossHighlight" title="Think of keywords as labels.  At this stage of the process, they assist the moderator in writing summaries, so it is important to keyword your concerns with words that help convey your meaning.">What is a keyword?</a> <img src="images/external.png" alt="(new window)"></p>
          <p>Suggested keywords:</p>
          <ul id="addConcernTagsList" class="tagsList">
            <!-- render suggested tags here -->
          </ul>
          <form action="javascript: addManualTag();">
            <input id="manualTag" type="text" size="10" value="Add your own keyword" onClick="if(this.value==this.defaultValue){this.value = ''}"/>
            <input type="button" value="Add Keyword" onClick="addManualTag();" />
          </form>
          <p><small>You must use at least 2 or more keywords to continue.</small></p>
        </div>
        <div id="btnContinueCont">
          <input id="btnContinue" type="button" value="Continue" onClick="prepareConcern();" />
        </div>
      </div>
      <!-- end right col -->
      <div class="clearBoth"></div>
    </div>
    <!-- end discussion -->
  </div>
  <!-- end container -->
  
<!-- start feedback form -->
  <pg:feedback id="feedbackDiv" action="cctView.do"/>
<!-- end feedback form -->

<!-- Start Global Headers  -->
    <wf:subNav />
<!-- End Global Headers -->

	<!-- Begin footer -->
	<div id="footer">
		<jsp:include page="/footer.jsp" />
	</div>
	<!-- End footer -->
  
   
  <script type="text/javascript">
		getContextConcerns('', 1, false, cct.showOnlyMyConcerns, cct.currentSort);
		
</script>
  </body>
</html:html>
