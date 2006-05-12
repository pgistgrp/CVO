<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>PGIST Portal - Let's Improve Transportation</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<style type="text/css" media="screen">@import "/styles/pgist.css";</style>
<style type="text/css" media="screen">@import "/styles/tabs.css";</style>

<script src="/scripts/tabs.js" type="text/javascript"></script>
<script src="/scripts/prototype.js" type="text/javascript"></script>
<script src="/scripts/effects.js" type="text/javascript"></script>
<script src="/scripts/combo.js" type="text/javascript"></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type='text/javascript' src='/dwr/interface/CCTAgent.js'></script>

<script type="text/javascript">
	  var cctId = ${cctForm.cct.id};
function validateForm()
	{
		if(""==document.forms.brainstorm.addConcern.value)
		{
			document.getElementById('validation').innerHTML = 'Please fill in your concern above.';
			Effect.OpenUp('validation');
			Effect.CloseDown('tagConcerns');
			Effect.Yellow('validation', {duration: 4, endcolor:'#FFCCCC'});
			Effect.Yellow('theTag', {duration: 10, endcolor:'#F3FFCB'});
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

function showTheError(errorString, exception){
					$('overview').style.display = 'none';
				$('slate').style.display = 'none';
				$('bar').style.display = 'none';
				$('caughtException').style.display = 'block';
				$('caughtException').innerHTML +='<p>If this problem persists, please <A HREF="mailto:webmaster@pgist.org?subject=LIT Website Problem>contact our webmaster</a></p>';
}
</script>
</Head>
<body>

<div id="decorBar"></div>
<div id="container">

<div id="searchNavContiner">
		<div id="logo"><img src="/images/logo.png"></div>
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
	<span class="title_page">Moderator Dashboard: </span><h1>Tag Management</h1>
		<div id="bread">
		<ul>
			<li class="first"><a href="null">Moderator Dashboard</a>
				<ul>
					<li>&#187; <a href="null">Databases</a></li>
						<ul>
								<li>&#187; <a href="null">Stop Word Management Tool</a></li>
						</ul>
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
	
	 <div id="caughtException"><h2>A Problem has Occured</h2><br>We are sorry but there was a problem accessing the server to complete your request.  <b>Please try refreshing the page.</b></div>
	<div class="tabber" id="myTab">
			  <div id="sidebar_tags" class="tabbertab">
		    	<H2>Include List</H2>
		    	<span class="title_section">Include List</span><br>Words that will be matched and suggested when a user submits a concern.  Below are the current words in the include list database.  Please use the textbox at the bottom to add new words to the list.
		    	<p>stop words inserted here</p>
		    	<hr>
		    	<input type="text" id="theIncludeWord" class="tagTextbox" name="theIncludeWord" size="15"><input type="button" name="addInclude" id="addInclude" value="Add Word to List" onclick="addTagToList('tagsList','theTag','tagValidation');return false;">
		    </div>
	
		    <div id="sidebar_concernsContainer" class="tabbertab">
		    	<h2>Exclude List</h2>
		    	<span class="title_section">Exclude List</span><br>Words that will be "thrown away" upon concern submission. Below are the current words in the exclude list database. Please use the textbox at the bottom to add new words to the list.
		    	<p>stop words inserted here</p>
		    	<hr>
		    	<input type="text" id="theExcludeWord" class="tagTextbox" name="theExcludeWord" size="15"><input type="button" name="addExclude" id="addExclude" value="Add Word to List" onclick="addTagToList('tagsList','theTag','tagValidation');return false;">
					<div id="sidebar_concerns">
					</div>
		    </div>
	</div>
	
	<div id="testConcern" class="slate fullBox">
		<h2>Test Stopwords</h2><br>Add a string of text that you would like to test.
		<form name="brainstorm" method="post" onSubmit="addTagToList('tagsList', 'theTag','tagValidation'); return false;">
			      <p><textarea class="textareaAddConcern" onkeypress="ifEnter(this,event);" name="addConcern" cols="20" rows="2" id="addConcern"></textarea></p>
			      <p class="indent">
				      <input type="button" id="btnContinue" name="Continue" value="Continue" onclick="prepareConcern();">
				      <input type="reset" name="Reset" value="Reset" onClick="resetForm();"> 
				      <span id="indicator" style="visibility:hidden;"><img src="/images/indicator.gif"></span>
			      </p>
			      <div style="display: none;" id="validation"></div>
				    <div id="tagConcerns" style="display: none;">
						    <div id="tags" style="background-color: #FFF; border: 5Px solid #BBBBBB; margin:auto; padding: 5px; width: 70%;">
						    	<h3>Results</h3>
									<ul class="tagsList" id="tagsList">
									</ul>	    
						    </div>
						    <br>
				    </div>
	</div>
</div>

<div id="footerContainer">
	<div id="footer"><a href="http://www.pgist.org" target="_blank"><img src="/images/footer_pgist.jpg" alt="Powered by the PGIST Portal" border="0" align="right"></a></div>
	<div id="nsf">This research is funded by National Science Foundation, Division of Experimental and Integrative Activities, Information Technology Research (ITR) Program, Project Number EIA 0325916, funds managed within the Digital Government Program.</div>
</div>
</body>
</html>

