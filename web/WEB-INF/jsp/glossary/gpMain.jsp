<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Glossary Public View</title>
<link rel="stylesheet" type="text/css" href="/styles/pgist.css">
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type='text/javascript' src='/dwr/interface/GlossaryPublicAgent.js'></script>

<script type="text/javascript">
	
		window.onload = doOnLoad();
		var direction = "asc";
		
		function doOnLoad(){
			direction = "asc";
			getTerms("", "name");
		}
		
		function setSort(thisSort, term){
			headings = document.getElementsByTagName("th"); 
			for (var i = 0; i < headings.length; i++) { 
				if (headings[i].id != 'def'){
			    if (headings[i].id == thisSort){
			    	if(direction == 'asc'){
			    		headings[i].innerHTML = thisSort + '&nbsp;&nbsp;<a href="javascript:sortDir(\'desc\',\'' + thisSort + '\', \''+ term +'\');"><img src="/images/sort_' + direction +'.gif" border="0"></a>';
			   		}else{
			   			headings[i].innerHTML = thisSort + '&nbsp;&nbsp;<a href="javascript:sortDir(\'asc\',\'' + thisSort + '\', \''+ term +'\');"><img src="/images/sort_' + direction +'.gif" border="0"></a>';
			   		}
			    }else{
			    	headings[i].innerHTML = '<a href="javascript: getTerms(\''+ term +'\',\''+ headings[i].id +'\');">'+ headings[i].innerHTML +'</a>';
			    }
			  }  
			}
		}	
		
		function sortDir(switchTo, thisSort, term){
				direction = switchTo;
				getTerms(term, thisSort); 
		}
		
		function getTerms(term,sortby){
				if ($('loading-indicator') != null ){ 
					$('loading-indicator').style.display = "inline";
				}
				if ($('txtSearch') != null){
							if ($('txtSearch').value != $('txtSearch').defaultValue) {
								$('clearSearch').style.display = "inline";
								$('clearSearch').innerHTML = '<a href="javascript: clearResults(\''+ sortby +'\');">Clear Search</a>';
							}
				}
				
				GlossaryPublicAgent.getTerms({filter:term, sort:sortby, direction:direction}, {
				callback:function(data){
					if (data.successful){ 	
							$('list').innerHTML = "";
							$('list').innerHTML += data.html;
							setSort(sortby, term);
							direction = "asc"; //reset direction
							$('loading-indicator').style.display = "none";
					}
					if (data.successful != true){
						alert(data.reason);
						$('loading-indicator').style.display = "none";
					}
					
				},
				errorHandler:function(errorString, exception){ 
						//showTheError();
				}
			});
		}
		

		function getTermObject(termId){
				GlossaryPublicAgent.getTermObject({id:termId}, {
				callback:function(data){

					if (data.successful){ 
							alert('ready to go');
					}

					if (data.successful != true){
						alert(data.reason);
					}
				},
				errorHandler:function(errorString, exception){ 
						//showTheError();
				}
			});
		}
		
	
		function clearResults(sortby){
			$('txtSearch').value = $('txtSearch').defaultValue;
			getTerms('', sortby);
			$('clearSearch').style.display = "none";
		}
		
		function lightboxDisplay(action){
			$('overlay').style.display = action;
			$('lightbox').style.display = action;
		}
		
		function proposeTermCont(){
			lightboxDisplay('inline');
			os = '';
			os = '<span class="closeBox"><a href="javascript: lightboxDisplay(\'none\');"><img src="/images/closelabel.gif" border="0"></a></span>'
			os += $('proposeForm').innerHTML;
			$('lightbox').innerHTML = os;
		}
		
		function proposeTermPrep(name, shortDef, fullDef, source1, source2, source3, termlink1, termlink2, termlink3){		
			//validate sources and termlinks
			if(name == '' || shortDef == '' || fullDef == ''){
				alert('Please make sure all required fields are filled out');
			}else{
			sources = new Array(source1,source2,source3);
				for (var i=0; i<sources.length; i++)
				{
					if(sources[i] == 'http://' || sources[i] == ''){
						delete sources[i];
					}
				}
			links = new Array(termlink1,termlink2,termlink3);
				for (var i=0; i<links.length; i++)
				{
					if(links[i] == 'http://' || links[i] == ''){
						delete links[i];
					}
				}
			proposeTerm(name,shortDef, fullDef, links, sources);

			}
		}
		
		function proposeTerm(name,shortDef, fullDef, links, sources){
				GlossaryPublicAgent.proposeTerm({name:name, shortDef:shortDef, fullDef:fullDef}, null, links, sources, null, {
				callback:function(data){

					if (data.successful){ 
							$('proposeForm').innerHTML = '<h3>Thank you for your term proposal!</h3><br><p>Your term has been submitted to the moderator for approval.  You will be notified after moderator review.</p><p><a href="javascript: lightboxDisplay(\'none\');">Close Box</a></p>';
					}else{
						  alert(data.reason);
					}
				},
				errorHandler:function(errorString, exception){ 
						//showTheError();
				}
			});
		}

</script>
<!-- Template 4 CSS -->
<style type="text/css" media="screen">@import "styles/position.css";</style>
<style type="text/css" media="screen">@import "styles/styles.css";</style>
<!-- Temporary Borders used for testing <style type="text/css" media="screen">@import "styles/tempborders.css";</style>-->
<!-- End Template 4 CSS -->

<style>
#slate ul{display:inline; margin:0; padding:0}
#slate li{list-style: none; display:inline; padding: 3px;}
tr:hover {background-color: #eee;}
#loading-indicator{width: 100px; position: fixed; top:0; left:0; background-color: red; z-index: 1; padding: 3px; color: #fff;} /*fixed positioning needs ie 6- hack */
</style>
</head>
<body>
<div id="loading-indicator">Loading... <img src="/images/indicator_arrows.gif"></div>
<div id="container">
<div id="header"><!--<jsp:include page="gmTerms.jsp"/>--><img src="/images/logo_reflect.gif"></div>
		<!-- LIGHTBOX -->
	<div id="overlay"></div>
	<div id="lightbox" style="top: 50%; height: 450px; overflow: auto;"></div> <!-- make this %80 of window height -->
	<!-- END LIGHTBOX -->
	
	<h1>Glossary Terms</h1>
	<h3>Listing of All Glossary Terms</h3>
	<div id="slate">
		<div id="filterTerms">
		<form id="form1" name="form1" method="post" action="">
		  <label>Filter Glossary 
		  <input type="text" id="txtSearch" name="txtSearch" style="width:120px; padding-left: 1px; padding-right: 20px; margin-right:5px; background: url('/images/search_light.gif') no-repeat right; background-color: #FFFFFF; color: #999;" class="txtSearch" value="Search Terms" onfocus="this.value = ( this.value == this.defaultValue ) ? '' : this.value;return true;" onkeyup="getTerms($('txtSearch').value, 'name');"><div id="txtSearchIndicator" style="visibility:hidden; position: absolute; right:0; margin-right: 150px;"><img src="/images/indicator.gif"></div>
		  </label>
		  <div id="clearSearch" style="display: none;"></div>
		</form>
		<p><a href="javascript:proposeTermCont();">Propose a Glossary Term</a></p>
	</div>
	  <div id="list"></div>
	</div>
	
	<div id="footer" style="clear:both;">
	footer
	</div>
</div>

<div id="proposeForm" style="display:none;">
	<h2>Propose a New Term</h2>
	<form id="proposeForm" name="proposeForm" method="post" action="" style="padding-top: 20px;">
  <label>Glossary Term Name <br />
  <input type="text" name="termName" id="termName" style="width: 75%" />
  </label>
  <p>
    <label>Short Definition <br />
    <input name="shortDef" id="shortDef" type="text" value="" style="width: 100%" />
    </label>
  </p>
  <p>
    <label>Extended Definition (Optional - leave blank if not available)<br />
    <textarea name="extDef" id="extDef" style="width: 100%"></textarea>
    </label>
  </p>
 
  <label>Sources (at least 1 is required)</label><br />
  	1. <input name="source1" type="text" id="source1" value="http://" style="width: 95%" /><br />
  	2. <input name="source2" type="text" id="source2" value="http://" style="width: 95%" /><br />
  	3. <input name="source3" type="text" id="source3" value="http://" style="width: 95%" />
  
  
  <label>Term Links (optional)</label><br />
  	1. <input name="termlink1" type="text" id="termlink1" value="http://" style="width: 95%" /><br />
  	2. <input name="termlink2" type="text" id="termlink2" value="http://" style="width: 95%" /><br />
  	3. <input name="termlink3" type="text" id="termlink3" value="http://" style="width: 95%" />
  
	<p><input type="button" value="Cancel" onClick="lightboxDisplay('none')"><input type="button" value="propose term" onClick="proposeTermPrep($('termName').value,$('shortDef').value,$('extDef').value, $('source1').value, $('source2').value, $('source3').value,$('termlink1').value,$('termlink2').value,$('termlink3').value);"></p>
  </form>
</div>
  

</body>
</html>
