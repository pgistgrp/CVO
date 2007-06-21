<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<pg:fragment type="html">
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


	<!-- #container is the container that wraps around all the main page content -->
	
	<div id="container">
		<!-- begin "overview and instructions" area -->
		<div id="overview" class="box2">
			<h3>Overview and Instructions</h3>
			<p>How are we going to pay for improvements to the regional transportation system?
				Some of the money for improvements comes from state and federal government. However,
				all of the projects you reviewed in Step 3A are not yet fully funded by these
				sources, and many are not funded at all. The purpose of the regional transportation
				ballot measure is to fund some of these projects with new regional tax increases.</p>
			<p>Below you can review and discuss five different kinds of regional tax increases
				that can be used to pay for transportation improvements.</p>
		</div>
		<!-- end overview -->
		<!-- begin Object -->
		<div id="object">
			<h3 class="headerColor">Available funding options</h3>
			<a href="javascript:Util.expandAll('objectives');">Expand all</a>
			<a href="javascript:Util.collapseAll('objectives');">Collapse all</a>
			<div id="obj-left" class="floatLeft"> 
			<div id="newtable">
				<table cellpadding=0 cellspacing=0>
					<tr class="tableHeading">
						<th class="first">Proposed Funding Sources</th>
						<th><span class="hiddenLabel" style="display:none">
						Estimated Money Raised</span></th>
						<th><span class="hiddenLabel" style="display:none">
						Estimated annual cost to the average taxpayer</span></th>
						<th><span class="hiddenLabel" style="display:none">
						Estimated annual cost to you</span></th>
					</tr>
					
					<c:forEach var="infoObject" items="${infoStructure.infoObjects}" varStatus="loop">
						<tr class="fundingType">
							<td class="fundingSourceItem"><a href="javascript:Util.toggleRow(${loop.index});">
							<img src="images/plus.gif" id="icon${loop.index}" class="icon"></a> <a href="javascript:Util.toggleRow(${loop.index});">${infoObject.object.source.name}</a></td>
							<td colspan="3">&nbsp;</td>
						</tr>
						<!-- end FUNDING source -->
						<!-- begin OPTIONS -->
						<tr style="display:none;" class="objectives" id="row${loop.index}">
							<td colspan="4">
								<table>
									<!-- start project alt -->
									<c:forEach var="altRef" items="${infoObject.object.altRefs}" varStatus="loop2">
										<tr>
											<td>${altRef.alternative.name}</td>
											<td>${altRef.alternative.revenue} million</td>
											<td>$${altRef.alternative.avgCost}</td>
											<td>$<c:out value="${userPkg.personalCost[altRef.id]}" /></td>
										</tr>
									</c:forEach>
									<!-- end project alt-->
								</table>
							</td>
						</tr>
						<tr>
							<td class="cost">&nbsp;</td>
							<td class="cost">&nbsp;</td>
							<td class="cost">&nbsp;</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			</div>
			<!-- begin cell containing Google Map object -->
			<div id="obj-right" class="floatRight">
				<h4 style="margin-top:0px;padding-top:0px;" class="headerColor centerAlign">The
					cost of tax increases</h4>
				<p>How did we come up with the annual personal cost figures? We estimate these
					figures based on data about residents in our region, such as income, miles driven
					per year, and average miles per gallon. Your personal annual cost figures are
					estimated based on the information you provided in the L.I.T. introductory questionnaire.</p>
				<div class="floatLeft" style="margin:1em"> 
				<a href="javascript:io.goToCalc();"><img src="images/calculator2.gif" border="0"></a> </div>
				<div class="floatLeft padding5" style="width:190px;margin-top:50px;"> <a href="javascript:io.goToCalc();">Calculate
						the cost of tax increases for you and learn how these costs are estimated</a> </div>
			</div>
			<!-- end cell containing Google Map object -->
			<!-- begin firefox height hack -->
			<div class="clearBoth"></div>
			<!-- end firefox height hack -->
		</div>
		<!-- end Object-->
	<!-- end container -->
	</div>
</pg:fragment>
<pg:fragment type="script"> 
	//All Javascript that is internal to this page must go here - not sdRoomMain.
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
	
	io.expandAll = function(){
		var rows = document.getElementsByClassName('objectives');
		var icons = document.getElementsByClassName('icon');
		//alert('icons: ' + icons.length + ' rows: ' + rows.length);
		for (var i = 1;i <= rows.length; i++){
			var row = 'objective' + i;
			var icon = 'icon' + i;
			$(row).show();
			$(icon).src = "images/minus.gif";
		}
		showLabels();
	}
	
	io.showLabels = function(){
		var labels = document.getElementsByClassName('hiddenLabel')
		for (var i = 0;i < labels.length;i++){
			labels[i].style.display = "";
		}
	}
	
	io.testOpenRows = function(){
		var rows = document.getElementsByClassName('objectives');
		io.hideLabels();
		for (var i = 1;i <= rows.length; i++){
			var row = 'objective' + i;
			if ($(row).style.display != "none"){
				io.showLabels();
			}else{}
		}
	}

	io.hideLabels = function(){
		var labels = document.getElementsByClassName('hiddenLabel')
		for (var i = 0;i < labels.length;i++){
			labels[i].style.display = "none";
		}
	}
	
	io.collapseAll = function(){
		var rows = document.getElementsByClassName('objectives');
		for (var i = 1;i <= rows.length; i++){
			var row = 'objective' + i;
			var icon = 'icon' + i;
			$(row).hide();
			$(icon).src = "images/plus.gif";
		}
		io.hideLabels();
	}

	io.toggleRow = function(project,icon){
		Effect.toggle(project, 'appear', {duration:.4, afterFinish:
			function(){
				if ($(project).style.display != ""){
					$(icon).src = "images/plus.gif";
					io.testOpenRows();
					}else{
						$(icon).src = "images/minus.gif";
						io.showLabels();
					}
				}
		});
	}
	/* *************** loading on getTargets() in SDRoomMain *************** */
	io.loadDynamicFile('/styles/step3b-reviewfunding.css');
	io.loadDynamicFile('/styles/table.css');
	io.loadDynamicFile('/dwr/interface/ProjectAgent.js');
	
	
	
	io.goToCalc = function(){
		window.open("fundingCalc.do?&fundSuiteId="+io.fundSuiteId,'Tax Calculator','width=1000,height=600,resizable=yes,scrollbars=yes');
	}
	
</pg:fragment>
