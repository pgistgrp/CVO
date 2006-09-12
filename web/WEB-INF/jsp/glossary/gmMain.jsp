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

		function getTerms(term,sortby){
		
				GlossaryManageAgent.getTerms({filter:term, sort:sortby, direction:direction}, {
				callback:function(data){
					if (data.successful){ 	
						$('list').innerHTML = "";
						$('list').innerHTML += data.html;
						setSort(sortby, term);
						direction = "asc"; //reset direction
						$('loading-indicator').style.display = "none";
						initialize(); //initializes lightbox links. Placed here so generated links are also made active
					}else{
						alert(data.reason);
						$('loading-indicator').style.display = "none";
					}
					
				},
				errorHandler:function(errorString, exception){ 
				alert(errorString+" "+exception);
						//showTheError();
				}
			});
		
		}
		
		
		function getSourcesByTermId(termid){
			var tmparray=new Array();
			GlossaryManageAgent.getTerm({id:termid, type:"edit"},{
			callback:function(data){
			if(data.successful){
				
				for(var ct=0; ct<data.term.sources.length;ct++){
					
						var sourcearray = new Array();
						sourcearray.push(data.term.sources[ct].citation);
						sourcearray.push(data.term.sources[ct].url);
						tmparray.push(sourcearray);
					
				}
				
				//tmparray=new Array(sourcearray.length);
				
				
			}else{
				alert(data.reason);
			}
			},
			errorHandler:function(errorString, exception){
			alert(errorString+" "+exception);
			}
			});
			
				return tmparray;
			
		}
		var globtermlinkarray=new Array();
		globtermlinkarray.push("very first element");
		function dummy(data){
		if(data.successful){
					//var asdfarray = new Array();
					//alert("it ran");
					
		//asdfarray.push("test link");
				for (var ct=0; ct<data.term.links.length;ct++){
					
						globtermlinkarray.push(data.term.links[ct].link);
						//alert("in dummy: "+data.term.links[ct].link);
					
				}
				//alert("in dummy array: "+asdfarray[0]);
					return globtermlinkarray;
		
				}else{
					alert("error from getTermLinksByTermId: "+data.reason);
				}
		}
		
		function getTermLinksByTermId(termid){
		//var bool=false;
			//var tmparray = new Array();
			//bool=true;
		//asdfg.push("first element");
			GlossaryManageAgent.getTerm({id:termid, type:"edit"},{
			callback:function(data){
				dummy(data);
				
			/*
				if(data.successful){
					
					alert("it ran");
					bool=true;
		
				for (var ct=0; ct<data.term.links.length;ct++){
					
						tmparray.push(data.term.links[ct].link);
						
					
				}
					return tmparray;
		
				}else{
					alert("error from getTermLinksByTermId: "+data.reason);
				}*/
			},
			errorHandler:function(errorString, exception){
			alert(errorString+" "+exception);
			}
		});/*
		alert("first alert: "+bool);
		//alert("second alert: "+bool);
		if(bool==false){
		if(bool==true){
		alert("second if: "+bool);
		}
		}else{
		alert("else: "+bool);
		}*/
	}
		/*
		function getCategoriesByTermId(termid){
			var tmparray = new Array();
			GlossaryManageAgent.getTerm({id:termid, type:"edit"},{
			callback:function(data){
				if(data.successful){
		
		
					for(var ct=0; ct<data.term.categories.length;ct++){
						
							tmparray.push(data.term.categories[ct].name);
						
					}
		
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){
				alert(errorString+" "+exception);
			}
			});
			
				return tmparray;
			
		}*/
		
		
		
		function saveEditedAttributes(tid){//tid, tname, tsd, ted, tlinks, tsources, tcats)
		
		
			//alert("asdfarray: "+asdfarray[0]);
			//alert("asdfarray second time: "+asdfarray[0]);
			getTermLinksByTermId(tid);
			var tidlinks=new Array();
			tidlinks=tidlinks.concat(globtermlinkarray);
			
			/*if(tidlinks[0]==undefined){
			alert('tidlinks is undefined');
			}*/
			
			/*if(globtermlinkarray[0]==undefined){
			alert("global is also undefined");
			}*/
			//var tmpar = new Array();
			//tmpar=tmpar.concat(getTermLinksByTermId(tid));
			//for(var ct=0;ct<=tmpar.length;ct++){
			//tidlinks=tidlinks.concat(tmpar);
			//alert("save edited: "+tmpar[0]);
			//}
			//tidlinks=tidlinks.concat(getTermLinksByTermId(tid));
			/*if(tmpar[0]!=undefined && tmpar[0]!=""){
			tidlinks=tidlinks.concat(getTermLinksByTermId(tid));
			alert("get term links from save attributes: "+tmpar[0]);
			}*/
			
		//alert(tidlinks);
		//tidlinks.concat(getTermLinksByTermId(tid));
		//tidlinks=tidlinks.concat(getTermLinksByTermId(tid));
		
		//alert("tidlinks: "+tidlinks[0]);
		//tidlinks=tidlinks.concat(getTermLinksByTermId(tid));
		if($('edaddtermlink').value!="Http://"){
			//if(tidlinks[0]==undefined){
				//tidlinks[0]=$('edaddtermlink').value;
			//}else{
				tidlinks.push($('edaddtermlink').value);
			//}
		}
		
		//var tidsources =new Array();
		//tidsources=tidsources.concat(getSourcesByTermId(tid));
		
		/*if($('edaddsource').value!="Http://"){
		var tmpsource=new Array();
		tmpsource.push('test citation');
		tmpsource.push(($('edaddsource').value));
		tidsources.push(tmpsource);
		//tidsources.push(['test citation', 'test url']);
		//tidsources.push(['test citation', $('edaddsource').value]);
		}*/
		//var tidcats=new Array();
		//tidcats=tidcats.concat(getCategoriesByTermId(tid));
		//tidcats.concat(getCategoriesByTermId(tid));
		alert("end of method: " + globtermlinkarray);
			GlossaryManageAgent.saveTerm({id:tid, name:$('edtermname').value, shortDefinition:$('edtermshortdef').value, extDefinition:document.getElementById('edtermextdef').value}, [], tidlinks, [], [],{
			callback:function(data){
				if (data.successful){
				//getTerms();
					//alert("saved successfully");
					alert("successful: "+globtermlinkarray);
				}else{
					alert("Reason for failure: "+data.reason);
				}
			},
			errorHandler:function(errorString, exception){
			alert(errorString+" "+exception);
			}
		});
		alert("end of save: "+globtermlinkarray);
		globtermlinkarray=new Array();
		globtermlinkarray.push("very first element");
		//alert(globtermlinkarray);
		}		
		
		
		function changeLightBox(termid){
			GlossaryManageAgent.getTerm({id:termid, type:"edit"}, {
			callback:function(data){
				if(data.successful){
				var sourcearray=data.term.sources;
				var termlinkarray=data.term.links;
				//var relatedtermarray=data.term.relatedTerms;
				var lboxhtml = $('lightboxpadding');
					lboxhtml.innerHTML="<h3>Editing Attributes for Glossary Term: "+data.term.name+"</h3>"+
"<div><label>Glossary Term</label><br /><input id='edtermname' type='text' value='"+data.term.name+"'/></div>"+
"<br /><div><label>Short Definition</label><br /><input id='edtermshortdef' type='text' value='"+data.term.shortDefinition+"'/>"+
"</div><br /><div><label>Full Definition (optional - leave blank if none exists)</label>"+
"<br /><textarea id='edtermextdef' rows=5 style='width:75%'>"+data.term.extDefinition+"</textarea></div><br /><div>"+
"<label>Sources</label>";
var satml="";
for (var sa=0; sa<sourcearray.length; sa++){
satml+="<a href='"+sourcearray[sa].url+"'>"+sourcearray[sa].citation+"</a><br />";

}
lboxhtml.innerHTML+=satml;
lboxhtml.innerHTML+="<br /><label>Add Source:</label>&nbsp;<input id='edaddsource' type='text' value='Http:"+'//'+"'/></div><br /><br />"+
"<div><label>Term Links</label><br />";

for (var tl=0; tl<termlinkarray.length; tl++){
lboxhtml.innerHTML+="<a href='"+termlinkarray[tl].link+"'>"+termlinkarray[tl].link+"</a><br />";

}
lboxhtml.innerHTML+="<label>Add Link:</label>&nbsp;<input id='edaddtermlink' type='text' value='Http:"+'//'+"'/></div><br /><br />";
/*for(var rt=0; rt<relatedtermarray.length;rt++){
lboxhtml.innerHTML+=relatedtermarray[rt]+",&nbsp;";

}*/
//lboxhtml.innerHTML+="<div><label>Participant Comments</label>";

//add participant comments
lboxhtml.innerHTML+="<div style='float:right;'><a href='#' class='lbAction' rel='deactivate'>Cancel</a>&nbsp;<a class='lbsaveclose' rel='deactivate' id='"+termid+"' href='#' style='background-color:#CCFF99;'>Save and Close</a></div>";
//if(data.term.name!=$('edtermname').value && data.term.shortDefinition!=$('edtermshortdef').value && data.term.extDefinition!=$('edtermextdef').value
		
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
		}
</script>

<!--Lightbox script-->

<script type="text/javascript" src="scripts/prototype.js"></script>
<script type="text/javascript" src="scripts/lightbox.js"></script>


<!--Lightbox style-->
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

</head>
<body onload="sizeMe();" onresize="sizeMe();">



<div id="leightcontainer" class="leightcontainer">
	<div id="leightbar" class="leightbar">
		<div class="lbclose"><a href='#' class="lbAction" rel="deactivate"><img id="closebut" class="close" onmouseout="javascript:this.src='images/closeinactivesm.gif'" onmouseover="javascript:this.src='images/closeactivesm.gif'" src='images/closeinactivesm.gif'/></a>
		</div>
	</div>

	<div id="lightbox1" class="leightbox">
		
		<div id="lightboxpadding" class="leightpadding">
			
			<h3>Editing Attributes for Glossary Term: ...</h3>
			<div>
				<label>Glossary Term</label>
				<br />
				<input type='text' value='...'/>
			</div>
			<br />
			<div>
				<label>Short Definition</label>
				<br />
				<input type='text' value='definition'/>
			</div>
			<br />
		
			<div>
				<label>Full Definition (optional - leave blank if none exists)</label>
				<br />
				<textarea rows=5 style='width:75%'>Full Definition goes here</textarea>
			</div>
			<br />
			<div>
				<label>Sources</label>
				<br />
				<a href=''>Source URL #1</a>
				<br />
				<a href=''>Source URL #2</a>
				<br />
				<label>Add Source:&nbsp;</label><input type='text' value='Http://'/>
			</div>
			<br />
			<div>
				<label>Term Links</label>
				<br />
				No links have been created.
				<br />
				<label>Add Link:&nbsp;</label><input type='text' value='Http://'/>
			</div>
			<br />
		
			<div>
				<label>
					Related Terms
				</label>
				<p>
					Automatically added related terms: term #1, term #2
				</p>
		
				<label>
					Manual relate a term
				</label>
				<input type='text' value=''/>
			</div>
			<br />
			<div>
				<label>
					Participant Comments
				</label>
				<br />
				NoSUV on August 28,2006 added the following comment:
				<br/>
				Comment #1&nbsp; | <a href=''>Delete</a>
			</div>
			<div style='float:right;'>
				<a style='background-color:#CCFF99;' href=''>Cancel</a>&nbsp;<a style='background-color:#CCFF99;' href='' class='lbsaveclose' rel='deactivate123'>Save and Close</a>
			</div>
		</div>
	</div>
</div>
	
	<a href="#lightbox1" rel="lightbox1" class="lbOn">Test Lightbox</a>
	<!-- Header -->

<div id="login"><a href="http://69.91.143.23:8080/logout.do"><img src="glossaryTermManagement_files/btn_logout.gif" border="0"></a></div>
<!-- End Header -->
<div style="display: inline;" id="loading-indicator">Loading... <img src="glossaryTermManagement_files/indicator_arrows.gif"></div>
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
