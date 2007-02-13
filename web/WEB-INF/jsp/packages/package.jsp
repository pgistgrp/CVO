<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<!--####
	Project: Let's Improve Transportation!
	Page: View Clustered Package
	Description: Allow users to view a single package (a selection of funding/projects)
	Author(s): 
	     Front End: Jordan Isip, Adam Hindman
	     Back End: Zhong Wang
	Todo Items:
		[x] Create Layout (Adam)
		[x] Initial Skeleton Code (Jordan)
		[ ] JavaScript/JSTL (Jordan)
		[ ] Test and Refine (Jordan)
#### -->
<html:html> 
<head>
<title>Package ${package.id}</title>
<!-- Site Wide JavaScript -->
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<!-- End Site Wide JavaScript -->

<!-- DWR JavaScript Libraries -->
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<!-- End DWR JavaScript Libraries -->

<!-- Site Wide CSS -->
<style type="text/css" media="screen">
@import "styles/lit.css";
</style>
	<!-- End Site Wide CSS -->
	<style type="text/css">

#obj-left
{
width:52%;
margin:1em .5em .5em 0em;
}

#obj-right
{
width:45%;
margin:0em 0em .5em .5em;
}

.odd {background: #E7F2F7}
.even {background: #ffffff}


.listRow
{
padding:.3em 0em;
}

.listRow h4 {margin:.5em 0em;}

#allListHeader
{
text-align:left;
}

.col1
{
width:400px;
font-size:1.1em;
margin-left:1em;

}

.col2
{
width:50px;
text-align:right;
font-weight:bold;
}

h4
{
font-size:1em;
}

.listRow h4 {margin:0em;}

#costSummary h4 
{
margin:2px 0px;
}

h4.subHeading {margin-left:.7em}

.highlight 
{
background:#FFF1DC;
}

.projCol1
{
width:325px;
font-size:1.1em;
margin-left:1em;
}

.projCol2
{
width:100px;
}

.fundingCol1
{
width:300px;
margin-left:1em;
}

.fundingCol2
{
width:100px;
}

.heading {margin:.5em auto;}



</style>
	<!-- Site Wide JS -->
	<script src="scripts/prototype.js" type="text/javascript"></script>
	<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
	<script src="scripts/search.js" type="text/javascript"></script>
	<script type='text/javascript' src='/dwr/engine.js'></script>
	<script type='text/javascript' src='/dwr/util.js'></script>

	<script src="scripts/prototype.js" type="text/javascript"></script>
	<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
	<script type="text/javascript">
		function expandList(project,icon){
			Effect.toggle(project, 'appear', {duration: .5, afterFinish:
				//window.setTimeout(toggleIcon,100);
				function(){
					if ($(project).style.display != ""){
						$(icon).src = "images/plus.gif";
						}else{
							$(icon).src = "images/minus.gif";
						}
					}
			});
		}
</script>
	</head>
	<body>
	<!-- #container is the container that wraps around all the main page content -->
	<div id="container">

		<!-- begin Object -->
		<div id="object">
			<div class="box4 padding5">
				<div class="floatLeft" id="packageHeader">
					<h3 class="headerColor">Package ${package.id}</h3>
				</div>
				<div class="clearBoth"></div>
			</div>

			<div id="obj-left" class="floatLeft clearBoth">
				<!--Begin project list -->
				<div id="projList">
					<!--start projects header-->
					<div class="listRow row headingColor heading">
						<div class="projCol1 floatLeft" style="margin-left:.2em">
							<div class="floatLeft">
								<h4>Projects included in this package</h4>
							</div>
						</div>
						<div class="projCol2 floatRight">
							<h4>Cost</h4>
						</div>
						<div class="clearBoth"></div>
					</div>
					<!--end projects header-->
					
					<c:forEach var="project" items="${package.projects}" varStatus="loop">
						<h3>${project.name}</h3>
						<c:forEach var="alt" items="${project.projAlts}" varStatus="loop">
							<div class="listRow row ${((loop.index % 2) == 0) ? 'even' : 'odd'}">
								<h4 class="subHeading">${alt.name}</h4>

								<div class="projCol2 floatRight">${alt.cost}</div>
								<div class="clearBoth"></div>
							</div>
						</c:forEach>
					</c:forEach>
				</div>
				<!--End project list -->
			</div>
			<!-- end obj-left -->
		</div>
		<!-- begin obj-right -->
		<!-- begin funding sources table -->
		<div id="obj-right" class="floatLeft">

			<!-- Begin funding sources table -->
			<div class="listRow row headingColor heading">
				<div class="fundingCol1 floatLeft" style="margin-left:.2em">
					<div class="floatLeft">
						<h4>Funding sources included in this package</h4>
					</div>
				</div>
				<div class="fundingCol2 floatRight">

					<h4>Money raised</h4>
				</div>
				<div class="clearBoth"></div>
			</div>
			<!--end funding headers -->

			<c:forEach var="source" items="${package.sources}" varStatus="loop">
				<h3>${project.name}</h3>
				<c:forEach var="alt" items="${source.fundAlts}" varStatus="loop">
					<div class="listRow row odd">
						<div class="fundingCol1 floatLeft">
							<div class="floatLeft">${alt.cost} increase</div>
						</div>
						<div class="fundingCol2 floatRight">${alt.revenue}</div>
						<div class="clearBoth"></div>
					</div>
				</c:forEach>
			</c:forEach>

			<div class="listRow row even">


				<div class="clearBoth"></div>
			</div>
		<!-- end funding sources table -->			
<br />
		<!-- Begin Cost Summary Div -->
        <div class="box2 padding5 floatLeft" id="costSummary">
          <div class="clearBoth">
            <div class="floatLeft">
              <h3>Total Cost</h3>

            </div>
            <div class="floatRight" id="totalCost">${package.totalCost}</div>
          </div>
          <div class="clearBoth">
            <div class="floatLeft">
              <h3>Total Funding</h3>
            </div>
            <div class="floatRight" id="totalFunding">${package.totalFunding}</div>

          </div>
          <div class="clearBoth">
            <div class="floatLeft">
              <h4>Cost to you</h4>
            </div>
            <div class="floatRight" id="costToYou">${packageStat.yourCost}</div>
          </div>
          <div class="clearBoth">

            <div class="floatLeft">
              <h4>Cost to the average resident</h4>
            </div>
            <div class="floatRight" id="costToAvg">${packageStat.avgCost}</div>
          </div>
          <div class="clearBoth"></div>
        </div>
        <!-- End Cost Summary Div -->

	
		</div>
		
	<!-- end obj-right -->
	</div>
	<!-- begin firefox height hack -->
	<div class="clearBoth"></div>
	<!-- end firefox height hack -->
	</div>
	<!-- end Object-->
	<div class="clearBoth"></div>

	</div>
	<!-- end container -->
	<!-- start feedback form -->
	<pg:feedback id="feedbackDiv" action="cctView.do"/>
	<!-- end feedback form -->
	</body>
</html:html>
