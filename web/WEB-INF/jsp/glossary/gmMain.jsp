<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Glossary Management View</title>


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
<script type='text/javascript' src='/dwr/interface/GlossaryManageAgent.js'></script>

<script type="text/javascript">

		var direction = "asc";
		
		function doOnLoad(){
			direction = "asc";
			getProposedTerms();
			getTerms("", "name");
			isTermsGotten();
			
		}
		
		var yscrollPos=0;
		var xscrollPos=0;
		function getScrollPos(){
			if (self.pageYOffset) {
				yscrollPos = self.pageYOffset;
				xscrollPos = self.pageXOffset;
			} else if (document.documentElement && document.documentElement.scrollTop){
				yscrollPos = document.documentElement.scrollTop; 
				xscrollPos = document.documentElement.scrollLeft;
			} else if (document.body) {
				yscrollPos = document.body.scrollTop;
				xscrollPos = document.body.scrollLeft;
			}
		}
		
		function setScrollPos(){
			window.scrollTo(xscrollPos,yscrollPos);
		}
		
		function deleteTerm(termid){
		GlossaryManageAgent.deleteTerm({id:termid},{
		callback:function(data){
			if (data.successful){
				new Effect.Fade('glossaryTerm' + termid);
			}else{
			alert(data.reason);
			}
		},
			errorHandler:function(errorString, exception){
			alert(errorString+" "+exception);
			}
		});
		}
		
		function getProposedTerms(){
		
			GlossaryManageAgent.getProposedTerms({},{
				callback:function(data){
					if (data.successful){
						$('proposedList').innerHTML="";
						$('proposedList').innerHTML+=data.html;
					}else{
						alert(data.reason);
					}
				},
				errorHandler:function(errorString, exception){
				alert(errorString+" "+exception);
				}
			
		
			});
		}
		var gotTerms=false;
		function getTerms(term,sortby){
		
				GlossaryManageAgent.getTerms({filter:term, sort:sortby, direction:direction}, {
				callback:function(data){
					if (data.successful){ 	
						$('list').innerHTML = "";
						$('list').innerHTML += data.html;
						setSort(sortby, term);
						direction = "asc"; //reset direction
						$('loading-indicator').style.display = "none";
						//initializes lightbox links. Placed here so generated links are also made active
						gotTerms=true;
					}else{
						alert(data.reason);
						$('loading-indicator').style.display = "none";
					}
					
				},
				errorHandler:function(errorString, exception){ 
				alert(errorString+" "+exception);
						/*showTheError();*/
				}
			});
		
		}
		var globalSourceLinks=new Array();
		var globalTermLinks = new Array();
		var globalCategoryLinks=new Array();
		
		
		var saveFinished=false;
		function saveEditedAttributes(tid){//tid, tname, tsd, ted, tlinks, tsources, tcats)
		
		/*if($('edaddtermlink'+tid).value!="Http://"){
			globalTermLinks.push($('edaddtermlink'+tid).value);
				
		}
		//alert("in saved edited attributes:"+globalTermLinks);
		
		if($('edaddsource'+tid).value!="Http://"){
		var tmpsource=new Array();
		tmpsource.push($('edaddsourcecitation'+tid).value);
		tmpsource.push($('edaddsource'+tid).value);
		globalSourceLinks.push(tmpsource);*/
		/*globalSourceLinks.push(['test citation', 'test url']);
		globalSourceLinks.push(['test citation', $('edaddsource').value]);
		}*/
			GlossaryManageAgent.saveTerm({id:tid, name:$('edtermname'+tid).value, shortDefinition:$('edtermshortdef'+tid).value, extDefinition:document.getElementById('edtermextdef'+tid).value}, [], globalTermLinks, globalSourceLinks, globalCategoryLinks,{
			callback:function(data){
				if (data.successful){
				saveFinished=true;
				}else{
					alert("Reason for failure: "+data.reason);
				}
			},
			errorHandler:function(errorString, exception){
			alert(errorString+" "+exception);
			}
			
		});
		
		
		
		}		
		
		
		
 function closeAllContentsExcept(id, className){
 
  var activeRecord = className + id;
  var activeRow= 'editrow'+id;
  var allRecords = document.getElementsByClassName(className);
  
  for(i = 0; i < allRecords.length; i++){
   if(allRecords[i].id == activeRecord){
  // document.getElementById(activeRow).style.visibility='visible';
   //document.getElementById(activeRecord).style.display='inline';
   //new Effect.toggle(activeRow,'blind',{duration: 0.4});
    new Effect.BlindDown(allRecords[i].id,{duration: 0.4});
   }else{
    if(allRecords[i].style.display != 'none'){
    new Effect.BlindUp(allRecords[i].id, {duration: 0.4});
	
    }
   }
  }
  //new Effect.toggle('quickPostContents${post.id}', 'blind', {duration: 0.3}); void(0);" 
 }
 
		function saveClose(tid){
			closeEdit(tid); 
			saveEditedAttributes(tid);
			isSaved();
			/*while(!saveFinished){
							
			}
			saveFinished=false;
			saveFinished=false;*/
		}
		
		function openEditBox(tid){
			changeEditBoxContent(tid);
			isContentChanged(tid);
		
		}
		
		var isContentChangedInterval;
		
		function isContentChanged(tid){
			isContentChangedInterval=setInterval('if(changedContent){changedContent=false; contIntervalCall(isContentChangedInterval,"openEditBox","'+tid+'");}',100);
		}
		
		var isSavedInterval;
		
		function isSaved(){
			isSavedInterval=setInterval('if(saveFinished){saveFinished=false; contIntervalCall(isSavedInterval,"saveTerm","");}',100);
		
		}
		
		var gotTermsInterval;
		
		function isTermsGotten(){
			gotTermsInterval=setInterval('if(gotTerms){gotTerms=false; contIntervalCall(gotTermsInterval,"getTerms","");}',100);
		
		}
		
		function contIntervalCall(a,type,tid){
			 if(type=='saveTerm'){
				clearInterval(a);
				getTerms("", "name");
				isTermsGotten();
			}else if(type=='getTerms'){
				clearInterval(a);
		
			}else if(type=='openEditBox'){
				clearInterval(a);
				//edit(tid);
				closeAllContentsExcept(tid,'editbox');
				
			}
		
		}
		
		function deleteSourceLink(arrid,tid){
			globalSourceLinks.splice(arrid,1);
			generateSourceLinks(tid);
		}
		
		
		function generateSourceLinks(tid){
			var gsl="";
			for (var sa=0; sa<globalSourceLinks.length; sa++){
					gsl+="<a href='"+globalSourceLinks[sa][1]+"'>"+globalSourceLinks[sa][0]+"</a><br /><br />[ <a href='#' onclick='deleteSourceLink("+sa+","+tid+"); return false;'>delete source</a> ]<br /><br />";
					
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
					
					gtl+="<a href='"+globalTermLinks[tl]+"'>"+globalTermLinks[tl]+"</a>&nbsp;&nbsp;[ <a href='#' onclick='deleteTermLink("+tl+","+tid+"); return false;'>delete term link</a> ]<br />";
				}
			$('termlinks'+tid).innerHTML=gtl;
		
		}
		
		function addSourceLink(tid){
			var cit=$('edaddsourcecitation'+tid).value;
			var url=$('edaddsource'+tid).value;
			globalSourceLinks.push([cit,url]);
			$('edaddsourcecitation'+tid).value="Citation";
			$('edaddsource'+tid).value="Http://";
			generateSourceLinks(tid);
		}
		
		function addTermLink(tid){
			var url=$('edaddtermlink'+tid).value;
			globalTermLinks.push(url);
			$('edaddtermlink'+tid).value="Http://";
			generateTermLinks(tid);
		
		}
		
		var changedContent=false;
		function changeEditBoxContent(termid){
			GlossaryManageAgent.getTerm({id:termid, type:"edit"}, {
			callback:function(data){
				if(data.successful){
				globalTermLinks=new Array();
				globalSourceLinks=new Array();
				var sourcearray=data.term.sources;
				var termlinkarray=data.term.links;
				
				var lboxhtml = $('editbox'+termid);
				
				var satml="";
				
				for (var sa=0; sa<sourcearray.length; sa++){
					satml+="<a href='"+sourcearray[sa].url+"'>"+sourcearray[sa].citation+"</a><br /><br />[ <a href='#' onclick='deleteSourceLink("+sa+","+termid+"); return false;'>delete source</a> ]<br /><br />";
					globalSourceLinks.push([sourcearray[sa].citation,sourcearray[sa].url]);
				}
				
				var tatml="";
				
				for (var tl=0; tl<termlinkarray.length; tl++){
					
					tatml+="<a href='"+termlinkarray[tl].link+"'>"+termlinkarray[tl].link+"</a>&nbsp;&nbsp;[ <a href='#' onclick='deleteTermLink("+tl+","+termid+"); return false;'>delete term link</a> ]<br />";
					globalTermLinks.push(termlinkarray[tl].link);
				}
				
				lboxhtml.innerHTML="<div><table rules='all'><tbody><tr><td><label>Term Name:</label><br /><input id='edtermname"+termid+"' type='text' value='"+data.term.name+"'/><br />"+
				"<label>Short Definition</label><br /><input id='edtermshortdef"+termid+"' type='text' value='"+data.term.shortDefinition+"'/></td>"+
				"<td rowspan=2><label>Sources</label><br /><div id='sourcelinks"+termid+"'>"+satml+
				"</div><br /><label>Add Source</label><br /><textarea id='edaddsourcecitation"+termid+"'>Citation</textarea><br /><input id='edaddsource"+termid+"' type='text' value='Http:"+'//'+"'/>"+
				"<br /><a href='#' onclick='addSourceLink("+termid+"); return false;'>Add Source</a><label><br /><br />Term Links</label><br /><div id='termlinks"+termid+"'>"+tatml+
				"</div><br /><label>Add Link</label><br /><input id='edaddtermlink"+termid+"' type='text' value='Http:"+'//'+"'/><br /><a href='#' onclick='addTermLink("+termid+"); return false;'>Add Link</a></td></tr><tr><td><label>Extended Definition</label><br />"+
				"<textarea id='edtermextdef"+termid+"'>"+data.term.extDefinition+"</textarea></td></tr><tr><td colspan=2><a href='#' onclick='closeEdit("+termid+"); return false;'>Cancel</a>&nbsp;<a href='#' onclick='saveClose("+termid+"); return false;'>Save and Close</a></td></tr></tbody></table></div>";
				//lboxhtml.innerHTML="<table cellspacing=10 rules='all'><tr><td><label>Term Name:</label><br /><input type='text' /><br /><label>Short Definition:</label><br /><input type='text'/></td><td rowspan=2><ol><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li><li>something</li></ol></td></tr><tr><td><label>Extended Definition</label><br /><textarea style=''>something</textarea></td></tr><tr><td colspan=2 ><a href='#'>Save and Close</a></td></tr></table>";
					/*lboxhtml.innerHTML="<h3>Editing Attributes for Glossary Term: "+data.term.name+"</h3>"+
"<div><label>Glossary Term</label><br /><input id='edtermname"+termid+"' type='text' value='"+data.term.name+"'/></div>"+
"<br /><div><label>Short Definition</label><br /><input id='edtermshortdef"+termid+"' type='text' value='"+data.term.shortDefinition+"'/>"+
"</div><br /><div><label>Full Definition (optional - leave blank if none exists)</label>"+
"<br /><textarea id='edtermextdef"+termid+"' rows=5 style='width:75%'>"+data.term.extDefinition+"</textarea></div><br /><div>"+
"<label>Sources</label>";
var satml="";
for (var sa=0; sa<sourcearray.length; sa++){
satml+="<a href='"+sourcearray[sa].url+"'>"+sourcearray[sa].citation+"</a><br />";
globalSourceLinks.push([sourcearray[sa].citation,sourcearray[sa].url]);

}
lboxhtml.innerHTML+=satml;
lboxhtml.innerHTML+="<br /><label>Add Source:</label>&nbsp;<input id='edaddsource"+termid+"' type='text' value='Http:"+'//'+"'/></div><br /><br />"+
"<div><label>Term Links</label><br />";

for (var tl=0; tl<termlinkarray.length; tl++){
globalTermLinks.push(termlinkarray[tl].link);
lboxhtml.innerHTML+="<a href='"+termlinkarray[tl].link+"'>"+termlinkarray[tl].link+"</a><br />";


}
lboxhtml.innerHTML+="<label>Add Link:</label>&nbsp;<input id='edaddtermlink"+termid+"' type='text' value='Http:"+'//'+"'/></div><br /><br />";


//add participant comments
lboxhtml.innerHTML+="<div style='float:right;'><a href='#' onclick='closeEdit("+termid+");'>Cancel</a>&nbsp;<a onclick='saveClose("+termid+");' href='#' style='background-color:#CCFF99;'>Save and Close</a></div>";
//if(data.term.name!=$('edtermname').value && data.term.shortDefinition!=$('edtermshortdef').value && data.term.extDefinition!=$('edtermextdef').value......closeEdit("+termid+"); saveEditedAttributes("+termid+"); getTerms();
		*/changedContent=true;
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){
			alert(errorString+" "+exception);
			}
			
			
		});
		
	}		
		function setSort(thisSort, term){
			headings = document.getElementsByTagName("th"); 
			for (var i = 0; i < headings.length; i++) { 
				if (headings[i].id != 'def' && headings[i].id!='actions'){
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
				isTermsGotten();
		}
</script>

<!--Lightbox script-->
<!--
<script type="text/javascript" src="scripts/prototype.js"></script>
<script type="text/javascript" src="scripts/lightbox.js"></script>-->


<!--Lightbox style-->
<!--
<style type="text/css" />

.leightpadding{

padding:0em 1em 1em 1em;


}

.leightcontainer{
	display: none;
	position: absolute;
	top: 15%;
	left: 15%;
	width: 70%;
	height: 70%;
	background-color: white;
	text-align: left;
	z-index:1002;
	overflow:hidden;	

}

.leightbox {
	color: #333;
	display: none;
	position:absolute;
	top:30px;
	height:90%;
	width:100%;
	background-color: white;
	text-align: left;
	z-index:1002;
	overflow:auto;	
}

.leightbar{
display:none;
position:absolute;
top:0%;
height:30px;
width:100%;
background-color:#0066FF;
z-index:1002;
overflow:hidden;
}

#overlay{
	display:none;
	position:absolute;
	top:0;
	left:0;
	width:100%;
	height:100%;
	z-index:1000;
	background-color:#333;
	-moz-opacity: 0.8;
	opacity:.80;
	filter: alpha(opacity=80);
}

img{
border:0;

}

.lbclose{
float:right;

}



.lightbox[id]{ /* IE6 and below Can't See This */    position:fixed;    }#overlay[id]{ /* IE6 and below Can't See This */    position:fixed;    }



</style>
-->

<style type='text/css'>
#overlay{
	display:none;
	position:absolute;
	top:0;
	left:0;
	width:100%;
	height:100%;
	z-index:1000;
	background-color:#333;
	-moz-opacity: 0.8;
	opacity:.80;
	filter: alpha(opacity=80);
}



</style>
<script type='text/javascript'>
function edit(boxid){
var editid='editbox'+boxid;
var editrowid='editrow'+boxid;

var editrow=document.getElementById(editrowid);
var editbox=document.getElementById(editid);





editbox.style.display='block';
editbox.style.backgroundColor='white';

$('overlay').style.display='inline';

editrow.style.visibility='visible';
new Effect.toggle(editid,'blind');

//new Effect.toggle(editrowid,'blind');

//document.getElementsByClassName

}
function closeEdit(boxid){
var editid='editbox'+boxid;
var editrowid='editrow'+boxid;
var editrow=document.getElementById(editrowid);
var editbox=document.getElementById(editid);
//editbox.style.display='none';
new Effect.toggle(editbox,'blind');
//editrow.style.visibility='hidden';
$('overlay').style.display='none';
}
</script>

</head>
<body>




	
	
	<!-- Header -->

<div id="login"><a href="http://69.91.143.23:8080/logout.do"><img src="glossaryTermManagement_files/btn_logout.gif" border="0"></a></div>
<!-- End Header -->
<div style="display: inline;" id="loading-indicator">Loading... <img src="glossaryTermManagement_files/indicator_arrows.gif"></div>
<div id="container">

<div id="overlay"></div>
	<!-- START LIGHTBOX -->

	<!--<div id="overlay" lightbox="" class="blueBB" style="display: none;"></div>-->
	<!-- END LIGHTBOX -->
	<!--START Title Header -->
	
	<div id="headerbar">
	<!-- Search -->
	  <form id="mysearch" name="form1" method="post" action="">
	    
	<div id="searchbox">
		<input name="search" class="search" value="Search" type="text">
	</div>
	<div id="submit">
	        <img src="glossaryTermManagement_files/searchinactivesm.gif" onmouseover="javascript:this.src='glossaryTermManagement_files/searchactivesm.gif'" onmouseout="javascript:this.src='glossaryTermManagement_files/searchinactivesm.gif'" name="Image1" id="Image1" border="0" height="19" width="19">    
	</div>
	<div id="searchresults"></div>
	  </form>
	<!-- End Search -->
	<a id="TitleHeader" name="TitleHeader"></a>
	
  </div>
	<!--END Title Header -->
	<!---->
	<h1>Databases: Glossary Terms</h1>
	<div>
	<h2>Overview and Instructions</h2>
	<div><p>....Instructions go here...</p>
	</div>
	</div>
	
	<!--Proposed Glossary Terms-->
	
	<div id="proposedList">
	<h3>Proposed Glossary Terms by Participants - Waiting for Moderator Approval</h3>
	<table frame="box" rules="all">
	<tbody>
	<tr><th style="text-align: center;" width="89">Term&nbsp;&nbsp;</th><th style="text-align: left;" width="514">Short Definition&nbsp;&nbsp;</th><th style="text-align: center;" width="146">Proposed By:</th>
	</tr><tr>
	<td style="text-align: center;"><a href="">Collision</a></td>
	<td>Physical impact between two or more ships or vessels used for navigation.</td>
	<td style="text-align: center;">NoSUV</td>
	</tr>
	<tr>
	<td style="text-align: center;"><a href="">Safety</a></td>
	<td>The condition of being protected against failure, damage, error, accidents, or harm.</td>
	<td style="text-align: center;">NoSUV</td>
	</tr>
	</tbody>
	</table>
	</div>
	
	<!-- Sub Title -->
	<div id="subheader">
	<h1>Listing of All Glossary Terms</h1> 
	</div>
	<!-- End Sub Title -->
	<!--new glossary term button-->
	<div style="background-color: rgb(204, 255, 51); float: right;"><a href="">new glossary term</a></div>
	<!-- Overview SpiffyBox -->
	<!-- End Overview -->
<div id="slate">
		<div id="filterTerms">
		<form id="form1" name="form1" method="post" action="">
		  <label>Filter Glossary 
		  <input id="txtSearch" name="txtSearch" style="background: rgb(255, 255, 255) url(/images/search_light.gif) no-repeat scroll right center; width: 120px; padding-left: 1px; padding-right: 20px; margin-right: 5px; -moz-background-clip: -moz-initial; -moz-background-origin: -moz-initial; -moz-background-inline-policy: -moz-initial; color: rgb(153, 153, 153);" class="txtSearch" value="Search Terms" type="text"></label><div id="txtSearchIndicator" style="visibility: hidden; position: absolute; right: 0pt; margin-right: 150px;"><img src="glossaryTermManagement_files/indicator.gif"></div>
		  
		  <div id="clearSearch" style="display: none;"></div>
		</form>
		
	</div>
	  <div id="list"><!--Load glossary terms-->
	<div>
	<p>
	</p><div style="float: right;">&nbsp;= Alert from participant(s) to review definition</div><div style="background-color: rgb(204, 0, 51); float: right;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
	
	</div>
	<br>
	<br>
	
    <table id="termListTable" class="blueBB" cellpadding="4" cellspacing="2" frame="box" rules="all" width="100%">
	 <tbody><tr>
  	<th style="text-align: left;" id="name">name&nbsp;&nbsp;<a href=""><img src="glossaryTermManagement_files/sort_asc.gif" border="0"></a></th><th style="text-align: left;" id="def">a short definition</th><th style="text-align: center;" id="comments"><a href="">comments</a></th><th style="text-align: center;" id="views"><a href="">views</a></th><th style="text-align: center;"><a href="">Actions</a></th>
   </tr>
  
	 
	    <tr>
	      <td><a name="A"></a><a href="http://69.91.143.23:8080/glossaryView.do?id=1668">ARZ</a></td>
	      <td>An
area in which vehicular traffic is regulated by time of day and type of
vehicle. Normal automobile traffic and, sometimes, delivery of goods
are limited to certain times; public transit, emergency vehicles, and
(usually) taxicabs are permitted unrestricted access.</td>
	      <td style="text-align: center;">0</td>
	      <td style="text-align: center;">1</td>
		  <td style="text-align: center;"><a href="#lightbox1" rel="lightbox1" class="lbOn">edit attributes</a>|<a href="">del</a></td>
	    </tr>
	  
	   <tr style="background-color: rgb(204, 0, 51);">
	      <td><a href="http://69.91.143.23:8080/glossaryView.do?id=1668">agh</a></td>
	      <td>This is not an actual term. Delete me.</td>
	      <td style="text-align: center;">0</td>
	      <td style="text-align: center;">1</td>
		  <td style="text-align: center;"><a href="#lightbox1" rel="lightbox1" class="lbOn">edit attributes</a>|<a href="">del</a></td>
	    </tr>
	    <tr>
	      <td><a href="http://69.91.143.23:8080/glossaryView.do?id=931">Accessibility</a></td>
	      <td>The extent to which facilities are barrier free and useable by persons with disabilities, including wheelchair users.</td>
	      <td style="text-align: center;">0</td>
	      <td style="text-align: center;">0</td>
		  <td style="text-align: center;"><a href="#lightbox1" rel="lightbox1" class="lbOn">edit attributes</a>|<a href="">del</a></td>
	    </tr>
	  
	    <tr>
	      <td><a href="http://69.91.143.23:8080/glossaryView.do?id=936">Advanced Design Bus</a></td>
	      <td>A bus introduced in 1977 that incorporates new styling and design features compared to previous buses.</td>
	      <td style="text-align: center;">0</td>
	      <td style="text-align: center;">0</td>
		  <td style="text-align: center;"><a href="#lightbox1" rel="lightbox1" class="lbOn">edit attributes</a>|<a href="">del</a></td>
	    </tr>
	    
</tbody></table>
</div>
  </div>

	
	
<br>
	<!--new glossary term button-->
	<div style="background-color: rgb(204, 255, 51); float: right;"><a href="">new glossary term</a></div>
<br>
<!-- Start Footer -->
<div id="footer_clouds">

	<div id="footer_text">
	<a href="http://www.pgist.org/"><img src="glossaryTermManagement_files/footerlogo.png" alt="PGIST Logo" class="imgright" border="0" height="51" width="156"></a><br>This
research is funded by National Science Foundation, Division of
Experimental and Integrative Activities, Information Technology
Research (ITR) Program, Project Number EIA 0325916, funds managed
within the Digital Government Program. </div>

</div>
<!-- End Footer -->

			  
			  
			 	
			  
		

				
<!-- Run javascript function after most of the page is loaded, work around for onLoad functions quirks with tabs.js -->


</div>
<!-- Run javascript function after most of the page is loaded, work around for onLoad functions quirks with tabs.js -->
<script type="text/javascript">
	doOnLoad();
</script>
</body>
</html>
