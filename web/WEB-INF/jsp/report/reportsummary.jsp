<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<!--####
	Project: Let's Improve Transportation!
	Page: Full Report
	Description: This is a full report of the entire challenge.
	Author(s): 
	     Front End: Jordan Isip, Adam Hindman
	     Back End: John Q Le, Zhong Wang
#### -->

<html:html>
<head>
<title>Report Summary Form</title>
<style type="text/css" media="screen">
  @import "styles/lit.css";
	@import "styles/step4b-voteresults.css";
	@import "styles/step5-report.css";
	textarea{width:500px;height:100px;}
	div.floatRight {width:400px;}
</style>
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
</head>
<body>
	<!--
		Themes for summaries: ***${summaries}***<br />
		Criteria References: ***${cr}***<br>
		Project References: ***${pr}***
	-->
<wf:nav />
<div id="headerMenu"></div>
<div id="container">

	<h2 class="headerColor">Report builder</h2>
	<html:form action="/reportsummary.do" method="POST">
	<html:hidden property="save" value="true"/>
	<html:hidden property="suite_id" value="${suite_id}"/>
	<html:hidden property="workflowId" value="${param.workflowId}"/>
	<html:hidden property="contextId" value="${param.contextId}"/>
	<html:hidden property="activityId" value="${param.activityId}"/>

	<p class="box6 padding5" style="width:500px"><strong>System Msg:</strong> ${reportForm.reason}</p>

	<p>Final Vote Date: 
	  <html:text property="finalVoteDate" value="${rSummary.finalVoteDate}"/>
	  (the exact text which will be displayed everywhere the final vote date is mentioned.)
	</p>
	  
	  <p>Final Report Date: 
	  <html:text property="finalReportDate" value="${rSummary.finalReportDate}"/>
	  (the exact text which will be displayed everywhere the final report date is mentioned.)
	  </p>
	  
	<p>
		<h3 class="headerColor">Executive Summary</h3>
		<html:textarea property="executiveSummary" value="${rSummary.executiveSummary}"/></p>
	<p>
		<h3 class="headerColor">Participants Summary</h3>
		<html:textarea property="part1a" value="${rSummary.part1a}"/></p>
	<p><h3 class="headerColor">Concern Summary</h3>
		<div class="floatRight"></div>
		<html:textarea property="part1b" value="${rSummary.part1b}"/></p>
	<p><h3 class="headerColor">Criteria Summary</h3><br/>
		<html:textarea property="part2a" value="${rSummary.part2a}"/></p>
	<p><h3 class="headerColor">Project Summary</h3>
		<html:textarea property="part3a" value="${rSummary.part3a}"/></p>
	<p><h3 class="headerColor">Package Summary</h3>
		<html:textarea property="part4a" value="${rSummary.part4a}"/></p>

	<p><h3 class="headerColor">Finalize Report?</h3>
	Yes <html:radio property="finalized" value="true" />  No <html:radio property="finalized" value="false" /></p>
	
	<html:submit value="submit" />
	</html:form>
</div>
<div id="headerMenu"></div>
	<!-- End Appendix C -->
</body>
</html:html>