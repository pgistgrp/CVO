<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<!--####
	Project: Let's Improve Transportation!
	Page: Criteria Manager
	Description: Manage all criteria for a given workflow instance.
	Author(s): 
	     Front End: Jordan Isip, Adam Hindman
	     Back End: Zhong Wang, John Le
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[x] Barebones JavaScript (Jordan)
		[x] Integrate all form elements (Jordan)
		[ ] Refactor with SuiteId (Jordan and John)
#### -->
<html:html>
<head>
<title>Manage Criteria</title>
<!-- Site Wide JavaScript -->
<script src="scripts/tags.js" type="text/javascript"></script>
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects" type="text/javascript"></script>
<script src="scripts/search.js" type="text/javascript"></script>
<!-- End Site Wide JavaScript -->

<!-- DWR JavaScript Libraries -->
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<!-- End DWR JavaScript Libraries -->

<!--Criteria Specific  Libraries-->
<script src="scripts/SideBar.js" type="text/javascript"></script>
<script type='text/javascript' src='/dwr/interface/CriteriaAgent.js'></script>

	<script type="text/javascript" charset="utf-8">
		//GLOBAL VARS
		selectedObjectives = [];
		objectivesList = [];
		critSuiteId = <%=request.getParameter("critSuiteId")%>;
		//END GLOBAL VARS

		/* *************** Grab All Criteria in the System - uses criteria.jsp *************** */
		function getCriteria(){
			CriteriaAgent.getAllCriterionForMgr({critSuiteId:critSuiteId}, {
				callback:function(data){
					if (data.successful){
						$('allCriteriaList').innerHTML = data.html;
					}else{
						$('allCriteriaList').innerHTML = "<b>Error in CriteriaAgent.getAllCriterion Method: </b>" + data.reason; 
					}
				},
				errorHandler:function(errorString, exception){ 
				alert("CriteriaAgent.getAllCriterion( error:" + errorString + exception);
				}
			});
		}
		
		/* *************** Add a New Criteria to the List *************** */
		function addCriterion(){
			var objectivesArr = '' //grabs from multi select list
			//DWR Test: {name:"testCriterion", themeIds: "2523,2528", objectiveIds: "1185", cctId: 1180, na:"this is a test"}
			
			var name = $('name').value;
			//##### var description = $('description').value;
			var objectivesGroup = document.getElementsByName('objectivesGroup');
			var checkedObjectives = []; //holds all the checked objectiveId's that are found in the following loop
			
			//loop through objectives
			for (i = 0; i < objectivesGroup.length; i++)
			{
				if(objectivesGroup[i].checked){
					checkedObjectives.push(objectivesGroup[i].id.replace("objective-", ""));				
				}
			}
			
			var checkedObjectivesStr = checkedObjectives.toString();
			
			//alert("names: " + name + " description" + description + " objectiveIds: " + checkedObjectivesStr); // Between name:name and ThemeIds is na:description;
			CriteriaAgent.addCriterion({critSuiteId:critSuiteId,name:name,themeIds:"",objectiveIds:checkedObjectivesStr}, {
				callback:function(data){
					if (data.successful){
						//highlight newly created criterion
						getCriteria();
						//reset form
						$('addPlanningFactor').reset();
					}else{
						alert(data.reason);
					}
				},
				errorHandler:function(errorString, exception){ 
				alert("CriteriaAgent.addCriterion( error:" + errorString + exception);
				}
			});
		}
		
		/* *************** Add a new Objective to the List *************** */
		function addObjective(id){
			var description = $('newObjective').value;
			if(id){
			description=$('newObjective'+id).value;
			}
			//alert("description: " + description ); 
			CriteriaAgent.addObjective({description:description}, {
				callback:function(data){
					if (data.successful){
						if(id){
							getObjectives(id);
						}else{
							getObjectives();
						}
						//reset form
						if(id){
							$('newObjective'+id).value='';
						}
						$('newObjective').value = '';
					}else{
						alert(data.reason);
					}
				},
				errorHandler:function(errorString, exception){ 
				alert("CriteriaAgent.addObjective( error:" + errorString + exception);
				}
			});
		}
		
		/* *************** Reveal a form to allow editing on a given criterion writes the general html form for editing *************** */
		function editCriterionPopup(id) {
			if($('criteriaEdit'+id).style.display=="inline"){
				$('criteriaEdit'+id).style.display="none";
			}else{
			//fill in fields
			//code to display edit fields
			$('criteriaEdit' + id).innerHTML = "";
			filler="";
			filler+="<fieldset style='float:left;'>";
			filler+="<form id='editPlanningFactor"+id+"' name='editPlanningFactor"+id+"'>";
			filler+="<h3>Edit Planning Factor</h3>";
			filler+="<br />";
			filler+="<label for='editName"+id+"' class='niceFormElement'>Factor Name</label>";
			filler+="<input id='editName"+id+"' name='editName"+id+"' type='text' class='niceFormElement' /><br />";
			
			//#### filler+="<label for='editDescription"+id+"' class='niceFormElement'>Description</label>";
			//filler+="<textarea id='editDescription"+id+"' name='editDescription"+id+"' class='niceFormElement'></textarea><br />";
			
			//filler+="<br />";
			
			filler+="<label for='editObjectivesDiv"+id+"' style='float:left;' class='niceFormElement'>Factor Objectives</label>";

			filler+="<div id='editObjectivesDiv"+id+"'>";
				<!-- load objectives here - getObjectives() -->
			filler+="</div>";

			filler+="<br /><p />";
			
				filler+="<label for='newObjective"+id+"' class='niceFormElement'>Add a New Objective</label>";
				filler+="<input type='text' id='newObjective"+id+"' style='width:300px;'>";
				filler+="<input type='button' style='margin-left:10px;' value='Add to List' onClick='javascript:addObjective("+id+");'>";

			filler+="<br />";
			

			filler+="<div id='list"+id+"' style='margin-top:1em;margin-bottom:1em;' class='indentNiceForm'>";
			
			filler+="</div>";


			
			filler+="<input type='button' value='Save Planning Factor Edits' onClick='editCriterion("+id+");'/>";
			filler+="<input type='reset' value='Clear Form' />";
			filler+="<br />";
			filler+="</form>";
		filler+="</fieldset>";
			$('criteriaEdit'+id).innerHTML=filler;
			fillEditForm(id);
			
			//new Effect.BlindDown('criteriaEdit'+id);
			$('criteriaEdit'+id).style.display="inline";
			
			}
		}
		
		/***************Fills a given edit form with the specific data for a given criterion ID*********/
		function fillEditForm(critId){
			CriteriaAgent.getCriterionById({id:critId},{
				callback:function(data){
					if(data.successful){
						$('editName'+critId).value=data.criterion.name;
						//##### $('editDescription'+critId).value=data.criterion.na;					
						$('editObjectivesDiv'+critId).innerHTML=getObjectives(critId);

					}else{
						alert("getCriterionById failed in fillEditForm");
					}
				},
				errorHandler:function(errorString,exception){
					alert("CriteriaAgent.getCriterionById in fillEditForm(error:"+ errorString+exception);
				}
			});
		}
		
		/* *************** Saves the changes for an edited Criterion *************** */
		function editCriterion(id){
			//alert("name: " + name + " description: " + description + " themes: " + themes + " objectivesArr: " + objectivesArr); 
			//grabs from multi select list
			var objectivesArr = document.getElementsByName('editObjectivesGroup-'+id); //grabs from multi select list
			var name = $('editName'+id).value;
			//##### var description = $('editDescription'+id).value;
			var checkedObjectives=[];
			
			for (i = 0; i < objectivesArr.length; i++)
			{
				if(objectivesArr[i].checked){
					checkedObjectives.push(objectivesArr[i].id.replace("editObjective-", ""));				
				}
			}
			
			checkedObjectivesStr=checkedObjectives.toString();
			
			if(checkedObjectivesStr==''){
				checkedObjectivesStr=',';
			}
			
			
			//alert("id: " + id +" name: " + name + " description" + description + " objectives: " + objectivesArr);
			CriteriaAgent.editCriterion({critId:id,name:name,themeIds:"",objectiveIds:checkedObjectivesStr}, {
				callback:function(data){
					if (data.successful){
						getCriteria();
					}else{
						alert(data.reason);
					}
				},
				errorHandler:function(errorString, exception){ 
				alert("CriteriaAgent.addCriterion( error:" + errorString + exception);
				}
			});
		}
		
		/* *************** Delete a given criterion *************** */
		function deleteCriterion(id){
			var destroy = confirm ("Are you sure you want to delete this planning factor? Note: there is no undo.")
			if(destroy){
				CriteriaAgent.deleteCriterion({id:id}, {
					callback:function(data){
						if (data.successful){
							new Effect.DropOut("criteria-" + id, {afterFinish:function(){getCriteria();}})
						}else{
							alert(data.reason);
						}
					},
					errorHandler:function(errorString, exception){ 
					alert("CriteriaAgent.deleteCriterion( error:" + errorString + exception);
					}
				});
			}
		}
		
		/* *************** Delete a given objective *************** */
		function deleteObjective(oid){
			var destroy = confirm ("Are you sure you want to delete this objective? Note: there is no undo.")
			if(destroy){
				CriteriaAgent.deleteObjective({id:oid}, {
					callback:function(data){
						if (data.successful){
							new Effect.Puff("objectiveCont-" + oid, {afterFinish:function(){getCriteria();getObjectives();}})
						}else{
							alert(data.reason);
						}
					},
					errorHandler:function(errorString, exception){ 
					alert("CriteriaAgent.deleteCriterion( error:" + errorString + exception);
					}
				});
			}
		}		
		

		/* *************** Grab all objectives for the current instance *************** */
		function getObjectives(id){
			CriteriaAgent.getObjectives({}, {
				callback:function(data){
					if (data.successful){
						if(id){
						
						temp=(data.html).replace(/objective-/g,"editObjective-");
						temp2=temp.replace(/objectivesGroup/g,("editObjectivesGroup-"+id));
						
							$('editObjectivesDiv'+id).innerHTML=temp2;
							getObjectivesToEditByCriterionId(id);
						}
						$('objectives').innerHTML = data.html;
						
						
					}else{
						alert(data.reason);
					}
				},
				errorHandler:function(errorString, exception){ 
				alert("CriteriaAgent.getObjectives( error:" + errorString + exception);
				}
			});
		}
		
	
		/* *************** Get List of Selected Objectives *************** */
		function getSelectedObjectives() {
			var len = $('objectives').options.length;
			for (var i = 0; i < len; i++) {
				if ($('objectives').options[i].selected) 
				{
					objectivesList.push($('objectives').options[i].firstChild.nodeValue);
					paintObjectivesList();	
				}
			}
		} 
		
	
		function buildObjectivesArray(){
			var newObjective = $('newObjective').value;
			objectivesList.push(newObjective);
			paintObjectivesList();		
		}
	
		function removeObj(objID){
			objectivesList.splice(objID,1);
			paintObjectivesList();
		}
	
		function paintObjectivesList(){		
				var listText = '<h3>Objectives Attached to This Factor</h3><small><strong>Important</strong>: Check for duplicates before submitting</small><ul>';
				var listBox = $('list');
				listBox.innerHTML = listText;
				for (var i = 0; i < objectivesList.length; i++){
					listBox.innerHTML += '<li>' + objectivesList[i] + '<a href="javascript:removeObj('+ i +');"> <img src="images/removeItem_lite.gif" style="border:0px" alt="Remove this objective"></a></li>';
				}
		}
		
		/* *************** Toggle Tree Node to view Asscociated Objectives for a Given Criterion *************** */
		//Function used in SD - so it needs io object
		var io = new Object;
		io.expandList = function(objective,icon){
			Effect.toggle(objective, 'appear', {duration: .5, afterFinish:
				//window.setTimeout(toggleIcon,100);
				function(){
					if ($(objective).style.display != ""){
							$(icon).src = "/images/plus.gif";
						}else{
							$(icon).src = "/images/minus.gif";
						}
					}
			});
		};
		
		
		/******Puts checkmarks next to the Objectives that are already part of a criterion, given its ID.****/
		/***Must have the edit form made and getObjectives(cid) run before***/
		/***This is called by getObjectives***/
		function getObjectivesToEditByCriterionId(cid){
			CriteriaAgent.getCriterionById({id:cid},{
				callback:function(data){
				if(data.successful){
				
					for(a=0;a<data.criterion.objectives.length;a++){
						
						$('editObjective-'+data.criterion.objectives[a].id).checked=true;
					}
					
				}else{
					alert("error in getObjectovesToEditByCriterionId");
				}
			
			},
			errorHandler:function(errorString, exception){ 
					alert("getObjectivesToEditByCriterionId error:" + errorString + exception);
				}
			});
		}

		function checkLength(area,maxLength){
			var fieldLength = $F(area).length;
			$(area + 'Warning').innerHTML = maxLength - fieldLength + ' characters left'; 
			if (fieldLength >= maxLength) {
				$(area + 'Warning').innerHTML = "<span>Too long!</span>";
				$(area + 'Warning').style.color = "red";			
			} else {
				$(area + 'Warning').style.color = "";
				$(area + 'Warning').innerHTML = '<span>' + (maxLength - fieldLength) + '</span> left'; 	
			}
		}
	</script>


<style type="text/css">

body {font-family:arial;font-size:10pt;}

.assocList li{
list-style: none;
}

.clearBoth {clear:both;}
.floatLeft {float:left}

#criteria {width:620px;}

.smallText
{
font-size:.8em;
}

.criteriaListRow
{
background:#E7F2F7;
padding:.3em 0em;
}

.criteriaListHeader
{
background:#fff;
}

#allCriteriaList
{
text-align:left;
}

.even {background: #ffffff}

.criteriaCol1
{
width:250px;
margin-right:.5em;
}

.criteriaCol1 img
{
margin:0px 3px 0px 0px;
vertical-align:middle;
border:0px;
}

.criteriaCol2
{
width:320px;
}

.criteriaCol3
{
margin-left:.5em;
width:190px;
}	

h4
{
font-size:1em;
margin:0px;
padding:0px;
}

.objectives
{
padding:.5em;
}

h3
{padding:0px;margin:0px;}

.niceFormElement{
display: block;
width: 150px;
float: left;
margin-bottom: 10px;
}

label.niceFormElement {
text-align: right;
width: 75px;
padding-right: 20px;
}

br {
clear: left;
}

.indentNiceForm{
margin-left: 95px;
margin-top: 0px;
padding:0;
}

textarea.niceFormElement, select.niceFormElement{
width: 500px;
height: 70px;

}

select.niceFormElement{
margin:0;
padding: 0;
}

fieldset{
}

.box3 /* Used in the DIV containing the Summary */
{
border:1px solid #D6E7EF;
background:#F7FBFF;
}

.errorMessage{
background: #FFC4BF;
border: 3px solid #BC0F00;
color: #840A00;
padding: 5px;
}

#descriptionWarning {text-align:center;}
#descriptionWarning span {font-size:1.2em;}
</style>
	<event:pageunload />
	</head>
	<body>
		<a href="main.do">Back Home</a>
		<h2>Manage Planning Factors for Experiment: ${cct.name}</h2>
		<!-- Begin list of planning factors -->
		
		<!-- START All Criteria List -->
			<div id="criteria" class="box3">
				<div id="allCriteriaList">
					<p><img src="images/indicator_arrows.gif" /> Loading Planning Factors ...</p>
					<!-- Load getCriteria() here - using criteria.jsp -->
				</div>
			</div>

				<!-- END All Criteria List -->

		<!-- End List of planning Factors -->
		<!-- Begin form to add a new planning factor -->
		<p><a href="javascript:location.href='#publish';new Effect.Highlight('publish'); void(0);">Finished?</a></p>
		<br />
		<fieldset style="float:left;">
			<form id="addPlanningFactor" name="addPlanningFactor">
			<h3>Add a New Planning Factor</h3>
			<br />
			<label for="name" class="niceFormElement">Factor Name</label>
			<input id="name" name="name" type="text" class="niceFormElement" 
				onkeydown="if (event.keyCode==13){addCriterion();}"/><br />
			
			<!--#####<div style="display:none"><label for="name" class="niceFormElement">Description<br/><span id="descriptionWarning"><span>2000</span> left</span></label>
			<textarea id="description" name="description" class="niceFormElement" onKeyPress="checkLength('description',2000)"></textarea><br /></div>-->

			<label for="name" class="niceFormElement">Factor Objectives</label>
			<div id="objectives">
				<!-- load objectives here - getObjectives() -->
			</div>

			<br /><p />
			
				<label for="name" class="niceFormElement">Add a New Objective</label>
				<input type="text" id="newObjective" 
					onkeydown="if (event.keyCode==13){addObjective();}" style="width:300px;">
				<input type="button"  style="margin-left:10px;" value="Add to List" 
					onClick="javascript:addObjective();">

			<br />
			

			<div id="list" style="margin-top:1em;margin-bottom:1em;" class="indentNiceForm">
			
			</div>


			
			<input type="button" value="Save and Add Planning Factor" onClick="addCriterion();"/>
			<input type="reset" value="Clear Form" />
			<br />
			</form>
		</fieldset>

		<!-- End form to add a new planning factor -->
		<!-- Begin publishing options -->
		<div style="clear:both"></div>
		<div id="publish">
			<a name="publish"></a>
			<br />
			<h4>Finished Creating Planning Factors?</h4>

			<p><input type="button" style="padding:5px" onClick="location.href='userhome.do'" 
				value="Finished!"/></p>

			<p>The workflow will publish these planning factors on: --workflow date--</p>
			<div class="errorMessage" id="publishError" style="display:none;"></div>

			<!--end publishing options -->
		</div>
		<script type="text/javascript">
			getCriteria();
			getObjectives();
		</script>
	</body>
</html:html>

