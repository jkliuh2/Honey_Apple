<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="w-50 mt-5">
	<%-- 닉네임 --%>
	<div class="d-flex">
		<div class="col-3 d-flex justify-content-center align-items-center">
			<b>*닉네임</b>
		</div>
		<div class="col-6">
			<input type="text" name="nickname" class="form-control" value="${user.nickname}">
		</div>
		<div class="col-3">
			<button type="button" id="nicknameCheckBtn" class="btn btn-info">중복확인</button>
		</div>
	</div>
	<%-- 닉네임 중복확인 메시지 --%>
	<div class="d-flex justify-content-center mt-2">
		<small id="NowNickname" class="nicknameMessage col-6 text-success">현재 아이디입니다.</small>
		<small id="nicknameDuplTrue" class="nicknameMessage col-6 text-danger d-none">중복된 아이디입니다.</small>
		<small id="nicknameDuplFalse" class="nicknameMessage col-6 text-success d-none">사용가능한 아이디입니다.</small>
	</div>
	
	<%-- 이메일 --%>
	<div class="d-flex mt-5">
		<div class="col-3 d-flex justify-content-center align-items-center">
			<b>*이메일</b>
		</div>
		<div class="col-6">
			<input type="text" name="email" class="form-control" value="${user.email}">
		</div>
		<div class="col-3"></div>
	</div>
	
	<%-- 프로필 이미지 --%>	
	<div class="mt-5 d-flex">
		<div class="col-3 d-flex justify-content-center align-items-center">
			<b>프로필<br>이미지</b>
		</div>
		<div class="col-5 d-flex justify-content-center align-items-center">
			<img src="/static/img/blank-profile.webp" width="70" height="70" alt="프로필 이미지">
		</div>
		<div class="col-4 d-flex justify-content-end align-items-center">
			<button type="button" class="btn btn-success">프로필 변경</button>
		</div>
	</div>
	
	<%-- 비밀번호 --%>
	<div class="d-flex mt-5">
		<div class="col-3 d-flex justify-content-center align-items-center">
			<b>비밀번호</b>
		</div>
		<div class="col-9">
			<input type="password" name="password" class="form-control" placeholder="비밀번호를 수정하려면 입력하세요.">
		</div>
	</div>
	<%-- 비밀번호 확인 --%>
	<div class="d-flex mt-5">
		<div class="col-3 d-flex justify-content-center align-items-center">
			<b>비밀번호 확인</b>
		</div>
		<div class="col-9">
			<input type="password" id="confirmPassword" class="form-control" placeholder="비밀번호를 수정하려면 입력하세요.">
		</div>
	</div>
	
	<%-- 확인 버튼 --%>
	<div class="mt-5 d-flex justify-content-end">
		<button type="button" class="btn btn-primary">정보 수정</button>
	</div>
</div>

<script>
	$(document).ready(function() {
		
		// 닉네임 중복확인 
		$('#nicknameCheckBtn').on('click', function() {
			let nickname = $('input[name=nickname]').val().trim();
			
			// 유효성 검사
			if (!nickname) {
				alert("닉네임을 입력하세요.");
				$('input[name=nickname]').focus();
				return;
			}
			if (nickname == "${user.nickname}") {
				alert("현재의 닉네임입니다.");
				return;
			}
			
			$('.nicknameMessage').addClass("d-none");
			// ajax
			$.ajax({
				type:"GET"
				, url:"/user/is-duplicated-nickname"
				, data:{"nickname":nickname}
				, success:function(data) {
					if (data.code == 200) {
						if (data.is_duplicated_nickname) {
							// 중복O
							$('#nicknameDuplTrue').removeClass('d-none');
						} else {
							// 중복X
							$('#nicknameDuplFalse').removeClass('d-none');
						}
					}
				}
				, error:function(request, status, error) {
					alert("닉네임 확인에 실패했습니다. 관리자에게 문의해주세요.");
				}
			});
		}); // 중복확인 클릭이벤트 끝
		
		// 닉네임 input change 이벤트
		$('input[name=nickname]').on('change', function() {
			$('.nicknameMessage').addClass("d-none");
			$('#NowNickname').removeClass("d-none");
			
			let nickname = $(this).val().trim();
			if (nickname == "${user.nickname}") {
				$('#NowNickname').removeClass("invisible");
				return
			}
			$('#NowNickname').addClass("invisible");
		}); // 닉네임 change이벤트 끝
	}); // 레디이벤트
</script>