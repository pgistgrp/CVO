<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>

<pg:fragment type="html">
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

  <ul>
    <logic:iterate id="infoObject" property="infoObjects" name="structure">
        <li><a href="javascript: infoStructure.getTargetPanes(${infoObject.id}); infoStructure.assignTargetHeaders('${infoObject.object.theme.title}');">${infoObject.object.theme.title}</a></li>
    </logic:iterate>
  </ul>
  
<div id="structure_question"></div>
</pg:fragment>

<pg:fragment type="script">
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
           		$('object_question').innerHTML = 'Does this summary adequately reflect concerns expressed by participants? <a href="javascript:infoStructure.setVote(true,' +ioid+ ',\"ioid\");"><img src="images/btn_yes_s.gif" alt="YES" border="0"><a href="javascript:infoStructure.setVote(false, ' +ioid+ ',\"ioid\");"><img src="images/btn_no_s.gif" alt="NO" border="0"></a>';
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
  
</pg:fragment>
