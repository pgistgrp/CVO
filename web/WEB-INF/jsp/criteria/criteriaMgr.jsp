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
	Description: Manage all criteria and publish selected criteria to create a new instance.
	Author(s): Jordan Isip, Adam Hindman, Issac Yang
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[ ] Barebones JavaScript (Jordan)
#### -->
<html:html>
<head>
<title>Manage Criteria</title>
<!-- Site Wide JavaScript -->
<script src="scripts/tags.js" type="text/javascript"></script>
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<script src="scripts/search.js" type="text/javascript"></script>
<!-- End Site Wide JavaScript -->

<!-- DWR JavaScript Libraries -->
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<!-- End DWR JavaScript Libraries -->

<!--Criteria Specific  Libraries-->
<script src="scripts/SideBar.js" type="text/javascript"></script>
<script type='text/javascript' src='/dwr/interface/CriteriaAgent.js'></script>

<script>


// Global Variables


// END Global Variables
	function getAllCriterion(){
		CriteriaAgent.getAllCriterion({}, {
				callback:function(data){
				
						if (data.successful){
							
							alert(data.html);
						}
						
						
				},
				errorHandler:function(errorString, exception){ 
				alert("getAllCriterion Error: "+errorString+" "+exception);
				}
			});
		}


		function addCriterion(name, low, medium, high){
		CriteriaAgent.addCriterion({name: name, low: low, medium: medium, high: high, na:""}, {
				callback:function(data){
				
						if (data.successful){
							
							resetForm();
						}
						
						
				},
				errorHandler:function(errorString, exception){ 
				alert("addCriterion Error: "+errorString+" "+exception);
				}
			});
		}

		function resetForm(){

			document.criterionForm.txtNewCriterion.value="";
			document.criterionForm.txtLowDesc.value="";
			document.criterionForm.txtMedDesc.value="";
			document.criterionForm.txtHighDesc.value="";

		}

		function deleteCriterion(id){
		CriteriaAgent.deleteCriterion({id: id}, {
				callback:function(data){
						if (data.successful){
							//new Effect.Fade("criterion"+id);
						}
				},
				errorHandler:function(errorString, exception){ 
				alert("addCriterion Error: "+errorString+" "+exception);
				}
			});
		}

		function editCriterion(name, low, medium, high){
		CriteriaAgent.addCriterion({name: name, low: low, medium: medium, high: high}, {
				callback:function(data){
						if (data.successful){
							
						}
				},
				errorHandler:function(errorString, exception){ 
				alert("addCriterion Error: "+errorString+" "+exception);
				}
			});
		}


</script>
<style type="text/css">
	label{display: block; font-weight:bold;}
</style>
</head>

<body onload="javascript:getAllCriterion();">
  <h3>Moderator Tools &raquo; Manage Criteria</h3> 
  
  <h4>All Criteria in All Decision Situations</h4>
  <ul id="criteriaList">
  <div id="allCriteria">
  	<!-- load criteria list here -->
  </div>

  <li>[ <a href="javascript:Effect.toggle('newCriteriaForm', 'blind');">New Criteria</a> ]
  <div id="newCriteriaForm" style="display: none;">
  		<h4>Create New Criteria</h4>
			<form name="criterionForm" onsubmit="javascript:addCriterion(document.criterionForm.txtNewCriterion.value,document.criterionForm.txtLowDesc.value,document.criterionForm.txtMedDesc.value,document.criterionForm.txtHighDesc.value); Effect.toggle('newCriteriaForm','blind'); return false;">
				<label>Name:</label>
				<input name="txtNewCriterion" type="text" value="" size="25">
			
				<label>Low Description:</label>
				<textarea name="txtLowDesc" cols="100" rows="5"></textarea>
				
				<label>Medium Description:</label>
				<textarea name="txtMedDesc" cols="100" rows="5"></textarea>
				
				<label>High Description:</label>
				<textarea name="txtHighDesc" cols="100" rows="5"></textarea>
				
				<p>
					<input type="submit" value="submit">
				</p>
			</form>
  </div></li>
  </ul>

</body>
</html:html>

