<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<select name="activelist" size="20" multiple="multiple" id="activelist" STYLE="width: 180px" multiple>
<c:choose>	
	<c:when test="${fn:length(users) == 0}">
		<option value="">No Users</option>
	</c:when>
	<c:otherwise>
			
			<c:forEach var="user" items="${users}" varStatus="loop">
				 <option value="${user.id}">${user.loginname}</option>
			</c:forEach>
			
	</c:otherwise>
</c:choose>
</select>	