<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!--####
	Project: Let's Improve Transportation!
	Page: Structured Discussion for Funding Sources
	Description: This page lists out all the available funding sources for the given decision situation.
	Author(s): Jordan Isip, Adam Hindman, Issac Yang
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[x] Integrate Structured Discussion (Jordan)
		[ ] Integrate Layout (Adam)
		[ ] Integrate static text and link to static page - see mockup (Adam)
#### -->

<pg:fragment type="html">
	<!-- begin "overview and instructions" area -->
	<div id="overview" class="box2">
		<h3 class="headerColor">Instructions</h3>
		<p>How are we going to pay for improvements.....</p>
		<p>Below you can review and discuss...</p>	
	</div>
	<!-- end overview -->
	
	<div id="list">
		<h3 class="headerColor clearBoth">Available Funding Options</h3>
		<ul>
		<c:forEach var="source" items="${sources}" varStatus="loop">
			<li id="source-${source.id}"><a href="#" title="${source.description}">${source.name}
				<ul>
					<c:forEach var="alternative" items="${source.alternatives}" varStatus="loop">
						<li>Funding Source: ${alternative.name}</li>
						<li>Money Raised: ${alternative.revenue}</li>
						<li>Annual Cost to You: ${alternative.userCost}</li>
						<li>Annual Cost to Average Resident: ${alternative.averageCost}</li>
					</c:forEach>
				</ul>
			</li>
		</c:forEach>
		</ul>
	</div>
	
	<div id="calculator"><!--right col for tax calculator-->
		<h4>The cost of tax increases</h4>
		<p>How did we come up with the annual personal cost figures?  We estimate these figures based on data
		about resident' in our region, such as income, miles driven per year, and average miles per gallon.
		Your personal annual cost figures are estimated based on the information you provided in the L.I.T.
		introductory questionnaire.</p>
		
		<p><a href="taxCalculator.do" target="blank">Calculate the cost of tax increases for you and learn how these costs are estimated</a></p>
	</div>

</pg:fragment>

<pg:fragment type="script">
	//All Javascript that is internal to this page must go here - not sdRoomMain.
	//Add Javascript to build tree list

	/* *************** load a dynamic javascript or css file ****************/

	io.loadDynamicFile = function(file){
		var start = file.indexOf('.') + 1
		var finish = file.length
		var type = file.substring(start,finish)
		
		var headElem = document.getElementsByTagName('head')[0];
		if(type == "css"){
			var cssLinkElem = document.createElement('link');
			cssLinkElem.setAttribute('href', file);
			cssLinkElem.setAttribute('type', 'text/css');
			cssLinkElem.setAttribute('rel', 'stylesheet');
			headElem.appendChild(cssLinkElem);
		}else{ //javascript
			var jsLinkElem = document.createElement('script');
			jsLinkElem.setAttribute('src', file);
			jsLinkElem.setAttribute('type', 'text/javascript');
			headElem.appendChild(jsLinkElem);
		}
		
	}
	
	/* *************** loading on getTargets() in SDRoomMain *************** */
	io.loadDynamicFile('styles/step3.css');
	io.loadDynamicFile('/dwr/interface/FundingAgent.js');
</pg:fragment>