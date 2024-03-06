<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
	
	<%-- 프로필 이미지 --%>	
	<div class="mt-5 d-flex">
		<div class="col-3 d-flex justify-content-center align-items-center">
			<b>프로필<br>이미지</b>
		</div>
		<%-- 이미지 태그 --%>
		<div class="col-5 d-flex justify-content-center align-items-center">
		<c:if test="${not empty user.profileImagePath}">
			<img src="${user.profileImagePath}" id="profileImg" width="70" height="70" alt="프로필 이미지">
		</c:if>
		<c:if test="${empty user.profileImagePath}">
			<img src="/static/img/blank-profile.webp" id="profileImg" width="70" height="70" alt="프로필 이미지">
		</c:if>
		</div>
		<%-- 이미지 input --%>
		<input type="file" id="profileImgFile" class="d-none" accept=".jpg, .png, .gif, .jpeg">
		<%-- 프로필 변경 버튼 --%>
		<div class="col-4 d-flex justify-content-end align-items-center">
			<button type="button" id="profileUploadBtn" class="btn btn-success">프로필 변경</button>
		</div>
	</div>
	
	<%-- 프로필 이미지 비우기 버튼 --%>
	<div class="d-flex justify-content-end my-5">
		<button type="button" id="emptyImg" class="btn btn-secondary">프로필 이미지 비우기</button>
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
	<div class="mt-5 d-flex justify-content-between">
		<a href="/user/set-hometown-view" class="btn btn-info">우리동네 설정</a>
		<button type="button" id="updateBtn" class="btn btn-primary">정보 수정</button>
	</div>
</div>

<script>
	$(document).ready(function() {
		
		// 정보수정 버튼 이벤트
		$('#updateBtn').on('click', function() {
			// 유효성 체크 - 아이디체크
			if (!$('#nicknameDuplTrue').hasClass('d-none')) {
				alert("중복된 닉네임입니다.");
				return;
			}
			if ($('#NowNickname').hasClass('invisible')) {
				alert("닉네임 중복확인이 필요합니다.");
				return;
			}
			let nickname = $('input[name=nickname]').val().trim(); 
			
			// 유효성 체크 - 비밀번호
			let password = $('input[name=password]').val(); 
			let confirmPassword = $('#confirmPassword').val();
			if (password != "" || confirmPassword != "") {
				// 비밀번호를 입력한 상태인데,
				if (password != confirmPassword) {
					// 비밀번호와 비밀번호 확인이 불일치
					alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
					$('input[name=password]').val("");
					$('#confirmPassword').val("");
					return;
				} else if (password.length < 4) {
					// 비밀번호 길이가 너무 짧음.
					alert("비밀번호는 최소 4자 이상이어야 합니다.");
					$('input[name=password]').val("");
					$('#confirmPassword').val("");
					return;
				}
			}
			
			// 프로필비우기 버튼 활성화 여부 - true:NULL로 업데이트 / false:건드리지 않을 것.
			let emptyProfile = $('#emptyImg').prop('disabled');
			
			// ajax - update
			let profileImgFile = $('#profileImgFile')[0].files[0]; // 프로필 이미지 파일
			let formData = new FormData();
			formData.append("nickname", nickname);
			formData.append("password", password);
			formData.append("profileImgFile", profileImgFile);
			formData.append("emptyProfile", emptyProfile);
			
			$.ajax({
				type:"POST"
				, url:"/user/update"
				, data:formData
				, enctype:"multipart/form-data" 
				, processData:false 
				, contentType:false
				
				, success:function(data) {
					if (data.code == 200) {
						alert("유저정보를 성공적으로 수정했습니다. 프로필 화면으로 돌아갑니다.");
						location.href="/profile?userId=" + ${userId};
					} else {
						alert("유저정보 수정에 실패했습니다.");
					}
				}
				, error:function(request, status, error) {
					alert("유저정보 업데이트에 실패했습니다. 관리자에게 문의해주세요.");
				}
			});
		}); // 정보수정 이벤트 끝
		
		// 프로필 이미지 비우기 버튼
		$('#emptyImg').on('click', function() {
			$('#profileImgFile').val(""); // input 비우기
			$('#profileImg').attr("src", "/static/img/blank-profile.webp"); // 기본이미지로 변경
			
			if (${user.profileImagePath != null}) {
				$(this).prop("disabled", true); // 프로필 비우기 버튼 비활성화
			}
		}); // 프로필 비우기 끝
		
		// 프로필 이미지 변경 이벤트
		$('#profileImgFile').on('change', function(e) {
			$('#emptyImg').prop("disabled", false); // 프로필 비우기버튼 활성화
			let file = e.target.files[0]; // 파일
			
			// null 검사
			if (!file) {
				// null로 들어옴
				if (${user.profileImagePath == null}) {
					$('#profileImg').attr("src", "/static/img/blank-profile.webp"); // 기본이미지로 변경
				} else {
					$('#profileImg').attr("src", "${user.profileImagePath}");
				}
				return;
			}
			
			// 확장자검사
			let ext = file.name.split(".").pop().toLowerCase();
			if ($.inArray(ext, ['jpg', 'png', 'gif', 'jpeg']) == -1) {
				alert("이미지 파일만 업로드 할 수 있습니다.");
				$(this).val(""); // input 비우기
				if (${user.profileImagePath == null}) {
					$('#profileImg').attr("src", "/static/img/blank-profile.webp"); // 기본이미지로 변경
				} else {
					$('#profileImg').attr("src", "${user.profileImagePath}");
				}
				return;
			}
			
			// 프로필이미지 미리보기 변경
			var reader = new FileReader();
			reader.onload = function(e) {
				$('#profileImg').attr("src", e.target.result);
			}
			reader.readAsDataURL(file);
			
		}); // 프로필 이미지 변경 이벤트
		
		// 프로필 변경 버튼 클릭이벤트 -> 숨은 프로필이미지 input 작동
		$('#profileUploadBtn').on('click', function() {
			$('#profileImgFile').click();
		}); // 프로필 변경 버튼이벤트 끝
		
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
							$('#NowNickname').removeClass("invisible");
						} else {
							// 중복X
							$('#nicknameDuplFalse').removeClass('d-none');
							$('#NowNickname').removeClass("invisible");
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
			$('#NowNickname').removeClass("invisible");
			
			let nickname = $(this).val().trim();
			if (nickname != "${user.nickname}") { // 현재의 닉네임과 같지 않으면
				$('#NowNickname').addClass("invisible"); // 글자 안보이게 한다.
			}
		}); // 닉네임 change이벤트 끝
	}); // 레디이벤트
</script>