<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<pg:fragment type="html">

	Below is a list of all the themes the moderator has identified in the concerns you and other participants submitted during Step 1a: Brainstorm Concerns. Each theme has its own room.  This room is for discussing whether any themes on that list should be added, removed, or changed.
   <c:forEach var="infoObject" items="${infoStructure.infoObjects}">
	     <p>${infoObject.object}</p>
	</c:forEach>	

</pg:fragment>
