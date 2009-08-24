<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html>
<head>
	<title>Learn More</title>
	<!-- Site Wide CSS -->
	<style type="text/css" media="screen">
		@import "styles/lit.css";
		#container {font-size:12pt;}
		#container h3 {font-size:1.2em;margin:30px auto;}
		p {margin-left:10px;margin-top:5px;}
		p.nomargin {margin-left:0px;}
	</style>
	<!-- End Site Wide CSS -->
	<!-- Site Wide JS -->
	<script src="scripts/prototype.js" type="text/javascript"></script>
	<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
	<script src="scripts/search.js" type="text/javascript"></script>
	<script type='text/javascript' src='/dwr/engine.js'></script>
	<script type='text/javascript' src='/dwr/util.js'></script>
</head>
<body>
	<!-- Begin the header - loaded from a separate file -->
    <wf:nav />
	<!-- End header -->
	<!-- Begin header menu - The wide ribbon underneath the logo -->
<div id="headerMenu">
	<div id="headerContainer">
		<div id="headerTitle" class="floatLeft">
			<h3 class="headerColor">Learn More About</h3>
		</div>
		<div class="floatLeft headerButton currentBox"> <pg:url page="lmMenu.do">Overview</pg:url> </div>
		<div class="floatLeft headerButton"> <pg:url page="lmAbout.do">Climate Concerns</pg:url> </div>
		<div class="floatLeft headerButton"> <pg:url page="lmFaq.do">FAQ</pg:url> </div>
        <!--
		<div class="floatLeft headerButton"> <pg:url page="lmTutorial1.do">Tutorial</pg:url> </div>
		<div class="floatLeft headerButton"> <pg:url page="lmGallery.do">Maps</pg:url> </div>
		<div class="floatLeft headerButton"> <pg:url page="glossaryPublic.do">Glossary</pg:url> </div>
		<div class="floatLeft headerButton"> <pg:url page="lmResources.do">More Resources</pg:url> </div>
        -->
	</div>
</div>
	<!-- End header menu -->
	<!-- #container is the container that wraps around all the main page content -->
<div id="container">
	<h2 class="headerColor">Overview</h2> 
	<p>From this page, a user can learn more about the Vulnerability Mapping project in general, as well as the two online experiments: 
	Voicing Climate Concerns (VCC) experiment and the Deliberative Mapping of Vulnerabillty (DMV) experiment. 
	The entire website is available to registered participants. <a href="register.do">Register now</a>.  A user can also link to the 
	<a href="http://www.coastalatlas.net"title="Oregon Coastal Atlas">Oregon Coastal Atlas</a>.</p>
	<!-- Begin explanation -->

	<h3 class="headerColor">Vulnerability Mapping Project Overview</h3>
		
	<p>Vulnerability assessments are a key component of adaptation planning for climate change.  
	Community vulnerability assessments provide an inventory the most salient concerns communities consider when undertaking 
	adaptation planning about climate change.</p>
	<p>Voicing Climate Concerns (VCC) is a web site (<a href="www.climateconcerns.org">www.climateconcerns.org</a>) 
	that helps people develop, through a structured participation process, 
	a shared understanding of their concerns about climate change and how to measure those concerns in terms of 
	climate indicators and receptor indicators. Indicators are commonly defined as measurements that many people can understand. 
	Climate and receptor indicators provide the information, and basis for establishing priorities, 
	about peoples' concerns about climate change as input community vulnerability assessments and 
	subsequently climate change adaptation planning. In addition, the indicators developed through 
	the VCC process provide information to data analysts that direct the compilation of data for creating online map data layers 
	that can be used by community participants for deliberating about priorities for vulnerability assessment and adaptation planning.</p>
	<p>A Deliberative Mapping of Vulnerability (DMV) web tool is under development to support discussions about place-based climate 
	and receptor indicators. The DMV is meant to support participatory deliberation about priorities for vulnerability assessment 
	and adaptation planning. Use of this web site will form a follow-on participatory activity to the VCC activity.</p>
	
	<p><strong>Using indicators to assess vulnerability</strong></p>
	<p>Specifically, a climate indicator is a measurement of a climate change characteristic. 
	Examples of climate indicators are increase or decrease in temperature by area (place) by year (or decade), 
	or increase or decrease in rainfall by area (place) by year (or decade).  
	A "receptor" is any phenomenon (person, place, thing as in habitats, crops, animals etc), 
	that is potentially vulnerable (affected by) climate change (climate indicator).  
	Examples of receptor indicators are habitat gain and/or loss measured in meters squared per year at some place and time, 
	or gain or loss of tourism measured in dollars. Receptor and climate indicators can be mapped, each as a "map layer". 
	Map layers of climate indicators and receptor indicators can be compared one against another to identify possible exposures.</p>
	<p>An overlay of the map layers provides an idea of places and times when receptors and climate indicators are collocated, 
	or in other words where exposures might occur. Using the indicators above, habitat map layers each for a different time can be 
	overlain with temperature map layers each for a different time. Changes in habitat can provide an idea of impact.  
	In other words, we can map where habitats could be vulnerable to increases in temperature. 
	An exposure indicator is the combination of one or more climate indicators (map layers) paired with one or more receptor indicators. 
	A user will be able to draw, label, and comment on the nature of the exposure.</p>
	<p>A climate change impact map can be created by taking the exposure indicator map and specifying the expected (measured) change 
	in a receptor indicator due to an expected (measured) change in a climate indicator.  
	For example, when sea-level rise increases by 6 centimeters for a particular area for a given period of time, 
	the corresponding expected change in habitat gain or loss will be X meters squared.</p>
	
	<p><strong>Levels of Vulnerability Assessment</strong></p>
	<p>Every project has some limitations for what it can do and what must be left for further work. 
	Identifying impacts from exposures, without considering any other information, can be called a level I vulnerability assessment.  
	Quantitative measurements of impacts are difficult to quantify without considering the functional relationship between each unit 
	of climate change and a corresponding unit of receptor change. Nonetheless, the categorical relationship can be discussed, 
	and that is the intention in the deliberative mapping tool used by participants.</p>
	<p>Furthermore, clearly, some receptors might be more "sensitive" to climate change than others.  
	When we take into consideration sensitivity, then we speak of a level II vulnerability assessment.  
	These can also be discussed in terms of categorical relationships rather than quantitative relationships. 
	Discussing them categorically is the first step in a quantitative consideration.</p>
	<p>It is also possible that some receptors have a better adaptive capacity than others to climate change.  
	When we take into consideration adaptive capacity then we are performing a level III vulnerability assessment.</p>
	<p>This project is funded to establish level I vulnerability concerns. 
	However, a deliberative mapping web site will support discussions about sensitivity and adaptive capacity, 
	thereby providing feedback about the development of community vulnerability indicators at levels I, II, III 
	(and perhaps combinations of indicators that we call indexes).  
	Establishing level 1 vulnerability concerns can help provide recommendations and directions for online tool building 
	that can address more details for climate change adaptation planning.</p>
	
	<h3 class="headerColor">Voicing Climate Concerns Experiment Overview</h3>
	<p>Voicing Climate Concerns is an online activity in which participants brainstorm concerns about climate change and 
	variability along the Oregon Coast and analysts produce maps depicting those concerns contingent on availabity of data. 
	Voicing Climate Concerns facilitates citizen involvement, <a href="http://www.oregon.gov/LCD/docs/goals/goal1.pdf">goal 1</a> of 
	<a href="http://www.oregon.gov/LCD/goals.shtml">Oregon's Statewide Planning Goals and Guidelines</a></p>
	<p>
	
    <p>
    There are 4 steps to complete this experiment.  Following the execution of each step, participants
    have the opportunity to review and comment about the group process.  As part of the review and 
    comment process, participants vote on whether the group is ready to proceed to the next step
    </p>
    
    <p>
    In Step 1, participants voice their concerns about climate change and attach key words or phrases
    of their choosing to each of their concerns.
    </p>
    
    <p>
    In Step 2, participants create categories by clustering the keywords and phrases they and the other
    participants articulated in Step 1.
    </p>
    
    <p>
    In Step 3, participants create hierarchies out of the climate concern categories they and other 
    participants created in Step 2.
    </p>
    
    <p>
    In Step 4, participants develop indicators to address the concerns organized in step 3, drawing on 
    information provided by data analysts.
    </p>
    
    <p>
    In Step 5, a report on the outcome of the experiment will be generated, and participants will have 
    the opportunity to review and comment on the report.
    </p>
		
	<br />
</div>
	<!-- end container -->
	<!-- Begin header menu - The wide ribbon underneath the logo -->
<div id="headerMenu">
	<div id="headerContainer">
		<div id="headerTitle" class="floatLeft">
			<h3 class="headerColor">Learn More</h3>
		</div>
		<div class="floatLeft headerButton currentBox"> <pg:url page="lmMenu.do">Overview</pg:url> </div>
		<div class="floatLeft headerButton"> <pg:url page="lmAbout.do">Climate Concerns</pg:url> </div>
		<div class="floatLeft headerButton"> <pg:url page="lmFaq.do">FAQ</pg:url> </div>
        <!--
		<div class="floatLeft headerButton"> <pg:url page="lmTutorial1.do">Tutorial</pg:url> </div>
		<div class="floatLeft headerButton"> <pg:url page="lmGallery.do">Maps</pg:url> </div>
		<div class="floatLeft headerButton"> <pg:url page="glossaryPublic.do">Glossary</pg:url> </div>
		<div class="floatLeft headerButton"> <pg:url page="lmResources.do">More Resources</pg:url> </div>
        -->
	</div>
</div>
	<!-- End header menu -->
</body>
</html:html>
