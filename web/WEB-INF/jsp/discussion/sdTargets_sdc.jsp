<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<pg:fragment type="html">
<!--
<div id="themeSelector">
		<form id="Tselector" name="ThemeSelector" method="post" action="">
		  <label>
		  Jump To:
		  <select name="selecttheme" id="selecttheme" onChange="javascript: infoStructure.getTargetPanes(this.value); infoStructure.assignTargetHeaders(this.innerValue);">		  
		    <option value = "-1">Select a Theme</option>
		    <logic:iterate id="infoObject" property="infoObjects" name="structure">
		        <option value ="${infoObject.id}">${infoObject.object.theme.title}</option>
		    </logic:iterate>		
	      </select>
		  </label>
		  </form>
 </div>
 -->

        <table width="100%" border="0" cellspacing="0">
          <tr class="objectblue">
            <td width="50%"><a href="#">Concern Theme </a></td>
			<td width="40%"><a href="#">Last Post</a></td>
            <td width="10%" class="textcenter"><a href="#">Discussions</a></td> 
          </tr>		 

    <c:forEach var="infoObject" items="${structure.infoObjects}" varStatus="loop">
          <tr class="${((loop.index % 2) == 0) ? 'disc_row_a' : 'disc_row_b'}">
            <td><a href="/sdRoom.do?isid=${structure.id}&ioid=${infoObject.id}">${infoObject.object.theme.title}</a><br /><span class="smalltext">Discuss concerns related to traffic</span></td>
            <td><a href="#">Viva La Tables!!</a><br /><span class="smalltext"><span class="textright">6-03-2006</span> By John Le</span></td>
            <td class="textcenter"><a href="#">20</a></td>
          </tr>		  
    </c:forEach>
	  
        </table>
 <br />
  <span class="smalltext">${structure.numAgree} of ${structure.numVote} participants have said that this list of concern themes adequately reflects concerns expressed by participants.</span><br />
  
<div id="structure_question"></div>
</pg:fragment>

<pg:fragment type="script">
	/*
  infoStructure.defaultObjectTitle = "All Concern Themes";
  infoStructure.defaultDiscussionTitle = "All Concern Themes";
  infoStructure.defaultSidebarTitle = "All Concern Themes";
  infoStructure.sideBarTheme = "Concerns related to";
  infoStructure.defaultTargetNavText = "To view the summary of a concern theme, click on the theme name.";
  
  
  infoStructure.getDetails = function(ioid){
    SDAgent.getSummary({ioid:ioid}, {
      callback:function(data){
          if (data.successful){
          $(infoStructure.isDivTargetNavText).innerHTML = '<a href="javascript:infoStructure.getTargets(); infoStructure.getPosts();">Back to '+ infoStructure.defaultObjectTitle +'</a>';
           $(infoStructure.isDivElement).innerHTML = data.source.html;

           	if(data.voting == null){
           		$('object_question').innerHTML = 'Does this summary adequately reflect concerns expressed by participants? <a href="javascript:infoStructure.setVote(true,' +ioid+ ',\'ioid\');"><img src="images/btn_yes_s.gif" alt="YES" border="0"><a href="javascript:infoStructure.setVote(false, ' +ioid+ ',\'ioid\');"><img src="images/btn_no_s.gif" alt="NO" border="0"></a>';
           	}else{
           		$('object_question').innerHTML = 'Your vote has been recorded. Thank you for your participation.';
           	}
           
          }else{
            alert("data.successful != true" + data.reason);
          }
      },
      errorHandler:function(errorString, exception){
          alert("getDetails Error" + errorString + exception);
      }
    });
  };
  */
</pg:fragment>
