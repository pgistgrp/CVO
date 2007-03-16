<html>
	<head>
		<title></title>
				
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
			var cctId = '1180';//"${cct.id}";			
			var sliderArray = new Array();
			var remainingWeight = 100;
			var range=new Array();
			for(a=0;a<101;a++){
			range.push(a);
			}
			
			
			//END Global Vars
			
			/* *************** Pull all criteria and their associated weights and objectives (criteriaAssoc_weights.jsp) *************** */
			function getWeights(){
			//$('loading-indicator').style.display="inline";
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
				CriteriaAgent.getAllCriterion({cctId:cctId},{
				  callback:function(data){
				    if(data.successful){
				    dc=data.criteria;
				   
				    	for(var a=0;a<dc.length;a++){
							addSlider(dc[a].id,a);
							sliderArray[a].setValue((($('input'+dc[a].id).value)));
				    	
				    	}
				    }else{
						alert(data.reason);
					}
					
				  },
				  async:false,
				  errorHandler:function(errorString, exception){
				        alert("getWeights error:"+errorString+" "+exception);
				  }
				  });
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
								
							}else{
							critWeight=getMaxValue(sliderArray[index].value);
								sliderArray[index].setValue(critWeight);
							$('input' + critId).value=critWeight;
							
							}
							$('input'+critId).value=sliderArray[index].value;
							
							
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
						slidervalue: $('input' + critId).value //grab value if user has already weighed this criteria
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
		<div>
			<div id="headerMenu"><div class="floatLeft" id="headerTitle"><h3 class="headerColor">Help Me</h3></div></div>
			<div id="container">
			Answer the following questions so that we can suggest a package that matches your general preferences. You will be able to adjust your suggested package before moving on.
			<br/>
			<br/>
			<div>
			
				<h4>1.) What are your preferred planning factor weights? We will select projects with scores that best match your preferences.</h4>
			</div>
			<br/>
			
			<div id="criteria">
			<!--Planning factor columns from criteriaWeight.jsp-->
			</div>
			<br/>
			
			
			<h4>2.) How much are you willing to pay per year?</h4>
			<br/>
			What is the total annual cost <em>you</em> are willing to pay to find your preferred transportation package? <input style="position:absolute; right:0px;" name="yourCost" type="text"/>
			<br/>
			<br/>
			What is the total annual cost that the <em>average resident</em> should have to pay to fund your preferred transportation package? <input style="position:absolute; right:0px;" name="avgCost" type="text"/>
			<br/>
			<br/>
			<h4>3.) Do you want us to include projects and funding sources already selected in your package, or suggest a brand new package?</h4>
			
			<div style="position:absolute; right:50%;">
				<br/>
				<input type="radio" name="IncludeOrCreate"/><strong>Include my current selection</strong>
				<br/>
				<input type="radio" name="IncludeOrCreate"/><strong>Create a brand-new package</strong>
				<br/>
				<br/>
				<input style="background-color:#E7F2F7;" type="button" value="I'm done: Show me my suggested package"/>
			</div>
			
			
		</div>
		</div>
		
		<!-- Run javascript function after most of the page is loaded, work around for onLoad functions quirks with tabs.js -->
		<script type="text/javascript" charset="utf-8">
			getWeights();
		</script>
	</body>
</html>