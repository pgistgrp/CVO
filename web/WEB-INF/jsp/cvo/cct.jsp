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
			Effect.Yellow('tags', {duration: 4, endcolor:'#DDDDDD'});
			$('theTag').value = "add tag";
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
		if (validateForm()){
			$('btnContinue').disabled=true;
			$('addConcern').style.background="#EEE";
			$('addConcern').style.color="#CCC";
			var concern = $('addConcern').value;
			document.getElementById("indicator").style.visibility = "visible";
			CCTAgent.prepareConcern({cctId:cctId,concern:concern}, function(data) {
				if (data.successful){
					var str= "";
					for(i=0; i < data.tags.length; i++){
						str += '<li class="tagsList"><span class="tagsList_tag">'+ data.tags[i] +'</span><span class="tagsList_controls">&nbsp;<a href="null"><img src="/images/trash.gif" alt="Delete this Tag!" border="0"></a></span>&nbsp;|&nbsp;</li>';
						concernTags += data.tags[i] + ',';
					}
					document.getElementById('tagsList').innerHTML = str;
				}
				document.getElementById("indicator").style.visibility = "hidden";
			} );
		}
	}
	
	function addTagToList(){
		document.getElementById('tagsList').innerHTML += '<li class="tagsList"><span class="tagsList_tag">'+ document.getElementById("theTag").value +'</span><span class="tagsList_controls">&nbsp;<a href="null"><img src="/images/trash.gif" alt="Delete this Tag!" border="0"></a></span>&nbsp;|&nbsp;</li>';
		concernTags += document.getElementById("theTag").value + ',';
		Effect.Yellow('theTag', {duration: 4, endcolor:'#EEEEEE'});
		$('theTag').value = "";
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
				concernTags = "";
				$('btnContinue').disabled=false;
				$('addConcern').value = "";
				$('addConcern').style.background="#FFF";
				$('addConcern').style.color="#333";
				$('addConcern').focus();
			}
		$("indicator").style.visibility = "hidden";
	});
}

function showTagCloud(){
		CCTAgent.getTagCloud({cctId:cctId,type:0,count:100}, function(data){
			if (data.successful){
				$('sidebar_tags').innerHTML = data.html;
			}
	
			$("indicator").style.visibility = "hidden";
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

function tagSearch(theTag){
CCTAgent.searchTags({cctId:cctId,tag:theTag}, function(data){
		if (data.successful){

			//alert(data.count);
			//if (data.count == 1){
				//$('topTags').innerHTML = data.tag.tag.name;
				//getConcernsByTag(data.tag.id);
				//$('myTab').tabber.tabShow(1);
			//}
			
			if (data.count > 0){
				$('tagIndicator').style.visibility = 'visible';
				$('searchTag_title').innerHTML = '<span class="title_section2">Tag Query:</span>'; 
				$('tagSearchResults').innerHTML = '<span class="highlight">' + data.count +' tags match your query&nbsp;&nbsp;(<a href="javascript:showTagCloud();">clear query</a>)</span>';
				$('topTags').innerHTML = data.html;
				$('tagIndicator').style.visibility = 'hidden';
			}
			
			if (data.count == 0){
				$('tagSearchResults').innerHTML = "<span class=\"highlight\">No tag matches found! Please try a different search.</span>";
				$('topTags').innerHTML = "";
			}
			
		}
	});
}
function clear_textbox(inputID)
	{
		inputID.value = "";
		inputID.style.color = "#333";
	} 
	
	
function editConcern(concernId, concern){
CCTAgent.editConcern({concernId:concernId, concern:concern}, function(data){
		if (data.successful){
			alert(concern);
		}
	});
}
</script>
</head>
<body>

<div id="decorBar"></div>
<div id="container">
<div id="header"><img src="/images/logo.jpg"></div>
<div id="searchNavContiner">
	<div id="mainSearch">
			<form name="mainSearch" method="post" onSubmit="search();">
				<input type="text" value="Search LIT!" ID="tbx1" class="tbx1_default" onfocus="clear_textbox(this)" onmouseover="this.className='tbx1_hover';" onmouseout="this.className='tbx1_default';"/>&nbsp;<img src="/images/search.gif">
			</form>	
	</div>
	<div id="mainNav">MyLIT&nbsp;&nbsp;|&nbsp;&nbsp;Discussion&nbsp;&nbsp;|&nbsp;&nbsp;Advanced Search&nbsp;&nbsp;|&nbsp;&nbsp;Help&nbsp;&nbsp;</div>
</div>
<!-- LIGHTBOX -->
<form name="editConcern" method="post" onSubmit="editConcern(id, concern) return false;")
	<input type="text" 
</form>
<a href="javascript:editConcern(443, ' is a change');" class="lbOn">Test This</a>
<div id="overlay"></div>
<div id="lightbox">Hello
    <div id="lbLoadMessage">
        <p>Loading</p>
    </div>
</div>
<!-- LIGHTBOX -->
<div id="navigation">
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
<br>

<h1>Brainstorm Concerns</h1>&nbsp;&nbsp;<span class="title_steps">Step 1 of 7</span>
  <div id="overview"><h3>Overview and Instructions</h3> 
  	<p><strong>Overview: </strong>${cctForm.cct.purpose}</p>
  	<p><strong>Instructions: </strong>${cctForm.cct.instruction}</p>
  </div>

  
 <div id="slate">
  		<h2>Add your concern</h2><br>What is one of your concerns about the Central Puget Sound Transportation System?  View examples of concerns in the right column (concerns tab).
		    <form name="brainstorm" method="post" onSubmit="addTagToList(); return false;">
			      <p><textarea class="textareaAddConcern" name="addConcern" cols="50" rows="5" id="addConcern"></textarea></p>
			      <p class="indent">
				      <input type="button" id="btnContinue" name="Continue" value="Continue" onclick="prepareConcern();">
				      <input type="reset" name="Reset" value="Reset" onClick="resetForm();"> 
				      <span id="indicator" style="visibility:hidden;"><img src="/images/indicator.gif"></span>
			      </p>
			      <div style="display: none; padding-left: 20px;" id="validation"></div>
			  
				    <div id="tagConcerns" style="display: none;">
						    <div id="tags" style="background-color: #DDDDDD; border: 5Px solid #BBBBBB; margin:auto; padding: 5px; width: 70%;">
						    	<h3>Tag Your Concern</h3>&nbsp;|&nbsp; or <a href="javascript:resetForm();">Cancel</a> - back to edit my concern<br>
						    	<p>The tags below are suggested tags for your concern.  Please delete those that do not apply to your concern and use the textbox below to add more tags (if needed). <span class="smallHelp">[ <a href="null">why are tags important?</a> ]</span></p>
									<ul class="tagsList" id="tagsList">
									</ul>	    
									
									<p><input type="text" id="theTag" name="theTag" size="15"><input type="button" name="addTag" id="addTag" value="Add Tag!" onclick="addTagToList();"></p>
									<hr>
									<b class="big">Finished Tagging? <input type="button" name="saveConcern" value="Add Concern to List!" onclick="saveTheConcern();"></b><br>or <a href="javascript:resetForm();">Cancel</a> - back to edit my concern
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
