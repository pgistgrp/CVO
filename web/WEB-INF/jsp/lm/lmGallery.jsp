<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="javascript" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>

<html:html>
<head>
<title>Let's Improve Transportation - Learn more: Project Map</title>
<!-- Site Wide JS -->
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<script src="scripts/search.js" type="text/javascript"></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/util.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>

<style type="text/css">
@import "styles/lit.css";
@import "styles/table.css";
@import "styles/step3a-reviewprojects.css";
#newTable .fundingSourceItem{width:75%;}
</style>

</head>
<body>
<!-- Begin the header - loaded from a separate file -->
    <wf:nav />
<!-- End header -->
<!-- Begin header menu - The wide ribbon underneath the logo -->
	<div id="headerMenu">
		<div id="headerContainer">
			<div id="headerTitle" class="floatLeft">
				<h3 class="headerColor">Learn More</h3>
			</div>
			<div class="floatLeft headerButton"> <a href="lmMenu.do">Menu</a> </div>
			<div class="floatLeft headerButton"> <a href="lmAbout.do">About LIT</a> </div>
			<div class="floatLeft headerButton"> <a href="lmFaq.do">FAQ</a> </div>
			<div class="floatLeft headerButton"> <a href="lmTutorial1.do">Tutorial</a> </div>
			<div class="floatLeft headerButton currentBox"> <a href="lmGallery.do">Project Map</a> </div>
			<div class="floatLeft headerButton"> <a href="glossaryPublic.do">Glossary</a> </div>
			<div class="floatLeft headerButton"> <a href="lmResources.do">More Resources</a> </div>
		</div>
	</div>
<!-- End header menu -->
<!-- #container is the container that wraps around all the main page content -->
<div id="container">
	<!-- begin Object --> 
	<h3 class="headerColor">Project Map</h3>
	<p>Explore the projects being considered in the <em>LIT Challenge</em>. Registered users can discuss these projects with other participants. <a href="index.jsp">Join the discussion</a>, or <a href="register.do">register now</a>.</p>
	<div id="object">
		<a href="javascript:Util.expandAll('objectives');">Expand all</a>
		<a href="javascript:Util.collapseAll('objectives');">Collapse all</a>
		<div id="rp3a-left" class="floatLeft">
			<!-- begin collapsible list of projects -->
			<div id="newtable">
				<table cellpadding=0 cellspacing=0>
					<tr class="tableHeading">
						<th colspan="2" class="first">Proposed improvement projects</th>
						<th class="right"><span class="hiddenLabel" style="display:none">Money Needed</span></th>
					</tr>
					
					
					<c:forEach var="category" begin="1" end="2">
						<!-- start road projects -->
						<tr>
							<c:choose>
								<c:when test="${category == 1}">
									<td class="category" colspan="3"><strong>Road projects</strong></td>
								</c:when>
								<c:otherwise>
									<td class="category" colspan="3"><strong>Transit projects</strong></td>
								</c:otherwise>
							</c:choose>
						</tr>
						<!-- end CATEGORY LABEL -->
						<!-- ******* LOOP ENTIRE PROJECT ******** -->
						<c:forEach var="project" items="${projects}" varStatus="loop">
							
							<c:if test="${project.transMode == category}">
								<!-- begin PROJECT -->
								<tr class="fundingType">
									<td class="fundingSourceItem">
											<a href="javascript:Util.toggleRow(${loop.index});">
											<img src="images/plus.gif" id="icon${loop.index}" class="icon">${project.name}</a>
									</td>
									<td class="col2" colspan="2" align="right"> <!--${(project.inclusive) ? 'Select at most one' : 'Select any number'}-->&nbsp; </td>
								</tr>
								<!-- end PROJECT -->

								<!-- begin HIDDEN ROW of OPTIONS -->
								<tr style="display:none;" class="objectives" id="row${loop.index}">
									<td colspan="3">
										<table>
											<!-- start project alt -->
											<c:forEach var="alt" items="${project.alternatives}" varStatus="loop">
												<tr>
													<td class="col1"><a target="_blank" href="lmAlt.do?altId=${alt.id}">${alt.name}</td>
													<td class="cost">
													$<fmt:formatNumber maxFractionDigits="0" value="${alt.cost/1000000}" /> million</td>
												</tr>
											</c:forEach>
											<!-- end project alt-->
										</table>
									</td>
								</tr>
								<!-- end HIDDEN ROW -->
							</c:if>
						</c:forEach>
					</c:forEach>

				</table>
			</div>

			<!-- end collapsible project list -->
		</div>
		<!-- end rp3a-left -->
	</div>
	<!-- begin cell containing Google Map object -->
	<!-- GUIRONG: This can be up to 420px wide -->
	<div id="map" class="floatRight">420px wide GMap goes here</div>
	<!-- end cell containing Google Map object -->
	<!-- begin firefox height hack -->
	<div class="clearBoth"></div>
	<!-- end firefox height hack -->
</div>
<!-- end Object-->
</div>
<!-- end container -->
<!-- start feedback form -->
<pg:feedback id="feedbackDiv" action="cctView.do"/>
<!-- end feedback form -->
<!-- Begin header menu - The wide ribbon underneath the logo -->
	<div id="headerMenu">
		<div id="headerContainer">
			<div id="headerTitle" class="floatLeft">
				<h3 class="headerColor">Learn More</h3>
			</div>
			<div class="floatLeft headerButton"> <a href="lmMenu.do">Menu</a> </div>
			<div class="floatLeft headerButton"> <a href="lmAbout.do">About LIT</a> </div>
			<div class="floatLeft headerButton"> <a href="lmFaq.do">FAQ</a> </div>
			<div class="floatLeft headerButton"> <a href="lmTutorial1.do">Tutorial</a> </div>
			<div class="floatLeft headerButton currentBox"> <a href="lmGallery.do">Project Gallery</a> </div>
			<div class="floatLeft headerButton"> <a href="glossaryPublic.do">Glossary</a> </div>
			<div class="floatLeft headerButton"> <a href="lmResources.do">More Resources</a> </div>
		</div>
	</div>
<!-- End header menu -->
<!-- Begin footer -->
<div id="footer">
	<jsp:include page="/footer.jsp" />
</div>
<!-- End footer -->
</body>
</html:html>
