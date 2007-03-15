<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=utf-8">
		<title>Weigh Planning Factors</title>
		<!-- Site Wide JavaScript -->
		<script src="/scripts/globalSnippits.js" type="text/javascript"></script>
		<script src="/scripts/tags.js" type="text/javascript"></script>
		<script src="/scripts/prototype.js" type="text/javascript"></script>
		<script src="/scripts/scriptaculous.js?control,effect" type="text/javascript"></script>
		<!-- End Site Wide JavaScript -->
		<!-- DWR JavaScript Libraries -->
		<script type='text/javascript' src='/dwr/engine.js'></script>
		<script type='text/javascript' src='/dwr/util.js'></script>
		<!-- End DWR JavaScript Libraries -->
		<!--Criteria Specific  Libraries-->
		<script type='text/javascript' src='/dwr/interface/CriteriaAgent.js'></script>
		
<!--####
	Project: Let's Improve Transportation!
	Page: Criteria Re-Weigh
	Description: This page allows users to re-weigh their criteria as well as see averge weights in the system.
	Author(s): 
	     Front End: Jordan Isip, Adam Hindman, Isaac Yang
	     Back End: Zhong Wang, John Le
	Todo Items:
		[ ] Initial Skeleton Code (Jordan)
		[ ] Integrate Weigh Criteria - Add "Average of all participants preferred weights" for each criterion.
#### -->




<script type="text/javascript" charset="utf-8">
			//START Global vars
			var cctId = "${cct.id}";			
			var sliderArray = new Array();
			var remainingWeight = 100;
			var range=new Array();
			for(a=0;a<101;a++){
			range.push(a);
			}
			
			
			//END Global Vars
			
			/* *************** Pull all criteria and their associated weights and objectives and average weights of all participants(criteriaAssoc_weightsWithAvg.jsp) *************** */
			function getWeightsWithAvg(){
				CriteriaAgent.getWeightsWithAVg({cctId:cctId},{
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
					addSlider('${criterion.id}',${loop.index});
					sliderArray[${loop.index}].setValue((($('input${criterion.id}').value)));
					addAvgSlider('${criterion.id});
					
				</c:forEach>
			}
			
			function getMaxValue(except){
				count=0;
				for(a=0;a<sliderArray.length;a++){
					count+=sliderArray[a].value;
				
				}
				return ((100-count)+except);
			}

			/****************** Assign a slider to the average criteria (sliders with average scores with have 'avghandle' + critId and 'avgtrack' +critId as its handle and track divs, 'avginput'+critId is the input)*/
			function addAvgSlider(critId){ 
				newSlider = new Control.Slider('avghandle' + critId,'avgtrack' + critId,{
						range:$R(0,100),
						minimum: 0,
						maximum: 100,
						values: range,
						sliderValue: $('avginput' + critId).value, //grab value if user has already weighed this criteria
						disabled: true
					});
			}
			
			/* *************** Assign a slider to a criteria and add it to the global slider array *************** */
			function addSlider(critId, index){
			  	newSlider = new Control.Slider('handle' + critId,'track' + critId,{
						onSlide:function(v){
						
							critWeight=0;
							
							
							
							
							if(sliderArray[index].value<=getMaxValue(sliderArray[index].value)){
								critWeight=sliderArray[index].value;
							
								sliderArray[index].setValue(critWeight);
							}else{
								critWeight=getMaxValue(sliderArray[index].value);
								
								sliderArray[index].setValue(critWeight);
							}
							
							manualSliderChange(index,critWeight);
							
							updateRemainingWeight();
							if((100-remainingWeight)==0){
								
								$('remainingWeightP').style.backgroundColor="#FFFF00";
								
							}else{
								
								$('remainingWeightP').style.backgroundColor="#FFFFFF";
							}
							
						},
						onChange:function(v){
						
							critWeight=0;
							
							
							
							if(sliderArray[index].value<=getMaxValue(sliderArray[index].value)){
								critWeight=sliderArray[index].value;
								$('input' + critId).value=critWeight;
								//setWeight(critId, critWeight);
							}else{
							critWeight=getMaxValue(sliderArray[index].value);
								sliderArray[index].setValue(critWeight);
							$('input' + critId).value=critWeight;
							
							}
							$('input'+critId).value=sliderArray[index].value;
							setWeight(critId, sliderArray[index].value);
							//manualSliderChange(index,critWeight);
							
							updateRemainingWeight();
							if((100-remainingWeight)==0){
								
								$('remainingWeightP').style.backgroundColor="#FFFF00";
								
					
							}else{
								
								$('remainingWeightP').style.backgroundColor="#FFFFFF";
							}
						},
						range:$R(0,100),
						minimum: 0,
						maximum: 100,
						values: range,
						sliderValue: $('input' + critId).value //grab value if user has already weighed this criteria
					});
				sliderArray.push(newSlider);
			}
			
			/* *************** Set the value of the slider if user manually sets it in the textbox *************** */
			function manualSliderChange(index, v){
				
				if((((100-remainingWeight) + (sliderArray[index].value) - (v))>=0)&&((100-remainingWeight)>=0)){
					
					sliderArray[index].setValue(v);
				}
				
				updateRemainingWeight();
			}
			
			function updateRemainingWeight(){
				remainingWeight = 0; //reset remainingWeight
				a=document.getElementsByTagName('input');
				for(i=0; i<sliderArray.length;i++){
					remainingWeight += sliderArray[i].value;
					for(b=0;b<=i;b++){
						if(a[b].tabIndex==(i+1)){
							a[b].value=sliderArray[i].value;
							break;
						
						}
					}
				}

		
				
				$('remainingWeight').innerHTML = ((100-remainingWeight));
				
			}
			

			
			/* *************** Set the weight of givin criterion *************** */
			function setWeight(critId, weight){
			$('saving-indicator').style.display="inline";
				
				CriteriaAgent.setWeight({cctId:cctId,critId:critId,weight:weight}, {
					callback:function(data){
						if (data.successful){
							
							
						}else{
							alert(data.reason);
						}
						$('saving-indicator').style.display="none";
					},
					errorHandler:function(errorString, exception){ 
					alert("CriteriaAgent.setWeight( error:" + errorString + exception);
					}
				});
				
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
		<style type="text/css" media="screen">
			@import "styles/lit.css";
		</style>
		
		<style type="text/css" media="screen">
			/*
			scriptaculous slider css
			*/
			
			/* put the left rounded edge on the track */
			.track-left {
				position: absolute;
				width: 5px;
				height: 9px;
				background: transparent url(images/slider-track-left.png) no-repeat top left;
			}
			
			/* put the track and the right rounded edge on the track */
			.track {
				background: transparent url(images/slider-track-right.png) no-repeat top right;
			}
			
			/*end scriptaculous slider css*/
			
			.criteriaListRow
			{
			background:#E7F2F7;
			padding:.3em 0em;
			}

			.criteriaListHeader
			{
			background:#fff;
			}

			#allCriteriaList
			{
			text-align:left;
			}

			.even {background: #ffffff}

			.weighCriteriaCol1
			{
			width:210px;
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
			width:330px;
			}

			.weighCriteriaCol3
			{
			margin-left:.5em;
			width:370px;
			}	

			h4
			{
			font-size:1em;
			margin:0px;
			padding:0px;
			}

			.objectives
			{
			padding:.5em;
			}
			
			#saving-indicator{
	display: none;
	background-color: red;
	color: white;
	position:absolute;
	top: 0;
	left:0;
	padding: 3px;
	z-index: 500;
}

#loading-indicator{
	
	background-color: red;
	color: white;
	position:absolute;
	top: 0;
	left:0;
	padding: 3px;
	z-index: 500;
}
		</style>
		
	</head>
	<body>
		<!-- Begin conditional styles for the benefit of IE -->
		<!--[if IE]>
		                          <style type="text/css">
		                          #sortingMenu {right:0px;}
								  #criteriaRuler {width:745px;}
								  
								  


div > div#saving-indicator{
position:fixed;
}

div > div#loading-indicator{
position:fixed;
}
</style>

		                          
		          <![endif]-->
		<!-- End conditional styles -->
		



		<!-- End header menu -->
		<!-- Begin loading indicator -->
		<div style="display: none;" id="loading-indicator"> Loading... 
			<img src="/images/indicator_arrows.gif"> 
		</div>
		<div style="display:none;" id="saving-indicator"> Saving...
			<img src="/images/indicator_arrows.gif">
		</div>
		<!-- End loading indicator -->
		<!-- Begin container - Main page content begins here -->
		<div id="container">
			<!-- begin "overview and instructions" area -->

			<!-- end overview -->
			<!-- begin object -->
			<div id="object">
				<!-- begin object content -->
				<div id="object-content">
					<div id="criteria">
						<!--load the criteria partial here -->
					</div>
				</div><!--end object content -->
					
					
				<div class="floatRight padding5">
					<input type="button" value="Submit" />
				</div>
				<div class="clearBoth">&nbsp;</div>
			</div><!-- end object -->
		</div><!--end container -->
				
		<!-- Run javascript function after most of the page is loaded, work around for onLoad functions quirks with tabs.js -->
		<script type="text/javascript" charset="utf-8">
			getWeightsWithAvg();
		</script>
		

		<!-- Begin footer -->
		<div id="footer"> </div>
		<!-- End footer -->
		<script type="text/javascript">
		
		</script>
	</body>
</html>