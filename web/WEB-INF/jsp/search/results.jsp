<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="javascript" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<html:html>
    <!--####
        Project: Let's Improve Transportation!
        Page: Search Results
        Description: Shows the results of a search from the header
        Author(s): 
             Front End: Jordan Isip, Adam Hindman
             Back End: Zhong Wang, John Le
        Todo Items:
            [ ] custom tag/fix all links (Zhong/Jordan)
            [ ] Pagination (Jordan)
            [ ] Search projects,static pages, and user profiles (John)
    #### -->
	<head>
    	<title>Let's Improve Transportation - Global Search</title>
    	<!-- Site Wide JS -->
    	<script src="scripts/prototype.js" type="text/javascript"></script>
    	<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
    	<!-- Site Wide CSS -->
    	<style type="text/css" media="screen">
            @import "styles/lit.css";
            @import "styles/global-search.css";
            .capitalize{
                text-transform: capitalize;
            }
        </style>
    	<!-- End Site Wide CSS -->
	</head>
	<body>
	<!-- Begin the header - loaded from a separate file -->
	<div id="header">
		<!-- Begin header -->
		<jsp:include page="/header.jsp" />
		<!-- End header -->
	</div>
	<!-- End header -->

	<!-- Begin header menu - The wide ribbon underneath the logo -->
	<div id="headerMenu">
		<div id="headerContainer">
			<div id="headerTitle" class="floatLeft">
				<h3 class="headerColor">Search Results</h3>
			</div>
			<div class="headerButton floatRight"><a href="#">Browse by Keyword</a></div>
		</div>
	</div>
	<!-- End header menu -->
	<!-- #container is the container that wraps around all the main page content -->
	<div id="container">
        <html:form action="/search.do" method="GET">
            <html:hidden property="workflowId" name="searchForm" value="${param['workflowId']}"/>
            <html:text property="queryStr" name="searchForm" maxlength="25" size="25"/>
            <input type="submit" value="Search"> 
		</html:form>
				
		<div class="pagination box3 padding5">
		    You are currently viewing page: ${setting.page} of ${setting.pageSize} &nbsp;
		    <a href="#"><img src="http://www.letsimprovetransportation.org/images/btn_prev_b.gif" style="vertical-align:top"></a>
			<a href="#"><img src="http://www.letsimprovetransportation.org/images/btn_next_a.gif" style="vertical-align:top"></a>
		</div>
		<h2 class="headerColor" style="float:left;margin-right:.5em;">Searched for: </h2>
		<h2 class="contrast2">"${param.queryStr}"</h2>
		<h3 class="headerColor">Found: ${setting.rowSize} 
		<c:choose>
	      <c:when test="${setting.rowSize == 1}">
	          result
	      </c:when>
	      <c:otherwise>
	          results
	      </c:otherwise>
	    </c:choose></h3>

		<!-- Start body -->
		<div id="gs_results">
			<div id="gs_typecontainer">
			        <ol>
        			    <c:forEach var="result" items="${results}">
    					<li>
    					    <h3 class="headerColor">
    					        <span class="capitalize">${result.type}</a>
    					        <c:if test="${result.type=='post'}"><a href="/sdThread.do?isid=${result.isid}&ioid=${result.ioid}&pid=${result.postid}"></c:if>
                                <c:if test="${result.type=='reply'}"><a href="/sdThread.do?isid=${result.isid}&ioid=${result.ioid}&pid=${result.postid}#replyAnchor${result.replyid}"></c:if>
                                <c:if test="${result.type=='concern'}"><a href="concern.do?id=${result.concernid}"></c:if>
                                <c:if test="${result.type=='comment'}"><a href="concern.do?id=${result.concernid}#commentAnchor${result.commentid}"></c:if>
        					    ${result.title}</a>
    					    </h3> 
    					    <p>${fn:substring(result.body, 0, 500)}...<br />
        						<c:if test="${result.type=='post'}"><a href="/sdThread.do?isid=${result.isid}&ioid=${result.ioid}&pid=${result.postid}"></c:if>
                                <c:if test="${result.type=='reply'}"><a href="/sdThread.do?isid=${result.isid}&ioid=${result.ioid}&pid=${result.postid}#replyAnchor${result.replyid}"></c:if>
                                <c:if test="${result.type=='concern'}"><a href="concern.do?id=${result.concernid}"></c:if>
                                <c:if test="${result.type=='comment'}"><a href="concern.do?id=${result.concernid}#commentAnchor${result.commentid}"></c:if>
                                View this ${result.type}</a>
        					</p>
    					</li>
		                </c:forEach>
    				</ol>
			</div>
			 <div class="pagination box3 padding5">
			 	You are currently viewing page: ${setting.page} of ${setting.pageSize} &nbsp;
				<a href="#"><img src="http://www.letsimprovetransportation.org/images/btn_prev_b.gif"></a>
				<a href="#"><img src="http://www.letsimprovetransportation.org/images/btn_next_a.gif"></a>
		  </div>

		</div>
	</div>
	<!-- end container -->
		
	<!-- start feedback form -->
	<pg:feedback id="feedbackDiv" action="cctView.do"/>
	<!-- end feedback form -->
	<!-- Begin header menu - The wide ribbon underneath the logo -->
	<div id="headerMenu">
		<div id="headerContainer">

			<div id="headerTitle" class="floatLeft">
				<h3 class="headerColor">Search Results</h3>
			</div>
			<div class="headerButton floatRight"><a href="#">Browse by Keyword</a></div>
		</div>
	</div>
	<!-- End header menu -->
	<div id="footer">

		<jsp:include page="/footer.jsp" />
	</div>
	<!-- End footer -->
	</body>
</html:html>

