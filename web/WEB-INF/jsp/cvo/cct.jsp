<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>PGIST: Step 1: Brainstorm Concerns</title>

<!-- Site Wide CSS -->
<style type="text/css" media="screen">@import "styles/position.css";</style>
<style type="text/css" media="screen">@import "styles/styles.css";</style>
<!-- End Site Wide CSS -->

<!-- Site Wide JS -->
<script src="scripts/search.js" type="text/javascript"></script>
<script src="scripts/tags.js" type="text/javascript"></script>
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<!-- End Site Wide JS -->

<!-- DWR JavaScript Libraries -->
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type='text/javascript' src='/dwr/interface/CCTAgent.js'></script>
<!-- End DWR JavaScript Libraries -->
<script src="scripts/lightbox.js" type="text/javascript"></script>

<script type="text/javascript">
	
var cctId = ${cctForm.cct.id};
var concernTags = "";
doOnLoad();




function doOnLoad(){
	showConcerns(2);
	showMyConcerns();
	
	
}
function validateForm()
{
	if(""==document.forms.brainstorm.addConcern.value)
	{
		document.getElementById('validation').innerHTML = 'Please fill in your concern above.';
		new Effect.BlindDown('validation');
		new Effect.BlindUp('tagConcerns');
		new Effect.Highlight('validation', {duration: 4, endcolor:'#EEF3D8'});
		new Effect.Highlight('theTag', {duration: 10, endcolor:'#EEF3D8'});
		return false;

	}else{
		new Effect.BlindUp('validation');
		new Effect.BlindDown('tagConcerns');
		new Effect.Highlight('tags', {duration: 4, endcolor:'#F1F7FF'});
		//$('theTag').value = "add tag";
		//$('theTag').focus();
		return true;
	}
}


function resetForm()
{
	$('addConcern').value = '';
	$('btnContinue').disabled=false;
	Effect.BlindUp('tagConcerns');
	Effect.BlindUp('validation');
	$('addConcern').style.background="#FFF";
	$('addConcern').style.color="#333";
}


function prepareConcern(){
	concernTags = "";
	potentialTags = "";
	tagHolderId = 0;
	if (validateForm()){
		$('btnContinue').disabled=true;
		$('addConcern').style.background="#EEE";
		$('addConcern').style.color="#CCC";
		var concern = $('addConcern').value;
		$('indicator').style.visibility = "visible";
		
		CCTAgent.prepareConcern({cctId:cctId,concern:concern}, function(data) {
			if (data.successful){
				for(i=0; i < data.tags.length; i++){
					concernTags += data.tags[i] + ',';
				}
				for(i=0; i < data.potentialtags.length; i++){
					potentialTags += data.potentialtags[i] + ',';
				}
				document.getElementById('tagsList').innerHTML = renderTags( concernTags, 1);  // + renderTags( data.suggested, 0);
				document.getElementById('tagsList').innerHTML += renderTags(potentialTags, 1);
				concernTags += potentialTags;
			}
			document.getElementById("indicator").style.visibility = "hidden";
		} );
	}
}

var tagHolderId = 1;

function removeFromGeneratedTags(name){
	if(name == "")return;
	var indexNum = concernTags.indexOf(name +',');
	if (indexNum > 0){
		firstpart = concernTags.substring(0, indexNum);
		secondpart = concernTags.substring(indexNum + name.length + 1, concernTags.length);
		concernTags = firstpart + secondpart;
	}else if (indexNum == 0){
		concernTags = concernTags.substring(indexNum + name.length +1, concernTags.length);
	}

	if (tagHolderId == 0){
		document.getElementById('tagsList').innerHTML = renderTags( concernTags, 1);
	}else{
		document.getElementById('editTagsList').innerHTML = renderTags( concernTags, 1);
	}
}

function renderTags(tags,type){
	sty = (type == 1)?"tagsList":"suggestedTagsList";
	var str= "";
	tagtemp = tags.split(",");
	
	for(i=0; i < tagtemp.length; i++){
		if(tagtemp [i] != ""){
			str += '<li class="' + sty + '">'+ tagtemp [i] +'</span><span class="tagsList_controls">&nbsp;<a href="javascript:removeFromGeneratedTags(\''+ tagtemp [i] +'\');"><img src="/images/trash.gif" alt="Delete this Tag!" border="0"></a></span></li>';	
		}
	}	
	return str;
}

var editingTags = new Array();
function removeFromList(tagId){
	if (tagHolderId == 1){
		d = document.getElementById('editTagsList'); 
	}else{
		d = document.getElementById('tagsList'); 
	}
	d_nested = document.getElementById(tagId); 
	
	if (editingTags[tagId] != null){
		var indexNum = concernTags.indexOf(editingTags[tagId]+',');
		if (indexNum > 0){
			firstpart = concernTags.substring(0, indexNum);
			secondpart = concernTags.substring(indexNum + editingTags[tagId].length + 1, concernTags.length);
			concernTags = firstpart + secondpart;
		}else if (indexNum == 0){
			concernTags = concernTags.substring(indexNum + editingTags[tagId].length +1, concernTags.length);
		}
	}
	
	throwaway_node = d.removeChild(d_nested);

}

var uniqueTagCounter = 0;
function addTagToList(theListId,theTagTextboxId,validationId){
	
	if(""==$(theTagTextboxId).value)
	{
		$(validationId).innerHTML = 'Please add your tag above.  Tag can not be blank.';
		new Effect.BlindDown(validationId);
		new Effect.Highlight(validationId, {duration: 20, endcolor:'#FFFFFF'});			
	}else{
		new Effect.BlindUp(validationId);
		uniqueTagCounter++;
		newTagId = 'userTag' + uniqueTagCounter;
		editingTags[newTagId] = document.getElementById(theTagTextboxId).value;
		document.getElementById(theListId).innerHTML += '<li id="'+ newTagId +'" class="tagsList">'+ document.getElementById(theTagTextboxId).value +'</span><span class="tagsList_controls">&nbsp;<a href="javascript:removeFromList(\''+ newTagId +'\');"><img src="/images/trash.gif" alt="Delete this Tag!" border="0"></a></span></li>';
		concernTags += document.getElementById(theTagTextboxId).value + ',';
		new Effect.Highlight(theTagTextboxId, {duration: 4, endcolor:'#FFFFFF'});
		$(theTagTextboxId).value = "";
	}
}

function saveTheConcern(){
	
	var concern = $('addConcern').value;
	//concernTags = '\"' + concernTags +'\"';
	$('indicator').style.visibility = "visible";
	//alert('cctId:' + cctId + ', concern: ' + concern + ', tags: ' + concernTags);
	CCTAgent.saveConcern({cctId:cctId,concern:concern,tags:concernTags}, {
		callback:function(data){
			if (data.successful){
				//alert(concernTags);
				new Effect.BlindUp('tagConcerns');
				$('btnContinue').disabled=false;
				$('addConcern').value = "";
				$('addConcern').style.background="#FFF";
				$('addConcern').style.color="#333";
				$('addConcern').focus();
				//getTagCloud();
				//alert(concernTags);
				showMyConcerns(data.concern.id);
				concernTags = '';
				$('theTag').value = '';
				
			}
		},
		errorHandler:function(errorString, exception){ 
			alert(data.reason);
		}
});
$("indicator").style.visibility = "hidden";
}

function getTagCloud(){
	CCTAgent.getTagCloud({cctId:cctId,type:0,count:1000}, {
		callback:function(data){
			if (data.successful){
				$('allTags').innerHTML = data.html;
				
			}
		},
		errorHandler:function(errorString, exception){ 
			showTheError();
		}
});
}

function getRandomConcerns(){
//$("sidebar_content").style.display = "none";
//Effect.FadeIn('sidebar_content');
new Effect.Highlight('sidebar_content');
showConcerns(2);
}

function showConcerns(theType){
CCTAgent.getConcerns({cctId:cctId,type:theType,count:5}, {
	callback:function(data){
		if (data.successful){
			$('sidebar_content').innerHTML = data.html;
		}
	},
	errorHandler:function(errorString, exception){ 
			showTheError();
	}
});
}

function showMyConcerns(id){

CCTAgent.getConcerns({cctId:cctId,type:0,count:-1}, {
		callback:function(data){
				if (data.successful){
					$('myConcernsList').innerHTML = data.html;
					if (id != undefined){
						new Effect.Highlight('concernId' + id, {duration: 4, endcolor:'#EEF3D8', afterFinish: function(){showMyConcerns();}})
					}
					if (data.total == 0){
						document.getElementById("myConcernsList").innerHTML = '<p><small>None created yet.  Please add a concern above.  Please refer to other participant\'s concerns on the right column for examples.</small></p>';
					}
				}
		},
		errorHandler:function(errorString, exception){ 
				showTheError();
		}
	});
}

function getConcernsByTag(id){
	CCTAgent.getConcernsByTag({tagRefId:id,count:-1}, {
	callback:function(data){
			if (data.successful){ 
				new Effect.Highlight('sidebar_content', {endcolor: '#DAE1E7'});
				$('sidebar_content').innerHTML = data.html;//tagSearch.jsp
				//new Element.scrollTo('SideConcernsTop'); //location.href='#SideConcernsTop';
				shrinkTagSelector();
				$('addFilter').style.display = 'none';
				if($('sidebarSearchResults').style.display != 'none'){
					new Effect.Fade('sidebarSearchResults', {duration: 0.5, endcolor:'#EEEEEE'});	
					$('txtmanualFilter').value = $('txtmanualFilter').defaultValue;
					
				}	
			}
		},
	errorHandler:function(errorString, exception){ 
			showTheError();
	}
	});
}

function goPage(pageNum){
CCTAgent.getConcerns({cctId:cctId,type:2,count:5, page:pageNum}, {
	callback:function(data){
			if (data.successful){
				$('sidebar_content').innerHTML = data.html;
				new Effect.Highlight('sidebar_content', {duration: 0.5, endcolor: '#DAE1E7'});
			}
		},
	errorHandler:function(errorString, exception){ 
			showTheError();
	}
	});
}

function tabFocus(num){
$('myTab').tabber.tabShow(num);
}

function tagSearch(theTag){
CCTAgent.searchTags({cctId:cctId,tag:theTag},{
	callback:function(data){
		  $('tagIndicator').style.visibility = 'visible';
			if (data.successful){
				if ($('txtSearch').value == ""){
					
					$('topTags').innerHTML = "";
					$('tagSearchResults').innerHTML = '<span class="highlight">Please type in your query or <a href="javascript:getTagCloud();">clear query</a>&nbsp;to view top tags again.</span>';
				  $('tagIndicator').style.visibility = 'hidden';
				  
				}
				
				if ($('txtSearch').value != ""){
			
					$('tagSearchResults').innerHTML = '<span class="highlight">' + data.count +' tags match your query&nbsp;&nbsp;(<a href="javascript:getTagCloud();">clear query</a>)</span>';
					$('topTags').innerHTML = data.html;
					$('tagIndicator').style.visibility = 'hidden';
				}
				
				if (data.count == 0 || $('txtSearch').value == "_"){
					$('tagSearchResults').innerHTML = '<span class=\"highlight\">No tag matches found! Please try a different search or <a href="javascript:getTagCloud();">clear the query</a>&nbsp;to view top tags again.</span>';
					$('topTags').innerHTML = "";
					$('tagIndicator').style.visibility = 'hidden';
					
				}
			}
	},
	errorHandler:function(errorString, exception){ 
				showTheError();
	}		
});
}

function sidebarTagSearch(theTag,key){
		//showing and hiding clear search action
		if (theTag != ""){
			$('btnClearSearch').style.display = 'inline';
		}else{
			closeSearchResults();
			clearSearch();
		}
		
		//hack to disable backspace on input box when length < 1 - "19 tags hack"
		if (key.keyCode == 8 && theTag.length < 1){
			return false;	
		}
		
		//if the query is greater than 2 chars do the action if not keep it hidden
		
		if($('txtmanualFilter').value.length > 2){
			sidebarSearchTagsAction(theTag);
		}else{
			$('sidebarSearchResults').style.display == 'none'
		}
}

function sidebarSearchTagsAction(theTag){
		CCTAgent.searchTags({cctId:cctId,tag:theTag},{
			callback:function(data){
					if (data.successful){
						//show results if hidden
						if($('sidebarSearchResults').style.display == 'none'){
							new Effect.Appear('sidebarSearchResults', {duration: 0.5});		
						}		
						
						$('sidebarSearchResults').innerHTML = $('sidebarSearchResults').innerHTML = data.html;
						
						if (data.count == 0){
							$('sidebarSearchResults').innerHTML = '<span class="closeBox"><a href="javascript:Effect.Fade(\'sidebarSearchResults\', {duration: 0.5}); void(0);">Close</a></span><p>No tag matches found! Please try a different search.</p> ';
						}
					}
			},
			errorHandler:function(errorString, exception){ 
						showTheError();
			}		
		});	
}

function glossaryPopup(term){
lightboxDisplay(true);
os = "";
os += '<div id="closeBox" style="text-align: right;"><a href="javascript: lightboxDisplay();"><img src="/images/closelabel.gif" border="0"></a></div>'
os += '<h4>Glossary Term: '+ term +'</h4><br>';
os += '<p>Tags helps make your concerns easier to find, since all this info is searchable later. Imagine this applied to thousands of concerns!</p>';
$('lightbox').innerHTML = os;
}

function editConcernPopup(concernId){
var currentConcern = '';
lightboxDisplay(true);
CCTAgent.getConcernById(concernId, {
	callback:function(data){
		if (data.successful){
				currentConcern = data.concern.content;
				os = "";
				os += '<div id="closeBox" style="text-align: right;"><a href="javascript: lightboxDisplay();"><img src="/images/closelabel.gif" border="0"></a></div>'
				os += '<h4>Edit My Concern</h4><br>';
				os += '<form><textarea style="margin: 2%; height: 150px; width: 95%;" name="editConcern" id="editConcern" cols="50" rows="5" id="addConcern">' +currentConcern+ '</textarea></p></form>';
				os += '<input type="button" id="modifyConcern" value="Submit Edits!" onClick="editConcern('+concernId+')">';
				os += '<input type="button" value="Cancel" onClick="lightboxDisplay()">';
				$('lightbox').innerHTML = os;
				
		}
	},
	errorHandler:function(errorString, exception){ 
			showTheError();
	}

});

}

//-----------------------------------------------------------------------------------------


//-------------


function editConcern(concernId){
newConcern = $('editConcern').value;
CCTAgent.editConcern({concernId:concernId, concern:newConcern}, {
	callback:function(data){
			if (data.successful){
				lightboxDisplay();
				showMyConcerns(concernId);
			}  
	},
	errorHandler:function(errorString, exception){ 
			showTheError();
	}
});
}

function editTagsPopup(concernId){
	tagHolderId = 1;
	concernTags = "";
	
	CCTAgent.getConcernById(concernId, {
	callback:function(data) {
			if (data.successful){
				
						lightboxDisplay(true);
						os = "";
						os += '<div id="closeBox" style="text-align: right;"><a href="javascript: lightboxDisplay();"><img src="/images/closelabel.gif" border="0"></a></div>'
						os += '<h4>Edit My Concern\'s Tags</h4><br />';
						os += '<ul id="editTagsList" class="tagsList"> '+data.id+ '</ul>';
						os += '<p></p><form name="editTagList" action="" onsubmit="addTagToList(\'editTagsList\',\'theNewTag\',\'editTagValidation\'); return false;"><input type="text" id="theNewTag" class="tagTextbox" name="theNewTag" size="15"><input type="button" name="addTag" id="addTag" value="Add Tag!" onClick="addTagToList(\'editTagsList\',\'theNewTag\',\'editTagValidation\');"></p>';
						//os += '<a href="javascript:editTags('+concernId+');">TestIt</a>';
						os += '<div style="display: none;" id="editTagValidation"></div>';
						os += '<hr><input type="button" value="Submit Edits" onClick="editTags('+concernId+')">';
						os += '<input type="button" value="Cancel" onClick="lightboxDisplay()"></form>';
							$('lightbox').innerHTML = os;
							var str= "";
							for(i=0; i < data.concern.tags.length; i++){
								str += '<li id="tag'+data.concern.tags[i].tag.id+'" class="tagsList">'+ data.concern.tags[i].tag.name +'&nbsp;<a href="javascript:removeFromGeneratedTags(\'' + data.concern.tags[i].tag.name + '\');"><img src="/images/trash.gif" alt="Delete this Tag!" border="0"></a></li>';
								concernTags += data.concern.tags[i].tag.name + ',';
							}
							document.getElementById('editTagsList').innerHTML = str;
			}
	},
	errorHandler:function(errorString, exception){ 
			showTheError();
	}
});

}

function removeLastComma(str){
str = str.replace(/[\,]$/,'');
concernTags = str;
}

function editTags(concernId){
removeLastComma(concernTags);
CCTAgent.editTags({concernId:concernId, tags:concernTags}, {
	callback:function(data){
		if (data.successful){ 
			lightboxDisplay();
			showMyConcerns(concernId);
			concernTags = "";
		}
		
		if (data.successful != true){
			alert(data.reason);
			concernTags = "";
		}
	},
	errorHandler:function(errorString, exception){ 
			showTheError();
	}
});
}

function delConcern(concernId){
var destroy = confirm ("Are you sure you want to delete this concern? Note: there is no undo.")
if (destroy){
		CCTAgent.deleteConcern({concernId:concernId}, {
		callback:function(data){	
				if (data.successful){
					showMyConcerns();
				}else{
					alert(data.reason);	
				}
		},
		errorHandler:function(errorString, exception){ 
			showTheError();
		}
		});
}
}

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

function showFinished(){
location.href = '#finished';
new Effect.Highlight('suppSlate', {duration: 4, endcolor:'#EEF3D8'});
}

function showTheError(errorString, exception){
			$('container').style.display = 'none';
			$('caughtException').style.display = 'block';
			$('caughtException').innerHTML +='<p>If this problem persists, please <A HREF="mailto:webmaster@pgist.org?subject=LIT Website Problem>contact our webmaster</a></p>';
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

function lightboxDisplay(show){
	if (show){
		$('overlay').style.display = 'block';
		$('lightcontainer').style.display = 'inline';
		centerDisable();
	}else{
		$('overlay').style.display = 'none';
		$('lightcontainer').style.display = 'none';
		centerReenable();
	}
}

function clearSearch(){
	$('txtmanualFilter').value = "";	
	$('txtmanualFilter').focus();
	$('btnClearSearch').style.display = 'none';
}

function closeSearchResults(){
 new Effect.Fade('sidebarSearchResults', {duration: 0.3});
}

</script>
<style type="text/css" />

.leightpadding{

padding:0em 1em 1em 1em;


}

.leightcontainer{
 	display: none;
 	position: absolute;
 	top: 50%;
 	left: 50%;
 	margin-left: -200px;
 	margin-top: -150px;
 	width:400px;
 	height: 300px;
 	background-color: white;
 	text-align: left;
 	z-index:1002;
 	overflow:hidden;
	border: 5px solid #E1E1E1;
}

.leightbox {
 	color: #333;
 	position:absolute;
 	top:30px;
 	height:100%;
 	width:100%;
 	background-color: white;
 	text-align: left;
 	z-index:1002;
 	overflow:auto;	


}

.leightbar{
	position:absolute;
	top:0%;
	height:30px;
	width:100%;
	background-color:#0066FF;
	z-index:1002;
	overflow:hidden;
}

#overlay{
 	display:none;
 	position:absolute;
 	top:0;
 	left:0;
 	width:100%;
 	height:100%;
 	z-index:1000;
 	background-color:#333;
 	-moz-opacity: 0.8;
 	opacity:.80;
 	filter: alpha(opacity=80);
}

img{
border:0;

}

.lbclose{
float:right;

}



.lightbox[id]{ /* IE6 and below Can't See This */    position:fixed;    
}#overlay[id]{ /* IE6 and below Can't See This */    position:fixed;    
}


</style>
</head>

<body onResize = "sizeMe();">
<div id="overlay"></div>
<div id="lightcontainer" class="leightcontainer">
 	<div id="lightbox" class="leightbox">
 		<div id="lightboxpadding" class="leightpadding">
 			<h3>Editing Attributes for Glossary Term: ...</h3>
 		</div>
 	</div>
</div>


<div id="container">

<!-- Header -->

<jsp:include page="/header.jsp" />
<!-- Sub Title -->
<div id="subheader">
<h1>Step 1 Brainstorm Concerns:</h1> <h2>Discuss Concerns Summary</h2>
</div>
<div id="footprints">
<span class="smalltext">LIT Process >> Step 1 Brainstorm Concerns >> Discuss Concerns Summary</span>
</div>
<!-- End Sub Title -->


<!-- Overview SpiffyBox -->
<div class="cssbox">
<div class="cssbox_head">
<h3>Overview</h3>
</div>
<div class="cssbox_body">
<p>Before we can determine how to best improve the transportation system, we need to know what the problems are. Our first task is to brainstorm concerns about the transportation system. To help you create your concerns, the right column displays concerns from other participants. Use the buttons at the bottom of this column to view more pages of concerns, or search for particular concerns by tags. </p>
<p>[ <a href="/readmore.jsp">Read more about how this step fits into the bigger picture.</a> ]</p>

</div>
</div>
<!-- End Overview -->

</div> <!-- End cont-top -->



<div id="cont-main">

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td id="maintop"><img src="" alt="" height="1" width="1"/></td>
<td><img src="images/sidebar_top.gif" alt="sidebartop" /></td>
</tr>
<tr>
<td valign="top" id="maincontent">
<!-- Main Content starts Here-->
<div id="slate" class="borderblue">
	<h4>Add your concern</h4><br>What problems do you encounter in your daily trips to work outside the home, shopping, and errands? In what ways do you feel our current transportation system fails to meet the needs of our growing region? <p>Describe <strong>one</strong> problem with our transportation system. You can add more concerns later</p>
	<form name="brainstorm" method="post" onSubmit="addTagToList('tagsList', 'theTag','tagValidation'); return false;">
	<p><textarea onkeypress="ifEnter(this,event);" name="addConcern" cols="20" rows="2" id="addConcern"></textarea></p>
	<p class="indent">
	<input type="button" id="btnContinue" name="Continue" value="Submit Concern" onclick="prepareConcern();">
	<input type="reset" name="Reset" value="Reset" onClick="resetForm();"> 
	<span id="indicator" style="visibility:hidden;"><img src="/images/indicator.gif"></span>
	</p>
	
	<div style="display: none;" id="validation"></div>
	
	
	<div id="tagConcerns" style="display: none;">
		<div id="tags" style="background-color: #F1F7FF; border: 5Px solid #BBBBBB; margin:auto; padding: 5px; width: 70%;">
		<h4>Tag Your Concern</h4>
		<p></p>   
		<p>Please delete those that do not apply to your concern and use the textbox below to add more tags (if needed).  <span class="glossary">[ what are <a href="javascript:glossaryPopup('tag');">tags</a>? ]</span></p>
		<b>Suggested Tags for your Concern:</b>  <ul class="tagsList" id="tagsList">
		</ul>	 
		<p><input type="text" id="theTag" class="tagTextbox" name="theTag" size="15"><input type="button" name="addTag" id="addTag" value="Add Tag!" onclick="addTagToList('tagsList','theTag','tagValidation');return false;"></p>
		<div style="display: none; padding-left: 20px;" id="tagValidation"></div>
		
		<span class="title_section">Finished Tagging? <br><input type="button" name="saveConcern" value="Add Concern to List!" onclick="saveTheConcern(); void(0);"></span><input type="button" value="Cancel - back to edit my concern" onclick="javascript:resetForm();">
		</div>
		<br>
		</div>
		
		<h4>Concerns you've contributed so far</h4><br>Finished? Click 'Continue' <a href="javascript:showFinished();">below</a>.<p></p>
		<div id="myConcernsList" class="indent">
		<ol id="myConcerns">
		</ol>
	</div>
	
	</form>
</div>
	
<div id="suppSlate" class="greenBB">
	<a name="finished"></a><h4 id="h4Finished">Ready for the next step?</h4>
	<p>Click on the continue button to go on to stage 2 of step 1.  The next part in the process is to discuss your concerns with other participants.   Go back to your <a href="main.do">home page</a> or  <a href="/waiting.jsp"><img src="images/btn_gcontinue_a.gif" alt="Continue" name="continue" class="button" id="continue" onMouseOver="MM_swapImage('continue','','images/btn_gcontinue_b.gif',1)" onMouseOut="MM_swapImgRestore()"></a></p>
</div>
<!-- End Main Content -->
</td>
<td width="280" valign="top" id="sidebarmiddle"><!-- This is the Right Col -->
<div id="sidebar_container">
<div id="sidebarHeader" style="padding: 5px;">
	<h4>Other Concerns</h4>
	<p>
	 To help you create your concerns, below are examples of other participant concerns. 
	</p>
</div>
	<div id="tagSelector">
		<div id="tagform">
		<div class="textright">Show All Concerns</div>
		<h6>Filter(s):</h6> (no current filters)
		<!-- insert filter list here -->
		<p><a href="javascript: Effect.toggle('addFilter', 'blind', {duration: 0.2}); void(0);">Add a Tag Filter</a></p>
		
		<div id="addFilter" style="display: none;">
			<p class="textright"><a href="javascript: Effect.toggle('addFilter', 'blind', {duration: 0.2}); void(0);">close</a></p>
			<b>Add a Tag Filter:</b> 
			<form id="frmSidebarTagSearch" onSubmit="sidebarSearchTagsAction($('txtmanualFilter').value); return false;">
				<input name="txtmanualFilter" id="txtmanualFilter" type="text" onKeyDown="sidebarTagSearch(this.value, event)" onkeyup="sidebarTagSearch(this.value, event)" /><span id="btnClearSearch" style="display: none;"><a href="javascript:clearSearch(); closeSearchResults();">Clear</a></span>
			</form>
			<p>or <a href="javascript: expandTagSelector();">Browse All Tags</a>
				
			<div id="sidebarSearchResults" style="display: none;"><!-- tag search results are loaded here --></div>
		</div>
		
	</div>
	<div id="pullDown" class="textright"></div>
	<div id="allTags" style="display: none;"></div>
	<div class="clear"></div>
	
</div>


  <div id="sidebar_content">


<div id="caughtException"><h4>A Problem has Occured</h4><br>We are sorry but there was a problem accessing the server to complete your request.  <b>Please try refreshing the page.</b></div>

</div><!-- End sidebarcontents-->
</div><!-- sidebar container-->
 </td><!-- End Right Col -->
</tr>

</table>
<div id="sidebarbottom" style="text-align:right;"><img src="images/sidebar_bottom.gif" alt="sidebarbottom" /></div>


<!-- start feedback form -->
	<p>Found a bug?  Problem accessing a part on the page?  <a href="javascript:Effect.toggle('feedbackForm','blind');">Send us feedback.</a></p>
	<div id="feedbackForm" style="display: none;">
		<pg:feedback id="feedbackDiv" action="cctView.do" />
	</div>
<!-- end feedback form -->
</div>
<!-- End cont-main -->

</div> <!-- End container -->


<!-- Start Footer -->
<jsp:include page="/footer.jsp" />
<!-- End Footer -->
</body>
</html:html>
