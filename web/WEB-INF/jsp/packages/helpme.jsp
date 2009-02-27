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
	Page: Help Me 
	Description: Users will use this component to re-weigh their criteria to have the system assist in the creation of a package.
	Author(s): 
	     Front End: Jordan Isip, Adam Hindman, Issac Yang
	     Back End: Zhong Wang, John Le
	Todo Items:
		[x] Initial Skeleton Code (Jordan)
		[ ] Add weigh criteria javascript - without setWeight (Jordan)
		[ ] Integrate Layout (Adam)

#### -->
<html:html>
<head>
<title>Manage Criteria</title>
<!-- Site Wide JavaScript -->
<script src="scripts/tags.js" type="text/javascript"></script>
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects" type="text/javascript"></script>
<script src="scripts/search.js" type="text/javascript"></script>
<!-- End Site Wide JavaScript -->

<!-- DWR JavaScript Libraries -->
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<!-- End DWR JavaScript Libraries -->

<!--Criteria Specific  Libraries-->
<script type='text/javascript' src='/dwr/interface/CriteriaAgent.js'></script>

	<script type="text/javascript" charset="utf-8">
		//START Global vars
		var cctId = "${cct.id}";			
		var sliderArray = new Array();
		var remainingWeight = 100;
		
		//END Global Vars
		
		/* *************** Pull all criteria and their associated weights and objectives (criteriaAssoc_weights.jsp) *************** */
		function getWeights(){
			CriteriaAgent.getWeights({cctId:cctId},{
			  callback:function(data){
			    if(data.successful){
			    	$('criteria').innerHTML = data.html;
			    	addAllSliders();
					updateRemainingWeight();
			    }else{
					alert(data.reason);
				}
			  },
			  errorHandler:function(errorString, exception){
			        alert("getWeights error:"+errorString+" "+exception);
			  }
			  });
		} 
		
		/* *************** Add All Criterion Sliders *************** */
		function addAllSliders(){
			<c:forEach var="criterion" items="${cct.criteria}" varStatus="loop">
				addSlider('${criterion.id}');
			</c:forEach>
		}

		/* *************** Assign a slider to a criteria and add it to the global slider array *************** */
		function addSlider(critId){
		  	newSlider = new Control.Slider('handle' + critId,'track' + critId,{
					onSlide:function(v){
						critWeight = (v * 100).toFixed(); //scriptaculous returns values ranging 0..1
						$('input' + critId).value= critWeight;
						updateRemainingWeight();
						},
					onChange:function(v){
						critWeight = (v * 100).toFixed();
						$('input' + critId).value=  critWeight;
						updateRemainingWeight();
						},
					minimum: 1,
					maximum: 100,
					sliderValue: $('input' + critId).value / 100 //grab value if user has already weighed this criteria
				});
			sliderArray.push(newSlider);
		}
		
		/* *************** Set the value of the slider if user manually sets it in the textbox *************** */
		function manualSliderChange(index, v){
			sliderArray[index].setValue(v / 100);
		}
		
		function updateRemainingWeight(){
			remainingWeight = 0; //reset remainingWeight
			for(i=0; i<sliderArray.length;i++){
				remainingWeight += sliderArray[i].value;
			}
			$('remainingWeight').innerHTML = 100 - (remainingWeight * 100).toFixed();
		}
		
		
		/* *************** Toggle simple tree menu - maybe pull this into an external file since a few files are now using this? *************** */			
		function expandList(objective,icon){
			Effect.toggle(objective, 'appear', {duration: .5, afterFinish:
				//window.setTimeout(toggleIcon,100);
				function(){
					if ($(objective).style.display != ""){
							$(icon).src = "/images/plus.gif";
						}else{
							$(icon).src = "/images/minus.gif";
						}
					}
			});
		};
	</script>
<event:pageunload />
</head>
<body>
	<h1>Help Me!</h1>
	<p>Answer the following questions so that we can suggest a package that matches your general preferences.
		you will be able to adjust your suggested package before moving on.</p>asdfs
		
	<!-- Start criteria headers -->
	<div class="criteriaListHeader">
	  <div class="weighCriteriaCol1 floatLeft">
	    <h4 class="headerColor">Planning factor</h4>
	  </div>
	  <div class="weighCriteriaCol2 floatLeft">
	    <h4 class="headerColor">Description</h4>
	  </div>
	  <div class="weighCriteriaCol3 floatLeft">
	    <h4 class="headerColor">Weight</h4>
	  </div>
	  <div class="clearBoth"></div>
	</div>
	<!-- end criteria headers -->
	
	<form action="createPackage.do" method="POST" accept-charset="utf-8">
		<h4>1) What are your preferred planning factor weights?  We will select projects with scores that best match your preferences.</h4>
		<c:forEach var="criterion" items="${criteria}" varStatus="loop">
			  <div id="criteria-${criterion.id}" class="criteriaListRow row ${((loop.index % 2) == 0) ? 'even' : ''}">
			    <div class="weighCriteriaCol1 floatLeft"><a href="#">
			      <div class="floatLeft"><a href="javascript:expandList('objectives${criterion.id}','icon${criterion.id}');"> <img src="/images/plus.gif" id="icon${criterion.id}"></a></div>
			      <div class="floatLeft"> ${criterion.name}</div>
			    </div>
			    <div class="weighCriteriaCol2 floatLeft">${criterion.na}</div>
			    <div class="weighCriteriaCol3 floatLeft">
			    	<!-- start slider bar -->
						<div id="track${criterion.id}" class="track" style="width:200px; height:9px;">
							<div id="track${criterion.id}-left" class="track-left"></div><div id="handle${criterion.id}" style="cursor: col-resize; width:19px; height:20px;"><img src="images/slider-handle.png" alt="" style="float: left;" /></div>
						</div>

						<input type="text" tabIndex="${loop.index + 1}" size="3" maxlength="3" id="input${criterion.id}"  name="critId" value = 
						<c:choose>
							<c:when test="${criterion.object.weight == null}">
								"0"
							</c:when>
							<c:otherwise>
								"${criterion.object.weight}"
							</c:otherwise>
						</c:choose>
						 /> <!-- end input -->

					<!-- end slider bar -->
			    </div>
			    <div class="clearBoth"></div>
			    <div class="objectives" id="objectives${criterion.id}" style="display:none;"><br /><strong>Objectives:</strong>
			      <ul class="smallText">
			        <c:if test="${fn:length(criterion.objectives) == 0}">
			          <li>None Selected</li>
			        </c:if>
			        <c:forEach var="objective" items="${criterion.objectives}" varStatus="loop">
			          <li>${objective.description}</li>
			        </c:forEach>
			      </ul>
			    </div>
			  </div>

			<div class="clearBoth"></div>
		</c:forEach>
		<p>Remaining Weight: <b id="remainingWeight"><!--load remaining weight here --></b></p>
		
		<h4>2) How much are you willing to pay per year?</h4>
			<p>What is the total annual cost <b>you</b> are willing to pay to fund your preferred transportation package? <input type="text" name="yourCost" /></P>
			<p>What is the total annual cost that the <b>average resident</b> should have to pay to fund your preferred transportation package? <input type="text" name="avgCost" /></p>
		<h4>3) Do you want us to include projects and funding sources already selected in your package, or suggest a new package?</h4>
			<label><input type="radio" name="includeCurrent" /> Include my current selection</label>
			<label><input type="radio" name="excludeCurrent" /> Create a brand-new package</label>
			
		<p><input type="submit" value="Submit &rarr;"></p>
	</form>
</body>
</html:html>