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
  <html:form action="fundingPublish.do" method="POST">
  <input type="hidden" name="action" value="publish">
	
  <h2>Publish Funding Sources </h2>
	<p>Go back to modify these funding sources. </p>  
    <p><input name="fundingId" type="checkbox" id="fundingId1" value="1">Funding Source 1</p>
    <table>
		<tr>
			<th>Alternative Name</th>
			<th>Revenue</th>
			<th>Tax Rate </th>
		</tr>
		<tr>
			<td>Alt Source 1</td>
			<td>10000</td>
			<td>0.047</td>
		</tr>
		<tr>
			<td>Alt Source 2</td>
			<td>50000</td>
			<td>0.08</td>
		</tr>	
	</table>
	
	
    <p><input name="fundingId" type="checkbox" id="fundingId2" value="1">Funding Source 2 </p>
    <table>
      <tr>
        <th>Alternative Name</th>
        <th>Revenue</th>
        <th>Tax Rate </th>
      </tr>
      <tr>
        <td>Alt Source 1</td>
        <td>10000</td>
        <td>0.03</td>
      </tr>
      <tr>
        <td>Alt Source 2</td>
        <td>20000</td>
        <td>0.09</td>
      </tr>
    </table>
    <p>Choose the decision situation you would like to publish these projects to: 

	      Choose a Descision Situation:
      <select name="cctId">
        <option value = "1">CCT ID</option>
      </select>
      <p>
      <input type="submit" name="Submit" value="Publish!">
    </p>
  </html:form>
	
</body>
</html:html>

