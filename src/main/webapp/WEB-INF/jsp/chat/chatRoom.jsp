<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="medium-size-div mt-5">
	<%-- 게시글 및 판매자 정보(header) --%>
	<div id="chat-room-header" class="d-flex justify-content-between align-items-center">
		<%-- 게시글 간략 정보 --%>
		<div class="d-flex">
			<div class="d-flex align-items-center mr-2">
				<img src="/static/img/test-img.webp" width="60" height="60">
			</div>
			<div>
				<%-- 제목 --%>
				<div class="font-weight-bold">
					${post.subject}
				</div>
				<%-- 가격 --%>
				<div>
					<fmt:formatNumber value="${post.price}" />원
				</div>
				<%-- 주소 --%>
				<div>
					부산 광안리
				</div>
			</div>
		</div>
		
		<%-- 판매자 정보 --%>
		<div class="d-flex">
			<div class="d-flex align-items-center mr-2">
				<img src="/static/img/blank-profile.webp" width="60" height="60">
			</div>
			<div>
				<div class="font-weight-bold">
					${seller.nickname}
				</div>
				<div>
					매너온도
				</div>
				<div>
					${seller.temperature}°C
				</div>
			</div>
		</div>
	</div>
	
	<hr>
	
	<%-- 채팅 메시지 --%>
	<div id="chat-message-section">
		<ul>
		<c:forEach items="${chatMessageList}" var="chatMessage">
			<c:choose>
				<c:when test="${chatMessage.sellerId eq userId || chatMessage.buyerId eq userId}">
					<li class="mt-1">
						<div class="my-message d-flex justify-content-end">
							<div class="message d-flex align-items-center justify-content-center">${chatMessage.content}</div>
						</div>
					</li>
				</c:when>
				<c:otherwise>
					<li class="mt-1">
						<div class="you-message d-flex justify-content-front">
							<div class="message d-flex align-items-center justify-content-center">${chatMessage.content}</div>
						</div>
					</li>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		</ul>
	</div>
	
	<hr>
	
	<%-- 예약 및 입력칸 --%>
	<form method="post" action="/chat/enter-message">
	<div class="mt-3 d-flex justify-content-between">
		<div class="col-2">
			<c:if test="${not empty chatRoom.id || userId == post.sellerId}">
				<button type="button" class="btn btn-secondary form-control">예약</button>
			</c:if>
		</div>
		<div class="col-7 input-group">
			<input type="text" id="content" name="content" class="form-control" placeholder="내용을 입력하세요.">
			<input type="text" class="d-none" name="chatId" value="${chatRoom.id}">
			<input type="text" class="d-none" name="postId" value="${post.id}">
			<div class="input-group-append">
				<button class="btn btn-primary" type="submit" disabled id="contentBtn">입력</button>
			</div>
		</div>
	</div>
	</form>
</div>

<script>
	$(document).ready(function() {
		// content change이벤트
		$('#content').on('change', function() {
			$('#contentBtn').prop('disabled', false);
			
			let content = $(this).val();
			if (!content) {
				$('#contentBtn').prop('disabled', true); // submit 버튼 활성화
			}
		}); // content change 이벤트끝
		
	});// 레디이벤트 끝
</script>