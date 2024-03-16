<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="d-flex flex-wrap justify-content-between">
	<%-- 게시물 Card (반복문) --%>
	<c:forEach items="${articleList}" var="article">
		<a href="/article/detail-view?postId=${article.post.id}" class="card card-size m-3"> 
			<%-- 이미지 --%> 
			<img src="${article.post.imgPath1}" class="card-img-top" alt="중고거래 이미지">
			<div class="card-body text-dark">
				<%-- 글 제목 --%>
				<h5 class="card-title">${article.post.subject}</h5>
				<%-- 가격 --%>
				<div class="font-weight-bold">
					<fmt:formatNumber value="${article.post.price}" />원
				</div>
				<%-- 판매자 위치정보 --%>
				<div>
					<small class="hometown">${article.user.hometown}</small>
				</div>
				<%-- 관심 수 --%>
				<div>
					<small>관심 ${article.interestCount}</small>
				</div>
				<%-- 업데이트 시간 --%>
				<div>
					<small>1시간 전 / 아직 미설정</small>
				</div>
			</div>
		</a>
	</c:forEach>
	<%-- 반복문 끝 --%>
	
	<%-- List가 없을 때 뜨는 내용. --%>
	<c:if test="${empty articleList}">
		<div class="d-flex justify-content-center mt-3">
			<h1>꿀템이 없습니다.</h1>
		</div>
	</c:if>
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