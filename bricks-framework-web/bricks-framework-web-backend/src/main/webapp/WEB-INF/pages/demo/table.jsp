<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>	
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<table class="table table-bordered table-hover">
	<thead>
		<tr>
			<th>名称</th>
			<th>城市</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>名称1</td>
			<td>城市1</td>
		</tr>
		<tr>
			<td>名称2</td>
			<td>城市2</td>
		</tr>
	</tbody>
</table>

<form:form id="test">
	<div class="row">
		<div class="col-md-6 text-right">
			<input type="text" name="testInput" />
		</div>
	    <div class="col-md-6">
			<input type="submit" value="提交" />
		</div>
	</div>
</form:form>