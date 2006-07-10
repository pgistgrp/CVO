<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<!-- add if moderator options -->
<h1>${term.name}</h1> Created on ${term.createTime} by ${term.creator}

<h2>Short Definition</h2>
	<p>${term.shortDefinition}</p>

<h2>Full Definition</h2>
	<p>${term.extDefinition}</p>
	
<h2>Sources</h2>
	<p>${term.sources}</p>

<h2>Term Links</h2>
	<p>${term.links}</p>

<h2>Related Terms</h2>
	<p>${term.relatedTerms}</p>