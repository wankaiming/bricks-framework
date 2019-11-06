<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<script src="${urls.getForLookupPath('/base/js/jquery/jquery-2.1.1.min.js')}"></script>

<style>
.list_table {
	font-size: 10px;
	padding: 0px;
	margin: 0px;
	border: 1px solid #eee;
	border-spacing: 0px;
}

.list_table tr td {
	padding: 0px;
	margin: 0px;
	border: 1px solid #eee;
	height: 30px;
}

.list_table tr td a {
	display: block;
}

.list_table tr.highlight {
	background-color: #ccc;
}

#isConcurrent, #addTaskBtn {
	height: 22px;
	line-height: 22px;
}
</style>
</head>
<body>
	<form:form id="addForm" method="post">
		<table class="list_table">
			<thead>
				<tr>
					<td style="width: 5%">ID</td>
					<td style="width: 5%">NAME</td>
					<td style="width: 5%">GROUP</td>
					<td style="width: 5%">状态</td>
					<td style="width: 10%">CRON表达式</td>
					<td style="width: 10%">描述</td>
					<td style="width: 5%">是否并发</td>
					<td style="width: 25%">类路径</td>
					<td style="width: 10%">SPRING ID</td>
					<td style="width: 10%">方法名</td>
					<td style="width: 10%">操作</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="job" items="${taskList}">
					<tr>
						<td>${job.id}</td>
						<td>${job.jobName}</td>
						<td>${job.jobGroup}</td>
						<td><c:choose>
								<c:when test="${job.jobStatus=='1'}">
									开启
							</c:when>
								<c:otherwise>
									停止
							</c:otherwise>
							</c:choose></td>
						<td>${job.cronExpression}</td>
						<td>${job.description}</td>
						<td><c:choose>
								<c:when test="${job.isConcurrent=='1'}">
									是
							</c:when>
								<c:otherwise>
									否
							</c:otherwise>
							</c:choose></td>
						<td>${job.beanClass}</td>
						<td>${job.springId}</td>
						<td>${job.methodName}</td>
						<td><c:choose>
								<c:when test="${job.jobStatus=='1'}">
									<a href="javascript:;"
										onclick="changeStatus('${job.id}','stop')">停止</a>&nbsp;
							</c:when>
								<c:otherwise>
									<a href="javascript:;"
										onclick="changeStatus('${job.id}','start')">开启</a>&nbsp;
							</c:otherwise>
							</c:choose> <br /> <a
							href="javascript:runTask(${job.id}, ${job.jobStatus})">立即运行</a> <br />
							<a href="javascript:updateCron(${job.id}, ${job.jobStatus})">更新CRON</a>
							<br /> <a href="javascript:delTask(${job.id}, ${job.jobStatus})">删除</a></td>
					</tr>
				</c:forEach>
				<tr>
					<td></td>
					<td><input type="text" name="jobName" id="jobName"></input></td>
					<td><input type="text" name="jobGroup" id="jobGroup"></input></td>
					<td>停止<input type="hidden" name="jobStatus" value="0"></input></td>
					<td><input type="text" name="cronExpression"
						id="cronExpression"></input></td>
					<td><input type="text" name="description" id="description"></input></td>
					<td><select name="isConcurrent" id="isConcurrent">
							<option value="0">否</option>
							<option value="1">是</option>
					</select></td>
					<td><input type="text" name="beanClass" id="beanClass"></input></td>
					<td><input type="text" name="springId" id="springId"></input></td>
					<td><input type="text" name="methodName" id="methodName"></input></td>
					<td><input type="button" id="addTaskBtn" onclick="addTask()"
						value="保存" /></td>
				</tr>
			</tbody>
		</table>
	</form:form>

	<script>
		var rows = document.getElementsByTagName('tr');
		for (var i = 0; i < rows.length; i++) {
			if (i != 0 && i != (rows.length - 1)) {
				rows[i].onmouseover = function() {
					this.className += 'highlight';
				}

				rows[i].onmouseout = function() {
					this.className = this.className.replace('highlight', '');
				}
			}

		}

		function validate() {
			if ($.trim($('#jobName').val()) == '') {
				alert('NAME不能为空！');
				$('#jobName').focus();
				return false;
			}
			if ($.trim($('#jobGroup').val()) == '') {
				alert('GROUP不能为空！');
				$('#jobGroup').focus();
				return false;
			}
			if ($.trim($('#cronExpression').val()) == '') {
				alert('CRON表达式不能为空！');
				$('#cronExpression').focus();
				return false;
			}
			if ($.trim($('#beanClass').val()) == ''
					&& $.trim($('#springId').val()) == '') {
				$('#beanClass').focus();
				alert('类路径和SPRING ID至少填写一个');
				return false;
			}
			if ($.trim($('#methodName').val()) == '') {
				$('#methodName').focus();
				alert('方法名不能为空！');
				return false;
			}
			return true;
		}
		function addTask() {
			if (validate()) {
				$.ajax({
					type : "POST",
					async : false,
					dataType : "JSON",
					cache : false,
					url : "/scheduleConfig/addTask",
					data : $("#addForm").serialize(),
					success : function(data) {
						if (data.flag) {
							location.reload();
						} else {
							alert(data.msg);
						}
					}
				});
			}
		}
		function changeStatus(id, cmd) {
			$.ajax({
				type : "POST",
				async : false,
				dataType : "JSON",
				cache : false,
				url : "/scheduleConfig/changeStatus",
				data : {
					id : id,
					cmd : cmd
				},
				success : function(data) {
					if (data.flag) {
						location.reload();
					} else {
						alert(data.msg);
					}

				}
			});
		}
		function updateCron(id, jobStatus) {
			if (jobStatus == 0) {
				var cron = prompt("输入CRON表达式！", "")
				if (cron) {
					$.ajax({
						type : "POST",
						async : false,
						dataType : "JSON",
						cache : false,
						url : "/scheduleConfig/updateCron",
						data : {
							id : id,
							cron : cron
						},
						success : function(data) {
							if (data.flag) {
								location.reload();
							} else {
								alert(data.msg);
							}

						}
					});
				}
			} else {
				alert("需要定时器为停止状态！");
			}
		}

		function runTask(id, jobStatus) {
			if (jobStatus == 1) {
				$.ajax({
					type : "POST",
					async : false,
					dataType : "JSON",
					cache : false,
					url : "/scheduleConfig/runTask",
					data : {
						id : id
					},
					success : function(data) {
						if (data.flag) {
							location.reload();
						} else {
							alert(data.msg);
						}
					}
				});
			} else {
				alert("需要定时器为开启状态！");
			}
		}

		function delTask(id, jobStatus) {
			if (jobStatus == 0) {
				$.ajax({
					type : "POST",
					async : false,
					dataType : "JSON",
					cache : false,
					url : "/scheduleConfig/delTask",
					data : {
						id : id
					},
					success : function(data) {
						if (data.flag) {
							location.reload();
						} else {
							alert(data.msg);
						}
					}
				});
			} else {
				alert("需要定时器为停止状态！");
			}
		}
	</script>

</body>
</html>