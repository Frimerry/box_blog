<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<div class="container">

	<div>
		<br />
		<br /> <input type="hidden" value="${board.id }">
	</div>
	<table class="table table-bordered align-middle">
		<tbody>
			<tr>
				<th class="table-light" scope="row" width="16%">제목</th>
				<td colspan="4"><b>${board.title }</b></td>
			</tr>
			<tr>
				<th class="table-light" scope="row" width="16%">카테고리</th>
				<td width="34%">${board.category }</td>
				
				<th class="table-light" scope="row" width="16%">작성자</th>
				<td width="34%">
					<c:choose>
						<c:when test="${empty board.users.username}">
							알 수 없음
						</c:when>
						<c:otherwise>
							${board.users.username}
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<th class="table-light" scope="row" width="16%">작성일시</th>
				<td width="34%"><fmt:formatDate value="${board.createDate}" pattern="YYYY-MM-dd HH:mm" /></td>
				
				<th class="table-light" scope="row" width="16%">조회수</th>
				<td width="34%">${board.count }</td>
			</tr>
			<tr>
				<td colspan="4">${board.content }</td>
			</tr>
		</tbody>
	</table>

	<div class="d-flex justify-content-end">
		<button class="btn btn-outline-secondary btn-sm detail-btn"
			onclick="history.back()">돌아가기</button>
		<!-- 작성자 id == 로그인 id -->
		<!-- 삭제, 수정버튼은 로그인한 작성자 본인에게만 생성 -->
		<c:if test="${board.users.id == principal.user.id}">
			<button id="btn-delete" class="btn btn-outline-danger btn-sm detail-btn">삭제</button>
			<a href="/board/${board.id}/updateForm" class="btn btn-outline-warning btn-sm detail-btn">수정</a>
		</c:if>
	</div>
	<br>

	<!-- 댓글 영역 -->

	<!-- 댓글 목록 영역 -->
	<div class="card text-dark bg-light">
		<div class="card-header">댓글 목록</div>
		<ul id="reply-box" class="list-group">
			<c:forEach var="reply" items="${board.replys}">
				<li id="reply-${reply.id}" class="list-group-item">
					<div>
						<div class="d-flex justify-content-left">
							<c:choose>
							<c:when test="${empty reply.users.username}">
								<b>작성자 : 알 수 없음</b>
							</c:when>
							<c:otherwise>
								<b>${reply.users.username}</b>
							</c:otherwise>
							</c:choose>
							<div id="replytime" style="color: #828282;">
								<fmt:formatDate value="${reply.createDate}"
									pattern="YYYY-MM-dd HH:mm" />
							</div>
						</div>
						<div class="d-flex float-right">
							<button onClick="index.replyDelete(${board.id}, ${reply.id})"
								class="btn btn-outline-danger btn-sm detail-btn">삭제</button>
						</div>
					</div>
					<div class="reply-content">${reply.content}</div>
				</li>
			</c:forEach>
		</ul>
	</div>
	<br />
	<!-- 댓글 작성 영역 -->
	<div class="card text-dark bg-light">
		<form>
			<input type="hidden" id="userId" value="${principal.user.id}" /> <input
				type="hidden" id="boardId" value="${board.id}" />
			<div class="card-body">
				<textarea id="reply-content" class="form-control" rows="2"></textarea>
			</div>
			<div class="card-footer">
				<div class="d-flex justify-content-end">
					<button type="button" id="btn-reply-save"
						class="button-black">댓글 등록</button>
				</div>
			</div>
		</form>
	</div>
	<br>
</div>
<hr />
<script type="text/javascript" src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>