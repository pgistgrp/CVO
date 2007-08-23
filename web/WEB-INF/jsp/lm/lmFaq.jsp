<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html>
	<head>
	<title>Let's Improve Transportation - Learn More FAQ</title>
	<!-- Site Wide CSS -->
	<style type="text/css" media="screen">
@import "styles/lit.css";

#container {font-size:12pt;}
#container h3 {font-size:1.5em;}

</style>
	<!-- End Site Wide CSS -->
	<!-- Site Wide JS -->
	<script language="JavaScript" src="scripts/qtip.js" type="text/JavaScript"></script>
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
			<div class="floatLeft headerButton currentBox"> <pg:url page="lmFaq.do">FAQ</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmTutorial1.do">Tutorial</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmGallery.do">Project Map</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="glossaryPublic.do">Glossary</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmResources.do">More Resources</pg:url> </div>
		</div>
	</div>
	<!-- End header menu -->
	<!-- #container is the container that wraps around all the main page content -->
	<div id="container"><pg:termHighlight styleClass="glossHighlight" url="glossaryView.do?id=">
		<h2 class="headerColor">Frequently Asked Questions</h2>
		<p>Here are answers to some of the most common questions participants ask.</p>
		
		<a name="step1"><h3 class="headerColor">Step 1: Discuss Concerns</h3></a>
		
		<a name="step1-keyword"><h4 class="headerColor">What is a keyword?</h4></a>
		<p>Think of keywords as labels. These labels help make your concerns easier to find among the many other concerns 	provided by other participants. Keywords are important for this first step in our process, as it assists the moderator in 	finding and clustering concerns so that summaries can be written. It is important, therefore, to provide keywords for your concerns with words or word phrases that you feel appropriately convey the meaning of your concern.</p>
		
		<a name="step1-created"><h4 class="headerColor">How are the summaries created?</h4></a>
		<p>As participants add concerns, the moderator will review a subset of these concerns by sampling the keywords participants used to label their concerns. While reading the concerns, the moderator will place these concerns into topical themes, by creating themes and adding keywords to these themes. Due to the many concerns added by participants, it is possible that the moderator will not be able to review every concern, or every group of concerns with the same keyword. Therefore, your responsibility as a participant is to review these summaries and discuss how these summaries might be re-written differently.</p>
		
		<a name="step1-revised"><h4 class="headerColor">How are summaries revised</h4></a>
		<p>When a significant number of participants indicate that they disagree with a summary, the moderator will review discussions and suggested revisions by participants. If it appears that a revision is necessary, the moderator will make changes and notify participants. At the conclusion of this step, the summaries will no longer be revised. Some participants may disagree with the 'final' version of a summary. These participants can express this disagreement in the 	poll as well as their discussion comments about the summary. These comments will always be available on the LIT website for all participants and will also be available to decision makers as a link from the final report.</p>
		
		<a name="step1-important"><h4 class="headerColor">Who reads these summaries? Why are the summaries important?</h4></a>
		<p>You and your fellow participants will initially read and review these summaries. The moderator may revise these summaries based on participant feedback and discussion. At the conclusion of this step, the summaries will cease to be updated. Next, the summaries will be used in Step 2 when participants assess different "factors" used to evaluate proposed transportation improvement projects. Finally, the summaries will be included in a final report given to decision makers. This report will include a link to the LIT website where readers can review the original comments and concerns from participants.</p>
		
		<a name="step2"><h3 class="headerColor">Step 2: Assess transportation improvement factors</h3></a>
		
		<a name="step2-chosen"><h4 class="headerColor">How were the transportation improvement factors chosen?</h4></a>
		<p>To aid in evaluation of transportation improvement projects the UW research team developed a set of criteria representing a broad range of attributes a particular project might influence. Such a broad representation allows a full picture of how a project impacts the community and wider region. </p>
		<p>The primary basis for our list of nine factors was the result of a review of federal, state and regional transportation planning factors that these agencies use to evaluate potential projects. The most recently passed federal surface transportation act, known as SAFETEA-LU, directs state and regional governments to consider a set of eight planning factors in transportation decision-making. These factors closely mirror the factors used for Let's Improve Transportation (LIT). In summarized form, they include: 1) economic vitality, 2) safety, 3) security, 4) accessibility and mobility, 5) environment, energy and quality of life, 6) integration and connectivity of the transportation system, 7) efficient system management, and 8) preservation. </p>
		<p>In addition, both the Washington State Department of Transportation (WSDOT) and the Puget Sound Regional Council (PSRC) have a list of strategic issues to consider when making long range transportation planning decisions. Both WSDOT's <a href="http://www.wsdot.wa.gov/planning/wtp/" target="_blank">Washington Transportation Plan</a> (WTP) and PSRC's <a href="http://www.psrc.org/projects/vision/index.htm" target="_blank">Vision 2020</a> list criteria that closely mirror the federal planning factors. One notable exception is both groups' inclusion of freight movement as an important consideration in planning decisions.</p>
		<p>The nine LIT improvement factors cover the priorities set forth at the federal, state and regional level. In addition, four specific objectives were identified for each factor in order to define their nature and scope. These objectives were chosen to reflect the federal, state and regional law and plans referred to above. Each factor was assigned an equal number of objectives to ensure equal weight for all factors in project scoring (see below).</p>
		
		<a name="step3"><h3 class="headerColor">Step 3: Create transportation packages</h3></a>
		
		<a name="step3-assigned"><h4 class="headerColor">How were project scores assigned?</h4></a>		
		
		<p>All proposed transportation projects appearing on the LIT website have a letter grade for each improvement factor. The factors were not scored directly. Rather, a panel of six researcher team members with a diversity of perspectives in transportation planning assigned a numeric score to each objective associated with the improvement factors. The scores, ranging from negative three to positive three, correspond to a subjective judgment regarding the predicted impacts of the completed project on the region. Positive scores mean the project is predicted to have a positive impact on the region for the objective under consideration, while negative scores mean the predicted impact is negative. </p>
		<p>For example, a score of two for the objective "impact on vehicle emissions and air pollution" means that the panel judged the project would have a moderate amount of beneficial impact on the region's vehicle emissions and air pollution. Conversely, a score of negative one for the objective "impact on travel demand" means the judges felt the project would have a minor negative impact on travel demand (or, in other words, a minor increase in regional travel demand).</p>
		<p>A project's improvement factor grade is based on a numeric average of the scores assigned to each of the four objectives associated with that factor. Each objective score is the average of two individual judges' scores, one with expertise in transportation engineering and another with expertise in urban planning or urban geography. The judges assigned scores to each project over a three-week period. To evaluate the projects they used the same project information currently available to users of the LIT website. </p>
		<p>It is important to note that the scores represent the <em>qualitative</em> judgments of research staff based on limited information. The transportation projects under consideration are at widely varying levels of completion. Some are nearly ready for construction, others have barely begun the planning and design process. In some cases a great deal of information is known about the projects, in others it is too early in their development to have many details. Therefore, the project grades are intended simply to begin a broader conversation among LIT participants regarding the potential merits and impacts of proposed projects.</p>
		
		<a name="step3-needed"><h4 class="headerColor">How is the "money needed" figured determined for each project?</h4></a>		
		
		<p>The transportation projects under consideration are at widely varying levels of completion. Some are nearly ready for construction, others have barely begun the planning and design process. In some cases a great deal of information is known about the projects, in others it is too early in their development to have many details.</p>
		<p>One important piece of information about each project for which there is varying levels of information is cost. Some projects, for which costs estimates and project development are more mature, state or federal funds have already been specifically allocated for implementation. For others, little has been done to develop the project, and no funds have been allocated. "Money needed" is the best estimate for a project's full cost minus money already guaranteed to complete it, given available information about what the cost is an how much money has been allocated for it. </p>
		<p>For example, $2.4 billion in federal, state and local funding has already been committed to the Alaska Way Viaduct. However, current cost estimates for the elevated structure show the total price of this alternative to be $2.82 billion. So, additional "money needed" to complete the project is $416.5 million.  Alternatively, the estimated cost of extending light rail above ground from Seattle to Bellevue is $1.7 bfrillion. But no money has been allocated for this project, so the "money needed" remains $1.7 billion.</p>

		<a name="step4"><h3 class="headerColor">Step 4: Select a recommended package</h3></a>
		
		<a name="step4-clustered"><h4 class="headerColor">How were participants' individual packages clustered into new "candidate" packages?</h4></a>		
		
		<a name="step4-ej"><h4 class="headerColor">What is environmental justice?</h4></a>		
		<p>Environmental justice (EJ) is the notion that certain people bear a disproportional share of environmental burdens. According to Newton (1996) the people that tend to bear this geographic inequality the most are poor people of color. People of color within the EJ movement represent "[i]ndividuals whose skin pigmentation may be other than white (such as brown, black, or yellow), but, more importantly, whose culture is different from that of white Americans from a European culture. African Americans, Hispanic Americans, Native Americans, Asian Americans, and Pacific Island Americans are usually regarded as belonging to communities of color" (p. 254). According to Shrader-Frechette (2002) people or organizations promoting EJ are attempting to "equalize the burdens of pollution, noxious development, and resource depletion"; to that end, EJ "requires both a more equitable distribution of environmental goods and bads and greater public participation in evaluating and apportioning these goods and bads" (p. 6). Further, studies "consistently show that socioeconomically deprived groups are more likely than affluent whites to live near pollution facilities, eat contaminated fish, and be employed at risky occupations. Research also confirms that they are less able to prevent and to remedy such inequities" (Shrader-Frechette, 2002, p. 7).</p>
		
		<a name="step4-variables"><h4 class="headerColor">Why were the variables for evaluating the environmental justice chosen?</h4></a>	
		<p><strong>Not White:</strong> Blacks, Latino's, and Asian Americans are thought of as being minorities that are higher risk of suffering unfair socio-economic prejudice(s). Since the White population in the Puget Sound region is high (in 2004 the white non-Hispanic population for Washington State was 77.5%), we use the census variable that indicates people who are White of one race. Then, we divide this by the total census tract population and subtract this value from one to derive a number that reflects all non-whites of one race in a census tract.</p>
		<p><strong>Poverty:</strong> Poverty has a large impact on an individual's choices, disadvantages, ability to participate in the democratic process, and so on.</p> 
		<p><strong>Single-mother households with children under the age of 18:</strong> This variable is considered because of the unique transportation challenges faced by single parents as well as society bias associated with gender.</p>
		<p><strong>Total non-English speakers from the ages of 5 through 65:</strong> This variable indicates areas where special attention (in the form of signs and symbols) may need to be directed.</p>
		<p><strong>Total elderly population:</strong> Elderly people face unique transportation challenges that necessitate attention. In this study, elderly is considered to be those individuals at or over the age of 65.</p> 
		<p><strong>Carless households:</strong> Carless people must rely on public transportation, friends, family, co-workers, bicycles, or their own two feet to get places they need to go (including work, grocery shopping, parks, and schools). This variable is calculated for both renters and owners of a residence.</p> 
		<p><strong>Disability:</strong> People with disability are less able to be mobile or leave the home and therefore may need special attention vis-&aacute;-vis transportation improvements. </p>


		<a name="step4-collected"><h4 class="headerColor">How were the data for environmental justice variables collected?</h4></a>	
		<p><em>Note, the following description may be confusing. If you have questions about these procedures, feel free to contact David Moore (<a href="mailto:dsm13@u.washington.edu">dsm13@u.washington.edu</a>) for clarification.</em></p>
		<p>The EJVs were collected from the US Census, 2000. While this data does not reflect the current (2007) landscape, it is useful. While more current data is available (the American Community Survey, for example) it is not available at the census tract level of geographic detail. Table 1 shown population-type data as delivered from the Census site. Seven variables will be used, and these appear in Table 2. </p>
		<strong>Table 1. Example of spreadsheet</strong>
		<p><img src="images/faq-table1.png" title="Example of spreadsheet"></p>
		<strong>Table 2. Threshold values for PSRC Region by county </strong>
		<p><img src="images/faq-table2.png" title="PSRC threshold values"></p>
		<p>The <strong>thresholds</strong> are calculated by taking the total number of people in a variable and dividing this by the total number of people in an areal unit (the County); this is done for all counties comprising the MPO area. For example, in King County, the total number of households in poverty is 55,739 while the entire number of households is 711,235. Thus (55,739)/(711,235) &#8776;.078369 or 7.84%. Our threshold for poverty is 7.84%. The thresholds for each variable appear in Table 4.</p>
		<p><strong>Tract percents</strong> are calculated in the same way as the thresholds are. Using Poverty as our example, again, we find that for tract number one, the household total is 2,581, and the households in poverty total 499. Thus, (499)/(2,581) &#8776;0.1933 or 19.33% households are in poverty in that tract. This example is shown in Table 3. </p>
		<strong>Table 3. Tract poverty threshold example </strong>
		<p><img src="images/faq-table3.png" title="Tract poverty threshold example"></p>
		<p>To determine if tracts are EJAs, the percents for the EJVs are classified into quartiles. The 75th percentile of any EJV is considered to be an EJA; therefore, tracts at or above the 75th percentile will appear on the map(s) participants generate. The quartiles and maximum values for each variable by county appear in Table 4. </p>
		<strong>Table 4. County quartiles and maximum values </strong>
		<p><img src="images/faq-table4.png" title="County quartiles and maximum values"></p>
				<a name="references"><h4 class="headerColor">References</h4></a>	
				<ul>
					<li>Newton, D. E. (1996). <em>Environmental justice: a reference handbook</em>. Santa Barbara, Calif.: ABC-CLIO. </li>
					<li>Shrader-Frechette, K. (2002). <em>Environmental justice: creating equality, reclaiming democracy</em>. Oxford; New York: Oxford University Press. </li>
				</ul>
	</p>
		
		<br /></pg:termHighlight>
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
			<div class="floatLeft headerButton currentBox"> <pg:url page="lmFaq.do">FAQ</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmTutorial1.do">Tutorial</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmGallery.do">Project Map</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="glossaryPublic.do">Glossary</pg:url> </div>
			<div class="floatLeft headerButton"> <pg:url page="lmResources.do">More Resources</pg:url> </div>
		</div>
	</div>
	<!-- End header menu -->
	<!-- Begin footer -->
	<!-- End footer -->
	</body>
</html:html>
