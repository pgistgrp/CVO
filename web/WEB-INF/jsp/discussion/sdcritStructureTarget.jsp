<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>



<pg:fragment type="html">
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

	
</pg:fragment>

<pg:fragment type="script">
	/* *************** Get information structure for object *************** */
	io.getCriteria = function(){
		CriteriaAgent.getAllCriterion({}, {
			callback:function(data){
				if (data.successful){
					$(io.objectDiv).innerHTML = data.html;
				}else{
					$(io.objectDiv).innerHTML = "<b>Error in CriteriaAgent.getAllCriterion Method: </b>" + data.reason; 
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("CriteriaAgent.getAllCriterion( error:" + errorString + exception);
			}
		});
	};
	
	/* *************** Toggle Tree Node to view Asscociated Objectives for a Given Criterion *************** */
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
	
	//DO ON LOAD
	io.getCriteria();

</pg:fragment>