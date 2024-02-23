<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="w-50 mt-5">
	<%-- 이미지(1~5개) --%>
	<div class="d-flex justify-content-center">
		<img src="${article.post.imgPath1}" alt="게시물 이미지" width="500">
	</div>
	
	<%-- 판매자 정보 --%>
	<div class="d-flex align-items-center mt-3">
		<%-- 판매자 프로필이미지 --%>
		<div class="col-2">
			<c:choose>
			<c:when test="${not empty article.user.profileImagePath}">
				<img src="${article.user.profileImagePath}" alt="판매자 프로필 이미지" width="60" height="60">
			</c:when>
			<c:otherwise>
				<img src="/static/img/blank-profile.webp" alt="판매자 프로필 이미지" width="60" height="60">
			</c:otherwise>
			</c:choose>
		</div>
		
		<%-- 판매자 nickname, hometown --%>
		<div class="col-7">
			<a href="/profile/selling-list-view?userId=${article.user.id}" 
			class="font-weight-bold text-dark">
				${article.user.nickname}
			</a>
			<div>
				${article.user.hometown} : 아직 미정
			</div>
		</div>
		
		<%-- 판매자 매너온도 --%>
		<div class="col-3">
			<div>
				매너온도
			</div>
			<div class="font-weight-bold">
				${article.user.temperature}°C
			</div>
		</div>
	</div>
	
	<hr>
	
	<%-- 여기부터 post 내용들 --%>
	<%-- 글 제목 + 거래상태 --%>
	<div class="d-flex justify-content-between align-items-center">
		<h3>${article.post.subject}</h3>
		<span class="text-info font-weight-bold">${article.post.status}</span>
	</div>
	
	<%-- 가격, 네고여부 --%>
	<div class="font-22px font-weight-bold">
		<%-- price --%>
		<span>
			<fmt:formatNumber value="${article.post.price}" />원
		</span> 
		
		<%-- negotiable --%>
		<c:if test="${article.post.negotiable eq 1}">
			<span class="text-success">네고가능</span>
		</c:if>
		<c:if test="${article.post.negotiable eq 0}">
			<span class="text-danger">네고불가</span>
		</c:if>
	</div>
	
	<%-- 글 내용 --%>
	<div class="my-3">
		<pre>${article.post.content}</pre>
	</div>
	
	<%-- 관심(+조회수) --%>
	<small class="text-secondary">
		- 관심 ${article.interestCount}
	</small>
	
	<hr>
	
	<%-- 거래 희망장소 --%>
	<div class="my-3">
		<div class="font-weight-bold">- 거래희망장소</div>
		<div>
			지도 들어갈 공간
		</div>
	</div>
	
	<%-- 버튼 공간 --%>
	<div class="mt-5 d-flex align-items-center">
		<%-- 관심 버튼(구매자용) --%>
		<div class="col-3 d-flex justify-content-center">
			<c:if test="${article.post.sellerId ne userId}">
				<a href="#" id="interestToggle">
					<img src="/static/img/
					<c:if test="${article.filledHeart eq false}">
					empty
					</c:if>
					<c:if test="${article.filledHeart}">
					fill
					</c:if>
					-heart.png" width="35" height="35">
				</a>
			</c:if>
		</div>
		
		<%-- 거래 제안하기(구매자용) or 제안 목록(판매자용) --%>
		<div class="col-6">
			<%-- 채팅방으로 이동(구매자전용, 비-로그인도 보임) --%>
			<c:if test="${article.post.sellerId ne userId}">
				<a href="/chat/chat-room-view?postId=${article.post.id}" class="btn btn-primary form-control">거래 제안하기</a>
			</c:if>
			
			<%-- 채팅목록으로 이동(판매자전용) --%>
			<c:if test="${article.post.sellerId eq userId}">
				<a href="/chat/chat-list-view?postId=${article.post.id}" class="btn btn-primary form-control">거래 제안목록</a>
			</c:if>
		</div>
		
		<%-- 수정버튼(판매자용) --%>
		<div class="col-3">
			<c:if test="${article.post.sellerId eq userId && article.post.status eq '판매중'}">
				<a href="/post/update-view?postId=${article.post.id}" class="btn btn-secondary form-control">수정</a>
			</c:if>
		</div>
	</div>
</div>

<script>
	$(document).ready(function() {
		
		// 관심 토글버튼
		$('#interestToggle').on('click', function(e) {
			e.preventDefault(); // <a> 페이지 올라가기 방지
			//alert("관심!");
			let postId = ${article.post.id}
			
			$.ajax({
				url:"/interest/" + postId
				
				, success:function(data) {
					if (data.code == 200) {
						// 토글 성공시 => 새로고침
						location.reload();
					} else if (data.code == 300) {
						// 로그인 만료시
						alert(data.error_message);
						location.href="/user/sign-in-view";
					} else {
						alert(data.error_message);
					}
				}
				, error:function(request, status, error) {
					alert("오류가 발생했습니다. 관리자에게 문의해주세요.");
				}
			});//ajax 끝
		}); // 관심 토글이벤트 끝
	}); // 레디이벤트 끝
</script>