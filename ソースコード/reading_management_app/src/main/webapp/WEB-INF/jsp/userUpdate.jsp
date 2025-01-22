<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="jp">
<%-- ページのタイトルを設定する --%>
<%
	pageContext.setAttribute("title", "会員情報更新", PageContext.PAGE_SCOPE);
%>
<%@ include file="/WEB-INF/jsp/include/head.jsp" %>
<body>
<div class="container-md">
	<div class="row">
		<div class="col-md-3"></div>
		<div class="col-md-6">
			<div class="card my-3">
				<div class="card-header">会員情報更新</div>
				<div class="card-body">
					<form action="/reading_management_app/UserUpdate" method="post">
						<div class="form-group">
							<label>Emailアドレス</label>
							<input type="text" 
									name="email" id="email"
									class="form-control<c:if test="${errors.email!=null}"> is-invalid</c:if>"
									value="<c:out value="${user.email}"/>">
							<span class="text-danger">${errors.email }</span>
						</div>
						<div class="form-group">
							<label>パスワード</label>
							<input type="password" 
									name="password" id="email"
									class="form-control<c:if test="${errors.password!=null}"> is-invalid</c:if>"
									value="<c:out value="${user.password}"/>">
							<span class="text-danger">${errors.password }</span>
						</div>
						<div class="form-group">
							<label>ニックネーム</label>
							<input type="text"
									name="name" id="name"
									class="form-control<c:if test="${errors.name!=null}"> is-invalid</c:if>"
									value="<c:out value="${user.name}"/>">
							<span class="text-danger">${errors.name }</span>
						</div>
						<button type="submit" class="btn btn-outline-success">更新</button>
					</form>
				</div>
			</div>
		</div>
		<div class="col-md-3"></div>
	</div>
</div>
</body>
</html>