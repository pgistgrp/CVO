
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
<head>
<title>Publish Projects</title>
<link rel="stylesheet" type="text/css" href="/styles/default.css'/>">
<event:pageunload />
</head>

<body>
  <h2>Publish Projects</h2>
	<p>Go back to modify these projects and alternatives</p>  
  <input type="checkbox" name="PrjName1" value="PrjId1"><h3>Project Name 1</h3>
	<table>
		<tr>
			<th>Alternative Name</th>
			<th>Revenue</th>
			<th>Tax Rate</th>
		</tr>
		<tr>
			<td>Alt Proj 1</td>
			<td>.800</td>
			<td>0.08%</td>
		</tr>
		<tr>
			<td>Alt Proj 2</td>
			<td>.689</td>
			<td>0.22%</td>
		</tr>	
	</table>
	
	
</body>
</html:html>

