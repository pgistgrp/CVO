// JavaScript Document
var windowWidth = "400";
var windowHeight = "400";

function findxy(){
	if (self.innerWidth)
	{
		frameWidth = self.innerWidth;
		frameHeight = self.innerHeight;
		windowWidth = frameWidth;
		windowHeight = frameHeight;
	}
	else if (document.documentElement && document.documentElement.clientWidth)
	{
		frameWidth = document.documentElement.clientWidth;
		frameHeight = document.documentElement.clientHeight;
		windowWidth = frameWidth;
		windowHeight = frameHeight;
	}
	else if (document.body)
	{
		frameWidth = document.body.clientWidth;
		frameHeight = document.body.clientHeight;
		windowWidth = frameWidth;
		windowHeight = frameHeight;
	}
	else return;

}