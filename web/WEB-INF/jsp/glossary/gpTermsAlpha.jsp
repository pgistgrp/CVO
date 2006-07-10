<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>


		
		Jump to starts with letter:
		<ul>
			<li>#</li>
			<li>a</li>
			<li>b</li>
			<li>c</li>
			<li>d</li>
			<li>e</li>
			<li>f</li>
			<li>g</li>
			<li>h</li>
			<li>i</li>
			<li>j</li>
			<li>k</li>
			<li>l</li>
			<li>m</li>
			<li>n</li>
			<li>o</li>
			<li>p</li>
			<li>q</li>
			<li>r</li>
			<li>s</li>
			<li>t</li>
			<li>u</li>
			<li>v</li>
			<li>w</li>
			<li>x</li>
			<li>y</li>
			<li>z</li>
		</ul>
		<br><small><img src='/images/background-revision'> = This term has been flagged as 'revision needed' by participant(s)</small>
<table id="termListTable" class="listtable" cellspacing="1" frame="box" rules="all" width="100%">
	 <tr>
  	<th>Term Name</th><th>Short Definition</th><th>Comments</th><th>Views</th>
   </tr>
  <logic:iterate id="term" name="terms">
    <tr>
      <td><a href="glossaryView.do?id=${term.id}"><pg:highlight text="${filter}" style="color:white; background-color:red;">${term.name}</pg:highlight></a></td>
      <td><pg:highlight text="${filter}" style="color:white; background-color:red;">${term.shortDefinition}</pg:highlight></td>
      <td>${term.commentCount}</td>
      <td>${term.hitCount}</td>
    </tr>
  </logic:iterate>
</table>
