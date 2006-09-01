<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>


<pg:fragment type="html">
	<pg:termHighlight styleClass="glossHighlight" url="glossaryView.do?id=">
	  <p>${infoObject.object.theme.summary}</p>
	</pg:termHighlight>
</pg:fragment>

<div id="structure_question_status">
	<span class="smalltext">${infoObject.numAgree} of ${infoObject.numVote} participants have said that this summary adequately reflects concerns expressed by participants.</span><br />
</div>
<div id="structure_question"></div>
	<pg:fragment type="script">

</pg:fragment>