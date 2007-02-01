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
		

		<script type="text/javascript" charset="utf-8">
			//START Global vars
			var cctId = "${cct.id}";			
			var sliderArray = new Array();
			var remainingWeight = 100;
			var currentSliderValue=0;
			
			
			//END Global Vars
			
			/* *************** Pull all criteria and their associated weights and objectives (criteriaAssoc_weights.jsp) *************** */
			function getWeights(){
				CriteriaAgent.getWeights({cctId:cctId},{
				  callback:function(data){
				    if(data.successful){
				    	$('criteria').innerHTML = data.html;
				    	addAllSliders();
				    	//addAllSliders();
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
					
					
				</c:forEach>
			}
			
			function getMaxValue(except){
				count=0;
				for(a=0;a<sliderArray.length;a++){
					count+=sliderArray[a].value;
				
				}
				return ((100-count)+except);
			}

			/* *************** Assign a slider to a criteria and add it to the global slider array *************** */
			function addSlider(critId, index){
			  	newSlider = new Control.Slider('handle' + critId,'track' + critId,{
						onSlide:function(v){
							critWeight=0;
							//alert(sliderArray[index].value);
							
							//if((((100-remainingWeight)+(currentSliderValue))-(sliderArray[index].value))>=0){
							
							if(sliderArray[index].value<=getMaxValue(sliderArray[index].value)){
								critWeight=sliderArray[index].value;
								//alert("set value: "+critWeight);
								sliderArray[index].setValue(critWeight);
							}else{
								critWeight=getMaxValue(sliderArray[index].value);
								
								sliderArray[index].setValue(critWeight);
							}
							//$('input' + critId).value= critWeight;
							manualSliderChange(index,critWeight);
							
							updateRemainingWeight();
							
							
							},
						onChange:function(v){
						//alert('onchange');
							critWeight=0;
							
							//if(((((100-remainingWeight) + (currentSliderValue)) - (sliderArray[index].value))>=0)&&((100-remainingWeight)>=0)){
							if(sliderArray[index].value<=getMaxValue(sliderArray[index].value)){
								critWeight=sliderArray[index].value;
								$('input' + critId).value=critWeight;
								//setWeight(critId, critWeight);
							}else{
							//critWeight=(100-remainingWeight);//currentSliderValue
							critWeight=getMaxValue(sliderArray[index].value);
								sliderArray[index].setValue(critWeight);
							$('input' + critId).value=critWeight;
							
							}
							$('input'+critId).value=sliderArray[index].value;
							setWeight(critId, sliderArray[index].value);
							//manualSliderChange(index,critWeight);
							
							updateRemainingWeight();
							},
						range:$R(0,100),
						minimum: 0,
						maximum: 100,
						values:[0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100],
						slidervalue: $('input' + critId).value //grab value if user has already weighed this criteria
					});
				sliderArray.push(newSlider);
			}
			
			/* *************** Set the value of the slider if user manually sets it in the textbox *************** */
			function manualSliderChange(index, v){
				
				if((((100-remainingWeight) + (sliderArray[index].value) - (v))>=0)&&((100-remainingWeight)>=0)){
					//sliderArray[index].setValue(v / 100);
					sliderArray[index].setValue(v);
				}
				/*a=document.getElementsByTagName('input');
				for(b=0;b<a.length;b++){
					if(a[b].tabIndex==(index+1)){
						a[b].value=sliderArray[index].value;
						break;
					}
				}*/
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

				//$('remainingWeight').innerHTML = ((1-remainingWeight) * 100).toFixed();
				$('remainingWeight').innerHTML = ((100-remainingWeight));
			}
			

			
			/* *************** Set the weight of givin criterion *************** */
			function setWeight(critId, weight){
				//alert("cctId: " + cctId + " critId: " + critId + " weight: " + weight); 
				CriteriaAgent.setWeight({cctId:cctId,critId:critId,weight:weight}, {
					callback:function(data){
						if (data.successful){
							//enable saving indicator here
							//alert('value saved')
						}else{
							alert(data.reason);
						}
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
		</style>
		
	</head>
	<body>
		<!-- Begin conditional styles for the benefit of IE -->
		<!--[if IE]>
		                          <style type="text/css">
		                          #sortingMenu {right:0px;}
								  #criteriaRuler {width:745px;}
		                          </style>
		          <![endif]-->
		<!-- End conditional styles -->
		
		<!-- Begin header -->
		<div id="header"> [Load from separate file] </div>
		<!-- End header -->
		<!-- Begin header menu - The wide ribbon underneath the logo -->
		<div id="headerMenu">
			<div id="headerContainer">
				<div id="headerTitle" class="floatLeft">
					<h3 class="headerColor">Step 2: Evaluate Planning Factors</h3>
				</div>
				<div class="headerButton floatLeft"> <a href="step2a.html">2a:
						Review and discuss factors</a> </div>
				<div class="headerButtonCurrent floatLeft currentBox"> <a href="step2b.html">2b:
						Weigh factors</a> </div>
				<div id="headerNext" class="floatRight box5"> <a href="/sdcWaiting.jsp">Next
						Step</a> </div>
			</div>
		</div>
		<!-- End header menu -->
		<!-- Begin loading indicator -->
		<div style="display: none;" id="loading-indicator"> Loading... 
			<img src="/images/indicator_arrows.gif"> 
		</div>
		<!-- End loading indicator -->
		<!-- Begin container - Main page content begins here -->
		<div id="container">
			<!-- begin "overview and instructions" area -->
			<div id="overview" class="box2">
				<h3>Overview and Instructions</h3>
				<p>Criteria are used to help Evaluate which proposed transportation
					projects are best suited to address problems with our transportation
					system. Below, these criteria have been associated with the
					concern themes discussed in the previous step. Please review
					these criteria and the associated themes. Do these criteria
					adequately reflect your concerns and the summaries? What criteria
					might be useful in evaluating proposed transportation projects?</p>
				<p><a href="readmore.jsp">Read more about how this step fits
						into the bigger picture</a>.</p>
			</div>
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
					<input type="button" value="Save and Continue" />
				</div>
				<div class="clearBoth">&nbsp;</div>
			</div><!-- end object -->
		</div><!--end container -->
				
		<!-- Run javascript function after most of the page is loaded, work around for onLoad functions quirks with tabs.js -->
		<script type="text/javascript" charset="utf-8">
			getWeights();
		</script>
		
		<!-- start the bottom header menu -->
		<!-- Begin header menu - The wide ribbon underneath the logo -->
		<div id="headerMenu">
			<div id="headerContainer">
				<div id="headerTitle" class="floatLeft">
					<h3 class="headerColor">Step 2: Evaluate Planning Factors</h3>
				</div>
				<div class="headerButton floatLeft"> <a href="step2a.html">2a:
						Review and discuss factors</a> </div>
				<div class="headerButtonCurrent floatLeft currentBox"> <a href="step2b.html">2b:
						Weigh factors</a> </div>
				<div id="headerNext" class="floatRight box5"> <a href="/sdcWaiting.jsp">Next
						Step</a> </div>
			</div>
		</div>

		<!-- End header menu -->
		<!-- end the bottom header menu -->
		<!-- Begin footer -->
		<div id="footer"> </div>
		<!-- End footer -->
		<script type="text/javascript">
		
		</script>
	</body>
</html>