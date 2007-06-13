<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Concerns Synthesis Tool</title>

	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<!--CSS Libraries -->

	<style type="text/css" media="screen">@import "/styles/dhtmlXTree/dhtmlXTree.css";</style>
	<style type="text/css" media="screen">@import "/styles/tabs.css";</style>
	<style type="text/css" media="screen">@import "/styles/lit.css";</style>

	<script type="text/javascript" src="/scripts/tinymce/jscripts/tiny_mce/tiny_mce.js"></script>
 	<!--JavaScript Libraries -->
 	<script src="/scripts/prototype.js" type="text/javascript"></script>
 	<script src="/scripts/rico_simple.js" type="text/javascript"></script>
 	<script src="/scripts/tabcookies.js" type="text/javascript"></script>
 	<script src="/scripts/tabs.js" type="text/javascript"></script>
 	<script src="/scripts/editor_simple.js" type="text/javascript"></script>
	<script src="/scripts/scriptaculous.js?load=effects,controls" type="text/javascript"></script>
	<script src="/scripts/tags.js" type="text/javascript"></script>
	<script src="scripts/search.js" type="text/javascript"></script>
	
	<!--DHTML XTree Libraries -->
	<script src="/scripts/dhtmlXTree/dhtmlXTree.js" type="text/javascript"></script>	
	<script src="/scripts/dhtmlXTree/dhtmlXCommon.js" type="text/javascript"></script>	
	
	<!--DWR and Component Interfaces -->
	<script type='text/javascript' src='/dwr/engine.js'></script>
	<script type='text/javascript' src='/dwr/util.js'></script>
	<script type='text/javascript' src='/dwr/interface/CCTAgent.js'></script>
	<script type='text/javascript' src='/dwr/interface/CSTAgent.js'></script>
	
	<!-- Template 5 Specific -->
	<style type="text/css" media="screen">@import "/styles/template5.css";</style>
	<script src="/scripts/resize_t5.js" type="text/javascript"></script>
	<script src="/scripts/util.js" type="text/javascript"></script>
	<!-- End Template 5 Specific -->
	
	

	<script language="javascript" type="text/javascript">
tinyMCE.init({
	mode : "exact",
	elements: "sumtext",
	theme : "advanced",
	theme_advanced_buttons1 : "bold, italic, undo, redo,link",
	theme_advanced_buttons2 : "",
	theme_advanced_buttons3 : "",
	content_css : "/scripts/tinymce/jscripts/tiny_mce/themes/simple/css/bigmce.css"
});

</script>
	
	
	
	<script type="text/javascript">
		///////////////////////////////////////////////////new change/////////////////////////
		//window.onload="doOnLoad();
		var cctId = ${cctForm.cct.id};
    var tree1 = null;
    var currentCategory = null;
     var currentTheme  = null;
     var themeCollection = null;
     var previousCategory = null;
     var previousTheme = null;

    
    function preLoadImages(){
      var imSrcAr = new Array("line1.gif","line2.gif","line3.gif","line4.gif","minus2.gif","minus3.gif","minus4.gif","plus2.gif","plus3.gif","plus4.gif","book.gif","books_open.gif","books_close.gif","magazines_open.gif","magazines_close.gif","tombs.gif","tombs_mag.gif","book_titel.gif","iconCheckAll.gif")
      var imAr = new Array(0);
      for(var i=0;i<imSrcAr.length;i++){
        imAr[imAr.length] = new Image();
        imAr[imAr.length-1].src = "/images/dhtmlXTree/"+imSrcAr[i];
      }
    }
    
    function setThemeTitle(themetitle){
    	document.getElementById("summaryEditorTitle").innerHTML = themetitle;
    }
    
		function doOnLoad(){
			//preLoadImages();
      resetCols()
      tree1=new dhtmlXTreeObject("col-left","100%","100%",0);
      tree1.setImagePath("/images/dhtmlXTree/");
      tree1.setDragHandler(moveNodeHandler);
      tree1.enableCheckBoxes(false);
      tree1.enableThreeStateCheckboxes(true);
      tree1.enableDragAndDrop(true);
      tree1.loadXML("/catsTree.do?cctId=${cctForm.cct.id}");
      tree1.cctId = cctId;
      tree1.setDragCopyHandler(copyNodeHandler);
      //tree1.setDragDuplicateHandler(duplicateNodeHandler);
      tree1.setOnClickHandler(treeClickHandler);
      
/**/      
			//getCategories();
			getOrphanTags();
			//getTags(12,1,1);
		}
		
		var editingThemeId = '';
		function getThemes(themeId){
			Util.loading(true,"Working")
			CSTAgent.getThemes({cctId:cctId,asHTML:true}, {
			callback:function(data){
				if (data.successful){
				  themeCollection = null;
				  themeCollection = data.themesMap;
				  if(previousTheme){ //this guy should be updated as well
				  	for(i=0; i<data.themes.length; i++)
						if(previousTheme.id == data.themes[i].id){
							previousTheme = null;
							previousTheme = data.themes[i];
							break;
						}
				  }
				  if(currentTheme){ //this guy should be updated as well
				  	for(i=0; i<data.themes.length; i++)
						if(currentTheme.id == data.themes[i].id){
							currentTheme = null;
							currentTheme = data.themes[i];
							break;
						}
															
				  }
				}else{
					alert(data.reason);
				}
			Util.loading(false);
			},
			errorHandler:function(errorString, exception){ 
					alert("getThemes: "+errorString+" "+exception);
			}
			});
	
		}
				
		function saveSummary(theTheme, theSummary){
			var summaryTags = $('summaryTags').value;
			if(!currentTheme)return;
			
			if(theTheme)
				themeId  = theTheme.id;
			else 
				themeId = currentTheme.id;
			
			if(theSummary)
				summaryString = theSummary;
			else
				//summaryString = //editor1.getContent();
				summaryString=tinyMCE.getContent();
				
			//alert("cctId: " + cctId + " themeId: " + themeId + " summary:  " + summaryString + " tags: " + summaryTags);
			Util.loading(true,"Working")
			CSTAgent.saveSummary({cctId:cctId, themeId:themeId, summary:summaryString, description: "discuss concerns about", tags: summaryTags}, {
			callback:function(data){
				if (data.successful){
					alert('Summary has been saved.');
					//editor1.setMessage("summary saved.");	
					getThemes ();
				}else{
					alert("CSTAgent.saveSummary() Error" + data.reason);
				}
			Util.loading(false);
			},
			errorHandler:function(errorString, exception){ 
					alert("saveSummary: "+errorString+" "+exception);
			}
			});
		}
		
		function keepBreaks(string){
			return string.replace(/\n/g,"<br>");
		}
		
		function getOrphanTags(){
				CSTAgent.getOrphanTags({cctId:cctId, page:0, count: -1}, {
				callback:function(data){
						if (data.successful){
							$('sidebar_tags').innerHTML += data.html;
						}
					}
				});
		}

		function tabberSwitch(tab){
			$('myTab').tabber.tabShow(tab);
		}
		var relatedTagsArr = [];
		function getTags(categoryId, page, type, orphanpage){
			Util.loading(true,"Working")
			CSTAgent.getTags({cctId:cctId, categoryId:categoryId, page:page, type: type, orphanPage:orphanpage}, {
			callback:function(data){
				if (data.successful){
					if (type == 0){			
						
						relatedTagsArr = [];
					    for(i=0; i<data.tags.length; i++){
							relatedTagsArr.push(data.tags[i].id);
					    }
						
						document.getElementById('col').innerHTML = '<h4>Tags within ' + currentCategory.label + '</h4>';
						document.getElementById('col').innerHTML += data.html;
						
						
						
						if (data.tags.length > 0){
							getConcernsByTags(0);
							$('col').innerHTML += '<a href="javascript:getConcernsByTags(1); new Effect.Highlight(\'sidebar_concerns\'); void(0);">Show concerns with the above tags</a>';
						}
						
					}
					if (type == 1){

						////todo: get rid of the first line, check the jsp template
						$('sidebar_tags').innerHTML = '<h4>Tags not in ' + currentCategory.label + '</h4>';
						$('sidebar_tags').innerHTML += data.html;
					}
				}else{
					alert("Getting tags not successful: " + data.reason);
				}
			Util.loading(false);
			},
			errorHandler:function(errorString, exception){ 
					alert("getTags: "+errorString+" "+exception);
			}
			});
		}

		function relateTag(tagId){
			$('savingIndicator').style.display = "inline";
			if(currentCategory == null)return;
			Util.loading(true,"Working")
			CSTAgent.relateTag({cctId:cctId, categoryId:currentCategory.dataId, tagId:tagId}, {
			callback:function(data){
					if (data.successful){
						Effect.Fade('savingIndicator');
						//new Effect.Fade('tag' + tagId, {duration: 0.5, afterFinish: function(){getTags(currentCategory.dataId, 0, 1, tagId);getTags(currentCategory.dataId, 0, 0, tagId);}});
						getTags(currentCategory.dataId, 0, 1, tagId);
						getTags(currentCategory.dataId, 0, 0, tagId);
					}
					if (data.successful != true){
						alert(data.reason);
						Effect.Fade('savingIndicator');
					}
				Util.loading(false);
				},
			errorHandler:function(errorString, exception){ 
			alert("relateTag: "+errorString+ " "+exception);
					//showTheError();
			}
			});
		}	

		
		function derelateTag(categoryId, tagId){
			$('savingIndicator').style.display = "inline";
				Util.loading(true,"Working");
				CSTAgent.derelateTag({cctId:cctId, categoryId:categoryId, tagId:tagId}, {
				callback:function(data){
						if (data.successful){
									//new Effect.SwitchOff('tag' + tagId);
									//new Effect.Fade('tag'+tagId, {duration: 0.5, afterFinish: function(){getTags(categoryId, 0, 1, tagId);getTags(categoryId, 0, 0, tagId);}});
									Effect.Fade('savingIndicator');
									getTags(categoryId, 0, 1, tagId);
									getTags(categoryId, 0, 0, tagId);
						}
						if (data.successful != true){
							alert(data.reason);
							Effect.Fade('savingIndicator');
						}
					Util.loading(false)
					},
				errorHandler:function(errorString, exception){ 
						alert("derelateTag: "+errorString+" "+exception);
				}
				});
		}		
			
		function getConcernsByTags(visibility){
			//eventually make  paginated
				Util.loading(true,"Working");
				CSTAgent.getConcernsByTags({cctId:cctId, page: 1, count: -1}, relatedTagsArr, {
				callback:function(data){
						if (data.successful){
							if (visibility == 1){	
								$('myTab').tabber.tabShow(1);
							}else{
								$('myTab').tabber.tabShow(0);
							}
							$('sidebar_concerns').innerHTML = data.html;
						}
						if (data.successful != true){
							alert(data.reason);
						}
					Util.loading(false)
					},
				errorHandler:function(errorString, exception){ 
						alert("getConcernsByTags: "+errorString+" "+exception);
				}
				});
		}
		
		function getConcerns(tagId, page){
				Util.loading(true,"Working");
				CSTAgent.getConcerns({cctId:cctId, tagId: tagId, page: page}, {
				callback:function(data){
						if (data.successful){
							$('myTab').tabber.tabShow(1);
							$('sidebar_concerns').innerHTML = data.html;
						}
						if (data.successful != true){
							alert(data.reason);
						}
					Util.loading(false);
					},
				errorHandler:function(errorString, exception){ 
						alert("getConcerns: "+errorString+" "+exception);
				}
				});
		}
		
				function getConcerns(tagId, page){
				Util.loading(true,"Working");
				CSTAgent.getConcerns({cctId:cctId, tagId: tagId, page: page}, {
				callback:function(data){
						if (data.successful){
							$('myTab').tabber.tabShow(1);
							$('sidebar_concerns').innerHTML = data.html;
						}
						if (data.successful != true){
							alert(data.reason);
						}
					Util.loading(false);
					},
				errorHandler:function(errorString, exception){ 
						alert("getConcerns: "+errorString+" "+exception);
				}
				});
		}
		
		function publish(){
				Util.loading(true,"Working");
				CSTAgent.publish({cctId:cctId}, {
				callback:function(data){
						if (data.successful){
							location.href="/sdlist.do"
						}
						if (data.successful != true){
							alert("There was a problem publishing concern themes:" + data.reason);
						}
					Util.loading(false);
					},
				errorHandler:function(errorString, exception){ 
						alert("publish: "+errorString+" "+exception);
				}
				});
		}
	//--------------------------------------
	function checkaddcategory(e){
		if(e.keyCode == 13)addcategory();
	}
	
	function addcategory(){
		
		if(document.getElementById("newcatetext").value != ""){
			$('savingIndicator').style.display = "inline";
			var catname = document.getElementById("newcatetext").value;
			var parentId = (tree1.lastSelected) ? tree1.lastSelected.parentObject.dataId : 0;
			//alert("new cate: " + catname + "; parent=" + parentId);
			Util.loading(true,"Working9");
			CSTAgent.addCategory({cctId:cctId, parentId:parentId, name:catname},{
				callback:function(data){
					if (data.successful){ 
						tree1.insertNewItemUnderSelected(document.getElementById("newcatetext").value,
						data.newId);
						document.getElementById("newcatetext").value = "";
						getThemes();
						Effect.Fade('savingIndicator');
						/////to do: display a message...
					}
					
					if (data.successful != true){
						Effect.Fade('savingIndicator');
						alert(data.reason);
					}
				Util.loading(false)
				},
				errorHandler:function(errorString, exception){alert("addcategory: "+errorString+" "+exception);}
			});			
		}else{
		alert("Category can't be blank.");
		}
	}

	function globalKeyHandler(e){
		if(e.keyCode==46)
			deleteSelectedCategory();
	}
		
	function deleteSelectedCategory(){
		
		if(tree1.lastSelected!=null){
			if(confirm("Are you sure you want to delete category \"" + tree1.lastSelected.parentObject.label + "\"")){
				$('savingIndicator').style.display = "inline";
				if(tree1.lastSelected.parentObject.parentObject.id == 0)
					var params = {cctId: cctId, categoryId: tree1.lastSelected.parentObject.dataId};
				else
					var params = {cctId: cctId, categoryId: tree1.lastSelected.parentObject.dataId,parentId: tree1.lastSelected.parentObject.parentObject.dataId}
					
				CSTAgent.deleteCategory(params, {
							callback:function(data){
								if (data.successful){
									Effect.Fade('savingIndicator');
									tree1.deleteSelectedItem();
									new Effect.PhaseOut('col-crud-options');
									getThemes(); 
								}
							},
							errorHandler:function(errorString, exception){
							alert("deleteSelectedCategory: "+errorString+" "+exception);
							}
				});
			}
		}
	}

	function modifySelectedCategory(){
		
		var newtext = document.getElementById("selcatetext").value;
		if(tree1.lastSelected!=null && newtext!=""){
			$('savingIndicator').style.display = "inline";
			var params = {cctId: cctId, categoryId: tree1.lastSelected.parentObject.dataId, name:newtext};
			CSTAgent.editCategory(params, {
						callback:function(data){
							if (data.successful){
								Effect.Fade('savingIndicator');
								tree1.modifyItemName(tree1.lastSelected.parentObject.dataId, newtext);
								new Effect.Fade('col-option'); 
								getThemes();
								setThemeTitle('View/edit summary for theme "' + newtext + '"');
							}else{
								alert("modifySelectedCategory failure reason: "+data.reason);
							}
						},
						errorHandler:function(errorString, exception){
							alert("modifySelectedCategory: "+errorString+" "+exception);
							}
			});
		}else{
			alert("Category can't be blank.");	
		}
	}
	
	function treeClickHandler(clickid, lastid, labeltext){
		
		document.getElementById("selcatetext").value = labeltext;
		currentCategory = tree1.lastSelected.parentObject;
		
		tempcate = tree1.getTopLevelNode(currentCategory);
		if(themeCollection != null){
			currentTheme = themeCollection[tempcate.dataId];
			if(previousTheme != currentTheme)
			{ 	//this means that the editor should be reloaded.
					if(previousTheme)
						if(previousTheme.summary != tinyMCE.getContent())
						{
							if(confirm ("Summary for theme \"" + previousTheme.title + "\" is changed. Do you want to save?"))
							  	//saveSummary (previousTheme,editor1.getContent());
								saveSummary(previousTheme,tinyMCE.getContent());
						  }
					previousTheme  = currentTheme ;
					if(currentTheme.summary == ""){

						setThemeTitle("Generate summary for theme \"" + tempcate.label + "\""); 
					}else{
						setThemeTitle("View/edit summary for theme \"" + tempcate.label + "\"");
					}
					//editor1.putContent (currentTheme.summary);
					tinyMCE.setContent(currentTheme.summary);
					
					$('summaryTags').value = "";

					for(i=0; i< currentTheme.tags.length; i++){
						$('summaryTags').value += currentTheme.tags[i].name + ","
					}
			}
		}
		
		if(!currentTheme==null){
			document.getElementById("ss").disabled = true;
		}else{
			document.getElementById("ss").disabled = false;
		}
		getTags(clickid, 0, 0,1);
		getTags(clickid, 0, 1, 1);
		
		if ($('col-crud-options').style.display == 'none'){
		new Effect.PhaseIn('col-crud-options'); 
	}else{
		new Effect.Highlight('col-crud-options');
	}
		new Effect.Fade('col-option');
		//location.href="#colsTop";
		
	}
	
	function unselectall(mode){
		if(mode){
			tree1.unSelectAll();
			$('selcatetext').value = '';

			currentCategory=null;
			resetCols()
			getOrphanTags();
			$('col-crud-options').style.display = "none"; 
			$('col-option').style.display = "none";;
		}
		tree1.clickedOn = false;
	}
	
	function moveNodeHandler(sourceO, targetO){
		params = {cctId: cctId, categoryId: sourceO.dataId};
		if(sourceO.parentObject.id != 0)
			params.parent0Id = sourceO.parentObject.dataId;			
		if(targetO.id != 0)
			params.parent1Id = targetO.dataId;
			
		CSTAgent.moveCategory(params,{
			callback:function(data){
				if (data.successful){
					getThemes();
					if(sourceO.parentObject.id == 0){	//it's a theme, possibly merge summery
						var sourceTheme = themeCollection[sourceO.dataId];
						if(sourceTheme.summary!=""){
							if(confirm("Merge the summary for \"" + sourceTheme.title + "\" to the new theme?")){
								tempcate = tree1.getTopLevelNode(targetO);
								if(themeCollection != null){
									currentTheme = themeCollection[tempcate.dataId];
									if(previousTheme)
										//if(previousTheme.summary != editor1.getContent())
										if(previousTheme.summary!= tinyMCE.getContent())
										{
											if(confirm ("Summery for theme \"" + previousTheme.title + "\" is changed. Do you want to save?"))
												//saveSummary (previousTheme,editor1.getContent());
												saveSummary(previousTheme,tinyMCE.getContent());
										  }
									previousTheme  = currentTheme ;
									
									//editor1.putContent (currentTheme.summary + "<br>" + sourceTheme.summary);				
									tinyMCE.setContent(currentTheme.summary + "<br>" + sourceTheme.summary);		
											
								}
							
							}
						}
					}
					var newID=tree1._moveNode(sourceO,targetO);
					tree1.selectItem(newID);
					return true;
				}
				else{ 
					alert(data.reason);
					return false;
				}
			},
			errorHandler:function(errorString, exception){
							alert("moveNodeHandler: "+errorString+" "+exception);
			}
		});
	}
	
	function copyNodeHandler(sourceO, targetO){
		$('savingIndicator').style.display = "inline";
		CSTAgent.copyCategory({cctId: cctId, categoryId: sourceO.dataId, parentId: targetO.dataId},{
			callback:function(data){
				if (data.successful){
					var newID=tree1._copyNodeTo(sourceO,targetO);
					tree1.selectItem(newID);
					getThemes();
					return true;
					Effect.Fade('savingIndicator');
				}
				else return false;
			},
			errorHandler:function(errorString, exception){
							alert("copyNodeHandler: "+errorString+" "+exception);
			}
		});
	}
	
	function duplicateSelectedCategory(){
		
		if(tree1.lastSelected!=null){
			var obj1 = tree1.lastSelected.parentObject;
			var params = {cctId:cctId,categoryId:obj1.dataId, name:"Similar to "+ obj1.label};
			$('savingIndicator').style.display = "inline";
			if(obj1.parentObject.Id!=0)
				params.parentId = obj1.parentObject.dataId;
			CSTAgent.duplicateCategory(params, 
			{callback:function(data){
				if(data.successful){
					var newitem = tree1.insertNewItem(obj1.parentObject.id,data.newId,"Similar to "+ obj1.label);
					//tree1.selectItem(newitem.id);
					
					//getTags(data.newId, 0, 0);
					//treeClickHandler(data.newId, data.newId, "Similar to "+ obj1.label)
					getThemes();
					if ($('col-crud-options').style.display == 'none'){
						new Effect.PhaseIn('col-crud-options'); 
					}else{
						new Effect.Highlight('col-crud-options');
					}
					new Effect.Fade('col-option');
					location.href="#colsTop";
					Effect.Fade('savingIndicator');
				}else
					
					alert(data.reason);
					Effect.Fade('savingIndicator');
			},
			errorHandler:function(errorString, exception){
							alert(errorString+" "+exception);
			}
		});
		}
	}
	
	function resetCols(){
		$('col').innerHTML = '<h4>Select a category in the left to see tags assciated with it.</h4>';
		$('sidebar_tags').innerHTML = '<h4>Orphan Tags</h4>';
	}
	//------------------------------------
	</script>
	
<style type="text/css"> 
	
   .accordionTabTitleBar { 
      font-size: 12px; 
      padding : 2px 6px 2px 6px;
      border-style : solid none solid none;
      border-top-color: #DDDDDD; 
      border-bottom-color : #DDDDDD;
      border-width: 1px 0px 1px 0px; 
      text-transform: capitalize;
      cursor:pointer;
   } 
   .accordionTabContentBox { 
      font-size : 11px;
      border : 1px solid #1f669b; 
      border-top-width : 0px;
      padding : 0px 8px 0px 8px;
   }
   
   .inplaceeditor-form textarea { 
   		width: 95%;
   		height: 100px;
   		
   		
   }


#topMenu {
padding:5px;
background:#E1F1C5;
border-bottom:1px solid #C6D78C;
margin-bottom:5px;
margin-left:-3px;
}

#topMenu input[type="text"]{width:110px;}

#topMenu2 {
padding:5px;
padding-left:0px;
width:97.75%;
}

#col-crud-options{
background:#EEF7DD;
padding:5px;
margin-top:10px;
border:1px solid #B4D579;
}

#col-left, #col {border:1px solid #B4D579;}

.closeBox{float:right;}

#summaryEditorTitle{display:block;margin-top:10px;}

button#ss{font-size:12pt;padding:5px;}

#sidebar_tags_header{border:1px solid #B4D579;}
</style>
<event:pageunload />
</head>

<body onResize="dosize()" onkeydown="globalKeyHandler(event);">
	<div id="savingIndicator" style="display: none; background-color:#FF0000;position:fixed;">&nbsp;Saving...<img src="/images/indicator.gif">&nbsp;</div>
<div id="header">
	<!-- Begin header -->
	<jsp:include page="/header.jsp" />
	<!-- End header -->
</div>
<!-- End header -->
<!-- Begin header menu - The wide ribbon underneath the logo -->
<div id="headerMenu"></div>
<div id="container">
	<div id="cont-resize">
		<a name="colsTop"></a>
		<div id="topMenu2">
			<input type="button" value="Unselect All" onclick="unselectall(true);">
			<input type="checkbox" onclick="tree1.switchCopyMode()">Copy mode <small>(or ctrl + drag)</small>
		</div>
		<div id="col-left" style="text-transform: capitalize; " onclick="unselectall(!tree1.clickedOn)">
			<div id="topMenu">
				<input type="text" id="newcatetext" onkeydown="checkaddcategory(event)">
				<input type="button" id="addCat" value="Add Category" onclick="addcategory();">
			</div>
		</div>
		
		<div id="col">
			
		</div>
		<div id="col-right">
				
				<!--START Tabs -->
				<div id="bar">
					<a id="SideConcernsTop" name="SideConcernsTop"></a>
						<div class="tabber" id="myTab">
								<!-- AB 1 -->
						    <div id="sidebar_tags_header" class="tabbertab">
						    	<H2>Tags</H2>
						    	<div id="sidebar_tags">
						    		<!-- load tags into this div -->
									</div>
						    </div>
						    <!-- END TAB 1 -->
						    <!-- START TAB 2 -->
						    <div id="sidebar_concerns_header" class="tabbertab">
						    	<h2>Concerns</h2>
									<div id="sidebar_concerns"><h4>Concerns</h4>
										<!-- load concerns into this div -->
									</div>
						    </div>
						   <!-- END TAB 2 -->
						</div>
				</div>
				<!--END Tabs -->
		</div>
	
		<div style="clear:both"></div>
			
			<div id="col-crud-options" style="display:none;">
					<span class="closeBox">
						<a href="javascript: new Effect.PhaseOut('col-crud-options'); void(0);">
							<img src="images/close.gif" border="0" alt="Hide" name="Hide" class="button" style="margin-bottom:3px;" id="hide" onMouseOver="MM_swapImage('hide','','images/close1.gif',1)" onMouseOut="MM_swapImgRestore()">
							</a>
					</span>
				<strong>Editing Options: </strong>
					<input type="button" onclick="javascript: deleteSelectedCategory();" value="Delete">
					<input type="button" cnclick="javascript: new Effect.PhaseOut('col-crud-options'); void(0); new Effect.Appear('col-option'); void(0);" value="Rename" />
					<input type="button" onclick="javascript:duplicateSelectedCategory();" value="Duplicate" />
			</div>
			
			<div id="col-option" style="display: none;"><span class="closeBox"><a href="javascript: new Effect.PhaseIn('col-crud-options'); void(0); new Effect.Fade('col-option'); void(0);">back to all options</a></span>
				<h4>Editing Options</h4>
				Rename to: <form name="modifyCategory" action="" method="GET" onsubmit="javascript: modifySelectedCategory(); return false;"><input type="text" style="width: 50%;" id="selcatetext" onkeydown="checkaddcategory(event)"><input type="button" id="btnNewName" value="Modify" onclick="modifySelectedCategory();"></form><br>
			</div>

		<div id="spacer">
		</div>
		
			<div id="wysiwyg" style="padding-bottom: 10px;"> 
				<h3><span id="summaryEditorTitle">Click on the theme name to view/edit.</span></h3>
			
				<!-- Run javascript function after most of the page is loaded, work around for onLoad functions quirks with tabs.js -->	
				<script type="text/javascript">
					dosize();
					doOnLoad();
					getThemes();
					
					//var editor1 = new WYSIWYG_Editor('editor1', 'Please select a theme to begin generating summary.');
					  //  editor1.display();
				
				</script>				  
		     		<!-- END Run javascript function after most of the page is loaded, work around for onLoad functions quirks with tabs.js -->
				<!--<div id="frmSaveSummary" style="text-align: right"><span id="wysiwyg_message" style="position: display:none;"></span><button id="ss" type="button" disabled="true" onclick="saveSummary()">Save summery</button></div>-->
			</div>

	
			<div id="frmSaveSummary">
				<div id="editorWrap">	
					 <textarea id="sumtext" style="width:100%; height:140px;"></textarea>
					<div class="clearBoth"></div>
				</div>
				 <p>Tags: <input id="summaryTags" type="text" style="width: 200px"/> (comma separated)</p>
				 <p><button id="ss" type="button" disabled="true" onclick="saveSummary()">Save summary</button></p>
			</div>
		<div id="spacer" style="text-align: right;"></div>
</div>
</div>


<!--feedback form-->

<div style="margin-top:130px;margin-left: 10px;">
<pg:feedback id="feedbackDiv" action="cstview.do" />
</div>

<!--end feedback form-->

<!-- Start Footer -->
	<div id="header">
		<!-- Begin header -->
		<jsp:include page="/footer.jsp" />
		<!-- End header -->
	</div>
	<!-- End header -->
	<!-- Begin header menu - The wide ribbon underneath the logo -->
	<div id="headerMenu"></div>
<!-- End Footer -->
<script src="http://www.google-analytics.com/urchin.js" type="text/javascript">
</script>
<script type="text/javascript">
_uacct = "UA-797433-1";
urchinTracker();
</script>
</body>
</html>
    