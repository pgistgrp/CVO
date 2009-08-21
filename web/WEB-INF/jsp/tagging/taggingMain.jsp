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
	<script src="/scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type='text/javascript' src='/dwr/interface/CCTAgent.js'></script>
<script type='text/javascript' src='/dwr/interface/TaggingAgent.js'></script>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>
<script type="text/javascript">
	window.onload(doOnLoad());


function doOnLoad(){
		goToStopWordPage(0);
		goToTagPage(0);
	}

function validateForm()
	{
		if(""==document.forms.testStopWord.addConcern.value)
		{
			$('includeExcludeLog').className ="validation";
			document.getElementById('includeExcludeLog').innerHTML = 'Please fill in your test concern below.';
			Effect.BlindDown('includeExcludeLog');
			Effect.BlindUp('tagConcerns');
			//Effect.Highlight('validation', {duration: 4, endcolor:'#EEF3D8'});
			//Effect.Highlight('theTag', {duration: 10, endcolor:'#EEF3D8'});
			return false;

		}else{
			Effect.BlindUp('validation');
			prepareConcern();
			Effect.BlindDown('tagConcerns');	
			//Effect.Highlight('tags', {duration: 4, endcolor:'#FFFFFF'});
			//$('theTag').value = "add tag";
			//$('theTag').focus();
			return true;
		}
	}
	function resetForm()
	{

		//$('btnContinue').disabled=false;
		Effect.BlindUp('tagConcerns');
		//Effect.BlindUp('includeExcludeLog');
		//$('addConcern').style.background="#FFF";
		//$('addConcern').style.color="#333";
	}

	function prepareConcern(){
		
		$('includeExcludeLog').style.display = 'none';
		concernTags = "";
    potentialTags = "";
		tagHolderId = 0;
		
		//$('btnContinue').disabled=true;
		//$('addConcern').style.background="#EEE";
		//$('addConcern').style.color="#CCC";
		var concern = $('addConcern').value;
		document.getElementById("indicator").style.visibility = "visible";
		CCTAgent.prepareConcern({concern:concern}, function(data) {
			if (data.successful){
				for(i=0; i < data.tags.length; i++){
					concernTags += data.tags[i] + ',';
				}
				for(i=0; i < data.potentialtags.length; i++){
          potentialTags += data.potentialtags[i].replace(/'/g, "\\\'") + ','; 
				}

				if (concernTags != ''){
					document.getElementById('tagsList').innerHTML = '<h5>Words Already in Tags List</h5><br> '+ renderTags(concernTags, 0);
				}else{
					document.getElementById('tagsList').innerHTML = '<p>There are no tags in your inputted text.</p>';
				}
				if (potentialTags != ''){
					document.getElementById('tagsList').innerHTML += '<p><h5>Potential Stopwords and Tags</h5></p><p> '+renderTags(potentialTags, 1)+'</p>';
				}else{
					document.getElementById('tagsList').innerHTML += '<p><h5>Potential Stopwords and Tags</h5></p><p>No potential tags available - all words in your inputted text are either stop words or tags.</p>';
				}
			}
			document.getElementById("indicator").style.visibility = "hidden";
		} );
		
	}

	function renderTags(tags,type){
		
		var str= "";
		tagtemp = tags.split(",");

		for(i=0; i < tagtemp.length; i++){
			if(tagtemp [i] != ""){
				if(type==0){
					str += '<li class="tagsList">'+ tagtemp [i] +  '</span><span class="tagsList_controls"> </span></li>';	
				}
				
				if(type==1){
					str += '<li class="tagsList">'+ tagtemp [i] +  '</span><span class="tagsList_controls"><a href="javascript:addStopWordFromResults(\''+ tagtemp [i] +'\');"><img src="/images/removeItem_lite.gif" alt="Exclude this Tag!" border="0"></a><a href="javascript:addTagfromResults(\''+ tagtemp [i] +'\');"><img src="/images/addItem_lite.gif" alt="Include this Tag!" border="0"></a></span></li>';	
				}
			}
		}
		return str;
	}

function ifEnter(field,event) {
	var theCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
	if (theCode == 13){
	validateForm();
	$('theTag').focus();
	return false;
	}
	else
	return true;
}

function showTheError(errorString, exception){
				$('container').style.display = 'none';
				$('caughtException').style.display = 'block';
				$('caughtException').innerHTML +='<p>If this problem persists, please <A HREF="mailto:webmaster@pgist.org?subject=LIT Website Problem>contact our webmaster</a></p>';
}


function goToStopWordPage(pageNum){
	TaggingAgent.getStopWords({page:pageNum, count: 150}, {
		callback:function(data){
				if (data.successful){

					$('excludeListCont').innerHTML = data.html;
				}
				//if (id != undefined){
					//		Effect.Highlight('stopWord' + id, {duration: 4, endcolor:'#EEF3D8'})
			//	}
			},
		errorHandler:function(errorString, exception){
				showTheError();
		}
		});
}

function goToTagPage(pageNum){
	TaggingAgent.getTags({page:pageNum, count: 150}, {
		callback:function(data){
				if (data.successful){

					$('includeListCont').innerHTML = data.html;
				}
				//if (id != undefined){
					//		Effect.Highlight('stopWord' + id, {duration: 4, endcolor:'#EEF3D8'})
			//	}
			},
		errorHandler:function(errorString, exception){
				showTheError();
		}
		});
}

function addStopWordFromResults(stopWord){
	createStopWord(stopWord);
	//new Effect.Highlight('tagsList', {duration: 0.1, beforeStart: function(){createStopWord(stopWord);},afterFinish: function(){ prepareConcern();}});
}

function addTagfromResults(tag){
	createTag(tag);
	//new Effect.Highlight('tagsList', {duration: 0.1, beforeStart: function(){createTag(tag);},afterFinish: function(){ prepareConcern();}});		
}

function createStopWord(stopWord){
	TaggingAgent.createStopWord({name:stopWord}, {
		callback:function(data){
				if (data.successful){
					Effect.BlindUp('excludeWordValidation');
					goToStopWordPage(0);
					prepareConcern();
					$('includeExcludeLog').innerHTML = '<h2>Successful!</h2><img src="/images/removeItem.gif" alt="Include" border="0">&nbsp;The word "<b>' + stopWord +'</b>" has been successfully added to the stop word list!';
					Effect.Appear('includeExcludeLog', {duration: 0.8, afterFinish:function(){Effect.SwitchOff('includeExcludeLog', {duration: 1});}});
					
					$('theExcludeWord').value = '';
					$('theExcludeWord').focus();
				}
				if (data.successful != true){
						alert(data.reason);
						//$('includeExcludeLog').className ="validation";
						//$('includeExcludeLog').innerHTML = '<h4>ERROR!</h4>&nbsp;'+ data.reason.toString();
						//new Effect.toggle('includeExcludeLog', 'appear', {duration: 1, afterFinish:function(){new Effect.toggle('includeExcludeLog', 'appear', {duration: 1});}});
					}
			},
		errorHandler:function(errorString, exception){
				showTheError();
		}
		});
}

function createTag(tag){
	TaggingAgent.createTag({name:tag}, {
		callback:function(data){
				if (data.successful){
					Effect.BlindUp('includeWordValidation');
					goToTagPage(0);
					prepareConcern();
					$('includeExcludeLog').innerHTML = '<h2>Successful!</h2><br>&nbsp;&nbsp;<img src="/images/addItem.gif" alt="Include" border="0">&nbsp;The word "<b>' + tag +'</b>" has been successfully added to the tag list!';
					Effect.Appear('includeExcludeLog', {duration: 0.8, afterFinish:function(){Effect.SwitchOff('includeExcludeLog', {duration: .5});}});
					
					$('theIncludeWord').value = '';
					$('theIncludeWord').focus();
				}
				if (data.successful != true){
						alert(data.reason);
						//$('includeExcludeLog').className ="validation";
						//$('includeExcludeLog').innerHTML = '<h4>ERROR!</h4>&nbsp;'+ data.reason.toString();
						//new Effect.toggle('includeExcludeLog', 'appear', {duration: 1, afterFinish:function(){new Effect.toggle('includeExcludeLog', 'appear', {duration: 1});}});
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
	TaggingAgent.deleteStopWord({id:stopWordID}, {
		callback:function(data){
				if (data.successful){
					$('txtSearchExclude').value = $('txtSearchExclude').defaultValue;
					goToStopWordPage(0);
				}
			},
		errorHandler:function(errorString, exception){
				showTheError();
		}
		});
	}
}

function deleteTag(tagID){
	var destroy = confirm ('Are you sure you want to delete this Tag? Note: there is no undo.')
	if (destroy){
	TaggingAgent.deleteTag({id:tagID}, {
		callback:function(data){
				if (data.successful){
					$('txtSearchInclude').value = $('txtSearchInclude').defaultValue;
					goToTagPage(0);
				}
				if (data.successful != true){
					alert(data.reason);
				}
			},
		errorHandler:function(errorString, exception){
				showTheError();
		}
		});
	}
}


function searchStopWords(stopWord){
	TaggingAgent.searchStopWords({name:stopWord},{
			callback:function(data){
				  $('stopWordSearchIndicator').style.visibility = 'visible';
					if (data.successful){
						if ($('txtSearchExclude').value == ""){
							goToStopWordPage(0);
							$('stopWordSearchIndicator').style.visibility = 'hidden';
						}
	
						if ($('txtSearchExclude').value != ""){
							if (data.successful) {
								$('excludeListCont').innerHTML = "";
							  for (var i=0; i<data.stopWords.length; i++) {
							   $('excludeListCont').innerHTML += '<li><span class="includeExclude">'+data.stopWords[i].name+'<a href="javascript:deleteStopWord(\''+ data.stopWords[i].id +'\');"><img src="/images/trash.gif" alt="Delete this Tag!" border="0"></a></span></li>';
							  }
							}
	
							$('stopWordSearchIndicator').style.visibility = 'hidden';
						}
					}
					if (data.successful != true){
						alert(data.reason);
					}
			},
			errorHandler:function(errorString, exception){
						showTheError();
			}
		});
}

function searchTags(tag){
TaggingAgent.searchTags({name:tag},{
		callback:function(data){
			  $('tagSearchIndicator').style.visibility = 'visible';
				if (data.successful){
					if ($('txtSearchInclude').value == ""){
						goToTagPage(0);
						$('tagSearchIndicator').style.visibility = 'hidden';
					}

					if ($('txtSearchInclude').value != ""){
						if (data.successful) {
							$('includeListCont').innerHTML = "";
						  for (var i=0; i<data.tags.length; i++) {
						   $('includeListCont').innerHTML += '<li><span class="includeExclude">'+data.tags[i].name+'<a href="javascript:deleteTag(\''+ data.tags[i].id +'\');"><img src="/images/trash.gif" alt="Delete this Tag!" border="0"></a></span></li>';
						  }
						}

						$('tagSearchIndicator').style.visibility = 'hidden';
					}
				}
				if (data.successful != true){
					alert(data.reason);
				}
		},
		errorHandler:function(errorString, exception){
					showTheError();
		}
	});
}


</script>
<wf:pageunload />
</Head>
<body>

<!-- HEADER -->
  <!-- Start Global Headers  -->
  <wf:nav />
  <wf:subNav />
  <!-- End Global Headers -->
<div id="container">


	<!-- LIGHTBOX -->
	<div id="overlay"></div>
	<div id="lightbox"></div>
	<!-- END LIGHTBOX -->

	
	<div class="tabber" id="myTab">
		    	<div id="tab_excludeList"  class="tabbertab">
				    	<h2>Stop Word List</h2>
				    	<div id="searchExclude_form" style="position: relative;">
								<form name="searchExclude" method="post" onSubmit="searchStopWords($('txtSearchExclude').value); return false;">
									<input type="text" id="txtSearchExclude" name="txtSearchExclude" style="width:120px; padding-left: 1px; padding-right: 20px; margin-right:5px; background: url('/images/search_light.gif') no-repeat right; background-color: #FFFFFF;" class="searchTagTextbox" value="Search Stop Words" onfocus="this.value = ( this.value == this.defaultValue ) ? '' : this.value;return true;" onkeyup="searchStopWords($('txtSearchExclude').value);"><div id="stopWordSearchIndicator" style="visibility:hidden; position: absolute; right:0; margin-right: 150px;"><img src="/images/indicator.gif"></div>
								</form>
							</div>
				    	<h4>top Word List (exclude words <img src="/images/removeItem.gif" alt="Exclude" border="0">)</h4><br>Words to be excluded upon concern submission. Below are the current words in the stop words list. Please use the textbox at the bottom to add new words to the list.

				    	<p>
				    		<div id="excludeListCont" style="list-style:none; margin:auto;">
								</div>
							</p>
				    	<div id="excludeListBreaker" class="breaker">
				    	<hr>
				    	<form name="createExcludeWord" method="post" onSubmit="createStopWord($('theExcludeWord').value);return false;">
				    		<input type="text" id="theExcludeWord" class="tagTextbox" name="theExcludeWord" size="15"><input type="button" name="addExclude" id="addExclude" value="Add Word to List" onclick="createStopWord($('theExcludeWord').value);return false;">
								<div style="display: none; height: 15px;" id="excludeWordValidation" class="validation"></div>
							</form>
							</div>
					</div>
				  <div id="tab_includeList" class="tabbertab">
			    	<H2>Tags List</H2>

				    	<div id="searchInclude_form" style="position: relative;">
								<form name="searchInclude" method="post" onSubmit="searchTags($('txtSearchInclude').value); return false;">
									<input type="text" id="txtSearchInclude" name="txtSearchInclude" style="width:120px; padding-left: 1px; padding-right: 20px; margin-right:5px; background: url('/images/search_light.gif') no-repeat right; background-color: #FFFFFF;" class="searchTagTextbox" value="Search Tags" onfocus="this.value = ( this.value == this.defaultValue ) ? '' : this.value;return true;" onkeyup="searchTags($('txtSearchInclude').value);"><div id="tagSearchIndicator" style="visibility:hidden; position: absolute; right:0; margin-right: 150px;"><img src="/images/indicator.gif"></div>
								</form>
							</div>

				    	<p><h4>Tags List (include words <img src="/images/addItem.gif" alt="Exclude" border="0">)</h4><br>Words that will be matched and suggested upon submission of concern. Below are the current words in the include list database. Please use the textbox at the bottom to add new words to the list.
				    		<div id="includeListCont" style="list-style:none; margin:auto;">
								</div>
							</p>
				    	<div id="includeListBreaker" class="breaker">
			    	<hr>
			    		<form name="createIncludeWord" method="post" onSubmit="createTag($('theIncludeWord').value);return false;">
				    		<input type="text" id="theIncludeWord" class="tagTextbox" name="theIncludeWord" size="15"><input type="button" name="addInclude" id="addInclude" value="Add Word to List" onclick="createTag($('theIncludeWord').value);return false;">
								<div style="display: none; height: 15px;" id="includeWordValidation" class="validation"></div>
							</form>
			    	<div style="display: none;" id="includeWordValidation" class="validation"></div>
			    </div>
	</div>
<div id="includeExcludeLog" class="successful" style="display: none;"></div>
	<div id="testConcern" class="suppSlate fullBox">
		<h4>Test Stopwords and Suggested Tags</h4><br>Add a string of text that you would like to test.  This will return suggested and unmatched tags from a participant's perspective.
		<form name="testStopWord" method="post" onSubmit="addTagToList('tagsList', 'theTag','tagValidation'); return false;">
			      <p><textarea class="textareaAddConcern" onkeypress="ifEnter(this,event);" name="addConcern" cols="20" rows="2" id="addConcern"></textarea></p>
			      <p class="indent">
				      <input type="button" id="btnContinue" name="Continue" value="Continue" onclick="validateForm();">
				      <input type="reset" name="Reset" value="Reset" onClick="resetForm();">
				      <span id="indicator" style="visibility:hidden;"><img src="/images/indicator.gif"></span>
			      </p>
			      <div style="display: none;" id="validation"></div>
			      
				    <div id="tagConcerns" style="display: none;">
						    <div id="tags" style="background-color: #FFF; border: 5Px solid #BBBBBB; margin:auto; padding: 5px; width: 70%;">
						    	<h3>Results (with stopwords removed)</h3><p></p>
									<ul class="tagsList" id="tagsList">
									</ul>
									<p></p>
									
									<p align="right"><input type="button" name="editConcern" value="close" onClick="resetForm();"> </p>
						    </div>
						    <br>
				    </div>
	 </form>
	</div>
</div>
 <div id="caughtException"><h2>A Problem has Occured</h2><br>We are sorry but there was a problem accessing the server to complete your request.  <b>Please try refreshing the page.</b></div>
</div>
  <!-- Start Global Headers  -->
  <wf:subNav />
  <!-- End Global Headers -->
</body>
</html>

