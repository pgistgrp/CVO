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

		//END GLOBAL VARS

		/* *************** Grab All Criteria in the System - uses criteria.jsp *************** */
		function getCriteria(){
			CriteriaAgent.getAllCriterionForMgr({}, {
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
			var name = $('name').value;
			//alert("names: " + name + " description" + description + " objectiveIds: " + checkedObjectivesStr); // Between name:name and ThemeIds is na:description;
			CriteriaAgent.addCriterion({name:name}, {
				callback:function(data){
					if (data.successful){
						getCriteria();
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
		
		function toggleEditField(item, id) {
			Element.toggle(item + "Edit" + id);
			Element.toggle(item + id);
		}
		

		/* *************** Saves the changes for an edited Criterion *************** */
		function editCriterion(id){
			var name = $('criterionName'+ id).value;
			//alert("id: " + id +" name: " + name + " description" + description + " objectives: " + objectivesArr);
			CriteriaAgent.editCriterion({critId:id,name:name}, {
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
		<a href="main.do">Back to Moderator Control Panel</a>
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
		<p><a href="javascript:Element.toggle('fieldsetPF'); void(0);">Add a Planning Factor</a></p>
		<br />
		<fieldset style="float:left; display:none;" id="fieldsetPF">
			<form action="javascript:addCriterion();" id="addPlanningFactor" name="addPlanningFactor">
			<h3>Add a New Planning Factor</h3>
			<br />
			<label for="name" class="niceFormElement">Factor Name</label>
			<input id="name" name="name" type="text" class="niceFormElement" /><br />
			<input type="submit" value="Add Planning Factor"/>
			<br />
			</form>
		</fieldset>

		<!-- End form to add a new planning factor -->
		<!-- Begin publishing options -->
		<div style="clear:both"></div>
		<div id="publish">
			<a name="publish"></a>
			<br />
			<h4>Finished Managing Planning Factors?</h4>

			<p><input type="button" style="padding:5px" onClick="location.href='userhome.do'" 
				value="Finished!"/></p>

			<!--end publishing options -->
		</div>
		<script type="text/javascript">
			getCriteria();
		</script>
	</body>
</html:html>

