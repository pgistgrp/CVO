<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Let's Improve Transportation: Glossary</title>
<!-- Site Wide CSS -->
<style type="text/css" media="screen">
	@import "styles/lit.css";
	@import "styles/glossary.css";
</style>
<!-- Temporary Borders used for testing <style type="text/css" media="screen">@import "styles/tempborders.css";</style>-->
<!-- End Site Wide CSS -->
<!-- Site Wide JavaScript -->
<script src="scripts/tabs.js" type="text/javascript"></script>
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
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
								$('clearSearch').innerHTML = '<input type="button" onclick="javascript:clearResults(\''+ sortby +'\');" value="Clear search">';
							}
				}
				
				GlossaryPublicAgent.getTerms({filter:term, sort:sortby, direction:direction}, {
				callback:function(data){
					if (data.successful){ 	
							$('list').innerHTML = "";
							$('list').innerHTML += data.html;
							//$('list').innerHTML = data.html;
							setSort(sortby, term);
							direction = "asc"; //reset direction
						$('loading-indicator').style.display = "none";
					}else{
						alert("reason for getTerms failure: "+data.reason);
						$('loading-indicator').style.display = "none";
					}
					
				},
				errorHandler:function(errorString, exception){ 
				alert("getTerms: "+errorString+" "+exception);
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

					else{
						alert("reason for getTermObject failure: "+data.reason);
					}
				},
				errorHandler:function(errorString, exception){ 
						alert("getTermObject: "+errorString+" "+exception);
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
			globalSourceLinks=new Array();
			globalTermLinks = new Array();
			$('proposeTermResult').style.display="";
			$('proposeTermBox').style.display="";
			location.hash='proposeTermBox';
			

			
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
							//$('proposeForm').innerHTML = '<h3>Thank you for your term proposal!</h3><br><p>Your term has been submitted to the moderator for approval.  You will be notified after moderator review.</p><p><a href="javascript: lightboxDisplay();">Close Box</a></p>';
							//clear Array
							//sourceList = "";
							$('proposeTermResult').innerHTML='<h3>Thank you for your term proposal!</h3><br><p>Your term has been submitted to the moderator for approval.  You will be notified after moderator review.<p><button type="button" onclick="$(\'proposeTermResult\').style.display=\'none\';">Close</button>';
							$('proposeTermResult').style.display='block';
					}else{
						  alert("reason for proposeTerm failure: "+data.reason);
					}
				},
				errorHandler:function(errorString, exception){ 
						alert("proposeTerm: "+errorString+" "+exception);
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
			var agt=navigator.userAgent.toLowerCase();
function sz(t) {
a = t.value.split('\n');
b=1;
for (x=0;x < a.length; x++) {
 if (a[x].length >= t.cols) b+= Math.floor(a[x].length/t.cols);
 }
b+= a.length;
if (b > t.rows && agt.indexOf('opera') == -1) t.rows = b;
}

 		var globalSourceLinks=new Array();
		var globalTermLinks = new Array();
		
		
function deleteSourceLink(arrid,tid){
			globalSourceLinks.splice(arrid,1);
			generateSourceLinks(tid);
		}
		
		
		function generateSourceLinks(tid){
			var gsl="";
			for (var sa=0; sa<globalSourceLinks.length; sa++){
					gsl+="<a href='"+globalSourceLinks[sa][1]+"'>"+globalSourceLinks[sa][0]+"</a><br /><br /><button type='button' onclick='deleteSourceLink("+sa+","+tid+"); return false;'>Delete Source</button><br /><br />";
					
				}
		
			$('sourcelinks'+tid).innerHTML=gsl;
		}
		
		function deleteTermLink(arrid,tid){
			globalTermLinks.splice(arrid,1);
			generateTermLinks(tid);
		
		
		}
		
		function generateTermLinks(tid){
			var gtl="";
			for (var tl=0; tl<globalTermLinks.length; tl++){
					
					gtl+="<a href='"+globalTermLinks[tl]+"'>"+globalTermLinks[tl]+"</a>&nbsp;&nbsp; <button type='button' onclick='deleteTermLink("+tl+","+tid+"); return false;'>Delete Term Link</button><br />";
				}
			$('termlinks'+tid).innerHTML=gtl;
		
		}
		
		function addSourceLink(tid){
			var cit=$('propaddsourcecitation'+tid).value;
			var url=$('propaddsource'+tid).value;
			globalSourceLinks.push([cit,url]);
			$('propaddsourcecitation'+tid).value="Citation";
			$('propaddsource'+tid).value="Http://";
			generateSourceLinks(tid);
		}
		
		function addTermLink(tid){
			var url=$('propaddtermlink'+tid).value;
			globalTermLinks.push(url);
			$('propaddtermlink'+tid).value="Http://";
			generateTermLinks(tid);
		
		}

function resetFilter(){
	$('txtSearch').value = '';
	getTerms(this.value, 'name');
}

</script>
<style>
#slate ul{display:inline; margin:0; padding:0}
#slate li{list-style: none; display:inline; padding: 3px;}
tr:hover {background-color: #F1F7FF;}
h3{
text-transform:none;
}


</style>
<event:pageunload />
</head>
<body>
<!-- Begin the header - loaded from a separate file -->
<div id="header">
	<!-- Begin header -->
	<jsp:include page="/header.jsp" />
	<!-- End header -->
</div>
<!-- End header -->
<!-- START LIGHTBOX -->
<div id="overlay" style="display: none;></div>
	<div id="lightbox" class="blueBB" style="top: 50%; height: 450px; overflow: auto;">
</div>
<!-- END LIGHTBOX -->
<!-- Begin header menu - The wide ribbon underneath the logo -->
<div id="headerMenu">
	<div id="headerContainer">
		<div id="headerTitle" class="floatLeft">
			<h3 class="headerColor">Learn More</h3>
		</div>
		<div class="floatLeft headerButton"> <a href="lmMenu.do">Menu</a> </div>
		<div class="floatLeft headerButton"> <a href="lmAbout.do">About LIT</a> </div>
		<div class="floatLeft headerButton"> <a href="lmFaq.do">FAQ</a> </div>
		<div class="floatLeft headerButton"> <a href="lmTutorial1.do">Tutorial</a> </div>
		<div class="floatLeft headerButton"> <a href="lmGallery.do">Project Gallery</a> </div>
		<div class="floatLeft headerButton currentBox"> <a href="glossaryPublic.do">Glossary</a> </div>
		<div class="floatLeft headerButton"> <a href="lmResources.do">More Resources</a> </div>
	</div>
</div>
<!-- End header menu -->
<!-- #container is the container that wraps around all the main page content -->
<div id="loading-indicator">Loading... <img src="/images/indicator_arrows.gif"></div>
<div id="container">
	<!-- begin "overview and instructions" area -->
	<div id="overview" class="box2">
		<h3>Overview and Instructions</h3>
		<p>This glossary contains some suggested definitions for terms you may encounter
			when discussing transportation issues. To search for a term, begin typing in the "Filter
			Glossary" box and the glossary will be searched as you type. You can also browse
			alphabetically. Click on the name of a given term to get more information about
			it. If you disagree with the definition of a term, you can flag it for the moderator,
			or leave a comment about it on the page for that term.</p>
	</div>
	<!-- end overview -->
	<div id="slate">
		<div id="filterTerms">
			<form id="form1" name="form1" method="post" action="javascript:getTerms($('txtSearch').value, 'name');">
				<label>
				Search Glossary
				<input type="text" id="txtSearch" name="txtSearch" style="background:url('/images/search_light.gif') no-repeat right;background:#fff;" class="txtSearch" value="Search Terms" onfocus="this.value = ( this.value == this.defaultValue ) ? '' : this.value;return true;" onkeyup="if (this.value.length >= 3){getTerms(this.value, 'name');}">
				<div id="txtSearchIndicator" style="visibility:hidden; position: absolute; right:0; margin-right: 150px;"><img src="/images/indicator.gif"></div>
				</label>
				<div id="clearSearch" style="display: none;"></div>
			</form>
		</div>
		<br class="clearBoth" />
		<p><a href="javascript:proposeTermCont();">Propose a Glossary Term</a></p>
		<center>
			<p>Found a bug? Problem accessing a part of the page? <a href="#feedbackForm" onclick="javascript:Effect.toggle('feedbackForm','blind'); setTimeout('location.hash=\'#feedbackDiv\';',900);">Send
					us feedback.</a></p>
		</center>
		<div id='proposeTermResult' style='display:none;'>
			<div id='proposeTermBox' style='display:none;border:thick solid #C0C0C0;'>
				<div>
					<table style='width:100%; height:100%;' rules='all'>
						<tbody>
							<tr>
								<td cellspacing=10 style=''>
									<div style='margin:2%;'>
										<label><strong>Term Name:</strong></label>
										<br />
										<input style='width:50%;' id='proptermname-1' type='text' value=''/>
										<br />
										<label><strong>Short Definition</strong></label>
										<br />
										<textarea style='width:90%; height:100%;' rows=3 cols=40 onclick='sz(this);' onkeyup='sz(this);' id='proptermshortdef-1'></textarea>
									</div>
								</td>
								<td rowspan=2>
									<div style='margin:2%;'>
										<label><strong>Sources</strong></label>
										<br />
										<div id='sourcelinks-1'> </div>
										<br />
										<label><strong>Add Source</strong></label>
										<br />
										<textarea style='width:90%;' rows=3 cols=40 onclick='sz(this);' onkeyup='sz(this);' id='propaddsourcecitation-1'>Citation</textarea>
										<br />
										<input style='width:50%;' id='propaddsource-1' type='text' value='Http://'/>
										<br />
										<button type='button' onclick='addSourceLink(-1); return false;'>Add Source</button>
										<br />
										<br />
										<label><strong>Term Links</strong></label>
										<br />
										<div id='termlinks-1'> </div>
										<br />
										<label>Add Link</label>
										<br />
										<input style='width:50%;' id='propaddtermlink-1' type='text' value='Http://'/>
										<br />
										<button type='button' onclick='addTermLink(-1); return false;'>Add Link</button>
									</div>
								</td>
							</tr>
							<tr style=''>
								<td style=''>
									<div style='min-height:100%; margin:2%;'>
										<label><strong>Extended Definition</strong></label>
										<br />
										<textarea rows=3 cols=40 onclick='sz(this);' onkeyup='sz(this);' style='width:90%; height:100%;' id='proptermextdef-1'></textarea>
									</div>
								</td>
							</tr>
							<tr style=''>
								<td style='width:50%;'></td>
								<td style=''>
									<div style='margin:0.5%; float:right;'>
										<button type='button' onclick="javascript:$('proposeTermBox').style.display='none'; return false;">Cancel</button>
										 
										<button type='button' onclick='proposeTerm($("proptermname-1").value, $("proptermshortdef-1").value, $("proptermextdef-1").value); $("proptermname-1").value=""; $("proptermshortdef-1").value=""; $("proptermextdef-1").value=""; $("sourcelinks-1").innerHTML=""; $("termlinks-1").innerHTML=""; $("proposeTermBox").style.display="none"; return false;'>Save
										and Close</button>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<br class="clearBoth" />
		<div id="list"></div>
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
	<div id="section_sources">
	<label>Sources (at least 1 is required)</label>
	<br />
	<div id="sourceList">No sources have been created yet.</div>
	<br>
	<div id="addSourceFormCont" style="display:none; padding: 5px; width: 100%; background-color: #ddd; margin-bottom: 5px;">
	<h4>Add a Source</h4>
	<div id="addBookForm">
	<form>
		<input name="author" type="text" id="txtauthor" value="Author" style="width: 30%" onfocus="this.value = ( this.value == this.defaultValue ) ? '' : this.value;return true;"/>
		&nbsp;&nbsp;
		<input name="author" type="text" id="txttitle" value="Title" style="width: 60%" onfocus="this.value = ( this.value == this.defaultValue ) ? '' : this.value;return true;"/>
		<br>
	</form>
</div>
<div id="addURLForm">
	<form>
		<input name="url" type="text" id="txturl" value="http://" style="width: 95%" onfocus="this.value = ( this.value == this.defaultValue ) ? '' : this.value;return true;"/>
		<br>
		<a href="javascript: addSourceToList($('txtauthor').value,$('txttitle').value, $('txturl').value);">Add
		this Source</a>
	</form>
</div>
</div>
<a href="javascript: Effect.toggle('addSourceFormCont', 'blind'); void(0);">Add a
Source</a> <br />
<div id="section_termlinks">
	<label>Term Links (optional)</label>
	<br />
	<div id="termList">No term links have been created yet.</div>
	<br>
	<div id="addTermsFormCont" style="display:none; padding: 5px; width: 100%; background-color: #ddd; margin-bottom: 5px;">
		<div id="addURLForm">
			<input name="url" type="text" id="txttermurl" value="http://" style="width: 95%" onfocus="this.value = ( this.value == this.defaultValue ) ? '' : this.value;return true;"/>
			<br>
			<a href="javascript: addItemToList('termList','url', $('txttermurl').value,'');">Add
			this URL Source</a></div>
	</div>
	<a href="javascript: Effect.toggle('addTermsFormCont', 'blind'); void(0);">Add a
	Term Link</a> <br>
</div>
<p>
	<input type="button" value="Cancel" onClick="lightboxDisplay()">
	<input type="button" value="propose term" onClick="proposeTermPrep($('termName').value,$('shortDef').value,$('extDef').value, $('source1').value, $('source2').value, $('source3').value,$('termlink1').value,$('termlink2').value,$('termlink3').value);">
</p>
</form>
</div>
</div>
<!-- end container -->
<!-- start feedback form -->
<pg:feedback id="feedbackDiv" action="cctView.do"/>
<!-- end feedback form -->
<!-- Begin header menu - The wide ribbon underneath the logo -->
<div id="headerMenu">
	<div id="headerContainer">
		<div id="headerTitle" class="floatLeft">
			<h3 class="headerColor">Learn More</h3>
		</div>
		<div class="floatLeft headerButton"> <a href="lmMenu.do">Menu</a> </div>
		<div class="floatLeft headerButton"> <a href="lmAbout.do">About LIT</a> </div>
		<div class="floatLeft headerButton"> <a href="lmFaq.do">FAQ</a> </div>
		<div class="floatLeft headerButton"> <a href="lmTutorial1.do">Tutorial</a> </div>
		<div class="floatLeft headerButton"> <a href="lmGallery.do">Project Gallery</a> </div>
		<div class="floatLeft headerButton currentBox"> <a href="glossaryPublic.do">Glossary</a> </div>
		<div class="floatLeft headerButton"> <a href="lmResources.do">More Resources</a> </div>
	</div>
</div>
<!-- Begin footer -->
<div id="footer">
	<jsp:include page="/footer.jsp" />
</div>
<!-- End footer -->
<script type="text/javascript">
	doOnLoad();
	
</script>
</body>
</html>
