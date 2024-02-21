<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="w-50">
	<div class="d-flex justify-content-center m-4">
		<h1>본인 확인</h1>
	</div>
	
	<div class="d-flex justify-content-center">
		<small class="text-info">*본인확인을 위해 다시한번 로그인 해 주세요.</small>
	</div>
	<div class="d-flex justify-content-center mt-3">
		<div id="login-box">
			<form id="identityForm" method="POST", action="/user/identity-verification">
				<%-- 아이디 입력 --%>
				<div>
					<input type="text" name=loginId class="form-control" placeholder="아이디를 입력하세요.">
				</div>
				
				<%-- 비밀번호 입력 --%>
				<div class="mt-4">
					<input type="password" name="password" class="form-control" placeholder="비밀번호를 입력하세요.">
				</div>
				
				<%-- 뒤로가기, 본인확인 버튼 --%>
				<div class="mt-5 d-flex justify-content-between">
					<a href="/profile?userId=${userId}" class="btn btn-secondary form-control col-5">뒤로</a>
					<button type="submit" id="checkBtn" class="btn btn-primary form-control col-5">정보수정</button>
				</div>
			</form>
		</div>
	</div>
</div>

<script>
	$(document).ready(function() {
		// 정보수정 form submit 이벤트
		$('#identityForm').on('submit', function(e) {
			e.preventDefault();
			
			// 유효성 검사(null)
			let loginId = $('input[name=loginId]').val().trim();
			let password = $('input[name=password]').val();
			if (!loginId || !password) {
				alert("아이디와 비밀번호를 입력하세요.");
				return false;
			}
			
			// ajax - 본인인증
			let form_data = $(this).serialize();
			let url = $(this).attr("action");
			$.post(url, form_data)
			.done(function(data) {
				if (data.code == 200) {
					// 본인인증 성공
					location.href="/user/update-view";
				} else if (data.code == 300){
					// 로그인 정보가 잘못됨
					alert(data.error_message);
					$('input').val("");
				} else if (data.code == 400) {
					// 세션 만료
					alert(data.error_message);
					location.href="/user/sign-in-view";
				} else {
					alert(data.error_message);
				}
			});// ajax 끝
		}); //submit 이벤트 끝 
	}); // 레디이벤트 끝
</script>