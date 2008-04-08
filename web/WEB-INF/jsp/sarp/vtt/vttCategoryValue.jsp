<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<p>Category "<b>${value.catRef.category.name}</b>"</p>
<p>Is this (${value.catRef.category.name}) a value?<br>

<c:if test="${value.value}">
  <label><input type="radio" id="isValue" name="catValue" checked value="true" onclick="radioClicked(true);">Yes</label>
  <label><input type="radio" id="isNotValue" name="catValue" value="false" onclick="radioClicked(false);">No</label>
  <p>Insert appropriate measurement for category "${value.catRef.category.name}":<br>
  <input type="text" id="valueName" value="${value.name}">
  <p>Insert an unit for this measurement:<br>
  <input type="text" id="valueUnit" value="${value.criterion}">
</c:if>
<c:if test="${!value.value}">
  <label><input type="radio" id="isValue" name="catValue" value="true" onclick="radioClicked(true);">Yes</label>
  <label><input type="radio" id="isNotValue" name="catValue" checked value="false" onclick="radioClicked(false);">No</label>
  <p>Insert appropriate measurement for category "${value.catRef.category.name}":<br>
  <input type="text" id="valueName" value="" disabled="true">
  <p>Insert an unit for this measurement:<br>
  <input type="text" id="valueUnit" value="" disabled="true">
</c:if>

<c:if test="${value.name==null}">
<p><input id="btnAdd" type="button" value="Add" onclick="saveValue(${value.id}, $('isValue').checked, $('valueName').value, $('valueUnit').value);">
</c:if>
<c:if test="${value.name!=null}">
<p><input id="btnAdd" type="button" value="Save" onclick="saveValue(${value.id}, $('isValue').checked, $('valueName').value, $('valueUnit').value);">
</c:if>

