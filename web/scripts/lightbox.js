/*
Created By: Chris Campbell
Website: http://particletree.com
Date: 2/1/2006

Adapted By: Simon de Haan
Website: http://blog.eight.nl
Date: 21/2/2006

Inspired by the lightbox implementation found at http://www.huddletogether.com/projects/lightbox/
And the lightbox gone wild by ParticleTree at http://particletree.com/features/lightbox-gone-wild/

*/

/*-------------------------------GLOBAL VARIABLES------------------------------------*/

var detect = navigator.userAgent.toLowerCase();
var OS,browser,version,total,thestring;
var initialized=false;



/*-----------------------------------------------------------------------------------------------*/

//Browser detect script origionally created by Peter Paul Koch at http://www.quirksmode.org/

function getBrowserInfo() {
	if (checkIt('konqueror')) {
		browser = "Konqueror";
		OS = "Linux";
	}
	else if (checkIt('safari')) browser 	= "Safari"
	else if (checkIt('omniweb')) browser 	= "OmniWeb"
	else if (checkIt('opera')) browser 		= "Opera"
	else if (checkIt('webtv')) browser 		= "WebTV";
	else if (checkIt('icab')) browser 		= "iCab"
	else if (checkIt('msie')) browser 		= "Internet Explorer"
	else if (!checkIt('compatible')) {
		browser = "Netscape Navigator"
		version = detect.charAt(8);
	}
	else browser = "An unknown browser";

	if (!version) version = detect.charAt(place + thestring.length);

	if (!OS) {
		if (checkIt('linux')) OS 		= "Linux";
		else if (checkIt('x11')) OS 	= "Unix";
		else if (checkIt('mac')) OS 	= "Mac"
		else if (checkIt('win')) OS 	= "Windows"
		else OS 								= "an unknown operating system";
	}
}

function checkIt(string) {
	place = detect.indexOf(string) + 1;
	thestring = string;
	return place;
}

/*-----------------------------------------------------------------------------------------------*/

//Event.observe(window, 'load', initialize, false);
Event.observe(window, 'load', getBrowserInfo, false);
Event.observe(window, 'unload', Event.unloadCache, false);

var lightbox = Class.create();

lightbox.prototype = {

	yPos : 0,
	xPos : 0,

	initialize: function(ctrl) {
		this.content = ctrl.rel;
		Event.observe(ctrl, 'click', this.activate.bindAsEventListener(this), false);
		ctrl.onclick = function(){return false;};//getTermArrays(ctrl.id); 
	},
	
	// Turn everything on - mainly the IE fixes
	activate: function(){
		//alert("activate called");
		if (browser == 'Internet Explorer'){
			this.getScroll();
			this.prepareIE('100%', 'hidden');
			this.setScroll(0,.15*window.innerHeight);
			this.hideSelects('hidden');
		}else{
		this.getScroll();
		this.setScroll(0,.15*window.innerHeight);
		bod = document.getElementsByTagName('body')[0];
		bod.style.height = "100%";
		bod.style.overflow = "hidden";
  
		htm = document.getElementsByTagName('html')[0];
		htm.style.height = "100%";
		htm.style.overflow = "hidden";
		}
		this.displayLightbox("inline");
		
	},
	
	// Ie requires height to 100% and overflow hidden or else you can scroll down past the lightbox
	prepareIE: function(height, overflow){
		bod = document.getElementsByTagName('body')[0];
		bod.style.height = height;
		bod.style.overflow = overflow;
  
		htm = document.getElementsByTagName('html')[0];
		htm.style.height = height;
		htm.style.overflow = overflow; 
	},
	
	// In IE, select elements hover on top of the lightbox
	hideSelects: function(visibility){
		selects = document.getElementsByTagName('select');
		for(i = 0; i < selects.length; i++) {
			selects[i].style.visibility = visibility;
		}
	},
	
	// Taken from lightbox implementation found at http://www.huddletogether.com/projects/lightbox/
	getScroll: function(){
		if (self.pageYOffset) {
			this.yPos = self.pageYOffset;
		} else if (document.documentElement && document.documentElement.scrollTop){
			this.yPos = document.documentElement.scrollTop; 
		} else if (document.body) {
			this.yPos = document.body.scrollTop;
		}
	},
	
	setScroll: function(x, y){
		window.scrollTo(x, y); 
		
	},
	
	displayLightbox: function(display){
		$('overlay').style.display = display;
		$(this.content).style.display = display;
		if(display != 'none') this.actions();
		document.getElementById("leightbar").style.display='inline';
		document.getElementById("leightcontainer").style.display='inline';
		
	},
	
	// Search through new links within the lightbox, and attach click event
	actions: function(){
		lbActions = document.getElementsByClassName('lbAction');

		for(i = 0; i < lbActions.length; i++) {
			Event.observe(lbActions[i], 'click', this[lbActions[i].rel].bindAsEventListener(this), false);
			lbActions[i].onclick = function(){return false;};
		}

		
		//Attaches click event to the save and close link in the lightbox
		lbsaveclose= document.getElementsByClassName("lbsaveclose");
		
		for(ij=0; ij<lbsaveclose.length; ij++){
			var lbs=lbsaveclose[ij].id;
			//alert("id: "+lbs);
			Event.observe(lbsaveclose[ij], 'click', this[lbsaveclose[ij].rel].bindAsEventListener(this), false);
			lbsaveclose[ij].onclick=function(){saveEditedAttributes(lbs); getTerms(); return false;};
		}


	},
	
	// Example of creating your own functionality once lightbox is initiated
	deactivate: function(){
		if (browser == "Internet Explorer"){
			//this.setScroll(0,this.yPos);
			this.prepareIE("auto", "auto");
			this.hideSelects("visible");
		}
		bod = document.getElementsByTagName('body')[0];
		bod.style.height = "auto";
		bod.style.overflow = "auto";
  
		htm = document.getElementsByTagName('html')[0];
		htm.style.height = "auto";
		htm.style.overflow = "auto";
		this.displayLightbox("none");
		document.getElementById("leightbar").style.display='none';
		document.getElementById("leightcontainer").style.display='none';
		
		this.setScroll(0,this.yPos);
	}
}

/*-----------------------------------------------------------------------------------------------*/

// Onload, make all links that need to trigger a lightbox active
function initialize(){
	addLightboxMarkup();
	lbox = document.getElementsByClassName('lbOn');
	for(i = 0; i < lbox.length; i++) {
		valid = new lightbox(lbox[i]);
	}
}

// Add in markup necessary to make this work. Basically two divs:
// Overlay holds the shadow
// Lightbox is the centered square that the content is put into.
function addLightboxMarkup() {

	bod 				= document.getElementsByTagName('body')[0];

	overlay 			= document.createElement('div');
	overlay.id			= 'overlay';

	bod.appendChild(overlay);

	
}
//resizes lightbox called by onload and onresize of body
function sizeMe(){
var conheight="";
if (browser=="Internet Explorer"){
conheight=(.7*document.body.clientHeight)-30;
}else{
conheight=(.7*window.innerHeight)-30;
}
document.getElementById("lightbox1").style.height=conheight+"px";

	
}
