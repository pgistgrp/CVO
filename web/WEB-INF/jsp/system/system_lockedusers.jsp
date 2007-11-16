<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<select name="lockedlist" size="20" multiple="multiple" id="lockedlist"  STYLE="width: 180px" multiple>
<c:choose>
	<c:when test="${fn:length(dusers) == 0}">
		<option value="">No Disabled Users</option>
	</c:when>
	<c:otherwise>
			
			<c:forEach var="duser" items="${dusers}" varStatus="loop">
				 <option value="${duser.id}">${duser.loginname}</option>
			</c:forEach>
			
	</c:otherwise>
</c:choose>
</select>		