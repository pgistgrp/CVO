<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=utf-8">
		<title>Weigh Planning Factors</title>
		<!-- Site Wide JavaScript -->
		<script src="scripts/globalSnippits.js" type="text/javascript"></script>
		<script src="scripts/tags.js" type="text/javascript"></script>
		<script src="scripts/prototype.js" type="text/javascript"></script>
		<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
		<!-- End Site Wide JavaScript -->
		<!-- DWR JavaScript Libraries -->
		<script type='text/javascript' src='/dwr/engine.js'></script>
		<script type='text/javascript' src='/dwr/util.js'></script>
		<!-- End DWR JavaScript Libraries -->
		<!--Criteria Specific  Libraries-->
		<script type='text/javascript' src='/dwr/interface/CriteriaAgent.js'></script>
		
		<script type="text/javascript" charset="utf-8">
			var cctId = "${cct.id}"
			/* *************** Pull all criteria and their associated weights and objectives (criteriaAssoc_weights.jsp) *************** */
			function getWeights(){
				CriteriaAgent.getWeights({cctId:cctId},{
				  callback:function(data){
				    if(data.successful){
				    	$('criteria').innerHTML = data.html;
				    }else{
						alert(data.reason);
					}
				  },
				  errorHandler:function(errorString, exception){
				        alert("getWeights error:"+errorString+" "+exception);
				  }
				  });
			}
			
			/* *************** Set the weight of givin criterion *************** */
			function setWeight(critId, weight){
				//alert("cctId: " + cctId + " critId: " + critId + " weight: " + weight + " param4: " + param4); 
				CriteriaAgent.setWeight({cctId:cctId,critId:critId,weight:weight}, {
					callback:function(data){
						if (data.successful){
							alert('value saved')
						}else{
							alert(data.reason);
						}
					},
					errorHandler:function(errorString, exception){ 
					alert("CriteriaAgent.setWeight( error:" + errorString + exception);
					}
				});
			}
			
			/* *************** Toggle simple tree menu - maybe pull this into an external file since a few files are now using this? *************** */			
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
			};
			
		</script>
		<style type="text/css" media="screen">
			@import "styles/lit.css";
		</style>
	</head>
	<body>
		<div id="criteria"><!--load the criteria partial here --></div>
		<script type="text/javascript" charset="utf-8">
			getWeights();
		</script>
	</body>
</html>