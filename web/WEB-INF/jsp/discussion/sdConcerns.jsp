<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Step 1a: Brainstorm Concerns</title>
<!-- Site Wide CSS -->
<style type="text/css" media="screen">
@import "styles/lit.css";
</style>
<!-- End Site Wide CSS -->
<!-- Site Wide JavaScript -->
<script src="scripts/tags.js" type="text/javascript"></script>
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<script src="scripts/search.js" type="text/javascript"></script>
<!-- End Site Wide JavaScript -->
<!-- DWR JavaScript Libraries -->
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<!-- End DWR JavaScript Libraries -->
<!--CCT Specific  Libraries-->
<script type='text/javascript' src='/dwr/interface/SDAgent.js'></script>
<!--End CCT Specific  Libraries-->
<script type="text/javascript">

//GLOBAL Vars
	var sd = new Object;
	sd.isid = "<%= request.getParameter('isid') %>";
	sd.ioid = "<%= request.getParameter('isid') %>";
	sd.filterAnchor = "#filterJump";

	function getConcerns(tags, page, jump){
		if(jump){
			location.href = sd.filterAnchor;
		}
		alert("isid: " + sd.isid + " ioid: " + sd.ioid + " tags: " + tags + " page: " + page); 
		SDAgent.getConcerns({isid:sd.isid, ioid: sd.ioid, tags: tags, page: page}, {
			callback:function(data){
					if (data.successful){
					   	alert(data.source.html);
					}else{
						alert(data.reason);
					}
				},
			errorHandler:function(errorString, exception){ 
					alert("delete post error:" + errorString + exception);
			}
			});
	}
		
</script>

  </head><body>

  <!-- #container is the container that wraps around all the main page content -->
  <div id="container">

    <a name="filterJump"></a>
    <div id="discussion" style="background-image: url('images/addConcern.gif'); background-repeat: no-repeat; background-position: 730px 0;">
      <div id="discussionHeader">
        <div class="sectionTitle">
          <h3 id="discussionTitle">All Participants' Concerns</h3>
          <div id="filteredBy"></div>
          <input type="checkbox" id="myconcerns" onClick="checkMyConcerns();"/>
          Show only my concerns </div>
        
		
		<!-- Begin sorting menu -->
        <div id="sortingMenu" class="box4"> sort discussion by:
          <select>
            <option>Option</option>
            <option>Option Option Option Option Option</option>
            <option>Option</option>
            <option>Option</option>
            <option>Option</option>
          </select>
          <br />
          <div class="floatLeft">filter discussion by:</div>
          <form action="javascript: customFilterAction($('txtCustomFilter').value);" class="floatLeft">
            <input type="text" id="txtCustomFilter" value="Add a filter" onKeyUp="customFilter(this.value, event);"  onKeyUp="customFilter(this.value, event);" onClick="javascript:if(this.value==this.defaultValue){this.value = ''}"/>
            or <a href="#">Browse All Tags</a>
          </form>
          <div id="searchResults" style="display: none;"></div>
        </div>
		<!-- End sorting menu -->
		
		
      </div>
      <div id="discussion-cont" class="floatLeft">
        <!-- left col -->
      </div>
      <!-- end left col -->
      <div id="colRight" class="floatLeft box6 colRight">
        <!-- right col -->
        <h3>Add your own Concern</h3>
        <fieldset>
        <textarea id="txtAddConcern" style="width:100%; border: 1px solid #FFC978; height: 100px;" onClick="if(this.value==this.defaultValue){this.value = ''}">Type your concern here.</textarea>
        </fieldset>
        <div id="tagNewConcern" class="box6 padding5" style="display:none;">
          <h3>Tag your concern</h3>
          <p>Suggested tags:</p>
          <ul id="addConcernTagsList" class="tagsList">
            <!-- render suggested tags here -->
          </ul>
          <form action="javascript: addManualTag();">
            <input id="manualTag" type="text" value="Add your own tag!" onClick="if(this.value==this.defaultValue){this.value = ''}"/>
            <input type="button" value="Add" onClick="addManualTag();" />
          </form>
          <p><small>You must have at least 2 or more tags to continue.</small></p>
        </div>
        <div id="btnContinueCont">
          <input id="btnContinue" type="button" value="continue" onClick="prepareConcern();" />
        </div>
      </div>
      <!-- end right col -->
      <div class="clearBoth"></div>
    </div>
    <!-- end discussion -->
  </div>
  <!-- end container -->
  

  
   
  <script type="text/javascript">
		//getContextConcerns('', 1, false, cct.showOnlyMyConcerns);
		getConcerns('', 1, false);
</script>
  </body>
</html:html>
