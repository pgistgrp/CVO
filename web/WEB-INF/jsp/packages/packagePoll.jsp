<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<!--####
	Project: Let's Improve Transportation!
	Page: Package Vote
	Description: Allow users to vote on packages.
	Author(s): 
	     Front End: Jordan Isip, Adam Hindman
	     Back End: Matt Paulin, Zhong Wang
	Todo Items:
		[x] Spec (Zhong) 
		[x] Initial Skeleton Code (Jordan)
		[x] Create Layout (Adam)
		[x] JavaScript/JSTL/Integrate Layout (Jordan)
		[ ] Add phase param from referring package- packageVote.jsp?phase=1 (Jordan)
		[ ] Backend form processing (Matt Paulin)
		[ ] Test and Refine (Jordan)
#### -->
<html:html> 
<head>
<title>Package Poll</title>
<!-- Site Wide JavaScript -->
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<script src="scripts/search.js" type="text/javascript"></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<!-- End Site Wide JavaScript -->

<!-- DWR JavaScript Libraries -->
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<!-- End DWR JavaScript Libraries -->

<script type='text/javascript' src='/dwr/interface/PackageAgent.js'></script>
<!-- Site Wide CSS -->
<style type="text/css" media="screen">
		@import "styles/lit.css";
</style>
<!-- End Site Wide CSS -->
<style type="text/css">

@import "styles/lit.css";

#voteBox
{
width:100%;
border: 1px solid #D6E7EF;
margin-bottom:1.5em;
padding:5px;
}

#voteBox p {margin:1em .5em;}

.odd {background: #D6E7EF;}
.even {background: #ffffff;}

.VoteListRow
{
padding:.3em 0em;
}

.voteCol1
{
width:16%;
margin-right:.5em;
padding-left:10px;
font-size:1.1em;
}

.voteCol2,.voteCol3,.voteCol4
{
width:26%;
text-align:center;
}

h4
{
font-size:1em;
margin:0em auto;
}

.highlight 
{
background:#FFF1DC;
}

.voteHeader{background:#D6E7EF;font-size:10pt;}



</style>

<script type="text/javascript" charset="utf-8">
	var pkgSuiteId = "${pkgSuiteId}";
	var projSuiteId = "${projSuiteId}";
	var fundSuiteId = "${fundSuiteId}";
	var critSuiteId = "${critSuiteId}";
	var voteSuiteId = "${voteSuite.id}";
	
	/* *************** Grab a Voting Object *************** */
	function setVoting(){	
		var choices = {};
		inputs = document.vote.elements;
		for (var i=0; i < inputs.length; i++) {
			if (inputs[i].type == "radio" && inputs[i].checked){
				var name = inputs[i].name.substring(3,inputs[i].name.length); //remove 'pkg'
				var strName = parseInt(name);
				var voteValue = parseInt(inputs[i].value)
				choices[strName] = voteValue;
			}
		}
		PackageAgent.setVoting(voteSuiteId, choices, {
			callback:function(data){
				if (data.successful){
					location.reload();
				}else{
					alert("setVoting Failed.  Reason: " +data.reason);
				}
			},
			errorHandler:function(errorString, exception){ 
			alert("PackageAgent.setVoting( error:" + errorString + exception);
			}
		});
	}

</script>
<event:pageunload />
</head>
<body>
<div id="container">
<!-- #container is the container that wraps around all the main page content -->
  <!-- begin Object -->
	<div id="object">
      <!-- begin one voting box -->
      <h3 class="headerColor">Package Poll</h3>
      		<p>Please indicate your willingness to recommend the following packages to decision makers.</p>
		<div id="voteBox" class="floatLeft clearBoth">
		<!-- begin voting headers -->
        <div class="VoteListRow row voteHeader">
          <div class="voteCol1 floatLeft">&nbsp;</div>
          <div class="voteCol2 floatLeft">I would <strong>enthusiastically recommend</strong> this package</div>
          <div class="voteCol3 floatLeft">I am <strong>willing to recommend</strong> this package if it receives greatest participant support</div>
          <div class="voteCol4 floatLeft">I would <strong>not recommend</strong> this package, regardless of its support among other participants</div>
          <div class="clearBoth"></div>
        </div>
		<!-- end voting headers -->
		<form name="vote" action="javascript:setVoting();">
			<c:forEach var="clusteredPkg" items="${voteSuite.pkgSuite.clusteredPkgs}" varStatus="loop">
			       <div class="VoteListRow row ${((loop.index % 2) == 0) ? 'even' : 'odd'}">
			         <div class="voteCol1 floatLeft">
			           <div class="floatLeft">
			               <pg:url target="blank" page="/package.do" params="pkgId=${clusteredPkg.id}&pkgSuiteId=${pkgSuiteId}&projSuiteId=${projSuiteId}&fundSuiteId=${fundSuiteId}&critSuiteId=${critSuiteId}">${clusteredPkg.description}</pg:url>
			           </div>
			         </div>
			         <div class="voteCol2 floatLeft"><input name="pkg${clusteredPkg.id}" value="1" type="radio" /></div>
			         <div class="voteCol3 floatLeft"><input name="pkg${clusteredPkg.id}" value="2" type="radio" /></div>
			         <div class="voteCol4 floatLeft"><input name="pkg${clusteredPkg.id}" value="3" type="radio" /></div>
			         <div class="clearBoth"></div>
			       </div>
			</c:forEach>
                        <c:if test="voteSuite.closed==false">
			  <p class="floatRight"><input type="reset" class="padding5" value="Reset form and start over" /> <input type="submit" class="padding5" value="Submit Vote" /></p>
                        </c:if>
		</form>
		
	</div><!-- end one voting box -->
	<div class="clearBoth"></div>	
    <!-- end obj-left -->
    <!-- begin firefox height hack -->
    <div class="clearBoth"></div>
    <!-- end firefox height hack -->
  </div>
  <!-- end Object-->
</div>
</div>
<!-- end container -->
</body>
</html:html>
