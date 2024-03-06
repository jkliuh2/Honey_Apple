<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="w-50">
	<h1>우리 동네 설정하기</h1>

	<%-- 동네 설정 select --%>
	<div class="mt-5 d-flex">
		<select id="sido" class="juso-select form-control">
			<option value="">시도 선택</option>
		</select> <select id="sigugun" class="juso-select form-control">
			<option value="">시군구 선택</option>
		</select> <select id="dong" class="juso-select form-control">
			<option value="">읍면동 선택</option>
		</select>
	</div>
	
	<%-- 확인 버튼 --%>
	<div class="d-flex justify-content-end">
		<button type="button" id="confirmBtn" class="btn btn-info mt-5 col-2">확인</button>
	</div>
</div>

<script>
	$(document).ready(function() {
		
		// 확인버튼 이벤트 - 동네설정 후 메인페이지로
		$('#confirmBtn').on('click', function() {
			let sido = $('#sido').val();
			let sigugun = $('#sigugun').val();
			let dong = $('#dong').val();
			
			// 유효성 검사
			if (!sido || !sigugun || !dong) {
				alert("읍면동 까지 동네설정을 해주세요.");
				return;
			}
			
			// ajax - user.hometown 수정 후 리다이렉트
			$.ajax({
				type:"POST"
				, url:"/user/set-hometown"
				, data:{"sido":sido, "sigugun":sigugun, "dong":dong}
			
				, success:function(data) {
					if (data.code == 200) {
						alert("우리동네를 설정했습니다.")
						location.href="/honey-apple";
					} else {
						alert("오류발생");
					}
				}
				, error:function() {
					alert("오류 발생. 관리자에게 문의해주세요.");
				}
			}); // ajax끝
		}); // 확인 버튼 이벤트끝
		
		
//////////////////////////////////////////// 아래로 select관련 내용들
		
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
	}); // 레디이벤트 끝
//옵션 추가하는 함수
function add_option(code, name){
    return '<option value="' + code +'">' + name +'</option>';
}		
</script>