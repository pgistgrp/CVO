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
<style type="text/css" media="screen">@import "styles/lit.css";</style>
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
<script type='text/javascript' src='/dwr/interface/CCTAgent.js'></script>
<!--End CCT Specific  Libraries-->
<script src="scripts/lightbox.js" type="text/javascript"></script>

<script type="text/javascript">
//START Global Variables
var cctId = ${cctForm.cct.id};
var concernTags = "";
var selectConcernTags="";
var cct = new Object;
var showOnlyMyConcerns = false;
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
//Input Element IDs
	cct.txtAddConcern = 'txtAddConcern';
	cct.btnContinueCont = 'btnContinueCont';
	cct.chbxMyConcerns = 'myconcerns';
	
//Settings
	cct.currentPage = 0;
	cct.currentFilter = '';  //Default tag filter
	cct.numTagsInNewConcern = 2;
	cct.concernsPerPage = 8;



//END Global Variables

//START CCT Clean up
	function getContextConcerns(tags, page, jump){
		if(jump){
			location.href = cct.filterAnchor;
		}
		cct.currentPage = page;
		CCTAgent.getContextConcerns({cctId: this.cctId,tags: tags, count: cct.concernsPerPage, page: page, contextAware: false, desc: true}, {
			callback:function(data){
					if (data.successful){
						$(cct.divDiscussionCont).innerHTML = data.html;
						
					}else{
						alert(data.reason);
					}
				},
			errorHandler:function(errorString, exception){ 
					alert("getContextConcerns error:" + errorString + exception);
			}
		});	
		return true
	}
		
	function goToPage(page){
		getContextConcerns(cct.currentFilter,page,true); 
		//new Effect.Highlight('discussion-cont',{startcolor: "#D6E7EF"});
	}
	function checkMyConcerns(){
		if($(cct.chbxMyConcerns).checked != true){
			$(cct.divFilteredBy).style.display = 'inline';
			getContextConcerns(cct.currentFilter, 0, false);
			
		}else{
			$(cct.divFilteredBy).style.display = 'none';
			showMyConcerns();
			
		}
	}
	
	function showMyConcerns(){
		CCTAgent.getConcerns({cctId:cctId,type:0,count:-1}, {
				callback:function(data){
						if (data.successful){
							$(cct.divDiscussionCont).innerHTML = data.html;
							if (data.total == 0){
								$(cct.divDiscussionCont).innerHTML = '<p>None created yet.  Please add a concern.</p>';
							}
						}
				},
				errorHandler:function(errorString, exception){ 
				alert("showMyConcerns: "+errorString+" "+exception);
						//showTheError();
				}
		});
	}

	
	//ADD Concern Functions
	function validateForm(){
		if($(cct.txtAddConcern).value == "" ||$(cct.txtAddConcern).value  == $(cct.txtAddConcern).defaultValue ){
				alert('Please fill in your concern above.');
				return false;
		}else{
				new Effect.BlindDown(cct.divTagNewConcern, {duration: 0.2});
				swapContinue(true);
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
			$(cct.btnContinueCont).innerHTML = '<input id="btnContinue" type="button" value="continue" onClick="prepareConcern(); swapContinue(true);" />';
			$(cct.txtAddConcern).disabled = false;
		}else{
			$(cct.btnContinueCont).innerHTML = '<input id="btnCancelNewConcern" type="button" value="cancel" onClick="cancelSubmit(); swapContinue();" /><input id="btnSubmitNewConcern" type="button" value="submit" onClick="saveConcern();" />';
			$(cct.txtAddConcern).disabled = true;
		}
	}

	function prepareConcern(){  //Find tags and potential tags to render
		var concernTags = new Array;
		var potentialTags = new Array;
		var concern = $(cct.txtAddConcern).value;
		
		if (validateForm()){	
			CCTAgent.prepareConcern({cctId:cctId,concern:concern}, function(data) {
				if (data.successful){
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
		//newConcernTagsArray = []; //clears array - this function send each tag to addtoConcernTagsArray function to push each tag into newConcernTagsArray
		for(i=0; i < newConcernTagsArray.length; i++){
			if(newConcernTagsArray[i] != ""){
				str += '<li><input type="checkbox" '+newConcernTagsArray[i].status+' onclick="checkNewConcernTag('+ i +');" />'+ newConcernTagsArray[i].tagName +'</li>';
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
	
	function addManualTag(){
		var manualTag = $('manualTag').value;
		addToConcernTagsArray(manualTag, "checked");	
		$(cct.divAddConcernTagsList).innerHTML = renderTags();
		$('manualTag').value = ""; //clear textbox
		
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
		alert("You must at least 2 tags");
		}else{
		var concern = $(cct.txtAddConcern).value;
		var newConcernSelectedTagsString = '';
		for (i=0; i<newConcernSelectedTagsArray.length; i++){
			newConcernSelectedTagsString += newConcernSelectedTagsArray[i] + ',';	
		}
		//alert(newConcernSelectedTagsString);
		//alert('cctId:' + cctId + ', concern: ' + concern + ', tags: ' + newConcernSelectedTagsString);
		//alert(newConcernSelectedTagsString);

		CCTAgent.saveConcern({cctId:cctId,concern:concern,tags:newConcernSelectedTagsString}, {
			callback:function(data){
				if (data.successful){
					getContextConcerns(cct.currentFilter, 0, true); 	
					setVote(data.concern.id, "true")
					swapContinue(false);
					$(cct.txtAddConcern).value = '';
					Effect.BlindUp(cct.divTagNewConcern);

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
            				 new Effect.Fade('concernVote'+id, {afterFinish: function(){getContextConcerns(cct.currentFilter,cct.currentPage, false); new Effect.Appear('concernVote'+id);}});
            			}else{ //newly created concern
            				getContextConcerns(cct.currentFilter, cct.currentPage, false); 	
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
							new Effect.Puff('concern'+concernId, {afterFinish:function(){getContextConcerns(cct.currentFilter,cct.currentPage,false);}});
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
		getContextConcerns(tagRefId, 0, true);
		cct.currentFilter = tagRefId;
		if (tagRefId != ''){
				CCTAgent.getTagByTagRefId(cct.currentFilter, {
				callback:function(data){
				if (data.successful){
		          			var tagName = data.tag.name;
							$(cct.divFilteredBy).innerHTML = '<h3 style="color: red">Filtered By: ' + tagName + ' <a href="javascript: changeCurrentFilter(\'\');"><img src="images/close.gif" alt="clear filter" /></a>';
						}else{
							alert(data.reason);
						}
				},
				errorHandler:function(errorString, exception){ 
						alert("get tagbytagref error:" + errorString + exception);
				}
				});
		}else{
			cct.currentFilter = '';	
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
			CCTAgent.searchTags({cctId:this.cctId,tag:query},{
				callback:function(data){
						if (data.successful){
							if($(cct.divSearchResults).style.display == 'none'){
								new Effect.Appear(cct.divSearchResults, {duration: 0.5});		
							}		
							
							$(cct.divSearchResults).innerHTML = $(cct.divSearchResults).innerHTML = data.html;
							if (data.count == 0){
								$(cct.divSearchResults).innerHTML = '<span class="closeBox"><a href="javascript:Effect.Fade('+cct.divSearchResults+', {duration: 0.5}); void(0);">Close</a></span><p>No tag matches found! Please try a different search.</p> ';
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
				/*os = "";
    os += '<div id="closeBox" style="text-align: right;"><a href="javascript: lightboxDisplay();"><img src="/images/closelabel.gif" border="0"></a></div>'
    os += '<h4>Edit My Concern</h4><br>';
    os += '<form id="editmyconcern"><textarea style="margin: 2%; height: 150px; width: 95%;" name="editConcern" id="editConcern" cols="50" rows="5" id="addConcern">' +currentConcern+ '</textarea></p></form>';
    os += '<input type="button" id="modifyConcern" value="Submit Edits!" onClick="editConcern('+concernId+')">';
    os += '<input type="button" value="Cancel" onClick="lightboxDisplay()">';
    $('lightbox').innerHTML = os;*/
				os = "";
				
				
				
				os += '<div id="closeBox" style="text-align: right;"><a href="javascript: lightboxDisplay();"><img src="/images/closelabel.gif" border="0"></a></div>'
				os += '<h4>Edit My Concern</h4><br>';

				os += '<div style="position:relative; margin:2%;"><textarea style="position:fixed; height: 150px; width: 390px;" name="editConcern" id="editConcern" cols="50" rows="5">' +currentConcern+ '</textarea>';
				
				if(navigator.appName=="Microsoft Internet Explorer"){
				os += '<div style="position:relative;"><input type="button" id="modifyConcern" value="Submit Edits!" onclick="javascript:editConcern('+concernId+');"/>';

				os += '<input type="button" value="Cancel" onClick="lightboxDisplay()"></div></div>';
				}else{
				os += '<div style="position:relative; top:150px;"><input type="button" id="modifyConcern" value="Submit Edits!" onclick="javascript:editConcern('+concernId+');"/>';

				os += '<input type="button" value="Cancel" onClick="lightboxDisplay()"></div></div>';
				}
				$('lightbox').innerHTML = os;
				
				
		}
	},
	errorHandler:function(errorString, exception){ 
			alert("editConcernPopup: "+errorString+" "+exception);
			//showTheError();
	}

});

}


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
			alert("editConcern: "+errorString+" "+exception);
			//showTheError();
		}
	});
	}
	
var editTagList=new Array();
function editTagsPopup(concernId){
	tagHolderId = 1;
	concernTags = "";
	
	CCTAgent.getConcernById(concernId, {
	callback:function(data) {
			if (data.successful){
			//<form name="editTagList" action="" onsubmit="addTagToList(\'editTagsList\',\'theNewTag\',\'editTagValidation\'); return false;">
			//</form>
				
						lightboxDisplay(true);
						os = "";
						os += '<div id="closeBox" style="text-align: right;"><a href="javascript: lightboxDisplay();"><img src="/images/closelabel.gif" border="0"></a></div>'
						os += '<h4>Edit My Concern\'s Tags</h4><br />';
						os += '<ul id="editTagsList" class="tagsList"> '+data.id+ '</ul>';
						
						if(navigator.appName=="Netscape"){
						os += '<p><form method="post" onSubmit="addTagToList(\'editTagsList\',\'theNewTag\',\'editTagValidation\');return false;"><span style="margin:2%;"><input type="text" style="position:fixed;" id="theNewTag" class="tagTextbox" name="theNewTag" size="15"><input type="button" name="addTag" id="addTag" value="Add Tag!" style="position:relative;left:120px; bottom:5px;" onClick="addTagToList(\'editTagsList\',\'theNewTag\',\'editTagValidation\');"></span></form></p>';
						//os += '<a href="javascript:editTags('+concernId+');">TestIt</a>';
						}else{
						os += '<p><form method="post" onSubmit="addTagToList(\'editTagsList\',\'theNewTag\',\'editTagValidation\'); return false;"><span style="margin:2%;"><input type="text" style="" id="theNewTag" class="tagTextbox" name="theNewTag" size="15"><input type="button" name="addTag" id="addTag" value="Add Tag!" style="" onClick="addTagToList(\'editTagsList\',\'theNewTag\',\'editTagValidation\');"></span></form></p>';
						}
						os += '<div style="display: none;" id="editTagValidation"></div>';
						os += '<div style="position:relative; top:20px;"><hr><input type="button" id="subeditTags" value="Submit Edits" onClick="editTags('+concernId+')">';
						os += '<input type="button" value="Cancel" onClick="lightboxDisplay()"></div>';//</form>
							$('lightbox').innerHTML = os;
							var str= "";
							for(i=0; i < data.concern.tags.length; i++){
							
								str += '<li id="tag'+data.concern.tags[i].tag.id+'" class="tagsList">'+ data.concern.tags[i].tag.name +'&nbsp;<a href=\'javascript:removeFromGeneratedTags("' + data.concern.tags[i].tag.name + '");\'><img class="trashcan" src="/images/trash.gif" alt="Delete this Tag!" border="0"></a></li>';//<img src="/images/trash.gif" alt="Delete this Tag!" border="0"></a></li>';
								concernTags += data.concern.tags[i].tag.name + ',';
							}
							document.getElementById('editTagsList').innerHTML = str;
			}
	},
	errorHandler:function(errorString, exception){ 
			alert("editTagsPopup: "+errorString+" "+exception);
			//showTheError();
	}
});

}

function removeLastComma(str){
str = str.replace(/[\,]$/,'');
concernTags = str;
}

function editTags(concernId){
if(concernTags.split(',').length-1<cct.numTagsInNewConcern){
alert("You must select 3 or more tags");
}else{
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
			alert("editTags: "+errorString+" "+exception);
			//showTheError();
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
	function displayIndicator(show){
		if (show){
			$('loading-indicator').style.display = "inline";	
		}else{
			$('loading-indicator').style.display = "none";	
		}
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


</script>
<style type="text/css" />

.leightpadding{

padding:0em 1em 1em 1em;


}
#loading-indicator{
	
	background-color: red;
	color: white;
	position:absolute;
	top: 0;
	left:0;
	padding: 3px;
	z-index: 500;
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



.lbclose{
float:right;

}



.lightbox[id]{ /* IE6 and below Can't See This */    position:fixed;    
}#overlay[id]{ /* IE6 and below Can't See This */    position:fixed;    
}


</style>


<html:html>
<style type="text/css">
#saving-indicator{
	display: none;
	background-color: red;
	color: white;
	position:absolute;
	top: 0;
	left:0;
	padding: 3px;
	z-index: 500;
}

#loading-indicator{
	
	background-color: red;
	color: white;
	position:absolute;
	top: 0;
	left:0;
	padding: 3px;
	z-index: 500;
}

div > div#saving-indicator{
position:fixed;
}

div > div#loading-indicator{
position:fixed;
}
</style>
<!--[if gte IE 5.5]><![if lt IE 7]>
		<style type="text/css">
#loading-indicator {
left: expression( ( 0 + ( ignoreMe2 = document.documentElement.scrollLeft ? document.documentElement.scrollLeft : document.body.scrollLeft ) ) + 'px' );
top: expression( ( 0 + ( ignoreMe = document.documentElement.scrollTop ? document.documentElement.scrollTop : document.body.scrollTop ) ) + 'px' );
}

#saving-indicator {
left: expression( ( 0 + ( fixme = document.documentElement.scrollLeft ? document.documentElement.scrollLeft : document.body.scrollLeft ) ) + 'px' );
top: expression( ( 0 + ( fixme = document.documentElement.scrollTop ? document.documentElement.scrollTop : document.body.scrollTop ) ) + 'px' );

#viewSidebarConcern {
left: expression( (146 + (fixside=document.documentElement.scrollLeft ? document.documentElement.scrollLeft : document.body.scrollLeft ) ) + 'px' );
top: expression( (20 + (fixside=document.documentElement.scrollTop ? document.documentElement.scrollTop : document.body.scrollTop ) ) + 'px' );
}
		</style>
		<![endif]><![endif]-->
</head>

<body onResize = "sizeMe();">

<!-- Begin the header - loaded from a separate file -->
	<div id="header">
		<p>Load separate file here</p>
	</div>
<!-- End header -->

    <!-- Begin header menu - The wide ribbon underneath the logo -->
    <div id="headerMenu">
        <div id="headerContainer">
            <div id="headerTitle" class="floatLeft">
                <h3 class="headerColor">Step 1: Brainstorm Concerns</h3>
            </div>
            <div class="headerButton box4 floatLeft currentBox"><a href=http://mail.yahoo.com/config/login?/"#">1a: Brainstorm Concerns</a></div>
            <div class="headerButtonCurrent floatLeft"><a href=http://mail.yahoo.com/config/login?/"#">1b: Discuss Summaries</A></div>
            <div id="headerNext" class="box5 floatRight"><a href=http://mail.yahoo.com/config/login?/"#">Next Step</A></div>
        </div>
    </div>
    <!-- End header menu --> 
<!-- #container is the container that wraps around all the main page content -->
	<div id="container">
<!-- begin "overview and instructions" area -->
		<div id="overview" class="box2">
			<h3>Overview and Instructions</h3>
		<p>Before we can determine how to best improve the transportation system, we need to know what the problems are. Our first task is to brainstorm concerns about the transportation system. To help you create your concerns, the right column displays concerns from other participants. Use the buttons at the bottom of this column to view more pages of concerns, or search for particular concerns by tags.</p>
		</div>
<!-- end overview -->
		<a name="filterJump"></a>
		<div id="discussion" style="background-image: url('images/addConcern.gif'); background-repeat: no-repeat; background-position: 730px 0;">
				<div id="discussionHeader">
					<div class="sectionTitle">
						<h3>All Participants' Concerns</h3>
						<div id="filteredBy"></div>
						<input type="checkbox" id="myconcerns" onClick="checkMyConcerns();"/>Show only my concerns
					</div>
					<div id="sortingMenu">
						sort discussion by:
						<select>
							<option>Option</option>
							<option>Option Option Option Option Option</option>
							<option>Option</option>
							<option>Option</option>
							<option>Option</option>
						</select>
						<br />
						filter discussion by:
						<form action="javascript: customFilterAction($('txtCustomFilter').value);">
							<input type="text" id="txtCustomFilter" onKeyDown="customFilter(this.value, event);" /> or <a href="#">Browse All Tags</a>
						</form>
						<div id="searchResults" style="display: none;"></div>
					</div>
				</div>

				<div id="discussion-cont" class="floatLeft"><!-- left col -->

				</div><!-- end left col -->
				
				<div id="colRight" class="floatLeft box6 colRight"><!-- right col -->
					<h3>Add your own Concern</h3>
					<fieldset>
						<textarea id="txtAddConcern" style="width:100%; border: 1px solid #FFC978; height: 100px;" onClick="if(this.value==this.defaultValue){this.value = ''}">Type your concern here.</textarea>
					</fieldset>
					<div id="tagNewConcern" class="box6 padding5" style="display:none;">
						<h3>Tag your concern</h3>
						<p>Suggested tags:</p>
						<ul id="addConcernTagsList" class="tagsList">
							<!-- render suggested tags here -->
						</ul>
						<form action="javascript: addManualTag();">
							<input id="manualTag" type="text" value="Add your own tag!" onClick="if(this.value==this.defaultValue){this.value = ''}"/><input type="button" value="Add" onClick="addManualTag();" />
						</form>
						<p><small>You must have at least 2 or more tags to continue.</small></p>
					</div>
					<div id="btnContinueCont"><input id="btnContinue" type="button" value="continue" onClick="prepareConcern();" /></div>
				</div><!-- end right col -->
				<div class="clearBoth"></div>
			</div><!-- end discussion -->
		</div> <!-- end container -->
    <!-- Begin header menu - The wide ribbon underneath the logo -->
    <div id="headerMenu">
        <div id="headerContainer">
            <div id="headerTitle" class="floatLeft">
                <h3 class="headerColor">Step 1: Brainstorm Concerns</h3>
            </div>
            <div class="headerButton box4 floatLeft currentBox"><a href=http://mail.yahoo.com/config/login?/"#">1a: Brainstorm Concerns</a></div>
            <div class="headerButtonCurrent floatLeft"><a href=http://mail.yahoo.com/config/login?/"#">1b: Discuss Summaries</A></div>
            <div id="headerNext" class="box5 floatRight"><a href=http://mail.yahoo.com/config/login?/"#">Next Step</A></div>
        </div>
    </div>
    <!-- End header menu --> 
		<div id="footer">
			Load footer file here
		</div><!-- end footer -->
<!-- start feedback form -->
<pg:feedback id="feedbackDiv" action="cctView.do" />
<script type="text/javascript">
		getContextConcerns();
		
</script>


</body>
</html:html>
