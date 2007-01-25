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
		<script src="/scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
		<!-- End Site Wide JavaScript -->
		<!-- DWR JavaScript Libraries -->
		<script type='text/javascript' src='/dwr/engine.js'></script>
		<script type='text/javascript' src='/dwr/util.js'></script>
		<!-- End DWR JavaScript Libraries -->
		<!--Criteria Specific  Libraries-->
		<script type='text/javascript' src='/dwr/interface/CriteriaAgent.js'></script>
		
		
		<script type="text/javascript" src="/scripts/FactorSlider/lib/LibCrossBrowser.js"></script>
		<script type="text/javascript" src="/scripts/FactorSlider/lib/EventHandler.js"></script>
		<script type="text/javascript" src="/scripts/FactorSlider/core/form/Bs_FormUtil.lib.js"></script>
		<script type="text/javascript" src="/scripts/FactorSlider/Bs_Slider.class.js"></script>

		<script type="text/javascript" charset="utf-8">
			var cctId = "${cct.id}";
			




var sliderArray = new Array();
var avgSliderArray = new Array();

var sliderCount=0;
var availableVals=100;

function init(){

<c:forEach var="criterion" items="${cct.criteria}" varStatus="loop">
addSlider(sliderArray,'${criterion.id}','${criterion.name}Slider');
</c:forEach>


}


function removeSlider(sliderid){
  document.getElementById(sliderid).style.display='none';
  for(rs=0;rs<sliderArray.length;rs++){
    if(sliderArray[rs].objectName==sliderid){
      availableVals+=sliderArray[rs]._valueInternal;
       sliderArray.splice(rs,1);
       break;
    }
  
  }

}



function refreshAvailablePoints(){

   document.f.t.value=availableVals;

}





function addSlider(arry,name,location){
a = new Bs_Slider();
sliderCount++;
a.attachOnChange(calcMaxSliderValOnChange);
a.width=400;
a.height=26;
a.minVal=0;
a.maxVal=100;
a.valueInterval=5;
a.arrowAmount=5;
a.valueDefault=0;


//Change this to point to the proper folder containing the images
	a.imgDir='/scripts/FactorSlider/img/';



	a.setSliderIcon('greyslider.gif', 11, 24);
	
	a.setBackgroundImage('greybar.gif', 'no-repeat');
	
	a.setArrowIconLeft('arrowLeft.gif', 16, 16);
	a.setArrowIconRight('arrowRight.gif', 16, 16);

	
	a.useInputField = 2;
	a.styleValueFieldClass = 'sliderInput';
	a.colorbar = new Object();
	a.colorbar['color']           = 'ADADAD';
	a.colorbar['height']          = 7;
	a.colorbar['widthDifference'] = -12;
	a.colorbar['offsetLeft']      = 1;
	a.colorbar['offsetTop']       = 7;
	a.width=610;
	a.height=40;

	a.objectName=name;	

	a.draw(location);
	arry.push(a);

}

function setSliderValue(slarray,slidername,val){

	for(a=0; a<slarray.length;a++){
		if(slarray[a].objectName==slidername){
			slarray[a].value=val;
			slarray[a]._valueInternal=val;
			pos=slarray[a]._getPositionByValue(val);
			slarray[a].updateHandle(pos);
			slarray[a].updateValueText(val);
			slarray[a]._updateColorbar(pos);
			slarray[a].updateValueField(val);
			break;
		}
	}
}



function calcMaxSliderVal(arr){
   count=0;


   for(a=1;a<(arr.length);a++){
    count+=Math.floor(100/arr.length);
    arr[a].maxVal=Math.floor((100/arr.length));
   }
   arr[0].maxVal=(100-count);


}


/*
calculate availableVals for all, if <=100, ok
else calc all except current and assign availableVal

*/
function calcMaxSliderValOnChange(sliderObj, val, newPos){
count=0;
  for(a=0;a<sliderArray.length;a++){


  
   
      count+=sliderArray[a]._valueInternal;
     
    
  }
  availableVals=100-count;
 if(count>100){
   ct=0;
   for(b=0;b<sliderArray.length;b++){
     if(sliderArray[b]!=sliderObj){
       ct+=sliderArray[b]._valueInternal;
     }
   }
   sliderObj._valueInternal=100-ct;
     sliderObj.setValue(100-ct);
   pos=sliderObj._getPositionByValue(100-ct);
   sliderObj.updateHandle(pos);
   sliderObj.updateValueText(100-ct);
   sliderObj._updateColorbar(pos);
   sliderObj.updateValueField(100-ct);
availableVals=100-(ct + (100-ct));
 }
bsSliderChange(sliderObj,val,newPos);
}

function calcMinSliderVal(){

}




/*function drawSliders() {
	mySlider = new Bs_Slider();
	sliderCount++;
	//mySlider.objectName = 'mySlider';
	mySlider.attachOnChange(bsSliderChange);
	mySlider.width         = 121;
	mySlider.height        = 26;
	mySlider.minVal        = 0;
	mySlider.maxVal        = 100;
	mySlider.valueInterval = 1;
	mySlider.arrowAmount   = 1;
	mySlider.valueDefault  = 50;
	
	mySlider.imgDir='scripts/FactorSlider/img/';


	
	
	
		mySlider.setBackgroundImage('greybar.gif', 'no-repeat');

	mySlider.setSliderIcon('greyslider.gif', 11, 24);
	mySlider.setArrowIconLeft('img/arrowLeft.gif', 16, 16);
	mySlider.setArrowIconRight('img/arrowRight.gif', 16, 16);
	
	
	mySlider.useInputField = 2;
	mySlider.styleValueFieldClass = 'sliderInput';
	mySlider.colorbar = new Object();
	mySlider.colorbar['color']           = 'ADADAD';
	mySlider.colorbar['height']          = 7;
	mySlider.colorbar['widthDifference'] = -12;
	mySlider.colorbar['offsetLeft']      = 1;
	mySlider.colorbar['offsetTop']       = 7;
	mySlider.width=610;
	mySlider.height=40;
		sliderArray.push(mySlider);
}*/

/**
* @param object sliderObj
* @param int val (the value)
*/
function bsSliderChange(sliderObj, val, newPos) { 
alert(val);
setWeight(sliderObj.objectName,val);
  //document.f.t.value = val;
}



/*
slarray is the array containing the sliders on the page
*/
function saveWeights(slarray){

	setWeight('1180','2546',slarray[0]._valueInternal);
	setWeight('1180','2548',slarray[1]._valueInternal);
	setWeight('1180','2550',slarray[2]._valueInternal);
	setWeight('1180','2552',slarray[3]._valueInternal);
}

function setAllSliderWeights(ctid){
	CriteriaAgent.getWeights({cctId:ctid},{
	
	  callback:function(data){
	
		
		
		if(data.successful){

			for(a=0;a<(data.weights).length;a++){

				setSliderValue(sliderArray,data.weights[a].id,data.weights[a].weight);
		
			}

		}else{
		alert("getWeights failed");
		alert(data);
		}
	  },
	  errorHandler:function(errorString, exception){
					 alert("getWeights error:"+errorString+" "+exception);
	  }
	  });


}



function getAllCriterion(ctid){
  CriteriaAgent.getAllCriterion({cctId:ctid},{
    callback:function(data){
      if(data.successful){
      alert("successful");

        
      }else{
        
      }
    },
    errorHandler:function(errorString, exception){ 
                   alert("getAllCriterion error:"+errorString+" "+exception);
    }
    
  });
}
			
			
			/* *************** Pull all criteria and their associated weights and objectives (criteriaAssoc_weights.jsp) *************** */
			function getWeights(){
				CriteriaAgent.getWeights({cctId:cctId},{
				  callback:function(data){
				    if(data.successful){
				    	$('criteria').innerHTML = data.html;
				    	init();
				    }else{
						alert(data.reason);
					}
				  },
				  errorHandler:function(errorString, exception){
				        alert("getWeights error:"+errorString+" "+exception);
				  }
				  });
			}
			
			/* *************** Set the weight of givin criterion *************** */
			function setWeight(critId, weight){
				//alert("cctId: " + cctId + " critId: " + critId + " weight: " + weight + " param4: " + param4); 
				CriteriaAgent.setWeight({cctId:cctId,critId:critId,weight:weight}, {
					callback:function(data){
						if (data.successful){
							alert('value saved')
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
	</body>
</html>