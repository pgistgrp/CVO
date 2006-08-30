<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<logic:iterate id="reply" name="replies">

	<div id="reply${reply.id}" class="replies">
		 <div id="replies_title" class="bluetitle"><a name="${reply.id}"></a>

		 	<span class="padding-sides"><strong>${reply.title}</strong> - <small>Posted on <fmt:formatDate value="${reply.createTime}" pattern="MM/dd/yy, hh:mm aaa"/> by: ${reply.owner.loginname}</small></span>
		 </div>
		
		<p>${reply.content}</p>
		
	 <c:if test="${fn:length(reply.tags) != 0}">
		<ul class="tagsList"><strong>tags: </strong>
			<logic:iterate id="tag" name="reply" property="tags">
				<li class="tagsList"><small>${tag.name}</small></li>
			</logic:iterate>
		<small>- click on a tag to view discussions with the same tag.</small>
	</c:if>
	</div>
	<br />
</logic:iterate>


	
	<c:if test="${setting.pageSize > 1}">
	More Pages: 
					<ul>
						<c:forEach var="i" begin="1" end="${setting.pageSize}" step="1">
							    <c:choose>
							      <c:when test="${setting.page == i }">
							     		<li class="activePage">${i}</li>
							      </c:when>
							      <c:otherwise>
							      		<li><a href="sdThread.do?isid=${structure.id}&pid=${post.id}&ioid=${infoObject.object.id}&page=${i}">${i}</a></li>
							      </c:otherwise>
							    </c:choose>
						</c:forEach>
					</ul>
	</c:if>
	<c:if test="${setting.page > 1}">
		<a href="sdThread.do?isid=${structure.id}&pid=${post.id}&ioid=${infoObject.object.id}&page=${setting.page - 1}">&#171; prev page</a>
	</c:if>
	
	<logic:notEqual name="setting" property="page" value="${setting.pageSize}">	
		<a href="sdThread.do?isid=${structure.id}&pid=${post.id}&ioid=${infoObject.object.id}&page=${setting.page + 1}">next page &#187; </a>
	</logic:notEqual>