
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
<div class="box6">
<h3>Participant endorsement vote results</h3>
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
</div>

<p>Thank you for casting your vote.</p>

<p>You will
receive an email announcing the vote results. The results of the
endorsement vote will also be added to the executive summary of the
final report. The email you receive will include a link to the final
published report which you can share with friends and family.</p>

<p>Your participation in the LIT challenge is <i>almost</i> complete.
Once the news of the endorsement vote is available, you will be asked
to complete one final survey in which you can describe your
experiences as a LIT participant. Please take the time to complete
this survey, as it will greatly help our effort to evaluate the
strengths and weaknesses of this method of involving the public in
regional transportation decision making. A link to the final survey
will be emailed to you.</p>
