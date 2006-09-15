<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
		
Jump to starts with letter:
	<ul>
	<logic:iterate id="initial" name="initials">
		<li><a href="#${initial}">${initial}</a></li>
	</logic:iterate>
	</ul>
		
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
		  <td style="text-align: center"><a href="#${term.id}" onclick='openEditBox(${term.id}); return false;' rel="lightbox1" class="lbOn">edit attributes</a>|<a href='javascript:deleteTerm(${term.id});'>del</a></td>
	    </tr>
		<tr>
		<td id='editrow${term.id}' colspan=5>
		<div id='editbox${term.id}' class='editbox' style='display:none;'><div>Future lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightboxFuture lightbox</div></div>
	  	</td>
		</tr>
	  </logic:iterate>
	  </table>
	  <!--  onmouseover="openEditBox(${term.id});" <tr style="background-color: rgb(204, 0, 51);">
	      <td><a href="http://69.91.143.23:8080/glossaryView.do?id=1668">agh</a></td>
	      <td>This is not an actual term. Delete me.</td>
	      <td style="text-align: center;">0</td>
	      <td style="text-align: center;">1</td>
		  <td style="text-align: center;"><a href="#lightbox1" rel="lightbox1" class="lbOn">edit attributes</a>|<a href="">del</a></td>
	    </tr>  changeLightBox(${term.id});onmouseout="clearArrays();"-->
		
		
		<!--<div id="leightcontainer" class="leightcontainer">
	<div id="leightbar" class="leightbar">
		<div class="lbclose"><a href='#' class="lbAction" rel="deactivate"><img id="closebut" class="close" onmouseout="javascript:this.src='images/closeinactivesm.gif'" onmouseover="javascript:this.src='images/closeactivesm.gif'" src='images/closeinactivesm.gif'/></a>
		</div>
	</div>

	<div id="lightbox1" class="leightbox">
		
		<div id="lightboxpadding" class="leightpadding">
			
			<h3>Editing Attributes for Glossary Term: ...</h3>
			<div>
				<label>Glossary Term</label>
				<br />
				<input type='text' value='...'/>
			</div>
			<br />
			<div>
				<label>Short Definition</label>
				<br />
				<input type='text' value='definition'/>
			</div>
			<br />
		
			<div>
				<label>Full Definition (optional - leave blank if none exists)</label>
				<br />
				<textarea rows=5 style='width:75%'>Full Definition goes here</textarea>
			</div>
			<br />
			<div>
				<label>Sources</label>
				<br />
				<a href=''>Source URL #1</a>
				<br />
				<a href=''>Source URL #2</a>
				<br />
				<label>Add Source:&nbsp;</label><input type='text' value='Http://'/>
			</div>
			<br />
			<div>
				<label>Term Links</label>
				<br />
				No links have been created.
				<br />
				<label>Add Link:&nbsp;</label><input type='text' value='Http://'/>
			</div>
			<br />
		
			<div>
				<label>
					Related Terms
				</label>
				<p>
					Automatically added related terms: term #1, term #2
				</p>
		
				<label>
					Manual relate a term
				</label>
				<input type='text' value=''/>
			</div>
			<br />
			<div>
				<label>
					Participant Comments
				</label>
				<br />
				NoSUV on August 28,2006 added the following comment:
				<br/>
				Comment #1&nbsp; | <a href=''>Delete</a>
			</div>
			<div style='float:right;'>
				<a style='background-color:#CCFF99;' href=''>Cancel</a>&nbsp;<a style='background-color:#CCFF99;' href='' class='lbsaveclose' rel='deactivate123'>Save and Close</a>
			</div>
		</div>
	</div>
</div>-->
