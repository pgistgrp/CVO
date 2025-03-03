<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!--####
	Project: Let's Improve Transportation!
	Page: Project Alternative Description
	Description: This page serves as an information page for a project alternative. 
	Author: Jordan Isip, Adam Hindman, Issac Yang
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[x] Integrate Layout (Adam)
		[ ] Integrate Project Map or static image (Guirong/Issac)
		[ ] Integrate Criteria Tree (Issac)
#### -->
<style type="text/css" media="screen">
	@import "styles/lit.css";
</style>
<style type="text/css">

#container {padding:1em;font-size:10pt;}

#criteria {margin-bottom:2em;}

.criteriaListRow
{
background:#E7F2F7;
padding:.3em 0em;
}

.criteriaListHeader {padding:.5em .3em}

#allCriteriaList
{
text-align:left;
}

.even {background: #ffffff}

.weighCriteriaCol1
{
width:325px;
margin-right:.5em;
}

.weighCriteriaCol1 img
{
margin:0px 3px 0px 0px;
vertical-align:middle;
border:0px;
}

.weighCriteriaCol2
{
width:525px;
}

.weighCriteriaCol3
{
margin-left:.5em;
width:50px;
text-align:center;
}	

h4
{
font-size:1em;
margin:0px;
padding:0px;
text-align:left;
}

.objectives
{
padding:.5em;
}

#map
{
margin:.5em 0em .5em 1em;
}

</style>
<!-- Site Wide JS -->
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<script src="scripts/search.js" type="text/javascript"></script>
<script src="scripts/qTip.js" type="text/javascript"></script>
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
<!-- #container is the container that wraps around all the main page content -->
<div id="container">
<!-- begin Object -->
<div id="object">
	<h1>REFERENCE:${reference}</h1>
  <div id="prjAlternativeDescription">
    <h3 class="headerColor" id="project-title">${reference.alternative.name}</h3>
    <!-- begin cell containing Google Map object -->
    <div id="map" class="floatRight"></div>
    <!-- end cell containing Google Map object -->
    <!--begin project description -->
    <p>
    <h4 style="display:inline">Money needed to complete this
      project: </h4>
    <span id="project-moneyNeeded">
    $<fmt:formatNumber maxFractionDigits="0" value="${reference.alternative.cost/1000000}" /> million
    </span>
    </p>
    <p>
    <h4 style="display:inline">Sponsoring Agency: </h4>
    <span id="project-sponsoringAgency">${reference.alternative.sponsor}</span>
    </p>
    <p>
    <h4>Short Description</h4>
    <span id="project-shortDescription">${reference.alternative.shortDesc}</span>
    </p>
    <p>
    <h4>Detailed Description</h4>
    <span id="project-detailedDescription">${reference.alternative.detailedDesc} </span>
    </p>
    <p>
    <h4>Links to additional information about this project</h4>
    <span id="project-links">${reference.alternative.links}</span>
    </p>
    <p>
    <h4>Statement for</h4>
    <span id="project-statementFor">${reference.alternative.statementFor}</span>
    </p>
    <p>
    <h4>Statement against</h4>
    <span id="project-statementAgainst">${reference.alternative.statementAgainst}</span>
    </p>
    <!-- end project description -->
  </div>
</div>
<!-- end obj-left -->
<!-- begin firefox height hack -->
<div class="clearBoth"></div>
<!-- end firefox height hack -->
<!-- Load separate file content starting here -->
<div id="criteriaHeader" style="height:30px">
  <div class="floatLeft">
    <h3 class="headerColor">Planning Factor Grades for ${alternative.name}</h3>
  </div>
</div>
<!-- begin criteria  -->
<div id="criteria" class="box3 floatLeft">
  <!-- START All Criteria List -->
  <div id="allCriteriaList">
    <div class="criteriaListHeader headingColor">
      <div class="weighCriteriaCol1 floatLeft">
        <h4>Planning factor</h4>
      </div>
      <div class="weighCriteriaCol2 floatLeft">
        <h4>Description</h4>
      </div>
      <div class="weighCriteriaCol3 floatLeft">
        <h4>Grade</h4>
      </div>
      <div class="clearBoth"></div>
    </div>
    <c:forEach var="criterion" items="${criteria}" varStatus="loop">
      <div class="criteriaListRow row">
        <div class="weighCriteriaCol1 floatLeft"><a href="#">
          <div class="floatLeft"><a href="javascript:expandList('objectives1','icon1');">
			  <img src="images/plus.gif" id="icon1"></a></a> </div>
          <div class="floatLeft"><a href="#">${criterion.name}</a></div>
        </div>
        <div class="weighCriteriaCol2 floatLeft smallText">${criterion.description}</div>
        <div class="weighCriteriaCol3 floatLeft">${criterion.grade}</div>
        <div class="clearBoth"></div>
        <div class="objectives" id="objectives1" style="display:none;"> Objectives
          for this planning factor (${fn:length(criterion.objectives)}):<br/>
          <table border="0" width="90%">
            <tr>
              <td><strong>Objective</strong></td>
              <td><strong>Grade</strong></td>
            </tr>
            <c:forEach var="objective" items="${criterion.objectives}" varStatus="loop">
              <tr>
                <td>${objective.name}</td>
                <td>${objective.grade}</td>
              </tr>
            </c:foreach>
          </table>
        </div>
      </div>
    </c:forEach>
    <div class="criteriaListRow" style="padding:5px;">
      <p>Average grade based on equal weighting of all planning
        factors: ${alternative.criteria.equalWeights}<br />
        Average grade based on all participants' weighting of
        planning factors: ${alternative.criteria.allAverage}<br />
        Average grade based on your preferred weighting of planning
        factors: ${alternative.criteria.userWeight}</p>
    </div>
  </div>
  <!-- END All Criteria List -->
  <!-- end criteria -->
  <!-- Separate file content stops here -->
</div>
<!-- end container -->