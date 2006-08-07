// JavaScript Document
var windowWidth = "400";
function dosize(){
	if (self.innerWidth)
	{
		frameWidth = self.innerWidth;
		frameHeight = self.innerHeight;
		windowWidth = frameWidth;
	document.getElementById("cont-resize").style.height= (frameHeight) + "px;";
	}
	else if (document.documentElement && document.documentElement.clientWidth)
	{
		frameWidth = document.documentElement.clientWidth;
		frameHeight = document.documentElement.clientHeight;
		windowWidth = frameWidth;
	document.getElementById("cont-resize").style.height= (frameHeight);
	}
	else if (document.body)
	{
		frameWidth = document.body.clientWidth;
		frameHeight = document.body.clientHeight;
		windowWidth = frameWidth;
	document.getElementById("cont-resize").style.height= (frameHeight);

	}
	else return;

}