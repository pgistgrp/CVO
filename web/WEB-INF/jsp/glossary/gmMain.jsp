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
		
		function deleteConfirm(termstring,tid){
			var dletebox=confirm("Do you really wish to delete \""+termstring+"\"?");
			if(dletebox==true){
				deleteTerm(tid);
			}
		
		}
		
		function deleteTerm(termid){
		GlossaryManageAgent.deleteTerm({id:termid},{
		callback:function(data){
			if (data.successful){
				new Effect.Fade('glossaryTerm' + termid);
				new Effect.Fade('editbox'+termid);
				new Effect.Fade('editrow'+termid);
			}else{
			alert("deleteTerm failure reason: "+data.reason);
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
						alert("getProposedTerms failure reason: "+data.reason);
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
						direction = "asc";
						$('loading-indicator').style.display = "none";
						
						gotTerms=true;
						isTermsGotten();
					}else{
						alert("getTerms failure reason: "+data.reason);
						$('loading-indicator').style.display = "none";
					}
					
				},
				errorHandler:function(errorString, exception){ 
				alert(errorString+" "+exception);
						
				}
			});
		
		}
		var globalSourceLinks=new Array();
		var globalTermLinks = new Array();
		var globalCategoryLinks=new Array();
		
		
		var saveFinished=false;
		function saveEditedAttributes(tid){
		$('saving-indicator').style.display = "inline";
		
			GlossaryManageAgent.saveTerm({id:tid, name:$('edtermname'+tid).value, shortDefinition:$('edtermshortdef'+tid).value, extDefinition:document.getElementById('edtermextdef'+tid).value}, [], globalTermLinks, globalSourceLinks, globalCategoryLinks,{
			callback:function(data){
				if (data.successful){
				refreshSavedTerm(tid,$('edtermname'+tid).value, $('edtermshortdef'+tid).value);
				saveFinished=true;
				isSaved();
				}else{
					alert("saveEditedAttributes failure reason: "+data.reason);
				}
			},
			errorHandler:function(errorString, exception){
			alert(errorString+" "+exception);
			}
			
		});
		
		
		
		}		
		function refreshSavedTerm(tid,name,shortDef){
			$('glossaryViewLink'+tid).innerHTML=name;
			$('termShortDefinitionCell'+tid).innerHTML=shortDef;
		
		}
		
		
		function openUp(id, className){
		
			var activeRecord = className+id;
			
			if(currentopenbox==''){
			$(activeRecord).style.backgroundColor='white';
			new Effect.BlindDown(activeRecord, {duration: 0.4});
			currentopenbox=activeRecord;
			}else{
			if(currentopenbox!=activeRecord){
			new Effect.BlindUp(currentopenbox,{duration:0.4});
			$(activeRecord).style.backgroundColor='white';
			new Effect.BlindDown(activeRecord, {duration: 0.4});
			currentopenbox=activeRecord;
			
			}else{
			
			
			}
			}
			
			
		var t = setTimeout('location.hash="'+activeRecord+'"',500);
		
		
		}
	
 
		function saveClose(tid){
			closeEdit(tid); 
			saveEditedAttributes(tid);
			
		}
		
		function openEditBox(tid){
			changeEditBoxContent(tid);
			
		
		}
		
		
		var currentopenbox='';
		function isContentChanged(tid){
		
			if(changedContent){
				changedContent=false;
		
				openUp(tid, 'editbox');
		
			}else{
		
		
				var t =setTimeout('isContentChanged('+tid+');',100);
		
			}
			
		}
		
		
		
		function isSaved(){
		
		if(saveFinished){
		saveFinished=false;
		$('saving-indicator').style.display = "none";
		
		}else{
		
		var t =setTimeout('isSaved();',100);
		
		}
		
		
		}
		
		
		
		function isTermsGotten(){
		
		if(gotTerms){
		gotTerms=false;
		}else{
		
		var t =setTimeout('isTermsGotten();',100);
		
		}
			
		
		}
		
		function contIntervalCall(a,type,tid){
			 if(type=='saveTerm'){
				clearInterval(a);
				
			}else if(type=='getTerms'){
				clearInterval(a);
		
			}else if(type=='openEditBox'){
				clearInterval(a);
				
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
						satml+="<a href='"+sourcearray[sa].url+"'>"+sourcearray[sa].citation+"</a><br /><br /><button type='button' onclick='deleteSourceLink("+sa+","+termid+"); return false;'>Delete Source</button><br /><br />";
						globalSourceLinks.push([sourcearray[sa].citation,sourcearray[sa].url]);
					}
				
					var tatml="";
				
					for (var tl=0; tl<termlinkarray.length; tl++){
					
						tatml+="<a href='"+termlinkarray[tl].link+"'>"+termlinkarray[tl].link+"</a>&nbsp;&nbsp; <button type='button' onclick='deleteTermLink("+tl+","+termid+"); return false;'>Delete Term Link</button><br />";
						globalTermLinks.push(termlinkarray[tl].link);
					}
				
					lboxhtml.innerHTML="<div border:thick solid #C0C0C0;'><table style='width:100%;' rules='all'><tbody><tr><td cellspacing=10 style=''><div style='margin:2%;'><label><strong>Term Name:</strong></label><br /><input style='width:50%;' id='edtermname"+termid+"' type='text' value='"+data.term.name+"'/><br />"+
				"<label><strong>Short Definition</strong></label><br /><input type='text' maxlength=256 size=25 id='edtermshortdef"+termid+"' value='"+data.term.shortDefinition+"'/></div></td>"+
				"<td rowspan=2><div style='margin:2%;'><label><strong>Sources</strong></label><br /><div id='sourcelinks"+termid+"'>"+satml+
				"</div><br /><label><strong>Add Source</strong></label><br /><textarea style='width:90%;' id='edaddsourcecitation"+termid+"'>Citation</textarea><br /><input style='width:50%;' id='edaddsource"+termid+"' type='text' value='Http:"+'//'+"'/>"+
				"<br /><button type='button' onclick='addSourceLink("+termid+"); return false;'>Add Source</button><br /><br /><label><strong>Term Links</strong></label><br /><div id='termlinks"+termid+"'>"+tatml+
				"</div><br /><label>Add Link</label><br /><input style='width:50%;' id='edaddtermlink"+termid+"' type='text' value='Http:"+'//'+"'/><br /><button type='button' onclick='addTermLink("+termid+"); return false;'>Add Link</button></div></td></tr><tr><td><div style='margin:2%;'><label><strong>Extended Definition</strong></label><br />"+
				"<textarea style='width:90%; height:100%;' id='edtermextdef"+termid+"'>"+data.term.extDefinition+"</textarea></div></td></tr><tr><td style='width:50%;'></td><td style=''><div style='margin:0.5%; float:right;'><button type='button' onclick='closeEdit("+termid+"); return false;'>Cancel</button>&nbsp;<button type='button' onclick='saveClose("+termid+"); return false;'>Save and Close</button></div></td></tr></tbody></table></div>";
					changedContent=true;
					isContentChanged(termid);
				}else{
					alert("changeEditBoxContent failure reason: "+data.reason);
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
				
		}
</script>

<style type='text/css'>
#saving-indicator{
	display: none;
	background-color: red;
	color: white;
	position: fixed;
	top: 0;
	left:0;
	padding: 3px;
	z-index: 500;
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
currentopenbox='';

}
</script>

</head>
<body>




	
	
	<!-- Header -->

<div id="login"><a href="http://69.91.143.23:8080/logout.do"><img src="glossaryTermManagement_files/btn_logout.gif" border="0"></a></div>
<!-- End Header -->
<div style="display: inline;" id="loading-indicator">Loading... <img src="glossaryTermManagement_files/indicator_arrows.gif"></div>
<div style="display: none;" id="saving-indicator">Saving... <img src="glossaryTermManagement_files/indicator_arrows.gif"></div>
<div id="container">


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
	<table width='100%' frame="box" rules="all">
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
	<div style="background-color: rgb(204, 255, 51); "><a href="">new glossary term</a></div>
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
	<div style="background-color: rgb(204, 255, 51); "><a href="">new glossary term</a></div>
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
