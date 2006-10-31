<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>


<pg:fragment type="html">
	<div class="voting">
		<h4>Quick Vote</h4>
		<div id="structure_question_status">
			<p><span class="smalltext" style="line-height: 15px; font-style: italic; "><strong>${infoObject.numAgree} of ${infoObject.numVote}</strong> participants have said that this summary adequately reflects concerns expressed by participants.</span></p>
		</div>
		<div id="structure_question"></div>
</div>
	<pg:termHighlight styleClass="glossHighlight" url="glossaryView.do?id=">
	  <p>${infoObject.object.theme.summary}</p>
	</pg:termHighlight>
</pg:fragment>

	<pg:fragment type="script">

</pg:fragment>