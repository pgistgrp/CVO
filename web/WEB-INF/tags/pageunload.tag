<%@ tag body-content="empty" %>
<script type='text/javascript' src='/dwr/interface/SystemAgent.js'></script>
<script>
  window.onunload = unloadPage;
  function unloadPage() {
    SystemAgent.setUnloading(function(data) {});
  }
</script>
