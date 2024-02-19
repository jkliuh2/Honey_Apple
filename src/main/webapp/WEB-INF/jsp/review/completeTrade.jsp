<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="w-50 mt-5">
	<%-- 판매글 정보 --%>
	<div class="d-flex justify-content-center">
		<div class="d-flex">
			<div class="d-flex align-items-center mr-2">
				<img src="${chatRoomView.post.imgPath1}" width="60" height="60">
			</div>
			<div>
				<%-- 제목 --%>
				<div class="font-weight-bold">${chatRoomView.post.subject}</div>
				<%-- 가격 --%>
				<div>
					<fmt:formatNumber value="${chatRoomView.post.price}" />원
				</div>
				<%-- 주소 --%>
				<div>부산 광안리 -- 미설정</div>
			</div>
		</div>
	</div> <%-- 판매글 정보 끝 --%>
	
	<%-- 리뷰 내용 --%>
	<form action="/review/complete-trade" method="post">
		<%-- 만족도 점수 --%>
		<div class="mt-3">
			<div class="font-weight-bold my-3">1. *거래는 만족스러우셨나요?</div>
			<div>
				<label>
					<input type="radio" name="score" value="2"> 매우 그렇다
				</label>
			</div>
			<div>
				<label>
					<input type="radio" name="score" value="1"> 그렇다
				</label>
			</div>
			<div>
				<label>
					<input type="radio" name="score" value="0"> 보통
				</label>
			</div>
			<div>
				<label>
					<input type="radio" name="score" value="-1"> 아니다
				</label>
			</div>
			<div>
				<label>
					<input type="radio" name="score" value="-2"> 매우 아니다
				</label>
			</div>
		</div> <%-- 만족도 점수 끝 --%>
		
		<%-- 리뷰 쓰기(null 가능) --%>
		<div class="mt-5 font-weight-bold">2. 판매자에게 남기고 싶은 말이 있으신가요?</div>
		<textarea name="review" class="mt-3 form-control" rows="4"></textarea>
		
		<%-- chatId 전달용 숨은 input --%>
		<input class="d-none" name="chatId" value="${chatRoomView.chat.id}">
		
		<%-- submit 버튼 --%>
		<div class="mt-5 d-flex justify-content-between align-items-center">
			<div>
				<small class="text-primary font-weight-bold">평가를 남기셔야 거래가 완료됩니다.</small>
			</div>
			<button class="col-4 btn btn-warning form-control">작성완료</button>
		</div>
	</form>
</div>

<script>
	$(document).ready(function() {
		// submit 이벤트
		$("form").on('submit', function(e) {
			e.preventDefault();
			//alert("submit");
			
			// 유효성 체크
			let score = $('input[name=score]:checked').val();
			if (!score) {
				alert("만족도 체크는 필수사항입니다.");
				return false;
			}
			
			// 진행 확인
			if (!confirm("리뷰 작성을 완료하셨습니까?")) {
				// 취소 이벤트
				return false;
			} 
			
			// ajax - 진행
			let formParams = $(this).serialize();
			let url = $(this).attr("action");
			$.post(url, formParams)
			.done(function(data) {
				if (data.code == 200) {
					// 성공
					alert("성공적인 거래를 축하합니다. 메인페이지로 돌아갑니다.");
					location.href="/honey-apple";
				} else if (data.code == 501) {	
					// 로그인유저 != 구매자
					alert(data.error_message);
					location.href="/article/detail-view?postId=" + ${chatRoomView.post.id};
				} else {
					// 실패
					alert(data.error_message);
				}
			}) // ajax 끝
		}); // submit 이벤트 끝
	}); // 레디이벤트 끝
</script>