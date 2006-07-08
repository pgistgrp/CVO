<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Glossary Management</title>
<link rel="stylesheet" type="text/css" href="/styles/default.css">
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type='text/javascript' src='/dwr/interface/GlossaryManageAgent.js'></script>

<script type="text/javascript">


		function getTerm(id, type){
				GlossaryManageAgent.getTerm({id:id, type:type}, {
				callback:function(data){
					if (data.successful){ 
							$('slate').innerHTML = data.html;
					}

					if (data.successful != true){
						alert(data.reason);
					}
				},
				errorHandler:function(errorString, exception){ 
						//showTheError();
				}
			});
		}
		

</script>
<!-- Template 4 CSS -->
<style type="text/css" media="screen">@import "styles/position.css";</style>
<style type="text/css" media="screen">@import "styles/styles.css";</style>
<!-- Temporary Borders used for testing <style type="text/css" media="screen">@import "styles/tempborders.css";</style>-->
<!-- End Template 4 CSS -->

<style>
#col-left {
	width: 20%;
	margin-right: 5px;
	border-right: 1px solid #CCCCCC;
}
#col-right {
	width: 75%;
}

#slate ul{display:inline; margin:0; padding:0}
#slate li{list-style: none; display:inline; padding: 3px;}

</style>
</head>
<body>

<div id="header"><!--<jsp:include page="gmTerms.jsp"/>--><img src="/images/logo_reflect.gif"></div>
<div id="container">

	<div id="col-left">
	<div>
	<h3>Moderator Control Panel Menu</h3>
	<ul id="modMenu">
		<li>Agenda Manager</li>
		<li>Concern Management</li>
				<ul><li>Concerns Synthesis Tool</li>
			  <li>Tags and Stopwords</li>
			  <li>Concern and Tag Statistics</li></ul>
				</li>
		<li>Criteria Management</li>
		<li>Scenario Management</li>
		<li>Resource Library
				<ul><li class="active">Glossary</li></ul>
				</li>
	</ul>
	</div>
	</div>
	<div id="col-right">
	<h1>Resource Library Management: Glossary Terms</h1>
	<div id="suppSlate">
		<h3>Overview and Intructions</h3>
	</div>
	
	<div id="suppSlate">
		<h3>Proposed Glossary Terms by Participants - Waiting for Moderator Approval</h3>
	</div>
	<div id="slate" class="leftBox">

	  <div id="list"><jsp:include page="gmTerms.jsp"/></div>
	</div>
	



</div>
	
	<div id="footer" style="clear:both;">
	footer
	</div>
	
</div>

  
	
</table>
</body>
</html>
