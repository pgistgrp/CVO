<%@ taglib prefix="wf" tagdir="/WEB-INF/tags" %>
<!-- Begin header menu - The wide ribbon underneath the logo -->
<div id="headerMenu">
  <div id="headerContainer">
    <div id="headerTitle" class="floatLeft">
      <h3 class="headerColor" style="margin-top:0px;">${structure.title}</h3>
      <wf:nav />
    </div>
    <div class="headerButton floatLeft"><a href="cctlist.do">1a: Brainstorm</a></div>
    <div class="floatLeft headerButton currentBox"><a href="#">1b: Review summaries</A></div>
    <div id="headerNext" class="floatRight box5"><a href="/sdcWaiting.jsp">Next step</A></div>

  </div>
</div>
<!-- End header menu -->