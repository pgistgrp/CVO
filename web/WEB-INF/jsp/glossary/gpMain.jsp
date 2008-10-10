<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Glossary</title>
<style type="text/css" media="screen">
	@import "styles/lit.css";
	@import "styles/glossary.css";
</style>
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
							direction = "desc"; //reset direction
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
<!-- Start Global Headers  -->
	<wf:nav />
<!-- End Global Headers -->
<!-- Begin header menu - The wide ribbon underneath the logo -->
<div id="headerMenu">
	<div id="headerContainer">
		<div id="headerTitle" class="floatLeft">
			<h3 class="headerColor">Learn More</h3>
		</div>
		<div class="floatLeft headerButton"> <a href="lmMenu.do">Menu</a> </div>
		<div class="floatLeft headerButton"> <a href="lmAbout.do">About VCC</a> </div>
		<div class="floatLeft headerButton"> <a href="lmFaq.do">FAQ</a> </div>
		<div class="floatLeft headerButton"> <a href="lmTutorial1.do">Tutorial</a> </div>
		<div class="floatLeft headerButton"> <a href="lmGallery.do">Maps</a> </div>
		<div class="floatLeft headerButton currentBox"> <a href="glossaryPublic.do">Glossary</a> </div>
		<div class="floatLeft headerButton"> <a href="lmResources.do">More resources</a> </div>
	</div>
</div>
<!-- End header menu -->
<!-- #container is the container that wraps around all the main page content -->
<div id="loading-indicator">Loading... <img src="/images/indicator_arrows.gif"></div>
<div id="container">
	<!-- begin "overview and instructions" area -->
	<h2 class="headerColor">The "Glossary" section is not being supported. Please register or return "Home."</h2><br/>


<div id="headerMenu">
	<div id="headerContainer">
		<div id="headerTitle" class="floatLeft">
			<h3 class="headerColor">Learn More</h3>
		</div>
		<div class="floatLeft headerButton"> <a href="lmMenu.do">Menu</a> </div>
		<div class="floatLeft headerButton"> <a href="lmAbout.do">About VCC</a> </div>
		<div class="floatLeft headerButton"> <a href="lmFaq.do">FAQ</a> </div>
		<div class="floatLeft headerButton"> <a href="lmTutorial1.do">Tutorial</a> </div>
		<div class="floatLeft headerButton"> <a href="lmGallery.do">Maps</a> </div>
		<div class="floatLeft headerButton currentBox"> <a href="glossaryPublic.do">Glossary</a> </div>
		<div class="floatLeft headerButton"> <a href="lmResources.do">More Resources</a> </div>
	</div>
</div>

<div id="footer">
	<jsp:include page="/footer.jsp" />
</div>

<script type="text/javascript">
	doOnLoad();
	
</script>
</body>
</html>
