<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<logic:iterate id="reply" name="replies">
	<div id="reply${reply.id}" class="blueBB">
		 <h4>theme >> ${reply.title}</h4>
		<small>Posted on <fmt:formatDate value="${reply.createTime}" pattern="MM/dd/yy, hh:mm aaa"/> by: ${reply.owner.loginname}</small>
		<p>${reply.content}</p>
		<ul class="tagsList"><strong>tags: </strong>
			<logic:iterate id="tag" name="reply" property="tags">
				<li class="tagsList">${tag.name}</li>
			</logic:iterate>
		<small>- click on a tag to view concerns with the same tag.</small>
	</div>
</logic:iterate>