function execPostCode() {
         new daum.Postcode({
             oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
  
                // 도로명 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var fullRoadAddr = data.roadAddress; // 도로명 주소 변수
                var extraRoadAddr = ''; // 도로명 조합형 주소 변수
 
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraRoadAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                   extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 도로명, 지번 조합형 주소가 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraRoadAddr !== ''){
                    extraRoadAddr = ' (' + extraRoadAddr + ')';
                }
                // 도로명, 지번 주소의 유무에 따라 해당 조합형 주소를 추가한다.
                if(fullRoadAddr !== ''){
                    fullRoadAddr += extraRoadAddr;
                }
 
                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                console.log(data.zonecode);
                console.log(fullRoadAddr);
                
                
                $("[name=zipcode]").val(data.zonecode);
                $("[name=address1]").val(fullRoadAddr);
                
                /* document.getElementById('signUpUserPostNo').value = data.zonecode; //5자리 새우편번호 사용
                document.getElementById('signUpUserCompanyAddress').value = fullRoadAddr;
                document.getElementById('signUpUserCompanyAddressDetail').value = data.jibunAddress; */
            }
         }).open();
     }


function checkId(){
        var userid = $('#userid').val();
        $.ajax({
            url:'/auth/idcheck', //Controller에서 인식할 주소
            type:'post', //POST 방식으로 전달
            data:{userid: userid},
            success:function(cnt){ //컨트롤러에서 넘어온 cnt값을 받는다 
                if(cnt != 1){ //cnt가 1이 아니면(=0일 경우) -> 사용 가능한 아이디 
                    $('.id_ok').css("display","inline-block"); 
                    $('.id_already').css("display", "none");
                } else { // cnt가 1일 경우 -> 이미 존재하는 아이디
                    $('.id_already').css("display","inline-block");
                    $('.id_ok').css("display", "none");
                }
            },
            error:function(){
                alert("에러입니다");
            }
        });
    };

function joinCheck() {

	if (document.joinform.userid.value == "") {
		alert("아이디를 입력해주세요.");
		document.joinform.userid.focus();
		return false;
	}

	if (document.joinform.userid.value.length < 4) {
		alert("아이디는 4글자 이상이어야 합니다.");
		document.joinform.userid.focus();
		return false;
	}
	
	if (document.joinform.username.value == "") {
		alert("이름을 입력해주세요.");
		document.joinform.username.focus();
		return false;
	}

	if (document.joinform.password.value == "") {
		alert("비밀번호를 입력해주세요.");
		document.joinform.password.focus();
		return false;
	}

	if (document.joinform.password2.value == "") {
		alert("비밀번호를 확인해주세요.");
		document.joinform.password2.focus();
		return false;
	}
	
	if (document.joinform.password.value != document.joinform.password2.value) {
		alert("비밀번호가 일치하지 않습니다.");
		document.joinform.password2.focus();
		return false;
	}
	
	if (document.joinform.email.value == "") {
		alert("메일주소를 입력해주세요.");
		document.joinform.email.focus();
		return false;
	}

	return true;
}

let index = {
	init: function() {
		$("#btn-save").on("click", () => {
			this.save();
		});
		
		$("#btn-update").on("click", () => {
			this.update();
		});
		
		$("#btn-userdelete").on("click", () => {
			this.userDelete();
		});
	},

	save: function() {
		let data={
			userid: $("#userid").val(),
			username: $("#username").val(),
			password: $("#password").val(),
			phone: $("#phone").val(),
			email: $("#email").val()
		};
		console.log(data); //자바스크립트 오브젝트
		$.ajax({ 
			type:"POST",
			url:"/auth/joinProc",
			data:JSON.stringify(data),
			contentType:"application/json; charset=utf-8",	
			dataType:"json" 
		}).done(function(resp){
			if(resp.status==500){
				alert("회원가입에 실패하였습니다.");
			}else{
				alert("회원가입이 완료되었습니다.");
				location.href="/";
			}
			
		}).fail(function(error){
			// alert(JSON.stringify(error));
		});
	},
	
	update: function() {
			let data={
			id: $("#id").val(),
			userid: $("#userid").val(),
			username: $("#username").val(),
			password: $("#password").val(),
			phone: $("#phone").val(),
			email: $("#email").val(),
			zipcode: $("#zipcode").val(),
			address1: $("#address1").val(),
			address2: $("#address2").val(),
			oauth: $("#oauth").val()
			};
			
			$.ajax({ 
				type:"PUT",
				url:"/user",
				data:JSON.stringify(data),
				contentType:"application/json; charset=utf-8",
				dataType:"json" 
			
			}).done(function(resp){
				alert("회원수정이 완료되었습니다.");
				location.href="/";
	
			}).fail(function(error){
				// alert(JSON.stringify(error));
	
			});
	},
	
	userDelete: function(){
		var id=$("#id").val();
		$.ajax({ 
			type:"DELETE",
			url:"/api/user/"+id,
			dataType:"json" 

		}).done(function(resp){
			alert("회원 탈퇴 완료되었습니다.");
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	},
}
index.init();