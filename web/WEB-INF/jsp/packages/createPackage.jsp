<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!--####
	Project: Let's Improve Transportation!
	Page: Create My Transportation Package
	Description: This page serves as a form for a user to create her/her own transportation package. 
	Author: Jordan Isip, Adam Hindman, Issac Yang
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[ ] Integrate Layout (Issac)
		[ ] JavaScript for Calculations (Issac)

#### -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=utf-8">
		<title>Create your own Package!</title>
		<style type="text/css" media="screen">
			@import "styles/lit.css";
		</style>
	</head>
	<body>
		<h1>Overview and Instructions</h1>
		<p>This is the overview blah blah blah...</p>
		
		<FORM action="packageAction.do" method="post">	
			<div id="createPackage">
				<div id="summary">
					<!-- load from javascript -->
				</div>
				<div id="projects">
					<h1>Create your Transportation Package</h1>
					<c:forEach var="project" items="${projects}" varStatus="loop">
						<label><input name="proj-${project.id}" type="radio" CHECKED /> Do Nothing</label>
						<p>Name: ${project.name}</p>
						<c:forEach var="alternative" items="${project.alternatives}" varStatus="loop">
							<input type="radio" name="proj-${project.id}" 
							<c:if test="${pg:contains(alternative, package.projAlts)}">
								CHECKED
							</c:if>
							<!-- end input -->
							Name: ${alternative.name}
						</c:forEach>
					</c:forEach>
				</div>
				
				<div id="map">
					<!-- load the map here gMan! -->
				</div>
				
				<div id="funding">
					<h1>Decide how to pay for it</h1>
					<c:forEach var="source" items="${sources}" varStatus="loop">
						${source.name}
							<c:forEach var="alternative" items="${source.alternatives}" varStatus="loop">
								<input type="radio" name="fund-${project.id}" 
								<c:if test="${pg:contains(alternative, package.fundAlts)}">
									CHECKED
								</c:if>
								<!-- end input -->
								${alternative.name}
							</c:forEach>
					</c:forEach>
					<h1>Finished?</h1>
				</div>
				<div id="summaryRepeat">
					<!-- load from javascript -->
				</div>
				<input type="button" value="Yes - Submit My Package!"> <!-- this should only be enabled if funding exceeds cost -->
				<input type="cancel" value="No - Start Over!">
			</div>
		

		</FORM>
	</body>
</html>
