<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>

<ul>
	<logic:iterate id="infoObject" property="infoObjects" name="structure">
			<li><a href="javascript: infoStructure.getTargetPanes(${infoObject.object.id})';">${infoObject.object.theme.title}</a></li>
	</logic:iterate>
</ul>

<script type="text/JavaScript">
		function register(targetDivId){
			infoStructure.getTargetPanes = function(ioid){				
				SDAgent.getSummary({ioid:ioid}, {
				callback:function(data){
						if (data.successful){
							$(targetDivId).innerHTML = data.html;
						}else{
							alert(data.reason);
						}
					},
				errorHandler:function(errorString, exception){ 
						alert(errorString + exception);
				}
				});
			},
	 	};	
		
		infoStructure.getTargetDetails = function(id){
			for (i=0; i < targetObject.length; i++){
				if(targetObject[i].object.theme.id == id){
					$('object_column').innerHTML = '<h4>Summary for theme: ' + targetObject[i].object.theme.title + '</h4>';
					$('object_column').innerHTML += '<p>'+targetObject[i].object.theme.summary+'</p>';
					break;
				}
			}
	}
</script>
<!-- need to figure out a better way to pass this summary back to the main jsp. or do i need it returned as a javascript object?-->
<!--${object.object.theme.content}-->