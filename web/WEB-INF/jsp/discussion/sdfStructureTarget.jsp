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
		[x] Integrate Layout (Adam)
		[ ] Integrate static text and link to static page - see mockup (Adam)
#### -->
<pg:fragment type="html">
	<!-- #container is the container that wraps around all the main page content -->
	
	<div id="container">
		<!-- begin "overview and instructions" area -->
		<div id="overview" class="box2">
			<h3>Overview and Instructions</h3>
			<p>How are we going to pay for improvements to the regional
				transportation system? Some of the money for improvements
				comes from state and federal government. However, all of
				the projects you reviewed in Step 3A are not yet fully funded
				by these sources, and many are not funded at all. The purpose
				of the regional transportation ballot measure is to fund
				some of these projects with new regional tax increases.</p>
			<p>Below you can review and discuss five different kinds of
				regional tax increases that can be used to pay for transportation
				improvements.</p>
		</div>
		<!-- end overview -->
		<!-- begin Object -->
		<div id="object">
			<div id="list">
				<h3 class="headerColor">Available funding options</h3>
				<div id="sdf-obj-left" class="floatLeft">
					<!-- begin list of funding options -->
					<div id="sdf-allListHeader headingColor">
						<div class="listHeaderHeader headingColor">
							<div class="sdf-col1 floatLeft"> <span class="sdf-listHeaderTitles">Funding
									Source</span> </div>
							<div class="sdf-col2 floatLeft"> <span class="sdf-listHeaderTitles">Money
									raised</span> </div>
							<div class="sdf-col3 floatLeft"> <span class="sdf-listHeaderTitles">Annual
									cost to you</span> </div>
							<div class="sdf-col4 floatLeft"> <span class="sdf-listHeaderTitles">Annual
									cost to average resident</span> </div>
							<div class="clearBoth"></div>
						</div>
						
						<c:forEach var="source" items="${sources}" varStatus="loop">
							<div id="source-${source.id}">
								<h4 class="headerColor">${source.name}</h4>
								<div class="sdf-listRow row">
									<c:forEach var="alternative" items="${source.alternatives}" varStatus="loop">
										<div class="sdf-col1 floatLeft">
											<div class="floatLeft">${alternative.name}</div>
										</div>
										<div class="sdf-col2 floatLeft smallText">${alternative.revenue}</div>
										<div class="sdf-col3 floatLeft smallText">${alternative.userCost}</div>
										<div class="sdf-col4 floatLeft smallText">${alternative.averageCost}</div>
										<div class="clearBoth"></div>
									</c:forEach>
								</div>
							</div>
						</c:forEach>
				</div>
				<!-- end list of funding options -->
			</div>
			<!-- end sdf-obj-left -->
			</div>
		</div>
		<!-- begin cell containing Google Map object -->
		<div id="sdf-obj-right" class="floatRight">
			<h4 style="margin-top:0px;padding-top:0px;" class="headerColor centerAlign">The
				cost of tax increases</h4>
			<p>How did we come up with the annual personal cost figures?
				We estimate these figures based on data about residents in
				our region, such as income, miles driven per year, and average
				miles per gallon. Your personal annual cost figures are estimated
				based on the information you provided in the L.I.T. introductory
				questionnaire.</p>
			<div class="floatLeft" style="margin:1em"> <a href="#"><img src="images/calculator2.gif" border="0"></a> </div>
			<div class="floatLeft padding5" style="width:250px;margin-top:50px;"> <a href="taxCalculator.do">Calculate
					the cost of tax increases for you and learn how these costs
					are estimated</a> </div>
		</div>
		<!-- end cell containing Google Map object -->
		<!-- begin firefox height hack -->
		<div class="clearBoth"></div>
		<!-- end firefox height hack -->
	</div>
	<!-- end Object-->
	</div>
	<!-- end container -->
	</pg:fragment>
	<pg:fragment type="script"> 
	//All Javascript that is internal
		to this page must go here - not sdRoomMain. //Add Javascript
		to build tree list /* *************** load a dynamic javascript
		or css file ****************/ io.loadDynamicFile = function(file){
		var start = file.indexOf('.') + 1 var finish = file.length
		var type = file.substring(start,finish) var headElem = document.getElementsByTagName('head')[0];
		if(type == "css"){ var cssLinkElem = document.createElement('link');
		cssLinkElem.setAttribute('href', file); cssLinkElem.setAttribute('type',
		'text/css'); cssLinkElem.setAttribute('rel', 'stylesheet');
		headElem.appendChild(cssLinkElem); }else{ //javascript var
		jsLinkElem = document.createElement('script'); jsLinkElem.setAttribute('src',
		file); jsLinkElem.setAttribute('type', 'text/javascript');
		headElem.appendChild(jsLinkElem); } } /* *************** loading
		on getTargets() in SDRoomMain *************** */ io.loadDynamicFile('styles/step3.css');
		io.loadDynamicFile('/dwr/interface/FundingAgent.js'); 
		</pg:fragment>
