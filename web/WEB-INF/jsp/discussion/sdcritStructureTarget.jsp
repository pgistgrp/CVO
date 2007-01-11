<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<!--####
	Project: Let's Improve Transportation!
	Page: SDCrit Structure Target
	Description: This is the object in the SDcriteria discussion room
	Author(s): 
	     Front End: Jordan Isip, Adam Hindman, Issac Yang
	     Back End: Zhong Wang, John Le
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[ ] Add JavaScript to get criteria (Jordan)
		[ ] Integrate Layout (Adam)
#### -->
<pg:fragment type="html">
	<div id="allCriteriaList">asdfsadfasdfsdaf========<!--load criteria here --></div>
	
</pg:fragment>

<pg:fragment type="script">
	function sayHello(){
		
	alert("hello");
	}
	function getCriteria(){
		alert("hello");
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
	

</pg:fragment>