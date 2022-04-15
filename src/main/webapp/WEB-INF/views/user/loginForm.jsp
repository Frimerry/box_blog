<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>
<div class="container col-md-6">

	<form action="/auth/loginProc" method="POST" name="loginform">
	<div class="member-title">
		<h1>로그인</h1>
	</div>
		<div class="form-group">
			<label for="userid">ID</label>
			<input type="text" class="form-control" placeholder="Enter your ID" id="userid" name="userid">
		</div>
		<div class="form-group">
			<label for="password">Password</label>
			<input type="password" class="form-control" placeholder="Enter password" id="password" name="password">
		</div>
		<div class="form-group">
			<label class="form-check-label remember">
				<input name="remember" class="form-check-input" type="checkbox">Remember me
			</label>
		</div>
		<c:if test="${error eq 'true'}">
			<p class="alert alert-danger">${exception}</p>
		</c:if>
		<div class="login-button">
			<button type="submit" id="btn-login" class="button-black" onclick="loginCheck();">로그인</button>
			<a href="https://kauth.kakao.com/oauth/authorize?client_id=8d470bb8eca940ae1ee8dab66bebedd4&redirect_uri=http://localhost:8083/auth/kakao/callback&response_type=code">
			<img height="38px" src="/image/kakao_login_button.png"></a>
		</div>
	</form>
</div>
<!-- <script src="/js/user.js"></script> -->
<br/>
<script type="text/javascript">
function loginCheck(){
	if (document.loginform.userid.value == ""){
		alert("아이디를 입력하세요.");
		document.loginform.userid.focus();
		return false;
	} else if (document.loginform.password.value == ""){
		alert("비밀번호를 입력하세요.");
		document.loginform.password.focus();
		return false;
	} else {
		return true;
	}
}</script>
<%@ include file="../layout/footer.jsp" %>