<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>

<pg:fragment type="html">
	<pg:termHighlight styleClass="glossHighlight" url="glossaryView.do?id=">
	  <p>${infoObject.object.theme.summary}</p>
	</pg:termHighlight>
</pg:fragment>

	<div id="object_question" class="smalltext">
		
	</div>
	
	<pg:fragment type="script">

</pg:fragment>