<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>

<pg:fragment type="html">
  <ul>
    <logic:iterate id="infoObject" property="infoObjects" name="structure">
        <li><a href="javascript: infoStructure.getTargetPanes(${infoObject.object.id});">${infoObject.object.theme.title}</a></li>
    </logic:iterate>
  </ul>
</pg:fragment>

<pg:fragment type="script">

  infoStructure.getDetails = function(ioid){
    SDAgent.getSummary({ioid:ioid}, {
      callback:function(data){
          if (data.successful){
           $(infoStructure.isDivElement).innerHTML = data.source.html;
          }else{
            alert(data.reason);
          }
      },
      errorHandler:function(errorString, exception){
          alert(errorString + exception);
      }
    });
  };
  
</pg:fragment>
