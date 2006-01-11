<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<title>PGIST main page</title>
</head>

<body bgcolor="white">

<h2><a href="logout.do">Logout</a></h2>

<pg:show users="admin">
  <p><a href="">User Management</a>
</pg:show>

<pg:show roles="member">
  <h2>Haha, this line can only be seen by role member!</h2>
</pg:show>

<pg:show users="admin, guest">
  <h2>Haha, this line can only be seen by user admin and guest!</h2>
</pg:show>

<pg:show condition="${userForm.user.loginname==sessionScope.userLoginname}">
  <h2>Haha, this line can only when condition==true!</h2>
</pg:show>

<pg:show owner="${userForm.user.loginname}">
  <h2>Haha, this line can only when I am the owner!</h2>
</pg:show>

</body>
</html>

