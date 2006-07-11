<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Glossary Public View</title>
<link rel="stylesheet" type="text/css" href="/styles/pgist.css">
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type='text/javascript' src='/dwr/interface/GlossaryPublicAgent.js'></script>

</head>

<body>
<!-- add if moderator options -->
<div id="container">
	<div id="slate" class="leftBox">
		<p><a href="glossaryPublic.do">Back to All Glossary Terms</a></p>
		<h1>${term.name}</h1> | Created on ${term.createTime} by ${term.creator}
		<p></p>
		<p>${term.shortDefinition}</p>
		<p>${term.extDefinition}</p>
			
		<h3>Sources</h3>
		<ul>
			<logic:iterate id="onesource" property="sources" name="term">
				<li>${onesource}</li>
			</logic:iterate>
		</ul>
		<h3>Term Links</h3>
		<ul>
			<logic:iterate id="onelink" property="links" name="term">
				<li>${onelink}</li>
			</logic:iterate>
		
		</ul>
		<h3>Related Terms</h3>
		<ul>
			<logic:iterate id="oneterm" property="relatedTerms" name="term">
				<li>${oneterm}</li>
			</logic:iterate>
		</ul>
		<p><a href="glossaryPublic.do">Back to All Glossary Terms</a></p>
	</div>
</div>
</body>
</html>

