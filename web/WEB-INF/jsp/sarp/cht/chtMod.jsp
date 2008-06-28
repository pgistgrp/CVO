<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<div id="pathsPanel" style="clear:both;border-right:1px solid #B4D579">
  <c:set var="cht" scope="request" value="${infoObject.target}"/>
  <jsp:include page="chtPaths.jsp" />
</div>
<div style="clear:both;">
  <b>New Path:</b>
  <input type="text" id="newPath" readonly style="width:99%;"><br>
  <input type="button" id="addBtn" value="Add" onclick="infoObject.addPath();">
  <input type="button" id="clearBtn" value="Clear" onclick="infoObject.clearPath();">
</div>
<br>
<div style="clear:both;">
  <b>Categories:</b><br>
  <ul class="tagsInline">
  <c:forEach var="catRef" items="${infoObject.target.winnerCategory.children}" varStatus="loop">
  <li id="li-${catRef.id}" class="box6 tagsInline"><a href="javascript:infoObject.addToPath(${catRef.id},'${catRef}');">${catRef}</a></li>
  <li id="lihide-${catRef.id}" class="box6 tagsInline" style="display:none;">${catRef}</li>
  </c:forEach>
  </ul>
  <div class="clearBoth"></div>
</div>

