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
	     Front End: Jordan Isip, Adam Hindman, Issac Yang
	     Back End: Zhong Wang, John Le
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[ ] Barebones JavaScript (Jordan)
		[ ] Integrate all form elements (Jordan)
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
		cctId = "${cct.id}"
		
		//END GLOBAL VARS
		window.onLoad = doOnLoad();
		
		function doOnLoad(){
			getCriteria();
			getThemes();
			getObjectives();
		}
		
		/* *************** Grab All Criteria in the System - uses criteria.jsp *************** */
		function getCriteria(){
			CriteriaAgent.getAllCriterion({}, {
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
			var themesArr = getOptionValueFromObjects(document.getElementById('themes').options); //grabs from multi select list
			//var objectivesArr = getOptionValueFromObjects(document.getElementById('objectives').options); ; //grabs from multi select list
			//DWR Test: {name:"testCriterion", themeIds: "2523,2528", objectiveIds: "1185", cctId: 1180, na:"this is a test"}
			
			var name = $('name').value;
			var description = $('description').value;
			
			alert("names: " + name + " description" + description + " themes: " + themesArr + " objectives: " + selectedObjectives);
			/*CriteriaAgent.addCriterion({name:name,na:description,themesArr:themesArr,objectivesArr:objectivesArr}, {
				callback:function(data){
					if (data.successful){
						//highlight newly created criterion
						getCriteria();
					}else{
						alert(data.reason);
					}
				},
				errorHandler:function(errorString, exception){ 
				alert("CriteriaAgent.addCriterion( error:" + errorString + exception);
				}
			});*/
		}
		
		/* *************** Reveal a form to allow editing on a given criterion *************** */
		function editCriterionPopup(id) {
			//code to display edit fields
			$('criteriaEdit' + id).innerHTML = "";
			//code to display edit fields
		}
		
		/* *************** Produce a form to edit a given criterion *************** */
		function editCriterion(id){
			//alert("name: " + name + " description: " + description + " themes: " + themes + " objectivesArr: " + objectivesArr); 
			var themesArr = getOptionValueFromObjects($('themes').options); //grabs from multi select list
			var objectivesArr = getOptionValueFromObjects($('objectives').options); ; //grabs from multi select list
			var name = $('name').value;
			var description = $('description').value;
			
			//alert("id: " + id +" name: " + name + " description" + description + " themes: " + themesArr + " objectives: " + objectivesArr);
			CriteriaAgent.editCriterion({id:id,name:name,description:description,themesArr:themesArr,objectivesArr:objectivesArr}, {
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
			CriteriaAgent.deleteCriterion({id:id}, {
				callback:function(data){
					if (data.successful){
						new Effect.Puff("criteria" + id, {afterFinish:function(){getCriteria();}})
					}else{
						alert(data.reason);
					}
				},
				errorHandler:function(errorString, exception){ 
				alert("CriteriaAgent.deleteCriterion( error:" + errorString + exception);
				}
			});
		}
		
		/* *************** Grab all themes for the current instance *************** */
		function getThemes(){
			//alert("cctId: " + cctId); 
			CriteriaAgent.getThemes({cctId:cctId}, {
				callback:function(data){
					if (data.successful){
						$('themes').innerHTML = data.html;
					}else{
						alert(data.reason);
					}
				},
				errorHandler:function(errorString, exception){ 
				alert("CriteriaAgent.getThemes( error:" + errorString + exception);
				}
			});
		}
		
		/* *************** Grab all objectives for the current instance *************** */
		function getObjectives(){
			CriteriaAgent.getObjectives({}, {
				callback:function(data){
					if (data.successful){
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
		
		/* *************** Grab the value from a multi-select list *************** */
		function getOptionValueFromObjects(optionObjs) {
			cleanList = [];
		    for (var i = 0; i < optionObjs.length; i++) {
		        if (optionObjs[i].selected) {
					cleanList.push(optionObjs[i].value)
		        }
			}
		    return cleanList
		}
		
		function publish(){
			CriteriaAgent.publish({cctId:cctId}, {
				callback:function(data){
					if (data.successful){
						alert("Publish Successful")
						//Redirect to discussion
					}else{
						alert(data.reason);
					}
				},
				errorHandler:function(errorString, exception){ 
				alert("ClassName.methodName( error:" + errorString + exception);
				}
			});
		}
	
		/* *************** Toggle Tree Node to view Asscociated Objectives for a Given Criterion *************** */
		function expandList(objective,icon){
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
	</script>


		<style type="text/css">
		
		.clearBoth {clear:both;}
		.floatLeft {float:left}
		
		#criteria {width:800px;}
		
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
		width:210px;
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
		width:330px;
		}
		
		.criteriaCol3
		{
		margin-left:.5em;
		width:220px;
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
		</style>
	</head>
	<body>
		<h2>Manage Planning Factors</h2>
		<!-- Begin list of planning factors -->
		
		<!-- START All Criteria List -->
			<div id="criteria" class="box3">
				<div id="allCriteriaList">
					<!-- Load getCriteria() here - using criteria.jsp -->
				</div>
			</div>

				<!-- END All Criteria List -->
		
		<p><i>No planning factors have been created yet</i></p>
		<small><a href="javascript:addPlanningFactor();">Add a New Planning Factor</a></small>
		<!-- End List of planning Factors -->
		<!-- Begin form to add a new planning factor -->
		<fieldset style="float:left;">
			<h4>Add a New Planning Factor</h4>

			<label for="name" class="niceFormElement">Factor Name</label>
			<input id="name" name="name" type="text" class="niceFormElement" /><br />
			
			<label for="name" class="niceFormElement">Description</label>
			<textarea id="description" name="description" class="niceFormElement"></textarea><br />
			
			<label for="name" class="niceFormElement">Related Themes (optional)</label>
			<select id="themes" name="themes" class="niceFormElement" MULTIPLE>
				<!-- load themes here - getThemes() -->
			</select><br />
			
			<br />
			
			<label for="name" class="niceFormElement">Factor Objectives</label>

			<select id="objectives" name="objectives" class="niceFormElement" MULTIPLE>
				<!-- load objectives here - getObjectives() -->
			</select><br />

			<input type="button" align="right" class="indentNiceForm" style="margin-top:.5em;margin-bottom:.5em;"
				onClick="javascript:getSelectedObjectives();" value="Add Selected Objectives to List" />

			
			<br /><p />
			
				<label for="name" class="niceFormElement">Add a New Objective</label>
				<input type="text" id="newObjective" style="width:300px;">
				<input type="button"  style="margin-left:10px;" 
					value="Add to List" onClick="javascript:buildObjectivesArray();">

			<br />
			
		<!-- Begin list of Objectives -->
			<div id="list" style="margin-top:1em;margin-bottom:1em;" class="indentNiceForm">
			
			</div>

		<!-- End list of objectives -->
			
			<input type="button" value="Save and Add Planning Factor" onClick="addCriterion();"/>
			<br />


		</fieldset>
		


		<!-- End form to add a new planning factor -->
		<!-- Begin publishing options -->
		<div style="clear:both"></div>
		<h4>Finished Creating Planning Factors?</h4>

		<p>Once you have completed a list of planning factors click the link below to publish this list and allow participants to begin discussing these factors and weighing them.</p>
		<input type="button" value="Publish Planning Factors!" onClick="publish();" />
		<!--end publishing options -->
	</body>
</html:html>

