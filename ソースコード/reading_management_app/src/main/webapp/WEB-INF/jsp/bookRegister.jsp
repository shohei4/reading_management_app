<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="jp">
	<%-- ページのタイトルを設定する --%>
<%
	pageContext.setAttribute("title", "登録本一覧", PageContext.PAGE_SCOPE);
%>
<%@ include file="/WEB-INF/jsp/include/head.jsp"%>
<%@ include file="/WEB-INF/jsp/include/css.jsp"%>
<%@ include file="/WEB-INF/jsp/include/js.jsp"%>
<title>本情報登録</title>
<body>
	<div class="container">
		<div class="row">
			<div class="card">
				<div class="card-header">本情報登録</div>
				<div class="card-body">
					<form action="/reading_management_app/BookRegister" method="post">
					<label for="title">題名</label>
					<p><input type="text" name="title" id="title"
					class="form-control <c:if test="${errors.title!=null }">is-invalid</c:if>" 
					value="${bookItem.title }"></p>
					<span class="text-danger">${errors.title }</span>
					<br>
					<label for="authorName">著者名</label>
					<p><input type="text" name="authorName" id="authorName"
					class="form-control <c:if test="${errors.authorName!=null }">is-invalid</c:if>"
					value="${bookItem.authorName }"></p>
					<span class="text-danger">${errors.authorName }</span>
					<br>
					<label for="thoughts">感想</label>
					<textarea id="thoughts" class="form-control <c:if test="${errors.thoughts != null }">is-invalid</c:if>"
							name="thoughts" rows="3" cols="60">${bookItem.thoughts}</textarea>
					<span class="text-danger">${errors.thoughts }</span>
					<br>
					<label for="memo">メモ</label>
						<textarea id="memo" class="form-control <c:if test="${errors.memo != null }">is-invalid</c:if>"
							name="memo" rows="3" cols="60">${bookItem.memo}</textarea>
						<span class="text-danger">${errors.memo }</span>
					<br>
					<button type="submit" class="btn btn-outline-success">登録</button>
					<input type="hidden" value="${bookItem.id }" name="id"/>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>