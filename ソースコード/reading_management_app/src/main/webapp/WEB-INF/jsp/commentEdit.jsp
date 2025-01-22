<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="jp">
	<%-- ページのタイトルを設定する --%>
<%
	pageContext.setAttribute("title", "コメント編集画面", PageContext.PAGE_SCOPE);
%>
<%@ include file="/WEB-INF/jsp/include/head.jsp"%>
<%@ include file="/WEB-INF/jsp/include/css.jsp"%>
<%@ include file="/WEB-INF/jsp/include/js.jsp"%>
<title>コメント編集画面</title>
<title>Insert title here</title>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="card">
				<div class="card-header">コメント編集</div>
				<div class="card-body">
					<form action="/reading_management_app/CommentEdit" method="post">
						<label for="comment">コメント編集</label>
						<textarea class="form-control <c:if test="${errors.comment != null }">is-invalid</c:if>"
							id="comment" name="comment" rows="3" cols="60">${commentModel.comment}</textarea>
						<span class="text-danger">${errors.comment }</span>
						<br>
						<button type="submit" class="btn btn-outline-success">編集</button>
						<input type="hidden" value="${commentModel.id }" name="id"/>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>