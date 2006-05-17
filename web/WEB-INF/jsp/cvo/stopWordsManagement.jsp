<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>PGIST Portal - Let's Improve Transportation</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<style type="text/css" media="screen">@import "/styles/pgist.css";</style>
<style type="text/css" media="screen">@import "/styles/tabsFull.css";</style>

<script src="/scripts/tabs.js" type="text/javascript"></script>
<script src="/scripts/prototype.js" type="text/javascript"></script>
<script src="/scripts/effects.js" type="text/javascript"></script>
<script src="/scripts/combo.js" type="text/javascript"></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type='text/javascript' src='/dwr/interface/CCTAgent.js'></script>
<script type='text/javascript' src='/dwr/interface/StopWordAgent.js'></script>

<script type="text/javascript">
	window.onload(doOnLoad());
  

function doOnLoad(){
		goToStopWordPage(0);
	}

function validateForm()
	{
		if(""==document.forms.testStopWord.addConcern.value)
		{
			document.getElementById('validation').innerHTML = 'Please fill in your concern above.';
			Effect.OpenUp('validation');
			Effect.CloseDown('tagConcerns');
			Effect.Yellow('validation', {duration: 4, endcolor:'#EEF3D8'});
			//Effect.Yellow('theTag', {duration: 10, endcolor:'#EEF3D8'});
			return false;
			
		}else{
			Effect.CloseDown('validation');
			Effect.OpenUp('tagConcerns');
			//Effect.Yellow('tags', {duration: 4, endcolor:'#FFFFFF'});
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
			CCTAgent.prepareConcern({concern:concern}, function(data) {
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
	
	function renderTags(tags,type){
		sty = (type == 1)?"tagsList":"suggestedTagsList";
		var str= "";
		tagtemp = tags.split(",");
		
		for(i=0; i < tagtemp.length; i++){
			if(tagtemp [i] != ""){
				str += '<li class="' + sty + '">'+ tagtemp [i] +'</span><span class="tagsList_controls">&nbsp;<a href="javascript:addStopWordFromResults(\''+ tagtemp [i] +'\');"><img src="/images/addItem.gif" alt="Add this Tag to Stop Word List!" border="0"></a></span></li>';	
			}
		}	
		return str;
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

function goToStopWordPage(pageNum){
	StopWordAgent.getStopWords({page:pageNum, count: 75}, {
		callback:function(data){
				if (data.successful){
					$('excludeList').innerHTML = data.html;
				}
				//if (id != undefined){
					//		Effect.Yellow('stopWord' + id, {duration: 4, endcolor:'#EEF3D8'})
			//	}
			},
		errorHandler:function(errorString, exception){ 
				showTheError();
		}
		});
}

function addStopWordFromResults(stopWord){
	createStopWord(stopWord);
	removeFromGeneratedTags(stopWord);
}
function createStopWord(stopWord){
	StopWordAgent.createStopWord({name:stopWord}, {
		callback:function(data){
				if (data.successful){
					Effect.CloseDown('excludeWordValidation');
					goToStopWordPage(0);
					$('theExcludeWord').value = '';
					$('theExcludeWord').focus();
				}
				if (data.successful != true){
						document.getElementById('excludeWordValidation').innerHTML = 'The stop word you entered already exists in the database.  Please enter a different stop word.&nbsp;&nbsp;<a href="javascript:Effect.CloseDown(\'excludeWordValidation\');">Close</a>';
						Effect.OpenUp('excludeWordValidation');
				}
			},
		errorHandler:function(errorString, exception){ 
				showTheError();
		}
		});
}

function deleteStopWord(stopWordID){
	var destroy = confirm ('Are you sure you want to delete this stop word? Note: there is no undo.')
	if (destroy){
	StopWordAgent.deleteStopWord({id:stopWordID}, {
		callback:function(data){
				if (data.successful){
					goToStopWordPage(0);
				}
			},
		errorHandler:function(errorString, exception){ 
				showTheError();
		}
		});
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
		<div id="mainNav">Moderator Home&nbsp;&nbsp;&nbsp;&nbsp;Agenda Manager&nbsp;&nbsp;&nbsp;&nbsp;<span class="active">Databases</span>&nbsp;&nbsp;&nbsp;&nbsp;Synthesize Concerns&nbsp;&nbsp;&nbsp;&nbsp;Manage Discussionb&nbsp;&nbsp;</div>
	</div>
	<!-- LIGHTBOX -->
	<div id="overlay"></div>
	<div id="lightbox"></div>
	<!-- LIGHTBOX -->
	<div id="pageTitle">
	<span class="title_page">Moderator Dashboard: </span><h1>Stop Word Management Tool</h1>
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
		    	<div id="tab_excludeList"  class="tabbertab">
				    	<h2>Exclude List</h2>
				    	<span class="title_section">Exclude List</span><br>Words that will be "thrown away" upon concern submission. Below are the current words in the exclude list database. Please use the textbox at the bottom to add new words to the list.
				    	<p>
				    		<ul class="tagsList" id="excludeList">
								</ul>	   
							</p>
				    	<div id="excludeListBreaker" class="breaker">
				    	<hr>
				    	<form name="createExcludeWord" method="post" onSubmit="createStopWord($('theExcludeWord').value);return false;">
				    		<input type="text" id="theExcludeWord" class="tagTextbox" name="theExcludeWord" size="15"><input type="button" name="addExclude" id="addExclude" value="Add Word to List" onclick="createStopWord($('theExcludeWord').value);return false;">
								<div style="display: none;" id="excludeWordValidation" class="validation"></div>
							</form>
							</div>
					</div>
				  <div id="tab_includeList" class="tabbertab">
			    	<H2>Include List</H2>
			    	<span class="title_section">Include List</span><br>Words that will be matched and suggested when a user submits a concern.  Below are the current words in the include list database.  Please use the textbox at the bottom to add new words to the list.
			    	<p>
			    		<ul class="stopWordList" id="includeList">
							</ul>	   
						</p>
			    	<hr>
			    	<input type="text" id="theIncludeWord" class="tagTextbox" name="theIncludeWord" size="15"><input type="button" name="addInclude" id="addInclude" value="Add Word to List" onclick="addTagToList('tagsList','theTag','tagValidation');return false;">
			    	<div style="display: none;" id="includeWordValidation" class="validation"></div>
			    </div>
	</div>
	
	<div id="testConcern" class="suppSlate fullBox">
		<h2>Test Stopwords</h2><br>Add a string of text that you would like to test.
		<form name="testStopWord" method="post" onSubmit="addTagToList('tagsList', 'theTag','tagValidation'); return false;">
			      <p><textarea class="textareaAddConcern" onkeypress="ifEnter(this,event);" name="addConcern" cols="20" rows="2" id="addConcern"></textarea></p>
			      <p class="indent">
				      <input type="button" id="btnContinue" name="Continue" value="Continue" onclick="prepareConcern();">
				      <input type="reset" name="Reset" value="Reset" onClick="resetForm();"> 
				      <span id="indicator" style="visibility:hidden;"><img src="/images/indicator.gif"></span>
			      </p>
			      <div style="display: none;" id="validation"></div>
				    <div id="tagConcerns" style="display: none;">
						    <div id="tags" style="background-color: #FFF; border: 5Px solid #BBBBBB; margin:auto; padding: 5px; width: 70%;">
						    	<h3>Results</h3><p></p>
									<ul class="tagsList" id="tagsList">
									</ul>	    
									<p align="right"><input type="button" name="editConcern" value="Edit Concern" onClick="resetForm();"> </p>
						    </div>
						    <br>
				    </div>
	 </form>
	</div>
</div>

<div id="footerContainer">
	<div id="footer"><a href="http://www.pgist.org" target="_blank"><img src="/images/footer_pgist.jpg" alt="Powered by the PGIST Portal" border="0" align="right"></a></div>
	<div id="nsf">This research is funded by National Science Foundation, Division of Experimental and Integrative Activities, Information Technology Research (ITR) Program, Project Number EIA 0325916, funds managed within the Digital Government Program.</div>
</div>
</body>
</html>

