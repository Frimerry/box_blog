<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>- Java -</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<div id="wrap">
<%@ include file="layout/header.jsp" %>
	<div class="container">
		<img src="image/index.jpg" width="100%">
	<c:forEach var="board" items="${boards.content }">
		<div class="card m-2">
			<div class="card-body">
				<div class="d-flex justify-content-between">
					<div>카테고리 <b>${board.category }</b></div>
					<div class="d-flex flex-column" id="index-board-date" style="color: #828282;">
						<div><fmt:formatDate value="${board.createDate}" pattern="YYYY-MM-dd"/></div><div>조회수&nbsp; ${board.count }</div>
					</div>
				</div>
					
				<div class="index-title">
					<h4 class="card-title"> ${board.title }</h4>
				</div>
					<div class="float-right">
						<c:choose>
							<c:when test="${empty board.users.username}">
								작성자 : 알 수 없음
							</c:when>
							<c:otherwise>
								작성자 : ${board.users.username} 
							</c:otherwise>
						</c:choose>
					</div>
				<a href="/board/${board.id}" class="button-black">상세보기</a>
			</div>
		</div>
	</c:forEach>
	<!-- 이전/다음 페이지이동 버튼 표시 영역 -->
		<ul class="pagination justify-content-center">
		<c:choose>
			<c:when test="${boards.first}">
				<li class="page-item disabled">
				<a class="page-link" href="?page=${boards.number-1}">Previous</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item">
				<a class="page-link" href="?page=${boards.number-1}">Previous</a></li>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${boards.last}">
				<li class="page-item disabled">
				<a class="page-link" href="?page=${boards.number+1}">Next</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item">
				<a class="page-link" href="?page=${boards.number+1}">Next</a></li>
			</c:otherwise>
		</c:choose>
		</ul>
	</div>
<br/>
<footer><%@ include file="layout/footer.jsp" %></footer>
</div>
</body>
</html>