<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
<head>
<title>PGIST main page</title>
<event:pageunload />
</head>

<body bgcolor="white">

<html:form action="/search.do" method="post">

<html:hidden property="workflowId" name="searchForm" value="${param['workflowId']}"/>

<table width="100%">
  <tr>
    <td><html:text property="queryStr" name="searchForm" maxlength="50" size="50"/><input type="submit" value="Search"></td>
  </tr>
</table>

<table width="100%">
  <tr>
    <td>Found: ${searchForm.total} results</td>
  </tr>
  <c:forEach var="result" items="${searchForm.results}">
  <tr>
    <td>
      <c:if test="${result.type=='post'}"><a href="/sdRoom.do?isid=${result.isid}&ioid=${result.ioid}">Post</a></c:if>
      <c:if test="${result.type=='reply'}"><a href="/sdThread.do?isid=${result.isid}&ioid=${result.ioid}&pid=${result.pid}">Reply</a></c:if>
      <c:if test="${result.type=='concern'}"><a href="/cctview.do?cctId=${result.cctid}">Concern</a></c:if>
      <c:if test="${result.type=='comment'}"><a href="/cctview.do?cctId=${result.cctid}">Comment</a></c:if>
    </td>
  </tr>
  </c:forEach>
</table>

</html:form>

</body>
</html:html>

