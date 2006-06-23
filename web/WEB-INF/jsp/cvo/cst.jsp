<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>PGIST Portal - Let's Improve Transportation</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<style type="text/css" media="screen">@import "/styles/tabs.css";</style>
	<style type="text/css" media="screen">@import "/styles/pgist.css";</style>
	 <style type="text/css">
/* <![CDATA[ */ 
body, ol, li, h2 { padding:0; margin: 0; }


ol#root {  padding-left: 5px; margin-top: 2px; margin-bottom: 1px; list-style: none;}
#root ol {  padding-left: 5px; margin-top: 2px; margin-bottom: 1px; list-style: none;}
#root li { margin-bottom: 1px; padding-left: 5px;  margin-top: 2px;}

.panel { border-bottom: 1px solid #999; margin-bottom: 2px; margin-top: 2px; background: #eee; }

#root ul { margin-bottom: 1px; margin-top: 2px; list-style-position: inside; } 



.folder { background: url(/images/folder-closed.gif)  no-repeat; float: left; height: 14px; width: 26px; padding-right: 3px }
.doc { background: url(/images/file.gif) no-repeat; float: left; height: 14px; width: 12px; padding-right: 3px; margin-left: 20px;}



/*  ]]> */
 </style>
	<script src="/scripts/tabs.js" type="text/javascript"></script>
	<script src="/scripts/prototype.js" type="text/javascript"></script>
	<script src="/scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
	<script type='text/javascript' src='/dwr/engine.js'></script>
	<script type='text/javascript' src='/dwr/util.js'></script>
	<script type='text/javascript' src='/dwr/interface/CCTAgent.js'></script>
	<script type='text/javascript' src='/dwr/interface/CSTAgent.js'></script>
	
	
	<script type="text/javascript">
		var cctId = ${cctForm.cct.id};
		window.onload(doOnLoad());

		function doOnLoad(){
			getCategories();
			getOrphanTags(1);
			
			//getTags(12,1,1);
		}
		//TREE SCRIPT
		function toggle(elm) {
		 var newDisplay = "none";
		 elm.style.backgroundImage = 'url(/images/folder-closed.gif)';
		 var e = elm.nextSibling; 
		 while (e != null) {
		  if (e.tagName == "OL" || e.tagName == "ol") {
		   if (e.style.display == "none") {
		    newDisplay = "block";
		    elm.style.backgroundImage = 'url(/images/folder-open.gif)';
		   }
		   break;
		  }
		  e = e.nextSibling;
		 }
		 while (e != null) {
		  if (e.tagName == "OL" || e.tagName == "ol") e.style.display = newDisplay;
		  e = e.nextSibling;
		 }
		}
		
		function collapseAll(tags) {
		 for (i = 0; i < tags.length; i++) {
		  var lists = document.getElementsByTagName(tags[i]);
		  for (var j = 0; j < lists.length; j++) 
		   lists[j].style.display = "none";
		   var e = document.getElementById("root");
		   e.style.display = "block";
		 }
		}
		
		function openBookMark() {
		 var h = location.hash;
		 if (h == "") h = "default";
		 if (h == "#") h = "default";
		 var ids = h.split(/[#.]/);
		 for (i = 0; i < ids.length; i++) {
		  if (ids[i] != "") toggle(document.getElementById(ids[i]));
		 }
		}

		//END tree script
		function addCategory(category, parentId){
				CSTAgent.addCategory({cctId:cctId, category:category, parentId:parentId}, {
				callback:function(data){
						if (data.successful){
							getCategories();
							$('txtAddCategory').value='';
							$('txtAddCategory').focus();
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
		
		function deleteCategory(categoryId, parentId){
				CSTAgent.deleteCategory({cctId:cctId, parentId:parentId, categoryId:categoryId}, {
				callback:function(data){
						if (data.successful){
								var destroy = confirm ("Are you sure you want to delete this category? Note: there is no undo.")
								if (destroy){
									new Effect.Fade('cat'+categoryId, {duration: 0.5, afterFinish: function(){getCategories();}});
								}
								$('panelTICContent').innerHTML = 'Start by choosing a category in the first column.';
								$('panel_tags').innerHTML = '';
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
		
		function getCategories(){
				CSTAgent.getCategories({cctId:cctId}, {
				callback:function(data){
					
						if (data.successful){
							$('panelCatsContent').innerHTML = data.html;
							collapseAll(["ol"]); openBookMark();
							
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
		
		function getConcerns(tagId, page){
				CSTAgent.getConcerns({cctId:cctId, tagId: tagId, page: page}, {
				callback:function(data){
						if (data.successful){
							tabberSwitch(1);
							$('panel_concernsContainer').innerHTML = data.html;
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
		
		function tabberSwitch(tab){
			$('myTab').tabber.tabShow(tab);
		}
		
		var currentCategory = "";
		function showTags(categoryId, page){
			
			//showing related and unrelated tags
			currentCategory = categoryId;
			getTags(categoryId, page, 1, 0);
			getTags(categoryId, page, 0, 0);
			tabberSwitch(0);

		}
		
		function getTags(categoryId, page, type, tagId){
				CSTAgent.getTags({cctId:cctId, categoryId:categoryId, page:page, type: type}, {
				callback:function(data){
					if (type == 1){
						$('panel_tags').innerHTML = 'Tags within ' + currentCategory;
						$('panel_tags').innerHTML += data.html;
					}
					if (type == 0){
						$('panelTICContent').innerHTML = 'Tags not in ' + currentCategory;
						$('panelTICContent').innerHTML = data.html;
					}
					//if (tagId != 0){
						//alert('tag'+ tagId);
					//}
					if (data.successful != true){
							alert(data.reason);
					}
				},
				errorHandler:function(errorString, exception){ 
						showTheError();
				}
				});
		}
		
		function getOrphanTags(page){
				CSTAgent.getOrphanTags({cctId:cctId, page:page}, {
				callback:function(data){
						if (data.successful){
							$('panel_tags').innerHTML = data.html;
						}
					},
				errorHandler:function(errorString, exception){ 
						showTheError();
				}
				});
		}
		
		function relateTag(tagId){
				CSTAgent.relateTag({cctId:cctId, categoryId:currentCategory, tagId:tagId}, {
				callback:function(data){
						if (data.successful){
							new Effect.SwitchOff('tag' + tagId);
						
							getTags(currentCategory, 0, 0, tagId);
							
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
		
		function derelateTag(categoryId, tagId){
				CSTAgent.derelateTag({cctId:cctId, categoryId:categoryId, tagId:tagId}, {
				callback:function(data){
						if (data.successful){
									//new Effect.SwitchOff('tag' + tagId);
									new Effect.Fade('tag'+tagId, {duration: 0.5, afterFinish: function(){getTags(categoryId, 0, 1, tagId);}});
								  //getTags(categoryId, 0, 1, tagId);
								  
								 
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
		
	function editTags(concernId){
	removeLastComma(concernTags);
	CCTAgent.editTags({concernId:concernId, tags:concernTags}, {
		callback:function(data){
			if (data.successful){ 
				lightboxDisplay('none');
				showMyConcerns(concernId);
				concernTags = "";
			}
			
			if (data.successful != true){
				alert(data.reason);
				concernTags = "";
			}
		},
		errorHandler:function(errorString, exception){ 
				showTheError();
		}
	});
}

function editCatsPopup(categoryId, category){
			lightboxDisplay('inline');
			os = "";
			os += '<span class="closeBox"><a href="javascript: lightboxDisplay(\'none\');"><img src="/images/close.gif" border="0"></a></span>'
			os += '<h2>Edit Category</h2><p></p>';
			os += '<input type="text" id="txtEditCategory" class="txtAddCategory" value=\''+ category +'\'<br>';
			os += '<hr><input type="button" value="Submit Edits" onClick="editCategory('+categoryId+',\'make this the value\')">';
			//os += '<hr><a href="javascript:editCategory('+categoryId+',\''+ newcategory +'\')">Submit</a>';
			os += '<input type="button" value="Cancel" onClick="lightboxDisplay(\'none\')"></form>';
			$('lightbox').innerHTML = os;
}

function lightboxDisplay(action){
	$('overlay').style.display = action;
	$('lightbox').style.display = action;
}

function editCategory(categoryId,category){
		CSTAgent.editCategory({cctId:cctId, categoryId:categoryId, category:category},{
		callback:function(data){
			if (data.successful){ 
				lightboxDisplay('none');
				getCategories();
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
function showTheError(errorString, exception){
				$('container').style.display = 'none';
				$('caughtException').style.display = 'block';
				$('caughtException').innerHTML +='<p>If this problem persists, please <A HREF="mailto:webmaster@pgist.org?subject=LIT Website Problem>contact our webmaster</a></p>';
}

		
	</script>
</head>
<body>
	<!-- LIGHTBOX -->
<div id="container">
	<div id="overlay"></div>
	<div id="lightbox"></div>
	<!-- END LIGHTBOX -->
	<div id="pageTitle">
	<h2>Moderator Dashboard: </h2><h3>Concerns Synthesis Tool</h3>
		<div id="bread">
		<ul>
			<li class="first"><a href="null">Moderator Dashboard</a>
				<ul>
					<li>&#187; <a href="null">Concerns Synthesis Tool</a></li>
				</ul>
			</li>
		</ul>
		</div>
	</div>	
	
	 <div id="overview">
		  	<h4>Overview and Instructions</h4> 
		  	<p class="indent"><strong>Overview: </strong>Synthesize concerns: As a moderator, you can use this tool to sort out all the concerns the participants expressed in the previous step. All concerns are given "tags" which roughly captures the "ontology" of a participant as expressed in the concern. By adjusting the relationship among categories and the relationship among categories and tags, concerns will be grouped into clusters.</p>
	 </div>
	
	 <div id="caughtException"><h2>A Problem has Occured</h2><br>We are sorry but there was a problem accessing the server to complete your request.  <b>Please try refreshing the page.</b></div>
<div id="panelContainer">
    <div id="panelCats">
    	<div id="panelCatsTitle" class="panelTitle"><h3>1. Pick the Category</h3><br><form><input type="text" id="txtAddCategory" class="tagTextbox"><input type="submit" value="add!" onclick="javascript:addCategory($('txtAddCategory').value); return false;"></form></div>
    	<div id="panelCatsContent"></div>	
    </div>
    
    <div id="panelTagsInCats">
	    <div id="panelTICTitle" class="panelTitle"><h3>2. View the Category</h3><br>Please click on a category to the left to view the tags within the category.</div>
	    <div id="panelTICContent">Start by choosing a category in the first column.</div>	
	  </div>

    
    <div id="bar" style="position: relative; padding: 5px;">
    		<div id="panelTICTitle" class="panelTitle"><h3>3. Add tags into Category</h3></div>
				<div class="tabber" id="myTab" style="position: absolute; top:0; margin-top: 50px; width:95%;">
					  <div id="panel_tags" class="tabbertab" style="height: 350px; overflow: auto;">
				    	<H2>Tags</H2>
				    </div>
				    <div id="panel_concernsContainer" class="tabbertab" style="height: 350px; overflow: auto;">
				    	<h2>Concerns</h2>
				    	<p>Please click on a tag to view concerns associated with that tag.</p>
				    </div>
				</div>
		</div>
</div>



</div>
 <div id="caughtException"><h2>A Problem has Occured</h2><br>We are sorry but there was a problem accessing the server to complete your request.  <b>Please try refreshing the page.</b></div>

</body>

</html>                                    