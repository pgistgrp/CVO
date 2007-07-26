
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<h3>Poll Results</h3>
<c:set var="total" value="${total}"/>
<p>Yes Votes: ${yes}<c:set var="yes" value="${yes}"/> or 
<c:set var="percentyes" value="${yes/total*100}"/>
<fmt:formatNumber maxFractionDigits="0" value="${percentyes}"/>%
</p>
<p>No Votes: ${no}<c:set var="no" value="${no}"/> or 
<c:set var="percentno" value="${no/total*100}"/>
<fmt:formatNumber maxFractionDigits="0" value="${percentno}"/>%
</p>

<p>Total Votes: ${total}</p>