<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="w-50">
	<div class="d-flex justify-content-center m-4">
		<h1>회원가입</h1>
	</div>
	
	<%-- 아이디 영역 --%>
	<div class="height-70-box">
		<%-- 아이디 input, 중복확인 버튼 --%>
		<div class="d-flex justify-content-between">
			<input type="text" id="loginId" class="form-control col-8" placeholder="아이디를 입력하세요.">
			<button type="button" id="loginIdCheckBtn" class="btn btn-info col-2">중복확인</button>
		</div>
		<%-- 아이디 중복확인 메세지 --%>
		<div class="mt-2">
			<small class="text-danger">중복된 아이디입니다.</small>
		</div>
	</div>
	
	<%-- 닉네임 영역 --%>
	<div class="height-70-box">
		<%-- 닉네임 input, 중복확인 버튼 --%>
		<div class="d-flex justify-content-between">
			<input type="text" id="nickname" class="form-control col-8" placeholder="닉네임을 입력하세요.">
			<button type="button" id="nicknameCheckBtn" class="btn btn-info col-2">중복확인</button>
		</div>
		<%-- 닉네임 중복확인 메세지 --%>
		<div class="mt-2">
			<small class="text-danger">중복된 아이디입니다.</small>
		</div>
	</div>
	
	<%-- 비밀번호 영역 --%>
	<div class="height-70-box">
		<input type="password" id="password" class="form-control col-8" placeholder="비밀번호">
		<%-- 비밀번호 메시지 --%>
		<div class="mt-2">
			<small class="text-danger">비밀번호가 너무 짧습니다.</small>
		</div>
	</div>
	
	<%-- 비밀번호 확인 영역 --%>
	<div class="height-70-box">
		<input type="password" id="passwordCheck" class="form-control col-8" placeholder="비밀번호 확인">
		<%-- 비밀번호 확인 메시지 --%>
		<div class="mt-2">
			<small class="text-danger">비밀번호가 일치하지 않습니다.</small>
		</div>
	</div>
	
	<%-- 이메일 영역 --%>
	<div class="height-70-box">
		<div class="d-flex">
			<input type="text" id="emailId" class="form-control col-4" placeholder="이메일을 입력하세요.">
			<div class="mx-1 d-flex align-items-center">
				@
			</div>
			<select id="emailDomain" class="col-4 form-control">
				<option selected>naver.com</option>
				<option>gmail.com</option>
				<option>nate.com</option>
				<option>kakao.com</option>
				<option>--직접 입력--</option>
			</select>
		</div>
	</div>
	
	<%-- submit 버튼 --%>
	<div class="d-flex justify-content-center">
		<button type="button" id="signUpBtn" class="btn btn-primary form-control col-6">회원가입</button>
	</div>
</div>

<script>
	$(document).ready(function() {
		
		// 회원가입
		$('#signUpBtn').on('click', function() {
			//alert("회원가입");
			
			// validation check
			let loginId = $('#loginId').val().trim();
			let nickname = $('#nickname').val().trim();
			let password = $('#password').val();
			let passwordCheck = $('#passwordCheck').val();
			let email = $('#emailId').val().trim() + "@" + $('#emailDomain').val();
			
			if (!loginId) {
				alert("아이디를 입력하세요.");
				$('#loginId').focus();
				return;
			}
			if (!nickname) {
				alert("닉네임을 입력하세요.");
				$('#nickname').focus();
				return;
			}
			if (!password) {
				alert("비밀번호를 입력하세요.");
				$('#password').focus();
				return;
			}
			if (!passwordCheck) {
				alert("비밀번호 확인을 입력하세요.");
				$('#passwordCheck').focus();
				return;
			}
			if (password != passwordCheck) {
				alert("비밀번호가 일치하지 않습니다.");
				$('#password').val("");
				$('#passwordCheck').val("");
				$('#password').focus();
				return;
			}
			if (!email) {
				alert("이메일을 입력하세요.");
				$('#email').focus();
				return;
			}
			
			// AJAX - INSERT
			$.ajax({
				type:"POST"
				, url:"/user/sign-up"
				, data:{"loginId":loginId, "nickname":nickname, 
					"password":password, "email":email}
				, success:function(data) {
					if (data.code == 200) {
						alert("환영합니다!");
						
					} else {
						alert(data.error_message);
					}
				}
				, error:function(request, status, error) {
					alert("회원가입에 실패했습니다. 관리자에게 문의해주세요.");
				}
			});// ajax 끝
		}); // 회원가입 끝
	}); // 레디이벤트 끝
</script>