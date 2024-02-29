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
		<%-- 아이디 관련 메세지 --%>
		<div class="mt-2">
			<small id="loginIdlength" class="text-danger d-none">아이디는 4자 이상, 20자 이하만 가능합니다.</small>
			<small id="duplLoginIdTrue" class="duplLoginIdCheckMessage text-danger d-none">중복된 아이디입니다.</small>
			<small id="duplLoginIdFalse" class="duplLoginIdCheckMessage text-success d-none">사용가능한 아이디입니다.</small>
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
			<small id="duplNicknameTrue" class="duplNicknameCheckMessage text-danger d-none">사용 중인 닉네임입니다.</small>
			<small id="duplNicknameFalse" class="duplNicknameCheckMessage text-success d-none">사용 가능한 닉네임입니다.</small>
		</div>
	</div>
	
	<%-- 비밀번호 영역 --%>
	<div class="height-70-box">
		<input type="password" id="password" class="form-control col-8" placeholder="비밀번호">
		<%-- 비밀번호 메시지 --%>
		<div class="mt-2">
			<small id="password4Word" class="passwordMessage text-danger d-none">비밀번호가 너무 짧습니다.</small>
		</div>
	</div>
	
	<%-- 비밀번호 확인 영역 --%>
	<div class="height-70-box">
		<input type="password" id="passwordCheck" class="form-control col-8" placeholder="비밀번호 확인">
	</div>
	
	<%-- 이메일 영역 --%>
	<div class="height-70-box">
		<label id="emailInputSelectBox" class="w-100">
			<div class="d-flex">
				<input type="text" id="emailId" class="form-control col-4" placeholder="이메일을 입력하세요.">
					@
				<select id="emailDomain" class="col-4 form-control">
					<option selected>naver.com</option>
					<option>gmail.com</option>
					<option>nate.com</option>
					<option>kakao.com</option>
					<option value="directInput">--직접 입력--</option>
				</select>
			</div>
		</label>
		
		<%-- 이메일 직접 입력 --%>
		<label id="emailDirectBox" class="w-100 d-none">
			<div>
				<input type="text" id="emailDirectInput" class="form-control col-8" placeholder="이메일을 입력하세요.">
			</div>
		</label>
	</div>
	
	<%-- hometown 영역 --%>
	<div class="height-70-box">
		<span class="font-weight-bold">동네 설정하기</span>
		<div class="d-flex mt-2">
			<select id="sido" class="juso-select form-control col-4">
				<option value="">시도 선택</option>
			</select> 
			<select id="sigugun" class="juso-select form-control col-4">
				<option value="">시군구 선택</option>
			</select> 
			<select id="dong" class="juso-select form-control col-4">
				<option value="">읍면동 선택</option>
			</select>
		</div>		
	</div>
	
	<%-- submit 버튼 --%>
	<div class="d-flex justify-content-center mt-5">
		<button type="button" id="signUpBtn" class="btn btn-primary form-control col-4">회원가입</button>
	</div>
</div>

<script>
//옵션 추가하는 함수
function add_option(code, name){
    return '<option value="' + code +'">' + name +'</option>';
}

	$(document).ready(function() {
//////////////////// hometown 관련		
		// 시/도 option 넣기
		$.each(hangjungdong.sido, function(index, value) {
			$('#sido').append(add_option(value.sido, value.codeNm));
		});// 시/도 option넣기 끝
		
		// 시/도 option change 이벤트
		$('#sido').on('change', function() {
			$('#sigugun').show(); // 세종특별시 예외처리 취소
			// 시군구 비우기
			$('#sigugun').empty(); 
			$('#sigugun').append(add_option("", '시군구 선택')); 
			// 읍면동 비우기
			$('#dong').empty();
			$('#dong').append(add_option("", '읍면동 선택'));
			
			let sidoCode = $(this).val(); // 시/도 code
			
			// 시군구 option 넣기
			$.each(hangjungdong.sigugun, function(index, value) {
				if (value.sido == sidoCode) {
					$('#sigugun').append(add_option(value.sigugun, value.codeNm));
				}
				
				// 세종 특별시 예외처리
				if (sidoCode == 36) {
					$('#sigugun').hide(); // 시군구 숨기기
					// sigugun select에서 자동으로 가장 위의 옵션 선택처리
					$('#sigugun option:eq(1)').attr('selected', true); 
					$('#sigugun').trigger('change');// 시군구 change이벤트 발생처리
				}
			}); // 시군구 option넣기 끝
		});// 시/도 change이벤트끝
		
		// 시군구 change이벤트
		$('#sigugun').on('change', function() {
			$('#dong').empty();
			$('#dong').append(add_option("", '읍면동 선택'));
			
			let sidoCode = $('#sido').val();
			let sigugunCode = $('#sigugun').val();
			// 읍면동 넣기
			$.each(hangjungdong.dong, function(index, value) {
				if (value.sido == sidoCode && value.sigugun == sigugunCode) {
					$('#dong').append(add_option(value.dong, value.codeNm));
				}
			});// 읍면동 넣기 끝
		}); // 시군구 change이벤트끝		
//////////////////// hometown 관련 끝

		// 이메일 직접입력
		$('#emailDomain').on('change', function() {
			//alert("이메일 선택");
			let domain = $(this).val();
			if (domain == "directInput") {
				$('#emailInputSelectBox').addClass('d-none');
				$('#emailDirectBox').removeClass('d-none');
			}
		}); // 이메일 선택이벤트 끝
		
		
		// 비밀번호 입력 이벤트
		$('#password').on('change', function() {
			$('.passwordMessage').addClass('d-none');
			let password = $('#password').val();
			
			if (password.length < 4) {
				$('#password4Word').removeClass('d-none');
			}
		});
		
		
		// 닉네임 input 글자 쓰기(중복확인 닉네임 메시지 지우기)
		$('#nickname').on('change', function() {
			$('.duplNicknameCheckMessage').addClass('d-none');
		});// 닉네임 input 끝
		
		// 닉네임 중복확인 이벤트
		$('#nicknameCheckBtn').on('click', function() {
			//alert("닉네임 중복");
			
			$('.duplNicknameCheckMessage').addClass('d-none');
			
			let nickname = $('#nickname').val().trim();
			
			// 닉네임 validation check
			if (!nickname) {
				alert("닉네임을 입력하세요.");
				$('#nickname').focus();
				return;
			}
			
			// AJAX 중복확인
			$.ajax({
				type:"GET"
				, url:"/user/is-duplicated-nickname"
				, data:{"nickname":nickname}
				, success:function(data) {
					if (data.code == 200) {
						if (data.is_duplicated_nickname) {
							// 중복O
							$('#duplNicknameTrue').removeClass('d-none');
						} else {
							// 중복X
							$('#duplNicknameFalse').removeClass('d-none');
						}
					}
				}
				, error:function(request, status, error) {
					alert("닉네임 확인에 실패했습니다. 관리자에게 문의해주세요.");
				}
			}); // ajax 끝
			
		}); // 닉네임 중복확인 끝
		
		
		// 로그인아이디 입력시 메시지
		$('#loginId').on('change', function() {
			$('#loginIdlength').addClass('d-none');
			$('.duplLoginIdCheckMessage').addClass('d-none');
			
			let loginId = $(this).val().trim();
			if (!loginId) {
				return;
			} else if (loginId.length < 4) {
				$('#loginIdlength').removeClass('d-none');
			} else if (loginId.length > 20) {
				$('#loginIdlength').removeClass('d-none');
			}
		});// 로그인아이디 change 끝
		
		// 로그인아이디 중복확인 버튼클릭
		$('#loginIdCheckBtn').on('click', function() {
			//alert("아이디 중복");
			
			$('.duplLoginIdCheckMessage').addClass('d-none');
			
			let loginId = $('#loginId').val().trim();
			
			// 아이디 길이 확인
			if (!$('#loginIdlength').hasClass('d-none')) {
				// 길이가 너무 짧음
				alert("아이디의 길이가 맞지 않습니다.");
				$('#loginId').focus();
				return;
			} else if (!loginId) {
				// 아이디 미입력
				alert("아이디를 입력하세요.");
				$('#loginId').focus();
				return;
			}
			
			// AJAX 중복확인
			$.ajax({
				type:"GET"
				, url:"/user/is-duplicated-loginId"
				, data:{"loginId":loginId}
				, success:function(data) {
					if (data.code == 200) {
						if (data.is_duplicated_loginId == true) {
							// 성공 + 중복
							$('#duplLoginIdTrue').removeClass('d-none');
							$('#loginId').focus();
						} else {
							// 성공 + 중복X
							$('#duplLoginIdFalse').removeClass('d-none');
						}
					} else {
						// DB 접근 실패
						alert(data.error_message);
					}
				}
				, error:function(request, status, error) {
					alert("중복확인에 실패했습니다. 관리자에게 문의해주새요.");
				}
			});
			
		}); // 로그인아이디 중복확인 끝
		
		// 회원가입
		$('#signUpBtn').on('click', function() {
			//alert("회원가입");
			
			// validation check
			let loginId = $('#loginId').val().trim();
			let nickname = $('#nickname').val().trim();
			let password = $('#password').val();
			let passwordCheck = $('#passwordCheck').val();
			
			if (!loginId) {
				alert("아이디를 입력하세요.");
				$('#loginId').focus();
				return;
			}
			if ($('#duplLoginIdFalse').hasClass('d-none')) {
				alert("아이디 중복확인을 해주세요.");
				$('#loginIdCheckBtn').focus();
				return;
			}
			
			if (!nickname) {
				alert("닉네임을 입력하세요.");
				$('#nickname').focus();
				return;
			}
			if ($('#duplNicknameFalse').hasClass('d-none')) {
				alert("닉네임 중복확인을 해주세요.");
				$('#nicknameCheckBtn').focus();
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
			
			// 이메일 validation check + 변수 가져오기
			let email;
			let emailDomain = $('#emailDomain').val();
			if (emailDomain == "directInput") {
				// 직접 입력
				email = $('#emailDirectInput').val().trim();
				if (!email) {
					alert("이메일을 직접 입력하세요.");
					$('#emailDirectInput').focus();
					return;
				}
			} else {
				// 입력 + 도메인 선택
				let emailId = $('#emailId').val().trim();
				if (!emailId) {
					alert("이메일 아이디를 입력하세요.");
					$('#emailId').focus();
					return;
				}
				email = emailId + "@" + emailDomain;
			}
			
			// hometown 설정
			let sido = $('#sido').val();
			let sigugun = $('#sigugun').val();
			let dong = $('#dong').val();
			if (!sido || !sigugun || !dong) {
				alert("동네를 설정해주세요.");
				return;
			}
			
			// AJAX - INSERT
			$.ajax({
				type:"POST"
				, url:"/user/sign-up"
				, data:{"loginId":loginId, "nickname":nickname, 
					"password":password, "email":email,
					"sido":sido, "sigugun":sigugun, "dong":dong}
				, success:function(data) {
					if (data.code == 200) {
						alert("회원가입을 환영합니다!");
						location.href="/honey-apple";
					} else {
						alert("회원가입 실패 : " + data.error_message);
					}
				}
				, error:function(request, status, error) {
					alert("회원가입에 실패했습니다. 관리자에게 문의해주세요.");
				}
			});// ajax 끝
		}); // 회원가입 끝
	}); // 레디이벤트 끝
</script>