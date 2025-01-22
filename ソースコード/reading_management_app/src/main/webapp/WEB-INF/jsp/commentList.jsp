<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="jp">
<%-- ページのタイトルを設定する --%>
<%
pageContext.setAttribute("title", "コメント一覧", PageContext.PAGE_SCOPE);
%>
<%@ include file="/WEB-INF/jsp/include/head.jsp"%>
<%@ include file="/WEB-INF/jsp/include/css.jsp"%>
<%@ include file="/WEB-INF/jsp/include/js.jsp"%>
<title>コメント一覧</title>
<body>
	<div class="containter">
		<%@ include file="/WEB-INF/jsp/include/navbar2.jsp"%>
		<div class="row">
			<div class="col-1"></div>
			<div class="col">
				<c:forEach var="item" items="${commentItems}">
					<div class="card mb-3" style="max-width: 720px;">
						<div class="card-body">
							<h5 class="card-title">
								<c:out value="${item.registrationDate }"></c:out>
								<c:out value="${item.userName }"></c:out>
							</h5>
							<div class="card-body">
								<label>コメント</label> <br>
								<c:out value="${item.comment }"></c:out>
								<br>
								<div class="row">
									<div class="col">
									<form action="/reading_management_app/CommentFavoriteUpdate"
										method="post">
											<c:choose>
												<c:when test="${item.favorite != 0}">
													<button type="submit" class="btn btn-warning">いいね</button>
													<c:out value="${item.favoriteCount }"></c:out>
													<input type="hidden" value="2" name="flag">
													<input type="hidden" value="${item.id }" name="commentId">
												</c:when>
												<c:otherwise>
													<button type="submit" class="btn btn-outline-warning">いいね</button>
													<c:out value="${item.favoriteCount }"></c:out>
													<input type="hidden" value="1" name="flag">
													<input type="hidden" value="${item.id }" name="commentId">
												</c:otherwise>
											</c:choose>
									</form>
									</div>
									<c:if test="${item.userId == user.id }">
										<div class="col">
											<form action="/reading_management_app/CommentEdit"
												method="get">
												<button type="submit" class="btn btn-outline-secondary">編集</button>
												<input type="hidden" value="${item.id }" name="id" />
											</form>
										</div>
										<div class="col">
											<form action="/reading_management_app/CommentUpdate"
												method="post">
												<button type="submit" class="btn btn-outline-danger"
													value="1" name="isDeleted">削除</button>
												<input type="hidden" value="${item.id }" name="id" />
											</form>
										</div>
									</c:if>
									<div class="col">
										<form action="/reading_management_app/ReplyRegister"
											method="get">
											<button type="submit" class="btn btn-outline-info">返信</button>
											<input type="hidden" value="${item.id }" name="id" />
										</form>
									</div>
								</div>
								<c:forEach var="replyItem" items="${item.replyItems}">
									<div class="card mb-3" style="max-width: 720px;">
										<div class="card-body">
											<h5 class="card-title">
												<c:out value="${replyItem.registrationDate }"></c:out>
												<c:out value="${replyItem.userName }"></c:out>
											</h5>
											<div class="card-body">
												<label>返信</label> <br>
												<c:out value="${replyItem.reply }"></c:out>
												<br>
												<div class="row">
													<div class="col">
														<form action="/reading_management_app/ReplyFavoriteUpdate"
															method="post">
															<c:choose>
																<c:when test="${replyItem.favorite != 0}">
																	<button type="submit" class="btn btn-warning">いいね</button>
																	<c:out value="${replyItem.favoriteCount }"></c:out>
																	<input type="hidden" name="replyId"
																		value="${replyItem.id }">
																	<input type="hidden" name="flag" value="2">
																</c:when>
																<c:otherwise>
																	<button type="submit" class="btn btn-outline-warning">いいね</button>
																	<c:out value="${replyItem.favoriteCount }"></c:out>
																	<input type="hidden" name="replyId"
																		value="${replyItem.id }">
																	<input type="hidden" name="flag" value="1">
																</c:otherwise>
															</c:choose>
														</form>
													</div>
													<c:if test="${replyItem.userId == user.id }">
														<div class="col">
															<form action="/reading_management_app/ReplyEdit"
																method="get">
																<button type="submit" class="btn btn-outline-secondary">編集</button>
																<input type="hidden" value="${replyItem.id }" name="id" />
															</form>
														</div>
														<div class="col">
															<form action="/reading_management_app/ReplyUpdate"
																method="post">
																<button type="submit" class="btn btn-outline-danger"
																	value="1" name="isDeleted">削除</button>
																<input type="hidden" value="${replyItem.id }" name="id" />
															</form>
														</div>
													</c:if>
												</div>
											</div>
										</div>
									</div>
								</c:forEach>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</body>
</html>