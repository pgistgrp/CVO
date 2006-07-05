// JavaScript Document
function dosize(){
	if (self.innerWidth)
	{
		frameWidth = self.innerWidth;
		frameHeight = self.innerHeight;
	document.getElementById("cont-resize").style.height= (frameHeight) + "px;";

	}
	else if (document.documentElement && document.documentElement.clientWidth)
	{
		frameWidth = document.documentElement.clientWidth;
		frameHeight = document.documentElement.clientHeight;
	document.getElementById("cont-resize").style.height= (frameHeight);

	}
	else if (document.body)
	{
		frameWidth = document.body.clientWidth;
		frameHeight = document.body.clientHeight;
	document.getElementById("cont-resize").style.height= (frameHeight);

	}
	else return;

}