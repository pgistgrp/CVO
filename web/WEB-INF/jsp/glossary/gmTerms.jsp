<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>

<!--<h3>Listing of All Glossary Terms</h3>-->
		<!--<p><a href="javascript:newTerm();">New Glossary Term</a></p>-->
		<br><!--<br><small><img src='/images/background-revision'> = This term has been flagged as 'revision needed' by participant(s)</small>-->
<table id="termListTable" class="blueBB" cellspacing="2" cellpadding="4" frame="box" rules="all" width="100%">
	 <tr>
  	<th style="text-align: left" id="name">name</th><th style="text-align: left" id="def">a short definition</th><th style="text-align:center" id="comments">comments</a></th><th style="text-align:center" id="views">views</th><th style="text-align:center" id="actions">actions</th>
   </tr>
  <bean:define id="storeInitial" value="" />
	  <logic:iterate id="term" name="terms">
	    <tr id="glossaryTerm${term.id}">
	      <td><logic:notEqual name="storeInitial" value="${term.initial}"><a name="<bean:write name="term" property="initial"/>"><bean:define id="storeInitial" value="${term.initial}" /></logic:notEqual><a href="glossaryView.do?id=${term.id}"><pg:highlight text="${filter}" style="color:white; background-color:red;">${term.name}</pg:highlight></a></td>
	      <td><pg:highlight text="${filter}" style="color:white; background-color:red;">${term.shortDefinition}</pg:highlight></td>
	      <td style="text-align: center">${term.commentCount}</td>
	      <td style="text-align: center">${term.viewCount}</td>
		   <td style="text-align: center"><a onfocus="secondinit();" href="#lightbox1" rel="lightbox1" class="lbOn">edit attributes</a>|<a href='javascript:deleteTerm(${term.id});'>del</a></td>
	    </tr>
	  </logic:iterate>
	  <!--   <tr style="background-color: rgb(204, 0, 51);">
	      <td><a href="http://69.91.143.23:8080/glossaryView.do?id=1668">agh</a></td>
	      <td>This is not an actual term. Delete me.</td>
	      <td style="text-align: center;">0</td>
	      <td style="text-align: center;">1</td>
		  <td style="text-align: center;"><a href="#lightbox1" rel="lightbox1" class="lbOn">edit attributes</a>|<a href="">del</a></td>
	    </tr>-->
</table>
