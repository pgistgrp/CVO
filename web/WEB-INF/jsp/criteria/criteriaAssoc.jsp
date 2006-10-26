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
<title>Associate and Publish Criteria</title>
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
				alert("functionName: "+errorString+" "+exception);
				}
			});
		}
*/

// Global Variables


// END Global Variables
// START DWR Functions

// END DWR Functions
</script>
</head>

<body>
<div id="container" style="width: 600px">
	<h1>Associate Criteria to Themes</h1>
	<p>Select criteria for each theme.  Use the "save" button at the bottom of the form to save the relationship.</p>
	<form id="AssocCriteria" name="AssocCriteria" method="post" action="">
	<div id="themes" style="float: left; width: 300px;">
	  <h3>Themes List</h3>
	  <p><input name="Theme" type="radio" value="Theme1" />Theme 1</p>
	  <p><input name="Theme" type="radio" value="Theme2" />Theme 2</p>
	  <p><input name="Theme" type="radio" value="Theme3" />Theme 3</p>
	  <p><input name="Theme" type="radio" value="Theme4" />Theme 4</p>
	</div>
	
	<div id="criteria" style="margin-left: 300px;">
	  <h3>Criteria List</h3>
	  <p><small>ctrl + click to select multiple</small></p>
	  <select name="CriteriaList" size="10" multiple="multiple" id="CriteriaList" style="width: 100%;">
	    <option value="Criteria1">Criteria1</option>
	    <option value="Criteria2">Criteria2</option>
	    <option value="Criteria3">Criteria3</option>
	    <option value="Criteria4">Criteria4</option>
      </select>
	  <div id="submit" style="text-align:right;"><input type="button" value="Save" /></div>
	  <div id="feedback" style="font-size: 0.8em; font-weight: bold; text-align:center; background: #BFFFBF; border: 3px solid #008F00;">Relationship was successfully saved!</div>
	  </div>
	</form>
	<div id="clear" style="clear: left;">Finished?  <input type="button" value="Publish!" /></div>
</div>
</body>
</html:html>

