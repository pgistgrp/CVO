<table width="790" height="100%">
  <tr>
    <td style="width:690px; height:20px;" nowrap>
      <b>Concerns</b>
      <div style="display:inline;padding-left:40px;">
        Sort feedback by:
        <select name="selectsort" id="selectsort" onChange="javascript:infoObject.getConcerns(1); ">
          <option value="1">Newest to oldest</option>
          <option value="2">Oldest to newest</option>
          <option value="3">Most agreement</option>
          <option value="4">Least agreement</option>
          <option value="5">Most comments</option>
          <option value="6">Most views</option>
          <option value="7">Most votes</option>
        </select>
      </div>
      <div id="filterDiv" style="display:none; padding-left:40px; width:50px;">filtered by "<span id="filterSpan" style="font-weight:bold;"></span>"<a href="javascript: infoObject.getConcerns(1, '-1');"><img src="images/close.gif" style="vertical-align:middle;" alt="clear filter" /></a></div>
    </td>
    <td style="width:10px; border-right:thin dotted #B4D579;"></td>
    <td>
      <b>Keyphrases</b>
      <div style="display:inline;">
        <select name="tagsort" id="tagsort" onChange="javascript:infoObject.getTags(1); ">
          <option value="0">a-z</option>
          <option value="1">z-a</option>
          <option value="2">frequency</option>
        </select>
      </div>
    </td>
  </tr>
  <tr>
    <td valign="top"><div id="concernsPanel" style="width:100%;"></div></td>
    <td style="width:10px; border-right:thin dotted #B4D579;"></td>
    <td valign="top"><div id="tagsPanel" style="width:100%;"></div></td>
  </tr>
</table>

