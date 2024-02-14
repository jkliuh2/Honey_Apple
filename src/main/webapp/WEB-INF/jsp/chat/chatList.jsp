<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="medium-size-div mt-5">
	<c:forEach items="${chatRoomViewList}" var="chatRoomView">
	<div class="d-flex mt-3">
		<%-- 구매자 정보 --%>
		<div class="col-5 d-flex">
			<%-- 구매자 프로필사진 --%>
			<div class="mr-2">
				<img src="/static/img/blank-profile.webp" width="70" height="70">
			</div>
			
			<%-- 구매자 (이름, 매너온도) --%>
			<div>
				<div class="font-weight-bold">${chatRoomView.buyer.nickname}</div>
				<div>매너온도</div>
				<div class="font-weight-bold">${chatRoomView.buyer.temperature}°C</div>
			</div>
		</div>
		
		<%-- 가장 마지막 채팅 메시지 --%>
		<a href="/chat/chat-room-view?chatId=${chatRoomView.chat.id}&postId=${chatRoomView.chat.postId}" class="col-6 text-white">
			<%-- 판매자의 채팅(본인 채팅) --%>
			<c:if test="${not empty chatRoomView.latestChatMessage.sellerId}">
			<div class="w-100 h-100 bg-secondary d-flex align-items-center justify-content-center rounded">
				<span>
					${chatRoomView.latestChatMessage.content}
				</span>
			</div>
			</c:if>
			
			<%-- 구매자의 채팅(타인 채팅) --%>
			<c:if test="${not empty chatRoomView.latestChatMessage.buyerId}">
			<div class="w-100 h-100 bg-success d-flex align-items-center justify-content-center rounded">
				<span>
					${chatRoomView.latestChatMessage.content}
				</span>
			</div>
			</c:if>
		</a>
		
		<%-- 예약여부 --%>
		<div class="col-1 d-flex justify-content-center align-items-center">
			<c:if test="${chatRoomView.chat.tradeStatus eq '예약'}">
			<span class="font-weight-bold">예약</span>
			</c:if>
		</div>
	</div>
	</c:forEach>
</div>