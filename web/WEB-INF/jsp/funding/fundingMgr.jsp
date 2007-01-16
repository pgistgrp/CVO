<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<!--####
	source: Let's Improve Transportation!
	Page: Funding Manager
	Description: CRUD Events on All Funding and their Alternatives
	Author(s): 
	     Front End: Jordan Isip, Adam Hindman, Issac Yang
	     Back End: Zhong Wang, John Le
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[ ] BareBones JavaScript (Isaac)
		[ ] Test form actions (Isaac)
		
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
<script type='text/javascript' src='/dwr/interface/FudningAgent.js'></script>

<script>
// Global Variables


// END Global Variables

	function getFundingSources(){
		FundingAgent.getFundingSources({}, {
			callback:function(data){
				if (data.successful){
					alert(data.html) // gets fundingMgr_sources.jsp
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("FundingAgent.getFundingSources( error:" + errorString + exception);
			}
		});
	}

	function createFundingSource(){
		var name = "";
		FundingAgent.createFundingSource({name:name}, {
			callback:function(data){
				if (data.successful){
					alert("successful");
					getFundingSources();
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("ClassName.methodName( error:" + errorString + exception);
			}
		});
	}
	
	function createFundingSourceAlternative(params){
		//alert("param1: " + param1 + " param2: " + param2 + " param3: " + param3 + " param4: " + param4); 
		ClassName.methodName({param1:param1,param2:param2,param3:param3,param4:param4}, {
			callback:function(data){
				if (data.successful){
					alert(data.html)
				}else{
					alert(data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("ClassName.methodName( error:" + errorString + exception);
			}
		});
	}

</script>
<style type="text/css">

</style>
</head>


<body>
	<h3>Moderator Tools &raquo; Manage Funding</h3> 
	<form name="publishsources" action="sourceDefine.do">
		<input type="hidden" name="activity" value="save" />
		<h4>All Funding Sources</h4>
		<ul id="sourcesList">
			<c:forEach var="source" items="${sources}">
				<li><input type="checkbox" name="sourceId" value="${source.id}"/>${source.name} [ <a href="javascript:Effect.toggle('editsource${source.id}','blind');">edit</a> ] [ <html:link action="/sourceMgr.do?action=delete" paramId="id" paramName="source" paramProperty="id">delete</html:link> ]
					<ul>
						<li id="editsource${source.id}" style="display: none;"><input name="txtsourceEdit${source.id}" type="text" value="${source.name}" size="25"> <input type="submit" value="submit"></li>
						<li>[ <a href="javascript:addAlternative(${source.id});">Add an Alternative</a> ]</li>
						<c:forEach var="alternative" items="${source.alternatives}">
							<li>${alternative.name} [ <a href="javascript: editAlternative(${alternative.id});">edit</a> ] [ <a href="javascript:deleteAlternative(${alternative.id});">delete</a> ]</li>
						</c:forEach>
					</ul>
				</li>
			</c:forEach>

			<li>[ <a href="javascript:Effect.toggle('newsourceForm', 'blind');">New Funding Source</a> ]
				<div id="newsourceForm" style="display: none;">
					<h4>Create New Funding Source</h4>
					<form>
						<label>Name:</label>
						<input name="txtNewCriterion" type="text" value="" size="25">

						<label>Description:</label>
						<textarea name="txtLowDesc" cols="100" rows="5"></textarea>

						<p><input type="submit" value="submit"></p>
					</form>
				</div>
			</li>
		</ul>
	</form>
</body>
</html:html>

