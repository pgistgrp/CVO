<%@ taglib uri="http://www.pgist.org/pgtaglib" prefix="pg" %>
<div>Category "<b>${value.catRef.category.name}</b>"</div>
<p>Is this (${value.catRef.category.name}) a value?<br>
<label><input type="radio" id="isValue" name="catValue" value="true">Yes</label>
<label><input type="radio" id="isNotValue" name="catValue" value="false">No</label>

<p>Insert appropriate measurement for category "${value.catRef.category.name}":<br>
<input type="text" id="name" value="${value.name}">

<p>Insert an unit for this measurement:<br>
<input type="text" id="unit" value="${value.criterion}">

<p><input type="button" value="Add" onclick="">
