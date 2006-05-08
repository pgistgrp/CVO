<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>PGIST Portal - Let's Improve Transportation</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<style type="text/css" media="screen">@import "/styles/tabs.css";</style>
<style type="text/css" media="screen">@import "/styles/pgist.css";</style>

<script type="text/javascript">

/* Optional: Temporarily hide the "tabber" class so it does not "flash"
   on the page as plain HTML. After tabber runs, the class is changed
   to "tabberlive" and it will appear. */

document.write('<style type="text/css">.tabber{display:none;}<\/style>');

/*==================================================
  Set the tabber options (must do this before including tabber.js)
  ==================================================*/
var tabberOptions = {

  'cookie':"tabber", /* Name to use for the cookie */

  'onLoad': function(argsObj)
  {
    var t = argsObj.tabber;
    var i;

    /* Optional: Add the id of the tabber to the cookie name to allow
       for multiple tabber interfaces on the site.  If you have
       multiple tabber interfaces (even on different pages) I suggest
       setting a unique id on each one, to avoid having the cookie set
       the wrong tab.
    */
    if (t.id) {
      t.cookie = t.id + t.cookie;
    }

    /* If a cookie was previously set, restore the active tab */
    i = parseInt(getCookie(t.cookie));
    if (isNaN(i)) { return; }
    t.tabShow(i);
    
  },

  'onClick':function(argsObj)
  {
    var c = argsObj.tabber.cookie;
    var i = argsObj.index;
    
    setCookie(c, i);
  }
};

/*==================================================
  Cookie functions
  ==================================================*/
function setCookie(name, value, expires, path, domain, secure) {
    document.cookie= name + "=" + escape(value) +
        ((expires) ? "; expires=" + expires.toGMTString() : "") +
        ((path) ? "; path=" + path : "") +
        ((domain) ? "; domain=" + domain : "") +
        ((secure) ? "; secure" : "");
}

function getCookie(name) {
    var dc = document.cookie;
    var prefix = name + "=";
    var begin = dc.indexOf("; " + prefix);
    if (begin == -1) {
        begin = dc.indexOf(prefix);
        if (begin != 0) return null;
    } else {
        begin += 2;
    }
    var end = document.cookie.indexOf(";", begin);
    if (end == -1) {
        end = dc.length;
    }
    return unescape(dc.substring(begin + prefix.length, end));
}
function deleteCookie(name, path, domain) {
    if (getCookie(name)) {
        document.cookie = name + "=" +
            ((path) ? "; path=" + path : "") +
            ((domain) ? "; domain=" + domain : "") +
            "; expires=Thu, 01-Jan-70 00:00:01 GMT";
    }
}

</script>

<script src="/scripts/tabs.js" type="text/javascript"></script>
<script src="/scripts/prototype.js" type="text/javascript"></script>
<script src="/scripts/effects.js" type="text/javascript"></script>
<script src="/scripts/combo.js" type="text/javascript"></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type='text/javascript' src='/dwr/interface/CCTAgent.js'></script>

<script type="text/javascript">
  var cctId = ${cctForm.cct.id};
  var concernTags = "";
  window.onload(doOnLoad());
  

	function doOnLoad(){
		showConcerns(2);
		showMyConcerns();
		showTagCloud();
	}
	function validateForm()
	{
		if(""==document.forms.brainstorm.addConcern.value)
		{
			document.getElementById('validation').innerHTML = 'Please fill in your concern above.';
			Effect.OpenUp('validation');
			Effect.CloseDown('tagConcerns');
			Effect.Yellow('validation', {duration: 4, endcolor:'#EEEEEE'});
			Effect.Yellow('theTag', {duration: 10, endcolor:'#EEEEEE'});
			return false;
			
		}else{
			Effect.CloseDown('validation');
			Effect.OpenUp('tagConcerns');
			Effect.Yellow('tags', {duration: 4, endcolor:'#FFFFFF'});
			//$('theTag').value = "add tag";
			//$('theTag').focus();
			return true;
		}
	}
	
	function resetForm()
	{
		$('btnContinue').disabled=false;
		Effect.CloseDown('tagConcerns');
		Effect.CloseDown('validation');
		$('addConcern').style.background="#FFF";
		$('addConcern').style.color="#333";
	}
	
	function prepareConcern(){
		concernTags = "";
		tagHolderId = 0;
		if (validateForm()){
			$('btnContinue').disabled=true;
			$('addConcern').style.background="#EEE";
			$('addConcern').style.color="#CCC";
			var concern = $('addConcern').value;
			document.getElementById("indicator").style.visibility = "visible";
			CCTAgent.prepareConcern({cctId:cctId,concern:concern}, function(data) {
				if (data.successful){
					for(i=0; i < data.tags.length; i++){
						concernTags += data.tags[i] + ',';
					}
					document.getElementById('tagsList').innerHTML = renderTags( concernTags, 1);  // + renderTags( data.suggested, 0);
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
			Effect.OpenUp(validationId);
			Effect.Yellow(validationId, {duration: 20, endcolor:'#FFFFFF'});			
		}else{
			Effect.CloseDown(validationId);
			uniqueTagCounter++;
			newTagId = 'userTag' + uniqueTagCounter;
			editingTags[newTagId] = document.getElementById(theTagTextboxId).value;
			document.getElementById(theListId).innerHTML += '<li id="'+ newTagId +'" class="tagsList">'+ document.getElementById(theTagTextboxId).value +'</span><span class="tagsList_controls">&nbsp;<a href="javascript:removeFromList(\''+ newTagId +'\');"><img src="/images/trash.gif" alt="Delete this Tag!" border="0"></a></span></li>';
			concernTags += document.getElementById(theTagTextboxId).value + ',';
			Effect.Yellow(theTagTextboxId, {duration: 4, endcolor:'#FFFFFF'});
			$(theTagTextboxId).value = "";
		}
	}
	
	function saveTheConcern(){
		var concern = $('addConcern').value;
		$("indicator").style.visibility = "visible";
		//$('explaination').innerHTML = "";
		CCTAgent.saveConcern({cctId:cctId,concern:concern,tags:concernTags}, function(data){
			if (data.successful){
				showMyConcerns(data.concern.id);
				Effect.CloseDown('tagConcerns');
				//Reset add concerns textbox and Clear comma separated concerns tag list
				$('btnContinue').disabled=false;
				$('addConcern').value = "";
				$('addConcern').style.background="#FFF";
				$('addConcern').style.color="#333";
				$('addConcern').focus();
				showTagCloud();
			}
		$("indicator").style.visibility = "hidden";
	});
}

function showTagCloud(){
		CCTAgent.getTagCloud({cctId:cctId,type:0,count:25}, function(data){
			if (data.successful){
				$('sidebar_tags').innerHTML = data.html;
			}
			//$("indicator").style.visibility = "hidden";
	});
}

function getRandomConcerns(){
	//$("sidebar_concerns").style.display = "none";
	//Effect.FadeIn('sidebar_concerns');
	Effect.Yellow('sidebar_concerns');
	showConcerns(2);
}

function showConcerns(theType){
	CCTAgent.getConcerns({cctId:cctId,type:theType,count:7}, function(data){
		if (data.successful){
			$('sidebar_concerns').innerHTML = data.html;
		}
	});
}

function showMyConcerns(id){
	CCTAgent.getConcerns({cctId:cctId,type:0,count:-1}, function(data){
/*	$('sidebar_concernsContainer')
					<span class="title_section">Other Participant's Concerns</span>
				<p>To help you create your concerns, Below are examples of other participant concerns in random order.</p>
				<p><a href="JavaScript:getRandomConcerns();">Get more random concerns!</a></p>
				*/
			if (data.successful){
				$('myConcernsList').innerHTML = data.html;
				if (id != undefined){
				Effect.Yellow('concernId' + id, {duration: 4, endcolor:'#EEEEEE'})
				}
				if (data.total == 0){
					document.getElementById("myConcernsList").innerHTML = '<p class="explaination">None created yet.  Please add a concern above.  Please refer to other participant\'s concerns on the right column for examples.</p>';
				}
			}
		});
	}

function getConcernsByTag(id){
		CCTAgent.getConcernsByTag({tagRefId:id,count:-1}, function(data){
			if (data.successful){
				//$("sidebar_concerns").style.display = "none";
				Effect.Yellow('sidebar_concerns');
				$('sidebar_concerns').innerHTML = data.html;
				$('myTab').tabber.tabShow(1);
			}
		});
}

function goPage(pageNum){
CCTAgent.getConcerns({cctId:cctId,type:2,count:7, page:pageNum}, function(data){
		if (data.successful){
			$('sidebar_concerns').innerHTML = data.html;
			Effect.Yellow('sidebar_concerns');
		}
	});
}

function tabFocus(num){
	$('myTab').tabber.tabShow(num);
}

function tagSearch(theTag){
CCTAgent.searchTags({cctId:cctId,tag:theTag}, function(data){
	  $('tagIndicator').style.visibility = 'visible';
		if (data.successful){
			//alert(data.count);
			//if (data.count == 1){
				//$('topTags').innerHTML = data.tag.tag.name;
				//getConcernsByTag(data.tag.id);
				//$('myTab').tabber.tabShow(1);
			//}
			if ($('txtSearch').value == ""){
				$('searchTag_title').innerHTML = '<span class="title_section2">Tag Query:</span>'; 
				$('topTags').innerHTML = "";
				$('tagSearchResults').innerHTML = '<span class="highlight">Please type in your query or <a href="javascript:showTagCloud();">clear query</a>&nbsp;to view top tags again.</span>';
			  $('tagIndicator').style.visibility = 'hidden';
			  
			}
			
			if ($('txtSearch').value != ""){
				$('searchTag_title').innerHTML = '<span class="title_section2">Tag Query:</span>'; 
				$('tagSearchResults').innerHTML = '<span class="highlight">' + data.count +' tags match your query&nbsp;&nbsp;(<a href="javascript:showTagCloud();">clear query</a>)</span>';
				$('topTags').innerHTML = data.html;
				$('tagIndicator').style.visibility = 'hidden';
			}
			
			if (data.count == 0){
				$('tagSearchResults').innerHTML = '<span class=\"highlight\">No tag matches found! Please try a different search or <a href="javascript:showTagCloud();">clear the query</a>&nbsp;to view top tags again.</span>';
				$('topTags').innerHTML = "";
				$('tagIndicator').style.visibility = 'hidden';
			}
			
		}
	});
}


function lightboxDisplay(action){
	$('overlay').style.display = action;
	$('lightbox').style.display = action;
}

function glossaryPopup(term){
	lightboxDisplay('inline');
	os = "";
	os += '<span class="closeBox"><a href="javascript: lightboxDisplay(\'none\');"><img src="/images/close.gif" border="0"></a></span>'
	os += '<br><h2>Glossary Term: '+ term +'</h2>';
	os += '<p>Tags helps make your concerns easier to find, since all this info is searchable later. Imagine this applied to thousands of concerns!</p>';
	$('lightbox').innerHTML = os;
}
function editConcernPopup(concernId){
  var currentConcern = '';
	lightboxDisplay('inline');
	CCTAgent.getConcernById(concernId, function(data){
		if (data.successful){
				currentConcern = data.concern.content;
				os = "";
				os += '<span class="closeBox"><a href="javascript: lightboxDisplay(\'none\');"><img src="/images/close.gif" border="0"></a></span>'
				os += '<h2>Edit My Concern</h2><br>';
				os += '<textarea style="height: 150px; width: 100%;" name="editConcern" id="editConcern" cols="50" rows="5" id="addConcern">' +currentConcern+ '</textarea></p>';
				os += '<input type="button" id="modifyConcern" value="Submit Edits!" onClick="editConcern('+concernId+')">';
				os += '<input type="button" value="Cancel" onClick="lightboxDisplay(\'none\')">';
				$('lightbox').innerHTML = os;
		}
	});

}
	
function editConcern(concernId){
	newConcern = $('editConcern').value;
	CCTAgent.editConcern({concernId:concernId, concern:newConcern}, function(data){
		if (data.successful){
			lightboxDisplay('none');
			showMyConcerns(concernId);
		}
	});
}

function editTagsPopup(concernId){
		tagHolderId = 1;
		concernTags = "";
		
		CCTAgent.getConcernById(concernId, function (data) {
		if (data.successful){
			
		lightboxDisplay('inline');
		os = "";
		os += '<span class="closeBox"><a href="javascript: lightboxDisplay(\'none\');"><img src="/images/close.gif" border="0"></a></span>'
		os += '<h2>Edit My Concern\'s Tags</h2><p></p>';
		os += '<ul id="editTagsList" class="tagsList"> '+data.id+ '</ul>';
		os += '<p></p><form name="editTagList" onsubmit="addTagToList(\'editTagsList\',\'theNewTag\',\'editTagValidation\'); return false;"><input type="text" id="theNewTag" class="tagTextbox" name="theNewTag" size="15"><input type="submit" name="addTag" id="addTag" value="Add Tag!"></p>';
		//os += '<a href="javascript:editTags('+concernId+');">TestIt</a>';
		os += '<div style="display: none;" id="editTagValidation"></div>';
		os += '<hr><input type="button" value="Submit Edits" onClick="editTags('+concernId+')">';
		os += '<input type="button" value="Cancel" onClick="lightboxDisplay(\'none\')"></form>';
			$('lightbox').innerHTML = os;
			var str= "";
			for(i=0; i < data.concern.tags.length; i++){
				str += '<li id="tag'+data.concern.tags[i].tag.id+'" class="tagsList">'+ data.concern.tags[i].tag.name +'&nbsp;<a href="javascript:removeFromGeneratedTags(\'' + data.concern.tags[i].tag.name + '\');"><img src="/images/trash.gif" alt="Delete this Tag!" border="0"></a></li>';
				concernTags += data.concern.tags[i].tag.name + ',';
			}
			document.getElementById('editTagsList').innerHTML = str;

		}
		
	});

}

function removeLastComma(str){
	str = str.replace(/[\,]$/,'');
	concernTags = str;
}

function editTags(concernId){
	removeLastComma(concernTags);
	CCTAgent.editTags({concernId:concernId, tags:concernTags}, function(data){
		if (data.successful){ 
			lightboxDisplay('none');
			showMyConcerns(concernId);
			concernTags = "";
		}
		
		if (data.successful != true){
			alert(data.reason);
			concernTags = "";
		}
	});
}

function delConcern(concernId){
	var destroy = confirm ("Are you sure you want to delete this concern? Note: there is no undo.")
	if (destroy){
			CCTAgent.deleteConcern({concernId:concernId}, function(data){
				if (data.successful){
					showMyConcerns();
				}
			});
	}
}

</script>
</Head>
<body>

<div id="decorBar"></div>
<div id="container">
<div id="header"><img src="/images/logo.jpg"></div>
<div id="searchNavContiner">
	<div id="authentication">Welcome, ${baseuser.firstname} [&nbsp;<a href="/logout.do">logout</a>&nbsp;]</div>
	<div id="mainSearch">
			<form name="mainSearch" method="post" onSubmit="search();">
				<input type="text" ID="tbx1" class="searchBox" style="padding-left: 5px; padding-right:20px; background: url('/images/search.gif') no-repeat right;" value="Search" onfocus="this.value = ( this.value == this.defaultValue ) ? '' : this.value;return true;"/>
			</form>	
	</div>
	<div id="mainNav">Home&nbsp;&nbsp;&nbsp;&nbsp;<span class="active">Current Task</span>&nbsp;&nbsp;&nbsp;&nbsp;Discussion&nbsp;&nbsp;&nbsp;&nbsp;Maps&nbsp;&nbsp;&nbsp;&nbsp;Library&nbsp;&nbsp;</div>
</div>
<!-- LIGHTBOX -->
<div id="overlay"></div>
<div id="lightbox"></div>
<!-- LIGHTBOX -->
<div id="pageTitle">
<span class="title_page">Current Task: </span><h1>Brainstorm Concerns</h1>
	<div id="bread">
	<ul>
		<li class="first"><a href="null">LIT Process</a>
			<ul>
				<li>&#187; <a href="null">Brainstorm Concerns</a></li>
			</ul>
		</li>
	</ul>
	</div>
</div>	

  <div id="overview">
	  	<h3>Overview and Instructions</h3> 
	  	<p class="indent"><strong>Overview: </strong>${cctForm.cct.purpose}</p>
	  	<p class="indent"><strong>Instructions: </strong>${cctForm.cct.instruction}</p>
 </div>

  
 <div id="slate">
  		<h2>Add your concern</h2><br>What is one of your concerns about the Central Puget Sound Transportation System?  View examples of concerns in the right column (<a href="javascript:tabFocus(1);">concerns tab</a>).
		    <form name="brainstorm" method="post" onSubmit="addTagToList('tagsList', 'theTag', 'tagValidation'); return false;">
			      <p><textarea class="textareaAddConcern" name="addConcern" cols="50" rows="5" id="addConcern"></textarea></p>
			      <p class="indent">
				      <input type="button" id="btnContinue" name="Continue" value="Continue" onclick="prepareConcern();">
				      <input type="reset" name="Reset" value="Reset" onClick="resetForm();"> 
				      <span id="indicator" style="visibility:hidden;"><img src="/images/indicator.gif"></span>
			      </p>
			      <div style="display: none;" id="validation"></div>
				    <div id="tagConcerns" style="display: none;">
						    <div id="tags" style="background-color: #FFF; border: 5Px solid #BBBBBB; margin:auto; padding: 5px; width: 70%;">
						    	<h3>Tag Your Concern</h3>
						    	<p>The tags below are suggested tags for your concern.  Please delete those that do not apply to your concern and use the textbox below to add more tags (if needed). <br><span class="glossary">[ what are <a href="javascript:glossaryPopup('tag');">tags</a>? ]</span></p>
									<ul class="tagsList" id="tagsList">
									</ul>	    
									
									<p><input type="text" id="theTag" class="tagTextbox" name="theTag" size="15"><input type="button" name="addTag" id="addTag" value="Add Tag!" onclick="addTagToList('tagsList','theTag','tagValidation');"></p>
									<div style="display: none; padding-left: 20px;" id="tagValidation"></div>
									<hr>
									<span class="title_section">Finished Tagging? <br><input type="button" name="saveConcern" value="Add Concern to List!" onclick="saveTheConcern();"></span><input type="button" value="Cancel - back to edit my concern" onclick="javascript:resetForm();">
						    </div>
						    <br>
				    </div>
				
				<h2>List of my created concerns</h2><br>Finished? Please submit concerns <a href="#finished">below</a>.<p></p>
			      <div id="myConcernsList" class="indent">
			 
				      <ol id="myConcerns">
					  	</ol>
				  	</div>
				  	
						<h2>Finished brainstorming concerns?</h2>
						<div id="finished_container">
							<div id="finished_p">When you are staisfied with your concerns list above, please use the button on the right to continue to the next step!</div>
							<div id="finished_img"><a name="finished" href="nextStep"><img src="/images/submission_brainstorm.gif" border="0" align="right"></a></div>
						</div>
		    </form>

  </div>
<!--START SIDEBAR -->
<div id="bar">
	<div class="tabber" id="myTab">
		  <div id="sidebar_tags" class="tabbertab">
	    	<H2>Tags</H2>
	    </div>

	    <div id="sidebar_concernsContainer" class="tabbertab">
	    	<h2>Concerns</h2>
				<div id="sidebar_concerns">
				</div>
	    </div>
	
	</div>
</div>
</div>
<!--END SIDEBAR -->
<div id="footerContainer">
	<div id="footer"><a href="http://www.pgist.org" target="_blank"><img src="/images/footer_pgist.jpg" alt="Powered by the PGIST Portal" border="0" align="right"></a></div>
	<div id="nsf">This research is funded by National Science Foundation, Division of Experimental and Integrative Activities, Information Technology Research (ITR) Program, Project Number EIA 0325916, funds managed within the Digital Government Program.</div>
</div>
</body>
</html>
