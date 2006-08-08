<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Glossary Public View</title>


<!-- Site Wide CSS -->
<style type="text/css" media="screen">@import "styles/position.css";</style>
<style type="text/css" media="screen">@import "styles/styles.css";</style>
<style type="text/css" media="screen">@import "styles/tabs.css";</style>
<style type="text/css" media="screen">@import "styles/headertabs.css";</style>
<!-- Temporary Borders used for testing <style type="text/css" media="screen">@import "styles/tempborders.css";</style>-->
<!-- End Site Wide CSS -->


<!-- Site Wide JavaScript -->
<script src="scripts/headercookies.js" type="text/javascript"></script>
<script src="scripts/headertabs.js" type="text/javascript"></script>
<script src="scripts/tabcookies.js" type="text/javascript"></script>
<script src="scripts/tabs.js" type="text/javascript"></script>
<script src="scripts/tags.js" type="text/javascript"></script>
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<script src="scripts/findxy.js" type="text/javascript"></script>
<script src="scripts/search.js" type="text/javascript"></script>
<!-- End Site Wide JavaScript -->

<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type='text/javascript' src='/dwr/interface/GlossaryPublicAgent.js'></script>

<script type="text/javascript">

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
		
		
		function proposeTermCont(){
			lightboxDisplay(true);
			os = '';
			os = '<span class="closeBox"><a href="javascript: lightboxDisplay();"><img src="/images/closelabel.gif" border="0"></a></span>'
			os += $('proposeForm').innerHTML;
			$('lightbox').innerHTML = os;
			

			
		}
		
		function proposeTermPrep(name, shortDef, fullDef, source1, source2, source3, termlink1, termlink2, termlink3){		
			//validate sources and termlinks
			if(name == '' || shortDef == '' || fullDef == ''){
				alert('Please make sure all required fields are filled out');
			}else{
			sources = new Array([source1,'link'],[source2,'link'],[source3, 'link']);
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
			
			//convert sourceList and termList arrays into a 2D array.
			
			
				GlossaryPublicAgent.proposeTerm({name:name, shortDef:shortDef, fullDef:fullDef}, links, sources, {
				callback:function(data){

					if (data.successful){ 
							$('proposeForm').innerHTML = '<h3>Thank you for your term proposal!</h3><br><p>Your term has been submitted to the moderator for approval.  You will be notified after moderator review.</p><p><a href="javascript: lightboxDisplay();">Close Box</a></p>';
							//clear Array
							sourceList = "";
					}else{
						  alert(data.reason);
					}
				},
				errorHandler:function(errorString, exception){ 
						//showTheError();
				}
			});
		}


		function addSourceToList(author, title, url){
				var myVar = 'author' + author + ' title:' + title + 'url' + url;
				a = new Array();
				for (var i = 0; i < a.length; i++) {
				   a[i] = new Array();
				   for (var j = 0; j < a.length; j++) {
				      a[i][j] = "[" + i + "," + j + "]";
				   }
				}
				for (var i = 0; i < a.length; i++) {
				   str = "source " + i + ":";
				   for (var j = 0; j < 4; j++) {
				      str += a[i][j];
				   }
				   myVar += str + "; ";
				}
				myVar += ['source1', 'link1'], ['source2', 'link2'];
			alert(myVar);
		}
		sourceList = new Array();
		termList = new Array();
		function addItemToList(list, type, value1, value2){
			if (list == 'sourceList'){
				if (type == 'book'){
					asource = value2 + ' by ' + value1;
				}else{
					asource = value1;
				}
				
			
				sourceList.push(asource);
				$('sourceList').innerHTML = "";
				for (var i=0; i<sourceList.length; i++)
				{
					$('sourceList').innerHTML += '<li id="source'+ i +'">'+ sourceList[i] +'</li>';
				}
				$('txtauthor').value = $('txtauthor').defaultValue;
				$('txttitle').value = $('txttitle').defaultValue;
				$('txturl').value = $('txturl').defaultValue;
				Effect.BlindUp('addSourceFormCont');
			}else{
				aterm = value1;
				termList.push(aterm);
				$('termList').innerHTML = "";
				for (var i=0; i<termList.length; i++)
				{
					$('termList').innerHTML += '<li id="term'+ i +'">'+ termList[i] +'</li>';
				}
				$('txttermurl').value = $('txttermurl').defaultValue;
			  Effect.BlindUp('addTermsFormCont');
			}	
		}
		

</script>

<style>
#slate ul{display:inline; margin:0; padding:0}
#slate li{list-style: none; display:inline; padding: 3px;}
tr:hover {background-color: #F1F7FF;}


</style>
</head>
<body>
	<!-- Header -->
<div id="header">

</div>
<div id="login"><a href="/logout.do"><img src="images/btn_logout.gif" border="0"/></a></div>
<!-- End Header -->
<div id="loading-indicator">Loading... <img src="/images/indicator_arrows.gif"></div>
<div id="container">
	<!-- START LIGHTBOX -->

	<div id="overlay" style="display: none;></div>
	<div id="lightbox" class="blueBB" style="top: 50%; height: 450px; overflow: auto;"></div>
	<!-- END LIGHTBOX -->
	<!--START Title Header -->
	
	<div id="headerbar">
	<!-- Search -->
	  <form id="mysearch" name="form1" method="post" action="">
	    
	<div id="searchbox">
		<input name="search" type="text" class="search" value="Search" onfocus="this.value = ( this.value == this.defaultValue ) ? '' : this.value;return true;" />
	</div>
	<div id="submit">
	        <img src="/images/btn_search_1.png" name="Image1" width="19" height="19" border="0" id="Image1" onClick="sendForm();return false;" onMouseDown="MM_swapImage('Image1','','/images/btn_search_3.png',1)" onMouseOver="MM_swapImage('Image1','','/images/btn_search_2.png',1)" onMouseOut="MM_swapImgRestore()">    
	</div>
	<div id="searchresults"></div>
	  </form>
	<!-- End Search -->
	<a id="TitleHeader" name="TitleHeader"></a>
	
		<div class="header" id="myHeader">
			<div id="header_currentMenuContainer" class="headertab">
		    	<h2>Home</h2>
					<div id="header_submenu1" class="submenulinks">
						<a href="#">Sub Navigation 1a</a> <a href="#">Sub Navigation 2a</a> <a href="#">Sub Navigation 3a</a> <a href="#">Sub Navigation 4a</a>
					</div>
		    </div>			
				<div id="sidebar_tab2" class="headertab">
		    	<h2>Current Task</h2>
					<div id="header_submenu2" class="submenulinks">
						<a href="#">Sub Navigation 1b</a> <a href="#">Sub Navigation 2b</a> <a href="#">Sub Navigation 3b</a> <a href="#">Sub Navigation 4b</a> 
					</div>
		    </div>	    
		    <div id="sidebar_tab3" class="headertab">
		    	<h2>Learn More</h2>
					<div id="header_submenu3" class="submenulinks">
						<a href="#">Sub Navigation 1c</a> <a href="#">Sub Navigation 2c</a> <a href="#">Sub Navigation 3c</a> <a href="#">Sub Navigation 4c</a> 
					</div>
		    </div>
		</div>
	</div>
	<!--END Title Header -->
	
	<!-- Sub Title -->
	<div id="subheader">
	<h1>Learn More: </h1> <h2>Listing of All Glossary Terms</h2>
	</div>
	<div id="footprints">
	<p>Learn More >> Glossary</p>
	</div>
	<!-- End Sub Title -->
	
	<!-- Overview SpiffyBox -->
	<div class="cssbox">
	<div class="cssbox_head">
	<h3>Overview and Instructions</h3>
	</div>
	<div class="cssbox_body">
		<p>[...]</p>
	</div>
	</div>
	<!-- End Overview -->

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
	

</div>
<!-- Start Footer -->
<div id="footer_clouds">

	<div id="footer_text">
	<a href="http://www.pgist.org"><img src="/images/footerlogo.png" alt="PGIST Logo" width="156" height="51" class="imgright" border="0"/></a><br />This research is funded by National Science Foundation, Division of Experimental and Integrative Activities, Information Technology Research (ITR) Program, Project Number EIA 0325916, funds managed within the Digital Government Program.    </div>

</div>
<!-- End Footer -->
<div id="proposeForm" style="display:none;">
			<h2>Propose a New Term</h2>
			<form id="proposeForm" name="proposeForm" method="post" action="" style="padding-top: 20px;">
			  <label>Glossary Term Name <br /><input type="text" name="termName" id="termName" style="width: 75%" /></label>
			  <p><label>Short Definition <br /><input name="shortDef" id="shortDef" type="text" value="" style="width: 100%" /></label></p>
			  <p><label>Extended Definition (Optional - leave blank if not available)<br />
			    <textarea name="extDef" id="extDef" style="width: 100%"></textarea>
			    </label>
			  </p>
			  
			  
			 	<div id="section_sources">
			  	<label>Sources (at least 1 is required)</label><br />
			 		<div id="sourceList">No sources have been created yet.</div><br>
			  	<div id="addSourceFormCont" style="display:none; padding: 5px; width: 100%; background-color: #ddd; margin-bottom: 5px;">
				  		<h4>Add a Source</h4>
				  		<div id="addBookForm"><form><input name="author" type="text" id="txtauthor" value="Author" style="width: 30%" onfocus="this.value = ( this.value == this.defaultValue ) ? '' : this.value;return true;"/>&nbsp;&nbsp;<input name="author" type="text" id="txttitle" value="Title" style="width: 60%" onfocus="this.value = ( this.value == this.defaultValue ) ? '' : this.value;return true;"/><br></form></div>
				  		<div id="addURLForm"><form><input name="url" type="text" id="txturl" value="http://" style="width: 95%" onfocus="this.value = ( this.value == this.defaultValue ) ? '' : this.value;return true;"/><br><a href="javascript: addSourceToList($('txtauthor').value,$('txttitle').value, $('txturl').value);">Add this Source</a></form></div>
			  	</div>
			  	<a href="javascript: Effect.toggle('addSourceFormCont', 'blind'); void(0);">Add a Source</a>
			  
				
			  <br />
			  <div id="section_termlinks">
			  <label>Term Links (optional)</label><br />
			 	<div id="termList">No term links have been created yet.</div><br>
			  <div id="addTermsFormCont" style="display:none; padding: 5px; width: 100%; background-color: #ddd; margin-bottom: 5px;">
				  <div id="addURLForm"><input name="url" type="text" id="txttermurl" value="http://" style="width: 95%" onfocus="this.value = ( this.value == this.defaultValue ) ? '' : this.value;return true;"/><br><a href="javascript: addItemToList('termList','url', $('txttermurl').value,'');">Add this URL Source</a></div>
			  </div>
			  <a href="javascript: Effect.toggle('addTermsFormCont', 'blind'); void(0);">Add a Term Link</a>
			  <br>
				</div>
				<p><input type="button" value="Cancel" onClick="lightboxDisplay()"><input type="button" value="propose term" onClick="proposeTermPrep($('termName').value,$('shortDef').value,$('extDef').value, $('source1').value, $('source2').value, $('source3').value,$('termlink1').value,$('termlink2').value,$('termlink3').value);"></p>   
		  
		  </form>
</div>
  

<!-- Run javascript function after most of the page is loaded, work around for onLoad functions quirks with tabs.js -->
<script type="text/javascript">
	doOnLoad();
	
</script>
</body>
</html>
