<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
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
/* DWR Template -----
		function functionName(param){
		THEAgent.theMethod({param1:param}, {
				callback:function(data){
						if (data.successful){

						}
				},
				errorHandler:function(errorString, exception){ 
				alert("functionName Error: "+errorString+" "+exception);
				}
			});
		}
*/

// Global Variables


// END Global Variables
// START CriteriaAgent DWR Functions - For more info See: http://localhost:8080/pgist-docs/org/pgist/criteria/CriteriaAgent.html

		/** addCriterion
		public java.util.Map addCriterion(java.util.Map params)
		    Add one new criterion to the system.
		    Parameters:
		        params - a Map contains:
		            * name - string, name of the criteia
		            * low - string, descript
		            * medium - string, descript
		            * high - string, descript
		            * na - string, descript. Optional.
		    Returns:
		        a Map contains:
		            * successful - a boolean value denoting if the operation succeeds
		            * reason - reason why operation failed (valid when successful==false)
		            * id - int, the id for the new Criteria object.
		    Additional Instructions:
		    		 * Use the returned ID to highlight newly created criterion (Effect.Highlight(ID);)
		 */
		function addCriterion(name, low, medium, high){
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


		/** deleteCriterion
			public java.util.Map deleteCriterion(java.util.Map params)
			    Delete a criterion from the system.
			    Parameters:
			        params - a Map contains:
			            * id - int, the id of the criterion to be deleted
			    Returns:
			        a Map contains:
			            * successful - a boolean value denoting if the operation succeeds
			            * reason - reason why operation failed (valid when successful==false)
			    Additional Instructions:
		    			 * On Successful, fade element that contains the criterion (Effect.Fade("criterion"+id));
		 */
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

		/** editCriterion
		public java.util.Map editCriterion(java.util.Map params)
	    Update an exisiting criterion.
	    Parameters:
	        params - a Map contains:
	            * id - int, the id of the Criteria to be edited
	            * name - string, name of the criteia
	            * low - string, descript
	            * medium - string, descript
	            * high - string, descript
	            * na - string, descript. Optional.
	    Returns:
	        a Map contains:
	            * successful - a boolean value denoting if the operation succeeds
	            * reason - reason why operation failed (valid when successful==false)
	    Additional Instructions:
		    	 * Use the given ID to highlight modified criterion (Effect.Highlight(ID);)
		 */
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

//java.util.Map 	editCriterion(java.util.Map params)
// END DWR Functions
</script>
<style type="text/css">
	label{display: block; font-weight:bold;}
</style>
</head>

<body>
  <h3>Moderator Tools &raquo; Manage Criteria</h3> 
  
  <h4>All Criteria in All Decision Situations</h4>
  <ul id="criteriaList">
  <c:forEach var="criterion" items="${criteria}">
  		<li>${criterion.name} [ <a href="javascript:Effect.toggle('criteriaEdit${criterion.id}','blind');">edit</a> ] [ <a href="javascript:deleteCriterion(${criterion.id});">delete</a> ]
			<ul id="criteriaEdit${criterion.id}" style="display: none;">
				<li><input name="txtCriteriaEdit${criterion.id}" type="text" value="${criterion.name}" size="25"> <input type="submit" value="submit"></li>
			</ul>
		</li>
  </c:forEach>

  <li>[ <a href="javascript:Effect.toggle('newCriteriaForm', 'blind');">New Criteria</a> ]
  <div id="newCriteriaForm" style="display: none;">
  		<h4>Create New Criteria</h4>
			<form>
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

