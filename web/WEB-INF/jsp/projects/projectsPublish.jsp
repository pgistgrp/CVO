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
  <html:form action="projectPublish.do" method="POST">
	<input type="hidden" name="action" value="publish">
  <h2>Publish Projects</h2>
	<p>Go back to modify these projects and alternatives</p>  
    <input name="projectId" type="checkbox" id="projectId" value="1">
    Project Name 1<br />
    Project Description - Blah blah blah blah
	<table>
		<tr>
			<th>Alternative Name</th>
			<th>Short Description</th>
			<th>Cost</th>
			<th>Sponsor</th>
			<th>GeoType</th>
		</tr>
		<tr>
			<td>Alt Proj 1</td>
			<td>This is the description of Alt Proj 1</td>
			<td>10000</td>
			<td>WSDOT</td>
			<td>Line</td>
		</tr>
		<tr>
			<td>Alt Proj 2</td>
			<td>This is the description of Alt Proj 2</td>
			<td>50000</td>
			<td>WSDOT</td>
			<td>Polygon</td>
		</tr>	
	</table>
	
	
    <p>&nbsp;    </p>
    <p>
      <input name="projectId" type="checkbox" id="projectId2" value="1">
Project Name 2 <br />
Project Description - Blah blah blah blah </p>
    <table>
      <tr>
        <th>Alternative Name</th>
        <th>Short Description</th>
        <th>Cost</th>
        <th>Sponsor</th>
        <th>GeoType</th>
      </tr>
      <tr>
        <td>Alt Proj 1</td>
        <td>This is the description of Alt Proj 1</td>
        <td>10000</td>
        <td>WSDOT</td>
        <td>Line</td>
      </tr>
      <tr>
        <td>Alt Proj 2</td>
        <td>This is the description of Alt Proj 2</td>
        <td>50000</td>
        <td>WSDOT</td>
        <td>Polygon</td>
      </tr>
    </table>
    <p>&nbsp;    </p>
    <p>Choose the decision situation you would like to publish these projects to: 

        <select name="cctId">
          <option value = "1">CCT ID</option>
      </select>
    </p>
    <p>&nbsp;</p>
    <p>
  
      <input type="submit" name="Submit" value="Publish!">
    </p>
  </html:form>
	
</body>
</html:html>

