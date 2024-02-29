<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="medium-size-div">
	<hr>
	
	<%-- 검색 창 및 옵션들 --%>
	<div class="mt-3">
		<%-- 유저 동네 옵션 --%>
		<div class="mt-3 d-flex">
			<div class="col-2 d-flex align-items-center">
				<label>
					<span class="font-weight-bold">주소 검색</span>
					<input type="checkbox" id="juso" class="ml-2">
				</label>
			</div>
			<select id="sido" class="juso-select form-control" disabled>
				<option value="">시도 선택</option>
			</select>
			<select id="sigugun" class="juso-select form-control" disabled>
      			<option value="">시군구 선택</option>
    		</select>
    		<select id="dong" class="juso-select form-control" disabled>
      			<option value="">읍면동 선택</option>
    		</select>
		</div>
		
		<%-- 검색창 --%>
		<div class="input-group mt-3">
			<input type="text" id="keyword" class="form-control" placeholder="물품명을 검색하세요.">
  			<div class="input-group-append">
    			<button class="btn btn-info" type="button" id="searchBtn">검색하기</button>
  			</div>
		</div>
	</div>

	<hr>
	
	<%-- 게시물 들어가는 div --%>
	<div id="contents" class="mt-3">
		<jsp:include page="../article/articleList.jsp" />
	</div>
</div>

<script>
$(document).ready(function() {
	
	// 검색 버튼 클릭이벤트
	$('#searchBtn').on('click', function() {
		let keyword = $('#keyword').val().trim();
		let sido = $('#sido').val();
		let sigugun = $('#sigugun').val();
		let dong = $('#dong').val();
		let juso = $('#juso').is(':checked'); // T/F
		
		// ajax - 검색 -> 게시물들 바꾸기
		$.ajax({
			type:"POST"
			, url:"/search"
			, data:{"keyword":keyword, "sido":sido, "sigugun":sigugun, "dong":dong, "juso":juso}
		
			, success:function(data) {
				$('#contents').html(data); // 아래 내용 바꾸기
			}
			, error:function(request, status, error) {
				alert("오류가 발생했습니다. 관리자에게 문의해주세요.");
			}
		}); // ajax 끝
		
	}); // 검색버튼 이벤트 끝

	// 주소 체크박스 선택
	$('#juso').on('change', function() {
		// 주소 select 활성화 토글
		if ($(this).is(':checked')) {
			// 체크on
			$('.juso-select').prop('disabled', false);
		} else {
			// 체크off
			$('.juso-select').prop('disabled', true);
		}
	});// 체크박스 change이벤트끝
	
///////////////////////////////////////////// 주소 관련 내용들
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
////////////////////////////////////////////////////////////////// 주소 관련 끝
}); // ready끝

//옵션 추가하는 함수
function add_option(code, name){
    return '<option value="' + code +'">' + name +'</option>';
}
</script>