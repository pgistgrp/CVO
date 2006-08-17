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
