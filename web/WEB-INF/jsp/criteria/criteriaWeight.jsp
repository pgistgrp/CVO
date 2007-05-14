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
			var suiteId = '<%=request.getParameter("suiteId")%>';	
			var remainingWeight = 100;
			var range=new Array();
			var sliders = $H({});
			
			//change this with prototype
			for(a=0;a<101;a++){
				range.push(a);
			}
			
			//END Global Vars
			
			/* *************** Render all criteria with placeholders for sliderbars *************** */
			function getCriteriaSuiteById(){
				CriteriaAgent.getCriteriaSuiteById({critSuiteId:suiteId},{
				  callback:function(data){
				    if(data.successful){
				    	$('criteria').innerHTML = data.html;
						addAllSliders();
				    }else{
						alert(data.reason);
					}	
				  },
				  errorHandler:function(errorString, exception){
				        alert("getCriteriaSuiteById error:"+errorString+" "+exception);
				  }
				  });
			} 
			
			/* *************** loop through all the criteria to assign sliders to each *************** */
			function addAllSliders(){
				CriteriaAgent.getAllCriterion({critSuiteId:suiteId},{
				  callback:function(data){
				    if(data.successful){
				    	var criteria = data.criteria;
						for(i=0;i<data.criteria.length;i++){
							addSlider(data.criteria[i].id);
						}
						updateRemainingWeight();
				    }else{
						alert(data.reason);
					}
					
				  },
				  errorHandler:function(errorString, exception){
				        alert("getAllCriterion error:"+errorString+" "+exception);
				  }
				  });
			} 
			
			/* *************** Create a slider object to a criteria and add it to the global slider hash *************** */
			function addSlider(critId){
				CriteriaAgent.getWeight({critSuiteId:suiteId,critId:critId}, {
					callback:function(data){
						sliderWeight  = (data.weight) ? data.weight : 0
						newSlider = new Control.Slider('handle' + critId,'track' + critId,{
								onSlide:function(v){ checkMaxAndUpdateValue(critId, false); },
								onChange:function(v){ checkMaxAndUpdateValue(critId, true); },
								range:$R(0,100),
								minimum: 0,
								maximum: 100,
								values: range,
								sliderValue: sliderWeight
							});

							sliders[critId] = newSlider;
							sliders[critId].setValue(sliderWeight);
					},
					errorHandler:function(errorString, exception){ 
					alert("CriteriaAgent.getWeight( error:" + errorString + exception);
					}
				});
			}
			
			function checkMaxAndUpdateValue(critId,sending) {
				if(sliders[critId].value > getMaxValue(sliders[critId].value)){
					critWeight=getMaxValue(sliders[critId].value);
					sliders[critId].setValue(critWeight);
				}
				
				if(sending){ setWeight(critId, sliders[critId].value); }

				updateRemainingWeight();
				checkRemaining(remainingWeight);
				$('input'+critId).value = sliders[critId].value;
			}
			
			function checkRemaining(remainingWeight){
				if((100-remainingWeight)==0){
					$('remainingWeightP').style.backgroundColor="#FFDF94";
					$('remainingWeightP2').style.backgroundColor="#FFDF94";
				}else{
					$('remainingWeightP').style.backgroundColor="#FFFFFF";
					$('remainingWeightP2').style.backgroundColor="#FFFFFF";
				}
			}
			
			function initWeights(){
				sliders.each(function(slider){
					getWeight(slider.key)
				})
			}
			
			function getWeight(critId) {

			}
			
			function getMaxValue(except){
				count=0;
				sliders.each(function(slider) {
					count += slider.value.value
				});
				
				maxValue =  ((100-count)+except);
				return maxValue;
				//alert(maxValue)
			}
			
			/* *************** Set the value of the slider if user manually sets it in the textbox *************** */
			function manualSliderChange(critId, v){
				//alert("CritID: " + critId + " value: "+ v)				
				if((((100-remainingWeight) + (sliders[critId].value) - (v))>=0)&&((100-remainingWeight)>=0)){
					sliders[critId].setValue(v);
				}else{
					sliders[critId].setValue(remainingWeight)
				}
				//updateRemainingWeight();
			}
			
			function updateRemainingWeight(){
				remainingWeight = 0; //reset remainingWeight
				a = document.getElementsByTagName('input');
				sliders.each(function(slider) {			
					remainingWeight += slider.value.value;
					for(b=0;b<=i;b++){
						if(a[b].tabIndex==(i+1)){
							a[b].value=slider.value.value;
							break;
						}
					}
				});
				$('remainingWeight').innerHTML = ((100-remainingWeight));
				$('remainingWeight2').innerHTML = ((100-remainingWeight));
			}
			

			
			/* *************** Set the weight of givin criterion *************** */
			function setWeight(critId, weight){
			$('saving-indicator').style.display="inline";
				
				CriteriaAgent.setCriteriaWeight({critSuiteId:suiteId,critId:critId,weight:weight}, {
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
background:#ADCFDE;
padding:5px 0px;
}


.criteriaListHeader .weighCriteriaCol1 {width:205px;padding-left:5px;}

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
vertical-align:top;
border:0px;
}

.weighCriteriaCol2
{
width:330px;
}

.weighCriteriaCol3
{
margin-left:.5em;
width:375px;
}	

h4
{
font-size:1em;
margin:0px;
padding:0px;
}

.objectives
{
padding:0px .5em;
margin-left:10px;
border-left:1px solid #ccc;
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

p#remainingWeightP, p#remainingWeightP2{
width:180px;
text-align:center;
float:right;
padding:5px 5px;
font-size:1.2em;
margin:10px 0px;
}

b#remainingWeight, b#remainingWeight2 {font-size:1.4em;}
.weighCriteriaCol3 input {width:2em;text-align:center;}
.objectives ul {margin-top:5px;}

#critRowWrapper{border:1px solid #ADCFDE;padding:5px;}
</style>
	<event:pageunload />
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
		
		<!-- Begin header -->
		<div id="header">		<jsp:include page="/header.jsp" /></div>
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
		<div style="display:none;" id="saving-indicator"> Saving...
			<img src="/images/indicator_arrows.gif">
		</div>
		<!-- End loading indicator -->
		<!-- Begin container - Main page content begins here -->
		<div id="container">
			<!-- begin "overview and instructions" area -->
			<div id="overview" class="box2">
				<h3>Overview and Instructions</h3>
<p>Which planning factors do you feel are most important when determining which transportation improvement projects to fund and build? On this page you can assign weights to the nine different planning factors. This will help you to identify projects in Step 3 which most closely reflect your own priorities for transportation system improvement.</p>

<p>To assign a weight to a planning factor, drag the slider bar next to that factor. You have <strong>100 points</strong> to distribute between the nine factors.</p>

<strong>Hint:</strong> Click the "plus" (<img src="images/plus.gif" valign="bottom"> ) icon to see what objectives go into grading each project.<br />
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
					
					
				<div class="clearBoth"></div>
				<div class="floatRight padding5">
					<input type="button" value="Continue" class="padding5" onclick="location.href='waiting.jsp'" />
				</div>
				<div class="clearBoth">&nbsp;</div>
			</div><!-- end object -->
		</div><!--end container -->
				
		<!-- Run javascript function after most of the page is loaded, work around for onLoad functions quirks with tabs.js -->
		<script type="text/javascript" charset="utf-8">
			//getWeights();
			getCriteriaSuiteById();

			setTimeout(function() {initWeights();}, 350);
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
		<div id="footer"> 		<jsp:include page="/footer.jsp" /></div>
		<!-- End footer -->

	</body>
</html>