<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<div class="container">
	<form>
	<h1>글 수정하기</h1>
		<input type="hidden" id="id" value="${board.id}"/>
		<div class="form-group">
			<input value="${board.title}" type="text" class="form-control"
			 placeholder="Enter title" id="title">
		</div>
		<div class="form-group">
			<select class="form-select" id="category" aria-label="Default select example">
			    <option value="선택안함">카테고리 선택안함</option>
			    <option value="Java">Java</option>
			    <option value="SQL">SQL</option>
			</select>
		</div>
		<div class="form-group">
			<textarea class="form-control summernote" rows="5" id="content">${board.content}</textarea>
		</div>
	</form>
	<button id="btn-update" class="button-black float-right">수정</button>
	<br/><br/>
</div>
<br/>
<script>
      $('.summernote').summernote({
        tabsize: 2,
        height: 300
      });
</script>
<script type="text/javascript" src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>

