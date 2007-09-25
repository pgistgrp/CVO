
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
	<head>
	<title>Resources</title>
	<!-- Site Wide CSS -->
	<style type="text/css" media="screen">
@import "styles/lit.css";

#resources p {margin-left:1em;}
#container {font-size:12pt;}
BODY, P, DIV, H1, H2, H3, H4, H5, H6, ADDRESS, OL, UL, TITLE, TD, OPTION, SELECT {
 font-family: Verdana;
 
}

BODY, P, DIV, ADDRESS, OL, UL, LI, TITLE, TD, OPTION, SELECT {  
  font-size: 10.0pt;
  margin-top:0pt;  
  margin-bottom:0pt;  
} 

BODY, P {
  margin-left:0pt; 
  margin-right:0pt;
}

BODY {
  line-height: ;

  margin: 6px;
  padding: 0px;
}

h6 { font-size: 10pt }
h5 { font-size: 11pt }
h4 { font-size: 12pt }
h3 { font-size: 13pt }
h2 { font-size: 14pt }
h1 { font-size: 16pt }

blockquote {padding: 10px; border: 1px #DDDDDD dashed }

a img {border: 0}

#doc-contents {
  background-color: #ffffff;
}

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
	<wf:nav />
	<!-- Begin header menu - The wide ribbon underneath the logo -->
	<div id="headerMenu">
		<div id="headerContainer">
			<div id="headerTitle" class="floatLeft">
				<h3 class="headerColor">Learn More</h3>
			</div>
			<div class="floatLeft headerButton"> <pg:url page="lmMenu.do">Menu</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmAbout.do">About LIT</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmFaq.do">FAQ</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmTutorial1.do">Tutorial</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmGallery.do">Project Map</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="glossaryPublic.do">Glossary</pg:url> </div>
			<div class="floatLeft headerButton currentBox"> <pg:url page="lmResources.do">More Resources</pg:url> </div>
		</div>
	</div>
	<!-- End header menu -->
	<!-- #container is the container that wraps around all the main page content -->
	<div id="container">
		<h2 class="headerColor">Resources</h2><br>
		<div id="resources">
<p>
  <font size=3><b>Government agencies responsible for regional transportation
  planning and operations</b></font>

</p>
<ul>
  <li>
    <p>
      <a href=http://www.psrc.org/ id=xcrd title="Puget Sound Regional Council">Puget
      Sound Regional Council</a>
    </p>
    <p>
      The Puget Sound Regional Council is an association of cities, towns,
      counties, ports, and state agencies that serves as a forum for developing
      policies and making decisions about regional growth and transportation
      issues in the four-county central Puget Sound region.
    </p>
  </li>

  <li>
    <p>
      <a href=http://www.wsdot.wa.gov id=la8d title="Washington Department of Transportation">Washington
      Department of Transportation</a>
    </p>
    <p>
      State agency responsible for highway maintenance and operations.
    </p>
  </li>
</ul>

<ul>
  <li>
    <p>
      <a href=http://www.soundtransit.org id=v5z_ title="Sound Transit">Sound
      Transit</a>
    </p>
    <p>
      Sound Transit was created by the Washington State Legislature to build a
      mass transit system that connects regional employment and population
      centers in King, Pierce, and Snohomish counties.
    </p>
  </li>

</ul>
<p>
  <font size=3><br>
  </font>
</p>
<p>
  <font size=3><b>Local organizations which conduct transportation research or
  advocacy</b></font><br>
</p>
<p>
</p>
<ul type=disc>
  <li class=MsoNormal>

    <p>
      <a href=http://www.allaboardwashington.org/>All Aboard Washington</a>
    </p>
  </li>
  <li class=MsoNormal>
    <p>
      <a href=http://www.effectivetransportation.org/>Coalition for Effective
      Transportation Alternatives</a>
    </p>

  </li>
  <li class=MsoNormal>
    <p>
      <a href=http://www.discovery.org/cascadia id=dwy_ title="Discovery Institute: Cascadia Center">Discovery
      Institute: Cascadia Center</a>
    </p>
  </li>
  <li class=MsoNormal>
    <p>

      <a href=http://www.eastsideta.com/ id=oici title="Eastside Transportation Association">Eastside
      Transportation Association</a>
    </p>
  </li>
  <li class=MsoNormal>
    <p>
      <a href=http://www.peopleswaterfront.org/>People's Waterfront
      Coalition</a>
    </p>
  </li>

  <li class=MsoNormal>
    <p>
      <a href=http://www.rethinkrail.com/>Rethink Rail</a>
    </p>
  </li>
</ul>
<ul>
  <li>
    <p>

      <a href=http://www.eastsideta.com/ id=oici title="Eastside Transportation Association"></a><a href=http://www.transportationchoices.org/ id=m681 title="Transportation Choices Coalition">Transportation
      Choices Coalition</a>
    </p>
  </li>
  <li class=MsoNormal>
    <p>
      <a href=http://www.transnow.org/>Transportation Northwest</a>
    </p>
  </li>

  <li class=MsoNormal>
    <p>
      <a href=http://trb.org/>Transportation Research Board</a>
    </p>
  </li>
  <li class=MsoNormal>
    <p>
      <a href=http://www.sightline.org/>Sightline</a>

    </p>
  </li>
  <li class=MsoNormal>
    <p>
      <a href=http://www.transnow.org/></a><a href=http://depts.washington.edu/trac/index.html>Washington
      State Transportation Center</a>
    </p>
  </li>
</ul>
<p>

  <font size=3><br>
  </font>
</p>
<p>
  <font size=3><b>Cool web resources and maps</b></font><br>
</p>
<ul>
  <li>
    <p>
      <a href=http://www.wsdot.wa.gov/traffic/seattle id=na_d title="Puget Sound Traffic Flow Map">Puget
      Sound Traffic Flow Map</a>

    </p>
  </li>
  <li class=MsoNormal>
    <p>
      <a href=http://www.wsdot.wa.gov/PugetSoundTraffic/>Puget Sound Traffic
      Cams</a>
    </p>
  </li>
</ul>
<ul>

  <li>
    <p>
      <a href=http://www.walkscore.com id=uewp title="Walk Score">Walk Score</a>
    </p>
    <p>
      Walk Score helps people find walkable places to live. Walk Score
      calculates the walkability of an address by locating nearby stores,
      restaurants, schools, parks, etc.
    </p>
  </li>
  <li>

    <p>
      <a class=state-published href=http://www.sightline.org/maps/maps/Sprawl-Walkability-CS06m/?searchterm=>Map
      of Walkable King County, WA</a>
    </p>
  </li>
  <li>
    <p>
      <a href=http://sustainableseattle.org/Programs/SUNI/researchingconditions/neighborhoodstats id=yq9k title="Sustainable Seattle's Neighborhood Statistics">Sustainable
      Seattle's Neighborhood Statistics</a>
    </p>

    <p>
      This resource provides users an opportunity to explore demographics,
      accessibility, and amenities of Seattle neighborhoods.
    </p>
  </li>
  <li>
    <p>
      <a href=http://historylink.org/results.cfm?keyword=washdot id=zevi title="History Link: Washington State transportation history">History
      Link: Washington State transportation history</a>
    </p>
  </li>

</ul>
<p>
  <font size=4><br>
  </font>
</p>
<p>
  <font size=4>Local media</font>
</p>
<ul type=disc>
  <li class=MsoNormal>
    <p>

      <a href=http://seattlepi.nwsource.com/transportation>Seattle P-I
      Transportation</a>
    </p>
  </li>
  <li class=MsoNormal>
    <p>
      <a href=http://seattletimes.nwsource.com/html/localnews/>Seattle Times
      Local News</a>
    </p>
  </li>

  <li class=MsoNormal>
    <p>
      <a href=http://www.heraldnet.com/section/NEWS01 id=yhor title="Everett Herald Local News">Everett
      Herald Local News</a>
    </p>
  </li>
  <li class=MsoNormal>
    <p>
      <a href=http://www.thenewstribune.com/news/local/ id=fl0k title="Tacoma Tribune Local News">The
      News Tribune (Tacoma) Local News</a>

    </p>
  </li>
  <li class=MsoNormal>
    <p>
      <a href=http://seattle.bizjournals.com/seattle/>Puget Sound Business
      Journal</a>
    </p>
  </li>
  <li class=MsoNormal>

    <p>
      <a href=http://www.thestranger.com/>The Stranger</a>
    </p>
  </li>
  <li class=MsoNormal>
    <p>
      <a href=http://www.seattleweekly.com/>Seattle Weekly</a>
    </p>

  </li>
</ul>
<p>
  <font size=4><br>
  </font>
</p>
<p>
  <font size=4>Blogs about local transportation
  issues</font><font color=#330033><font size=4> and
  politics</font></font><b><font color=#330033><br>
  </font></b>

</p>
<ul type=disc>
  <li class=MsoNormal>
    <p>
      <font color=#330033><a href=http://blog.seattlepi.nwsource.com/buschick>Bus
      Chick</a></font>
    </p>
  </li>
  <li class=MsoNormal>
    <p>

      <font color=#330033><a href=http://www.sightline.org/daily_score>The Daily
      Score</a></font>
    </p>
  </li>
  <li class=MsoNormal>
    <p>
      <font color=#330033><a href=http://www.cascadiareport.com/>Cascadia
      Report</a></font>
    </p>
  </li>

  <li class=MsoNormal>
    <p>
      <font color=#330033><a href=http://urbantransit.blogspot.com/index.html>Urban
      Environmentalist</a></font>
    </p>
  </li>
  <li class=MsoNormal>
    <p>
      <font color=#330033><a href=http://friendsofseattle.typepad.com/blog/>Friends
      of Seattle</a></font>

    </p>
  </li>
  <li class=MsoNormal>
    <p>
      <font color=#330033><a href=http://www.orphanroad.com id=twec title="Orphan Road: Documenting Seattle's infrastructure upgrade">Orphan
      Road: Documenting Seattle's infrastructure upgrade</a></font>
    </p>
  </li>
  <li class=MsoNormal>

    <p>
      <font color=#330033><a href=http://seatrans.blogspot.com/>Seattle Transit
      Blog</a></font>
    </p>
  </li>
  <li class=MsoNormal>
    <p>
      <font color=#330033><a href=http://iamseattletraffic.org/>I Am Seattle
      Traffic</a></font>
    </p>

  </li>
</ul>
<ul>
  <li>
    <p>
      <a href=http://wsdotblog.blogspot.com/ id=yev_ title="The WashDOT Blog">The
      WSDOT Blog</a>
    </p>
  </li>
</ul>
<ul type=disc>

  <li class=MsoNormal>
    <p>
      <a href=http://www.soundpolitics.com/ title="">Sound Politics</a>
    </p>
  </li>
</ul>
<p class=MsoNormal>
  &nbsp;
</p>
<p>

  <br>
</p>
<br>
		</div>
	</div>
	<!-- end container -->
	<!-- Begin header menu - The wide ribbon underneath the logo -->
	<div id="headerMenu">
		<div id="headerContainer">
			<div id="headerTitle" class="floatLeft">
				<h3 class="headerColor">Learn More</h3>
			</div>
			<div class="floatLeft headerButton"> <pg:url page="lmMenu.do">Menu</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmAbout.do">About LIT</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmFaq.do">FAQ</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmTutorial1.do">Tutorial</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmGallery.do">Project Map</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="glossaryPublic.do">Glossary</pg:url> </div>
			<div class="floatLeft headerButton currentBox"> <pg:url page="lmResources.do">More Resources</pg:url> </div>
		</div>
	</div>
	<!-- End header menu -->
	<!-- Begin footer -->
	<!-- End footer -->
	</body>
</html>
