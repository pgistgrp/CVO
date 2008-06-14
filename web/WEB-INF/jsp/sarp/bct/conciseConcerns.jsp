<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:choose>
  <c:when test="${fn:length(concerns) == 0}">
      <div class="discussion-left box8">
        <p>No concerns, yet.</p>
      </div>
  </c:when>
  <c:otherwise>
    <div style="width:790px; height:550px; padding:3px; overflow:auto; background-color:#FFFAF0">
      <table style="width:760px;">
        <c:forEach var="concern" items="${concerns}" varStatus="loop">
          <tr><td>
            <table style="width:100%">
              <tr><td align="left">Author: <b>${concern.author.loginname}</b></td><td align="right"><b>${concern.numAgree} of ${concern.numVote} people agree so far</b></td></tr>
              <tr><td colspan="2">"${fn:escapeXml(concern.content)}"</td></tr>
              <tr>
              <c:choose>
                <c:when test="${loop.last}">
                  <td colspan="2">
                </c:when>
                <c:otherwise>
                  <td colspan="2" style="border-bottom:thin dotted black;">
                </c:otherwise>
              </c:choose>
                  <ul class="tagsInline">
                    <li class="tagsInline"><strong>Tags:</strong> </li>
                    <c:forEach items="${concern.tags}" var="tagref">
                      <c:choose>
                        <c:when test="${baseuser.id == concern.author.id}">
                          <li class="box6 tagsInline">
                        </c:when>
                        <c:otherwise>
                          <li class="box8 tagsInline">
                        </c:otherwise>
                      </c:choose>
                      <a href="javascript:infoObject.getConcerns(1, ${tagref.id});">${fn:escapeXml(tagref.tag.name)}</a></li>
                    </c:forEach>
                  </ul>
                </td>
              </tr>
            </table>
          </td></tr>
        </c:forEach>
      </table>
    </div>

    <div class="pagination">
            Viewing page: ${setting.page} of ${setting.pageSize} &nbsp;
          <logic:equal name="setting" property="page" value="1">
            <input type="button" value="Prev" alt="No Previous Pages" disabled="true" />
          </logic:equal>
          <logic:notEqual name="setting" property="page" value="1">  
          <input type="button" name="prev" id="prev" 
            value="Prev" onclick="infoObject.getConcerns(${setting.page}-1);" />
          </logic:notEqual>
          
          <logic:equal name="setting" property="page" value="${setting.pageSize}">
            <input type="button" value="Next" alt="No Additional Pages" disabled="true" />
          </logic:equal>
          <logic:notEqual name="setting" property="page" value="${setting.pageSize}">  
            <input type="button" name="next" id="next" value="Next" onclick="infoObject.getConcerns(${setting.page}+1)" />
          </logic:notEqual>
    </div>
  </c:otherwise>
</c:choose>

