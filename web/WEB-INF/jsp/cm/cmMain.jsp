<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
<head>
<title>Choice Modeler</title>
<link rel="stylesheet" type="text/css" href="/styles/default.css">
<wf:pageunload />
</head>

<body>

<c:choose>
  <c:when test="${name == null}">
    <p>Input your name:
    <form action="/cm.do" method="POST">
        <input type="text" name="name" value="${name}">
        <input type="submit" value="Commit">
    </form>
  </c:when>
  <c:otherwise>
    <p>Reply from Choice Modeler: <span style="background-color:#EEEEEE;color:red;"><b>${greeting}<b></span>
  </c:otherwise>
</c:choose>

</body>
</html:html>

