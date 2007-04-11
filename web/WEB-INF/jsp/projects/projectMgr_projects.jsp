<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<!--####
	Project: Let's Improve Transportation!
	Page: Projects Partial
	Description: Partial page to get all projects
	Author(s): 
	     Front End: Jordan Isip, Adam Hindman
	     Back End: Zhong Wang, Matt Paulin
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[x] loop through all projects
		[ ] Remove project alt form from this page - do it though js

		
#### -->

<c:if test="${fn:length(projects) == 0}">
	<p>No projects have been created yet.</p>
</c:if>
<ul id="projectsList" class="treeview">
	<c:forEach var="project" items="${projects}">
		<li class="projectList" id="project-${project.id}">
		<div style="float:left;width:620px;">
			<a href="#" class="project">${project.name}</a>
		</div>
		<div style="float:left;width:200px;">
		<small> 
		 <a href="javascript:prepareProject(${project.id});">edit
					properties</a> | <a href="javascript:deleteProject(${project.id});">delete</a>
		</small>
		</div><br />
			<!-- for editing project -->
			<div id="projectForm${project.id}" style="display:none">
				<h4>Editing ${project.name}</h4>
				<form action="javascript:editProject(${project.id});" id="frmProject${project.id}">
					<!--form inserted from js renderProjectForm();-->
				</form>
			</div>
			<!-- end for editing project -->
			<ul>
				<c:forEach var="alternative" items="${project.alternatives}">
					<li id="alt-${alternative.id}">${alternative.name} <small><a href="javascript: mapAlternative(${alternative.id});">edit
								map</a> | <a href="javascript: prepareProjectAlt(${alternative.id}, 'altId');">edit
								properties</a> | <a href="javascript:deleteProjectAlt(${alternative.id});">delete</a></small>
						<div id="alternativeForm${alternative.id}" style="display:none;">
							<form action="javascript:editProjectAlt(${alternative.id});" id="frmProjectAlt${alternative.id}">
								<!--form inserted from js renderProjectAltForm();-->
							</form>
						</div>
						<div id="alternativeMap${alternative.id}">
							<!-- map for this alt goes here -->
						</div>
					</li>
				</c:forEach>
				<!-- for creating project alt-->
				<li><small>[ <a href="javascript:prepareProjectAlt(${project.id},'projId');">Add
							an Alternative</a> ]</small>
					<div id="alternativeForm${project.id}" style="display:none;">
						<form action="javascript:createProjectAlt(${project.id});" id="frmProjectAlt${project.id}">
							<!--form inserted from js renderProjectAltForm();-->
						</form>
					</div>
				</li>
				<!-- end for creating project alt -->
			</ul>
			<div id="editAlternativeForm${project.id}" style="display: none"></div>
		</li>
	</c:forEach>
	<li>[ <a href="javascript:prepareProject();">Add a Project</a> ]
		<div id="projectForm" style="display: none;">
			<h4>Add a New Project</h4>
			<form action="javascript:createProject();" id="frmProject">
				<!-- form loaded via js  renderProjectForm();-->
			</form>
		</div>
	</li>
</ul>
