<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="jp">
	<%-- ページのタイトルを設定する --%>
<%
	pageContext.setAttribute("title", "返信投稿画面", PageContext.PAGE_SCOPE);
%>
<%@ include file="/WEB-INF/jsp/include/head.jsp"%>
<%@ include file="/WEB-INF/jsp/include/css.jsp"%>
<%@ include file="/WEB-INF/jsp/include/js.jsp"%>
<title>返信投稿画面</title>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="card">
				<div class="card-header">返信投稿</div>
				<div class="card-body">
				<label>コメント</label>
				<br>
				<c:out value="${model.comment }">
				</c:out>
					<form action="/reading_management_app/ReplyRegister" method="post">
					<label for="reply">返信</label>
						<textarea class="form-control <c:if test="${errors.reply != null }">is-invalid</c:if>"
							id="replly" name="reply" rows="3" cols="60">${replyModel.reply}</textarea>
						<span class="text-danger">${errors.reply }</span>
						<br>
						<button type="submit" class="btn btn-outline-success">投稿</button>
						<input type="hidden" name="commentId" value="${model.id }"/>
					</form>					
				</div>
			</div>
		</div>
	</div>
</body>
</html>