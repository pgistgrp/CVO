<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>

<pg:fragment type="html">
	<pg:termHighlight styleClass="glossHighlight" url="glossaryView.do?id=">
	  <p>${infoObject.object.theme.summary}</p>
	</pg:termHighlight>
</pg:fragment>

<pg:fragment type="script">

</pg:fragment>
