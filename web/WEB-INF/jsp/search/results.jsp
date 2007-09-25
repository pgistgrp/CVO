<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="javascript" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>
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
    <!-- Start Global Headers  -->
        <wf:nav />
        <wf:subNav />
    <!-- End Global Headers -->

	<!-- #container is the container that wraps around all the main page content -->
	<div id="container">
		<form action="/sdSearch.do" method="GET">
            <input type="hidden" name="workflowId" value="${param.workflowId}" />
            <input type="hidden" name="count" value="${param.count}" />
            <input type="hidden" name="page" value="${param.page}" />
            <input type="text" name="queryStr" value="${param.queryStr}" />
            <input type="submit" value="Search"> 
		</form>
		
		<c:choose>
            <c:when test="${param.page}">
                <c:set var="page" value="param.page" />
            </c:when>
            <c:otherwise>
                <c:set var="page" value="1" />
            </c:otherwise>
        </c:choose>
				
		 <div class="pagination box3 padding5">
		 	Viewing page: ${setting.page} of ${setting.pageSize} &nbsp;

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
		<h2 class="headerColor" style="float:left;margin-right:.5em;">Global Search for: </h2>
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
    					    <h3 class="headerColor"><span class="capitalize">
    					        <c:choose>
    					           <c:when test="${result.type == 'staticpage'}">
    					               Learn More
    					           </c:when>
    					           <c:otherwise>
    					               ${result.type}
    					           </c:otherwise>
    					        </c:choose>
    					        :</span>
    					        <c:if test="${result.type=='post'}"><a href="/sdThread.do?workflowId=${result.workflowid}&activityId=${result.activityid}&contextId=${result.contextid}&isid=${result.isid}&ioid=${result.ioid}&pid=${result.postid}">${result.title}</a></c:if>
    					        <c:if test="${result.type=='reply'}"><a href="/sdThread.do?workflowId=${result.workflowid}&activityId=${result.activityid}&contextId=${result.contextid}&isid=${result.isid}&ioid=${result.ioid}&pid=${result.postid}#replyAnchor${result.replyid}">${result.title}</a></c:if>
    					        <c:if test="${result.type=='concern'}"><a href="/concern.do?workflowId=${result.workflowid}&activityId=${result.activityid}&contextId=${result.contextid}&id=${result.concernid}">${result.title}</a></c:if>
    					        <c:if test="${result.type=='comment'}"><a href="/concern.do?workflowId=${result.workflowid}&activityId=${result.activityid}&contextId=${result.contextid}&id=${result.concernid}">id=${result.concernid}#commentAnchor${result.commentid}">${result.title}</a></c:if>
    					        <c:if test="${result.type=='project'}"><a target="_blank" href="/projectAlt.do?workflowId=${result.workflowid}&activityId=${result.activityid}&contextId=${result.contextid}&altrefId=${result.projectaltid}">${result.projectaltname}</a></c:if>
    					        <c:if test="${result.type=='userprofile'}"><a href="/publicprofile.do?workflowId=${result.workflowid}&activityId=${result.activityid}&contextId=${result.contextid}&user=${result.loginname}">${result.loginname}</a></c:if>
                      <c:if test="${result.type=='staticpage'}"><a href="${result.url}?workflowId=${result.workflowid}&activityId=${result.activityid}&contextId=${result.contextid}&">${result.title}</a></c:if>
    					    </h3> 
    					    <p>
    					        <c:if test="${result.type != 'staticpage'}">
           					        <c:out value="${fn:substring(result.body, 0, 500)}" escapeXml="false" />...<br />
    					        </c:if>
    					        
    					        <c:if test="${result.type=='post'}"><a href="/sdThread.do?workflowId=${result.workflowid}&activityId=${result.activityid}&contextId=${result.contextid}&isid=${result.isid}&ioid=${result.ioid}&pid=${result.postid}">View this ${result.type}</a></c:if>
    					        <c:if test="${result.type=='reply'}"><a href="/sdThread.do?workflowId=${result.workflowid}&activityId=${result.activityid}&contextId=${result.contextid}&isid=${result.isid}&ioid=${result.ioid}&pid=${result.postid}#replyAnchor${result.replyid}">View this ${result.type}</a></c:if>
    					        <c:if test="${result.type=='concern'}"><a href="/concern.do?workflowId=${result.workflowid}&activityId=${result.activityid}&contextId=${result.contextid}&id=${result.concernid}">View this ${result.type}</a></c:if>
    					        <c:if test="${result.type=='comment'}"><a href="/concern.do?workflowId=${result.workflowid}&activityId=${result.activityid}&contextId=${result.contextid}&id=${result.concernid}">id=${result.concernid}#commentAnchor${result.commentid}">View this ${result.type}</a></c:if>
    					        <c:if test="${result.type=='project'}"><a target="_blank" href="/projectAlt.do?workflowId=${result.workflowid}&activityId=${result.activityid}&contextId=${result.contextid}&altrefId=${result.projectaltid}">View this ${result.type}</a></c:if>
    					        <c:if test="${result.type=='userprofile'}"><a href="/publicprofile.do?workflowId=${result.workflowid}&activityId=${result.activityid}&contextId=${result.contextid}&user=${result.loginname}">View ${result.loginname}'s Profile</a></c:if>
                      <c:if test="${result.type=='staticpage'}"><a href="${result.url}?workflowId=${result.workflowid}&activityId=${result.activityid}&contextId=${result.contextid}&">View ${result.title}</a></c:if>
        					</p>
    					</li>
		                </c:forEach>
    				</ol>
			</div>
			 <div class="pagination box3 padding5">
			 	Viewing page: ${setting.page} of ${setting.pageSize} &nbsp;

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
    <!-- Start Global Headers  -->
        <wf:subNav />
    <!-- End Global Headers -->
	<div id="footer">

		<jsp:include page="/footer.jsp" />
	</div>
	<!-- End footer -->
	</body>
</html:html>

