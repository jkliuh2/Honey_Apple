<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="w-50">
	<div class="d-flex justify-content-center m-4">
		<h1>로그인</h1>
	</div>
	
	<div class="d-flex justify-content-center">
		<div id="login-box">
			<%-- 아이디 입력 --%>
			<div>
				<input type="text" id="loginId" class="form-control" placeholder="아이디를 입력하세요.">
			</div>
			
			<%-- 비밀번호 입력 --%>
			<div class="mt-4">
				<input type="password" id="password" class="form-control" placeholder="비밀번호를 입력하세요.">
			</div>
			
			<%-- 로그인 버튼 --%>
			<div class="mt-5">
				<button type="button" id="loginBtn" class="btn btn-primary form-control">로그인</button>
			</div>
			
			<%-- 회원가입 버튼 --%>
			<div class="mt-3">
				<a href="/user/sign-up-view" class="btn btn-secondary form-control">회원가입</a>
			</div>
			<hr class="mt-3">
			
			<%-- 카카오 로그인 --%>
			<div class="mt-3">
				<a href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=e69c8cb6c6982e89cceb6b10f29ea504&redirect_uri=http%3A%2F%2Flocalhost%2Foauth" type="button" class="btn btn-warning form-control">카카오 로그인</a>
			</div>
			
			<%-- 구글 로그인 --%>
			<div class="mt-3">
				<button type="button" class="btn btn-info form-control">구글 로그인</button>
			</div>
		</div>
	</div>
</div>

<script>
	$(document).ready(function() {
		// 로그인 이벤트
		$('#loginBtn').on('click', function() {
			//alert("로그인");
			
			// validation check
			let loginId = $('#loginId').val().trim();
			let password = $('#password').val();
			
			if (!loginId) {
				alert("아이디를 입력하세요.");
				$('#loginId').focus();
				return;
			}
			if (!password) {
				alert("비밀번호를 입력하세요.");
				$('#password').focus();
				return;
			}
			
			// ajax - 로그인
			$.ajax({
				type:"POST"
				, url:"/user/sign-in"
				, data:{"loginId":loginId, "password":password}
				, success:function(data) {
					if (data.code == 200) {
						// 로그인 성공
						alert(data.login_message);
						location.href="/honey-apple";
					} else if (data.code == 500) {
						// 로그인 실패 (아이디 존재 or 아예 없음)
						alert("로그인 실패 : " + data.error_message);
						$('#password').val("");
					}
				}
				, error:function(request, status, error) {
					alert("로그인에 실패했습니다. 관리자에게 문의해주세요.");
				}
			});
		});
	}); //레디이벤트 끝
</script>