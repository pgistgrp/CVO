<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
  .catSelected {
    background-color:#D6E7EF;
    cursor:pointer;
    font-weight:bold;
  }
  .catUnSelected {
    background-color:white;
    cursor:pointer;
    font-weight:normal;
  }
</style>

<c:set var="vtt" value="${infoObject.target}" scope="request" />
<div id="pathsPanel" style="width:100%; height:200px; border:1px solid #B4D579; overflow:auto;">
  <jsp:include page="vttModPaths.jsp" />
</div>
<br>
<div id="newPathContainer" style="width:100%; border:1px solid #B4D579;">
  <b><a href="javascript:toggleNewPathPanel();">Added new path</a></b>
  <div id="newPathPanel" style="width:100%; height:200px; overflow:auto; display:none;">
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
      <c:forEach var="catRef" items="${infoObject.target.cht.winnerCategory.children}" varStatus="loop">
      <li id="li-${catRef.id}" class="box6 tagsInline"><a href="javascript:infoObject.addToPath(${catRef.id},'${catRef}');">${catRef}</a></li>
      <li id="lihide-${catRef.id}" class="box6 tagsInline" style="display:none;">${catRef}</li>
      </c:forEach>
      </ul>
      <div class="clearBoth"></div>
    </div>
  </div>
</div>
<br>
<div id="pathInfo" style="width:100%; height:200px; border:1px solid #B4D579; overflow:auto;">
  No path selected.
</div>

