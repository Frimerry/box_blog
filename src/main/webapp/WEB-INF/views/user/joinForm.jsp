<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>
<div class="container col-md-6">
	<form name="joinform">
	<h1>회원가입</h1>
	<div class="form-group">
			<label for="userid">ID</label>
			<input type="text" class="form-control" placeholder="Enter ID" id="userid" required oninput = "checkId()">
			<!-- 아이디 중복확인 후 조건에 따라 메세지 출력 -->
			<span class="id_ok">사용 가능한 아이디입니다.</span>
			<span class="id_already">이미 사용중인 아이디입니다.</span>
		</div>
		<div class="form-group">
			<label for="username">Username</label>
			<input type="text" class="form-control" placeholder="Enter username" id="username" required="required">
		</div>
		<div class="form-group">
			<label for="password">Password</label>
			<input type="password" class="form-control" placeholder="Enter password" id="password" required="required">
		</div>
		<div class="form-group">
			<label for="password2">Password</label>
			<input type="password" class="form-control" placeholder="Enter password" id="password2" required="required">
		</div>
		<div class="form-group">
			<label for="phone">Phone</label>
			<input type="text" class="form-control" placeholder="Enter phone number" id="phone">
		</div>
		<div class="form-group">
			<label for="email">Email</label>
			<input type="email" class="form-control" placeholder="Enter email" id="email">
		</div>
		<div class="form-group">                   
			<input class="form-control" style="width: 40%; display: inline;" placeholder="우편번호" name="zipcode" id="zipcode" type="text" readonly="readonly" >
			    <button type="button" class="button-zipcode" onclick="execPostCode();"><i class="fa fa-search"></i> 우편번호 찾기</button>                               
		</div>
		<div class="form-group">
		    <input class="form-control" style="top: 5px;" placeholder="도로명 주소" name="address1" id="address1" type="text" readonly="readonly" />
		</div>
		<div class="form-group">
		    <input class="form-control" placeholder="상세주소" name="address2" id="address2" type="text"/>
		</div>
		
	</form>
	<button id="btn-save" class="button-black" onclick="joinCheck();">회원가입</button>
</div>
<br/>
<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp" %>

</body>
</html>