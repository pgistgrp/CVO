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
<script src="/scripts/ajax.js" type="text/javascript"></script>
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
  
	function doOnLoad() {
		OpenTab("tab_page2", "Tags", "tags.html", false, '');
		OpenTab("tab_page1", "Other Concerns", "concerns.html", false, '');
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
			Effect.Yellow('tagConcerns', {duration: 4, endcolor:'#EEEEEE'});
			return true;
		}
	}
	
	function resetForm()
	{
		Effect.CloseDown('tagConcerns');
		Effect.CloseDown('validation');
	}
	
	function prepareConcern(){
		if (validateForm()){
			var concern = $('addConcern').value;
			document.getElementById("indicator").style.visibility = "visible";
			CCTAgent.prepareConcern({cctId:cctId,concern:concern}, function(data) {
				if (data.successful){
					var str= "";
					for(i=0; i < data.tags.length; i++){
						str += '<li><span class="tagsList_tag"></span>'+ data.tags[i] +' <span class="tagsList_controls"><img src="/images/trash.gif" alt="Delete this Tag!" ></span></li>';
						concernTags += data.tags[i] + ',';
					}
					document.getElementById('tagsList').innerHTML = str;

				}
				document.getElementById("indicator").style.visibility = "hidden";
			} );
		}
	}
	
	function addTagToList(){
		document.getElementById('tagsList').innerHTML += '<li><span class="tagsList_tag"></span>'+ document.getElementById("theTag").value +' <span class="tagsList_controls"><img src="/images/trash.gif" alt="Delete this Tag!" ></span></li>';
		concernTags += document.getElementById("theTag").value + ',';
		Effect.Yellow('theTag', {duration: 4, endcolor:'#EEEEEE'});
		$('theTag').value = "";
	}
	
	function saveTheConcern(){
		
		var concern = $('addConcern').value;
		$("indicator").style.visibility = "visible";
		$('explaination').innerHTML = "";
		CCTAgent.saveConcern({cctId:cctId,concern:concern,tags:concernTags}, function(data){
			if (data.successful){
				$('myConcerns').innerHTML += '<li>'+ concern + '<br>' + concernTags + '</li>';
				Effect.Yellow('myConcerns', {duration: 4, endcolor:'#EEEEEE'});
				Effect.CloseDown('tagConcerns');
				//Reset add concerns textbox and Clear comma separated concerns tag list
				concernTags = "";
				document.forms.brainstorm.addConcern.value = "";
				document.forms.brainstorm.addConcern.focus();
			}
		$("indicator").style.visibility = "hidden";
	});
}

	function showTagCloud(){
		CCTAgent.getTagCloud({cctId:cctId,type:0,count:20}, function(data){
			if (data.successful){
				var s = '';
				for (i=0; i<data.tags.length; i++){
					s += data.tags[i].tag.name + ' ';
				}
				$("tabPanels").innerHTML = s;
			}
		$("indicator").style.visibility = "hidden";
	});
}

function showConcerns(theType){
	CCTAgent.getConcerns({cctId:cctId,type:theType,count:5}, function(data){
		if (data.successful){
			$('tabPanels').innerHTML = data.html;
		}
	});
}

	
	
</script>
</head>
<body onload="doOnLoad()">

<div id="decorBar"></div>
<div id="header"><img src="/images/logo.jpg"></div>

<div id="navigation">
	<div id="bread">
	<ul>
		<li class="first">LIT Process
			<ul>
				<li>&#187; Brainstorm Concerns</li>
			</ul>
		</li>
	</ul>
	</div>
</div>
<br>
<span class="title_page">Brainstorm Concerns</span>
<div id="container"><br>
  <div id="overview"><span class="title_overview">Overview and Instructions</span> 
  	<p><strong>Instructions:</strong>${cctForm.cct.instruction}</p>
  </div>
  <br>
  
 <div id="slate">
    <span class="title_section">Add your concern</span>
    <form name="brainstorm" method="post" onSubmit="addTagToList(); return false;">
	    	<span id="addConcernInput">
		      <p><textarea style="width:70%" name="addConcern" cols="50" rows="5" id="addConcern"></textarea></p>
		      <p><input type="reset" name="Submit2" value="Reset" onClick="resetForm();"> <input type="button" name="Continue" value="Continue" onclick="prepareConcern();"><span id="indicator" style="visibility:hidden;"><img src="/images/indicator.gif"></span>  </p>
		      <div style="display: none;" id="validation"></div>
	      </span>
	   
		    <div id="tagConcerns" style="display: none;"><span class="title_section">Tag Your Concern</span>
				    <div id="tags">The tags below are suggested tags for your concern.  Please delete those that do not apply to your concern and use the textbox below to add more tags (if needed). <span class="smallHelp">[ <a href="null">why are tags important?</a> ]</span>
							<ul id="tagsList">
							</ul>	    
							Add more tags (Add tags that were not suggested - View Examples)<br>
							<input type="text" id="theTag" name="theTag" size="15"><input type="button" name="addTag" id="addTag" value="Add Tag!" onclick="addTagToList();">
							<p>Finished Tagging? <input type="button" name="saveConcern" value="Add Concern to List!" onclick="saveTheConcern();"></p>
				    </div>
		    </div>
		   
		    <hr><span class="title_section">List of Created Concerns</span><br>
		    <p><span id="explaination" class="explaination">None created yet.  Please add a concern above.  Please refer to other participant's concerns on the right column for examples.</span></p>
	      <ol id="myConcerns">
			  
		  	</ol>
		  	<hr>
				<span class="title_section">Finished Brainstorming Concerns?</span><br>
				<p><span class="explaination"><a href="javascript:showConcerns(1);">Continue to the next step!</a></span></p>
    </form>
  </div>
 
  <div id="tabContainer">
		<div id="tabs">
			<ul id="tabList">
			</ul>
		</div>
		<div id="tabPanels"></div>
  </div>
<div id="footerContainer">
	<div id="footer"><a href="http://www.pgist.org" target="_blank"><img src="/images/footer_pgist.jpg" alt="Powered by the PGIST Portal" border="0" align="right"></a></div>
	<div id="nsf">This research is funded by National Science Foundation, Division of Experimental and Integrative Activities, Information Technology Research (ITR) Program, Project Number EIA 0325916, funds managed within the Digital Government Program.</div>
</div>
</body>
</html>
