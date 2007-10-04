<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<title>GA Knapsack Testing</title>
</head>
<body>
	<p><b>GA Knapsack Algorithm - ${param.type}</b></p>
  <form name="mainForm" method="POST">
    <input type="hidden" name="save" value="true">
    <c:if test="${type=='Funding'}">
      <input type="hidden" name="type" value="Funding">
    </c:if>
    <c:if test="${type=='Project'}">
      <input type="hidden" name="type" value="Project">
    </c:if>
    <textarea id="beanshell" name="beanshell" style="width:100%; height:85%;">${beanshell}</textarea>
    <br>
    <input type="submit" value="Save">
  </form>
</body>
</html>

