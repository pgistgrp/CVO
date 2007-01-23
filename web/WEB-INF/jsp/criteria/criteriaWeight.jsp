<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
	"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" version="-//W3C//DTD XHTML 1.1//EN" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>Step 2b: Evaluate planning factors</title>
<style type="text/css" media="screen">
        @import "/styles/lit.css";
        </style>
<style type="text/css">

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
<script src="/scripts/prototype.js" type="text/javascript"></script>
<script src="/scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<script type="text/javascript">
		function expandList(objective,icon){
			Effect.toggle(objective, 'appear', {duration: .5, afterFinish:
				//window.setTimeout(toggleIcon,100);
				function(){
					if ($(objective).style.display != ""){
						$(icon).src = "images/plus.gif";
						}else{
							$(icon).src = "images/minus.gif";
						}
					}
			});
		}
	</script>
	
<script type="text/javascript" src="/scripts/FactorSlider/lib/LibCrossBrowser.js"></script>
<script type="text/javascript" src="/scripts/FactorSlider/lib/EventHandler.js"></script>
<script type="text/javascript" src="/scripts/FactorSlider/core/form/Bs_FormUtil.lib.js"></script>
<script type="text/javascript" src="/scripts/FactorSlider/Bs_Slider.class.js"></script>


<!-- Site Wide JavaScript -->
<script src="/scripts/tags.js" type="text/javascript"></script>
<script src="/scripts/prototype.js" type="text/javascript"></script>
<script src="/scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<script src="/scripts/globalSnippits.js" type="text/javascript"></script>
<!-- End Site Wide JavaScript -->

<!-- DWR JavaScript Libraries -->
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<!-- End DWR JavaScript Libraries -->

<!--SDX Specific  Libraries-->
<script type='text/javascript' src='/dwr/interface/CriteriaAgent.js'></script>
<!--End SDX Specific  Libraries-->



<script type="text/javascript">

var sliderArray = new Array();
var avgSliderArray = new Array();

var sliderCount=0;
var availableVals=100;

function init(){

<c:forEach var="criterion" items="${criteria}" varStatus="loop">
addSlider(sliderArray,'${criterion.name}','${criterion.name}Div');

</c:forEach>
/*
addSlider(sliderArray,'2546','123');
addSlider(sliderArray,'2548', '456');
addSlider(sliderArray,'2550', '789');
addSlider(sliderArray,'2552', '012');*/
//addSlider(avgSliderArray,'345', 'staticDiv');

//avgSliderArray[0].setSliderIcon('greyslider.gif',0,0);
//avgSliderArray[0].setDisabled(true);
//avgSliderArray[0].draw('staticDiv');
//setSliderValue(avgSliderArray,'345',10);

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
  //refreshAvailablePoints();
}



function refreshAvailablePoints(){

   document.f.t.value=availableVals;

}




//add objectName parameter??
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
a.imgDir='scripts/FactorSlider/img/';

//$('testImage').style.backgroundImage=a.imgDir+'/greyslider.gif';
//Change the following to the proper images
//a.setBackgroundImage('random/backgrounds/bg3.h.gif', 'no-repeat'); 
//a.setBackgroundImage('green_bar2.gif', 'no-repeat');
	a.setBackgroundImage('greybar.gif', 'no-repeat');
	//a.setSliderIcon('green_slider.gif', 9, 21);
	a.setSliderIcon('green_slider2.gif', 19, 19);
	//a.setSliderIcon('greyslider.gif', 11, 24);
	a.imgDir='/scripts/FactorSlider/img/img/';
	a.setArrowIconLeft('arrowLeft.gif', 16, 16);
	a.setArrowIconRight('arrowRight.gif', 16, 16);
	//alert(a.imgDir);
	
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
	
	
	if(name==""||location==name){
	//alert("empty location");
	}
	
	
	a.objectName=name;	
	
	//removelink=document.createElement('a');
	//removelink.setAttribute('onclick',"javascript: removeSlider('"+name+"'); return false;");
	//removelink.innerHTML="Remove Slider";
	//removelink.setAttribute('href','');
	//document.getElementById(location).appendChild(removelink);
	//document.getElementById("sliderCollection").appendChild(removelink);
	
	
	//d=document.createElement('div');
	//d.setAttribute('id','sliderDiv'+sliderCount);

	//document.getElementById("sliderCollection").appendChild(d);
	
	
	//br=document.createElement('br');
	//document.getElementById("sliderCollection").appendChild(br);
	//br=document.createElement('br');
	//document.getElementById("sliderCollection").appendChild(br);
	
	a.draw(location);
	arry.push(a);
	//calcMaxSliderVal(sliderArray);
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
			
		}
	}
}



function calcMaxSliderVal(arr){
   count=0;

 //if(arr.length%3==0){
   for(a=1;a<(arr.length);a++){
    count+=Math.floor(100/arr.length);
    arr[a].maxVal=Math.floor((100/arr.length));
   }
   arr[0].maxVal=(100-count);
 //}

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
  // availableVals=100-count;
   
   
  // for(a=0;a<sliderArray.length;a++){
  //  sliderArray[a].maxVal=(sliderArray[a]._valueInternal+availableVals);
  // }
//refreshAvailablePoints();
}

function calcMinSliderVal(){

}




function drawSliders() {
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
	
	mySlider.imgDir='/scripts/FactorSlider/img/';
	
	/*mySlider.setBackgroundImage('bob/background.gif', 'no-repeat');
	mySlider.setSliderIcon('bob/horizontal_knob.gif', 15, 19);
	mySlider.setArrowIconLeft('img/arrowLeft.gif', 16, 16);
	mySlider.setArrowIconRight('img/arrowRight.gif', 16, 16);
	mySlider.useInputField = 2;
	mySlider.styleValueFieldClass = 'sliderInput';
	mySlider.colorbar = new Object();
	mySlider.colorbar['color']           = 'red';
	mySlider.colorbar['height']          = 5;
	mySlider.colorbar['widthDifference'] = -12;
	mySlider.colorbar['offsetLeft']      = 5;
	mySlider.colorbar['offsetTop']       = 9;
	//mySlider.draw('sliderDiv1');*/

	
	
	
		mySlider.setBackgroundImage('greybar.gif', 'no-repeat');
	//mySlider.setSliderIcon('green_slider.gif', 9, 21);
	//mySlider.setSliderIcon('green_slider2.gif', 19, 19);
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
}

/**
* @param object sliderObj
* @param int val (the value)
*/
//function bsSliderChange(sliderObj, val, newPos) { 
//  document.f.t.value = val;
//}



/*
slarray is the array containing the sliders on the page
*/
function saveWeights(slarray){
	/*for(a=0;a<slarray.length; a++){
		setWeight('1180','2508',slarray[a]._valueInternal);

	}*/
	setWeight('1180','2546',slarray[0]._valueInternal);
	setWeight('1180','2548',slarray[1]._valueInternal);
	setWeight('1180','2550',slarray[2]._valueInternal);
	setWeight('1180','2552',slarray[3]._valueInternal);
}

function setWeight(ctid, criteriaId, wt){
 
  
  CriteriaAgent.setWeight({cctId:ctid, critId:criteriaId, weight:wt},{
    callback:function(data){
      if(data.successful){
      alert("weight set");
		alert(wt);
        
      }else{
        
      }
    },
    errorHandler:function(errorString, exception){ 
                   alert("setWeight error:"+errorString+" "+exception);
    }
  });
}



function getWeights(ctid){

CriteriaAgent.getWeights({cctId:ctid},{

  callback:function(data){

	
	
    if(data.successful){
   // alert(data.weights[0].weight);
    //alert("weights length in getWeights"+(data.weights).length);
		for(a=0;a<(data.weights).length;a++){
			alert(data.weights[a].weight);
			setSliderValue(sliderArray,data.weights[a].id,data.weights[a].weight);
	
		}
		//alert("getWeights: "+data.weights[0].weight);
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

function setAllSliderWeights(ctid){
	CriteriaAgent.getWeights({cctId:ctid},{
	
	  callback:function(data){
	
		
		
		if(data.successful){
	   // alert(data.weights[0].weight);
		//alert("weights length in getWeights"+(data.weights).length);
			for(a=0;a<(data.weights).length;a++){
				//alert(data.weights[a].weight);
				setSliderValue(sliderArray,data.weights[a].id,data.weights[a].weight);
		
			}
			//alert("getWeights: "+data.weights[0].weight);
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
        //put into page
        
      }else{
        
      }
    },
    errorHandler:function(errorString, exception){ 
                   alert("getAllCriterion error:"+errorString+" "+exception);
    }
    
  });
}










</script>



</head>
<body bgColor="white" text="#3366aa" link="#0000ee" alink="#ff0000" vlink="#551a8b" onLoad="init();">




<!--<form name="f">
Points to distribute: <input type="text" name="t" size="6" style="background:yellow;">
</form>-->

	

Put weigh criteria here
available variables: "cct" and "criteria"
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
			<!-- Load separate file content starting here -->
			<div id="criteria">
				<div id="criteriaHeader" style="height:30px">
					<div class="floatLeft">
						<h3 class="headerColor">All planning factors and related
							concern themes</h3>
					</div>
				</div>
			</div>
			<!-- begin criteria (summary) -->
			<div id="criteria" class="box3 floatLeft">
				<!-- START All Criteria List -->
				<div id="allCriteriaList">
					<div class="criteriaListHeader">
						<div class="weighCriteriaCol1 floatLeft">
							<h4 class="headerColor">Planning factor</h4>
						</div>
						<div class="weighCriteriaCol2 floatLeft">
							<h4 class="headerColor">Description</h4>
						</div>
						<div class="weighCriteriaCol3 floatLeft">
							<h4 class="headerColor">Your weight</h4>
						</div>
						<div class="clearBoth"></div>
					</div>
					<c:forEach var="criterion" items="{criteria}" varStatus="loop">
					<div class="criteriaListRow row">
						<div class="weighCriteriaCol1 floatLeft"><a href="#">
							<div class="floatLeft"><a href="javascript:expandList('objectives1','icon1');">
								<img src="images/plus.gif" id="icon1"></a></a></div>
							<div class="floatLeft"><a href="#">${criterion.name}</a></div>
						</div>
						</c:forEach>
						<div class="weighCriteriaCol2 floatLeft smallText">Support
							the economic vitality of the metropolitan area especially
							by enabling global competitiveness, productivity, and
							efficiency</div>
						<div id="123"class="weighCriteriaCol3 floatLeft smallText"></div>
						<div class="clearBoth"></div>
						<div class="objectives" id="objectives1" style="display:none;"> Lorem
							Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
							Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem
							Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
							Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem
							Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
							Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem
							Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
							Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem
							Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
							Lorem Ipsum Lorem Ipsum Lorem Ipsum </div>
					</div>
					<div class="criteriaListRow row even">
						<div class="weighCriteriaCol1 floatLeft"><a href="#">
							<div class="floatLeft"><a href="javascript:expandList('objectives2','icon2');">
								<img src="images/plus.gif" id="icon2"></a></div>
							<div class="floatLeft"><a href="#">Security</a></div>
						</div>
						<div class="weighCriteriaCol2 floatLeft smallText">Increase
							security of the transportation system for motorized and
							nonmotorized uses.</div>
						<div id='456' class="weighCriteriaCol3 floatLeft smallText"></div>
						<div class="clearBoth"></div>
						<div class="objectives" id="objectives2" style="display:none;"> Lorem
							Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
							Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem
							Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
							Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem
							Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
							Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem
							Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
							Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem
							Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
							Lorem Ipsum Lorem Ipsum Lorem Ipsum </div>
					</div>
					<div class="criteriaListRow row">
						<div class="weighCriteriaCol1 floatLeft">
							<div class="floatLeft"><a href="javascript:expandList('objectives3','icon3');">
								<img src="images/plus.gif" id="icon3"></a></div>
							<div class="floatLeft"><a href="#">Accessibility and Mobility</a></div>
						</div>
						<div class="weighCriteriaCol2 floatLeft smallText">Increase
							the accessibility and mobility options to people and freight</div>
						<div id='789' class="weighCriteriaCol3 floatLeft smallText"> </div>
						<div class="clearBoth"></div>
						<div class="objectives" id="objectives3" style="display:none;"> Lorem
							Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
							Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem
							Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
							Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem
							Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
							Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem
							Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
							Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem
							Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
							Lorem Ipsum Lorem Ipsum Lorem Ipsum </div>
					</div>
					<div class="criteriaListRow row even">
						<div class="weighCriteriaCol1 floatLeft">
							<div class="floatLeft"><a href="javascript:expandList('objectives4','icon4');">
								<img src="images/plus.gif" id="icon4"></a></div>
							<div class="floatLeft"><a href="#">Lorem Ipsum Dolor Sit
									Amet Adapiscing Elit</a></div>
						</div>
						<div class="weighCriteriaCol2 floatLeft smallText"> Protect
							and enhance the environment, promote energy conservation,
							and improve quality of life. </div>
						<div id='012' class="weighCriteriaCol3 floatLeft smallText"> </div>
						<div class="clearBoth"></div>
						<div class="objectives" id="objectives4" style="display:none;"> Lorem
							Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
							Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem
							Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
							Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem
							Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
							Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem
							Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
							Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem
							Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum
							Lorem Ipsum Lorem Ipsum Lorem Ipsum </div>
					</div>
				</div>
				<!-- END All Criteria List -->
			</div>
			<!-- end criteria (summary) -->
			<div class="floatRight padding5">
				<input type="button" value="Save" onclick="javascript:alert(sliderArray[0]._valueInternal);saveWeights(sliderArray);"/>
				<input type="button" value="Save and Continue" />
			</div>
		</div>
		<!-- Separate file content stops here -->
	</div>
	<!--end object content -->
	<!-- end object -->
	<div class="clearBoth">&nbsp;</div>
	<!-- start feedback form -->
	<!-- end feedback form -->
</div>
<!-- end container -->
<!-- Start Footer -->
<!-- End Footer -->
<!-- Run javascript function after most of the page is loaded, work around for onLoad functions quirks with tabs.js -->
<script type="text/javascript">
/*
                        io.getPosts('', 1, false, io.currentSort);
                        //infoObject.assignTargetHeaders();
                        io.getTargets();
                        */
                        //window.setTimeout("getWeights('1180');",10000);
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
		<div class="headerButtonCurrent floatLeft currentBox"> <a href="step2b.html">1b:
				Weigh factors</a> </div>
		<div id="headerNext" class="floatRight box5"> <a href="/sdcWaiting.jsp">Next
				Step</a> </div>
	</div>
</div>
<div id='testImage'></div>
<div id='staticDiv'></div>
<!-- End header menu -->
<!-- end the bottom header menu -->
<!-- Begin footer -->
<div id="footer"> </div>
</body>
</html>
