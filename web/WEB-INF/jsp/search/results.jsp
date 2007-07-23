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
		
		<c:choose>
            <c:when test="${param.page}">
                <c:set var="page" value="param.page" />
            </c:when>
            <c:otherwise>
                <c:set var="page" value="1" />
            </c:otherwise>
        </c:choose>
				
		 <div class="pagination box3 padding5">
		 	You are currently viewing page: ${setting.page} of ${setting.pageSize} &nbsp;

	 	    <c:choose>
	 	       <c:when test="${setting.page == 1}">
	 	           <img src="images/btn_prev_fade.gif" alt="No Previous Pages" />
	 	       </c:when>
	 	       <c:otherwise>
	 	           <a href="/search.do?workflowId=${param.workflowId}&queryStr=${param.queryStr}&page=${page - 1}"><img src="images/btn_prev_a.gif"></a>
	 	       </c:otherwise>
	 	    </c:choose>
	 	    
	 	    <c:choose>
	 	       <c:when test="${setting.page == setting.pageSize}">
	 	           <img src="images/btn_next_fade.gif" alt="No Additional Pages" />
	 	       </c:when>
	 	       <c:otherwise>
	 	           <a href="search.do?workflowId=${param.workflowId}&queryStr=${param.queryStr}&page=${page + 1}"><img src="/images/btn_next_a.gif"></a>
	 	       </c:otherwise>
	 	    </c:choose>
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
    					        <c:if test="${result.type=='post'}"><pg:url page="/sdThread.do" params="isid=${result.isid}&ioid=${result.ioid}&pid=${result.postid}">${result.title}</pg:url></c:if>
    					        <c:if test="${result.type=='reply'}"><pg:url page="/sdThread.do" params="isid=${result.isid}&ioid=${result.ioid}&pid=${result.postid}#replyAnchor${result.replyid}">${result.title}</pg:url></c:if>
    					        <c:if test="${result.type=='concern'}"><pg:url page="/concern.do" params="id=${result.concernid}">${result.title}</pg:url></c:if>
    					        <c:if test="${result.type=='comment'}"><pg:url page="/concern.do" params="id=${result.concernid}#commentAnchor${result.commentid}">${result.title}</pg:url></c:if>
    					    </h3> 
    					    <p><c:out value="${fn:substring(result.body, 0, 500)}" />...<br />
    					        <c:if test="${result.type=='post'}"><pg:url page="/sdThread.do" params="isid=${result.isid}&ioid=${result.ioid}&pid=${result.postid}">View this ${result.type}</pg:url></c:if>
    					        <c:if test="${result.type=='reply'}"><pg:url page="/sdThread.do" params="isid=${result.isid}&ioid=${result.ioid}&pid=${result.postid}#replyAnchor${result.replyid}">View this ${result.type}</pg:url></c:if>
    					        <c:if test="${result.type=='concern'}"><pg:url page="/concern.do" params="id=${result.concernid}">View this ${result.type}</pg:url></c:if>
    					        <c:if test="${result.type=='comment'}"><pg:url page="/concern.do" params="id=${result.concernid}#commentAnchor${result.commentid}">View this ${result.type}</pg:url></c:if>
        					</p>
    					</li>
		                </c:forEach>
    				</ol>
			</div>
			 <div class="pagination box3 padding5">
			 	You are currently viewing page: ${setting.page} of ${setting.pageSize} &nbsp;

		 	    <c:choose>
		 	       <c:when test="${setting.page == 1}">
		 	           <img src="images/btn_prev_fade.gif" alt="No Previous Pages" />
		 	       </c:when>
		 	       <c:otherwise>
		 	           <a href="/search.do?workflowId=${param.workflowId}&queryStr=${param.queryStr}&page=${page - 1}"><img src="images/btn_prev_a.gif"></a>
		 	       </c:otherwise>
		 	    </c:choose>
		 	    
		 	    <c:choose>
		 	       <c:when test="${setting.page == setting.pageSize}">
		 	           <img src="images/btn_next_fade.gif" alt="No Additional Pages" />
		 	       </c:when>
		 	       <c:otherwise>
		 	           <a href="search.do?workflowId=${param.workflowId}&queryStr=${param.queryStr}&page=${page + 1}"><img src="/images/btn_next_a.gif"></a>
		 	       </c:otherwise>
		 	    </c:choose>
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

