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
 	<script src="/scripts/rico.js" type="text/javascript"></script>
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
		
		//window.onload="doOnLoad();
		var cctId = ${cctForm.cct.id};
    var tree1 = null;
    
    
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
      
      tree1=new dhtmlXTreeObject("col-left","100%","100%",0);
      tree1.setImagePath("/images/dhtmlXTree/");
      tree1.setDragHandler(moveNodeHandler);
      tree1.enableCheckBoxes(true)
      tree1.enableThreeStateCheckboxes(true);
      tree1.enableDragAndDrop(true);
      tree1.loadXML("/catsTree.do?cctId=${cctForm.cct.id}");
      tree1.cctId = cctId;
      tree1.setDragCopyHandler(copyNodeHandler);
      tree1.setDragDuplicateHandler(duplicateNodeHandler);
      tree1.setOnClickHandler(treeClickHandler);
/**/      
			//getCategories();
			//getOrphanTags();
			//getTags(12,1,1);
		}
		
		function preLoadImages(){
			var imSrcAr = new Array("line1.gif","line2.gif","line3.gif","line4.gif","minus2.gif","minus3.gif","minus4.gif","plus2.gif","plus3.gif","plus4.gif","book.gif","books_open.gif","books_close.gif","magazines_open.gif","magazines_close.gif","tombs.gif","tombs_mag.gif","book_titel.gif","iconCheckAll.gif")
			var imAr = new Array(0);
			for(var i=0;i<imSrcAr.length;i++){
				imAr[imAr.length] = new Image();
				imAr[imAr.length-1].src = "/images/dhtmlXTree/"+imSrcAr[i];
			}
		}	
		

		
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
							//collapseAll(["ol"]); openBookMark();
							
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
		
		function getOrphanTags(){
				CSTAgent.getOrphanTags({cctId:cctId, page:0, count: -1}, {
				callback:function(data){
						if (data.successful){
							$('sidebar_tags').innerHTML += data.html;
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

	function editTheme(value){
		alert(value);
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
			tree1.deleteSelectedItem();
	}
	
	function treeClickHandler(clickid, lastid, labeltext){
		//alert("clicked on: " + clickid + "; with text: " + labeltext);
		if(!tree1.clickedOn)
			tree1.unSelectAll();
		
		tree1.clickedOn = false;
		document.getElementById("newName").value = labeltext;
	}
	
	function moveNodeHandler(cateid,p0id, p1id){
		//alert("cateid=" + cateid + ", p0id=" + p0id + ", p1id=" + p1id);
		if(p0id == 0)var params = {cctId: cctId, categoryId: cateid,parent1Id: p1id};
		else var params = {cctId: cctId, categoryId: cateid, parent0Id: p0id, parent1Id: p1id};
		CSTAgent.moveCategory(params,{
			callback:function(data){
				if (data.successful){alert("will return true"); return true;}
				else return false;
			}
		});
	}
	
	function copyNodeHandler(cateid, parentid){
		
	}
	
	function duplicateNodeHandler(cateid, parentid){
		
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
   		padding: 0px 5px 0px 5px;
   		
   }

</style>
<event:pageunload />
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
				<input type="text" id="newcatetext" onkeydown="checkaddcategory(event)">
				<input type="button" value="Add categoey" onclick="addcategory();"><br>
				<input type="button" value="Delete" onclick="tree1.deleteSelectedItem();">
				<input type="text" id="newcatetext" onkeydown="checkaddcategory(event)">
				<input type="button" value="Edit" onclick="tree1.unSelectAll();"><br>
				<input type="checkbox" onclick="tree1.switchCopyMode()">Copy mode
				<p><a href="javascript: new Effect.toggle('col-crud-options', 'blind'); void(0); new Effect.BlindUp('col-option'); void(0);">Show Editing Options</a></p>
			</div>
		</div>
		<!-- End Overview -->

	
	<div id="cont-resize">
		<div id="col-left">
			<div id="col-crud-options" style="display:none;"><span class="closeBox"><a href="javascript: new Effect.BlindUp('col-crud-options'); void(0);">hide options</a></span>
				<h4>Editing Options</h4>
				<small>Delete&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;<a href="javascript: new Effect.BlindUp('col-crud-options'); void(0); new Effect.BlindDown('col-option'); void(0);">Rename</a>&nbsp;&nbsp;|&nbsp;&nbsp;Create Copy&nbsp;&nbsp;|&nbsp;&nbsp;Edit Summary</small>
			</div>
			<div id="col-option" style="display: none;"><span class="closeBox"><a href="javascript: new Effect.BlindDown('col-crud-options'); void(0); new Effect.BlindUp('col-option'); void(0);">back to all options</a></span>
				<h4>Editing Options</h4>
				Rename to: <input type="text" style="width: 50%;" id="newName"><input type="button" id="btnNewName" value="Submit"><br>
			</div>
		</div>
		
		<div id="col">
			
			Just a column
			<p>Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah</p>
		</div>
		<div id="col-right">
				<!--START Tabs -->
				<div id="bar">
					<a id="SideConcernsTop" name="SideConcernsTop"></a>
						<div class="tabber" id="myTab">
								<!-- AB 1 -->
						    <div id="sidebar_tags_header" class="tabbertab">
						    	<H2>Tags</H2>
						    	<div id="sidebar_tags"><h4>Tags</h4>
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
		</div>

		<div id="spacer">
		</div>
		
		<div id="slate" style="border: 0px solid #fff;">  <!-- how do i make this as big as the floating accordian div -->

			<h3>Concerns Summary Builder</h3>
			<p>Click on the theme name to view/edit</p>
				   <div id="accordionDiv" style="padding-bottom: 20px;">
				      <div id="overviewPanel">
				           <div id="overviewheader" class="accordionTabTitleBar ">Theme: Public Transportation</div>
				           <div id="overviewtext">
												<p id="editme1">Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam accumsan tincidunt libero. Nunc ut lectus. Sed ante urna, semper vitae, rhoncus consectetuer, porta vitae, urna. Donec in neque. Suspendisse porta, felis non malesuada consectetuer, massa erat interdum nunc, id luctus turpis metus ac sapien. Duis risus. Aenean tempor. Etiam porttitor elementum risus. Sed accumsan aliquet felis. Nunc tellus metus, commodo at, venenatis vitae, vehicula eu, orci. Curabitur nulla justo, lacinia id, sagittis eget, bibendum vitae, pede. Nulla id justo nec odio sagittis pulvinar. Maecenas iaculis. Nam vitae leo sed ligula molestie tempus. Ut vestibulum tortor eu nisi. Maecenas placerat. Morbi a libero. Cras sapien. Etiam placerat neque eu pede. Mauris lectus leo, rutrum sed, varius nec, pulvinar id, dui. Etiam imperdiet luctus elit. Vivamus dapibus odio et elit. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Curabitur aliquet tortor id justo. Integer quis metus ornare velit nonummy pellentesque. Aenean egestas, turpis nec dictum pulvinar, nulla est porttitor dui, cursus nonummy dolor tortor ut ligula. In porttitor elit vitae ligula. </p>
												
												<script type="text/javascript">
													new Ajax.InPlaceEditor('editme1', 'url', {rows: 10, cols: 70, onFailure: function(transport) { editTheme(this.id);}})
												</script>
				           </div>
				      </div>
				      <div id="anotherPanel">
				           <div id="overviewheader2"  class="accordionTabTitleBar ">Theme: Safety</div>
				           <div id="overviewtext2">
				           			<p id="editme2">Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam accumsan tincidunt libero. Nunc ut lectus. Sed ante urna, semper vitae, rhoncus consectetuer, porta vitae, urna. Donec in neque. Suspendisse porta, felis non malesuada consectetuer, massa erat interdum nunc, id luctus turpis metus ac sapien. Duis risus. Aenean tempor. Etiam porttitor elementum risus. Sed accumsan aliquet felis. Nunc tellus metus, commodo at, venenatis vitae, vehicula eu, orci. Curabitur nulla justo, lacinia id, sagittis eget, bibendum vitae, pede. Nulla id justo nec odio sagittis pulvinar. Maecenas iaculis. Nam vitae leo sed ligula molestie tempus. Ut vestibulum tortor eu nisi. Maecenas placerat. Morbi a libero. Cras sapien. Etiam placerat neque eu pede. Mauris lectus leo, rutrum sed, varius nec, pulvinar id, dui. Etiam imperdiet luctus elit. Vivamus dapibus odio et elit. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Curabitur aliquet tortor id justo. Integer quis metus ornare velit nonummy pellentesque. Aenean egestas, turpis nec dictum pulvinar, nulla est porttitor dui, cursus nonummy dolor tortor ut ligula. In porttitor elit vitae ligula. </p>
												
												<script type="text/javascript">
													new Ajax.InPlaceEditor('editme2', '/demoajaxreturn.html', {rows: 10, cols: 70});
												</script>	
				           	
				           </div>
				      </div>
				      <div id="anotherPanel2">
				           <div id="overviewheader3"  class="accordionTabTitleBar ">Theme: Traffic</div>
				           <div id="overviewtext3">
				           			<p id="editme3">Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam accumsan tincidunt libero. Nunc ut lectus. Sed ante urna, semper vitae, rhoncus consectetuer, porta vitae, urna. Donec in neque. Suspendisse porta, felis non malesuada consectetuer, massa erat interdum nunc, id luctus turpis metus ac sapien. Duis risus. Aenean tempor. Etiam porttitor elementum risus. Sed accumsan aliquet felis. Nunc tellus metus, commodo at, venenatis vitae, vehicula eu, orci. Curabitur nulla justo, lacinia id, sagittis eget, bibendum vitae, pede. Nulla id justo nec odio sagittis pulvinar. Maecenas iaculis. Nam vitae leo sed ligula molestie tempus. Ut vestibulum tortor eu nisi. Maecenas placerat. Morbi a libero. Cras sapien. Etiam placerat neque eu pede. Mauris lectus leo, rutrum sed, varius nec, pulvinar id, dui. Etiam imperdiet luctus elit. Vivamus dapibus odio et elit. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Curabitur aliquet tortor id justo. Integer quis metus ornare velit nonummy pellentesque. Aenean egestas, turpis nec dictum pulvinar, nulla est porttitor dui, cursus nonummy dolor tortor ut ligula. In porttitor elit vitae ligula. </p>
												
												<script type="text/javascript">
													new Ajax.InPlaceEditor('editme3', '/demoajaxreturn.html', {rows: 10, cols: 70});
												</script>
				           	
				           	
				           </div>
				      </div>
				      <div id="anotherPanel3">
				           <div id="overviewheader4"  class="accordionTabTitleBar ">Theme: [...]</div>
				           <div id="overviewtext4">
				           			<p id="editme4">Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam accumsan tincidunt libero. Nunc ut lectus. Sed ante urna, semper vitae, rhoncus consectetuer, porta vitae, urna. Donec in neque. Suspendisse porta, felis non malesuada consectetuer, massa erat interdum nunc, id luctus turpis metus ac sapien. Duis risus. Aenean tempor. Etiam porttitor elementum risus. Sed accumsan aliquet felis. Nunc tellus metus, commodo at, venenatis vitae, vehicula eu, orci. Curabitur nulla justo, lacinia id, sagittis eget, bibendum vitae, pede. Nulla id justo nec odio sagittis pulvinar. Maecenas iaculis. Nam vitae leo sed ligula molestie tempus. Ut vestibulum tortor eu nisi. Maecenas placerat. Morbi a libero. Cras sapien. Etiam placerat neque eu pede. Mauris lectus leo, rutrum sed, varius nec, pulvinar id, dui. Etiam imperdiet luctus elit. Vivamus dapibus odio et elit. 
								Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Curabitur aliquet tortor id justo. Integer quis metus ornare velit nonummy pellentesque. Aenean egestas, turpis nec dictum pulvinar, nulla est porttitor dui, cursus nonummy dolor tortor ut ligula. In porttitor elit vitae ligula. </p>
												
												<script type="text/javascript">
													new Ajax.InPlaceEditor('editme4', '/demoajaxreturn.html', {rows: 10, cols: 70});
												</script>
				           	
				           	
				           </div>
				      </div>
				   </div>		
				<script>new Rico.Accordion( $('accordionDiv'), {panelHeight:150});</script>

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
</script>

</body>
</html>
    