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
		Does this summary adequately reflect concerns expressed by participants? <a href="javascript:infoStructure.setVote(1, ${infoObject.object.id});"><img src="images/btn_yes_s.gif" alt="YES" border="0"><a href="javascript:infoStructure.setVote(0, ${infoObject.object.id});"><img src="images/btn_no_s.gif" alt="NO" border="0"></a>
	</div>
	
	<pg:fragment type="script">

</pg:fragment>