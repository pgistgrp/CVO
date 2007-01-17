<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!--####
	Project: Let's Improve Transportation!
	Page: Object for Structured Discussion for Projects
	Description: This page will list and map out all of the projects in the given decision situation.
				 The javascript on this page will allow for map manipulation.  Clicking on a project
				 will 
	Author(s): Jordan Isip, Adam Hindman, Issac Yang
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[x] Integrate Structured Discussion (Jordan)
		[ ] Integrate Project Tree (Issac)
		[ ] Integrate Layout (Adam) 
#### -->

<pg:fragment type="html">
	<!-- begin "overview and instructions" area -->
	<div id="overview" class="box2">
		<h3 class="headerColor">Instructions</h3>
		<p>
			Here you can review proposed projects for improving or expanding our regional transportation system.  Click on a
			project to review information and discuss its merits or drawbacks with other participants.
		</p>
		<p>
			Each of the projects has been examined by a panel of specialists who assigned scores based on the criteria we reviewed
			in step 2. <a href="#">Read more about the project scoring process</a>
		</p>
			
		
	</div>
	<!-- end overview -->
	
	<div id="list"> <!--left col-->
		<h3 class="headerColor clearBoth">All Proposed Projects</h3>
		<h4 class="headerColor">Road Projects</h4>
		<ul>
			<c:forEach var="project" items="${infoStructure.infoObjects}" varStatus="loop">
				<c:if test="${project.type == 1}">
					<li>${project.name} (${fn:length(project.alternatives)})
						<ul>
							<c:forEach var="alternative" items="${project.alternatives}" varStatus="loop">
								<li><a href="javascript:mapProjectAlt(${alternative.id})">${alternative.name}</a></li>
							</c:forEach>
						</ul>
					</li>
				</c:if>
			</c:forEach>
		</ul>
	
		<h4 class="headerColor">Transit Projects</h4>
		<ul>
			<c:forEach var="project" items="${infoStructure.infoObjects}" varStatus="loop">
				<c:if test="${project.type == 2}">
					<li>${project.name} (${fn:length(project.alternatives)})
						<ul>
							<c:forEach var="alternative" items="${project.alternatives}" varStatus="loop">
								<li><a href="javascript:mapProjectAlt(${alternative.id})">${alternative.name}</a></li>
							</c:forEach>
						</ul>
					</li>
				</c:if>
			</c:forEach>
		</ul>
	</div>

	<div id="map"> <!--right col-->
		<!-- load map here -->
	</div>
</pg:fragment>

<pg:fragment type="script">
	//All Javascript that is internal to this page must go here - not sdRoomMain.
	//Add Javascript to build tree list

</pg:fragment>