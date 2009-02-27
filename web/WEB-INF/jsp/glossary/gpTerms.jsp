<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
		
	
<table id="termListTable" class="blueBB" cellspacing="2" cellpadding="4" frame="box" rules="all" width="100%">
	 <tr>
  	<th style="text-align: left" id="name">name</th><th style="text-align: left" id="def">Short Definition</th><th style="text-align:center" id="comments">comments</a></th><th style="text-align:center" id="views">views</th>
   </tr>
  <bean:define id="storeInitial" value="" />
	  <logic:iterate id="term" name="terms">
	    <tr>
	      <td><logic:notEqual name="storeInitial" value="${term.initial}"><a name="<bean:write name="term" property="initial"/>"><bean:define id="storeInitial" value="${term.initial}" /></logic:notEqual><a href="glossaryView.do?id=${term.id}"><pg:highlight text="${filter}" style="color:white; background-color: #FFDF94;">${term.name}</pg:highlight></a></td>
	      <td><pg:highlight text="${filter}" style="color:white; background-color: #FFDF94;">${term.shortDefinition}</pg:highlight></td>
	      <td style="text-align: center">${term.commentCount}</td>
	      <td style="text-align: center">${term.viewCount}</td>
	    </tr>
	  </logic:iterate>
</table>
