<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="jp">
<%-- ページのタイトルを設定する --%>
<%
pageContext.setAttribute("title", "共有画面", PageContext.PAGE_SCOPE);
%>
<%@ include file="/WEB-INF/jsp/include/head.jsp"%>
<%@ include file="/WEB-INF/jsp/include/css.jsp"%>
<%@ include file="/WEB-INF/jsp/include/js.jsp"%>

<body>
	<div class="containter">
		<%@ include file="/WEB-INF/jsp/include/navbar2.jsp"%>
		<div class="row">
			<div class="col-1"></div>
			<div class="col">
				<c:forEach var="item" items="${items}">
					<div class="card mb-3" style="max-width: 720px;">
						<div class="card-body">
							<h5 class="card-title">
								<c:out value="${item.registrationDate }"></c:out>
								<c:out value="${item.userName }"></c:out>
							</h5>
							<div class="card-body">
								<div class="card-title">
									<label>題名:</label>
									<c:out value="${item.title }"></c:out>
									<br>
									<label>著者名:</label>
									<c:out value="${item.authorName }"></c:out>
								</div>
								<p class="card-text">
									<label>感想</label>
									<p><c:out value="${item.thoughts }"/></p>
									<form action="/reading_management_app/CommentRegister" action="get">
										<button type="submit" class="btn btn-outline-primary">コメント</button>
										<input type="hidden" name="bookId" value="${item.id }"/>
									</form>
							</div>
						</div>
					</div>	
				</c:forEach>
			</div>
		</div>
	</div>
</body>
</html>