<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>
<div class="container col-md-6">
	<div class="member-title">
		<h3>회원정보수정</h3>
	</div>
	<form class="member_update" name="updateform">
		<input type="hidden" id="id" value="${principal.user.id }"/>
		<input type="hidden" id="oauth" name="oauth" value="${principal.user.oauth }"/>
		<div class="form-group">
			<label for="userid">ID</label>
			<input type="text" value="${principal.user.userid}" class="form-control" placeholder="Enter ID" id="userid" readonly>
		</div>
		<div class="form-group">
			<label for="username">Username</label>
			<input type="text" value="${principal.user.username }" class="form-control" 
			placeholder="Enter Username" id="username">
		</div>
		
		<c:if test="${empty principal.user.oauth}">
			<div class="form-group">
			<label for="password">Password</label>
			<input type="password" class="form-control" placeholder="Enter password" id="password">
			</div>
		</c:if>
		
		<div class="form-group">
			<label for="phone">Phone</label>
			<input type="text" value="${principal.user.phone }" class="form-control" placeholder="Enter phone number" id="phone">
		</div>
		
		<c:choose>
			<c:when test="${not empty principal.user.oauth}">
				<div class="form-group">
					<label for="email">Email</label>
					<input type="email" value="${principal.user.email }" class="form-control" 
					placeholder="Enter email" id="email" name="email" readonly>
				</div>
			</c:when>
			<c:otherwise>
				<div class="form-group">
					<label for="email">Email</label>
					<input type="email" value="${principal.user.email }" class="form-control" 
					placeholder="Enter email" id="email" name="email">
				</div>
		</c:otherwise>
		</c:choose>
		<div class="form-group">                   
			<input class="form-control" style="width: 40%; display: inline;" placeholder="우편번호" value="${principal.user.zipcode}" name="zipcode" id="zipcode" type="text" readonly="readonly" >
			    <button type="button" class="button-zipcode" onclick="execPostCode();"><i class="fa fa-search"></i> 우편번호 찾기</button>                               
		</div>
		<div class="form-group">
		    <input class="form-control" style="top: 5px;" placeholder="도로명 주소" value="${principal.user.address1}" name="address1" id="address1" type="text" readonly="readonly" />
		</div>
		<div class="form-group">
		    <input class="form-control" placeholder="상세주소" value="${principal.user.address2}" name="address2" id="address2" type="text"/>
		</div>
		
		
	</form>
	<div class="d-flex justify-content-center member-update-button">
		<button id="btn-update" class="button-black">회원수정완료</button>
		<button id="btn-userdelete" class="button-black">회원탈퇴</button>
	</div>
	
	
</div>
<br/>
<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp" %>