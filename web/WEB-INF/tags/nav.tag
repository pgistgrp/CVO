<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>

<div id="header">
    <div id="header-wrap" class="clearBoth">
        <div id="header-logo">
            <a href="/main.do"><img src="/images/mainlogo.png" alt="LIT LOGO" /></a>
        </div>
        
        <div id="header-navigation">
            <span><a href="main.do">Home</a></span>
            <span><pg:url page="/lmMenu.do">Learn More</pg:url></span>
            <c:choose>
            <c:when test="${baseuser != null}">
                <pg:hide roles="guest">
                  <span><pg:url page="/usercp.do">User Settings</pg:url></span>
                </pg:hide>
                <span><pg:url page="/logout.do">Log out</pg:url></span>
            </c:when>
            <c:otherwise>
                <span><pg:url page="/login.do">Log in</pg:url></span>
            </c:otherwise>
            </c:choose>
        </div>

        <!-- Begin Search -->
        <div id="header-search">
            <form action="/search.do" id="mysearch" method="GET">
                <div id="searchbox">
                    <input type="hidden" name="workflowId" value="${param.workflowId}"/>
                    <input type="text" name="queryStr" value="${param.queryStr}" style="width:120px"/>
                    <input type="submit" value="Search" />
                </div>
                <div id="submit" class="floatLeft"></div>
            </form>
        </div>
        <!-- End Search -->
    </div>	
    <div class="clearBoth"></div>
</div>

