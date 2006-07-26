<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<logic:iterate id="theme" name="themes">
<div id="anotherPanel${theme.id}">
	<div id="overviewheader${theme.id}"  class="accordionTabTitleBar ">${theme.title}</div>
	<div id="overviewtext${theme.id}"><p id="editSummary${theme.id}">
		<logic:equal name="theme" property="summary" value="">
		Summary for "${theme.title}" has not been created yet.  Click to edit summary.
		</logic:equal>
		<logic:notEqual name="theme" property="summary" value="">
		${theme.summary}
		</logic:notEqual>
		</p>
	</div>					
</div>	


<!-- I need this script to make each summary <p> turn into in place edit boxes 
<script type="text/javascript" language="javascript">
  // <![CDATA[
   new Ajax.InPlaceEditor('editSummary${theme.id}', '/demoajaxreturn.html', {rows: 10, cols: 70});
  // ]]>
</script>
-->

<!-- this alert is not working -->
<script type="text/javascript">
<!--
  alert ("Hello from inline script");
-->
</script>
</logic:iterate>
