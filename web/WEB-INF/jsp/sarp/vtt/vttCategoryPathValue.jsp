<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<p>Category Path "<b>${path.title}</b>"</p>
<p>Is this (${path.title}) a value?<br>

<c:if test="${value!=null}">
  <label><input type="radio" id="isValue" name="catValue" checked value="true" onclick="radioClicked(true);">Yes</label>
  <label><input type="radio" id="isNotValue" name="catValue" value="false" onclick="radioClicked(false);">No</label>
  <p>Insert appropriate measurement for path "${path.title}":<br>
  <input type="text" id="valueName" value="${value.name}">
  <p>Insert an unit for this measurement:<br>
  <input type="text" id="valueUnit" value="${value.criterion}">
</c:if>
<c:if test="${value==null}">
  <label><input type="radio" id="isValue" name="catValue" value="true" onclick="radioClicked(true);">Yes</label>
  <label><input type="radio" id="isNotValue" name="catValue" checked value="false" onclick="radioClicked(false);">No</label>
  <p>Insert appropriate measurement for category "${path.title}":<br>
  <input type="text" id="valueName" value="" disabled="true">
  <p>Insert an unit for this measurement:<br>
  <input type="text" id="valueUnit" value="" disabled="true">
</c:if>

<c:if test="${value==null}">
<p><input id="btnAdd" type="button" value="Add" onclick="saveValue(${path.id}, $('isValue').checked, $('valueName').value, $('valueUnit').value);">
</c:if>
<c:if test="${value!=null}">
<p><input id="btnAdd" type="button" value="Save" onclick="saveValue(${path.id}, $('isValue').checked, $('valueName').value, $('valueUnit').value);">
</c:if>

