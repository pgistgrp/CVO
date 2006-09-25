<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>		
		
		

 <logic:iterate id="term" name="terms">
<c:if test="${fn:length(terms) != 0}">
	<h3>Proposed Glossary Terms by Participants - Waiting for Moderator Approval</h3>
		<table id="proposedTermListTable" class="blueBB" cellspacing="2" cellpadding="4" frame="box" rules="all" width="100%">
	 <tr>
  	<th style="text-align: left" id="name">term</th><th style="text-align: left" id="def">a short definition</th><th style="text-align:center" id="proposedBy">proposed by</th>
   </tr>
 
	    <tr>
	      <td><a href="glossaryView.do?id=${term.id}">${(term.name)}</a></td>
	      <td>${term.shortDefinition}</td>
	      <td style="text-align: center">${term.creator.loginname}</td>
	      <!--<td style="text-align: center">${term.viewCount}</td>-->
	    </tr>
	  
	  <!--   <tr style="background-color: rgb(204, 0, 51);">
	      <td><a href="http://69.91.143.23:8080/glossaryView.do?id=1668">agh</a></td>
	      <td>This is not an actual term. Delete me.</td>
	      <td style="text-align: center;">0</td>
	      <td style="text-align: center;">1</td>
		  <td style="text-align: center;"><a href="#lightbox1" rel="lightbox1" class="lbOn">edit attributes</a>|<a href="">del</a></td>
	    </tr>-->
		</c:if>
		</logic:iterate>
</table>





<!--<div>
	<h3>Proposed Glossary Terms by Participants - Waiting for Moderator Approval</h3>
	<table frame="box" rules="all">
	<tbody>
	<tr><th style="text-align: center;" width="89">Term&nbsp;&nbsp;</th><th style="text-align: left;" width="514">Short Definition&nbsp;&nbsp;</th><th style="text-align: center;" width="146">Proposed By:</th>
	</tr><tr>
	<td style="text-align: center;"><a href="">Collision</a></td>
	<td>Physical impact between two or more ships or vessels used for navigation.</td>
	<td style="text-align: center;">NoSUV</td>
	</tr>
	<tr>
	<td style="text-align: center;"><a href="">Safety</a></td>
	<td>The condition of being protected against failure, damage, error, accidents, or harm.</td>
	<td style="text-align: center;">NoSUV</td>
	</tr>
	</tbody>
	</table>
	</div>-->