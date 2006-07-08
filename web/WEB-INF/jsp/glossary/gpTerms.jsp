<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<h3>Listing of All Glossary Terms</h3>
		<p><a href="javascript:newTerm();">New Glossary Term</a></p>
		<br><small><img src='/images/background-revision'> = This term has been flagged as 'revision needed' by participant(s)</small>
<table id="termListTable" class="listtable" cellspacing="1" frame="box" rules="all" width="100%">
	 <tr>
  	<th>Term Name</th><th>Short Definition</th><th>Comments</th><th>Views</th><th>Created Date</th><th>Actions</th>
   </tr>
  <logic:iterate id="term" name="terms">
    <tr>
      <td><a href="javascript:getTerm(${term.id}, 'view');">${term.name}</a></td>
      <td>${term.shortDefinition}</td>
      <td>${term.commentCount}</td>
      <td>${term.hitCount}</td>
      <td>${term.createTime}</td>
      <td>edit&nbsp;&nbsp;|&nbsp;&nbsp;del</td>
    </tr>
  </logic:iterate>
</table>

