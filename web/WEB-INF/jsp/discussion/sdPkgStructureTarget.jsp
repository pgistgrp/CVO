<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<pg:fragment type="html">
	<!--####
		Project: Let's Improve Transportation!
		Page: Object for Structured Discussion for Packages
		Description: This page will list and map out all of the clustered packages in the given decision situation.
		Author(s): 
		     Front End: Jordan Isip, Adam Hindman
		     Back End: Zhong Wang
		Todo Items:
			[x] Initial Skeleton Code (Jordan)
			[x] Integrate Structured Discussion (Jordan)
			[x] Integrate Adam's Layout (Jordan) 
			[ ] Test with backend contractor code (Jordan)
			[ ] Highlight User's related package (Jordan)
			[ ] Dynamic info in overview and instructions (Jordan)
	#### -->

		<!-- begin "overview and instructions" area -->
		<div id="overview" class="box2">
			<h3>Overview and instructions</h3>
			<p>In this step we determine which single package participants are willing to recommend to decision makers. Based on the <strong>283</strong> unique packages created by participants in Step 3, a set of new "candidate packages" were generated through a statistical process.</p>
			<ul>
				<li>Review, compare, and evaluate the candidate packages.</li>
				<li>Discuss with other participants which package should be collectively recommended to decision makers.</li>
				<li>When you are ready, express your preferences in the <a href="javascript:io.goToPackagePoll();">package poll</a></li>
			</ul>
			<a href="#" onclick="Effect.toggle('hiddenRM','blind'); return false;adjustMapPosition();">Read more about this step</a></p>
				<p id="hiddenRM" style="display:none">Participants created <strong>283</strong> unique packages in Step 3. In order to narrow down the field of packages under consideration we generated a small set of new packages through a statistical process.  These new packages collectively represent the diversity of packages created by participants in Step 3 (<pg:url page="lmFaq.do" target="_blank" anchor="step4-clustered">read about how we did this</pg:url>). The goal of this step is to work together to determine which package can get the greatest degree of collective support by <em>LIT Challenge participants</em>. The <a href="javascript:io.goToPackagePoll();">package poll</a> is intended to help inform this discussion. In the event that strong majority consensus does not emerge in the final vote, the moderator will identify a minority endorsement package based on an analysis of final vote results.</p>
		</div>
		<!-- end overview --> 
		<!-- begin Object -->
		<div id="object">
			<!-- begin obj-left -->
			<div id="obj-left" class="floatLeft clearBoth">
				<!-- begin list of funding options -->
				<div id="allListHeader">
					<div class="listHeaderHeader">
						<h3 class="headerColor">LIT packages (created based on participant input)</h3>
						<br/>
						<small>The package you created most closely matches the <span class="highlight">highlighted</span> package below.</small>
						</p>
						<div class="clearBoth"></div>
					</div>
					<div class="listRow row headingColor">
						<div class="col1 floatLeft">
							<div class="floatLeft">&nbsp;</div>
						</div>
						<div class="col2 floatLeft">Total</div>
						<div class="col3 floatLeft">Total Cost for you</div>
						<div class="col4 floatLeft">Cost for avg. resident</div>
						<div class="clearBoth"></div>
					</div>
					<pg:sort name="infoObjects" items="${infoStructure.infoObjects}" key="object.description" />
					<c:forEach var="infoObject" items="${infoObjects}" varStatus="loop">
						<c:if test="${infoObject.object.manual == false}">
							<div class="listRow row ${(package.id == userClusteredPkgId) ? 'box5': ''}""> <!-- use 'highlight' css class to highlight user's related package -->
								<div class="col1 floatLeft">
									<div class="floatLeft"><a href="javascript:io.goToPackage(${infoObject.object.id});">${infoObject.object.description}</a></div>
								</div>
								<div class="col2 floatLeft">
								$<fmt:formatNumber maxFractionDigits="1" value="${infoObject.object.totalCost/1000000000}" /> billion
								</div>
								<div class="col3 floatLeft">
                  <pg:totalCost2u var="cost2u" pkg="${infoObject.object}" />
                  $<fmt:formatNumber maxFractionDigits="0" value="${cost2u}" /> / year
                </div>
								<div class="col4 floatLeft">
								$<fmt:formatNumber maxFractionDigits="0" value="${infoObject.object.avgResidentCost}" /> / year</div>
								<div class="clearBoth"></div>
							</div>
						</c:if>
					</c:forEach>

					<p>
						
					<c:forEach var="infoObject" items="${infoStructure.infoObjects}" varStatus="loop">
						<c:if test="${infoObject.object.manual}">
							<div class="listRow row">
								<div class="col1 floatLeft">
									<div class="floatLeft"><a href="javascript:io.goToPackage(${infoObject.object.id});">${infoObject.object.description}</a></div>
								</div>
								<div class="col2 floatLeft">
								$<fmt:formatNumber maxFractionDigits="1" value="${infoObject.object.totalCost/1000000000}" /> billion </div>
								<div class="col3 floatLeft">
                  <pg:totalCost2u var="cost2u" pkg="${infoObject.object}" />
                  $<fmt:formatNumber maxFractionDigits="0" value="${cost2u}" /> / year
                </div>
								<div class="col4 floatLeft">$<fmt:formatNumber maxFractionDigits="0" value="${infoObject.object.avgResidentCost/1000000}" /> / year</div>
								<div class="clearBoth"></div>
							</div>
						</c:if>
					</c:forEach>
					
					
					<br>

				</div>
				<!-- end list of funding options -->
			</div>
			<!-- end obj-left -->
			<!-- begin cell containing Google Map object -->
			<div id="obj-right" class="floatRight">
			  <p style="margin-top:20px;font-size:14pt;">Express your preferences in the <strong><a href="javascript:io.goToPackagePoll();">package poll</a></strong></p>
      </div>
			<!-- end cell containing Google Map object -->
			<!-- begin firefox height hack -->
			<div class="clearBoth"></div>
			<!-- end firefox height hack -->
		</div>
		<!-- end Object-->
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
	io.loadDynamicFile('/styles/step4-start.css');
	io.loadDynamicFile('/dwr/interface/ProjectAgent.js');
	
	/* *************** Build Package Link *************** */
	
	io.goToPackage = function(id){
		window.open("package.do?"+io.wfInfo+"&pkgId="+id+"&fundSuiteId="+io.fundSuiteId+"&projSuiteId="+io.projSuiteId+"&critSuiteId="+io.critSuiteId+"&pkgSuiteId="+io.pkgSuiteId,'_blank','width=1000,height=600,resizable=yes,scrollbars=yes');
	}
	
	io.goToPackagePoll = function(){
		window.open("packagePoll.do?"+io.wfInfo+"&voteSuiteId="+io.voteSuiteId+"&fundSuiteId="+io.fundSuiteId+"&projSuiteId="+io.projSuiteId+"&critSuiteId="+io.critSuiteId+"&pkgSuiteId="+io.pkgSuiteId,'_blank','width=1000,height=600,resizable=yes,scrollbars=yes');
	}
	


</pg:fragment>
