<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<pg:fragment type="html">
</pg:fragment>

<pg:fragment type="script">
	var pgistmap = new PGISTMap('projectmap');
	pgistmap.setAfterDataLoadHandler(showAllProjects);
	//pgistmap.setProjectClickHandler='openProject';  //this is temporarily not used
	pgistmap.setProjectSelection();
	pgistmap.discussionActionURL = "/sdRoom.do";
	pgistmap.infoStructureId = ${structure.id};
	
	//it is necessary to make a map between project ids and info object ids here (idMapping)
    <c:forEach var="infoObject" items="${structure.infoObjects}" varStatus="loop">
	pgistmap.idMapping["${infoObject.object.id}"] = ${infoObject.id};
    </c:forEach>
	
	function showAllProjects(){
		
		pgistmap.map.clearOverlays();
	
		for(i=0; i<pgistmap.projectList.length; i++){
			if(pgistmap.projectList[i].fpids && pgistmap.projectList[i].fpids!=""){	
				pgistmap.redrawProjectFootprint(pgistmap.projectList[i]);
				
				for(var j=0; j<pgistmap.projectList[i].bubblemarkers.length; j++){
					pgistmap.map.addOverlay(pgistmap.projectList[i].bubblemarkers[j]);
				}			
			}
		}
	}
	
</pg:fragment>
