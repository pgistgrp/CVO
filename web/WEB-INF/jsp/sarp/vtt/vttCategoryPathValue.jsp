<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<p>Indicator creation for Category Path "<b>${path.title}</b>"</p>

<label><input type="radio" id="selectTagRadio" name="selectTagRadio" value="true" checked="${value.tag}">Select a tag</label>
<select id="valueNameTag" style="min-width:50px;" onclick="$('selectTagRadio').click();">
  <option value=""></option>
  <c:forEach var="tag" items="${tags}">
    <option value="${tag}">${tag}</option>
  </c:forEach>
</select>
, or<br>
<label><input type="radio" id="selectTagRadio1" name="selectTagRadio" value="false" onfocus="$('valueName').focus();" checked="${!value.tag}">write my own: </label>
<input type="text" id="valueName" value="${value.name}" onclick="$('selectTagRadio1').click();">
<p>Suggested Unit of Messurement (e.g. for rainfall, cm per year):<br>
<input type="text" id="valueUnit" value="${value.criterion}">

<p><input id="btnAdd" type="button" value="Add Indicator" onclick="saveValue(${path.id}, $('selectTagRadio').selected ? $('valueNameTag').value : $('valueName').value, $('valueUnit').value);">

