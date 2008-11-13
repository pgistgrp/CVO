<center><b>Path : ${path.title}</b></center>
<br>
<table>
  <tr>
    <td align="right">Indicator:</td>
    <td><input type="text" maxlength="100" size="50" id="indicator" value=""></td>
  </tr>
  <tr>
    <td align="right">Suggested unit of measurement:</td>
    <td><input type="text" maxlength="100" size="50" id="measurement" value=""></td>
  </tr>
</table>
<b>Specialist Information:</b>
<table>
  <tr>
    <td width="20"></td>
    <td><label style="cursor: pointer;	cursor: hand;" for="appr">Appropriate</label> <input type="checkbox" id="appr" value="appr"></td>
    <td style="padding-left:20px;"><label style="cursor: pointer;	cursor: hand;" for="avail">Available</label> <input type="checkbox" id="avail" value="avail"></td>
    <td style="padding-left:20px;"><label style="cursor: pointer;	cursor: hand;" for="dup">Duplicate</label> <input type="checkbox" id="dup" value="dup"></td>
    <td style="padding-left:20px;"><label style="cursor: pointer;	cursor: hand;" for="reco">Recommended</label> <input type="checkbox" id="reco" value="reco"></td>
  </tr>
</table>
<br>
<center><input type="button" value="Save" onclick="tree1.saveUnit();"></center>
