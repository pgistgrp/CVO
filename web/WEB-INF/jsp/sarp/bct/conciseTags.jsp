<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:choose>
  <c:when test="${fn:length(tags) == 0}">
      <div style="width:235px;padding-left:5px;margin-bottom:10px;border:1px solid #D6E5EE;background:#F4F9FB;">
        <p>No keywords, yet.</p>
      </div>
  </c:when>
  <c:otherwise>
    <div style="width:135px; height:530px; padding:3px; overflow:auto; background-color:#FFFAF0;">
      <table style="width:130px;">
        <c:forEach var="tag" items="${tags}" varStatus="loop">
          <tr><td><div style="border:1px solid #D6E5EE;background:#F4F9FB;padding:1px;width:125px;"><a href="javascript:infoObject.getConcerns(1, ${tag.id});">${tag.tag.name} (${tag.times})</a></div></td></tr>
        </c:forEach>
      </table>
    </div>
    
    <div style="text-align:center; clear: left;margin-bottom: 0px;">
      ${setting.page} of ${setting.pageSize} &nbsp;<br>
      <logic:equal name="setting" property="page" value="1">
        <input type="button" value="Prev" alt="No Previous Pages" disabled="true" />
      </logic:equal>
      <logic:notEqual name="setting" property="page" value="1">  
      <input type="button" name="prev" id="prev" 
        value="Prev" onclick="infoObject.getTags(${setting.page}-1);" />
      </logic:notEqual>
      
      <logic:equal name="setting" property="page" value="${setting.pageSize}">
        <input type="button" value="Next" alt="No Additional Pages" disabled="true" />
      </logic:equal>
      <logic:notEqual name="setting" property="page" value="${setting.pageSize}">  
        <input type="button" name="next" id="next" value="Next" onclick="infoObject.getTags(${setting.page}+1)" />
      </logic:notEqual>
    </div>
  </c:otherwise>
</c:choose>

