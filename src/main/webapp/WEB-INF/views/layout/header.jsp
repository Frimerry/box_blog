<!DOCTYPE html>
<html>
<head>
<title>- SpringBox -</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!-- isAuthenticated() 권한에 관계없이 로그인 인증 받은 경우 -->
<!-- 표현식이 지정한 권한에 맞을때만 출력 -->
<sec:authorize access="isAuthenticated()">
	<!-- principal : 현재 유저 오브젝트에 직접 접근 허용 -->
	<sec:authentication property="principal" var="principal" />
</sec:authorize>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>
<link rel="stylesheet" href="/css/design.css">
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
</head>
<body>
	<nav class="navbar navbar-expand-md" style="background-color: #000000;">
		<a class="navbar-brand" href="/">Spring BOX</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#collapsibleNavbar">
			<span class="navbar-toggler-icon">
			</span>
		</button>
		<ul class="navbar-nav">
		 	<li class="nav-item"><a class="nav-link category" href="/java">Java</a>
			</li>
			<li class="nav-item"><a class="nav-link category" href="/sql">SQL</a>
			</li>
		</ul>
		<div class="collapse navbar-collapse" id="collapsibleNavbar">
			<c:choose>
				<c:when test="${empty principal}">	<!-- line12 var의 principal-->
					<ul class="navbar-nav">
						<li class="nav-item"><a class="nav-link" href="/auth/loginForm">로그인</a>
						</li>
						<li class="nav-item"><a class="nav-link" href="/auth/joinForm">회원가입</a>
						</li>
					</ul>
				</c:when>
			<c:otherwise>
				<ul class="navbar-nav">
					<li class="nav-item"><a class="nav-link nav-menu" href="/board/Form">글쓰기</a>
					</li>
					<li class="nav-item"><a class="nav-link nav-menu" href="/user/updateForm">회원정보</a>
					</li>
					<li class="nav-item"><a class="nav-link nav-menu" href="/logout">로그아웃</a>
					</li>
				</ul>
			</c:otherwise>
		</c:choose>
	</div>
</nav>

