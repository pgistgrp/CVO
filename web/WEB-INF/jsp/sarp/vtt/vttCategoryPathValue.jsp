<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<p>Indicator creation for Category Path "<b>${path.title}</b>"</p>

<label><input type="radio" id="selectTagRadio" name="selectTagRadio" value="true" <c:if test="${value.tag}">checked</c:if>>Select a tag</label>
<select id="valueNameTag" style="min-width:50px;" onclick="$('selectTagRadio').click();">
  <option value=""></option>
  <c:forEach var="tag" items="${tags}">
    <option value="${tag}" <c:if test="${tag==value.name}">selected</c:if>>${tag}</option>
  </c:forEach>
</select>
, or<br>
<label><input type="radio" id="selectTagRadio1" name="selectTagRadio" value="false" onfocus="$('valueName').focus();" <c:if test="${!value.tag}">checked</c:if>>write my own: </label>
<input type="text" id="valueName" value="<c:if test="${!value.tag}">${value.name}</c:if>" onclick="$('selectTagRadio1').click();">
<p>Suggested Unit of Messurement (e.g. for rainfall, cm per year):<br>
<input type="text" id="valueUnit" value="${value.criterion}">

<p><input id="btnAdd" type="button" value="<c:if test="${value.id==null}">Add Indicator</c:if><c:if test="${value.id!=null}">Save</c:if>" onclick="saveValue(${path.id}, $('selectTagRadio').checked ? $('valueNameTag').options[$('valueNameTag').selectedIndex].value : $('valueName').value, $('valueUnit').value, $('selectTagRadio').checked ? 'true' : 'false');">

