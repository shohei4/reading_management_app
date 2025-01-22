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
<title>登録本一覧</title>
<body>
	<div class="containter">
		<%@ include file="/WEB-INF/jsp/include/navbar.jsp"%>
		<div class="row">
			<div class="col-1"></div>
			<div class="col">
				<c:forEach var="item" items="${items}">
					<div class="card mb-3" style="max-width: 720px;">
						<div class="row g-0">
							<div class="col-md-2" style="background-color: <c:if test="${item.isFinished==1 }">#339999;</c:if> #CC3300;"></div>
							<div class="col-md-6">
								<div class="card-body">
								<c:out value="${item.registrationDate }"></c:out>
									<div class="card-title">
										<label>題名：</label>
										<c:out value="${item.title }"></c:out>
										<br>
										<label>著者名：</label>
										<c:out value="${item.authorName }"></c:out>
										<form action="BookUpdate" method="post">
											<c:choose>
												<c:when test="${item.isFinished != 0 }">
													<button type="submit" class="btn btn-info"
														name="isFinished" value="0">読了</button>
													<input type="hidden" value="${item.id }" name="id" />
												</c:when>
												<c:otherwise>
													<button type="submit" class="btn btn-outline-info"
														name="isFinished" value="1">読了</button>
													<input type="hidden" value="${item.id }" name="id" />
												</c:otherwise>
											</c:choose>											
										</form>	
									</div>
									<p class="card-text">
									<label>感想</label>
									<p>
										<c:out value="${item.thoughts }"></c:out>
									</p>
									<label>メモ</label>
									<p>
										<c:out value="${item.memo }"></c:out>
									</p>
									<div class="row">
										<div class="col">
											<form action="/reading_management_app/BookUpdate"
												method="post">
												<button type="submit" class="btn btn-outline-danger"
													name="isDeleted" value="1">削除</button>
												<input type="hidden" value="${item.id }" name="id" />
											</form>
										</div>
										<div class="col">
											<form action="/reading_management_app/BookEdit" method="get">
												<button type="submit" class="btn btn-outline-secondary">編集</button>
												<input type="hidden" value="${item.id }" name="id" />
											</form>
										</div>
										<div class="col">
											<form action="/reading_management_app/BookUpdate"
												method="post">
												<c:choose>
													<c:when test="${item.isShared != 0 }">
														<button type="submit" class="btn btn-success"
														name="isShared" value="0">共有</button>
														<input type="hidden" value="${item.id }" name="id" />
													</c:when>
													<c:otherwise>
														<button type="submit" class="btn btn-outline-success"
														name="isShared" value="1">共有</button>
														<input type="hidden" value="${item.id }" name="id" />
													</c:otherwise>
												</c:choose>
											</form>
										</div>
									</div>

								</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</body>
</html>