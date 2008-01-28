<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html:html>
<head>
<title>Discuss and Review Tool</title>
<style type="text/css" media="screen">@import "styles/lit.css";</style>
<script language="JavaScript" src="scripts/qtip.js" type="text/JavaScript"></script>
<script src="scripts/tags.js" type="text/javascript"></script>
<script src="scripts/prototype.js" type="text/javascript"></script>
<script src="scripts/scriptaculous.js?load=effects,dragdrop" type="text/javascript"></script>
<script src="scripts/globalSnippits.js" type="text/javascript"></script>

<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type='text/javascript' src='/dwr/interface/DRTAgent.js'></script>

<pg:outputProperty property="javascript" />

<script type="text/javascript">
    var infoObject = null;
    
    function InfoObject() {
        displayIndicator(true);
        this.oDivElement = 'infoObjectBox';
        this.discussionDivElement = 'discussionBox';
        this.page = 1;
        this.getTargets = function(){
            DRTAgent.getTargets({oid:${infoObject.id}}, <pg:wfinfo/>,{
                callback:function(data){
                    if (data.successful){
                        displayIndicator(false);
                        $(infoObject.oDivElement).innerHTML = data.source.html;
                        eval(data.source.script);
                    }else{
                        displayIndicator(false);
                        alert(data.reason);
                    }
                },
                errorHandler:function(errorString, exception){ 
                    alert("get targets error: " + errorString +" "+ exception);
                }
            });
        };
        this.getComments = function(page){
            DRTAgent.getComments({oid:${infoObject.id}, page:page}, <pg:wfinfo/>,{
                callback:function(data){
                    if (data.successful){
                        displayIndicator(false);
                        $(infoObject.discussionDivElement).innerHTML = data.html;
                        infoObject.page = data.page;
                    }else{
                        displayIndicator(false);
                        alert(data.reason);
                    }
                },
                errorHandler:function(errorString, exception){ 
                    alert("get targets error: " + errorString +" "+ exception);
                }
            });
        };
    }
    
    function onPageLoaded() {
        infoObject = new InfoObject();
        window.setTimeout('tooltip.init()',1000);
        infoObject.getComments(1);
    }
</script>

<event:pageunload />

</head>

<body onLoad="onPageLoaded();">
    <wf:nav />
    <wf:subNav />

    <div style="display: none;" id="loading-indicator">Loading... <img src="/images/indicator_arrows.gif"></div>
    <div id="container">

        <div id="overview" class="box2">
            <pg:termHighlight styleClass="glossHighlight" url="glossaryView.do?id=">
                <h3 class="headerColor">Overview and instructions</h3>
                <p>The moderators have reviewed the concerns and summarized them according to themes. Now you can:</p>
                <ul>
                    <li>Review the concern themes</li>
                    <li>Discuss how well these summaries represent participants' concerns</li>
                    <li>Suggest revisions to the summaries in your discussion comments</li>
                </ul>
                <a href="#" onclick="Effect.toggle('hiddenRM','blind'); return false">Read more about this step</a>
                <p id="hiddenRM" style="display:none">After the brainstorm concluded, the moderators synthesized and summarized the concerns offered by participants. (<pg:url page="lmFaq.do" target="_blank" anchor="step1-created">Read more about the summarization process</pg:url>). Each concern theme is associated with a group of keywords. As you review summaries let us know if you think these summaries are accurate and if you feel any important themes were left out. The moderator will make revisions based on participant comments. The final version of these summaries will be included in the final report of the <em>LIT Challenge</em>. The summaries will also be used in Step 2 when we we assess different "factors" used to evaluate proposed transportation improvement projects.</p>
            </pg:termHighlight>
        </div>

        <div id="infoObjectBox" class="infoObjectBox">
            <pg:include property="page" />
        </div>
        
        <div id="discussionBox" class="discussionBox"></div>

        <div class="clearBoth"></div>
    </div>
    
    <wf:subNav />
    
    <pg:feedback id="feedbackDiv" action="sdMain.do" />

    <div id="footer">
        <jsp:include page="/footer.jsp" />
    </div>
    
</body>

</html:html>

