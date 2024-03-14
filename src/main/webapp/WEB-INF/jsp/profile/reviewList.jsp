<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="mt-3">
	<c:forEach items="${reviewCardList}" var="reviewCard">
	<div>
		<img src="/static/img/blank-profile.webp" width="25" height="25" alt="프로필 이미지">
		<small class="font-weight-bold ml-2">${reviewCard.buyer.nickname}</small>
		<small class="ml-2 hometown">${reviewCard.buyer.hometown}</small>
	</div>
	<div class="mt-2">
		${reviewCard.review.review}
	</div>
	<div class="mt-2">
		<small class="gray-text">3개월 전(createdAt)</small>
	</div>
	<hr>
	</c:forEach>
</div>


<script>
// 주소 8자리 -> 실제 주소로 변경하는 script 덩어리

// 주소코드 8자리 -> 주소로 변경하는 메소드
function transHometown(selector) {
	var code = selector.textContent;
	let sidoCode = code.slice(0,2);
	let sigugunCode = code.slice(2, 5);
	let dongCode = code.slice(5);
	
	$.each(hangjungdong.dong, function(index, value) {
		if (sidoCode == value.sido 
				&& sigugunCode == value.sigugun
				&& dongCode == value.dong) {
			dongCode = value.codeNm;
		}
	});
	$.each(hangjungdong.sigugun, function(index, value) {
		if (sidoCode == value.sido && sigugunCode == value.sigugun) {
			sigugunCode = value.codeNm;
		}
	});
	$.each(hangjungdong.sido, function(index, value) {
		if (sidoCode == value.sido) {
			sidoCode = value.codeNm;
		}
	});
	let str = sidoCode + " " + sigugunCode + " " + dongCode;
	selector.innerText=str;
} // 코드 -> 주소 변경 메소드 끝

	$(document).ready(function() {
		// user.hometown 일괄 변경하기
		var arr = $('.hometown');
		for (let i = 0; i < arr.length; i++) {
			transHometown(arr[i]);
		}
	});
</script>