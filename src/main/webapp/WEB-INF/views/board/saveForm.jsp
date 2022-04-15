<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>
<div class="container">
	<form>
		<h1>글 작성하기</h1><br/>
		<div class="form-group">
			<label for="title"></label>
			<input type="text" class="form-control" placeholder="Enter title" id="title">
		</div>
		<div class="form-group">
			<select class="form-select" id="category" aria-label="Default select example">
			    <option value="선택안함">카테고리 선택안함</option>
			    <option value="Java">Java</option>
			    <option value="SQL">SQL</option>
			</select>
		</div>
		<div class="form-group">
			<label for="content"></label>
			<textarea class="form-control summernote" rows="5" id="content"></textarea>
		</div>
	</form>
	<div class="save-button">
	<button id="btn-save" class="button-black align-right float-right">작성하기</button>
	</div>

</div>
<br/>
<script>
	$('.summernote').summernote({
		tabsize:2,
		height:300
	});
</script>
<script type="text/javascript" src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp" %>