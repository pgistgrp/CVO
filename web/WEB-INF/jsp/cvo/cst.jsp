<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>CST</title>

	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<!--CSS Libraries -->

	<style type="text/css" media="screen">@import "/styles/dhtmlXTree/dhtmlXTree.css";</style>
	<style type="text/css" media="screen">@import "/styles/position.css";</style>
	<style type="text/css" media="screen">@import "/styles/styles.css";</style>
	<style type="text/css" media="screen">@import "/styles/tabs.css";</style>

 	<!--JavaScript Libraries -->
 	<script src="/scripts/prototype.js" type="text/javascript"></script>
 	<script src="/scripts/rico_simple.js" type="text/javascript"></script>
 	<script src="/scripts/tabcookies.js" type="text/javascript"></script>
 	<script src="/scripts/tabs.js" type="text/javascript"></script>
	<script src="/scripts/scriptaculous.js?load=effects,controls" type="text/javascript"></script>
	<script src="/scripts/tags.js" type="text/javascript"></script>
	
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
	<!-- End Template 5 Specific -->
	
	<script type="text/javascript">
		///////////////////////////////////////////////////new change/////////////////////////
		//window.onload="doOnLoad();
		var cctId = ${cctForm.cct.id};
    var tree1 = null;
    var currentCategory = null;
    
    function preLoadImages(){
      var imSrcAr = new Array("line1.gif","line2.gif","line3.gif","line4.gif","minus2.gif","minus3.gif","minus4.gif","plus2.gif","plus3.gif","plus4.gif","book.gif","books_open.gif","books_close.gif","magazines_open.gif","magazines_close.gif","tombs.gif","tombs_mag.gif","book_titel.gif","iconCheckAll.gif")
      var imAr = new Array(0);
      for(var i=0;i<imSrcAr.length;i++){
        imAr[imAr.length] = new Image();
        imAr[imAr.length-1].src = "/images/dhtmlXTree/"+imSrcAr[i];
      }
    }
    
		function doOnLoad(){
			//preLoadImages();
      resetCols()
      tree1=new dhtmlXTreeObject("col-left","100%","100%",0);
      tree1.setImagePath("/images/dhtmlXTree/");
      tree1.setDragHandler(moveNodeHandler);
      tree1.enableCheckBoxes(true)
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
			CSTAgent.getThemes({cctId:cctId,asHTML:true}, {
			callback:function(data){
				if (data.successful){
					$('accordionDiv').innerHTML= data.html;
			 		for(var i=0;i < data.idList.length;i++){
			 			 
       			 var temp = new Ajax.InPlaceEditor('editSummary'+ data.idList[i], 'nopage.html', 
       			 {highlightendcolor: '#EEEEEE', okText:'Save Summary', rows: 5, cols: 5, callback: function(form, value) 
       			 { saveSummary(this.formId,value); }, onFailure: function(){}}); 
       			 
      		}
				  themeAccordian =  new Rico.Accordion($('accordionDiv'), {panelHeight:150});
				  activateTab(editingThemeId);
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
					alert(errorString + exception);
			}
			});
	
		}
		var themeAccordian = null;
		
		function activateTab(themeId){
			if (themeId == ''){return;}
			for (var i=0; i < themeAccordian.accordionTabs.length; i++){
				var tempId = themeAccordian.accordionTabs[i].content.id.replace(/[a-zA-Z\-]/g,'');
				if(tempId == (themeId)){
					themeAccordian.accordionTabs[i].showExpanded();
					themeAccordian.showTab(themeAccordian.accordionTabs[i]);
				}else{
					themeAccordian.accordionTabs[i].showCollapsed();
				}
			}
		}
		
		
		function saveSummary(themeId, summaryString){
			themeId = themeId.replace(/[a-zA-Z\-]/g, '');
			editingThemeId = themeId;
			summaryString = keepBreaks(summaryString);
			
			CSTAgent.saveSummary({cctId:cctId, themeId:themeId, summary:summaryString}, {
			callback:function(data){
				if (data.successful){
					$('editSummary'+ themeId).innerHTML = summaryString;
					getThemes(themeId);
					
				
					
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
					alert(errorString + exception);
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

		function getTags(categoryId, page, type, orphanpage){
			CSTAgent.getTags({cctId:cctId, categoryId:categoryId, page:page, type: type, orphanPage:orphanpage}, {
			callback:function(data){
				if (data.successful){
					if (type == 0){
						document.getElementById('col').innerHTML = '<h4>Tags within ' + currentCategory.label + '</h4>';
						document.getElementById('col').innerHTML += data.html;
					}
					if (type == 1){
						////todo: get rid of the first line, check the jsp template
						$('sidebar_tags').innerHTML = '<h4>Tags not in ' + currentCategory.label + '</h4>';
						$('sidebar_tags').innerHTML += data.html;
					}
				}else{
					alert("Getting tags not successful: " + data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
					showTheError();
			}
			});
		}

		function relateTag(tagId){
			if(currentCategory == null)return;
			CSTAgent.relateTag({cctId:cctId, categoryId:currentCategory.dataId, tagId:tagId}, {
			callback:function(data){
					if (data.successful){
						new Effect.Fade('tag' + tagId, {duration: 0.5, afterFinish: function(){getTags(currentCategory.dataId, 0, 1, tagId);getTags(currentCategory.dataId, 0, 0, tagId);}});
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
									new Effect.Fade('tag'+tagId, {duration: 0.5, afterFinish: function(){getTags(categoryId, 0, 1, tagId);getTags(categoryId, 0, 0, tagId);}});

								 
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
							$('myTab').tabber.tabShow(1);
							$('sidebar_concerns').innerHTML = data.html;
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
	//--------------------------------------
	function checkaddcategory(e){
		if(e.keyCode == 13)addcategory();
	}
	
	function addcategory(){
		if(document.getElementById("newcatetext").value != ""){
			var catname = document.getElementById("newcatetext").value;
			var parentId = (tree1.lastSelected) ? tree1.lastSelected.parentObject.dataId : 0;
			//alert("new cate: " + catname + "; parent=" + parentId);
			CSTAgent.addCategory({cctId:cctId, parentId:parentId, name:catname},{
				callback:function(data){
					if (data.successful){ 
						tree1.insertNewItemUnderSelected(document.getElementById("newcatetext").value,
						data.newId);
						document.getElementById("newcatetext").value = "";
						getThemes();
						/////to do: display a message...
					}
					
					if (data.successful != true){
						alert(data.reason);
					}
				},
				errorHandler:function(errorString){ alert("oops, exception: " + errorString);}
			});			
		}
	}

	function globalKeyHandler(e){
		if(e.keyCode==46)
			deleteSelectedCategory();
	}
		
	function deleteSelectedCategory(){
		if(tree1.lastSelected!=null){
			if(confirm("Are you sure you want to delete category \"" + tree1.lastSelected.parentObject.label + "\"")){
				if(tree1.lastSelected.parentObject.parentObject.id == 0)
					var params = {cctId: cctId, categoryId: tree1.lastSelected.parentObject.dataId};
				else
					var params = {cctId: cctId, categoryId: tree1.lastSelected.parentObject.dataId,parentId: tree1.lastSelected.parentObject.parentObject.dataId}
	
				CSTAgent.deleteCategory(params, {
							callback:function(data){
								if (data.successful){
									tree1.deleteSelectedItem();
									new Effect.PhaseOut('col-crud-options');
									getThemes(); 
								}
							}
				});
			}
		}
	}

	function modifySelectedCategory(){
		var newtext = document.getElementById("selcatetext").value;
		if(tree1.lastSelected!=null && newtext!=""){
			var params = {cctId: cctId, categoryId: tree1.lastSelected.parentObject.dataId, name:newtext};
			CSTAgent.editCategory(params, {
						callback:function(data){
							if (data.successful){
								tree1.modifyItemName(tree1.lastSelected.parentObject.dataId, newtext);
								new Effect.Fade('col-option'); 
								getThemes();
							}else
								alert(data.reason);
						}
			});
		}
	}
	
	function treeClickHandler(clickid, lastid, labeltext){
		
		document.getElementById("selcatetext").value = labeltext;
		currentCategory = tree1.lastSelected.parentObject;
		alert(currentCategory.dataId);
		//get tags and populate the pane
		//$('catetagstitle') = "Tags associated with category \"" + labeltext + "\"";
		getTags(clickid, 0, 0,1);
		getTags(clickid, 0, 1, 1);
		//getTags(clickid, 0, 1);
		if ($('col-crud-options').style.display == 'none'){
		new Effect.PhaseIn('col-crud-options'); 
	}else{
		new Effect.Highlight('col-crud-options');
	}
		new Effect.Fade('col-option');
		location.href="#colsTop";
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
		params = {cctId: cctId, categoryId: sourceO.dataId, parent0Id: sourceO.parentObject.dataId, parent1Id: targetO.dataId};
		CSTAgent.moveCategory(params,{
			callback:function(data){
				if (data.successful){
					var newID=tree1._moveNode(sourceO,targetO);
					tree1.selectItem(newID);
					getThemes();
					return true;
				}
				else return false;
			}
		});
	}
	
	function copyNodeHandler(sourceO, targetO){
		CSTAgent.copyCategory({cctId: cctId, categoryId: sourceO.dataId, parentId: targetO.dataId},{
			callback:function(data){
				if (data.successful){
					var newID=tree1._copyNodeTo(sourceO,targetO);
					tree1.selectItem(newID);
					getThemes();
					return true;
				}
				else return false;
			}
		});
	}
	
	function duplicateSelectedCategory(){
		if(tree1.lastSelected!=null){
			var obj1 = tree1.lastSelected.parentObject;
			var params = {cctId:cctId,categoryId:obj1.dataId, name:"Similar to "+ obj1.label};
			if(obj1.parentObject.Id!=0)
				params.parentId = obj1.parentObject.dataId;
			CSTAgent.duplicateCategory(params, 
			{callback:function(data){
				if(data.successful){
					var newitem = tree1.insertNewItem(obj1.parentObject.id,data.newId,"Similar to "+ obj1.label);
					tree1.selectItem(newitem.id);
					
					//getTags(data.newId, 0, 0);
					treeClickHandler(data.newId, data.newId, "Similar to "+ obj1.label)
					getThemes();
					if ($('col-crud-options').style.display == 'none'){
						new Effect.PhaseIn('col-crud-options'); 
					}else{
						new Effect.Highlight('col-crud-options');
					}
					new Effect.Fade('col-option');
					location.href="#colsTop";
					
				}else
					alert(data.reason);
			}});
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

</style>
</head>

<body onResize="dosize()" onkeydown="globalKeyHandler(event);">
<div id="container">

	<div id="overlay"></div>
	<div id="lightbox"></div>
	<!-- END LIGHTBOX -->
	<div id="pageTitle">
		<h1>Moderator Dashboard: </h1><h2>Concerns Synthesis Tool</h2>
	</div>	

		<!-- Overview SpiffyBox -->
		<div class="cssbox">
			<div class="cssbox_head">
				<h3>Overview and Instructions</h3>
			</div>
			<div class="cssbox_body">
				<p>Lorem Ipsum [...]</p>
			</div>
		</div>
		<!-- End Overview -->

	
	<div id="cont-resize">
				<a name="colsTop"></a>
				<input type="text" id="newcatetext" onkeydown="checkaddcategory(event)">
				<input type="button" value="Add Category" onclick="addcategory();"><br>
				<input type="button" value="Unselect All" onclick="unselectall(true);">

			<input type="checkbox" onclick="tree1.switchCopyMode()">Copy mode <small>(or ctrl + drag)</small>
		
		<div id="col-left" style="text-transform: capitalize; " onclick="unselectall(!tree1.clickedOn)">
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
	
		<div id="clear">
			<div id="col-crud-options" style="display:none;"><span class="closeBox"><a href="javascript: new Effect.PhaseOut('col-crud-options'); void(0);">hide options</a></span>
				<h4>Editing Options</h4>
				<small><a href="javascript: deleteSelectedCategory();">Delete</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;<a href="javascript: new Effect.PhaseOut('col-crud-options'); void(0); new Effect.Appear('col-option'); void(0);">Rename</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="javascript:duplicateSelectedCategory();">Duplicate</a>&nbsp;&nbsp;|&nbsp;&nbsp;Edit Summary</small>
			</div>
			<div id="col-option" style="display: none;"><span class="closeBox"><a href="javascript: new Effect.PhaseIn('col-crud-options'); void(0); new Effect.Fade('col-option'); void(0);">back to all options</a></span>
				<h4>Editing Options</h4>
			Rename to: <form name="modifyCategory" action="" method="GET" onsubmit="javascript: modifySelectedCategory(); return false;"><input type="text" style="width: 50%;" id="selcatetext" onkeydown="checkaddcategory(event)"><input type="button" id="btnNewName" value="Modify" onclick="modifySelectedCategory();"></form><br>
			</div>
		</div>

		<div id="spacer">
		</div>
		
		<div id="slate" style="border: 0px solid #fff;">  <!-- how do i make this as big as the floating accordian div -->

			<h3>Step _ -Create a Summary for Each Theme</h3>
			<p>Click on the theme name to view/edit</p>
				   <div id="accordionDiv" style="padding-bottom: 20px;">
							<!--Insert themes here -->
				   </div>		
				 
		</div>
	</div>
	<!--
	<div id="footer">
	Footer and NSF
	</div>
		-->
	<div id="clear">
	</div>
	

	<div id="spacer">
	</div>
	

</div>

<!-- Run javascript function after most of the page is loaded, work around for onLoad functions quirks with tabs.js -->
<script type="text/javascript">
dosize();
doOnLoad();
getThemes();
</script>

</body>
</html>
    