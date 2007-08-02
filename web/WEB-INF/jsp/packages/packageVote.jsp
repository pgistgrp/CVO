<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
<title>Package Vote</title>
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

#voteBox
{
width:100%;
border: 1px solid #ADCFDE;
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

.voteHeader{background:#ADCFDE;font-size:12pt;}


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
<!-- Start Global Headers  -->
<wf:nav />
<wf:subNav />
<!-- End Global Headers -->
<!-- #container is the container that wraps around all the main page content -->
<div id="container">
  <!-- begin "overview and instructions" area -->
  <div id="overview" class="box2">
    <h3>Overview and instructions</h3>
    <p>It is now time to vote for the package (or packages) you are willing to recommend to decision makers. You have until midnight <strong><fmt:formatDate value="${current.endTime}" dateStyle="long" /></strong> to submit your vote. You can only vote once, and you cannot change your vote. The results of this vote will be included in a report to decision makers which you can review in Step 5.</p>
    <a href="#" onclick="Effect.toggle('hiddenRM','blind'); return false;adjustMapPosition();">Read more about this step</a>
		<p id="hiddenRM" style="display:none">The purpose of this vote is to determine which package can get the greatest degree of collective support by <em>LIT Challenge</em> participants. Feel free to refer to the package poll or discussion in Step 4a before casting your vote. Keep in mind that a recommendation that is supported by a strong majority of participants is likely to carry more weight. In the event that strong majority consensus does not emerge, the moderator will identify a minority endorsement package based on an analysis of final vote results.</p>
  </div>

  <!-- end overview -->
  <!-- begin Object -->
	<div id="object">
      <!-- begin one voting box -->
		<div id="voteBox" class="floatLeft clearBoth">
		<!-- begin voting headers -->
		<p>Please indicate your current willingness to recommend each of the following packages to decision makers.</p>
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
			<p class="floatRight"><input type="reset" class="padding5" value="Reset form and start over" /> <input type="submit" class="padding5" value="Submit Vote" /></p>
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
<!-- End foldable average grades table -->

</div>
<!-- end container -->
<!-- start feedback form -->
<pg:feedback id="feedbackDiv" action="cctView.do"/>
<!-- end feedback form -->
<!-- Start Global Headers  --> 
<wf:subNav />
<!-- End Global Headers -->
<!-- Begin footer -->
<div id="footer">
  <jsp:include page="/footer.jsp" />

</div>
<!-- End footer -->
</body>
</html:html>
