//START Global vars	
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
	CriteriaAgent.getCriteriaSuiteById({critSuiteId:critSuiteId},{
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
	CriteriaAgent.getAllCriterion({critSuiteId:critSuiteId},{
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
	CriteriaAgent.getWeight({critSuiteId:critSuiteId,critId:critId}, {
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
	
	CriteriaAgent.setCriteriaWeight({critSuiteId:critSuiteId,critId:critId,weight:weight}, {
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

